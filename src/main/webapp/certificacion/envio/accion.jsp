<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="Envio" scope="page" class="aca.envio.EnvioServicio"/>
<jsp:useBean id="EnvioU" scope="page" class="aca.envio.EnvioServicioUtil"/>

<script type="text/javascript">
	
	function Nuevo()	{		
		document.frmenvio.ServicioId.value 		= " ";
		document.frmenvio.ServicioNombre.value 	= " ";
		document.frmenvio.Telefonos.value 	= " ";
		document.frmenvio.Accion.value="1";
		document.frmenvio.submit();		
	}
	
	function Grabar(){
		if(document.frmenvio.ServicioId.value!="" && document.frmenvio.ServicioNombre!="" && document.frmenvio.Telefonos!="" ){
			document.frmenvio.Accion.value="2";
			document.frmenvio.submit();			
		}else{
			alert("Complete el formulario ..! ");
		}
	}
	
	function Borrar( ){
		if(document.frmenvio.ServicioId.value!=""){
			if(confirm("Estas seguro de eliminar el registro!")==true){
	  			document.frmenvio.Accion.value="4";
				document.frmenvio.submit();
			}			
		}else{
			alert("Escriba la Clave !");
			document.frmenvio.ServicioId.focus(); 
	  	}
	}
	
	function Consultar(){
		document.frmenvio.Accion.value="5";
		document.frmenvio.submit();		
	}
	
</script>
<%
	// Declaracion de variables	
	int nAccion			= Integer.parseInt(request.getParameter("Accion"));
	String sResultado		= "";
	
	if ( nAccion == 1 )
		Envio.setServicioId(EnvioU.maximoReg(conEnoc));
	else
		Envio.setServicioId(request.getParameter("ServicioId"));
		
	// Operaciones a realizar en la pantalla	
	switch (nAccion){
		case 1: { // Nuevo			
			sResultado = "Llene el formulario correctamente ..¡¡";
			break;
		}		
		case 2: { // Grabar
			Envio.setServicioNombre(request.getParameter("ServicioNombre"));
			Envio.setTelefonos(request.getParameter("Telefonos"));
			if (EnvioU.existeReg(conEnoc, Envio.getServicioId()) == false){
				if (EnvioU.insertReg(conEnoc, Envio)){
					sResultado = "Grabado: "+Envio.getServicioId();
				}else{
					sResultado = "No Grabó: "+Envio.getServicioId();
				}
			}else{				
				if (EnvioU.updateReg(conEnoc, Envio)){
					sResultado = "Modificado: "+Envio.getServicioId();
				}else{
					sResultado = "No Cambió: "+Envio.getServicioId();
				}
			}
			
			break;
		}		
		case 4: { // Borrar
			if (EnvioU.existeReg(conEnoc, Envio.getServicioId()) == true){
				if (EnvioU.deleteReg(conEnoc, Envio.getServicioId())){
					sResultado = "Borrado: "+Envio.getServicioId();
				}else{
					sResultado = "No Borró: "+Envio.getServicioId();
				}	
			}else{
				sResultado = "No existe: "+Envio.getServicioId();
			}
			break;
		}
		case 5: { // Consultar			
			if (EnvioU.existeReg(conEnoc, Envio.getServicioId()) == true){
				Envio.mapeaRegId(conEnoc, request.getParameter("ServicioId"));
				sResultado = "Consulta";
			}else{
				sResultado = "No existe: "+Envio.getServicioId(); 
			}	
			break;			
		}
	}	
	
%>
<html>
<head><title><spring:message code='aca.DocumentoSinTitulo'/></title></head>
<body>
<div class="container-fluid">
<h1>Catalogo de Servicios</h1>
<div class="alert alert-info">
	<a href="envio" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>
</div>
<form action="accion" method="post" name="frmenvio" target="_self">
<input type="hidden" name="Accion">

	<div class="form-group">
        <label for="aca.Clave">Clave</label>
        <input name="ServicioId" type="text" class="form-control" id="ServicioId" style="width:120px;" size="3" maxlength="3" required value="<%= Envio.getServicioId()%>">		
        <br><br>
        <label for="aca.Nombre">Nombre</label>
        <input name="ServicioNombre" type="text" class="form-control" id="ServicioNombre" style="width:120px;" size="40" maxlength="40" required value="<%= Envio.getServicioNombre() %>">
		<br><br>
        <label for="aca.Telefono">Telefono</label>
        <input name="Telefonos" type="text" class="form-control" style="width:120px;"   id="Telefonos" size="40" maxlength="40" required value="<%= Envio.getTelefonos() %>">
        <br><br>
          <%=sResultado%>  
   </div>
   
  		<div class="alert alert-info"> 
		 	 <a class="btn btn-primary" href="javascript:Nuevo()"><spring:message code='aca.Nuevo'/></a> &nbsp;
	  		 <a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a> &nbsp;		   
		  	 <a class="btn btn-primary" href="javascript:Borrar()"><spring:message code='aca.Borrar'/></a> &nbsp;
		     <a class="btn btn-primary" href="javascript:Consultar()"><spring:message code='aca.Consultar'/></a>
		</div>
		
</form>
</div>
</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>