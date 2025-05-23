<%@page import="java.util.HashMap"%>

<%@ include file="../../con_enoc.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="EstadisticaU" scope="page" class="aca.vista.EstadisticaUtil"/>
<jsp:useBean id="FacultadU" scope="page" class="aca.catalogo.CatFacultadUtil"/>
<jsp:useBean id="CarreraU" scope="page" class="aca.catalogo.CatCarreraUtil"/>
<jsp:useBean id="MapaCursoU" scope="page" class="aca.plan.CursoUtil"/>
<jsp:useBean id="KrdxAltaU" scope="page" class="aca.kardex.KrdxAltaUtil"/>
<jsp:useBean id="KrdxBajaU" scope="page" class="aca.kardex.KrdxBajaUtil"/>
<jsp:useBean id="FinTablaU" scope="page" class="aca.vista.FinTablaUtil"/>

<script type="text/javascript">	
	function Mostrar(){	
		document.forma.submit();
	}
</script>
<%	
	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	
	String yearActual		= aca.util.Fecha.getHoy().substring(6);	
	String year			= request.getParameter("Year")==null?yearActual:request.getParameter("Year");
	
	String cargas 		= EstadisticaU.lisCargasEntreFechas(conEnoc, "01/01/"+year, "31/12/"+year);
	
	// Lista de materias que dieron de alta los alumno en el año
	ArrayList<aca.kardex.KrdxAlta> lisAltas	= KrdxAltaU.getLista(conEnoc, year, "ORDER BY CARRERA_ID,CODIGO_PERSONAL");
	
	// Lista de materias que dieron de baja los alumnos en el año
	ArrayList<aca.kardex.KrdxAlta> lisBajas	= KrdxBajaU.getLista(conEnoc, year, "ORDER BY CARRERA_ID,CODIGO_PERSONAL");

	//System.out.println("Datos:"+lisAltas.size()+":"+lisBajas.size()+":"+year);
	
	// Map de modalidades
	java.util.HashMap<String, aca.catalogo.CatModalidad> mapModalidad 	= aca.catalogo.ModalidadUtil.getMapAll(conEnoc,"");
	
	//Map de Facultades
	HashMap<String, aca.catalogo.CatFacultad> mapFacultad 		= FacultadU.getMapFacultad(conEnoc, "");
	
	//Map de Carreras
	HashMap<String, aca.catalogo.CatCarrera> mapaCarrera 		= CarreraU.getMapAll(conEnoc, "ORDER BY NOMBRE_CARRERA");
	
	//Map de materias
	HashMap<String, aca.plan.MapaCurso> mapaMateria 			= MapaCursoU.getAllMapaCursos(conEnoc,"");
	
	// Map de costos de credito
	HashMap<String, aca.vista.FinTabla> mapCostoCredito 		= FinTablaU.mapTablaCargas(conEnoc, cargas);
%>
<head>	
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
</head>
<body>
<div class="container-fluid">
	<h1>Informe de Altas y Bajas por año</h1>
	<form id="forma" name="forma" action="altasbajas" method="post">
	<div class="alert alert-info d-flex justify-content-start">
		<a href="menu" class="btn btn-primary">Menú</a>
		&nbsp;
		<select name="Year" class="form-select" style="width:100px;">
			<option value="2015" <%=year.equals("2015")?"Selected":""%>>2015</option>
			<option value="2016" <%=year.equals("2016")?"Selected":""%>>2016</option>
			<option value="2017" <%=year.equals("2017")?"Selected":""%>>2017</option>
			<option value="2018" <%=year.equals("2018")?"Selected":""%>>2018</option>
			<option value="2019" <%=year.equals("2019")?"Selected":""%>>2019</option>
			<option value="2020" <%=year.equals("2020")?"Selected":""%>>2020</option>
			<option value="2021" <%=year.equals("2021")?"Selected":""%>>2021</option>
		</select>
		&nbsp;
		<a href="javascript:Mostrar();" class="btn btn-primary btn-sm d-flex align-items-center"><i class="fas fa-sync-alt"></i></a>
	</div>
	</form>
	<div class="alert alert-info"><h3>Materias de Alta</h3></div>
	<table class="table table-sm table-striped">
		<tr class="table-info">
			<th>#</th>
			<th>Matricula</th>		
			<th>Nombre del Alumno</th>
			<th>Carga</th>
			<th>Blq.</th>
			<th>Materia</th>
			<th>Modalidad</th>
			<th>Facultad</th>
			<th>Carrera</th>			
			<th class="right">Cred.</th>
			<th class="right">$Cred.</th>						
			<th class="right">$Total</th>
		</tr>	
<%
	int row = 0;
	double totCreditos=0, totMateria = 0, totCosto = 0;

	for (aca.kardex.KrdxAlta altas : lisAltas){
		row++;
		
		String nombreModalidad = "-";
		if (mapModalidad.containsKey(altas.getModalidadId())){
			nombreModalidad = mapModalidad.get(altas.getModalidadId()).getNombreModalidad();
		}
		String nombreCarrera 	= "-";
		String facultadId		= "000";
		String nombreFacultad	= "-";
		if (mapaCarrera.containsKey(altas.getCarreraId())){
			nombreCarrera 	= mapaCarrera.get(altas.getCarreraId()).getNombreCarrera();
			facultadId 		= mapaCarrera.get(altas.getCarreraId()).getFacultadId();
			if (mapFacultad.containsKey(facultadId)){
				nombreFacultad = mapFacultad.get(facultadId).getNombreCorto();
			}
		}
		
		String nombreCurso = "-";
		if (mapaMateria.containsKey(altas.getCursoId()) ){
			nombreCurso = mapaMateria.get(altas.getCursoId()).getNombreCurso();
		}
		
		String clasFin = aca.alumno.AcademicoUtil.getClasFinAlumno(conEnoc, altas.getCodigoPersonal());
		
		String costo 	= "0";
		double costoReal	= 0;
		if (mapCostoCredito.containsKey(altas.getCargaId()+altas.getCarreraId()+altas.getModalidadId())){
			if (clasFin.equals("1")){
				costo = mapCostoCredito.get(altas.getCargaId()+altas.getCarreraId()+altas.getModalidadId()).getAcfe();
			}else{
				costo = mapCostoCredito.get(altas.getCargaId()+altas.getCarreraId()+altas.getModalidadId()).getNoAcfe();				
			}
			costoReal = Double.valueOf(costo) * Double.valueOf(mapCostoCredito.get(altas.getCargaId()+altas.getCarreraId()+altas.getModalidadId()).getPorCredito());
		}
		
		totCreditos 	+= Double.valueOf(altas.getCreditos());		
		totMateria 		= Double.valueOf(altas.getCreditos()) * costoReal;
		totCosto 		+= totMateria;	
%>
		<tr>
			<td><%=row%></td>
			<td><%=altas.getCodigoPersonal()%></td>
			<td><%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, altas.getCodigoPersonal(), "NOMBRE")%></td>
			<td><%=altas.getCargaId()%></td>
			<td><%=altas.getBloqueId()%></td>
			<td><%=nombreCurso%></td>
			<td><%=nombreModalidad%></td>			
			<td><%=nombreFacultad%></td>
			<td><%=nombreCarrera%></td>	
			<td class="right"><%=altas.creditos%></td>
			<td class="right"><%=getFormato.format(costoReal)%></td>
			<td class="right"><%=getFormato.format(totMateria)%></td>
		</tr>		
<%		
	}		
%>	
		<tr>
			<th colspan="9">TOTALES DE ALTA</th>			
			<th class="right"><%=totCreditos%></th>
			<th class="right"><%=0%></th>
			<th class="right"><%=getFormato.format(totCosto)%></th>
		</tr>
	</table>
	
	<div class="alert alert-info"><h3>Materias de Baja</h3></div>
	<table class="table table-condensed table-striped">
		<tr>
			<th>#</th>
			<th>Matricula</th>		
			<th>Nombre del Alumno</th>
			<th>Carga</th>
			<th>Blq.</th>
			<th>Materia</th>
			<th>Modalidad</th>
			<th>Facultad</th>
			<th>Carrera</th>			
			<th class="right">Cred.</th>
			<th class="right">$Cred.</th>						
			<th class="right">$Total</th>
		</tr>	
<%
	row = 0;
	totCreditos=0; totMateria = 0; totCosto = 0;

	for (aca.kardex.KrdxAlta bajas : lisBajas){
		row++;
		
		String nombreModalidad = "-";
		if (mapModalidad.containsKey(bajas.getModalidadId())){
			nombreModalidad = mapModalidad.get(bajas.getModalidadId()).getNombreModalidad();
		}
		String nombreCarrera 	= "-";
		String facultadId		= "000";
		String nombreFacultad	= "-";
		if (mapaCarrera.containsKey(bajas.getCarreraId())){
			nombreCarrera 	= mapaCarrera.get(bajas.getCarreraId()).getNombreCarrera();
			facultadId 		= mapaCarrera.get(bajas.getCarreraId()).getFacultadId();
			if (mapFacultad.containsKey(facultadId)){
				nombreFacultad = mapFacultad.get(facultadId).getNombreCorto();
			}
		}
		
		String nombreCurso = "-";
		if (mapaMateria.containsKey(bajas.getCursoId()) ){
			nombreCurso = mapaMateria.get(bajas.getCursoId()).getNombreCurso();
		}	
		
		String clasFin = aca.alumno.AcademicoUtil.getClasFinAlumno(conEnoc, bajas.getCodigoPersonal());
		
		String costo 		= "0";
		double costoReal	= 0;
		if (mapCostoCredito.containsKey(bajas.getCargaId()+bajas.getCarreraId()+bajas.getModalidadId())){
			if (clasFin.equals("1")){
				costo = mapCostoCredito.get(bajas.getCargaId()+bajas.getCarreraId()+bajas.getModalidadId()).getAcfe() ;
			}else{
				costo = mapCostoCredito.get(bajas.getCargaId()+bajas.getCarreraId()+bajas.getModalidadId()).getNoAcfe();				
			}
			costoReal = Double.valueOf(costo) * Double.valueOf(mapCostoCredito.get(bajas.getCargaId()+bajas.getCarreraId()+bajas.getModalidadId()).getPorCredito());
		}
		
		totCreditos 	+= Double.valueOf(bajas.getCreditos());		
		totMateria 		= Double.valueOf(bajas.getCreditos()) * costoReal;
		totCosto 		+= totMateria;
%>
		<tr>
			<td><%=row%></td>
			<td><%=bajas.getCodigoPersonal()%></td>
			<td><%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, bajas.getCodigoPersonal(), "NOMBRE")%></td>
			<td><%=bajas.getCargaId()%></td>
			<td><%=bajas.getBloqueId()%></td>
			<td><%=nombreCurso%></td>
			<td><%=nombreModalidad%></td>			
			<td><%=nombreFacultad%></td>
			<td><%=nombreCarrera%></td>	
			<td class="right"><%=bajas.creditos%></td>
			<td class="right"><%=getFormato.format(costoReal)%></td>
			<td class="right"><%=getFormato.format(totMateria)%></td>
		</tr>		
<%		
	}
%>	
		<tr>
			<th colspan="9">TOTALES DE BAJAS</th>
			<th class="right"><%=totCreditos%></th>
			<th class="right"><%=0%></th>
			<th class="right"><%=getFormato.format(totCosto)%></th>
		</tr>
	</table>
</div>
</body>
<%@ include file="../../cierra_enoc.jsp"%>
