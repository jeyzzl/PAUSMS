<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguroJquery.jsf" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumPersonal"/>

<%	
	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");
	int totTraspasos 			= (int) request.getAttribute("totTraspasos");
	String grabados 			= request.getParameter("Grabados")==null?"0":request.getParameter("Grabados");
%>

<script type="text/javascript">	
	function Traspasar(){		
		var x = document.getElementById("loading");
  	  	if (x.style.display === "none") {
 	    	x.style.display = "block";
  	  	} else {
  	    	x.style.display = "none";
  	  	}
		document.forma.submit();
	}
</script>

<head>		
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css"/>
    <link rel = "stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">  
</head>
<div class="container-fluid">
	<h1>Reportes de Exalumnos</h1>
	<form id="forma" name="forma" action="traspasar" method="get"> 
	<div class="alert alert-info">
<%	if (totTraspasos >= 1){ %>	
		<a href="javascript:Traspasar();" class="btn btn-success">Traspasar <span class="bg bg-dark rounded-pill"><%=totTraspasos%></span></a>
<%	}else{%>				
		<a href="menu" class="btn btn-success">Traspasar <span class="bg bg-dark rounded-pill"><%=totTraspasos%></span></a>
<%	}%>	
		<div id="loading" class="spinner-border text-success" role="status" style="display: none;"></div>				
	</div>
<%	if (Integer.parseInt(grabados) >= 1){ %>
	<div class="alert alert-success">Registros que se traspasaron: <%=grabados%>.
<% 	}%>		
	</form>
	<ul  class="list-group" style="width:49%">
  	<li class="list-group-item"> <a href="anioEgreso">1. Egreso y Carreras</a></li>
	<li class="list-group-item"> <a href="paisEdo">2. País y Estado</a></li>
	<li class="list-group-item"> <a href="carreras">3. Carreras</a></li>
	<li class="list-group-item"> <a href="carrerasPais">4. Carreras y Paises</a></li>
	<li class="list-group-item"> <a href="cumple">5. Cumpleaños</a></li>
	</ul>
</div>