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

<jsp:useBean id="Acceso" scope="page" class="aca.acceso.Acceso" />
<jsp:useBean id="AccesoU" scope="page" class="aca.acceso.AccesoUtil" />
<jsp:useBean id="GrupoEvaluacion" scope="page" class="aca.carga.CargaGrupoEvaluacion" />
<jsp:useBean id="GrupoEvaluacionU" scope="page" class="aca.carga.CargaGrupoEvaluacionUtil" />
<jsp:useBean id="Grupo" scope="page" class="aca.carga.CargaGrupo" />

<head>
<script type="text/javascript">
	function cambiaCarga(){		
		document.location.href = "activas?CargaId="+document.forma.CargaId.value;
	}
</script>
</head>
<%
	String codigoUsuario 							= (String) session.getAttribute("codigoPersonal");
	String cargaId 									= (String)request.getAttribute("cargaId");
	String origen 									= request.getParameter("Origen")==null?"O":request.getParameter("Origen");
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
	<h2>Active Subjects</h2>
	<form name="forma" action="activas" method="post">
	<div class="alert alert-info">		
		<a class="btn btn-primary" href="activas?CargaId=<%=cargaId%>&Origen=<%=origen%>"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
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
	int num=0;
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
			
			if (num==0 ) {
				
			
	%>
	<table class="table table-bordered">
	<thead class="table-info">
		<tr>
			<th align="center" width="2%">#</th>
						<th align="center" width="6%">School</th>
			<th align="center" width="16%">Course</th>
			<th align="center" width="8%">Sub. <spring:message code="aca.Clave" /></th>
			<th align="center" width="2%"><spring:message code="cargasGrupos.materiasActivas.Blo" /></th>
			<th align="center" width="16%"><spring:message code="aca.Materia" /> <spring:message code="aca.Nombre" /></th>
			<th align="center" width="7%">Sub-Load ID</th>
			<th align="center" width="5%">Lecturer ID</th>
			<th align="center" width="18%"><spring:message code="aca.Maestro" /></th>			
			<th align="center" width="2%"><spring:message code="cargasGrupos.materiasActivas.Gpo" /></th>
			<th align="center" width="3%"><spring:message code="cargasGrupo.materias.NAl" /></th>
			<th align="center" width="6%"><spring:message code="aca.Status" /></th>
			<th align="center" width="5%"><spring:message code="aca.Modalidad" /></th>		
			<th align="center" width="2%">**</th>
			<th align="center" width="2%">Cr.</th>
		</tr>
	</thead>
<%num++;

			}
			row++;
%>
		<tr class="tr2">
			<td align="left"><%=row%></td>
			<td align="left"><%=facultadNombre%></td>
			<td align="left"><%=carreraNombre%></td>
			<td align="left"><%=materia.getCursoId()%></td>
			<td align="center"><%=materia.getBloqueId()%></td>
			<td align="left"><a
				href="listado_profesor_alumnos.jsp?CursoCargaId=<%=materia.getCursoCargaId()%>&CursoId=<%=materia.getCursoId()%>"><%=materia.getNombreCurso()%></a>
			</td>
			<td align="left" height="19"><%=materia.getCursoCargaId()%></td>
			<td align="left" height="19"><%=materia.getCodigoPersonal()%></td>
			<td align="left" height="19"><%=materia.getNombre()%></td>			
			<td align="center" height="19"><%=materia.getGrupo()%></td>			
			<td class="center"><b><%=numAlumnos%></b></td>
			<td align="center"><b><%=estadoNombre%></b></td>
			<td align="center"><b><%=modalidadNombre%></b></td>			
			<td align="center"><b><%=Integer.parseInt(materia.getHt()) + Integer.parseInt(materia.getHp())%></b></td>
			<td align="center"><b><%=materia.getCreditos()%></b></td>
		</tr>
<%			
		
	}
			total_materias = total_materias + row;
%>		
		<tr>
			<td colspan="15">&nbsp;</td>
		</tr>
		<tr>
			<th colspan="15" class="titulo2"><spring:message code="cargasGrupo.materias.TotalBloque" />: <%=total_materias%>&nbsp;&nbsp;
			Active: <%=totActiva%>&nbsp;&nbsp;
			Remedial: <%=totExtra%>&nbsp;&nbsp;
			Closed: <%=totCerrada%>&nbsp;&nbsp;
			Registered: <%=totRegistrada%>
			</th>
		</tr>
	</table>
</div>
</body>
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>