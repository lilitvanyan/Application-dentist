package com.example.dentisst;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBconnector
{
    public Connection databselink;

    public Connection getDatabselink()
    {
        String name = "dentist", user = "root", password = "Lilit.2004";
        String url = "jdbc:mysql://localhost:3306/" + name;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databselink = DriverManager.getConnection(url, user, password);
        } catch(Exception e) {
            System.out.println("error: couln't connect to the server");
            //e.printStackTrace();
        }
        return databselink;
    }
}
