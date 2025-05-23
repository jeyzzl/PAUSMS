<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.graduacion.spring.AlumEvento"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.kardex.spring.KrdxCursoAct"%>
<%@page import="aca.carga.spring.CargaGrupo"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<html>
<head></head>
<%
	// Creacion de Variables
	java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	formato.setRoundingMode(java.math.RoundingMode.DOWN);

	AlumEvento alumEvento 		= (AlumEvento)request.getAttribute("alumEvento");

	// Lista de alumnos graduandos
	List<KrdxCursoAct> listaPendientes = (List<KrdxCursoAct> )request.getAttribute("listaPendientes");

	HashMap<String, AlumPersonal> mapAlumno 		= (HashMap<String,AlumPersonal>)request.getAttribute("mapaPersonal");
	HashMap<String, String> mapaCursos 				= (HashMap<String,String>)request.getAttribute("mapaCursos");
	HashMap<String, CargaGrupo> mapaGrupos 			= (HashMap<String,CargaGrupo>)request.getAttribute("mapaGrupos");
	HashMap<String, String> mapaMaestros 			= (HashMap<String,String>)request.getAttribute("mapaMaestros");
	HashMap<String, String> mapCursosPlan			= (HashMap<String,String>)request.getAttribute("mapCursosPlan");		
	HashMap<String, String> mapCarreraPlan 			= (HashMap<String,String>)request.getAttribute("mapCarreraPlan");
	HashMap<String,CatCarrera> mapaCarreras 		= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String, CatFacultad> mapaFacultad		= (HashMap<String,CatFacultad>)request.getAttribute("mapaFacultad");
	
	int cont = 1;
%>
<body>
<div class="container-fluid">
	<h3>Graduandos con materias pendientes de evaluar</h3>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="graduandos?EventoId=<%=alumEvento.getEventoId()%>"><i class="fas fa-arrow-left"></i></a>
		&nbsp;&nbsp;	
		<%= alumEvento.getEventoNombre()%>
	</div>
	<table class="table table-sm table-bordered">
		<thead>
			<tr>
				<th width="3%" style="text-align:center">N°.</th>			
				<th width="5%" style="text-align:center">Matrícula</th>			
				<th width="30%" style="text-align:center">Nombre</th>				
				<th width="5%" style="text-align:center">Fac.</th>
				<th width="5%" style="text-align:center">Acta</th>
				<th width="30%" style="text-align:center">Curso</th>				
				<th width="20%" style="text-align:center">Maestro</th>
				<th width="7%" style="text-align:center">Estado</th>
			</tr>	
		</thead>
<% 		
	for(KrdxCursoAct lista : listaPendientes){
		String nombreAlumno		= "-";
		String apellidosAlumno	= "-";
		if (mapAlumno.containsKey(lista.getCodigoPersonal())){
			AlumPersonal personal = mapAlumno.get(lista.getCodigoPersonal());
			nombreAlumno 	= personal.getNombre();
			apellidosAlumno	= personal.getApellidoPaterno()+" "+personal.getApellidoMaterno();
		}

		String nombreCurso	= "-";
		if (mapaCursos.containsKey(lista.getCursoId())){
			nombreCurso = mapaCursos.get(lista.getCursoId());
		}
		
		String codigoMaestro = "-";
		String estado = "0";
		if (mapaGrupos.containsKey(lista.getCursoCargaId())){
			codigoMaestro 	= mapaGrupos.get(lista.getCursoCargaId()).getCodigoPersonal();
			estado 			= mapaGrupos.get(lista.getCursoCargaId()).getEstado();
		}
		
		if (estado.equals("1")) estado = "Creada";
		if (estado.equals("2")) estado = "Ordinario";
		
		String maestroNombre	= "-";
		if (mapaMaestros.containsKey(codigoMaestro)){
			maestroNombre = mapaMaestros.get(codigoMaestro);
		}

		String maestroFacultad	= "-";
		if (mapaMaestros.containsKey(codigoMaestro)){
			maestroNombre = mapaMaestros.get(codigoMaestro);
		}

		CatFacultad facultad = new CatFacultad();
		if (mapCursosPlan.containsKey(lista.getCursoId())){
			String planId = mapCursosPlan.get(lista.getCursoId());
			if (mapCarreraPlan.containsKey(planId)){
				String carreraId = mapCarreraPlan.get(planId);
				if (mapaCarreras.containsKey(carreraId)){
					CatCarrera carrera = mapaCarreras.get(carreraId);
					if (mapaFacultad.containsKey(carrera.getFacultadId())){
						facultad = mapaFacultad.get(carrera.getFacultadId());
					}
				}
			}
		}
%>					
		<tr>
			<td style="text-align:center"><%=cont++%></td>
			<td><%=lista.getCodigoPersonal()%></td>
			<td><%=nombreAlumno%> <%=apellidosAlumno%></td>			
			<td><%=facultad.getNombreCorto()%></td>			
			<td><%=lista.getCursoCargaId()%></td>
			<td><%=nombreCurso%></td>			
			<td><%=maestroNombre%></td>
			<td><%=estado%></td>
		</tr>
<% }%>
	</table>
</div>
</body>
</html>