package cn.qs.utils.weixin.pay;

import java.io.InputStream;

import com.github.wxpay.sdk.IWXPayDomain;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayConstants;

import cn.qs.utils.weixin.WeixinConstants;

public class MyWxPayConfig extends WXPayConfig {

	@Override
	public String getAppID() {
		return WeixinConstants.APPID;
	}

	@Override
	public String getMchID() {
		return WeixinConstants.MCHID;
	}

	@Override
	public String getKey() {
		return WeixinConstants.API_KEY;
	}

	@Override
	public InputStream getCertStream() {
		return null;
	}

	@Override
	public IWXPayDomain getWXPayDomain() {
		return new IWXPayDomain() {

			@Override
			public void report(String domain, long elapsedTimeMillis, Exception ex) {

			}

			@Override
			public DomainInfo getDomain(WXPayConfig config) {
				return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
			}
		};
	}

	/**
	 * 是否自动上报。 若要关闭自动上报，子类中实现该函数返回 false 即可。
	 *
	 * @return
	 */
	@Override
	public boolean shouldAutoReport() {
		return false;
	}

	@Override
	public String getSandBoxApiKey() {
		try {
			return MyWxPayUtils.getSandboxAPIKey();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

}
