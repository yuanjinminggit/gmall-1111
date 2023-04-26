package com.atguigu.test;

import com.atguigu.mapper.UserMapper;
import com.atguigu.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserMapperTest {
    static SqlSessionFactory sqlSessionFactory;
    @Before
    public void setUp() throws Exception {
        sqlSessionFactory= new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
    }

    public void queryone(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user = mapper.queryUserById(1);
            System.out.println(user);
            System.out.println(mapper.queryUserById(1));
        } finally {
            sqlSession.close();
        }
    }
    @Test
    public void firstCacheFail(){
        queryone();
        queryone();
    }
    @Test
    public void firstCacheFail2(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user = mapper.queryUserById(1);
            sqlSession.clearCache();
            System.out.println(mapper.queryUserById(1));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void firstCacheFail3(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user = mapper.queryUserById(1);
            mapper.insertUser(user);
            System.out.println(mapper.queryUserById(1));
        } finally {
            sqlSession.close();
        }
    }


}