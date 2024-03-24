package view;

import control.ConnectSQLServer;
import control.ManagementFunction;
import static java.lang.System.exit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.Inputter;

public class Menu {

    public void menu() {
        ManagementFunction mf = new ManagementFunction();
        mf.setConn(ConnectSQLServer.getConnection());
        try {
            Statement st = mf.getConn().createStatement();
            String sql = "select * from Menu";
            ResultSet rs = st.executeQuery(sql);
            //ArrayList<String> arr = new ArrayList<>();
            while (rs.next()) {
                System.out.println(rs.getString("functions"));
                //arr.add(s);
            }
            while (true) {
                int choice = Inputter.GetInt("Enter your choice: ");
                switch (choice) {
                    case 1:
                        mf.createBook();
                        break;
                    case 2:
                        mf.updateBook();
                        break;
                    case 3:
                        mf.findBook();
                        break;
                    case 4:
                        mf.viewAll();
                        break;
                    case 5:
                        mf.loadDataToArrayList();
                        mf.exportXLSX();
                        break;
                    default:
                        exit(0);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Menu m = new Menu();
        m.menu();
    }
}
