
package com.mop.billing.integration.tahseel.wsdl;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;


/**
 * 
 * 
 * <p>Java class for SCId_Type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * <pre>{@code
 * <simpleType name="SCId_Type">
 *   <restriction base="{https://www.ecp.mof.gov.sa/agw}NC">
 *     <maxLength value="10"/>
 *     <enumeration value="G2G"/>
 *     <enumeration value="ECLLCTN"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "SCId_Type")
@XmlEnum
public enum SCIdType {

    @XmlEnumValue("G2G")
    G_2_G("G2G"),
    ECLLCTN("ECLLCTN");
    private final String value;

    SCIdType(String v) {
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
    public static SCIdType fromValue(String v) {
        for (SCIdType c: SCIdType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
