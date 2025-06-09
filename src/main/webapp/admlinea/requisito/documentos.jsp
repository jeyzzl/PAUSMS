<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.admision.spring.AdmRequisito"%>
<%@ page import= "aca.admision.spring.AdmDocumento"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<html>
<%
	String facultad				= request.getParameter("Facultad")==null?"0":request.getParameter("Facultad");
	String carrera				= request.getParameter("Carrera")==null?"0":request.getParameter("Carrera");
	String facultadNombre		= (String)request.getAttribute("facultadNombre");
	String carreraNombre		= (String)request.getAttribute("carreraNombre");
	String mensaje 				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");

	List<AdmDocumento> lisDocumentos 				= (List<AdmDocumento>) request.getAttribute("lisDocumentos");
	HashMap<String,AdmRequisito> mapaRequisitos		= (HashMap<String,AdmRequisito>) request.getAttribute("mapaRequisitos");
%>
<body>
<div class="container-fluid">
	<h2><spring:message code="aca.Requisitos"/><small class="text-muted fs-4"> ( <%=carreraNombre%> ) </small></h2>
	<div class="alert alert-info">
		<a href="carrera?facultad=<%=facultad %>" class="btn btn-primary"><spring:message code='aca.Regresar'/></a>&nbsp;&nbsp;
		<strong><spring:message code='aca.SeleccioneUnDocumento'/>&nbsp;!</strong>
<%	if(!mensaje.equals("-")){%>
			<%=mensaje%>
<%	}%>
	</div>
	<table id="table" class="table table-sm table-bordered"  >
   	<thead>
	  	<tr>
	  		<th>#</th>									    	
	    	<th><spring:message code="aca.Documento"/></th>
	    	<th><spring:message code="aca.Modalidades"/></th>
			<th>Study Levels</th>
			<th>Application Types</th>
			<th>Nationalities</th>
			<th>Marital Status</th>
	    	<th><spring:message code="aca.Coordinador"/> Authorization</th>
	  	</tr>
	</thead>
	<tbody>
<%
	int row = 0;
	for(AdmDocumento documento : lisDocumentos){
		row++;
		
		String etiqueta		= "<span class='bg'>"+row+"</span>"; 
		String valor		= "NO";
		String modalidades 	= "-";
		String niveles 		= "-";
		String tipos 		= "-";
		String nacionalidades = "-";
		String estadosCiviles = "-";
		String requerido 	= "-";
		String coordinador	= "-";
		if (mapaRequisitos.containsKey(documento.getDocumentoId())){
			modalidades 	= mapaRequisitos.get(documento.getDocumentoId()).getModalidades();
			niveles 		= mapaRequisitos.get(documento.getDocumentoId()).getNiveles();
			tipos 			= mapaRequisitos.get(documento.getDocumentoId()).getTipos();
			nacionalidades 	= mapaRequisitos.get(documento.getDocumentoId()).getNacionalidades();
			estadosCiviles 	= mapaRequisitos.get(documento.getDocumentoId()).getEstadosCiviles();
			requerido 		= mapaRequisitos.get(documento.getDocumentoId()).getRequerido();
			coordinador 	= mapaRequisitos.get(documento.getDocumentoId()).getAutorizar();
			if (requerido.equals("S")) {
				etiqueta	= "<span class='badge bg-dark'>"+row+"</span>";
			}
			if (coordinador.equals("C")) {
				valor 		= "YES";
			}
		}
%>
	  	<tr>
	    	<td align="center"><strong><%=etiqueta%></strong></td>
	    	<td>
	    		<a href="modalidades?Facultad=<%=facultad %>&Carrera=<%=carrera %>&Documento=<%=documento.getDocumentoId() %>">
	    			<strong>[ <%=documento.getDocumentoId()%> ] <%=documento.getDocumentoNombre() %></strong>
	    		</a>
	    	</td>
	    	<td>
	    	<%=modalidades%>
    		</td>
	    	<td>
	    	<%=niveles%>
    		</td>
	    	<td>
	    	<%=tipos%>
    		</td>
	    	<td>
	    	<%=nacionalidades%>
    		</td>
	    	<td>
	    	<%=estadosCiviles%>
    		</td>
	    	<td>
	    	<%=valor%>
    		</td>
	  	</tr>
<%	} %>
	</tbody>
	</table>							
</div>
</body>
</html>