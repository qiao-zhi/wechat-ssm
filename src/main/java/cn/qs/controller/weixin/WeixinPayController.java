package cn.qs.controller.weixin;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wxpay.sdk.WXPayConstants.SignType;
import com.github.wxpay.sdk.WXPayUtil;

import cn.qs.utils.JSONResultUtil;
import cn.qs.utils.weixin.WeixinConstants;

@RestController
@RequestMapping("weixin/pay")
public class WeixinPayController {

	@RequestMapping("/unifiedOrder")
	public JSONResultUtil<Map<String, String>> unifiedOrder() throws Exception {
		Map<String, String> result = new LinkedHashMap<>();
		String nonceStr = WXPayUtil.generateNonceStr();
		String timeStamp = WXPayUtil.getCurrentTimestampStr();
		String packagee = "prepay_id=wx20191224214737140199";
		String signType = "MD5";
		result.put("appId", WeixinConstants.APPID);
		result.put("timeStamp", timeStamp);
		result.put("nonceStr", nonceStr);
		result.put("package", packagee);
		result.put("signType", signType);

		String generateSignature = WXPayUtil.generateSignature(result, "", SignType.MD5);
		result.put("paySign", generateSignature);

		return new JSONResultUtil<Map<String, String>>(true, "", result);
	}

}
