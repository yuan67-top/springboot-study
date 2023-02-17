package top.yuan67.webapp.mapper;

import org.apache.ibatis.annotations.*;
import top.yuan67.webapp.entity.User;

import java.util.List;

/**
 * @Author: NieQiang
 * @User: CAPTAIN
 * @CreateTime: 2023-2-17
 * @Desc: 描述信息
 **/
@Mapper
public interface UserMapper {
  @Insert("INSERT INTO user (`name`, `age`) VALUES (#{name}, #{age})")
  int add(User user);
  
  @Delete("DELETE FROM `user` WHERE id = #{id}")
  int del(Integer id);
  
  @Select("SELECT * FROM `user`")
  List<User> findAll();
  
  @Update("UPDATE `user` SET `name` = #{name}, `age` = #{age} WHERE `id` = #{id}")
  int update(User user);
}
