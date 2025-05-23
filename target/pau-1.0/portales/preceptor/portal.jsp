<%
	String codigoPreceptor			= (String) session.getAttribute("codigoPreceptor");
	String menuPreceptor 			= (String) session.getAttribute("menuPreceptor");
	String preceptores				= (String) session.getAttribute("preceptores");
	String rolInternado 			= (String) session.getAttribute("rolInternado");	
%>
<body>
<%	String scr = "";
	if (preceptores.contains(codigoPreceptor) || rolInternado.equals("P")){
%> 
<ul class="nav nav-tabs">
	<li class="nav-item"><a class="nav-link <%=menuPreceptor.equals("1")?"active":""%>" href="personal">Staff</a></li>
	<li class="nav-item"><a class="nav-link <%=menuPreceptor.equals("2")?"active":""%>" href="cuartos"><spring:message code="aca.Cuartos"/></a></li>
	<li class="nav-item"><a class="nav-link <%=menuPreceptor.equals("3")?"active":""%>" href="asignarCuartos">Assign Rooms</a></li>
	<li class="nav-item"><a class="nav-link <%=menuPreceptor.equals("4")?"active":""%>" href="cumple">Birthday</a></li>
	<li class="nav-item"><a class="nav-link <%=menuPreceptor.equals("5")?"active":""%>" href="internos"><spring:message code="aca.Asignados"/></a></li>
	<li class="nav-item"><a class="nav-link <%=menuPreceptor.equals("6")?"active":""%>" href="internos_inscritos"><spring:message code="aca.Inscritos"/></a></li>
	<li class="nav-item"><a class="nav-link <%=menuPreceptor.equals("7")?"active":""%>" href="disciplina"><spring:message code="aca.Disciplina"/></a></li>
</ul>
<%	} else if (rolInternado.equals("A")){ %>
<ul class="nav nav-tabs">
	<li class="nav-item"><a class="nav-link <%=menuPreceptor.equals("1")?"active":""%>" href="personal">Staff</a></li>
	<li class="nav-item"><a class="nav-link <%=menuPreceptor.equals("2")?"active":""%>" href="cuartos"><spring:message code="aca.Cuartos"/></a></li>
	<li class="nav-item"><a class="nav-link <%=menuPreceptor.equals("3")?"active":""%>" href="asignarCuartos">Assign Rooms</a></li>
	<li class="nav-item"><a class="nav-link <%=menuPreceptor.equals("4")?"active":""%>" href="cumple">Birthday</a></li>
	<li class="nav-item"><a class="nav-link <%=menuPreceptor.equals("5")?"active":""%>" href="internos"><spring:message code="aca.Asignados"/></a></li>
	<li class="nav-item"><a class="nav-link <%=menuPreceptor.equals("6")?"active":""%>" href="internos_inscritos"><spring:message code="aca.Inscritos"/></a></li>
	<li class="nav-item"><a class="nav-link <%=menuPreceptor.equals("7")?"active":""%>" href="disciplina"><spring:message code="aca.Disciplina"/></a></li>
</ul>
<% } else if(rolInternado.equals("M")){%>
<ul class="nav nav-tabs">
	<li class="nav-item"><a class="nav-link <%=menuPreceptor.equals("3")?"active":""%>" href="asignarCuartos">Assign Rooms</a></li>
	<li class="nav-item"><a class="nav-link <%=menuPreceptor.equals("4")?"active":""%>" href="cumple">Birthday</a></li>
</ul>
<% }%>