<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="porSeccionU" scope="page" class="aca.por.PorSeccionUtil"/>
<jsp:useBean id="porSeccion" scope="page" class="aca.por.PorSeccion"/>

<script type="text/javascript">
	function Borrar(){
		document.frmPortafolio.Accion.value="2";
		document.frmPortafolio.submit();
	}	
</script>
<%	
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String porId 			= request.getParameter("porId")==null?"0":request.getParameter("porId");
	int accionFmt			= 0;
	

	// Lista de portafolios 
	ArrayList<aca.por.PorSeccion> lisSeccion	=  porSeccionU.listPortafolio(conEnoc, porId, "ORDER BY ORDEN, SECCION_ID");	
%>
<div class="container-fluid">
	<h1><spring:message code="aca.FormatoPortafolio"/></h1>

<%	if(accionFmt==1){%>
		<div class="alert alert-info"><h3><spring:message code='aca.SeGuardoCorrectamente'/></h3></div>
<%	
	}
	if(accionFmt==2){%>
		<div class="alert alert-danger"><h3>No se pudo guardar</h3></div>
<%	}
	if(accion.equals("3")){%>
		<div class="alert alert-info"><h3>Se elimin&oacute; correctamente</h3></div>
<%	} %>
	<div class="alert alert-info">
		<a href="portafolio" class="btn btn-primary"><i class="icon-white icon-chevron-left"></i> <spring:message code="aca.Regresar"/></a>
		&nbsp;
		<a href="grabarSeccion?PortafolioId=<%=porId%>&SeccionId=0" class="btn btn-primary"><i class="icon icon-plus icon-white"></i> Sección</a>
	</div>
	<table class="table table-sm table-bordered">
  	<tr class="table-info">
		<th align="center" width="5%"><spring:message code="aca.Numero"/></th>
		<th align="left" width="10%"><spring:message code="aca.Opcion"/></th>
		<th align="left" width="10%"><spring:message code="aca.Clave"/></th>
    	<th align="left" width="40%"><spring:message code="aca.Seccion"/></th>
    	<th align="left" width="15%"><spring:message code="aca.Tipo"/></th>   	
    	<th align="left" width="10%"><spring:message code="aca.Orden"/></th>
    	<th align="left" width="10%"><spring:message code="aca.Estado"/></th>
  	</tr>
<%	
	int row = 0;
	for(aca.por.PorSeccion portafolio : lisSeccion){
		row++;
		
		String tipo = "Informativo";
		if (portafolio.getTipo().equals("C")){
			tipo = "Texto corto";
		}else if ( portafolio.getTipo().equals("L")){
			tipo = "Texto Largo";
		}else if (portafolio.getTipo().equals("I")){
			tipo = "Imagen";
		}else if (portafolio.getTipo().equals("A")){
			tipo = "Archivo";
		}else if (portafolio.getTipo().equals("E")){
			tipo = "Enlace externo";
		}else{
			tipo = "Informativo";
		}
			
		boolean usaEmp			= porSeccion.seUtilizaEmp(conEnoc, porId, portafolio.getSeccionId());
		boolean tieneHijo		= porSeccion.tieneHijo(conEnoc, portafolio.getSeccionId());
		
		if(portafolio.getSeccionId().length()==2){					
%>
		<tr>
			<td><%=row%></td>
			<td>
				<a href="editarSeccion?Accion=2&PortafolioId=<%=porId %>&SeccionId=<%=portafolio.getSeccionId() %>" class="btn btn-sm btn-success"><i class="fas fa-check-square"></i></i></a>
				<%	//if (portafolio.getTipo().equals("-")){ %>
					<a href="grabarSeccion?PortafolioId=<%=portafolio.getPorId()%>&SeccionId=<%=portafolio.getSeccionId()%>" class="btn btn-sm btn-primary"><i class="fas fa-plus-square"></i></a>					
				<%	//} %>
				<%	if((usaEmp==false)&&(tieneHijo ==false)){ %>
					<a href="javascript: Borrar('<%=porId%>','<%=portafolio.getSeccionId()%>')" class="btn btn-sm btn-danger"><i class="fas fa-trash-alt icon-white"></i></a>
					&nbsp;&nbsp;
				<%	} %>					
			</td>
			<td>
				<span class="badge bg-dark"><%=portafolio.getSeccionId()%></span>				
			</td>
			<td>
				<span class="badge bg-dark"><%=portafolio.getTitulo()%></span> <%=portafolio.getSeccionNombre()%>
			</td>
			<td><%= tipo %></td>
			<td><%= portafolio.getOrden() %></td>
			<td><%= portafolio.getEstado() %></td>
		</tr>						
<%			
		}
		if(portafolio.getSeccionId().length()==4){
%>
		<tr>
			<td><%=row%></td>
			<td>
				<a href="editarSeccion?Accion=2&PortafolioId=<%=porId %>&SeccionId=<%=portafolio.getSeccionId() %>" class="btn btn-sm btn-success"><i class="fas fa-check-square"></i></a>
				<%	//if (portafolio.getTipo().equals("-")){ %>
					<a href="grabarSeccion?PortafolioId=<%=portafolio.getPorId()%>&SeccionId=<%=portafolio.getSeccionId()%>" class="btn btn-sm btn-primary"><i class="fas fa-plus-square"></i></a>					
				<% 	//}%>	
				<%	if((usaEmp==false)&&(tieneHijo ==false)){%>
					<a href="javascript: Borrar('<%=porId%>','<%=portafolio.getSeccionId()%>')" class="btn btn-sm btn-danger"><i class="fas fa-trash-alt icon-white"></i></a>
					&nbsp;&nbsp;
				<%	} %>
			</td>
			<td>
				<span class="badge bg-dark"><%=portafolio.getSeccionId()%></span>				
			</td>
			<td>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span class="badge bg-success"><%=portafolio.getTitulo()%></span> <%=portafolio.getSeccionNombre()%>
			</td>
			<td><%= tipo %></td>
			<td><%= portafolio.getOrden() %></td>
			<td><%= portafolio.getEstado() %></td>
		</tr>					
<%		
		}
		
		if(portafolio.getSeccionId().length()==6){
%>
		<tr>
			<td><%=row%></td>
			<td>
				<a href="editarSeccion?Accion=2&PortafolioId=<%=porId %>&SeccionId=<%=portafolio.getSeccionId() %>" class="btn btn-sm btn-success"><i class="fas fa-check-square"></i></a>
				<%	//if (portafolio.getTipo().equals("-")){ %>
					<a href="grabarSeccion?PortafolioId=<%=portafolio.getPorId()%>&SeccionId=<%=portafolio.getSeccionId()%>" class="btn btn-sm btn-primary"><i class="fas fa-plus-square"></i></a>					
				<%	//} %>	
				<%	if((usaEmp==false)&&(tieneHijo ==false)){ %>
					<a href="javascript: Borrar('<%=porId%>','<%=portafolio.getSeccionId()%>')" class="btn btn-sm btn-danger"><i class="fas fa-trash-alt icon-white"></i></a>
					&nbsp;&nbsp;
				<%	} %>					
			</td>
			<td>
				<span class="badge bg-dark"><%=portafolio.getSeccionId()%></span>				
			</td>
			<td>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span class="badge bg-info"><%=portafolio.getTitulo()%></span> <%= portafolio.getSeccionNombre() %>
			</td>
			<td><%= tipo %></td>
			<td><%= portafolio.getOrden() %></td>
			<td><%= portafolio.getEstado() %></td>
		</tr>								
<%			
		}
		if(portafolio.getSeccionId().length()==8){
%>
		<tr>
			<td><%=row%></td>
			<td>
			


	<a href="editarSeccion?Accion=2&PortafolioId=<%=portafolio.getPorId()%>&SeccionId=<%=portafolio.getSeccionId()%>" class="btn btn-sm btn-success"><i class="fas fa-check-square"></i></a>
				<%	//if (portafolio.getTipo().equals("-")){ %>
					<a href="grabarSeccion?PortafolioId=<%=portafolio.getPorId()%>&SeccionId=<%=portafolio.getSeccionId()%>" class="btn btn-sm btn-primary"><i class="fas fa-plus-square"></i></a>					
				<%	//} %>	
				<%	if((usaEmp==false)&&(tieneHijo ==false)){ %>
					<a href="javascript: Borrar('<%=porId%>','<%=portafolio.getSeccionId()%>')" class="btn btn-sm btn-danger"><i class="fas fa-trash-alt icon-white"></i></a>
					&nbsp;&nbsp;
				<%	} %>					
			</td>
			<td>
				<span class="badge bg-dark"><%=portafolio.getSeccionId()%></span>				
			</td>
			<td>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span class="badge bg-warning"><%=portafolio.getTitulo()%></span> <%= portafolio.getSeccionNombre() %>
			</td>
			<td><%= tipo %></td>
			<td><%= portafolio.getOrden() %></td>
			<td><%= portafolio.getEstado() %></td>
		</tr>	
<%				
		}				
	}
%>
	</table>
</div>
<script>
	function Borrar(portafolioId, seccionId) {
		if (confirm("Esta seguro de eliminar esta seccion?") == true) {
			document.location.href = "editarSeccion?Accion=3&PortafolioId="+portafolioId+"&SeccionId="+seccionId;
		}
	}
</script>
<%@ include file= "../../cierra_enoc.jsp" %>