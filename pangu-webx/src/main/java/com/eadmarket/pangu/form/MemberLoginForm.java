/**
 *
 */
package com.eadmarket.pangu.form;

import lombok.Data;

/**
 * @author liuyongpo@gmail.com
 */
@Data
public class MemberLoginForm {

  private String email;

  private String password;

  private String redirectUrl;
}
