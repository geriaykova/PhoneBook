import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class PhoneBook {
    private Map<String, String> phoneBookMap;
    private Map<Map.Entry<String, String>, Integer> callsBookMap;


    public PhoneBook(File file) throws IOException {
        phoneBookMap = new TreeMap<>();
        callsBookMap = new HashMap<>();
        createPhoneBook(file, phoneBookMap);
    }

    public Map<String, String> getPhoneBookMap() {
        return phoneBookMap;
    }

    public void setPhoneBookMap(Map<String, String> phoneBookMap) {
        this.phoneBookMap = phoneBookMap;
    }

    public Map<Map.Entry<String, String>, Integer> getCallsBookMap() {
        return callsBookMap;
    }

    public void setCallsBookMap(Map<Map.Entry<String, String>, Integer> callsBookMap) {
        this.callsBookMap = callsBookMap;
    }


    public void createPhoneBook(File file, Map<String, String> phoneBook) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String s;
        String[] splits;

        while ((s = br.readLine()) != null) {
            if (s.contains(" : ")) {
                splits = s.split(" : ");
                if (checkPhoneNumber(splits[1])) {
                    phoneBook.put(splits[0], normalizePhoneNumber(splits[1]));
                }
            }
        }
    }

    public void createCallsBook(PhoneBook phoneBook, Map<Map.Entry<String, String>, Integer> callsBookMap) {
        for (Map.Entry<String, String> entry : phoneBook.getPhoneBookMap().entrySet()) {
            callsBookMap.put(entry, 0);
        }
    }

    private boolean checkPhoneNumber(String phoneNumber) {
        if (phoneNumber.matches("^(\\+359|0|00359)\\s?8(\\d{2}\\s\\d{3}\\d{3}|[789]\\d{7})$")) {
            return true;
        } else {
            return false;
        }
    }

    private String normalizePhoneNumber(String phoneNumber) {
        if (phoneNumber.startsWith("00359")) {
            String[] splits = phoneNumber.split("00359");
            phoneNumber = "0" + splits[1];
        }
        if (phoneNumber.startsWith("+359")) {
            String[] splits = phoneNumber.split("\\+359");
            phoneNumber = "0" + splits[1];
        }
        return phoneNumber;
    }

    public void addPair(File file, String name, String phoneNumber) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
        bw.newLine();
        bw.append(name + " : " + phoneNumber);
        bw.close();

        this.getPhoneBookMap().put(name, phoneNumber);
        this.showPhoneBook();
        this.getCallsBookMap().put(this.getEntryByName(name), 0);
        this.showCallsBook();
    }

    // не ми е много ясно как се случва триенето от файл... затова и не върши полезна работа
    public void deletePair(File file, String name) throws Exception {
        File tempFile = new File("C:\\Users\\Freeware Sys\\Desktop\\temp.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;
        while((currentLine = br.readLine()) != null){
            String trimmedLine = currentLine.trim();
            if(trimmedLine.toLowerCase().contains(name.toLowerCase())) {
                continue;
            }
            bw.write(currentLine + System.getProperty("line.separator"));
        }
        bw.close();
        br.close();
        //tempFile.renameTo(file);
        this.getCallsBookMap().remove(getEntryByName(name));
        this.getPhoneBookMap().remove(name);
    }

    public void callSomeone(String name, int times) {
        System.err.println(name + " is calling someone...");
        for (Map.Entry<Map.Entry<String, String>, Integer> entry : this.getCallsBookMap().entrySet()) {
            if (entry.getKey().getKey().toLowerCase().equals(name.toLowerCase())) {
                entry.setValue(entry.getValue() + times);

            }
        }
    }

    public void showTopFiveCallers() {
        System.out.println();
        System.err.println("Top 5 callers:");
        this.getCallsBookMap()
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .forEach(System.out::println);

    }

    public void showPhoneBook(){
        System.out.println();
        System.err.println("Phone Book:");
        for(Map.Entry<String, String> entry : this.getPhoneBookMap().entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    public void showCallsBook(){
        System.out.println();
        System.err.println("Calls Book:");
        for(Map.Entry<Map.Entry<String, String>, Integer> entry : this.getCallsBookMap().entrySet()){
            System.out.println(entry.getKey() + "  calls: " + entry.getValue());
        }
    }

    private Map.Entry<String, String> getEntryByName(String name) throws Exception {
        Map.Entry<String,String> result = null;
        if(this.getPhoneBookMap() != null){
            for(Map.Entry<String, String> entry : this.getPhoneBookMap().entrySet()){
                if(entry.getKey().toLowerCase().equals(name.toLowerCase())){
                    result = entry;
                }
            }
        }
        if(result == null){
            throw new Exception("There is no record with name: " + name);
        }
        else {
            return result;
        }
    }


    public void findByName(String name){
        for(Map.Entry<String, String> entry : this.getPhoneBookMap().entrySet()){
            if(entry.getKey().toLowerCase().equals(name.toLowerCase())){
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
        }
    }
}
