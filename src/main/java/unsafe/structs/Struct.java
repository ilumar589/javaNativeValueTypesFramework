package unsafe.structs;

import java.io.Closeable;

public interface Struct extends Closeable {

    int getPositionInMemory();

    long getInternalMemoryBlock();
}
