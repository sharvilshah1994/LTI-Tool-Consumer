package com.lti.toolconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ConsumerController {

    private final OauthConfig oauthConfig;

    @Autowired
    public ConsumerController(OauthConfig oauthConfig) {
        this.oauthConfig = oauthConfig;
    }

    @GetMapping("/")
    public String getDefaultHomePage(Model model) {
        Map<String, String> paramsMap = new HashMap<>();
        String secret = ParamConfig.SHARED_SECRET;
        String oauthConsumerKey = ParamConfig.OAUTH_CONSUMER_KEY;
        String url = ParamConfig.TOOL_PRODUCER_URL;
        paramsMap.put("custom_endpoint", url);
        paramsMap.put("oauth_consumer_key", oauthConsumerKey);
        paramsMap.put("lti_message_type", "basic-lti-launch-request");
        paramsMap.put("lti_version", "LTI-1p0");
        paramsMap.put("oauth_signature_method", "HMAC-SHA1");
        paramsMap.put("user_id","123456");
        paramsMap.put("roles","urn:lti:sysrole:ims/lis/User");
        paramsMap.put("lis_person_name_full","John Doe");
        paramsMap.put("lis_person_name_given","John");
        paramsMap.put("lis_person_name_family","Doe");
        paramsMap.put("oauth_callback","about:blank");
        paramsMap.put("custom_simple_key", "custom_simple_value");
        paramsMap.put("custom_complex_key", "Complex!@#$^*;(){}[]½Value");
        paramsMap.put("custom_cert_userid", "$User.id");
        paramsMap.put("custom_cert_username", "$User.username");
        paramsMap.put("custom_tc_profile_url", "$ToolConsumerProfile.url");
        paramsMap.put("oauth_version", "1.0");
        paramsMap.put("resource_link_id", "value");
        paramsMap.put("tool_consumer_info_product_family_code", "abc");
        paramsMap.put("tool_consumer_info_version", "1.0");
        if (ParamConfig.GET_GRADES_FROM_TOOL) {
            paramsMap.put("lis_outcome_service_url", ParamConfig.TOOL_CONSUMER_URL);
        }
        paramsMap.put("oauth_nonce", oauthConfig.getNonce());
        paramsMap.put("oauth_timestamp", String.valueOf(oauthConfig.getTimestamp()));
        paramsMap.put("oauth_signature", oauthConfig.getOAuthSignature(paramsMap, oauthConsumerKey, secret,
                url, "POST"));
        paramsMap.put("secret", secret);
        model.addAllAttributes(paramsMap);
        return "home";
    }

    @PostMapping("/")
    public String getGradesFromTool(HttpServletRequest request,
                                    Model model) {
        String lisResultSourcedid = request.getParameter("lis_result_score");
        model.addAttribute("result", lisResultSourcedid);
        return "grade";
    }
}
