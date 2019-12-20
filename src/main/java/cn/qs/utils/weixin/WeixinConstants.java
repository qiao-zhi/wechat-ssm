package cn.qs.utils.weixin;

import cn.qs.utils.file.PropertiesFileUtils;

public class WeixinConstants {

	public static String APPID = PropertiesFileUtils.getPropertyValue("application.properties", "APPID");

	public static String APP_SECRET = PropertiesFileUtils.getPropertyValue("application.properties", "APP_SECRET");

	public static String AUTH_REDIRECT_URL = PropertiesFileUtils.getPropertyValue("application.properties",
			"AUTH_REDIRECT_URL");

	public static void main(String[] args) {
		System.out.println(AUTH_REDIRECT_URL);

		PropertiesFileUtils.saveOrUpdateProperty("test", "123");
		System.out.println(PropertiesFileUtils.getPropertyValue("test"));
	}
}
