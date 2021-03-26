/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mappers;

import beans.camposConBean;
import beans.productosBean;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author pedro
 */
public class creditosMapper implements Mapper{
    
    public Object mapRow(ResultSet rs) throws SQLException {
        camposConBean usr = new camposConBean();
        
        
 
        if (rs.getString("RFC_CLIENTE") != null) {
            usr.setRFC_CLIENTE(rs.getString("RFC_CLIENTE").trim());
        } else {
            usr.setRFC_CLIENTE(rs.getString("RFC_CLIENTE"));
        }
        if (rs.getString("RAZONSOCIAL") != null) {
            usr.setRAZONSOCIAL(rs.getString("RAZONSOCIAL").trim());
        } else {
            usr.setRAZONSOCIAL(rs.getString("RAZONSOCIAL"));
        }
          
        if (rs.getString("TELEFONO_CLIENTE") != null) {
            usr.setTELEFONO_CLIENTE(rs.getString("TELEFONO_CLIENTE").trim());
        } else {
            usr.setTELEFONO_CLIENTE(rs.getString("TELEFONO_CLIENTE"));
        }
         if (rs.getString("CORREO_CLIENTE") != null) {
            usr.setCORREO_CLIENTE(rs.getString("CORREO_CLIENTE").trim());
        } else {
            usr.setCORREO_CLIENTE(rs.getString("CORREO_CLIENTE"));
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
