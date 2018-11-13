package com.nastryair.project.hotelmanager.hotel.common.config;

import com.nastryair.project.hotelmanager.hotel.util.ConfigUtil;
import com.nastryair.project.hotelmanager.hotel.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class HotelConf {
    public final static String MODULE_NAME = "hotel";
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelConf.MODULE_NAME);

    public static String blankPageUrl;

    static {
        String configFileName = "/user-center.properties";
        try {
            Properties properties = ConfigUtil.getConfFileProperties(configFileName);

            blankPageUrl = properties.getProperty("usercenter.wuxingFront.blankPage.url");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info(ExceptionUtil.getStackInfo(e));
            System.exit(1);
        }
    }


}
