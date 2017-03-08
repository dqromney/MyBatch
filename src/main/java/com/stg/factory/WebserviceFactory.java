package com.stg.factory;

import lombok.extern.slf4j.Slf4j;

import static com.stg.factory.Webservice.QUANDL_DATABASE_V3;
import static com.stg.factory.Webservice.QUANDL_DATATABLE_V3;
import static com.stg.factory.Webservice.WIKI_EOD_FILE_V3;
import static com.stg.factory.Webservice.WIKI_EOD_V3;

/**
 * Data Source Factory.
 *
 * Created by dqromney on 2/20/17.
 */
@Slf4j
public class WebserviceFactory {

    public Webservice getWebservice(String pWebService) {
        if (pWebService == null) {
            return null;
        }
        if (pWebService.equalsIgnoreCase(WIKI_EOD_V3)) {
            return new WikiV3Eod();
        }
        if (pWebService.equalsIgnoreCase(WIKI_EOD_FILE_V3)) {
            return new WikiFileEodV3();
        }
        if (pWebService.equalsIgnoreCase(QUANDL_DATABASE_V3)) {
            return new QuandlDatabasesV3();
        }
        if (pWebService.equalsIgnoreCase(QUANDL_DATATABLE_V3)) {
            return new QuandlDatatableV3();
        }
        return null;
    }
}
