package cn.qs.service.impl.wechat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.qs.bean.wechat.Kindergarten;
import cn.qs.mapper.BaseMapper;
import cn.qs.mapper.wechat.KindergartenMapper;
import cn.qs.service.impl.AbastractBaseSequenceServiceImpl;
import cn.qs.service.wechat.KindergartenService;

@Service
@Transactional
public class KindergartenServiceImpl extends AbastractBaseSequenceServiceImpl<Kindergarten>
		implements KindergartenService {

	@Autowired
	private KindergartenMapper kindergartenMapper;

	@Override
	public BaseMapper<Kindergarten, Integer> getBaseMapper() {
		return kindergartenMapper;
	}

	@Override
	public List<Map<String, Object>> listNamesAndIds() {
		// 构造请求参数，页号从0开始。
		int pageNum = 0;
		int pageSize = 1000;
		Pageable pageRequest = new QPageRequest(pageNum, pageSize);
		Page<Kindergarten> page = kindergartenMapper.findAll(pageRequest);

		List<Map<String, Object>> result = new ArrayList<>();
		if (page != null && CollectionUtils.isNotEmpty(page.getContent())) {
			Map<String, Object> tmpMap = null;

			for (Kindergarten tmp : page.getContent()) {
				tmpMap = new HashMap<>();
				tmpMap.put("key", tmp.getId());
				tmpMap.put("value", tmp.getName());

				result.add(tmpMap);
			}
		}

		return result;

	}

}
