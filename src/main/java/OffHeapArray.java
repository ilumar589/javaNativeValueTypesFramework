import unsafe.util.UnsafeHelper;

public class OffHeapArray {

    private final static int BYTE = 1;
    private long size;
    private long address;

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

    public long size() {
        return size;
    }

    public void freeMemory() {
        UnsafeHelper.getUnsafe().freeMemory(address);
    }
}
