package hire.me.warranties.application.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request object for creating a new complaint")
public class UpdateComplaintContentRequest {
    @Schema(description = "Content of the complaint", example = "Example description", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;
}
