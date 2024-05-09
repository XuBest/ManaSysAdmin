package com.spring.controller;

import com.spring.dao.RegistrationinfoMapper;
import com.spring.entity.Events;
import com.spring.entity.Registrationinfo;
import com.spring.service.EventsService;
import com.spring.service.RegistrationinfoService;
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

/**活动报名信息*/
@Controller
public class RegistrationinfoController extends BaseController {
    @Autowired
    private RegistrationinfoMapper dao;
    @Autowired
    private RegistrationinfoService service;
    @Autowired
    private EventsService serviceRead;

    public String getWhere()
    {
        _var = new LinkedHashMap(); // 重置数据
        String where = " ";
        // 判断URL 参数eventid是否大于0
        if( Request.getInt("eventid") > 0 ) {
            // 大于0 则写入条件
            where += " AND eventid='"+Request.getInt("eventid")+"' ";
        }
        // 以下也是一样的操作，判断是否符合条件，符合则写入sql 语句
        if(!Request.get("eventName").equals("")) {
            where += " AND eventName LIKE '%"+Request.get("eventName")+"%' ";
        }
        return where;
    }
    /**后台报名信息列表*/
    @RequestMapping("/registrationinfo_list")
    public String list() {

        // 检测是否有登录，没登录则跳转到登录页面
        if (!checkLogin()) {
            return showError("尚未登录", "./login.do");
        }
        String order = Request.get("order", "id"); // 获取前台提交的URL参数 order  如果没有则设置为id
        String sort = Request.get("sort", "desc"); // 获取前台提交的URL参数 sort  如果没有则设置为desc
        int pagesize = Request.getInt("pagesize", 12); // 获取前台一页多少行数据

        Example example = new Example(Registrationinfo.class);
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
        List<Registrationinfo> list = service.selectPageExample(example , page , pagesize);   // 获取当前页的行数
        // 将列表写给界面使用
        assign("totalCount" , request.getAttribute("totalCount"));
        assign("list" , list);
        assign("orderby" , order);  // 把当前排序结果写进前台
        assign("sort" , sort);      // 把当前排序结果写进前台
        return json();   // 将数据写给前端
    }
    /**前台：用户自己的活动报名信息列表*/
    @RequestMapping("/registrationinfo_list_adder")
    public String listadder() {
        // 检测是否有登录，没登录则跳转到登录页面
        if (!checkLogin()) {
            return showError("尚未登录", "./login.do");
        }
        String order = Request.get("order", "id"); // 获取前台提交的URL参数 order  如果没有则设置为id
        String sort = Request.get("sort", "desc"); // 获取前台提交的URL参数 sort  如果没有则设置为desc
        int pagesize = Request.getInt("pagesize", 12); // 获取前台一页多少行数据

        Example example = new Example(Registrationinfo.class);
        Example.Criteria criteria = example.createCriteria();
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
        List<Registrationinfo> list = service.selectPageExample(example , page , pagesize);
        assign("totalCount" , request.getAttribute("totalCount"));
        assign("list" , list);
        assign("orderby" , order);
        assign("sort" , sort);
        return json();   // 将数据写给前端
    }

    @RequestMapping("/registrationinfo_add")
    public String add() {
        _var = new LinkedHashMap(); // 重置数据
        int id = Request.getInt("id");  // 根据id 获取 票务中的数据
        Events readMap =  serviceRead.find(id);
        // 将数据行写入给前台jsp页面
        assign("readMap" , readMap);
        return json();   // 将数据写给前端
    }

    @RequestMapping("/registrationinfo_updt")
    public String updt()
    {
        _var = new LinkedHashMap(); // 重置数据
        int id = Request.getInt("id");
        // 获取行数据，并赋值给前台jsp页面
        Registrationinfo mmm = service.find(id);
        assign("mmm" , mmm);
        assign("updtself" , 0);
        return json();   // 将数据写给前端
    }
    /**添加订票信息@return*/
    @RequestMapping("/registrationinfoinsert")
    public String insert() {
        _var = new LinkedHashMap(); // 重置数据
        String tmp = "";
        Registrationinfo post = new Registrationinfo();
        post.setEventid(Request.getInt("eventid"));
        post.setEventName(Request.get("eventName"));
        post.setEndTime(Request.get("endTime"));
        post.setEventStatus(Request.get("eventStatus"));
        post.setTelephone(Request.get("telephone"));
        post.setRegiStatus(Request.get("regiStatus"));
        post.setNotes(Request.get("notices"));
        post.setReserver(Request.get("reserver"));
        post.setRegiTime(Info.getDateStr());
        post.setStartTime(Request.get("startTime"));
        post.setAdder(Request.get("adder"));
        service.insert(post); // 插入数据
        int charuid = post.getId().intValue();
        Query.execute("update registrationinfo set regiStatus");
        if(isAjax()){
            return jsonResult(post);

        }
        return showSuccess("添加成功" , Request.get("referer").equals("") ? request.getHeader("referer") : Request.get("referer"));

    }

    /**更新活动报名信息*/
    @RequestMapping("/registrationinfoupdate")
    public String update() {
        _var = new LinkedHashMap(); // 重置数据
        // 创建实体类
        Registrationinfo post = new Registrationinfo();
        // 将前台表单数据填充到实体类
        if(!Request.get("eventid").equals(""))
            post.setEventid(Request.getInt("eventid"));
        if(!Request.get("eventName").equals(""))
            post.setEventName(Request.get("eventName"));
        if(!Request.get("endTime").equals(""))
            post.setEndTime(Request.get("endTime"));
        if(!Request.get("eventStatus").equals(""))
            post.setEventStatus(Request.get("eventStatus"));
        if(!Request.get("telephone").equals(""))
            post.setTelephone(Request.get("telephone"));
        if(!Request.get("regiStatus").equals(""))
            post.setRegiStatus(Request.get("regiStatus"));
        if(!Request.get("notices").equals(""))
            post.setNotes(Request.get("notices"));
        if(!Request.get("reserver").equals(""))
            post.setReserver(Request.get("reserver"));
        if(!Request.get("regiTime").equals(""))
            post.setRegiTime(Request.get("regiTime"));
        if(!Request.get("startTime").equals(""))
            post.setStartTime(Request.get("startTime"));
        if(!Request.get("adder").equals(""))
            post.setAdder(Request.get("adder"));

        post.setId(Request.getInt("id"));
        service.update(post); // 更新数据
        int charuid = post.getId().intValue();

        if(isAjax()){
            return jsonResult(post);
        }
        return showSuccess("更新成功" , Request.get("referer")); // 弹出保存成功，并跳转到前台提交的 referer 页面

    }
/**删除*/
    @RequestMapping("/registrationinfo_delete")
    public String delete()
    {
        _var = new LinkedHashMap(); // 重置数据
        if(!checkLogin()){
            return showError("尚未登录");
        }
        int id = Request.getInt("id");  // 根据id 删除某行数据
        HashMap map = Query.make("registrationinfo").find(id);

        service.delete(id);// 根据id 删除某行数据
        return showSuccess("删除成功",request.getHeader("referer"));//弹出删除成功，并跳回上一页
    }
}
