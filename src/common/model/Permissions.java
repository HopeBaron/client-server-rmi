package common.model;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.BitSet;

public final class Permissions implements Serializable {
    public Permissions(int value) {
        ByteBuffer buf = ByteBuffer.allocate(4);
        buf.putInt(value);
        set = BitSet.valueOf(buf);
    }

    public Permissions() {
        set = new BitSet();
    }

    private final BitSet set;

    public boolean contains(Permission perm) {
        return set.get(perm.getValue() / 2);
    }

    public boolean contains(Permissions permissions) {
        return set.equals(permissions);
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

    public void setValue(Permissions permissions) {
        set.xor(permissions.set);
    }
}
