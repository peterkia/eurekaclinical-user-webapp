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
package org.eurekaclinical.user.webapp.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;

import org.eurekaclinical.scribeupext.provider.SSLTwitterProvider;

import org.eurekaclinical.user.webapp.config.UserWebappProperties;
/**
 *
 * @author miaoai
 */
public class ScribeExtTwitterProvider implements Provider<SSLTwitterProvider> {
	private final SSLTwitterProvider twitterProvider;

	@Inject
	public ScribeExtTwitterProvider(UserWebappProperties inProperties) {
		this.twitterProvider = new SSLTwitterProvider();
		this.twitterProvider.setKey(inProperties.getTwitterOAuthKey());
		this.twitterProvider.setSecret(inProperties.getTwitterOAuthSecret());
		this.twitterProvider.setCallbackUrl(inProperties.getApplicationUrl() + "registrationoauthtwittercallback");
	}
	
	@Override
	public SSLTwitterProvider get() {
		return this.twitterProvider;
	}    
}
