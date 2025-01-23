
public class BookStore {
    // 2 attributes: Book[] books, User[] users (initialized to an empty array of 10 max users)
    // Intially set books length to 5 in order to ensure that if any methos are called such as consolidateUser, it will return empty instead of not returning anything
    private Book[] books = new Book[5];
    private int bookIndex = 0;
    // (initialized to an empty array of 10 max users)
    private User[] users = new User[10];
    private int userIndex = 0;

    // requires 1 empty constructor
    BookStore() {

    }

    // Getter/setter methods
    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Book[] getBooks() {
        return books;
    }

    // Modify the user at userIndex (which is at the end of the User[] array) in order to add a user to the end
    public void addUser(User user) {
        users[userIndex] = user;
        // Increment userIndex so you can keep track of the end of the User[] array
        userIndex++;
    }

    public void removeUser(User user) {
        for (int i = 0; i < users.length; i++) {
            // If the given user has the same memory address as the user at index i they are the same so set the value there to null
            if (users[i] == user) {
                users[i] = null;
            }
        }
        // Consolidate users in order to make sure there isn't empty gaps with null values
        consolidateUsers();
    }

    // method to move non-null items to the front and null items to the back
    public void consolidateUsers() {
        User[] newUsers = new User[users.length];
        int newUserIndex = 0;

        // For testing
        // for (int i = 0; i < newUsers.length; i++) {
        //     System.out.println(newUsers[i]);
        // }
        // System.out.println("----------");

        for (int i = 0; i < users.length; i++) {
            // If the user is not null then add it to the end of newUsers (using newUserIndex) and increment newUserIndex. This ensures that the new array only has non-null values
            // Ignore all null values
            if (users[i] != null) {
                newUsers[newUserIndex] = users[i];
                newUserIndex++;
            }
        }
        // For testing
        // System.out.println("----------");
        // for (int i = 0; i < newUsers.length; i++) {
        //     System.out.println(newUsers[i]);
        // }
        // System.out.println("----------");

        // Ensure that the users variable is updated with newUsers
        users = newUsers;
    }

    // Modify the null value at bookIndex (which is at the end of the Book[] array) in order to add a book to the end
    // There is no check for if bookIndex is greater than the end of the array
    public void addBook(Book book) {
        // Ensure that that it won't overflow the array
        if (bookIndex <= 4) {
            books[bookIndex] = book;
            // Ensure that the index of end of the Book[] array is updated
            bookIndex++;
        } else {
            System.out.println("Library is full");
        }
    }

    public void insertBook(Book book, int index) {
        // Allocate a new Book array with length 5
        Book[] newBooks = new Book[5];
        // Insert the book at the given index
        newBooks[index] = book;
        // Fill values previous to the index as books from the original books array
        for (int i = 0; i < index; i++) {
            newBooks[i] = books[i];
        }
        // Shift the values past the index from the original array and assign those values to the newBooks array
        for (int i = index + 1; i < books.length - 1; i++) {
            newBooks[i] = books[i - 1];
        }
        books = newBooks;
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
                        bookIndex--;
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
        Book[] newBooks = new Book[books.length];
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

    public void removeAllUserNulls() {
        int nullAmount = 0;
        for (int i = 0; i < users.length; i++) {
            // Ensure books[i] is not null before checking if a method exists on it
            if (users[i] == null) {
                nullAmount++;
            }
        }
        System.out.println(books.length + " " + nullAmount);
        // Make a new array but without the null values (book length is amount of nulls found)
        User[] newUsers = new User[books.length - nullAmount];
        int newUserIndex = 0;
        for (int i = 0; i < books.length; i++) {
            // Ensure books[i] is not null before checking if a method exists on it
            if (books[i] != null) {
                // Set the last index of newBooks to the book that is not null
                newUsers[newUserIndex] = users[i];
                newUserIndex++;
            }
        }
        users = newUsers;
    }

    // public String bookStoreBookInfo(){

    // } //you are not tested on this method but use it for debugging purposes

    // public String bookStoreUserInfo(){

    // } //you are not tested on this method but use it for debugging purposes

}