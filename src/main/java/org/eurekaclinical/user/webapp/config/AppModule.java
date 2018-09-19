/*
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

/**
 *
 * @author miaoai
 */
import com.google.inject.AbstractModule;
import com.google.inject.servlet.SessionScoped;
import org.eurekaclinical.common.comm.clients.AuthorizingEurekaClinicalClient;

import org.eurekaclinical.common.comm.clients.RouterTable;
import org.eurekaclinical.registry.client.EurekaClinicalRegistryClient;
import org.eurekaclinical.user.webapp.clients.ServiceClientRouterTable;

import org.eurekaclinical.standardapis.props.CasEurekaClinicalProperties;
import org.eurekaclinical.user.client.EurekaClinicalUserClient;

/**
 * Configure all the web related binding for Guice and Jersey.
 *
 * @author miaoai
 */
public class AppModule extends AbstractModule {

    private final UserWebappProperties userWebappProperties;
    private final EurekaClinicalUserClientProvider clientProvider;
    private final EurekaClinicalRegistryClientProvider registryClientProvider;

    /**
     * Inject userServiceUrl to EurekaclinicalUserClient
     */
    AppModule(UserWebappProperties userWebappProperties) {
        this.userWebappProperties = userWebappProperties;
        this.clientProvider = new EurekaClinicalUserClientProvider(
                userWebappProperties.getUserServiceUrl());
        this.registryClientProvider = new EurekaClinicalRegistryClientProvider(
                userWebappProperties.getRegistryServiceUrl());
    }

    @Override
    protected void configure() {
        bind(RouterTable.class).to(ServiceClientRouterTable.class).in(SessionScoped.class);
        bind(AuthorizingEurekaClinicalClient.class).toProvider(this.clientProvider).in(SessionScoped.class);
        bind(EurekaClinicalUserClient.class).toProvider(this.clientProvider).in(SessionScoped.class);
        bind(EurekaClinicalRegistryClient.class).toProvider(this.registryClientProvider).in(SessionScoped.class);
        bind(UserWebappProperties.class).toInstance(this.userWebappProperties);
        bind(CasEurekaClinicalProperties.class).toInstance(this.userWebappProperties);
    }
}
