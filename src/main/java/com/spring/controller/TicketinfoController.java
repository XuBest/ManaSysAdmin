package com.spring.controller;

import com.spring.dao.TicketinfoMapper;
import com.spring.entity.Lvyouxianlu;
import com.spring.entity.Ticket;
import com.spring.entity.Ticketinfo;
import com.spring.entity.Yuding;
import com.spring.service.TicketService;
import com.spring.service.TicketinfoService;
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

/**购票信息*/
@Controller
public class TicketinfoController  extends BaseController{
    @Autowired
    private TicketinfoMapper dao;
    @Autowired
    private TicketinfoService service;

    @Autowired
    private TicketService serviceRead;

    /**后台列表页*/
    @RequestMapping("/ticketinfo_list")
    public String list() {

        // 检测是否有登录，没登录则跳转到登录页面
        if (!checkLogin()) {
            return showError("尚未登录", "./login.do");
        }
        String order = Request.get("order", "id"); // 获取前台提交的URL参数 order  如果没有则设置为id
        String sort = Request.get("sort", "desc"); // 获取前台提交的URL参数 sort  如果没有则设置为desc
        int pagesize = Request.getInt("pagesize", 12); // 获取前台一页多少行数据

        Example example = new Example(Ticketinfo.class); //  创建一个扩展搜索类
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
        List<Ticketinfo> list = service.selectPageExample(example , page , pagesize);   // 获取当前页的行数

        // 将列表写给界面使用
        assign("totalCount" , request.getAttribute("totalCount"));
        assign("list" , list);
        assign("orderby" , order);  // 把当前排序结果写进前台
        assign("sort" , sort);      // 把当前排序结果写进前台
        return json();   // 将数据写给前端
    }

    public String getWhere()
    {
        _var = new LinkedHashMap(); // 重置数据
        String where = " ";
        // 判断URL 参数ticketid是否大于0
        if( Request.getInt("ticketid") > 0 ) {
            // 大于0 则写入条件
            where += " AND ticketid='"+Request.getInt("ticketid")+"' ";
        }
        // 以下也是一样的操作，判断是否符合条件，符合则写入sql 语句
        if(!Request.get("ticketkey").equals("")) {
            where += " AND ticketkey LIKE '%"+Request.get("ticketkey")+"%' ";
        }
        if(!Request.get("ticketname").equals("")) {
            where += " AND ticketname LIKE '%"+Request.get("ticketname")+"%' ";
        }
        return where;
    }

    /**预订人处输出的列表信息*/
    @RequestMapping("/ticketinfo_list_adder")
    public String listadder()
    {
        // 检测是否有登录，没登录则跳转到登录页面
        if(!checkLogin()){
            return showError("尚未登录" , "./login.do");
        }
        String order = Request.get("order" , "id"); // 获取前台提交的URL参数 order  如果没有则设置为id
        String sort  = Request.get("sort" , "desc"); // 获取前台提交的URL参数 sort  如果没有则设置为desc
        int    pagesize = Request.getInt("pagesize" , 12); // 获取前台一页多少行数据


        Example example = new Example(Ticketinfo.class);  //  创建一个扩展搜索类
        Example.Criteria criteria = example.createCriteria();           // 创建一个扩展搜索条件类
        // 初始化一个条件，条件为：预订人=当前登录用户
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

        List<Ticketinfo> list = service.selectPageExample(example , page , pagesize);
        assign("totalCount" , request.getAttribute("totalCount"));
        assign("list" , list);
        assign("orderby" , order);
        assign("sort" , sort);
        return json();   // 将数据写给前端
    }

    @RequestMapping("/ticketinfo_add")
    public String add()
    {
        _var = new LinkedHashMap(); // 重置数据
        int id = Request.getInt("id");  // 根据id 获取 票务中的数据
        Ticket readMap = serviceRead.find(id);
        // 将数据行写入给前台jsp页面
        assign("readMap" , readMap);

        return json();   // 将数据写给前端
    }

    @RequestMapping("/ticketinfo_updt")
    public String updt()
    {
        _var = new LinkedHashMap(); // 重置数据
        int id = Request.getInt("id");
        // 获取行数据，并赋值给前台jsp页面
        Ticketinfo mmm = service.find(id);
        assign("mmm" , mmm);
        assign("updtself" , 0);

        return json();   // 将数据写给前端
    }

    /**
     * 添加订票信息
     * @return
     */
    @RequestMapping("/ticketinfoinsert")
    public String insert()
    {
        _var = new LinkedHashMap(); // 重置数据
        String tmp="";
        Ticketinfo post = new Ticketinfo();
        System.out.println("ci'sci'sh此时票务idwei为，可qu取到否"+Request.getInt("ticketid"));
        post.setTicketid(Request.getInt("ticketid"));
        post.setTicketkey(Request.get("ticketkey"));
        post.setTicketname(Request.get("ticketname"));
        post.setPrice(Request.getDouble("price"));
        post.setOrdernum(Request.get("ordernum"));
        post.setOrdertime(Request.get("ordertime"));
        post.setOrdername(Request.get("ordername"));
        post.setPhonenum(Request.get("phonenum"));
        post.setTicketstatus(Request.get("ticketstatus"));
        post.setNotice(Request.get("notice"));
        post.setAdder(Request.get("adder"));
        post.setAddtime(Info.getDateStr());
        post.setIfpay("否");

        service.insert(post); // 插入数据
        int charuid = post.getId().intValue();
        Query.execute("update ticketinfo set ticketstatus");
        if(isAjax()){
            return jsonResult(post);

        }
        return showSuccess("添加成功" , Request.get("referer").equals("") ? request.getHeader("referer") : Request.get("referer"));
    }

    /**
     * 更新订票信息
     * @return
     */
    @RequestMapping("/ticketinfoupdate")
    public String update()
    {
        _var = new LinkedHashMap(); // 重置数据
        // 创建实体类
        Ticketinfo post = new Ticketinfo();
        // 将前台表单数据填充到实体类
        if(!Request.get("ticketid").equals(""))
            post.setTicketid(Request.getInt("ticketid"));
        if(!Request.get("ticketkey").equals(""))
            post.setTicketkey(Request.get("ticketkey"));
        if(!Request.get("ticketname").equals(""))
            post.setTicketname(Request.get("ticketname"));
        if(!Request.get("price").equals(""))
            post.setPrice(Request.getDouble("price"));
        if(!Request.get("ordernum").equals(""))
            post.setOrdernum(Request.get("ordernum"));
        if(!Request.get("ordertime").equals(""))
            post.setOrdertime(Request.get("ordertime"));
        if(!Request.get("ordername").equals(""))
            post.setOrdername(Request.get("ordername"));
        if(!Request.get("phonenum").equals(""))
            post.setPhonenum(Request.get("phonenum"));
        if(!Request.get("ticketstatus").equals(""))
            post.setTicketstatus(Request.get("ticketstatus"));
        if(!Request.get("notice").equals(""))
            post.setNotice(Request.get("notice"));
        if(!Request.get("adder").equals(""))
            post.setAdder(Request.get("adder"));
        if(!Request.get("addtime").equals(""))
            post.setAddtime(Request.get("addtime"));

        post.setId(Request.getInt("id"));
        service.update(post); // 更新数据
        int charuid = post.getId().intValue();

        if(isAjax()){
            return jsonResult(post);
        }

        return showSuccess("更新成功" , Request.get("referer")); // 弹出保存成功，并跳转到前台提交的 referer 页面
    }
    /**
     *  删除
     */
    @RequestMapping("/ticketinfo_delete")
    public String delete()
    {
        _var = new LinkedHashMap(); // 重置数据
        if(!checkLogin()){
            return showError("尚未登录");
        }
        int id = Request.getInt("id");  // 根据id 删除某行数据
        HashMap map = Query.make("ticketinfo").find(id);

        service.delete(id);// 根据id 删除某行数据
        return showSuccess("删除成功",request.getHeader("referer"));//弹出删除成功，并跳回上一页
    }


}
