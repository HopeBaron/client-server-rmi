package common.model;

public enum Permission {
    WRITE(2),
    MODIFY_OTHERS(6);

    private int value;

    public int getValue() {
        return value;
    }

    Permission(int value) {
        this.value = value;
    }
}
