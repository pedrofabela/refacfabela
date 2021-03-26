/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mappers;

import beans.camposConBean;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author pedro
 */
public class creditosNotaMapper implements Mapper{
     public Object mapRow(ResultSet rs) throws SQLException {
        camposConBean usr = new camposConBean();
        
        
 
        if (rs.getString("NO_VENTA") != null) {
            usr.setNO_VENTA(rs.getString("NO_VENTA").trim());
        } else {
            usr.setNO_VENTA(rs.getString("NO_VENTA"));
        }
        if (rs.getString("FECHA_VENTA") != null) {
            usr.setFECHA_VENTA(rs.getString("FECHA_VENTA").trim());
        } else {
            usr.setFECHA_VENTA(rs.getString("FECHA_VENTA"));
        }
          
        if (rs.getString("ESTATUS") != null) {
            usr.setESTATUS(rs.getString("ESTATUS").trim());
        } else {
            usr.setESTATUS(rs.getString("ESTATUS"));
        }
       
          if (rs.getString("TOTAL_DEUDA") != null) {
            usr.setTOTAL_DEUDA(rs.getFloat("TOTAL_DEUDA"));
        } else {
            usr.setTOTAL_DEUDA(rs.getFloat("TOTAL_DEUDA"));
        }
           if (rs.getString("TOTAL_APORTACIONES_DEUDA") != null) {
            usr.setTOTAL_APORTACIONES_DEUDA(rs.getFloat("TOTAL_APORTACIONES_DEUDA"));
        } else {
            usr.setTOTAL_APORTACIONES_DEUDA(rs.getFloat("TOTAL_APORTACIONES_DEUDA"));
        }
             if (rs.getString("SALDO_DEUDA") != null) {
            usr.setSALDO_DEUDA(rs.getFloat("SALDO_DEUDA"));
        } else {
            usr.setSALDO_DEUDA(rs.getFloat("SALDO_DEUDA"));
        }
              if (rs.getString("AVANCE_DEUDA") != null) {
            usr.setAVANCE_DEUDA(rs.getString("AVANCE_DEUDA").trim());
        } else {
            usr.setAVANCE_DEUDA(rs.getString("AVANCE_DEUDA"));
        }
         
          return usr;
       }
}
