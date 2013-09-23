package com.eadmarket.pangu.module.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.alibaba.citrus.service.form.CustomErrors;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.FormField;
import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.eadmarket.pangu.ManagerException;
import com.eadmarket.pangu.domain.UserDO;
import com.eadmarket.pangu.form.MemberLoginForm;
import com.eadmarket.pangu.form.MemberRegisterForm;
import com.eadmarket.pangu.manager.user.UserManager;
import com.google.common.collect.Maps;

/**
 * 会员操作Action
 * 
 * @author liuyongpo@eadmarket.com
 */
public class MemberAction {
	
	private final static Logger LOG = LoggerFactory.getLogger(MemberAction.class);
	
	@Resource private HttpSession httpSession;
	
	@Resource private UserManager userManager;
	
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
	}
	
	public void doLogin(@FormGroup("login")MemberLoginForm loginForm, Navigator nav){
		try {
			UserDO user = userManager.getUserByEmailAndPwd(loginForm.getEmail(), loginForm.getPassword());
			httpSession.setAttribute("_ead_u_id_", user.getId());
			httpSession.setAttribute("_ead_u_nick_", user.getNick());
			String redirectUrl = loginForm.getRedirectUrl();
			if (!StringUtils.isBlank(redirectUrl)) {
				redirectUrl = URLDecoder.decode(redirectUrl, "utf-8");
			} else {
				//TODO:跳转到index
			}
			LOG.warn("redirectUrl is " + redirectUrl);
			nav.redirectToLocation(redirectUrl);
		} catch (ManagerException ex) {
			LOG.error("loginError " + loginForm, ex);
		} catch (UnsupportedEncodingException ex) {
			LOG.error("loginError " + loginForm, ex);
		}
	}
}
