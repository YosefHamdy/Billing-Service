
package com.mop.billing.integration.tahseel.wsdl;

import javax.xml.datatype.XMLGregorianCalendar;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MsgRqHdr_Type complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="MsgRqHdr_Type">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}RqUID"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}SCId"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}FuncId"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}PartnerInfo"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}RqMode" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}PartyId" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}UserId" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}ProxyUserId" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}ClientDt"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}EchoData" minOccurs="0"/>
 *         <element ref="{https://www.ecp.mof.gov.sa/agw}Version" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MsgRqHdr_Type", propOrder = {
    "rqUID",
    "scId",
    "funcId",
    "partnerInfo",
    "rqMode",
    "partyId",
    "userId",
    "proxyUserId",
    "clientDt",
    "echoData",
    "version"
})
public class MsgRqHdrType {

    /**
     * SCId||Date(DDMMYYYY)||(Sequence)#######
     * 				Example: IB190520081000001
     * 
     */
    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "SCId", required = true)
    @XmlSchemaType(name = "string")
    protected SCIdType scId;
    /**
     * Function identifier
     * 
     */
    @XmlElement(name = "FuncId", required = true)
    protected String funcId;
    @XmlElement(name = "PartnerInfo", required = true)
    protected PartnerInfoType partnerInfo;
    /**
     *  0: Normal request(Default)
     * 				Greater than 0: Repeated request
     * 				-1: Reversal request
     * 				Less than -1: Repeated reversal request
     * 
     */
    @XmlElement(name = "RqMode")
    protected Long rqMode;
    /**
     * Party Identification
     * 
     */
    @XmlElement(name = "PartyId")
    protected PartyIdType partyId;
    /**
     * User identifier
     * 
     */
    @XmlElement(name = "UserId")
    protected String userId;
    @XmlElement(name = "ProxyUserId")
    protected String proxyUserId;
    /**
     * Client timestamp
     * 
     */
    @XmlElement(name = "ClientDt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar clientDt;
    /**
     * Echo back field, the value in it will be echoed back in message response header
     * 
     */
    @XmlElement(name = "EchoData")
    protected String echoData;
    @XmlElement(name = "Version")
    protected String version;

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
     * Gets the value of the scId property.
     * 
     * @return
     *     possible object is
     *     {@link SCIdType }
     *     
     */
    public SCIdType getSCId() {
        return scId;
    }

    /**
     * Sets the value of the scId property.
     * 
     * @param value
     *     allowed object is
     *     {@link SCIdType }
     *     
     */
    public void setSCId(SCIdType value) {
        this.scId = value;
    }

    /**
     * Function identifier
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFuncId() {
        return funcId;
    }

    /**
     * Sets the value of the funcId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getFuncId()
     */
    public void setFuncId(String value) {
        this.funcId = value;
    }

    /**
     * Gets the value of the partnerInfo property.
     * 
     * @return
     *     possible object is
     *     {@link PartnerInfoType }
     *     
     */
    public PartnerInfoType getPartnerInfo() {
        return partnerInfo;
    }

    /**
     * Sets the value of the partnerInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartnerInfoType }
     *     
     */
    public void setPartnerInfo(PartnerInfoType value) {
        this.partnerInfo = value;
    }

    /**
     *  0: Normal request(Default)
     * 				Greater than 0: Repeated request
     * 				-1: Reversal request
     * 				Less than -1: Repeated reversal request
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRqMode() {
        return rqMode;
    }

    /**
     * Sets the value of the rqMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     * @see #getRqMode()
     */
    public void setRqMode(Long value) {
        this.rqMode = value;
    }

    /**
     * Party Identification
     * 
     * @return
     *     possible object is
     *     {@link PartyIdType }
     *     
     */
    public PartyIdType getPartyId() {
        return partyId;
    }

    /**
     * Sets the value of the partyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdType }
     *     
     * @see #getPartyId()
     */
    public void setPartyId(PartyIdType value) {
        this.partyId = value;
    }

    /**
     * User identifier
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getUserId()
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * Gets the value of the proxyUserId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProxyUserId() {
        return proxyUserId;
    }

    /**
     * Sets the value of the proxyUserId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProxyUserId(String value) {
        this.proxyUserId = value;
    }

    /**
     * Client timestamp
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getClientDt() {
        return clientDt;
    }

    /**
     * Sets the value of the clientDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     * @see #getClientDt()
     */
    public void setClientDt(XMLGregorianCalendar value) {
        this.clientDt = value;
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

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

}
