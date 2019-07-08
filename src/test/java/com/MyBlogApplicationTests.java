package com;

import com.dao.MyBlogDao;
import com.service.Imp.MyBlogServiceImp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.GeneralSecurityException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MyBlogApplicationTests {
	@Autowired
	MyBlogDao myBlogDao;

	@Test
	public void contextLoads() throws MessagingException, GeneralSecurityException {

	}
}
