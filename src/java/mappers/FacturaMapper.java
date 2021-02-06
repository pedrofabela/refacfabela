/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mappers;

import beans.FacturaBean;
import java.sql.ResultSet;
import java.sql.SQLException;


public class FacturaMapper implements Mapper {

    public Object mapRow(ResultSet rs) throws SQLException {
        FacturaBean factura = new FacturaBean();

        if (rs.getString("NO_VENTA") != null) {
            factura.setNO_VENTA(rs.getString("NO_VENTA").trim());
        } else {
            factura.setNO_VENTA(rs.getString("NO_VENTA"));
        }
        if (rs.getString("NO_PARTE") != null) {
            factura.setNO_PARTE(rs.getString("NO_PARTE").trim());
        } else {
            factura.setNO_PARTE(rs.getString("NO_PARTE"));
        }
        if (rs.getString("PRECIO_UNITARIO_CONIVA") != null) {
            factura.setPRECIO_UNITARIO_CONIVA(rs.getDouble("PRECIO_UNITARIO_CONIVA"));
        } else {
            factura.setPRECIO_UNITARIO_CONIVA(rs.getDouble("PRECIO_UNITARIO_CONIVA"));
        }
        if (rs.getString("PRECIO_UNITARIO_SINIVA") != null) {
            factura.setPRECIO_UNITARIO_SINIVA(rs.getDouble("PRECIO_UNITARIO_SINIVA"));
        } else {
            factura.setPRECIO_UNITARIO_SINIVA(rs.getDouble("PRECIO_UNITARIO_SINIVA"));
        }
        if (rs.getString("IVA_UNITARIO") != null) {
            factura.setIVA_UNITARIO(rs.getDouble("IVA_UNITARIO"));
        } else {
            factura.setIVA_UNITARIO(rs.getDouble("IVA_UNITARIO"));
        }
        if (rs.getString("CANTIDAD") != null) {
            factura.setCANTIDAD(rs.getInt("CANTIDAD"));
        } else {
            factura.setCANTIDAD(rs.getInt("CANTIDAD"));
        }
        if (rs.getString("IMPORTE_PRODUCTO") != null) {
            factura.setIMPORTE_PRODUCTO(rs.getDouble("IMPORTE_PRODUCTO"));
        } else {
            factura.setIMPORTE_PRODUCTO(rs.getDouble("IMPORTE_PRODUCTO"));
        }
        if (rs.getString("IMPORTE_IVA") != null) {
            factura.setIMPORTE_IVA(rs.getDouble("IMPORTE_IVA"));
        } else {
            factura.setIMPORTE_IVA(rs.getDouble("IMPORTE_IVA"));
        }
        if (rs.getString("TOTAL_CALCULADO") != null) {
            factura.setTOTAL_CALCULADO(rs.getDouble("TOTAL_CALCULADO"));
        } else {
            factura.setTOTAL_CALCULADO(rs.getDouble("TOTAL_CALCULADO"));
        }
        if (rs.getString("PRECIO_FINAL") != null) {
            factura.setPRECIO_FINAL(rs.getDouble("PRECIO_FINAL"));
        } else {
            factura.setPRECIO_FINAL(rs.getDouble("PRECIO_FINAL"));
        }
        if (rs.getString("STATUS_VENTA") != null) {
            factura.setSTATUS_VENTA(rs.getString("STATUS_VENTA"));
        } else {
            factura.setSTATUS_VENTA(rs.getString("STATUS_VENTA"));
        }
        if (rs.getDate("FECHA_VENTA") != null) {
            factura.setFECHA_VENTA(rs.getDate("FECHA_VENTA"));
        } else {
            factura.setFECHA_VENTA(rs.getDate("FECHA_VENTA"));
        }
        if (rs.getString("NO_COTIZA") != null) {
            factura.setNO_COTIZA(rs.getString("NO_COTIZA"));
        } else {
            factura.setNO_COTIZA(rs.getString("NO_COTIZA"));
        }
        if (rs.getString("RASON_CLIENT") != null) {
            factura.setRASON_CLIENT(rs.getString("RASON_CLIENT"));
        } else {
            factura.setRASON_CLIENT(rs.getString("RASON_CLIENT"));
        }
        if (rs.getString("RFC_CLIENT") != null) {
            factura.setRFC_CLIENT(rs.getString("RFC_CLIENT"));
        } else {
            factura.setRFC_CLIENT(rs.getString("RFC_CLIENT"));
        }
        if (rs.getString("NOMBRE_CLIENT") != null) {
            factura.setNOMBRE_CLIENT(rs.getString("NOMBRE_CLIENT"));
        } else {
            factura.setNOMBRE_CLIENT(rs.getString("NOMBRE_CLIENT"));
        }
        if (rs.getString("DIRECCION_CLIENT") != null) {
            factura.setDIRECCION_CLIENT(rs.getString("DIRECCION_CLIENT"));
        } else {
            factura.setDIRECCION_CLIENT(rs.getString("DIRECCION_CLIENT"));
        }
        if (rs.getString("TELEFONO_CLIET") != null) {
            factura.setTELEFONO_CLIET(rs.getString("TELEFONO_CLIET"));
        } else {
            factura.setTELEFONO_CLIET(rs.getString("TELEFONO_CLIET"));
        }
        if (rs.getString("CORREO_CLIENT") != null) {
            factura.setCORREO_CLIENT(rs.getString("CORREO_CLIENT"));
        } else {
            factura.setCORREO_CLIENT(rs.getString("CORREO_CLIENT"));
        }
        if (rs.getString("EMPRESA") != null) {
            factura.setEMPRESA(rs.getString("EMPRESA"));
        } else {
            factura.setEMPRESA(rs.getString("EMPRESA"));
        }
        if (rs.getString("RESP_REGISTRO") != null) {
            factura.setRESP_REGISTRO(rs.getString("RESP_REGISTRO"));
        } else {
            factura.setRESP_REGISTRO(rs.getString("RESP_REGISTRO"));
        }
        if (rs.getString("PRODUCTO") != null) {
            factura.setPRODUCTO(rs.getString("PRODUCTO"));
        } else {
            factura.setPRODUCTO(rs.getString("PRODUCTO"));
        }
        if (rs.getString("UNIDADMEDIDA") != null) {
            factura.setUNIDADMEDIDA(rs.getString("UNIDADMEDIDA"));
        } else {
            factura.setUNIDADMEDIDA(rs.getString("UNIDADMEDIDA"));
        }
        if (rs.getString("CVE_SAT") != null) {
            factura.setCVE_SAT(rs.getString("CVE_SAT"));
        } else {
            factura.setCVE_SAT(rs.getString("CVE_SAT"));
        }
        

        return factura;
    }

}
