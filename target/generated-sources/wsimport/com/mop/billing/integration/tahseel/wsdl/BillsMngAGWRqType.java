
package com.mop.billing.integration.tahseel.wsdl;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BillsMngAGWRq_Type complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="BillsMngAGWRq_Type">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}MsgRqHdr"/>
 *         <element name="Body" type="{https://www.ecp.mof.gov.sa/agw}BillsMngAGWRqBody_Type"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BillsMngAGWRq_Type", propOrder = {
    "msgRqHdr",
    "body"
})
public class BillsMngAGWRqType {

    @XmlElement(name = "MsgRqHdr", required = true)
    protected MsgRqHdrType msgRqHdr;
    @XmlElement(name = "Body", required = true)
    protected BillsMngAGWRqBodyType body;

    /**
     * Gets the value of the msgRqHdr property.
     * 
     * @return
     *     possible object is
     *     {@link MsgRqHdrType }
     *     
     */
    public MsgRqHdrType getMsgRqHdr() {
        return msgRqHdr;
    }

    /**
     * Sets the value of the msgRqHdr property.
     * 
     * @param value
     *     allowed object is
     *     {@link MsgRqHdrType }
     *     
     */
    public void setMsgRqHdr(MsgRqHdrType value) {
        this.msgRqHdr = value;
    }

    /**
     * Gets the value of the body property.
     * 
     * @return
     *     possible object is
     *     {@link BillsMngAGWRqBodyType }
     *     
     */
    public BillsMngAGWRqBodyType getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillsMngAGWRqBodyType }
     *     
     */
    public void setBody(BillsMngAGWRqBodyType value) {
        this.body = value;
    }

}
