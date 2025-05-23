<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.vista.spring.CargaAcademica"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%	
	String cargaId 				= (String)request.getAttribute("cargaId");
	int numAlumnos				= 0;
	int numBajas				= 0;		
	
	String carrera				= "";
	String carreraTemp			= "X";
	String strEstado			= "";
	
	List<Carga> lisCargas 							= (List<Carga>) request.getAttribute("lisCargas");
	List<CargaAcademica> lisMaterias 				= (List<CargaAcademica>) request.getAttribute("lisMaterias");
	
	HashMap<String,CatFacultad> mapaFacultades		= (HashMap<String,CatFacultad>)request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras			= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String, String> mapaAlumnos				= (HashMap<String,String>)request.getAttribute("mapaAlumnos");
	HashMap<String, String> mapaInscritos			= (HashMap<String,String>)request.getAttribute("mapaInscritos");
	HashMap<String, String> mapaAcreditados			= (HashMap<String,String>)request.getAttribute("mapaAcreditados");
	HashMap<String, String> mapaReprobados			= (HashMap<String,String>)request.getAttribute("mapaReprobados");
	HashMap<String, String> mapaBajas				= (HashMap<String,String>)request.getAttribute("mapaBajas");
	HashMap<String, String> mapaPendientes			= (HashMap<String,String>)request.getAttribute("mapaPendientes");
%>
<div class="container-fluid">
	<h2>Análisis de materias</h2>
	<form name="forma" action="analisis" method='post'>
	<div class="alert alert-info">	
		<a class="btn btn-primary" href="../reportes/menu"><i class="fas fa-arrow-left"></i></a>	
    	<spring:message code="aca.Carga"/>: 
    	<select name="CargaId" onchange='document.forma.submit()' style="width:350px">
<%	for( Carga carga : lisCargas){%>
			<option value="<%=carga.getCargaId()%>" <%=cargaId.equals(carga.getCargaId())?"selected":""%>>
				<%=carga.getCargaId()%> - <%=carga.getNombreCarga()%>
			</option>
<%	}%>		
		</select>			
	</div>
	</form>
	<table class="table table-bordered">
	<thead class="table-info"> 
	<tr>
	  	<th width="3%" align="center"><spring:message code="aca.Numero"/></th>
	  	<th width="5%" align="center" class="button"><spring:message code="aca.Facultad"/></th>
	  	<th width="20%" align="center" class="button"><spring:message code="aca.Carrera"/></th>
	  	<th width="8%" align="center"><spring:message code="analisis.materias.Acta"/></th>
	  	<th width="20%" align="center" class="button"><spring:message code="aca.Maestro"/></th>	  	
	  	<th width="3%" align="center" class="button"><spring:message code="aca.Sem"/></th>
	  	<th width="20%" align="center" class="button"><spring:message code="aca.Materia"/></th>
	  	<th width="5%" align="center" class="button"><spring:message code="aca.Estado"/></th>
	  	<th width="3%" align="center">#<spring:message code="analisis.materias.Al"/></th>
	  	<th width="3%" align="center"><spring:message code="aca.Insc"/></th>
	  	<th width="3%" align="center"><spring:message code="aca.AC"/></th>
	  	<th width="3%" align="center"><spring:message code="aca.Na"/></th>
	  	<th width="3%" align="center"><spring:message code="aca.Cd"/></th>
	  	<th width="3%" align="center"><spring:message code="aca.B"/></th>	  	
	</tr>	
	</thead>
<%
	int row=0;
	for (CargaAcademica materia : lisMaterias){
		row++;
		String facultadCorto	= "-";
		String carreraNombre 	= "-";
		if (mapaCarreras.containsKey( materia.getCarreraId()) ){
			carreraNombre 		= mapaCarreras.get(materia.getCarreraId()).getNombreCarrera();
			facultadCorto 		= mapaCarreras.get(materia.getCarreraId()).getFacultadId();
			if (mapaFacultades.containsKey(facultadCorto)){
				facultadCorto  	= mapaFacultades.get(facultadCorto).getNombreCorto();
			}
		}		
		if (materia.getEstado().equals("1")) strEstado = "<font color='#5E610B'><b>Creada</b></font>";
		if (materia.getEstado().equals("2")) strEstado = "<font color='blue'><b>Ordinario</b></font>";
		if (materia.getEstado().equals("3")) strEstado = "<font color='green'><b>Extra.</b></font>";
		if (materia.getEstado().equals("4")) strEstado = "<font color='red'><b>Cerrada</b></font>";
		if (materia.getEstado().equals("5")) strEstado = "<font color='black'><b>Registrada</b></font>";
		
		String alumnos = "0";
		if(mapaAlumnos.containsKey(materia.getCursoCargaId())){
			alumnos = mapaAlumnos.get(materia.getCursoCargaId());
		}
		String inscritos = "0";
		if(mapaInscritos.containsKey(materia.getCursoCargaId())){
			inscritos = mapaInscritos.get(materia.getCursoCargaId());
		}
		String acreditados = "0";
		if(mapaAcreditados.containsKey(materia.getCursoCargaId())){
			acreditados = mapaAcreditados.get(materia.getCursoCargaId());
		}
		String reprobados = "0";
		if(mapaReprobados.containsKey(materia.getCursoCargaId())){
			reprobados = mapaReprobados.get(materia.getCursoCargaId());
		}
		String bajas = "0";
		if(mapaBajas.containsKey(materia.getCursoCargaId())){
			bajas = mapaBajas.get(materia.getCursoCargaId());
		}
		String pendientes = "0";
		if(mapaPendientes.containsKey(materia.getCursoCargaId())){
			pendientes = mapaPendientes.get(materia.getCursoCargaId());
		}
%>

  	<tr> 
		<td align="center"><font size="1"><%=row%></font></td>
		<td align="left"><font size="1"><%=facultadCorto%></font></td>
		<td align="left"><font size="1"><%=carreraNombre%></font></td>
		<td align="center"><font size="1"><%=materia.getCursoCargaId() %></font></td>
		<td align="left"><font size="1"><%=materia.getNombre()%></font></td>		
		<td align="center"><font size="1"><%=materia.getCiclo()%></font></td>
		<td align="left"><font size="1"><%= materia.getNombreCurso() %></font></td>
		<td align="left"><font size="1"><%= strEstado %></font></td>
		<td align="left"><font size="1"><%= alumnos %></font></td>
		<td align="left"><font size="1"><%= inscritos%></font></td>
		<td align="left"><font size="1"><%= acreditados %></font></td>
		<td align="left"><font size="1"><%= reprobados %></font></td>
		<td align="left"><font size="1"><%= bajas %></font></td>
		<td align="left"><font size="1"><%= pendientes %></font></td>		
	</tr>
<% 
	}
%>
	</table>
</div>