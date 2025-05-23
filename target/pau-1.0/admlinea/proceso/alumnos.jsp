<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.admision.spring.AdmDocAlum"%>
<%@page import="aca.admision.spring.AdmSolicitud"%>
<%@page import="aca.admision.spring.AdmUsuario"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<% 
	List<AdmDocAlum> lisDocumentos  				= (List<AdmDocAlum>) request.getAttribute("lisDocumentos");
	HashMap<String,AdmSolicitud> mapaSolicitudes 	= (HashMap<String,AdmSolicitud>) request.getAttribute("mapaSolicitudes");
	HashMap<String,AdmUsuario> mapaUsuarios 		= (HashMap<String,AdmUsuario>) request.getAttribute("mapaUsuarios");
	HashMap<String,String> mapaAsesores 			= (HashMap<String,String>) request.getAttribute("mapaAsesores");
	HashMap<String,String> mapaPendientes			= (HashMap<String,String>) request.getAttribute("mapaPendientes");
 	String tempFolio = "X";
%>
<body>
<div class="container-fluid">
	<h2><spring:message code='aca.DocumentosPendientes'/></h2>
	<div class="alert alert-info d-flex align-items-center"  >
		        <input type="text" class="form-control search-query" placeholder="Buscar" id="buscar" style="width:200px">
	
	</div>
	<table id="table" class="table table-sm table-bordered"> 
    <thead>
    <tr  class ="table-info">
    	<th width="7% "class="text-center"><h3>#</h3></th>
		<th width="7%" class="text-center"><h4><spring:message code="aca.Folio"/></h4></th>
		<th width="25%"><h4><spring:message code="aca.Nombre"/></h4></th>
		<th width="15%" class="text-center"><h4><spring:message code='aca.NumeroDocumentos'/></h4></th>
		<th width="28%"><h4><spring:message code="aca.Asesor"/></h4></th>
	</tr>
	</thead>
 	<tbody>
<%  			
	int row=0;
	for(AdmDocAlum doc : lisDocumentos){
		String nombreCandidato 	= "-";					
		String asesorId			= "0";
		String nombreAsesor		= "-";
		String usuarioId		= "0";
		if (mapaSolicitudes.containsKey(doc.getFolio())){
			usuarioId = mapaSolicitudes.get(doc.getFolio()).getUsuarioId();
			if (mapaUsuarios.containsKey(usuarioId)){
				nombreCandidato = mapaUsuarios.get(usuarioId).getNombre()+" "+mapaUsuarios.get(usuarioId).getApellidoPaterno()+" "+mapaUsuarios.get(usuarioId).getApellidoMaterno();
			}
			asesorId 		= mapaSolicitudes.get(doc.getFolio()).getAsesorId();
			if (mapaAsesores.containsKey(asesorId)){
				nombreAsesor = mapaAsesores.get(asesorId);
			}
		}
		String pendientes = "0";
		if (mapaPendientes.containsKey(doc.getFolio())){
			pendientes = mapaPendientes.get(doc.getFolio());
		}
		
		boolean asesor = asesorId.equals(session.getAttribute("codigoPersonal").toString()) || asesorId.equals(session.getAttribute("codigoPersonal").toString());
		if(!tempFolio.equals(doc.getFolio())){
			row++;
			tempFolio = doc.getFolio();
				%>  
		 <tr style="cursor:pointer;" onclick="document.location.href='../admision/documentos?Folio=<%=doc.getFolio()%>&pag=Alumnos';">
		    <td align="center"><%=row%></td>
		    <td align="center"><%= doc.getFolio()%></td>
		    <td><%=nombreCandidato%></td>
		    <td align="center"><font size="3"><b><%=pendientes%></b></font></td>
		    <td><font color="<%=asesor ? "green" : "#000" %>" <%=asesor ? "style='font-weight:bold; font-size:10pt;'" : "" %>><%=nombreAsesor%></font></td>
		  </tr>
<% 
		}
	}%>
	</tbody>
	</table>		
</div>
</body>
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>