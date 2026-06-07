
package com.mop.billing.integration.tahseel.wsdl;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BillsMngAGWRsBody_Type complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="BillsMngAGWRsBody_Type">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}BillIBAN" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BillsMngAGWRsBody_Type", propOrder = {
    "billIBAN"
})
public class BillsMngAGWRsBodyType {

    /**
     * Bill IBAN
     * 
     */
    @XmlElement(name = "BillIBAN")
    protected String billIBAN;

    /**
     * Bill IBAN
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillIBAN() {
        return billIBAN;
    }

    /**
     * Sets the value of the billIBAN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getBillIBAN()
     */
    public void setBillIBAN(String value) {
        this.billIBAN = value;
    }

}
