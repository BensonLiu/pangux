package com.eadmarket.pangu.util.seq;

import java.util.List;

/**
 * Created by liu on 3/28/14.
 */
public interface ISequenceGenerator {

  /**
   * 获取一个id
   */
  Long get(String seqName) throws SeqException;

  /**
   * 获取多个id
   */
  List<Long> getMulti(String seqName, Integer num) throws SeqException;
}
