
package com.health.client;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.health.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CalculateBMI_QNAME = new QName("http://service.health.com/", "calculateBMI");
    private final static QName _CalculateBMIResponse_QNAME = new QName("http://service.health.com/", "calculateBMIResponse");
    private final static QName _CalculateBMR_QNAME = new QName("http://service.health.com/", "calculateBMR");
    private final static QName _CalculateBMRResponse_QNAME = new QName("http://service.health.com/", "calculateBMRResponse");
    private final static QName _CalculateBodyFat_QNAME = new QName("http://service.health.com/", "calculateBodyFat");
    private final static QName _CalculateBodyFatResponse_QNAME = new QName("http://service.health.com/", "calculateBodyFatResponse");
    private final static QName _CalculateCalories_QNAME = new QName("http://service.health.com/", "calculateCalories");
    private final static QName _CalculateCaloriesResponse_QNAME = new QName("http://service.health.com/", "calculateCaloriesResponse");
    private final static QName _CalculateLBM_QNAME = new QName("http://service.health.com/", "calculateLBM");
    private final static QName _CalculateLBMResponse_QNAME = new QName("http://service.health.com/", "calculateLBMResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.health.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CalculateBMI }
     * 
     */
    public CalculateBMI createCalculateBMI() {
        return new CalculateBMI();
    }

    /**
     * Create an instance of {@link CalculateBMIResponse }
     * 
     */
    public CalculateBMIResponse createCalculateBMIResponse() {
        return new CalculateBMIResponse();
    }

    /**
     * Create an instance of {@link CalculateBMR }
     * 
     */
    public CalculateBMR createCalculateBMR() {
        return new CalculateBMR();
    }

    /**
     * Create an instance of {@link CalculateBMRResponse }
     * 
     */
    public CalculateBMRResponse createCalculateBMRResponse() {
        return new CalculateBMRResponse();
    }

    /**
     * Create an instance of {@link CalculateBodyFat }
     * 
     */
    public CalculateBodyFat createCalculateBodyFat() {
        return new CalculateBodyFat();
    }

    /**
     * Create an instance of {@link CalculateBodyFatResponse }
     * 
     */
    public CalculateBodyFatResponse createCalculateBodyFatResponse() {
        return new CalculateBodyFatResponse();
    }

    /**
     * Create an instance of {@link CalculateCalories }
     * 
     */
    public CalculateCalories createCalculateCalories() {
        return new CalculateCalories();
    }

    /**
     * Create an instance of {@link CalculateCaloriesResponse }
     * 
     */
    public CalculateCaloriesResponse createCalculateCaloriesResponse() {
        return new CalculateCaloriesResponse();
    }

    /**
     * Create an instance of {@link CalculateLBM }
     * 
     */
    public CalculateLBM createCalculateLBM() {
        return new CalculateLBM();
    }

    /**
     * Create an instance of {@link CalculateLBMResponse }
     * 
     */
    public CalculateLBMResponse createCalculateLBMResponse() {
        return new CalculateLBMResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CalculateBMI }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CalculateBMI }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.health.com/", name = "calculateBMI")
    public JAXBElement<CalculateBMI> createCalculateBMI(CalculateBMI value) {
        return new JAXBElement<CalculateBMI>(_CalculateBMI_QNAME, CalculateBMI.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CalculateBMIResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CalculateBMIResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.health.com/", name = "calculateBMIResponse")
    public JAXBElement<CalculateBMIResponse> createCalculateBMIResponse(CalculateBMIResponse value) {
        return new JAXBElement<CalculateBMIResponse>(_CalculateBMIResponse_QNAME, CalculateBMIResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CalculateBMR }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CalculateBMR }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.health.com/", name = "calculateBMR")
    public JAXBElement<CalculateBMR> createCalculateBMR(CalculateBMR value) {
        return new JAXBElement<CalculateBMR>(_CalculateBMR_QNAME, CalculateBMR.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CalculateBMRResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CalculateBMRResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.health.com/", name = "calculateBMRResponse")
    public JAXBElement<CalculateBMRResponse> createCalculateBMRResponse(CalculateBMRResponse value) {
        return new JAXBElement<CalculateBMRResponse>(_CalculateBMRResponse_QNAME, CalculateBMRResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CalculateBodyFat }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CalculateBodyFat }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.health.com/", name = "calculateBodyFat")
    public JAXBElement<CalculateBodyFat> createCalculateBodyFat(CalculateBodyFat value) {
        return new JAXBElement<CalculateBodyFat>(_CalculateBodyFat_QNAME, CalculateBodyFat.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CalculateBodyFatResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CalculateBodyFatResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.health.com/", name = "calculateBodyFatResponse")
    public JAXBElement<CalculateBodyFatResponse> createCalculateBodyFatResponse(CalculateBodyFatResponse value) {
        return new JAXBElement<CalculateBodyFatResponse>(_CalculateBodyFatResponse_QNAME, CalculateBodyFatResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CalculateCalories }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CalculateCalories }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.health.com/", name = "calculateCalories")
    public JAXBElement<CalculateCalories> createCalculateCalories(CalculateCalories value) {
        return new JAXBElement<CalculateCalories>(_CalculateCalories_QNAME, CalculateCalories.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CalculateCaloriesResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CalculateCaloriesResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.health.com/", name = "calculateCaloriesResponse")
    public JAXBElement<CalculateCaloriesResponse> createCalculateCaloriesResponse(CalculateCaloriesResponse value) {
        return new JAXBElement<CalculateCaloriesResponse>(_CalculateCaloriesResponse_QNAME, CalculateCaloriesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CalculateLBM }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CalculateLBM }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.health.com/", name = "calculateLBM")
    public JAXBElement<CalculateLBM> createCalculateLBM(CalculateLBM value) {
        return new JAXBElement<CalculateLBM>(_CalculateLBM_QNAME, CalculateLBM.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CalculateLBMResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CalculateLBMResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.health.com/", name = "calculateLBMResponse")
    public JAXBElement<CalculateLBMResponse> createCalculateLBMResponse(CalculateLBMResponse value) {
        return new JAXBElement<CalculateLBMResponse>(_CalculateLBMResponse_QNAME, CalculateLBMResponse.class, null, value);
    }

}
