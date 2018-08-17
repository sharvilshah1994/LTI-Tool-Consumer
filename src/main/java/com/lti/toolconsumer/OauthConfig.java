package com.lti.toolconsumer;

import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthException;
import net.oauth.OAuthMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Map;
import java.util.Random;

@Configuration
public class OauthConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(OauthConfig.class);

    public String getOAuthSignature(Map<String, String> parameters, String key, String secret, String url, String method){
        OAuthMessage oam = new OAuthMessage(method, url, parameters.entrySet());
        OAuthConsumer cons = new OAuthConsumer(null, key, secret, null);
        OAuthAccessor acc = new OAuthAccessor(cons);

        try {
            oam.addRequiredParameters(acc);
            return oam.getParameter("oauth_signature");
        } catch (IOException | URISyntaxException | OAuthException var12) {
            LOGGER.error(var12.toString());
        }
        return null;
    }

    public String getNonce() {
        Random random = new Random();
        return String.valueOf((random.nextInt() * 100000000));
    }

    public long getTimestamp() {
        return new Date().getTime() / 1000;
    }
}
