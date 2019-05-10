// https://highlyscalable.wordpress.com/2012/02/02/direct-memory-access-in-java/

import unsafe.structs.Vector3f;
import unsafe.util.MemoryAllocator;

public class Main {

    public static void main(String[] args) {
        Vector3f vector = (Vector3f) MemoryAllocator.allocateMemory(Vector3f.class);

        vector.setX(0, 9.8f);

        System.out.println(vector.getX());
        System.out.println(vector.getY());

        MemoryAllocator.freeMemory(vector);
    }
}
