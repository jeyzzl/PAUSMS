<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.alumno.spring.AlumCovid"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.alerta.spring.AlertaPeriodo"%>
<%@page import="aca.alerta.spring.AlertaHistorial"%>
<%@page import="aca.alerta.spring.AlertaAntro"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.internado.spring.IntAcceso"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<head>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap4/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/fontawesome.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/solid.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/regular.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-table.min.css">
	<script src="<%=request.getContextPath()%>/js/jquery-3.6.0.min.js"></script>  	
	<script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
  	<script src="<%=request.getContextPath()%>/bootstrap4/js/bootstrap.min.js" type="text/javascript"></script> 
  	<script src="<%=request.getContextPath()%>/js/bootstrap-table.min.js" type="text/javascript"></script>
</head>
<style>
	body{
		background: white;
	} 
	
	input[type=checkbox]{
	  /* Double-sized Checkboxes */
	  -ms-transform: scale(1.5); /* IE */
	  -moz-transform: scale(1.5); /* FF */
	  -webkit-transform: scale(1.5); /* Safari and Chrome */
	  -o-transform: scale(1.5); /* Opera */
	  padding: 10px;
	}
	
	th{
		padding-right: 30px !important;
	}
</style>
<body>
<%	
	String periodoId			= (String) request.getAttribute("periodoId");
	String codigoAlumno			= (String) request.getAttribute("codigoAlumno");
	String nombreAlumno			= (String) request.getAttribute("nombreAlumno");	
	boolean existeAlumno		= (boolean) request.getAttribute("existeAlumno");
	boolean tieneAcceso			= (boolean) request.getAttribute("tieneAcceso");
	boolean esSuPreceptor		= (boolean) request.getAttribute("esSuPreceptor");	
	String planId				= (String) request.getAttribute("planId");
	String carreraId			= (String) request.getAttribute("carreraId");
	String carreraNombre		= (String) request.getAttribute("carreraNombre");
	String mensaje				= (String) request.getAttribute("Mensaje");
	
	Acceso acceso 				= (Acceso) request.getAttribute("acceso");
	IntAcceso intAcceso 		= (IntAcceso) request.getAttribute("intAcceso");
	AlumAcademico alumAcademico = (AlumAcademico) request.getAttribute("alumAcademico");
			
	List<AlumCovid> lisDatos 						= (List<AlumCovid>) request.getAttribute("lisDatos");
	List<AlertaPeriodo> lisPeriodos 				= (List<AlertaPeriodo>) request.getAttribute("lisPeriodos");
	HashMap<String,AlumAcademico> mapAcademicoCovid	= (HashMap<String,AlumAcademico>) request.getAttribute("mapAcademicoCovid");
	HashMap<String,String> mapEdadCovid				= (HashMap<String,String>) request.getAttribute("mapEdadCovid");
	HashMap<String,String> mapaAlumnos				= (HashMap<String,String>) request.getAttribute("mapaAlumnos");
	HashMap<String,String> mapaPlanAlumno			= (HashMap<String,String>) request.getAttribute("mapaPlanAlumno");	
	HashMap<String,MapaPlan> mapaPlanes				= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
	HashMap<String,String> mapaDocumentos 			= (HashMap<String,String>) request.getAttribute("mapaDocumentos");	
	HashMap<String,String> mapaDormitorio 			= (HashMap<String,String>) request.getAttribute("mapaDormitorio");
	HashMap<String,CatFacultad> mapaFacultades 		= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras	 		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	
	String filtro							= (String) request.getAttribute("filtro");	
	
	String datosResidencia = "Externo";
	
	if(alumAcademico.getResidenciaId().equals("I")){
		alumAcademico.getDormitorio();
		datosResidencia = "Interno (Dormitorio "+alumAcademico.getDormitorio()+")";
	}
%>
<div class="container-fluid">
	<h1>Alumnos registrados</h1>
	<form name="forma" action="datos" method="post">
	<div class="alert alert-info">		
		<select name="PeriodoId" id="PeriodoId" style="width:350px;" onchange="document.forma.submit()">
	<%	for(AlertaPeriodo periodo: lisPeriodos){ %> 
			<option value="<%=periodo.getPeriodoId() %>" <%if(periodoId.equals(periodo.getPeriodoId()))out.print("selected"); %>><%=periodo.getPeriodoNombre() %></option>
	<%	} %>
		</select>
		&nbsp;&nbsp;		
		<select name="Filtro" id="Filtro" style="width:150px;" onchange="document.forma.submit()">
			<option value="T" <%=filtro.equals("T")?"selected":"" %>>Todos</option>
			<option value="P" <%=filtro.equals("P")?"selected":"" %>>Positivo</option>
			<option value="S" <%=filtro.equals("S")?"selected":"" %>>Sospechoso</option>
			<option value="A" <%=filtro.equals("A")?"selected":"" %>>Aislamiento</option>
		</select>
		&nbsp;&nbsp;		
<!-- 		<input type="text" id="buscar" placeholder="Buscar..." size="20" maxlength="25" />&nbsp;&nbsp; -->
		<a title= "Alumnos nuevos para registrar" href="nuevos?PeriodoId=<%=periodoId%>" target="_blank"><i class="fas fa-search-plus fa-2x"></i></a>
	</div>
	<%	if (existeAlumno){ %>	
	<div class="alert alert-success">			
		<i class="fas fa-cloud-upload-alt fa-2x"></i>
		<%=codigoAlumno%> - <%=nombreAlumno%> - <%=planId%> - <%=carreraNombre%> - <%=datosResidencia%>&nbsp;	 
	<%		if (tieneAcceso || esSuPreceptor){ %>
		<a href="editar?PeriodoId=<%=periodoId%>" class="btn btn-primary"><i class="fas fa-plus"></i></a>
	<% 		}%>	
	</div>
	<%	} %>
	<%	if(existeAlumno==false){%>
	<div class="alert alert-danger" role="alert">
  		 Necesitas cosultar la información de un alumno en el buscador principal, localizado en la parte superior derecha.
	</div>
<% 	}else if(tieneAcceso==false){%>
	<div class="alert alert-warning" role="alert">
  		No tienes privilegios para agregar al alumno, ya que no estás autorizado como referente en su carrera/dormitorio actual.
	</div>
<% 	}%>
	</form>
<!-- 	<table class="table table-condesed table-bordered" id="table"> -->
<table id="table" class="table" data-toggle="table" data-pagination="false" data-search="true" data-show-columns="true" data-page-list="[10, 25, 50, 100, all]" data-show-header="true" data-show-footer="true">
	<thead>
	<tr>
		<th>#</th>
		<th>Op.</th>
		<th>Matrícula</th>
		<th>Nombre</th>
		<th>Residencia</th>
		<th>Edad</th>
		<th>Facultad</th>
		<th>Plan</th>
		<th>Tipo</th>
		<th>F. llegada</th>
		<th>Vac.</th>
		<th>F. Vac.</th>
		<th>Pos.</th>
		<th>F. Pos.</th>
		<th>Sosp.</th>
		<th>F. Sos.</th>
		<th>Ais.</th>
		<th>Fin Ais.</th>
		<th>Usuario</th>
		<th>F.Alta</th>
		<th>Val.</th>
		<th>Doc. Resp.</th>
		<th>Doc. Llega</th>
		<th>Doc. Vac.</th>
		<th>Doc. PCR</th>		
	</tr>
	</thead>
	<tbody>
<%
	int cont = 0;
	for(AlumCovid dato : lisDatos){
		
		String nombre = "-";
 		if(mapaAlumnos.containsKey(dato.getCodigoPersonal())){
 			nombre = mapaAlumnos.get(dato.getCodigoPersonal());
 		}
 		
 		String planAlumno = "0";
 		if(mapaPlanAlumno.containsKey(dato.getCodigoPersonal())){
 			planAlumno = mapaPlanAlumno.get(dato.getCodigoPersonal());
 		}
 		
 		String carreraAlumno = "0";
 		if(mapaPlanes.containsKey( planAlumno )){
 			carreraAlumno = mapaPlanes.get( planAlumno ).getCarreraId();
 		}
 		String facultadAlumno	= "0"; 
 		String facultadNombre	= "0";
 		if (mapaCarreras.containsKey(carreraAlumno)){
 			facultadAlumno = mapaCarreras.get(carreraAlumno).getFacultadId();
 			if (mapaFacultades.containsKey(facultadAlumno)){
 				facultadNombre = mapaFacultades.get(facultadAlumno).getNombreCorto();
 			}
 		}
 		
 		String residencia = "Externo";
 		if(mapAcademicoCovid.containsKey(dato.getCodigoPersonal())){
 			if(mapAcademicoCovid.get(dato.getCodigoPersonal()).getResidenciaId().equals("I")){
 				residencia = "Interno";
 			}
 		}
 		
 		String edad = "";
 		if(mapEdadCovid.containsKey(dato.getCodigoPersonal())){
 			edad = mapEdadCovid.get(dato.getCodigoPersonal());
 		}
 		
 		boolean muestraTodos = true;

 		if (acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") || acceso.getAccesos().contains(carreraAlumno) || intAcceso.getRol().equals("P")){
 			cont++;
 			
 			if(intAcceso.getRol().equals("P") && !mapaDormitorio.containsKey(dato.getCodigoPersonal())){
 				muestraTodos = false;
 			}
 			
			if(muestraTodos){
%>
		 
	<tr>
		<td><%=cont%></td>
		<td>
			<a href="javascript:Borrar('<%=dato.getCodigoPersonal()%>','<%=dato.getPeriodoId()%>')"><i class="fas fa-trash-alt"></i></a>&nbsp;
			<a href="editar?CodigoAlumno=<%=dato.getCodigoPersonal()%>&PeriodoId=<%=dato.getPeriodoId()%>"><i class="fas fa-pencil-alt"></i></a>
		</td>
		<td><%=dato.getCodigoPersonal() %></td>
		<td><%=nombre%></td>
		<td><%=residencia%></td>
		<td><%=edad%></td>
		<td><%=facultadNombre%></td>
		<td><%=planAlumno%></td>
		<td><%=dato.getTipo().equals("L")?"Local":"Foraneo"%></td>
		<td><%=dato.getFechaLlegada()==null?"":dato.getFechaLlegada()%></td>
		<td><%=dato.getVacuna().equals("N")?"NO":"SI"%></td>
		<td><%=dato.getFechaVacuna()==null?"":dato.getFechaVacuna()%></td>
		<td><%=dato.getPositivoCovid().equals("N")?"NO":"SI"%></td>
		<td><%=dato.getFechaPositivo()==null?"":dato.getFechaPositivo()%></td>
		<td><%=dato.getSospechoso().equals("N")?"NO":"SI"%></td>
		<td><%=dato.getFechaSospechoso()==null?"":dato.getFechaSospechoso()%></td>
		<td><%=dato.getAislamiento().equals("N")?"NO":"SI"%></td>
		<td><%=dato.getFinAislamiento()==null?"":dato.getFinAislamiento()%></td>
		<td><%=dato.getUsuarioAlta()%></td>
		<td><%=dato.getFechaAlta()==null?"":dato.getFechaAlta()%></td>	
		<td><%=dato.getValidado().equals("N")?"NO":"SI"%></td>
		<td><%=mapaDocumentos.containsKey(dato.getCodigoPersonal()+"1")?"<a href='descargarRetorno?CodigoAlumno="+dato.getCodigoPersonal()+"&DocumentoId=1'>SI</a>":"NO"%></td>
		<td><%=mapaDocumentos.containsKey(dato.getCodigoPersonal()+"2")?"<a href='descargarRetorno?CodigoAlumno="+dato.getCodigoPersonal()+"&DocumentoId=2'>SI</a>":"NO"%></td>
		<td><%=mapaDocumentos.containsKey(dato.getCodigoPersonal()+"3")?"<a href='descargarRetorno?CodigoAlumno="+dato.getCodigoPersonal()+"&DocumentoId=3'>SI</a>":"NO"%></td>
		<td><%=mapaDocumentos.containsKey(dato.getCodigoPersonal()+"4")?"<a href='descargarRetorno?CodigoAlumno="+dato.getCodigoPersonal()+"&DocumentoId=4'>SI</a>":"NO"%></td>		
	</tr>	
<%
 			}
 		}
	}
%>
	</tbody>
	</table>	
</div>
<script>
	function Borrar( codigoAlumno, periodoId ){
		if(confirm("<spring:message code="aca.JSEliminar"/>")==true){
	  		document.location.href="borrar?CodigoAlumno="+codigoAlumno+"&PeriodoId="+periodoId;
	  	}
	}
</script>
</html>