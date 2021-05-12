package common.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.BitSet;

public final class Permissions implements Serializable {
    public Permissions(int value) {
        set = BitSet.valueOf(new long[]{value});
    }

    public Permissions() {
        set = new BitSet();
    }

    private final BitSet set;

    public boolean contains(Permission perm) {
        System.out.println(Arrays.toString(set.toByteArray()));
        return set.get(perm.getValue() / 2);

    }

    public boolean contains(Permissions permissions) {
        return set.equals(permissions.set);
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
