package common.exception;

public class InvalidArticleException extends RemoteAuthenticationException {
    public InvalidArticleException() {
        super(ErrorCode.INVALID_ARTICLE,"Invalid content or title. Please make sure there is no spaces.");
    }
}