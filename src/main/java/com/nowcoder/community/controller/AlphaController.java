package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {

    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/find")
    @ResponseBody
    public  String getDate(){
        return alphaService.find();
    }



    /*@RequestMapping("/hello")
    @ResponseBody
    public String sayHello(){
        return "Hello 2 Spring Boot!";
    }


    public String getData(){
        return alphaService.find();
    }*/

    @RequestMapping("/http_1")
    public void httpTest(HttpServletRequest request, HttpServletResponse response){
        //获取请求数据
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()){
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name+": "+value);
        }
        System.out.println(request.getParameter("code"));


        //返回响应数据
        response.setContentType("text/html;charset=utf-8");
        try (
                PrintWriter writer = response.getWriter();  //编译后会自动加一个finally，将writer关闭掉
                ){
            writer.write("<h1>牛客网</h1>");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Get请求
    //传参方式1： /students?current=1&limit=20
    @RequestMapping(path = "/students",method = RequestMethod.GET) //强制是get请求才能访问到
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current",required = false,defaultValue = "1") int current,
            @RequestParam(name = "limit",required = false,defaultValue = "10") int limit){

        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    // 传参方式2:  /student/3
    @RequestMapping(path = "/student/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id){
        System.out.println(id);
        return "a student";
    }


    //Post请求
    @RequestMapping(path = "/student",method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name,int age){
        System.out.println(name);
        System.out.println(age);

        return "success";
    }

    //响应HTML数据
    @RequestMapping(path = "/teacher",method = RequestMethod.GET)
    public ModelAndView getTeacher(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("name","张三");
        mav.addObject("age",12);
        mav.setViewName("/demo/view");  //resources--templates路径下
        return mav;
    }

    @RequestMapping(path = "/school",method = RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name","武汉大学");
        model.addAttribute("age",100);
        return "/demo/view";
    }

    //响应JSON数据（异步请求）
    //java对象,json字符串-->JS对象

    @RequestMapping(path = "/emp",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getEmp(){
        Map<String,Object> emp = new HashMap<>();
        emp.put("name","张三");
        emp.put("age","12");
        emp.put("salary",8000.00);
        return emp;
    }

    @RequestMapping(path = "/emps",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getEmps(){
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> emp = new HashMap<>();
        emp.put("name","张三");
        emp.put("age","12");
        emp.put("salary",8000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","李四");
        emp.put("age","13");
        emp.put("salary",6000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name","王五");
        emp.put("age","16");
        emp.put("salary",7000.00);
        list.add(emp);

        return list;
    }
}
