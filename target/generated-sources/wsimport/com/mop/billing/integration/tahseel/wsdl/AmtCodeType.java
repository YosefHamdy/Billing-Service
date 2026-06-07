
package com.mop.billing.integration.tahseel.wsdl;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Amount Code
 * 
 * <p>Java class for AmtCode_Type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * <pre>{@code
 * <simpleType name="AmtCode_Type">
 *   <restriction base="{https://www.ecp.mof.gov.sa/agw}ClosedEnum">
 *     <enumeration value="MinPartialPmt"/>
 *     <enumeration value="MinAdvancePmt"/>
 *     <enumeration value="MaxAdvancePmt"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "AmtCode_Type")
@XmlEnum
public enum AmtCodeType {

    @XmlEnumValue("MinPartialPmt")
    MIN_PARTIAL_PMT("MinPartialPmt"),
    @XmlEnumValue("MinAdvancePmt")
    MIN_ADVANCE_PMT("MinAdvancePmt"),
    @XmlEnumValue("MaxAdvancePmt")
    MAX_ADVANCE_PMT("MaxAdvancePmt");
    private final String value;

    AmtCodeType(String v) {
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
    public static AmtCodeType fromValue(String v) {
        for (AmtCodeType c: AmtCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
