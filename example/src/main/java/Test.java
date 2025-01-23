
class Test {
    public static void main(String[] args) {
        IdGenerate.reset(); //reset for test
        IdGenerate.generateID();
        User u1 = new User("John",IdGenerate.getCurrentId());
        String expected = "Name: John\nId: 100\nBooks: \nempty\nempty\nempty\nempty\nempty\n";
        System.out.println(u1.userInfo());
        // DO NOT EDIT
        // for (User z : store.getUsers()) {
        //     if (z != null) {
        //         System.out.println(z.getName());
        //     } else {
        //         System.out.println("null");
        //     }
        // }
        // System.out.println();

        // for (User z : expected) {
        //     if (z != null) {
        //         System.out.println(z.getName());
        //     } else {
        //         System.out.println("null");
        //     }
        // }
        // System.out.println();
    }
}