<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.Date,java.text.SimpleDateFormat"%>
<%@ page import="aca.carga.spring.CargaAlumno"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.alumno.spring.AlumAcademico"%>
<%@ page import="aca.alumno.spring.AlumUbicacion"%>
<%@ page import="aca.alumno.spring.AlumPlan"%>
<%@ page import="aca.candado.spring.CandTipo"%>
<%@ page import="aca.candado.spring.CandAlumno"%>
<%@ page import="aca.candado.spring.Candado"%>
<%@ page import="aca.financiero.spring.AyudaEstudios"%>
<%@ page import="aca.carga.spring.CargaBloque"%>
<%@ page import="aca.plan.spring.MapaPlan"%>
<%@page import= "aca.catalogo.spring.CatCarrera"%>
<%@page import= "aca.catalogo.spring.CatNivel"%>
<%@page import= "aca.catalogo.spring.CatModalidad"%>
<%@page import= "aca.catalogo.spring.CatReligion"%>
<%@page import= "aca.catalogo.spring.CatCultural"%>
<%@page import= "aca.catalogo.spring.CatRegion"%>
<%@page import= "aca.catalogo.spring.CatPais"%>
<%@page import= "aca.catalogo.spring.CatEstado"%>
<%@page import= "aca.catalogo.spring.CatCiudad"%>
<%@page import= "aca.internado.spring.IntDormitorio"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ include file="../alumno/menu.jsp"%>

<head></head>
<%
	DecimalFormat frmDecimal 	= new DecimalFormat("###,##0.00;(###,##0.00)");

	String codigoPersonal 		= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno 		= (String) session.getAttribute("codigoAlumno");
	String opcion 				= request.getParameter("Opcion") == null ? "1" : request.getParameter("Opcion");

	AlumPersonal alumPersonal 	= (AlumPersonal) request.getAttribute("alumPersonal");
	AlumAcademico alumAcademico = (AlumAcademico) request.getAttribute("alumAcademico");
	AlumUbicacion alumUbicacion = (AlumUbicacion) request.getAttribute("alumUbicacion");
	AlumPlan alumPlan 			= (AlumPlan) request.getAttribute("alumPlan");
	AyudaEstudios ayuda 		= (AyudaEstudios) request.getAttribute("ayuda");
	String nombreNacionalidad 	= (String) request.getAttribute("nombreNacionalidad");
	String razon 				= "";
	String fechaVencimiento 	= (String) request.getAttribute("fechaVencimiento");
	String nombreInstitucion 	= (String) request.getAttribute("nombreInstitucion");
	String nombreTipo 			= (String) request.getAttribute("nombreTipo");	
	String clasFin 				= (String) request.getAttribute("clasFin");
	String religionNombre		= (String) request.getAttribute("religionNombre");
	String planNombre			= (String) request.getAttribute("planNombre");
	boolean esExtranjero		= (boolean) request.getAttribute("extranjero");	
	boolean validaCurp 			= aca.alumno.AlumUtil.validarCurp(alumPersonal.getCurp());
	boolean vencioFM3 			= false;
	String nombreCandado 		= "";

	List<CandAlumno> lisCandado 			= (List<CandAlumno>) request.getAttribute("lisCandado");
	
	HashMap<String,CatCarrera> mapaCarreras			= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String,CatNivel> mapaNiveles			= (HashMap<String,CatNivel>)request.getAttribute("mapaNiveles");
	HashMap<String,CatModalidad> mapaModalidades	= (HashMap<String,CatModalidad>)request.getAttribute("mapaModalidades");
	HashMap<String,IntDormitorio> mapaDormitorios	= (HashMap<String,IntDormitorio>)request.getAttribute("mapaDormitorios");	
	HashMap<String,CatReligion> mapaReligiones		= (HashMap<String,CatReligion>)request.getAttribute("mapaReligiones");
	HashMap<String,CatCultural> mapaCultural		= (HashMap<String,CatCultural>)request.getAttribute("mapaCultural");
	HashMap<String,CatRegion> mapaRegion			= (HashMap<String,CatRegion>)request.getAttribute("mapaRegion");
	HashMap<String,CatPais> mapaPaises				= (HashMap<String,CatPais>)request.getAttribute("mapaPaises");
	HashMap<String,CatEstado> mapaEstados			= (HashMap<String,CatEstado>)request.getAttribute("mapaEstados");
	HashMap<String,CatCiudad> mapaCiudades			= (HashMap<String,CatCiudad>)request.getAttribute("mapaCiudades");
	
	session.removeAttribute("enLineaSiguiente");
	
	int con = 1;
%>
<div class="container-fluid mt-1">	
	<div class="alert alert-success">		
		<a href="previos"><i class="far fa-arrow-alt-circle-left fa-lg"></i></a>&nbsp;
		<span class="badge rounded-pill bg-dark">1A</span> <spring:message code="portal.alumno.validacion.AsuntosPrevios"/> - <spring:message code="portal.alumno.validacion.Datos"/>
		<small class="text-muted"> ( <%=codigoAlumno%> - <%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoMaterno()%> <%=alumPersonal.getApellidoPaterno()%> )</small>
	</div>
	<hr>
	<table class="table table-sm table-bordered">
	<thead class="table-dark">
	<tr>		
		<th width="15%"><spring:message code="portal.alumno.validacion.Datos"/></th>
		<th width="30%"><spring:message code="portal.alumno.validacion.Valores"/></th>
		<th width="45%"></th>
	</tr>
	</thead>
	<tr>		
		<td>&nbsp;&nbsp;<b>Name:</b></td>
		<td><%=alumPersonal.getNombre()%></td>
		<td></td>
	</tr>
	<tr>		
		<td>&nbsp;&nbsp;<b>Maiden Name:</b></td>
		<td><%=alumPersonal.getApellidoMaterno()%></td>
		<td></td>
	</tr>
	<tr>		
		<td>&nbsp;&nbsp;<b>Surname:</b></td>
		<td><%=alumPersonal.getApellidoPaterno()%></td>
		<td></td>
	</tr>
	<tr>		
		<td>&nbsp;&nbsp;<b>GOB ID:</b></td>
		<td><%=alumPersonal.getCurp()%></td>
		<td></td>
	</tr>
	<tr>		
		<td>&nbsp;&nbsp;<b>Gender:</b></td>
		<td><%=alumPersonal.getSexo().equals("M")?"Male":"Female"%></td>
		<td></td>
	</tr>	
	<tr>		
		<td>&nbsp;&nbsp;<b>Civil Status:</b></td>
		<td>
			<% if(alumPersonal.getEstadoCivil().equals("S"))out.print("Single"); %>
			<% if(alumPersonal.getEstadoCivil().equals("C"))out.print("Married"); %>
			<% if(alumPersonal.getEstadoCivil().equals("D"))out.print("Divorced"); %>
			<% if(alumPersonal.getEstadoCivil().equals("V"))out.print("Widowed"); %>
		</td>
		<td></td>
	</tr>
	<tr>
<%
		String religion = "";
	
		if(mapaReligiones.containsKey(alumPersonal.getReligionId())){
			religion = mapaReligiones.get(alumPersonal.getReligionId()).getNombreReligion();
		}
%>
		<td>&nbsp;&nbsp;<b>Denomination:</b></td>
		<td><%=religion%></td>
		<td></td>
	</tr>	
	<tr>		
		<td>&nbsp;&nbsp;<b>Baptized:</b></td>
		<td><%=alumPersonal.getBautizado().equals("S")?"YES":"NO"%></td>
		<td></td>
	</tr>	
<%
		String fechaBautizo = "";
	
		if(alumPersonal.getfBautizado() != null){
			fechaBautizo = alumPersonal.getfBautizado();
		}
%>
		<td>&nbsp;&nbsp;<b>Date of Baptism:</b></td>
		<td><%=fechaBautizo%></td>
		<td></td>
	</tr>
	<tr>		
		<td>&nbsp;&nbsp;<b><spring:message code="aca.Correo" />:</b></td>
		<td><%=alumPersonal.getEmail() == null ? "-" : alumPersonal.getEmail()%></td>
		<td></td>
	</tr>
	<tr>		
		<td>&nbsp;&nbsp;<b><spring:message code="aca.Celular" />:</b></td>
		<td><%=alumPersonal.getTelefono() == null ? "-" : alumPersonal.getTelefono()%></td>
		<td></td>
	</tr>
	<tr>
<%
		String cultural = "";
	
		if(mapaCultural.containsKey(alumPersonal.getCulturalId())){
			cultural = mapaCultural.get(alumPersonal.getCulturalId()).getNombreCultural();
		}
		
		String region = "";
		if(mapaRegion.containsKey(alumPersonal.getRegionId()+alumPersonal.getCulturalId())){
			region = mapaRegion.get(alumPersonal.getRegionId()+alumPersonal.getCulturalId()).getNombreRegion();
		}
%>
		<td>&nbsp;&nbsp;<b>Cultural Group:</b></td>
		<td><%=cultural%></td>
		<td></td>
	</tr>
	<tr>
		<td>&nbsp;&nbsp;<b>Provincial Group:</b></td>
		<td><%=region%></td>
		<td></td>	
	</tr>
<%
		String pais = "";
		String estado = "";
		String ciudad = "";

		String resPais = "";
		String resEstado = "";
		String resCiudad = "";

		String nacionalidad = "";
	
		if(mapaPaises.containsKey(alumPersonal.getPaisId()))
			pais = mapaPaises.get(alumPersonal.getPaisId()).getNombrePais();
		if(mapaPaises.containsKey(alumPersonal.getResPaisId()))
			resPais = mapaPaises.get(alumPersonal.getResPaisId()).getNombrePais();

		if(mapaPaises.containsKey(alumPersonal.getNacionalidad()))
			nacionalidad = mapaPaises.get(alumPersonal.getNacionalidad()).getNacionalidad();

		if(mapaEstados.containsKey(alumPersonal.getPaisId()+alumPersonal.getEstadoId()))
			estado = mapaEstados.get(alumPersonal.getPaisId()+alumPersonal.getEstadoId()).getNombreEstado();
		if(mapaEstados.containsKey(alumPersonal.getResPaisId()+alumPersonal.getResEstadoId()))
			resEstado = mapaEstados.get(alumPersonal.getResPaisId()+alumPersonal.getResEstadoId()).getNombreEstado();;

		if(mapaCiudades.containsKey(alumPersonal.getPaisId()+alumPersonal.getEstadoId()+alumPersonal.getCiudadId()))
			ciudad = mapaCiudades.get(alumPersonal.getPaisId()+alumPersonal.getEstadoId()+alumPersonal.getCiudadId()).getNombreCiudad();
		if(mapaCiudades.containsKey(alumPersonal.getResPaisId()+alumPersonal.getResEstadoId()+alumPersonal.getResCiudadId()))
			resCiudad = mapaCiudades.get(alumPersonal.getResPaisId()+alumPersonal.getResEstadoId()+alumPersonal.getResCiudadId()).getNombreCiudad();

%>
	<tr>
		<td>&nbsp;&nbsp;<b>Nationality:</b></td>
		<td><%=nacionalidad%></td>
		<td></td>
	</tr>
	<tr>
		<td>&nbsp;&nbsp;<b>Country:</b></td>
		<td><%=pais%></td>
		<td></td>
	</tr>
	<tr>
		<td>&nbsp;&nbsp;<b>Province:</b></td>
		<td><%=estado%></td>
		<td></td>	
	</tr>
	<tr>
		<td>&nbsp;&nbsp;<b>Village:</b></td>
		<td><%=ciudad%></td>
		<td></td>	
	</tr>
	<tr>
		<td>&nbsp;&nbsp;<b>Home Country:</b></td>
		<td><%=resPais%></td>
		<td></td>
	</tr>
	<tr>
		<td>&nbsp;&nbsp;<b>Home Province:</b></td>
		<td><%=resEstado%></td>
		<td></td>	
	</tr>
	<tr>
		<td>&nbsp;&nbsp;<b>Home Village:</b></td>
		<td><%=resCiudad%></td>
		<td></td>	
	</tr>
<%
		String modalidad = "";
	
		if(mapaModalidades.containsKey(alumAcademico.getModalidadId())){
			modalidad = mapaModalidades.get(alumAcademico.getModalidadId()).getNombreModalidad();
		}
		
		String dormitorio = "";
		if(mapaDormitorios.containsKey(alumAcademico.getDormitorio())){
			dormitorio = mapaDormitorios.get(alumAcademico.getDormitorio()).getNombre();
		}
%>
	<tr>
		<td>&nbsp;&nbsp;<b><spring:message code="portal.alumno.validacion.Modalidad"/>:</b></td>
		<td><%=modalidad%></td>
		<td></td>
	</tr>
	<tr>		
		<td>&nbsp;&nbsp;<b>Birth Date:</b></td>
		<td><%=alumPersonal.getFNacimiento()%></td>
		<td></td>
	</tr>
	<tr>		
		<td>&nbsp;&nbsp;<b><spring:message code='aca.Residencia' />:</b></td>
		<td><%=alumAcademico.getResidenciaId().equals("E")?"Day Student":"Boarding Student"%></td>
		<td>
<%		if (alumAcademico.getResidenciaId().equals("E")) out.print(razon); else out.print(dormitorio);%>
		</td>
	</tr>
	<tr>		
		<td>&nbsp;&nbsp;<b><spring:message code="portal.alumno.validacion.TipoDeAlumno"/>:</b></td>
		<td><%=nombreTipo%></td>
		<td></td>
	</tr>
	<tr>		
		<td>&nbsp;&nbsp;<b><spring:message code="portal.alumno.validacion.ClasificacionFinanciera"/>:</b></td>
		<% 
			String clasifiacion = "";
			if(clasFin.equals("1")){
				clasifiacion = "Adventist Student";
			}else{
				clasifiacion = "Non Adventist Student";
			}
		
		%>
 		<td><%=clasifiacion%></td>
 		<td><%=clasFin.equals("1") ? " ":religionNombre%></td>
	</tr>		
	<tr>		
		<td>&nbsp;&nbsp;<b><spring:message code="portal.alumno.validacion.Principal"/> <spring:message code="aca.Plan" />:</b></td>
		<td><%=alumPlan.getPlanId() %></td>
		<td><%=planNombre %></td>
	</tr>
	</table>
</div>
</body>