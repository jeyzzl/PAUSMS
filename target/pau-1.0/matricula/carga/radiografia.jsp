<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.plan.spring.MapaCursoPre"%>
<%@page import="aca.vista.spring.CargaAcademica"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.conva.spring.ConvMateria"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.carga.spring.CargaPermiso"%>
<%@page import="aca.carga.spring.Carga"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<head>		
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">			
	<link rel="stylesheet" href="../../print.css" type="text/css" media="print">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/fontawesome.min.css">
  	<link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/solid.min.css">
  	<link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/regular.min.css">
	<link rel="stylesheet" href="../../bootstrap4.4/css/bootstrap.min.css">		
	<script src="../../js/jquery-3.3.1.min.js"></script>
	<script src="../../js/popper.min.js"></script>
	<script src="../../bootstrap4.4/js/bootstrap.min.js"></script>	
</head>
<%
	if(request.getParameter("Matricula")!=null) session.setAttribute("codigoAlumno", request.getParameter("Matricula"));
	String codigoAlumno = (String)session.getAttribute("codigoAlumno");
	String cargaId		= request.getParameter("carga")==null?"-":request.getParameter("carga");
	String planAlumno	= request.getParameter("planAlumno")==null?"-":request.getParameter("planAlumno");
	String pagina 		= request.getParameter("pagedropdown")==null?"-":request.getParameter("pagedropdown");
	
	AlumPersonal alumPersonal 	= (AlumPersonal)request.getAttribute("alumPersonal");
	AlumAcademico alumAcademico = (AlumAcademico)request.getAttribute("alumAcademico");
	Carga carga					= (Carga)request.getAttribute("carga");
	CargaPermiso cargaPermiso	= (CargaPermiso)request.getAttribute("cargaPermiso");
	String carrera				= (String)request.getAttribute("carrera");
	String carreraId			= (String)request.getAttribute("carreraId");		
	
	List<MapaCurso> lisCursos 				= (List<MapaCurso>)request.getAttribute("lisCursos");
	List<AlumnoCurso> lisCursosAlumno 		= (List<AlumnoCurso>)request.getAttribute("lisCursosAlumno");
	List<MapaCursoPre> lisPrerrequisitos 	= (List<MapaCursoPre>)request.getAttribute("lisPrerrequisitos");
	List<ConvMateria> lisConvalidaciones 	= (List<ConvMateria>)request.getAttribute("lisConvalidaciones");
	
	HashMap<String,String> mapaOfertadas	= (HashMap<String,String>)request.getAttribute("mapaOfertadas");
	HashMap<String,MapaCurso> mapaCursos	= (HashMap<String,MapaCurso>)request.getAttribute("mapaCursos");

%>	
<div class="container-fluid">
	<h3>Subject Registration <small class="text-muted fs-5" >( <%=codigoAlumno %> - <%=alumPersonal.getNombreLegal()%>  )</small></h3>
	<div class="alert alert-info d-flex align-items-center">
		<a href="materias" class="btn btn-primary">Return</a>&nbsp;&nbsp;
		<spring:message code="aca.Carga"/>: <%=carga.getNombreCarga() %> <%=cargaPermiso.getRecuperacion().equals("S")?"( Recovery )":""%>&nbsp;&nbsp;
		Plan: <%=planAlumno%> - <%=carrera%> &nbsp; Modality: <em><%=alumAcademico.getModalidadId()%></em>
		&nbsp;&nbsp;&nbsp;&nbsp;
		Filter:&nbsp;<input id="buscar" type="text" class="input-medium search-query" placeholder="Search...">
	</div>
	<form id="forma" name="forma">
	
	<table class="table table-striped table-bordered table-sm" id="table">  	
    <thead>
		<tr>
			<th width="5%"><h6><spring:message code="aca.Clave"/></h6></th>
			<th width="5%"><h6><%=carreraId.substring(0,3).equals("107")?"Tetra.":"Sem." %></h6></th>
			<th width="30%"><h6><spring:message code="aca.Materia"/></h6></th>
			<th width="10%"><h6>Status</h6></th>
			<th width="10%"><h6>Offered</h6></th>
			<th width="10%"><h6>Assigned</h6></th>
			<th width="10%" class="ayuda <%=idJsp %> 005"><h6>Valid..</h6></th>
			<th width="10%" class="ayuda <%=idJsp %> 006"><h6>Prerre.</h6></th>
			<th width="10%" class="ayuda <%=idJsp %> 007"><h6>See Pre.</h6></th>
		</tr>
	</thead>
	<tbody>
<%	
	String ciclo = "";
	for(MapaCurso mapaCurso	: lisCursos){
		ciclo = mapaCurso.getCiclo();
		
//---------------------- Para los prerrequisitos ----------------------
		boolean cumplePrerrequisitos = true;
		boolean tienePrerrequisitos = false;
		String prerrequisitos = "";
		for(MapaCursoPre mapaCursoPre : lisPrerrequisitos){
			if(mapaCurso.getCursoId().equals(mapaCursoPre.getCursoId())){
				tienePrerrequisitos = true;
				
				String claveCurso 	= "";
				String cicloCurso	= "";
				String nombreCurso 	= ""; 
				if (mapaCursos.containsKey(mapaCursoPre.getCursoIdPre())){
					claveCurso 		= mapaCursos.get(mapaCursoPre.getCursoIdPre()).getCursoClave();
					cicloCurso		= mapaCursos.get(mapaCursoPre.getCursoIdPre()).getCiclo();
					nombreCurso		= mapaCursos.get(mapaCursoPre.getCursoIdPre()).getNombreCurso();
				}
				
				prerrequisitos += claveCurso + "<span class='badge badge-dark' data-toggle='tooltip' data-placement='top' title='"+nombreCurso+"'>"+cicloCurso+"</span>, ";
				boolean cursoLaMateria = false;
				for(AlumnoCurso alumnoCursos : lisCursosAlumno){
					if(mapaCursoPre.getCursoIdPre().equals(alumnoCursos.getCursoId())){
						cursoLaMateria = true;
						if(Float.parseFloat(alumnoCursos.getNota()) < Float.parseFloat(alumnoCursos.getNotaAprobatoria()) && Float.parseFloat(alumnoCursos.getNotaExtra()) < Float.parseFloat(alumnoCursos.getNotaAprobatoria())){
							cumplePrerrequisitos &= false;
						}
					}
				}
				if(!cursoLaMateria){
					cumplePrerrequisitos &= false;
				}
			}
		}
		if(!tienePrerrequisitos)
			prerrequisitos = " ";
//---------------------------------------------------------------------
		boolean asignado 	= false;		
		boolean cursado 	= false;
		AlumnoCurso alumnoCurso = new AlumnoCurso();
		for(AlumnoCurso alumnoCursos : lisCursosAlumno){//Ciclo que ubica el curso con el mismo ya cursado por el alumno
			if(mapaCurso.getCursoId().equals(alumnoCursos.getCursoId())){
				cursado = true;
				if (alumnoCursos.getTipoCalId().equals("M")){
					asignado = true;
				}else{
					alumnoCurso = alumnoCursos;
				}				
				
			}
		}
		
		boolean convalidada = false;
		for(ConvMateria convMateria : lisConvalidaciones){
			if(mapaCurso.getCursoId().equals(convMateria.getCursoId())){
				convalidada = true;
			}
		}
		boolean esOfertada = false;
		if(mapaOfertadas.containsKey(mapaCurso.getCursoId())){
			esOfertada = true;
		}
		if(cursado){
%>
		<tr>
			<td><%=mapaCurso.getCursoClave() %></td>
			<td align="center"><%=ciclo %></td>
			<td><%=mapaCurso.getNombreCurso() %></td>
			<td align="center"><%=alumnoCurso.getTipoCalId().equals("1")?(((Float.parseFloat(alumnoCurso.getNota()) >= Float.parseFloat(alumnoCurso.getNotaAprobatoria()))?alumnoCurso.getNota():alumnoCurso.getNotaExtra())+"-AC"):alumnoCurso.getTipoCalId() %></td>
			<td align="center">
<%
			if(esOfertada){
%>
							<img src="../../imagenes/check.png" width="15px" />
<%
			}else{
				out.println("&nbsp;");
			}
%>
			</td>
			<td align="center">
<%			if(asignado){ %>
					<img src="../../imagenes/check.png" width="15px" />
<%
			}else{
				out.println("&nbsp;");
			}
%>
			</td>
			<td align="center">
<%
			if(convalidada){
%>
				<img src="../../imagenes/check.png" width="15px" />
<%
			}else{
				out.println("&nbsp;");
			}
%>
			</td>
			<td align="center"><%if(cumplePrerrequisitos){%><img src="../../imagenes/check.png" width="15px" /><%} %></td>
			<td align="center"><%=prerrequisitos%></td>
		</tr>
<%
		}else{
%>
		<tr>
			<td><%=mapaCurso.getCursoId() %></td>
			<td align="center"><%=ciclo %></td>
			<td><%=mapaCurso.getNombreCurso() %></td>
			<td>&nbsp;</td>
			<td align="center">
<%
			if(esOfertada){
%>
				<img src="../../imagenes/check.png" width="15px" />
<%
			}else{
				out.println("&nbsp;");
			}
%>
			</td>
			<td>&nbsp;</td>
			<td align="center">
<%
			if(convalidada){
%>
				<img src="../../imagenes/check.png" width="15px" />
<%
			}else{
				out.println("&nbsp;");
			}
%>
			</td>
			<td align="center"><%if(cumplePrerrequisitos){%><img src="../../imagenes/check.png" width="15px" /><%} %></td>
			<td align="center"> <%=prerrequisitos %></td>
		</tr>
<%
		}
	}
%>
	</tbody>
	</table>						
	</form>
 </div>
 <script type="text/javascript" src="../../js/search.js"></script>
 <script>	
 	jQuery('#buscar').focus().search({table:jQuery("#table")});
	$(function () {
  		$('[data-toggle="tooltip"]').tooltip();
  		$('[data-toggle="popover"]').popover();
	});
</script>