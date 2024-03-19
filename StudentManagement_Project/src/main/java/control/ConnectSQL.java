package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectSQL {
    
    // Connect to SQL
    public static Connection getConnection(){
        String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=Management;user=sa;password=0984795609;encrypt=true;trustServerCertificate=true";
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(connectionUrl);
            System.out.println("Done !!!");
        }
        catch(Exception ex){
            System.out.println("Error !!!");
        }
        
        return conn;
    }
    
    // Hàm ngắt kết nối tới SQLSerrver
    public static void closeConnection(Connection c){
        if(c != null){
            try {
                c.close();
            } catch (SQLException ex) {
               ex.printStackTrace();
            }
        }
    }
}
