
public class User{
    //requires 3 private attributes String name, String Id, Book[] book that is initialized to empty
    private String name;
    private String Id;
    private Book[] books = new Book[5];

    //requires 1 contructor with two parameters that will initialize the name and id
    public User(String name, String Id) {
        this.name = name;
        this.Id = Id;
    }

    // ## GETTERS AND SETTERS ##

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getId() {return Id;}

    public void setId(String Id) {this.Id = Id;}

    public Book[] getBooks() {return books;}

    public void setBooks(Book[] books) {this.books = books;}

    // returns the info/booklist about the books the user owns, if no book exists at the index, add "empty"
    public String bookListInfo(){
        String info = "";
        for (Book book : books) {
            if (book == null) {
                info += "empty";
            } else {
                // Use helper method bookInfo in order to append book info like isbn, author, etc
                info += book.bookInfo();
            }
            info += "\n";
        }
        return info;
    } 

    public void removeBook(Book book) {
        for (int i = 0; i < books.length; i++) {
            // Ensure books[i] is not null before checking if a method exists on it
            if (books[i] != null) {
                // If the given user to remove matches the current user's ID they are the same user so remove it
                if (books[i] == book) {
                    // First check if the quantity is greater than 1
                    if (books[i].getQuantity() > 1) {
                        // Then, decrease the quantity by 1
                        books[i].setQuantity(books[i].getQuantity() - 1);
                    } else {
                        // This means there is only one book, so remove it completely
                        books[i] = null;
                    }
                }
            }
        }
        removeAllBookNulls();
    }

    public void removeAllBookNulls() {
        int nullAmount = 0;
        for (int i = 0; i < books.length; i++) {
            // Ensure books[i] is not null before checking if a method exists on it
            if (books[i] == null) {
                nullAmount++;
            }
        }
        // Make a new array but without the null values (book length is amount of nulls found)
        Book[] newBooks = new Book[books.length - nullAmount];
        int newBookIndex = 0;
        for (int i = 0; i < books.length; i++) {
            // Ensure books[i] is not null before checking if a method exists on it
            if (books[i] != null) {
                // Set the last index of newBooks to the book that is not null
                newBooks[newBookIndex] = books[i];
                newBookIndex++;
            }
        }
        books = newBooks;
    }

    public String userInfo(){
        return "Name: " + name + "\nId: " + Id + "\nBooks: \n" + bookListInfo();
    } //returns  "Name: []\nID: []\nBooks:\n[]"
       
}