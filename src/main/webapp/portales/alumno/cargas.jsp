<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Date"%>
<%@page import="aca.util.Fecha"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumEstado"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.financiero.spring.FesCcobro"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.carga.spring.CargaBloque"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%@ include file= "menu.jsp" %>
<head>
<script>
	function refrescar(){
		document.forma.Accion.value="1";
		document.forma.submit();	
	}
</script>
</head>
<body>
<%
	String codigoPersonal			= (String) session.getAttribute("codigoPersonal");
	String matricula				= (String) session.getAttribute("codigoAlumno");
	AlumPersonal alumPersonal 		= (AlumPersonal) request.getAttribute("alumPersonal");
	
	List<CargaAlumno> lisCargas					= (List<CargaAlumno>) request.getAttribute("lisCargas");
	HashMap<String,String> mapaMaterias			= (HashMap<String,String>) request.getAttribute("mapaMaterias");
	HashMap<String,MapaPlan> mapaPlanes			= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
	HashMap<String, AlumEstado> mapaEstados		= (HashMap<String, AlumEstado>) request.getAttribute("mapaEstados");
// 	HashMap<String, FesCcobro> mapaFesCobro 	= (HashMap<String, FesCcobro>)  request.getAttribute("mapaFesCobro");
	HashMap<String, Carga> mapaCargas	 		= (HashMap<String, Carga>)  request.getAttribute("mapaCargas");
	HashMap<String, CargaBloque> mapaBloques	= (HashMap<String, CargaBloque>)  request.getAttribute("mapaBloques");
%>
<div class="container-fluid mt-1">
	<div class="alert alert-success">
		<a href="pasos"><i class="far fa-arrow-alt-circle-left fa-lg"></i></a>
		<span class="badge rounded-pill bg-dark">2</span> <spring:message code="portal.alumno.cargas.CargaAcademica"/>
		<small class="text-muted">( <%=alumPersonal.getCodigoPersonal()%> - <%=alumPersonal.getNombre()%>
			<%=alumPersonal.getApellidoMaterno()%> <%=alumPersonal.getApellidoPaterno()%>
			)
		</small>
	</div>
<% if(lisCargas.size() >= 1 && lisCargas != null){%>
	<table class="table table-fullcondensed table-bordered">
	<thead>
		<tr>
			<th width="3%">#</th>
			<th width="5%"><spring:message code="portal.alumno.cargas.Alta"/></th>
			<th width="8%"><spring:message code="portal.alumno.cargas.Estado"/></th>
			<th width="5%"><spring:message code="portal.alumno.cargas.Carga"/></th>
			<th width="16%"><spring:message code="portal.alumno.cargas.NombreCarga"/></th>
			<th width="10%"><spring:message code="portal.alumno.cargas.Bloque"/></th>
			<th width="5%"><spring:message code="aca.Plan" /></th>
			<th width="24%"><spring:message code="portal.alumno.cargas.Carrera"/></th>
			<th width="10%" class="center"><spring:message code="aca.Materias" /></th>
			<th width="10%">Declaration</th>
		</tr>
	</thead>
	<tbody>
<%
	int row = 0;
	for (CargaAlumno carga : lisCargas) {
		row++;

		String numMaterias = "0";
		if (mapaMaterias.containsKey(carga.getCargaId() + carga.getBloqueId())) {
			numMaterias = mapaMaterias.get(carga.getCargaId() + carga.getBloqueId());
		}

		String carreraSe = "";
		if (mapaPlanes.containsKey(carga.getPlanId())) {
			carreraSe = mapaPlanes.get(carga.getPlanId()).getCarreraSe();
		}
		
		String arrayFecha[] = carga.getFecha().substring(0, 10).split("-");
		String fecha = arrayFecha[2]+"/"+arrayFecha[1]+"/"+arrayFecha[0];
		
		String fechaInsc 	= "<span class='badge bg-danger' style='font-size:12px;'>Pending</span>";
		String inscrito 	= "N";
		
		if(mapaEstados.containsKey(carga.getCargaId()+carga.getBloqueId())){
			if(mapaEstados.get(carga.getCargaId()+carga.getBloqueId()).getEstado().equals("I")){
				inscrito = "S";
				fechaInsc = mapaEstados.get(carga.getCargaId()+carga.getBloqueId()).getFecha();
			}else{
				if (numMaterias.equals("0")){
					fechaInsc 	= "<span class='badge bg-danger' style='font-size:12px;'>Pending</span>";
				}else if (carga.getConfirmar().equals("S") ){
					fechaInsc 	= "<span class='badge bg-primary' style='font-size:12px;'>Subjects</span>";
				}else{
					fechaInsc 	= "<span class='badge bg-info' style='font-size:12px;'>Subjects</span>";
				}
			}
		}
		
		boolean ligaMaterias 	= false;		
		String estado 			= "-";
		if (numMaterias.equals("0")){
			estado 	= "<span class='badge bg-danger' style='font-size:12px;'>No Subjects</span>";
		}else if (inscrito.equals("S")){
			ligaMaterias = true;
			estado 	= "<span class='badge bg-success' style='font-size:12px;'>Enrolled</span>";
		}else if (inscrito.equals("N") && carga.getConfirmar().equals("S")){
			ligaMaterias = true;
			estado 	= "<span class='badge bg-primary' style='font-size:12px;'>Confirmed</span>";
		}else if (inscrito.equals("N") && carga.getConfirmar().equals("N")){
			ligaMaterias = true;
			estado 	= "<span class='badge bg-secondary' style='font-size:12px;'>Declaration Form</span>";
		}else{
			estado 	= "<span class='badge bg-danger' style='font-size:12px;'>Error</span>";
		}
		
		String cargaNombre 		= "-";
		if (mapaCargas.containsKey(carga.getCargaId())){
			cargaNombre = mapaCargas.get(carga.getCargaId()).getNombreCarga();
		} 
		
		String bloqueNombre 	= "-";
		if (mapaBloques.containsKey(carga.getCargaId()+carga.getBloqueId())){
			bloqueNombre = mapaBloques.get(carga.getCargaId()+carga.getBloqueId()).getNombreBloque();
		}
%>
		<tr>
			<td><%=row%></td>
			<td><%=fecha%></td>
			<td>
<%		if (ligaMaterias){%>
			<a href="verMaterias?CargaId=<%=carga.getCargaId()%>&BloqueId=<%=carga.getBloqueId()%>" style="text-decoration:none"><%=estado%></a>
<%		}else{%>
			<%=estado%>
<%		} %>			
			</td>
			<td><%=carga.getCargaId()%></td>
			<td><%=cargaNombre%></td>
			<td><%=carga.getBloqueId()+"-"+bloqueNombre%></td>
			<td><%=carga.getPlanId()%></td>
			<td><%=carreraSe%></td>
			<td class="center">
<% 		if(numMaterias.equals("0")){%>
				<a><span class="badge bg-danger" style='font-size:12px;'><%=numMaterias%></span></a>
<% 		}else if (inscrito.equals("N")){%>
				<a href="verMaterias?CargaId=<%=carga.getCargaId()%>&BloqueId=<%=carga.getBloqueId()%>" title="Ver materias" style="text-decoration:none">
					<span class="<%=carga.getConfirmar().equals("S")?"badge bg-primary":"badge bg-info"%>" style="font-size:12px;"><%=numMaterias%></span> <i class='fas fa-search'></i>
				</a>										
<% 		}else if (inscrito.equals("S")){%>
				<a href="verMaterias?CargaId=<%=carga.getCargaId()%>&BloqueId=<%=carga.getBloqueId()%>" title="View Subjects">
				<span class="badge bg-success" style='font-size:12px;'><%=numMaterias%></span>
				</a>
<%		}%>
			</td>
			<td width="10%"><a href="verMaterias?CargaId=<%=carga.getCargaId()%>&BloqueId=<%=carga.getBloqueId()%>" class="btn-sm btn-primary text-decoration-none">DECLARATION</a></td>
		</tr>
<%	} %>
	</tbody>
	</table>
<% }else {%>
	<div class="alert alert-warning">
		No Academic Loads found. Wait for the Academic Office to assign your load. 
	</div>
<% }%>
</div>
</body>