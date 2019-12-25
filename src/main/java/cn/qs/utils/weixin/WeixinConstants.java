package cn.qs.utils.weixin;

import cn.qs.utils.file.PropertiesFileUtils;

public class WeixinConstants {

	public static String APPID = PropertiesFileUtils.getPropertyValue("application.properties", "APPID");

	public static String MCHID = PropertiesFileUtils.getPropertyValue("application.properties", "MCHID");

	public static String API_KEY = PropertiesFileUtils.getPropertyValue("application.properties", "API_KEY");

	public static String APP_SECRET = PropertiesFileUtils.getPropertyValue("application.properties", "APP_SECRET");

	public static String AUTH_REDIRECT_URL = PropertiesFileUtils.getPropertyValue("application.properties",
			"AUTH_REDIRECT_URL");

	public static String ROLE_ADMIN_REDIRECTURL = PropertiesFileUtils.getPropertyValue("application.properties",
			"ROLE_ADMIN_REDIRECTURL");

	public static String ROLE_PLAIN_REDIRECTURL = PropertiesFileUtils.getPropertyValue("application.properties",
			"ROLE_PLAIN_REDIRECTURL");
}
