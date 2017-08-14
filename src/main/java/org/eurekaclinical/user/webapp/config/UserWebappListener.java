package org.eurekaclinical.user.webapp.config;

/*-
 * #%L
 * Eureka! Clinical User
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
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceServletContextListener;

import javax.servlet.ServletContextEvent;

import org.eurekaclinical.common.config.InjectorSupport;

/**
 *
 * @author miaoai
 */
public class UserWebappListener extends GuiceServletContextListener {

    private final UserWebappProperties userWebAppProperties;
    private Injector injector;

    public UserWebappListener() {
        this.userWebAppProperties = new UserWebappProperties();
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        super.contextInitialized(servletContextEvent);
        servletContextEvent.getServletContext().addListener(this.injector.getInstance(ClientSessionListener.class));
        servletContextEvent.getServletContext().setAttribute(
                "userWebAppProperties", this.userWebAppProperties);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        super.contextDestroyed(servletContextEvent);
        servletContextEvent.getServletContext().removeAttribute(
                "userWebAppProperties");
    }

    @Override
    protected Injector getInjector() {
        this.injector = new InjectorSupport(
                new Module[]{
                    new AppModule(this.userWebAppProperties),
                    new ServletModule(this.userWebAppProperties)
                },
                this.userWebAppProperties).getInjector();
        return this.injector;
    }
}
