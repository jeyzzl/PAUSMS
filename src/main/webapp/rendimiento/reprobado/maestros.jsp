<%@ page import="java.text.*"%>
<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>

<%@ page import= "aca.catalogo.spring.CatPeriodo"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.vista.spring.Estadistica"%>
<%@ page import= "aca.vista.spring.AlumnoCurso"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<script>
	function grabaPeriodo(periodoId) {
		document.location.href = "maestros?cambioPeriodo=1&PeriodoId="+periodoId;
	}
	function grabaCarga(cargaId) {
		document.location.href = "maestros?cambioCarga=1&CargaId="+cargaId;
	}
</script>
<%@ page import="java.util.HashMap"%>
<%	
	DecimalFormat getFormato	= new DecimalFormat("###,##0.00;(###,##0.00)");

	String periodoId 			= (String)session.getAttribute("periodo");
	String cargaId 				= (String)session.getAttribute("cargaId");
	
	String cambioPeriodo 		= request.getParameter("cambioPeriodo")==null?"0":request.getParameter("cambioPeriodo");
	String cambioCarga 			= request.getParameter("cambioCarga")==null?"0":request.getParameter("cambioCarga");
	
	if (cambioPeriodo.equals("1")){
		periodoId = request.getParameter("PeriodoId");
		session.setAttribute("periodo", periodoId);
	}
	if (cambioCarga.equals("1")){
		cargaId = request.getParameter("CargaId");
		session.setAttribute("cargaId", cargaId);
	}
	int totMaterias		= 0;
	int totAlumnos		= 0;
	float totProm		= 0;
	int totReprobados	= 0;
	float totPorRep		= 0;
	
	List<CatPeriodo> lisPeriodos 				= (List<CatPeriodo>) request.getAttribute("lisPeriodos");
	List<Carga> lisCargas						= (List<Carga>) request.getAttribute("lisCargas");
	List<String> lisMaestros					= (List<String>) request.getAttribute("lisMaestros");
	
	// Map que cuenta la cantidad de materias que imparte el maestro
	HashMap<String,String> mapaMaterias 		= (HashMap<String,String>) request.getAttribute("mapaMaterias");	
	// Map que cuenta la cantidad de materias que imparte el maestro
	HashMap<String,String> mapaAlumnos 			= (HashMap<String,String>) request.getAttribute("mapaAlumnos");	
	// Map que cuenta la cantidad de materias que imparte el maestro
	HashMap<String,String> mapaReprobados 		= (HashMap<String,String>) request.getAttribute("mapaReprobados");
	HashMap<String,String> mapaMaestros 		= (HashMap<String,String>) request.getAttribute("mapaMaestros");
%>
<div class="container-fluid">
	<h2>Maestros en la carga</h2>
	<form id="noayuda" name="forma" action="facultades.jsp" method='post'>
		<div class="alert alert-info">
			<a href="menu" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
			<spring:message code="aca.Periodo" />:
			<select onchange="javascript:grabaPeriodo(this.value);" name="PeriodoId" class="input input-medium">
<%		for(CatPeriodo periodo : lisPeriodos){ %>
				<option
					<%if(periodoId.equals(periodo.getPeriodoId()))out.print(" Selected ");%>
					value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
<%		}%>
			</select>&nbsp;&nbsp;
			Carga:
			<select onchange="javascript:grabaCarga(this.value)" name="CargaId" style="width:350px;">
<%		
		for(Carga carga : lisCargas){
%>
				<option value='<%=carga.getCargaId()%>'
					<%if (cargaId.equals(carga.getCargaId()))out.print("selected");%>>
					[<%=carga.getCargaId()%>] -
					<%=carga.getNombreCarga()%>
				</option>
<%		}%>
			</select>
		</div>
	</form>
	<table class="table table-bordered">
	<thead class="table-info">
		<tr>
			<th>#</th>
			<th>Clave</th>
			<th>Maestro</th>
			<th style="text-align: right">No.Mat.</th>
			<th style="text-align: right">No.Alum.</th>
			<th style="text-align: right">Prom.Al.Mat</th>
			<th style="text-align: right">Alum.Rep.</th>
			<th style="text-align: right">%</th>
		</tr>
	</thead>
		<%
	int row = 0;
	for (String maestro : lisMaestros){
		row++;
		
		String numMat = "0";
		if (mapaMaterias.containsKey(maestro)){
			numMat = mapaMaterias.get(maestro);
			totMaterias = totMaterias + Integer.parseInt(numMat);
		}
		
		String numAlum = "0";
		if (mapaAlumnos.containsKey(maestro)){
			numAlum = mapaAlumnos.get(maestro);
			totAlumnos = totAlumnos + Integer.parseInt(numAlum);
		}
		
		String numRep = "0";
		if (mapaReprobados.containsKey(maestro)){
			numRep = mapaReprobados.get(maestro);
			totReprobados = totReprobados + Integer.parseInt(numRep);
		}
		
		float promAlum 	= 0;
		if (!numMat.equals("0")){
			promAlum = Float.parseFloat(numAlum)/Float.parseFloat(numMat);
		}
		
		float porRep	= 0;
		if (!numAlum.equals("0")){
			porRep = (Float.parseFloat (numRep)* 100) /Float.parseFloat (numAlum);
		}
		
		String maestroNombre = "-";
		if (mapaMaestros.containsKey(maestro)){
			maestroNombre = mapaMaestros.get(maestro);
		}
%>
		<tr>
			<td><%=row%></td>
			<td><%=maestro%></td>
			<td><%=maestroNombre%></td>
			<td style="text-align: right"><%=numMat%></td>
			<td style="text-align: right"><%=numAlum%></td>
			<td style="text-align: right"><%=getFormato.format(promAlum)%></td>
			<td style="text-align: right"><%=numRep%></td>
			<td style="text-align: right"><%=getFormato.format(porRep)%></td>
		</tr>
<%		
	}		
		totProm = 0;
		if (totMaterias!=0){
			totProm =  (float)totAlumnos / (float)totMaterias;
		}
		
		if (totAlumnos!=0){
			totPorRep = ((float)totReprobados * 100) / (float)totAlumnos;
		}
%>
		<tr>
			<td colspan="3">T O T A L E S</td>
			<td style="text-align: right"><%=totMaterias%></td>
			<td style="text-align: right"><%=totAlumnos%></td>
			<td style="text-align: right"><%=getFormato.format(totProm)%></td>
			<td style="text-align: right"><%=totReprobados%></td>
			<td style="text-align: right"><%=getFormato.format(totPorRep)%></td>
		</tr>
	</table>
</div>