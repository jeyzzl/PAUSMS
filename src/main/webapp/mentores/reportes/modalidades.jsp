<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.catalogo.spring.CatModalidad"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">	
	function Mostrar(){					
		document.forma.Accion.value="1";
		document.forma.submit();
	}
</script>
<%
	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");	
	String cargas				= (String)session.getAttribute("cargas") == null?"0":session.getAttribute("cargas").toString();
	String modalidades			= (String)session.getAttribute("modalidades") == null?"":session.getAttribute("modalidades").toString();
	String accion 				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	List<CatModalidad> lisModalidades		= (List<CatModalidad>)request.getAttribute("lisModalidades");
	HashMap<String,String> mapaAlumnos		= (HashMap<String,String>) request.getAttribute("mapaAlumnos");
%>
<head></head>	
<body>
<div class="container-fluid">
	<h2>Elegir Modalidades<small class="text-muted fs-4"> (Cargas:<%=cargas.replace("'","")%>)</small></h2>
	<form id="forma" name="forma" action="modalidades" method="post">
	<input name="Accion" type="hidden">
	<div class="alert alert-info">
		<a class="btn btn-primary btn-small" href="menu"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		<input class="btn btn-primary" type="submit" value="Grabar" onclick="Mostrar();"/>
	</div>	
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	
	<tr>
		<th width="20%">Elegir
			<a onclick="jQuery('.checkboxModalidad').prop('checked',true)" class="btn btn-primary"><spring:message code='aca.Todos'/></a>&nbsp;
			<a onclick="jQuery('.checkboxModalidad').prop('checked', false)" class="btn btn-primary"><spring:message code='aca.Ninguno'/></a>	
		</th>
		<th><spring:message code="aca.Clave"/></th>
		<th><spring:message code="aca.Modalidad"/></th>
		<th><spring:message code="aca.Inscritos"/></th>
	</tr>
	</thead>	
	<tr><td colspan="4"><b><spring:message code="aca.Seleccionar"/>:</b></td></tr>		
<%
	String modalidadSelected = "";
	for( CatModalidad modalidad : lisModalidades){
		if (modalidades.contains("'" + modalidad.getModalidadId() + "'"))
			modalidadSelected = "checked";
		else
			modalidadSelected = "";
		
		String totAlumnos = "0";
		if (mapaAlumnos.containsKey(modalidad.getModalidadId())){
			totAlumnos = mapaAlumnos.get(modalidad.getModalidadId());
		}
		String etiqueta = totAlumnos; 
		if (Integer.parseInt(totAlumnos) > 0 ){
			etiqueta = "<span class='label label-success'>"+totAlumnos+"</span>";
		}
%>
	<tr>
		<td>
			<input class="checkboxModalidad" type="checkbox" id="<%=modalidad.getModalidadId() %>" name="<%=modalidad.getModalidadId() %>" value="S" <%=modalidadSelected%>/>
		</td>
		<td><b><%=modalidad.getModalidadId()%></b></td>
		<td><b><%=modalidad.getNombreModalidad() %></b></td>
		<td><b><%=etiqueta%></b></td>			
	</tr>		
<%	} %>
	<tr><td colspan="4"><input class="btn btn-primary" type="submit" value="Grabar" onclick="Mostrar();"/></td></tr>
	</table>	
	</form>
</div>
</body>