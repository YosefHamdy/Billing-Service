
package com.mop.billing.integration.tahseel.wsdl;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BenInfo_Type complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="BenInfo_Type">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}BenPOI" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}BenName" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BenInfo_Type", propOrder = {
    "benPOI",
    "benName"
})
public class BenInfoType {

    @XmlElement(name = "BenPOI")
    protected POIType benPOI;
    @XmlElement(name = "BenName")
    protected String benName;

    /**
     * Gets the value of the benPOI property.
     * 
     * @return
     *     possible object is
     *     {@link POIType }
     *     
     */
    public POIType getBenPOI() {
        return benPOI;
    }

    /**
     * Sets the value of the benPOI property.
     * 
     * @param value
     *     allowed object is
     *     {@link POIType }
     *     
     */
    public void setBenPOI(POIType value) {
        this.benPOI = value;
    }

    /**
     * Gets the value of the benName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBenName() {
        return benName;
    }

    /**
     * Sets the value of the benName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBenName(String value) {
        this.benName = value;
    }

}
