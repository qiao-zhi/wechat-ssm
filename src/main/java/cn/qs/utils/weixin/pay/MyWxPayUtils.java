package cn.qs.utils.weixin.pay;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayConstants.SignType;
import com.github.wxpay.sdk.WXPayUtil;

import cn.qs.utils.HttpUtils;
import cn.qs.utils.weixin.WeixinConstants;

public class MyWxPayUtils {

	public static void main(String[] args) throws Exception {
		// unifiedOrder();
		orderquery();
	}

	private static void orderquery() throws Exception {
		// 构造请求数据
		Map<String, String> param = new LinkedHashMap<>();
		// 商户订单号(根据商户订单号查询)
		param.put("out_trade_no", "2");

		// 构造配置信息
		WXPayConfig wxPayConfig = new MyWxPayConfig();
		WXPay wxPay = new WXPay(wxPayConfig, null, false, true);
		Map<String, String> orders = wxPay.orderQuery(param);
		System.out.println(orders);
	}

	/**
	 * 获取沙箱测试的API_KEY
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getSandboxAPIKey() throws Exception {
		// 参与sign的字段包括mch_id、nonce_str、真实环境的key
		Map<String, String> param = new LinkedHashMap<>();
		param.put("mch_id", WeixinConstants.MCHID);
		param.put("nonce_str", WXPayUtil.generateNonceStr());
		String generateSignature = WXPayUtil.generateSignature(param, WeixinConstants.API_KEY, SignType.MD5); // wxPayConfig.getKey()是真实环境的key值
		param.put("sign", generateSignature);

		// 转为XML
		String mapToXml = WXPayUtil.mapToXml(param);
		String url = "https://api.mch.weixin.qq.com/sandboxnew/pay/getsignkey";
		// 发送请求获取XML数据
		String doPost = HttpUtils.doPost(url, mapToXml);
		Map<String, String> xmlToMap = WXPayUtil.xmlToMap(doPost);
		String sandbox_signkey = MapUtils.getString(xmlToMap, "sandbox_signkey", "");
		return sandbox_signkey;
	}

	private static void unifiedOrder() throws Exception {
		// 构造请求数据
		Map<String, String> param = new LinkedHashMap<>();

		// 设备信息
		param.put("device_info", "WEB");
		// 商品描述
		param.put("body", "测试样品");
		// 商品详情
		param.put("detail", "商品详情");
		// 商品详情
		param.put("attach", "附加信息");
		// 商户订单号
		param.put("out_trade_no", "2");
		// 标价币种
		param.put("fee_type", "CNY");
		// 标价金额(单位为分)
		param.put("total_fee", "101");
		// 终端IP
		param.put("spbill_create_ip", "123.12.12.123");
		// 交易起始时间
		param.put("time_start", "20191220091010");
		// 交易结束时间
		param.put("time_expire", "20201227091010");
		// 通知地址
		param.put("notify_url", "http://www.weixin.qq.com/wxpay/pay.php");
		// 交易类型
		param.put("trade_type", "JSAPI");
		// 商品ID
		param.put("product_id", "123");

		// 构造配置信息
		WXPayConfig wxPayConfig = new MyWxPayConfig();
		WXPay wxPay = new WXPay(wxPayConfig, null, false, true);
		Map<String, String> unifiedOrder = wxPay.unifiedOrder(param);
		System.out.println(unifiedOrder);
	}
}
