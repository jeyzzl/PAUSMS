<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.kardex.spring.KrdxCursoAct"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<%
	String cursoCargaId 	= (String) request.getAttribute("cursoCargaId");
	String evaluacion 		= (String) request.getAttribute("evaluacion");
	String actividad 		= (String) request.getAttribute("actividad");
	String enviar 			= (String) request.getAttribute("enviar");
	String mensaje 			= (String) request.getAttribute("mensaje");
	
	String enviaInfo        = "";
	
// 	if(!evaluacion.equals("0") & actividad.equals("0")) {
// 		enviaInfo        = "Evaluacion="+evaluacion;
// 	}else if(evaluacion.equals("0") & !actividad.equals("0")) {
// 		enviaInfo        = "Actividad="+actividad;
// 	}
		
%>	
	
<div class="container-fluid">
	<h2>Subir Archivos </h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="detallecal?CursoCargaId=<%=cursoCargaId%>"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
	</div>	
<% 	if(enviar.equals("A")){%>
	<form name="formaArchivo" enctype="multipart/form-data" action="grabarArchivo?CursoCargaId=<%=cursoCargaId%>&Enviar=<%=enviar%>&Actividad=<%=actividad%>&Evaluacion=<%=evaluacion%>" method="post">
		<table>
			<tr>
			   	<td colspan='5'>Tamaño máximo del archivo: <b>7 Megabytes.</b><br>
				  	<input style="border-width:1px;" name="archivo" id="archivo" type="file" size="50"/>
			 	</td>
			</tr>
			<tr>
		 		<td colspan='5'>
				  <div align='left'>Comentario:<br></div>
		 		  <textarea cols='68' name='comentario' onkeyup='charcountupdate(this.value)'></textarea>
		  		</td>
		 	</tr>
			<tr>
		 		<td colspan='5'>
			  		<span style="font-weight: bold;" id=charcount></span>
		  		</td>
		 	</tr>
		</table><br>
		<div>
			<input class="btn btn-primary" type="submit" value="Upload">&nbsp;&nbsp;&nbsp;<%=mensaje%>
		</div>
	</form>
<% 	}else{ %>
	<form name="formaComentario" action="grabarComentario?CursoCargaId=<%=cursoCargaId%>&Enviar=<%=enviar%>" method="post">
		<input type="hidden" name="Actividad" value="<%=actividad%>">
		<input type="hidden" name="Evaluacion" value="<%=evaluacion%>">
		<table>
			<tr>
		 		<td colspan='5'>
				  <div align='left'>&nbsp;&nbsp;Comentario:<br></div>
		 		  <textarea cols='68' name='comentario' onkeyup='charcountupdate(this.value)'></textarea>
		  		</td>
		 	</tr>
			<tr>
		 		<td colspan='5'>
			  		<span style="font-weight: bold;" id=charcount></span>
		  		</td>
		 	</tr>
		</table><br>
		<div>
			<input class="btn btn-primary" type="submit" value="Subir">&nbsp;&nbsp;&nbsp;<%=mensaje%>
		</div>
	</form>
<% 	}%>
	<script>
		function charcountupdate(str) {
			var lng = str.length;
			document.getElementById("charcount").innerHTML = lng + ' letras escritas de 1500';
		}
	</script>
</div>