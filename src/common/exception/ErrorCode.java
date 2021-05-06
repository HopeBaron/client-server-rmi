package common.exception;

public enum ErrorCode {

    INVALID_INFO(1),
    MISSING_ACCESS(2),
    ALREADY_EXIST(3),
    MISSING_PERMISSION(4);

    int code;

    ErrorCode(int code) {
        this.code = code;
    }

}