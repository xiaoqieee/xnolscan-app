package com.lanbing.spring.xnolscan.common;

public enum ResponseStatus {

    SUCCESS(1), FAILURE(0);

    int status;

    ResponseStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
