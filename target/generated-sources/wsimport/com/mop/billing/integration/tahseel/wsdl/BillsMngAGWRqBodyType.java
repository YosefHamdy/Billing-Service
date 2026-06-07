
package com.mop.billing.integration.tahseel.wsdl;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BillsMngAGWRqBody_Type complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="BillsMngAGWRqBody_Type">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}BatchId" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}BillList"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BillsMngAGWRqBody_Type", propOrder = {
    "batchId",
    "billList"
})
public class BillsMngAGWRqBodyType {

    /**
     * Unique Batch Id
     * 
     */
    @XmlElement(name = "BatchId")
    protected String batchId;
    /**
     * List of Bills
     * 
     */
    @XmlElement(name = "BillList", required = true)
    protected BillListType billList;

    /**
     * Unique Batch Id
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBatchId() {
        return batchId;
    }

    /**
     * Sets the value of the batchId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getBatchId()
     */
    public void setBatchId(String value) {
        this.batchId = value;
    }

    /**
     * List of Bills
     * 
     * @return
     *     possible object is
     *     {@link BillListType }
     *     
     */
    public BillListType getBillList() {
        return billList;
    }

    /**
     * Sets the value of the billList property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillListType }
     *     
     * @see #getBillList()
     */
    public void setBillList(BillListType value) {
        this.billList = value;
    }

}
