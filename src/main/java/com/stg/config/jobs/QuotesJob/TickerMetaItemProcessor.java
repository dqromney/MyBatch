package com.stg.config.jobs.QuotesJob;

import com.stg.dto.Ticker;
import com.stg.factory.Webservice;
import com.stg.factory.WebserviceFactory;
import com.stg.utils.Converters;
import org.springframework.batch.item.ItemProcessor;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;

/**
 * Ticker Meta Item Processor.
 *
 * Created by dqromney on 3/9/17.
 */
public class TickerMetaItemProcessor implements ItemProcessor<Ticker, Ticker> {
    /**
     * Process the provided item, returning a potentially modified or new item for continued
     * processing.  If the returned result is null, it is assumed that processing of the item
     * should not continue.
     *
     * @param item to be processed
     * @return potentially modified or new item for continued processing, null if processing of the
     * provided item should not continue.
     * @throws Exception
     */
    @Override
    public Ticker process(Ticker item) throws Exception {
        // Retrieve meta data from Quandl Stock Metadata Webservice
        InputStream is = getUrlConnection(item.getDatasetCode()).getInputStream();

        JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonReader jsonReader = factory.createReader(is);
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();
        is.close();

        JsonObject topJsonObject = jsonObject.getJsonObject("dataset");
        Long sharedId = new Long(topJsonObject.getInt("id", 0));
        String datasetCode = topJsonObject.getString("dataset_code"); // Symbol
        String databaseCode = topJsonObject.getString("database_code");
        String name = escapeHtml(topJsonObject.getString("name")); // Long Name
        String description = escapeHtml(topJsonObject.getString("description")); // Description
        Date refreshedAt = Converters.strToDate(topJsonObject.getString("refreshed_at"));
        Date newestAvailableDate = Converters.strToDate(topJsonObject.getString("newest_available_date"));
        Date oldestAvailableDate = Converters.strToDate(topJsonObject.getString("oldest_available_date"));
        String columnNames = topJsonObject.getJsonArray("column_names").toString();
        String frequency = topJsonObject.getString("frequency");
        String type = topJsonObject.getString("type");
        Boolean premium = topJsonObject.getBoolean("premium");
        Long databaseId = new Long(topJsonObject.getInt("database_id", 0));

        Ticker ticker = Ticker.builder()
                .sharedId(sharedId)
                .datasetCode(datasetCode)
                .databaseCode(databaseCode)
                .name(name)
                .description(description)
                .refreshedAt(refreshedAt)
                .newestAvailableDate(newestAvailableDate)
                .oldestAvailableDate(oldestAvailableDate)
                .columnNames(columnNames)
                .frequency(frequency)
                .type(type)
                .premium(premium)
                .databaseId(databaseId)
                .build();

        return ticker;
    }

    /**
     * Get the URL connection.
     *
     * @return a {@link URLConnection} object
     * @throws IOException
     */
    private URLConnection getUrlConnection(String pSymbol) throws IOException {
        Webservice ws = new WebserviceFactory().getWebservice(Webservice.WIKI_META_V3);
        URL url = new URL(String.format("%1$s%2$s/metadata.json?api_key=%3$s",
                ws.getUrl(), pSymbol, Webservice.API_KEY));
        return url.openConnection();
    }

}
