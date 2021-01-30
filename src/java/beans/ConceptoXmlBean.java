/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;

/**
 *
 * @author gioca
 */
public class ConceptoXmlBean implements Serializable{
     private static final long serialVersionUID = 1L;
     
     public String claveUnidad;
     public String claveProdServ;
     public String noIdentificacion;
     public String cantidad;
     public String unidad;
     public String descripcion;
     public String valorUnitario;
     public String importe;
     public String descuento;
     public String Base;
     public String impuesto;
     public String tipoFactor;
     public String tasaOCuota;
     public String importeImpuesto;

    public String getClaveUnidad() {
        return claveUnidad;
    }

    public void setClaveUnidad(String claveUnidad) {
        this.claveUnidad = claveUnidad;
    }

    public String getClaveProdServ() {
        return claveProdServ;
    }

    public void setClaveProdServ(String claveProdServ) {
        this.claveProdServ = claveProdServ;
    }

    public String getNoIdentificacion() {
        return noIdentificacion;
    }

    public void setNoIdentificacion(String noIdentificacion) {
        this.noIdentificacion = noIdentificacion;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(String valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getBase() {
        return Base;
    }

    public void setBase(String Base) {
        this.Base = Base;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public String getTipoFactor() {
        return tipoFactor;
    }

    public void setTipoFactor(String tipoFactor) {
        this.tipoFactor = tipoFactor;
    }

    public String getTasaOCuota() {
        return tasaOCuota;
    }

    public void setTasaOCuota(String tasaOCuota) {
        this.tasaOCuota = tasaOCuota;
    }

    public String getImporteImpuesto() {
        return importeImpuesto;
    }

    public void setImporteImpuesto(String importeImpuesto) {
        this.importeImpuesto = importeImpuesto;
    }

    @Override
    public String toString() {
        return "ConceptoXmlBean{" + "claveUnidad=" + claveUnidad + ", claveProdServ=" + claveProdServ + ", noIdentificacion=" + noIdentificacion + ", cantidad=" + cantidad + ", unidad=" + unidad + ", descripcion=" + descripcion + ", valorUnitario=" + valorUnitario + ", importe=" + importe + ", descuento=" + descuento + ", Base=" + Base + ", impuesto=" + impuesto + ", tipoFactor=" + tipoFactor + ", tasaOCuota=" + tasaOCuota + ", importeImpuesto=" + importeImpuesto + '}';
    }

    
    
    

   
     
    
    
}
