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

import org.eurekaclinical.standardapis.props.CasEurekaClinicalProperties;

/**
 *
 * @author miaoai
 */
public class UserWebappProperties extends CasEurekaClinicalProperties {

    public UserWebappProperties() {
        super("/etc/ec-user");
    }

    public String getUserServiceUrl() {
        return this.getValue("eurekaclinical.userservice.url");
    }

    public String getEurekaWebappUrl() {
        return this.getValue("eureka.webapp.url");
    }

    public String getEurekaServicesUrl() {
        return this.getValue("eureka.services.url");
    }

    public boolean isEphiProhibited() {
        return Boolean.parseBoolean(getValue("eurekaclinical.userwebapp.ephiprohibited"));
    }

    public boolean isDemoMode() {
        return Boolean.parseBoolean(getValue("eurekaclinical.userwebapp.demomode"));
    }

    @Override
    public String getProxyCallbackServer() {
        return this.getValue("eurekaclinical.userwebapp.callbackserver");
    }

    public boolean isOAuthRegistrationEnabled() {
        return isGoogleOAuthRegistrationEnabled() || isGitHubOAuthRegistrationEnabled() || isGlobusOAuthRegistrationEnabled();
    }

    public boolean isGoogleOAuthRegistrationEnabled() {
        return getGoogleOAuthKey() != null && getGoogleOAuthSecret() != null;
    }

    public boolean isGitHubOAuthRegistrationEnabled() {
        return getGitHubOAuthKey() != null && getGitHubOAuthSecret() != null;
    }

    public boolean isGlobusOAuthRegistrationEnabled() {
        return getGlobusOAuthKey() != null && getGlobusOAuthSecret() != null;
    }

    public boolean isLocalAccountRegistrationEnabled() {
        return Boolean.parseBoolean(getValue("eurekaclinical.userwebapp.localregistrationenabled"));
    }

    public boolean isRegistrationEnabled() {
        return isLocalAccountRegistrationEnabled() || isOAuthRegistrationEnabled();
    }

    public String getGitHubOAuthKey() {
        return getValue("eurekaclinical.userwebapp.githuboauthkey");
    }

    public String getGitHubOAuthSecret() {
        return getValue("eurekaclinical.userwebapp.githuboauthsecret");
    }

    public String getGlobusOAuthKey() {
        return getValue("eurekaclinical.userwebapp.globusoauthkey");
    }

    public String getGlobusOAuthSecret() {
        return getValue("eurekaclinical.userwebapp.globusoauthsecret");
    }

    public String getGoogleOAuthKey() {
        return getValue("eurekaclinical.userwebapp.googleoauthkey");
    }

    public String getGoogleOAuthSecret() {
        return getValue("eurekaclinical.userwebapp.googleoauthsecret");
    }

    /**
     * Gets the service's base URL.
     * 
     * @return the service's base URL.
     */
    @Override
    public String getUrl() {
        return getValue("eurekaclinical.userwebapp.url");
    }

    public String getRegistryServiceUrl() {
        return getValue("eurekaclinical.registryservice.url");
    }

}
