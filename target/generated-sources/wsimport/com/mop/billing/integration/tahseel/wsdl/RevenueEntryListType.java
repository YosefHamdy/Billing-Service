
package com.mop.billing.integration.tahseel.wsdl;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RevenueEntryList_Type complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="RevenueEntryList_Type">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}RevenueEntryInfo" maxOccurs="unbounded"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RevenueEntryList_Type", propOrder = {
    "revenueEntryInfo"
})
public class RevenueEntryListType {

    /**
     * Revenue Entry Information
     * 
     */
    @XmlElement(name = "RevenueEntryInfo", required = true)
    protected List<RevenueEntryInfoType> revenueEntryInfo;

    /**
     * Revenue Entry Information
     * 
     * Gets the value of the revenueEntryInfo property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the revenueEntryInfo property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getRevenueEntryInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RevenueEntryInfoType }
     * </p>
     * 
     * 
     * @return
     *     The value of the revenueEntryInfo property.
     */
    public List<RevenueEntryInfoType> getRevenueEntryInfo() {
        if (revenueEntryInfo == null) {
            revenueEntryInfo = new ArrayList<>();
        }
        return this.revenueEntryInfo;
    }

}
