package com.eadmarket.pangu.module.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.form.CustomErrors;
import com.alibaba.citrus.service.uribroker.URIBrokerService;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.alibaba.citrus.turbine.dataresolver.FormField;
import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.eadmarket.pangu.ExceptionCode;
import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.domain.UserDO;
import com.eadmarket.pangu.form.MemberLoginForm;
import com.eadmarket.pangu.form.MemberRegisterForm;
import com.eadmarket.pangu.manager.user.UserManager;
import com.eadmarket.web.common.LoginConstants;
import com.google.common.collect.Maps;

/**
 * 会员操作Action
 * 
 * @author liuyongpo@gmail.com
 */
public class MemberAction {
	
	private final static Logger LOG = LoggerFactory.getLogger(MemberAction.class);
	
	//@Resource private HttpSession httpSession;
	
	@Resource private UserManager userManager;
	
	@Autowired private URIBrokerService uriBrokerService;
	/*
	public void doRegister(@FormField(name="email",group="register")CustomErrors err, 
			@FormGroup("register")MemberRegisterForm userForm, Navigator nav) {
		try {
			
			UserDO userDO = generateUserDO(userForm);
			
			userManager.registerUser(userDO);
		} catch (ManagerException ex) {
			LOG.error("Register Member " + userForm, ex);
			Map<String, Object> params = Maps.newHashMap();
			params.put("errorMessage", ex.getCode());
			err.setMessage("registerError", params);
		}
	}
	
	private UserDO generateUserDO(MemberRegisterForm userForm) {
		UserDO userDO = new UserDO();
		BeanUtils.copyProperties(userForm, userDO);
		return userDO;
	}*/
	
	public void doLogin(TurbineRunData runData, Context context){
		try {
			boolean formIsInvalid = false;
			String email = runData.getParameters().getString("email", "");
			if (StringUtils.isBlank(email)) {
				context.put("login_email_error", "请填写email");
				formIsInvalid = true;
			}
			String password = runData.getParameters().getString("password", "");
			if (StringUtils.isBlank(password)) {
				context.put("login_password_error", "请填写密码");
				formIsInvalid = true;
			}
			
			if (formIsInvalid) {
				return;
			}
			
			UserDO user = userManager.getUserByEmailAndPwd(email, password);
			
			HttpSession httpSession = runData.getRequest().getSession();
			httpSession.setAttribute(LoginConstants.U_ID_SESSION_KEY, user.getId());
			httpSession.setAttribute(LoginConstants.U_NICK_SESSION_KEY, user.getNick());
			
			String redirectUrl = runData.getParameters().getString("redirect_url", "");
			if (!StringUtils.isBlank(redirectUrl)) {
				redirectUrl = URLDecoder.decode(redirectUrl, LoginConstants.REDIRECT_URL_ENCODE_CHARSET);
			} else {
				redirectUrl = uriBrokerService.getURIBroker("indexLink").fork().render();
			}
			LOG.warn("redirectUrl is " + redirectUrl);
			runData.setRedirectLocation(redirectUrl);
		} catch (ManagerException ex) {
			ExceptionCode code = ex.getCode();
			context.put("error_message", code.getDesc());
			if (code.isSystemError()) {
				LOG.error("loginError " + code, ex);
			}
		} catch (UnsupportedEncodingException ex) {
			LOG.error("loginError ", ex);
		}
	}

}
