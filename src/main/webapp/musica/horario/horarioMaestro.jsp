<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.musica.spring.MusiHorario"%>
<%@page import="aca.musica.spring.MusiHorarioAlumno"%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function Borrar(folio, periodoId, cargaId, codigoPersonal){
		if(confirm("<spring:message code="aca.JSEliminar"/>")==true){
	  		document.location="borrarHorario?Folio="+folio+"&PeriodoId="+periodoId+"&CargaId="+cargaId+"&MaestroId="+codigoPersonal;
	  	}
	}
</script>
<%
	String periodoId		= (String) request.getAttribute("PeriodoId");
	String cargaId			= (String) request.getAttribute("CargaId");
	String codigoPersonal	= (String) request.getAttribute("codigoPersonal");
	String nombreMaestro	= (String) request.getAttribute("nombreMaestro");
	
	List<MusiHorario> lisHorarioMaestro 					= (List<MusiHorario>)request.getAttribute("lisHorarioMaestro");
	HashMap<String, MusiHorarioAlumno> mapaHorarioALumno 	= (HashMap<String, MusiHorarioAlumno>)request.getAttribute("mapaHorarioALumno");
	
	String[] semana = {"Domingo","Lunes","Martes","Miércoles","Jueves","Viernes","Sábado"};
%>
<body>
<div class="container-fluid">
	<h2>Horario Maestro <small>(<%=nombreMaestro%> <%=codigoPersonal%> - <%=cargaId%>)</small></h2>
	<form name="forma" action="horario">
	<div class="alert alert-info">
		<a class="btn btn-primary" href="horario?PeriodoId=<%=periodoId%>&CargaId=<%=cargaId%>">Regresar</a> 
		<a class="btn btn-info" href="agregarHorario?PeriodoId=<%=periodoId%>&CargaId=<%=cargaId%>&MaestroId=<%=codigoPersonal%>&Pag=2">Añadir</a> 
	</div>
	</form>
	
	<table class="table">
		<tr>
			<th width="5%">Op.</th>
		    <th width="5%">#</th>		    
		    <th width="10%">Día</th>
		    <th width="10%">Hora Inicio</th>		    
		    <th width="10%">Hora Final</th>
		    <th width="10%">Cupo</th>
		    <th width="10%">Valor</th>
		    <th width="10%">Alumno</th>
		</tr>
<%
	int row = 0;
	for(MusiHorario musiHorario : lisHorarioMaestro){
		row++;
%>
		<tr>
			<td>
				<a href="agregarHorario?CargaId=<%=cargaId%>&Folio=<%=musiHorario.getFolio()%>&MaestroId=<%=codigoPersonal%>"><i class="fas fa-pencil-alt"></i></a>&nbsp;
				<a class="fas fa-trash-alt" href="javascript:Borrar('<%=musiHorario.getFolio()%>','<%=periodoId%>','<%=cargaId%>','<%=codigoPersonal%>')"></a>
			</td>
			<td><%=row%></td>			
			<td><%=semana[Integer.parseInt(musiHorario.getDia())-1]%></td>
			<td><%=musiHorario.getHoraInicio()%>:<%=musiHorario.getMinInicio()%></td>			
			<td><%=musiHorario.getHoraFinal()%>:<%=musiHorario.getMinFinal()%></td>
			<td><%=musiHorario.getCupo()%></td>
			<td><%=musiHorario.getValor()%></td>
			<td><%=mapaHorarioALumno.containsKey(musiHorario.getFolio())?mapaHorarioALumno.get(musiHorario.getFolio()).getCodigoPersonal():"-"%></td>
		</tr>
<%	}%>
	</table>
	
</div>	
</body>
<%@ include file= "../../cierra_enoc.jsp" %>