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
package org.eurekaclinical.user.servlet.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.eurekaclinical.eureka.client.comm.User;
import org.eurekaclinical.common.comm.clients.ClientException;
import org.eurekaclinical.user.common.comm.clients.ServicesClient;
import org.eurekaclinical.user.webapp.config.RequestAttributes;
import org.eurekaclinical.user.webapp.config.UserWebappProperties;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author miaoai
 */
@Singleton
public class UserFilter implements Filter {

	private static final Logger LOGGER
			= LoggerFactory.getLogger(UserFilter.class);

	private final ServicesClient servicesClient;
	private final UserWebappProperties properties;

	@Inject
	public UserFilter(ServicesClient inServicesClient, UserWebappProperties inProperties) {
		this.servicesClient = inServicesClient;
		this.properties = inProperties;
	}

	@Override
	public void init(FilterConfig inFilterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest inRequest, ServletResponse inResponse, FilterChain inFilterChain) throws IOException, ServletException {
            
		HttpServletRequest servletRequest = (HttpServletRequest) inRequest;
		HttpServletResponse servletResponse = (HttpServletResponse) inResponse;
                
		String remoteUser = servletRequest.getRemoteUser();
                
		inRequest.setAttribute(RequestAttributes.Eureka_Webapp_URL, this.properties.getEurekaWebappUrl());    
		inRequest.setAttribute(RequestAttributes.Eureka_Services_URL, this.properties.getEurekaServicesUrl());  
                
		if (!StringUtils.isEmpty(remoteUser)) {
			try {
				HttpSession session = servletRequest.getSession(false);
				if (session != null) {
					User user = this.servicesClient.getMe();
					if (!user.isActive()) {
						session.invalidate();
						sendForbiddenError(servletResponse, servletRequest, true);
					} else {
						inRequest.setAttribute(RequestAttributes.USER, user);
						inRequest.setAttribute(RequestAttributes.USER_IS_ACTIVATED, user.isActive());
						inFilterChain.doFilter(inRequest, inResponse);
					}
				} else {
					goHome(servletRequest, servletResponse);
				}
			} catch (ClientException ex) {
				if (null != ex.getResponseStatus()) {
					switch (ex.getResponseStatus()) {
						case FORBIDDEN: {
							HttpSession session = servletRequest.getSession(false);
							if (session != null) {
								session.invalidate();
							}
							sendForbiddenError(servletResponse, servletRequest, false);
							break;
						}
						case UNAUTHORIZED: {
							HttpSession session = servletRequest.getSession(false);
							if (session != null) {
								session.invalidate();
							}
							goHome(servletRequest, servletResponse);
							break;
						}
						default:
							throw new ServletException("Error getting user "
									+ servletRequest.getRemoteUser(), ex);
					}
				}
			}
		} else {
			inFilterChain.doFilter(inRequest, inResponse);
		}
	}

	private void goHome(HttpServletRequest inRequest, HttpServletResponse inResponse) throws IOException {
		inResponse.sendRedirect(inRequest.getContextPath() + "/logout?goHome=true");
	}

	private void sendForbiddenError(HttpServletResponse servletResponse, HttpServletRequest servletRequest, boolean created) throws IOException {
		if (this.properties.isRegistrationEnabled()) {
			servletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
			if (created) {
				servletResponse.sendRedirect(servletRequest.getContextPath() + "/logout?awaitingActivation=true");
			} else {
				servletResponse.sendRedirect(servletRequest.getContextPath() + "/logout?notRegistered=true");
			}
		} else {
			servletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
	}

	@Override
	public void destroy() {
	}

}
