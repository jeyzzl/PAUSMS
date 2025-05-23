<%@ page import="java.text.*" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.archivos.spring.ArchivosAlumno"%>
<%@ page import="aca.kardex.spring.KrdxAlumnoActiv"%>
<%@ page import="aca.carga.spring.CargaGrupoActividad"%>
<%@ page import="aca.carga.spring.CargaGrupoEvaluacion" %>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<script type="text/javascript">	
	
</script>
<%
	DecimalFormat getformato 	= new DecimalFormat("##0.00;(##0.00)");

 	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");
 	
 	String cursoCargaId 				= (String)request.getAttribute("cursoCargaId");
	String evaluacionId			= request.getParameter("EvaluacionId")==null?"0":request.getParameter("EvaluacionId");
	String maestroNombre 		= (String)request.getAttribute("maestroNombre");
	String materiaNombre 		= (String)request.getAttribute("materiaNombre");
	String alumnoNombre 		= (String)request.getAttribute("alumnoNombre");
	
	List<ArchivosAlumno> lisArchivos	= (List<ArchivosAlumno>) request.getAttribute("lisArchivos");
	
	HashMap<String,KrdxAlumnoActiv> mapActividadesMateria 			= (HashMap<String,KrdxAlumnoActiv>)request.getAttribute("mapActividadesMateria");
	HashMap<String,CargaGrupoActividad> mapActividadesEnMateria 	= (HashMap<String,CargaGrupoActividad>)request.getAttribute("mapActividadesEnMateria");
	HashMap<String,CargaGrupoEvaluacion> mapaEvaluacionPorMateria 	= (HashMap<String, CargaGrupoEvaluacion>)request.getAttribute("mapaEvaluacionPorMateria");
	
%>
<div class="container-fluid">
	<h3><spring:message code="portal.maestro.archivoAlum.Tareas"/> <small>( <%=materiaNombre%> - <%=alumnoNombre%> )</small></h3>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="evaluar?CursoCargaId=<%=cursoCargaId%>&EvaluacionId=0"><spring:message code="portal.maestro.archivoAlum.Regresar"/></a>
	</div>	
	<table class="table table-fontsmall table-sm" >
	<tr>
		<td colspan="9"><spring:message code="portal.maestro.archivoAlum.Maestro"/>: <%=codigoPersonal%> -  <%=maestroNombre%></td>			
  	</tr>  	
  	<tr>
		<th width="3%"><spring:message code="aca.Numero"/></th>
		<th width="3%"><spring:message code="portal.maestro.archivoAlum.Opcion"/></th>    	
		<th width="10%"><spring:message code="aca.Fecha"/></th>
		<th width="30%"><spring:message code="portal.maestro.archivoAlum.Comentario"/></th>
		<th width="10%"><spring:message code="portal.maestro.metodo.Evaluacion"/></th>
		<th width="5%"><spring:message code="aca.Valor"/></th>
		<th width="15%"><spring:message code="portal.maestro.archivoAlum.Actividad"/></th>
		<th width="5%"><spring:message code="aca.Valor"/></th>
		<th width="5%"><spring:message code="portal.maestro.archivoAlum.Nota"/></th>	
		<th width="5%"><spring:message code="portal.maestro.archivoAlum.Grabar"/></th>		
  	</tr>
<%	
	int row = 0;	
	for ( ArchivosAlumno archivo :  lisArchivos){
		row++;
		
		String nota = "0";
		
		if(mapActividadesMateria.containsKey(archivo.getCodigoPersonal()+archivo.getActividadId())){
			nota = mapActividadesMateria.get(archivo.getCodigoPersonal()+archivo.getActividadId()).getNota();
		}

		if (mapActividadesEnMateria.containsKey(archivo.getActividadId())){
			
			CargaGrupoActividad cga = mapActividadesEnMateria.get(archivo.getActividadId());
			CargaGrupoEvaluacion cge = mapaEvaluacionPorMateria.get(archivo.getEvaluacionId());
%>  
  	<tr>    
		<td align="center"><strong><%=row%></strong></td>
		<td align="center">
<% 			if(!archivo.getNombre().equals("-") & archivo.getArchivoNuevo() != null){
%>				
			<a title="<%=archivo.getNombre()%>>" class="btn btn-info" href="../alumno/bajarArchivoAlumno?CodigoAlumno=<%=archivo.getCodigoPersonal()%>&CursoCargaId=<%=archivo.getArchivoId()%>&Folio=<%=archivo.getFolio()%>">
				<i class="fas fa-download"></i>
			</a>
<% 			}
%>	
		</td>		
    	<td align="left"><%=archivo.getFecha()%></td>
		<td style="text-align:left"><%=archivo.getComentario()+" ("+archivo.getActividadId()+")"%></td>
		<td style="text-align: left"><span class="badge bg-dark rounded-pill" title="<%=cge.getNombreEvaluacion()%>"><%=cge.getEvaluacionId()%></span></td>
		<td style="text-align: left"><%=cge.getValor()%><%=cge.getTipo()%></td>
		<td style="text-align:left"><%=cga.getNombre()%></td>
		<td style="text-align:left"><%=cga.getValor()%>%</td>
		
		<form action="grabarNotaArchivoActiv" method="post">
		<td>			
			<input name="CodigoAlumno" type="hidden" value="<%=archivo.getCodigoPersonal()%>">
			<input name="CursoCargaId" type="hidden" value="<%=cursoCargaId%>">
			<input name="ActividadId" type="hidden" value="<%=archivo.getActividadId()%>">
		    <div class="input-group">
		    	<input name="Nota" type="text" class="form-control" value="<%=nota%>">
		    </div>	    	
		</td>
		<td style="text-align: center">
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