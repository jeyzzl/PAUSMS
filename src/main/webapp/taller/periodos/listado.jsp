<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page import="aca.bec.spring.BecPeriodo"%>

<html>
<head>
	<script type="text/javascript"> 
		function Borrar(PeriodoId) {
			if (confirm("<spring:message code="aca.JSEliminar"/>") == true) {
				document.location = "borrar?PeriodoId="+PeriodoId;
			}
		}
 	</script>
</head>
<%
	/* Lista de las categorias */
	List<BecPeriodo> listaPeriodos 	 		= (List<BecPeriodo>)request.getAttribute("listaPeriodos");
 	HashMap<String,String> mapaPeriodos		= (HashMap<String,String>)request.getAttribute("mapaPeriodos");
	String periodoId  						= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
	String mensaje 							= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
%>
<body>
<div class="container-fluid">
	<h1>Lista Periodos</h1>
	<div class="alert alert-info">
		<a href="editar" class="btn btn-primary"><spring:message code="aca.Anadir"/></a>	
	</div>	
<%	if (!mensaje.equals("-")){%>
	<div class="alert alert-info"><%=mensaje%></div>
<%	} %>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
	<tr>
		<th width="3%">#</th>
		<th width="3%">Op.</th>
		<th width="3%">Clave</th>
		<th width="15%">Nombre Periodo</th>
		<th width="7%">Fecha Ini</th>
		<th width="7%">Fecha Fin</th>
		<th width="5%">Estado</th>
		<th width="5%">Ejercicio</th>
		<th width="5%">Tipo</th>
		<th width="15%">Fechas Universidad</th>
		<th width="15%">Fechas Prepa</th>
		<th width="15%">Fechas Periodo</th>
	</tr>
	</thead>
<%				
	int row = 0;
	for (BecPeriodo periodo: listaPeriodos) {
		row++;	
		
		String numPeriodos = "0";
		if(mapaPeriodos.containsKey(periodo.getPeriodoId())){
			numPeriodos = mapaPeriodos.get(periodo.getPeriodoId());
		}
%>
	<tr>
		<td><%=row%></td>
		<td style="text-align: center">
			<a href="editar?PeriodoId=<%=periodo.getPeriodoId()%>" title="Modificar"><i class="fas fa-edit"></i></a>&nbsp;
<% 		if (numPeriodos.equals("0")){%>
			<a href="javascript:Borrar('<%=periodo.getPeriodoId()%>')" title="Eliminar"><i class="fas fa-trash-alt"></i></a>
<% 		}%>	
		</td>				
		<td><%=periodo.getPeriodoId()%></td>
		<td><%=periodo.getPeriodoNombre()%></td>
		<td><%=periodo.getFechaIni()%></td>
		<td><%=periodo.getFechaFin()%></td>
		<td><span class="badge bg-primary"><%=periodo.getEstado().equals("A")?"Activo":"Inactivo" %></span></td>
		<td><%=periodo.getIdEjercicio()%></td>
		<td><span class="badge bg-primary"><%=periodo.getTipo().equals("R")?"Recuperar":"Verano" %></span></td>
		<td><%=periodo.getFechasUniversidad().length()>30?periodo.getFechasUniversidad().substring(0,29)+" ...":periodo.getFechasUniversidad()%></td>
		<td><%=periodo.getFechasPrepa().length()>30?periodo.getFechasPrepa().substring(0,29)+" ...":periodo.getFechasPrepa()%></td>
		<td><%=periodo.getFechasPeriodo().length()>30?periodo.getFechasPeriodo().substring(0,29)+" ...":periodo.getFechasPeriodo()%></td>
	</tr>
<%	}  %>
	</table>
</div>
</body>
</html>
