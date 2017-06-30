package com.univ.labs;

import com.univ.labs.database.JDBCConnectionProcessor;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

/**
 * Created by Анастасия on 25.06.2017.
 */
public class Generate {
    private static Random random = new Random();

    public static void main(String... args) {
        generateCodes(20);
    }

    private static void generateCodes(int amount) {
        for (int i = 0; i < amount; i++) {
            StringBuilder code = new StringBuilder();
            for (int j = 0; j < 7; j++) {
                int number = random.nextInt(10);
                while (j == 0 && number == 0)
                    number = random.nextInt(10);
                code.append(number);
            }
            int price = random.nextInt(10000);
            insertIntoDatabase(code.toString(), price);
        }
    }

    private static void insertIntoDatabase(String code, int price) {
        try {
            Statement statement = JDBCConnectionProcessor.getConnection().createStatement();
            statement.executeUpdate("INSERT INTO codes (code, amount) VALUES ('" + code + "', '" + price + "');");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCConnectionProcessor.closeConnection();
        }
    }
}