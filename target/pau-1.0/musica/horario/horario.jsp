<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.vista.spring.Maestros"%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">

	function cambioPeriodo(){
		var periodoId = document.getElementById("PeriodoId").value;
		location.href = "horario?PeriodoId="+periodoId;
	}	
	
	function cambioCarga(){
		var periodoId 	= document.getElementById("PeriodoId").value;
		var cargaId 	= document.getElementById("CargaId").value;
		location.href = "horario?PeriodoId="+periodoId+"&CargaId="+cargaId;
	}

</script>
<%
	String codigoEmpleado 			= (String)session.getAttribute("codigoEmpleado");

	String periodoId				= (String) request.getAttribute("PeriodoId");
	String cargaId					= (String) request.getAttribute("CargaId");
	
	List<CatPeriodo> lisPeriodos	= (List<CatPeriodo>)request.getAttribute("lisPeriodos");
	List<Carga> lisCargas		 	= (List<Carga>)request.getAttribute("lisCargas");
	List<Maestros> lisMaestros	 	= (List<Maestros>)request.getAttribute("lisMaestros");
	
	HashMap<String,String> mapMaestros	= (HashMap<String,String>)request.getAttribute("mapMaestros");
%>
<body>
<div class="container-fluid">
	<h1>Lista de Maestros</h1>
	<form name="forma" action="horario">
		<div class="alert alert-info">
			<b>Periodo:</b>
			<select id="PeriodoId" name="PeriodoId" onchange="javascript:cambioPeriodo();" style="width:200px">
	<%		for(CatPeriodo periodo : lisPeriodos){%>
				<option value="<%=periodo.getPeriodoId()%>" <%=periodo.getPeriodoId().equals(periodoId)?"selected":""%>><%=periodo.getNombre()%></option>
	<%		}%>
			</select>&nbsp;&nbsp;&nbsp;
			<b>Carga:</b>
			<select id="CargaId" name="CargaId" onchange="javascript:cambioCarga();" style="width:350px">
	<%		for(Carga carga : lisCargas){%>
				<option value="<%=carga.getCargaId()%>" <%=carga.getCargaId().equals(cargaId)?"selected":""%>><%=carga.getCargaId()%>-<%=carga.getNombreCarga()%></option>
	<%		}%>
			</select>
		</div>
<% 		if(!mapMaestros.containsKey(codigoEmpleado)){%>
		<div class="alert alert-info" role="alert">
			<a class="btn btn-primary" href="agregarHorario?PeriodoId=<%=periodoId%>&CargaId=<%=cargaId%>&MaestroId=<%=codigoEmpleado%>&Pag=1">Agregar: <%=codigoEmpleado%></a>
		</div>
<% 		}%>
	</form>
	<table class="table">
		<tr>
		    <th width="5%">#</th>
		    <th width="15%">No. Nómina</th>
		    <th width="80%">Nombre Maestro</th>
		</tr>
<%
	int row = 0;
	for(Maestros maestro : lisMaestros){
		row++;
%>
		<tr>
			<td><%=row%></td>
			<td><%=maestro.getCodigoPersonal()%></td>
			<td>
				<a href="horarioMaestro?CargaId=<%=cargaId%>&PeriodoId=<%=periodoId%>&MaestroId=<%=maestro.getCodigoPersonal()%>">
				<%=maestro.getNombre()+" "+maestro.getApellidoPaterno()+" "+maestro.getApellidoMaterno()%></a>
			</td>
		</tr>
<%	}%>
	</table>
	
</div>	
</body>
<%@ include file= "../../cierra_enoc.jsp" %>