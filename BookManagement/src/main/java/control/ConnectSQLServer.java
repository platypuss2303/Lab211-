
package control;

import java.sql.Connection;
import java.sql.DriverManager;



public class ConnectSQLServer {
    // Connection
    public static Connection getConnection(){
        String url = "jdbc:sqlserver://localhost:1433;databaseName=BookManagement;user=sa;password=0984795609;encrypt=true;trustServerCertificate=true";
        Connection connect = null;
        try {
            connect = DriverManager.getConnection(url);
            System.out.println("Connect successfully !");
        } catch (Exception e) {
            System.out.println("Error !!");
        }
        return connect;
    }
    
    // Close
    public static void closeConnection(Connection connect){
        if( connect != null ){
            try {
                connect.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
}
