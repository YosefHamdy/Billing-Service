
package com.mop.billing.integration.tahseel.wsdl;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PartnerInfo_Type complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="PartnerInfo_Type">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}PartnerId"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}PartnerType"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}PartnerCode" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartnerInfo_Type", propOrder = {
    "partnerId",
    "partnerType",
    "partnerCode"
})
public class PartnerInfoType {

    /**
     * Partner Identifier
     * 
     */
    @XmlElement(name = "PartnerId", required = true)
    protected String partnerId;
    /**
     * Partner Type
     * 
     */
    @XmlElement(name = "PartnerType", required = true)
    @XmlSchemaType(name = "string")
    protected PartnerTypeType partnerType;
    /**
     * Onboarded Partner Code
     * 
     */
    @XmlElement(name = "PartnerCode")
    protected String partnerCode;

    /**
     * Partner Identifier
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerId() {
        return partnerId;
    }

    /**
     * Sets the value of the partnerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getPartnerId()
     */
    public void setPartnerId(String value) {
        this.partnerId = value;
    }

    /**
     * Partner Type
     * 
     * @return
     *     possible object is
     *     {@link PartnerTypeType }
     *     
     */
    public PartnerTypeType getPartnerType() {
        return partnerType;
    }

    /**
     * Sets the value of the partnerType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartnerTypeType }
     *     
     * @see #getPartnerType()
     */
    public void setPartnerType(PartnerTypeType value) {
        this.partnerType = value;
    }

    /**
     * Onboarded Partner Code
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerCode() {
        return partnerCode;
    }

    /**
     * Sets the value of the partnerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getPartnerCode()
     */
    public void setPartnerCode(String value) {
        this.partnerCode = value;
    }

}
