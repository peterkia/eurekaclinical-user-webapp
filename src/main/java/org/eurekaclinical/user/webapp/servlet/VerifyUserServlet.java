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
package org.eurekaclinical.user.webapp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Injector;
import javax.inject.Singleton;

import org.eurekaclinical.common.comm.clients.ClientException;
import org.eurekaclinical.user.client.EurekaClinicalUserProxyClient;

/**
 * Servlet to handle user verification requests.
 * 
 * @author miaoai
 *
 */
@Singleton
public class VerifyUserServlet extends HttpServlet {

	/**
	 * Used for serialization/deserialization.
	 */
	private static final long serialVersionUID = -737043484641381552L;
	private final Injector injector;

	@Inject
	public VerifyUserServlet(Injector inInjector) {
		this.injector = inInjector;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String code = req.getParameter("code");
		EurekaClinicalUserProxyClient servicesClient = this.injector.getInstance(EurekaClinicalUserProxyClient.class);
		try {
			servicesClient.verifyUser(code);
		} catch (ClientException e) {
			throw new ServletException(e);
		}
		
		req.getRequestDispatcher("/registration_info.jsp").forward(req,
				resp);
	}
}
