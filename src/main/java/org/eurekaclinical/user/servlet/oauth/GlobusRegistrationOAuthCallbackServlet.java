package org.eurekaclinical.user.servlet.oauth;

/*
 * #%L
 * Eureka WebApp
 * %%
 * Copyright (C) 2012 - 2015 Emory University
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

import com.google.inject.Inject;
import org.eurekaclinical.scribeupext.profile.GlobusProfile;
import org.eurekaclinical.scribeupext.provider.GlobusProvider;

/**
 *
 * @author miaoai
 */
public class GlobusRegistrationOAuthCallbackServlet extends AbstractOAuthRegistrationCallbackServlet<GlobusProfile> {

	@Inject
	public GlobusRegistrationOAuthCallbackServlet(GlobusProvider inProvider) {
		super(inProvider);
	}
	
}
