package com.stg.config.jobs.QuotesJob;

import com.stg.dto.Data;
import com.stg.utils.Converters;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.math.BigDecimal;

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

        String symbol = fieldSet.readString("symbol");
        Long volume = Converters.strToLong(fieldSet.readString("volume"));
        Long adjVolume = Converters.strToLong(fieldSet.readString("adjVolume"));

        Data data = Data.builder()
                .symbol(symbol)
                .date(fieldSet.readDate("date"))
                .open(fieldSet.readBigDecimal("open", new BigDecimal(0.0)))
                .high(fieldSet.readBigDecimal("high", new BigDecimal(0.0)))
                .low(fieldSet.readBigDecimal("low", new BigDecimal(0.0)))
                .close(fieldSet.readBigDecimal("close", new BigDecimal(0.0)))
                .volume(volume)
                .exDividend(fieldSet.readBigDecimal("exDividend", new BigDecimal(0.0)))
                .splitRatio(fieldSet.readBigDecimal("splitRatio", new BigDecimal(0.0)))
                .adjOpen(fieldSet.readBigDecimal("adjOpen", new BigDecimal(0.0)))
                .adjHigh(fieldSet.readBigDecimal("adjHigh", new BigDecimal(0.0)))
                .adjLow(fieldSet.readBigDecimal("adjLow", new BigDecimal(0.0)))
                .adjClose(fieldSet.readBigDecimal("adjClose", new BigDecimal(0.0)))
                .adjVolume(adjVolume)
                .build();
        return data;
    }
}
