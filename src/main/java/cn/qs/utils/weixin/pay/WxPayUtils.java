package cn.qs.utils.weixin.pay;

import java.util.LinkedHashMap;
import java.util.Map;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;

public class WxPayUtils {

	public static void main(String[] args) throws Exception {
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
		param.put("out_trade_no", "1");
		// 标价币种
		param.put("fee_type", "CNY");
		// 标价金额
		param.put("total_fee", "88");
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
