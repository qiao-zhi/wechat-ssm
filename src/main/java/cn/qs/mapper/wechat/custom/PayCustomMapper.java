package cn.qs.mapper.wechat.custom;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PayCustomMapper {

	@Select("select pay.*,kindergarten.server,kindergarten.version,kindergarten.amount from pay inner join kindergarten on kindergarten.id = pay.kindergarten_id where pay.id = #{id}")
	Map<String, Object> detail(Integer id);

}
