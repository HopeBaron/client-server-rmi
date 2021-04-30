package model;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.util.BitSet;

public final class Permissions {
    public Permissions(int value) {
        ByteBuffer buf = ByteBuffer.allocate(1);
        buf.putInt(value);
        set =  BitSet.valueOf(buf);
    }

    public Permissions() {
        set = new BitSet();
    }

    private final BitSet set;

    public boolean contains(Permission perm) {
        return set.get(perm.getValue() / 2);
    }

    public void add(Permission permission) {
        set.set(permission.getValue() / 2, true);
    }


    public void remove(Permission permission) {
        set.set(permission.getValue() / 2, false);
    }

    public int getValue() {
        return (int) set.toLongArray()[0];
    }
}
