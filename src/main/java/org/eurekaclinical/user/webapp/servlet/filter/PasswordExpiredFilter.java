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
package org.eurekaclinical.user.webapp.servlet.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Singleton;

import org.eurekaclinical.user.client.comm.LocalUser;
import org.eurekaclinical.user.client.comm.User;

import org.eurekaclinical.user.webapp.config.RequestAttributes;
/**
 * @author miaoai
 */
@Singleton
public class PasswordExpiredFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(PasswordExpiredFilter.class);
	private String redirectUrl;
	private String saveUrl;

	public PasswordExpiredFilter() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.redirectUrl = filterConfig.getInitParameter("redirect-url");
		this.saveUrl = filterConfig.getInitParameter("save-url");
		if (this.redirectUrl == null) {
			throw new ServletException("Parameter redirect-url must be set");
		}
		if (this.saveUrl == null) {
			throw new ServletException("Parameter save-url must be set");
		}
		LOGGER.debug("redirect-url: {}", this.redirectUrl);
		LOGGER.debug("save-url: {}", this.saveUrl);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		User user = (User) request.getAttribute(RequestAttributes.USER);
		if (!(user instanceof LocalUser)) {
			chain.doFilter(request, response);
		} else {
			Date now = new Date();
			Date expiration = ((LocalUser) user).getPasswordExpiration();
			LOGGER.debug("expiration date: {}", expiration);
			if (expiration != null && now.after(expiration)) {
				String targetUrl = servletRequest.getRequestURI();
				String fullRedirectUrl = servletRequest.getContextPath()
						+ this.redirectUrl;
				String fullSaveUrl = servletRequest.getContextPath()
						+ this.saveUrl;
				LOGGER.debug("fullRedirectUrl: {}", fullRedirectUrl);
				LOGGER.debug("fullSaveUrl: {}", fullSaveUrl);
				LOGGER.debug("targetUrl: {}", targetUrl);
				if (!targetUrl.equals(fullRedirectUrl) && !targetUrl
						.equals(fullSaveUrl)) {
					String encodeRedirectURL = servletResponse.encodeRedirectURL(fullRedirectUrl
							+ "?firstLogin=" + (user.getLastLogin() == null) + "&redirectURL=" + targetUrl);
					LOGGER.debug("encodeRedirectURL: {}", encodeRedirectURL);
					servletResponse.sendRedirect(encodeRedirectURL);
				} else {
					chain.doFilter(request, response);
				}
			} else {
				chain.doFilter(request, response);
			}
		}
	}

	@Override
	public void destroy() {
	}
}
