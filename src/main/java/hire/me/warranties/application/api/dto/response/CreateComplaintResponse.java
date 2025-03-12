package hire.me.warranties.application.api.dto.response;

import hire.me.warranties.domain.complaint.idendifiers.ComplaintId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Response containing the id of the created compliant")
public class CreateComplaintResponse {
    @Schema(description = "UUID of the complaint", example = "c1f5e3bc-8c44-4b55-a935-c2d8a7e4e0f5")
    String compliantId;

    public static CreateComplaintResponse fromCompliantId(ComplaintId complaintId){
        return new CreateComplaintResponse(complaintId.getValue().toString());
    }
}
