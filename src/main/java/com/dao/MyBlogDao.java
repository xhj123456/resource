package com.dao;


import com.pojo.MyBlog;
import com.pojo.Myblog01;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MyBlogDao {
    @Select(value = " select id,user,password from myblog where user=#{user};")
    MyBlog getmyblog(String user);//查询用户名和密码

    @Insert(value = "insert into myblog(user,password,email) value (#{user},#{password},#{email})")
    Integer Insert01(String user, String password, String email);//用户注册成功，存储信息

    @Update(value = "update myblog set introduce=#{introduce},Occupation=#{Occupation}, age=#{age}, hobby=#{hobby},telephonenumber=#{tel} where user=#{user}")
    Integer update01(String user, String introduce, String Occupation, String age, String hobby, String tel);//修改个人信息

    @Insert(value = "insert into wz(uid,w_user,time,title,wzfilepath,article_content) value(#{id},#{username},#{time},#{title},#{wzfilepath},#{article_content})")
    Integer Insert02(Integer id, String username, String time, String title, String wzfilepath, String article_content); //插入文章

    @Insert(value = "insert into xq(uid,xq_user,mood,time) value(#{id},#{user},#{mood},#{time})")
    Integer Insert03(Integer id, String user, String time, String mood); //插入心情

    @Insert(value = "insert into xc(uid,xc_user,time,xcfilepath) value(#{id},#{username},#{time},#{xcfilepath})")
    Integer Insert04(Integer id, String username, String time, String xcfilepath);  //插入图片路径

    Myblog01 getArticle(String user, Integer currtpage, Integer pagesize);    //返回初始页数文章

    Myblog01 getMood(String user, Integer currtpage, Integer pagesize);      //返回心情

    Myblog01 getxc(String user);    //查询所有相册路径

    @Select(value = "select m.user,m.hobby,m.introduce,m.age,m.occupation,m.telephonenumber from myblog m where m.user=#{username}")
    Myblog01 getAbouts(String username);    //查询个人信息

    @Select(value = "select count(*) from wz where w_user=#{username}")
    Integer getCountWz(String username);    //返回用户文章总条数

    @Select(value = "select count(*) from xq where xq_user=#{username}")
    Integer getCountXq(String username);    //返回用户心情总条数
}
