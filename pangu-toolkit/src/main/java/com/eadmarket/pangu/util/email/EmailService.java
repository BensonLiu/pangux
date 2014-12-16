package com.eadmarket.pangu.util.email;

import java.util.Map;

/**
 * 发邮件的通用服务接口
 *
 * @author liuyongpo@gmail.com
 */
public interface EmailService {

  void sendEmail(String templateId, String email, Map<String, Object> content)
      throws EmailException;
}
