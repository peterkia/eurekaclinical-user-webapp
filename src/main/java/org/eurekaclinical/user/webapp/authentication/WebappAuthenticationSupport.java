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
package org.eurekaclinical.user.webapp.authentication;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eurekaclinical.common.comm.clients.ClientException;

import org.eurekaclinical.user.client.comm.User;
import org.eurekaclinical.user.common.authentication.AbstractUserSupport;
import org.eurekaclinical.user.webapp.clients.EurekaclinicalUserInternalClient;
/**
 *
 * @author miaoai
 */
public final class WebappAuthenticationSupport extends AbstractUserSupport {
	
	public WebappAuthenticationSupport(EurekaclinicalUserInternalClient inServicesClient) {
	}
	
	public User getMe(HttpServletRequest req) throws ClientException {
		return (User) req.getAttribute("user");
	}

	public void needsToLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.getSession().invalidate();
		resp.sendRedirect("/eureka-webapp/protected/login");
	}
}
