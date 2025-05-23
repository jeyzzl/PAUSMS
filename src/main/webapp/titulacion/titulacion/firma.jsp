<%@ page import= "java.util.ArrayList"%>
<%@ page import= "java.util.HashMap"%>
<%@page import="aca.tit.spring.TitFirma"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function Grabar() {
		document.frmTitulo.submit();
	}	
</script>

<%
	String codigoPersonal			= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno 			= (String) session.getAttribute("codigoAlumno");
	String nombreAlumno				= (String) request.getAttribute("nombreAlumno");
	String institucion				= request.getParameter("Institucion")==null?"UM":request.getParameter("Institucion");
	String folio					= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String planId					= (String) request.getAttribute("planId");
	
	ArrayList<TitFirma> lisFirmas	= (ArrayList<TitFirma>)request.getAttribute("lisFirmas");
	HashMap<String,String> mapaCer	= (HashMap<String,String>)request.getAttribute("mapaCertificados");
%>

<div class="container-fluid">
	<h2>Firmas Autorizadas<small class="text-muted fs-4"> ( <%=codigoAlumno%> - <%=nombreAlumno%> - <%=planId%> )</small></h2>
	<div class="alert alert-info">		
		<a class="btn btn-primary" href="titulacion"><i class="fas fa-arrow-left"></i></a>&nbsp;
	</div>		
	<table class="table table-sm table-bordered">  
	<thead class="table-info">
		<tr>			
			<th>&nbsp;</th>
			<th>#</th>
			<th>Clave</th>			
			<th>CURP</th>
			<th>Nombre</th>			
			<th>Cargo</th>
			<th>Certificado</th>
		</tr>
	</thead>
	<tbody>
<%
	int row=0;
	for (TitFirma firma : lisFirmas){
		row++;
		
		String tieneCer = "NO";
		if (mapaCer.containsKey(firma.getInstitucion()+firma.getCodigoPersonal()) ){
			tieneCer = "SI";
		}
%>	
		<tr>
			<td>
<% 	
		if(codigoPersonal.equals("9800308")){
%>
				<a href="subircer?CodigoPersonal=<%=firma.getCodigoPersonal()%>&Institucion=<%=institucion%>&Folio=<%=folio%>"><i class="fa fa-upload" ></i></a>
<%					
		} 
%>
			</td>
			<td><%=row%></td>
			<td><%=firma.getCodigoPersonal()%></td>
			<td><%=firma.getCurp()%></td>
			<td><%=firma.getPrimerApellido()%> <%=firma.getSegundoApellido()%> <%=firma.getNombre()%></td>
			<td><%=firma.getCargo()%></td>
			<td><%=tieneCer%></td>
		</tr>
<%
	}
%>	
	</tbody>
	</table>
</div>