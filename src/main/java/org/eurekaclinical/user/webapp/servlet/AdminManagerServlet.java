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

import org.eurekaclinical.user.webapp.clients.EurekaclinicalUserServiceClient;
import org.eurekaclinical.user.webapp.servlet.worker.ServletWorker;
import org.eurekaclinical.user.webapp.servlet.worker.admin.EditUserWorker;
import org.eurekaclinical.user.webapp.servlet.worker.admin.ListUsersWorker;
import org.eurekaclinical.user.webapp.servlet.worker.admin.SaveUserWorker;
/**
 *
 * @author miaoai
 */
public class AdminManagerServlet extends HttpServlet {

	private final EurekaclinicalUserServiceClient servicesClient;

	@Inject
	public AdminManagerServlet (EurekaclinicalUserServiceClient inClient) {
		this.servicesClient = inClient;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (!req.isUserInRole("admin")) {
			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		} else {
			String action = req.getParameter("action");
			if (action == null) {
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				resp.getWriter().write("The action parameter is required");
			} else {
				ServletWorker worker = null;
				if (action.equals("list")) {
					worker = new ListUsersWorker(this.servicesClient);
				} else if (action.equals("edit")) {
					worker = new EditUserWorker(this.servicesClient);
				} else if (action.equals("save")) {
					worker = new SaveUserWorker(this.servicesClient);
				}

				if (null == worker) {
					resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					resp.getWriter().write("Invalid action parameter " + action + ". Allowed values are list, edit and save.");
				}
				else {
					worker.execute(req, resp);
				}
			}
		}
	}
}
