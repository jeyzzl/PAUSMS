<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<jsp:useBean id="Salon" scope="page" class="aca.musica.MusiSalon"/>


<script type="text/javascript">
	
	function Nuevo()	{		
		document.frmSalon.SalonId.value 	  = " ";
		document.frmSalon.SalonNombre.value   = " ";
		document.frmSalon.Cupo.value 	      = " ";
		document.frmSalon.Accion.value        = "1";
		document.frmSalon.submit();		
	}
	
	function Grabar(){
		if(document.frmSalon.SalonId.value!="" && document.frmSalon.SalonNombre!="" && document.frmSalon.Cupo!="" ){			
			document.frmSalon.Accion.value="2";
			document.frmSalon.submit();			
		}else{
			alert("Complete el formulario ..! ");
		}
	}
	
	function Borrar( ){
		if(document.frmSalon.SalonId.value!=""){
			if(confirm("Estas seguro de eliminar el registro!")==true){
	  			document.frmSalon.Accion.value="4";
				document.frmSalon.submit();
			}			
		}else{
			alert("Escriba la Clave !");
			document.frmSalon.SalonId.focus(); 
	  	}
	}
	
</script>
<%
	// Declaracion de variables	
	int nAccion			= Integer.parseInt(request.getParameter("Accion"));
	String sResultado   = "";
	
	if ( nAccion == 1 )
		Salon.setSalonId(Salon.maximoReg(conEnoc));
	else
		Salon.setSalonId(request.getParameter("SalonId"));
		
	// Operaciones a realizar en la pantalla	
	switch (nAccion){
		case 1: { // Nuevo			
			sResultado = "Llene el formulario correctamente ..¡¡";
			break;
		}		
		case 2: { // Grabar la cuenta
			Salon.setSalonId(request.getParameter("SalonId"));
			Salon.setSalonNombre(request.getParameter("SalonNombre"));
			Salon.setCupo(request.getParameter("Cupo"));
			if (Salon.existeReg(conEnoc) == false){
				if (Salon.insertReg(conEnoc)){
					sResultado = "Grabado: "+Salon.getSalonId();					
				}else{
					sResultado = "No Grabó: "+Salon.getSalonId();
				}
			}else{				
				if (Salon.updateReg(conEnoc)){
					sResultado = "Modificado: "+Salon.getSalonId();					
				}else{
					sResultado = "No Cambió: "+Salon.getSalonId();
				}
			}
			
			break;
		}		
		case 4: { // Borrar
			if (Salon.existeReg(conEnoc) == true){
					if (Salon.deleteReg(conEnoc)){
						sResultado = "Borrado: "+Salon.getSalonId();						
					}else{
						sResultado = "No Borró: "+Salon.getSalonId();
					}						
			}else{
				sResultado = "No existe: "+Salon.getSalonId();
			}
			break;
		}
		case 5: { // Consultar
			if (Salon.existeReg(conEnoc) == true){
				Salon.mapeaRegId(conEnoc, request.getParameter("SalonId"));
				sResultado = "Consulta";
			}else{
				sResultado = "No existe: "+Salon.getSalonId();
			}
			break;
		}
	}	
	
%>
<html>
<body>
<div class="container-fluid">
	<h2>Catalogo de Salones</h2>
	<form name="frmSalon" action="accion" method="post" target="_self">
	<input type="hidden" name="Accion"> 
	<div class="alert alert-info">
		  <a class="btn btn-primary" href="salon"><spring:message code="aca.Regresar"/></a> 
	</div>
	
		<div class="form-group">
          	<label for="aca.Clave">Clave</label>
          	<input name="SalonId" type="text" class="input input-mini" id="SalonId" size="3" maxlength="2" value="<%=Salon.getSalonId()%>">			
        	<br><br> 
          	<label for="aca.Nombre">Nombre</label>
          	<input name="SalonNombre" type="text" class="text" id="SalonNombre" size="40" maxlength="50" value="<%= Salon.getSalonNombre()%>">
 			<br><br>
	      	<label>Cupo</label>
          	<input name="Cupo" type="text" class="text" id="Cupo" size="3" maxlength="2" value="<%= Salon.getCupo()%>">
			<br><br>
		   	<%=sResultado%>
        </div>
        	<div class="alert alert-info">
		  		<a class="btn btn-primary" href="javascript:Nuevo()"><spring:message code='aca.Nuevo'/></a> &nbsp;
		  		<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a> &nbsp;		   
		  		<a class="btn btn-primary" href="javascript:Borrar()"><spring:message code='aca.Borrar'/></a> &nbsp;
		 	</div>
</form>
</div>
</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>