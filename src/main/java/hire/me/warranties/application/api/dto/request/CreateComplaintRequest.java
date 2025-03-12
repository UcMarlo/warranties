package hire.me.warranties.application.api.dto.request;

import hire.me.warranties.domain.complaint.command.CreateNewComplaintCommand;
import hire.me.warranties.domain.complaint.idendifiers.ProductId;
import hire.me.warranties.domain.complaint.idendifiers.UserId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Request object for creating a new complaint")
public class CreateComplaintRequest {
    @Schema(description = "UUID of the product associated with the complaint", example = "a65d86d8-f12b-4d70-8704-671a7c3e132f", requiredMode = Schema.RequiredMode.REQUIRED)
    private String productId;

    @Schema(description = "UUID of the user reporting the complaint", example = "50763b8c-78d1-4031-b106-a8258a221e62", requiredMode = Schema.RequiredMode.REQUIRED)
    private String reporterId;

    @Schema(description = "Content of the complaint", example = "Example description", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Schema(description = "IP address from where complaint has been sent", example = "192.168.1.1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String sentByIp;

    public CreateNewComplaintCommand toCommand(){
        return new CreateNewComplaintCommand(
                new ProductId(this.productId),
                new UserId(this.reporterId),
                this.content,
                this.sentByIp
        );
    }
}
