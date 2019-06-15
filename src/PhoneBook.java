import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class PhoneBook {
    private Map<String, String> phoneBook;

    public PhoneBook(File file) throws IOException {
        phoneBook = new TreeMap<>();
        createPhoneBook(file, phoneBook);
    }

    public Map<String, String> getPhoneBook() {
        return  phoneBook;
    }

    public void setPhoneBook(Map<String, String> phoneBook) {
        this.phoneBook = phoneBook;
    }


    //създаване на указател от файл
    public void createPhoneBook(File file, Map<String, String> phoneBook) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String s;
        String[] splits;

        while ((s = br.readLine())!= null){
            if(s.contains(" : ")) {
                splits = s.split(" : ");
                if(checkPhoneNumber(splits[1])) {
                    phoneBook.put(splits[0], normalizePhoneNumber(splits[1]));
                }
            }
        }
    }

    // проверка за валиден тел.номер
    private boolean checkPhoneNumber(String phoneNumber){
        if( phoneNumber.matches("^(\\+359|0|00359)\\s?8(\\d{2}\\s\\d{3}\\d{3}|[789]\\d{7})$")){
            return true;
        }
        else{
            return false;
        }
    }

    // нормализиране на тел.номер --> +359*********
    private String normalizePhoneNumber(String phoneNumber){
        if(phoneNumber.startsWith("00359")) {
            String[] splits = phoneNumber.split("00359");
            phoneNumber = "0" + splits[1];
        }
        if(phoneNumber.startsWith("+359")){
            String[] splits = phoneNumber.split("\\+359");
            phoneNumber = "0" + splits[1];
        }
        return phoneNumber;
    }

    //добавяне на име и номер в указателя
    public void addPair(File file, String name, String phoneNumber) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
        bw.newLine();
        bw.append(name + " : " + phoneNumber);
        bw.close();

        this.getPhoneBook().put(name, phoneNumber);
        this.showPhoneBook();
    }

    //показване на указателя
    public void showPhoneBook(){
        for(Map.Entry<String, String> entry : this.getPhoneBook().entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    public void findByName(String name){
        for(Map.Entry<String, String> entry : this.getPhoneBook().entrySet()){
            if(entry.getKey().toLowerCase().equals(name.toLowerCase())){
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
        }
    }
}
