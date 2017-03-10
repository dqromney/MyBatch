package com.stg.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Ticker model class.
 *
 * Created by dqromney on 3/2/17.
 */
@Data
@Builder
public class Ticker {
    Long id;
    Long sharedId;
    String datasetCode;
    String databaseCode;
    String name;
    String description;
    Date refreshedAt;
    Date newestAvailableDate;
    Date oldestAvailableDate;
    String columnNames;
    String frequency;
    String type;
    Boolean premium;
    Long databaseId;

    public Ticker() {
        // Empty
    }

    public Ticker(Long id, Long sharedId, String datasetCode, String databaseCode, String name, String description, Date refreshedAt, Date newestAvailableDate, Date oldestAvailableDate, String columnNames, String frequency, String type, Boolean premium, Long databaseId) {
        this.id = id;
        this.sharedId = sharedId;
        this.datasetCode = datasetCode;
        this.databaseCode = databaseCode;
        this.name = name;
        this.description = description;
        this.refreshedAt = refreshedAt;
        this.newestAvailableDate = newestAvailableDate;
        this.oldestAvailableDate = oldestAvailableDate;
        this.columnNames = columnNames;
        this.frequency = frequency;
        this.type = type;
        this.premium = premium;
        this.databaseId = databaseId;
    }
}
