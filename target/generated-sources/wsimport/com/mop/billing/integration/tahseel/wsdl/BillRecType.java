
package com.mop.billing.integration.tahseel.wsdl;

import java.math.BigDecimal;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BillRec_Type complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="BillRec_Type">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}AgencyId" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}BillNum" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}BillAcct" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}BillAction" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}ActionReason" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}ActionDt" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}BillStatusCode" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}BillCategory"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}BillCycle" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}DisplayLabelAr" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}DisplayLabelEn" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}BillAmt"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}PresDt" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}DueDt"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}ExpDt" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}BillRefInfo" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}BillDesc" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}BillSummList" minOccurs="0"/>
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
@XmlType(name = "BillRec_Type", propOrder = {
    "agencyId",
    "billNum",
    "billAcct",
    "billAction",
    "actionReason",
    "actionDt",
    "billStatusCode",
    "billCategory",
    "billCycle",
    "displayLabelAr",
    "displayLabelEn",
    "billAmt",
    "presDt",
    "dueDt",
    "expDt",
    "billRefInfo",
    "billDesc",
    "billSummList",
    "pmtOptions",
    "benInfo",
    "revenueEntryList"
})
@XmlSeeAlso({
    BillInfoType.class
})
public class BillRecType {

    /**
     *  17 Digit Agency ID
     * 
     */
    @XmlElement(name = "AgencyId")
    protected String agencyId;
    @XmlElement(name = "BillNum")
    protected String billNum;
    @XmlElement(name = "BillAcct")
    protected String billAcct;
    @XmlElement(name = "BillAction")
    @XmlSchemaType(name = "string")
    protected BillActionType billAction;
    /**
     * Action Reason
     * 
     */
    @XmlElement(name = "ActionReason")
    protected String actionReason;
    @XmlElement(name = "ActionDt")
    protected String actionDt;
    @XmlElement(name = "BillStatusCode")
    @XmlSchemaType(name = "string")
    protected BillStatusCodeType billStatusCode;
    @XmlElement(name = "BillCategory", required = true)
    protected String billCategory;
    @XmlElement(name = "BillCycle")
    protected String billCycle;
    /**
     * Display Label Arabic
     * 
     */
    @XmlElement(name = "DisplayLabelAr")
    protected String displayLabelAr;
    /**
     * Display Label English
     * 
     */
    @XmlElement(name = "DisplayLabelEn")
    protected String displayLabelEn;
    @XmlElement(name = "BillAmt", required = true)
    protected BigDecimal billAmt;
    @XmlElement(name = "PresDt")
    protected String presDt;
    @XmlElement(name = "DueDt", required = true)
    protected String dueDt;
    @XmlElement(name = "ExpDt")
    protected String expDt;
    @XmlElement(name = "BillRefInfo")
    protected String billRefInfo;
    @XmlElement(name = "BillDesc")
    protected String billDesc;
    @XmlElement(name = "BillSummList")
    protected BillSummListType billSummList;
    @XmlElement(name = "PmtOptions")
    protected PmtOptionsType pmtOptions;
    @XmlElement(name = "BenInfo")
    protected BenInfoType benInfo;
    /**
     * Revenue Entries List
     * 
     */
    @XmlElement(name = "RevenueEntryList")
    protected RevenueEntryListType revenueEntryList;

    /**
     *  17 Digit Agency ID
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgencyId() {
        return agencyId;
    }

    /**
     * Sets the value of the agencyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getAgencyId()
     */
    public void setAgencyId(String value) {
        this.agencyId = value;
    }

    /**
     * Gets the value of the billNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillNum() {
        return billNum;
    }

    /**
     * Sets the value of the billNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillNum(String value) {
        this.billNum = value;
    }

    /**
     * Gets the value of the billAcct property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillAcct() {
        return billAcct;
    }

    /**
     * Sets the value of the billAcct property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillAcct(String value) {
        this.billAcct = value;
    }

    /**
     * Gets the value of the billAction property.
     * 
     * @return
     *     possible object is
     *     {@link BillActionType }
     *     
     */
    public BillActionType getBillAction() {
        return billAction;
    }

    /**
     * Sets the value of the billAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillActionType }
     *     
     */
    public void setBillAction(BillActionType value) {
        this.billAction = value;
    }

    /**
     * Action Reason
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActionReason() {
        return actionReason;
    }

    /**
     * Sets the value of the actionReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getActionReason()
     */
    public void setActionReason(String value) {
        this.actionReason = value;
    }

    /**
     * Gets the value of the actionDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActionDt() {
        return actionDt;
    }

    /**
     * Sets the value of the actionDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActionDt(String value) {
        this.actionDt = value;
    }

    /**
     * Gets the value of the billStatusCode property.
     * 
     * @return
     *     possible object is
     *     {@link BillStatusCodeType }
     *     
     */
    public BillStatusCodeType getBillStatusCode() {
        return billStatusCode;
    }

    /**
     * Sets the value of the billStatusCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillStatusCodeType }
     *     
     */
    public void setBillStatusCode(BillStatusCodeType value) {
        this.billStatusCode = value;
    }

    /**
     * Gets the value of the billCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillCategory() {
        return billCategory;
    }

    /**
     * Sets the value of the billCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillCategory(String value) {
        this.billCategory = value;
    }

    /**
     * Gets the value of the billCycle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillCycle() {
        return billCycle;
    }

    /**
     * Sets the value of the billCycle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillCycle(String value) {
        this.billCycle = value;
    }

    /**
     * Display Label Arabic
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayLabelAr() {
        return displayLabelAr;
    }

    /**
     * Sets the value of the displayLabelAr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getDisplayLabelAr()
     */
    public void setDisplayLabelAr(String value) {
        this.displayLabelAr = value;
    }

    /**
     * Display Label English
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayLabelEn() {
        return displayLabelEn;
    }

    /**
     * Sets the value of the displayLabelEn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getDisplayLabelEn()
     */
    public void setDisplayLabelEn(String value) {
        this.displayLabelEn = value;
    }

    /**
     * Gets the value of the billAmt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBillAmt() {
        return billAmt;
    }

    /**
     * Sets the value of the billAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBillAmt(BigDecimal value) {
        this.billAmt = value;
    }

    /**
     * Gets the value of the presDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPresDt() {
        return presDt;
    }

    /**
     * Sets the value of the presDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPresDt(String value) {
        this.presDt = value;
    }

    /**
     * Gets the value of the dueDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDueDt() {
        return dueDt;
    }

    /**
     * Sets the value of the dueDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDueDt(String value) {
        this.dueDt = value;
    }

    /**
     * Gets the value of the expDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpDt() {
        return expDt;
    }

    /**
     * Sets the value of the expDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpDt(String value) {
        this.expDt = value;
    }

    /**
     * Gets the value of the billRefInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillRefInfo() {
        return billRefInfo;
    }

    /**
     * Sets the value of the billRefInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillRefInfo(String value) {
        this.billRefInfo = value;
    }

    /**
     * Gets the value of the billDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillDesc() {
        return billDesc;
    }

    /**
     * Sets the value of the billDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillDesc(String value) {
        this.billDesc = value;
    }

    /**
     * Gets the value of the billSummList property.
     * 
     * @return
     *     possible object is
     *     {@link BillSummListType }
     *     
     */
    public BillSummListType getBillSummList() {
        return billSummList;
    }

    /**
     * Sets the value of the billSummList property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillSummListType }
     *     
     */
    public void setBillSummList(BillSummListType value) {
        this.billSummList = value;
    }

    /**
     * Gets the value of the pmtOptions property.
     * 
     * @return
     *     possible object is
     *     {@link PmtOptionsType }
     *     
     */
    public PmtOptionsType getPmtOptions() {
        return pmtOptions;
    }

    /**
     * Sets the value of the pmtOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link PmtOptionsType }
     *     
     */
    public void setPmtOptions(PmtOptionsType value) {
        this.pmtOptions = value;
    }

    /**
     * Gets the value of the benInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BenInfoType }
     *     
     */
    public BenInfoType getBenInfo() {
        return benInfo;
    }

    /**
     * Sets the value of the benInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BenInfoType }
     *     
     */
    public void setBenInfo(BenInfoType value) {
        this.benInfo = value;
    }

    /**
     * Revenue Entries List
     * 
     * @return
     *     possible object is
     *     {@link RevenueEntryListType }
     *     
     */
    public RevenueEntryListType getRevenueEntryList() {
        return revenueEntryList;
    }

    /**
     * Sets the value of the revenueEntryList property.
     * 
     * @param value
     *     allowed object is
     *     {@link RevenueEntryListType }
     *     
     * @see #getRevenueEntryList()
     */
    public void setRevenueEntryList(RevenueEntryListType value) {
        this.revenueEntryList = value;
    }

}
