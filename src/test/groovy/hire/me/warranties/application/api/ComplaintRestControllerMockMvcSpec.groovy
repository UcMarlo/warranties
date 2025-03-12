package hire.me.warranties.application.api

import hire.me.warranties.application.service.ComplaintService
import hire.me.warranties.domain.complaint.Complaint
import hire.me.warranties.domain.complaint.ComplaintContent
import hire.me.warranties.domain.complaint.exception.ComplaintNotFoundException
import hire.me.warranties.domain.complaint.idendifiers.ComplaintId
import hire.me.warranties.domain.complaint.idendifiers.ProductId
import hire.me.warranties.domain.complaint.idendifiers.UserId
import org.spockframework.spring.SpringBean
import org.springframework.data.domain.PageImpl
import org.springframework.test.web.servlet.MockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.data.domain.PageRequest
import spock.lang.Specification
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@WebMvcTest
@Import(value = [RestExceptionHandler.class, ComplaintsRestController.class])
class ComplaintRestControllerMockMvcSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    static final def ENDPOINT_PATH = '/api/v1/complaint'

    @SpringBean
    ComplaintService complaintService = Mock()

    //region POST /api/v1/complaint

    def "should return 200 and complaintId when creation has been successful"() {
        given:
        def requestBody = '''
        {
            "productId": "a65d86d8-f12b-4d70-8704-671a7c3e132f",
            "reporterId": "50763b8c-78d1-4031-b106-a8258a221e62",
            "content": "The product is defective beyond repair.",
            "sentByIp": "192.168.1.1"
        }
        '''
        def responseBody = '''{"compliantId":"c1f5e3bc-8c44-4b55-a935-c2d8a7e4e0f5"}'''

        complaintService.registerNewComplaint(_) >> new ComplaintId("c1f5e3bc-8c44-4b55-a935-c2d8a7e4e0f5")

        when:
        def result = mockMvc.perform(post(ENDPOINT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))

        then:
        result.andExpect(status().isOk())
                .andExpect(content().json(responseBody))
    }

    def "should return 400 and complaintId when an validation exception as been thrown"() {
        given:
        def requestBody = '''
        {
            "productId": "a65d86d8-f12b-4d70-8704-671a7c3e132f",
            "reporterId": "50763b8c-78d1-4031-b106-a8258a221e62",
            "content": null,
            "sentByIp": "192.168.1.1"
        }
        '''
        def expectedResponseBody = 'Content cannot be null or blank'

        complaintService.registerNewComplaint(_) >> { throw new IllegalArgumentException(expectedResponseBody) }

        when:
        def result = mockMvc.perform(post(ENDPOINT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))

        then:
        result.andExpect(status().isBadRequest())
                .andExpect(content().string(expectedResponseBody))
    }

    //endregion

    //region GET /api/v1/complaint/{id}


    def "should get complaint details by ID and return 200 with response"() {
        given:
        def complaintId = "c1f5e3bc-8c44-4b55-a935-c2d8a7e4e0f5"
        def responseBody = '''
        {
            "complaintId": "c1f5e3bc-8c44-4b55-a935-c2d8a7e4e0f5",
            "productId": "a65d86d8-f12b-4d70-8704-671a7c3e132f",
            "reporterId": "50763b8c-78d1-4031-b106-a8258a221e62",
            "content": "Dog ate the product, please respond.",
            "country": "Poland",
            "reportCount": 2137
        }
        '''
        Complaint compliantMock = Mock()
        compliantMock.getId() >> new ComplaintId(complaintId)
        compliantMock.getProductId() >> new ProductId("a65d86d8-f12b-4d70-8704-671a7c3e132f")
        compliantMock.getReporterId() >> new UserId("50763b8c-78d1-4031-b106-a8258a221e62")
        compliantMock.getContent() >> new ComplaintContent("Dog ate the product, please respond.")
        compliantMock.getCountry() >> "Poland"
        compliantMock.getReportCount() >> 2137

        and:
        complaintService.findCompliantByComplaintId(_) >> compliantMock

        when:
        def result = mockMvc.perform(get("${ENDPOINT_PATH}/${complaintId}")
                .accept(MediaType.APPLICATION_JSON))

        then:
        result.andExpect(status().isOk())
                .andExpect(content().json(responseBody))
    }

    def "should return 404 when ComplaintNotFoundException has been thrown"() {
        given:
        def complaintId = "c1f5e3bc-8c44-4b55-a935-c2d8a7e4e0f5"

        and:
        complaintService.findCompliantByComplaintId(_) >> { throw new ComplaintNotFoundException("Complaint not found") }

        when:
        def result = mockMvc.perform(get("${ENDPOINT_PATH}/${complaintId}")
                .accept(MediaType.APPLICATION_JSON))

        then:
        result.andExpect(status().isNotFound())
    }

    //endregion

    //region PUT /api/v1/complaint/{id}

    def "should return 204 when complaint has been successfully updated"() {
        given:
        def complaintId = "c1f5e3bc-8c44-4b55-a935-c2d8a7e4e0f5"
        def requestBody = '''
        {
            "content": "Never mind, it works fine after restart."
        }
        '''

        when:
        def result = mockMvc.perform(put("${ENDPOINT_PATH}/${complaintId}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))

        then:
        result.andExpect(status().isNoContent())
    }

    def "should return 404 when trying to update a complaint that does not exist"() {
        given:
        def complaintId = "c1f5e3bc-8c44-4b55-a935-c2d8a7e4e0f5"
        def requestBody = '''
        {
            "content": "It seems like I forgot to create the complaint beforehand..."
        }
        '''

        and:
        complaintService.updateComplaint(_, _) >> { throw new ComplaintNotFoundException("Complaint not found") }

        when:
        def result = mockMvc.perform(put("${ENDPOINT_PATH}/${complaintId}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))

        then:
        result.andExpect(status().isNotFound())
    }

    def "should return 400 when a validation exception occurred during updating compliant"() {
        given:
        def complaintId = "c1f5e3bc-8c44-4b55-a935-c2d8a7e4e0f5"
        def requestBody = '''
        {
            "content": null
        }
        '''
        def expectedResponseBody = 'Content cannot be null or blank'

        and:
        complaintService.updateComplaint(_, _) >> { throw new IllegalArgumentException(expectedResponseBody) }

        when:
        def result = mockMvc.perform(put("${ENDPOINT_PATH}/${complaintId}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))

        then:
        result.andExpect(status().isBadRequest())
                .andExpect(content().string(expectedResponseBody))
    }

    //endregion

    //region GET /api/v1/complaint/list

    def "should return 200 and list a list of complaints"() {
        given:
        Complaint compliantMock = Mock()
        compliantMock.getId() >> new ComplaintId("c1f5e3bc-8c44-4b55-a935-c2d8a7e4e0f5")
        compliantMock.getProductId() >> new ProductId("a65d86d8-f12b-4d70-8704-671a7c3e132f")
        compliantMock.getReporterId() >> new UserId("50763b8c-78d1-4031-b106-a8258a221e62")
        compliantMock.getCountry() >> "Poland"
        compliantMock.getReportCount() >> 2137

        Complaint compliantMock2 = Mock()
        compliantMock2.getId() >> new ComplaintId("c1f5e3bc-8c44-4b55-a935-c2d8a7e4e0f5")
        compliantMock2.getProductId() >> new ProductId("a65d86d8-f12b-4d70-8704-671a7c3e132f")
        compliantMock2.getReporterId() >> new UserId("50763b8c-78d1-4031-b106-a8258a221e62")
        compliantMock2.getCountry() >> "United States of America"
        compliantMock2.getReportCount() >> 1

        complaintService.getAllComplaints(_) >> new PageImpl<>(
                [compliantMock, compliantMock2],
                PageRequest.of(0, 2),
                2
        )

        when:
        def result = mockMvc.perform(get("${ENDPOINT_PATH}/list")
                .accept(MediaType.APPLICATION_JSON))

        then:
        result.andExpect(status().isOk())
                .andExpect(jsonPath("\$.content[0].complaintId").value("c1f5e3bc-8c44-4b55-a935-c2d8a7e4e0f5"))
                .andExpect(jsonPath("\$.content[0].productId").value("a65d86d8-f12b-4d70-8704-671a7c3e132f"))
                .andExpect(jsonPath("\$.content[0].reporterId").value("50763b8c-78d1-4031-b106-a8258a221e62"))
                .andExpect(jsonPath("\$.content[0].country").value("Poland"))
                .andExpect(jsonPath("\$.content[0].reportCount").value(2137))
                .andExpect(jsonPath("\$.content[1].complaintId").value("c1f5e3bc-8c44-4b55-a935-c2d8a7e4e0f5"))
                .andExpect(jsonPath("\$.content[1].productId").value("a65d86d8-f12b-4d70-8704-671a7c3e132f"))
                .andExpect(jsonPath("\$.content[1].reporterId").value("50763b8c-78d1-4031-b106-a8258a221e62"))
                .andExpect(jsonPath("\$.content[1].country").value("United States of America"))
                .andExpect(jsonPath("\$.content[1].reportCount").value(1))
    }

    //endregion

}