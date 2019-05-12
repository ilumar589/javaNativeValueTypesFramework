// https://highlyscalable.wordpress.com/2012/02/02/direct-memory-access-in-java/

import unsafe.structs.Vector3f;
import unsafe.util.StructMemoryAllocator;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            try(Vector3f vector = (Vector3f) StructMemoryAllocator.allocateMemory(Vector3f.class)) {
                vector.setX(0, 9.8f);

                System.out.println(vector.getX());
                System.out.println(vector.getY());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
