package hire.me.warranties.domain

import spock.lang.Specification
import hire.me.warranties.domain.complaint.ComplaintContent

class ComplaintContentSpec extends Specification {

    def "should create a valid ComplaintContent"() {
        given:
        def validContent = "This is a valid complaint content."

        when:
        def complaintContent = new ComplaintContent(validContent)

        then:
        complaintContent.value == validContent
    }

    def "should throw IllegalArgumentException if content is null"() {
        when:
        new ComplaintContent(null)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Content cannot be blank"
    }

    def "should throw IllegalArgumentException if content is blank"() {
        when:
        new ComplaintContent("")

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Content cannot be blank"
    }

    def "should throw IllegalArgumentException if content exceeds 4000 characters"() {
        given:
        def contentExceedingTheLimit = "A" * 4001

        when:
        new ComplaintContent(contentExceedingTheLimit)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "Content cannot exceed 4000 characters"
    }
}
