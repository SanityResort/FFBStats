/*
 * Decompiled with CFR 0_122.
 */
package com.balancedbytes.games.ffb.util;

import java.net.MalformedURLException;
import java.net.URL;

public class UtilUrl {
    public static String createUrl(String pBaseUrl, String pRelativeUrl) {
        String url;
        if (StringTool.isProvided(pBaseUrl)) {
            if (StringTool.isProvided(pRelativeUrl)) {
                try {
                    URL base = new URL(pBaseUrl);
                    url = new URL(base, pRelativeUrl).toString();
                }
                catch (MalformedURLException mue) {
                    url = null;
                }
            } else {
                url = pBaseUrl;
            }
        } else {
            url = pRelativeUrl;
        }
        return url;
    }

}

