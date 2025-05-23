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
		document.forma.submit();
	}	
</script>
<%
	String cursoCargaId 	= (String) request.getAttribute("cursoCargaId");	
	String mensaje			= (String) request.getAttribute("mensaje");	
	if(mensaje.equals("-")){
		mensaje = "";
	}
%>	
	
<div class="container-fluid">
	<h2><spring:message code="portal.maestro.subirArchivo.SubirArchivos"/> </h2>
	<div class="alert alert-info m-0">
		<a class="btn btn-primary" href="filesMaestro?CursoCargaId=<%=cursoCargaId%>&EvaluacionId=0"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
	</div>

	<form name="forma" enctype="multipart/form-data" action="grabarArchivo?CursoCargaId=<%=cursoCargaId%>&Accion=1" method="post">		
	<table class="my-3">
		<tr>
		   	<td colspan='5'><spring:message code="portal.maestro.subirArchivo.TamanoArchivo"/>: <b>7 Megabytes.</b><br>
				  <input style="border-width:1px;" name="archivo" id="archivo" type="file" size="50"/>
		 	</td>
		</tr>
		<tr>
	 		<td colspan='5'>
			  <div align='left'>&nbsp;<spring:message code="portal.maestro.subirArchivo.Comentario"/>:<br></div>
	 		  <textarea cols='68' name='comentario' onkeyup='checaCuantasLetras(this.value);'></textarea><br>
	 		  <span style="font-weight: bold;" id=charcount></span>
	  		</td>
	 	</tr>
	</table>
	</form>
	<div class="alert alert-info">
		<a href="javascript:AlumnosAutorizados();" class="btn btn-primary ml-3"/>Save</a><%=mensaje%>
	</div>
</div>
<script>
	function checaCuantasLetras(str) {
		var lng = str.length;
		document.getElementById("charcount").innerHTML = lng + ' CHARACTERS OF 500';
	}
</script>