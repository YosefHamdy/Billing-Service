
package com.mop.billing.integration.tahseel.wsdl;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Closed Enum: An POINum is a unique identifier for a bill-paying entity, including people and companies.  
 * 			'NAT': National ID
 * 'IQA': Iqama ID
 * 'BIS': Business ID (Commercial Registration)
 * 'UOI': Unified Organization ID
 * 'C700': 700 Code
 * 'GCC': GCC Passport Number
 * 'PAS': Passport Number
 * 'BDN': Border Number
 * 'FCN': Family Card Number
 * 
 * <p>Java class for POIType_Type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * <pre>{@code
 * <simpleType name="POIType_Type">
 *   <restriction base="{https://www.ecp.mof.gov.sa/agw}NC">
 *     <maxLength value="4"/>
 *     <enumeration value="NAT"/>
 *     <enumeration value="IQA"/>
 *     <enumeration value="BIS"/>
 *     <enumeration value="UOI"/>
 *     <enumeration value="C700"/>
 *     <enumeration value="GCC"/>
 *     <enumeration value="PAS"/>
 *     <enumeration value="BDN"/>
 *     <enumeration value="FCN"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "POIType_Type")
@XmlEnum
public enum POITypeType {

    NAT("NAT"),
    IQA("IQA"),
    BIS("BIS"),
    UOI("UOI"),
    @XmlEnumValue("C700")
    C_700("C700"),
    GCC("GCC"),
    PAS("PAS"),
    BDN("BDN"),
    FCN("FCN");
    private final String value;

    POITypeType(String v) {
        value = v;
    }

    /**
     * Gets the value associated to the enum constant.
     * 
     * @return
     *     The value linked to the enum.
     */
    public String value() {
        return value;
    }

    /**
     * Gets the enum associated to the value passed as parameter.
     * 
     * @param v
     *     The value to get the enum from.
     * @return
     *     The enum which corresponds to the value, if it exists.
     * @throws IllegalArgumentException
     *     If no value matches in the enum declaration.
     */
    public static POITypeType fromValue(String v) {
        for (POITypeType c: POITypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
