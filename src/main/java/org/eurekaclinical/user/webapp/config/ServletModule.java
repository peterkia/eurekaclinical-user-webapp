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

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Singleton;

import org.eurekaclinical.common.config.AbstractServletModule;
import org.eurekaclinical.common.servlet.LogoutServlet;

import org.eurekaclinical.user.webapp.servlet.UserAcctManagerServlet;
import org.eurekaclinical.user.webapp.servlet.VerifyUserServlet;
import org.eurekaclinical.user.webapp.servlet.ChooseAccountTypeServlet;
import org.eurekaclinical.user.webapp.servlet.RegisterUserServlet;
import org.eurekaclinical.user.webapp.servlet.AdminManagerServlet;
import org.eurekaclinical.user.webapp.servlet.filter.MessagesFilter;
import org.eurekaclinical.user.webapp.servlet.filter.UserFilter;
import org.eurekaclinical.user.webapp.servlet.filter.RolesFilter;
import org.eurekaclinical.user.webapp.servlet.oauth.GitHubRegistrationOAuthCallbackServlet;
import org.eurekaclinical.user.webapp.servlet.oauth.GlobusRegistrationOAuthCallbackServlet;
import org.eurekaclinical.user.webapp.servlet.oauth.GoogleRegistrationOAuthCallbackServlet;
import org.eurekaclinical.user.webapp.servlet.oauth.TwitterRegistrationOAuthCallbackServlet;
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
	private static final String LOGOUT_PATH = "/logout";

	private final UserWebappProperties properties;

	public ServletModule(UserWebappProperties inProperties) {
		super(inProperties, CONTAINER_PATH, CONTAINER_PROTECTED_PATH);
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

	@Override
	protected void setupFilters() {
		this.setupMessageFilter();
		this.setupUserFilter();
		this.setupRolesFilter();
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
