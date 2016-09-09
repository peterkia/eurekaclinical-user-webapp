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
package org.eurekaclinical.user.webapp.clients;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.core.UriBuilder;
import com.sun.jersey.api.client.GenericType;

import org.eurekaclinical.common.comm.Role;
import org.eurekaclinical.common.comm.clients.ClientException;
import org.eurekaclinical.common.comm.clients.EurekaClinicalClient;


import org.eurekaclinical.user.client.comm.PasswordChangeRequest;
import org.eurekaclinical.user.client.comm.User;
import org.eurekaclinical.user.client.comm.UserRequest;
import org.eurekaclinical.user.client.comm.OAuthProvider;
/**
 * EurekaclinicalUserInternalClient is an internal client that should be used only by user-webapp
 * 
 * @author miaoai
 */
public class EurekaclinicalUserInternalClient extends EurekaClinicalClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(EurekaclinicalUserInternalClient.class);    
    
	private static final GenericType<List<User>> UserList = new GenericType<List<User>>() {
	};
	private static final GenericType<List<Role>> RoleList = new GenericType<List<Role>>() {
	};   
       
	private final String userServiceUrl;

	public EurekaclinicalUserInternalClient(String inUserServiceUrl) {
		super(null);
		LOGGER.debug("Using eurekaclinical user service URL {}", inUserServiceUrl);
		this.userServiceUrl = inUserServiceUrl;
	}

	@Override
	protected String getResourceUrl() {
		return this.userServiceUrl;
	}

	public List<User> getUsers() throws ClientException {
		final String path = "/api/protected/users";
		return doGet(path, UserList);
	}
        
	public User getMe() throws ClientException {
		String path = "/api/protected/users/me";
		return doGet(path, User.class);
	} 

	public User getUserById(Long inUserId) throws ClientException {
		final String path = "/api/protected/users/" + inUserId;
		return doGet(path, User.class);
	}

	public void changePassword(String inOldPass, String inNewPass) throws ClientException {
		final String path = "/api/protected/users/passwordchange";
		PasswordChangeRequest passwordChangeRequest
				= new PasswordChangeRequest();
		passwordChangeRequest.setOldPassword(inOldPass);
		passwordChangeRequest.setNewPassword(inNewPass);
		doPost(path, passwordChangeRequest);
	}

	public void updateUser(User inUser, Long userId) throws ClientException {
		final String path = "/api/protected/users/" + userId;
		doPut(path, inUser);
	}

	public void addUser(UserRequest inRequest) throws ClientException {
		final String path = "/api/userrequests";
		doPostCreate(path, inRequest);
	}     
        
	public void verifyUser(String inCode) throws ClientException {
		final String path = "/api/userrequests/verify/" + inCode;
		doPut(path);
	} 
        
	public List<Role> getRoles() throws ClientException {
		final String path = "/api/protected/roles";
		return doGet(path, RoleList);
	}    
        
	public Role getRole(Long inRoleId) throws ClientException {
		final String path = "/api/protected/roles/" + inRoleId;
		return doGet(path, Role.class);
	}
	
	public Role getRoleByName(String name) throws ClientException {
		return doGet("/api/protected/roles/byname/" + name, Role.class);
	}

	public OAuthProvider getOAuthProvider(Long inId) throws ClientException {
		final String path = "/api/protected/oauthproviders/" + inId;
		return doGet(path, OAuthProvider.class);
	}

	public OAuthProvider getOAuthProviderByName(String inName) throws ClientException {
		final String path = UriBuilder.fromPath("/api/protected/oauthproviders/byname/")
				.segment(inName)
				.build().toString();
		return doGet(path, OAuthProvider.class);
	}      
}
