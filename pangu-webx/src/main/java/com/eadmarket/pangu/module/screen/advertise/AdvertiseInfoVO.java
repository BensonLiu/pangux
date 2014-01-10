package com.eadmarket.pangu.module.screen.advertise;

import lombok.Data;

/**
 * AdvertiseInfo对应的VO对象
 *
 * @author liuyongpo@gmail.com
 */
@Data
public class AdvertiseInfoVO {
    private int type;

    private String content = "";

    private String clickUrl;

    private Integer width;

    private Integer height;

    private Integer status;

}
