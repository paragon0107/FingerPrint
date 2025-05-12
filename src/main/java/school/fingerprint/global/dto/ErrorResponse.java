package school.fingerprint.global.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import school.fingerprint.global.exception.ErrorCode;
import school.fingerprint.global.exception.FfException;

import static school.fingerprint.global.exception.ErrorCode.NULL_DATA_ERROR;

public record ErrorResponse(
        ErrorCode errorCode
) {
    public static ResponseEntity<ErrorResponse> of(final HttpStatus status, final ErrorCode errorCode) {
        if (status == null || errorCode == null) {
            throw new FfException(NULL_DATA_ERROR);
        }
        return ResponseEntity.status(status.value())
                .body(new ErrorResponse(errorCode));
    }
}