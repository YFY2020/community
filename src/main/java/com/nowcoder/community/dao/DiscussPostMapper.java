package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    //查询帖子
    List<DiscussPost> selectDiscussPosts(int userId,int offset,int limit);

    //查询帖子数量
    //@Param注解用于给参数取别名
    //如果只有一个参数，并且在动态标签<if>里使用，则必须加别名
    int selectDiscussPostRows(@Param("userId") int userId);

    //插入帖子
    int insertDiscussPost(DiscussPost discussPost);

    //根据id查询帖子
    DiscussPost selectDiscussPostById(int id);

    //修改评论数量
    int updateCommentCount(int id,int commentCount);

    //修改帖子类型
    int updateType(int id, int type);

    //修改帖子状态
    int updateStatus(int id, int status);

}
