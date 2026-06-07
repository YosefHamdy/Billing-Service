
package com.mop.billing.integration.tahseel.wsdl;

import java.math.BigDecimal;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RevenueEntryInfo_Type complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="RevenueEntryInfo_Type">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}BenAgencyId"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}GFSCode"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}Amt"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RevenueEntryInfo_Type", propOrder = {
    "benAgencyId",
    "gfsCode",
    "amt"
})
public class RevenueEntryInfoType {

    /**
     *  17 Digit Agency ID
     * 
     */
    @XmlElement(name = "BenAgencyId", required = true)
    protected String benAgencyId;
    /**
     * GFS Code
     * 
     */
    @XmlElement(name = "GFSCode", required = true)
    protected String gfsCode;
    @XmlElement(name = "Amt", required = true)
    protected BigDecimal amt;

    /**
     *  17 Digit Agency ID
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBenAgencyId() {
        return benAgencyId;
    }

    /**
     * Sets the value of the benAgencyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getBenAgencyId()
     */
    public void setBenAgencyId(String value) {
        this.benAgencyId = value;
    }

    /**
     * GFS Code
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGFSCode() {
        return gfsCode;
    }

    /**
     * Sets the value of the gfsCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getGFSCode()
     */
    public void setGFSCode(String value) {
        this.gfsCode = value;
    }

    /**
     * Gets the value of the amt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmt() {
        return amt;
    }

    /**
     * Sets the value of the amt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmt(BigDecimal value) {
        this.amt = value;
    }

}
