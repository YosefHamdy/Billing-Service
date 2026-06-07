
package com.mop.billing.integration.tahseel.wsdl;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Bill Status Code
 * 'BillNew': Bill record is new, no corresponding bill exists in the system.
 * 'BillUpdated': Bill is an updated version of a bill currently in the SADAD system.
 * 'BillExpired': The specified bill should be set to “expired.
 * 
 * <p>Java class for BillStatusCode_Type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * <pre>{@code
 * <simpleType name="BillStatusCode_Type">
 *   <restriction base="{https://www.ecp.mof.gov.sa/agw}ClosedEnum">
 *     <enumeration value="BillNew"/>
 *     <enumeration value="BillUpdated"/>
 *     <enumeration value="BillExpired"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "BillStatusCode_Type")
@XmlEnum
public enum BillStatusCodeType {

    @XmlEnumValue("BillNew")
    BILL_NEW("BillNew"),
    @XmlEnumValue("BillUpdated")
    BILL_UPDATED("BillUpdated"),
    @XmlEnumValue("BillExpired")
    BILL_EXPIRED("BillExpired");
    private final String value;

    BillStatusCodeType(String v) {
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
    public static BillStatusCodeType fromValue(String v) {
        for (BillStatusCodeType c: BillStatusCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
