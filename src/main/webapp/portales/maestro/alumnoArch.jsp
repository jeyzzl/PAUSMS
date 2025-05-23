<%@ page import="java.text.*" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.archivos.spring.ArchivosAlumno"%>
<%@ page import="aca.carga.spring.CargaGrupoActividad"%>
<%@ page import="aca.carga.spring.CargaGrupoEvaluacion"%>
<%@ page import="aca.kardex.spring.KrdxAlumnoActiv"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	DecimalFormat getformato 	= new DecimalFormat("##0.00;(##0.00)");
 	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");
 	
 	String actividadId			= request.getParameter("ActividadId")==null?"0":request.getParameter("ActividadId");
 	String cursoCargaId			= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
	String evaluacionId			= request.getParameter("EvaluacionId")==null?"0":request.getParameter("EvaluacionId");
	String maestroNombre 		= (String)request.getAttribute("maestroNombre");
	String materiaNombre 		= (String)request.getAttribute("materiaNombre");	
	CargaGrupoActividad cga		= (CargaGrupoActividad)request.getAttribute("cga");
	CargaGrupoEvaluacion cge	= (CargaGrupoEvaluacion)request.getAttribute("cge");
	String maestro	 			= (String)request.getAttribute("maestroNombre");
	String materia 				= (String)request.getAttribute("materiaNombre");
	
	List<ArchivosAlumno> lisArchivos						= (List<ArchivosAlumno>) request.getAttribute("lisArchivos");	
	HashMap<String,String> mapAlumnoMaterias				= (HashMap<String,String>)request.getAttribute("mapAlumnoMaterias");
	HashMap<String,KrdxAlumnoActiv> mapNotas				= (HashMap<String,KrdxAlumnoActiv>)request.getAttribute("mapNotas");
	
%>
<div class="container-fluid">
	<h3>Sent Files  <small class="text-muted fs-6">( <%=materiaNombre%> - <%=codigoPersonal%> - <%=maestroNombre%> )</small></h3>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="metodo?CursoCargaId=<%=cursoCargaId%>&EvaluacionId=0"><spring:message code="portal.maestro.archivoAlum.Regresar"/></a>
	</div>	
	<table class="table table-sm table-bordered" >
	<tr class="table-dark">
		<td colspan="9"><b>Evaluation:</b> <%=cge.getNombreEvaluacion()%> Valor: <%=cge.getValor()%><%=cge.getTipo()%> - <b>Activity:</b> <%=cga.getNombre()%> Valor: <%=cga.getValor()%>%</td>			
  	</tr>  	
  	<tr>
		<th width="3%"><spring:message code="aca.Numero"/></th>
		<th width="3%"><spring:message code="portal.maestro.archivoAlum.Opcion"/></th>
		<th width="15%"><spring:message code="portal.maestro.archivoAlum.Alumno"/></th>    	
		<th width="10%"><spring:message code="aca.Fecha"/></th>
		<th width="25%"><spring:message code="portal.maestro.archivoAlum.Comentario"/></th>
		<th width="25%">Comment</th>
		<th width="5%">Evaluation</th>		
		<th width="10%"><spring:message code="portal.maestro.archivoAlum.Nota"/></th>	
		<th width="5%"><spring:message code="portal.maestro.archivoAlum.Grabar"/></th>		
  	</tr>
<%	
	int row = 0;
	
	for ( ArchivosAlumno archivo :  lisArchivos){
		row++;
		
		String nota = "";
		if(mapNotas.containsKey(archivo.getCodigoPersonal()+archivo.getActividadId())){
			nota = mapNotas.get(archivo.getCodigoPersonal()+archivo.getActividadId()).getNota();
		}else{
			nota = "0";
		}

		if (true){
%>  
  	<tr>    
		<td align="center"><strong><%=row%></strong></td>
		<td align="center">
<% 			if(!archivo.getNombre().equals("-") & archivo.getArchivoNuevo() != null){%>				
			<a title="Download file" class="btn btn-info btn-sm" href="../alumno/bajarArchivoAlumno?CursoCargaId=<%=archivo.getArchivoId()%>&CodigoAlumno=<%=archivo.getCodigoPersonal()%>&Folio=<%=archivo.getFolio()%>"><i class="fas fa-download"></i></a>
<% 			}%>	
		</td>
		<td align="center">
		<%= mapAlumnoMaterias.get(archivo.getCodigoPersonal()) %>
		</td>		
    	<td align="left"><%=archivo.getFecha()%></td>
		<td width="9%" style="text-align:left"><%=archivo.getNombre()%></td>
		<td><%=archivo.getComentario()%></td>
		<td style="text-align:left"><span class="badge bg-dark rounded-pill" title="<%=cge.getNombreEvaluacion()%>-<%=cge.getValor()%>"><%=cga.getEvaluacionId()%></span></td>
		<form action="grabarNotaAlumnoArchivoActiv">
		<td>			
			<input name="CodigoAlumno" type="hidden" value="<%=archivo.getCodigoPersonal()%>">
			<input name="CursoCargaId" type="hidden" value="<%=cursoCargaId%>">
			<input name="ActividadId" type="hidden" value="<%=archivo.getActividadId()%>">
		    <div class="input-group">
		    	<input name="Nota" type="text" class="form-control" value="<%=nota%>">
		    </div>	    	
		</td>
		<td>
			<span class="input-group-btn">
	      		<button type="submit" class="btn btn-primary btn-sm">
			      	<i class="fas fa-save"></i>
		      	</button>
	      	</span>
		</td>
		</form>
  	</tr>  
<%
		}
	}	
%>	
	</table>
</div>
<!-- fin de estructura -->