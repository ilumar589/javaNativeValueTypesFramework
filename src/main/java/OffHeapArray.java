import sun.misc.Unsafe;
import unsafe.util.UnsafeHelper;

public class OffHeapArray {

    private final static int BYTE = 1;
    private long size;
    private long address;
    private Unsafe unsafe = UnsafeHelper.getUnsafe();

    OffHeapArray(long size) {
        this.size = size;
        address = UnsafeHelper.getUnsafe().allocateMemory(size * BYTE);
    }

    void set(long addressOffset, byte value) {
        UnsafeHelper.getUnsafe().putByte(address + addressOffset * BYTE, value);
    }

    int get(long addressOffset) {
        return UnsafeHelper.getUnsafe().getByte(address + addressOffset * BYTE);
    }

    public void putFloat(long offset, float value) {
        unsafe.putFloat(address + offset * 4, value);
    }

    public float getFloat(long offset) {
        return unsafe.getFloat(address + offset * 4);
    }

    public long size() {
        return size;
    }

    public void freeMemory() {
        UnsafeHelper.getUnsafe().freeMemory(address);
    }
}
