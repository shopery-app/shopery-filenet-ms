package az.shopery.filenet_ms.model.dto.shared;

import static az.shopery.filenet_ms.util.constants.PatternConstants.EXCEPTION_TIMESTAMP_PATTERN;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponse {

    HttpStatus status;

    int statusCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = EXCEPTION_TIMESTAMP_PATTERN)
    LocalDateTime timestamp;

    String path;
    String message;

    Map<String, String> errors;
}
