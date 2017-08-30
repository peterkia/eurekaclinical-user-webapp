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
package org.eurekaclinical.user.webapp.servlet.oauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.scribe.up.provider.OAuthProvider;

import org.eurekaclinical.scribeupext.profile.EurekaProfile;

/**
 *
 * @author miaoai
 */
public class AbstractOAuthRegistrationCallbackServlet<E extends EurekaProfile> extends HttpServlet {

    private final RegistrationOAuthCallbackSupport<E> callbackSupport;

    public AbstractOAuthRegistrationCallbackServlet(OAuthProvider oauthProvider) {
        this.callbackSupport = new RegistrationOAuthCallbackSupport<>(oauthProvider);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.callbackSupport.forwardProfileToRegisterPage(req, resp);
    }

}
