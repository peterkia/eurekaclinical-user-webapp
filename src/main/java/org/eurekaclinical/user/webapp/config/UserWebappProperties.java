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

import org.eurekaclinical.user.common.props.AbstractProperties;

/**
 *
 * @author miaoai
 */
public class UserWebappProperties extends AbstractProperties {  
	public String getUserWebappUrl() {
		return this.getValue("user.webapp.url");
	}        
	public String getUserServiceUrl() {
		return this.getValue("user.services.url");
	}   
	public String getEurekaWebappUrl() {
		return this.getValue("eureka.webapp.url");
	}        
	public String getEurekaServicesUrl() {
		return this.getValue("eureka.services.url");
	}         
	public boolean isEphiProhibited() {
		return Boolean.parseBoolean(getValue("user.webapp.ephiprohibited"));
	}
	public boolean isDemoMode() {
		return Boolean.parseBoolean(getValue("user.webapp.demomode"));
	}        
	@Override
	public String getProxyCallbackServer() {
		return this.getValue("user.webapp.callbackserver");
	}         
	public boolean isOAuthRegistrationEnabled() {
		return isGoogleOAuthRegistrationEnabled() || isGitHubOAuthRegistrationEnabled() || isTwitterOAuthRegistrationEnabled() || isGlobusOAuthRegistrationEnabled();
	}

	public boolean isGoogleOAuthRegistrationEnabled() {
		return getGoogleOAuthKey() != null && getGoogleOAuthSecret() != null;
	}

	public boolean isGitHubOAuthRegistrationEnabled() {
		return getGitHubOAuthKey() != null && getGitHubOAuthSecret() != null;
	}

	public boolean isTwitterOAuthRegistrationEnabled() {
		return getTwitterOAuthKey() != null && getTwitterOAuthSecret() != null;
	}

	public boolean isGlobusOAuthRegistrationEnabled() {
		return getGlobusOAuthKey() != null && getGlobusOAuthSecret() != null;
	}

	public boolean isLocalAccountRegistrationEnabled() {
		return Boolean.parseBoolean(getValue("user.webapp.localregistrationenabled"));
	}
	
	public boolean isRegistrationEnabled() {
		return isLocalAccountRegistrationEnabled() || isOAuthRegistrationEnabled();
	}        
        
        
	public String getGitHubOAuthKey() {
		return getValue("user.webapp.githuboauthkey");
	}

	public String getGitHubOAuthSecret() {
		return getValue("user.webapp.githuboauthsecret");
	}        
	public String getGlobusOAuthKey() {
		return getValue("user.webapp.globusoauthkey");
	}

	public String getGlobusOAuthSecret() {
		return getValue("user.webapp.globusoauthsecret");
	}        
	public String getGoogleOAuthKey() {
		return getValue("user.webapp.googleoauthkey");
	}

	public String getGoogleOAuthSecret() {
		return getValue("user.webapp.googleoauthsecret");
	}        
	public String getTwitterOAuthKey() {
		return getValue("user.webapp.twitteroauthkey");
	}

	public String getTwitterOAuthSecret() {
		return getValue("user.webapp.twitteroauthsecret");
	}        
        
}
