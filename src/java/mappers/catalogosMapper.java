/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mappers;

import beans.FacturaBean;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author pedro
 */
public class catalogosMapper implements Mapper{
    
    public Object mapRow(ResultSet rs) throws SQLException {
        FacturaBean factura = new FacturaBean();

        if (rs.getString("S_CLAVE") != null) {
            factura.setS_CLAVE(rs.getString("S_CLAVE").trim());
        } else {
            factura.setS_CLAVE(rs.getString("S_CLAVE"));
        }
        if (rs.getString("S_NOMBRE") != null) {
            factura.setS_NOMBRE(rs.getString("S_NOMBRE").trim());
        } else {
            factura.setS_NOMBRE(rs.getString("S_NOMBRE"));
        }
        
        

        return factura;
    }
    
}
