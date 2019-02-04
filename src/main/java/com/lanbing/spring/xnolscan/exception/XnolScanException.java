package com.lanbing.spring.xnolscan.exception;

public class XnolScanException extends RuntimeException {

    public XnolScanException(String message) {
        super(message);
    }

    public XnolScanException(String message, Throwable cause) {
        super(message, cause);
    }
}
