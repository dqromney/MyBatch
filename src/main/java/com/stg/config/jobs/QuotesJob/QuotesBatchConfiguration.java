package com.stg.config.jobs.QuotesJob;

import com.stg.config.jobs.JobCompletionNotificationListener;
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
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
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
 *
 * Created by dqromney on 1/30/17.
 */
@Configuration
@EnableBatchProcessing
@Log
//@Import({ MysqlDataSourceConfiguration.class })
public class QuotesBatchConfiguration {

    private String targetDirectory = "./src/main/resources/";
    private String targetFile = "WIKI.zip";
    private Resource inputResource;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

//    @Autowired
//    public DataSource dataSource;

    @Bean
    public Job processEodJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("processEodJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(downloadStep())
                .next(decompressStep())
                .end()
                .build();
    }

    @Bean
    public Step downloadStep() {
        return stepBuilderFactory.get("downloadStep")
                .tasklet((contribution, chunkContext) -> {
                    DatatableBuildDownload download = getDatatableBuildDownload();
                    log.info(download.toString());
                    Download.downloadUsingNIO(download.getLink(), targetDirectory + targetFile);
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step decompressStep() {
        return stepBuilderFactory.get("decompressStep")
                .tasklet((contribution, chunkContext) -> {
                    File inputFile = new File(targetDirectory + targetFile);

                    if (inputFile.exists()) {

                        InputStream is = new FileInputStream(inputFile);
                        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));

                        File targetDirectoryAsFile = new File(targetDirectory);
                        if (!targetDirectoryAsFile.exists()) {
                            FileUtils.forceMkdir(targetDirectoryAsFile);
                        }
                        File target = new File(targetDirectory, "WIKI.csv");
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

        DatatableBuildDownload datatableBuildDownload = new DatatableBuildDownload();
        JsonObject topJsonObject = jsonObject.getJsonObject("datatable_bulk_download");

        JsonObject innerInnerJsonObject = topJsonObject.getJsonObject("file");
        datatableBuildDownload.setLink(innerInnerJsonObject.getString("link"));
        datatableBuildDownload.setStatus(innerInnerJsonObject.getString("status"));
        datatableBuildDownload.setDataSnapshotTime(innerInnerJsonObject.getString("data_snapshot_time"));

        innerInnerJsonObject = topJsonObject.getJsonObject("datatable");
        datatableBuildDownload.setLastRefreshedTime(innerInnerJsonObject.getString("last_refreshed_time"));
        return datatableBuildDownload;
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
