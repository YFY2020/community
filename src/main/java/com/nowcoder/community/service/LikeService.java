package com.nowcoder.community.service;

import com.nowcoder.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private RedisTemplate redisTemplate;

    //点赞
    public void like(int userId, int entityType, int entityId, int entityUserId) {

        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
                String userLikeKey = RedisKeyUtil.getUserLikeKey(entityUserId);
                //判断user是否点过赞（通过判断userId是否在set集合中）
                boolean isMember = redisTemplate.opsForSet().isMember(entityLikeKey, userId);

                redisOperations.multi(); //开启事务
                if (isMember) {
                    redisOperations.opsForSet().remove(entityLikeKey, userId);  //如果已经点过赞，再次点击就取消赞
                    redisOperations.opsForValue().decrement(userLikeKey);   //用户收到的赞减1
                } else {
                    redisOperations.opsForSet().add(entityLikeKey, userId);  //如果没点过赞,就将userId加入set集合
                    redisOperations.opsForValue().increment(userLikeKey);  //用户收到的赞加1
                }

                return redisOperations.exec();  //提交事务
            }
        });

    }

    //查询某实体(帖子、评论、回复)点赞的数量
    public long findEntityLikeCount(int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

    //查询某人对某实体(帖子、评论、回复)的点赞状态
    public int findEntityLikeStatus(int userId, int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().isMember(entityLikeKey, userId) ? 1 : 0;  //1表示点赞，0表示没点赞
    }

    // 查询某个用户获得的赞
    public int findUserLikeCount(int userId) {
        String userLikeKey = RedisKeyUtil.getUserLikeKey(userId);
        Integer count = (Integer) redisTemplate.opsForValue().get(userLikeKey);
        return count == null ? 0 : count.intValue();
    }

}
