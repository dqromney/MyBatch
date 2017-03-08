package com.stg.config.jobs.QuotesJob;

import lombok.Builder;
import lombok.Data;
import lombok.extern.java.Log;

/**
 * Wiki EOD Datatable Bulk Download.
 *
 * Created by dqromney on 3/6/17.
 */
@Log
@Data
@Builder
public class DatatableBuildDownload {
    private String link;
    private String status;
    private String dataSnapshotTime;
    private String lastRefreshedTime;

    public DatatableBuildDownload() {
        // Empty
    }

    public DatatableBuildDownload(String link, String status, String dataSnapshotTime, String lastRefreshedTime) {
        this.link = link;
        this.status = status;
        this.dataSnapshotTime = dataSnapshotTime;
        this.lastRefreshedTime = lastRefreshedTime;
    }

}
