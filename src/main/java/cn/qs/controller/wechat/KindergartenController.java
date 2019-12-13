package cn.qs.controller.wechat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.qs.bean.wechat.Kindergarten;
import cn.qs.controller.AbstractSequenceController;
import cn.qs.service.BaseService;
import cn.qs.service.wechat.KindergartenService;

@Controller
@RequestMapping("kindergarten")
public class KindergartenController extends AbstractSequenceController<Kindergarten> {

	@Autowired
	private KindergartenService KindergartenService;

	@Override
	public String getViewBasePath() {
		return "kindergarten";
	}

	@Override
	public BaseService<Kindergarten, Integer> getBaseService() {
		return KindergartenService;
	}

}
