package hire.me.warranties.application.api;

import hire.me.warranties.application.api.dto.request.CreateComplaintRequest;
import hire.me.warranties.application.api.dto.request.UpdateComplaintContentRequest;
import hire.me.warranties.application.api.dto.response.ComplaintMetadataResponse;
import hire.me.warranties.application.api.dto.response.CompliantDetailsResponse;
import hire.me.warranties.application.api.dto.response.CreateComplaintResponse;
import hire.me.warranties.application.service.ComplaintService;
import hire.me.warranties.domain.complaint.idendifiers.ComplaintId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/complaint")
@RequiredArgsConstructor
@Tag(name = "Complaints", description = "API for managing complaints")
public class ComplaintsRestController {
    private final ComplaintService complaintService;

    @Operation(summary = "Create a new complaint", description = "Register a new complaint with product, reporter, and content details." +
            " If Same pair of productId and reporterId has been found in already existing complaint, request count is incremented")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Complaint created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload, validation errors",
                    content = @Content( schema = @Schema(type = "string", example = "Something something validation")))
    })
    @PostMapping()
    public ResponseEntity<CreateComplaintResponse> createNewComplaint(
            @RequestBody CreateComplaintRequest createComplaintRequest) {
        return ResponseEntity.ok(CreateComplaintResponse.fromCompliantId(
                complaintService.registerNewComplaint(createComplaintRequest.toCommand())
        ));
    }

    @Operation(summary = "Get complaint details by ID", description = "Retrieve the details of a specific complaint using its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Compliant is returned"),
            @ApiResponse(responseCode = "404", description = "Complaint not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CompliantDetailsResponse> getComplaintDetails(
            @PathVariable @Parameter(description = "UUID of the complaint to retrieve") String id) {
        return ResponseEntity.ok(CompliantDetailsResponse.fromComplaint(
                complaintService.findCompliantByComplaintId(new ComplaintId(id))
        ));
    }

    @Operation(summary = "Get a list of complaints", description = "Retrieve a paginated list of complaints metadata.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Complaints list retrieved")
    })
    @GetMapping("/list")
    public ResponseEntity<Page<ComplaintMetadataResponse>> getComplaints(
            @Parameter(description = "Pagination information") Pageable pageable) {
        return ResponseEntity.ok(complaintService.getAllComplaints(pageable)
                .map(ComplaintMetadataResponse::toComplaintMetadataResponse));
    }

    @Operation(summary = "Update complaint content", description = "Update the content of an existing complaint.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Complaint updated successfully"),
            @ApiResponse(responseCode = "404", description = "Complaint not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload, validation errors",
                    content = @Content( schema = @Schema(type = "string", example = "Something something validation")))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateComplaint(
            @PathVariable @Parameter(description = "ID of the complaint to update") String id,
            @RequestBody UpdateComplaintContentRequest updateComplaintContentRequest) {
        complaintService.updateComplaint(
                new ComplaintId(id),
                updateComplaintContentRequest.getContent());
        return ResponseEntity.noContent().build();
    }

}
