package unsafe.structs;

public class Vector3fExp {

    @NativeAlloc(size = 4)
    private float x;
    @NativeAlloc(size = 4)
    private float y;
    @NativeAlloc(size = 4)
    private float z;

    public Vector3fExp(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }
}

