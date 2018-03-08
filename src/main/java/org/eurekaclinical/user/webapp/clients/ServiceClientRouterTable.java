package org.eurekaclinical.user.webapp.clients;

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
import javax.inject.Inject;
import org.eurekaclinical.common.comm.clients.Route;
import org.eurekaclinical.common.comm.clients.RouterTable;
import org.eurekaclinical.registry.client.EurekaClinicalRegistryClient;
import org.eurekaclinical.user.client.EurekaClinicalUserClient;

/**
 * EurekaclinicalUserClient is the external used client
 *
 * @author miaoai
 */
public class ServiceClientRouterTable implements RouterTable {

    private final EurekaClinicalUserClient client;
    private final EurekaClinicalRegistryClient registryClient;

    @Inject
    public ServiceClientRouterTable(EurekaClinicalUserClient inClient,
            EurekaClinicalRegistryClient inRegistryClient) {
        this.client = inClient;
        this.registryClient = inRegistryClient;
    }

    @Override
    public Route[] load() {
        return new Route[]{
            new Route("/components", "/api/protected/components", this.registryClient),
            new Route("/userrequests","/api/userrequests",this.client),
            new Route("/oauthuser","/api/oauthuser",this.client),
            new Route("/", "/api/protected/", this.client)       
        };
    }

}
