package com.mop.billing.integration.tahseel;

import jakarta.xml.soap.*;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;

import javax.xml.namespace.QName;
import java.util.Set;

public class TahseelSecurityHandler implements SOAPHandler<SOAPMessageContext> {

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        Boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (Boolean.TRUE.equals(outbound)) {
            try {
                SOAPMessage message = context.getMessage();
                SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
                SOAPHeader header = envelope.getHeader();

                if (header == null) {
                    header = envelope.addHeader();
                }

                // Namespace for WS-Security
                String wsseNS = "http://schemas.xmlsoap.org/ws/2002/12/secext";
                QName securityQName = new QName(wsseNS, "Security", "wsse");

                SOAPHeaderElement security = header.addHeaderElement(securityQName);

                // 🔹 Placeholder for now (no signing yet)
                SOAPElement token = security.addChildElement("UsernameToken", "wsse");
                token.addChildElement("Username", "wsse").addTextNode("test-user");
                token.addChildElement("Password", "wsse").addTextNode("test-pass");

                message.saveChanges();

            } catch (Exception e) {
                throw new RuntimeException("Error adding WS-Security header", e);
            }
        }

        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    @Override
    public void close(MessageContext context) {}

    @Override
    public Set<QName> getHeaders() {
        return null;
    }
}