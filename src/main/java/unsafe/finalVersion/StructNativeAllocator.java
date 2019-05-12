package unsafe.finalVersion;

import sun.misc.Unsafe;
import unsafe.structs.NativeAlloc;
import unsafe.util.UnsafeHelper;

import java.lang.reflect.Field;

public class StructNativeAllocator {

    private static final Unsafe unsafe = UnsafeHelper.getUnsafe();

    public static long allocateMemory(Class<?> runtimeStruct) {
        int allocationSize = 0;

        Field[] fields = runtimeStruct.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(NativeAlloc.class)) {
                NativeAlloc nativeAlloc = field.getAnnotation(NativeAlloc.class);
                allocationSize += nativeAlloc.size();
            }
        }

        if (allocationSize <= 0) {
            throw new RuntimeException("Class fields must have the @NativeAlloc annotation in order to allocate to native memory");
        }

        return unsafe.allocateMemory(allocationSize);
    }

    public static void freeMemory(long memoryLocation) {
        unsafe.freeMemory(memoryLocation);
    }

    public static void setFloat(long memoryLocation, long offset, float value) {
        unsafe.putFloat(memoryLocation + offset * 4, value);
    }

    public static float getFloat(long memoryLocation, long offset) {
        return unsafe.getFloat(memoryLocation  + offset * 4);
    }
}
