<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.catalogo.spring.CatNivel"%>
<%@ page import="aca.catalogo.spring.CatModalidad"%>
<%@ page import="aca.alumno.spring.AlumVacacion"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>


<%	
	List<AlumVacacion> lisPeriodos						= (List<AlumVacacion>)request.getAttribute("lisPeriodos");
	HashMap<String,CatNivel> mapaNiveles				= (HashMap<String,CatNivel>)request.getAttribute("mapaNiveles");
	HashMap<String,String> mapaModalidades				= (HashMap<String,String>)request.getAttribute("mapaModalidades");
%>
<head></head>
<body>
<div class="container-fluid">
	<h1>Periodo de Vacaciones</h1>
	<div class="alert alert-info">
		<a href="editar" class="btn btn-primary">Agregar</a>&nbsp;
	</div>

<% if(lisPeriodos.size() > 0){%>

<table style="width:50%"  class="table table-condensed">
  <tr align="center"> 
    <td colspan="6"><strong><font size="3">Listado de Periodos</strong> </td>
  </tr>
  <tr> 
    <th width="1%"><spring:message code="aca.Operacion"/></th>
    <th width="15%">Nivel</th>
    <th width="15%"><spring:message code="aca.Modalidad"/></th>
    <th width="10%">F.Examen</th> 
    <th width="10%">F.Inicio</th>    
    <th width="10%">F.Final</th>
 </tr>
<%
		for(AlumVacacion vacaciones : lisPeriodos){
			
			String nombreNivel = "-";
			if(mapaNiveles.containsKey(vacaciones.getNivelId())){
				nombreNivel = mapaNiveles.get(vacaciones.getNivelId()).getNombreNivel();
			}
			
			String nombreModalidad = "-";
			if(mapaModalidades.containsKey(vacaciones.getModalidadId())){
				nombreModalidad = mapaModalidades.get(vacaciones.getModalidadId());
			}
			
%>
  <tr class="tr2"> 
    <td align="center"> 
    	<a href="editar?NivelId=<%=vacaciones.getNivelId()%>&ModalidadId=<%=vacaciones.getModalidadId()%>" class="icon icon-pencil"></a> &nbsp; 
      	<a href="borrar?NivelId=<%=vacaciones.getNivelId()%>&ModalidadId=<%=vacaciones.getModalidadId()%>" class="fas fa-trash-alt"></a>      
    </td>
    <td align="center"><%=nombreNivel%></td>
    <td align="center"><%=nombreModalidad%></td>
    <td align="center"><%=vacaciones.getfExamen()%></td>    
    <td align="center"><%=vacaciones.getfInicio()%></td>
    <td align="center"><%=vacaciones.getfFinal()%></td>
    </tr>
<%		} %>
</table>
</div>
<%	}else{%>
<font color="black">
	<h3 align="center">No hay Periodos agregados!</h3>
</font>

<%	} %>
		
</body>
<script>
	jQuery('#dateTest').datepicker();
	jQuery('#dateStart').datepicker();
	jQuery('#dateEnd').datepicker();
</script>