import imgui.ImFont;
import imgui.ImFontConfig;
import imgui.ImFontGlyphRangesBuilder;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.ImGuiStyle;
import imgui.ImVec2;
import imgui.app.Application;
import imgui.app.Configuration;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImInt;
import imgui.type.ImString;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main extends Application {
    // private final ImString str = new ImString(5);
    // private final float[] flt = new float[1];
    private BookStore store = new BookStore();
    private ImVec2 largeButtonSize = new ImVec2(400, 50);
    private ImVec2 mediumButtonSize = new ImVec2(200, 50);
    private ImVec2 mediumButtonSizeLong = new ImVec2(400, 50);
    private ImVec2 smallButtonSize = new ImVec2(25, 30);

    @Override
    protected void configure(final Configuration config) {
        config.setTitle("Book Application");
    }

    @Override
    protected void initImGui(final Configuration config) {
        super.initImGui(config);

        final ImGuiIO io = ImGui.getIO();
        io.setIniFilename(null); // We don't want to save .ini file
        // io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard); // Enable Keyboard
        // Controls
        // io.addConfigFlags(ImGuiConfigFlags.DockingEnable); // Enable Docking
        // io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable); // Enable Multi-Viewport
        // / Platform Windows
        io.setConfigViewportsNoTaskBarIcon(true);
        initFonts(io);
        ImGuiStyle style = ImGui.getStyle();
        // Round corners
        style.setFrameRounding(2);
        style.setDisplayWindowPadding(4f, 4f);
        // io.setFontGlobalScale(1.72f);
    }

    /**
     * Example of fonts configuration
     * For more information read:
     * https://github.com/ocornut/imgui/blob/33cdbe97b8fd233c6c12ca216e76398c2e89b0d8/docs/FONTS.md
     */
    private void initFonts(final ImGuiIO io) {
        // This enables FreeType font renderer, which is disabled by default.
        io.getFonts().setFreeTypeRenderer(true);

        // Font config for additional fonts
        // This is a natively allocated struct so don't forget to call destroy after
        // atlas is built
        final ImFontConfig fontConfig = new ImFontConfig();

        fontConfig.setOversampleH(8);
        fontConfig.setRasterizerMultiply(10f); // Set RasterizationDensity

        // Load custom font
        ImFont openSans = io.getFonts().addFontFromMemoryTTF(loadFromResources("OpenSans-Light.ttf"), 30.0f,
                fontConfig);

        // Add default font for latin glyphs
        // io.getFonts().addFontDefault();
        io.setFontDefault(openSans);

        fontConfig.setMergeMode(true); // Enable merge mode to merge cyrillic, japanese and icons with default font

        // You can use the ImFontGlyphRangesBuilder helper to create glyph ranges based
        // on text input.
        // For example: for a game where your script is known, if you can feed your
        // entire script to it (using addText) and only build the characters the game
        // needs.
        // Here we are using it just to combine all required glyphs in one place
        final ImFontGlyphRangesBuilder rangesBuilder = new ImFontGlyphRangesBuilder(); // Glyphs ranges provide
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesDefault());
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesCyrillic());
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesJapanese());
        rangesBuilder.addRanges(FontAwesomeIcons._IconRange);

        final short[] glyphRanges = rangesBuilder.buildRanges();

        // io.getFonts().addFontFromMemoryTTF(loadFromResources("Tahoma.ttf"), 14,
        // fontConfig, glyphRanges); // cyrillic
        // // glyphs
        // io.getFonts().addFontFromMemoryTTF(loadFromResources("NotoSansCJKjp-Medium.otf"),
        // 14, fontConfig, glyphRanges); // japanese
        // // glyphs
        io.getFonts().addFontFromMemoryTTF(loadFromResources("fa-regular-400.ttf"), 14, fontConfig, glyphRanges); // font
                                                                                                                  // awesome
        io.getFonts().addFontFromMemoryTTF(loadFromResources("fa-solid-900.ttf"), 14, fontConfig, glyphRanges); // font
        // // awesome
        io.getFonts().build();

        fontConfig.destroy();
    }

    private enum STATE_ENUM {
        NONE,
        ADD_BOOK,
        UP_QUANTITY_OF_BOOK,
        SEARCHBOOK,
        SHOWALLBOOKS,
        REGISTERSTUDENT,
        SHOWALLREGISTEREDSTUDENTS,
        CHECKOUT,
        CHECKIN
    };

    private STATE_ENUM state = STATE_ENUM.NONE;

    private void defaultWindow() {
        if (ImGui.button(FontAwesomeIcons.SignOutAlt + " Exit Application", largeButtonSize)) {
            System.exit(0);
        }
        if (ImGui.button(FontAwesomeIcons.Plus + " Add new book", largeButtonSize)) {
            state = STATE_ENUM.ADD_BOOK;
        }
        if (ImGui.button(FontAwesomeIcons.ChevronUp + " Upgrade quantity of book", largeButtonSize)) {
            state = STATE_ENUM.UP_QUANTITY_OF_BOOK;
        }
        if (ImGui.button(FontAwesomeIcons.Search + " Search a book", largeButtonSize)) {
            state = STATE_ENUM.SEARCHBOOK;
        }
        if (ImGui.button(FontAwesomeIcons.Eye + " Show all books", largeButtonSize)) {
            state = STATE_ENUM.SHOWALLBOOKS;
        }
        if (ImGui.button(FontAwesomeIcons.SignInAlt + " Register student", largeButtonSize)) {
            state = STATE_ENUM.REGISTERSTUDENT;
        }
        if (ImGui.button(FontAwesomeIcons.Eye + " Show all registered students", largeButtonSize)) {
            state = STATE_ENUM.SHOWALLREGISTEREDSTUDENTS;
        }
        if (ImGui.button(FontAwesomeIcons.MinusCircle + " Check out book", largeButtonSize)) {
            state = STATE_ENUM.CHECKOUT;
        }
        if (ImGui.button(FontAwesomeIcons.CheckCircle + " Check in book", largeButtonSize)) {
            state = STATE_ENUM.CHECKIN;
        }
        if (ImGui.button(FontAwesomeIcons.Bug + " Add test book values", largeButtonSize)) {
            for (int i = 0; i < 5; i++) {
                String title = "Book " + i;
                String author = "Author " + i;
                int yearPublished = 2000 + i;
                String isbn = String.valueOf(1000 + i);
                int quantity = 1;
                Book test = new Book(title, author, yearPublished, isbn, quantity);
                store.addBook(test);
            }
        }
        if (ImGui.button(FontAwesomeIcons.Bug + " Add test user values", largeButtonSize)) {
            for (int z = 0; z < 5; z++) {
                String name = "User" + z;
                String id = String.valueOf(z * 10);
                User user = new User(name, id);
                // ## Add test books to each user ##
                // Book[] bArr = new Book[5];
                // for (int i = 0; i < 5; i++) {
                // String title = "Book " + i;
                // String author = "Author " + i;
                // int yearPublished = 2000 + i;
                // String isbn = String.valueOf(1000 + i);
                // int quantity = 1;
                // Book book = new Book(title, author, yearPublished, isbn, quantity);
                // bArr[i] = book;
                // }
                // user.setBooks(bArr);
                store.addUser(user);
            }
        }
    }

    private ImString title = new ImString(20);
    private ImString author = new ImString(20);
    private ImString yearPublished = new ImString(4);
    private ImString isbn = new ImString(20);
    private ImString quantity = new ImString(4);

    private void addBookWindow() {
        ImGui.inputText("Book Name", title, ImGuiInputTextFlags.CallbackResize);
        ImGui.inputText("Author", author, ImGuiInputTextFlags.CallbackResize);
        ImGui.inputText("Year Published", yearPublished, ImGuiInputTextFlags.CallbackResize);
        ImGui.inputText("ISBN", isbn, ImGuiInputTextFlags.CallbackResize);
        ImGui.inputText("Quantity", quantity, ImGuiInputTextFlags.CallbackResize);
    }

    private ArrayList<Book> searchForBookHelper(Book[] bookArr) {
        // Ensure this is not private so results don't keep getting appended
        ArrayList<Book> results = new ArrayList<Book>();
        ImGui.combo("Filter by", currentFilter, filters);
        ImGui.inputText("Search for the book", filterInput, ImGuiInputTextFlags.CallbackResize);
        for (Book book : bookArr) {
            // Skip null values
            if (book == null) {
                continue;
            }
            switch (currentFilter.get()) {
                case 0:
                    // If book name exists, show it in results
                    if (book.getTitle().indexOf(filterInput.get()) != -1) {
                        results.add(book);
                    }
                    break;
                case 1:
                    // If book author exists, show it in results
                    if (book.getAuthor().indexOf(filterInput.get()) != -1) {
                        results.add(book);
                    }
                    break;
                case 2:
                    // If book year exists, show it in results
                    if (String.valueOf(book.getYearPublished()).indexOf(filterInput.get()) != -1) {
                        results.add(book);
                    }
                    break;
                case 3:
                    // If book ISBN exists, show it in results
                    if (book.getIsbn().indexOf(filterInput.get()) != -1) {
                        results.add(book);
                    }
                    break;
                case 4:
                    // If book quantity exists, show it in results
                    if (String.valueOf(book.getQuantity()).indexOf(filterInput.get()) != -1) {
                        results.add(book);
                    }
                    break;
            }
        }
        return results;
    }

    private ImString filterInput = new ImString(20);
    private final String[] filters = { "Name", "Author", "Year", "ISBN", "Quantity" };
    private ImInt currentFilter = new ImInt(0);

    private void upgradeQuantityOfBookWindow(Book[] books) {
        ArrayList<Book> results = searchForBookHelper(books);
        for (int i = 0; i < results.size(); i++) {
            Book book = results.get(i);
            ImGui.text(book.bookInfo());
            // Important: Push the ID to ensure that ImGui handles the button seperately and
            // so all of them work
            ImGui.pushID(i);
            if (ImGui.button("+", smallButtonSize)) {
                book.setQuantity(book.getQuantity() + 1);
            }
            ImGui.sameLine();
            if (ImGui.button("-", smallButtonSize)) {
                // Just remove it if the quantity is 0
                if (book.getQuantity() == 1) {
                    store.removeBook(book);
                } else {
                    book.setQuantity(book.getQuantity() - 1);
                }
            }
            ImGui.popID();
        }
    }

    private void searchForBookWindow(Book[] books) {
        ArrayList<Book> results = searchForBookHelper(books);
        for (int i = 0; i < results.size(); i++) {
            Book book = results.get(i);
            ImGui.text(book.bookInfo());
        }
    }

    private void showAllBookWindow(Book[] bookArr) {
        if (bookArr.length == 0) {
            ImGui.text("None found.");
            return;
        }
        for (Book book : bookArr) {
            // Skip null values
            if (book == null) {
                continue;
            }
            ImGui.text(book.bookInfo());
        }
    }

    private void addBook(User user, Book newBook) {
        Book[] bookArr = user.getBooks();
        Book[] newArr = new Book[5];
        int i = 0;
        while (bookArr[i] != null && i < 4) {
            // System.out.println(bookArr[i] + " " + i);
            newArr[i] = bookArr[i];
            i++;
        }
        if (bookArr[i] == null) {
            newArr[i] = newBook;
        } else {
            System.out.println("Book array is full.");
            // End early to avoid any problems with setting the wrong books
            return;
        }
        user.setBooks(newArr);
    }

    private ImString studentName = new ImString(20);
    private ImString studentId = new ImString(20);
    private User lastUser;

    private void addBookForUserWindow(Book[] books, User user, String message) {
        ImGui.text("Choose books from the library to add to your user:");
        ArrayList<Book> results = searchForBookHelper(books);
        if (results.size() == 0) {
            ImGui.text("No books found.");
            return;
        }
        for (int i = 0; i < results.size(); i++) {
            Book book = results.get(i);
            ImGui.text(book.bookInfo());
            // Important: Push the ID to ensure that ImGui handles the button seperately and
            // so all of them work
            ImGui.pushID(i);
            if (ImGui.button(message + user.getName(), mediumButtonSizeLong)) {
                if (book.getQuantity() == 1) {
                    store.removeBook(book);
                } else {
                    book.setQuantity(book.getQuantity() - 1);
                }
                addBook(user, book);
            }
            ImGui.popID();
        }
    }

    private void returnBookToLibraryWindow(Book[] books, User user, String message) {
        ImGui.text("Choose books from your user to return to the library:");
        ArrayList<Book> results = searchForBookHelper(user.getBooks());
        if (results.size() == 0) {
            ImGui.text("No books found.");
            return;
        }
        for (int i = 0; i < results.size(); i++) {
            Book book = results.get(i);
            ImGui.text(book.bookInfo());
            // Important: Push the ID to ensure that ImGui handles the button seperately and
            // so all of them work
            ImGui.pushID(i);
            if (ImGui.button(message + user.getName(), mediumButtonSizeLong)) {
                boolean check = false;
                // Check if there are other copies in the library
                for (Book b : store.getBooks()) {
                    if (b == book) {
                        // Then increase the quantity of the book in the library
                        b.setQuantity(b.getQuantity() + 1);
                        check = true;
                    }
                }
                // If it's not in the library
                if (check == false) {
                    // Then add the book to the library
                    store.addBook(book);
                    user.removeBook(book);
                }
            }
            ImGui.popID();
        }
    }

    private void registerStudentWindow() {
        ImGui.inputText("Name of student", studentName, ImGuiInputTextFlags.CallbackResize);
        ImGui.inputText("Id of student", studentId, ImGuiInputTextFlags.CallbackResize);
        if (ImGui.button("Create user", largeButtonSize)) {
            User newUser = new User(studentName.get(), studentId.get());
            store.addUser(newUser);
            lastUser = newUser;
        }
        if (lastUser == null || lastUser.getBooks().length == 0) {
            ImGui.text("User not created! Create your user first.");
        } else {
            addBookForUserWindow(store.getBooks(), lastUser, "Add book for ");
            ImGui.text("Your user's books:");
            showAllBookWindow(lastUser.getBooks());
        }
        if (ImGui.button("Go back")) {
            // Reset lastUser
            lastUser = null;
            state = STATE_ENUM.NONE;
        }
    }

    private final String[] userFilters = { "Name", "ID" };
    private ImInt currentUserFilter = new ImInt(0);

    private ArrayList<User> searchForUserWindow(User[] userArr) {
        // Ensure this is not private so results don't keep getting appended
        ArrayList<User> results = new ArrayList<User>();
        ImGui.combo("Filter by", currentUserFilter, userFilters);
        ImGui.inputText("Search for the book", filterInput, ImGuiInputTextFlags.CallbackResize);
        for (User user : userArr) {
            // Skip null values
            if (user == null) {
                continue;
            }
            switch (currentFilter.get()) {
                case 0:
                    // If book name exists, show it in results
                    if (user.getName().indexOf(filterInput.get()) != -1) {
                        results.add(user);
                    }
                    break;
                case 1:
                    // If book author exists, show it in results
                    if (user.getId().indexOf(filterInput.get()) != -1) {
                        results.add(user);
                    }
                    break;
            }
        }
        return results;
    }

    User checkOutOrInUser;

    // private void checkOutBookWindow(Book[] results) {
    // // ArrayList<Book> results = searchForBookWindow(checkOutOrInUser);
    // for (int i = 0; i < results.length; i++) {
    // Book book = results[i];
    // if (book != null) {
    // ImGui.text(book.bookInfo());
    // // Important: Push the ID to ensure that ImGui handles the button seperately
    // and
    // // so all of them work
    // ImGui.pushID(i);
    // if (ImGui.button("Check out book", largeButtonSize)) {
    // if (book.getQuantity() == 1) {
    // checkOutOrInUser.removeBook(book);
    // } else {
    // book.setQuantity(book.getQuantity() - 1);
    // }
    // }
    // ImGui.popID();
    // }
    // }
    // }

    private void showAllRegisteredStudentsWindow() {
        for (User user : store.getUsers()) {
            // Ensure user is not null so you don't get NullPointerException
            if (user != null) {
                ImGui.text(user.userInfo());
            }
            ImGui.newLine();
        }
    }

    @Override
    public void process() {
        ImVec2 fullScreen = ImGui.getWindowViewport().getSize();
        if (ImGui.begin("The Coolest Interface TM",
                ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoMove)) {
            ImGui.setWindowPos(0, 0);
            ImGui.setWindowSize(fullScreen);
            switch (state) {
                case NONE:
                    defaultWindow();
                    break;
                case ADD_BOOK:
                    addBookWindow();
                    // Book(String title, String author, int yearPublished, String isbn, int
                    // quantity)
                    if (ImGui.button("Add book", largeButtonSize)) {
                        try {
                            Book newBook = new Book(title.get(), author.get(), Integer.parseInt(yearPublished.get()),
                                    isbn.get(),
                                    Integer.parseInt(quantity.get()));
                            store.addBook(newBook);
                        } catch (Exception e) {
                            System.out.println("Invalid input");
                        }
                        state = STATE_ENUM.NONE;
                    }
                    if (ImGui.button("Go back")) {
                        state = STATE_ENUM.NONE;
                    }
                    break;
                case UP_QUANTITY_OF_BOOK:
                    upgradeQuantityOfBookWindow(store.getBooks());
                    if (ImGui.button("Go back")) {
                        state = STATE_ENUM.NONE;
                    }
                    break;
                case SEARCHBOOK:
                    searchForBookWindow(store.getBooks());
                    if (ImGui.button("Go back")) {
                        state = STATE_ENUM.NONE;
                    }
                    break;
                case SHOWALLBOOKS:
                    showAllBookWindow(store.getBooks());
                    if (ImGui.button("Go back")) {
                        state = STATE_ENUM.NONE;
                    }
                    break;
                case REGISTERSTUDENT:
                    registerStudentWindow();
                    break;
                case SHOWALLREGISTEREDSTUDENTS:
                    showAllRegisteredStudentsWindow();
                    if (ImGui.button("Go back")) {
                        state = STATE_ENUM.NONE;
                    }
                    break;
                case CHECKOUT:
                    if (checkOutOrInUser == null) {
                        ArrayList<User> results = searchForUserWindow(store.getUsers());
                        for (int i = 0; i < results.size(); i++) {
                            User user = results.get(i);
                            ImGui.pushID(i);
                            ImGui.text(user.userInfo());
                            if (ImGui.button("Choose this user to checkout book", largeButtonSize)) {
                                checkOutOrInUser = user;
                            }
                            ImGui.popID();
                        }
                    } else {
                        addBookForUserWindow(store.getBooks(), checkOutOrInUser, "Check out book for ");
                        // checkOutBookWindow(checkOutOrInUser.getBooks());
                    }
                    if (ImGui.button("Go back")) {
                        // Reset checkOutOrInUser
                        checkOutOrInUser = null;
                        state = STATE_ENUM.NONE;
                    }
                    break;
                case CHECKIN:
                    if (checkOutOrInUser == null) {
                        ArrayList<User> results = searchForUserWindow(store.getUsers());
                        for (int i = 0; i < results.size(); i++) {
                            User user = results.get(i);
                            ImGui.pushID(i);
                            ImGui.text(user.userInfo());
                            if (ImGui.button("Choose this user to check in book", largeButtonSize)) {
                                checkOutOrInUser = user;
                            }
                            ImGui.popID();
                        }
                    } else {
                        returnBookToLibraryWindow(store.getBooks(), checkOutOrInUser, "Check in book for ");
                    }
                    if (ImGui.button("Go back")) {
                        // Reset checkOutOrInUser
                        checkOutOrInUser = null;
                        state = STATE_ENUM.NONE;
                    }
                    break;
            }
            // ImGui.sameLine();
            // ImGui.text(String.valueOf(count));
            // ImGui.sliderFloat("float", flt, 0, 1);
            // ImGui.separator();
            // ImGui.text("Extra");
            // Extra.show(this);
        }
        ImGui.end();
    }

    private static byte[] loadFromResources(String name) {
        try {
            return Files.readAllBytes(Paths.get(Main.class.getResource(name).toURI()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(final String[] args) {
        launch(new Main());
        System.exit(0);
    }
}
