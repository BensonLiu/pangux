package com.eadmarket.pangu.domain;

import lombok.Data;

import java.util.Date;

/**
 * Created by liu on 1/13/14.
 */
@Data
public class ReportInfoDO {

    public static final int DISPLAY_OPT_TYPE = 1;

    public static final int CLICK_OPT_TYPE = 2;

    /**
     * 记录编号
     */
    private Long id;
    /**
     * 广告位编号
     */
    private Long advertiseId;
    /**
     * 交易编号
     */
    private Long tradeId;
    /**
     * 对应的操作类型，1为展示，2为点击
     */
    private int operationType;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 访问来源ip
     */
    private String ip;

}
