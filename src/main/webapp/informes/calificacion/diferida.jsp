<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.kardex.spring.KrdxCursoAct"%>
<%@page import="aca.emp.spring.Empleado"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.carga.spring.CargaGrupo"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatTipoCal"%>
<%@page import="aca.plan.spring.MapaCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	
	// lista de materias con nota diferida 
	List<KrdxCursoAct> lisDiferidas				= (List<KrdxCursoAct>)request.getAttribute("lisDiferidas");	
	// Map de alumnos con diferidas
	HashMap<String,AlumPersonal> mapAlumnos 	= (HashMap<String,AlumPersonal>)request.getAttribute("mapAlumnos");	
	// Map de empleados con materias en estado diferido	
	HashMap<String,Empleado> mapEmpleados		= (HashMap<String,Empleado>)request.getAttribute("mapEmpleados");
	// Map de grupos con diferidas
	HashMap<String,CargaGrupo> mapGrupos		= (HashMap<String,CargaGrupo>)request.getAttribute("mapGrupos");
	HashMap<String,CatFacultad> mapFacultades	= (HashMap<String,CatFacultad>)request.getAttribute("mapFacultades");
	HashMap<String,CatCarrera> mapCarreras		= (HashMap<String,CatCarrera>)request.getAttribute("mapCarreras");
	HashMap<String,MapaCurso> mapCursos			= (HashMap<String,MapaCurso>)request.getAttribute("mapCursos");
	HashMap<String,CatTipoCal> mapTipos			= (HashMap<String,CatTipoCal>)request.getAttribute("mapTipos");
	HashMap<String,String> mapFechas			= (HashMap<String,String>)request.getAttribute("mapFechas");
%>
<div class="container-fluid">
	<h2>Calificaciones Diferidas</h2>
	<table class="table table-bordered">
	<thead class="table-info">
	<tr>
  		<th width="2%" align="center"><spring:message code="aca.Numero"/></th>
  		<th width="3%" align="center"><spring:message code="aca.Facultad"/></th>
  		<th width="5%" align="center"><spring:message code="aca.Carrera"/></th>
  		<th width="5%" align="center"><spring:message code="aca.Matricula"/></th>
  		<th width="19%" align="left"><spring:message code="aca.Nombre"/></th>
  		<th width="19%" align="center"><spring:message code="aca.Maestro"/></th>
  		<th width="5%" align="center"><spring:message code="aca.Acta"/></th>
  		<th width="19%" align="center"><spring:message code="aca.Materia"/></th>
  		<th width="4%" align="center"><spring:message code="aca.Tipo"/></th>  	
  		<th width="4%" align="center"><spring:message code="aca.Nota"/></th>
  		<th width="5%" align="center">Evaluado</th>
  		<th width="5%" align="center">Vencimiento</th>
  		<th width="5%" align="center"><spring:message code="aca.Estado"/></th>
	</tr>
	</thead>
 <%	
 	int row=0;
	for(KrdxCursoAct cal : lisDiferidas){
		row++;
		
 		String empleadoId		= ""; 		
 		String empleadoNombre	= "";
 		String estado 			= "-";
 		String carreraId		= "0";
 		if (mapGrupos.containsKey( cal.getCursoCargaId())){
 			empleadoId 	= mapGrupos.get( cal.getCursoCargaId()).getCodigoPersonal();
 			estado 		= mapGrupos.get( cal.getCursoCargaId()).getEstado();
 			carreraId	= mapGrupos.get( cal.getCursoCargaId()).getCarreraId();
 			if (mapEmpleados.containsKey( empleadoId )){
 				Empleado empleado 	= (Empleado) mapEmpleados.get( empleadoId );
 				empleadoNombre 		= empleado.getNombre()+" "+empleado.getAppaterno()+" "+empleado.getApmaterno(); 
 			}
 		}
 		
 		String facultadId		= "0";
 		String facultadNombre 	= "-";
		String carreraNombre 	= "-";
		if (mapCarreras.containsKey(carreraId)){
			facultadId 		= mapCarreras.get(carreraId).getFacultadId();
			carreraNombre 	= mapCarreras.get(carreraId).getNombreCorto();
			if (mapFacultades.containsKey(facultadId)){
				facultadNombre = mapFacultades.get(facultadId).getNombreCorto();
			}
		}
			
 		if (estado.equals("1"))	estado = "Creada";
 		else if (estado.equals("2")) estado = "Ordinario";
 		else if (estado.equals("3")) estado = "Extra";
 		else if (estado.equals("4")) estado = "Cerrada";
 		else if (estado.equals("5")) estado = "Registrada";
 		else estado = "-";
		
		String alumnoNombre		= "";
		if (mapAlumnos.containsKey( cal.getCodigoPersonal() )){
			AlumPersonal alumno = (AlumPersonal) mapAlumnos.get( cal.getCodigoPersonal());
			alumnoNombre = alumno.getNombre()+" "+alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno();
		}
		
		String cursoNombre = "-";
		if (mapCursos.containsKey(cal.getCursoId())){
			cursoNombre = mapCursos.get(cal.getCursoId()).getNombreCurso();
		}
		
		String tipoNombre = "-";
		if (mapTipos.containsKey(cal.getTipoCalId())){
			tipoNombre = mapTipos.get(cal.getTipoCalId()).getNombreCorto();
		}
		
		String fechaFinal = "<span class='badge bg-warning'>Sin Fecha</span>";
		if (mapFechas.containsKey(cal.getCodigoPersonal()+cal.getCursoCargaId())){
			fechaFinal = mapFechas.get(cal.getCodigoPersonal()+cal.getCursoCargaId());
		}
%>
	<tr class="tr2">
  		<td align="center"><%=row%></td>
  		<td><%=facultadNombre %></td>
  		<td><%=carreraNombre%></td>
  		<td><%=cal.getCodigoPersonal() %></td>
  		<td align="left"><%=alumnoNombre %></td>
  		<td align="left"><%=empleadoNombre %></td>
  		<td align="left"><%=cal.getCursoCargaId()%></td>
  		<td align="left"><%=cursoNombre %></td>
  		<td align="center"><%=tipoNombre%></td>
  		<td align="center"><%=cal.getNota() %></td>
  		<td align="center"><%=cal.getfNota()%></td>
  		<td align="center"><%=fechaFinal%></td>
  		<td align="center"><%=estado%></td>
	</tr>
<%	} %>
</table>
</div>