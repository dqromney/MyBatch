package com.stg.config.jobs.QuotesJob;

import com.stg.config.jobs.JobCompletionNotificationListener;
import com.stg.dto.Data;
import com.stg.dto.DatatableBuildDownload;
import com.stg.factory.Webservice;
import com.stg.factory.WebserviceFactory;
import com.stg.utils.Download;
import lombok.extern.java.Log;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.sql.DataSource;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipInputStream;

/**
 * Stock Data Batch Configuration.
 * <p>
 * Created by dqromney on 1/30/17.
 */
@Log
@Configuration
@EnableBatchProcessing
//@Import({ MysqlDataSourceConfiguration.class })
public class QuotesBatchConfiguration {

    private String targetDirectory = "./src/main/resources/";
    private String targetFile = "WIKI.zip";
    private Resource inputResource;
    private DatatableBuildDownload download;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Bean
    public Job processEodJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("processEodJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(downloadStep())
                .next(decompressStep())
                .next(importDataStep())
                .next(cleanupStep())
                .end()
                .build();
    }

    @Bean
    public Step downloadStep() {
        return stepBuilderFactory.get("downloadStep")
                .tasklet((contribution, chunkContext) -> {
                    download = getDatatableBuildDownload();
                    // log.info(download.toString());
                    Download.downloadUsingNIO(download.getLink(), targetDirectory + download.getZipFileName());
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step decompressStep() {
        return stepBuilderFactory.get("decompressStep")
                .tasklet((contribution, chunkContext) -> {
                    String[] csvTargetFileArray = download.getZipFileName().split("[.]");
                    String csvTargetFile = csvTargetFileArray[0] + ".csv";

                    File inputFile = new File(targetDirectory, download.getZipFileName());
                    File target = new File(targetDirectory, csvTargetFile);
                    download.setCsvFileName(csvTargetFile);

                    if (inputFile.exists() && !target.exists()) {

                        InputStream is = new FileInputStream(inputFile);
                        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));

                        File targetDirectoryAsFile = new File(targetDirectory);
                        if (!targetDirectoryAsFile.exists()) {
                            FileUtils.forceMkdir(targetDirectoryAsFile);
                        }
                        BufferedOutputStream dest = null;
                        while (zis.getNextEntry() != null) {
                            if (!target.exists()) {
                                target.createNewFile();
                            }
                            FileOutputStream fos = new FileOutputStream(target);
                            dest = new BufferedOutputStream(fos);
                            IOUtils.copy(zis, dest);
                            dest.flush();
                            dest.close();
                        }
                        zis.close();
                        if (!target.exists()) {
                            throw new IllegalStateException("Could not decompress anything from the archive:");
                        }
                    }
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step importDataStep() {
        return stepBuilderFactory.get("importDataStep")
                .<Data, Data>chunk(500)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Step cleanupStep() {
        return stepBuilderFactory.get("cleanupStep")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Cleanup task executing...");
                    if (download != null) {
                        File zipFile = new File(targetDirectory, download.getZipFileName());
                        File csvFile = new File(targetDirectory, download.getCsvFileName());

//                    if (csvFile.exists()) {
//                        if (csvFile.delete()) {
                        log.info(String.format("CSV file removed: [%1$s]", csvFile.toString()));
//                        } else {
//                            log.warning(String.format("Error CSV file NOT removed: [%1$s]", csvFile.toString()));
//                        }
//                    }

//                    if (zipFile.exists()) {
//                        if (zipFile.delete()) {
                        log.info(String.format("ZIP file removed: [%1$s]", zipFile.toString()));
//                        } else {
//                            log.warning(String.format("Error ZIP file NOT removed: [%1$s]", zipFile.toString()));
//                        }
//                    }
                    }
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public DataFieldSetMapper<Data> dataDataFieldSetMapper() {
        return new DataFieldSetMapper<Data>();
    }

    @Bean
    public FlatFileItemReader<Data> reader() {
        FlatFileItemReader<Data> reader = new FlatFileItemReader<>();
        DatatableBuildDownload download = DatatableBuildDownload.builder()
                .zipFileName("WIKI_PRICES_212b326a081eacca455e13140d7bb9db.zip")
                .csvFileName("WIKI_PRICES_212b326a081eacca455e13140d7bb9db.csv")
                .build();
        if (download != null) {
            reader.setResource(new ClassPathResource(download.getCsvFileName()));
            reader.setLineMapper(new DefaultLineMapper<Data>() {{
                setLineTokenizer(new DelimitedLineTokenizer() {{
                    setNames(new String[]{
                            "symbol", "date", "open", "high", "low", "close", "volume",
                            "exDividend", "splitRatio",
                            "adjOpen", "adjHigh", "adjLow", "adjClose", "adjVolume"});
                }});
                setFieldSetMapper(new DataFieldSetMapper<Data>() {{
                    // setTargetType(Data.class);
                }});
            }});
        }
        reader.setLinesToSkip(1);
        return reader;
    }

    @Bean
    public DataItemProcessor processor() {
        return new DataItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Data> writer() {
        JdbcBatchItemWriter<Data> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO data (symbol, date, open, high, low, close, volume, ex_dividend, split_ratio, adj_open, adj_high, adj_low, adj_close, adj_volume) " +
                "VALUES (:symbol, :date, :open, :high, :low , :close , :volume , :exDividend, :splitRatio, :adjOpen , :adjHigh , :adjLow , :adjClose , :adjVolume )");
        writer.setDataSource(dataSource);
        System.out.print(".");
        return writer;
    }

    // ----------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------

    /**
     * Get DatatableBuildDownload object.
     *
     * @return {@link DatatableBuildDownload} object
     * @throws IOException
     */
    private DatatableBuildDownload getDatatableBuildDownload() throws IOException {

        InputStream is = getUrlConnection().getInputStream();

//        FileOutputStream os = new FileOutputStream(new File("./wiki.json"));

//        byte[] buffer = new byte[1024];
//        int len = is.read(buffer);
//        while (len != -1) {
//            os.write(buffer, 0, len);
//            len = is.read(buffer);
//        }

        JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonReader jsonReader = factory.createReader(is);
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();
        is.close();

        JsonObject topJsonObject = jsonObject.getJsonObject("datatable_bulk_download");
        JsonObject fileJsonObject = topJsonObject.getJsonObject("file");
        String link = fileJsonObject.getString("link");
        String zipFileName = getFileName(link);
        JsonObject datatableJsonObject = topJsonObject.getJsonObject("datatable");

        DatatableBuildDownload datatableBuildDownload = DatatableBuildDownload.builder()
                .link(link)
                .zipFileName(zipFileName)
                .status(fileJsonObject.getString("status"))
                .dataSnapshotTime(fileJsonObject.getString("data_snapshot_time"))
                .lastRefreshedTime(datatableJsonObject.getString("last_refreshed_time"))
                .build();

        return datatableBuildDownload;
    }

    /**
     * DatatableBuildDownload(link=https://duf9k3nptkna3.cloudfront.net/WIKI/PRICES/WIKI_PRICES_212b326a081eacca455e13140d7bb9db.zip?api_key=iDys-FbJZTMWF56LXBie&snapshot_time=2017-03-08+01%3A00%3A10+UTC&Expires=1488992244&Signature=TZHkqTMzCnzOxSbLyfBXdLQvv9L11ERT405v2L4QmJF5l-miSva3Ebul0WAAQzjLSxn-5hLnujcUBHTAJ9ZZs9H5fruUrw62eXTqSuWYmDH9sdQNzP5OHfu6Cb-U4e34pV-ADydjaaPQ-o~pgPsjtzm2uaIEq5Xnj557-AGuYJTQLdFDnrawW2OhDXi~HqDkN~d3n-Nr5miy9gMBKoO8liN8YCwri9FvG8u9lBqhhZCQtULziGft495IpMT7BQBiTT1NZk9y07HBgxFaoCjnK9GBA6tiGYeHQzGx29--7yQtXcfq1vyxmOuLa95xXZy82-2LBrClI84BH7LT5enIFA__&Key-Pair-Id=APKAJ22GRK7AQ76GWFJQ, status=fresh, dataSnapshotTime=2017-03-08 01:00:10 UTC, lastRefreshedTime=2017-03-08 00:35:36 UTC)
     *
     * @param link
     * @return
     */
    private String getFileName(String link) {
        String[] parts;
        String fileName = null;
        if (link != null) {
            parts = link.split("/");
            for (String item : parts) {
                if (item.startsWith("WIKI_PRICES_")) {
                    int endIndex = item.indexOf("?");
                    fileName = item.substring(0, endIndex);
                }
            }
        }
        return fileName;
    }

    /**
     * Get the URL connection.
     *
     * @return a {@link URLConnection} object
     * @throws IOException
     */
    private URLConnection getUrlConnection() throws IOException {
        Webservice ws = new WebserviceFactory().getWebservice(Webservice.WIKI_EOD_FILE_V3);
        URL url = new URL(String.format("%1$s%2$s&api_key=%3$s", ws.getUrl(), "PRICES.json?qopts.export=true", Webservice.API_KEY));
        return url.openConnection();
    }

    // ----------------------------------------------------------------
    // Access methods
    // ----------------------------------------------------------------

    public void setInputResource(Resource inputResource) {
        this.inputResource = inputResource;
    }

    public void setTargetDirectory(String targetDirectory) {
        this.targetDirectory = targetDirectory;
    }

    public void setTargetFile(String targetFile) {
        this.targetFile = targetFile;
    }

}
