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
package org.eurekaclinical.user.webapp.servlet.oauth;

import com.google.inject.Inject;

import org.eurekaclinical.scribeupext.profile.GitHubProfile;
import org.eurekaclinical.scribeupext.provider.GitHubProvider;
/**
 *
 * @author miaoai
 */
public class GitHubRegistrationOAuthCallbackServlet extends AbstractOAuthRegistrationCallbackServlet<GitHubProfile> {

	@Inject
	public GitHubRegistrationOAuthCallbackServlet(GitHubProvider inProvider) {
		super(inProvider);
	}
	
}
