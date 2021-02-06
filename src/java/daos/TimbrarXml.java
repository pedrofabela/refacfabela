/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import beans.CabeceraXmlBean;
import beans.FacturaBean;
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
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
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
import org.datacontract.schemas._2004._07.tes_tfd_v33.RespuestaCreditos;
import org.datacontract.schemas._2004._07.tes_tfd_v33.RespuestaTFD33;
import org.tempuri.IWSCFDI33;
import org.tempuri.WSCFDI33;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import utilidades.Constantes;
import utilidades.ConstantesGenerales;

/**
 *
 * @author gioca
 */
public class TimbrarXml {

    public String timbrarXml(Comprobante xml,int  idVenta, CabeceraXmlBean cabecera) throws GeneralSecurityException, IOException, ParserConfigurationException, SAXException, Exception {
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
              System.out.println("sali a geerar sello digital");
            String SelloModificado = quitarSaltos(selloDigital);
               System.out.println("sali a  sello digital modificado");

            //Agregamos sello al xml
            xml.setSello(SelloModificado);
              System.out.println("sali de agregar sello xml");

            String consello = ConstantesGenerales.xmltoString(xml);
              System.out.println("sali de transformar xml a string");

            //mandamos xml a timbrar al webservice
            if(consultaFolio()>0){
                System.out.println("ENTRE A TIMBRAR");
            procesarXml(consello, idVenta,  cabecera);
            }
            else {
                Constantes.enviaMensajeConsola("creditos insuficientes");
            }

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
        System.out.println("entre a generar sello digital");
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(key, new SecureRandom());
        sign.update(cadenaOriginal.getBytes());
        byte[] signature = sign.sign();
        return new String(Base64.encode(signature));
    }

    private static void procesarXml(final String xml, int idventa, CabeceraXmlBean cabecera) throws ParserConfigurationException, SAXException, IOException, TransformerException, Exception {
        RespuestaTFD33 Respuesta;

        Respuesta = timbrarCFDI(ConstantesGenerales.usuarioFolios, ConstantesGenerales.passwordFolios, xml, "TIMBRADO33");
       
        FacturaServiceImpl fact =new FacturaServiceImpl();
        FacturaBean facturaBean=new FacturaBean();

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
          
            facturaBean.setNO_VENTA(String.valueOf(idventa));
            facturaBean.setUUID(Respuesta.getTimbre().getValue().getUUID().getValue());
            facturaBean.setESTADO(Respuesta.getTimbre().getValue().getEstado().getValue());
            facturaBean.setNO_CERTIFICADOSAT(Respuesta.getTimbre().getValue().getNumeroCertificadoSAT().getValue());
            facturaBean.setSELLOCFD(Respuesta.getTimbre().getValue().getSelloCFD().getValue());
            facturaBean.setSELLOSAT(Respuesta.getTimbre().getValue().getSelloSAT().getValue());           
            fact.guardarDatosFactura(facturaBean);
            generarPdf(Respuesta.getTimbre().getValue().getUUID().getValue(), idventa);
            enviarMail(cabecera);

        } else {
            System.out.println("Hubo un error en la operación");
            System.out.println(Respuesta.getCodigoRespuesta().getValue());
            System.out.println(Respuesta.getMensajeErrorDetallado().getValue());
        }

        if (Respuesta.getCodigoConfirmacion().getValue() != null) {
            System.out.println("Codigo de Confirmacion: " + Respuesta.getCodigoConfirmacion().getValue());
        }    
 
       
    
        
      
    }
    
    private static void generarPdf (String UUID, int idventa){
        RespuestaTFD33 Respuesta;
        
        //Se declara una variable de tipo String para enviar el logo en Base 64
        String logo64 =ConstantesGenerales.logo64;
        String nombreArchivo= idventa+".pdf";
        // "Usuario" , "Contraseña", "UUID", "LogoBase64"
        Respuesta = obtenerPDF(ConstantesGenerales.usuarioFolios, ConstantesGenerales.passwordFolios, UUID, logo64);
        
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
    
    
    private static String enviarMail(CabeceraXmlBean cabecera ) {

        String error="";
     

        Properties properties = new Properties();
        String siEnvio = "NO";
        

        try {
            properties.put("mail.smtp.host", "mail.refaccionesfabela.com");
            properties.put("mail.transport.protocol", "smtp");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.user", ConstantesGenerales.emailusuario);
            properties.setProperty("mail.password", ConstantesGenerales.emailpass);
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.port", "587"); //587
            //sesion
            Session mailSession = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            ConstantesGenerales.emailusuario, ConstantesGenerales.emailpass);
                }
            });

//construyendo el mensaje 
            Message msg = new MimeMessage(mailSession);
            msg.setSubject("Factura: " + cabecera.getFolio());
            try {
                String asunto="";
                String email="";
                asunto="Factura de compra:"+cabecera.getFolio()+" Refacciones Fabela";
                            
                
                msg.setFrom(new InternetAddress(ConstantesGenerales.emailusuario, asunto));
            } catch (Exception e) {
                error="error en conexion  " + e;
                return error;
            }

            msg.addRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(cabecera.getEmailReceptor())});

            Multipart multipart = new MimeMultipart("related");
            BodyPart htmlPart = new MimeBodyPart();
           
            htmlPart.setContent("<html><center>"
                    + "<img src='http://refaccionesfabela.com/LOGO.jpg'>"
                    + "<br/>"
                    + "<h3><span style='color:blue;'>Refacciones Fabela</span></h3>"
                    + "<p>La factura de tu compra No: "+cabecera.getFolio()+" fue generada de forma satisfactoria "
                      + "<br/>"
                    + "NOTA: El contenido de este correo electr&oacute;nico es confidencial y exclusivo para el  destinatario, <br/>favor de no responder a esta "
                    + "direcci&oacute;n de correo, ya que no se encuentra habilitada para recibir mensajes."
                    + "<br/><br/><br/><br/>"
                    + "</p></center></html>", "text/html");

            BodyPart adjuntoPDF = new MimeBodyPart();
            adjuntoPDF.setDataHandler(new DataHandler(new FileDataSource(ConstantesGenerales.rutaPdf+cabecera.getFolio()+".pdf")));
            adjuntoPDF.setFileName(cabecera.getFolio()+".pdf");                       
           
               BodyPart adjuntoXML = new MimeBodyPart();
            adjuntoXML.setDataHandler(new DataHandler(new FileDataSource(ConstantesGenerales.rutaxmlTimbrado+cabecera.getFolio()+".xml")));
            adjuntoXML.setFileName(cabecera.getFolio()+".xml");    
            
            multipart.addBodyPart(htmlPart);
            multipart.addBodyPart(adjuntoPDF); 
              multipart.addBodyPart(adjuntoXML); 
            msg.setContent(multipart);

            javax.mail.Transport.send(msg, msg.getAllRecipients());
            siEnvio = "SI";
        } catch (Exception e) {
            
          
        }

        Constantes.enviaMensajeConsola("-------bandera si envio correo = .... " + siEnvio);
        if (siEnvio == "SI") {
            error="TE HEMOS ENVIADO UN CORREO, FAVOR DE VERIFICAR TU BANDEJA DE ENTRADA";
        } else {
            error="Tramite EMITIDO. Solicitante sin direccion de correo electronico. Correo NO enviado....";
        }
        return "SUCCESS";
    }
    
    public static int consultaFolio(){
        
        int restantes=0;
        
        RespuestaCreditos Respuesta;
     
     //Se invoca al método del WS
     Respuesta = consultarCreditos(ConstantesGenerales.usuarioFolios, ConstantesGenerales.passwordFolios);
     
     //Se comprueba le operación
     if (Respuesta.isOperacionExitosa())
             {
                 System.out.println("Exito");
                 //Solo traera la informacion del primer paquete.
                 
                 System.out.println("En Uso: " + Respuesta.getPaquetes().getValue().getDetallesPaqueteCreditos().get(0).isEnUso().booleanValue());
                 System.out.println(Respuesta.getPaquetes().getValue().getDetallesPaqueteCreditos().get(0).getFechaActivacion().toString());
                 System.out.println(Respuesta.getPaquetes().getValue().getDetallesPaqueteCreditos().get(0).getFechaVencimiento().toString());
                 System.out.println(Respuesta.getPaquetes().getValue().getDetallesPaqueteCreditos().get(0).getPaquete().getValue());
                 System.out.println(Respuesta.getPaquetes().getValue().getDetallesPaqueteCreditos().get(0).getTimbres().intValue());
                 System.out.println(Respuesta.getPaquetes().getValue().getDetallesPaqueteCreditos().get(0).getTimbresRestantes().intValue());
                 restantes=Respuesta.getPaquetes().getValue().getDetallesPaqueteCreditos().get(0).getTimbresRestantes().intValue();
                 System.out.println(Respuesta.getPaquetes().getValue().getDetallesPaqueteCreditos().get(0).getTimbresUsados().intValue());
             }
     else
     {
         System.out.println("Hubo un error al realizar la consulta");
         System.out.println(Respuesta.getMensajeError().getValue());
     }
        
        
        
        
        
        
        
        return restantes;
    }
    
     private static RespuestaCreditos consultarCreditos(java.lang.String usuario, java.lang.String password) {
         System.out.println("llege a consultar creditos");
         WSCFDI33 service = new WSCFDI33();
        IWSCFDI33 port = service.getSoapHttpEndpoint();
        return port.consultarCreditos(usuario, password);
    }
    
   
    

    
    
    

}
