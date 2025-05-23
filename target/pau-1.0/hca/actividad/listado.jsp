<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.hca.spring.HcaActividad"%>
<%@page import="aca.hca.spring.HcaTipo"%>
<%@page import="aca.catalogo.spring.CatNivel"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<head>
<script type="text/javascript">
	function eliminar(actividad){
		if(confirm("¿Está seguro que desea borrar la actividad?")){
			document.location.href = "eliminar?ActividadId="+actividad;
		}
	}
</script>
</head>
<% 
	String mensaje 	= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");

	List<HcaActividad> lisActividad				= (List<HcaActividad>) request.getAttribute("lisActividad");	
	HashMap<String,String> mapaActividades		= (HashMap<String,String>)request.getAttribute("mapaActividades");
	HashMap<String,HcaTipo> mapaTipos 			= (HashMap<String,HcaTipo>)request.getAttribute("mapaTipos");
	HashMap<String,CatNivel> mapaNiveles 		= (HashMap<String,CatNivel>)request.getAttribute("mapaNiveles");
%>
<div class="container-fluid">
	<h1><spring:message code="aca.Actividades"/></h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="edita_actividad">Nueva</a>
		<%=mensaje.equals("-")?"":mensaje%>		
	</div>
	<table class="table table-bordered ">
<%		
	String tipo = "";
	String nivel = "";
	for( HcaActividad actividad : lisActividad){
		
		String nivelNombre = "";
		if(actividad.getNivelId().equals("0")){
			nivelNombre = "Todos";
		}else{
			if (mapaNiveles.containsKey(actividad.getNivelId())){
				nivelNombre = mapaNiveles.get(actividad.getNivelId()).getNombreNivel();
			}	
		}
		
		String tipoNombre = "-";		
		if(!tipo.equals(actividad.getTipoId())){
			tipo = actividad.getTipoId();
			if (mapaTipos.containsKey(actividad.getTipoId())){
				tipoNombre = mapaTipos.get(actividad.getTipoId()).getTipoNombre();
			}			
%>
		    
		<tr class="th">
		  <td colspan="5"><h3><%=tipoNombre%></h3></td>
		</tr>
		<tr>
		  <th>Op.</th>
		  <th>Nivel</th>
		  <th><spring:message code="aca.Nombre"/></th>
		  <th>Valor</th>
		  <th>Mod.</th>
		</tr>
<%		} %>
		<tr >
		  <td>
		    <a href="edita_actividad?ActividadId=<%=actividad.getActividadId()%>" <i class="fas fa-pencil-alt"></i></a>
<% 		// Si la actividad existe la tabla de actividades del maestro no se debe borrar de este catálogo 
		if ( !mapaActividades.containsKey(actividad.getActividadId())){%>
		    <a href="javascript:eliminar('<%=actividad.getActividadId() %>');"><i class="fas fa-trash-alt"></i></a> &nbsp;
<%		}%>	
		  </td>
		  <td><%= nivelNombre %></td>
		  <td><%=actividad.getActividadNombre() %></td>
		  <td align="center"><%=actividad.getValor() %></td>
		  <td align="center"><%=actividad.getModificable().equals("S")?"SI":"NO" %></td>
		</tr>
<%	} %>
	</table>
</div>
