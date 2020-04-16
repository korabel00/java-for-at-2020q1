package com.epam.ilia_solovev.java.lesson6_sql.task1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateDB {

    private static final String URL = "jdbc:sqlserver://localhost:1433";
    private static final String INSTANCE = "instance=SQLEXPRESS";
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String DB_NAME = "VEpamke";
    private static final String CREDENTIALS = "integratedSecurity=true";

    public static void createDBAndTables() {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName(DRIVER);
            connection = getConnection();

            preparedStatement = createDB(connection);
            preparedStatement.execute();

            preparedStatement = createTableUsers(connection);
            preparedStatement.execute();

            preparedStatement = createTableFriendships(connection);
            preparedStatement.execute();

            preparedStatement = createTablePosts(connection);
            preparedStatement.execute();

            preparedStatement = createTableLikes(connection);
            preparedStatement.execute();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static Connection getConnection() throws SQLException {
        System.out.println(URL + ";" + INSTANCE + ";" + CREDENTIALS);
        return DriverManager.getConnection(URL + ";" + INSTANCE + ";" + CREDENTIALS);
    }


    private static PreparedStatement createDB(Connection connection) throws SQLException {
        return connection.prepareStatement("IF DB_ID('" + DB_NAME + "') IS NULL " +
                "CREATE DATABASE " + DB_NAME);
    }

    // Users (id, name, surname, birthdate)
    private static PreparedStatement createTableUsers(Connection connection) throws SQLException {

        String tableName = "Users";

        return connection.prepareStatement("USE " + DB_NAME + "; " +
                "IF OBJECT_ID('" + tableName + "') IS NULL " +
                "CREATE TABLE " + tableName + "(" +
                "ID int IDENTITY(1,1) PRIMARY KEY, " +
                "Name varchar(20), " +
                "Surname varchar(30), " +
                "Birthdate date);");
    }

    // Friendships (userid1, userid2,timestamp)
    private static PreparedStatement createTableFriendships(Connection connection) throws SQLException {

        String tableName = "Friendships";

        return connection.prepareStatement("USE " + DB_NAME + "; " +
                "IF OBJECT_ID('" + tableName + "') IS NULL " +
                "CREATE TABLE " + tableName + "(" +
                "UserId1 int, " +
                "UserId2 int, " +
                "Timestamp datetime, " +
                "FOREIGN KEY(UserId1) REFERENCES Users (ID));");
    }

    // Posts(id, userId, text, timestamp)
    private static PreparedStatement createTablePosts(Connection connection) throws SQLException {

        String tableName = "Posts";

        return connection.prepareStatement("USE " + DB_NAME + "; " +
                "IF OBJECT_ID('" + tableName + "') IS NULL " +
                "CREATE TABLE " + tableName + "(" +
                "ID int PRIMARY KEY, " +
                "UserId int, " +
                "Text varchar(255), " +
                "Timestamp datetime, " +
                "FOREIGN KEY(UserId) REFERENCES Users (ID));");
    }

    // Likes (postid, userid, timestamp)
    private static PreparedStatement createTableLikes(Connection connection) throws SQLException {

        String tableName = "Likes";

        return connection.prepareStatement("USE " + DB_NAME + "; " +
                "IF OBJECT_ID('" + tableName + "') IS NULL " +
                "CREATE TABLE " + tableName + "(" +
                "PostId int PRIMARY KEY, " +
                "UserId int, " +
                "Timestamp datetime, " +
                "FOREIGN KEY(UserId) REFERENCES Users (ID));");
    }
}

