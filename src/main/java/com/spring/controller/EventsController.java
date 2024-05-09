package com.spring.controller;

import com.spring.dao.EventsMapper;
import com.spring.entity.Events;
import com.spring.entity.Ticket;
import com.spring.service.EventsService;
import dao.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import tk.mybatis.mapper.entity.Example;
import util.Info;
import util.Request;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Controller
public class EventsController  extends BaseController{
    @Autowired
    private EventsMapper dao;
    @Autowired
    private EventsService service;
    /*进行条件查询的时候，用到该方法**/
    public String getWhere()
    {
        _var = new LinkedHashMap(); // 重置数据
        String where = " ";
        // 以下也是一样的操作，判断是否符合条件，符合则写入sql 语句
        if(!Request.get("eventName").equals("")) {
            where += " AND eventName LIKE '%"+Request.get("eventName")+"%' ";
        }
        if(!Request.get("eventStatus").equals("")) {
            where += " AND eventStatus LIKE '%"+Request.get("eventStatus")+"%' ";
        }
        return where;
    }

    /**后台活动信息展示列表*/
    @RequestMapping("/events_list")
    public String list()
    {
        String order = Request.get("order" , "id"); // 获取前台提交的URL参数 order  如果没有则设置为id
        String sort  = Request.get("sort" , "desc"); // 获取前台提交的URL参数 sort  如果没有则设置为desc
        int    pagesize = Request.getInt("pagesize" , 12); // 获取前台一页多少行数据
        Example example = new Example(Events.class);//创建一个events类的拓展搜索类
        Example.Criteria criteria = example.createCriteria();          // 创建一个扩展搜索条件类
        String where = " 1=1 ";   // 创建初始条件为：1=1
        where += getWhere();      // 从方法中获取url 上的参数，并写成 sql条件语句
        criteria.andCondition(where);   // 将条件写进上面的扩展条件类中
        if(sort.equals("desc")){        // 判断前台提交的sort 参数是否等于  desc倒序  是则使用倒序，否则使用正序
            example.orderBy(order).desc();  // 把sql 语句设置成倒序
        }else{
            example.orderBy(order).asc();   // 把 sql 设置成正序
        }
        int page = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));  // 获取前台提交的URL参数 page  如果没有则设置为1
        page = Math.max(1 , page);  // 取两个数的最大值，防止page 小于1
        List<Events> list = service.selectPageExample(example , page , pagesize);   // 获取当前页的行数
        // 将列表写给界面使用
        assign("totalCount" , request.getAttribute("totalCount"));
        assign("list" , list);
        assign("orderby" , order);  // 把当前排序结果写进前台
        assign("sort" , sort);      // 把当前排序结果写进前台
        return json();   // 将数据写给前端
    }

    /**前台活动信息列表展示*/
    @RequestMapping("/eventslist")
    public String index() {

        String order = Request.get("order", "id");
        String sort = Request.get("sort", "desc");

        Example example = new Example(Events.class);//创建一个events类的拓展搜索类
        Example.Criteria criteria = example.createCriteria();          // 创建一个扩展搜索条件类
        String where = " 1=1 ";   // 创建初始条件为：1=1
        where += getWhere();      // 从方法中获取url 上的参数，并写成 sql条件语句
        criteria.andCondition(where);   // 将条件写进上面的扩展条件类中
        if(sort.equals("desc")){        // 判断前台提交的sort 参数是否等于  desc倒序  是则使用倒序，否则使用正序
            example.orderBy(order).desc();  // 把sql 语句设置成倒序
        }else{
            example.orderBy(order).asc();   // 把 sql 设置成正序
        }
        int page = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));  // 获取前台提交的URL参数 page  如果没有则设置为1
        page = Math.max(1 , page);  // 取两个数的最大值，防止page 小于1
        List<Events> list = service.selectPageExample(example , page ,12);   // 获取当前页的行数
        // 将列表写给界面使用
        assign("totalCount" , request.getAttribute("totalCount"));
        assign("list" , list);
        assign("where" , where);
        assign("orderby" , order);  // 把当前排序结果写进前台
        assign("sort" , sort);      // 把当前排序结果写进前台
        return json();
    }
    /**将当前信息清除，放回空的信息让add页面去填写*/
    @RequestMapping("/events_add")
    public String add()
    {
        _var = new LinkedHashMap(); // 重置数据
        return json();   // 将数据写给前端
    }
    /**获取信息，返回信息添加页面*/
    @RequestMapping("/events_updt")
    public String updt()
    {
        _var = new LinkedHashMap(); // 重置数据
        int id = Request.getInt("id");
        // 获取行数据，并赋值给前台jsp页面
        Events mmm = service.find(id);
        assign("mmm" , mmm);
        assign("updtself" , 0);

        return json();   // 将数据写给前端
    }

    /**添加内容@return*/
    @RequestMapping("/eventsinsert")
    public String insert()
    {
        _var = new LinkedHashMap(); // 重置数据
        String tmp="";
        Events post = new Events();//创建Events实体类；
        post.setEventName(Request.get("eventName"));
        post.setEventContent(Request.get("eventContent"));
        post.setMaxJoin(Request.getInt("maxJoin"));
        post.setPublishTime(Info.getDateStr());//是获取当下的时间戳吧
        post.setEndTime(Request.get("endTime"));
        post.setEventStatus(Request.get("eventStatus"));
        post.setCounter(Request.getInt("counter"));
        post.setSuccess(Request.getInt("success"));
        post.setReginum(Request.getInt("reginum"));
        post.setPicture(Request.get("picture"));

        service.insert(post);
        int charuid = post.getId().intValue();

        if(isAjax()){
            return jsonResult(post);
        }
        return showSuccess("添加成功" , Request.get("referer").equals("") ? request.getHeader("referer") : Request.get("referer"));
    }
    /**更新*/
    @RequestMapping("/eventsupdate")
    public String update()
    {
        _var = new LinkedHashMap(); // 重置数据
        String tmp="";
        Events post = new Events();//创建Events实体类；
        if(!Request.get("eventName").equals(""))
            post.setEventName(Request.get("eventName"));
        if(!Request.get("eventContent").equals(""))
            post.setEventContent(util.DownloadRemoteImage.run(Request.get("eventContent")));
        if(!Request.get("maxJoin").equals(""))
            post.setMaxJoin(Request.getInt("maxJoin"));
        if(!Request.get("endTime").equals(""))
            post.setEndTime(Request.get("endTime"));
        if(!Request.get("publishTime").equals(""))
            post.setPublishTime(Request.get("publishTime"));
        if(!Request.get("eventStatus").equals(""))
            post.setEventStatus(Request.get("eventStatus"));
        if(!Request.get("counter").equals(""))
            post.setCounter(Request.getInt("counter"));
        if(!Request.get("success").equals(""))
            post.setSuccess(Request.getInt("success"));
        if(!Request.get("reginum").equals(""))
            post.setReginum(Request.getInt("reginum"));
        if(!Request.get("picture").equals(""))
            post.setPicture(Request.get("picture"));

        post.setId(Request.getInt("id"));
        service.update(post); // 更新数据
        int charuid = post.getId().intValue();

        if(isAjax()){
            return jsonResult(post);
        }
        return showSuccess("更新成功" , Request.get("referer")); // 弹出保存成功，并跳转到前台提交的 referer 页面
    }

    /**后台：查看活动详情信息*/
    @RequestMapping("/events_detail")
    public String detail()
    {
        _var = new LinkedHashMap(); // 重置数据
        int id = Request.getInt("id");
        Events map = service.find(id);  // 根据前台url 参数中的id获取行数据
        assign("map" , map);  // 把数据写到前台
        return json();   // 将数据写给前端
    }
    /**前台：查看活动详情信息*/
    @RequestMapping("/eventsdetail")
    public String detailweb()
    {
        _var = new LinkedHashMap(); // 重置数据
        int id = Request.getInt("id");
        Events map = service.find(id);  // 根据前台url 参数中的id获取行数据
        Query.execute("update events set counter=counter+1 where id='"+request.getParameter("id")+"'");//做浏览量计数工作
        if(!checkLogin()){
            assign("isCollect" , false);
        }else{
            assign("isCollect" , Query.make("shoucangjilu").where("username",session.getAttribute("username")).where("biao" , "events").where("xwid",id).count()>0);
        }
        assign("map" , map);
        return json();   // 将数据写给前端
    }
    /**删除*/
    @RequestMapping("/events_delete")
    public String delete()
    {
        _var = new LinkedHashMap(); // 重置数据
        if(!checkLogin()){
            return showError("尚未登录");
        }
        int id = Request.getInt("id");  // 根据id 删除某行数据
        HashMap map = Query.make("events").find(id);
        service.delete(id);// 根据id 删除某行数据
        return showSuccess("删除成功",request.getHeader("referer"));//弹出删除成功，并跳回上一页
    }

    }
