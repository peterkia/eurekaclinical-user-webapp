/*
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
package org.eurekaclinical.user.webapp.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import org.eurekaclinical.user.webapp.clients.ServicesClient;
import org.eurekaclinical.user.webapp.config.UserWebappProperties;
/**
 *
 * @author miaoai
 */
@Singleton
public class ServicesClientProvider implements Provider<ServicesClient> {
    
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ServicesClientProvider.class);
        private final ServicesClient client;

	@Inject
	public ServicesClientProvider(UserWebappProperties inProperties) {
		LOGGER.info("user service url = {}", inProperties.getUserServiceUrl());
		this.client = new ServicesClient(inProperties.getUserServiceUrl());
	}

	@Override
	public ServicesClient get() {
		return this.client;
	}    
}
