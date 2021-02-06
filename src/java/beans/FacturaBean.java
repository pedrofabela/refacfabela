/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author gioca
 */
public class FacturaBean implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    public String NO_VENTA;
    public String NO_PARTE;
    public Double PRECIO_UNITARIO_CONIVA;
    public Double PRECIO_UNITARIO_SINIVA;
    public Double IVA_UNITARIO;
    public Integer CANTIDAD;
    public Double IMPORTE_PRODUCTO;
    public Double IMPORTE_IVA;
    public Double TOTAL_CALCULADO;
    public Double PRECIO_FINAL;
    public String STATUS_VENTA;
    public Date FECHA_VENTA;
    public String NO_COTIZA;
    public String RASON_CLIENT;
    public String RFC_CLIENT;
    public String NOMBRE_CLIENT;
    public String DIRECCION_CLIENT;
    public String TELEFONO_CLIET;
    public String CORREO_CLIENT;
    public String EMPRESA;
    public String RESP_REGISTRO;
    public String PRODUCTO;
    public String UNIDADMEDIDA;
    public String CVE_SAT;
    
    //FACTURACIÓN 
    public String N_ID;
    public String UUID;
    public String ESTADO;
    public String NO_CERTIFICADOSAT;
    public String SELLOCFD;
    public String SELLOSAT;
    public String CADENAORIGINAL;
    public String S_CLAVE;
    public String S_NOMBRE;

    public String getS_CLAVE() {
        return S_CLAVE;
    }

    public void setS_CLAVE(String S_CLAVE) {
        this.S_CLAVE = S_CLAVE;
    }

    public String getS_NOMBRE() {
        return S_NOMBRE;
    }

    public void setS_NOMBRE(String S_NOMBRE) {
        this.S_NOMBRE = S_NOMBRE;
    }
    

    
    
    public String getN_ID() {
        return N_ID;
    }

    public void setN_ID(String N_ID) {
        this.N_ID = N_ID;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getESTADO() {
        return ESTADO;
    }

    public void setESTADO(String ESTADO) {
        this.ESTADO = ESTADO;
    }

    public String getNO_CERTIFICADOSAT() {
        return NO_CERTIFICADOSAT;
    }

    public void setNO_CERTIFICADOSAT(String NO_CERTIFICADOSAT) {
        this.NO_CERTIFICADOSAT = NO_CERTIFICADOSAT;
    }

    public String getSELLOCFD() {
        return SELLOCFD;
    }

    public void setSELLOCFD(String SELLOCFD) {
        this.SELLOCFD = SELLOCFD;
    }

    public String getSELLOSAT() {
        return SELLOSAT;
    }

    public void setSELLOSAT(String SELLOSAT) {
        this.SELLOSAT = SELLOSAT;
    }

    public String getCADENAORIGINAL() {
        return CADENAORIGINAL;
    }

    public void setCADENAORIGINAL(String CADENAORIGINAL) {
        this.CADENAORIGINAL = CADENAORIGINAL;
    }

    
            

    public String getNO_VENTA() {
        return NO_VENTA;
    }

    public void setNO_VENTA(String NO_VENTA) {
        this.NO_VENTA = NO_VENTA;
    }

    public String getNO_PARTE() {
        return NO_PARTE;
    }

    public void setNO_PARTE(String NO_PARTE) {
        this.NO_PARTE = NO_PARTE;
    }

    public Double getPRECIO_UNITARIO_CONIVA() {
        return PRECIO_UNITARIO_CONIVA;
    }

    public void setPRECIO_UNITARIO_CONIVA(Double PRECIO_UNITARIO_CONIVA) {
        this.PRECIO_UNITARIO_CONIVA = PRECIO_UNITARIO_CONIVA;
    }

    public Double getPRECIO_UNITARIO_SINIVA() {
        return PRECIO_UNITARIO_SINIVA;
    }

    public void setPRECIO_UNITARIO_SINIVA(Double PRECIO_UNITARIO_SINIVA) {
        this.PRECIO_UNITARIO_SINIVA = PRECIO_UNITARIO_SINIVA;
    }

    public Double getIVA_UNITARIO() {
        return IVA_UNITARIO;
    }

    public void setIVA_UNITARIO(Double IVA_UNITARIO) {
        this.IVA_UNITARIO = IVA_UNITARIO;
    }

    public Integer getCANTIDAD() {
        return CANTIDAD;
    }

    public void setCANTIDAD(Integer CANTIDAD) {
        this.CANTIDAD = CANTIDAD;
    }

    public Double getIMPORTE_PRODUCTO() {
        return IMPORTE_PRODUCTO;
    }

    public void setIMPORTE_PRODUCTO(Double IMPORTE_PRODUCTO) {
        this.IMPORTE_PRODUCTO = IMPORTE_PRODUCTO;
    }

    public Double getIMPORTE_IVA() {
        return IMPORTE_IVA;
    }

    public void setIMPORTE_IVA(Double IMPORTE_IVA) {
        this.IMPORTE_IVA = IMPORTE_IVA;
    }

    public Double getTOTAL_CALCULADO() {
        return TOTAL_CALCULADO;
    }

    public void setTOTAL_CALCULADO(Double TOTAL_CALCULADO) {
        this.TOTAL_CALCULADO = TOTAL_CALCULADO;
    }

    public Double getPRECIO_FINAL() {
        return PRECIO_FINAL;
    }

    public void setPRECIO_FINAL(Double PRECIO_FINAL) {
        this.PRECIO_FINAL = PRECIO_FINAL;
    }

    public String getSTATUS_VENTA() {
        return STATUS_VENTA;
    }

    public void setSTATUS_VENTA(String STATUS_VENTA) {
        this.STATUS_VENTA = STATUS_VENTA;
    }

    public Date getFECHA_VENTA() {
        return FECHA_VENTA;
    }

    public void setFECHA_VENTA(Date FECHA_VENTA) {
        this.FECHA_VENTA = FECHA_VENTA;
    }

   
    

    public String getNO_COTIZA() {
        return NO_COTIZA;
    }

    public void setNO_COTIZA(String NO_COTIZA) {
        this.NO_COTIZA = NO_COTIZA;
    }

    public String getRASON_CLIENT() {
        return RASON_CLIENT;
    }

    public void setRASON_CLIENT(String RASON_CLIENT) {
        this.RASON_CLIENT = RASON_CLIENT;
    }

    public String getRFC_CLIENT() {
        return RFC_CLIENT;
    }

    public void setRFC_CLIENT(String RFC_CLIENT) {
        this.RFC_CLIENT = RFC_CLIENT;
    }

    public String getNOMBRE_CLIENT() {
        return NOMBRE_CLIENT;
    }

    public void setNOMBRE_CLIENT(String NOMBRE_CLIENT) {
        this.NOMBRE_CLIENT = NOMBRE_CLIENT;
    }

    public String getDIRECCION_CLIENT() {
        return DIRECCION_CLIENT;
    }

    public void setDIRECCION_CLIENT(String DIRECCION_CLIENT) {
        this.DIRECCION_CLIENT = DIRECCION_CLIENT;
    }

    public String getTELEFONO_CLIET() {
        return TELEFONO_CLIET;
    }

    public void setTELEFONO_CLIET(String TELEFONO_CLIET) {
        this.TELEFONO_CLIET = TELEFONO_CLIET;
    }

    public String getCORREO_CLIENT() {
        return CORREO_CLIENT;
    }

    public void setCORREO_CLIENT(String CORREO_CLIENT) {
        this.CORREO_CLIENT = CORREO_CLIENT;
    }

    public String getEMPRESA() {
        return EMPRESA;
    }

    public void setEMPRESA(String EMPRESA) {
        this.EMPRESA = EMPRESA;
    }

    public String getRESP_REGISTRO() {
        return RESP_REGISTRO;
    }

    public void setRESP_REGISTRO(String RESP_REGISTRO) {
        this.RESP_REGISTRO = RESP_REGISTRO;
    }

    public String getPRODUCTO() {
        return PRODUCTO;
    }

    public void setPRODUCTO(String PRODUCTO) {
        this.PRODUCTO = PRODUCTO;
    }

    public String getUNIDADMEDIDA() {
        return UNIDADMEDIDA;
    }

    public void setUNIDADMEDIDA(String UNIDADMEDIDA) {
        this.UNIDADMEDIDA = UNIDADMEDIDA;
    }

    public String getCVE_SAT() {
        return CVE_SAT;
    }

    public void setCVE_SAT(String CVE_SAT) {
        this.CVE_SAT = CVE_SAT;
    }

    @Override
    public String toString() {
        return "FacturaBean{" + "NO_VENTA=" + NO_VENTA + ", NO_PARTE=" + NO_PARTE + ", PRECIO_UNITARIO_CONIVA=" + PRECIO_UNITARIO_CONIVA + ", PRECIO_UNITARIO_SINIVA=" + PRECIO_UNITARIO_SINIVA + ", IVA_UNITARIO=" + IVA_UNITARIO + ", CANTIDAD=" + CANTIDAD + ", IMPORTE_PRODUCTO=" + IMPORTE_PRODUCTO + ", IMPORTE_IVA=" + IMPORTE_IVA + ", TOTAL_CALCULADO=" + TOTAL_CALCULADO + ", PRECIO_FINAL=" + PRECIO_FINAL + ", STATUS_VENTA=" + STATUS_VENTA + ", FECHA_VENTA=" + FECHA_VENTA + ", NO_COTIZA=" + NO_COTIZA + ", RASON_CLIENT=" + RASON_CLIENT + ", RFC_CLIENT=" + RFC_CLIENT + ", NOMBRE_CLIENT=" + NOMBRE_CLIENT + ", DIRECCION_CLIENT=" + DIRECCION_CLIENT + ", TELEFONO_CLIET=" + TELEFONO_CLIET + ", CORREO_CLIENT=" + CORREO_CLIENT + ", EMPRESA=" + EMPRESA + ", RESP_REGISTRO=" + RESP_REGISTRO + ", PRODUCTO=" + PRODUCTO + ", UNIDADMEDIDA=" + UNIDADMEDIDA + ", CVE_SAT=" + CVE_SAT + '}';
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
