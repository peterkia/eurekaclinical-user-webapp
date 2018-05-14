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

import java.util.List;
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

    @Override
    public List<String> getAllowedWebClientUrls() {
        return getStringListValue("eurekaclinical.userwebapp.allowedwebclients");
    }

}
