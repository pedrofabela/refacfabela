/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transformador;

import beans.CabeceraXmlBean;
import beans.ConceptoXmlBean;
import beans.FacturaBean;
import beans.ImpuestoBean;
import java.util.ArrayList;
import java.util.List;
import utilidades.ConstantesGenerales;

/**
 *
 * @author gioca
 */
public class Transformar {

    public CabeceraXmlBean objCabecera(List<FacturaBean> lista) {

        System.out.println("lista ObjectCabecera: " + lista);

        CabeceraXmlBean cabeceraXmlBean = new CabeceraXmlBean();
        

        for (FacturaBean facturaBean : lista) {
            cabeceraXmlBean.setVersion(ConstantesGenerales.version);
            cabeceraXmlBean.setSerie(ConstantesGenerales.serie);
            cabeceraXmlBean.setFolio(facturaBean.getNO_VENTA());
            cabeceraXmlBean.setFormaPago(ConstantesGenerales.formaPago);
            cabeceraXmlBean.setCondicionesPago(ConstantesGenerales.condicionesPago);
            cabeceraXmlBean.setSubTotal(String.valueOf(calculaSubTotal(lista)));
            cabeceraXmlBean.setMoneda(ConstantesGenerales.moneda);
            cabeceraXmlBean.setTotal(String.valueOf(calcularTotal(lista)));
            cabeceraXmlBean.setTipoComprobante(ConstantesGenerales.tipoComprobante);
            cabeceraXmlBean.setMetodoPago(ConstantesGenerales.MetodoPago);
            cabeceraXmlBean.setLugarExpedicion(ConstantesGenerales.lugarExpedicion);
            cabeceraXmlBean.setNoCertificado(ConstantesGenerales.noCertificado);
            cabeceraXmlBean.setCertificado(ConstantesGenerales.certificado);
            cabeceraXmlBean.setSello("");
            cabeceraXmlBean.setNombreEmisor(ConstantesGenerales.nombreEmisor);
            cabeceraXmlBean.setRfcEmisor(ConstantesGenerales.rfcEmisor);
            cabeceraXmlBean.setRegimenFiscal(ConstantesGenerales.regimenFiscal);
            cabeceraXmlBean.setUsoCFDI(ConstantesGenerales.usoCFDI);
            cabeceraXmlBean.setNombreReceptor(facturaBean.getRASON_CLIENT());
            cabeceraXmlBean.setRfcReceptor(ConstantesGenerales.rfcReceptor);// cambiar por rfc de consulta         
        }

        return cabeceraXmlBean;

    }

    public List<ConceptoXmlBean> listaConceptos(List<FacturaBean> lista) {

        System.out.println("listaConceptos: " + lista);
        List<ConceptoXmlBean> listaConcepto = new ArrayList<ConceptoXmlBean>();

        for (int i = 0; i < lista.size(); i++) {
            ConceptoXmlBean conceptoXmlBean = new ConceptoXmlBean();
            conceptoXmlBean.setClaveUnidad(ConstantesGenerales.claveUnidad);
            conceptoXmlBean.setClaveProdServ(lista.get(i).getCVE_SAT());
            conceptoXmlBean.setNoIdentificacion(lista.get(i).getNO_PARTE());
            conceptoXmlBean.setCantidad(lista.get(i).getCANTIDAD().toString());
            conceptoXmlBean.setUnidad(lista.get(i).getUNIDADMEDIDA());
            conceptoXmlBean.setDescripcion(lista.get(i).getPRODUCTO());
            conceptoXmlBean.setValorUnitario(lista.get(i).getPRECIO_UNITARIO_SINIVA().toString());
            conceptoXmlBean.setImporte(lista.get(i).getIMPORTE_PRODUCTO().toString());
            conceptoXmlBean.setBase(lista.get(i).getIMPORTE_PRODUCTO().toString());
            conceptoXmlBean.setImpuesto(ConstantesGenerales.impuesto);
            conceptoXmlBean.setTipoFactor(ConstantesGenerales.TipoFactor);
            conceptoXmlBean.setTasaOCuota(ConstantesGenerales.TasaOCuota);
            conceptoXmlBean.setImporteImpuesto(lista.get(i).getIMPORTE_IVA().toString());
            listaConcepto.add(conceptoXmlBean);
        }

        return listaConcepto;

    }

    public ImpuestoBean obtenerImpuestoTotal(List<FacturaBean> lista) {
        ImpuestoBean impuesto = new ImpuestoBean();
        impuesto.setTotalImpuestosTraslados(String.valueOf(calculaIvaTotal(lista)));
        impuesto.setImpuesto(ConstantesGenerales.impuesto);
        impuesto.setTipoFactor(ConstantesGenerales.TipoFactor);
        impuesto.setTasaOCuota(ConstantesGenerales.TasaOCuota);
        impuesto.setImporte(String.valueOf(calculaIvaTotal(lista)));

        return impuesto;
    }

    private static Double calculaSubTotal(List<FacturaBean> lista) {
        Double subTotal = 0.0;
        for (int i = 0; i < lista.size(); i++) {
            subTotal = subTotal + lista.get(i).getIMPORTE_PRODUCTO();
        }
        return ConstantesGenerales.truncaValor(subTotal);
    }

    private static Double calculaIvaTotal(List<FacturaBean> lista) {
        Double iva = 0.0;
        for (int i = 0; i < lista.size(); i++) {
            iva = iva + lista.get(i).getIMPORTE_IVA();
        }

        return ConstantesGenerales.truncaValor(iva);
    }
    
    private static Double calcularTotal(List<FacturaBean> lista){     
      Double  total= calculaSubTotal(lista) + calculaIvaTotal(lista);    
        return ConstantesGenerales.truncaValor(total);
    }

}