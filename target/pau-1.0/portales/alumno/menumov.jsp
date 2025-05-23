<head>		
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<link href="css/portalAlumno.css" rel="STYLESHEET" type="text/css">	
	<link href="../../print.css" rel="STYLESHEET" type="text/css" media="print">
<!-- 	<link rel="stylesheet" href="../../fontawesome/css/font-awesome.min.css"> -->	
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" type='text/css'>
	<link rel="stylesheet" href="../../bootstrap4/css/bootstrap.min.css">
			
	<script src="../../js/jquery-3.3.1.min.js"></script>
	<script src="../../js/popper.min.js"></script>
	<script src="../../bootstrap4/js/bootstrap.min.js"></script>	
</head>
<style>
	.font15 { font-size: 15pt; }
	.font18 { font-size: 18pt; }
	.font20 { font-size: 20pt; }
	.font22 { font-size: 22pt; }
	.alert-alto-m{
		height:35px;
	}
</style>
<%
	String cerrarPortal = (String) session.getAttribute("cerrarPortal");
	String subMenu		= request.getAttribute("SubMenu")==null?"1":request.getAttribute("SubMenu").toString();	
%>
<div class="container-fluid oculto">
<ul class="nav nav-tabs">
  <li class="nav-item">
    <a class="nav-link font22 <%=subMenu.equals("1")?"active":""%>" href="../alumno/resumenmov">Inicio</a>
  </li>
  <li class="nav-item">
    <a class="nav-link font22 <%=subMenu.equals("2")?"active":""%>" href="../alumno/datosmov">Datos</a>
  </li>
  <li class="nav-item">
    <a class="nav-link font22 <%=subMenu.equals("3")?"active":""%>" href="../alumno/materiasmov">Materias</a>
  </li>  
<!--   <li class="nav-item"> -->
<%--     <a class="nav-link font10 <%=subMenu.equals("4")?"active":""%>" href="../alumno/calificaciones">Notas</a> --%>
<!--   </li> -->
  <li class="nav-item">
    <a class="nav-link font22 <%=subMenu.equals("5")?"active":""%>" href="../alumno/financieromov">Cuenta</a>
  </li>
<!--   <li class="nav-item"> -->
<%--     <a class="nav-link font10 <%=subMenu.equals("6")?"active":""%>" href="../alumno/documentos">Archivo</a> --%>
<!--   </li> -->
<!--   <li class="nav-item"> -->
<%--     <a class="nav-link font10 <%=subMenu.equals("7")?"active":""%>" href="../alumno/tramites">Tramites</a> --%>
<!--   </li> -->
<!--   <li class="nav-item"> -->
<%--     <a class="nav-link font10 <%=subMenu.equals("8")?"active":""%>" href="../alumno/servicio">Ser.Soc.</a> --%>
<!--   </li> -->
<!--   <li class="nav-item"> -->
<%--     <a class="nav-link font10 <%=subMenu.equals("9")?"active":""%>" href="../alumno/disciplina">Conducta</a> --%>
<!--   </li> -->
<!--   <li class="nav-item"> -->
<%--     <a class="nav-link font10 <%=subMenu.equals("10")?"active":""%>" href="../alumno/avance">Gradua</a> --%>
<!--   </li> -->
  <li class="nav-item">
    <a class="nav-link font22 <%=subMenu.equals("11")?"active":""%>" href="../alumno/cumplemov">Cumple</a>
  </li>
<!--   <li class="nav-item"> -->
<%--     <a class="nav-link font10 <%=subMenu.equals("12")?"active":""%>" href="../alumno/convalidacion">Convalida</a> --%>
<!--   </li> -->
<!--   <li class="nav-item"> -->
<%--     <a class="nav-link font10 <%=subMenu.equals("13")?"active":""%>" href="../inscEnlinea/validacion">Insc.</a> --%>
<!--   </li>   -->
</ul>
</div>