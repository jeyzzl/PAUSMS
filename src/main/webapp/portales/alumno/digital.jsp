<%@ page import="java.io.*" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.archivo.spring.ArchDocAlum"%>
<%@ page import="aca.pg.archivo.spring.PosArchDocAlum"%>
<%@ page import="aca.archivo.spring.ArchDocumentos"%>
<%@ page import="aca.archivo.spring.ArchStatus"%>
<%@ page import="aca.alumno.spring.AlumPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ include file="menu.jsp"%>
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
		if (confirm("This digitized document will be deleted. Are you sure?")){
			location.href= "borrarDocumento?matricula="+matricula+"&documento="+documento+"&hoja="+hoja;
		}
	}
</SCRIPT>
<body class="tabbox">
<div class="container-fluid">
	<h3> <a href="documentos" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a> &nbsp; Student Documents <small class="text-muted fs-4	">( <%=matricula%> - <%=alumnoNombre%> - <%=alumPlan.getPlanId()%> )</small></h3>
	<hr>
	<table class="table table-bordered">
	<tr>
		<td class="center" width="30%" ><h3>Documents</h3></td>
		<td class="center" width="70%">
			<h4>Images( <%=documentoNombre%> Sheet:<%=hoja%> <%=imagenDoc.getOrigen().equals("O")?"Original":"Copy"%> Date:<%=imagenDoc.getFupdate()%>)</h4>
		</td>
	</tr>
	<tr>
		<td width="30%" valign="top">	
			<table class="table table-condensed" style="width:100%">
			<tr>
				<th width="5%">#</th>
				<th width="70%">Document</th>
				<th width="25%">Sheets</th>		
			</tr>
<%
	int row = 0;
	for (ArchDocAlum doc : lisDocumentos){
		row++;
		String nombre = "-";
		if (mapaDocumentos.containsKey(doc.getIdDocumento())){
			nombre = mapaDocumentos.get(doc.getIdDocumento());
		}
		
		String color = "success";
		
		if(doc.getIncorrecto().equals("S")){
			color = "warning";
		}
%>
			<tr>
				<td><%=row%>&nbsp;
<% 				if(codigoUsuario.equals("9801097") || codigoUsuario.equals("9800400") || codigoUsuario.equals("9801052") || codigoUsuario.equals("9800308")
				|| codigoUsuario.equals("9800525") || codigoUsuario.equals("9801124") || codigoUsuario.equals("9800018") || codigoUsuario.equals("1040207")){%>
					<a class="btn btn-<%=color%> btn-sm" href="digitalIncorrecto?DocumentoId=<%=doc.getIdDocumento()%>"><i class="far fa-sad-tear"></i></a>
<% 				}%>
				</td>
				<td><%=nombre%></td>
				<td>
	<%
		for (PosArchDocAlum imagen : lisImagenes){
			if (imagen.getIddocumento().equals(doc.getIdDocumento())){
				out.print("<a href='digital?DocumentoId="+imagen.getIddocumento()+"&Hoja="+imagen.getHoja()+"'><span class='badge bg-pill  badge bg-dark' style='font-size:0.7rem;'>"+imagen.getHoja()+"</span></a>");
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
		out.print("<img style='width:100%;' class='img-responsive' name='imagen' id='imagen' src='../../imagenes/imagen.png' width='200'>");
	}else{
		out.print("<img style='width:100%;' class='img-responsive' name='imagen' id='imagen' src='../../fotoArchivo?matricula="+matricula+"&documento="+documentoId+"&hoja="+hoja+"' width='777' nosave>");
	}
%>		
		
		</td>		
	</table>
</div>
</body>