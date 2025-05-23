<%@ page import= "java.util.* "%>
<%@ page import="aca.financiero.spring.FinPeriodo"%>
<%@ page import="aca.catalogo.spring.CatModalidad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<script>
	function elegirTodos(){
		var elegir = jQuery("#elegir");
		if (elegir.html()=="Todos"){
			jQuery(".modalidad").attr("checked",true);
			elegir.html("Ninguno");
		}else{				
			jQuery(".modalidad").attr("checked",false);
			elegir.html("Todos");
		}	
	}

	function modificarModalidades(){		
		document.frmModalidades.submit();
	}		
</script>
<%
	FinPeriodo finPeriodo 				= (FinPeriodo)request.getAttribute("finPeriodo");	
	List<CatModalidad> lisModalidades 	= (List<CatModalidad>)request.getAttribute("lisModalidades");
	int cont 				= 1;	
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
<div class="container-fluid" align="center">
	<h2>Modalidades <small class="text-muted fs-4">¡ Elige !</small></h2>
	<a href="periodo" class="btn btn-primary" align="center"><i class="fas fa-arrow-left"></i></a>
	<a class="btn btn-success" onclick="modificarModalidades();" ><i class="fas fa-check"></i> Guardar</a>
	<br>			
	<br>
	<form name="frmModalidades" action="grabarModalidades" method="post">							
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
		<tr>
			<th width="10%" align="center"><a id="elegir" class="btn btn-small btn-info" onclick="javascript:elegirTodos();" style="cursor:pointer; font-size:12px;">Todos</a></th>
			<th width="5%">#</th>					
			<th width="15%"><spring:message code="aca.Modalidad"/></th>
			<th width="10%">¿Es en línea?</th>
		</tr>
	</thead>
	<tbody>
					
<%	
for(CatModalidad modalidad : lisModalidades){	
%>
		<tr>					
			<td style="text-align:center;"><input type="checkbox" class="modalidad" id="<%=modalidad.getModalidadId()%>" name="<%=modalidad.getModalidadId() %>" value="S" <%if(finPeriodo.getModalidades().contains("-"+modalidad.getModalidadId()+"-")){%>checked<%} %> /></td>
			<td><%=cont %></td>					
			<td align="center"><%=modalidad.getNombreModalidad() %></td>
			<td align="center"><%=modalidad.getEnLinea() %></td>
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