package exception;

import common.ErrorCode;

public class ProductException extends RuntimeException{
    private final ErrorCode error;

    public ProductException(ErrorCode error) {
        super(error.getMsg());
        this.error = error;
}
}
