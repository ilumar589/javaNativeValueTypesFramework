// https://highlyscalable.wordpress.com/2012/02/02/direct-memory-access-in-java/
import unsafe.finalVersion.StructNativeAllocator;
import unsafe.structs.Vector3fExp;

public class Main {

    public static void main(String[] args) {
        timeNativeArray();
//        timeVMArrays();
//        timeVM();
//        timeNative();
    }

    private static void timeNativeArray() {
        OffHeapArray offHeapArray = new OffHeapArray(3_000_000 * 12);

        for (int i=0; i < 3_000_000; i++) {
            offHeapArray.putFloat(i, i);
            offHeapArray.putFloat(i + 1, i + 1);
            offHeapArray.putFloat(i + 2, i + 2);
        }

        float sum = 0;

        // Warm up
        for (int i=0; i < 3_000_000; i++) {
            sum += offHeapArray.getFloat(i) + offHeapArray.getFloat(i + 1) + offHeapArray.getFloat(i + 2);
        }

        for (int i=0; i < 3_000_000; i++) {
            sum += offHeapArray.getFloat(i) + offHeapArray.getFloat(i + 1) + offHeapArray.getFloat(i + 2);
        }

        //
        long startTime = System.currentTimeMillis();


        for (int i=0; i < 3_000_000; i++) {
            sum += offHeapArray.getFloat(i) + offHeapArray.getFloat(i + 1) + offHeapArray.getFloat(i + 2);
        }

        offHeapArray.freeMemory();
        System.out.println(sum);

        long stopTime = System.currentTimeMillis();

        System.out.println(stopTime - startTime);
    }

    private static void timeVMArrays() {
        Vector3fExp[] vector3fExps = new Vector3fExp[3_000_000];

       for (int i=0; i < 3_000_000; i++) {
           vector3fExps[i] = new Vector3fExp(i+1, i+2, i+3);
       }

        float sum = 0;
       // Warm up
        for (int i=0; i < 3_000_000; i++) {
            Vector3fExp vTemp = vector3fExps[i];
            sum += vTemp.getX() + vTemp.getY() + vTemp.getZ();
        }
        for (int i=0; i < 3_000_000; i++) {
            Vector3fExp vTemp = vector3fExps[i];
            sum += vTemp.getX() + vTemp.getY() + vTemp.getZ();
        }
        //

        long startTime = System.currentTimeMillis();

        for (int i=0; i < 3_000_000; i++) {
            Vector3fExp vTemp = vector3fExps[i];
            sum += vTemp.getX() + vTemp.getY() + vTemp.getZ();
        }

        System.out.println(sum);
        long stopTime = System.currentTimeMillis();

        System.out.println(stopTime - startTime);
    }

    private static void timeVM() {
        long startTime = System.currentTimeMillis();

        for (long i=0; i < 3_000_000L; i++) {
            Vector3fExp vectorHandle = new Vector3fExp(1.5f, 2.5f, 3.5f);

//
//            System.out.println("X value:" + vectorHandle.getX());
//            System.out.println("Y value:" + vectorHandle.getY());
//            System.out.println("Z value:" + vectorHandle.getZ());
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

//            System.out.println("X value:" + StructNativeAllocator.getFloat(vectorHandle, 0));
//            System.out.println("Y value:" + StructNativeAllocator.getFloat(vectorHandle, 1));
//            System.out.println("Z value:" + StructNativeAllocator.getFloat(vectorHandle, 2));

            StructNativeAllocator.freeMemory(vectorHandle);
        }

        long stopTime = System.currentTimeMillis();

        System.out.println(stopTime - startTime);
    }
}
