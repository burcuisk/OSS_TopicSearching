package com.oss;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // select statements range of 1000 id rows for all request
    public ArrayList<ArrayList<String>> selectRowsGithub (String pl) {
        System.out.println("database geldik : sorgu yapcaz");
        ArrayList<ArrayList<String>> datas = new ArrayList<ArrayList<String>>();
        ArrayList<String> repo;
        System.out.println(lastSelectionId);
        String query = "SELECT * FROM github_repository where pl=\""+pl+"\" LIMIT " + lastSelectionId + "," + 1000;

        Statement st = null;
        try {
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                repo = new ArrayList<String>();
                int id_g = rs.getInt("id");
                String repoUrl = rs.getString("url");
                String description = rs.getString("description");
                String topics = rs.getString("topics");

                if (topics != null)
                    description += " " +topics;

                repo.add(Integer.toString(id_g));
                repo.add(repoUrl);
                repo.add(description);

                datas.add(repo);
            }
            lastSelectionId +=1000;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(st != null)
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return datas;
    }

    // select statements limit 1000 row for all request
    public ArrayList<String> selectRowsSourceForge () {
        ArrayList<String> datas = null;


        // lastSelectionId  = lastRepoId
        return datas;
    }
 }
