package hire.me.warranties.application.api.dto;

import hire.me.warranties.domain.command.CreateNewComplaintCommand;
import hire.me.warranties.domain.idendifiers.ProductId;
import hire.me.warranties.domain.idendifiers.UserId;
import lombok.Data;

@Data
public class CreateComplaintRequest {

    private String productId;
    private String reporterId;
    private String content;
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