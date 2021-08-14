package com.server.utils;

import java.sql.*;

/**
 * 功能:封装一个JDBC工具类
 * 作者:吴工
 * 时间:2021/8/9 11:31
 * 版本:1.0
 **/
public class JDBCUtil {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/supermarket";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static Connection ct;
    private static PreparedStatement ps;

    //  1.加载驱动:通过反射加载驱动类:因为加载驱动只需要一次，所以放在静态代码块中
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //2.封装一个获得链接的方法
    public static Connection getConnection() {
        Connection ct = null;
        try {
            ct = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ct;
    }

    //3.封装一个增删改的方法，可以完成增删改操作:参数:第一个是sql语句，第二个占位符：
    public static int executeUpdate(String sql, Object... obj) {
        int ri = 0;
        try {
            ct = getConnection();
            ps = ct.prepareStatement(sql);
            //处理占位符
            if (obj != null) {
                for (int i = 0; i < obj.length; i++) {
                    ps.setObject(i + 1, obj[i]);
                }
            }
            ri = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            close(null);
        }
        return ri;
    }

    //4.封装一个方法可以完成查询操作
    public static ResultSet executeQuery(String sql, Object... obj) {
        ResultSet rs = null;
        try {
            ct = getConnection();
            ps = ct.prepareStatement(sql);
            //处理占位符
            if (obj != null) {
                for (int i = 0; i < obj.length; i++) {
                    ps.setObject(i + 1, obj[i]);
                }
            }
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    //封装一个关闭资源的方法 rs,ps,ct
    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (ct != null) {
            try {
                ct.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}




