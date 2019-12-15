package cn.qs.service.impl.wechat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.qs.bean.wechat.Pay;
import cn.qs.mapper.BaseMapper;
import cn.qs.mapper.wechat.PayMapper;
import cn.qs.service.impl.AbastractBaseSequenceServiceImpl;
import cn.qs.service.wechat.PayService;

@Service
@Transactional
public class PayServiceImpl extends AbastractBaseSequenceServiceImpl<Pay> implements PayService {

	@Autowired
	private PayMapper payMapper;

	@Override
	public BaseMapper<Pay, Integer> getBaseMapper() {
		return payMapper;
	}

}
