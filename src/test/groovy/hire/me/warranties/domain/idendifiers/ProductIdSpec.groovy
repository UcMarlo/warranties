package hire.me.warranties.domain.idendifiers

import hire.me.warranties.domain.complaint.idendifiers.ProductId
import spock.lang.Specification


class ProductIdSpec extends Specification {

    def "should create a valid ProductId with given UUID"(){
        given:
        def uuidValue = "1e603af2-c005-45dd-bbc0-514923a5669b"

        when:
        def productId = new ProductId(uuidValue)

        then:
        productId != null
        productId.value == UUID.fromString(uuidValue)
    }

}