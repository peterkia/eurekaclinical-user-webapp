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

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import javax.inject.Singleton;

import com.google.inject.Inject;

import org.eurekaclinical.standardapis.filter.RolesRequestWrapper;

import org.eurekaclinical.common.comm.Role;
import org.eurekaclinical.common.comm.clients.ClientException;

import org.eurekaclinical.user.client.comm.User;

import org.eurekaclinical.user.webapp.clients.ServicesClient;
import org.eurekaclinical.user.webapp.config.RequestAttributes;
/**
 *
 * @author miaoai
 */
@Singleton
public class RolesFilter implements Filter {

	private final ServicesClient client;

	@Inject
	public RolesFilter(ServicesClient inClient) {
		this.client = inClient;
	}

	@Override
	public void init(FilterConfig fc) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest inRequest, ServletResponse inResponse, FilterChain inChain) throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) inRequest;
		User user = (User) servletRequest.getAttribute(RequestAttributes.USER);
		if (user != null) {
			try {
				HttpSession session = servletRequest.getSession(false);
				assert session != null : "session should not be null";

				Principal principal = servletRequest.getUserPrincipal();
				assert principal != null : "principal should not be null";

				Set<Long> userRoleIds = new HashSet<>(user.getRoles());
				List<Role> roles = this.client.getRoles();
				Map<Long, Role> idsToRoles = new HashMap<>();
				for (Role role : roles) {
					if (userRoleIds.contains(role.getId())) {
						idsToRoles.put(role.getId(), role);
					}
				}
				Set<String> roleNames = new HashSet<>();
				for (Map.Entry<Long, Role> idToRole : idsToRoles.entrySet()) {
					roleNames.add(idToRole.getValue().getName());
				}
				HttpServletRequest wrappedRequest = new RolesRequestWrapper(
						servletRequest, principal, roleNames);

				inChain.doFilter(wrappedRequest, inResponse);
			} catch (ClientException ce) {
				throw new ServletException(ce);
			}
		} else {
			inChain.doFilter(inRequest, inResponse);
		}

	}

	@Override
	public void destroy() {
	}

}
