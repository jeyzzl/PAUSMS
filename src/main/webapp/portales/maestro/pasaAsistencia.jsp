<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>

<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file="../../idioma.jsp"%>

<!-- inicio de estructura -->
<%
 	String codigoPersonal	= (String) session.getAttribute("codigoEmpleado");
	String cursoCargaId		= request.getParameter("CursoCargaId");
	String materia 			= request.getParameter("Materia");
	String folio			= request.getParameter("Folio");
	String maestro 			= (String)request.getAttribute("maestroNombre");

	int accionFmt			= Integer.valueOf(request.getParameter("accionFmt")==null?"0":request.getParameter("accionFmt"));
	
	List<AlumnoCurso> lisAlumnos 				= (List<AlumnoCurso>) request.getAttribute("lisAlumnos");	
	HashMap<String,String> mapAsistencia 		= (HashMap<String,String>) request.getAttribute("mapAsistencia");
	HashMap<String,AlumPersonal> mapAlumnos 	= (HashMap<String,AlumPersonal>) request.getAttribute("mapAlumnos");
	HashMap<String,String> mapNombreCorto		= (HashMap<String,String>) request.getAttribute("mapNombreCorto");
%>
<div class="container-fluid">
	<h2>Attendance record<small class="text-muted fs-5">( <%=codigoPersonal%> - <%=maestro%> - <%=materia%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="asistencia?CursoCargaId=<%=cursoCargaId%>&Materia=<%=materia%>"><i class="fas fa-arrow-left"></i></a>
	</div>
	<form name="frmAsistencia" action="grabarAsistencia" method="post">
	<input name="Accion" type="hidden">
  	<input name="CursoCargaId" type="hidden" value="<%=cursoCargaId %>">
  	<input name="Folio" type="hidden" value="<%=folio%>"/>
  	<input name="Materia" type="hidden" value="<%=materia%>"/>	
	<table class="table table-condensed table-sm table-bordered" style="width:70%">	
	<tr class="table-info"> 
    	<th class="center" width="3%"><b><spring:message code="aca.Numero"/></b></th>
    	<th class="center"><b><spring:message code="aca.Matricula"/></b></th>
    	<th class="center"><b><spring:message code="aca.Nombre"/></b></th>
    	<th class="center"><b>Status</b></th>
    	<th class="left">
    		<b>Attendance</b>
    		<a onclick="javascript:CambiarPresente('todos');" class="btn btn-small"><i class="far fa-check-square"></i></a>
    		<a onclick="javascript:CambiarPresente('ninguno');" class="btn btn-small"><i class="far fa-square"></i></a>
    		
    	</th>
    	<th class="left">
    		<b>Late</b>	    		
    		<a onclick="javascript:CambiarTardanza('todos');" class="btn btn-small"><i class="far fa-check-square"></i></a>
    		<a onclick="javascript:CambiarTardanza('ninguno');" class="btn btn-small"><i class="far fa-circle"></i></a>
    	</th>
	</tr>
<%
	int row = 0;
	for (AlumnoCurso ac : lisAlumnos){
		row++;
		
		// Busca la modalidad del Alumno
		String checkPresente = "";
		String checkTardanza = "";
		if ( mapAsistencia.containsKey(ac.getCodigoPersonal())){			
			if (mapAsistencia.get(ac.getCodigoPersonal()).equals("1")) checkPresente = "checked"; 
			if (mapAsistencia.get(ac.getCodigoPersonal()).equals("2")) checkTardanza = "checked";
		}
		
		String alumnoNombre = "";
		if (mapAlumnos.containsKey(ac.getCodigoPersonal())){
			alumnoNombre = mapAlumnos.get(ac.getCodigoPersonal()).getApellidoPaterno()+" "+mapAlumnos.get(ac.getCodigoPersonal()).getApellidoMaterno()+", "+mapAlumnos.get(ac.getCodigoPersonal()).getNombre(); 
		}
		
		String nombreCorto = mapNombreCorto.get(ac.getTipoCalId());
%>	
   	<tr>
    	<td align="center"><%=row%></td> 
    	<td align="center" width="20">
      	<%=ac.getCodigoPersonal()%>
      	<input name="CodigoPersonal<%=ac.getCodigoPersonal()%>" type="hidden" value="<%=ac.getCodigoPersonal()%>"/>
    	</td>     
    	<td align="left">
    	<%=alumnoNombre%>
    	</td>        
    	<td align="left">
    	<%= nombreCorto%>
    	</td> 
    	<td><input type="checkbox" name="Presente<%=ac.getCodigoPersonal()%>" value="1" class="presente" <%=checkPresente%>></td>
		<td><input type="checkbox" name="Tardanza<%=ac.getCodigoPersonal()%>" value="2" class="tardanza" <%=checkTardanza%>></td>
  	</tr>
<%	} %>
	</table>
	</form>			
	<div class="alert alert-info">			
		<input type="button" value="Save" onclick="javascript:Grabar();" class="btn btn-primary"/>&nbsp;&nbsp;
<%
 	if(accionFmt!=0){
  		if(accionFmt==1){ out.print("Saved");}
		if(accionFmt==2){ out.print("Error saving");}
		if(accionFmt==3){ out.print("Updated");}
		if(accionFmt==4){ out.print("Error Updating");}
  	}
%>			
	</div>
</div>
<script>

	function Grabar() {
		document.frmAsistencia.Accion.value = "1";
		document.frmAsistencia.submit();
	}
	
	function CambiarPresente(opcion){		
		if(opcion  === 'todos' ){
			jQuery('.presente').prop('checked',true);
		}else{
			jQuery('.presente').prop('checked',false);							
		}		
	} 
	
	function CambiarTardanza(opcion){
		if(opcion  === 'todos' ){
			jQuery('.tardanza').prop('checked',true);
		}else{
			jQuery('.tardanza').prop('checked',false);							
		}		
	}
</script>