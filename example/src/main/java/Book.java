
public class Book{
    //5 attributes: String title; String author; int yearPublished; String isbn; int quantity
    private String title;
    private String author;
    private int yearPublished;
    private String isbn;
    private int quantity;
    // 1 constructor with 5 arguments that intitialize the attribtues of the class.
    Book(String title, String author, int yearPublished, String isbn, int quantity) {
        this.title = title;
        this.author = author;
        this.yearPublished = yearPublished;
        this.isbn = isbn;
        this.quantity = quantity;
    }
    // Getter/setter methods
    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getAuthor() {return author;}

    public void setAuthor(String author) {this.author = author;}

    public int getYearPublished() {return yearPublished;}

    public void setYearPublished(int yearPublished) {this.yearPublished = yearPublished;}

    public String getIsbn() {return isbn;}

    public void setIsbn(String isbn) {this.isbn = isbn;}

    public int getQuantity() {return quantity;}

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // bookInfo concatenates strings and variables to return info about the Book
    public String bookInfo(){
        return "Title: " + title + ", Author: " + author + ", Year: " + yearPublished + ", ISBN: " + isbn + ", Quantity: " + quantity;
    } //returns "Title: []; Author: []; Year: []; ISBN: []; Quantity: []"
}