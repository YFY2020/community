package com.nowcoder.community.dao;

import com.nowcoder.community.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User selectById(int id);  //根据id查询

    User selectByName(String username);  //根据名称查询

    User selectByEmail(String email);    //根据邮箱查询

    int insertUser(User user);   //插入用户

    int updateStatus(int id,int status);    //根据id更改用户状态

    int updateHeader(int id,String headerUrl);   //根据id更改用户头像

    int updatePassword(int id,String password);  //根据id更改用户密码

}
