package com.nastryair.project.hotelmanager.common.config;

import com.nastryair.project.hotelmanager.util.ConfigUtil;
import com.nastryair.project.hotelmanager.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class UserCenterConf {
    public final static String MODULE_NAME = "user-center";
    private static final Logger LOGGER = LoggerFactory.getLogger(UserCenterConf.MODULE_NAME);
    public static int numberThread;
    public static int userTokenExpiryDate;
    public static String jwtSecretKey;
    public static String swaggerGlobalDefaultUserId;
    public static String swaggerGlobalDefaultToken;

    public static String unifiedAuthBaseUrl;
    public static String unifiedAuthGetTokenUrl;
    public static String unifiedAuthRedirectUri;
    public static String unifiedAuthResponseType;
    public static String unifiedAuthScope;
    public static String unifiedAuthState;
    public static String unifiedAuthClientId;
    public static String unifiedAuthClientSecret;
    public static String blankPageUrl;

    static {
        String configFileName = "/user-center.properties";
        try {
            Properties properties = ConfigUtil.getConfFileProperties(configFileName);
            numberThread = Integer.valueOf(properties.getProperty("usercenter.consumer.number.thread", "4"));
            userTokenExpiryDate = Integer.valueOf(properties.getProperty("user.token.expiry.date.number", "1"));
            jwtSecretKey = properties.getProperty("usercenter.jwt.secret.key");
            swaggerGlobalDefaultToken = properties.getProperty("usercenter.swagger.global.default.token");
            swaggerGlobalDefaultUserId = properties.getProperty("usercenter.swagger.global.default.userid");
            unifiedAuthBaseUrl = properties.getProperty("usercenter.unified.auth.baseUrl");
            unifiedAuthGetTokenUrl = properties.getProperty("usercenter.unified.auth.getTokenUrl");
            unifiedAuthRedirectUri = properties.getProperty("usercenter.unified.auth.redirectUri");
            unifiedAuthResponseType = properties.getProperty("usercenter.unified.auth.responseType");
            unifiedAuthScope = properties.getProperty("usercenter.unified.auth.scope");
            unifiedAuthState = properties.getProperty("usercenter.unified.auth.state");
            unifiedAuthClientId = properties.getProperty("usercenter.unified.auth.clientId");
            unifiedAuthClientSecret = properties.getProperty("usercenter.unified.auth.clientSecret");
            blankPageUrl = properties.getProperty("usercenter.wuxingFront.blankPage.url");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info(ExceptionUtil.getStackInfo(e));
            System.exit(1);
        }
    }


}
