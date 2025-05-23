<%@ page import= "java.util.HashMap"%>
<%@ page import= "java.util.List"%>
<%@ page import= "aca.cert.spring.CertPlan"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%
	String facultad			= (String) request.getAttribute("facultad");	
	boolean borro			= (boolean) request.getAttribute("borro");
	String nombreFacultad	= (String) request.getAttribute("nombreFacultad");
	String mensaje			= (String) request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	List<CertPlan> lisPlan 					= (List<CertPlan>)request.getAttribute("lisPlan");
	HashMap<String,String> mapNumCursos 	= (HashMap<String,String>)request.getAttribute("mapNumCursos");
	HashMap<String,String> mapCarreraPlan 	= (HashMap<String,String>)request.getAttribute("mapCarreraPlan");
	HashMap<String,CatCarrera> mapCarreras 	= (HashMap<String,CatCarrera>)request.getAttribute("mapCarreras");
	
	String facT			= "X";
	String carrera		= "";
	String carreraTemp	= "X";	
	String cursos 		= "";	
%>
<head>
	<script type="text/javascript">
		function modificar(plan){
			document.location = "edita_plan?plan="+plan;
		}
		
		function borrar(plan){
			if(confirm("¿Está seguro que desea borrar definitivamente el plan?"))
				document.location = "planes?Accion=3&plan="+plan;
		}
	</script>
</head>
<div class="container-fluid">
	<h2><%=facultad%> - <%=nombreFacultad%></h2>
	<div class="alert alert-info">
		<a href="carrera" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		<a href="edita_plan?facultad=<%=facultad%>" class="btn btn-primary"><spring:message code="aca.Anadir"/></a>
	</div>
<%	if(!mensaje.equals("-")){ %>	
	<div class="alert alert-info"><%=mensaje%></div>
<%	}%>		
	<table style="width:100%; margin:0 auto;">
<%
	for(CertPlan certPlan : lisPlan){
		
		if(mapNumCursos.containsKey(certPlan.getPlanId())){
			cursos = mapNumCursos.get(certPlan.getPlanId());
		}

		if(mapCarreraPlan.containsKey(certPlan.getPlanId())){
			carrera = mapCarreraPlan.get(certPlan.getPlanId());
		}

		if(mapCarreras.containsKey(carrera)){
			facultad = mapCarreras.get(carrera).getFacultadId();
		}
		
		if ( !facultad.equals(facT) ){
			facT = facultad;
		
		
%>	
	</table>
	<table class="table table-sm table-bordered">
	<thead>
    <tr> <td colspan="8" style="font-size:12pt;"><h3><%= nombreFacultad %></h3></td></tr>
	<tr class="table-info">
		<th width="8%"><spring:message code="aca.Operacion"/></th>
		<th><spring:message code="aca.Plan"/></th>
		<th><spring:message code="aca.Carrera"/></th>
		<th class="text-end">Cursos</th>
	</tr>
	</thead>
	<tbody>
				
<%		}%>
	<tr class="button">
		<td>
			<i class="fas fa-edit" title="Editar"  onclick="modificar('<%=certPlan.getPlanId() %>');" class="button" width="15px" ></i>
<%		if(cursos.equals("0")){%>
			<i class="fas fa-trash-alt" title="Eliminar"  onclick="borrar('<%=certPlan.getPlanId() %>');" class="button" width="15px" ></i>
<%		}%>
		</td>
		<td onclick="document.location.href = 'ciclos?plan=<%=certPlan.getPlanId()%>&facultad=<%=facultad%>';"><%=certPlan.getPlanId() %></td>					
		<td onclick="document.location.href = 'ciclos?plan=<%=certPlan.getPlanId()%>&facultad=<%=facultad%>';"><%=certPlan.getCarrera() %></td>
		<td class="text-end"><%=cursos%></td>
	</tr>
<%	}%>
	</tbody>
	</table>
</div>