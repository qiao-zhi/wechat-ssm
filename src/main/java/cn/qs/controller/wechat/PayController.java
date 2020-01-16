package cn.qs.controller.wechat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.qs.bean.user.User;
import cn.qs.bean.wechat.Pay;
import cn.qs.controller.AbstractSequenceController;
import cn.qs.service.BaseService;
import cn.qs.service.user.UserService;
import cn.qs.service.wechat.PayService;
import cn.qs.utils.JSONResultUtil;
import cn.qs.utils.UUIDUtils;
import cn.qs.utils.format.ArithUtils;
import cn.qs.utils.system.MySystemUtils;
import cn.qs.utils.weixin.pay.WeixinPayUtils;
import cn.qs.utils.weixin.pay.WxPayXmlUtil;

@Controller
@RequestMapping("pay")
public class PayController extends AbstractSequenceController<Pay> {

	private static final Logger logger = LoggerFactory.getLogger(PayController.class);

	@Autowired
	private PayService payService;

	@Autowired
	private UserService userService;

	@Override
	public String getViewBasePath() {
		return "kindergarten";
	}

	@Override
	public BaseService<Pay, Integer> getBaseService() {
		return payService;
	}

	/**
	 * 统一下订单
	 * 
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping("unifiedOrder")
	@ResponseBody
	public JSONResultUtil<Map<String, String>> unifiedOrder(@RequestBody Pay pay) {
		// 1.创建系统信息
		pay.setPayDate(new Date());
		pay.setUserId(MySystemUtils.getLoginUser().getId());
		pay.setUsername(MySystemUtils.getLoginUser().getUsername());

		String loginUsername = MySystemUtils.getLoginUsername();
		User findUserByUsername = userService.findUserByUsername(loginUsername);
		Float coupon = ArithUtils.format(findUserByUsername.getCoupon(), 2);
		Float actuallyPay = pay.getPayAmount();
		if (coupon != null && coupon != 0 && coupon < pay.getPayAmount()) {
			Float shouldPay = ArithUtils.format(pay.getPayAmount(), 2);
			actuallyPay = ArithUtils.sub(shouldPay, coupon);
			pay.setPayAmount(actuallyPay);
			pay.setRemark1("应收金额: " + shouldPay + ",实收金额: " + actuallyPay + ", 第一次付费减金额： " + coupon);

			// 去掉优惠券
			findUserByUsername.setCoupon(0F);
			userService.update(findUserByUsername);

			logger.info("{}使用第一次赠送金额{}", findUserByUsername.getFullname(), coupon);
		} else {
			logger.info("没有优惠金额");
		}

		String orderId = UUIDUtils.getUUID2();
		pay.setOrderId(orderId);
		pay.setOrderStatus("未支付");

		payService.add(pay);

		// 2.创建订单==用于JSAPI发起支付
		String orderName = pay.getChildrenName() + "在幼儿园 " + pay.getKindergartenName() + "支付学费";
		Map<String, String> unifiedOrder = WeixinPayUtils.unifiedOrder(orderId, orderName, actuallyPay,
				MySystemUtils.getLoginUser().getUsername());
		unifiedOrder.put("payId", pay.getId() + "");

		return new JSONResultUtil<Map<String, String>>(true, "ok", unifiedOrder);
	}

	@RequestMapping("detailCus/{id}")
	@ResponseBody
	public JSONResultUtil<Map<String, Object>> detailCus(@PathVariable() Integer id) {
		Map<String, Object> result = payService.detail(id);

		return new JSONResultUtil<>(true, "", result);
	}

	/**
	 * 微信成功回调地址
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/paySuccess")
	public void paySuccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			InputStream inStream = request.getInputStream();
			int _buffer_size = 1024;
			if (inStream != null) {
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				byte[] tempBytes = new byte[_buffer_size];
				int count = -1;
				while ((count = inStream.read(tempBytes, 0, _buffer_size)) != -1) {
					outStream.write(tempBytes, 0, count);
				}
				tempBytes = null;
				outStream.flush();
				// 将流转换成字符串
				String result = new String(outStream.toByteArray(), "UTF-8");

				// 转换为Map处理自己的业务逻辑，这里将订单状态改为已支付
				if (StringUtils.isNotBlank(result)) {
					Map<String, String> xmlToMap = WxPayXmlUtil.xmlToMap(result);
					if ("SUCCESS".equals(MapUtils.getString(xmlToMap, "result_code", ""))) {
						String orderId = MapUtils.getString(xmlToMap, "out_trade_no", "");
						Pay systemPay = payService.findByOrderId(orderId);
						if (systemPay != null && systemPay.getOrderStatus() != "已支付") {
							systemPay.setOrderStatus("已支付");
							logger.info("修改订单状态为已支付, orderId: {} ", orderId);
							payService.update(systemPay);
						}
					}
				}
			}

			// 通知微信支付系统接收到信息
			response.getWriter().write(
					"<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
		} catch (Exception e) {
			logger.error("paySuccess error", e);

			// 如果失败返回错误，微信会再次发送支付信息
			response.getWriter().write("fail");
		}
	}

}
