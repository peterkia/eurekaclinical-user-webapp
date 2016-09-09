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
package org.eurekaclinical.user.webapp.servlet.worker.useracct;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eurekaclinical.common.comm.clients.ClientException;

import org.eurekaclinical.user.client.comm.User;

import org.eurekaclinical.user.webapp.clients.EurekaclinicalUserInternalClient;
import org.eurekaclinical.user.webapp.servlet.worker.ServletWorker;
import org.eurekaclinical.user.webapp.config.UserWebappProperties;
/**
 *
 * @author miaoai
 */
public class SaveUserAcctInfoWorker implements ServletWorker {

	private static Logger LOGGER = LoggerFactory.getLogger(SaveUserAcctInfoWorker.class);
	
	private final ResourceBundle messages;
	private final EurekaclinicalUserInternalClient servicesClient;
	private final UserWebappProperties properties;

	public SaveUserAcctInfoWorker(ServletContext ctx, EurekaclinicalUserInternalClient inClient) {
		String localizationContextName = ctx.getInitParameter("javax.servlet.jsp.jstl.fmt.localizationContext");
		this.messages = ResourceBundle.getBundle(localizationContextName);
		this.servicesClient = inClient;
		this.properties = new UserWebappProperties();
	}

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {   
		String id = req.getParameter("id");
		if(id!=null && !id.isEmpty()){
                        String firstName = req.getParameter("firstName");
                        String lastName = req.getParameter("lastName");
                        String email = req.getParameter("email");                
                        String organization = req.getParameter("organization");
                        String title = req.getParameter("title");
                        String department = req.getParameter("department");
                        String fullName = firstName+' '+lastName;               

                        User user = null;
                        try {
                                user = this.servicesClient.getUserById(Long.valueOf(id));
                        } catch (ClientException ex) {
                                LOGGER.error("Error getting user by id at {}", SaveUserAcctInfoWorker.class.getName(), ex);
                                resp.setContentType("text/plain");
                                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                resp.getWriter().write(ex.getMessage());                        
                        }
                                user.setFirstName(firstName);
                                user.setLastName(lastName);
                                user.setEmail(email);
                                user.setOrganization(organization);
                                user.setTitle(title);
                                user.setDepartment(department);
                                user.setFullName(fullName);
                        try {     
                                this.servicesClient.updateUser(user,Long.valueOf(id));
                        } catch (ClientException ex) {
                                LOGGER.error("Error updating user at {}", SaveUserAcctInfoWorker.class.getName(), ex);
                                resp.setContentType("text/plain");
                                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                                resp.getWriter().write(ex.getMessage());                          
                        }
                        resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                                LOGGER.error("Error getting user by id at {}, id is invalid", SaveUserAcctInfoWorker.class.getName());               
                                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);                               
                }
                
	}
}
