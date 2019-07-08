package com.service;

import com.pojo.Article;
import com.pojo.Myblog01;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

public interface MyBlogService {
    boolean getMB(String user); //change事件判断是否存在用户

    boolean getMyblog(String user, String password, HttpSession session); //判断用户登录密码是否正确

    String getEmailyz(String email) throws MessagingException, GeneralSecurityException;//返回注册用户邮箱验证码

    Integer Insert(String user, String password, String email);//注册成功保存用户信息

    Integer update01(String user, String introduce, String Occupation, String age, String hobby, String tel);//修改个人信息

    Integer Insertwz(String title, MultipartFile[] files, String article_content, HttpSession session) throws IOException;//发表文章

    Integer Insertxq(String mood, HttpSession session);     //发表心情

    Integer Insertxc(String xcfilepath, HttpSession session);   //上传相册路径

    Myblog01 index(HttpSession session);    //主页数据和文章并进行分页初始

    Myblog01 wzpage(String user, Integer currtpage); //点击文章上下页实现局部切换

    Myblog01 shuo02(HttpSession session);   //心情页和数据并进行分页初始

    Myblog01 xqpage(String user, Integer currtpage);

    Myblog01 getXc(HttpSession session);    //查询该用户的所有上传相册

    Myblog01 getAbout(HttpSession session); //关于我页面初始

    Myblog01 getfb(HttpSession session);    //发布页面初始

    Myblog01 getGuestBook(HttpSession session); //留言页面初始
}
