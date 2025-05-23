<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>

<%@ page import= "aca.catalogo.spring.CatPeriodo"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.vista.spring.AlumnoCurso"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.catalogo.spring.CatTipoCal"%>
<%@ page import= "aca.acceso.spring.Acceso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%
	String sCodigoPersonal			= (String) session.getAttribute("codigoPersonal");
	String periodoId				= (String) request.getAttribute("periodoId");
	String cargaId					= (String) request.getAttribute("cargaId");
	Acceso acceso					= (Acceso) request.getAttribute("acceso");
	boolean encuentraCarga			= false;
	
	List<CatPeriodo> lisPeriodos	= (List<CatPeriodo>) request.getAttribute("lisPeriodos");
	List<Carga> lisCargas 			= (List<Carga>) request.getAttribute("lisCargas");
	List<AlumnoCurso> lisAlumnos 	= (List<AlumnoCurso>) request.getAttribute("lisAlumnos");
	HashMap<String,CatFacultad> mapaFacultades 	= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras 	= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,CatTipoCal> mapaTipos 		= (HashMap<String,CatTipoCal>) request.getAttribute("mapaTipos");
	HashMap<String,String> mapaMaestros 		= (HashMap<String,String>) request.getAttribute("mapaMaestros");
	HashMap<String,String> mapaAlumnos 			= (HashMap<String,String>) request.getAttribute("mapaAlumnos");
%>		
<div class="container-fluid">
	<h2>Análisis de componentes</h2>
	<form name="forma" action="carga" method="post">
	<div class="alert alert-info">	
		<a class="btn btn-primary" href="../reportes/menu"><i class="fas fa-arrow-left"></i></a>	
		<b><font size="2"><spring:message code="aca.Periodo"/>:</font></b>
		<select onchange="javascript:document.forma.submit();" name="PeriodoId" class="input input-medium">
	<%	for(CatPeriodo periodo : lisPeriodos){%>
		<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print(" Selected ");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
	<%	}%>
    	</select>
		&nbsp;&nbsp;
		<b><spring:message code="aca.Carga"/>: </b>
		<select onchange='javascript:document.forma.submit();' name="CargaId" style="width:350px;" class="input input-xlarge">
			<option <%=cargaId.equals("000000")?" Selected":""%> value="000000">[000000] Elige una carga</option>
	<%	for(Carga carga : lisCargas){
			if (carga.getCargaId().equals(cargaId)) encuentraCarga = true;
	%>
			<option <%if(cargaId.equals(carga.getCargaId()))out.print(" Selected ");%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId() %>] <%=carga.getNombreCarga()%></option>
	<%	}%>
		</select>		
		&nbsp;&nbsp;
	</div>	
	</form>	
<table class="table table-bordered">
	<thead class="table-info">
	<tr>
		<th>#</th>
		<th>Facultad</th>
		<th>Carrera</th>
		<th>Acta</th>
		<th>Bloque</th>
		<th>Estado</th>
		<th>Maestro</th>		
		<th>Matrícula</th>
		<th>Alumno</th>
		<th>Plan</th>
		<th>Materia</th>
		<th class="right">Nota</th>
		<th class="right">Extra</th>
		<th>Tipo</th>
	</tr>
	</thead>
	<tbody>
<%
	int row=0;
	for (AlumnoCurso curso : lisAlumnos){
		
		if( acceso.getAccesos().indexOf(curso.getCarreraId())!= -1 || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") ){
		
			row++;		
			String facultadId 		= "0";
			String facultadNombre 	= "";
			String carreraNombre 	= "";		
			if (mapaCarreras.containsKey(curso.getCarreraId())){
				carreraNombre	= mapaCarreras.get(curso.getCarreraId()).getNombreCarrera();
				facultadId 		=  mapaCarreras.get(curso.getCarreraId()).getFacultadId();
				if (mapaFacultades.containsKey(facultadId)){
					facultadNombre = mapaFacultades.get(facultadId).getNombreCorto();
				}
			}
			
			String tipo = "";
			if (mapaTipos.containsKey(curso.getTipoCalId())){
				tipo = mapaTipos.get(curso.getTipoCalId()).getNombreCorto();
				if (tipo.equals("CD") || tipo.equals("Inscrito")) tipo = "<span class='badge bg-warning'>"+tipo+"</span>";
			}
			
			String alumnoNombre = "-";
			if (mapaAlumnos.containsKey(curso.getCodigoPersonal())){
				alumnoNombre = mapaAlumnos.get(curso.getCodigoPersonal());
			}
			
			String maestroNombre = "-";
			if (mapaMaestros.containsKey(curso.getMaestro())){
				maestroNombre = mapaMaestros.get(curso.getMaestro());
			}
			
			String estado = "-";
			if (curso.getEstado().equals("1")) estado = "<span class='badge bg-warning'>Creada</span>";
			if (curso.getEstado().equals("2")) estado = "<span class='badge bg-info'>Oridinario</span>";
			if (curso.getEstado().equals("3")) estado = "<span class='badge bg-success'>Extra</span>";
			if (curso.getEstado().equals("4")) estado = "<span class='badge bg-dark'>Cerrada</span>";
			if (curso.getEstado().equals("5")) estado = "<span class='badge bg-dark'>Registrada</span>";
%>
	<tr>
		<td><%=row%></td>
		<td><%=facultadNombre%></td>
		<td><%=carreraNombre%></td>
		<td><%=curso.getCursoCargaId()%></td>
		<td><%=curso.getBloqueId()%></td>
		<td><%=estado%></td>
		<td><%=maestroNombre%></td>		
		<td><%=curso.getCodigoPersonal()%></td>
		<td><%=alumnoNombre%></td>
		<td><%=curso.getPlanId()%></td>
		<td><%=curso.getNombreCurso()%></td>
		<td class="right"><%=curso.getNota()%></td>
		<td class="right"><%=curso.getNotaExtra()%></td>
		<td><%=tipo%></td>
	</tr>
<%		
		}
	}
%>	
	</tbody>
	</table>
</div>
