
package com.mop.billing.integration.tahseel.wsdl;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BillInfo_Type complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="BillInfo_Type">
 *   <complexContent>
 *     <restriction base="{https://www.ecp.mof.gov.sa/agw}BillRec_Type">
 *       <sequence>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}AgencyId"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}BillAcct"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}BillAction"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}ActionReason" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}BillCategory"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}BillCycle" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}DisplayLabelAr" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}DisplayLabelEn" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}BillAmt"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}DueDt"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}ExpDt" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}BillRefInfo" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}BillDesc" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}PmtOptions" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}BenInfo" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}RevenueEntryList" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BillInfo_Type")
public class BillInfoType
    extends BillRecType
{


}
