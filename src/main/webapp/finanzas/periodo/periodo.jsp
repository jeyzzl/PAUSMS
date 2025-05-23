<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.financiero.spring.FinPeriodo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Borrar(periodoId){					
		if( confirm("¿Estas seguro de eliminar este registro?") ){
			document.location.href="borrar?PeriodoId="+periodoId;
		}
	}		
</script>
<%
	List<FinPeriodo> lisPeriodos 			= (List<FinPeriodo>) request.getAttribute("lisPeriodos");
	HashMap<String,String> mapaBloqueados 	= (HashMap<String,String>) request.getAttribute("mapaBloqueados");
%>
<body>
<div class="container-fluid">
	<h2>Periodos de Bloqueo</h2>	
	<div class="alert alert-info">
		<a href="accion" class="btn btn-primary"><i class="fas fa-plus"></i><spring:message code='aca.Añadir'/></a>
	</div>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
	<tr>
		<th>#</th>
		<th>Op</th>
		<th>Clave</th>
		<th><spring:message code="aca.Inicio"/></th>
		<th><spring:message code='aca.Final'/></th>
		<th><spring:message code="aca.Cargas"/></th>
		<th><spring:message code="aca.Modalidades"/></th>		
		<th>Mensaje</th>
		<th><spring:message code="aca.Estado"/></th>
		<th>Cantidad</th>
		<th>#Al.</th>
	</tr>
	</thead>
<% 
	String estado = "";
	int cont = 0;
	for(FinPeriodo periodo : lisPeriodos){
		cont++;
		if(periodo.getEstado().equals("A")){
			estado = "Aviso";
		}else if (periodo.getEstado().equals("B")){
			estado = "Bloqueado";
		}else{
			estado = "Inactivo";
		}
		String total = "0";
		if (mapaBloqueados.containsKey(periodo.getPeriodoId())){
			total = mapaBloqueados.get(periodo.getPeriodoId());
		}		
%>
	<tr>
		<td><%=cont%></td>
		<td>
			<a href="accion?PeriodoId=<%=periodo.getPeriodoId()%>"><i class="fas fa-edit"></i></a>
<%		if (total.equals("0")){%>						
			&nbsp;<a href="javascript:Borrar('<%=periodo.getPeriodoId()%>')"><i class="fas fa-trash-alt" style="color:red"></i></a>
<%		} %>			
		</td>
		<td><%=periodo.getPeriodoId()%></td>
		<td><%=periodo.getFechaIni()%></td>
		<td><%=periodo.getFechaFin()%></td>
		<td onclick="location.href='cargas?FinPeriodoId=<%=periodo.getPeriodoId()%>'" style="cursor:pointer;" onmouseover="this.style.backgroundColor='#E6E6E6';"onmouseout="this.style.backgroundColor='';"><%=periodo.getCargas()%></td>
		<td onclick="location.href='modalidades?FinPeriodoId=<%=periodo.getPeriodoId()%>'" style="cursor:pointer;" onmouseover="this.style.backgroundColor='#E6E6E6';"onmouseout="this.style.backgroundColor='';"><%=periodo.getModalidades()%></td>
		<td><%=periodo.getMensaje()%></td>
		<td><%=estado%></td>
		<td class="text-end"><%=periodo.getCantidad()%></td>
		<td class="text-center"><%=total.equals("0")?"<span class='badge bg-warning rounded-pill'>"+total+"</span>":"<span class='badge bg-dark rounded-pill'>"+total+"</span>"%></td>
	</tr>
<%	}%>
	</table>
</div>
</body>