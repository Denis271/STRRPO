import java.sql.*;

import java.text.ParseException;


public class DB {
    private Connection connection;
    public  DB() throws SQLException, ClassNotFoundException {
        this.connGet();
    }
    public void connGet () throws SQLException, ClassNotFoundException {
//        String dbUser = "host1834644";
//        String dbPass = "QDaBYEi4Tj";
//        String dbName = "host1834644";
//        String connUrl = "jdbc:mysql://localhost:3306/host1834644";
        String dbUser = "root";
        String dbPass = "";
        String dbName = "javadb";
        String connUrl = "jdbc:mysql://127.0.0.1:3306/javadb";
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.connection = DriverManager.getConnection(connUrl,dbUser,dbPass);
    }

    public void createPersonal(String name, String surname, String email) throws SQLException, ParseException {


        PreparedStatement statement = this.connection.prepareStatement("INSERT INTO user (id, name, surname, email) VALUES (NULL,?,?,?)");
        statement.setString(1,name);
        statement.setString(2,surname);
        statement.setString(3,email);


        statement.execute();
    }
}