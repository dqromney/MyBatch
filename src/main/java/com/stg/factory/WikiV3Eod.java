package com.stg.factory;

import lombok.extern.slf4j.Slf4j;

/**
 * Wiki End of Day Quotes Web Service.
 *
 * Created by dqromney on 2/20/17.
 */
@Slf4j
public class WikiV3Eod implements Webservice {

    @Override
    public String getSource() {
        return "https://www.quandl.com";
    }

    @Override
    public String getApi() {
        return "api/";
    }

    @Override
    public String getVersion() {
        return "v3/";
    }

    @Override
    public String getDataset() {
        return "datasets/WIKI/";
    }

    @Override
    public String getUrl() {
        return new StringBuilder()
                .append(getSource())
                .append(getApi())
                .append(getVersion())
                .append(getDataset()).toString();
    }

}
