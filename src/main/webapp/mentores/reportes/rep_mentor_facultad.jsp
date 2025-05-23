<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.vista.spring.MenCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />

<%
	String codigoPersonal   = (String)session.getAttribute("codigoPersonal");

	String fechaAconsejado 	= request.getParameter("FechaAconsejado")==null?aca.util.Fecha.getHoy():request.getParameter("FechaAconsejado");
	String facultad			= "X";			
	
	// lista de mentores en u periodo escolar ordenados por facultades
	List<MenCarrera> lisCarMentor			= (List<MenCarrera>)request.getAttribute("lisCarMentor");

	// MAP DE ALUMNOS
	HashMap<String,String> mapaAlumnos 		= (HashMap<String,String>) request.getAttribute("mapaAlumnos");
%>

<div class="container-fluid">
	<h1>Tutores por Facultad</h1>
	<form name="forma" action="rep_mentor_facultad" method="post">	
	<div class="alert alert-info d-flex align-items-center">
		<a href="menu" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		<input id="FechaAconsejado" name="FechaAconsejado" class="form-control" style="margin:0;" type="text" maxlength="10" data-date-format="dd/mm/yyyy" value="<%=fechaAconsejado %>" />		
		<a onclick="document.forma.submit();" class="btn btn-info"><i class="icon-calendar icon-white"></i> Mostrar</a>
	</div>
	</form>
	<table id="table" class="table table-sm table-bordered">
<% 	for(int i=0; i<lisCarMentor.size(); i++){
		MenCarrera carreraMentor = (MenCarrera) lisCarMentor.get(i);
		if	(!facultad.equals(carreraMentor.getFacultadId())){
			facultad = carreraMentor.getFacultadId();
%>	  
	<thead>	 
	  <tr>
	    <td colspan="4" align="left"><font size="3"><strong><%=carreraMentor.getFacultadNombre() %></strong></td>
	  </tr>  
 	</thead>
	<thead class="table-info">	 
	  <tr align="center">
	    <th><spring:message code="aca.Numero"/></th>
	    <th height="15"><spring:message code="aca.Clave"/></th>
	    <th><spring:message code="aca.Nombre"/></th>
	    <th class="right">Aconsejados</th>
	  </tr>
 	</thead>
<% 		}
		//Busca numero de Alumnos por mentor
		String numAlum			= "0";
		if(mapaAlumnos.containsKey(carreraMentor.getMentorId()+facultad)){
			numAlum = mapaAlumnos.get(carreraMentor.getMentorId()+facultad);
		}		
%>
	  <tr>
	    <td align="center"><%=i+1%></td>
	    <td align="center"><%=carreraMentor.getMentorId()%></td>
	    <td align="left">&nbsp;<%=carreraMentor.getMentorNombre() %></td>
	    <td class="right"><%=numAlum%></td>
	  </tr>
<%	} %>
	</table>
</div>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script type="text/javascript">
	jQuery('#FechaAconsejado').datepicker();
</script>