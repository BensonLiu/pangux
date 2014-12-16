package com.eadmarket.web.common;

import com.alibaba.citrus.service.pipeline.Pipeline;
import com.alibaba.citrus.service.pipeline.PipelineContext;
import com.alibaba.citrus.service.pipeline.support.AbstractValve;
import com.alibaba.citrus.service.uribroker.URIBrokerService;
import com.alibaba.citrus.service.uribroker.uri.URIBroker;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.alibaba.citrus.turbine.util.TurbineUtil;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 登录检查，如果请求的资源必须登录并且会员没有登录，则会直接跳转到登录页面
 *
 * @author liuyongpo@gmail.com
 */
public class LoginCheckValve extends AbstractValve {

  @Autowired
  private SecurityMappingManager securityMappingManager;

  @Autowired
  private HttpServletRequest request;

  @Autowired
  private URIBrokerService uriBrokerService;

  @Override
  public void invoke(PipelineContext pipelineContext) throws Exception {
    TurbineRunData turbineRunData = TurbineUtil.getTurbineRunData(request);
    String target = turbineRunData.getTarget();
    if (isUnprotected(target) || isMemberLogined()) {
      pipelineContext.invokeNext();
      return;
    }

    turbineRunData.setRedirectLocation(generateLoginUrl());
    pipelineContext.breakPipeline(Pipeline.TOP_LABEL);
  }

  /**
   * 判断当前url是否需要登录保护
   */
  private boolean isUnprotected(String url) {
    return securityMappingManager.isUnprotected(url);
  }

  /**
   * 会员是否已经登录
   */
  private boolean isMemberLogined() {
    HttpSession session = request.getSession();
    return session.getAttribute(LoginConstants.U_ID_SESSION_KEY) != null
           && session.getAttribute(LoginConstants.U_NICK_SESSION_KEY) != null;
  }

  /**
   * 拼接Login链接
   */
  private String generateLoginUrl() {
    URIBroker loginUriBroker = uriBrokerService.getURIBroker("loginLink").fork();

    String
        redirectUrl =
        request.getRequestURL().append('?').append(request.getQueryString()).toString();

    try {
      redirectUrl = URLEncoder.encode(redirectUrl, LoginConstants.REDIRECT_URL_ENCODE_CHARSET);
    } catch (UnsupportedEncodingException ex) {
      throw new RuntimeException(
          "encode '" + redirectUrl + "' with " + LoginConstants.REDIRECT_URL_ENCODE_CHARSET, ex);
    }

    return loginUriBroker.addQueryData("redirectUrl", redirectUrl).render();
  }
}
