package cn.qs.service.impl.wechat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.qs.bean.wechat.Kindergarten;
import cn.qs.mapper.BaseMapper;
import cn.qs.mapper.wechat.KindergartenMapper;
import cn.qs.service.impl.AbastractBaseSequenceServiceImpl;
import cn.qs.service.wechat.KindergartenService;

@Service
@Transactional
public class KindergartenServiceImpl extends AbastractBaseSequenceServiceImpl<Kindergarten> implements KindergartenService{

	@Autowired
	private KindergartenMapper kindergartenMapper;
	
	@Override
	public BaseMapper<Kindergarten, Integer> getBaseMapper() {
		return kindergartenMapper;
	}

}
