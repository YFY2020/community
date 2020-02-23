package com.nowcoder.community.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository("alphaMybatis")
@Primary  //高优先级
public class AlphaDaoMybatisImpl implements AlphaDao {
    @Override
    public String select() {
        return "Mybatis";
    }
}
