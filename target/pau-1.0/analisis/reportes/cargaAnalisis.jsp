<%@page import="aca.vista.MaestrosUtil"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>

<%@ page import= "aca.catalogo.spring.CatPeriodo"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.catalogo.spring.CatTipoCurso"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.vista.spring.Maestros"%>

<html>
	<head></head>
<%		
	String periodoId 		= (String) request.getAttribute("periodoId");
	String cargaId 			= (String) request.getAttribute("cargaId");
	
	List<CatPeriodo> listaPeriodos 	= (List<CatPeriodo>) request.getAttribute("listaPeriodos");
	List<Carga> lisCarga 			= (List<Carga>) request.getAttribute("lisCargas");
	List <CatTipoCurso> lista 		= (List<CatTipoCurso>) request.getAttribute("lista");
	List<CatFacultad> listaFacultad = (List<CatFacultad>) request.getAttribute("listaFacultad");
	
	HashMap<String, String> mapAlumnosPorTipo 	= (HashMap<String, String>) request.getAttribute("mapAlumnosPorTipo");
	HashMap<String, String> mapMateriasPorTipo 	= (HashMap<String, String>) request.getAttribute("mapMateriasPorTipo");
	HashMap<String, Maestros> mapaMaestros 		= (HashMap<String, Maestros>) request.getAttribute("mapaMaestros");
	
	int [] hombresYmujeres 	= (int[]) request.getAttribute("hombresYmujeres");	
	int numMaestros 		= (int) request.getAttribute("numMaestros");
	int numHombres			= hombresYmujeres[0];
	int numMujeres			= hombresYmujeres[1];
	int promedioEdad 		= (int) request.getAttribute("promedioEdad");
	
	List<String> masMaterias 	= (List<String>) request.getAttribute("masMaterias");
	List<String> masAlumnos 	= (List<String>) request.getAttribute("masAlumnos");
	String [] masMat 					= new String [2];
	String [] masAlum 					= new String [2];
	
	Carga carga = new Carga();	
%>
<body>
<div class="container-fluid">
	<h1>Informe de carga</h1>
	<form name="forma" action="cargaAnalisis" method="post">
	<div class="alert alert-info">	
		<a class="btn btn-primary" href="../reportes/menu"><i class="fas fa-arrow-left"></i></a>	
		<b>Periodo: </b>
	  	<select onchange="submit();" name="PeriodoId" class="input input-medium">
		<%	for(int j=0; j<listaPeriodos.size(); j++){
				CatPeriodo periodo = listaPeriodos.get(j);%>
			<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print(" Selected ");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
		<%	}%>
		</select>&nbsp;
		<b>Carga: </b>
		<select name="CargaId" onchange="submit();" style="width:350px;" class="input input-xlarge">
		<%	for(int i=0; i<lisCarga.size(); i++){
			carga = lisCarga.get(i);%>
				<option <%if(cargaId.equals(carga.getCargaId()))out.print(" Selected ");%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId() %>] <%=carga.getNombreCarga()%></option>
		<%	}%>
		</select>
	</div>
	</form>
	<table class="table table-bordered">
	<thead class="table-info">
		<tr>
			<th>Tipo de Curso</th>
			<th class="right">N. Materias</th>
			<th class="right">N. Alumnos</th>
		</tr>
	</thead>	
	<tbody>
<%	
	String numMaterias 	= "";
	String numAlumnos	= "";
	for(CatTipoCurso tipo : lista){			
		numMaterias = "0";
		if (mapMateriasPorTipo.containsKey(tipo.getTipoCursoId()) ){
			numMaterias = mapMateriasPorTipo.get(tipo.getTipoCursoId());
		}
			
		numAlumnos = "0";
		if (mapAlumnosPorTipo.containsKey(tipo.getTipoCursoId()) ){
			numAlumnos = mapAlumnosPorTipo.get(tipo.getTipoCursoId());
		}
%>
		<tr>
			<td>
				<%=tipo.getNombreTipoCurso()%>
			</td>
			<td>
				<div align="right">
					<%=numMaterias%>
				</div>
			</td>
			<td>
				<div align="right">
					<%=numAlumnos%>
				</div>
			</td>
		</tr>
<%	} %>
	</tbody>		
	</table>
	<table class="table table-bordered">
	<thead class="table-info">
		<tr>
			<th>#<spring:message code="aca.Maestros"/></th>
			<th><spring:message code="aca.Hombres"/></th>
			<th><spring:message code="aca.Mujeres"/></th>
			<th>Prom. Edad</th>
		</tr>
	</thead>
		<tr>
			<td>
				<div>
					<%=numMaestros%>
				</div>
			</td>
			<td>
				<div>
					<%=numHombres%>
				</div>
			</td>
			<td>
				<div>
					<%=numMujeres%>
				</div>
			</td>
			<td>
				<div>
					<%=promedioEdad%>
				</div>
			</td>
		</tr>
	</table>
	<table>
		<tr><td style="text-align:center" colspan="2"><font size="3">Top 10 - Alumnos por Maestro</font></td></tr>
	</table>
	<table class = "table">
		<tr>
			<th>
				Nombre Completo
			</th>
			<th>
				Maestro
			</th>
			<th>
				N. Materias
			</th>
		</tr>			
<%		
		int limiteMaterias = masMaterias.size()>9?10:masMaterias.size();			
		for(int i=0; i < limiteMaterias; i++){
			masMat = masMaterias.get(i).split("-");			
			String nombreMaestro = ""; 
			if(mapaMaestros.containsKey(masMat[0])){
				nombreMaestro = mapaMaestros.get(masMat[0]).getNombre()+" "+mapaMaestros.get(masMat[0]).getApellidoPaterno()+" "+mapaMaestros.get(masMat[0]).getApellidoMaterno();				
			}			
%>
		<tr>
			<td>
				<div>
					<%=nombreMaestro%>
				</div>
			</td>			
			<td>
				<div>
					<%=masMat[0] %>					
				</div>
			</td>			
			<td>
				<div>
					<%=0%>
				</div>
			</td>
		</tr>
<%
		}
%>
	</table>
	<table>
		<tr><td style="text-align:center" colspan="2"><font size="3">Top 10 - Alumnos por Maestro</font></td></tr>
	</table>
	<table class = "table">
		<tr>
			<th>
				Nombre Completo
			</th>
			<th>
				Maestros
			</th>
			<th>
				N. Alumnos
			</th>
		</tr>
<%	
		int limiteAlumnos = masAlumnos.size()>9?10:masAlumnos.size();
		for(int i=0; i < limiteAlumnos; i++){
		masAlum = masAlumnos.get(i).split("-");
		
		String nombreMaestro = ""; 
		if(mapaMaestros.containsKey(masAlum[0])){
			nombreMaestro = mapaMaestros.get(masAlum[0]).getNombre()+" "+mapaMaestros.get(masAlum[0]).getApellidoPaterno()+" "+mapaMaestros.get(masAlum[0]).getApellidoMaterno();
		}	
%>
		<tr>
			<td>
				<div>
					<%= nombreMaestro %>
				</div>
			</td>
			<td>
				<div>
					<%=masAlum[0] %>
				</div>
			</td>
			<td>
				<div>
					<%=masAlum[1] %>
				</div>
			</td>
		</tr>
<%		} %>
	</table>
</div>	
</body>
</html>