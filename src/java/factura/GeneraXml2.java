/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factura;

import Decoder.BASE64Encoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import mx.bigdata.sat.cfdi.v33.schema.CTipoFactor;
import mx.bigdata.sat.cfdi.v33.schema.CUsoCFDI;
import mx.bigdata.sat.cfdi.v33.schema.Comprobante;
import mx.bigdata.sat.cfdi.v33.schema.Comprobante.CfdiRelacionados;
import mx.bigdata.sat.cfdi.v33.schema.Comprobante.CfdiRelacionados.CfdiRelacionado;
import mx.bigdata.sat.cfdi.v33.schema.Comprobante.Conceptos;
import mx.bigdata.sat.cfdi.v33.schema.Comprobante.Conceptos.Concepto;
import mx.bigdata.sat.cfdi.v33.schema.Comprobante.Emisor;
import mx.bigdata.sat.cfdi.v33.schema.Comprobante.Impuestos;
import mx.bigdata.sat.cfdi.v33.schema.Comprobante.Receptor;
import mx.bigdata.sat.cfdi.v33.schema.ObjectFactory;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.PKCS8EncodedKeySpec;

import java.util.Calendar;
import java.util.TimeZone;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import mx.bigdata.sat.cfdi.v33.schema.CMetodoPago;
import mx.bigdata.sat.cfdi.v33.schema.CMoneda;
import mx.bigdata.sat.cfdi.v33.schema.CTipoDeComprobante;
import org.apache.commons.ssl.PKCS8Key;
import org.datacontract.schemas._2004._07.tes_tfd_v33.RespuestaTFD33;
import org.tempuri.IWSCFDI33;
import org.tempuri.WSCFDI33;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 *
 * @author gioca
 */
public class GeneraXml2 {

    public static Comprobante createComprobante() throws Exception {
        ObjectFactory of = new ObjectFactory();
        Comprobante xml = of.createComprobante();

        xml.setVersion("3.3");
        xml.setSerie("A");
        xml.setFolio("12345");
        xml.setFecha(getXMLCalendar());
        xml.setFormaPago("01");
        xml.setCondicionesDePago("CONTADO");
        xml.setSubTotal(new BigDecimal("855.75"));
        xml.setDescuento(new BigDecimal("100.00"));
        xml.setMoneda(CMoneda.MXN);
        //xml.setTipoCambio(new BigDecimal("1"));
        xml.setTotal(new BigDecimal("892.67"));
        xml.setTipoDeComprobante(CTipoDeComprobante.I);
        xml.setMetodoPago(CMetodoPago.PUE);
        xml.setLugarExpedicion("91700");

        //EMISOR
        xml.setEmisor(createEmisor(of));

        //RECEPTOR
        xml.setReceptor(createReceptor(of));

        //CONCEPTOS
        xml.setConceptos(createConceptos(of));

        //ImpuestosTotales
        xml.setImpuestos(createImpuestos(of));

        File cer = new File("/home/pedro/certificados/CertificadoFirmadoPM.cer");
        File key = new File("/home/pedro/certificados/LlavePkcs8PM.key");

        //agregar certificado y no de certificado al comprobante 
        X509Certificate x509certificate = getX509Certificate(cer);
        String certificado = getCertificadoBase64(x509certificate);
        String NoCertificado = getNoCertificado(x509certificate);

        xml.setCertificado("MIIDiTCCAnGgAwIBAgIUMjAwMDEwMDAwMDAzMDAwMjI4MjQwDQYJKoZIhvcNAQEFBQAwSzEVMBMGA1UEAwwMQS5DLiBQcnVlYmFzMRAwDgYDVQQKDAdQcnVlYmFzMQswCQYDVQQGEwJNWDETMBEGA1UEBwwKQ3VhdWh0ZW1vYzAeFw0xNzA0MjgxODM0NDdaFw0yMjEwMTkxODM0NDdaMIGVMRowGAYDVQQDDBFDb21wdWhpcGVybWVnYXJlZDEaMBgGA1UEKQwRQ29tcHVoaXBlcm1lZ2FyZWQxGjAYBgNVBAoMEUNvbXB1aGlwZXJtZWdhcmVkMSMwIQYDVQQtDBpURVMwMzAyMDEwMDEgLyBURVMxMDMxN0E0NjEaMBgGA1UECwwRQ29tcHVoaXBlcm1lZ2FyZWQwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCrF7Z1mytCR6XrnVnseTPdELc8IhR6SF2iYPvsW1QjGjkGZ8CVgBu24ParI95NoDEq6AuGqhJlK+uYAzRDIq8OckZmXeuT47kfS5mHKlw1GfLgPMN1fPgYmm0l7sh96eNvipTRMv2rsQ8+myTVkmiQVev0P+NHPlY+cL8QIq7Sd+ZuPIKPHwcdKlUIJEeosM/nSnS+JBXKdcue23gC/UWXJr4wFNa54Gkt8lBR4xzDthrDrqmILLwBHS/kVhY01SxaPheIFixlDCSKiGseFpMYa1h7jfymL++ljMz6J9ELN4mmMH3hofW5zwBtJRLkkjpQTNfxiRD819yoPO4GzbRXAgMBAAGjGjAYMAkGA1UdEwQCMAAwCwYDVR0PBAQDAgbAMA0GCSqGSIb3DQEBBQUAA4IBAQAj9NJAcBxKaP+QQu4wSev8ia+bYgpMVJr5iblNELiZxKk7W16x01i2goPqvEB0pmcjQb6desfWj24OP3tCbsNiin3+49MK+Qb2is1toyYUdwp0V3PPy04IaYXYjrqCABOBvFrubfaC9cGhMG+aILZfvuTCJ3oZpevj8JH70OEM1bW+2DVHM1lumweMWEucVdzfdmihRUfMk1MjctxUDakYSE+WgvnB5Q1GzW2eOtEdOxsC/ni5OerxSxed7tcJNhxCiJg77HdiyBLL2O+TZJI52anwS6ej6s9PsGbTeXJ5GldZVnPaiIsTzqfosqzYWszrYCinCKNPhnEWjewTD6hc");
        xml.setNoCertificado("20001000000300022824");

        //covertir xml a String para sellado 
        String cadxml = jaxBObjectToXml(xml);

        System.out.println("+++++++++++++++++++++++++++++++++++cadxml: " + cadxml);

        String cadenaOriginal = "";
        PrivateKey llavePrivada = null;
        String selloDigital = "";

        try {
            cadenaOriginal = generarCadenaOriginal(cadxml);
        } catch (TransformerException e) {
            Logger.getLogger(GeneraXml2.class.getName()).log(Level.SEVERE, null, e);
        }

        // obtener llave privada
        llavePrivada = getPrivateKey(key, "12345678a");

        // obtener sello digital
        selloDigital = generarSelloDigital(llavePrivada, cadenaOriginal);

        String SelloModificado = quitarSaltos(selloDigital);

        //System.out.println("selleOriginal: "+selloDigital);
        //System.out.println("selleModificado: "+SelloModificado);
        xml.setSello(SelloModificado);

        String consello = jaxBObjectToXml(xml);

        //System.out.println("+++++++++++++++++++++++++++++++++++xmlString: " + consello);
        timbrarXml(consello);

        return xml;
    }

    private static CfdiRelacionados createCfdiRelacionados(ObjectFactory of) {
        CfdiRelacionados cfdir = of.createComprobanteCfdiRelacionados();
        cfdir.setTipoRelacion("06");
        cfdir.getCfdiRelacionado().add(createCfdiRelacionado(of));
        return cfdir;
    }

    private static CfdiRelacionado createCfdiRelacionado(ObjectFactory of) {
        CfdiRelacionado cfdir = of.createComprobanteCfdiRelacionadosCfdiRelacionado();
        cfdir.setUUID("a0452045-89cb-4792-9cc0-153f21faab7f");
        return cfdir;
    }

    private static Emisor createEmisor(ObjectFactory of) {
        Emisor emisor = of.createComprobanteEmisor();
        emisor.setNombre("EMISOR");
        emisor.setRfc("TES030201001");
        emisor.setRegimenFiscal("601");
        return emisor;
    }

    private static Receptor createReceptor(ObjectFactory of) {
        Receptor receptor = of.createComprobanteReceptor();
        receptor.setRfc("TEST010203001");
        receptor.setNombre("JAMES BOND 007");
        //receptor.setResidenciaFiscal(CPais.MEX);
        //receptor.setNumRegIdTrib("ResidenteExtranjero1");
        receptor.setUsoCFDI(CUsoCFDI.I_04);
        return receptor;
    }

    private static Conceptos createConceptos(ObjectFactory of) {
        Conceptos cps = of.createComprobanteConceptos();
        List<Concepto> list = cps.getConcepto();
        Concepto c1 = of.createComprobanteConceptosConcepto();
        c1.setClaveProdServ("44101719");
        c1.setNoIdentificacion("ASCA2400");
        c1.setCantidad(new BigDecimal("1"));
        c1.setClaveUnidad("EA");
        c1.setUnidad("PZA");
        c1.setDescripcion("VIBRAMICINA 100MG 10");
        c1.setValorUnitario(new BigDecimal("855.75"));
        c1.setImporte(new BigDecimal("855.75"));
        c1.setDescuento(new BigDecimal("100.00"));
        c1.setImpuestos(createImpuestosConceptos(of));
        list.add(c1);
        return cps;
    }

    private static Concepto.Impuestos createImpuestosConceptos(ObjectFactory of) {
        Concepto.Impuestos imp = of.createComprobanteConceptosConceptoImpuestos();
        Concepto.Impuestos.Traslados trs = of.createComprobanteConceptosConceptoImpuestosTraslados();
        Concepto.Impuestos.Traslados.Traslado tr = of.createComprobanteConceptosConceptoImpuestosTrasladosTraslado();
        tr.setImpuesto("002");
        tr.setTipoFactor(CTipoFactor.TASA);
        tr.setTasaOCuota(new BigDecimal("0.160000"));
        tr.setImporte(new BigDecimal("136.92"));
        tr.setBase(new BigDecimal("855.75"));

        trs.getTraslado().add(tr);
        imp.setTraslados(trs);
        return imp;
    }

//    private static InformacionAduanera createInformacionAduanera(ObjectFactory of) {
//        InformacionAduanera ia = of.createComprobanteConceptosConceptoInformacionAduanera();
//        ia.setNumeroPedimento("67  52  3924  8060097");
//        return ia;
//    }
//
//    private static CuentaPredial createCuentaPredial(ObjectFactory of) {
//        CuentaPredial cup = of.createComprobanteConceptosConceptoCuentaPredial();
//        cup.setNumero("123456");
//        return cup;
//    }
    private static Impuestos createImpuestos(ObjectFactory of) {
        Impuestos imps = of.createComprobanteImpuestos();
        imps.setTotalImpuestosTrasladados(new BigDecimal("136.92"));
        imps.setTraslados(createTraslados(of));
        return imps;
    }

    private static Impuestos.Traslados createTraslados(ObjectFactory of) {
        Impuestos.Traslados its = of.createComprobanteImpuestosTraslados();
        Impuestos.Traslados.Traslado it = of.createComprobanteImpuestosTrasladosTraslado();
        it.setImpuesto("002");
        it.setTipoFactor(CTipoFactor.TASA);
        it.setTasaOCuota(new BigDecimal("0.160000"));
        it.setImporte(new BigDecimal("136.92"));
        its.getTraslado().add(it);
        return its;
    }

    public static String jaxBObjectToXml(Comprobante xml) {

        String xmlString = "";
        try {
            String rutaComprobante = "/home/pedro/certificados/cfdi.xml";
            JAXBContext context = JAXBContext.newInstance(Comprobante.class);
            Marshaller m = context.createMarshaller();

            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            m.setProperty("com.sun.xml.bind.namespacePrefixMapper", new DefaultNamespacePrefixMapper());
            m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv33.xsd");
            m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            m.marshal(xml, new File(rutaComprobante));

            StringWriter sw = new StringWriter();
            m.marshal(xml, sw);

            xmlString = sw.toString();

            //System.out.println("xml en string: " + xmlString);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlString;
    }

    //Método de sellado
    private static X509Certificate getX509Certificate(final File cer) throws CertificateException, IOException {
        FileInputStream is = null;
        try {
            is = new FileInputStream(cer);
            CertificateFactory of = CertificateFactory.getInstance("X.509");
            return (X509Certificate) of.generateCertificate(is);

        } finally {
            if (is != null) {
                is.close();
            }
        }

    }

    private static String getCertificadoBase64(final X509Certificate cert) throws CertificateEncodingException {

        BASE64Encoder encoder = new BASE64Encoder();
        String psB64Certificate = encoder.encodeBuffer(cert.getEncoded());

        return psB64Certificate;

    }

    private static String getNoCertificado(final X509Certificate cer) throws CertificateEncodingException {
        BigInteger serial = cer.getSerialNumber();
        byte[] sArr = serial.toByteArray();
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < sArr.length; i++) {
            buffer.append((char) sArr[i]);
        }

        return buffer.toString();
    }

    private static String generarCadenaOriginal(final String xml) throws TransformerException {

        StreamSource streamSource = new StreamSource("/home/pedro/certificados/cadenaoriginal_3_3.xslt");
        TransformerFactory trasnformerFactory = TransformerFactory.newInstance();
        Transformer xlsTranformer = trasnformerFactory.newTransformer(streamSource);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        xlsTranformer.transform(new StreamSource(new StringReader(xml)), new StreamResult(output));
        String resultado = "";
        try {
            resultado = output.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            Logger.getLogger(GeneraXml2.class.getName()).log(Level.SEVERE, null, e);
        }

        return resultado;

    }

    private static PrivateKey getPrivateKey(final File keyFile, final String password) throws GeneralSecurityException, IOException {
        FileInputStream in = new FileInputStream(keyFile);
        PKCS8Key pkcs8 = new PKCS8Key(in, password.toCharArray());
        byte[] decrypted = pkcs8.getDecryptedBytes();
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decrypted);

        PrivateKey pk = null;

        if (pkcs8.isDSA()) {
            pk = KeyFactory.getInstance("DSA").generatePrivate(spec);
        } else if (pkcs8.isRSA()) {
            pk = KeyFactory.getInstance("RSA").generatePrivate(spec);
        }
// For lazier types (like me):

        System.out.println("private key: " + pkcs8.getPrivateKey());
        pk = pkcs8.getPrivateKey();

        return pk;

    }

    public static String generarSelloDigital(final PrivateKey key, final String cadenaOriginal) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException, UnsupportedEncodingException {

        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(key, new SecureRandom());
        sign.update(cadenaOriginal.getBytes());
        byte[] signature = sign.sign();
        return new String(Base64.encode(signature));
    }

    private static void timbrarXml(final String xml) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        RespuestaTFD33 Respuesta;

        Respuesta = timbrarCFDI("FAMJ810312D33", "contRa$3na", xml, "TIMBRADO33");

        if (Respuesta.isOperacionExitosa()) {
          
            stringToDom(Respuesta.getXMLResultado().getValue());
            
           
            
            System.out.println("Operación exitosa");
            System.out.println(Respuesta.getXMLResultado().getValue());
            System.out.println("PDF: "+Respuesta.getPDFResultado().getValue());
            System.out.println(Respuesta.getTimbre().getValue().getEstado().getValue());
            System.out.println(Respuesta.getTimbre().getValue().getNumeroCertificadoSAT().getValue());
            System.out.println(Respuesta.getTimbre().getValue().getSelloCFD().getValue());
            System.out.println(Respuesta.getTimbre().getValue().getSelloSAT().getValue());
            System.out.println(Respuesta.getTimbre().getValue().getUUID().getValue());

        } else {
            System.out.println("Hubo un error en la operación");
            System.out.println(Respuesta.getCodigoRespuesta().getValue());
            System.out.println(Respuesta.getMensajeErrorDetallado().getValue());
        }

        if (Respuesta.getCodigoConfirmacion().getValue() != null) {
            System.out.println("Codigo de Confirmacion: " + Respuesta.getCodigoConfirmacion().getValue());
        }
    }

    private static RespuestaTFD33 timbrarCFDI(java.lang.String usuario, java.lang.String password, java.lang.String cadenaXML, java.lang.String referencia) {
        WSCFDI33 service = new WSCFDI33();
        IWSCFDI33 port = service.getSoapHttpEndpoint();
        return port.timbrarCFDI(usuario, password, cadenaXML, referencia);
    }

    public static String quitarSaltos(String cadena) {
        // Para el reemplazo usamos un string vacío 
        return cadena.replaceAll("\n", "");
    }

    
    
     public static XMLGregorianCalendar getXMLCalendar() throws Exception {
        Calendar sDate = Calendar.getInstance();
        DatatypeFactory dtf = DatatypeFactory.newInstance();
        XMLGregorianCalendar calendar;
        
        calendar = dtf.newXMLGregorianCalendar();
        calendar.setYear(sDate.get(Calendar.YEAR));
        calendar.setDay(sDate.get(Calendar.DAY_OF_MONTH));
        calendar.setMonth(sDate.get(Calendar.MONTH)+1);
        calendar.setHour(sDate.get(Calendar.HOUR));
        calendar.setMinute(sDate.get(Calendar.MINUTE));
        calendar.setSecond(sDate.get(Calendar.SECOND));
       
        
        
        
         System.out.println("Calendar: "+calendar);
        
        return calendar;

}
     public static void stringToDom(String xmlSource) 
        throws SAXException, ParserConfigurationException, IOException, TransformerConfigurationException, TransformerException {
    // Parse the given input
    String rutaComprobante = "/home/pedro/certificados/cfdiTimbrado.xml";
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(new InputSource(new StringReader(xmlSource)));

    // Write the parsed document to an xml file
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    DOMSource source = new DOMSource(doc);

    StreamResult result =  new StreamResult(new File(rutaComprobante));
    transformer.transform(source, result);
}
     
}
