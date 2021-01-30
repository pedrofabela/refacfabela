/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
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
import mx.bigdata.sat.cfdi.v33.schema.Comprobante;
import org.apache.commons.ssl.PKCS8Key;
import org.datacontract.schemas._2004._07.tes_tfd_v33.RespuestaTFD33;
import org.tempuri.IWSCFDI33;
import org.tempuri.WSCFDI33;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import utilidades.ConstantesGenerales;

/**
 *
 * @author gioca
 */
public class TimbrarXml {

    public String timbrarXml(Comprobante xml,int  idVenta) throws GeneralSecurityException, IOException, ParserConfigurationException, SAXException {
        String xmlString = "";
        String cadenaOriginal = "";
        PrivateKey llavePrivada = null;
        String selloDigital = "";

        File key = new File(ConstantesGenerales.rutaKey);

        try {

            xmlString = ConstantesGenerales.xmltoString(xml);
            
            System.out.println("xml: "+xmlString);

            cadenaOriginal = generarCadenaOriginal(xmlString);
            // obtener llave privada
            llavePrivada = getPrivateKey(key, ConstantesGenerales.passwordKey);

            // obtener sello digital
            selloDigital = generarSelloDigital(llavePrivada, cadenaOriginal);

            String SelloModificado = quitarSaltos(selloDigital);

            //Agregamos sello al xml
            xml.setSello(SelloModificado);

            String consello = ConstantesGenerales.xmltoString(xml);

            //mandamos xml a timbrar al webservice
            procesarXml(consello, idVenta);

        } catch (TransformerException e) {
            Logger.getLogger(GeneraXml.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return "Timbrado Correcto";

    }

    private static String generarCadenaOriginal(final String xml) throws TransformerException {

        StreamSource streamSource = new StreamSource(ConstantesGenerales.cadenaOriginalXslt);
        TransformerFactory trasnformerFactory = TransformerFactory.newInstance();
        Transformer xlsTranformer = trasnformerFactory.newTransformer(streamSource);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        xlsTranformer.transform(new StreamSource(new StringReader(xml)), new StreamResult(output));
        String resultado = "";
        try {
            resultado = output.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            Logger.getLogger(GeneraXml.class.getName()).log(Level.SEVERE, null, e);
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

    private static String generarSelloDigital(final PrivateKey key, final String cadenaOriginal) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException, UnsupportedEncodingException {

        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(key, new SecureRandom());
        sign.update(cadenaOriginal.getBytes());
        byte[] signature = sign.sign();
        return new String(Base64.encode(signature));
    }

    private static void procesarXml(final String xml, int idventa) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        RespuestaTFD33 Respuesta;

        Respuesta = timbrarCFDI("FAMJ810312D33", "contRa$3na", xml, "TIMBRADO33");

        if (Respuesta.isOperacionExitosa()) {

            stringToDom(Respuesta.getXMLResultado().getValue(), idventa);

            System.out.println("Operación exitosa");
            System.out.println(Respuesta.getXMLResultado().getValue());
            System.out.println("PDF: " + Respuesta.getPDFResultado().getValue());
            System.out.println(Respuesta.getTimbre().getValue().getEstado().getValue());
            System.out.println(Respuesta.getTimbre().getValue().getNumeroCertificadoSAT().getValue());
            System.out.println(Respuesta.getTimbre().getValue().getSelloCFD().getValue());
            System.out.println(Respuesta.getTimbre().getValue().getSelloSAT().getValue());
            System.out.println(Respuesta.getTimbre().getValue().getUUID().getValue());
            
            //generarPdf(Respuesta.getTimbre().getValue().getUUID().getValue() , idventa);

        } else {
            System.out.println("Hubo un error en la operación");
            System.out.println(Respuesta.getCodigoRespuesta().getValue());
            System.out.println(Respuesta.getMensajeErrorDetallado().getValue());
        }

        if (Respuesta.getCodigoConfirmacion().getValue() != null) {
            System.out.println("Codigo de Confirmacion: " + Respuesta.getCodigoConfirmacion().getValue());
        }
        
        generarPdf("79565982-7E57-7E57-7E57-A35C1A539778" , idventa);
    }
    
    private static void generarPdf (String UUID, int idventa){
        RespuestaTFD33 Respuesta;
        
        //Se declara una variable de tipo String para enviar el logo en Base 64
        String logo64 =ConstantesGenerales.logo64;
        String nombreArchivo= idventa+".pdf";
        // "Usuario" , "Contraseña", "UUID", "LogoBase64"
        Respuesta = obtenerPDF("FAMJ810312D33", "contRa$3na", UUID, logo64);
        
        //Se verifica el resultado
        if(Respuesta.isOperacionExitosa())
        {
            System.out.println("Resultado exitoso");
            String pdf;
            pdf = Respuesta.getPDFResultado().getValue();
            ConstantesGenerales.obtenerArchivoPdf(pdf, nombreArchivo);
            System.out.println(pdf);
        }
        else
        {
            System.out.println("Obtención incorrecta");
            System.out.println(Respuesta.getMensajeErrorDetallado().getValue());
            System.out.println(Respuesta.getCodigoRespuesta().getValue());
        }
    }

    private static RespuestaTFD33 timbrarCFDI(java.lang.String usuario, java.lang.String password, java.lang.String cadenaXML, java.lang.String referencia) {
        WSCFDI33 service = new WSCFDI33();
        IWSCFDI33 port = service.getSoapHttpEndpoint();
        return port.timbrarCFDI(usuario, password, cadenaXML, referencia);
    }
    
    private static RespuestaTFD33 obtenerPDF(java.lang.String usuario, java.lang.String password, java.lang.String uUID, java.lang.String logoBase64) {
        WSCFDI33 service = new WSCFDI33();
        IWSCFDI33 port = service.getSoapHttpEndpoint();
        return port.obtenerPDF(usuario, password, uUID, logoBase64);
    }

    public static String quitarSaltos(String cadena) {
        // Para el reemplazo usamos un string vacío 
        return cadena.replaceAll("\n", "");
    }

    private static void stringToDom(String xmlSource, int idVenta)
            throws SAXException, ParserConfigurationException, IOException, TransformerConfigurationException, TransformerException {
        // Parse the given input
        String nombreArchivo= idVenta+".xml";
        String rutaComprobante = ConstantesGenerales.rutaxmlTimbrado+nombreArchivo;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlSource)));

        // Write the parsed document to an xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        StreamResult result = new StreamResult(new File(rutaComprobante));
        transformer.transform(source, result);
    }

}
