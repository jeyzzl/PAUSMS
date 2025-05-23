<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>

<%@page import="aca.investiga.spring.InvProyecto"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

<%
	String codigo			= (String) request.getAttribute("codigo");	
	String nombreUsuario	= (String) request.getAttribute("nombreUsuario");	
	
	List<InvProyecto> lisProyectos			= (List<InvProyecto>) request.getAttribute("lisProyectos");	
	
	HashMap<String,String> mapArchivoLight		= (HashMap<String, String>) request.getAttribute("mapArchivoLight");	
	HashMap<String, String> mapComentarios		= (HashMap<String, String>) request.getAttribute("mapComentarios");	
	HashMap<String, CatCarrera> mapaCarreras	= (HashMap<String, CatCarrera>) request.getAttribute("mapaCarreras");	

%>
<!DOCTYPE html>
<html lang="es">
<head>
<style>		
div.scroll_vertical::-webkit-scrollbar-track
{
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
	border-radius: 10px;
	background-color: #F5F5F5;
}

div.scroll_vertical::-webkit-scrollbar
{
	width: 12px;
	background-color: #F5F5F5;
}

div.scroll_vertical::-webkit-scrollbar-thumb
{
	border-radius: 10px;
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
	background-color: #555;
}
	
div.scroll_vertical {
	height: 85px;
	overflow: auto;
	padding: 8px;
}

.zoom-image-hover
{
  max-width:400px;
  margin: auto;
}
.zoom-image-hover img{
  width:100%;
  box-shadow: 0 0 8px #000;
  transition:all 1s;
  transform: scale(0.9);
}

.zoom-image-hover a:hover img{
  box-shadow: 0 0 40px #000;
  transform: scale(4.5);
}

</style>
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
	<div class="container-fluid">
	<h2>Listado de proyectos</h2>				
		<div class="alert alert-info">
			Usuario: [ <%=codigo%> ] &nbsp; <%=nombreUsuario%> &nbsp; <a href="accion?Accion=0" class="btn btn-primary"><i class="fas fa-plus"></i> Agregar</a>
			<a href="https://drive.google.com/file/d/0BzG4tjyKDs-dRlF3NHRGWUxJVlE/view" target="blank" style="margin-left:10px;" class="btn btn-info pull-right" data-toggle="tooltip" title="Descargar"><i class="fas fa-download"></i> Informe de actividades</a> &nbsp; &nbsp;
		</div>
		<div class="alert alert-alert">
			<h4>Después de capturar tu proyecto de investigación es necesario entregar de forma física una carta de sometimiento al Comité de Investigación Institucional (CII) y 
			al Comité de Ética en Investigación (CEI) en la oficinas de la DPI.<br>
			<small class="text-muted">*Puedes encontrar los formatos en:  VRA/ investigación/ proyectos empleado/ 
			agregar o editar/ documentos. A continuación te mostramos el proceso completo para registrar tus proyectos.</small></h4>
		</div>	
<!-- 		<div class="zoom-image-hover"> -->
<!-- 		  <a href=""><img alt="Proceso de registro" src="../../imagenes/ProcesoRegistro.png" style="width:300px;height:120px;"><br><br></a> -->
<!-- 		</div> -->
	<table class="table table-sm table-bordered table-responsive">	
		<tr>				
			<th width="8%">Opción</th>
			<th width="4%">Referencia</th>
			<th width="18%">Proyecto/Protocolo</th>
			<th width="6%"><spring:message code="aca.Carrera"/></th>
			<th width="10%"><spring:message code="aca.Tipo"/> Doc.</th>
			<th width="10%"><spring:message code="aca.Tipo"/> Inv.</th>
			<th width="8%">Línea</th>
			<th width="5%">Fecha Ini.</th>
			<th width="5%">Fecha Fin.</th>
			<th width="27%">Resumen</th>
			
			<th width="4%">Doc. Ext.</th>
			<th width="5%"><spring:message code="aca.Estado"/></th>
		</tr>
<%
	for (InvProyecto proyecto : lisProyectos){
		
		String nombreTipo = " ";
		if(proyecto.getTipo().equals("1")){
			nombreTipo = "Académico-científica";
		}else if(proyecto.getTipo().equals("2")){
			nombreTipo = "Investigación institucional";
		}else if(proyecto.getTipo().equals("3")){
			nombreTipo = "Investigación educativa";
		}else{
			nombreTipo = "Desarrollo de habilidades de investigación";
		}
		
		String nombreCarrera = "";
		if(mapaCarreras.containsKey(proyecto.getCarreraId())){
			nombreCarrera = mapaCarreras.get(proyecto.getCarreraId()).getNombreCorto();
		}
		
		boolean tienePdf 		= false;	
		boolean tienePdfInf1	= false;
		boolean tienePdfInf2	= false;
		
		// Verifica si existe la imagen	
		String dirArchivo = application.getRealPath("/investigacion/proyecto/archivos/"+proyecto.getProyectoId()+".pdf");
		java.io.File archivo = new java.io.File(dirArchivo);
		if (archivo.exists()){
			tienePdf = true;
		}
				
		// Verifica si existe la imagen	inf 1
		dirArchivo = application.getRealPath("/investigacion/proyecto/archivos/info1/"+proyecto.getProyectoId()+"a.pdf");
		archivo = new java.io.File(dirArchivo);
		if (archivo.exists()){
			tienePdfInf1 = true;
		}
				
		// Verifica si existe la imagen	inf 2
		dirArchivo = application.getRealPath("/investigacion/proyecto/archivos/"+proyecto.getProyectoId()+"b.pdf");
		archivo = new java.io.File(dirArchivo);
		if (archivo.exists()){
			tienePdfInf2 = true;
		}
		
		String nombreEstado = "-";
		if (proyecto.getEstado().equals("0")) nombreEstado = "Capturado";
		if (proyecto.getEstado().equals("1")) nombreEstado = "Comité Inv.";
		if (proyecto.getEstado().equals("2")) nombreEstado = "Comité Etica.";
		if (proyecto.getEstado().equals("3")) nombreEstado = "DPI";
		if (proyecto.getEstado().equals("4")) nombreEstado = "DPI-Registrado";
		if (proyecto.getEstado().equals("5")) nombreEstado = "Capturado";
		
		String docExt = "No";
		if(mapArchivoLight.containsKey(proyecto.getProyectoId()+"8")){
			docExt = mapArchivoLight.get(proyecto.getProyectoId()+"8");
		}
		
%>		
		<tr>			
			<td>
<% 			if (proyecto.getEstado().equals("0")){%>				
				<a href="accion?Accion=3&proyectoId=<%=proyecto.getProyectoId() %>" class="btn btn-info btn-sm" title="Editar"><i class="fas fa-pencil-alt"></i></a>&nbsp;
<% 			}
 			if (!proyecto.getEstado().equals("0")){%>
				<a onclick="javascript:eliminar('<%=proyecto.getProyectoId()%>');" class="btn btn-danger btn-sm" title="Eliminar"><i class="fas fa-trash-alt"></i></a>			
<% 				
			}else{
				if (proyecto.getEstado().equals("0")){
%>				
				<a onclick="javascript:enviar('<%=proyecto.getProyectoId()%>');" class="btn btn-primary btn-sm" title="Enviar"><i class="fas fa-arrow-right"></i></a>
<%			
				}
			}
			
		if(mapComentarios.containsKey(proyecto.getProyectoId())){
			if(Integer.parseInt(mapComentarios.get(proyecto.getProyectoId()))>= 1){
%>				
				<a href="comentario?ProyectoId=<%= proyecto.getProyectoId() %>" class="btn btn-primary btn-sm" title="Comentario"><i class="fas fa-comment-alt"></i></a>&nbsp;
<% 			}
		}else{  	%>
						
				<a href="comentario?ProyectoId=<%= proyecto.getProyectoId() %>" class="btn btn-info btn-sm" title="Comentario"><i class="fas fa-comment-alt"></i></a>&nbsp;
<%		} %>
				<a href="bitacora?ProyectoId=<%= proyecto.getProyectoId() %>" class="btn btn-warning btn-sm" title="Bitacora"><i class="fas fa-book"></i></a>&nbsp;<br>
				<%
		if (tienePdfInf1) {
%>
			<a onclick="javascript:enviar('<%=proyecto.getProyectoId()%>');" class="btn btn-primary btn-sm" title="Enviar"><i class="fas fa-arrow-right"></i></a>
			<p style="color:red;"><b>*Recuerde enviar el informe</b></p>
<%		
		}
%>
			</td>			
			<td><%= proyecto.getProyectoId() %></td>
			<td><%= proyecto.getProyectoNombre() %></td>
			<td><%= nombreCarrera%></td>
			<td><%= proyecto.getTipoDocumento().equals("1")?"Proyecto":"Protocolo" %></td>
			<td><%= nombreTipo %></td>
			<td><%= proyecto.getLinea() %></td>
			<td><%= proyecto.getFechaInicio() %></td>
			<td><%= proyecto.getFechaFinal() %></td>
			<td><div class="scroll_vertical"><%= proyecto.getResumen() %></div></td>
			<td>			  
			
			<%-- <a class="btn btn-danger" onclick="javascript:borrar('<%=proyectos.getProyectoId()%>');" title="Borrar propuesta"><i class="glyphicon glyphicon-trash"></i></a> --%>

<%		
		if (nombreEstado != "Capturado") {
			if (!tienePdfInf1){
%>
				<a onclick="javascript:subir1('<%=proyecto.getProyectoId()%>');" class="btn btn-info btn-sm" style="margin-top: 4px;" title="Subir"><i class="icon-arrow-up icon-white"></i>Informe 1</a>			  
<%
			}else if (tienePdfInf1){
%>		
				<div>
				<a class="btn btn-sm btn-danger" onclick="javascript:borrarInfo1('<%=proyecto.getProyectoId()%>');" title="Borrar info1"><i class="fas fa-trash-alt icon-white"></i></a>
<%			
				out.print("&nbsp; <a href='../proyecto/archivos/info1/"+proyecto.getProyectoId()+"a.pdf' target='blank' title='Descargar info1'><img src='../../imagenes/pdf.png' height='25px'/></a>");
			}
%>
				</div>
<%
					
			if (!tienePdfInf2){
%>
				<a onclick="javascript:subir2('<%=proyecto.getProyectoId()%>');" class="btn btn-info btn-sm" style="margin-top: 4px;" title="Subir"><i class="icon-arrow-up icon-white"></i>Informe 2</a>				  
<%
			}else if (tienePdfInf2){
%>		
				<a class="btn btn-danger" onclick="javascript:borrarInfo2('<%=proyecto.getProyectoId()%>');" title="Borrar"><i class="fas fa-trash-alt icon-white"></i></a>
<%			
				out.print("&nbsp; <a href='descargar?Ruta="+dirArchivo+"&NombreArchivo="+proyecto.getProyectoId()+"b.pdf' title='Descargar'><img src='../../imagenes/pdf.png' height='25px'/></a>");
			}
		} 
%>				<br><br>
<%-- 				<a href="view?proyectoId=<%=proyecto.getProyectoId()%>" class="btn btn-primary">Ver doc</a> --%>
				<%=docExt%>
			</td>
			<td><%= nombreEstado %></td>
		</tr>
<%
	} 
%>
	</table>
	</div>	
</body>
<script>
	function eliminar(proyectoId){
		if(confirm("¿Estás seguro que deseas eliminar este registro?")){
			location.href = "accion?Accion=2&proyectoId="+proyectoId;
		}
	}
	
	function enviar(proyectoId){
		if(confirm("¡Al enviar el proyecto ya no podrás hacer modificaciones! ¿Deseas continuar?")){
			location.href = "borrar?Accion=2&ProyectoId="+proyectoId;
		}
	}
	
	function borrar(proyectoId){
		if( confirm("¿Estas seguro de eliminar la propuesta?") ){
			location.href="borrar?Accion=1&ProyectoId="+proyectoId;
		}
	}
	
	function borrarInfo1(proyectoId){
		if( confirm("¿Estas seguro de eliminar el informe?") ){
			location.href="borrarInfo1?Accion=1&ProyectoId="+proyectoId;
		}
	}
	
	function borrarInfo2(proyectoId){
		if( confirm("¿Estas seguro de eliminar el documento?") ){
			location.href="borrarInfo2?Accion=1&ProyectoId="+proyectoId;
		}
	}
	
	function subir(proyectoId){
		location.href="subir?ProyectoId="+proyectoId;
	
	}
	
	function subir1(proyectoId){
		location.href="subirInfo1?ProyectoId="+proyectoId+"a";

	}
	
	function subir2(proyectoId){
		location.href="subirInfo2?ProyectoId="+proyectoId;

	}	
</script>
</html>
