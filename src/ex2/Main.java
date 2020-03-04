package ex2;

public class Main {
    //REFACCIÓ: he aplicat el mètode de refacció X per què he
    //considerat que no tenia molt sentit tenir el main en el HasTable
    //ja que en el main se estaba creant un HashTable dins de la clase HashTable
    //
    //Te mes sentit tenir el main en una clase a part com per exemple la clase Main

    public static void main(String[] args) {
        HashTable hashTable = new HashTable();

        // Put some key values.
        for(int i=0; i<30; i++) {
            final String key = String.valueOf(i);
            hashTable.put(key, key);
        }

        // Print the HashTable structure
        HashTable.log("****   HashTable  ***");
        HashTable.log(hashTable.toString());
        HashTable.log("\nValue for key(20) : " + hashTable.get("20") );
    }

    static void log(String msg) {
        System.out.println(msg);
    }
}
