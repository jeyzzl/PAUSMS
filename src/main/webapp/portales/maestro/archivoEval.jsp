<%@ page import="java.text.*" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.archivos.spring.ArchivosAlumno"%>
<%@ page import="aca.carga.spring.CargaGrupoActividad"%>
<%@ page import="aca.kardex.spring.KrdxAlumnoEval"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<script type="text/javascript">	
	
</script>
<%

	// LISTA DE ARCHIVOS NO SE DESPLIEGA 
	DecimalFormat getformato 	= new DecimalFormat("##0.00;(##0.00)");

 	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");
 	
	String cursoCargaId 				= (String)request.getAttribute("cursoCargaId");
	String evaluacionId 				= (String)request.getAttribute("evaluacionId");
	String maestroNombre 				= (String)request.getAttribute("maestroNombre");
	String materiaNombre 				= (String)request.getAttribute("materiaNombre");
	String evaluacionNombre 			= (String)request.getAttribute("evaluacionNombre");
	String opcion			 			= (String)request.getAttribute("opcion");
	
	List<ArchivosAlumno> lisArchivos	= (List<ArchivosAlumno>) request.getAttribute("lisArchivos");	

	HashMap<String,String> mapaAlumnos			= (HashMap<String,String>)request.getAttribute("mapaAlumnos");
	HashMap<String,KrdxAlumnoEval> mapAlumEval 	= (HashMap<String,KrdxAlumnoEval>)request.getAttribute("mapAlumEval");
	HashMap<String,CargaGrupoActividad> mapActividadesEnMateria = (HashMap<String,CargaGrupoActividad>)request.getAttribute("mapActividadesEnMateria");
%>
<div class="container-fluid">
	<h2><spring:message code="portal.maestro.archivoEval.Tareas"/> <small>( <%=materiaNombre%> - <%=evaluacionNombre%> )</small></h2>
	<div class="alert alert-info">
<%	if (opcion.equals("1")){%>	
		<a class="btn btn-primary" href="metodo?CursoCargaId=<%=cursoCargaId%>"><spring:message code="portal.maestro.archivoEval.Regresar"/></a>
<%	}else{%>
		<a class="btn btn-primary" href="evaluar?CursoCargaId=<%=cursoCargaId%>&EvaluacionId=0"><spring:message code="portal.maestro.archivoEval.Regresar"/></a>
<%	}%>		
	</div>	
	<table   class="table table-fontsmall table-condensed table-bordered" >
	<tr class="table-info">
		<td colspan="9"><b><spring:message code="portal.maestro.archivoEval.Maestro"/></b>: <small class="text-muted fs-6">(<%=codigoPersonal%> -  <%=maestroNombre%>)</small></td>
  	</tr>  	
  	<tr>
		<th width="3%"><spring:message code="aca.Numero"/></th>
		<th width="3%"><spring:message code="portal.maestro.archivoEval.Opcion"/></th>    	
		<th width="10%"><spring:message code="aca.Fecha"/></th>
		<th width="5%"><spring:message code="portal.maestro.archivoEval.Matricula"/></th>
		<th width="25%"><spring:message code="portal.maestro.archivoEval.Alumno"/></th>
		<th width="10%"><spring:message code="portal.maestro.archivoEval.Archivo"/></th>
		<th width="25%"><spring:message code="portal.maestro.archivoEval.Comentario"/></th>		
		<th width="5%"><spring:message code="portal.maestro.archivoEval.Nota"/></th>	
		<th width="5%"><spring:message code="portal.maestro.archivoEval.Grabar"/></th>	
			
  	</tr>
<%	
	int row = 1;
	for ( ArchivosAlumno archivo :  lisArchivos){	
		
		String nombreAlumno = "-";
		
		if(mapaAlumnos.containsKey(archivo.getCodigoPersonal())){
			nombreAlumno = mapaAlumnos.get(archivo.getCodigoPersonal());
		}
		
		String nota = "0";

		if(mapAlumEval.containsKey(archivo.getCodigoPersonal()+cursoCargaId+evaluacionId)){
			nota = mapAlumEval.get(archivo.getCodigoPersonal()+cursoCargaId+evaluacionId).getNota();
		}

%>  
	<form action="grabarNotaArchivoEval" method="post">
  	<tr>    
		<td align="center"><strong><%=row++%></strong></td>
		<td align="center">		
<% 		if(!archivo.getNombre().equals("-") & archivo.getArchivoNuevo() != null){%>	
			<a title="Download file" class="btn btn-info" href="../alumno/bajarArchivoAlumno?CursoCargaId=<%=archivo.getArchivoId()%>&Folio=<%=archivo.getFolio()%>&CodigoAlumno=<%=archivo.getCodigoPersonal()%>"><i class="fas fa-download"></i></a>
<% 		}%>	
		</td>		
    	<td align="left"><%=archivo.getFecha()%></td>			
		<td align="left"><%=archivo.getCodigoPersonal()%></td>
		<td style="text-align:left"><%=nombreAlumno%></td>
		<td style="text-align:left"><%=archivo.getNombre()%></td>
		<td style="text-align:left"><%=archivo.getComentario()%></td>
		<td>
			<input name="CodigoAlumno" type="hidden" value="<%=archivo.getCodigoPersonal()%>">
			<input name="CursoCargaId" type="hidden" value="<%=cursoCargaId%>">
			<input name="EvaluacionId" type="hidden" value="<%=evaluacionId%>">
			<input name="Opcion" type="hidden" value="<%=opcion%>">
			<div class="input-group">
				<input name="Nota" type="text" class="form-control" value="<%=nota%>">
			</div>
		</td>
		<td>
<%
			if(mapActividadesEnMateria.isEmpty()){
%>
			<span class="input-group-btn">
		      	<button type="submit" class="btn btn-primary">
		      		<i class="fas fa-save"></i>
		      	</button>
			</span>
<%
			}
%>
		</td>
  	</tr> 
	</form>  
<%				
	}	
%>	
	</table>
</div>
<!-- fin de estructura -->