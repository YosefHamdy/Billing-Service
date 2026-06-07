
package com.mop.billing.integration.tahseel.wsdl;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * -aggregate identifies a specific bill paying Customer or institution
 * 
 * <p>Java class for POI_Type complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="POI_Type">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}POINum"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}POIType"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "POI_Type", propOrder = {
    "poiNum",
    "poiType"
})
public class POIType {

    @XmlElement(name = "POINum", required = true)
    protected String poiNum;
    @XmlElement(name = "POIType", required = true)
    @XmlSchemaType(name = "string")
    protected POITypeType poiType;

    /**
     * Gets the value of the poiNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOINum() {
        return poiNum;
    }

    /**
     * Sets the value of the poiNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOINum(String value) {
        this.poiNum = value;
    }

    /**
     * Gets the value of the poiType property.
     * 
     * @return
     *     possible object is
     *     {@link POITypeType }
     *     
     */
    public POITypeType getPOIType() {
        return poiType;
    }

    /**
     * Sets the value of the poiType property.
     * 
     * @param value
     *     allowed object is
     *     {@link POITypeType }
     *     
     */
    public void setPOIType(POITypeType value) {
        this.poiType = value;
    }

}
