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
import business.FacturaService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappers.FacturaMapper;
import mx.bigdata.sat.cfdi.v33.schema.Comprobante;

/**
 *
 * @author gioca
 */
public class FacturaServiceImpl extends OracleDAOFactory implements FacturaService {

    private GeneraXml generaxml = new GeneraXml();
    private Transformar transforma = new Transformar();
    private TimbrarXml timbrarXml= new TimbrarXml();

    @Override
    public void FacturarVenta(Integer idVenta) {

        try {

            List<FacturaBean> listaFacturaVenta = this.obtenerVenta(idVenta);

            //System.out.println("listaFacturaVenta: "+listaFacturaVenta.size());
            CabeceraXmlBean cabeceraXmlBean = new CabeceraXmlBean();
            ImpuestoBean impuestoBean= new ImpuestoBean();
            
            //pasamos lista de base a objeto cabeceraXmlBean
            cabeceraXmlBean = transforma.objCabecera(listaFacturaVenta);
            
            //pasamos lista de base a lista concepto
            List<ConceptoXmlBean> listaConceptos = transforma.listaConceptos(listaFacturaVenta);
            
            //pasamos lista de base a objeto impuesto bean
            impuestoBean = transforma.obtenerImpuestoTotal(listaFacturaVenta);

            // generamos xml
            Comprobante xml = generaxml.createComprobante(cabeceraXmlBean, listaConceptos, impuestoBean);
            
            //timbramos xml
            timbrarXml.timbrarXml(xml, idVenta);
            
            

        } catch (Exception ex) {
            Logger.getLogger(FacturaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
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

}
