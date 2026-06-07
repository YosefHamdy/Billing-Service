
package com.mop.billing.integration.tahseel.wsdl;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * 
 * 
 * <p>Java class for PartnerType_Type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * <pre>{@code
 * <simpleType name="PartnerType_Type">
 *   <restriction base="{https://www.ecp.mof.gov.sa/agw}Identifier_Type">
 *     <maxLength value="7"/>
 *     <enumeration value="GOVT"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "PartnerType_Type")
@XmlEnum
public enum PartnerTypeType {

    GOVT;

    public String value() {
        return name();
    }

    public static PartnerTypeType fromValue(String v) {
        return valueOf(v);
    }

}
