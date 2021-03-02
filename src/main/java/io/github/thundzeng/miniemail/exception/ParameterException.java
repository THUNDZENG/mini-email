package io.github.thundzeng.miniemail.exception;

/**
 * 参数检查异常
 *
 * @author thundzeng
 */
public class ParameterException extends RuntimeException {
    public ParameterException(String message) {
        super(message);
    }
}
