package ex2;

import ex1.HashTable;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertNull;

class HashTableTest {
    /** */
    @org.junit.jupiter.api.Test
    void size() {
        ex2.HashTable hT = new ex2.HashTable();

        //1.-Primero agregamos 1000 por probar y comprobamos el size que en teoria esta bien, ya que el put esta arreglado
        for (int i = 0; i < 1000 ; i++) {
            hT.put(String.valueOf(i),"valor"+i);
        }

        //2.-Despues quitamos 500 y comprobamos el size pero aqui falla ya que no quita los 500 porque hay varios errores en el drop
        Assertions.assertEquals("1000",hT.size());
        for (int i = 0; i < 500 ; i++) {
            hT.drop(String.valueOf(i));
        }
        //3.-Por ultimo agregamos de nuevo 1000 para ver si no se buguea al no haber hecho bien el drop y comprobamos el size
        Assertions.assertEquals("500",hT.size());
        for (int i = 0; i < 1000 ; i++) {
            hT.put(String.valueOf(i),"valor"+i);
        }
        Assertions.assertEquals("1000",hT.size());
    }

    @org.junit.jupiter.api.Test
    void realSize() {
        ex2.HashTable hT = new ex2.HashTable();

        //1.-Primero agregamos 1000 por probar y comprobamos el realSize que en teoria debe ser siempre 16
        // ya que se iran agregando a las listas de estos 16 buckets hasta haber agregado los 1000
        for (int i = 0; i < 1000 ; i++) {
            hT.put(String.valueOf(i),"valor"+i);
        }
        //2.-Despues quitamos 500 y comprobamos el realSize
        Assertions.assertEquals("16",hT.realSize());
        for (int i = 0; i < 500 ; i++) {
            hT.drop(String.valueOf(i));
        }
        //3.-Por ultimo agregamos de nuevo 1000 y comprobamos el realSize
        Assertions.assertEquals("16",hT.realSize());
        for (int i = 0; i < 1000 ; i++) {
            hT.put(String.valueOf(i),"valor"+i);
        }
        Assertions.assertEquals("16",hT.realSize());
    }

    @org.junit.jupiter.api.Test
    void put() {
        ex2.HashTable hT = new ex2.HashTable();

        //1.-Primero vamos a comprobar si da problemas agregar numeross negativos como key
        //aunque no deberia ya que tambien se pueden poner textos como key.
        //llamamos al metodo de getcollisionsForKey() para que nos muestre por pantalla
        //las diferentes collisiones con la key -1.
        System.out.println(hT.getCollisionsForKey("-1",5));
        hT.put("-1","negativo");

        //2.-Comprobamos y esto funciona bien.
        Assertions.assertEquals("\n bucket[4] = [-1, negativo]", hT.toString());

        //3.-Vamos a agregar mas valores y comprobamo la collision del -1 que creamos antes
        //esto funciona tambien
        for (int i = 0; i <10 ; i++) {
            hT.put(String.valueOf(i),"valor"+i);
        }
        Assertions.assertEquals(
                "\n bucket[0] = [0, valor0]\n" +
                " bucket[1] = [1, valor1]\n" +
                " bucket[2] = [2, valor2]\n" +
                " bucket[3] = [3, valor3]\n" +
                " bucket[4] = [-1, negativo] -> [4, valor4]\n" +
                " bucket[5] = [5, valor5]\n" +
                " bucket[6] = [6, valor6]\n" +
                " bucket[7] = [7, valor7]\n" +
                " bucket[8] = [8, valor8]\n" +
                " bucket[9] = [9, valor9]", hT.toString());

        //4.-Ahora vamos a agregar solisiones en el bucket 0
        //esto fallaba pero ya esta arreglado, a lo largo de intentos de arreglar el put he tenido
        //varios problemas con el tema de sobreescribir elementos ya que al principio solo se añadian y remplazaba.
                hT.put("11","Valor Sobreescrito");
                hT.put("22","Valor Sobreescrito");
                hT.put("33","Valor Sobreescrito");
        //5.-Ahora si remplazamos el primero por ejemplo lo cambia sin problemas
                hT.put("0","Valor Sobreescrito");
        //6-He creado otro reemplazo ya que si al crear colisiones y sustituir en un bucket anterior en el siguiente no lo remplazaba.
                hT.put("1","perro");
        //7.-Ahora vamos a borrar el -1 que creamos al principio y vaya... se a borrado ya que al borrar el primero se borran todos

                hT.drop("-1");
        //8.-He puesto una comprobacion de que al borrar el -1 solo tiene que borrar el -1 y no el resto,
        //el que estaba en la segunda posicion deberia haber estado en la primera posicion una vez borrado el primero
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

        //9.-Aqui realizo un size normal para comprobarlo de antes
        //deberia haber 13 ya que en el size se cuentan todas las colisiones
        Assertions.assertEquals(13,hT.size());

    }

    @org.junit.jupiter.api.Test
    void get() {
        ex2.HashTable hT = new ex2.HashTable();

        //1.-Primero agregaremos varios valores
        hT.put("0","Valor");

        hT.put("1","zorro");
        hT.put("11","gata");
        hT.put("22","camello");

        //2.-Comprobamos que estan creadas bien haciendo un get(key)

        Assertions.assertEquals("Valor",hT.get("0"));

        //he agregado alguna collision para poder comprobar el primero, el ultimo y el del medio
        Assertions.assertEquals("zorro",hT.get("1"));

        Assertions.assertEquals("gata",hT.get("11"));

        Assertions.assertEquals("camello",hT.get("22"));

        //3.-Por ultimo he comprobado keys inexistente
        //y salen null como tenia previsto
        assertNull(hT.get("12"));
        assertNull(hT.get("-1"));
    }

    @org.junit.jupiter.api.Test
    void drop() {

        ex2.HashTable hT = new ex2.HashTable();

        //1.-Agregamos 10 valores
        for (int i = 0; i < 11 ; i++) {
            hT.put(String.valueOf(i),"valor"+i);
        }

        //2.-Los comprobamos con un get() los que vamos a borrar a continuacion

        Assertions.assertEquals("valor10",hT.get("10"));
        Assertions.assertEquals("valor1",hT.get("1"));
        Assertions.assertEquals("valor0",hT.get("0"));

        //3.-Comprobamos que el size este bien
        Assertions.assertEquals(11,hT.size());

        //4.-Y ahora intentamos borrar los que comprobamos antes, y funcionará
        //pero fallara cuando quedamos hacer drop() de una cantidad mas grande como hicimos en el size() anteriormente
        hT.drop("10");

        Assertions.assertEquals("\n bucket[0] = [0, valor0]\n" +
                " bucket[1] = [1, valor1]\n" +
                " bucket[2] = [2, valor2]\n" +
                " bucket[3] = [3, valor3]\n" +
                " bucket[4] = [4, valor4]\n" +
                " bucket[5] = [5, valor5]\n" +
                " bucket[6] = [6, valor6]\n" +
                " bucket[7] = [7, valor7]\n" +
                " bucket[8] = [8, valor8]\n" +
                " bucket[9] = [9, valor9]",hT.toString());

        hT.drop("1");

        Assertions.assertEquals("\n bucket[0] = [0, valor0]\n" +
                " bucket[2] = [2, valor2]\n" +
                " bucket[3] = [3, valor3]\n" +
                " bucket[4] = [4, valor4]\n" +
                " bucket[5] = [5, valor5]\n" +
                " bucket[6] = [6, valor6]\n" +
                " bucket[7] = [7, valor7]\n" +
                " bucket[8] = [8, valor8]\n" +
                " bucket[9] = [9, valor9]",hT.toString());

        hT.drop("0");

        Assertions.assertEquals("\n bucket[2] = [2, valor2]\n" +
                " bucket[3] = [3, valor3]\n" +
                " bucket[4] = [4, valor4]\n" +
                " bucket[5] = [5, valor5]\n" +
                " bucket[6] = [6, valor6]\n" +
                " bucket[7] = [7, valor7]\n" +
                " bucket[8] = [8, valor8]\n" +
                " bucket[9] = [9, valor9]",hT.toString());

        //5.-Comprobamos que los que hemos borrado son null a la hora de buscarlos mediante el get().
        assertNull(hT.get("10"));
        assertNull(hT.get("1"));
        assertNull(hT.get("0"));

        //6.-Y por ultimo comprobamos el size para ver si lo ha hecho bien,
        //el problema viene cuando es una cantidad mas grande de valores a borrar.
        Assertions.assertEquals(8,hT.size());
    }
}