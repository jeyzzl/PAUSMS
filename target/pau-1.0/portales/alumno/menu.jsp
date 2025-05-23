<head>		
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">	
	<link rel="stylesheet" href="../../print.css" type="text/css" media="print">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/fontawesome.min.css">
  	<link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/solid.min.css">
  	<link rel="stylesheet" href="<%=request.getContextPath()%>/fontawesome5/css/regular.min.css">
  	<link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap5.1/css/bootstrap.min.css">			
  	<script src="<%=request.getContextPath()%>/js/jquery-3.6.0.min.js"></script>	
	<script src="<%=request.getContextPath()%>/js/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/bootstrap5.1/js/bootstrap.min.js"></script>		
</head>
<style>
	.font10 { font-size: 10pt; }
	
	#container{
		margin: 10px 10px 0 10px;
	}
</style>
<%
	String cerrarPortal   = (String) session.getAttribute("cerrarPortal");
  String institution    = (String) session.getAttribute("institucion");
	String subMenu		= request.getAttribute("SubMenu")==null?"1":request.getAttribute("SubMenu").toString();	
%>
<nav class="navbar navbar-expand-lg navbar-light" style="background-color: #eeeeee;">
  <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item <%=subMenu.equals("1")?"active":""%>" style="border-left: 2px solid white;">
        <a class="nav-link" href="../alumno/resumen">Home</a>
      </li>
      <li class="nav-item <%=subMenu.equals("2")?"active":""%>" style="border-left: 2px solid white;">
        <a class="nav-link" href="../alumno/datos">Data</a>
      </li>
      <li class="nav-item" style="border-left: 2px solid white;">
        <a class="nav-link  <%=subMenu.equals("3")?"active":""%>" href="../alumno/materias">Subjects</a>
      </li>
      <li class="nav-item" style="border-left: 2px solid white;">
        <a class="nav-link  <%=subMenu.equals("4")?"active":""%>" href="../alumno/calificaciones">Grades</a>
      </li>
<%  if(institution.equals("Fulton") || institution.equals("Sonoma")){%>
      <li class="nav-item" style="border-left: 2px solid white;">
        <a class="nav-link  <%=subMenu.equals("5")?"active":""%>" href=../alumno/financieroFulton>Account</a>
      </li>
<%  }%>
<%  if(institution.equals("Pacific Adventist University")){%>
      <li class="nav-item" style="border-left: 2px solid white;">
        <a class="nav-link  <%=subMenu.equals("5")?"active":""%>" href=../alumno/financieroPau>Account</a>
      </li>
<%  }%>
      <li class="nav-item" style="border-left: 2px solid white;">
        <a class="nav-link  <%=subMenu.equals("6")?"active":""%>" href="../alumno/documentos">Archive</a>
      </li>
<!--       <li class="nav-item" style="border-left: 2px solid white;"> -->
<%--         <a class="nav-link  <%=subMenu.equals("7")?"active":""%>" href="../alumno/tramites">Applications</a> --%>
<!--       </li> -->
<!--       <li class="nav-item" style="border-left: 2px solid white;"> -->
<%--         <a class="nav-link  <%=subMenu.equals("8")?"active":""%>" href="../alumno/servicio">Social Serv.</a> --%>
<!--       </li> -->
      <li class="nav-item" style="border-left: 2px solid white;">
        <a class="nav-link  <%=subMenu.equals("9")?"active":""%>" href="../alumno/disciplina">Behavior</a>
      </li>
      <li class="nav-item" style="border-left: 2px solid white;">
        <a class="nav-link  <%=subMenu.equals("14")?"active":""%> mx-1" href="../alumno/workline">CTP</a>
      </li>
<!--       <li class="nav-item" style="border-left: 2px solid white;"> -->
<%--         <a class="nav-link  <%=subMenu.equals("10")?"active":""%>" href="../alumno/avance">Graduates</a> --%>
<!--       </li> -->
      <%-- <li class="nav-item" style="border-left: 2px solid white;">
        <a class="nav-link  <%=subMenu.equals("11")?"active":""%>" href="../alumno/cumple">Birthday</a>
      </li> --%>
<!--       <li class="nav-item" style="border-left: 2px solid white;"> -->
<%--         <a class="nav-link  <%=subMenu.equals("12")?"active":""%>" href="../alumno/convalidacion">Validates</a> --%>
<!--       </li> -->
      <li class="nav-item" style="border-left: 2px solid white; border-right: 2px solid white;">
        <a class="nav-link  <%=subMenu.equals("13")?"active":""%>" href="../alumno/pasos">Registration</a>
      </li>
    </ul>
  </div>
</nav>