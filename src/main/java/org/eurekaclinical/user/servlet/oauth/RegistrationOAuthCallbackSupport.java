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
package org.eurekaclinical.user.servlet.oauth;

import org.eurekaclinical.eureka.client.comm.authentication.AuthenticationMethod;
import org.eurekaclinical.user.servlet.filter.PersonNameSplitter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eurekaclinical.scribeupext.profile.EurekaProfile;
import org.scribe.up.credential.OAuthCredential;
import org.scribe.up.provider.OAuthProvider;
import org.scribe.up.session.HttpUserSession;

/**
 *
 * @author miaoai
 */
class RegistrationOAuthCallbackSupport<E extends EurekaProfile> {

	private final OAuthProvider provider;

	RegistrationOAuthCallbackSupport(OAuthProvider provider) {
		assert provider != null : "provider cannot be null";
		this.provider = provider;
	}

	E getProfile(HttpServletRequest req) {
		assert req != null : "request cannot be null";
		OAuthCredential credential
				= this.provider.getCredential(
						new HttpUserSession(req.getSession()),
						req.getParameterMap());
		if (credential != null) {
			return (E) this.provider.getUserProfile(credential);
		} else {
			/* User rejected authorization request. */
			return null;
		}
	}

	boolean setEurekaAttributeFromProfile(HttpServletRequest req) {
		assert req != null : "request cannot be null";
		E userProfile = getProfile(req);
		if (userProfile != null) {
			req.setAttribute("accountTypeDisplayName", userProfile.getType());
			req.setAttribute("authenticationMethod", 
					AuthenticationMethod.OAUTH.name());
			req.setAttribute("oauthProvider", this.provider.getType());
			String fullName = userProfile.getDisplayName();
			req.setAttribute("fullName", fullName);
			String firstName = userProfile.getFirstName();
			String lastName = userProfile.getFamilyName();

			if ((firstName == null || lastName == null) && fullName != null) {
				PersonNameSplitter splitter
						= new PersonNameSplitter(fullName);
				if (firstName == null) {
					firstName = splitter.getFirstName();
				}
				if (lastName == null) {
					lastName = splitter.getLastName();
				}
			}
			req.setAttribute("firstName", firstName);
			req.setAttribute("lastName", lastName);
			req.setAttribute("email", userProfile.getEmail());
			req.setAttribute("username", userProfile.getTypedId());
			req.setAttribute("providerUsername", userProfile.getUsername());
			return true;
		} else {
			return false;
		}
	}

	void forwardProfileToRegisterPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (setEurekaAttributeFromProfile(req)) {
			req.getRequestDispatcher("/register.jsp").forward(req, resp);
		} else {
			resp.sendRedirect(req.getContextPath() + "/chooseaccounttype");
		}
	}
}
