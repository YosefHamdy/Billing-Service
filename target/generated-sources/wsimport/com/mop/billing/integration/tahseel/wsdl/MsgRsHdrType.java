
package com.mop.billing.integration.tahseel.wsdl;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MsgRsHdr_Type complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="MsgRsHdr_Type">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}RqUID" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}SPRefNum" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}MsgRecDt" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}Status"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}EchoData" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MsgRsHdr_Type", propOrder = {
    "rqUID",
    "spRefNum",
    "msgRecDt",
    "status",
    "echoData"
})
public class MsgRsHdrType {

    /**
     * SCId||Date(DDMMYYYY)||(Sequence)#######
     * 				Example: IB190520081000001
     * 
     */
    @XmlElement(name = "RqUID")
    protected String rqUID;
    /**
     * Service provider reference number
     * 
     */
    @XmlElement(name = "SPRefNum")
    protected String spRefNum;
    /**
     * Service provider processing timestamp
     * 
     */
    @XmlElement(name = "MsgRecDt")
    protected String msgRecDt;
    /**
     * Status Aggregate
     * 
     */
    @XmlElement(name = "Status", required = true)
    protected StatusType status;
    /**
     * Echo back field, the value in it will be echoed back in message response header
     * 
     */
    @XmlElement(name = "EchoData")
    protected String echoData;

    /**
     * SCId||Date(DDMMYYYY)||(Sequence)#######
     * 				Example: IB190520081000001
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRqUID() {
        return rqUID;
    }

    /**
     * Sets the value of the rqUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getRqUID()
     */
    public void setRqUID(String value) {
        this.rqUID = value;
    }

    /**
     * Service provider reference number
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSPRefNum() {
        return spRefNum;
    }

    /**
     * Sets the value of the spRefNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getSPRefNum()
     */
    public void setSPRefNum(String value) {
        this.spRefNum = value;
    }

    /**
     * Service provider processing timestamp
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsgRecDt() {
        return msgRecDt;
    }

    /**
     * Sets the value of the msgRecDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getMsgRecDt()
     */
    public void setMsgRecDt(String value) {
        this.msgRecDt = value;
    }

    /**
     * Status Aggregate
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     * @see #getStatus()
     */
    public void setStatus(StatusType value) {
        this.status = value;
    }

    /**
     * Echo back field, the value in it will be echoed back in message response header
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEchoData() {
        return echoData;
    }

    /**
     * Sets the value of the echoData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getEchoData()
     */
    public void setEchoData(String value) {
        this.echoData = value;
    }

}
