import sun.misc.Unsafe;
import unsafe.util.UnsafeHelper;

public class B {

    private static final int KNOWN_SIZE_IN_BYTES = A.getSizeInBytes() + 4;

    private A aInstance; // will get the size from the class intance
    private float z; // 4 bytes
    private String name; // ? unknown size

    private long memoryAddress;
    private long startingOffset = (long) Integer.MAX_VALUE;

    public B(A aInstance, float z) {
        Unsafe unsafe = UnsafeHelper.getUnsafe();
        this.aInstance = aInstance;

        memoryAddress = unsafe.allocateMemory(KNOWN_SIZE_IN_BYTES);

        this.aInstance.addXToOtherMemory(memoryAddress, startingOffset);
        this.aInstance.addYToOtherMemory(memoryAddress, startingOffset + 1);

        System.out.println(unsafe.getInt(memoryAddress));
        System.out.println(unsafe.getLong(memoryAddress + 1));
    }

    public void freeMemory() {
        UnsafeHelper.getUnsafe().freeMemory(memoryAddress);
    }
}
