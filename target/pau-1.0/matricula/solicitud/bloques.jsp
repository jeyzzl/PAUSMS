<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.CargaBloque"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">	
	function Grabar(){	
		document.forma.submit();
	}
</script>
<%
	String cargaId 					= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
	String bloques					= (String) session.getAttribute("bloques");
	String nombreCarga				= (String)request.getAttribute("nombreCarga");
	List<CargaBloque> lisBloques	= (List<CargaBloque>) request.getAttribute("lisBloques");	
%>
<head></head>	
<body>
<div class="container-fluid">
	<h2>Select Blocks <small class="text-muted fs-4"> ( Load:<%=cargaId%> - <%=nombreCarga%> )</small></h2>
	<form id="forma" name="forma" action="subirBloques" method="post">
	<input type="hidden" name="CargaId" value="<%=cargaId%>">
	<div class="alert alert-info">
		<a class="btn btn-primary" href="listado?CargaId=<%=cargaId%>"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
		<input class="btn btn-primary" type="submit" value="Save" onclick="javascript:Grabar();"/>
	</div>	
	<table style="width:50%" class="table table-sm table-bordered">
		<tr>
			<th width="20%">
				<a onclick="jQuery('.checkboxBloques').prop('checked',true)" class="badge rounded-pill bg-info text-dark text-decoration-none"><spring:message code='aca.Todos'/></a>&nbsp;
				<a onclick="jQuery('.checkboxBloques').prop('checked', false)" class="badge rounded-pill bg-warning text-dark text-decoration-none"><spring:message code='aca.Ninguno'/></a>	
			</th>
			<th><spring:message code="aca.Clave"/></th>
			<th><spring:message code="aca.Bloque"/></th>
			<th>Start Date</th>
			<th>End Date</th>
		</tr>	
<%-- 		<tr><td colspan="4"><b><spring:message code="aca.Seleccionar"/>:</b></td></tr>		 --%>
<%

	String bloqueSelected = "";
	for(CargaBloque bloque : lisBloques){
		if (bloques.contains(bloque.getBloqueId()))
			bloqueSelected = "checked";
		else
			bloqueSelected = "";	
%>
		<tr>
			<td class="text-center">
				<input class="checkboxBloques" type="checkbox" id="<%=bloque.getBloqueId()%>" name="<%=bloque.getBloqueId()%>" value="S"<%=bloqueSelected%>/>
			</td>
			<td class="text-center">			
				<%=bloque.getBloqueId()%>																			
			</td>
			<td>			
				<%=bloque.getNombreBloque()%>																		
			</td>		
			<td>			
				<%=bloque.getFInicio()%>																		
			</td>		
			<td>	
				<%=bloque.getFFinal()%>																		
			</td>			
		</tr>		
<%	} %>
	</table>	
	<div class="alert alert-info">
		<input class="btn btn-primary" type="submit" value="Save" onclick="Mostrar();"/>
	</div>	
	</form>
  </div>
</body>