<%@ page import= "java.util.List"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.carga.spring.CargaPractica"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<head>	
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
	<script type="text/javascript">	
		function Grabar(){
			if(document.frmPractica.FechaIni.value != "" && document.frmPractica.FechaFin.value != ""){					
				document.frmPractica.submit();
			}else{
				alert("<spring:message code='aca.JSCompletar'/>");
			}			
		}	

		function Borrar(cursoCargaId,folio){
			if(confirm("Are you sure you want to delete this record?")){					
				document.location.href="borrarPracticas?CursoCargaId="+cursoCargaId+"&Folio="+folio;
			}	
		}
	</script>
</head>	
<%		
	String cursoCargaId 		= request.getParameter("CursoCargaId");
	String carreraId	 		= (String)request.getAttribute("carreraId");
	String planId		 		= (String)request.getAttribute("planId");
	String cursoOrigen	 		= (String)request.getAttribute("cursoOrigen");
	String materiaNombre		= (String)request.getAttribute("materiaNombre");
	Carga carga					= (Carga)request.getAttribute("carga");
	
	List<CargaPractica> lisPracticas 	= (List<CargaPractica>) request.getAttribute("lisPracticas");
%>
<body>
<div class="container-fluid">
	<h2>Practicum Dates<small class="text-muted fs-4">( <%=cursoCargaId%> - <%=planId%> - <%=materiaNombre%>)</small></h2>
	<form name="frmPractica" action="grabarPracticas" method="post">
	<input type="hidden" name="CursoCargaId" value="<%=cursoCargaId%>">
	<input type="hidden" name="CarreraId" value="<%=carreraId%>">
	<input type="hidden" name="PlanId" value="<%=planId%>">
	<div class="alert alert-info">
		<a class="btn btn-primary" href="grupo?CarreraId=<%=carreraId%>&PlanId=<%=planId%>"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;&nbsp;&nbsp;
		Start Date: <input type="text" class="text" data-date-format="dd/mm/yyyy" name="FechaIni" id="FechaIni" value="<%=carga.getFInicio()%>" maxlength="10" size="12">&nbsp;&nbsp;
		End Date: <input type="text" class="text" data-date-format="dd/mm/yyyy" name="FechaFin" id="FechaFin" value="<%=carga.getFFinal()%>"  maxlength="10" size="12">&nbsp;&nbsp;
		<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a>
	</div>
	</form>
	<table class="table table-condensed">
	<thead>
	<tr>
		<th>Op.</th>
		<th>Key</th>
		<th>Start</th>
		<th>End</th>
		<th>Status</th>
	</tr>
	</thead>	
	<tbody>
<%	for (CargaPractica practica : lisPracticas){ %>	
	<tr>
		<td><a href="javascript:Borrar('<%=practica.getCursoCargaId()%>','<%=practica.getFolio()%>');"><i class="fas fa-trash-alt"></i></a></td>
		<td><%=practica.getFolio()%></td>
		<td><%=practica.getFechaIni()%></td>
		<td><%=practica.getFechaFin()%></td>
		<td><%=practica.getEstado().equals("A")?"Active":"Inactive"%></td>
	</tr>
<%	} %>	
	</tbody>
	</table>
</div>	
</body>
<script>		
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();
</script>