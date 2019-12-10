package com.qfedu;


import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.jupiter.api.Test;

public class Md5Demo {

    @Test
    public void test_md5(){

        //明文，原始密码
        String password = "123";
        //盐
        String salt = "qwer";
        //散列
        int hashIterations = 2;

        Md5Hash md5Hash1 = new Md5Hash(password);
        System.out.println(md5Hash1.toString());

        Md5Hash md5Hash2 = new Md5Hash(password,salt);
        System.out.println(md5Hash2.toString());

        Md5Hash md5Hash3 = new Md5Hash(password,salt,hashIterations);
        System.out.println(md5Hash3.toString());

    }

    @Test
    public void test_md5_2(){

        //明文，原始密码
        String password = "123";
        //盐
        String salt = "qwer";
        //散列
        int hashIterations = 2;

        SimpleHash simpleHash= new SimpleHash("md5",password,salt,hashIterations);
        System.out.println(simpleHash.toString());

    }
}
