<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>
<%@page import="aca.vista.spring.Estadistica"%>

<%@ include file= "../../headPortal.jsp" %>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	
	function Mostrar(){					
		document.forma.Accion.value="1";
		document.forma.submit();
	}

</script>
<%
	String codigoPersonal				= (String) request.getAttribute("codigoPersonal");
	String cargas						= (String) request.getAttribute("cargas");
	String modalidades					= (String) request.getAttribute("modalidades");
	String accion 						= (String) request.getAttribute("accion");
	
	List<CatModalidad> lisModalidad		= (List<CatModalidad>) request.getAttribute("lisModalidad");
	
	HashMap<String,String> mapa = (HashMap<String,String>) request.getAttribute("mapa");
%>
<head></head>	
<body>
<div class="container-fluid">
	<h2>Select Modalities</h2>
	<form id="forma" name="forma" action="modalidades" method="post">
	<input name="Accion" type="hidden">
	<div class="alert alert-info">
		<a class="btn btn-primary btn-small" href="bestadistica"><spring:message code="aca.Regresar"/></a>&nbsp;
		<input class="btn btn-primary" type="submit" value="Save" onclick="Mostrar();"/>
	</div>	
	<table style="width:50%" class="table table-sm table-striped">
		<tr class="table-info">
			<th>
				Select
				<a onclick="jQuery('.checkboxModalidad').prop('checked',true)" class="badge bg-success"><spring:message code='aca.Todos'/></a>&nbsp;
				<a onclick="jQuery('.checkboxModalidad').prop('checked', false)" class="badge bg-warning"><spring:message code='aca.Ninguno'/></a>	
			</th>
			<th><spring:message code="aca.Clave"/></th>
			<th><spring:message code="aca.Modalidad"/></th>
			<th><spring:message code="aca.Inscritos"/></th>
		</tr>			
<%
	String modalidadSelected = "";
	for(CatModalidad modalidad : lisModalidad){
		if (modalidades.contains("'" + modalidad.getModalidadId() + "'"))
			modalidadSelected = "checked";
		else
			modalidadSelected = "";
		
		String totAlumnos = "0";
		if (mapa.containsKey(modalidad.getModalidadId())){
			totAlumnos = mapa.get(modalidad.getModalidadId());
		}
		String etiqueta = totAlumnos; 
		if (Integer.parseInt(totAlumnos) > 0 ){
			etiqueta = "<span class='badge bg-success'>"+totAlumnos+"</span>";
		}		
%>
		<tr>
			<td>
				<input class="checkboxModalidad" type="checkbox" id="<%=modalidad.getModalidadId() %>" name="<%=modalidad.getModalidadId() %>" value="S" <%=modalidadSelected%>/>
			</td>
			<td>			
				<%=modalidad.getModalidadId() %>																					
			</td>
			<td>			
				<b><%=modalidad.getNombreModalidad() %></b>																					
			</td>
			<td>			
				<b><%=etiqueta%></b>
			</td>			
		</tr>		
<%	} %>
		<tr><td colspan="4"><input class="btn btn-primary" type="submit" value="Save" onclick="Mostrar();"/></td></tr>
	</table>	
	</form>
</div>
</body>