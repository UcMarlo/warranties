package hire.me.warranties.application.api.dto;

import hire.me.warranties.domain.complaint.idendifiers.ComplaintId;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateComplaintResponse {
    String compliantId;

    public static CreateComplaintResponse fromCompliantId(ComplaintId complaintId){
        return new CreateComplaintResponse(complaintId.getValue().toString());
    }
}
