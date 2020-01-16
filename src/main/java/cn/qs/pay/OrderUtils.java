package cn.qs.pay;

import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;

import cn.qs.utils.weixin.WeixinConstants;

public class OrderUtils {

	public static void main(String[] args) {
		// 微信支付配置
		WxPayConfig wxPayConfig = new WxPayConfig();
		wxPayConfig.setAppId(WeixinConstants.APPID); // 公众号Id

		// 支付商户资料
		wxPayConfig.setMchId(WeixinConstants.MCHID);
		wxPayConfig.setMchKey(WeixinConstants.API_KEY);
		wxPayConfig.setNotifyUrl(WeixinConstants.PAY_SUCCESS_NOTIFY_URL);

		// 支付类, 所有方法都在这个类里
		BestPayServiceImpl bestPayService = new BestPayServiceImpl();
		bestPayService.setWxPayConfig(wxPayConfig);

		PayRequest payRequest = new PayRequest();
		payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_MP);
		payRequest.setOrderId("123456");
		payRequest.setOrderName("微信公众账号支付订单");
		payRequest.setOrderAmount(0.01);
		payRequest.setOpenid("o5uzSvo2VX6WEckzynm7sQymz0Zs");
		PayResponse pay = bestPayService.pay(payRequest);
		
		System.out.println(pay);
	}
}
