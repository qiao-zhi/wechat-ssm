package cn.qs.service.wechat;

import java.util.Map;

import cn.qs.bean.wechat.Pay;
import cn.qs.service.BaseSequenceService;

public interface PayService extends BaseSequenceService<Pay> {

	Map<String, Object> detail(Integer id);

}
