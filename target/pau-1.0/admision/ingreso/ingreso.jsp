<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<jsp:useBean id="InscritosU" scope="page" class="aca.vista.InscritosUtil"/>
<%
	
	String facultad		= "X";
	String fac			= "";
	String bgColor		= ""; 
	int total 			= 0;	
	int totalFac		= 0;
	ArrayList<ArrayList<String>> lisInscritos = InscritosU.getPrimerIngreso(conEnoc, " ORDER BY FACULTAD_ID, CARRERA_ID");
%>
<body>
<div class="container-fluid">
	<h2>First Year student Reports</h2>
	<hr>
	<table class="table table-sm table-bordered table-striped">
	<thead class="table-info">
    <tr>
	    <th><h3><spring:message code="aca.Numero"/></h3></th>
	    <th><h3><spring:message code="aca.Fac"/></h3></th>
	    <th><h3><spring:message code="aca.Carrera"/></h3></th>
	    <th><h3><spring:message code="aca.Mat"/></h3></th>
	    <th><h3><spring:message code="aca.Nombre"/></h3></th>
	    <th><h3><spring:message code="aca.Plan"/></h3></th>
	    <th><h3><spring:message code="aca.Carga"/></h3></th>
	    <th><h3><spring:message code="aca.Res"/></h3></th>
	    <th><h3><spring:message code="aca.Modalidad"/></h3></th>
	    <th><h3><spring:message code="aca.Tipo"/></h3></th>
	    <th><h3><spring:message code="aca.Gen"/></h3></th>
	    <th><h3><spring:message code="aca.Nac"/></h3></th>
	    <th><h3><spring:message code="aca.Pais"/></h3></th>
	    <th><h3><spring:message code="aca.Estado"/></h3></th>
	</tr>
	</thead>
	<tbody> 
<%

	for(int j=0; j<lisInscritos.size(); j++){
		total++;
		ArrayList<String> inscritos = lisInscritos.get(j);				
%>
	<tr>
		<td align="center"><%=total%></td>
		<td align="center"><%=inscritos.get(0) %></td>
		<td><%=inscritos.get(1) %></td>
		<td align="center"><%=inscritos.get(2) %></td>
		<td><%=inscritos.get(3) %></td>
		<td><%=inscritos.get(4) %></td>
		<td align="center"><%=inscritos.get(5) %></td>
		<td align="center"><%=inscritos.get(6) %></td>
		<td align="center"><%=inscritos.get(7) %></td>
		<td align="center"><%=inscritos.get(8) %></td>
		<td align="center"><%=inscritos.get(9) %></td>
		<td align="center"><%=inscritos.get(10).equals("91")?"Mex.":"Ext."%></td>
		<td><%=inscritos.get(11) %></td>
		<td><%=inscritos.get(12)==null ? "-" : inscritos.get(12) %></td>
	</tr>
<%	}%>
	</tbody>
	</table>	
</div>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>