package com.nastryair.project.hotelmanager.common.config;

import com.nastryair.project.hotelmanager.util.ConfigUtil;
import com.nastryair.project.hotelmanager.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class UserCenterConf {
    public final static String MODULE_NAME = "user-center";
    private static final Logger LOGGER = LoggerFactory.getLogger(UserCenterConf.MODULE_NAME);

    public static int userTokenExpiryDate = 1;
    public static String jwtSecretKey = "2019test";


//    static {
//        String configFileName = "/user-center.properties";
////        try {
//            Properties properties = ConfigUtil.getConfFileProperties(configFileName);
//
//            userTokenExpiryDate = Integer.valueOf(properties.getProperty("user.token.expiry.date.number", "1"));
//            jwtSecretKey = properties.getProperty("user.jwt.secret.key");
//
////        } catch (Exception e) {
////            e.printStackTrace();
////            LOGGER.info(ExceptionUtil.getStackInfo(e));
//////            System.exit(1);
////        }
//    }


}
