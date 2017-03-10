package com.stg.config.jobs.QuotesJob;

import com.stg.dto.Data;
import lombok.extern.java.Log;
import org.springframework.batch.item.ItemProcessor;

/**
 * Data Item Processor.
 *
 * Created by dqromney on 3/8/17.
 */
@Log
public class ImportDataItemProcessor implements ItemProcessor<Data, Data> {

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
    public Data process(Data item) throws Exception {
        return item;
    }
}
