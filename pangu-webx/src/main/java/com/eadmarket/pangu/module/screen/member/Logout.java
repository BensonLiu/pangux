package com.eadmarket.pangu.module.screen.member;

import com.alibaba.citrus.turbine.TurbineRunData;
import com.eadmarket.web.common.LoginConstants;

import javax.servlet.http.HttpSession;

/**
 * 会员注销
 *
 * @author liuyongpo@gmail.com
 */
public class Logout {

  public void execute(TurbineRunData runData) {
    HttpSession session = runData.getRequest().getSession();
    session.removeAttribute(LoginConstants.U_ID_SESSION_KEY);
    session.removeAttribute(LoginConstants.U_NICK_SESSION_KEY);
  }
}
