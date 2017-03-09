package com.stg.config.jobs.QuotesJob;

import com.stg.dto.Data;
import lombok.extern.java.Log;

import javax.batch.api.chunk.ItemProcessor;

/**
 * Data Item Processor.
 *
 * Created by dqromney on 3/8/17.
 */
@Log
public class DataItemProcessor implements ItemProcessor, org.springframework.batch.item.ItemProcessor<Data, Data> {

    /**
     * The processItem method is part of a chunk
     * step. It accepts an input item from an
     * item reader and returns an item that gets
     * passed onto the item writer. Returning null
     * indicates that the item should not be continued
     * to be processed.  This effectively enables processItem
     * to filter out unwanted input items.
     *
     * @param item specifies the input item to process.
     * @return output item to write.
     * @throws Exception thrown for any errors.
     */
    @Override
    public Object processItem(Object item) throws Exception {
        return item;
    }

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
