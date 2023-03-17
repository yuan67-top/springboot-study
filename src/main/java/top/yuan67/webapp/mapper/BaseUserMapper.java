package top.yuan67.webapp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.yuan67.webapp.entity.BaseUser;

/**
 * @Author: NieQiang
 * @User: CAPTAIN
 * @CreateTime: 2023-3-15
 * @Desc: 描述信息
 **/
@Mapper
public interface BaseUserMapper {
  @Select("SELECT * FROM base_user WHERE username = #{username}")
  BaseUser loadUserByUsername(String username);
}
