<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.kardex.spring.KrdxCursoAct"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%> 
<script>
	function AlumnosAutorizados(){
		var mat = "";

		jQuery('input[name="mats"]:checked').each(function(){
			mat += jQuery(this).val()+"_N,";
		});
		
		document.forma.matriculas.value = mat;
		document.forma.submit();
	}
	
	function checaTodos(){
		jQuery('input[name="mats"]').attr("checked", true);
	}
	
	function checaNinguno(){
		jQuery('input[name="mats"]').attr("checked", false);
	}
</script>

<%
	String cursoCargaId = (String) request.getAttribute("cursoCargaId");
	
	String mensaje		= (String) request.getAttribute("mensaje");

	List<KrdxCursoAct> lisAlumnos 	= (List<KrdxCursoAct>) request.getAttribute("lisAlumnos");
	
	HashMap<String, AlumPersonal> mapaAlumno = (HashMap<String, AlumPersonal>) request.getAttribute("mapaAlumno");
	
	if(mensaje.equals("0")){
		mensaje = "";
	}
%>	
	
<div class="container-fluid">
	<h2><spring:message code="portal.maestro.subirArchivo.SubirArchivos"/> </h2>
	<div class="alert alert-info m-0">
		<a class="btn btn-primary" href="filesMaestro?CursoCargaId=<%=cursoCargaId%>&EvaluacionId=0"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
	</div>

	<form name="forma" enctype="multipart/form-data" action="grabarArchivo?CursoCargaId=<%=cursoCargaId%>&Accion=1" method="post">

	<input type="hidden" name="matriculas"/>
	
	<table class="my-3">
		<tr>
		   	<td colspan='5'><spring:message code="portal.maestro.subirArchivo.TamanoArchivo"/>: <b>7 Megabytes.</b><br>
				  <input style="border-width:1px;" name="archivo" id="archivo" type="file" size="50"/>
		 	</td>
		</tr>
		<tr>
	 		<td colspan='5'>
			  <div align='left'>&nbsp<spring:message code="portal.maestro.subirArchivo.Comentario"/>:<br></div>
	 		  <textarea cols='68' name='comentario' onkeyup='checaCuantasLetras(500);'></textarea>
	  		</td>
	 	</tr>
	</table>
	<table class="table table-bordered">
		<tr class="table-info">
			<td colspan='3'>
				<b><spring:message code="portal.maestro.subirArchivo.Para"/>: </b>
				<a href='javascript:checaTodos();' class="btn btn-info"><spring:message code="portal.maestro.subirArchivo.Todos"/></a>
				<a href='javascript:checaNinguno();' class="btn btn-info"><spring:message code='aca.Ninguno'/></a>
				<input class="align-content-center btn btn-primary ml-3" onclick='AlumnosAutorizados();' type="button" name=Enviar value="Enviar"/><%=mensaje%>
			</td>
		</tr>
<%				
			for (int i=0; i<lisAlumnos.size();i++){
				KrdxCursoAct ac = lisAlumnos.get(i);
				String nombreAlumno = "";
				if(mapaAlumno.containsKey(ac.getCodigoPersonal())){
					nombreAlumno = mapaAlumno.get(ac.getCodigoPersonal()).getNombreLegal();
				}
%>
		<tr>
			<td align='center'><input type='checkbox' name="mats" value="<%=ac.getCodigoPersonal()%>"/></td>
			<td align='center'><%=ac.getCodigoPersonal()%></td>
			<td><%=nombreAlumno%></td>
		</tr>
<%}%>
	</table>
	</form>

</div>