<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
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
	
	String codigoPersonal 	= (String) request.getAttribute("codigoPersonal");
	String cargas 			= (String) request.getAttribute("cargas");
	String modalidades 		= (String) request.getAttribute("modalidades");
	String accion 			= (String) request.getAttribute("accion");
	String mensaje 			= "";

	List<CatModalidad> lisModalidad						= (List<CatModalidad>) request.getAttribute("lisModalidad");
	HashMap<String, String> mapaModalidadesEnCargas 	= (HashMap<String, String>) request.getAttribute("mapaModalidadesEnCargas");
	
	if (accion.equals("1")){
		
		modalidades = "";
		for(CatModalidad modalidad : lisModalidad){
			if(request.getParameter(modalidad.getModalidadId()) != null ){			
				if(modalidades.equals(""))
					modalidades = "'"+modalidad.getModalidadId()+"'";
				else
					modalidades += ",'"+modalidad.getModalidadId()+"'";						
			}
		}
		mensaje = "1";
		session.setAttribute("modalidades", modalidades);	
	}	
%>
<head></head>	
<body>
<div class="container-fluid">
	<h2>Select Modalities <small class="text-muted fs-4"> ( Loads:<%=cargas.replace("'","")%> )</small></h2>
	<form id="forma" name="forma" action="modalidades" method="post">
	<input name="Accion" type="hidden">
	<div class="alert alert-info">
		<a class="btn btn-primary btn-small" href="inscritos"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
		<input class="btn btn-primary" type="submit" value="Save" onclick="Mostrar();"/>
	</div>	
<% if(mensaje.equals("1")){%>
	<div class="alert alert-success">
		Saved
	</div>	
<% }%>
	<table class="table table-sm table-bordered table-striped" style="width:50%">
		<tr class="table-info">
			<th width="20%">Select
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
		if (modalidades.contains("'" + modalidad.getModalidadId() + "'")){
			modalidadSelected = "checked";
		}else{
			modalidadSelected = "";
		}
		String totAlumnos = "0";
		if (mapaModalidadesEnCargas.containsKey(modalidad.getModalidadId())){
			totAlumnos = mapaModalidadesEnCargas.get(modalidad.getModalidadId());
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
			<td>			
				<%=modalidad.getModalidadId() %>																					
			</td>
			<td>			
				<b><%=modalidad.getNombreModalidad() %></b>																					
			</td>
			<td class="text-end">			
				<b><%=etiqueta%></b>
			</td>		
		</tr>		
<%  } %>
		<tr><td colspan="4"><input class="btn btn-primary" type="submit" value="Save" onclick="Mostrar();"/></td></tr>
	</table>
	
	</form>
</div>
</body>