package ex1;

import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {

    @org.junit.jupiter.api.Test
    void size() {
//        hT.put("0","Valor Sobreescrito");
//        Assertions.assertEquals("\n bucket[0] = [0, Valor Sobreescrito]", hT.toString());

    }

    @org.junit.jupiter.api.Test
    void realSize() {
    }

    @org.junit.jupiter.api.Test
    void put() {
        HashTable hT = new HashTable();

        for (int i = 0; i <10 ; i++) {
            hT.put(String.valueOf(i),"valor"+i);
        }
//        Assertions.assertEquals(
//                "\n bucket[0] = [0, valor0]\n" +
//                " bucket[1] = [1, valor1]\n" +
//                " bucket[2] = [2, valor2]\n" +
//                " bucket[3] = [3, valor3]\n" +
//                " bucket[4] = [4, valor4]\n" +
//                " bucket[5] = [5, valor5]\n" +
//                " bucket[6] = [6, valor6]\n" +
//                " bucket[7] = [7, valor7]\n" +
//                " bucket[8] = [8, valor8]\n" +
//                " bucket[9] = [9, valor9]", hT.toString());
                hT.put("11","Valor Sobreescrito");
                hT.put("22","Valor Sobreescrito");
                hT.put("33","Valor Sobreescrito");

                hT.put("0","Valor Sobreescrito");

                hT.put("1","perro");


        Assertions.assertEquals(
                "\n bucket[0] = [0, Valor Sobreescrito] -> [11, Valor Sobreescrito] -> [22, Valor Sobreescrito] -> [33, Valor Sobreescrito]\n" +
                        " bucket[1] = [1, perro]\n" +
                        " bucket[2] = [2, valor2]\n" +
                        " bucket[3] = [3, valor3]\n" +
                        " bucket[4] = [4, valor4]\n" +
                        " bucket[5] = [5, valor5]\n" +
                        " bucket[6] = [6, valor6]\n" +
                        " bucket[7] = [7, valor7]\n" +
                        " bucket[8] = [8, valor8]\n" +
                        " bucket[9] = [9, valor9]", hT.toString());

        Assertions.assertEquals(13,hT.size());

    }

    @org.junit.jupiter.api.Test
    void get() {
    }

    @org.junit.jupiter.api.Test
    void drop() {
    }
}