
package com.mop.billing.integration.tahseel.wsdl;

import java.math.BigDecimal;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PmtOptions_Type complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="PmtOptions_Type">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}MinPartialPmtAmt" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}MinAdvancePmtAmt" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}MaxAdvancePmtAmt" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PmtOptions_Type", propOrder = {
    "minPartialPmtAmt",
    "minAdvancePmtAmt",
    "maxAdvancePmtAmt"
})
public class PmtOptionsType {

    @XmlElement(name = "MinPartialPmtAmt")
    protected BigDecimal minPartialPmtAmt;
    @XmlElement(name = "MinAdvancePmtAmt")
    protected BigDecimal minAdvancePmtAmt;
    @XmlElement(name = "MaxAdvancePmtAmt")
    protected BigDecimal maxAdvancePmtAmt;

    /**
     * Gets the value of the minPartialPmtAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMinPartialPmtAmt() {
        return minPartialPmtAmt;
    }

    /**
     * Sets the value of the minPartialPmtAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMinPartialPmtAmt(BigDecimal value) {
        this.minPartialPmtAmt = value;
    }

    /**
     * Gets the value of the minAdvancePmtAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMinAdvancePmtAmt() {
        return minAdvancePmtAmt;
    }

    /**
     * Sets the value of the minAdvancePmtAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMinAdvancePmtAmt(BigDecimal value) {
        this.minAdvancePmtAmt = value;
    }

    /**
     * Gets the value of the maxAdvancePmtAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMaxAdvancePmtAmt() {
        return maxAdvancePmtAmt;
    }

    /**
     * Sets the value of the maxAdvancePmtAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMaxAdvancePmtAmt(BigDecimal value) {
        this.maxAdvancePmtAmt = value;
    }

}
