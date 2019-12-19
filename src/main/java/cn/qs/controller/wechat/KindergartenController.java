package cn.qs.controller.wechat;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.qs.bean.wechat.Kindergarten;
import cn.qs.controller.AbstractController;
import cn.qs.controller.AbstractSequenceController;
import cn.qs.service.BaseService;
import cn.qs.service.wechat.KindergartenService;
import cn.qs.utils.DefaultValue;
import cn.qs.utils.JSONResultUtil;

@Controller
@RequestMapping("kindergarten")
public class KindergartenController extends AbstractSequenceController<Kindergarten> {

	private static final Logger LOGGER = LoggerFactory.getLogger(KindergartenController.class);

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

	@RequestMapping("listNamesAndIds")
	@ResponseBody
	public JSONResultUtil<List<Map<String, Object>>> listNamesAndIds() {
		// 开始分页
		List<Map<String, Object>> result = KindergartenService.listNamesAndIds();
		return new JSONResultUtil<List<Map<String, Object>>>(true, "", result);
	}

	/**
	 * SpringDataJPA分页(返回JSON信息，封装到工具类中)
	 * 
	 * @param condition
	 * @param request
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping("pageJSON")
	@ResponseBody
	public JSONResultUtil<Page<Kindergarten>> pageJSON(@RequestBody Map condition) throws InterruptedException {
		int pageNum = 1;
		if (StringUtils.isNotBlank(MapUtils.getString(condition, "pageNum"))) { // 如果不为空的话改变当前页号
			pageNum = MapUtils.getInteger(condition, "pageNum");
		}
		int pageSize = DefaultValue.PAGE_SIZE;
		if (StringUtils.isNotBlank(MapUtils.getString(condition, "pageSize"))) { // 如果不为空的话改变当前页大小
			pageSize = MapUtils.getInteger(condition, "pageSize");
		}

		condition.put("pageNum", pageNum - 1);
		condition.put("pageSize", pageSize);

		Page<Kindergarten> pages = null;
		// 开始分页
		try {
			pages = KindergartenService.pageByCondition(condition);
		} catch (Exception e) {
			LOGGER.error("SpringDataJPA page error, viewbasePath : {}", getViewBasePath(), e);
		}

		return new JSONResultUtil<Page<Kindergarten>>(true, "ok", pages);
	}
}
