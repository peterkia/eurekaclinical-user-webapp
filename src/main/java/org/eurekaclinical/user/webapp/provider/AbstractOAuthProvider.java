package org.eurekaclinical.user.webapp.provider;

/*-
 * #%L
 * Eureka! Clinical User Webapp
 * %%
 * Copyright (C) 2016 - 2017 Emory University
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

import com.google.inject.Provider;
import java.net.URI;
import org.eurekaclinical.user.webapp.config.UserWebappProperties;
import org.scribe.up.provider.BaseOAuthProvider;

/**
 * Base class for implementing providers of OAuth registration.
 * @author Andrew Post
 * @param <E> the type of the provider to provide.
 */
public abstract class AbstractOAuthProvider<E extends BaseOAuthProvider> implements Provider<E> {

    private final E oAuthProvider;
    
    protected AbstractOAuthProvider(UserWebappProperties inProperties, E inOAuthProvider, String inCallbackPath) {
        this.oAuthProvider = inOAuthProvider;
        this.oAuthProvider.setKey(inProperties.getGitHubOAuthKey());
        this.oAuthProvider.setSecret(inProperties.getGitHubOAuthSecret());
        URI url = URI.create(inProperties.getUrl());
        URI callbackUrl = url.resolve(inCallbackPath);
        this.oAuthProvider.setCallbackUrl(callbackUrl.toString());
    }
    
    @Override
    public E get() {
        return this.oAuthProvider;
    }

}
