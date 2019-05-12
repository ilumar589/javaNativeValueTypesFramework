// https://highlyscalable.wordpress.com/2012/02/02/direct-memory-access-in-java/
import unsafe.finalVersion.StructNativeAllocator;
import unsafe.structs.Vector3fExp;

public class Main {

    public static void main(String[] args) {
        timeVM();
//        timeNative();
    }

    private static void timeVM() {
        long startTime = System.currentTimeMillis();

        for (long i=0; i < 3_000_000L; i++) {
            Vector3fExp vectorHandle = new Vector3fExp(1.5f, 2.5f, 3.5f);


            System.out.println("X value:" + vectorHandle.getX());
            System.out.println("Y value:" + vectorHandle.getY());
            System.out.println("Z value:" + vectorHandle.getZ());
        }

        long stopTime = System.currentTimeMillis();

        System.out.println(stopTime - startTime);
    }

    private static void timeNative() {
        long startTime = System.currentTimeMillis();

        for (long i=0; i < 3_000_000L; i++) {
            long vectorHandle = StructNativeAllocator.allocateMemory(Vector3fExp.class);
            StructNativeAllocator.setFloat(vectorHandle, 0, 1.5f);
            StructNativeAllocator.setFloat(vectorHandle, 1, 2.5f);
            StructNativeAllocator.setFloat(vectorHandle, 2, 3.5f);

            System.out.println("X value:" + StructNativeAllocator.getFloat(vectorHandle, 0));
            System.out.println("Y value:" + StructNativeAllocator.getFloat(vectorHandle, 1));
            System.out.println("Z value:" + StructNativeAllocator.getFloat(vectorHandle, 2));

            StructNativeAllocator.freeMemory(vectorHandle);
        }

        long stopTime = System.currentTimeMillis();

        System.out.println(stopTime - startTime);
    }
}
