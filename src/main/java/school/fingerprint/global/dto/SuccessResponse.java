package school.fingerprint.global.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import school.fingerprint.global.exception.FfException;

import static school.fingerprint.global.exception.ErrorCode.NULL_DATA_ERROR;

public record SuccessResponse<T>(
        String message,
        T data
) {
    public static <T> ResponseEntity<SuccessResponse<T>> of(final HttpStatus status, final String message, final T data) {
        if (status == null || message == null || data == null) {
            throw new FfException(NULL_DATA_ERROR);
        }
        return ResponseEntity.status(status.value())
                .body(new SuccessResponse<T>(message, data));
    }

    public static ResponseEntity<SuccessResponse<?>> of(final HttpStatus status, final String message) {
        if (status == null || message == null) {
            throw new FfException(NULL_DATA_ERROR);
        }
        return ResponseEntity.status(status.value())
                .body(new SuccessResponse<>(message, null));
    }
}