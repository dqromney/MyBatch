package com.stg.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Wiki EOD Datatable Bulk Download.
 *
 * Created by dqromney on 3/6/17.
 */
@Data
@Builder
public class DatatableBuildDownload {
    private String link;
    private String zipFileName;
    private String csvFileName;
    private String status;
    private String dataSnapshotTime;
    private String lastRefreshedTime;
}
