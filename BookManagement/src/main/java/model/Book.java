
package model;

import java.util.Date;


public class Book {
   private int id;
   private String bookCode;
   private String bookName;
   private String bookTitle;
   private String bookDesc;
   private String bookType;
   private double bookPrice;
   private String createdAt;
   private String updatedAt;


    public Book(int id, String bookCode, String bookName, String bookTitle, String bookDesc, String bookType, double bookPrice, String createdAt, String updatedAt) {
        this.id = id;
        this.bookCode = bookCode;
        this.bookName = bookName;
        this.bookTitle = bookTitle;
        this.bookDesc = bookDesc;
        this.bookType = bookType;
        this.bookPrice = bookPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookDesc() {
        return bookDesc;
    }

    public void setBookDesc(String bookDesc) {
        this.bookDesc = bookDesc;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", bookCode=" + bookCode + ", bookName=" + bookName + ", bookTitle=" + bookTitle + ", bookDesc=" + bookDesc + ", bookType=" + bookType + ", bookPrice=" + bookPrice + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
   
    
}
