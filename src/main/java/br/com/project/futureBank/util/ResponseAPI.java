package br.com.project.futureBank.util;

public class ResponseAPI<T> {

    private T data;
    private String message;
    private boolean success;

    public ResponseAPI(T data, String message, boolean success) {
        this.data = data;
        this.message = message;
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
