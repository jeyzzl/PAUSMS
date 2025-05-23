<%@ page import= "java.text.*"%>
<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>

<%@ page import= "aca.catalogo.spring.CatPeriodo"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.catalogo.spring.CatTipoCal"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.vista.spring.AlumnoCurso"%>
<%@ page import= "aca.vista.spring.Estadistica"%>
<%@ page import= "aca.alumno.spring.AlumPersonal"%>
<%@ page import= "aca.plan.spring.MapaPlan"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<script>
	function grabaPeriodo(){		
		document.forma.cambioPeriodo.value = "1";
		document.forma.submit();
	}
	
	function grabaCarga(){
		document.forma.cambioCarga.value = "1";
		document.forma.submit();
	}	
</script>


<!-- inicio de estructura -->
<%
	DecimalFormat getFormato	= new DecimalFormat("###,##0.00;(###,##0.00)");
	
	String periodoId 		= (String)session.getAttribute("periodo");
	String cargaId 			= (String)session.getAttribute("cargaId");
	
	String cambioPeriodo 	= request.getParameter("cambioPeriodo")==null?"0":request.getParameter("cambioPeriodo");
	String cambioCarga 		= request.getParameter("cambioCarga")==null?"0":request.getParameter("cambioCarga");
	String incidencias		= request.getParameter("Incidencias")==null?"0":request.getParameter("Incidencias");
	
	if (cambioPeriodo.equals("1")){
		periodoId = request.getParameter("PeriodoId");
		session.setAttribute("periodo", periodoId);
	}
	if (cambioCarga.equals("1")){
		cargaId = request.getParameter("CargaId");
		session.setAttribute("cargaId", cargaId);
	}
	List<CatPeriodo> lisPeriodos 					= (List<CatPeriodo>) request.getAttribute("lisPeriodos");
	List<Carga> lisCargas 							= (List<Carga>) request.getAttribute("lisCargas");	
	List<String> lisReprobones 						= (List<String>) request.getAttribute("lisReprobones");
	
	/* Map de alumnos*/
	HashMap<String,AlumPersonal> mapaAlumnos		= (HashMap<String,AlumPersonal>) request.getAttribute("mapaAlumnos");	
	/* Map de reprobones*/
	HashMap<String,String> mapaReprobones			= (HashMap<String,String>) request.getAttribute("mapaReprobones");	
	/* Map de materias reprobadas reincidentemente*/
	HashMap<String,MapaCurso> mapaMaterias			= (HashMap<String,MapaCurso>) request.getAttribute("mapaMaterias");	
	/* Map de planes */
	HashMap<String,String> mapaCarreraDePlan		= (HashMap<String,String>) request.getAttribute("mapaCarreraDePlan");	
	/* Map de facultades */
	HashMap<String,CatFacultad> mapaFacultades		= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");	
	/* Map de materias reprobadas reincidentemente*/
	HashMap<String,CatCarrera> mapaCarreras			= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");	
%>
<div class="container-fluid">
	<h2>Materias reprobadas </h2>
	<form name="forma" action="reprobones" method='post'>
	<input name="cambioPeriodo" type="hidden">
	<input name="cambioCarga" type="hidden">		
	<div class="alert alert-info">
		<a href="menu.jsp" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
		&nbsp;&nbsp;
		<spring:message code="aca.Periodo"/>: 
		<select onchange="javascript:grabaPeriodo();" name="PeriodoId" class="input input-medium">
<%		for(CatPeriodo periodo : lisPeriodos){ %>
			<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print(" Selected ");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
<%		}%>
		</select>
		&nbsp;&nbsp;
		Carga: [ <%=cargaId%> ] 
    	<select name="CargaId" class="input input-xlarge" onchange="javascript:grabaCarga();">
<%		for(Carga carga: lisCargas){%>			
			<option value="<%=carga.getCargaId()%>" <%=cargaId.equals(carga.getCargaId())?"selected":""%>>
			<%=carga.getNombreCarga()%>
			</option>
<%		}
%>		</select>
		&nbsp;&nbsp;
		Incidencias:&nbsp;
		<select name="Incidencias" class="input input-medium" onchange="document.forma.submit();">			
			<option value="0" <%=incidencias.equals("0")?"selected":""%>>Mayor que 0</option>
			<option value="1" <%=incidencias.equals("1")?"selected":""%>>Mayor que 1</option>
			<option value="2" <%=incidencias.equals("2")?"selected":""%>>Mayor que 2</option>
			<option value="3" <%=incidencias.equals("3")?"selected":""%>>Mayor que 3</option>
		</select>
	</div>
	</form>
	<table class="table table-bordered">
	<thead class="table-info">
	<tr>
		<th>#</th>
		<th>Facultad</th>
		<th>Carrera</th>
		<th>Matrícula</th>
		<th>Nombre</th>
		<th>Clave</th>
		<th>Materia</th>			
		<th class="right">#Cred.</th>
		<th class="right">#Rep.</th>
		<th class="right">Tot.Cred.</th>
	</tr>
	</thead>
<%							
	int row = 0;
	for (String alumno : lisReprobones){
		
		row++;
		String datos[] 			= alumno.split("&&");			
		
		String codigoPersonal 	= datos[0];
		String claveMateria		= datos[1];			
		
		String reincidencias	= "0";
		if(mapaReprobones.containsKey(codigoPersonal+claveMateria)){
			reincidencias = mapaReprobones.get(codigoPersonal+claveMateria);
		}
		
		String alumnoNombre = "";
		if(mapaAlumnos.containsKey(codigoPersonal)){
			AlumPersonal alum = mapaAlumnos.get(codigoPersonal);
			alumnoNombre = alum.getApellidoPaterno()+" "+alum.getApellidoMaterno()+" "+alum.getNombre();
		}
		
		String materia 			= "-";
		float creditos 			= 0;
		float totCreditos 		= 0;
		String planId			= "";
		String facultadId 		= "0";
		String carreraId		= "0";
		String facultadNombre 	= "-";
		String carreraNombre 	= "-";
		
		if(mapaMaterias.containsKey(claveMateria)){
			// Busca la carrera de un plan de estudios
			if ( mapaCarreraDePlan.containsKey(mapaMaterias.get(claveMateria).getPlanId()) ){
				carreraId 		= mapaCarreraDePlan.get(mapaMaterias.get(claveMateria).getPlanId());
				
				//Busca el nombre de la carrera
				if (mapaCarreras.containsKey(carreraId)){
					carreraNombre	= mapaCarreras.get(carreraId).getNombreCarrera();
					facultadId 		= mapaCarreras.get(carreraId).getFacultadId();
				}					
				// Busca la facultad
				if (mapaFacultades.containsKey(facultadId)){
					facultadNombre 	= mapaFacultades.get(facultadId).getNombreCorto();
				}
			}
			
			materia 		= mapaMaterias.get(claveMateria).getNombreCurso();
			creditos 		= Float.valueOf( mapaMaterias.get(claveMateria).getCreditos());
			totCreditos 	= creditos * Float.valueOf(reincidencias);
		}		
%>
		<tr>
			<td><%=row%></td>
			<td><%=facultadNombre%></td>
			<td><%=carreraNombre%></td>
			<td><%=codigoPersonal%></td>
			<td><%=alumnoNombre%></td>
			<td><%=claveMateria%></td>
			<td><%=materia%></td>
			<td class="right"><%=getFormato.format(creditos)%></td>
			<td class="right"><%=reincidencias%></td>
			<td class="right"><%=getFormato.format(totCreditos)%></td>
		</tr>
		<%		
	}
%>
	</table>
</div>