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
public class abonosMapper implements Mapper{
       public Object mapRow(ResultSet rs) throws SQLException {
        camposConBean usr = new camposConBean();
        
        
 
        if (rs.getString("N_ID") != null) {
            usr.setN_ID(rs.getString("N_ID"));
        } else {
            usr.setN_ID(rs.getString("N_ID"));
        }
        if (rs.getString("APORTACION") != null) {
            usr.setAPORTACION(rs.getString("APORTACION"));
        } else {
            usr.setAPORTACION(rs.getString("APORTACION"));
        }
          
        if (rs.getString("FECHA_APORTACION") != null) {
            usr.setFECHA_APORTACION(rs.getString("FECHA_APORTACION"));
        } else {
            usr.setFECHA_APORTACION(rs.getString("FECHA_APORTACION"));
        }
        
         
          return usr;
       }
}
