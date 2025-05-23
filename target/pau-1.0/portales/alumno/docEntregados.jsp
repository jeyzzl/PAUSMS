<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.archivo.spring.ArchEntrega"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ include file= "menu.jsp" %>
<jsp:useBean id="ArchEntregaU" scope="page" class="aca.archivo.ArchEntregaUtil"/>
<%
	String codigoAlumno		= (String)session.getAttribute("codigoAlumno");
	
	AlumPersonal alumPersonal 						= (AlumPersonal) request.getAttribute("alumPersonal");
	List<ArchEntrega> lisEntregas					= (List<ArchEntrega>) request.getAttribute("lisEntregas");
	HashMap<String,String> mapaDocumentos 			= (HashMap<String,String>)request.getAttribute("mapaDocumentos");
	HashMap<String,String> mapaIdentificacion 		= (HashMap<String,String>)request.getAttribute("mapaIdentificacion");	
	HashMap<String,String> mapaPoder		 		= (HashMap<String,String>)request.getAttribute("mapaPoder");
	HashMap<String,String> mapaEnvio		 		= (HashMap<String,String>)request.getAttribute("mapaEnvio");
	HashMap<String,String> mapaFirma		 		= (HashMap<String,String>)request.getAttribute("mapaFirma");
	HashMap<String,String> mapaExtra		 		= (HashMap<String,String>)request.getAttribute("mapaExtra");

	String fecha 			= "";
%>
<script type="text/javascript">
	function borrar(folio){
		if(confirm("¿Estás Seguro que Deseas Eliminar Este Registro?")){
			location.href = "borrar?Folio="+folio; 
		}
	}
</script>

<div class="container-fluid">
	<h2>Documentos entregados <small class="text-muted fs-4">( <%=codigoAlumno%> - <%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoPaterno()%> <%=alumPersonal.getApellidoMaterno()%> )</small></h2>
	<div class="alert alert-info">
		<a href="documentos" class="btn btn-primary">Regresar</a>
	</div>
	<table class="table">
		<tr>			
			<td width="2%"><strong>Folio</strong></td>
			<td width="2%"><strong>Fecha</strong></td>
			<td width="41%"><strong>Documentos</strong></td>
			<td width="10%"><strong>Indentificacion</strong></td>
			<td width="10%"><strong>Carta poder</strong></td>
			<td width="10%"><strong>Envio</strong></td>
			<td width="10%"><strong>Firma</strong></td>
			<td width="10%"><strong>Extra</strong></td>
		</tr>
<%		for(ArchEntrega entrega : lisEntregas){ 
			String[] arrayFec = entrega.getFecha().substring(0, 10).split("-");
			fecha = arrayFec[2]+"/"+arrayFec[1]+"/"+arrayFec[0];
%>
		<tr>			
			<td><a title="Fotos" href="docFotos?Folio=<%=entrega.getFolio()%>"><span class="badge"><%=entrega.getFolio()%></span></a></td>
			<td><%=fecha%></td>
			<td>
<% 			
			String[] arrayDoc = entrega.getDocumentos().split("-");
			for(String docId : arrayDoc){
				String docNombre = "";
				if (mapaDocumentos.containsKey(docId)){
					docNombre = mapaDocumentos.get(docId);
				}
%>
				<span class="badge bg-success"><%=docNombre%></span>
<% 			}%>
			</td>
			<td>
<% 				if( mapaIdentificacion.containsKey(entrega.getFolio()) ){%>
					<span class="badge bg-success"><i class="glyphicon glyphicon-ok icon-white"></i></span>
<% 				}%>
			</td>
			<td>
<% 				if( mapaPoder.containsKey(entrega.getFolio()) ){%>
					<span class="badge bg-success"><i class="glyphicon glyphicon-ok icon-white"></i></span>
<% 				}%>
			</td>
			<td>
<% 				if( mapaEnvio.containsKey(entrega.getFolio()) ){%>
					<span class="badge bg-success"><i class="glyphicon glyphicon-ok icon-white"></i></span>
<% 				}%>
			</td>
			<td>
<% 				if( mapaFirma.containsKey(entrega.getFolio()) ){%>
					<span class="badge bg-success"><i class="glyphicon glyphicon-ok icon-white"></i></span>
<% 				}%>
			</td>
			<td>
<% 				if( mapaExtra.containsKey(entrega.getFolio()) ){%>
					<span class="badge bg-success"><i class="glyphicon glyphicon-ok icon-white"></i></span>
<% 				}%>
			</td>
		</tr>
<% 		}%>
	</table>
</div>