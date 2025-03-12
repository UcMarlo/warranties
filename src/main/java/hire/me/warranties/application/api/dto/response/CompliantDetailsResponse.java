package hire.me.warranties.application.api.dto.response;

import hire.me.warranties.domain.complaint.Complaint;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompliantDetailsResponse {
    String complaintId;
    String productId;
    String reporterId;
    String content;
    String country;
    Integer reportCount;

    public static CompliantDetailsResponse fromComplaint(Complaint complaint){
        return new CompliantDetailsResponse(
                complaint.getId().getValue().toString(),
                complaint.getProductId().getValue().toString(),
                complaint.getReporterId().getValue().toString(),
                complaint.getContent().getValue(),
                complaint.getCountry(),
                complaint.getReportCount()
        );
    }
}
