<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.residencia.spring.ResAlumno"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.residencia.spring.ResPeriodo"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<head></head>	
<%	
	String codigoAlumno 			= (String) session.getAttribute("codigoAlumno");
	String nombreAlumno 			= (String) request.getAttribute("nombreAlumno");
	String residencia				= (String) request.getAttribute("residencia");
	AlumAcademico alumAcademico		= (AlumAcademico) request.getAttribute("alumAcademico");
	
	List<ResAlumno> lisAlumno					= (List<ResAlumno>)request.getAttribute("lisAlumno");
	HashMap<String, String> mapaRazon 			= (HashMap<String, String>)request.getAttribute("mapaRazon");	
	HashMap<String, ResPeriodo> mapaPeriodos 	= (HashMap<String, ResPeriodo>)request.getAttribute("mapaPeriodos");	
%>
<body>
<script type="text/javascript">
	function Borrar( matricula, folio ){
		if(confirm("<spring:message code="aca.JSEliminar"/>")==true){
	  		document.location="borrarListado?Matricula="+matricula+"&Folio="+folio;
	  	}
	}
	function CambiarResidencia(folio){
		if(confirm("Are you sure you want to change the student's residency status?")==true){
	  		document.location="cambiarResidencia?Folio="+folio;
	  	}
	}
</script>

<div class="container-fluid">
	<h2>Student Residence<small class="text-muted fs-5">( <%=codigoAlumno%> - <%=nombreAlumno%> - <%=alumAcademico.getResidenciaId().equals("E")?"Off-campus":"Dormitory"%> <%=!alumAcademico.getDormitorio().equals("0")?"Dormitory "+alumAcademico.getDormitorio():""%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary btn-small" title="New Cycle" href="nuevoExterno">New off-campus student</a>
		<a class="btn btn-primary btn-small" title="New Cycle" href="nuevoInterno">New dormitory student</a>
	</div>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
	<tr>
		<th width="5%" >Op.</th>
		<th width="2%" >#</th>
		<th width="10%">Cycle</th>
		<th width="3%">S. Date</th>
		<th width="3%">E. Date</th>
		<th width="3%">Residen.</th>
		<th width="9%">Reason</th>
		<th width="2%">Perm.</th>
		<th width="2%">Dorm.</th>
		<th width="8%">Street</th>
		<th width="3%">Number</th>
		<th width="5%">Neighborhood</th>			
		<th width="5%">Town</th>			
		<th width="5%">Phone N.</th>			
		<th width="3%">User</th>			
		<th width="3%">Date</th>			
	</tr>	
	</thead>			
	<%
	int con = 1;
		
	for (ResAlumno alumno : lisAlumno) {
		
		String razon = "-";
		if(mapaRazon.containsKey(alumno.getRazon())){
			razon = mapaRazon.get(alumno.getRazon());
		}
		
		String periodo 	= "-";
		String estado 	= "-";
		if(mapaPeriodos.containsKey(alumno.getPeriodoId())){
			periodo = mapaPeriodos.get(alumno.getPeriodoId()).getPeriodoNombre();
			estado 	= mapaPeriodos.get(alumno.getPeriodoId()).getEstado();
		}
	%>
		<tr>			
			<td>	
<% 			if(alumno.getResidenciaId().equals("E")){%>
				<a href="javascript:CambiarResidencia('<%=alumno.getFolio()%>');"><i class="fas fa-home"></i></a>
				<a href="copiaExterno?Folio=<%=alumno.getFolio()%>&PeriodoId=<%=alumno.getPeriodoId()%>"><i class="fas fa-copy"></i></a>
<%				if (estado.equals("A")){ %>				
				<a class="fas fa-edit" href="nuevoExterno?Folio=<%=alumno.getFolio()%>&PeriodoId=<%=alumno.getPeriodoId()%>"></a>
<% 				}%>				
				
<% 			}else{%>
				<a class="glyphicon glyphicon-home" href="javascript:CambiarResidencia('<%=alumno.getFolio()%>');"></a>
				<a class="glyphicon glyphicon-duplicate" href="copiaInterno?Folio=<%=alumno.getFolio()%>&PeriodoId=<%=alumno.getPeriodoId()%>"></a>
<%				if (estado.equals("A")){ %>				
				<a class="fas fa-edit" href="nuevoInterno?Folio=<%=alumno.getFolio()%>&PeriodoId=<%=alumno.getPeriodoId()%>"></a>
<% 				}%>				
<% 			}%>
<%			if (estado.equals("A")){ %>
				<a class="fas fa-trash-alt" href="javascript:Borrar('<%=alumno.getMatricula()%>','<%=alumno.getFolio()%>');"></a>
<%			}%>				
			</td>
			<td>
				<%=con++%>
			</td>
			<td><%=periodo%></td>			
			<td><%=alumno.getFechaInicio()%></td>
			<td><%=alumno.getFechaFinal()%></td>
			<td><%=alumno.getResidenciaId().equals("E") ? "Off-campus" : "Dormitory"%></td>
			<td><%=razon%></td>
			<td><%=alumno.getEsPermanente().equals("S")?"YES":"NO"%></td>			
			<td><%=alumno.getDormitorio()%></td>
			<td><%=alumno.getCalle()%></td>
			<td><%=alumno.getNumero()%></td>
			<td><%=alumno.getColonia()%></td>
			<td><%=alumno.getMunicipio()%></td>
			<td>(<%=alumno.getTelArea()%>) <%=alumno.getTelNum()%></td>		
			<td><%=alumno.getUsuario()%></td>
			<td><%=alumno.getFecha()%></td>			
		</tr>
		<%
	}				
	%>
	</table>
</div>
</body>
