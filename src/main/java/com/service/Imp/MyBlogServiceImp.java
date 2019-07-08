package com.service.Imp;

import com.dao.MyBlogDao;
import com.pojo.MyBlog;
import com.pojo.Myblog01;
import com.service.MyBlogService;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MyBlogServiceImp implements MyBlogService {
    @Autowired
    MyBlogDao myBlogDao;

    public String Time() {       //实时时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    @Override
    public boolean getMB(String user) {
        MyBlog myBlog = myBlogDao.getmyblog(user);
        if (myBlog == null) {     //如果为空就是不存在该用户
            return false;
        } else {
            return true;        //存在该用户
        }
    }

    @Override
    public boolean getMyblog(String user, String password, HttpSession session) {
        session.setAttribute("username", user);      //将用户名存到session域中
        MyBlog myBlog = myBlogDao.getmyblog(user);
        if (myBlog.getPassword().equals(password)) {
            return true;        //密码正确
        } else {
            return false;       //密码错误
        }
    }

    // 随机验证码
    public String achieveCode() {  //由于数字1 和0 和字母 O,l 有时分不清，所有，没有字母1 、0
        String[] beforeShuffle = new String[]{"2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F",
                "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a",
                "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
                "w", "x", "y", "z"};
        List list = Arrays.asList(beforeShuffle);//将数组转换为集合
        Collections.shuffle(list);  //打乱集合顺序
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)); //将集合转化为字符串
        }
        return sb.toString().substring(3, 8);  //截取字符串第4到8
    }

    @Override
    public String getEmailyz(String email) throws MessagingException, GeneralSecurityException {
        String yzm = achieveCode();
        Properties pros = new Properties();
        pros.setProperty("mail.debug", "true");  // 开启debug调试，以便在控制台查看
        pros.setProperty("mail.host", "smtp.qq.com");    // 设置邮件服务器主机名
        pros.setProperty("mail.smtp.auth", "true");  // 发送服务器需要身份验证
        pros.setProperty("mail.transport.protoool", "smtp"); // 发送邮件协议名称
        // 开启SSL加密，否则会失败
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        pros.put("mail.smtp.ssl.enable", "true");
        pros.put("mail.smtp.ssl.socketFactory", sf);
        Session session = Session.getInstance(pros);
        Transport ts = session.getTransport();// 通过session得到transport对象
        ts.connect("smtp.qq.com", "814318698", "phsvdkdlxnmnbbfg");//后面的字符是授权码，用qq密码
        MimeMessage msg = new MimeMessage(session); // 创建邮件对象
        msg.setFrom(new InternetAddress("814318698@qq.com"));    //发件人邮箱
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(email));//收件人邮箱
        msg.setSubject("发财的机会来啦！！", "UTF-8");     //设置邮件主题
        msg.setContent("欢迎注册博客，你老人家的验证码为： " + yzm + "  请保密！！！", "text/html;charset=UTF-8");// 邮件的文本内容 格式
        ts.sendMessage(msg, msg.getAllRecipients()); //发送邮件
        ts.close();
        return yzm;
    }

    @Override
    public Integer Insert(String user, String password, String email) {
        return myBlogDao.Insert01(user, password, email);
    }

    @Override
    public Integer update01(String user, String introduce, String Occupation, String age, String hobby, String tel) {
        return myBlogDao.update01(user, introduce, Occupation, age, hobby, tel);
    }

    @Override
    public Integer Insertwz(String title, @RequestParam("filename") MultipartFile[] files, String article_content, HttpSession session) throws IOException {
        String username = session.getAttribute("username").toString();
        MyBlog myBlog = myBlogDao.getmyblog(username);
        String time = Time();
        String path = ResourceUtils.getURL("classpath:").getPath();
        path = path + "upload";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        System.out.println(file);
        String wzfilepath = null;
        String filename = "";
        for (MultipartFile fie : files) {
            long size = fie.getSize();
            System.out.println("size :" + size);
            System.out.println("Empty : " + fie.isEmpty());
            if (!fie.isEmpty()) {
                String uuid = UUID.randomUUID().toString().replace("-","");
                System.out.println(uuid);
                filename = uuid+fie.getOriginalFilename();
                System.out.println(file + File.separator + filename);
//                fie.transferTo(new File("D:/IDEA/img"+ File.separator + filename));
                fie.transferTo(new File("/JavaJar/imgss" + File.separator + filename));   //linux
                wzfilepath = "upload/" + filename;
                System.out.println(file + File.separator + filename);
                return myBlogDao.Insert02(myBlog.getId(), username, time, title, wzfilepath, article_content);
            }
        }
        return myBlogDao.Insert02(myBlog.getId(), username, time, title, wzfilepath, article_content);
    }

    @Override
    public Integer Insertxq(String mood, HttpSession session) {
        String username = session.getAttribute("username").toString();
        MyBlog myblog = myBlogDao.getmyblog(username);
        String time = Time();
        return myBlogDao.Insert03(myblog.getId(), username, time, mood);
    }

    @Override
    public Integer Insertxc(String xcfilepath, HttpSession session) {
        String username = session.getAttribute("username").toString();
        MyBlog myBlog = myBlogDao.getmyblog(username);
        String time = Time();
        return myBlogDao.Insert04(myBlog.getId(), username, time, xcfilepath);
    }

    @Override
    public Myblog01 index(HttpSession session) {
        String username = session.getAttribute("username") == null ? null : session.getAttribute("username").toString();
        if (username == null) { //游客，因为如果登录。会存一个username到session域。
            Myblog01 myblog01 = myBlogDao.getArticle("吴远航", 0, 3);
            myblog01.setUser01(0);
            myblog01.setCount(myBlogDao.getCountWz("吴远航"));
            return myblog01;
        } else {
            Myblog01 myblog01 = myBlogDao.getArticle(username, 0, 3);
            myblog01.setUser01(1);
            myblog01.setCount(myBlogDao.getCountWz(username));
            return myblog01;
        }
    }

    @Override
    public Myblog01 wzpage(String user, Integer currtpage) {
        currtpage = (currtpage - 1) * 3;
        Myblog01 myblog01 = myBlogDao.getArticle(user, currtpage, 3);
        System.out.println(myblog01);
        return myblog01;
    }

    @Override
    public Myblog01 shuo02(HttpSession session) {
        String username = session.getAttribute("username") == null ? null : session.getAttribute("username").toString();
        if (username == null) { //游客，因为如果登录。会存一个username到session域。
            Myblog01 myblog01 = myBlogDao.getMood("吴远航", 0, 3);
            myblog01.setUser01(0);
            myblog01.setCount(myBlogDao.getCountXq("吴远航"));
            return myblog01;
        } else {
            Myblog01 myblog01 = myBlogDao.getMood(username, 0, 3);
            myblog01.setUser01(1);
            myblog01.setCount(myBlogDao.getCountXq(username));
            return myblog01;
        }
    }

    @Override
    public Myblog01 xqpage(String user, Integer currtpage) {
        currtpage = (currtpage - 1) * 3;
        Myblog01 myblog01 = myBlogDao.getMood(user, currtpage, 3);
        System.out.println(myblog01);
        return myblog01;
    }

    @Override
    public Myblog01 getXc(HttpSession session) {
        String username = session.getAttribute("username") == null ? null : session.getAttribute("username").toString();
        if (username == null) { //游客，因为如果登录。会存一个username到session域。
            Myblog01 myblog01 = myBlogDao.getxc("吴远航");
            myblog01.setUser01(0);
            return myblog01;
        } else {
            Myblog01 myblog01 = myBlogDao.getxc(username);
            myblog01.setUser01(1);
            return myblog01;
        }
    }

    @Override
    public Myblog01 getAbout(HttpSession session) {
        String username = session.getAttribute("username") == null ? null : session.getAttribute("username").toString();
        if (username == null) { //游客，因为如果登录。会存一个username到session域。
            Myblog01 myblog01 = myBlogDao.getAbouts("吴远航");
            myblog01.setUser01(0);
            return myblog01;
        } else {
            Myblog01 myblog01 = myBlogDao.getAbouts(username);
            myblog01.setUser01(1);
            return myblog01;
        }
    }

    @Override
    public Myblog01 getfb(HttpSession session) {
        String username = session.getAttribute("username") == null ? null : session.getAttribute("username").toString();
        Myblog01 myblog01 = new Myblog01();
        if (username == null) { //游客，因为如果登录。会存一个username到session域。
            myblog01.setUser01(0);
            myblog01.setUser("吴远航");
            return myblog01;
        } else {
            myblog01.setUser01(1);
            myblog01.setUser(username);
            return myblog01;
        }
    }

    @Override
    public Myblog01 getGuestBook(HttpSession session) {
        //留言未完成
        return null;
    }
}
