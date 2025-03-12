package hire.me.warranties.application.api.dto.response;

import hire.me.warranties.domain.complaint.Complaint;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ComplaintMetadataResponse {
    String complaintId;
    String productId;
    String reporterId;
    String country;
    Integer reportCount;

    public static ComplaintMetadataResponse toComplaintMetadataResponse(Complaint complaint){
        return new ComplaintMetadataResponse(
                complaint.getId().getValue().toString(),
                complaint.getProductId().getValue().toString(),
                complaint.getReporterId().getValue().toString(),
                complaint.getCountry(),
                complaint.getReportCount()
        );
    }
}
