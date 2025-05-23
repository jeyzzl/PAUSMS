<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.vista.spring.MenCarrera"%>
<%@page import="aca.mentores.spring.MentAlumno"%>
<%@page import="aca.mentores.spring.MentCarrera"%>
<%@page import="aca.mentores.spring.MentContacto"%>
<%@page import="aca.mentores.spring.MentMotivo"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<%	
	String codigoPersonal  		= (String)session.getAttribute("codigoPersonal");
	String facultadNombre		= (String)request.getAttribute("facultadNombre");
	String periodoNombre		= (String)request.getAttribute("periodoNombre");
	String fechaAconsejado 		= request.getParameter("FechaAconsejado")==null?aca.util.Fecha.getHoy():request.getParameter("FechaAconsejado");	
	String facultad				= request.getParameter("facultad");	
	Acceso acceso				= (Acceso)request.getAttribute("acceso");
	String sFacultad			= "X";
	String sMentor				= "X";
	String sNombre				= "";
	String sConteo				= "";
	String sBgcolor				= "";
	String motivoNombre			= "";
	int j						= 0;
	
	List<MenCarrera> lisMentores			= (List<MenCarrera>) request.getAttribute("lisMentores");
	List<MentContacto> lisContactos			= (List<MentContacto>) request.getAttribute("lisContactos");
	HashMap<String,AlumPersonal> mapaAlumnos = (HashMap<String,AlumPersonal>) request.getAttribute("mapaAlumnos");
	HashMap<String,MentMotivo> mapaMotivos 	= (HashMap<String,MentMotivo>) request.getAttribute("mapaMotivos");
%>
<div class="container-fluid">
	<h2>
		Entrevistas de Tutores <small class="text-muted fs-4">(<%=facultadNombre%>)</small>
	</h2>
	<form name="forma" action="rep_entrevista?facultad=<%=facultad%>" method="post">
	<div class="alert alert-info">
		<a href="facultad?Opcion=2" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
		&nbsp;&nbsp;
		<input id="FechaAconsejado" name="FechaAconsejado" class="input" style="margin:0;" type="text" maxlength="10" data-date-format="dd/mm/yyyy" value="<%=fechaAconsejado %>" />		
		<a onclick="document.forma.submit();" class="btn btn-info"><i class="far fa-calendar-alt"></i> Mostrar</a>
	</div>
	</form>
	<div class="alert">
		<h3><%=periodoNombre%></h3>
	</div>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
	<tr> 
    	<th width="5%" > <div><strong>#</strong></div></th>
    	<th width="25%"> <div><strong>Mentor</strong></div></th>
    	<th width="5%"> <div><strong>Matr&iacute;cula</strong></div></th>
    	<th width="25%"> <div><strong><spring:message code="aca.Nombre"/></strong></div></th>    	
    	<th width="10%"> <div><strong>Tipo</strong></div></th>    	
    	<th width="20%"> <div><strong>Motivo</strong></div></th>
    	<th width="10%"> <div><strong><spring:message code="aca.Fecha"/></strong></div></th>
  	</tr>
  	</thead>
<%	
	for(MenCarrera carreraMentor : lisMentores){
		if( acceso.getAccesos().indexOf(carreraMentor.getCarreraId())!= -1 || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") ){			
			for(MentContacto contacto: lisContactos){
				if (contacto.getMentorId().equals(carreraMentor.getMentorId())){	
					
					if(mapaAlumnos.containsKey(contacto.getCodigoPersonal())){
						sNombre = mapaAlumnos.get(contacto.getCodigoPersonal()).getNombreLegal();
					}
					
					String motivos[] = contacto.getMotivos().split(",");					
					
					String tipoNombre = ""; 
					if (contacto.getTipo().equals("A")) 
						tipoNombre = "Asamblea";
					else if (contacto.getTipo().equals("E")) 
						tipoNombre = "Entrevista";
					else if (contacto.getTipo().equals("R")) 
						tipoNombre = "Reunión";
					else
						tipoNombre = "Visita";		
%>
  	<tr align="right" <%=sBgcolor %>> 
    	<td> <div align="center"><font size=2><%=j+1 %></font></div></td>
    	<td> <div align="left"><font size=2><%=carreraMentor.getMentorNombre() %></font></div></td>
    	<td> <div align="center"><font size=2><%=contacto.getCodigoPersonal() %></font></div></td>
    	<td><div><font size=2><%=sNombre %></font></div></td>
    	<td><div><font size=2><%=tipoNombre%></font></div></td>
    	<td><div><font size=2>
<% 		
					for (String mot : motivos){						
						if(mapaMotivos.containsKey(mot)){
							motivoNombre	= mapaMotivos.get(mot).getMotivoNombre();
							out.print("*"+motivoNombre+" ");
						}
					}
%>   	
    	</font></div></td>    	 	
    	<td><div><font size=2><%=contacto.getFechaContacto() %></font></div></td>
  	</tr>
 <%				}
  			}
		}
	}	
%>
	</table>
</div>
<script type="text/javascript">
	jQuery('#FechaAconsejado').datepicker();
</script>