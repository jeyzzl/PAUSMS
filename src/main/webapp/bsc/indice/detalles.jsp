<%@ include file= "../../con_enoc.jsp"%>
<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="catCarrera" scope="page" class="aca.catalogo.CatCarrera"/>
<jsp:useBean id="Evento" scope="page" class="aca.alumno.AlumEvento"/>
<jsp:useBean id="EventoU" scope="page" class="aca.alumno.AlumEventoUtil"/>

<%
	String carreraId = request.getParameter("carreraId");
	String eventoId = request.getParameter("eventoId");
	int años 		= Integer.parseInt(request.getParameter("year"));
	
	ArrayList<aca.alumno.AlumEgreso> lisAlumEgreso = aca.alumno.AlumEgresoUtil.getListFechaIngresoUm(conEnoc, "AND CARRERA_ID='"+carreraId+"' ORDER BY ENOC.ALUM_NOMBRE(CODIGO_PERSONAL)", eventoId);
	
	Evento = EventoU.mapeaRegId(conEnoc, eventoId);
	int añoGraduacion = Integer.parseInt(Evento.getFecha().split("/")[2]);
%>

<body>
<div class="container-fluid">
	<h2>Alumnos graduandos de <%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, carreraId) %></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="desercion?eventoId=<%=eventoId%>"><spring:message code="aca.Regresar"/></a>
	</div>
	<form name="forma" >
		<table style="width:100%">
			<tr>
				<td valign="top">
					<table style="margin: 0 auto;  width:100%" class="table table-fullcondensed">
					  	<tr><td class="titulo" colspan="4">&nbsp;<br>Alumnos rezagados</td></tr>
					  	<tr>
					  		<th><spring:message code="aca.Numero"/></th>
					  		<th><spring:message code="aca.Nombre"/></th>
					  		<th>Fecha Ingreso</th>
					  		<th><spring:message code="aca.Plan"/></th>
					  	</tr>
					  	<%
					  	int cont = 0;
					  	for(aca.alumno.AlumEgreso alumno: lisAlumEgreso){ 
					  		if(Integer.parseInt( alumno.getFecha().split("/")[2])==(añoGraduacion-años) && Integer.parseInt(alumno.getFecha().split("/")[1])>=8){
					  			continue;
							}
					  		if(Integer.parseInt( alumno.getFecha().split("/")[2])>(añoGraduacion-años)){
					  			continue;
					  		}
					  		
					  		cont++;
					  	%>
					  	<tr>
					  		<td align="center"><%=cont %></td>
					  		<td><%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, alumno.getCodigoPersonal(), "NOMBRE")  %></td>
					  		<td align="center"><%=alumno.getFecha() %></td>
					  		<td align="center"><%=alumno.getPlanId() %></td>
					  	</tr>
					  	<%} %>
					</table>
				</td>
				<td>&nbsp;</td>
				<td valign="top">
					<table style="margin: 0 auto;  width:100%" class="table table-fullcondensed">
					  	 <tr><td class="titulo" colspan="4">&nbsp;<br>Alumnos de primer ingreso</td></tr>
					
					  	<tr>
					  		<th><spring:message code="aca.Numero"/></th>
					  		<th><spring:message code="aca.Nombre"/></th>
					  		<th>Fecha Ingreso</th>
					  		<th><spring:message code="aca.Plan"/></th>
					  	</tr>
					  	<%
					  	cont = 0;
					  	for(aca.alumno.AlumEgreso alumno: lisAlumEgreso){ 
					  		if(Integer.parseInt( alumno.getFecha().split("/")[2])<(añoGraduacion-años)){
								continue;
					  		}
							if(Integer.parseInt( alumno.getFecha().split("/")[2])==(añoGraduacion-años) && Integer.parseInt(alumno.getFecha().split("/")[1])<8)continue;
		
					  		
					  		cont++;
					  	%>
					  	<tr>
					  		<td align="center"><%=cont %></td>
					  		<td><%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, alumno.getCodigoPersonal(), "NOMBRE")  %></td>
					  		<td align="center"><%=alumno.getFecha() %></td>
					  		<td align="center"><%=alumno.getPlanId() %></td>
					  	</tr>
					  	<%} %>
					</table>
				</td>
			</tr>
		</table>
	</form>
</div>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>