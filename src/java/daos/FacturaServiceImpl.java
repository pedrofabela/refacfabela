/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import Transformador.Transformar;
import beans.CabeceraXmlBean;
import beans.ConceptoXmlBean;
import beans.FacturaBean;
import beans.ImpuestoBean;
import beans.camposConBean;
import business.FacturaService;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import mappers.FacturaMapper;
import mappers.catalogosMapper;
import mx.bigdata.sat.cfdi.v33.schema.Comprobante;
import utilidades.Constantes;
import utilidades.ObjPrepareStatement;

/**
 *
 * @author gioca
 */
public class FacturaServiceImpl extends OracleDAOFactory implements FacturaService {

    private GeneraXml generaxml = new GeneraXml();
    private Transformar transforma = new Transformar();
    private TimbrarXml timbrarXml= new TimbrarXml();
    String error="";

    @Override
    public void FacturarVenta(camposConBean camp) {

        try {

            List<FacturaBean> listaFacturaVenta = this.obtenerVenta(Integer.parseInt(camp.getNO_VENTA()));
            
            System.out.println("lista:"+listaFacturaVenta.size());

            //System.out.println("listaFacturaVenta: "+listaFacturaVenta.size());
            CabeceraXmlBean cabeceraXmlBean = new CabeceraXmlBean();
            ImpuestoBean impuestoBean= new ImpuestoBean();
            
            //pasamos lista de base a objeto cabeceraXmlBean
            cabeceraXmlBean = transforma.objCabecera(listaFacturaVenta, camp);
            
            //pasamos lista de base a lista concepto
            List<ConceptoXmlBean> listaConceptos = transforma.listaConceptos(listaFacturaVenta);
            
            //pasamos lista de base a objeto impuesto bean
            impuestoBean = transforma.obtenerImpuestoTotal(listaFacturaVenta);

            // generamos xml
            Comprobante xml = generaxml.createComprobante(cabeceraXmlBean, listaConceptos, impuestoBean);
            
            //timbramos xml
            timbrarXml.timbrarXml(xml, Integer.parseInt(camp.getNO_VENTA()), cabeceraXmlBean);
            
            

        } catch (Exception ex) {
            Logger.getLogger(FacturaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void  guardarDatosFactura(FacturaBean facturaBean) throws Exception {

        ArrayList<ObjPrepareStatement> arregloCampos = new ArrayList<ObjPrepareStatement>();
        ObjPrepareStatement temporal;
       
           temporal = new ObjPrepareStatement("NO_VENTA", "STRING", facturaBean.getNO_VENTA());
        arregloCampos.add(temporal);
        temporal = new ObjPrepareStatement("UUID", "STRING", facturaBean.getUUID());
        arregloCampos.add(temporal);
        temporal = new ObjPrepareStatement("ESTADO", "STRING", facturaBean.getESTADO());
        arregloCampos.add(temporal);
        temporal = new ObjPrepareStatement("NO_CERTIFICADOSAT", "STRING", facturaBean.getNO_CERTIFICADOSAT());
        arregloCampos.add(temporal);
        temporal = new ObjPrepareStatement("SELLOCFD", "STRING", facturaBean.getSELLOCFD());
        arregloCampos.add(temporal);
        temporal = new ObjPrepareStatement("SELLOSAT", "STRING", facturaBean.getSELLOSAT());
        arregloCampos.add(temporal);
        temporal = new ObjPrepareStatement("CADENAORIGINAL", "STRING", facturaBean.getCADENAORIGINAL());
        arregloCampos.add(temporal);

        super.queryInsert("FACTURAS", arregloCampos);
     }
            
            

      
 

    private List<FacturaBean> obtenerVenta(int idVenta) {

        String query = "SELECT\n" +
"    *\n" +
"FROM\n" +
"    (\n" +
"        SELECT\n" +
"            ven.no_venta,\n" +
"            ven.no_parte,\n" +
"            ven.precio_unitario                                                                                                                          AS precio_unitario_coniva,\n" +
"            trunc (to_number(ven.precio_unitario) /(1.16), 2    )                                                                                        AS precio_unitario_siniva,\n" +
"            trunc (to_number(ven.precio_unitario) - to_number(ven.precio_unitario) /(1.16),2  )                                                        AS iva_unitario,\n" +
"            ven.no_productoventa AS cantidad,\n" +
"            trunc(to_number(ven.precio_unitario) /(1.16), 2) * to_number(no_productoventa)                                                               AS importe_producto,\n" +
"            trunc ( ( to_number(ven.precio_unitario) - to_number(ven.precio_unitario) /(1.16) ) * to_number(no_productoventa),2 )                         AS importe_iva,\n" +
"            trunc ( ( ( to_number(ven.precio_unitario) /(1.16) * to_number(no_productoventa) ) + ( to_number(ven.precio_unitario) -\n" +
"            to_number(ven.precio_unitario) /(1.16) ) * to_number(no_productoventa) ), 2  )                                                         AS total_calculado,\n" +
"            ven.precio_final,\n" +
"            ven.status_venta,\n" +
"            ven.fecha_venta,\n" +
"            ven.no_cotiza,\n" +
"            cli.*,\n" +
"            pro.producto,\n" +
"            'PZA'                                                                                                                                        AS unidadmedida,\n" +
"            pro.cve_sat\n" +
"        FROM\n" +
"                 venta_productos ven\n" +
"            JOIN client     cli ON ven.rfc_cliente = cli.rfc_client\n" +
"            JOIN productos  pro ON pro.no_parte = ven.no_parte\n" +
"    )\n" +
"WHERE\n" +
"        status_venta = 2\n" +
"    AND no_venta = '"+idVenta+"'";

        List listaVenta = null;

        try {
            listaVenta = queryForList(query, new FacturaMapper());
            //System.out.println("Lista venta: "+listaVenta.toString());
        } catch (Exception ex) {
            Logger.getLogger(FacturaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaVenta;

    }

    @Override
    public List<camposConBean> Obtenerusocfdi() {
        List listaUso = null;
        
          String query = "SELECT S_CLAVE, S_NOMBRE FROM CAT_USOCFDI WHERE N_ESTATUS=1";
        
          
          
        try {
            listaUso = queryForList(query, new catalogosMapper());
            //System.out.println("Lista venta: "+listaVenta.toString());
        } catch (Exception ex) {
            Logger.getLogger(FacturaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
        return listaUso;
    }

    @Override
    public List<camposConBean> Obtenerformapago() {
        
        List listaformapago = null;
        
          String query = "SELECT S_CLAVE, S_NOMBRE FROM CAT_FORMAPAGO WHERE N_ESTATUS=1";
        
          
          
        try {
            listaformapago = queryForList(query, new catalogosMapper());
            //System.out.println("Lista venta: "+listaVenta.toString());
        } catch (Exception ex) {
            Logger.getLogger(FacturaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
        return listaformapago;
        
        
    }
    
    
    
   

}
