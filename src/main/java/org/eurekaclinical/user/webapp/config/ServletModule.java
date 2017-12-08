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
package org.eurekaclinical.user.webapp.config;

import java.util.HashMap;
import java.util.Map;

import org.eurekaclinical.common.config.AbstractAuthorizingServletModule;
import org.eurekaclinical.common.config.WebappServletModule;

import org.eurekaclinical.common.servlet.DestroySessionServlet;
import org.eurekaclinical.common.servlet.LogoutServlet;
import org.eurekaclinical.common.servlet.ProxyServlet;

import org.eurekaclinical.user.webapp.servlet.UserAcctManagerServlet;
import org.eurekaclinical.user.webapp.servlet.VerifyUserServlet;
import org.eurekaclinical.user.webapp.servlet.ChooseAccountTypeServlet;
import org.eurekaclinical.user.webapp.servlet.RegisterUserServlet;
import org.eurekaclinical.user.webapp.servlet.AdminManagerServlet;
import org.eurekaclinical.user.webapp.servlet.oauth.GitHubRegistrationOAuthCallbackServlet;
import org.eurekaclinical.user.webapp.servlet.oauth.GlobusRegistrationOAuthCallbackServlet;
import org.eurekaclinical.user.webapp.servlet.oauth.GoogleRegistrationOAuthCallbackServlet;

/**
 *
 * @author miaoai
 */
public class ServletModule extends WebappServletModule {

    public ServletModule(UserWebappProperties inProperties) {
        super(inProperties);
    }

    @Override
    protected void setupServlets() {
        super.setupServlets();
        serve("/chooseaccounttype").with(ChooseAccountTypeServlet.class);
        serve("/register").with(RegisterUserServlet.class);
        serve("/protected/user_acct").with(UserAcctManagerServlet.class);
        serve("/verify").with(VerifyUserServlet.class);
        serve("/protected/admin").with(AdminManagerServlet.class);
        serve("/registrationoauthgithubcallback").with(
                GitHubRegistrationOAuthCallbackServlet.class);
        serve("/registrationoauthgooglecallback").with(
                GoogleRegistrationOAuthCallbackServlet.class);
        serve("/registrationoauthglobuscallback").with(
                GlobusRegistrationOAuthCallbackServlet.class);

    }

    @Override
    protected void setupFilters() {
        super.setupFilters();
        filterRegex("^/(?!proxy-resource).*").through(AttributeFilter.class);
    }

}
