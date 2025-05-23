<%@page import="java.util.List"%>
<%@page import= "java.util.HashMap"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<% 
	java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	
	String cargaId 			= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
	String carreraId		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");	
	String nombreCarrera 	= (String) request.getAttribute("nombreCarrera");
	String codigoId			= "X";
	
	List<AlumnoCurso> lisCargaCarrera 				= (List<AlumnoCurso>) request.getAttribute("lisCargaCarrera");
	HashMap<String,AlumPersonal> mapaAlumnos 		= (HashMap<String,AlumPersonal>) request.getAttribute("mapaAlumnos");
	HashMap<String,String> mapaMateriasEnCarga		= (HashMap<String,String>) request.getAttribute("mapaMateriasEnCarga");
	HashMap<String,String> mapaReprobadasEnCarga	= (HashMap<String,String>) request.getAttribute("mapaReprobadasEnCarga");
	
	double porRep = 0;
	double totMaterias = 0;
	double totReprobadas = 0;
%>

<body>
<div class="container-fluid">
	<h2>Alumnos Reprobados<small class="text-muted fs-4"> ( <%=cargaId%> - <%=nombreCarrera%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="reprobados?CargaId=<%=cargaId%>&CarreraId=<%=carreraId%>"><i class="fas fa-arrow-left"></i></a>
	</div>	
	<table class="table table-bordered">
	<thead class="table-info">
	<tr>
	    <th width="3%"><spring:message code="aca.Numero"/></th>
	    <th width="5%"><spring:message code="aca.Matricula"/></th>
	    <th width="15%"><spring:message code="aca.Nombre"/></th>
	    <th width="3%" class="right">N° Mat.</th>
	    <th width="3%" class="right">N° Rep.</th>
	    <th width="3%" class="right">Porcentaje</th>
	</tr>
	</thead>
<% 	int row = 1;
	for(AlumnoCurso alum : lisCargaCarrera){
		
		String nombreAlumno = "-";
		if (mapaAlumnos.containsKey(alum.getCodigoPersonal())){
			AlumPersonal alumno = new AlumPersonal();
			alumno = mapaAlumnos.get(alum.getCodigoPersonal());
			nombreAlumno = alumno.getNombre()+" "+alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno();
		}
			
		if(!alum.getCodigoPersonal().equals(codigoId)){
			codigoId = alum.getCodigoPersonal();
			
			int numMaterias = 0;
			if(mapaMateriasEnCarga.containsKey(alum.getCodigoPersonal())){
				numMaterias = Integer.parseInt(mapaMateriasEnCarga.get(alum.getCodigoPersonal()));
			}
			int numReprobadas = 0;
			if(mapaReprobadasEnCarga.containsKey(alum.getCodigoPersonal())){
				numReprobadas = Integer.parseInt(mapaReprobadasEnCarga.get(alum.getCodigoPersonal()));
			}
			porRep 		= ( Double.valueOf( String.valueOf(numReprobadas) ) *100) / Double.valueOf(String.valueOf(numMaterias));
			
			totMaterias		= totMaterias + numMaterias;
			totReprobadas	= totReprobadas + numReprobadas;
	%>
	<tr class="tr2">
	    <td align="center"><%=row%></td>
	    <td><%=alum.getCodigoPersonal() %></td>
	    <td><a href="materias?CargaId=<%=cargaId%>&CarreraId=<%=carreraId%>&codigoId=<%=codigoId%>"><%=nombreAlumno%></a></td>
	    <td class="right"><%=numMaterias%></td>
	    <td class="right"><%=numReprobadas%></td>
	    <td class="right"><%=formato.format( porRep )%></td>
	</tr>
<% 
			row++;
		}
	}
	
	double totPorcentaje = (totReprobadas * 100) / totMaterias;
%>
	<tr>
	   	<th colspan="3">TOTALES</th>
	   	<th width="3%" class="right"><%=totMaterias%></th>
	   	<th width="3%" class="right"><%=totReprobadas%></th>
	   	<th width="3%" class="right"><%=formato.format(totPorcentaje)%></th>
	</tr>
	</table>
</div>	
</body>