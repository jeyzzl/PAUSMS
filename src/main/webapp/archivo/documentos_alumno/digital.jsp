<%@ page import="java.io.*" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.archivo.spring.ArchDocAlum"%>
<%@ page import="aca.pg.archivo.spring.PosArchDocAlum"%>
<%@ page import="aca.archivo.spring.ArchDocumentos"%>
<%@ page import="aca.archivo.spring.ArchStatus"%>
<%@ page import="aca.alumno.spring.AlumPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String matricula 			= (String) session.getAttribute("codigoAlumno");
	String codigoUsuario 		= (String) session.getAttribute("codigoPersonal");	
	String documentoId			= request.getParameter("DocumentoId")==null?"0":request.getParameter("DocumentoId");
	String hoja					= request.getParameter("Hoja")==null?"0":request.getParameter("Hoja");	
	String usuario				= request.getParameter("usuario");		
	String fecha				= "";	
	
	String alumnoNombre 		= (String) request.getAttribute("alumnoNombre");
	String documentoNombre 		= (String) request.getAttribute("documentoNombre");
	AlumPlan alumPlan			= (AlumPlan) request.getAttribute("alumPlan");
	PosArchDocAlum imagenDoc	= (PosArchDocAlum) request.getAttribute("imagenDoc");
	
	List<ArchDocAlum> lisDocumentos 		= (List<ArchDocAlum>) request.getAttribute("lisDocumentos");
	List<PosArchDocAlum> lisImagenes 		= (List<PosArchDocAlum>) request.getAttribute("lisImagenes");
	HashMap<String,String> mapaDocumentos 	= (HashMap<String,String>) request.getAttribute("mapaDocumentos");
%>
<STYLE TYPE="text/css">
	.tabbox
	{
		background: #eeeeee;
	}
</STYLE>
<SCRIPT>
	function openwindow(open){
		window.open(open,"Popup","toolbar=0, location=0, directories=0, status=0, menubar=0, scrollbars=1, resizable=1, width=800, height=600, top=0, left=0")
	}
	
	function eliminar(matricula,documento,hoja){
		if (confirm("This digitized document will be deleted. Do you want to continue?")){
			location.href= "borrarDocumento?matricula="+matricula+"&documento="+documento+"&hoja="+hoja;
		}
	}
</SCRIPT>
<body>
<div class="container-fluid">
	<h1></h1>
	<div class="alert alert-info d-flex align-items-center">	
		<h3> <a href="documentos" class="btn btn-primary btn-sm"><i class="fas fa-arrow-left"></i></a> &nbsp; Digitized documents <small class="text-muted fs-6">( <%=matricula%> - <%=alumnoNombre%> - <%=alumPlan.getPlanId()%> )</small></h3>
	</div>	
	<table class="table table-sm table-bordered">
	<tr>
	<td class="center"><h3>Document</h3></td>
	<td class="center">
		<h3>Image<small class="text-muted fs-6">( <%=documentoNombre%> Page:<%=hoja%> [<%=imagenDoc.getOrigen().equals("O")?"Original":"Copy"%>] Date:<%=imagenDoc.getFupdate()%>)</small> &nbsp; <i class="fas fa-times-circle" title="Delete" onclick="eliminar('<%=matricula%>','<%=documentoId%>','<%=hoja%>');" style="color:red;"></i>
		</h3>
	</td>
	</tr>
	<tr>
	<td width="30%" valign="top">	
		<table class="table table-sm" style="width:100%">
		<tr>			
			<th width="5%">&nbsp;</th>
			<th width="5%">#</th>
			<th width="65%">File</th>
			<th width="5%">Pdf</th>
			<th width="20%">Pages</th>		
		</tr>
<%
	int row = 0;
	for (ArchDocAlum doc : lisDocumentos){
		row++;
		String nombre = "-";
		if (mapaDocumentos.containsKey(doc.getIdDocumento())){
			nombre = mapaDocumentos.get(doc.getIdDocumento());
		}
		
		String iconoDocumento = "<i class='fas fa-smile' style='color:green'></i>";
		if(doc.getIncorrecto().equals("S")){
			iconoDocumento = "<i class='fas fa-frown' style='color:orange'></i>";
		}
%>
		<tr>
			<td>
<% 				if(codigoUsuario.equals("9801097") || codigoUsuario.equals("9800400") || codigoUsuario.equals("9801052") || codigoUsuario.equals("9800308")
				|| codigoUsuario.equals("9800525") || codigoUsuario.equals("9801124") || codigoUsuario.equals("9800018") || codigoUsuario.equals("1040207")){%>
					<a href="digitalIncorrecto?DocumentoId=<%=doc.getIdDocumento()%>"><%=iconoDocumento%></a>
<% 				}%>
			</td>
			<td><%=row%></td>
			<td><span style="font-size:11pt;"><%=nombre%></span></td>
			<td><a href="documentosToPdf?DocumentoId=<%=doc.getIdDocumento()%>"><i class="fas fa-file-pdf"></i></a></td>
			<td>
<%
		for (PosArchDocAlum imagen : lisImagenes){
			if (imagen.getIddocumento().equals(doc.getIdDocumento())){
				out.print("<a href='digital?DocumentoId="+imagen.getIddocumento()+"&Hoja="+imagen.getHoja()+"'><span class='badge bg-dark'>"+imagen.getHoja()+"</span></a>");
			}
		}			
%>			
			</td>
		</tr>
<%		
	}
%>	
		</table>
	</td>
	<td width="70%" valign="top">
<%
	if (documentoId.equals("0") && hoja.equals("0")){
		out.print("<img name='imagen' id='imagen' src='../../imagenes/imagen.png' width='200'>");
	}else{
		out.print("<img name='imagen' id='imagen' src='../../fotoArchivo?matricula="+matricula+"&documento="+documentoId+"&hoja="+hoja+"' width='777' nosave>");
	}
%>		
	</td>		
	</table>
</div>
</body>