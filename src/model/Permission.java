package model;

public enum Permission {
    READ(1),
    WRITE(2),
    MODIFY(4),
    MODIFY_OTHERS(6);

    private int value;

    public int getValue() {
        return value;
    }

    Permission(int value) {
        this.value = value;
    }
}
