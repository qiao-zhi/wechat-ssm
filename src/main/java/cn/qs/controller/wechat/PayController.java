package cn.qs.controller.wechat;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.qs.bean.wechat.Pay;
import cn.qs.controller.AbstractSequenceController;
import cn.qs.service.BaseService;
import cn.qs.service.wechat.PayService;
import cn.qs.utils.JSONResultUtil;
import cn.qs.utils.system.SystemUtils;

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

	@RequestMapping("detailCus/{id}")
	@ResponseBody
	public JSONResultUtil<Map<String, Object>> detailCus(@PathVariable() Integer id) {
		Map<String, Object> result = payService.detail(id);

		return new JSONResultUtil<>(true, "", result);
	}

}
