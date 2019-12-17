package cn.qs.utils.weixin;

import cn.qs.utils.file.PropertiesFileUtils;

public class WeixinConstants {

	public static String APPID = PropertiesFileUtils.getPropertyValue("application.properties", "APPID");

	public static String APP_SECRET = PropertiesFileUtils.getPropertyValue("application.properties", "APP_SECRET");
}
