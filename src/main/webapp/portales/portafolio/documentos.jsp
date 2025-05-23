<%@page import="java.util.List"%>
<%@page import="java.io.File"%>
<%@page import="java.util.HashMap"%>

<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="PorEmpDoctoU" scope="page" class="aca.portafolio.PorEmpDoctoUtil" />
<%@ include file="menuPortal.jsp"%>

<%
	String codigoPersonal 			= (String)request.getAttribute("codigoPersonal");
	String periodoId				= (String)request.getAttribute("periodoId");
	String fechaHoy 				= (String)request.getAttribute("fechaHoy");
	String origen 					= (String)request.getAttribute("origen");
	
	List<aca.portafolio.spring.PorEmpDocto> lisDocumentos	= (List<aca.portafolio.spring.PorEmpDocto>)request.getAttribute("lisDocumentos");
	
	HashMap<String, aca.portafolio.spring.PorEmpArchivo> mapArchivos = (HashMap<String, aca.portafolio.spring.PorEmpArchivo>)request.getAttribute("mapArchivos");
	
	List<String> lsArchivos = new ArrayList<String>();
	
	File folder = new File(application.getRealPath("/WEB-INF/portafolio/"));
	File[] fileList = folder.listFiles();
	for (int i = 0; i < fileList.length; i++) {
		if (fileList[i].isFile()) {
			if (fileList[i].getName().startsWith(codigoPersonal)) {
				lsArchivos.add( fileList[i].getName());
			}
		}
	}
	
%>

<div class="container-fluid">

	<h2>
		Documentos <small> (<%=periodoId%>)
		</small>
	</h2>

	<hr />
	<div style="margin-left: 20px; float: left; background: white;"
		class="alert alert-info ">

		<table style="width:100%" class="table">
			<tr>
				<th colspan="7"><font size="3"><spring:message code='aca.ListaDeDocumentos' /></font></th>
				<th colspan="1">
<% 				if(origen.equals("")){ %>
					<a class="btn btn-info" href="agregar?codigoPersonal=<%=codigoPersonal%>&Accion=0"><i class="icon-white icon-plus"></i> Agregar</a>
<% 				}else{ %>
					<a class="btn btn-info" href="<%= origen %>.jsp"><i class="icon-white icon-plus"></i> Agregar</a>
<% 				} %>
				</th>
			</tr>
			<tr>
				<th>#</th>
				<th><spring:message code="aca.Documento" /></th>
				<th><spring:message code="aca.Fecha" /></th>
				<th><spring:message code='aca.Usuario' /></th>
				<th>Img.1</th>
				<th>Img.2</th>
				<th>Img.3</th>
				<th>Archivo</th>
			</tr>
			<%	for(aca.portafolio.spring.PorEmpDocto documento : lisDocumentos){
				int cont = 1;
				String nombre 		=  aca.portafolio.PorDocumentoUtil.getdocumentoNombre(conEnoc, documento.getDocumentoId());
				String prefijo = aca.portafolio.PorDocumento.getArchivo(conEnoc, documento.getDocumentoId());
				
				String hoja1 = "btn-danger";
				String hoja2 = "btn-danger";
				String hoja3 = "btn-danger";
				String hoja0 = "btn-danger";
				
				if(mapArchivos.containsKey(codigoPersonal+documento.getDocumentoId()+"1")){
					hoja1="btn-primary";
				}
				if(mapArchivos.containsKey(codigoPersonal+documento.getDocumentoId()+"2")){
					hoja2="btn-primary";
				}
				if(mapArchivos.containsKey(codigoPersonal+documento.getDocumentoId()+"3")){
					hoja3="btn-primary";
				}
				if(mapArchivos.containsKey(codigoPersonal+documento.getDocumentoId()+"4")){
					hoja0="btn-primary";
				}
				
				
			%>
			<tr>
				<td><%=cont %></td>
				<td><%=nombre%></td>
				<td><%=fechaHoy%></td>
				<td><%=documento.getUsuario()%></td>
				<td>
					<a class="btn <%= hoja1 %>" href="subir?DocumentoId=<%=documento.getDocumentoId()%>&Folio=1"><i class="icon-picture icon-white"></i></a>
				<td>
					<a class="btn <%= hoja2 %>"	href="subir?DocumentoId=<%=documento.getDocumentoId()%>&Folio=2"><i class="icon-picture icon-white"></i></a>
				</td>
				<td>
					<a class="btn <%= hoja3 %>" href="subir?DocumentoId=<%=documento.getDocumentoId()%>&Folio=3"><i class="icon-picture icon-white"></i></a>
				</td>
				<td>
					<a class="btn <%= hoja0 %>" href="subir?DocumentoId=<%=documento.getDocumentoId()%>&Folio=4"><i class="icon-picture icon-white"></i></a>
				</td>
			</tr>
			<%				
					cont++;
				}
			%>
		</table>

	</div>

</div>
<script>
	jQuery('.documentos').addClass('active');
</script>
<%@ include file="../../cierra_enoc.jsp"%>