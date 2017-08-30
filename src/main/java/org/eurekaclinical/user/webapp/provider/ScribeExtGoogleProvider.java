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
package org.eurekaclinical.user.webapp.provider;

import javax.inject.Inject;
import org.eurekaclinical.scribeupext.provider.Google2Provider;

import org.eurekaclinical.user.webapp.config.UserWebappProperties;

/**
 * A provider for injecting a scribe-up Google OAuth provider.
 * 
 * @author miaoai, Andrew Post
 */
public class ScribeExtGoogleProvider extends AbstractOAuthProvider<Google2Provider> {

    @Inject
    public ScribeExtGoogleProvider(UserWebappProperties inProperties) {
        super(inProperties, Google2Provider.class, "registrationoauthgooglecallback");
    }

}
