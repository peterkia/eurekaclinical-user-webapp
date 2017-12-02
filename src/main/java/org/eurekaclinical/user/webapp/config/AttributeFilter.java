package org.eurekaclinical.user.webapp.config;

/*-
 * #%L
 * Eureka! Clinical User Webapp
 * %%
 * Copyright (C) 2016 - 2017 Emory University
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
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.eurekaclinical.common.comm.clients.ClientException;
import org.eurekaclinical.user.client.EurekaClinicalUserClient;

/**
 *
 * @author Andrew Post
 */
@Singleton
public class AttributeFilter implements Filter {

    private final UserWebappProperties properties;
    private final Provider<EurekaClinicalUserClient> clientProvider;

    @Inject
    public AttributeFilter(UserWebappProperties inProperties, Provider<EurekaClinicalUserClient> inClient) {
        this.properties = inProperties;
        this.clientProvider = inClient;
    }

    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
        sr.setAttribute("eurekaWebappUrl", this.properties.getEurekaWebappUrl());
        if (((HttpServletRequest) sr).getRemoteUser() != null) {
            try {
                sr.setAttribute("user", this.clientProvider.get().getMe());
            } catch (ClientException ex) {
                throw new ServletException(ex);
            }
        }
        fc.doFilter(sr, sr1);
    }

    @Override
    public void destroy() {
    }

}
