package com.eadmarket.pangu.domain;

import lombok.Data;

/**
 * Created by liu on 3/27/14.
 */
@Data
public class KnowledgeDO {

    public final static int NORMAL_STATUS = 1;

    public final static int DELETED_STATUS = 2;

    private Long id;

    private int status = NORMAL_STATUS;

    private String summary;

    private Long category;

}
