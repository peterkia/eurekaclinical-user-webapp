package org.eurekaclinical.user.webapp.config;

/*-
 * #%L
 * Eureka WebApp
 * %%
 * Copyright (C) 2012 - 2017 Emory University
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

import com.google.inject.Provider;
import org.eurekaclinical.user.client.EurekaClinicalUserProxyClient;

/**
 *
 * @author Andrew Post
 */
public class EurekaClinicalUserProxyClientProvider implements Provider<EurekaClinicalUserProxyClient> {

	private final String userServiceUrl;

	public EurekaClinicalUserProxyClientProvider(String inUserServiceUrl) {
		this.userServiceUrl = inUserServiceUrl;
	}
	
	@Override
	public EurekaClinicalUserProxyClient get() {
		System.out.println("creating new user client");
		return new EurekaClinicalUserProxyClient(this.userServiceUrl);
	}

}
