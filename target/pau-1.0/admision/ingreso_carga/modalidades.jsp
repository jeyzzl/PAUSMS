<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>

<%@ include file= "../../headPortal.jsp"%>
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
	String codigoPersonal		= (String) request.getAttribute("codigoPersonal");
	String cargas				= (String) request.getAttribute("cargas");
	String modalidades			= (String) request.getAttribute("modalidades");
	String accion 				= (String) request.getAttribute("accion");
	String mensaje 				= (String) request.getAttribute("mensaje");
	
	List<CatModalidad> lisModalidad		= (List<CatModalidad>) request.getAttribute("lisModalidad");	
	HashMap<String,String> mapa 		= (HashMap<String, String>) request.getAttribute("mapa");
%>
<head></head>	
<body>
<div class="container-fluid">
	<h2>Elegir Modalidades<small class="text-muted fs-4"> ( Cargas:<%=cargas.replace("'","")%> )</small></h2>
	<form id="forma" name="forma" action="modalidades" method="post">
	<input name="Accion" type="hidden">
	<div class="alert alert-info">
		<a class="btn btn-primary" href="ingreso"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
		<input class="btn btn-primary" type="submit" value="Grabar" onclick="Mostrar();"/>&nbsp;
		<%=!mensaje.equals("-")?mensaje:""%>
	</div>	
	<table style="width:50%" class="table table-condensed">
	<tr>
		<th width="20%">Elegir
			<a onclick="jQuery('.checkboxModalidad').prop('checked',true)" class="badge bg-success"><spring:message code='aca.Todos'/></a>&nbsp;
			<a onclick="jQuery('.checkboxModalidad').prop('checked', false)" class="badge bg-warning"><spring:message code='aca.Ninguno'/></a>	
		</th>
		<th><spring:message code="aca.Clave"/></th>
		<th><spring:message code="aca.Modalidad"/></th>
		<th class="text-end"><spring:message code="aca.Inscritos"/></th>
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
		
%>
	<tr>
		<td>
			<input class="checkboxModalidad" type="checkbox" id="<%=modalidad.getModalidadId() %>" name="<%=modalidad.getModalidadId() %>" value="S"<%=modalidadSelected%>/>
		</td>
		<td>			
			<b><%=modalidad.getModalidadId() %></b>																			
		</td>
		<td>			
			<b><%=modalidad.getNombreModalidad() %></b>																			
		</td>			
		<td class="text-end">			
			<b><%=totAlumnos%></b>
		</td>
	</tr>		
<%	} %>
	<tr>
		<td colspan="4">
			<input class="btn btn-primary" type="submit" value="Grabar" onclick="Mostrar();"/>			
		</td>
	</tr>
	</table>
	
	</form>
  </div>
</body>