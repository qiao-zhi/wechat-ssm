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

	public static void main(String[] args) {
		String nonceStr = WXPayUtil.generateNonceStr();
		String timeStamp = WXPayUtil.getCurrentTimestampStr();
		System.out.println(nonceStr);
		System.out.println(timeStamp);
	}

	@RequestMapping("/unifiedOrder")
	public JSONResultUtil<Map<String, String>> unifiedOrder() throws Exception {
		Map<String, String> result = new LinkedHashMap<>();
		// String nonceStr = WXPayUtil.generateNonceStr();
		String nonceStr = "QBdMnRuSkPjWFp2Z";
		String timeStamp = WXPayUtil.getCurrentTimestampStr();
		String signType = "MD5";
		result.put("appId", WeixinConstants.APPID);
		result.put("timeStamp", timeStamp);
		result.put("nonceStr", nonceStr);
		String packagee = "prepay_id=wx16165741768311f4178d750b1566498000";
		result.put("package", packagee);
		result.put("signType", signType);

		String generateSignature = "C13486F86334DE1C1265723B448F2448";
		result.put("paySign", generateSignature);

		return new JSONResultUtil<Map<String, String>>(true, "", result);
	}

	@RequestMapping("/paySuccess")
	public void paySuccess(Map<String, Object> msg) {
		System.out.println("成功通知：" + msg);
	}

}
