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
<link rel="stylesheet" type="text/css" href="css/stylesBoleto.css" />

<script type="text/javascript" src="js/mootools.js"></script>
<script type="text/javascript" src="js/mootools-more.js"></script>
<title>Mis eventos reservados</title>
</head>
<!-- Muchachito malo eso no se hace  :P -->
<body>
<%
	//CAMBIAR!
	String codigoAlumno = (String) session.getAttribute("codigoAlumno");
    HttpSession mySesion = request.getSession(true);
	mySesion.setAttribute("codigoAlumno", codigoAlumno );	

	String matricula=mySesion.getAttribute("codigoAlumno").toString();//(String)session.getAttribute("matricula");//request.getParameter("matricula");
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
	<h2>Eventos próximos disponibles</h2>
	<ul class="actions">
		<li><a href="disponibles.jsp">Disponibles</a></li>
		<li><a class="activo" href="reservados">Reservados</a></li>
		<li><a href="acreditados">Acreditados</a></li>
		<li><a href="imprimir">Imprimir Boletos</a></li>
	</ul>
	<div style="clear:both"></div>
	<div class="form">
	<form method="POST" action="reservados">
	
		<div class="items">
			<div class="list">
			<%
			//String matricula = "1010014";//request.getParameterValues("matricula");
			//request.
			
				for(int i=0;i<lst_eventos.size();i++){
					if(i%2==0)
						trclass="odd";
					else
						trclass="even";					%>
					<div class="evento <%=trclass %>">
						<div class="desreservar">Cancelar reservaci&oacute;n <input type="checkbox" name="eventosdesreservar" value=<%=lst_eventos.get(i).getId() %>></div>
						<h1><img src="img/tipoevento<%=lst_eventos.get(i).getTipo_evento_id()%>.png"/><%=lst_eventos.get(i).getNombre()%></h1>
						<div class="desc"><%=lst_eventos.get(i).getDescripcion()%></div>
						<div class="details">
						<%
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
							
							if(day1!=day2){
						%>
							<span class="fecha">Desde el <b><%=sdf3.format(c1.getTime())%></b> a las <b><%=lst_eventos.get(i).hora_inicio%></b> al <b><%=sdf3.format(c2.getTime())%></b> a las <b><%=lst_eventos.get(i).hora_fin%></b></span>
						<%
							}else{
						%>
							<span class="fecha"><b><%=sdf3.format(c1.getTime())%></b>, de <b><%=lst_eventos.get(i).hora_inicio%></b> a <b><%=lst_eventos.get(i).hora_fin%></b></span>
						<%	
							}
						%>
							<span class="lugar" title="Ubicaci&oacute;n"><%=lst_eventos.get(i).getLugar() %></span>
							<span class="capacidad" title="Capacidad (Espacios desocupados)"><%=(lst_eventos.get(i).getCapacidad()-lst_eventos.get(i).getOcupados())%>/</span>
							<span class="ocupados"  title="Capacidad (Espacios desocupados)"><%=lst_eventos.get(i).getCapacidad() %></span>
						</div>
					</div>
					<%
				}
				
			%>
			</div>
		</div>
	<div class="row buttons"><input type="submit" class="boton" name="unc" value="Cancelar"><!-- Seguro cres que funcionara?... intentalo y me avisas @Jorgelig XD --></div>	
	</form>		
	</div>
	</div>
</div>
<script type="text/javascript"> 
/*<![CDATA[*/
window.addEvent('domready',function(){
	$$('.asistir').addEvent('click',function(){
		if(this.hasClass('checked')){
			this.removeClass('checked');
			this.getFirst('input').set('checked',false);
			
		}else{
			this.addClass('checked');
			this.getFirst('input').set('checked',true);
		}
	});
 
});
/*]]>*/
</script> 
</body>
</html>