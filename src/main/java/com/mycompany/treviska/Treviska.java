package com.mycompany.treviska;

import java.sql.*;

public class Treviska {

    public static void main(String[] args) {

        // ➊ Connection details
        String url  = "jdbc:postgresql://localhost:5432/e-library"; // DB name here
        String user = "postgres";                                   // DB user
        String pass = "01091999";                                   // DB password

        // ➋ Connect, run query, print rows
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement  st   = conn.createStatement();
             ResultSet  rs   = st.executeQuery(
                     "SELECT userid, usernam, email FROM users ORDER BY userid")) {

            System.out.println("✅ Connected to " + conn.getCatalog());

            while (rs.next()) {
                int    id       = rs.getInt   ("userid");
                String username = rs.getString("usernam");
                String email    = rs.getString("email");

                System.out.printf("Row %d → %s | %s%n", id, username, email);
            }
        }
        catch (SQLException e) {
            System.err.println("❌ SQL error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
