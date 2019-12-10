/**
 * 自定义工具类
 *
 * @author 刘鹏尧
 * @date
 */
package com.qfedu.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyUtils {
    /**
     * 获取时间函数
     * @return
     */
    public static String getTime() {
        Date d = new Date();
        System.out.println(d);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        System.out.println("格式化后的日期：" + dateNowStr);
        return dateNowStr;
    }

    /**
     * 获取6位随机数
     * @return
     */
    public static int getRandom() {
        return (int)((Math.random()*9+1)*100000);
    }

    public static void main(String[] args) {
        System.out.println(getTime());
        System.out.println(getRandom());
    }

}
