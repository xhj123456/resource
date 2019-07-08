package com.controller;

import com.dao.MyBlogDao;
import com.service.MyBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Controller
public class Controller01 {
    @Autowired
    MyBlogService myBlogService;    //通过SpringMvc方式注入接口

    @RequestMapping("/")
    public String in() {
        return "dlzc";
    }

    @RequestMapping("/dlzc.html")
    public String index() {
        return "dlzc";
    }

    @RequestMapping("index")
    public ModelAndView index(HttpServletRequest request, ModelAndView mv) {
        HttpSession session = request.getSession();
        mv.setViewName("index");
        mv.addObject("All", myBlogService.index(session));
        return mv;
    }

    @RequestMapping("wzPaging")
    public String wzPaging(Model model, HttpServletRequest request) {
        String user = request.getParameter("user");
        Integer currtpage = Integer.valueOf(request.getParameter("currtpage"));
        model.addAttribute("All", myBlogService.wzpage(user, currtpage));
        return "index :: Wzres";
    }

    @RequestMapping("shuo")
    public ModelAndView shuo(HttpServletRequest request, ModelAndView mv) {
        HttpSession session = request.getSession();
        mv.setViewName("shuo");
        mv.addObject("All", myBlogService.shuo02(session));
        return mv;
    }

    @RequestMapping("xqPaging")
    public String xqPaging(Model model, HttpServletRequest request) {
        String user = request.getParameter("user");
        Integer currtpage = Integer.valueOf(request.getParameter("currtpage"));
        model.addAttribute("All", myBlogService.xqpage(user, currtpage));
        return "shuo :: Xqres";
    }


    @RequestMapping("xc")
    public ModelAndView xc(HttpServletRequest request, ModelAndView mv) throws FileNotFoundException {
        HttpSession session = request.getSession();
        mv.setViewName("xc");
        mv.addObject("All", myBlogService.getXc(session));
        return mv;
    }

    @RequestMapping("abouts")
    public ModelAndView abouts(HttpServletRequest request, ModelAndView mv) {
        HttpSession session = request.getSession();
        mv.setViewName("about");
        mv.addObject("All", myBlogService.getAbout(session));
        return mv;
    }

    @RequestMapping("guestbook")
    public ModelAndView guestbook(HttpServletRequest request, ModelAndView mv) {
        HttpSession session = request.getSession();
        mv.setViewName("guestbook");
//        mv.addObject("All", myBlogService.getGuestBook(session));
        mv.addObject("All", myBlogService.getfb(session));
        return mv;
    }

    @RequestMapping("fb")
    public ModelAndView fb(HttpServletRequest request, ModelAndView mv) {
        HttpSession session = request.getSession();
        mv.setViewName("fb");
        mv.addObject("All", myBlogService.getfb(session));
        return mv;
    }

    @RequestMapping("upload")
    public String upload(@RequestParam("filename") MultipartFile[] files, HttpServletRequest request) throws Exception {
        String path = ResourceUtils.getURL("classpath:").getPath(); //获取到resources路径
        path = path + "upload";//得到上传文件的保存目录，不允许外界直接访问，保证上传文件的安全
        File file = new File(path);
        if (!file.exists()) {   //判断文件保存目录是否存在
            file.mkdirs();
        }
        String file1 = "";
        for (MultipartFile file2 : files) {     //遍历要上传的文件
            if (!file2.isEmpty()) {
                String uuid = UUID.randomUUID().toString().replace("-", "");
                file1 =uuid+file2.getOriginalFilename(); //得到上传的文件名。没有路径

                System.out.println("/JavaJar/imgss"  + File.separator + file1);
                file2.transferTo(new File("/JavaJar/imgss"  + File.separator + file1));


      //          file2.transferTo(new File("D:/IDEA/img" + File.separator + file1));
                System.out.println(new File("/JavaJar/imgss"  + File.separator + file1).getPath());
                HttpSession session = request.getSession();
                myBlogService.Insertxc("upload/" + file1, session);
            }
        }
        return "redirect:xc";
    }

    @RequestMapping("sub1")
    public String sub1(@RequestParam("filename") MultipartFile[] files, HttpServletRequest request) throws IOException {
        String title = request.getParameter("title");
        String article_content = request.getParameter("Article_content");
        HttpSession session = request.getSession();
        myBlogService.Insertwz(title, files, article_content, session);
        return "redirect:index";
    }
}
