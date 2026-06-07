
package com.mop.billing.integration.tahseel.wsdl;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PartyId_Type complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="PartyId_Type">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}PartyIdNum" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}PartyIdType" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartyId_Type", propOrder = {
    "partyIdNum",
    "partyIdType"
})
public class PartyIdType {

    /**
     * Party identifier
     * 
     */
    @XmlElement(name = "PartyIdNum")
    protected String partyIdNum;
    /**
     * Party identifier Type
     * 
     */
    @XmlElement(name = "PartyIdType")
    protected String partyIdType;

    /**
     * Party identifier
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartyIdNum() {
        return partyIdNum;
    }

    /**
     * Sets the value of the partyIdNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getPartyIdNum()
     */
    public void setPartyIdNum(String value) {
        this.partyIdNum = value;
    }

    /**
     * Party identifier Type
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartyIdType() {
        return partyIdType;
    }

    /**
     * Sets the value of the partyIdType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getPartyIdType()
     */
    public void setPartyIdType(String value) {
        this.partyIdType = value;
    }

}
