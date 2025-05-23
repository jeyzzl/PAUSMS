<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.candado.spring.CandTipo"%>
<%@page import="aca.candado.spring.CandAlumno"%>
<%@page import="aca.alumno.spring.AlumPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	
	function Mostrar(){	
		document.forma.submit();
	}

</script>
<% 		
	String yearActual 		= aca.util.Fecha.getHoy().substring(6,10);
	Acceso acceso			= (Acceso)request.getAttribute("acceso");
	String fechaDefault		= aca.util.Fecha.getHoy().substring(0,6)+String.valueOf(Integer.parseInt(yearActual)-1);	
	String fechaIni 		= request.getParameter("FechaIni")==null?fechaDefault:request.getParameter("FechaIni");		
	String facultadId 		= "X";
	String facTemp 			= "";
	String carreraId 		= "";
	String carrTemp			= "";
	String facultadNombre	= "";
	String carreraNombre 	= "";
	
	// Lista de candados
	List<CandAlumno> lisCandado 				= (List<CandAlumno>)request.getAttribute("lisCandado");	
	
	// Map de tipos de candados
	HashMap<String, CandTipo> mapaTipos 		= (HashMap<String, CandTipo>)request.getAttribute("mapaTipos");
	HashMap<String, CatFacultad> mapaFacultades	= (HashMap<String, CatFacultad>)request.getAttribute("mapaFacultades");
	HashMap<String, CatCarrera> mapaCarreras	= (HashMap<String, CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String, String> mapaCarrerasAlumnos	= (HashMap<String, String>)request.getAttribute("mapaCarrerasAlumnos");	
	HashMap<String,String> mapaAlumnos 			= (HashMap<String,String>) request.getAttribute("mapaAlumnos");
	HashMap<String,String> mapaInscritos		= (HashMap<String,String>) request.getAttribute("mapaInscritos");
%>
<div class="container-fluid">
	<h2>Applied Locks List</h2>
	<form id="forma" name="forma" action="rep_candado" method="post">
	<div class="alert alert-info">
		Start Date: <input id="FechaIni" name="FechaIni" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10" class="form-control d-inline-flex" style="width: 10rem;" />&nbsp;&nbsp;
		<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>
	</div>
	</form>
<%		
	
	for(int i = 0; i<lisCandado.size(); i++){
		int cont=i+1;
		CandAlumno candado = (CandAlumno) lisCandado.get(i);
		carrTemp = "X";
		if (mapaCarrerasAlumnos.containsKey(candado.getCodigoPersonal())){
			carrTemp = mapaCarrerasAlumnos.get(candado.getCodigoPersonal());
		}
		if( (!carrTemp.equals("X")) && (acceso.getAccesos().indexOf(carrTemp)!= -1 || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") )){
			facTemp 		= "0";
			facultadNombre	= "";
			carreraNombre 	= "";
			if (mapaCarreras.containsKey(carrTemp)){
				facTemp = mapaCarreras.get(carrTemp).getFacultadId();
				carreraNombre = mapaCarreras.get(carrTemp).getNombreCarrera();
				if (mapaFacultades.containsKey(facTemp)){
					facultadNombre = mapaFacultades.get(facTemp).getNombreFacultad();
				}
			}			
			if(!facultadId.equals(facTemp)){				
				// Cerrar la tabla anterior 
				if (!facultadId.equals("X")) out.print("</table>");
				
				facultadId = facTemp;
				carreraId = "x";				
%>				
					<div class="alert alert-success"><h2><%=facultadNombre%></h2></div>
				
				<%			
			}
			if (!carreraId.equals(carrTemp)){
				carreraId = carrTemp;				
				%>
				<table id="table" class="table table-sm table-bordered">
				<thead>	 
				<tr>
					<td colspan="8"><b><%=carreraId%> - <%=carreraNombre%></b></td>
				</tr>
				</thead>
				<thead class="table-info">	 
				<tr> 
					<th width="5%"><spring:message code="aca.Numero"/></th>
				    <th width="10%"><spring:message code="aca.Matricula"/></th>
				    <th width="25%"><spring:message code="aca.Nombre"/></th>
				    <th width="10%"><spring:message code="aca.Fecha"/></th>
				    <th width="10%"><spring:message code="aca.Tipo"/></th>
				    <th width="35%"><spring:message code="aca.Comentario"/></th>
				    <th width="5%"><spring:message code="aca.Status"/></th>
				    <th width="5%"><spring:message code="aca.Inscrito"/></th>
				</tr>
				</thead>
			<%				
			}
			
			//Comentado por matr�cula de Julio y Agosto 2020
			/**
			String tipoCandado = "-";
			if (mapaTipos.containsKey(candado.getCandadoId().substring(0, 2))){
				tipoCandado = mapaTipos.get(candado.getCandadoId().substring(0, 2)).getNombreTipo();
			}
			**/
			
			//Se modific� por matr�cula de Julio y Agosto 2020
			String tipoCandado = "-";
			if (mapaTipos.containsKey(candado.getCandadoId().substring(0, 2))){
				tipoCandado = mapaTipos.get(candado.getCandadoId().substring(0, 2)).getNombreTipo();
				if((candado.getCandadoId().substring(0, 2)).equals("06")){
					continue;
				}
			}
			
			String nombreAlumno = "-";
			if(mapaAlumnos.containsKey(candado.getCodigoPersonal())){
				nombreAlumno = mapaAlumnos.get(candado.getCodigoPersonal());
			}
			
			String inscrito = "<span class='badge bg-warning'>NO</span>";
			if(mapaInscritos.containsKey(candado.getCodigoPersonal())){
				inscrito = "<span class='badge bg-success'>SI</span>";
			}
			
			%>					
			  <tr> 
			    <td><%=cont%></td>
			    <td><%=candado.getCodigoPersonal()%></td>
			    <td><%=nombreAlumno%></td>
			    <td><%=candado.getfCreado()%></td>
			    <td><%=tipoCandado%></td>
			    <td><%=candado.getComentario()%></td>
			    <td><%=candado.getEstado().equals("A")?"Active":"Inactive"%></td>
			    <td><%=inscrito%></td>
			  </tr>
<%			
		}
	}	 
%>
	</table>
</div>	

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();		
</script>