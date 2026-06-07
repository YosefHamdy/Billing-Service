
package com.mop.billing.integration.tahseel.wsdl;

import java.math.BigDecimal;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BillSummInfo_Type complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="BillSummInfo_Type">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}Amt"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}AmtCode"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BillSummInfo_Type", propOrder = {
    "amt",
    "amtCode"
})
public class BillSummInfoType {

    @XmlElement(name = "Amt", required = true)
    protected BigDecimal amt;
    @XmlElement(name = "AmtCode", required = true)
    @XmlSchemaType(name = "string")
    protected AmtCodeType amtCode;

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

    /**
     * Gets the value of the amtCode property.
     * 
     * @return
     *     possible object is
     *     {@link AmtCodeType }
     *     
     */
    public AmtCodeType getAmtCode() {
        return amtCode;
    }

    /**
     * Sets the value of the amtCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmtCodeType }
     *     
     */
    public void setAmtCode(AmtCodeType value) {
        this.amtCode = value;
    }

}
