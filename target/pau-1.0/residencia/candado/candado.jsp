<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<jsp:useBean id="Candado" scope="page" class="aca.residencia.ResCandado"/>
<jsp:useBean id="CandadoU" scope="page" class="aca.residencia.ResCandadoUtil"/>

<head>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script type="text/javascript">
	
	function Nuevo()	{		
		document.frmCandado.Codigo.value 			= " ";
		document.frmCandado.Fecha.value 			= " ";
		document.frmCandado.Comentario.value 		= " ";
		document.frmCandado.Accion.value="1";
		document.frmCandado.submit();		
	}
	
	function Grabar(){
		if(document.frmCandado.Codigo.value!="" && document.frmCandado.Fecha.value!="" && document.frmCandado.Comentario.value){			
			document.frmCandado.Accion.value="2";
			document.frmCandado.submit();			
		}else{
			alert("Fill out the entire form");
		}
	}
	
	
	function Borrar( ){
		if(document.frmCandado.Codigo.value!=""){
			if(confirm("Are you sure you want to delete the record?")==true){
	  			document.frmCandado.Accion.value="3";
				document.frmCandado.submit();
			}			
		}else{
			alert("Input the Key");
			document.frmCandado.Codigo.focus(); 
	  	}
	}

</script>
</head>
<% 

	String usuario			= (String)session.getAttribute("codigoEmpleado");
	String codigoPersonal	= (String)session.getAttribute("codigoAlumno")==null?"":(String)session.getAttribute("codigoAlumno");
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	int nAccion				= Integer.parseInt(accion);
	String sResultado		= "";	

	// Operaciones a realizar en la pantalla	
	switch (nAccion){
		case 1: { // Nuevo			
			sResultado = "Fill out the entire form";
			break;
		}		
		case 2: { // Grabar
			Candado.setCodigoPersonal(request.getParameter("Codigo"));
			Candado.setUsuario(usuario);
			Candado.setFecha(request.getParameter("Fecha"));
			Candado.setComentario(request.getParameter("Comentario"));
			Candado.setEstado(request.getParameter("Estado"));
			
			if (Candado.existeReg(conEnoc) == false){
				if (Candado.insertReg(conEnoc)){
					sResultado = "Saved: "+Candado.getCodigoPersonal();
				}else{
					sResultado = "Error saving: "+Candado.getCodigoPersonal();
				}
			}else{
				if (Candado.updateReg(conEnoc)){
					sResultado = "Updated: "+Candado.getCodigoPersonal();
				}else{
					sResultado = "Error updating: "+Candado.getCodigoPersonal();
				}
			}
			
			break;
		}
		case 3: { // Borrar
			Candado.setCodigoPersonal(codigoPersonal);
			if (Candado.existeReg(conEnoc) == true){
				if (Candado.deleteReg(conEnoc)){
					sResultado = "Deleted: "+Candado.getCodigoPersonal();
				}else{
					sResultado = "Error deleting: "+Candado.getCodigoPersonal();
				}	
			}else{
				sResultado = "Not found "+Candado.getCodigoPersonal();
			}
			break;
		}
		case 4: { // Consultar
			String codigo = request.getParameter("Codigo");
			Candado.mapeaRegId(conEnoc, codigo);
			break;			
		}
	}
	
	ArrayList<aca.residencia.ResCandado> lisCandados = CandadoU.getListAll(conEnoc, "ORDER BY CODIGO_PERSONAL");
%>
<body>
<div class="container-fluid">
	<h2>Locks</h2>
	<hr>
	<form action="candado" method="post" name="frmCandado" target="_self">
		<input type="hidden" name="Accion">
		<div class="row">
			<div class="col-3">
				<label for="Codigo">ID Number <a class="btn btn-primary btn-sm" href="buscar"><i class="fas fa-search"></i></a></label>
<% 				if(nAccion!=4){%>    
    			<input name="Codigo" type="text" class="form-control" id="Codigo" size="10" maxlength="7" value="<%= codigoPersonal%>" readonly>
<% 				}else{%>
  				<input name="Codigo" type="text" class="form-control" id="Codigo" size="10" maxlength="7" value="<%= Candado.getCodigoPersonal()%>" readonly>
<% 				}%> 
				<br>
				<label for="Fecha"><spring:message code="aca.Fecha"/> (DD/MM/AAAA)</label>			
				<input name="Fecha" type="text" class="form-control" id="Fecha" size="12" data-date-format="dd/mm/yyyy" maxlength="10" onfocus="focusFecha(this);" value="<%= Candado.getFecha() %>">      			
				<br>
				<label for="Comentario"><spring:message code="aca.Comentario"/></label>			
				<textarea id="Comentario" class="form-control" name="Comentario" ><%= Candado.getComentario()%></textarea>
				<br>
				<label for="Estado"><spring:message code="aca.Status"/></label>			
				<select id="Estado" name="Estado" class="form-select" style="width:200px;">
			        <option value="0" selected>Select Status</option>
			        <option value="A" <%= Candado.getEstado().equals("A")?"Selected":"" %>><spring:message code='aca.Activo'/></option>
			        <option value="I" <%= Candado.getEstado().equals("I")?"Selected":"" %>><spring:message code='aca.Inactivo'/></option>
			    </select>
			</div>
		</div>
		<br>
		<div class="alert alert-info">
			<a class="btn btn-primary" href="javascript:Nuevo()"><spring:message code='aca.Nuevo'/></a> &nbsp;
			<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a> &nbsp; 
			<a class="btn btn-primary" href="javascript:Borrar()"><spring:message code='aca.Borrar'/></a> &nbsp;
		</div>
	</form>
<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
  <tr>
    <th><spring:message code="aca.Numero"/></th>
    <th>Op.</th>
    <th><spring:message code="aca.Nombre"/></th>
    <th><spring:message code='aca.Usuario'/></th>
    <th><spring:message code="aca.Fecha"/></th>
    <th><spring:message code="aca.Status"/></th>
  </tr>
 </thead>
<% for(int i=0; i<lisCandados.size();i++){
	aca.residencia.ResCandado candado = (aca.residencia.ResCandado) lisCandados.get(i);
%>
  <tr>
    <td><%=i+1 %></td>
    <td><a href="candado?Accion=4&Codigo=<%= candado.getCodigoPersonal()%>"> 
      <img title="Edit" src="../../imagenes/editar.gif" alt="Update" > 
      </a> <a href="javascript:Borrar('<%=candado.getCodigoPersonal()%>')"> <img src="../../imagenes/no.png" alt="Delete" > 
      </a> </td>
    <td><%= aca.alumno.AlumUtil.getNombreAlumno(conEnoc,candado.getCodigoPersonal(),"NOMBRE")%></td>
    <td align="center"><%= candado.getUsuario()%></td>
    <td align="center"><%= candado.getFecha() %></td>
    <td align="center"><%= candado.getEstado() %></td>
  </tr>
<% }%>  
</table>
</div>
</body>
<script>
	jQuery('#Fecha').datepicker();
</script>
<%@ include file= "../../cierra_enoc.jsp" %> 