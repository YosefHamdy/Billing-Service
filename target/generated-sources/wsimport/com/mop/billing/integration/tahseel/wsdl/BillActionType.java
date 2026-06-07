
package com.mop.billing.integration.tahseel.wsdl;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * Bill Action
 * 			'I': Initiate Bill record, no corresponding bill exists in eCollection system.
 * 'U': Update bill which is currently in eCollection system.
 * 'E': Expire the specified bill.
 * 
 * <p>Java class for BillAction_Type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * <pre>{@code
 * <simpleType name="BillAction_Type">
 *   <restriction base="{https://www.ecp.mof.gov.sa/agw}ClosedEnum">
 *     <enumeration value="I"/>
 *     <enumeration value="U"/>
 *     <enumeration value="E"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "BillAction_Type")
@XmlEnum
public enum BillActionType {

    I,
    U,
    E;

    public String value() {
        return name();
    }

    public static BillActionType fromValue(String v) {
        return valueOf(v);
    }

}
