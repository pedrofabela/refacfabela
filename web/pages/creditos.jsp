<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>


<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart);

      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['Fecha', 'Precio al Púbico', 'Precio de provedor'],
    <s:iterator value="ListaProductoHist" id="  ListaProductoHist" status="stat" >
                ['<s:property value="FECHAINGRESO"/>',  <s:property value="PRECIO" />,    <s:property value="PRECIO_PESO"/>],
    </s:iterator>
         
        ]);

        var options = {
          title: 'Historial de ingreso de los productos',
          hAxis: {title: 'Fecha de ingreso del Producto',  titleTextStyle: {color: '#333'}},
          vAxis: {minValue: 0}
        };

        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
    </script>

<script type="text/javascript">
	//EVENTOS EN javascript

	
        function hacer_click() { 
             location.href="#popup";
         
        
       
            
           
        } 
	
       
       


	function hacer_hover()
	{
		alert("Estas sobre mi!");
	}

	function salir_del_hover()
	{
		alert("Adios!");
	}

	function cargar_pagina()
	{alert("Ya se ha cargado el sitio web");}
	</script>


<script>
  function validarSiNumero(numero){
    if (!/^([0-9])*$/.test(numero))
     
     document.forma.INCREMENTO.value = "";
     
     
     
  }
</script>






<html xmlns="http://www.w3.org/1999/xhtml">


    <link href="css/menu.css" rel="stylesheet" type="text/css" />
    <link href="css/style.css" rel="stylesheet" type="text/css" />
      <script type="text/javascript">
      function guardaValida(accion) {
            
        var stock=<s:property value="camp.TOTAL_PRODUCTO_BODEGAS"></s:property>;
     var vendidos =document.getElementById("CANTIDAD_VENTA").value;
     
      
      if(stock<vendidos){
      var mensaje =confirm("¡No se cuenta con la catidad de productos solicitada!, ¿Deseas agregar el producto a la cotización?"); 
        
        if (mensaje) {
 document.forma.action = accion;
           document.forma.submit();


}
//Detectamos si el usuario denegó el mensaje
else {

}
        
    } 
    else {
        
        document.forma.action = accion;
           document.forma.submit();
        
    }
        
          
       
        }
    </script>
    
    <script type="text/javascript">

        function guarda(accion) {
            
            
          
            document.forma.action = accion;
            document.forma.submit();
       
        }
        function incremento(accion) {
            
            numero=document.getElementById("INCREMENTO").value;
            
            if (!/^([0-9])*$/.test(numero)){
     
     document.forma.INCREMENTO.value = "";
 }
 else{
            document.forma.action = accion;
            document.forma.submit();
        }
        }
       
        
         
        function actualiza(accion, valor) {

            document.forma.RFCAUX.value = valor;
            document.forma.action = accion;
            document.forma.target = "_self";
            document.forma.submit();
        }
         function consultaProducto(accion, valor) {

            document.forma.BUSCARCLIENTE.value = valor;
            document.forma.action = accion;          
            document.forma.submit();
        }
        function consultaProductoAbono(accion, valor) {

            document.forma.VENTA.value = valor;
            document.forma.action = accion;          
            document.forma.submit();
        }
        function consulta(accion, valor) {

            document.forma.CONSULTA_PARTE.value = valor;
            document.forma.action = accion;
            document.forma.target = "_self";
            document.forma.submit();
        }
     function consultaCliente(accion, valor, valor2) {

            document.forma.AUX_RFC_CLIENTE.value = valor;
             document.forma.RFCCLIENTE.value = valor2;
            document.forma.action = accion;
            document.forma.target = "_self";
            document.forma.submit();
        }

        //PARA REGRESAR EN DONDE SE QUEDO...........
        window.onload = function () {
            var pos = window.name || 0;
            window.scrollTo(0, pos);
        }
        window.onunload = function () {
            window.name = self.pageYOffset
                    || (document.documentElement.scrollTop + document.body.scrollTop);
        }
    </script>


    <head>
        <title>Refacciones Fabela</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    </head>
    <body>



        <header id="main-header">



            <h1  class="logo">Refacciones Fabela</h1>

        </header><!-- / #main-header -->
        <jsp:include page="/pages/menu.jsp"></jsp:include>
        <s:form name="forma" >
            <section id="main-content">



                <div class="div-contenido_gral">  
                    <h1 class="user-sesion" align="right">USUARIO:  <s:property value="usuariocons.NAMEUSUARIO" ></s:property></h1>
                    <s:hidden name="usuariocons.NAMEUSUARIO" id="%{usuariocons.NAMEUSUARIO}"></s:hidden> 

                        <article class="articulo1" >
                            
                          
                                
                            <!-- BUSQUEDA DE CLIENTE PARA LA VENTA O NOTA -->    
                                
                               
                                <div class="div_sec1" id="flotante-busqueda">
                            <div class="div-busqueda">
                                 <div class="div_titulos_sec"> <h2 class="text-tit-sec">Busqueda</h2></div>
                            <s:textfield name="camp.BUSCARCLIENTE" id="BUSCARCLIENTE" placeholder="RFC ó Nombre ó Empresa" required="true"  cssClass="campoFormBusqueda" onKeyUp="this.value=this.value.toUpperCase();"/>
                           <s:textfield name="camp.NO_VENTA" id="VENTA" cssClass="campoFormBusqueda" />
                          

                              <a href="Javascript:guarda('consultaCreditos')"><div class="boton">  Buscar  </div> </a>
                              
                              <img src="img/maquina-busqueda.jpg" alt="Maquina" style="width: 90%; margin-top: 20px; margin-bottom: 20px;" ></img>
                            </div>



                        </div>
                        

                        </article> 
                        <article class="articulo2">

                     
                            
                
                            <!--LISTA DE CREDITOS EN GENERAL -->
                            <s:if test="listaCreditos.size()>0 ">
                                <div class="div_sec1">
                                    <div class="div_titulos_sec"> <h2 class="text-tit-sec">Clientes con créditos activos</h2></div>

                                    <div style="width: 30%; float: right; margin-right: 20px; background: purple; text-align: center; display: inline-block; color: white; margin-bottom: 10px; border-radius: 10px;">Total de Clientes:  <s:property value="listaCreditos.size()"></s:property></div>

                                        <div id="scroltabla-cliente" style="overflow: scroll; height: 350px;">
                                            <table id="customers">
                                                <thead>
                                                    <th>NP</th>
                                                    <th>RFC</th>
                                                    <th>RAZON SOCIAL</th>
                                                    <th>TELÉFONO</th>
                                                    <th>CORREO ELECTRÓNICO</th>
                                                    <th>TOTAL DEUDA</th>
                                                    <th>APORTACIONES</th>
                                                    <th>SALDO ACTUAL</th>
                                                    <th>AVANCE EN EL PAGO</th>
                                                    <th>VER</th>

                                                </thead>

                                            <s:iterator value="listaCreditos" id="listaCreditos" status="stat" >
                                                <tr>
                                                    <td align="center"><s:property value="#stat.count" /></td>
                                                    <td><s:property value="RFC_CLIENTE" /></td>
                                                    <td><s:property value="RAZONSOCIAL" /></td>
                                                    <td><s:property value="TELEFONO_CLIENTE" /></td>                                                 
                                                    <td><s:property value="CORREO_CLIENTE" /></td>
                                                    <td align="center"><s:property value="TOTAL_DEUDA" /></td>
                                                    <td align="center"><s:property value="TOTAL_APORTACIONES_DEUDA" /></td>
                                                    <td align="center"><s:property value="SALDO_DEUDA" /></td>
                                                    <td align="center" ><s:property value="AVANCE_DEUDA" /></td>
                                                 <!--  <td id="centrar-dato"><s:if test="camp.NO_VENTA<1"><div onclick="hacer_click()"><a href="#popup"><img src="img/emitido.png"/></a></div></s:if></td>-->
                                                    <td align="center" ><a href="Javascript:consultaProducto('consultaNotasCredito','<s:property value="RFC_CLIENTE" />')">Ver notas</a></td>

                                                    </tr>

                                            </s:iterator>
                                            <tr style="text-decoration: none; border: 0px solid white;" >
                                                <td colspan="5" style="border: 0px solid white;"></td>
                                                <td align="center" style="background: purple; color:white;  border: 0px solid white;"><s:property value="camp.TOTAL_DEUDA_GENERAL" /></td>
                                                <td align="center" style="background: green; color:white;"><s:property value="camp.TOTAL_APORTACIONES_DEUDA_GENERAL" /></td>
                                                <td align="center" style="background: red; color:white;"><s:property value="camp.SALDO_DEUDA_GENERAL" /></td>
                                                <td align="center"  style="text-decoration: none; border: 0px solid white;"></td>
                                                 <td align="center"  style="text-decoration: none; border: 0px solid white;"></td>
                                            </tr>

                                        </table>


                                    </div>
                                </div>
                            </s:if>

                            <s:else>
                                <div class="div_sec1">
                                    <div class="div_titulos_sec"> <h2 class="text-tit-sec">Clientes con créditos activos</h2></div>

                                    <div style="width: 30%; float: right; margin-right: 20px; background: purple; text-align: center; display: inline-block; color: white; margin-bottom: 10px; border-radius: 10px;">Total de Clientes:  <s:property value="listaCreditos.size()"></s:property></div>

                                        <div id="scroltabla-cliente" style="overflow: scroll; height: 350px;">
                                            <table id="customers">
                                                <thead>
                                                    <th>NP</th>
                                                    <th>RFC</th>
                                                    <th>RAZON SOCIAL</th>
                                                    <th>TELÉFONO</th>
                                                    <th>CORREO ELECTRÓNICO</th>
                                                    <th>TOTAL DEUDA</th>
                                                    <th>APORTACIONES</th>
                                                    <th>SALDO ACTUAL</th>
                                                    <th>AVANCE EN EL PAGO</th>


                                                </thead>

                                           
                                            <tr style="text-decoration: none; border: 0px solid white;" >
                                                <td colspan="9" align="center" style="border: 0px solid white; color:red;">¡Sin Información!</td>
                                               
                                            </tr>

                                        </table>


                                    </div>
                                </div>
                            </s:else>
                            
                            
                            
                            <!--LISTA CREDITOS POR NOTA -->
                            <s:if test="bancrednota">
                               
                            <s:if test="listaCreditosNota.size()>0 ">
                                <div class="div_sec1">
                                    <div class="div_titulos_sec"> <h2 class="text-tit-sec">Notas del cliente</h2></div>

                                    <div style="width: 30%; float: right; margin-right: 20px; background: green; text-align: center; display: inline-block; color: white; margin-bottom: 10px; border-radius: 10px;">Total de notas:  <s:property value="listaCreditosNota.size()"></s:property></div>

                                        <div id="scroltabla-cliente" style="overflow: scroll; height: 800px;">
                                            <table id="customers">
                                                <thead>
                                                    <th>NP</th>
                                                    <th>NO_VENTA</th>                                         
                                                    <th>FECHA_VENTA</th>
                                                    <th>TOTAL DEUDA</th>
                                                    <th>APORTACIONES</th>
                                                    <th>SALDO ACTUAL</th>
                                                    <th>AVANCE EN EL PAGO</th>
                                                    <th>ESTATUS</th>
                                                    <th>VER</th>
                                                    <th>NOTA</th>

                                                </thead>

                                            <s:iterator value="listaCreditosNota" id="listaCreditosNota" status="stat" >
                                                <tr>
                                                    <td align="center"><s:property value="#stat.count" /></td>
                                                    <td align="center"><s:property value="NO_VENTA" /></td>
                                                    <td align="center"><s:property value="FECHA_VENTA" /></td>
                                                
                                                    <td align="center"><s:property value="TOTAL_DEUDA" /></td>
                                                    <td align="center"><s:property value="TOTAL_APORTACIONES_DEUDA" /></td>
                                                    <td align="center"><s:property value="SALDO_DEUDA" /></td>
                                                    <td align="center" ><s:property value="AVANCE_DEUDA" /></td>
                                                    <td align="center"><s:property value="ESTATUS" /></td> 

                                                        <s:url action="GeneraPdf" id="myUrl2" escapeAmp="false">

                                                                  <s:param name="TipoReporte" value="'VENTANBUENA.jasper'"/>
                                                                  <s:param name="NO_VENTA" value="NO_VENTA"/>

                                                                  <s:param name="esExcel" value="'false'"/>
                                                                  <s:param name="esPDF" value="'true'"/>            

                                                         </s:url> 
                                                    <td align="center" ><a href="Javascript:consultaProductoAbono('consultaNotasCreditoAbonos','<s:property value="NO_VENTA" />')">Ver Abonos</a></td>

                                                    <td id="centrar-dato">  <a href="<s:property value="#myUrl2" />" target="_blank"><img src="img/descarga.png" style="width: 50px; margin-top: 5px;"></img> </a></td>

                                                 <!--  <td id="centrar-dato"><s:if test="camp.NO_VENTA<1"><div onclick="hacer_click()"><a href="#popup"><img src="img/emitido.png"/></a></div></s:if></td>-->

                                                    </tr>

                                            </s:iterator>
                                           

                                        </table>
                                            
                                        <s:if test="listaCreditosNotaAbonos.size()>0">
                                            
                                            <div style="width: 100%; text-align: center; margin-top: 20px; margin-bottom: 20px;"> Abonos</div>
                                           
                                              <div style="width: 100%; display: inline-block; margin-bottom: 20px;">
                                            
                                                <s:textfield name="camp.APORTACION" id="APORTACION" placeholder="Aportación" required="true"  cssClass="campoFormBusqueda" cssStyle="width:30%;"/>
                                                   <a href="Javascript:guarda('guardaAbonos')"><div class="boton">  Abonar  </div> </a>
                                                
                                            </div>
                                            
                                            <table id="customers" style="width: 70%; " align="center">
                                                <thead>
                                                    <th>NP</th>
                                                    <th>ABONO</th>                                         
                                                    <th>FECHA DE ABONO</th>
                                                    <th>ACCIONES</th>
                                                </thead>

                                            <s:iterator value="listaCreditosNotaAbonos" id="listaCreditosNotaAbonos" status="stat" >
                                                <tr>
                                                    <td align="center"><s:property value="#stat.count" /></td>
                                                    <td align="center"><s:property value="APORTACION" /></td>
                                                    <td align="center"><s:property value="FECHA_APORTACION" /></td>
                                                
                                                 

                                                    <td id="centrar-dato">  <a href="<s:property value="#myUrl2" />" target="_blank"><img src="img/BORRAR.png" style="width: 30px; margin-top: 5px;"></img> </a></td>

                                                 <!--  <td id="centrar-dato"><s:if test="camp.NO_VENTA<1"><div onclick="hacer_click()"><a href="#popup"><img src="img/emitido.png"/></a></div></s:if></td>-->

                                                    </tr>

                                            </s:iterator>
                                           

                                        </table>
                                              
                                            
                                          
                                                
                                    </s:if>

                                    </div>
                                </div>
                            </s:if>

                            <s:else>
                                <div class="div_sec1">
                                    <div class="div_titulos_sec"> <h2 class="text-tit-sec">Notas del cliente</h2></div>

                                    <div style="width: 30%; float: right; margin-right: 20px; background: green; text-align: center; display: inline-block; color: white; margin-bottom: 10px; border-radius: 10px;">Total de Notas:  <s:property value="listaCreditosNota.size()"></s:property></div>

                                        <div id="scroltabla-cliente" style="overflow: scroll; height: 350px;">
                                            <table id="customers">
                                                <thead>
                                                    <th>NP</th>
                                                    <th>RFC</th>
                                                    <th>RAZON SOCIAL</th>
                                                    <th>TELÉFONO</th>
                                                    <th>CORREO ELECTRÓNICO</th>
                                                    <th>TOTAL DEUDA</th>
                                                    <th>APORTACIONES</th>
                                                    <th>SALDO ACTUAL</th>
                                                    <th>AVANCE EN EL PAGO</th>


                                                </thead>

                                           
                                            <tr style="text-decoration: none; border: 0px solid white;" >
                                                <td colspan="9" align="center" style="border: 0px solid white; color:red;">¡Sin Información!</td>
                                               
                                            </tr>

                                        </table>


                                    </div>
                                </div>
                            </s:else>
                            </s:if>

                                
              


                    </article>
                    <!-- /article -->
                    </div>
                                
                   <div class="modal-wrapper" id="popup">
                    <div class="popup-contenedor">


                        <div style="width: 100; text-align: center;"> <h2 style="font-size: 18px; margin-top: 40px; margin-bottom: 30px;" >¿Desea agregar el producto a la venta?</h2></div>
                        
                        

                        <div style="width: 100%;"> <a href="Javascript:guardaValida('guardaProductoVenta')"><div style="width: 120px; height: 30px; background: green; color: white; text-align: center; font-size: 16px; margin: auto; border-radius: 8px;">OK</div></a></div>
                            
                        

                      
                        <a class="popup-cerrar" href="#">X</a>
                    </div>
                </div>   
                                
                                
                         <!--Mensaje guardar cotización -->       
                                
                        <div class="modal-wrapper" id="popup2">
                    <div class="popup2-contenedor">


                        <div style="width: 100; text-align: center;"> <h2 style="font-size: 18px; margin-top: 40px; margin-bottom: 30px;" >¿Desea guardar la cotización?</h2></div>
                        
                        

                        <div style="width: 100%;"> <a href="Javascript:guarda('generaCotizacion')"><div style="width: 120px; height: 30px; background: green; color: white; text-align: center; font-size: 16px; margin: auto; border-radius: 8px;">OK</div></a></div>
                            
                        

                      
                        <a class="popup2-cerrar" href="#">X</a>
                    </div>
                </div>            
                                
                        <!--Mensaje guardar venta -->       
                                
                        <div class="modal-wrapper" id="popup3">
                    <div class="popup3-contenedor">


                        <div style="width: 100; text-align: center;"> <h2 style="font-size: 18px; margin-top: 40px; margin-bottom: 30px;" >¿Desea generar la venta?</h2></div>
                        
                        

                        <div style="width: 100%;"> <a href="Javascript:guarda('generarVenta')" onclick="this.onclick=function(){return false}"><div style="width: 120px; height: 30px; background: green; color: white; text-align: center; font-size: 16px; margin: auto; border-radius: 8px;">OK</div></a></div>
                            
                        

                      
                        <a class="popup3-cerrar" href="#">X</a>
                    </div>
                </div>                
                                
                                
                                
                                
                                
                </section> <!-- / #main-content -->



                <footer id="main-footer">
                    <p>&copy; 2017 <a href="refaccionesfabela.com">Refacciones Fabela</a></p>
                </footer> <!-- / #main-footer -->

                <s:iterator value="modulosAUX" id="modulosAUX" status="stat">
                    <s:hidden  name = "modulosAUX[%{#stat.index}].CVE_MODULO" id="CVE_MODULO"></s:hidden>
                    <s:hidden  name = "modulosAUX[%{#stat.index}].CVE_MODPADRE" id="CVE_MODPADRE"></s:hidden>
                    <s:hidden  name = "modulosAUX[%{#stat.index}].DESC_MOD" id="DESC_MOD"></s:hidden>
                    <s:hidden  name = "modulosAUX[%{#stat.index}].IMAGEN" id="IMAGEN"></s:hidden>
                </s:iterator>
                <s:iterator value="modulosAUXP" id="modulosAUXP" status="stat">                        
                    <s:hidden  name = "modulosAUXP[%{#stat.index}].MODULO" id="MODULO"></s:hidden>
                    <s:hidden  name = "modulosAUXP[%{#stat.index}].MODPADRE" id="MODPADRE"></s:hidden>
                    <s:hidden  name = "modulosAUXP[%{#stat.index}].MOD" id="MOD"></s:hidden>
                    <s:hidden  name = "modulosAUXP[%{#stat.index}].ACCION" id="ACCION"></s:hidden>
                </s:iterator>
                <s:iterator value="listaCreditos" id="listaCreditos" status="stat">
                    <s:hidden  name = "listaCreditos[%{#stat.index}].RFC_CLIENTE"  id="RFC_CLIENTE"></s:hidden> 
                    <s:hidden  name = "listaCreditos[%{#stat.index}].RAZONSOCIAL"  id="RAZONSOCIAL"></s:hidden>  
                    <s:hidden  name = "listaCreditos[%{#stat.index}].TELEFONO_CLIENTE"  id="TELEFONO_CLIENTE"></s:hidden>  
                    <s:hidden  name = "listaCreditos[%{#stat.index}].CORREO_CLIENTE"  id="CORREO_CLIENTE"></s:hidden>  
                    <s:hidden  name = "listaCreditos[%{#stat.index}].TOTAL_DEUDA"  id="TOTAL_DEUDA"></s:hidden>  
                    <s:hidden  name = "listaCreditos[%{#stat.index}].TOTAL_APORTACIONES_DEUDA"  id="TOTAL_APORTACIONES_DEUDA"></s:hidden>  
                    <s:hidden  name = "listaCreditos[%{#stat.index}].SALDO_DEUDA"  id="SALDO_DEUDA"></s:hidden>  
                    <s:hidden  name = "listaCreditos[%{#stat.index}].AVANCE_DEUDA"  id="AVANCE_DEUDA"></s:hidden>  
                </s:iterator> 
                 <s:iterator value="listaCreditosNota" id="listaCreditosNota" status="stat">
                    <s:hidden  name = "listaCreditosNota[%{#stat.index}].NO_VENTA"  id="NO_VENTA"></s:hidden> 
                    <s:hidden  name = "listaCreditosNota[%{#stat.index}].FECHA_VENTA"  id="FECHA_VENTA"></s:hidden>  
                    <s:hidden  name = "listaCreditosNota[%{#stat.index}].ESTATUS"  id="ESTATUS"></s:hidden>  
                    <s:hidden  name = "listaCreditosNota[%{#stat.index}].TOTAL_DEUDA"  id="TOTAL_DEUDA"></s:hidden>  
                    <s:hidden  name = "listaCreditosNota[%{#stat.index}].TOTAL_APORTACIONES_DEUDA"  id="TOTAL_APORTACIONES_DEUDA"></s:hidden>  
                    <s:hidden  name = "listaCreditosNota[%{#stat.index}].SALDO_DEUDA"  id="SALDO_DEUDA"></s:hidden>  
                    <s:hidden  name = "listaCreditosNota[%{#stat.index}].AVANCE_DEUDA"  id="AVANCE_DEUDA"></s:hidden>  
                </s:iterator> 
                
                   <s:hidden  name = "bancrednota"  id="bancrednota"></s:hidden>  
                   <s:hidden  name = "bancrednotaAbonos"  id="bancrednotaAbonos"></s:hidden>  

               


        </s:form>
                
                
                



    </body    

















</html>

