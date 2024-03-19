
package control;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.Inputter;

import model.Student;
import model.Teacher;

public class Management {

    Connection conn;

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    // Hàm login
    public Teacher login() {
        try {
            Statement st = conn.createStatement();
            ResultSet rs;
            while (true) {
                String userName = Inputter.GetString("Enter username : ");
                String passWord = Inputter.GetString("Enter password");
                String sql = "SELECT * FROM Teacher WHERE giaoVienID = '" + userName + "' AND matKhau = '" + passWord + "';";
                rs = st.executeQuery(sql);
                if (rs.next() == true) {
                    break;
                }
                System.out.println("Invalid Username Or Invalid Password!");
            }
            //
            Teacher t = new Teacher(rs.getInt("giaoVienId"),
                    rs.getString("hoTen"), rs.getString("diaChi"),
                    rs.getString("matKhau"), rs.getString("soDienThoai"),
                    rs.getString("monHoc"));
            return t;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Hàm logout
    public void logout(Teacher t) {
        System.out.println("You are log out !");
        t = login();
    }

    // Hàm tạo Account Admin
    public void createAdmin() {
        try {
            Statement st = conn.createStatement();
            int id = searchTeacher();
            if (id == -1) {
                System.out.println("Invalid ID");
                return;
            }
            String password = Inputter.GetString("Enter password : ");
            String name = Inputter.GetString("Enter fullname: ");
            String address = Inputter.GetString("Enter address: ");
            String phoneNumber = Inputter.getPhoneNumber("Enter phone number: ");
            String sql = "INSERT INTO Teacher "
                    + "(giaoVienID, hoTen, diaChi, matKhau, soDienThoai, monHoc, isADmin)"
                    + "VALUES "
                    + "(" + id + ", " + name + ", " + address + ", " + password + ", " + phoneNumber + ", NULL, " + 1 + ");";
            st.executeUpdate(sql);
            System.out.println("Done !!!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void createTeacher() {
        Statement st;
        try {
            st = conn.createStatement();
            int id = searchTeacher();
            if (id == -1) {
                System.out.println("ID không hợp lệ.");
                return;
            }
            String hoTen = Inputter.GetString("Enter lecturer's name: ");
            String diaChi = Inputter.GetString("Enter lecturer's address: ");
            String matKhau = Inputter.GetString("Enter password: ");
            String soDienThoai = Inputter.getPhoneNumber("Enter phone number: ");
            String monHoc = Inputter.GetString("Enter major: ");
            int isADmin = Inputter.getBit("Admin accept: ");
            String newSql = "INSERT INTO Teacher"
                    + " (giaoVienID, hoTen, diaChi, matKhau, soDienThoai, monHoc, isADmin) "
                    + "VALUES (" + id + ", '" + hoTen + "', '" + diaChi + "', '" + matKhau + "', '" + soDienThoai + "', '" + monHoc + "', " + isADmin + ");";
            st.executeUpdate(newSql);
            System.out.println("Done !!!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void createStudent() {
        try {
            Statement st = conn.createStatement();
            int id = findStudent();
            if (id == -1) {
                System.out.println("Id đã tồn tại !");
                return;
            };
            String hoTen = Inputter.GetString("Enter student's name: ");
            String diaChi = Inputter.GetString("Enter student's address: ");
            String soDienThoai = Inputter.getPhoneNumber("Enter phone number: ");
            int giaoVienID = Inputter.GetInt("Enter lecturer's ID: ");
            String newSql = "INSERT INTO Student"
                    + " (ID, hoVaTen, diaChi, soDienThoai, giaoVienID) "
                    + "VALUES (" + id + ", '" + hoTen + "', '" + diaChi + "', '" + soDienThoai + "', " + giaoVienID + ");";
            st.executeUpdate(newSql);
            System.out.println("Done !!!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteStudent() {
        try {
            Statement st = conn.createStatement();
            Student s = search();
            if (s == null) {
                System.out.println("Invalid ID.");
                return;
            }
            int id = s.getID();
            String sql = "DELETE Student WHERE ID = " + id + ";";
            st.executeUpdate(sql);
            System.out.println("Removed !");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteTeacher() {
        try {
            Statement st = conn.createStatement();
            Teacher t = findTeacher();
            if (t == null) {
                System.out.println("Invalid ID");
                return;
            }
            int id = t.getGiaoVienID();
            String sql = "DELETE Teacher WHERE giaoVienID = " + id + ";";
            st.executeUpdate(sql);
            System.out.println("Removed !");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void editTeacher() {
        try {
            Statement st = conn.createStatement();
            Teacher t = findTeacher();
            if (t == null) {
                System.out.println("Invalid ID");
                return;
            }
            int id = t.getGiaoVienID();
            String hoTen = Inputter.GetString("Enter lecturer's name: ");
            String diaChi = Inputter.GetString("Enter lecturer's address: ");
            String matKhau = Inputter.GetString("Enter password: ");
            String soDienThoai = Inputter.getPhoneNumber("Enter phone number: ");
            String monHoc = Inputter.GetString("Enter major: ");
            String isADmin = Inputter.GetString("Admin acception: ");
            String sql = "UPDATE Teacher "
                    + "SET hoTen = '" + hoTen + "', "
                    + "diaChi = '" + diaChi + "', "
                    + "matKhau = '" + matKhau + "', "
                    + "soDienThoai = '" + soDienThoai + "', "
                    + "monHoc = '" + monHoc + "', "
                    + "isADmin = '" + isADmin + "';";
            st.executeUpdate(sql);
            System.out.println("Edited !");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void editStudent() {
        try {
            Statement st = conn.createStatement();
            Student s = search();

            if (s == null) {
                System.out.println("Invalid");
                return;
            }
            int id = s.getID();
            String hoTen = Inputter.GetString("Enter student's name: ");
            String diaChi = Inputter.GetString("Enter student's address: ");
            String soDienThoai = Inputter.getPhoneNumber("Enter phone number: ");
            int giaoVienID = Inputter.GetInt("Enter lecturer's ID: ");
            String sql = "UPDATE Student "
                    + "SET hoVaTen = '" + hoTen + "', "
                    + "diaChi = '" + diaChi + "', "
                    + "soDienThoai = '" + soDienThoai + "', "
                    + "giaoVienID = " + giaoVienID + " WHERE ID = " + id + ";";
            st.executeUpdate(sql);
            System.out.println("Edited !");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Student search() {
        System.out.println("1. Find by student's ID");
        System.out.println("2. Find by student's name");
        System.out.println("3. FInd by lecturer's ID");
        System.out.println("Choose another number to ");
        try {
            Statement st = conn.createStatement();
            int choice = Inputter.GetInt("Your choice: ");
            switch (choice) {
                case 1:
                    ResultSet rs1 = st.executeQuery("SELECT * FROM Student WHERE ID = " + Inputter.GetInt("Enter student's ID: ") + ";");
                    if (rs1.next() == false) {
                        return null;
                    }
                    System.out.println(rs1.getInt("ID") + " " + rs1.getString("hoVaTen") + " " + rs1.getString("diaChi") + " " + rs1.getString("soDienThoai") + " " + rs1.getInt("giaoVienID"));
                    Student st1 = new Student(rs1.getInt("ID"), rs1.getString("hoVaTen"), rs1.getString("diaChi"), rs1.getString("soDienThoai"), rs1.getInt("giaoVienID"));
                    return st1;
                case 2:
                    ResultSet rs2 = st.executeQuery("SELECT * FROM Student WHERE hoVaTen = '" + Inputter.GetString("Enter student's name: ") + "';");
                    Student st2 = null;
                    while (rs2.next()) {
                        System.out.println(rs2.getInt("ID") + " " + rs2.getString("hoVaTen") + " " + rs2.getString("diaChi") + " " + rs2.getString("soDienThoai") + " " + rs2.getInt("giaoVienID"));
                        st2 = new Student(rs2.getInt("ID"), rs2.getString("hoVaTen"), rs2.getString("diaChi"), rs2.getString("soDienThoai"), rs2.getInt("giaoVienID"));
                        return st2;
                    }

                case 3:
                    ResultSet rs3 = st.executeQuery("SELECT * FROM Student WHERE ID = " + Inputter.GetInt("Enter lecturer's ID: ") + ";");
                    Student st3 = null;
                    while (rs3.next()) {
                        System.out.println(rs3.getInt("ID") + " " + rs3.getString("hoVaTen") + " " + rs3.getString("diaChi") + " " + rs3.getString("soDienThoai") + " " + rs3.getInt("giaoVienID"));
                        st3 = new Student(rs3.getInt("ID"), rs3.getString("hoVaTen"), rs3.getString("diaChi"), rs3.getString("soDienThoai"), rs3.getInt("giaoVienID"));
                    }
                    return st3;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Teacher findTeacher() {
        int id = Inputter.GetInt("Enter student's ID: ");
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Teacher WHERE giaoVienID = " + id + ";");
            boolean b = rs.next();
            if (b == false) {
                return null;
            }
            Teacher t = new Teacher(rs.getInt("giaoVienID"), rs.getString("hoTen"), rs.getString("diaChi"), rs.getString("matKhau"), rs.getString("soDienThoai"), rs.getString("monHoc"));
            return t;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int findStudent() {
        try {
            Statement st = conn.createStatement();
            int id = Inputter.GetInt("Enter student's ID: ");
            ResultSet rs1 = st.executeQuery("SELECT * FROM Student WHERE ID = " + id + ";");
            if (rs1.next() == false) {
                return id;
            }
            System.out.println(rs1.getInt("ID") + " " + rs1.getString("hoVaTen") + " " + rs1.getString("diaChi") + " " + rs1.getString("soDienThoai") + " " + rs1.getInt("giaoVienID"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public int searchTeacher() {
        int id = Inputter.GetInt("Enter ID: ");
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Teacher WHERE giaoVienID = " + id + ";");
            if (rs.next() == false) {
                return id;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public static void main(String[] args) throws SQLException {
       Management sm = new Management();
        sm.conn = control.ConnectSQL.getConnection();
        Statement st = sm.conn.createStatement();
        sm.createAdmin();
    }
}
