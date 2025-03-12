package hire.me.warranties.domain.idendifiers

import hire.me.warranties.domain.complaint.idendifiers.ComplaintId
import spock.lang.Specification


class ComplaintIdSpec extends Specification {

    def "should create a valid ComplaintId with random UUID"(){
        when:
        def complaintId = new ComplaintId()

        then:
        complaintId != null
        complaintId.value != null
    }

    def "should create a valid ComplaintId with given UUID"(){
        given:
        def uuidValue = "1e603af2-c005-45dd-bbc0-514923a5669b"

        when:
        def complaintId = new ComplaintId(uuidValue)

        then:
        complaintId != null
        complaintId.value == UUID.fromString(uuidValue)
    }

}