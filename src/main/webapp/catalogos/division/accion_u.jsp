<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="Union" scope="page" class="aca.catalogo.CatUnion"/>

<script type="text/javascript">
	
	function Nuevo()	{	
		document.frmunion.UnionId.value 		= " ";
		document.frmunion.NombreUnion.value 	= " ";
		document.frmunion.Direccion.value 		= " ";
		document.frmunion.Colonia.value 		= " ";
		document.frmunion.CodPostal.value 		= " ";
		document.frmunion.Telefono.value 		= " ";
		document.frmunion.Fax.value 			= " ";
		document.frmunion.Email.value 			= " ";
		document.frmunion.PaisId.value 			= " ";
		document.frmunion.EstadoId.value 		= " ";
		document.frmunion.CiudadId.value 		= " ";
		document.frmunion.Accion.value			="1";
		document.frmunion.submit();		
	}
	
	function Grabar(){
		if(document.frmunion.UnionId.value!="" && document.frmunion.NombreUnion!="" ){			
			document.frmunion.Accion.value="2";
			document.frmunion.submit();			
		}else{
			alert("Complete el formulario ..! ");
		}
	}
	
	function Modificar(){
		document.frmunion.Accion.value="3";
		document.frmunion.submit();
	}
	
	function Borrar( ){
		if(document.frmunion.UnionId.value!=""){
			if(confirm("Estas seguro de eliminar el registro!")==true){
	  			document.frmunion.Accion.value="4";
				document.frmunion.submit();
			}			
		}else{
			alert("Escriba la Clave !");
			document.frmunion.UnionId.focus(); 
	  	}
	}
	
	function Consultar(){
		document.frmunion.Accion.value="5";
		document.frmunion.submit();		
	}
	function PEC( Pec){		
		document.frmunion.Accion.value="6";
		document.frmunion.Pec.value 	= Pec;
		document.frmunion.submit();
	}	
</script>
<%
	// Declaracion de variables	
	String sResultado		= "";
	int nAccion			= Integer.parseInt(request.getParameter("Accion"));
	int i 					= 0;
	Union.setDivisionId(request.getParameter("DivisionId"));
	ArrayList listor			= new ArrayList();
	ArrayList lisPais			= new ArrayList();
	
	if ( nAccion == 1 )
		Union.setUnionId(Union.maximoReg(conEnoc,Union.getDivisionId()));
	else
		Union.setUnionId(request.getParameter("UnionId"));

	if (nAccion == 2 || nAccion == 3 || nAccion == 6){
		Union.setNombreUnion(request.getParameter("NombreUnion"));
		Union.setDireccion(request.getParameter("Direccion"));
		Union.setColonia(request.getParameter("Colonia"));
		Union.setCodPostal(request.getParameter("CodPostal"));
		Union.setTelefono(request.getParameter("Telefono"));
		Union.setFax(request.getParameter("Fax"));
		Union.setEmail(request.getParameter("Email"));
		Union.setPaisId(request.getParameter("PaisId"));
	}
			
	// Operaciones a realizar en la pantalla	
	switch (nAccion){
		case 1: { // Nuevo			
			sResultado = "Llene el formulario correctamente ..¡¡";
			break;
		}		
		case 2: { // Grabar
			
			Union.setEstadoId(request.getParameter("EstadoId"));
			Union.setCiudadId(request.getParameter("CiudadId"));
			if (Union.existeReg(conEnoc) == false){
				if (Union.insertReg(conEnoc)){
					sResultado = "Grabado: " +Union.getUnionId();
				}else{
					sResultado = "No Grabó: "+Union.getUnionId();
				}
			}else{
				sResultado = "Ya existe: "+Union.getUnionId();
			}
			
			break;
		}
		case 3: { // Modificar
			Union.setEstadoId(request.getParameter("EstadoId"));
			Union.setCiudadId(request.getParameter("CiudadId"));
			if (Union.existeReg(conEnoc) == true){
				if (Union.updateReg(conEnoc)){
					sResultado = "Modificado: "+Union.getUnionId();
				}else{
					sResultado = "No Cambió: "+Union.getUnionId();
				}
			}else{
				sResultado = "No existe: "+Union.getUnionId();
			}
			break;
		}
		case 4: { // Borrar
			if (Union.existeReg(conEnoc) == true){
				if (Union.deleteReg(conEnoc)){
					sResultado = "Borrado: "+Union.getUnionId();
				}else{
					sResultado = "No Borró: "+Union.getUnionId();
				}	

			}else{
				sResultado = "No existe: "+Union.getUnionId();
			}
		
			break;
		}
		case 5: { // Consultar			
			if (Union.existeReg(conEnoc) == true){
				Union.mapeaRegId(conEnoc, Union.getDivisionId(), request.getParameter("UnionId"));
				sResultado = "Consulta";
			}else{
				sResultado = "No existe: "+Union.getUnionId(); 
			}	
			break;			
		}
		case 6: { // Refrescar combos PEC
			if (request.getParameter("Pec").equals("2")){
				Union.setEstadoId(request.getParameter("EstadoId"));
			}else{
				Union.setEstadoId("0");
				Union.setCiudadId("0");
			}
			break;						
		}
	}
%>
<div class="container-fluid">
<h1>Catalogo de Uniones</h1>
<form action="accion_u" method="post" name="frmunion" target="_self">
<input type="hidden" name="Accion">
<input type="hidden" name="Pec">
<div class="alert alert-info">
	<a class="btn btn-primary"href="union?DivisionId=<%=Union.getDivisionId()%>"><i class="fas fa-arrow-left"></i></a> 
</div>

		<div class="form-group">
           	<label for="Clave">Clave:</label>
           	<input name="UnionId" type="text" class="input input-mini form-control" id="UnionId" size="3" maxlength="3" required value="<%=Union.getUnionId()%>">
          	<br>
            <label for="aca.Nombre">Nombre:</label>
            <input name="NombreUnion" type="text" class="input input-xlarge form-control" id="NombreUnion" required value="<%=Union.getNombreUnion()%>" size="40" maxlength="40">
          	<br>
		   	<label for="catalogos.division.Direccion">Direccion:</label>
            <input name="Direccion" type="text" class="input input-xlarge form-control" id="Direccion" required value="<%=Union.getDireccion()%>" size="40" maxlength="40">        
		   	<br>
            <label for="catalogos.division.Colonia">Colonia:</label>
            <input name="Colonia" type="text" class="input input-xlarge form-control" id="Colonia" required value="<%=Union.getColonia()%>" size="40" maxlength="40">
		  	<br>
            <label for="catalogos.division.CodigoPostal">Codigo Postal:</label>
            <input name="CodPostal" type="text" class="input input-xlarge form-control" id="CodPostal" required value="<%=Union.getCodPostal()%>" size="40" maxlength="40">
			<br>
            <label for="aca,Telefono">Telefono:</label>
            <input name="Telefono" type="text" class="form-control" id="Telefono" required value="<%=Union.getTelefono()%>" size="40" maxlength="40">
            <br>
            <label for="catalogos.division.Fax">Fax:</label>
            <input name="Fax" type="text" class="input input-xlarge form-control" id="Fax" value="<%=Union.getFax()%>" size="40" maxlength="40">
          	<br>
            <label for="aca.Email">Email:</label>
            <input name="Email" type="text" class="input input-xlarge form-control" id="Email" required value="<%=Union.getEmail()%>" size="40" maxlength="40">
          	<br>
            <label for="aca.Pais">Pais:</label> 
            <select name="PaisId" id="PaisId" onchange = "PEC('1')" class="form-select">
                 <%			  	
				aca.catalogo.PaisUtil paisU = new aca.catalogo.PaisUtil();
				lisPais = paisU.getListAll(conEnoc,"ORDER BY 2");
				for( i=0;i<lisPais.size();i++){
					aca.catalogo.CatPais pais = (aca.catalogo.CatPais) lisPais.get(i);
					if (pais.getPaisId().equals(Union.getPaisId())){
						out.print(" <option value='"+pais.getPaisId()+"' Selected>"+ pais.getNombrePais()+"</option>");
					}else {
						out.print(" <option value='"+pais.getPaisId()+"'>"+ pais.getNombrePais()+"</option>");
					}
				}				
				paisU	= null;
			  %>
            </select>
     		<br>
            <label for="aca.Estado">Estado:</label>
            <select name="EstadoId" id="EstadoId"  onChange= "javascript:PEC('2')" class="form-select">
			  <%			  	
				aca.catalogo.EstadoUtil estadoU = new aca.catalogo.EstadoUtil();
				listor = estadoU.getLista(conEnoc,Union.getPaisId(),"ORDER BY 1,3");				
				for( i=0;i<listor.size();i++){
					aca.catalogo.CatEstado estado = (aca.catalogo.CatEstado) listor.get(i);
					if (estado.getEstadoId().equals(Union.getEstadoId())){
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
            <label for="aca.Cuidad">Cuidad:</label>
            <select name="CiudadId" id="CiudadId" class="form-select">
			  <%			  	
				aca.catalogo.CiudadUtil ciudadU = new aca.catalogo.CiudadUtil();
				listor = ciudadU.getLista(conEnoc,Union.getPaisId(),Union.getEstadoId(),"ORDER BY 4");
				for( i=0;i<listor.size();i++){
					aca.catalogo.CatCiudad ciudad = (aca.catalogo.CatCiudad) listor.get(i);					
					if (ciudad.getCiudadId().equals(Union.getCiudadId())){
						out.print(" <option value='"+ciudad.getCiudadId()+"' Selected>"+ ciudad.getNombreCiudad()+"</option>");						
					}else {
						out.print(" <option value='"+ciudad.getCiudadId()+"'>"+ ciudad.getNombreCiudad()+"</option>");
					}				
				}
				listor 		= null;				
				ciudadU	= null;
			  %>
		  </select> 
          	<br>
   	 </div>    
          <div class="alert alert-info">
            <a href="javascript:Nuevo()" class="btn btn-primary"><spring:message code="aca.Anadir"/></a>&nbsp;
            <a href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Guardar"/></a>&nbsp; 
            <a href="javascript:Modificar()" class="btn btn-primary"><spring:message code='aca.Modificar'/></a>&nbsp; 
            <a href="javascript:Borrar()" class="btn btn-primary"><spring:message code="aca.Eliminar"/></a>&nbsp;
            <a href="javascript:Consultar()" class="btn btn-primary"><spring:message code='aca.Consultar'/></a> 
         </div> 

	
<%
	listor  = null;
	listor	= null;
%>
</form>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>