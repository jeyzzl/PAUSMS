<%@ include file="../../idioma.jsp"%>

<%@page import="java.text.SimpleDateFormat"%>
 <!-- page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" -->

    <%@page import="aca.cultural.EventoLugar" %>
    <%@page import="aca.cultural.AlumnoEvento" %>
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
<title>Listado de Eventos proximos</title>
</head>
<body>
<%
	String codigoAlumno = (String) session.getAttribute("codigoAlumno");
    HttpSession mySesion = request.getSession(true);
    mySesion.setAttribute("codigoAlumno", codigoAlumno );

EventoLugar evento = new EventoLugar();
AlumnoEvento alevento = new AlumnoEvento();
String trclass="";
String typebtn="";
String mensaje="";
int totalreservados=alevento.getsumreservados(mySesion.getAttribute("codigoAlumno").toString());


ArrayList<EventoLugar> lst_eventos = new ArrayList<EventoLugar>();
lst_eventos =evento.getEventosProx(mySesion.getAttribute("codigoAlumno").toString());

if(totalreservados<4){
	typebtn="submit";
	mensaje="Tienes <STRONG>[" + totalreservados + "]</STRONG> eventos reservados, aun puedes reservar <STRONG>[" + (4 - totalreservados) + "]</STRONG> mas.";
}
else{
	typebtn="hidden";
	mensaje="Ya has reservado 4 eventos, verifica tus eventos <a href='reservados'>Reservados</a>.";
	
}


%>
<div class="container" id="page">
	<div class="container-fluid">
	<h2>Pr&oacute;ximos eventos</h2>
	<ul class="actions">
		<li><a class="activo" href="disponibles.jsp">Disponibles</a></li>
		<li><a href="reservados">Reservados</a></li>
		<li><a href="acreditados">Acreditados</a></li>
		<li><a href="imprimir">Imprimir Boletos</a></li>
	</ul>
	<div style="clear:both"></div>
	<div class="form">
	<div class="help" id="helpdiv">
		<h3>&iexcl;Bienvenido!</h3>
		<ul>
		<li>Para comenzar, selecciona los eventos a los que desees asistir haciendo click en "&iexcl;Deseo asistir!". Luego presiona el boton "Reservar" que se encuentra al final de la p&aacute;gina.</li>
		<li>Una vez que reserves los eventos, podr&aacute; verlos en la secci&oacute;n de "Reservados", donde tambi&eacute;n podr&aacute;s cancelar tu asistencia. </li>
		<li>Por &uacute;ltimo, en la secci&oacute;n de "Acreditados", est&aacute; una lista de los eventos que a los que ya asististe.</li>
		</ul>
	</div>
	<div class="errorSummary"><%=mensaje %></div>
	<form method="POST" action="reservados.jsp">
	
		<div class="items">
			<div class="list">
			<%
			//String matricula = "1010014";//request.getParameterValues("matricula");
			//request.
				boolean showButton = totalreservados<4;
				for(int i=0;i<lst_eventos.size();i++){
					if(i%2==0)
						trclass="odd";
					else
						trclass="even";					%>
					<div class="evento <%=trclass %>">
						<div class="asistir" style="<%if(!showButton){%>display:none;<%}%>"><input type="checkbox" name="eventosreservar" value=<%=lst_eventos.get(i).getId() %>></div>
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
	<div class="row buttons"><input type="<%=typebtn %>" name="savevento" class="boton" value="Reservar"><!-- Seguro cres que funcionara?... intentalo y me avisas @Jorgelig XD --></div>	
	</form>		
	</div>
	</div>
</div>
<script type="text/javascript"> 
/*<![CDATA[*/
window.addEvent('domready',function(){
	$$('.asistir').getFirst('input').setStyle('display','none');
	$$('.asistir').addEvent('click',function(){
		if(this.hasClass('checked')){
			this.removeClass('checked');
			this.getFirst('input').set('checked',false);
			
		}else{
			this.addClass('checked');
			this.getFirst('input').set('checked',true);
		}
	});

	if(!Cookie.read('firstTime'))
		var myCookie = Cookie.write('firstTime', 'false');
	else
		$('helpdiv').hide();
 
});
/*]]>*/
</script> 
</body>
</html>