<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.vista.spring.CargaAcademica"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<head>
<script type="text/javascript">
	function Refrescar(){		
		document.forma.submit();
	}
</script>
</head>
<%
	String origen 			= request.getParameter("Origen")==null?"T":request.getParameter("Origen");
	String codigoUsuario 							= (String) session.getAttribute("codigoPersonal");
	String cargaId 									= (String)request.getAttribute("cargaId");
	//String origen 									= (String)request.getAttribute("origen");
	Acceso acceso 									= (Acceso)request.getAttribute("acceso");
	List<Carga> lisCargas							= (List<Carga>) request.getAttribute("lisCargas");
	List<CargaAcademica> lisMaterias				= (List<CargaAcademica>) request.getAttribute("lisMaterias");
	HashMap<String, CatFacultad> mapaFacultades		= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String, CatCarrera> mapaCarreras		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String, CatModalidad> mapaModalidades	= (HashMap<String,CatModalidad>) request.getAttribute("mapaModalidades");
	HashMap<String, String> mapaTotalAlumnos		= (HashMap<String,String>) request.getAttribute("mapaTotalAlumnos");	

	int numAlumnos 			= 0;	
	int total_materias 		= 0;
	int i 					= 0;	
	int suma 				= 0;	
	String s_linea 			= "";
%>
<body>
<div class="container-fluid">
	<h3><spring:message code="cargasGrupos.materiasActivas.Titulo"/></h3>
	<form name="forma" action="activas" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<spring:message code="aca.Carga"/>:&nbsp;
		<select name="CargaId" onchange='javascript:Refrescar()' class="form-select" style="width:350px">
<%
	for (Carga carga: lisCargas ){
%>
			<option <%=cargaId.equals(carga.getCargaId())?" Selected ":""%> value="<%=carga.getCargaId()%>"><%=carga.getCargaId()%>-<%=carga.getNombreCarga()%></option>
<%
	}
%>
		</select>
		&nbsp;&nbsp;
		<spring:message code="aca.Tipo"/>:
		<select name="Origen" onchange='javascript:Refrescar()' class="form-select" style="width:100px">
			<option <%=origen.equals("T")?" Selected ":""%> value="T"><spring:message code="aca.Todos"/></option>
			<option <%=origen.equals("O")?" Selected ":""%> value="O">Origin</option>
			<option <%=origen.equals("E")?" Selected ":""%> value="E"><spring:message code="aca.Unido"/></option>		
		</select>
		&nbsp;&nbsp;
		<a href="activasreporte?CargaId=<%=cargaId%>&Origen=<%=origen%>" class="btn btn-success btn-sm"><i class="fas fa-file-alt"></i></a>
		&nbsp;&nbsp;
		<input type="text" class="form-control search-query" placeholder=<spring:message code="aca.Buscar"/> id="buscar" style="width:170px">
	</div>
	</form>	
<%
	String facultadTemp = "X";
	String carreraTemp  = "X";
	int totActiva = 0;
	int totExtra = 0;
	int totCerrada = 0;
	int totRegistrada =0;
	int row=0;
%>
	<table class="table table-bordered" id="table">
	<thead class="table-info">	
		<tr>
			<th align="center" width="26">#</th>
			<th align="center" width="40"><spring:message code="aca.Facultad"/></th>
			<th align="center" width="58"><spring:message code="aca.Carrera"/></th>			
			<th align="center" width="58">Sub. <spring:message code="aca.Clave" /></th>
			<th align="center" width="29"><spring:message code="cargasGrupos.materiasActivas.Blo" /></th>
			<th align="center" width="319"><spring:message code="aca.Materia" /> <spring:message code="aca.Nombre" /></th>
			<th align="center" width="50">Sub-Load ID</th>
			<th align="center" width="50">Emp. ID</th>
			<th align="center" width="265">Lecturer</th>			
			<th align="center" width="5">Group</th>
			<th align="center" width="39">No.</th>
			<th align="center" width="75"><spring:message code="aca.Status" /></th>
			<th align="center" width="82"><spring:message code="aca.Modalidad" /></th>		
			<th align="center" width="20">HH</th>
			<th align="center" width="20"><spring:message code="aca.AbbrCreditos"/></th>
			<th align="center" width="30"><spring:message code="aca.Tipo"/></th>
		</tr>
		</thead>
<%	

	for (CargaAcademica materia : lisMaterias){
		
		String facultadId		= "";
		String facultadNombre	= "";
		String carreraNombre	= ""; 
		if (mapaCarreras.containsKey(materia.getCarreraId())){
			 carreraNombre 		= mapaCarreras.get(materia.getCarreraId()).getNombreCarrera();
			 facultadId 		= mapaCarreras.get(materia.getCarreraId()).getFacultadId();
			 if (mapaFacultades.containsKey(facultadId)){
				 facultadNombre = mapaFacultades.get(facultadId).getNombreCorto();
			 }			   
		}	

		if ((acceso.getAccesos().indexOf(materia.getCarreraId()) != -1)	|| (acceso.getAdministrador().equals("S")) || (acceso.getSupervisor().equals("S"))) {
			
			row++;
			
			String modalidadNombre = "-";
			if (mapaModalidades.containsKey(materia.getModalidadId()) ){
				modalidadNombre = mapaModalidades.get(materia.getModalidadId()).getNombreModalidad();
			}
			
			numAlumnos = 0;
			if (mapaTotalAlumnos.containsKey(materia.getCursoCargaId())){
				numAlumnos = Integer.parseInt(mapaTotalAlumnos.get(materia.getCursoCargaId()));
			}
			
			String estadoNombre = "";
			if (materia.getEstado().equals("0") || materia.getEstado().equals("1") || materia.getEstado().equals("2")){
				estadoNombre = "ACTIVE";
				totActiva++;
			}
			if (materia.getEstado().equals("3") ) {
				estadoNombre = "EXTRA";
				totExtra++;
			}
			if (materia.getEstado().equals("4") ) {
				estadoNombre = "CLOSED";
				totCerrada++;
			}
			if (materia.getEstado().equals("5") ) {
				estadoNombre = "REGISTERED";
				totRegistrada++;
			}			
			
			
%>
		<tr class="tr2">
			<td align="left"><%=row%></td>
			<td align="left"><%=facultadNombre%></td>
			<td align="left"><%=carreraNombre%></td>
			<td align="left"><%=materia.getCursoId()%></td>
			<td align="center"><%=materia.getBloqueId()%></td>
			<td align="left"><a
				href="alumnos?CursoCargaId=<%=materia.getCursoCargaId()%>&CursoId=<%=materia.getCursoId()%>"><%=materia.getNombreCurso()%></a>
			</td>
			<td align="left" height="19"><%=materia.getCursoCargaId()%></td>
			<td align="left" height="19"><%=materia.getCodigoPersonal()%></td>
			<td align="left" height="19"><%=materia.getNombre()%></td>			
			<td align="center" height="19"><%=materia.getGrupo()%></td>			
			<td class="right"><%=numAlumnos%></td>
			<td align="center"><%=estadoNombre%></td>
			<td align="center"><%=modalidadNombre%></td>			
			<td align="center"><%=materia.getHh()%></td>
			<td align="center"><%=materia.getCreditos()%></td>
			<td align="center"><%=materia.getOrigen().equals("O")?"<span style='color:black'>Origin</span>":"<span style='color:green'>Joined</span>"%></td>
		</tr>
<%			
		}
	}
			total_materias = total_materias + row;
%>		
		<tr>
			<th colspan="15" class="titulo2"><spring:message code="cargasGrupo.materias.TotalBloque" />: <%=total_materias%>&nbsp;&nbsp;
			<spring:message code="aca.Activas"/>: <%=totActiva%>&nbsp;&nbsp;
			<spring:message code="aca.Extras"/>: <%=totExtra%>&nbsp;&nbsp;
			<spring:message code="aca.Cerradas"/>: <%=totCerrada%>&nbsp;&nbsp;
			<spring:message code="aca.Registradas"/>: <%=totRegistrada%>
			</th>
		</tr>
	</table>
</div>
</body>
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>