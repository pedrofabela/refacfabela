/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import beans.FacturaBean;
import beans.Navegacion;
import beans.camposConBean;
import beans.clientesBean;
import beans.moduloAuxBean;
import beans.moduloBean;
import beans.productosBean;
import beans.proveedoresBean;
import beans.usuarioBean;
import business.AccesoBusiness;
import business.ConsultaBusiness;
import business.FacturaService;
import com.opensymphony.xwork2.ActionSupport;
import daos.FacturaServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;
import utilidades.Constantes;

/**
 *
 * @author pedro
 */
public class Factura_Action extends ActionSupport implements SessionAware {
    
    private FacturaService facturaService;
    
    public Factura_Action() throws Exception{
        this.facturaService = new FacturaServiceImpl();
    }

    private usuarioBean usuariocons;
    private String cveusuario;
    private String pasusuario;
    private String nomModulo;
    private String modulo;
    private String nombreUsuario;
   
    //LISTAS PERSISTENTES DEL MENU
    public List<moduloBean> modulosAUX = new ArrayList<moduloBean>();
    public List<moduloAuxBean> modulosAUXP = new ArrayList<moduloAuxBean>();
    public List<FacturaBean> listausocfdi = new ArrayList<FacturaBean>();
     public List<FacturaBean> listaformapago = new ArrayList<FacturaBean>();
     public List<productosBean> ListaCarroCotizacion = new ArrayList<productosBean>();
    public List<productosBean> ListaTraerProducto = new ArrayList<productosBean>();
  
    Statement objConexion;
    PreparedStatement objPreConexion;
    Connection conecta;
    
    
    // private Map session  = ActionContext.getContext().getSession();
    private String nivelUsuario;
    //Exception
    private String TipoError;
    private String TipoException;

    
    
    
    public List<productosBean> getListaCarroCotizacion() {
        return ListaCarroCotizacion;
    }

    public void setListaCarroCotizacion(List<productosBean> ListaCarroCotizacion) {
        this.ListaCarroCotizacion = ListaCarroCotizacion;
    }

    public List<productosBean> getListaTraerProducto() {
        return ListaTraerProducto;
    }

    public void setListaTraerProducto(List<productosBean> ListaTraerProducto) {
        this.ListaTraerProducto = ListaTraerProducto;
    }

    
    
    
    
    public List<FacturaBean> getListaformapago() {
        return listaformapago;
    }

    public void setListaformapago(List<FacturaBean> listaformapago) {
        this.listaformapago = listaformapago;
    }

    public Statement getObjConexion() {
        return objConexion;
    }

    public void setObjConexion(Statement objConexion) {
        this.objConexion = objConexion;
    }

    public PreparedStatement getObjPreConexion() {
        return objPreConexion;
    }

    public void setObjPreConexion(PreparedStatement objPreConexion) {
        this.objPreConexion = objPreConexion;
    }

    public Connection getConecta() {
        return conecta;
    }

    public void setConecta(Connection conecta) {
        this.conecta = conecta;
    }

    public List<FacturaBean> getListausocfdi() {
        return listausocfdi;
    }

    public void setListausocfdi(List<FacturaBean> listausocfdi) {
        this.listausocfdi = listausocfdi;
    }

    public camposConBean getCamp() {
        return camp;
    }

    public void setCamp(camposConBean camp) {
        this.camp = camp;
    }

  

    public usuarioBean getUsuariocons() {
        return usuariocons;
    }

    public void setUsuariocons(usuarioBean usuariocons) {
        this.usuariocons = usuariocons;
    }

    public String getCveusuario() {
        return cveusuario;
    }

    public void setCveusuario(String cveusuario) {
        this.cveusuario = cveusuario;
    }

    public String getPasusuario() {
        return pasusuario;
    }

    public void setPasusuario(String pasusuario) {
        this.pasusuario = pasusuario;
    }

    public String getNomModulo() {
        return nomModulo;
    }

    public void setNomModulo(String nomModulo) {
        this.nomModulo = nomModulo;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public List<moduloBean> getModulosAUX() {
        return modulosAUX;
    }

    public void setModulosAUX(List<moduloBean> modulosAUX) {
        this.modulosAUX = modulosAUX;
    }

    public List<moduloAuxBean> getModulosAUXP() {
        return modulosAUXP;
    }

    public void setModulosAUXP(List<moduloAuxBean> modulosAUXP) {
        this.modulosAUXP = modulosAUXP;
    }

    public String getNivelUsuario() {
        return nivelUsuario;
    }

    public void setNivelUsuario(String nivelUsuario) {
        this.nivelUsuario = nivelUsuario;
    }

    public String getTipoError() {
        return TipoError;
    }

    public void setTipoError(String TipoError) {
        this.TipoError = TipoError;
    }

    public String getTipoException() {
        return TipoException;
    }

    public void setTipoException(String TipoException) {
        this.TipoException = TipoException;
    }

    public Navegacion getObjNaveg() {
        return objNaveg;
    }

    public void setObjNaveg(Navegacion objNaveg) {
        this.objNaveg = objNaveg;
    }

    public String getLigaActual() {
        return ligaActual;
    }

    public void setLigaActual(String ligaActual) {
        this.ligaActual = ligaActual;
    }


    //******************** PARA OBJETO DE NAVEGACIoN ***********************************************
    private Map session;
    Navegacion objNaveg;
    String ligaRegreso = Navegacion.InitialPage;
    String ligaActual = "";
    
    camposConBean camp =new camposConBean();
    

    public void setSession(Map session) {
        this.session = session;
    }

    public Map getSession() {
        return session;
    }

    public String getLigaRegreso() {
        return ligaRegreso;
    }

    public void setLigaRegreso(String ligaRegreso) {
        this.ligaRegreso = ligaRegreso;
    }
    
    

    public String facturaVenta() {

        try {
            if (session.get("cveUsuario") != null) {
                String sUsu = (String) session.get("cveUsuario");
            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            if (session.containsKey("usuario")) {
                usuariocons = (usuarioBean) session.get("usuario");
                nivelUsuario = usuariocons.getFILTRO();

            } else {
                addActionError("**** La sesión ha expirado *** favor de iniciar una nueva sesion *** ");
                return "SESION";
            }
            
            

         
            
            this.facturaService.FacturarVenta(camp);
            
//            for (int i = 14000; i < 14100; i++) {
//                
//            }
            
            
     

        } catch (Exception e) {
            addActionError("Ocurrio un error: " + e);
            return "ERROR";
        }

        return "SUCCESS";
    }
    
    

   
}
