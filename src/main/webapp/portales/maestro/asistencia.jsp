<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.carga.spring.CargaGrupoAsistencia"%>
<%@page import="aca.carga.spring.CargaGrupoProgramacion"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<!-- inicio de estructura -->
<%
 	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");

	String cursoCargaId			= request.getParameter("CursoCargaId");
	String materia	 			= request.getParameter("Materia");
	
	String nombreMaestro 		= (String) request.getAttribute("nombreMaestro");
	String nombreMateria 		= (String) request.getAttribute("nombreMateria");
	
	List<AlumnoCurso> lisAlumnos 			= (List<AlumnoCurso>) request.getAttribute("lisAlumnos"); 
	List<CargaGrupoProgramacion> lisProgra 	= (List<CargaGrupoProgramacion>)request.getAttribute("lisProgra");
	
	// Consulta la asistencia de los alumnos en cada clase
	HashMap<String, String> mapAsistencia 		= (HashMap<String, String>)request.getAttribute("mapAsistencia");
	HashMap<String, String> mapAsistenciaTotal 	= (HashMap<String, String>)request.getAttribute("mapAsistenciaTotal");
	HashMap<String, AlumPersonal> mapPersonal	= (HashMap<String, AlumPersonal>)request.getAttribute("mapPersonal");
	
	int i=0;	
%>
<div class="container-fluid">
	<h3>Attendance Registration
		<small class="text-muted fs-5">( <%=codigoPersonal%> - <%=nombreMaestro%> - <%=nombreMateria%> )
		</small>
	</h3>
	<div class="alert alert-info">
		<a href="cursos" class="btn btn-primary" ><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
		<a href="altafechas?CursoCargaId=<%=cursoCargaId%>&Materia=<%=materia%>" class="btn btn-primary">New</a>	
	</div>	
	<table style="width:50%" class="table table-sm table-bordered">
	<head class="table-dark">
	<tr> 
      <th width="5%"><spring:message code="aca.Numero"/></th>
      <th width="10%">Option</th>
      <th width="15%">Date</th>
      <th width="15%">List Call</th>
      <th width="18%">Attendance</th>
      <th width="18%">Delays</th>
      <th width="18%">Absents</th>
    </tr>
    </head>
    <body>
<%
	for (i=0; i<lisProgra.size(); i++){
		CargaGrupoProgramacion programa = (CargaGrupoProgramacion) lisProgra.get(i);
		int presente = 0, tardanza = 0, ausente = 0;
		if (mapAsistencia.containsKey(programa.getFolio()+"1")) presente 	= Integer.parseInt(mapAsistencia.get(programa.getFolio()+"1")); 
		if (mapAsistencia.containsKey(programa.getFolio()+"2")) tardanza 	= Integer.parseInt(mapAsistencia.get(programa.getFolio()+"2"));
		if (mapAsistencia.containsKey(programa.getFolio()+"3")) ausente 	= Integer.parseInt(mapAsistencia.get(programa.getFolio()+"3"));		
%>
    <tr class="tr2"> 
      <td ><%=i+1%></td>    
      <td>
      	<a href="altafechas?CursoCargaId=<%=cursoCargaId%>&Materia=<%=materia%>&Folio=<%=programa.getFolio()%>"><i class="fas fa-check"></i></a>&nbsp;
        <a href="javascript:Borrar('<%=cursoCargaId%>','<%=materia%>','<%=programa.getFolio()%>')"><i class="fas fa-trash-alt"></i></a>
      </td>
      <td>
        &nbsp;<%=programa.getFecha()%>
      </td>   
      <td>
        <a title="List Call" href="pasaAsistencia?CursoCargaId=<%=cursoCargaId%>&Materia=<%=materia%>&Folio=<%=programa.getFolio()%>"><i class="far fa-check-square"></i></a>
      </td>   
      <td><%=presente%></td>
      <td><%=tardanza%></td>
      <td><%=ausente%></td>
<%	} %>
	</tr>
	</table>	
	<left><h3>Student statistics</h3></left>	
	</body>
	<table style="width:50%" align="left" class="table table-sm table-bordered">
	<head class="table-dark">
	<tr> 
      <th width="5%"><spring:message code="aca.Numero"/></th>
      <th width="10%"><spring:message code="aca.Matricula"/></th>
      <th width="30%"><spring:message code="aca.Nombre"/></th>
      <th width="18%">Attendance</th>
      <th width="18%">Delays</th>
      <th width="18%">Absents</th>
      <th width="18%">% of Absence</th>
    </tr>
    </head>
    <body>
  	<%
  	int row = 0;
	for (AlumnoCurso ac : lisAlumnos){
		row++;			
		int presenteTotal = 0, tardanzaTotal = 0, ausenteTotal = 0;
		if (mapAsistenciaTotal.containsKey(ac.getCodigoPersonal()+"1")) presenteTotal 	= Integer.parseInt(mapAsistenciaTotal.get(ac.getCodigoPersonal()+"1"));
		if (mapAsistenciaTotal.containsKey(ac.getCodigoPersonal()+"2")) tardanzaTotal 	= Integer.parseInt(mapAsistenciaTotal.get(ac.getCodigoPersonal()+"2"));
		if (mapAsistenciaTotal.containsKey(ac.getCodigoPersonal()+"3")) ausenteTotal 	= Integer.parseInt(mapAsistenciaTotal.get(ac.getCodigoPersonal()+"3"));
		
		String nombreAlumno = "-";
		if (mapPersonal.containsKey(ac.getCodigoPersonal())){
			AlumPersonal alumno = mapPersonal.get(ac.getCodigoPersonal());
			nombreAlumno = (alumno.getApellidoMaterno().equals("-")?"":alumno.getApellidoMaterno())+" "+alumno.getApellidoPaterno()+" "+alumno.getNombre();
		}
		
		float porcentaje = 0;
		
		
		
		
		if (presenteTotal + tardanzaTotal + ausenteTotal  > 0 && ausenteTotal > 0){
			porcentaje =((float)ausenteTotal / (presenteTotal + tardanzaTotal + ausenteTotal)) * 100;
			                                                                                                                                                      
		}else{
			porcentaje = 0;
		}
		
	%>
    <tr>
    	<td><%=row%></td> 
    	<td width="20">
      	<%=ac.getCodigoPersonal()%>
      	<input name="CodigoPersonal<%=ac.getCodigoPersonal()%>" type="hidden" value="<%=ac.getCodigoPersonal()%>"/>
    	</td>     
    	<td>
    	<%=nombreAlumno%>
    	</td>  
    	<td><%=presenteTotal %></td>
    	<td><%=tardanzaTotal %></td>
    	<td><%=ausenteTotal %></td>
    	<td><%=porcentaje  %></td>
    </tr>
<%	}%>
    </body>
	</table>
</div>	
<script>	
	function Borrar(cursoCargaId,materia,folio) {		
		if (confirm("�Are you sure of delete the date and files of attendance?")){
			location.href = "borrarfecha?CursoCargaId="+cursoCargaId+"&Materia="+materia+"&Folio="+folio;
		}
	}

</script>