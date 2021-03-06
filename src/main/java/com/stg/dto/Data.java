package com.stg.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Data model class.
 *
 * Created by dqromney on 3/1/17.
 */
@lombok.Data
@Builder
public class Data {
    String symbol;
    Date date;
    BigDecimal open;
    BigDecimal high;
    BigDecimal low;
    BigDecimal close;
    Long volume;
    BigDecimal exDividend;
    BigDecimal splitRatio;
    BigDecimal adjOpen;
    BigDecimal adjHigh;
    BigDecimal adjLow;
    BigDecimal adjClose;
    Long adjVolume;
}
