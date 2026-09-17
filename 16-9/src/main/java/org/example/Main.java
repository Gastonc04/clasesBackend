package org.example;

import java.sql.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        // Antiguamente se hacia asi para cargar el driver de la BD
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:tcp:localhost", "sa", "");

            // Permite ejecutar operaciones en la BD
            Statement statement = connection.createStatement();

            // Selecciono segun la operacion
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Test");
            while(resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}