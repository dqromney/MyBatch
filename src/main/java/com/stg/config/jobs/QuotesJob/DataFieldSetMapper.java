package com.stg.config.jobs.QuotesJob;

import com.stg.dto.Data;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/**
 * Data field-set mapper.
 *
 * Created by dqromney on 3/8/17.
 */
public class DataFieldSetMapper<D> implements FieldSetMapper<Data> {
    /**
     * Method used to map data obtained from a {@link FieldSet} into an object.
     *
     * @param fieldSet the {@link FieldSet} to map
     * @throws BindException if there is a problem with the binding
     */
    @Override
    public Data mapFieldSet(FieldSet fieldSet) throws BindException {
        Data data = Data.builder()
                .symbol(fieldSet.readString("symbol"))
                .date(fieldSet.readDate("date"))
                .open(fieldSet.readBigDecimal("open"))
                .high(fieldSet.readBigDecimal("high"))
                .low(fieldSet.readBigDecimal("low"))
                .close(fieldSet.readBigDecimal("close"))
                .volume(fieldSet.readLong("volume"))
                .exDividend(fieldSet.readBigDecimal("exDividend"))
                .splitRatio(fieldSet.readBigDecimal("splitRatio"))
                .adjOpen(fieldSet.readBigDecimal("adjOpen"))
                .adjHigh(fieldSet.readBigDecimal("adjHigh"))
                .adjLow(fieldSet.readBigDecimal("adjLow"))
                .adjClose(fieldSet.readBigDecimal("adjClose"))
                .adjVolume(fieldSet.readLong("adjVolume"))
                .build();
        return data;
    }
}
