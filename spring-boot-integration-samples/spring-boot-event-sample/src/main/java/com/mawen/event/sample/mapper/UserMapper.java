package com.mawen.event.sample.mapper;

import com.mawen.event.sample.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author mawen
 * @since 2023/1/5
 */
@Mapper
public interface UserMapper {

    @Select("select id, name from user where id = #{id}")
    UserEntity findById(@Param("id") Long id);

    @Update("update user set name = #{entity.name} where id = #{entity.id}")
    int updateById(@Param("entity") UserEntity entity);
}
