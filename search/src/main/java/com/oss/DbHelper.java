package com.oss;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by burcu on 25.12.2017.
 */
public class DbHelper {

    static int lastSelectionId = 1;
    Connection conn = null;

    // database connection
    public DbHelper() throws ClassNotFoundException, SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/oss_repos?verifyServerCertificate=false&useSSL=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "qweasdqwe");
            System.out.println("Bağlantı başarılı.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // select statements limit 1000 row for all request
    public ArrayList<String> selectRows (String pl) {
        ArrayList<String> datas = null;


       // lastSelectionId  = lastRepoId
        return datas;
    }
 }
