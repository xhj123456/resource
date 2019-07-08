package com.controller;

import com.service.MyBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RestController01 {
    @Autowired
    MyBlogService myBlogService;
    static int i = 1;

    //遍历文件下面的文件放在网页上面去
    public void listFile(File file, Map<String, String> map) {
        //判断是不是一个文件
        if (!file.isFile()) {
            System.out.println("asdasd");
            File[] files = file.listFiles();
            for (File file1 : files) {
                listFile(file1, map);
            }
        } else {
            String filename = file.getName().substring(file.getName().lastIndexOf("$") + 1, file.getName().lastIndexOf("."));
            if (map.get(filename) != null) {
                map.put(filename + i++, file.getName());
            } else {
                map.put(filename, file.getName());
            }
        }
    }

    @RequestMapping("down")
    public ModelAndView download(ModelAndView mv) {
        HashMap<String, String> map = new HashMap<>();
        listFile(new File("E:\\uploads"), map);
        mv.setViewName("download");     //html名
        mv.addObject("name", "下载");
        mv.addObject("map", map);
        return mv;
    }

    @RequestMapping("yh01")
    public boolean yhyz01(HttpServletRequest request) {
        String user = request.getParameter("user");
        return myBlogService.getMB(user);
    }

    @RequestMapping("dl")
    public boolean dl(HttpServletRequest request) {
        String user = request.getParameter("user");
        HttpSession session = request.getSession();
        String password = request.getParameter("password");
        return myBlogService.getMyblog(user, password, session);
    }

    @RequestMapping("yh02")
    public boolean yhyz02(HttpServletRequest request) {
        String user = request.getParameter("user");
        return myBlogService.getMB(user);
    }

    @RequestMapping("emailyzm")
    public Map<String, Object> emailyz(HttpServletRequest request) throws MessagingException, GeneralSecurityException {
        String email = request.getParameter("email");
        String emailyz = myBlogService.getEmailyz(email);

        Map<String, Object> map = new HashMap<>();
        map.put("emailyz", emailyz);
        return map;
    }

    @RequestMapping("zc")
    public Integer zc(HttpServletRequest request) {
        String user = request.getParameter("user");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        return myBlogService.Insert(user, password, email);
    }

    @RequestMapping("update")
    public Integer update(HttpServletRequest request) {
        String user = request.getParameter("user");
        String introduce = request.getParameter("introduce");
        String age = request.getParameter("age");
        String hobby = request.getParameter("hobby");
        String Occupation = request.getParameter("Occupation");
        String tel = request.getParameter("tel");
        return myBlogService.update01(user, introduce, Occupation, age, hobby, tel);
    }

    @RequestMapping("aaa")
    public void bbb(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("username");
    }

    @RequestMapping("sub2")
    public Integer sub2(HttpServletRequest request) {
        String mood = request.getParameter("mood");
        HttpSession session = request.getSession();
        return myBlogService.Insertxq(mood, session);
    }

}
