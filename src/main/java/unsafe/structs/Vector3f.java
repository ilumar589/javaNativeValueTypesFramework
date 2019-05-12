package unsafe.structs;

import unsafe.util.UnsafeHelper;

import java.io.IOException;

@NativeAlloc
public class Vector3f implements Struct {

    @NativeAlloc(order = 0, size = 4)
    private float x;
    @NativeAlloc(order = 1, size = 4)
    private float y;
    @NativeAlloc(order = 2, size = 4)
    private float z;

    private long internalMemoryBlock;

    @Override
    public int getPositionInMemory() {
        return 0;
    }

    @Override
    public long getInternalMemoryBlock() {
        return internalMemoryBlock;
    }

    public void setX(long offset, float value) {
        UnsafeHelper.getUnsafe().putFloat(internalMemoryBlock + offset * 4, value);
    }

    public float getX() {
        return UnsafeHelper.getUnsafe().getFloat(internalMemoryBlock);
    }

    public float getY() {
        return UnsafeHelper.getUnsafe().getFloat(internalMemoryBlock, 2L);
    }

    @Override
    public void close() throws IOException {
        UnsafeHelper.getUnsafe().freeMemory(internalMemoryBlock);
    }
}
