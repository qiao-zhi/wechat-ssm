package cn.qs.mapper.wechat.custom;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import cn.qs.bean.wechat.Pay;

@Mapper
public interface PayCustomMapper {

	@Select("select pay.*,kindergarten.server,kindergarten.version,kindergarten.amount from pay inner join kindergarten on kindergarten.id = pay.kindergarten_id where pay.id = #{id}")
	Map<String, Object> detail(Integer id);

	/**
	 * 管理员看所有
	 * 
	 * @param condition
	 * @return
	 */
	@Select("select id, children_name as childrenName, kindergarten_name as kindergartenName,parent_name as parentName from pay where kindergarten_name like '%${keywords}%' or parent_name like '%${keywords}%' or children_name like '%${keywords}%'")
	List<Pay> listByCondition(Map condition);

	/**
	 * 普通用户看自己创建的
	 * 
	 * @param condition
	 * @return
	 */
	@Select("select id, children_name as childrenName, kindergarten_name as kindergartenName,parent_name as parentName from pay where creator = #{creator} and (kindergarten_name like '%${keywords}%' or parent_name like '%${keywords}%' or children_name like '%${keywords}%')")
	List<Pay> listByConditionByCreator(Map condition);

}
