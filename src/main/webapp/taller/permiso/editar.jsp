<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="aca.financiero.spring.ContEjercicio" %>
<%@ page import="aca.financiero.spring.ContCcosto" %>
<%@ page import="aca.bec.spring.BecFija" %>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script>
	function elegirTodos(){
		var elegir = jQuery("#elegir");
		if (elegir.html()=="Todos"){
			jQuery(".depto").attr("checked",true);
			elegir.html("Ninguno");
		}else{				
			jQuery(".depto").attr("checked",false);
			elegir.html("Todos");
		}	
	}

	function modificarDepartamentos(ejercicioId, tipo){		
		document.frmDepartamentos.submit();			
	}		
</script>
<%
	//String ejercicioId			= (String)session.getAttribute("ejercicioId");
	String ejercicioId				= request.getParameter("EjercicioId")==null?aca.util.Fecha.getEjercicioHoy():request.getParameter("EjercicioId");	
	String tipo 					= request.getParameter("Tipo")==null?"0":request.getParameter("Tipo");	
	String opcion					= request.getParameter("Opcion")==null?"0":request.getParameter("Opcion");
	String usuarios 				= "";
	int cont 						= 1;
	
	List<ContCcosto> lisDeptos		= (List<ContCcosto>) request.getAttribute("lisDeptos");
	List<String> lisSeleccionados 	= (List<String>) request.getAttribute("lisSeleccionados");	
%>

<html>
<head>
	<style>
		.empleados:hover{
			font-weight: bold;
		}
 	body{
 		background : white;
 	}
	</style>
</head>	
<body>
<div class="container-fluid">
	<h2>Centros de Costo</h2>
	<div class="alert alert-info">
	<a href="fija?EjercicioId=<%=ejercicioId%>" class="btn btn-primary" align="center"><i class="fas fa-arrow-left"></i></a>	
	<a class="btn btn-success" onclick="javascript:modificarDepartamentos(<%=ejercicioId%>,<%=tipo%>);" ><i class="fas fa-check"></i> Guardar centros de costo</a>	
	<a class="btn btn-primary" href="editar?EjercicioId=<%=ejercicioId%>&Tipo=<%=tipo%>&Opcion=<%=opcion.equals("0")?"1":"0"%>">
	  <%=opcion.equals("0")?"Mostrar Todos":"Mostrar elegidos"%>
	</a>
	</div>
	<form name="frmDepartamentos" action="grabar" method="post">
	<input type="hidden" name="EjercicioId" value="<%=ejercicioId%>">
	<input type="hidden" name="Tipo" value="<%=tipo%>">
				
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">
		<tr>
			<th width="10%" align="center"><a id="elegir" class="btn btn-small btn-primary" onclick="javascript:elegirTodos();" style="cursor:pointer; font-size:12px;">Todos</a></th>					
			<th width="15%">Costo</th>
			<th width="50%"><spring:message code="aca.Nombre"/></th>
		</tr>
	</thead>
	<tbody>
						
<%	for(ContCcosto depto : lisDeptos){
		if(!lisSeleccionados.contains(depto.getIdCcosto()) && opcion.equals("0") ){
			continue;
		}		
%>
		<tr>					
			<td style="text-align:center;"><input type="checkbox" class="depto" id="<%=depto.getIdCcosto()%>" name="<%=depto.getIdCcosto()%>" value="S" <%if(lisSeleccionados.contains(depto.getIdCcosto())){%>checked<%} %> /></td>					
			<td align="center"><%=depto.getIdCcosto() %></td>
			<td align="center"><%=depto.getNombre()%></td>
		</tr>				
<% 		cont++;
	}
%>
	</tbody>
	</table>
	</form>
</div>
</body>
</html>