package common.exception;

public enum ErrorCode {

    INVALID_INFO(1),
    MISSING_ACCESS(2),
    ALREADY_EXIST(3),
    MISSING_PERMISSION(4),
    INACTIVE_ACCOUNT(5),
    INVALID_ARTICLE(6),
    INTERNAL_SERVER_ERROR(7);

    int code;

    ErrorCode(int code) {
        this.code = code;
    }

}