package cn.qs.controller.wechat;

import java.util.Date;
import java.util.Map;

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
import cn.qs.utils.format.ArithUtils;
import cn.qs.utils.system.MySystemUtils;

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
	 * JSON形式的数据
	 * 
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping("doAddJSON")
	@ResponseBody
	public JSONResultUtil<Pay> doAddJSON(@RequestBody Pay pay) {
		pay.setPayDate(new Date());
		pay.setUserId(MySystemUtils.getLoginUser().getId());
		pay.setUsername(MySystemUtils.getLoginUser().getUsername());

		String loginUsername = MySystemUtils.getLoginUsername();
		User findUserByUsername = userService.findUserByUsername(loginUsername);
		Float coupon = ArithUtils.format(findUserByUsername.getCoupon(), 2);
		if (coupon != null && coupon != 0 && coupon < pay.getPayAmount()) {
			Float shouldPay = ArithUtils.format(pay.getPayAmount(), 2);
			Float actuallyPay = ArithUtils.sub(shouldPay, coupon);
			pay.setPayAmount(actuallyPay);
			pay.setRemark1("应收金额: " + shouldPay + ",实收金额: " + actuallyPay + ", 第一次付费减金额： " + coupon);

			// 去掉优惠券
			findUserByUsername.setCoupon(0F);
			userService.update(findUserByUsername);

			logger.info("{}使用第一次赠送金额{}", findUserByUsername.getFullname(), coupon);
		} else {
			logger.info("没有优惠金额");
		}

		payService.add(pay);
		return new JSONResultUtil<Pay>(true, "ok", pay);
	}

	@RequestMapping("detailCus/{id}")
	@ResponseBody
	public JSONResultUtil<Map<String, Object>> detailCus(@PathVariable() Integer id) {
		Map<String, Object> result = payService.detail(id);

		return new JSONResultUtil<>(true, "", result);
	}

}
