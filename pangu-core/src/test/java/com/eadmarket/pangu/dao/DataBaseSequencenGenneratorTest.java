package com.eadmarket.pangu.dao;

import com.eadmarket.pangu.BaseTest;
import com.eadmarket.pangu.util.seq.ISequenceGenerator;
import com.eadmarket.pangu.util.seq.SeqException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author liuyongpo@gmail.com
 */
public class DataBaseSequencenGenneratorTest extends BaseTest {

    Logger LOG = LoggerFactory.getLogger(DataBaseSequencenGenneratorTest.class);

	@Resource private ISequenceGenerator sequenceGenerator;
	
	@Test public void testGetConcurrently() throws Exception {

        for (int i = 0; i < 100; i ++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int j = 0; j < 1000; j ++) {
                            Long id = sequenceGenerator.get("knowledge_cate_1");
                            LOG.error("-----------------------------------" + id);
                        }
                    } catch (SeqException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }

        TimeUnit.SECONDS.sleep(30);
	}

    @Test public void testGet() throws Exception {
        sequenceGenerator.get("knowledge_cate_1");
    }



}
