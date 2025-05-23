<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="Division" scope="page" class="aca.catalogo.CatDivision"/>
<jsp:useBean id="DivisionU" scope="page" class="aca.catalogo.DivisionUtil"/>

<script type="text/javascript">
	
	function Nuevo()	{		
		document.frmdivision.DivisionId.value 		= " ";
		document.frmdivision.NombreDivision.value 	= " ";
		document.frmdivision.Direccion.value 		= " ";
		document.frmdivision.Colonia.value 			= " ";
		document.frmdivision.CodPostal.value 		= " ";
		document.frmdivision.Telefono.value 		= " ";
		document.frmdivision.Fax.value 				= " ";
		document.frmdivision.Email.value 			= " ";
		document.frmdivision.PaisId.value 			= "135";
		document.frmdivision.EstadoId.value 		= "0";
		document.frmdivision.CiudadId.value 		= "0";
		document.frmdivision.Accion.value="1";
		document.frmdivision.submit();		
	}
	
	function Grabar(){
		if(document.frmdivision.DivisionId.value!="" && document.frmdivision.NombreDivision.value!="" ){			
			document.frmdivision.Accion.value="2";
			document.frmdivision.submit();			
		}else{
			alert("<spring:message code='aca.JSCompletar'/>");
		}
	}
	
	function Modificar(){
		document.frmdivision.Accion.value="3";
		document.frmdivision.submit();
	}
	
	function Borrar( ){
		if(document.frmdivision.DivisionId.value!=""){
			if(confirm("<spring:message code="aca.JSEliminar"/>")==true){
	  			document.frmdivision.Accion.value="4";
				document.frmdivision.submit();
			}			
		}else{
			alert("<spring:message code="aca.JSEscribirClave"/>");
			document.frmdivision.DivisionId.focus(); 
	  	}
	}
	
	function Consultar(){
		document.frmdivision.Accion.value="5";
		document.frmdivision.submit();		
	}

	function PEC( Pec){		
		document.frmdivision.Accion.value="6";
		document.frmdivision.Pec.value 	= Pec;
		document.frmdivision.submit();
	}
	
</script>
<%
	// Declaracion de variables	
	String sResultado		= "";
	int i 					= 0;
	int accionFmt				= 0;
	int nAccion			= Integer.parseInt(request.getParameter("Accion"));	
	ArrayList listor			= new ArrayList();
	ArrayList lisPais			= new ArrayList();

	if ( nAccion == 1 )
		Division.setDivisionId(DivisionU.maximoReg(conEnoc));
	else
		Division.setDivisionId(request.getParameter("DivisionId")==null?"0":request.getParameter("DivisionId"));

	if (nAccion == 2 || nAccion == 3 || nAccion == 6){
		Division.setNombreDivision(request.getParameter("NombreDivision"));
		Division.setNombreCorto(request.getParameter("NombreCorto"));
		Division.setDireccion(request.getParameter("Direccion"));
		Division.setColonia(request.getParameter("Colonia"));
		Division.setCodPostal(request.getParameter("CodPostal"));
		Division.setTelefono(request.getParameter("Telefono"));
		Division.setFax(request.getParameter("Fax"));
		Division.setEmail(request.getParameter("Email"));
		Division.setPaisId(request.getParameter("PaisId"));	
	}

	// Operaciones a realizar en la pantalla	
	switch (nAccion){
		case 1: { // Nuevo			
			//sResultado = "Llene el formulario correctamente ..¡¡";
			break;
		}		
		case 2: { // Grabar
			
			Division.setEstadoId(request.getParameter("EstadoId"));
			Division.setCiudadId(request.getParameter("CiudadId"));
			if (DivisionU.existeReg(conEnoc, Division.getDivisionId()) == false){
				if (DivisionU.insertReg(conEnoc, Division)){
					accionFmt = 1;
				}else{
					accionFmt = 2;
				}
			}else{
				if (DivisionU.updateReg(conEnoc, Division)){
					accionFmt = 3;
				}else{
					accionFmt = 4;
				}
			}
			
			break;
		}
		
		case 4: { // Borrar
			if (DivisionU.existeReg(conEnoc, Division.getDivisionId()) == true){
				if (DivisionU.deleteReg(conEnoc, Division.getDivisionId())){
					accionFmt = 5;
				}else{
					accionFmt = 6;
				}	

			}else{
				accionFmt = 7;
			}
			
			break;
		}
		case 5: { // Consultar			
			Division.setDivisionId(request.getParameter("DivisionId"));	
			if (DivisionU.existeReg(conEnoc, Division.getDivisionId()) == true){				
				Division = DivisionU.mapeaRegId(conEnoc, request.getParameter("DivisionId"));
			}else{				
				accionFmt = 7; 
			}	
			break;			
		}
		case 6: { // Refrescar combos PEC
			if (request.getParameter("Pec").equals("2")){
				Division.setEstadoId(request.getParameter("EstadoId"));
			}else{
				Division.setEstadoId("0");
				Division.setCiudadId("0");
			}			
		}
	}	

%>
<div class="container-fluid">
	<h1><spring:message code="catalogos.division.Titulo2"/></h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="division"><i class="fas fa-arrow-left"></i></a>
	</div>
	
<%	if(accionFmt==1){%>
   			<div class="alert alert-success">
   				<spring:message code="aca.Guardado"/><%=sResultado%>
   			</div>	
	<%	}
   		else if(accionFmt==2){%>
   			<div class="alert alert-danger">
				Not Saved <%=sResultado%>
			</div>
	<%	}
   		else if(accionFmt==3){%>
  				<div class="alert alert-success">
				<spring:message code="aca.Modificado"/><%=sResultado%>
			</div>	
	<%	}
   		else if(accionFmt==4){%>
   			<div class="alert alert-danger">
				<spring:message code="aca.Nomodificado"/><%=sResultado%>
			</div>	
	<%	}
   		else if(accionFmt==5){%>
   			<div class="alert alert-success">
				<spring:message code="aca.Eliminado"/><%=sResultado%>
			</div>	
	<%	}
   		else if(accionFmt==6){%>
   			<div class="alert alert-danger">
				<spring:message code="aca.Noeliminado"/><%=sResultado%>
			</div>	
	<%	}
   		else if(accionFmt==7){%>
   			<div class="alert alert-info">
				<spring:message code="aca.Noexiste"/><%=sResultado%>
			</div>	
	<%	}%>
	
	<form action="accion_d" method="post" name="frmdivision" target="_self">
	<input type="hidden" name="Accion">
	<input name="Pec" type="hidden">
	<div class="row container-fluid">
	
		<div class="span4 col">
    		<label for="Clave"><spring:message code="aca.Clave"/>:</label>
           	<input name="DivisionId" type="text" class="input input-mini form-control" id="DivisionId" size="3" maxlength="3" required value="<%=Division.getDivisionId()%>" readonly>
      		<br>
      		<label for="Nombre"><spring:message code="aca.Nombre"/>:</label>
            <input name="NombreDivision" type="text" class="input input-xlarge form-control" id="NombreDivision" required value="<%=Division.getNombreDivision()%>" maxlength="40">
      		<br>
      		<label for="Corto"><spring:message code="aca.NombreCorto"/>:</label>
            <input name="NombreCorto" type="text" class="input input-xlarge form-control" id="NombreCorto" required value="<%=Division.getNombreCorto()%>" maxlength="20">
      		<br>
            <label for="catalogos.division.Direccion"><spring:message code="aca.Direccion"/>:</label>
            <input name="Direccion"  type="text" class="input input-xlarge form-control" id="Direccion" required value="<%=Division.getDireccion()%>" maxlength="80">
			<br>
			</div>
					<div class="span4 col">
			
            <label for="catalogos.division.Colonia"><spring:message code="catalogos.division.Colonia"/>:</label>
            <input name="Colonia"  type="text" class="input input-xlarge form-control" id="Colonia" required value="<%=Division.getColonia()%>" maxlength="80">
            <br>  
		   	<label for="catalogos.division.CodigoPostal"><spring:message code="catalogos.division.CodigoPostal"/>:</label>
           	<input name="CodPostal" type="text" class="input input-xlarge form-control" id="CodPostal" required value="<%=Division.getCodPostal()%>" maxlength="80">
           	  <br>                      
			<label for="Telefono"><spring:message code="catalogos.division.Telefono"/>:</label>
            <input name="Telefono"  type="text" class="input input-xlarge form-control" id="Telefono" required value="<%=Division.getTelefono()%>" size="40" maxlength="80">
            <br>			
            <label for="catalogos.division.Fax"><spring:message code="catalogos.division.Fax"/>:</label>
            <input name="Fax"  type="text" class="input input-xlarge form-control" id="Fax" required value="<%=Division.getFax()%>" size="40" maxlength="80">
            <br>  
            </div>
            					<div class="span4 col">
            
         	<label for="aca.Email"><spring:message code="aca.Correo"/>:</label>	
            <input name="Email"  type="text" class="input input-xlarge form-control" id="Email" required value="<%=Division.getEmail()%>" size="40" maxlength="80">
            <br>
            <label for="aca.Pais"><spring:message code="aca.Pais"/>:</label> 
            <select name="PaisId" id="PaisId" onchange = "PEC('1')" class="form-select">          
               <%			  	
				aca.catalogo.PaisUtil paisU = new aca.catalogo.PaisUtil();
				lisPais = paisU.getListAll(conEnoc," ORDER BY NOMBRE_PAIS");
				for( i=0;i<lisPais.size();i++){
					aca.catalogo.CatPais pais = (aca.catalogo.CatPais) lisPais.get(i);
					if (pais.getPaisId().equals(Division.getPaisId())){
						out.print(" <option value='"+pais.getPaisId()+"' Selected>"+ pais.getNombrePais()+"</option>");
					}else {
						out.print(" <option value='"+pais.getPaisId()+"'>"+ pais.getNombrePais()+"</option>");
					}
				}				
				paisU	= null;
			  %>
            </select>  
            <br>        
            <label for="aca.Estado"><spring:message code="catalogos.division.estado"/>:</label>
            <select name="EstadoId" id="EstadoId"  onChange= "javascript:PEC('2')" class="form-select">
			  <%			  
				aca.catalogo.EstadoUtil estadoU = new aca.catalogo.EstadoUtil();
				listor = estadoU.getLista(conEnoc,Division.getPaisId()," ORDER BY PAIS_ID,NOMBRE_ESTADO");
				for( i=0;i<listor.size();i++){
					aca.catalogo.CatEstado estado = (aca.catalogo.CatEstado) listor.get(i);
					if (estado.getEstadoId().equals(Division.getEstadoId())){
						out.print(" <option value='"+estado.getEstadoId()+"' Selected>"+ estado.getNombreEstado()+"</option>");
					}else {
						out.print(" <option value='"+estado.getEstadoId()+"'>"+ estado.getNombreEstado()+"</option>");
					}				
				}
				listor 		= null;
				estadoU	= null;
			  %>
			</select>
          	<br>
            <label for="aca.Cuidad"><spring:message code="aca.Ciudad"/>:</label>
            <select name="CiudadId" id="CiudadId" class="form-select">
			  <%			  
				aca.catalogo.CiudadUtil ciudadU = new aca.catalogo.CiudadUtil();
				listor = ciudadU.getLista(conEnoc,Division.getPaisId(),Division.getEstadoId()," ORDER BY NOMBRE_CIUDAD");
				for( i=0;i<listor.size();i++){
					aca.catalogo.CatCiudad ciudad = (aca.catalogo.CatCiudad) listor.get(i);					
					if (ciudad.getCiudadId().equals(Division.getCiudadId())){
						out.print(" <option value='"+ciudad.getCiudadId()+"' Selected>"+ ciudad.getNombreCiudad()+"</option>");						
					}else {
						out.print(" <option value='"+ciudad.getCiudadId()+"'>"+ ciudad.getNombreCiudad()+"</option>");
					}				
				}
				listor 		= null;				
				ciudadU	= null;
			  %>
			</select>
		  	</div>
		 		  	
	</div>		
	<br>
	<div class="alert alert-info">
		<a href="javascript:Nuevo()" class="btn btn-primary"><spring:message code="aca.Anadir"/></a>&nbsp;
       	<a href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Guardar"/></a>&nbsp;  
       	<a href="javascript:Borrar()" class="btn btn-primary"><spring:message code="aca.Eliminar"/></a>
    </div>        		
<%
	listor  = null;
	listor	= null;
%>
</form>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>