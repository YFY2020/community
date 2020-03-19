package com.nowcoder.community;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class redisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    //测试String类型的
    @Test
    public void testString(){
        String redisKey = "test:count";
        redisTemplate.opsForValue().set(redisKey,1);

        System.out.println(redisTemplate.opsForValue().get(redisKey));
        System.out.println(redisTemplate.opsForValue().increment(redisKey));
        System.out.println(redisTemplate.opsForValue().decrement(redisKey));
    }

    //测试Hash类型的
    @Test
    public void testHashes(){
        String redisKey = "test:user";
        redisTemplate.opsForHash().put(redisKey,"id",1);
        redisTemplate.opsForHash().put(redisKey,"username","zhangsan");

        System.out.println(redisTemplate.opsForHash().get(redisKey,"id"));
        System.out.println(redisTemplate.opsForHash().get(redisKey,"username"));
    }


    @Test
    public void testLists(){
        String redisKey = "test:ids";
        redisTemplate.opsForList().leftPush(redisKey,101);
        redisTemplate.opsForList().leftPush(redisKey,102);
        redisTemplate.opsForList().leftPush(redisKey,103);

        System.out.println(redisTemplate.opsForList().size(redisKey));  //List中有多少个数据
        System.out.println(redisTemplate.opsForList().index(redisKey,0));  //获取第0个数据
        System.out.println(redisTemplate.opsForList().range(redisKey,0,2));  //获取第0-2的数据

        System.out.println(redisTemplate.opsForList().leftPop(redisKey));
        System.out.println(redisTemplate.opsForList().leftPop(redisKey));
        System.out.println(redisTemplate.opsForList().leftPop(redisKey));
    }


    //测试set集合
    @Test
    public void testSets(){
        String redisKey = "test:teachers";
        redisTemplate.opsForSet().add(redisKey,"刘备","关羽","张飞","赵云","诸葛亮");

        System.out.println(redisTemplate.opsForSet().size(redisKey));  //set集合中一共有多少个值
        System.out.println(redisTemplate.opsForSet().pop(redisKey));   //随即被弹出一个值
        System.out.println(redisTemplate.opsForSet().members(redisKey));  //查看集合中的数据
    }

    //测试SortedSet集合
    @Test
    public void testSortedSet(){
        String redisKey = "test:students";
        redisTemplate.opsForZSet().add(redisKey,"唐僧",80);
        redisTemplate.opsForZSet().add(redisKey,"悟空",90);
        redisTemplate.opsForZSet().add(redisKey,"八戒",50);
        redisTemplate.opsForZSet().add(redisKey,"沙僧",70);
        redisTemplate.opsForZSet().add(redisKey,"白龙马",60);

        System.out.println(redisTemplate.opsForZSet().zCard(redisKey));  //统计数据的个数
        System.out.println(redisTemplate.opsForZSet().score(redisKey,"八戒"));//取“八戒”的值
        System.out.println(redisTemplate.opsForZSet().rank(redisKey,"八戒"));//由小到大排列，看排第几
        System.out.println(redisTemplate.opsForZSet().reverseRank(redisKey,"八戒"));//由大到小排列，看排第几
        System.out.println(redisTemplate.opsForZSet().range(redisKey,0,2));//由小到大排列，取第0，1，2三个数据
        System.out.println(redisTemplate.opsForZSet().reverseRange(redisKey,0,2));//由大到小排列，取第0，1，2三个数据
    }

    //与key相关的方法
    @Test
    public void testKeys(){
        redisTemplate.delete("test:user");
        System.out.println(redisTemplate.hasKey("test:user"));

        redisTemplate.expire("test:students",10, TimeUnit.SECONDS);  //设置过期时间
    }


    //解决多次访问同一个key，不用重复输入key的问题
    @Test
    public void testBoundOperations(){
        String redisKey ="test:count";
        BoundValueOperations operations = redisTemplate.boundValueOps(redisKey);
        operations.increment();
        operations.increment();
        operations.increment();

        System.out.println(operations.get());
    }

    //编程式事务
    @Test
    public void testTransactional(){
        Object obj = redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {

                String redisKey = "test:tx";
                redisOperations.multi();//启用事务

                redisOperations.opsForSet().add(redisKey,"zhangsan");
                redisOperations.opsForSet().add(redisKey,"lisi");
                redisOperations.opsForSet().add(redisKey,"wangwu");

                //在redis管理事务时，不要做查询，查询是无效的
                System.out.println(redisOperations.opsForSet().members(redisKey));

                return redisOperations.exec(); //提交事务
            }
        });
        System.out.println(obj);
    }

}
