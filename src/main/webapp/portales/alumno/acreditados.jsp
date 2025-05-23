<%@ include file="../../idioma.jsp"%>

<%@page import="java.text.SimpleDateFormat"%>
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
<title>Mis eventos Acreditados</title>
<script>
function recarga(form){
    form.submit();	
}
</script>
</head>
<body>
<%
	ArrayList<EventoLugar> lst_eventos = new ArrayList<EventoLugar>();
	ArrayList<EventoLugar> lst_ciclos = new ArrayList<EventoLugar>();	
	String[] eventos;
	
	//CAMBIAR!
	String codigoAlumno = (String) session.getAttribute("codigoAlumno");
    HttpSession mySesion = request.getSession(true);
    mySesion.setAttribute("codigoAlumno", codigoAlumno );
	
	String codigoPersonal=(String)session.getAttribute("codigoAlumno");//request.getParameter("matricula");
	eventos = request.getParameterValues("eventos");
	String trclass="";
	int id_ciclo=0;
	EventoLugar evento = new EventoLugar();
	
	if(eventos!=null)
	{
		for(int i=0;i<eventos.length;i++){
			AlumnoEvento alevento = new AlumnoEvento(codigoPersonal,eventos[i].toString());
			alevento.reservarevento();
		}	
	}
	if(request.getParameterValues("ciclo")!=null){
		id_ciclo=Integer.parseInt(request.getParameterValues("ciclo")[0].toString());
	}else{
		id_ciclo=evento.getidultimoSem(codigoPersonal);
	}
	lst_eventos =evento.getEventosAcreditados(codigoPersonal,id_ciclo);
	lst_ciclos = evento.getCiclosEscolares(codigoPersonal);
%>
<div class="container" id="page">
	<div class="container-fluid">
	<h2>Mis eventos acreditados</h2>
	<ul class="actions">
		<li><a href="disponibles.jsp">Disponibles</a></li>
		<li><a href="reservados">Reservados</a></li>
		<li><a class="activo" href="acreditados">Acreditados</a></li>
		<li><a href="imprimir">Imprimir Boletos</a></li>
	</ul>
	<div style="clear:both"></div>
	<div class="form">
	<form id="acreditados" method="POST" action="acreditados">	
	<input type=hidden name="codigoPersonal" value="<%=(String)session.getAttribute("matricula") %>">
	Selecciona el semestre: <select name="ciclo" onChange="recarga(this.form)">
	<%
	for(int i=0;i<lst_ciclos.size();i++){
		String selected="";
		if(id_ciclo==lst_ciclos.get(i).getCiclo_id()){selected="SELECTED";}
	%>
			<option value ="<%=lst_ciclos.get(i).getCiclo_id() %>" <%=selected %>><%=lst_ciclos.get(i).getNombreciclo() %></option>
	<%}%>
	</select>
	
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
						<h1><img src="img/tipoevento<%=lst_eventos.get(i).getTipo_evento_id()%>.png"/><%=lst_eventos.get(i).getNombre()%></h1>
						<div class="desc">Acreditado el <b><%=lst_eventos.get(i).getFecha_fin() %></b></div>
						<div class="details">
							<span class="fecha"><b><%=lst_eventos.get(i).hora_inicio %></b></span>
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
	</form>		
	</div>
	</div>
	</div>
</body>
</html>