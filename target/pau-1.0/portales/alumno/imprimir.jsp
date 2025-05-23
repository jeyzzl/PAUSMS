<%@ include file= "../../con_enoc.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.cultural.AlumnoEvento"%>
<%@page import="aca.cultural.EventoLugar"%>
<%@page import="java.text.SimpleDateFormat"%>
 <!-- page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" -->
<%@page import="aca.cultural.AlumnoEvento" %>
<%@page import="aca.cultural.EventoLugar" %>
<%@page import="java.util.*" %>
<%@page import="jakarta.servlet.*" %>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- <link rel="stylesheet" type="text/css" href="http://cultural.um.edu.mx/css/screen.css" media="screen, projection" />
<link rel="stylesheet" type="text/css" href="http://cultural.um.edu.mx/css/main.css" />
<link rel="stylesheet" type="text/css" href="http://cultural.um.edu.mx/css/form.css" />-->
<link rel="stylesheet" type="text/css" href="css/styles.css" />

<style type="text/css" media="print">
#header{display:none}
.buttons{display:none;}
.warning{display:none;}
.boleto{background-color:red;}
</style>

<script type="text/javascript" src="js/mootools.js"></script>
<script type="text/javascript" src="js/mootools-more.js"></script>
<title>Mis eventos reservados</title>
</head>
<!-- Muchachito malo eso no se hace  :P -->
<body>
<%
	//CAMBIAR!
	String codigoAlumno = (String) session.getAttribute("codigoAlumno");	
	HttpSession ssmatricula = request.getSession(true);
	ssmatricula.setAttribute("codigoAlumno", codigoAlumno );

	String matricula=ssmatricula.getAttribute("codigoAlumno").toString();//(String)session.getAttribute("matricula");//request.getParameter("matricula");
	String[] eventosreservar = request.getParameterValues("eventosreservar");
	String[] eventosdesreservar = request.getParameterValues("eventosdesreservar");
	EventoLugar evento = new EventoLugar();
	AlumnoEvento alevento = new AlumnoEvento();
	ArrayList<EventoLugar> lst_eventos = new ArrayList<EventoLugar>();
	int totalreservados=alevento.getsumreservados(matricula);
	String trclass="";
	
	if(eventosreservar!=null)
	{
		for(int i=0;i<eventosreservar.length;i++){
			if(i<4-totalreservados){
				alevento = new AlumnoEvento(matricula,eventosreservar[i].toString());
				alevento.reservarevento();
			}
		}
		eventosreservar = null;
	}
	
	if(eventosdesreservar!=null)
	{
		for(int i=0;i<eventosdesreservar.length;i++){
			alevento = new AlumnoEvento(matricula,eventosdesreservar[i].toString());
			alevento.desreservarevento();
		}
		eventosdesreservar=null;
	}
	
	lst_eventos =evento.getEventosReservado(matricula);
%>
<div class="container" id="page">
	<div class="container-fluid">
	<div id="header">
		<h2>Eventos próximos disponibles</h2>
		<ul class="actions">
			<li><a href="disponibles.jsp">Disponibles</a></li>
			<li><a href="reservados">Reservados</a></li>
			<li><a href="acreditados">Acreditados</a></li>
			<li><a  class="activo" href="imprimir">Imprimir Boletos</a></li>
		</ul>
	</div>
	<div style="clear:both"></div>
	<br/>
	<div class="warning">Al presionar el bot&oacute;n "Imprimir" que se encuentra en la parte inferior de la p&aacute;gina, solamente se imprimir&aacute;n los boletos, este mensaje y los botones se ocultan autom&aacute;ticamente.</div>
	<div class="form">
	<form method="POST" action="reservados">
	
		<div class="items">
			<div class="list">
			<%
			//String matricula = "1010014";//request.getParameterValues("matricula");
			//request.
			
				for(int i=0;i<lst_eventos.size();i++){
					
					Calendar c1 = Calendar.getInstance();
					Calendar c2 = Calendar.getInstance();
					
					//SimpleDateFormat sdf = new SimpleDateFormat("DD-MM-yyyy HH:mm:SS");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
					SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm a");
					SimpleDateFormat sdf3 = new SimpleDateFormat("EEEE d 'de' MMMMMMMMMM");
					
					c1.setTime(lst_eventos.get(i).getFecha_inicio());
					c2.setTime(lst_eventos.get(i).getFecha_fin());
					
					int day1 = c1.get(Calendar.DAY_OF_YEAR);
					int day2 = c2.get(Calendar.DAY_OF_YEAR);
					
					int rand1 = Math.round(390-Math.round((Math.random()*60)));
					int rand2 = Math.round(0-Math.round((Math.random()*30)));
					
					%>
					
					<table class="boleto_completo" style="background-position:<%=rand1%>px <%=rand2%>px">
					<tr>
					<td class="boleto" style="border-right:1px dashed black;">
						<h1><%=lst_eventos.get(i).getNombre()%></h1>
						
						<%
							if(day1!=day2){
						%>
							<b>FECHA: </b><span class="fecha">Desde el <%=sdf3.format(c1.getTime())%> a las <%=lst_eventos.get(i).hora_inicio%> al <%=sdf3.format(c2.getTime())%> a las <%=lst_eventos.get(i).hora_fin%></span>
						<%
							}else{
						%>
							<b>FECHA: </b><span class="fecha"><%=sdf3.format(c1.getTime())%>, de <%=lst_eventos.get(i).hora_inicio%> a <%=lst_eventos.get(i).hora_fin%></span>
						<%	
							}
						%>
						<br/>
						<b>LUGAR: </b><span class="lugar" title="Ubicaci&oacute;n"><%=lst_eventos.get(i).getLugar() %></span>
						<div class="matricula"><%=ssmatricula.getAttribute("codigoAlumno").toString()%></div>
						<%= aca.alumno.AlumUtil.getNombreCorto(conEnoc, codigoAlumno) %>						
					</td>
					<td class="copia">
						<h3><%=lst_eventos.get(i).getNombre()%></h3>
						<span class="lugar" title="Ubicaci&oacute;n"><%=lst_eventos.get(i).getLugar() %></span>
						<div class="matricula"><%=ssmatricula.getAttribute("codigoAlumno").toString()%></div>
						<%= aca.alumno.AlumUtil.getNombreCorto(conEnoc, codigoAlumno) %>
					</td>
					</tr>
					</table>
					<br/>
					<%
				}
				
			%>
			</div>
		</div>
	<div class="row buttons"><input type="button" onclick="javascript:window.print();" class="boton" name="unc" value="Imprimir"></div>	
	</form>		
	</div>
	</div>
</div>
<script type="text/javascript"> 
/*<![CDATA[*/
window.addEvent('domready',function(){
 
});
/*]]>*/
</script> 
</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>