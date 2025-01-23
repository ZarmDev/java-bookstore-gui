
public class IdGenerate{ //This class contains statics variable and methods, you do not initalize an object to use it.
    
    //requires 1 private attribute String currentId. You must initialize it to 99
    private static String currentId = "99";
    //requires one empty constructor
    IdGenerate() {

    }

    // getter
    public static String getCurrentId(){
        return currentId;
    }

    // resets the currentId to 99
    public static void reset(){
        currentId = "99";
    }

    //generates a new id, when called it will increment the currentId by 1 
    public static void generateID(){
        // First, convert the ID from string to int.
        // Then, increment the value of the int and then convert it into a string.
        // Set that value to currentId
        currentId = String.valueOf(Integer.parseInt(currentId) + 1);
    }
}