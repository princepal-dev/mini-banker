package common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    private CommonResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
    
    public static <T> CommonResponse<T> success(String message, T data) {
        return new CommonResponse<>(true, message, data);
    }

    public static <T> CommonResponse<T> success(String message) {
        return new CommonResponse<>(true, message, null);
    }

    public static <T> CommonResponse<T> error(String message, T data) {
        return new CommonResponse<>(false, message, data);
    }

    public static <T> CommonResponse<T> error(String message) {
        return new CommonResponse<>(false, message, null);
    }
}
