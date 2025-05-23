<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.hca.spring.HcaMaestro"%>
<%@ page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<head>
	<script type="text/javascript">
		function Grabar(){
			document.frmDocente.Accion.value="1";
			document.frmDocente.submit();
		}
		
		function Borrar(facultad, maestroId){
			if(confirm("Estas seguro de quitar el maestro: "+maestroId)==true){
				document.location.href="maestro?FacultadId="+facultad+"&Accion=2&MaestroId="+maestroId;
		  	}
		}	
	</script>
</head>
<%
	String codigoEmpleado	= (String) session.getAttribute("codigoEmpleado");
	String facultad 		= request.getParameter("FacultadId")==null?"0":request.getParameter("FacultadId");
	String empleadoNombre	= (String)request.getAttribute("empleadoNombre");
	String facultadNombre	= (String)request.getAttribute("facultadNombre");	
	String carreraId		= "X";	
	int cont=0;
	
	List<HcaMaestro> lisMaestros 				= (List<HcaMaestro>)request.getAttribute("lisMaestros");
	List<CatCarrera> lisCarreras 				= (List<CatCarrera>)request.getAttribute("lisCarreras");
	HashMap<String,String> mapaMaestros 		= (HashMap<String,String>)request.getAttribute("mapaMaestros"); 
	HashMap<String,CatCarrera> mapaCarreras 	= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
%>
<body>
<div class="container-fluid">
	<h2><%=facultadNombre%></h2>
	<div class="alert alert-info">
		<a href="facultad" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>
	</div>
	<form name="frmDocente" >
	<div class="alert alert-success d-flex align-items-center">
		<b>Employee:&nbsp;</b> [<b><%=codigoEmpleado%></b>] <b><%=empleadoNombre %>&nbsp;</b>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="hidden" name="Accion">
		<input type="hidden" name="FacultadId" value="<%=facultad%>">
		<b>&nbsp;<spring:message code='aca.Carrera'/>:</b> 
		<select id="CarreraId" name="CarreraId" class="form-select" style="width:550px">
		<%	for(int j = 0; j < lisCarreras.size(); j++){
				CatCarrera carrera = lisCarreras.get(j);%>
			<option value="<%= carrera.getCarreraId() %>"><%= carrera.getNombreCarrera() %></option>
		<%	}%>
		</select>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="btn btn-primary" onclick="Grabar();" value="Save" />
	</div>
	</form>
	
	<table style="width:95%" align="center" class="table table-condensed">
		<tr>
			<th width="5%"><spring:message code="aca.Operacion"/></th>
			<th width="5%"><spring:message code="aca.Numero"/></th>
			<th width="10%"><spring:message code="aca.Codigo"/></th>
			<th width="20%"><spring:message code="aca.Maestro"/></th>
			<th width="20%">Career</th>
			<th class="text-center" width="20%"><spring:message code="aca.Estado"/></th> 	
		</tr>

<%	for (int i=0; i<lisMaestros.size(); i++){
		HcaMaestro docente = lisMaestros.get(i);
		
		String carreraNombre = "-";
		if (mapaCarreras.containsKey(docente.getCarreraId())){
			carreraNombre = mapaCarreras.get(docente.getCarreraId()).getNombreCarrera();
		}
		
		String maestroNombre = "-";
		if (mapaMaestros.containsKey(docente.getCodigoPersonal())){
			maestroNombre = mapaMaestros.get(docente.getCodigoPersonal());
		}
			
		cont++;
%>
		<tr> 
			<td align="center">
			  	<img title="Eliminar" src="../../imagenes/no.png" title="Eliminar" onclick="Borrar('<%= facultad %>','<%=docente.getCodigoPersonal()%>');" class="button" />
			</td>
			<td align="center"><%=cont%></td>
			<td align="center"><%=docente.getCodigoPersonal()%></td>
			<td><%=maestroNombre%></td>
			<td><%=carreraNombre%></td>
			<td align="center"><%= docente.getEstado() %></td>
		</tr>
<%	} %>
	</table>
</div>
</body>