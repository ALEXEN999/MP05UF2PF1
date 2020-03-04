package ex4;

// Original source code: https://gist.github.com/amadamala/3cdd53cb5a6b1c1df540981ab0245479
// Modified by Fernando Porrino Serrano for academic purposes.

import ex2.Main;

import java.util.ArrayList;

public class HashTable extends Main {
    /** Este initial size es fijo ya que es el tamaño de buckets de nuestro hashTable*/
    private int INITIAL_SIZE = 16;
    /** El size normal se incrementara cada vez que se agrege un valor nuevo al hashTable y se restara cuando se borre alguno*/
    private int size = 0;

    /** Esto es un array de HashEntry, es decirm nuestro hashTable practicamente*/
    private HashEntry[] entries = new HashEntry[INITIAL_SIZE];

    /** Este boolean lo he puesto para poder sobreescribir valores en el put()*/
    boolean estaEscrito = false;

    public int size(){
        return this.size;
    }

    public int realSize(){
        return this.INITIAL_SIZE;
    }

    /** El metodo put funciona como deberia, ya que he arreglado varios problemas que comentare ahora
     * pero este metodo lo que hace es insertar datos a nuestro hashtable pasandole una clave y un valor*/

    public void put(String key, String value) {

        /** Con esa misma clave que hemos recogida creamos un hash y un hash Entry para poder acceder a las diferentes "entradas"*/

        int hash = getHash(key);
        final HashEntry hashEntry = new HashEntry(key, value);

        /** Si este entry es nullo significa que no hay ningun dato en el y por lo tanto puede escribirlo sin problema
         *
         * Si no es nullo, creamos un hashEntry temporal para recorrer el hashTable entero en busca de ese lugar*/
        if(entries[hash] == null) {
            entries[hash] = hashEntry;
        }
        else {
            HashEntry temp = entries[hash];

            /** En este if lo que he hecho es que si la siguiente celda de en la que estoy yo es igual a null y ya he realizado alguna escritura entonces entro en el if*/
            if (temp.next == null && estaEscrito){
                /** He puesto un condicional que comprueba que si el temp(HashEntry).key sea igual a la key que he introducido por el put.
                 *
                 * Si es asi entonces igualo el temp.value por el value que le he pasado al metodo put y al boolean lo pongo en true y restamos el size-- ya que se habia sumado*/

                if (temp.key.equals(hashEntry.key)) {
                    temp.value = value;
                    estaEscrito = true;
                    size--;
                }
            }else {
                /**Aqui vamos a recorrer el bucle hasta que el temp.next sea null
                 * De la misma forma que antes hacemos lo de comparar las keys
                 * Pero en cada vuelta del bucle vamos recorriendo de uno en uno el hashTable*/

                while (temp.next != null) {
                    if (temp.key.equals(hashEntry.key)) {
                        temp.value = value;
                        estaEscrito = true;
                        size--;
                    }
                    temp = temp.next;
                }
                /** Esta parte de aqui lo que hace es simplemente remplazar los valores con la key igual
                 *
                 * Si no esta escrito entonces el siguiente de en el que estoy yo lo igualamos al hashEnty que le pasamos al metodo put con un key y un value,
                 *
                 * Luego el hashEntry.prev es decir el de la posicion anterior la igualamos al temp
                 *
                 * Y por ultimo cambiamos el estado de nuestro boolean a false*/

                if (!estaEscrito) {
                    temp.next = hashEntry;
                    hashEntry.prev = temp;
                    estaEscrito = false;
                }
            }
        }
        /** Incrementamos el size con el size++ cada vez que agregamos uno nuevo*/
        size++;
    }

    /**
     * Returns 'null' if the element is not found.
     */


    /** Para el metodo get le pasaremos una key, por la que este buscara por el hashTable en busca de esa key para devolvernos su valor*/
    public String get(String key) {
        /** Conseguimos el hash de la key*/
        int hash = getHash(key);

        /** Si el hash que le he pasado es null me retornara un null, pero si no es null entonces crearemos un temoral para recorrer el hashtable*/
        if(entries[hash] != null) {
            HashEntry temp = entries[hash];

            /** Mientras no coincidan la clave temporal con la que le ha pasado por metodo, este seguira buscando y cuando lo encuentre retornara ese valor*/
            while( !temp.key.equals(key))
                temp = temp.next;

            return temp.value;
        }

        return null;
    }


    /** Este metodo lo que hara sera desenlazar una "celda" del resto cambiando los enlaces de .next y .prev*/
    public void drop(String key) {
        int hash = getHash(key);
        if(entries[hash] != null) {

            /** Como siempre buscamos que las keys coincidan*/
            HashEntry temp = entries[hash];
            while( !temp.key.equals(key))
                temp = temp.next;

            /** Este if lo que hace es que si no hay nada detras de mi signifa que soy el primero entonces igualo mi hash a null,
             * El problema viene cuando se borran todos al borrar el primero*/

            if(temp.prev == null) entries[hash] = null;             //esborrar element únic (no col·lissió)
            else{
                /** Como dije al principio de este metodo ahora tenemos que cambiar los enlaces de los temp haciendo que el anterior y el siguiente a nosotros esten conectados
                 * sin pasar por nosotros*/
                if(temp.next != null) temp.next.prev = temp.prev;   //esborrem temp, per tant actualitzem l'anterior al següent
                temp.prev.next = temp.next;                         //esborrem temp, per tant actualitzem el següent de l'anterior
            }

            /** Y cada vez que borro resto size*/
            size--;
        }
    }

    private int getHash(String key) {
        // piggy backing on java string
        // hashcode implementation.
        return key.hashCode() % INITIAL_SIZE;
    }

    private class HashEntry {
        String key;
        String value;

        // Linked list of same hash entries.
        HashEntry next;
        HashEntry prev;

        public HashEntry(String key, String value) {
            this.key = key;
            this.value = value;
            this.next = null;
            this.prev = null;
        }

        /** Con estos dos toString les estoy dando una forma mas visual a nuestro hashTable a la hora de mostrarlo */
        @Override
        public String toString() {
            return "[" + key + ", " + value + "]";
        }
    }

    @Override
    public String toString() {
        int bucket = 0;
        StringBuilder hashTableStr = new StringBuilder();
        for (HashEntry entry : entries) {
            if(entry == null) {
                bucket++;
                continue;
            }

            hashTableStr.append("\n bucket[")
                    .append(bucket)
                    .append("] = ")
                    .append(entry.toString());
            bucket++;
            HashEntry temp = entry.next;
            while(temp != null) {
                hashTableStr.append(" -> ");
                hashTableStr.append(temp.toString());
                temp = temp.next;
            }
        }
        return hashTableStr.toString();
    }

    public ArrayList<String> getCollisionsForKey(String key) {
        return getCollisionsForKey(key, 1);
    }


    /** Este metodo lo que hace es que a partir de una key y una cantidad de combinaciones no devuelve las colisiones con esa misma key*/
    public ArrayList<String> getCollisionsForKey(String key, int quantity){
        /*
          Main idea:
          alphabet = {0, 1, 2}

          Step 1: "000"
          Step 2: "001"
          Step 3: "002"
          Step 4: "010"
          Step 5: "011"
           ...
          Step N: "222"

          All those keys will be hashed and checking if collides with the given one.
        * */

        final char[] alphabet = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        ArrayList<Integer> newKey = new ArrayList();
        ArrayList<String> foundKeys = new ArrayList();

        newKey.add(0);
        int collision = getHash(key);
        int current = newKey.size() -1;

        while (foundKeys.size() < quantity){
            //building current key
            String currentKey = "";
            for(int i = 0; i < newKey.size(); i++)
                currentKey += alphabet[newKey.get(i)];

            if(!currentKey.equals(key) && getHash(currentKey) == collision)
                foundKeys.add(currentKey);

            //increasing the current alphabet key
            newKey.set(current, newKey.get(current)+1);

            //overflow over the alphabet on current!
            if(newKey.get(current) == alphabet.length){
                int previous = current;
                do{
                    //increasing the previous to current alphabet key
                    previous--;
                    if(previous >= 0)  newKey.set(previous, newKey.get(previous) + 1);
                }
                while (previous >= 0 && newKey.get(previous) == alphabet.length);

                //cleaning
                for(int i = previous + 1; i < newKey.size(); i++)
                    newKey.set(i, 0);

                //increasing size on underflow over the key size
                if(previous < 0) newKey.add(0);

                current = newKey.size() -1;
            }
        }

        return  foundKeys;
    }
}