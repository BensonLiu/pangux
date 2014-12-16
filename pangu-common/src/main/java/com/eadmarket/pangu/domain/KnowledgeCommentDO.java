package com.eadmarket.pangu.domain;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

/**
 * Created by liu on 4/4/14.
 */
@Data
@ToString
public class KnowledgeCommentDO {

  private Long id;

  private String srcIp;

  @JSONField(serialize = false)
  private Long knowledgeId;

  private String comment;

  @JSONField(serialize = false)
  private int status = 1;

  @JSONField(name = "createDate", format = "yyyy-MM-dd HH:mm")
  private Date gmtCreate;

}
