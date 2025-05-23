<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>

<jsp:useBean id="AfisRegistroUtil" scope="page" class="aca.afis.AfisRegistroUtil"/>
<jsp:useBean id="AfisPeriodoUtil" scope="page" class="aca.afis.AfisPeriodoUtil"/>
<jsp:useBean id="AfisPeriodo" scope="page" class="aca.afis.AfisPeriodo"/>
<jsp:useBean id="FacultadU" scope="page" class="aca.catalogo.CatFacultadUtil"/>
<jsp:useBean id="CarreraU" scope="page" class="aca.catalogo.CatCarreraUtil"/>
<jsp:useBean id="Alumno" scope="page" class="aca.alumno.AlumUtil"/>

<script type="text/javascript">
	function eliminar(periodoId, codigoPersonal){
		var r = confirm("Vas a borrar el periodo "+periodoId+" con matricula "+codigoPersonal+" ?");
		if (r == true) {
			window.location.href = "borrarAlumno?PeriodoId="+periodoId+"&CodigoPersonal="+codigoPersonal;
		}
	}
</script>
<%
	SimpleDateFormat formatter 	= new SimpleDateFormat("dd/MM/yyyy");
	String codigoPersonal		= (String) session.getAttribute("codigoAlumno");

	String accion 				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String periodoId 			= request.getParameter("PeriodoId")==null?"1":request.getParameter("PeriodoId");
	
	Date hoy 					= formatter.parse(aca.util.Fecha.getHoy());
	Date fechaIni 				= formatter.parse("01/01/2000");	
	Date fechaFin 				= formatter.parse("31/12/2000");	
	
	AfisPeriodo.setPeriodoId(periodoId);
	if (AfisPeriodoUtil.existeReg(conEnoc, periodoId)){
		AfisPeriodo = AfisPeriodoUtil.mapeaRegId(conEnoc, periodoId);
		fechaIni 	= formatter.parse(AfisPeriodo.getFechaIni());	
		fechaFin 	= formatter.parse(AfisPeriodo.getFechaFin());
	}
		
	String filtro															= AfisPeriodo.getFiltro();
	ArrayList<aca.afis.AfisRegistro> listaAlumnos							= AfisRegistroUtil.getListAllPorPeriodo(conEnoc, periodoId, " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
	ArrayList<aca.afis.AfisPeriodo> listaPeriodos							= AfisPeriodoUtil.getAll(conEnoc, " ORDER BY PERIODO_ID");
	
	java.util.HashMap<String, String> mapPeriodosActivos					= AfisPeriodoUtil.mapPeriodosActivos(conEnoc);
	java.util.HashMap<String, aca.afis.AfisRegistro> mapAlumnosPorPeriodo	= AfisRegistroUtil.mapAlumnosPorPeriodo(conEnoc, periodoId);
	java.util.HashMap<String, aca.catalogo.CatFacultad> mapFacultades		= FacultadU.getMapFacultad(conEnoc, "");
	java.util.HashMap<String, aca.catalogo.CatCarrera> mapCarreras			= CarreraU.getMapAll(conEnoc, "");
%>
<div class="container-fluid">
	<h1>Alumnos registrados</h1>
<%
	if(accion.equals("2")){
%>
		<div class="alert alert-success">¡Registro Borrado!</div>
<%
	}
%>
	<form name="forma" action="listado">
		<input type="hidden" name="Accion">
		<div class="alert alert-info d-flex align-items-center">	
			<select id="PeriodoId" name="PeriodoId" class="form-select" onChange="document.forma.submit();" style="width:400px">
<%
			for(aca.afis.AfisPeriodo periodo : listaPeriodos){				
%>
				<option value="<%=periodo.getPeriodoId()%>" <%=periodo.getPeriodoId().equals(periodoId)?"selected":""%>>[<%=periodo.getPeriodoId()%>] | <%=periodo.getPeriodoNombre()%>(<%=periodo.getFiltro()%>)</option>
<%				
			}
%>
			</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<%
			boolean alumnoFiltro = false;
			// Valida si el alumno cumple la condicion del filtro
			if (filtro.equals("TODO") || aca.kardex.KrdxCursoAct.tieneMateriaDeAptitud(conEnoc, codigoPersonal, filtro, "'I'")){
				alumnoFiltro = true;			 
			}
			
			if(mapPeriodosActivos.containsKey(periodoId) && alumnoFiltro){
%>			
				<strong>
				<a class="btn btn-primary" href="accionAlumno.jsp?PeriodoId=<%=periodoId%>" title="Nuevo"><i class="icon-plus icon-white"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</strong>						
<%
			}		
%>
				<strong>
					<%=codigoPersonal%> | <%=Alumno.getNombre(conEnoc,codigoPersonal,"")%>&nbsp;&nbsp;&nbsp;
<%			
			if (mapAlumnosPorPeriodo.containsKey(codigoPersonal)) 
				out.print("<samp style='color:#00cc00'><strong>¡Registrado!</strong></samp>");
			else
				out.print("<samp style='color:red'><strong>¡Sin Registro!</strong></samp>");

			if (!alumnoFiltro)			
				out.print("&nbsp;&nbsp;&nbsp;&nbsp;<samp style='color:red'><strong>¡No lleva la materia!</strong></samp>");
%>							
				</strong>
		</div>
	</form>
	<table class="table table-bordered table-striped">
		<tr>
			<th>#</th>
			<th>Opción</th>
			<th>Codigo personal</th>
			<th>Nombre</th>
			<th>Genero</th>
			<th>Residencia</th>
			<th>Facultad</th>
			<th>Carrera</th>
			<th>Fecha registro</th>
			<th>Peso(Kg)</th>
			<th>Talla(m)</th>
			<th>Circ. Abd.(cm)</th>
			<th>%Grasa</th>
			<th>%Masa Musc.</th>
			<th>IMC</th>
			<th>Dieta</th>
		</tr>
<%
		int con = 0;
		for(aca.afis.AfisRegistro periodo : listaAlumnos){
			con++;
			String dieta = "";
			if (periodo.getDieta().equals("0")) dieta = "-";
			if (periodo.getDieta().equals("1")) dieta = "Vegetariano";
			if (periodo.getDieta().equals("2")) dieta = "No Vegetariano";
			
			String facultadId 		= "0";
			String nombreFacultad	= "";
			String nombreCarrera 	= "";
			if (mapCarreras.containsKey( periodo.getCarreraId())){
				nombreCarrera 	= mapCarreras.get(periodo.getCarreraId()).getNombreCarrera();
				facultadId 		= mapCarreras.get(periodo.getCarreraId()).getFacultadId();
				if (mapFacultades.containsKey(facultadId)){
					nombreFacultad = mapFacultades.get(facultadId).getNombreCorto();
				}
			}
%>
		<tr>
			<td><%=con %></td>
			<td>
<%
			if( hoy.getTime() < fechaFin.getTime() && hoy.getTime() > fechaIni.getTime()){
				if(mapPeriodosActivos.containsKey(periodo.getPeriodoId())){			
%>
				<a href="accionAlumno.jsp?PeriodoId=<%=periodo.getPeriodoId()%>&CodigoPersonal=<%=periodo.getCodigoPersonal()%>&Accion=2" class="btn btn-primary"><i class="icon-white icon-pencil" title="Editar"></i></a> 
<%
				}
%>
				<a href="javascript:eliminar('<%=periodo.getPeriodoId()%>','<%=periodo.getCodigoPersonal()%>');" class="btn btn-danger"><i class="icon-white fas fa-trash-alt" title="Eliminar"></i></a>
<%
			}
%>		
			</td>
			<td><%=periodo.getCodigoPersonal() %></td>
			<td><%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, periodo.getCodigoPersonal(), "APELLIDO")%></td>
			<td><%=aca.alumno.AlumUtil.getGenero(conEnoc, periodo.getCodigoPersonal()).equals("F")?"Mujer":"Hombre"%></td>
			<td><%=periodo.getResidencia().equals("E")?"Externo":periodo.getResidencia().equals("I")?"Interno":periodo.getResidencia().equals("i")?"Interno":"-"%></td>
			<td><%=nombreFacultad%></td>
			<td><%=nombreCarrera%></td>
			<td><%=periodo.getFecha() %></td>
			<td><%=periodo.getPeso() %></td>
			<td><%=periodo.getTalla() %></td>
			<td><%=periodo.getCintura() %></td>
			<td><%=periodo.getGrasa() %></td>
			<td><%=periodo.getMusculo() %></td>
			<td><%=periodo.getImc() %></td>						
			<td><%=dieta%></td>
		</tr>
<%	
		}
%>	
	</table>
</div>

<%@ include file= "../../cierra_enoc.jsp" %>