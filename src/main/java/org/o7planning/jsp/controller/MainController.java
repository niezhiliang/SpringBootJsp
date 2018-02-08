package org.o7planning.jsp.controller;

import com.alibaba.fastjson.JSON;
import org.o7planning.jsp.config.GeetestConfig;
import org.o7planning.jsp.model.Person;
import org.o7planning.jsp.sdk.GeetestLib;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class MainController {
private static List<Person> persons = new ArrayList<Person>();
static {
    persons.add(new Person("Jaffel","jaffel"));
    persons.add(new Person("Jaffel","jaffel1"));
}
@RequestMapping(value = {"/","index"},method = RequestMethod.GET)
    public String index(Model model){
    String message = "Hello Spring Boot + JSP";
    model.addAttribute("message",message);
    return "index";
}
@RequestMapping(value = {"personList"},method = RequestMethod.GET)
    public String viewPersonList(Model model){
    model.addAttribute("persons",persons);
    return "personList";
}
@RequestMapping(value = {"/","login"})
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/gt/register1")
    @ResponseBody
    public Object start1(HttpServletRequest request,
                         HttpServletResponse response,String t) throws IOException {
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                GeetestConfig.isnewfailback());

        String resStr = "{}";

        String userid = "test";

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userid); //网站用户id
        param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP

        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(param);

        //将服务器状态设置到session中
        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        //将userid设置到session中
        request.getSession().setAttribute("userid", userid);

        resStr = gtSdk.getResponseStr();
        return JSON.toJSON(resStr);

    }
    @RequestMapping(value = "/gt/register2")
    @ResponseBody
    public Object start2(HttpServletRequest request,
                       HttpServletResponse response,String t) {

          GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                GeetestConfig.isnewfailback());

        String resStr = "{}";

        //自定义userid
        String userid = "test";

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userid); //网站用户id
        param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP

        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(param);

        //将服务器状态设置到session中
        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        //将userid设置到session中
        request.getSession().setAttribute("userid", userid);

        resStr = gtSdk.getResponseStr();

        return JSON.toJSON(resStr);
    }
}
