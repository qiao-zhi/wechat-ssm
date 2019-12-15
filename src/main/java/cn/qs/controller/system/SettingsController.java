package cn.qs.controller.system;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.qs.utils.JSONResultUtil;
import cn.qs.utils.file.PropertiesFileUtils;

@RequestMapping("settings")
@RestController
public class SettingsController {

	@RequestMapping("getProperty")
	public JSONResultUtil<Float> getProperty(String key) {
		String propertyValue = PropertiesFileUtils.getPropertyValue(key);
		float amount = NumberUtils.toFloat(propertyValue, 0);
		return new JSONResultUtil<Float>(true, "ok", amount);
	}

	@RequestMapping("setProperty")
	public JSONResultUtil<Float> setProperty(String key, Float value) {
		PropertiesFileUtils.saveOrUpdateProperty(key, String.valueOf(value));
		return new JSONResultUtil<Float>(true, "ok", value);
	}

}
