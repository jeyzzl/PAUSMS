<%@ page import= "java.util.* "%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatNivel"%>
<%@page import="aca.bec.spring.BecTipoCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<script>
	function elegirTodos(){
		var elegir = jQuery("#elegir");
		if (elegir.html()=="Todos"){
			jQuery(".carrera").attr("checked",true);
			elegir.html("Ninguno");
		}else{
			jQuery(".carrera").attr("checked",false);
			elegir.html("Todos");
		}	
	}

	function modificarDepartamentos(ejercicioId, tipo){
		document.frmDepartamentos.submit();
	}
</script>
<%
	String ejercicioId 		= request.getParameter("EjercicioId")==null?"0":request.getParameter("EjercicioId");
	String tipo		   		= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");
	String facultadId		= request.getParameter("FacultadId")==null?"0":request.getParameter("FacultadId");
	String tipoNombre		= (String)request.getAttribute("tipoNombre");
	
	List<CatFacultad> lisFacultades				= (List<CatFacultad>)request.getAttribute("lisFacultades");
	List<CatCarrera> lisCarreras				= (List<CatCarrera>)request.getAttribute("lisCarreras");
	List<String> lisGrabadas					= (List<String>)request.getAttribute("lisGrabadas");
	HashMap<String,CatFacultad> mapaFacultades	= (HashMap<String,CatFacultad>)request.getAttribute("mapaFacultades");
	HashMap<String,CatNivel> mapaNiveles		= (HashMap<String,CatNivel>)request.getAttribute("mapaNiveles");
%>
<html>
<head>
<style>
	.empleados:hover{
		font-weight: bold;
	}
	body{
		background:white;
	}
</style>
<body>
<div class="container-fluid">
	<h2>Carreras<small class="text-muted fs-4">( Para <%=tipoNombre%>)</small></h2>
	<form name="frmFacultad" action="editarcarreras" method="post">
	<input type="hidden" name="EjercicioId" value="<%=ejercicioId%>">
	<input type="hidden" name="Tipo" value="<%=tipo%>">	
	<div class="alert alert-info d-flex align-items-center">	
		<a href="becas?ejercicioId=<%=ejercicioId%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
		&nbsp;
		<select name="FacultadId" class="form-select" style="width:350px;" onchange="document.frmFacultad.submit();">
			<option value="0" <%=facultadId.equals("0")?"selected":""%>>Grabadas</option>
			<option value="99" <%=facultadId.equals("99")?"selected":""%>>Todas</option>
<% 	for (CatFacultad facultad : lisFacultades){	%>
			<option value="<%=facultad.getFacultadId()%>" <%=facultadId.equals(facultad.getFacultadId())?"selected":""%>><%=facultad.getNombreFacultad()%></option>
<%	} %>			
		</select>		
	</div>			
	</form>
	<form name="frmCarreras" action="grabarcarreras" method="post">
	<input type="hidden" name="EjercicioId" value="<%=ejercicioId%>">
	<input type="hidden" name="Tipo" value="<%=tipo%>">
	<input type="hidden" name="FacultadId" value="<%=facultadId%>">
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
		<tr>
			<th width="10%" align="center"><a id="elegir" class="btn btn-info btn-sm" onclick="javascript:elegirTodos();" style="cursor:pointer; font-size:12px;">Todos</a></th>
			<th width="10%">Facultad</th>					
			<th width="10%">Carrera</th>
			<th width="55%"><spring:message code="aca.Nombre"/></th>
			<th width="15%">Nivel</th>
		</tr>
	</thead>
	<tbody>
				
<%	
	for(CatCarrera carrera : lisCarreras){
		String facultadNombre = "-";
		if (mapaFacultades.containsKey(carrera.getFacultadId())){
			facultadNombre = mapaFacultades.get(carrera.getFacultadId()).getNombreCorto();
		}
		String nivelNombre = "-";
		if (mapaNiveles.containsKey(carrera.getNivelId())){
			nivelNombre = mapaNiveles.get(carrera.getNivelId()).getNombreNivel();
		}
%>
		<tr>					
			<td style="text-align:center;"><input type="checkbox" class="carrera" id="<%=carrera.getCarreraId()%>" name="<%=carrera.getCarreraId()%>" value="S" <%if(lisGrabadas.contains(carrera.getCarreraId())){%>checked<%}%>/></td>
			<td><%=facultadNombre%></td>					
			<td><%=carrera.getCarreraId()%></td>
			<td><%=carrera.getNombreCarrera()%></td>
			<td><%=nivelNombre%></td>
		</tr>				
<% 	} %>		
	</tbody>
	</table>
	<div class="alert alert-info">
		<a href="javascript:document.frmCarreras.submit();" class="btn btn-primary">Grabar</a>
	</div>	
	</form>
</div>
</body>
</html>