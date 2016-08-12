/*
 * #%L
 * Eureka! Clinical User Webapp
 * %%
 * Copyright (C) 2012 - 2015 Emory University
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
package org.eurekaclinical.user.servlet;

import com.google.inject.Inject;
import org.eurekaclinical.user.webapp.config.UserWebappProperties;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eurekaclinical.scribeupext.provider.GitHubProvider;
import org.eurekaclinical.scribeupext.provider.GlobusProvider;
import org.eurekaclinical.scribeupext.provider.Google2Provider;
import org.eurekaclinical.scribeupext.provider.SSLTwitterProvider;
import org.scribe.up.session.HttpUserSession;
import org.slf4j.LoggerFactory;

/**
 *
 * @author miaoai
 */
public class ChooseAccountTypeServlet extends HttpServlet {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ChooseAccountTypeServlet.class);    
	private final UserWebappProperties properties;
	private final Google2Provider googleProvider;
	private final SSLTwitterProvider twitterProvider;
	private final GlobusProvider globusProvider;
	private final GitHubProvider gitHubProvider;

	@Inject
	public ChooseAccountTypeServlet(UserWebappProperties inProperties, 
			Google2Provider inGoogleProvider, 
			GitHubProvider inGitHubProvider, 
			SSLTwitterProvider inTwitterProvider, 
			GlobusProvider inGlobusProvider) {
		this.properties = inProperties;
		this.googleProvider = inGoogleProvider;
		this.twitterProvider = inTwitterProvider;
		this.globusProvider = inGlobusProvider;
		this.gitHubProvider = inGitHubProvider;
	}
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		boolean oauthRegistrationEnabled = this.properties.isOAuthRegistrationEnabled();
		req.setAttribute("oauthRegistrationEnabled", oauthRegistrationEnabled);
		
		boolean localAccountRegistrationEnabled = this.properties.isLocalAccountRegistrationEnabled();
		
		if (oauthRegistrationEnabled) {
			HttpUserSession userSession = new HttpUserSession(req);
			String authorizationUrl = null;
			int countEnabled = 0;
			boolean googleAuthEnabled = this.properties.isGoogleOAuthRegistrationEnabled();
			req.setAttribute("googleAuthEnabled", googleAuthEnabled);
			if (googleAuthEnabled) {
				authorizationUrl = googleProvider.getAuthorizationUrl(userSession);
				req.setAttribute("Google2ProviderUrl", authorizationUrl);
				countEnabled++;
			}
			
			boolean gitHubAuthEnabled = this.properties.isGitHubOAuthRegistrationEnabled();
			req.setAttribute("gitHubAuthEnabled", gitHubAuthEnabled);
			if (gitHubAuthEnabled) {
				authorizationUrl = gitHubProvider.getAuthorizationUrl(userSession);
				req.setAttribute("GitHubProviderUrl", authorizationUrl);
				countEnabled++;
			}
			
			boolean twitterAuthEnabled = this.properties.isTwitterOAuthRegistrationEnabled();
			req.setAttribute("twitterAuthEnabled", twitterAuthEnabled);
			if (twitterAuthEnabled) {
				authorizationUrl = twitterProvider.getAuthorizationUrl(userSession);
				req.setAttribute("SSLTwitterProviderUrl", authorizationUrl);
				countEnabled++;
			}
			
			boolean globusAuthEnabled = this.properties.isGlobusOAuthRegistrationEnabled();
			req.setAttribute("globusAuthEnabled", globusAuthEnabled);
			if (globusAuthEnabled) {
				authorizationUrl = globusProvider.getAuthorizationUrl(userSession);
				req.setAttribute("GlobusProviderUrl", authorizationUrl);
				countEnabled++;
			}
			
			if (countEnabled == 1 && !localAccountRegistrationEnabled) {
				resp.sendRedirect(authorizationUrl);
				return;
			}
		}
		
		if (localAccountRegistrationEnabled && !oauthRegistrationEnabled) {
                        
                        resp.sendRedirect(req.getContextPath() + "/register.jsp");
                        
                        LOGGER.info("Host = " + req.getServerName()+"  Port = " + req.getServerPort()+" Scheme = "
                                +req.getScheme()+" Servlet Context="+req.getServletContext()+" Context Path="+req.getContextPath());
                        String httpsString = req.getContextPath();
                        LOGGER.info("httpString is: "+httpsString+ " Index of / is: "+ httpsString.indexOf("/"));
                        LOGGER.info("user webapp url: "+this.properties.getUserWebappUrl());
                        
		} else if (!localAccountRegistrationEnabled && !oauthRegistrationEnabled) {
			throw new ServletException("Registration is disabled");
		} else {
			req.setAttribute("localAccountRegistrationEnabled", localAccountRegistrationEnabled);
			req.getRequestDispatcher("/choose_account_type.jsp").forward(req, resp);
		}
	}
	
}
