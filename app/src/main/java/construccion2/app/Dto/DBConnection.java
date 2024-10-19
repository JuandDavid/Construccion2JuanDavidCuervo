package App.Dto;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import App.Model.User;

@Getter
@Setter
@NoArgsConstructor
public class PartnerInvoiceAmountDto {
    private long id;
    private User userId;
    private double amount;
    private String type;
    private Timestamp creationDate;
    private double invoiceAmount;
}
Clase Eliminada 
CONFIG
package app.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/club";
    private static final String USER = "weazel";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa \n");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
