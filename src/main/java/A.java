import sun.misc.Unsafe;
import unsafe.util.UnsafeHelper;

public class A {
    private final static int SIZE_IN_BYTES = 12; // 12 bytes
    private static final int INT_SIZE = 4;
    private static final int LONG_SIZE = 8;
    private int x; // 4 bytes
    private long y; // 8 bytes

    private long memoryAddress;
    private long startingOffset = (long) Integer.MAX_VALUE; // don't know why we can't start with a pointer to first memory zone

    // This section is for individual off heap allocation
    public A(int x, long y) {
        Unsafe unsafe = UnsafeHelper.getUnsafe();
        memoryAddress = unsafe.allocateMemory(SIZE_IN_BYTES);
        unsafe.putInt(memoryAddress, x);
        unsafe.putLong(memoryAddress + LONG_SIZE, y);
    }

    public int getX() {
        return UnsafeHelper.getUnsafe().getInt(memoryAddress );
    }

    public long getY() {
        return UnsafeHelper.getUnsafe().getLong(memoryAddress + LONG_SIZE);
    }
    // ----

    public long getMemoryAddress() {
        return memoryAddress;
    }

    public void addXToOtherMemory(long address, long offset) {
        UnsafeHelper.getUnsafe().putInt(address +  offset, getX());
    }

    public void addYToOtherMemory(long address, long offset) {
        UnsafeHelper.getUnsafe().putLong(address +  offset, getY());
    }

    public void freeMemory() {
        UnsafeHelper.getUnsafe().freeMemory(memoryAddress);
    }

    public static int getSizeInBytes() {
        return SIZE_IN_BYTES;
    }
}
