package unsafe.util;

import sun.misc.Unsafe;
import unsafe.structs.NativeAlloc;
import unsafe.structs.Struct;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MemoryAllocator {

    private static final String INTERNAL_MEMORY = "internalMemoryBlock";
    private static final String BYTE = "byte";
    private static final String SHORT = "short";
    private static final String INTEGER = "int";
    private static final String LONG = "long";
    private static final String FLOAT = "float";
    private static final String DOUBLE = "double";

    public static Struct allocateMemory(Class<? extends Struct> runtimeStruct) {
        Struct struct = null;
        try {
            struct = runtimeStruct.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (!runtimeStruct.isAnnotationPresent(NativeAlloc.class)) {
            throw new RuntimeException("Class or class fields must have the @NativeAlloc annotation in order to allocate to native memory");
        }

        int allocationSize = 0;

        Field[] fields = runtimeStruct.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(NativeAlloc.class)) {
                NativeAlloc nativeAlloc = field.getAnnotation(NativeAlloc.class);
                // check for -1 values to throw error
                allocationSize += nativeAlloc.size();
            }
        }

        long internalMemoryBlock = UnsafeHelper.getUnsafe().allocateMemory(allocationSize);

        // set internal memory for structure
        try {
            Field internalMemory = runtimeStruct.getDeclaredField(INTERNAL_MEMORY);
            internalMemory.setAccessible(true);
            internalMemory.set(struct, internalMemoryBlock);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        for (Field field : fields) {
            if (field.isAnnotationPresent(NativeAlloc.class)) {
                field.setAccessible(true);
                NativeAlloc nativeAlloc = field.getAnnotation(NativeAlloc.class);
                getFieldAllocationOperation(field.getType().getSimpleName(), internalMemoryBlock, nativeAlloc.order(), nativeAlloc.size())
                        .accept(getFieldType(field, struct).get());
            }
        }

        return struct;
    }

    public static void freeMemory(Struct struct) {
        UnsafeHelper.getUnsafe().freeMemory(struct.getInternalMemoryBlock());
    }

    private static Consumer getFieldAllocationOperation(String fieldName, long internalMemoryBlock, int order, int size) {
        Unsafe unsafe = UnsafeHelper.getUnsafe();
        long location = internalMemoryBlock + order * size;

        switch (fieldName) {
            case BYTE: {
                return (Consumer<Byte>) (aByte) -> unsafe.putByte(location, aByte);
            }
            case SHORT: {
                return (Consumer<Short>) (aShort) -> unsafe.putShort(location, aShort);
            }
            case INTEGER: {
                return (Consumer<Integer>) (aInteger) -> unsafe.putInt(location, aInteger);
            }
            case LONG: {
                return (Consumer<Long>) (aLong) -> unsafe.putLong(location, aLong);
            }
            case FLOAT: {
                return (Consumer<Float>) (aFloat) -> unsafe.putFloat(location, aFloat);
            }
            case DOUBLE: {
                return (Consumer<Double>) (aDouble) -> unsafe.putDouble(location, aDouble);
            }
            default: {
                throw new RuntimeException("Incorrect field name passed");
            }
        }
    }

    private static Supplier getFieldType(Field field, Struct struct) {
        switch (field.getType().getName()) {
            case BYTE: {
                return (Supplier<Byte>) () -> {
                    try {
                        return field.getByte(struct);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return null;
                    }
                };
            }
            case SHORT: {
                return (Supplier<Short>) () -> {
                    try {
                        return field.getShort(struct);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return null;
                    }
                };
            }
            case INTEGER: {
                return (Supplier<Integer>) () -> {
                    try {
                        return field.getInt(struct);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return null;
                    }
                };
            }
            case LONG: {
                return (Supplier<Long>) () -> {
                    try {
                        return field.getLong(struct);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return null;
                    }
                };
            }
            case FLOAT: {
                return (Supplier<Float>) () -> {
                    try {
                        return field.getFloat(struct);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return null;
                    }
                };
            }
            case DOUBLE: {
                return (Supplier<Double>) () -> {
                    try {
                        return field.getDouble(struct);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return null;
                    }
                };
            }
            default: {
                return null;
            }
        }
    }
}

