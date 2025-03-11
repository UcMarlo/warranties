package hire.me.warranties.application;

import hire.me.warranties.domain.complaint.Complaint;
import hire.me.warranties.domain.complaint.ComplaintContent;
import hire.me.warranties.domain.complaint.ComplaintRepository;
import hire.me.warranties.domain.geolocation.IpGeolocationPort;
import hire.me.warranties.domain.idendifiers.ComplaintId;
import hire.me.warranties.domain.idendifiers.ProductId;
import hire.me.warranties.domain.idendifiers.UserId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComplaintService {
    private final ComplaintRepository complaintRepository;
    private final IpGeolocationPort ipGeolocationPort;

    @Transactional
    public ComplaintId registerNewComplaint(ProductId productId, UserId userId, String description, String ip){
        Optional<Complaint> optionalComplaint = complaintRepository.findByProductIdAndReporterId(productId, userId);
        if (optionalComplaint.isPresent()) {
            Complaint complaint = optionalComplaint.get();
            complaint.incrementReportCount();
            return complaint.getId();
        }
        String country = ipGeolocationPort.findCountryByIP(ip).getCountry();

        Complaint complaint = new Complaint(productId, new ComplaintContent(description), userId, country);
        complaintRepository.save(complaint);
        return complaint.getId();
    }
}
