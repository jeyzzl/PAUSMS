<%@page import="java.util.List"%>
<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.financiero.spring.FinPeriodo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<script>

	function elegirTodos(){
		var elegir = jQuery("#elegir");
		if (elegir.html()=="Todos"){
			jQuery(".cargas").attr("checked",true);
			elegir.html("Ninguno");
		}else{				
			jQuery(".cargas").attr("checked",false);
			elegir.html("Todos");
		}	
	}

	function modificarCargas(){			
		document.frmCargas.submit();
	}
		
</script>
<script type="text/javascript">

	function cambiaPeriodo(periodoId){
		document.location.href="cargas?PeriodoId="+periodoId;
	}
	
</script>
<%
	String periodoId 				= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
	FinPeriodo finPeriodo 			= (FinPeriodo)request.getAttribute("finPeriodo");
	List<CatPeriodo> lisPeriodos 	= (List<CatPeriodo>)request.getAttribute("lisPeriodos");
	List<Carga> lisCargas 			= (List<Carga>)request.getAttribute("lisCargas");
	int cont 						= 1;
	
	if (periodoId.equals("0") && lisPeriodos.size() >= 1){
		periodoId = lisPeriodos.get(0).getPeriodoId();
	}
%>

<html>
<head>
	<style>
		.empleados:hover{
			font-weight: bold;
		}
	}
	</style>
<body>
<div class="container-fluid" align="center">
	<h2>Cargas <small class="text-muted fs-4">¡ Elige !</small></h2>
	<a href="periodo" class="btn btn-primary" align="center"><i class="fas fa-arrow-left"></i></a>
	<a class="btn btn-success" onclick="modificarCargas();" ><i class="fas fa-check"></i> Guardar</a>
	<br>			
	<br>	
    <div align="center">
       <font color="#000099" size="2"><strong>Seleccione el Período:</strong>&nbsp;</font>&nbsp;
       <select onchange='javascript:cambiaPeriodo(this.value);' name="PeriodoId" class="input input-medium">
	<%	for(CatPeriodo periodo : lisPeriodos){%>
				<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print("Selected");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
	<%	}%>
	</select>
     </div>	
	<form name="frmCargas" action="grabarCargas" method="post">
	<input type="hidden" name="PeriodoId" value="<%=periodoId%>">					
	<table id="table" class="table table-sm table-bordered">
		<thead class="table-info">	 
			<tr>
				<th width="10%" align="center"><a id="elegir" class="btn btn-small btn-info" onclick="javascript:elegirTodos();" style="cursor:pointer; font-size:12px;">Todos</a></th>
				<th width="5%">#</th>		
				<th width="5%"><spring:message code="aca.Carga"/></th>					
				<th width="15%"><spring:message code="aca.Nombre"/></th>
			</tr>
		</thead>
		<tbody>				
<%	
	for(Carga carga : lisCargas){		
%>
		<tr>					
			<td style="text-align:center;"><input type="checkbox" class="cargas" id="<%=carga.getCargaId()%>" name="<%=carga.getCargaId() %>" value="S" <%if(finPeriodo.getCargas().contains(carga.getCargaId())){%>checked<%} %> /></td>
			<td><%=cont%></td>				
			<td><%=carga.getCargaId() %></td>					
			<td align="center"><%=carga.getNombreCarga() %></td>
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