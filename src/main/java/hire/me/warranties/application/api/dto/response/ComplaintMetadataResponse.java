package hire.me.warranties.application.api.dto.response;

import hire.me.warranties.domain.complaint.Complaint;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Response object containing metadata of a complaint - does not include the contents of the complaint")
public class ComplaintMetadataResponse {
    @Schema(description = "UUID of the complaint", example = "c1f5e3bc-8c44-4b55-a935-c2d8a7e4e0f5")
    private String complaintId;

    @Schema(description = "UUID of the associated product", example = "a65d86d8-f12b-4d70-8704-671a7c3e132f")
    private String productId;

    @Schema(description = "UUID of the user who reported the complaint", example = "50763b8c-78d1-4031-b106-a8258a221e62")
    private String reporterId;

    @Schema(description = "Country where the complaint was reported", example = "Poland")
    private String country;

    @Schema(description = "Number of reports submitted for this complaint", example = "2137")
    private Integer reportCount;

    public static ComplaintMetadataResponse toComplaintMetadataResponse(Complaint complaint) {
        return new ComplaintMetadataResponse(
                complaint.getId().getValue().toString(),
                complaint.getProductId().getValue().toString(),
                complaint.getReporterId().getValue().toString(),
                complaint.getCountry(),
                complaint.getReportCount()
        );
    }
}
