package cn.qs.controller.wechat;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.qs.bean.wechat.Pay;
import cn.qs.controller.AbstractSequenceController;
import cn.qs.service.BaseService;
import cn.qs.service.wechat.PayService;
import cn.qs.utils.JSONResultUtil;
import cn.qs.utils.system.SystemUtils;
import cn.qs.utils.web.WebUtils;

@Controller
@RequestMapping("pay")
public class PayController extends AbstractSequenceController<Pay> {

	@Autowired
	private PayService payService;

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
	public JSONResultUtil doAddJSON(@RequestBody Pay pay) {
		pay.setPayDate(new Date());
		pay.setUserId(SystemUtils.getLoginUser().getId());
		pay.setUsername(SystemUtils.getLoginUser().getUsername());

		payService.add(pay);
		return JSONResultUtil.ok();
	}

}
