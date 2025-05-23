<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.salida.spring.SalClub"%>
<%@page import="aca.salida.spring.SalAlumno"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.alumno.spring.AlumEstado"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<script type="text/javascript">
	function Borrar(salidaId, codAlumno) {
		if (confirm("Estas seguro de eliminar el registro: " + codAlumno) == true) {
			document.location.href = "accionAlumno?Accion=4&salida=" + salidaId + "&Alumno=" + codAlumno;
		}
	}
	
	function Mostrar(){
		document.frmBusqueda.Accion.value = "1";
		document.frmBusqueda.submit();
	}
	
	function Grabar(){		
		document.frmBusqueda.Accion.value = "2";
		document.frmBusqueda.submit();
	}
	
	function cambiaFacultad(){
		document.frmBusqueda.submit();
	}
	
	function GrabarAlumnos(){
		document.frmGrupo.submit();
	}
	
	function BorrarAlumnos(salidaId) {
		if (confirm("¿ Estás seguro de eliminar todos los alumnos registrados en la salida ?") == true) {
			document.location.href = "borrarAlumnos?salida="+salidaId;
		}
	}
	
</script>

<%
	String codigoAlumno		= (String)request.getAttribute("codigoAlumno");
	
	String salidaId 	= (String)request.getAttribute("salidaId");
	String modalidad 	= (String)request.getAttribute("modalidad");
	String facultad 	= (String)request.getAttribute("facultad");
	String carreraId 	= (String)request.getAttribute("carreraId");
	String grado 		= (String)request.getAttribute("grado");
	String residencia 	= (String)request.getAttribute("residencia");
	String dormitorio 	= (String)request.getAttribute("dormitorio");
	String alumnos		= "";

	String nombreAlumno	= (String)request.getAttribute("nombreAlumno");

	List<SalAlumno> lisAlumno 	= (List<SalAlumno>)request.getAttribute("lisAlumno");
	List<AlumEstado> lisFiltro	= (List<AlumEstado>)request.getAttribute("lisFiltro");
	List<CatFacultad> listFacu 	= (List<CatFacultad>)request.getAttribute("listFacu");
	List<CatCarrera> listCar 	= (List<CatCarrera>)request.getAttribute("listCar");
	List<SalClub> lisClubes 	= (List<SalClub>) request.getAttribute("lisClubes");
	
	HashMap<String, AlumPersonal> mapaInscritos = (HashMap<String, AlumPersonal>) request.getAttribute("mapaInscritos");
	HashMap<String, CatFacultad> mapaFacultad 	= (HashMap<String, CatFacultad>) request.getAttribute("mapaFacultad");
	HashMap<String, CatCarrera> mapaCarrera 	= (HashMap<String, CatCarrera>) request.getAttribute("mapaCarrera");
	
	for (SalAlumno salAlumno : lisAlumno){
		alumnos += salAlumno.getCodigoPersonal()+",";
	}	
%>
<div class="container-fluid">
	<h2><a class="btn btn-primary btn-sm" href="solicitud"><i class="fas fa-arrow-left icon-white"></i></a> Listado de alumnos</h2>
	<div class="alert alert-info ">
		Alumno:	
		<% if(!codigoAlumno.substring(0,2).equals("98")){%>
			<input type="text" value="<%=nombreAlumno%>" class="input-xxlarge" readonly /> &nbsp;
			<a class="btn btn-success btn-small" href="accionAlumno?salida=<%=salidaId%>&Alumno=<%=codigoAlumno%>&Accion=2"><i class="fas fa-plus icon-white"></i>&nbsp;<spring:message code='aca.Añadir'/></a>
		<%}else{%>
			<strong> - Para añadir alumos a la lista usar la búsqueda - </strong>
		<%}%>
	</div>
	<form id="frmGrupo" name="frmGrupo" method="post" action="grabarAlumnos" target="_self">
	<input type="hidden" name="salida" value="<%=salidaId%>">	
	<div class="alert alert-info d-flex align-items-center">
		<b>Grupo:</b>&nbsp;				
		<select name="GrupoId" id="GrupoId" style="width:300px;" class="form-select">
<%		for (aca.salida.spring.SalClub grupo : lisClubes) {%>
			<option value="<%=grupo.getGrupoId()%>"><%=grupo.getGrupoNombre()%> (<%=grupo.getAlumnos().split(",").length%> alumnos)</option>
<%		}%>
		</select>&nbsp;	
<% 		if(lisClubes.size() >= 1){%>
		<a class="btn btn-success btn-small" href="javascript:GrabarAlumnos();"><i class="fas fa-plus icon-white"></i>&nbsp;<spring:message code='aca.Añadir'/></a>&nbsp;&nbsp;
<% 		}%>		
	</div>
	</form>
	<form id="frmBusqueda" name="frmBusqueda" method="post" action="alumnos" target="_self">
	<input type="hidden" name="Accion">
	<input type="hidden" name="modalidad" value="1">
	<input type="hidden" name="salida" value="<%=salidaId%>">
		
	<div class="alert alert-info d-flex align-items-center">
		<i class="fas fa-filter"></i>
		<b>Filtros |</b>&nbsp;&nbsp;
		<b>Facultad:</b>&nbsp;				
		<select name="facultad" id="facultad" style="width:120px;" class="form-select" onchange='javascript:cambiaFacultad()'>
			<option value="000" <%=facultad.equals("000")?"Selected":""%>>Todas</option>
	<%	for (int i = 0; i < listFacu.size(); i++) {
			CatFacultad facu = listFacu.get(i);
	%>
			<option value="<%=facu.getFacultadId()%>" <%=facultad.equals(facu.getFacultadId())?"Selected":""%>>
				<%=facu.getNombreCorto()%>
			</option>
	<%
		}
	%>
		</select>&nbsp;&nbsp;
 		<b>Carrera:</b>&nbsp;
 		<select name="carreraId" id="carreraId" style="width:120px;" class="form-select">
 			<option value="00000" <%=carreraId.equals("00000")?"Selected":""%>>Todas</option>
 	<%	for (int i = 0; i < listCar.size(); i++) {
				CatCarrera carrera = listCar.get(i);
	%>
			<option value="<%=carrera.getCarreraId()%>" <%=carreraId.equals(carrera.getCarreraId())?"Selected":""%>>
				<%=carrera.getNombreCarrera()%>
			</option>
	<%
		}
	%>
 		</select>&nbsp;&nbsp;
 		<b>Grado:</b>&nbsp;				
		<select name="grado" id="grado" style="width:120px;" class="form-select">
			<option value="0" <%=grado.equals("0")?"Selected":""%>>Todos</option>
			<option value="1" <%=grado.equals("1")?"Selected":""%>>1</option>
			<option value="2" <%=grado.equals("2")?"Selected":""%>>2</option>						
			<option value="3" <%=grado.equals("3")?"Selected":""%>>3</option>
			<option value="4" <%=grado.equals("4")?"Selected":""%>>4</option>
			<option value="5" <%=grado.equals("5")?"Selected":""%>>5</option>
			<option value="6" <%=grado.equals("6")?"Selected":""%>>6</option>
		</select>&nbsp;&nbsp;
		<b>Residencia:</b>&nbsp;				
		<select name="residencia" id="residencia" style="width:120px;" class="form-select">	
			<option value="0" <%=residencia.equals("0")?"Selected":""%>>Todas</option>				
			<option value="E" <%=residencia.equals("E")?"Selected":""%>>Externo</option>
			<option value="I" <%=residencia.equals("I")?"Selected":""%>>Interno</option>
		</select>&nbsp;&nbsp;
		<b>Dormitorio:</b>&nbsp;				
		<select name="dormitorio" id="dormitorio" style="width:120px;" class="form-select">
			<option value="0" <%=dormitorio.equals("0")?"Selected":""%>>Todos</option>
			<option value="1" <%=dormitorio.equals("1")?"Selected":""%>>Dorm.1</option>
			<option value="2" <%=dormitorio.equals("2")?"Selected":""%>>Dorm.2</option>						
			<option value="3" <%=dormitorio.equals("3")?"Selected":""%>>Dorm.3</option>
			<option value="4" <%=dormitorio.equals("4")?"Selected":""%>>Dorm.4</option>
		</select>
		&nbsp;&nbsp;
		<a class="btn btn-primary btn-small" href="javascript:Mostrar();"><i class="fas fa-search icon-white"></i> Buscar</a>&nbsp;&nbsp;
		<a class="btn btn-success btn-small" href="javascript:Grabar()"><i class="fas fa-plus icon-white"></i>&nbsp;<spring:message code='aca.Añadir'/></a>&nbsp;&nbsp;
	</div>
	<br>
	<table class="table table-sm table-bordered" style="width:100%;">
	<tr class="alert alert-info">
		<td colspan="9"><h3>Busqueda de alumnos</h3></td>
	</tr>
	<tr>		
		<th width="6%">&nbsp;
			<a onclick="jQuery('.checkboxAlumno').prop('checked',true)" class="btn btn-info btn-sm" title="Todos"><i class="fas fa-check-square icon-white"></i></a>
			<a onclick="jQuery('.checkboxAlumno').prop('checked', false)" class="btn btn-info btn-sm" title="Ninguno"><i class="fas fa-redo icon-white"></i></a>
		</th>
		<th width="2%" style="text-align:center;">#</th>
		<th width="5%" style="text-align:center;"><spring:message code="aca.Matricula"/></th>
		<th width="15%"><spring:message code="aca.Nombre"/></th>
		<th width="8%"><spring:message code="aca.Facultad"/></th>
		<th width="10%"><spring:message code="aca.Carrera"/></th>
		<th width="5%"><spring:message code="aca.Residencia"/></th>
		<th width="5%" style="text-align:center;"><spring:message code="aca.Dormitorio"/></th>
		<th width="5%"><spring:message code="aca.Grado"/></th>
	</tr>
<%
		int row = 0;
		for(AlumEstado alumno :  lisFiltro){
			row++;
			String selected = "";
			if (alumnos.contains(alumno.getCodigoPersonal()+",")){
				selected = "checked";
			}
			String nombre = "";
			String nombreFacultad = "";
			String nombreCarrera = "";
			
			if(mapaInscritos.containsKey(alumno.getCodigoPersonal())){
				nombre = mapaInscritos.get(alumno.getCodigoPersonal()).getNombre();
			}
			if(mapaFacultad.containsKey(alumno.getFacultadId())){
				nombreFacultad = mapaFacultad.get(alumno.getFacultadId()).getNombreCorto();
			}
			if(mapaCarrera.containsKey(alumno.getCarreraId())){
				nombreCarrera = mapaCarrera.get(alumno.getCarreraId()).getNombreCorto();
			}
%>
	<tr>
		<td style="text-align:center;">
			<input class="checkboxAlumno" type="checkbox" id="<%=alumno.getCodigoPersonal()%>" name="<%=alumno.getCodigoPersonal()%>" value="S" <%=selected%>/>
		</td>
		<td style="text-align:center;"><%=row%></td>
		<td style="text-align:center;"><%=alumno.getCodigoPersonal()%></td>
		<td><%=nombre%></td>
		<td><%=nombreFacultad%></td>
		<td><%=nombreCarrera%></td>				
		<td><%=alumno.getResidenciaId().equals("E")?"Externo":"Interno"%></td>	
		<td style="text-align:center;"><%=alumno.getDormitorio()%></td>				
		<td style="text-align:center;"><%=alumno.getGrado()%></td>
	</tr>
<%		}	%>
	</table>
	</form>
	<br>
	<table class="table table-sm table-bordered">
	<tr class="alert alert-success">
		<td colspan="5">
			<h3>Alumnos registrados &nbsp; <a href="javascript:BorrarAlumnos('<%=salidaId%>');" class="btn btn-danger"><i class="fas fa-trash-alt icon-white"></i></a></h3></td>
	</tr>	
	<tr>
		<th width="5%" style="text-align:center;">#</th>
		<th width="10%" style="text-align:center;"><spring:message code="aca.Matricula"/></th>
		<th width="70%"><spring:message code="aca.Nombre"/></th>
		<th width="10%"><spring:message code="aca.Fecha"/></th>
		<th width="5%" style="text-align:center;"><spring:message code="aca.Operacion"/></th>
	</tr>		
<%
	int row2 = 0;
	for(int i=0; i<lisAlumno.size();i++ ){
		SalAlumno alumno = lisAlumno.get(i);
		row2++;
		String nombre = "";
		if(mapaInscritos.containsKey(alumno.getCodigoPersonal())){
			nombre = mapaInscritos.get(alumno.getCodigoPersonal()).getNombre();
		}
%>
	<tr>
		<td style="text-align:center;"><%=row2%></td>
		<td style="text-align:center;"><%=alumno.getCodigoPersonal()%></td>
		<td><%=nombre%></td>
		<td><%=alumno.getFecha()%></td>
		<td style="text-align:center;">
			<a href="javascript:Borrar('<%=alumno.getSalidaId()%>','<%=alumno.getCodigoPersonal()%>')" class="fas fa-trash-alt" title="Eliminar"></a>
		</td>
	</tr>
<%	} %>
	</table>			
</div>