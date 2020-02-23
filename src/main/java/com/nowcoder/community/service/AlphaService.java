package com.nowcoder.community.service;

import com.nowcoder.community.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class AlphaService {

  /*  public AlphaService(){
        System.out.println("实例化AlphaService");
    }


    @PostConstruct   //表示：init()方法会在构造器之后调用
    public void init(){
        System.out.println("初始化AlphaService");
    }


    @PreDestroy   //在销毁之前执行
    public void destory(){
        System.out.println("销毁AlphaService");
    }*/

     @Autowired
     private AlphaDao alphaDao;

     public  String find(){
         return alphaDao.select();
     }
}
