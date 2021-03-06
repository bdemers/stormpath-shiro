/*
 * Copyright 2012 Stormpath, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stormpath.shiro.servlet.mvc;

import com.stormpath.sdk.idsite.AuthenticationResult;
import com.stormpath.sdk.servlet.mvc.IdSiteResultController;
import com.stormpath.sdk.servlet.mvc.ViewModel;
import com.stormpath.shiro.realm.PassthroughApplicationRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.config.ConfigurationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * This will be removed before the 0.7.0 release, keeping for now as it was referenced in a support request ticket.
 * @deprecated replaced with {@link com.stormpath.shiro.servlet.filter.StormpathShiroPassiveLoginFilter}.
 */
@Deprecated
public class ShiroIDSiteResultController extends IdSiteResultController {

    @Override
    protected ViewModel onAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationResult result) {
        ViewModel vm = super.onAuthentication(request, response, result);

        AuthenticationToken token = new PassthroughApplicationRealm.AccountAuthenticationToken(result.getAccount());

        try {
            SecurityUtils.getSubject().login(token);
        } catch (AuthenticationException e) {
            String msg = "Stormpath Shiro realm is not configured correctly, see documentation for: " + PassthroughApplicationRealm.class.getName();
            throw new ConfigurationException(msg, e);
        }

        return vm;
    }
}
