
package com.mop.billing.integration.tahseel.wsdl;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BillsMngAGWRs_Type complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="BillsMngAGWRs_Type">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}MsgRsHdr"/>
 *         <element name="Body" type="{https://www.ecp.mof.gov.sa/agw}BillsMngAGWRsBody_Type" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BillsMngAGWRs_Type", propOrder = {
    "msgRsHdr",
    "body"
})
public class BillsMngAGWRsType {

    @XmlElement(name = "MsgRsHdr", required = true)
    protected MsgRsHdrType msgRsHdr;
    @XmlElement(name = "Body")
    protected BillsMngAGWRsBodyType body;

    /**
     * Gets the value of the msgRsHdr property.
     * 
     * @return
     *     possible object is
     *     {@link MsgRsHdrType }
     *     
     */
    public MsgRsHdrType getMsgRsHdr() {
        return msgRsHdr;
    }

    /**
     * Sets the value of the msgRsHdr property.
     * 
     * @param value
     *     allowed object is
     *     {@link MsgRsHdrType }
     *     
     */
    public void setMsgRsHdr(MsgRsHdrType value) {
        this.msgRsHdr = value;
    }

    /**
     * Gets the value of the body property.
     * 
     * @return
     *     possible object is
     *     {@link BillsMngAGWRsBodyType }
     *     
     */
    public BillsMngAGWRsBodyType getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillsMngAGWRsBodyType }
     *     
     */
    public void setBody(BillsMngAGWRsBodyType value) {
        this.body = value;
    }

}
