package school.fingerprint.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ErrorCode {
    NULL_DATA_ERROR("NULL_DATA_ERROR")
    ;
    private final String message;
}
