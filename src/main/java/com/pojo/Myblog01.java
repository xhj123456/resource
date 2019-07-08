package com.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Myblog01 {
    private Integer user01;     //通过01来判断吴远航本人是否登录
    private Integer count;
    private Integer id;
    private String user;
    private String introduce;
    private String hobby;
    private String age;
    private String Occupation;
    private String telephonenumber;
    private List<Article> articles;
    private List<Mood> moods;
    private List<Xc> xcs;
}
