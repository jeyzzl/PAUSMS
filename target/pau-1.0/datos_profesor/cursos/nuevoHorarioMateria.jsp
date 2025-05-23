<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.vista.spring.CargaHorario"%>
<%@page import="aca.catalogo.spring.CatEdificio"%>
<%@page import="aca.catalogo.spring.CatSalon"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<html>
<head></head>	
<%
	String cursoCargaId 	= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
	String materia 			= request.getParameter("Materia")==null?"0":request.getParameter("Materia");	
	String regresar 		= request.getParameter("Regresar")==null ? "0" : request.getParameter("Regresar");
	
	List<CargaHorario> lisHorarios 				= (List<CargaHorario>) request.getAttribute("lisHorarios"); 
	HashMap<String,CatEdificio> mapaEdificios 	= (HashMap<String,CatEdificio>) request.getAttribute("mapaEdificios");
	HashMap<String,CatSalon> mapaSalones 		= (HashMap<String,CatSalon>) request.getAttribute("mapaSalones");	
%>	
<body>
<div class="container-fluid">
	<h2>Horario de <%=materia%></h2>
	<div class="alert alert-info">
<%	if(regresar.equals("0")){%>
		<a href="cursos" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
<%	}else if (regresar.equals("1")){ %>	
		<a href="../../portales/maestro/cursos" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
<%	}else if (regresar.equals("2")){%>		
		 <a href="../../portales/maestro2/cursos" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
<%	}else if (regresar.equals("3")){%>	
		<a href="../evalua/cursos" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>	
<%	}%>		
	</div>
		
	<table class="table table-sm table-bordered" style="width:70%">
	<tr>
		<th width="5%">#</th>		
		<th width="10%">Día</th>
		<th width="10%">Hora Inicio</th>
		<th width="10%">Hora Fin</th>
		<th width="35%">Edificio</th>
		<th width="30%">Salon</th>
	</tr>
<%
	int row=0;
	for (CargaHorario horario : lisHorarios){
		row++;
		String edificioId 		= "-";
		String salonNombre 		= "-";
		String edificioNombre 	= "";
		if (mapaSalones.containsKey(horario.getSalonId())){			
			salonNombre  	= mapaSalones.get(horario.getSalonId()).getNombreSalon();
			edificioId		= mapaSalones.get(horario.getSalonId()).getEdificioId();
			if (mapaEdificios.containsKey(edificioId)){
				edificioNombre = mapaEdificios.get(edificioId).getNombreEdificio();
			}
		}
		
		String diaNombre = "Domingo";
		if (horario.getDia().equals("2")) diaNombre = "Lunes";
		if (horario.getDia().equals("3")) diaNombre = "Martes";
		if (horario.getDia().equals("4")) diaNombre = "Miércoles";
		if (horario.getDia().equals("5")) diaNombre = "Jueves";
		if (horario.getDia().equals("6")) diaNombre = "Viernes";
		if (horario.getDia().equals("7")) diaNombre = "Sábado";		
%>	
	<tr>
		<td><%=row%></td>		
		<td><%=horario.getDia()+"- "+diaNombre%></td>
		<td><%=horario.getHoraInicio()+":"+horario.getMinutoInicio()%></td>
		<td><%=horario.getHoraFinal()+":"+horario.getMinutoFinal()%></td>
		<td><%=edificioNombre%></td>
		<td><%=salonNombre%></td>		
	</tr>	
<% 	}%>
	</table>
</div>		
</body>
</html>