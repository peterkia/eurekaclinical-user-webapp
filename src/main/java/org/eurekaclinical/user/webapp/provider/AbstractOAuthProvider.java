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

import java.net.URI;
import javax.inject.Provider;
import org.eurekaclinical.user.webapp.config.UserWebappProperties;
import org.scribe.up.provider.BaseOAuthProvider;

/**
 * Base class for implementing injection providers for OAuth providers.
 * 
 * @author Andrew Post
 * @param <E> the type of the provider to provide.
 */
public abstract class AbstractOAuthProvider<E extends BaseOAuthProvider> implements Provider<E> {

    private final Class<E> oAuthProviderCls;
    private final UserWebappProperties properties;
    private final String callbackPath;
    
    /**
     * Instantiates the injection provider with the webapp's properties, the
     * class of the OAuth provider to create, and the callback path for the
     * OAuth provider to us. The OAuth provider must have a zero-argument
     * constructor that this class can call.
     * 
     * @param inProperties the user webapp's properties.
     * @param inOAuthProviderCls the class of the OAuth provider to create.
     * @param inCallbackPath the callback path to use.
     */
    AbstractOAuthProvider(UserWebappProperties inProperties, Class<E> inOAuthProviderCls, String inCallbackPath) {
        this.oAuthProviderCls = inOAuthProviderCls;
        this.properties = inProperties;
        this.callbackPath = inCallbackPath;
    }
    
    /**
     * Creates and returns the OAuth provider.
     * 
     * @return a newly-created OAuth provider.
     */
    @Override
    public E get() {
        try {
            E oAuthProvider = oAuthProviderCls.newInstance();
            oAuthProvider.setKey(getKey());
            oAuthProvider.setSecret(getSecret());
            URI callbackUrl = this.properties.getURI().resolve(this.callbackPath);
            oAuthProvider.setCallbackUrl(callbackUrl.toString());
            return oAuthProvider;
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new AssertionError("Can't create provider", ex);
        }
    }
    
    /**
     * Get the user webapp properties data.
     * 
     * @return the user webapp properties. Guaranteed not null.
     */
    UserWebappProperties getProperties() {
        return this.properties;
    }
    
    abstract String getKey();
    
    abstract String getSecret();

}
