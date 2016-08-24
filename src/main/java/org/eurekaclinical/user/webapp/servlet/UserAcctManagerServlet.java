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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import org.eurekaclinical.user.common.comm.clients.ServicesClient;
import org.eurekaclinical.user.webapp.servlet.worker.ServletWorker;
import org.eurekaclinical.user.webapp.servlet.worker.useracct.ListUserAcctWorker;
import org.eurekaclinical.user.webapp.servlet.worker.useracct.SaveUserAcctInfoWorker;
import org.eurekaclinical.user.webapp.servlet.worker.useracct.SaveUserAcctWorker;
/**
 *
 * @author miaoai
 */
public class UserAcctManagerServlet extends HttpServlet {

	private static final Logger LOGGER = LoggerFactory.getLogger
			(UserAcctManagerServlet.class);
	private final ServicesClient servicesClient;

	@Inject
	public UserAcctManagerServlet (ServicesClient inClient) {
		this.servicesClient = inClient;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doGet(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String action = req.getParameter("action");
		ServletWorker worker;

		if (action != null && action.equals("savepassword")) {
                        LOGGER.info("Saving user password");
                        worker = new SaveUserAcctWorker(this.getServletContext(), this.servicesClient);     
		} else if (action != null && action.equals("saveinfo")) {
                        LOGGER.info("Saving user info");                    
                        worker = new SaveUserAcctInfoWorker(this.getServletContext(), this.servicesClient);                     
		} else {
			LOGGER.info("Listing user");
			worker = new ListUserAcctWorker(this.servicesClient);
		}
		worker.execute(req, resp);
	}
}
