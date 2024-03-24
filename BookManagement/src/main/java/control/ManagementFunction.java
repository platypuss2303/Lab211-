package control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.System.exit;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Book;
import model.Inputter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ManagementFunction {

    ArrayList<Book> bookList = new ArrayList<>();
    Connection connect;

    public Connection getConn() {
        return connect;
    }

    public void setConn(Connection connect) {
        this.connect = connect;
    }

    // Creat a book
    public void createBook() {
        try {
            Statement st = connect.createStatement();
            String bookCode = checkBookCode();
            if (bookCode == null) {
                System.out.println("Book code is duplicate !");
                return;
            }
            String bookName = Inputter.GetString("Enter book name: ");
            String bookTitle = Inputter.GetString("Enter book title: ");
            String bookDesc = Inputter.GetString("Enter book description: ");
            String bookType = Inputter.GetString("Enter book type: ");
            double bookPrice = Inputter.GetDouble("Enter book price: ");
            String createdAt = Inputter.GetString("Enter create date: ");
            String insertBook = "insert into Book(bookCode, bookName, bookTitle, bookDesc, bookType, bookPrice, createdAt) values ('" + bookCode + "', '" + bookName + "', '" + bookTitle + "', '" + bookDesc + "', '" + bookType + "', " + bookPrice + ", '" + createdAt + "')";
            st.executeUpdate(insertBook);
            System.out.println("Done !!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String checkBookCode() {
        String bookCode = Inputter.GetString("Enter book code: ");
        try {
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery("select * from Book where bookCode = '" + bookCode + "'");
            if (rs.next() == false) {
                return bookCode;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update book
    public void updateBook() {
        try {
            Statement st = connect.createStatement();
            String bookCode;
            while (true) {
                bookCode = findBookCode();
                if (bookCode == null) {
                    System.out.println("Book code doesn't exist !");
                } else {
                    break;
                }

            }
            String bookName = Inputter.GetString("Enter book name: ");
            String bookTitle = Inputter.GetString("Enter book title: ");
            String bookDesc = Inputter.GetString("Enter book description: ");
            String bookType = Inputter.GetString("Enter book type: ");
            double bookPrice = Inputter.GetDouble("Enter book price: ");
            String updatedAt = Inputter.GetString("Enter updated date: ");
            // String updateBook = "update Book set bookName = '" + bookName + "', bookTitle = '" + bookTitle + "', bookDesc = '" + bookDesc + "', bookType = '" + bookType + "', bookPrice = " + bookPrice + ", updatedAt = '" + updatedAt + "' where bookCode = '" + bookCode + "'";
            String updateBook = "UPDATE Book SET "
                    + "bookName = '" + bookName + "', "
                    + "bookTitle = '" + bookTitle + "', "
                    + "bookDesc = '" + bookDesc + "', "
                    + "bookType = '" + bookType + "', "
                    + "bookPrice = " + bookPrice + ", "
                    + "updatedAt = '" + updatedAt + "' "
                    + "WHERE bookCode = '" + bookCode + "'";
            st.executeUpdate(updateBook);
            System.out.println("Update successfully !");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String findBookCode() {
        String bookCode = Inputter.GetString("Enter book code: ");
        try {
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery("select * from Book where bookCode = '" + bookCode + "'");
            if (rs.next() == true) {
                return bookCode;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Find book with name/title
    public Book findBook() {
        System.out.println("1. Find w name");
        System.out.println("2. Find w title");
        System.out.println("Choose another number to exit !");
        try {
            Statement st = connect.createStatement();
            int choice = Inputter.GetInt("Enter your choice: ");
            switch (choice) {
                case 1: {
                    String bookName = Inputter.GetString("Enter book name: ");
                    ResultSet rs = st.executeQuery("select * from Book where bookName = '" + bookName + "'");
                    if (rs.next() == false) {
                        System.out.println("Book name doesn't exist !");
                        return null;
                    }
                    System.out.println(rs.getString("bookName") + " " + rs.getString("bookCode") + " " + rs.getString("bookTitle") + " " + rs.getString("bookDesc") + " " + rs.getString("bookType") + " " + rs.getDouble("bookPrice"));

                }
                case 2: {
                    String bookTitle = Inputter.GetString("Enter book title: ");
                    ResultSet rs = st.executeQuery("select * from Book where bookTitle = '" + bookTitle + "'");
                    if (rs.next() == false) {
                        System.out.println("Book title doesn't exist !");
                        return null;
                    }
                    System.out.println(rs.getString("bookTitle") + " " + rs.getString("bookCode") + " " + rs.getString("bookName") + " " + rs.getString("bookDesc") + " " + rs.getString("bookType") + " " + rs.getDouble("bookPrice"));

                }
                default:
                    exit(0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // View all w paging 
    public void viewAll() {
        int pageIndex = Inputter.GetInt("Enter number of pages: ");
        int pageNum = Inputter.GetInt("Enter number of records: ");
        String callProc = "{call Paging(?, ?)}";
        try {
            CallableStatement cst = connect.prepareCall(callProc);
            cst.setInt(1, pageIndex);
            cst.setInt(2, pageNum);
            ResultSet rs = cst.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("bookTitle") + " " + rs.getString("bookCode") + " " + rs.getString("bookName") + " " + rs.getString("bookDesc") + " " + rs.getString("bookType") + " " + rs.getDouble("bookPrice"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Export data 
    public void loadDataToArrayList() {
        try {
            Statement st = connect.createStatement();
            String sql = "select * from Book";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String bookCode = rs.getString("bookCode");
                String bookName = rs.getString("bookName");
                String bookTitle = rs.getString("bookTitle");
                String bookDesc = rs.getString("bookDesc");
                String bookType = rs.getString("bookType");
                double bookPrice = rs.getDouble("bookPrice");
                String createdAt = rs.getString("createdAt");
                String updatedAt = rs.getString("updatedAt");
                Book book = new Book(id, bookCode, bookName, bookTitle, bookDesc, bookType, bookPrice, createdAt, updatedAt);
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
  

    public void exportXLSX() {
        try {
            XSSFWorkbook workBook = new XSSFWorkbook();
            XSSFSheet sheet = workBook.createSheet("BookManagement");
            XSSFRow row = null;
            Cell cell = null;
            
            row = sheet.createRow(1); 
            cell = row.createCell(0, CellType.NUMERIC);
            cell.setCellValue("id");

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("bookCode");

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("bookName");

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("bookTitle");

            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("bookDesc");

            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("bookType");

            cell = row.createCell(6, CellType.NUMERIC);
            cell.setCellValue("bookPrice");

            cell = row.createCell(7, CellType.STRING);
            cell.setCellValue("createdAt");

            cell = row.createCell(8, CellType.STRING);
            cell.setCellValue("updatedAt");

            for (int i = 0; i < bookList.size(); i++) {
                row = sheet.createRow(i+2);

                cell = row.createCell(0, CellType.NUMERIC);
                cell.setCellValue(bookList.get(i).getId());

                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(bookList.get(i).getBookCode());

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(bookList.get(i).getBookName());

                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(bookList.get(i).getBookTitle());

                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(bookList.get(i).getBookDesc());

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(bookList.get(i).getBookType());

                cell = row.createCell(6, CellType.NUMERIC);
                cell.setCellValue(bookList.get(i).getBookPrice());

                cell = row.createCell(7, CellType.STRING);
                cell.setCellValue(bookList.get(i).getCreatedAt());

                cell = row.createCell(8, CellType.STRING);
                cell.setCellValue(bookList.get(i).getUpdatedAt());
            }
            File f = new File("book.xlsx");
            try {
                FileOutputStream file = new FileOutputStream(f);
                workBook.write(file);
                file.close();
                workBook.close();
                System.out.println("Export successfully !");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            
            

        } catch (IOException e) {
            
        }
    }

   
    public static void main(String[] args) throws SQLException {
        ManagementFunction m = new ManagementFunction();
        m.connect = ConnectSQLServer.getConnection();
        Statement st = m.connect.createStatement();
        m.loadDataToArrayList();
        m.exportXLSX();
    }
}
