/**
 * Copyright (c) 2016, Joyent, Inc. All rights reserved.
 */
package com.joyent.http.signature.apache.httpclient;

import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.config.Lookup;

import java.security.KeyPair;

/**
 * {@link org.apache.http.config.Lookup} implementation that provides a
 * default mapping to an HTTP signatures
 * {@link org.apache.http.auth.AuthSchemeProvider}.
 *
 * @author <a href="https://github.com/dekobon">Elijah Zupancic</a>
 * @since 2.0.3
 */
public class HttpSignatureAuthSchemeProviderLookup implements Lookup<AuthSchemeProvider> {
    /**
     * Backing instance of provider used for all lookups.
     */
    private final HttpSignatureAuthSchemeProvider authSchemeProvider;

    /**
     * Create a new instance of the lookup with the passed provider.
     * @param authSchemeProvider provider to use to back lookup calls
     */
    public HttpSignatureAuthSchemeProviderLookup(
            final HttpSignatureAuthSchemeProvider authSchemeProvider) {
        this.authSchemeProvider = authSchemeProvider;
    }

    /**
     * Create a new instance of the lookup with a new provider setup with the
     * passed key.
     * @param keyPair Public/private keypair object used to sign HTTP requests.
     * @param useNativeCodeToSign true to enable native code acceleration of cryptographic singing
     */
    public HttpSignatureAuthSchemeProviderLookup(
            final KeyPair keyPair, final boolean useNativeCodeToSign) {
        this.authSchemeProvider = new HttpSignatureAuthSchemeProvider(
                keyPair, useNativeCodeToSign);
    }

    @Override
    public AuthSchemeProvider lookup(final String name) {
        if (name.equalsIgnoreCase(HttpSignatureAuthScheme.SCHEME_NAME)) {
            return authSchemeProvider;
        } else {
            return null;
        }
    }
}
