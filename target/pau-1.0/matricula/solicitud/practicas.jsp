<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="aca.carga.spring.CargaPracticaAlumno"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp"%>

<html>
<%
	String cargaId 			= request.getParameter("CargaId") == null ? "0" : request.getParameter("CargaId");
	String facultadId 		= request.getParameter("FacultadId") == null ? "0" : request.getParameter("FacultadId");	
	String facultadNombre 	= (String) request.getAttribute("facultadNombre");
	String bloques		 	= (String) request.getAttribute("bloques");
	
	List<AlumnoCurso> lisMaterias 				= (List<AlumnoCurso>) request.getAttribute("lisMaterias");
	HashMap<String,String> mapaAlumnos			= (HashMap<String,String>) request.getAttribute("mapaAlumnos");	
	HashMap<String,CatCarrera> mapaCarreras		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,String> mapaFechas			= (HashMap<String,String>) request.getAttribute("mapaFechas");
	HashMap<String,String> mapaLibres			= (HashMap<String,String>) request.getAttribute("mapaLibres");
	HashMap<String,AlumAcademico> mapaRes       = (HashMap<String,AlumAcademico>) request.getAttribute("mapaRes");
	HashMap<String, String> mapaPracticas		= (HashMap<String, String>) request.getAttribute("mapaPracticas");
	HashMap<String,CatFacultad> mapaFacultad    = (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultad");


%>
<body>
<div class="container-fluid">
	<h2>Alumnos con materias en prácticas<small class="text-muted fs-4">( <%=facultadNombre%> )</small></h2>
	<form name="frmProceso" action="materias" method="post">	
	<input type="hidden" name="CargaId" value="<%=cargaId%>">
	<input type="hidden" name="FacultadId" value="<%=facultadId%>">
	<div class="alert alert-info d-flex align-items-center">
		<a href="listado?CargaId=<%=cargaId%>" class="btn btn-primary">Regresar</a>&nbsp;&nbsp;		
		<a href="practicas?CargaId=<%=cargaId%>&FacultadId=<%=facultadId%>&Bloques=<%=bloques%>" class="btn btn-primary" type="submit"><i class="fas fa-sync-alt"></i></a>    
	</div>	
	</form>
	<table style="width:100%" class="table table-sm table-bordered">
	<thead>
		<tr class="noHover">
			<th style="width:2%">#</th>
			<th style="width:4%">Matricula</th>
			<th style="width:10%">Alumno</th>
			<th style="width:3%">Res.</th>			
			<th style="width:4%">Plan</th>	
			<th style="width:15%">Carrera</th>
			<th style="width:2%">Blq.</th>
			<th style="width:7%">Materia</th>
			<th style="width:2%" class="right">Crd.</th>
			<th style="width:2%">HT</th>
			<th style="width:2%">HP</th>
			<th style="width:9%">Fechas</th>	
			<th style="width:15%">Liberado</th>	
			
		</tr>
	</thead>
	<tbody>	
<% 		
	int row=0;
	int totalCreditos	=0;
	int totalHTeoria	=0;
	int totalHPracticas	=0;
	
	for(AlumnoCurso curso : lisMaterias){
		row++;	
		
		String alumnoNombre 	= "<span class='badge'>0</span>";		
		if(mapaAlumnos.containsKey(curso.getCodigoPersonal())){
			alumnoNombre 		= mapaAlumnos.get(curso.getCodigoPersonal());
		}		
		
		String carreraNombre = "";
		if (mapaCarreras.containsKey(curso.getCarreraId())){
			carreraNombre = mapaCarreras.get(curso.getCarreraId()).getNombreCarrera();
		}
		
		String facultad	= "-";
		if (mapaCarreras.containsKey(curso.getCarreraId())){
			facultad	= mapaCarreras.get(curso.getCarreraId()).getFacultadId();
		}
		
		
// 		int 0 = 0;
// 		String practicas = "";
// 		if (mapaPracticas.containsKey(facultad)) {
// 			pra 		= Integer.parseInt(mapaPracticas.get(facultad));
// 			practicas 	= "<span class='badge badge-inverse'>"+String.valueOf(pra)+"</span>";		
// 		}
		
		String fechas = "";
		if (mapaFechas.containsKey(curso.getCursoCargaId())){
			fechas = mapaFechas.get(curso.getCursoCargaId());
		}
		
		String libre = "-";
		if (mapaLibres.containsKey(curso.getCodigoPersonal()+"1")){
			libre = mapaLibres.get(curso.getCodigoPersonal()+"1");
		}
		
		String residencia = "-";
		if (mapaRes.containsKey(curso.getCodigoPersonal())){
			residencia = mapaRes.get(curso.getCodigoPersonal()).getResidenciaId();
		}
		
		int creditos = Integer.parseInt(curso.getCreditos());
		int hp = Integer.parseInt(curso.getHp());
		int ht = Integer.parseInt(curso.getHt());
		
		totalCreditos 	= totalCreditos + creditos;
		totalHPracticas	= totalHPracticas + hp;
		totalHTeoria 	= totalHTeoria + ht;
		
%>
		<tr>
			<td><%=row%></td>
			<td><%=curso.getCodigoPersonal()%></td>
			<td><%=alumnoNombre%></td>	
			<td><%=residencia.equals("E")?"Externo":"Interno" %></td>		
			<td><%=curso.getPlanId()%></td>
			<td><%=curso.getCarreraId()+"-"+carreraNombre%></td>
			<td><%=curso.getBloqueId()%></td>
			<td><%=curso.getNombreCurso()%></td>
			<td class="right"><%=curso.getCreditos()%></td>
			<td><%=curso.getHt()%></td>
			<td><%=curso.getHp()%></td>
			<td><%=fechas%></td>
			<td><%=libre%></td>	
					
		</tr>
<% 		}%>	
		<tr style="background-color: #E6E6E6; ">
			<td colspan="8" class="center" style="font-weight: bold;">Total</td>
			<td style="font-weight: bold;"><%=totalCreditos%></td>
			<td style="font-weight: bold;"><%=totalHTeoria%></td>
			<td style="font-weight: bold;"><%=totalHPracticas%></td>
			<td></td>
			<td></td>
		</tr>


	</tbody>
	</table>	
</div>
</body>
<script type="text/javascript">
	function Actualizar(){		
		document.frmProceso.submit();
	}
</script>
</html>
