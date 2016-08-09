/*-
 * #%L
 * Eureka! Clinical User Webapp
 * %%
 * Copyright (C) 2016 Emory University
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.eurekaclinical.user.webapp.config;

import com.google.inject.Singleton;
import org.eurekaclinical.user.servlet.*;
import org.eurekaclinical.user.servlet.filter.MessagesFilter;
import org.eurekaclinical.user.servlet.filter.UserFilter;
import org.eurekaclinical.user.servlet.filter.RolesFilter;
import org.eurekaclinical.user.servlet.filter.PasswordExpiredFilter;
import java.util.HashMap;
import java.util.Map;
import org.eurekaclinical.common.config.AbstractServletModule;
import org.eurekaclinical.user.servlet.oauth.GitHubRegistrationOAuthCallbackServlet;
import org.eurekaclinical.user.servlet.oauth.GlobusRegistrationOAuthCallbackServlet;
import org.eurekaclinical.user.servlet.oauth.GoogleRegistrationOAuthCallbackServlet;
import org.eurekaclinical.user.servlet.oauth.TwitterRegistrationOAuthCallbackServlet;
import org.eurekaclinical.common.servlet.LogoutServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author miaoai
 */
class ServletModule extends AbstractServletModule {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ServletModule.class);
        
	private static final String CONTAINER_PATH = "/site/*";
	private static final String CONTAINER_PROTECTED_PATH = "/protected/*";
	private static final String FILTER_PATH = "^/(?!(assets|bower_components)).*";
	private static final String PASSWORD_EXPIRED_REDIRECT_URL = "/protected/password_expiration.jsp";
	private static final String PASSWORD_SAVE_PATH = "protected/user_acct"; 
	private static final String LOGOUT_PATH = "/logout";

	private final UserWebappProperties properties;

	public ServletModule(UserWebappProperties inProperties) {
		super(inProperties, CONTAINER_PATH, CONTAINER_PROTECTED_PATH,
				/*LOGOUT_PATH*/"/*");
		this.properties = inProperties;
	}
        
	private void setupMessageFilter() {
		bind(MessagesFilter.class).in(Singleton.class);
		filterRegex(FILTER_PATH).through(MessagesFilter.class);
	}

	private void setupUserFilter() {
		bind(UserFilter.class).in(Singleton.class);
		filterRegex(FILTER_PATH).through(UserFilter.class);
	}
	
	private void setupRolesFilter() {
		filterRegex(FILTER_PATH).through(RolesFilter.class);
	}

	private void setupPasswordExpiredFilter() {/*
		bind(PasswordExpiredFilter.class).in(Singleton.class);
		Map<String, String> params = new HashMap<>();
		params.put("redirect-url", PASSWORD_EXPIRED_REDIRECT_URL);
		params.put("save-url", PASSWORD_SAVE_PATH);
		if (LOGGER.isDebugEnabled()) {
			this.printParams(params);
		}
		filter(CONTAINER_PROTECTED_PATH).through(
				PasswordExpiredFilter.class, params);*/
	}

	@Override
	protected void setupFilters() {
		this.setupMessageFilter();
		this.setupUserFilter();
		this.setupRolesFilter();
		this.setupPasswordExpiredFilter();
	}
        
	@Override
	protected void setupServlets() {
                bind(LogoutServlet.class).in(Singleton.class);
		serve(LOGOUT_PATH).with(LogoutServlet.class);
            
		bind(ChooseAccountTypeServlet.class).in(Singleton.class);
		serve("/chooseaccounttype").with(ChooseAccountTypeServlet.class);   
                
		bind(RegisterUserServlet.class).in(Singleton.class);
		serve("/register").with(RegisterUserServlet.class); 

		bind(UserAcctManagerServlet.class).in(Singleton.class);
		serve("/protected/user_acct").with(UserAcctManagerServlet.class);    
                
		bind(VerifyUserServlet.class).in(Singleton.class);
		serve("/verify").with(VerifyUserServlet.class);              
                
		bind(AdminManagerServlet.class).in(Singleton.class);
		serve("/protected/admin").with(AdminManagerServlet.class);
                
		bind(GitHubRegistrationOAuthCallbackServlet.class).in(Singleton.class);
		serve("/registrationoauthgithubcallback").with(
				GitHubRegistrationOAuthCallbackServlet.class);

		bind(GoogleRegistrationOAuthCallbackServlet.class).in(Singleton.class);
		serve("/registrationoauthgooglecallback").with(
				GoogleRegistrationOAuthCallbackServlet.class);

		bind(TwitterRegistrationOAuthCallbackServlet.class).in(Singleton.class);
		serve("/registrationoauthtwittercallback").with(
				TwitterRegistrationOAuthCallbackServlet.class);

		bind(GlobusRegistrationOAuthCallbackServlet.class).in(Singleton.class);
		serve("/registrationoauthglobuscallback").with(
				GlobusRegistrationOAuthCallbackServlet.class);  
                
        }

	@Override
	protected Map<String, String> getCasValidationFilterInitParams() {
		Map<String, String> params = new HashMap<>();
		params.put("casServerUrlPrefix", this.properties.getCasUrl());
		params.put("serverName", this.properties.getProxyCallbackServer());
		params.put("proxyCallbackUrl", getCasProxyCallbackUrl());
		params.put("proxyReceptorUrl", getCasProxyCallbackPath());
		return params;
	}
}
