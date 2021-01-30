/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import utilidades.DefaultNamespacePrefixMapper;
import Decoder.BASE64Encoder;
import beans.CabeceraXmlBean;
import beans.ConceptoXmlBean;
import beans.FacturaBean;
import beans.ImpuestoBean;
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
import utilidades.ConstantesGenerales;


/**
 *
 * @author gioca
 */
public class GeneraXml {

    public  Comprobante createComprobante(CabeceraXmlBean cabeceraXml, List<ConceptoXmlBean> listaConceptos, ImpuestoBean impuestoBean) throws Exception {
        
        System.out.println("cabeceraXml: "+cabeceraXml.toString());
        System.out.println("listaConceptos: "+listaConceptos.toString());
        System.out.println("impuestoXml: "+impuestoBean.toString());
        
            
        ObjectFactory of = new ObjectFactory();
        Comprobante xml = of.createComprobante();

        xml.setVersion(cabeceraXml.getVersion());
        xml.setSerie(cabeceraXml.getSerie());
        xml.setFolio(cabeceraXml.getFolio());
        xml.setFecha(ConstantesGenerales.getXMLCalendar());
        xml.setFormaPago(cabeceraXml.getFormaPago());
        xml.setCondicionesDePago(cabeceraXml.getCondicionesPago());
        xml.setSubTotal(new BigDecimal(cabeceraXml.getSubTotal()));
        //xml.setDescuento(new BigDecimal("100.00"));
        xml.setMoneda(CMoneda.MXN);
        //xml.setTipoCambio(new BigDecimal("1"));
        xml.setTotal(new BigDecimal(cabeceraXml.getTotal()));
        xml.setTipoDeComprobante(CTipoDeComprobante.I);
        xml.setMetodoPago(CMetodoPago.fromValue(ConstantesGenerales.MetodoPago));
        xml.setLugarExpedicion(cabeceraXml.getLugarExpedicion());// codigo postal 

        //EMISOR
        xml.setEmisor(createEmisor(of, cabeceraXml));

        //RECEPTOR
        xml.setReceptor(createReceptor(of, cabeceraXml));

        //CONCEPTOS
        xml.setConceptos(createConceptos(of, listaConceptos));

        //ImpuestosTotales
        xml.setImpuestos(createImpuestos(of, impuestoBean));

        

        

        xml.setCertificado(cabeceraXml.getCertificado());
        xml.setNoCertificado(cabeceraXml.getNoCertificado());

        return xml;
    }

    
    private static Emisor createEmisor(ObjectFactory of, CabeceraXmlBean cabeceraXml) {
        Emisor emisor = of.createComprobanteEmisor();
        emisor.setNombre(cabeceraXml.getNombreEmisor());
        emisor.setRfc(cabeceraXml.getRfcEmisor());
        emisor.setRegimenFiscal(cabeceraXml.getRegimenFiscal());
        return emisor;
    }

    private static Receptor createReceptor(ObjectFactory of, CabeceraXmlBean cabeceraXml) {
        Receptor receptor = of.createComprobanteReceptor();
        receptor.setRfc(cabeceraXml.getRfcReceptor());
        receptor.setNombre(cabeceraXml.getNombreReceptor());
        //receptor.setResidenciaFiscal(CPais.MEX);
        //receptor.setNumRegIdTrib("ResidenteExtranjero1");
        receptor.setUsoCFDI(CUsoCFDI.fromValue(ConstantesGenerales.usoCFDI));
        return receptor;
    }

    private static Conceptos createConceptos(ObjectFactory of, List<ConceptoXmlBean> listaConceptos) {
        Conceptos cps = of.createComprobanteConceptos();
        List<Concepto> list = cps.getConcepto();
        for (ConceptoXmlBean concepto : listaConceptos) {
        Concepto c1 = of.createComprobanteConceptosConcepto();
        c1.setClaveProdServ(concepto.getClaveProdServ());
        c1.setNoIdentificacion(concepto.getNoIdentificacion());
        c1.setCantidad(new BigDecimal(concepto.getCantidad()));
        c1.setClaveUnidad(concepto.getClaveUnidad());
        c1.setUnidad(concepto.getUnidad());
        c1.setDescripcion(concepto.getDescripcion());
        c1.setValorUnitario(new BigDecimal(concepto.getValorUnitario()));
        c1.setImporte(new BigDecimal(concepto.getImporte()));
        //c1.setDescuento(new BigDecimal("100.00"));
        c1.setImpuestos(createImpuestosConceptos(of, concepto.getImpuesto(), concepto.getTasaOCuota(), concepto.getImporteImpuesto(), concepto.getBase()));
        list.add(c1);
            
        }  
        return cps;
    }

    private static Concepto.Impuestos createImpuestosConceptos(ObjectFactory of, String impuesto, String tasa, String importe, String base) {
        Concepto.Impuestos imp = of.createComprobanteConceptosConceptoImpuestos();
        Concepto.Impuestos.Traslados trs = of.createComprobanteConceptosConceptoImpuestosTraslados();
        Concepto.Impuestos.Traslados.Traslado tr = of.createComprobanteConceptosConceptoImpuestosTrasladosTraslado();
        tr.setImpuesto(impuesto);
        tr.setTipoFactor(CTipoFactor.TASA);
        tr.setTasaOCuota(new BigDecimal(tasa));
        tr.setImporte(new BigDecimal(importe));
        tr.setBase(new BigDecimal(base));
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
    private static Impuestos createImpuestos(ObjectFactory of, ImpuestoBean impuesto) {
        Impuestos imps = of.createComprobanteImpuestos();
        imps.setTotalImpuestosTrasladados(new BigDecimal(impuesto.getTotalImpuestosTraslados()));
        imps.setTraslados(createTraslados(of,impuesto));
        return imps;
    }

    private static Impuestos.Traslados createTraslados(ObjectFactory of, ImpuestoBean impuesto) {
        Impuestos.Traslados its = of.createComprobanteImpuestosTraslados();
        Impuestos.Traslados.Traslado it = of.createComprobanteImpuestosTrasladosTraslado();
        it.setImpuesto(impuesto.getImpuesto());
        it.setTipoFactor(CTipoFactor.TASA);
        it.setTasaOCuota(new BigDecimal(impuesto.getTasaOCuota()));
        it.setImporte(new BigDecimal(impuesto.getImporte()));
        its.getTraslado().add(it);
        return its;
    }

   

    

    
     
}
