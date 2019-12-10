package com.qfedu;


import com.qfedu.pojo.Student;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class freemarkerDemo {

    @Test
    public  void test_freemarker()throws Exception{
        //获取配置对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //设置字符集
        configuration.setDefaultEncoding("utf-8");
        //设置加载的模版目录
        configuration.setDirectoryForTemplateLoading(new File("D:/ftl"));
        //使用map集合加载数据
        HashMap<String, String> map = new HashMap<>();
        map.put("hello","hello freemarker");
        //创建输出流对象
        FileWriter fileWriter = new FileWriter(new File("D:/ftl/ftl_html/hello.html"));
        //获取加载的模板
        Template template = configuration.getTemplate("hello.ftl");
        //生成html文件
        template.process(map,fileWriter);
        //关流
        fileWriter.close();
    }


    @Test
    public  void test_freemarker_student()throws Exception{
        //获取配置对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //设置字符集
        configuration.setDefaultEncoding("utf-8");
        //设置加载的模版目录
        configuration.setDirectoryForTemplateLoading(new File("D:/ftl"));
        //创建List集合获取多个元素
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student(1001,"jack",18,"郑州二七"));
        students.add(new Student(1002,"rose",19,"郑州中原"));
        students.add(new Student(1003,"tom",20,"郑州金水"));
        //使用map集合加载数据
        HashMap<String,ArrayList> map = new HashMap<>();
        map.put("stuList",students);
        //创建输出流对象
        FileWriter fileWriter = new FileWriter(new File("D:/ftl/ftl_html/student.html")); //文件夹不存在会报错
        //获取加载的模板
        Template template = configuration.getTemplate("student.ftl");
        //生成html文件
        template.process(map,fileWriter);
        //关流
        fileWriter.close();
    }
}
