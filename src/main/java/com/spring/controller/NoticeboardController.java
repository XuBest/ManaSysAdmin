package com.spring.controller;

import com.spring.dao.NoticeboardMapper;
import com.spring.entity.News;
import com.spring.entity.Noticeboard;
import com.spring.service.NoticeboardService;
import dao.CommDAO;
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

/**公告信息*/
@Controller
public class NoticeboardController extends BaseController{
    @Autowired
    private NoticeboardMapper dao;
    @Autowired
    private NoticeboardService service;

    public String getWhere() /**生成动态select的语句，下面写的这些后面可以根据它来进行查询信息*/
    {
        _var = new LinkedHashMap(); // 重置数据
        String where = " ";
        // 以下也是一样的操作，判断是否符合条件，符合则写入sql 语句
        if(!Request.get("title").equals("")) {
            where += " AND title LIKE '%"+Request.get("title")+"%' ";
        }
        if(!Request.get("adder").equals("")) {
            where += " AND adder LIKE '%"+Request.get("adder")+"%' ";
        }
        if(!Request.get("counter_start").equals("")) {
            where += " AND counter >='"+Request.get("counter_start")+"' ";
        }
        if(!Request.get("counter_end").equals("")) {
            where += " AND counter <= '"+Request.get("counter_end")+"' ";
        }
        if(!Request.get("content").equals("")) {
            where += " AND content LIKE '%"+Request.get("content")+"%' ";
        }
        return where;
    }

    /**后台公告列表展示*/
    @RequestMapping("/noticeboard_list")
    public String list(){
        // 检测是否有登录，没登录则跳转到登录页面
        if(!checkLogin()){
            return showError("尚未登录" , "./login.do");
        }
        String order = Request.get("order" , "id"); // 获取前台提交的URL参数 order  如果没有则设置为id
        String sort  = Request.get("sort" , "desc"); // 获取前台提交的URL参数 sort  如果没有则设置为desc
        int   pagesize = Request.getInt("pagesize" , 12); // 获取前台一页多少行数据

        Example example = new Example(Noticeboard.class);  //  创建一个扩展搜索类
        Example.Criteria criteria = example.createCriteria();           // 创建一个扩展搜索条件类
        // 初始化一个条件，条件为：添加人=当前登录用户
        String where = " 1=1 ";   // 创建初始条件为：1=1
        where += getWhere();
        criteria.andCondition(where);   // 将条件写入
        if(sort.equals("desc")){        // 注释同list
            example.orderBy(order).desc(); // 注释同list
        }else{
            example.orderBy(order).asc(); // 注释同list
        }
        int page = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page")); // 注释同list
        page = Math.max(1 , page); // 注释同list

        List<Noticeboard> list = service.selectPageExample(example , page , pagesize);// 获取当前页的行数

        // 将列表写给界面使用
        assign("totalCount" , request.getAttribute("totalCount"));
        assign("list" , list);
        assign("orderby" , order);  // 把当前排序结果写进前台
        assign("sort" , sort);      // 把当前排序结果写进前台
        return json();   // 将数据写给前端
    }
    /**前台公告列表展示*/
    @RequestMapping("/noticeboardlist")
    public String BeforeList()
    {
        String order = Request.get("order" , "id");
        String sort  = Request.get("sort" , "desc");
        int    pagesize = Request.getInt("pagesize" , 12); // 获取前台一页多少行数据
        Example example = new Example(Noticeboard.class);
        Example.Criteria criteria = example.createCriteria();
        String where = " 1=1 ";
        where += getWhere();
        criteria.andCondition(where);
        if(sort.equals("desc")){
            example.orderBy(order).desc();
        }else{
            example.orderBy(order).asc();
        }
        int page = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
        page = Math.max(1 , page);
        List<Noticeboard> list = service.selectPageExample(example , page , pagesize);// 获取当前页的行数

        // 将列表写给界面使用
        assign("totalCount" , request.getAttribute("totalCount"));
        assign("list" , list);
        assign("orderby" , order);  // 把当前排序结果写进前台
        assign("sort" , sort);      // 把当前排序结果写进前台
        return json();   // 将数据写给前端
    }

    /**管理员自己添加的公告列表展示*/
    @RequestMapping("/noticeboard_list_adder")
    public String NoticeAdder(){
        // 检测是否有登录，没登录则跳转到登录页面
        if(!checkLogin()){
            return showError("尚未登录" , "./login.do");
        }
        String order = Request.get("order" , "id"); // 获取前台提交的URL参数 order  如果没有则设置为id
        String sort  = Request.get("sort" , "desc"); // 获取前台提交的URL参数 sort  如果没有则设置为desc
        int   pagesize = Request.getInt("pagesize" , 12); // 获取前台一页多少行数据

        Example example = new Example(Noticeboard.class);  //  创建一个扩展搜索类
        Example.Criteria criteria = example.createCriteria();           // 创建一个扩展搜索条件类
        // 初始化一个条件，条件为：添加人=当前登录用户
        String where = " adder='"+request.getSession().getAttribute("username")+"' ";
        where += getWhere();
        criteria.andCondition(where);   // 将条件写入
        if(sort.equals("desc")){        // 注释同list
            example.orderBy(order).desc(); // 注释同list
        }else{
            example.orderBy(order).asc(); // 注释同list
        }
        int page = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page")); // 注释同list
        page = Math.max(1 , page); // 注释同list

        List<Noticeboard> list = service.selectPageExample(example , page , pagesize);// 获取当前页的行数

        // 将列表写给界面使用
        assign("totalCount" , request.getAttribute("totalCount"));
        assign("list" , list);
        assign("orderby" , order);  // 把当前排序结果写进前台
        assign("sort" , sort);      // 把当前排序结果写进前台
        return json();   // 将数据写给前端
    }

    /**公告添加*/
    @RequestMapping("/noticeboard_add")
    public String add()
    {
        _var = new LinkedHashMap(); // 重置数据
        return json();   // 将数据写给前端
    }

    /**根据id,将新闻信息获取出来，输出在新闻更新的前端页面那里？*/
    @RequestMapping("/noticeboard_updt")
    public String updt()
    {
        _var = new LinkedHashMap(); // 重置数据
        int id = Request.getInt("id");
        Noticeboard mmm = service.find(id);
        assign("mmm" , mmm);
        assign("updtself" , 0);

        return json();   // 将数据写给前端}
    }

    /**插入公告*/
    @RequestMapping("/noticeboardinsert")
    public String insert()
    {
        _var = new LinkedHashMap(); // 重置数据
        String tmp="";
        Noticeboard post = new Noticeboard(); //创建实体类
        //设置前台提交上来的数据到实体类中
        post.setTitle(Request.get("title"));
        post.setPicture(Request.get("picture"));
        post.setAdder(Request.get("adder"));
        post.setCounter(Request.getInt("counter"));
        post.setContent(util.DownloadRemoteImage.run(Request.get("content")));
        post.setAddtime(Info.getDateStr());

        service.insert(post);//插入数据
        int charuid = post.getId().intValue();

        if(isAjax()){
            return jsonResult(post);

        }
        return showSuccess("公告插入成功" , Request.get("referer").equals("") ? request.getHeader("referer") : Request.get("referer"));

    }
    /**更新公告内容  。获取前端输入的信息，和实体类对应，去数据库中更新字段*/
    @RequestMapping("/noticeboardupdate")
    public String update()
    {
        _var = new LinkedHashMap(); // 重置数据
        Noticeboard post = new Noticeboard(); //创建实体类
        if(!Request.get("title").equals(""))
            post.setTitle(Request.get("title"));
        if(!Request.get("picture").equals(""))
            post.setPicture(Request.get("picture"));
        if(!Request.get("adder").equals(""))
            post.setAdder(Request.get("adder"));
        if(!Request.get("counter").equals(""))
            post.setCounter(Request.getInt("counter"));
        if(!Request.get("content").equals(""))
            post.setContent(util.DownloadRemoteImage.run(Request.get("content")));
        if(!Request.get("addtime").equals(""))
            post.setAddtime(Request.get("addtime"));

        post.setId(Request.getInt("id"));
        service.update(post); // 更新数据
        int charuid = post.getId().intValue();

        if(isAjax()){
            return jsonResult(post);
        }

        return showSuccess("公告信息更新成功" , Request.get("referer")); // 弹出保存成功，并跳转到前台提交的 referer 页面

    }

    /**查看公告详情 后台*/
    @RequestMapping("/noticeboard_detail")
    public String detail()
    {
        _var = new LinkedHashMap(); // 重置数据
        int id = Request.getInt("id");
        Noticeboard map = service.find(id);
        assign("map" , map);  // 把数据写到前台
         /**       if(map.equals(null) ){
                    System.out.println("空");
                }else{
                    System.out.println("map的title="+map.getTitle());
                    System.out.println("非空");};*/


        return json();
    }

    /**前台详情 主要就是允许添加收藏以及浏览量+1*/
    @RequestMapping("/noticeboarddetail")
    public String detailweb()
    {
        _var = new LinkedHashMap(); // 重置数据
        int id = Request.getInt("id");
        Noticeboard map = service.find(id);
        Query.execute("UPDATE noticeboard SET counter=counter+1 WHERE id="+request.getParameter("id")+"");

        if(!checkLogin()){
            assign("isCollect" , false);
        }else{
            assign("isCollect" , Query.make("shoucangjilu").where("username",session.getAttribute("username")).where("biao" , "noticeboard").where("xwid",id).count()>0);
        }//将该公告放到收藏记录里面
        assign("map" , map);
        return json();   // 将数据写给前端
    }
    /**删除公告*/
    @RequestMapping("/noticeboard_delete")
    public String delete()
    {
        _var = new LinkedHashMap(); // 重置数据
        if(!checkLogin()){
            return showError("尚未登录");
        }
        int id = Request.getInt("id");  // 根据id 删除某行数据
        HashMap map = Query.make("noticeboard").find(id);
        service.delete(id);// 根据id 删除某行数据
        return showSuccess("该公告删除成功",request.getHeader("referer"));//弹出删除成功，并跳回上一页
    }
}
