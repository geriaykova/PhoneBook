import java.io.*;

public class Demo {

    public static void main(String[] args) throws Exception {


        File file = new File("C:\\Users\\Freeware Sys\\Desktop\\test.txt");

        PhoneBook phoneBook3 = new PhoneBook(file);
        phoneBook3.showPhoneBook();
        phoneBook3.createCallsBook(phoneBook3, phoneBook3.getCallsBookMap());
        phoneBook3.showCallsBook();
        phoneBook3.callSomeone("sisi", 5);
        phoneBook3.callSomeone("gergana aykova", 6);
        phoneBook3.callSomeone("Georgi", 7);
        phoneBook3.callSomeone("Rosen", 1);
        phoneBook3.callSomeone("fafi", 3);
        phoneBook3.callSomeone("Anni", 4);
        phoneBook3.showCallsBook();
        phoneBook3.showTopFiveCallers();








    }
}
