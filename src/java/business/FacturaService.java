/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import beans.FacturaBean;
import beans.camposConBean;
import java.util.List;

/**
 *
 * @author gioca
 */
public interface FacturaService {
    
    public boolean FacturarVenta(camposConBean camp);
    public List <camposConBean> Obtenerusocfdi();
      public List <camposConBean> Obtenerformapago();
        public List <FacturaBean> obtenerVenta(int idventa);
    
}
