<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="InvProyectoU" scope="page" class="aca.investiga.InvProyectoUtil"/>
<jsp:useBean id="Usuario" scope="page" class="aca.vista.Usuarios"/>
<%
	String codigo			= (String) session.getAttribute("codigoPersonal");	
	String nombreUsuario	= " ";	
	
	Usuario.setCodigoPersonal(codigo);
	if (Usuario.existeReg(conEnoc)){
		Usuario.mapeaRegId(conEnoc, codigo);
		nombreUsuario	= Usuario.getNombre()+" "+Usuario.getApellidoPaterno()+" "+Usuario.getApellidoMaterno();
	}else{
		nombreUsuario = "¡ No existe !";
	}
	
	String carrera			=  aca.investiga.InvReferenteUtil.getCarrera(conEnoc, codigo);
	
	// Lista de proyectos de investigación 
	ArrayList<aca.investiga.InvProyecto> lisProyectos = InvProyectoU.getListProyectosCarrera(conEnoc, carrera, codigo, "ORDER BY PROYECTO_ID");
%>
<html>
<head>
<body>
	<div class="container-fluid">
		<h2>Listado de proyectos<small class="text-muted fs-4">&nbsp;( Usuario: <%=codigo%> &nbsp; <%=nombreUsuario%> )</small></h2>
		<div class="alert alert-info">	
			<input type="text" class="input-medium search-query form-control" placeholder="Buscar" id="buscar" style="width:200px;">
		</div>	
		<table id="table" class="table table-sm table-bordered">  	
  		<thead class="table-info">	
			<tr>
				<th width="2%">#</th>
				<th width="4%">Referencia</th>
				<th width="18%">Proyecto/Protocolo</th>
				<th width="6%"><spring:message code="aca.Carrera"/></th>
				<th width="7%"><spring:message code="aca.Nombre"/></th>
				<th width="10%"><spring:message code="aca.Tipo"/></th>
				<th width="8%">Linea</th>
				<th width="5%">Fecha Ini.</th>
				<th width="5%">Fecha Fin.</th>
				<th width="20%">Resumen</th>
				<th width="4%"><spring:message code="aca.Documento"/></th>
				<th width="5%"><spring:message code="aca.Estado"/></th>
			</tr>
		</thead>
<%
	int row = 0; 
	for (int i=0; i<lisProyectos.size();i++){
		aca.investiga.InvProyecto proyectos = (aca.investiga.InvProyecto) lisProyectos.get(i);
		row++;
		String nombreTipo = " ";
		if(proyectos.getTipo().equals("1")){
			nombreTipo = "Académico-científica";
		}else if(proyectos.getTipo().equals("2")){
			nombreTipo = "Investigación institucional";
		}else if(proyectos.getTipo().equals("3")){
			nombreTipo = "Investigación educativa";
		}else{
			nombreTipo = "Desarrollo de habilidades de investigación";
		}
		
		boolean tienePdf 		= false;		
		// Verifica si existe la imagen	
		String dirArchivo = application.getRealPath("/investigacion/proyecto/archivos/"+proyectos.getProyectoId()+".pdf");
		java.io.File archivo = new java.io.File(dirArchivo);
		if (archivo.exists()){
			tienePdf = true;
		}
		
		String nombreEstado = "-";
		if (proyectos.getEstado().equals("0")) nombreEstado = "Capturado";
		if (proyectos.getEstado().equals("1")) nombreEstado = "Comité Inv.";
		if (proyectos.getEstado().equals("2")) nombreEstado = "Comité Etica.";
		if (proyectos.getEstado().equals("3")) nombreEstado = "DPI";
		if (proyectos.getEstado().equals("4")) nombreEstado = "DPI-Registrado";
	
%>		
		<tr>			
			<td><%=row%></td>
			<td><%= proyectos.getProyectoId() %></td>
			<td><%= proyectos.getProyectoNombre() %></td>
			<td><%= aca.catalogo.CatCarreraUtil.getNombreCorto(conEnoc, proyectos.getCarreraId())%></td>
			<td><%= aca.vista.MaestrosUtil.getNombreCorto(conEnoc, proyectos.getCodigoPersonal(), "NOMBRE")  %></td>
			<td><%= nombreTipo %></td>
			<td><%= proyectos.getLinea() %></td>
			<td><%= proyectos.getFechaInicio() %></td>
			<td><%= proyectos.getFechaFinal() %></td>
			<td><%= proyectos.getResumen() %></td>
			<td>			  
<%			
			if (tienePdf){
				out.print("&nbsp; <a href='../proyecto/descargar?Ruta="+dirArchivo+"&NombreArchivo="+proyectos.getProyectoId()+".pdf'><img src='../../imagenes/pdf.png' height='25px'/></a>");
			}
%>			  
			</td>
			<td><%= nombreEstado %></td>
		</tr>
<%	} %>
		</table>
	</div>	
</body>
</html>
<script src="../../js/jquery-1.9.1.min.js"></script>
<script src="../../js/search.js"></script>	
<script>	
	jQuery('#buscar').focus().search({table:jQuery("#table")});
	
	function cancelar(proyectoId){
		if( confirm("¡Esta acción cancela la autorización! ¿Deseas continuar?") ){
			location.href="accion?Accion=1&ProyectoId="+proyectoId;
		}
	}
	
	function autorizar(proyectoId){
		if(confirm("¡Esta acción autoriza el proyecto! ¿Deseas continuar?")){
			location.href = "accion?Accion=2&ProyectoId="+proyectoId;
		}
	}
</script>
<%@ include file= "../../cierra_enoc.jsp" %>