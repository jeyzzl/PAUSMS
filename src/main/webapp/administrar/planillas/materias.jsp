<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.vista.spring.CargaAcademica"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String cargaId 			= (String) session.getAttribute("cargaId");
	String carreraId 		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");
	String carreraNombre 	= request.getParameter("CarreraNombre")==null?"0":request.getParameter("CarreraNombre");	
	String mensaje 			= "";
	
	int i=0,nCursos=0;
	int row =-1;
	int Cre=0,Or=0,Ex=0,Ce=0,Re=0;
	List<CargaAcademica> lisCursos 				= (List<CargaAcademica>) request.getAttribute("lisCursos");
	HashMap<String, String> mapaModalidades		= (HashMap<String, String>) request.getAttribute("mapaModalidades");
	HashMap<String, String> mapaAlumnos			= (HashMap<String, String>) request.getAttribute("mapaAlumnos");
	
%>
<div class="container-fluid"> 
	<h2>Listado de planillas del periodo <%=cargaId%> <small class="text-muted h4">(<%=carreraId%> - <%=carreraNombre%>)</small></h2>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="entregas"><i class="fas fa-arrow-left"></i></a>
		&nbsp;&nbsp;&nbsp;
		<span class="badge bg-success" onclick="jQuery('.materia').prop('checked',true)">Todos</span>&nbsp;
		<span class="badge bg-warning" onclick="jQuery('.materia').prop('checked',false)">Ninguno</span>
	</div>
				
	<table class="table table-sm" style="margin:0 auto;">
	<tr>
		<th>#</th>
		<th><spring:message code="aca.Entrega"/></th>
		<th><spring:message code="aca.Plan"/></th>
		<th><spring:message code="aca.Materia"/></th>
		<th><spring:message code="aca.Maestro"/></th>
		<th><spring:message code="aca.NoAlum"/></th>
		<th><spring:message code="aca.Estado"/></th>
		<th><spring:message code="aca.Modalidad"/></th>
	</tr>
<%  	
	for (CargaAcademica grupo: lisCursos){
		row++;			
		if (grupo.getEstado().equals("1")) Cre++;
		else if (grupo.getEstado().equals("2")) Or++;
		else if (grupo.getEstado().equals("3")) Ex++;
		else if (grupo.getEstado().equals("4")) Ce++;
		else if (grupo.getEstado().equals("5")) Re++;
		
		String modalidadNombre = "-";
		if (mapaModalidades.containsKey(grupo.getModalidadId())){
			modalidadNombre = mapaModalidades.get(grupo.getModalidadId());
		}
		
		String alumnos = "0";
		if (mapaAlumnos.containsKey(grupo.getCursoCargaId())){
			alumnos = mapaAlumnos.get(grupo.getCursoCargaId());
		}
%>			
	<tr>
		<td align="left"><%=row+1 %></td>
		<td align="left">
<%		if (grupo.getEstado().equals("4")) {
			nCursos++;
%>					
			<input type="checkbox" class="checkbox materia" name="chkCurso<%=nCursos%>" value="1">
			<input type="hidden" name="cursoCargaId<%=nCursos%>" value="<%=grupo.getCursoCargaId()%>">
<%		}else out.print("&nbsp;");
%>			
		</td>
		<td align="left"><%=grupo.getPlanId()%></td>
		<td><%=grupo.getNombreCurso()%></td>
		<td><%=grupo.getNombre()%></td>
		<td align="left"><%=alumnos%></td>
		<td align="left">
<%			if (grupo.getEstado().equals("1")) out.print("Creada");
			else if (grupo.getEstado().equals("2")) out.print("Ordinario");
			else if (grupo.getEstado().equals("3")) out.print("Extra");
			else if (grupo.getEstado().equals("4")) out.print("Cerrada");
			else if (grupo.getEstado().equals("5")) out.print("Entregada");
%>		</td>
		<td align="left"><%=modalidadNombre%></td>
	</tr>
<%	}
	if (nCursos>0){
%>	
	<tr>
		<th>	
			<input class="btn btn-primary" type="submit" type="button" value="Guardar">
			<input type="hidden" name="nCursos" value="<%=nCursos%>">
		</th>
		<th></th>
		<th></th>
		<th></th>
		<th></th>
		<th></th>
		<th></th>
		<th></th>
  	</tr>		
<%	}%> 
	</table>
	<hr>	
	<table class="table table-sm" style="margin: 0 auto;">
	<tr>
		<th style="text-align:center;"><spring:message code="aca.Creada"/></th>
		<th style="text-align:center;"><spring:message code="aca.Ordinario"/></th>
		<th style="text-align:center;"><spring:message code="aca.Extra"/></th>
		<th style="text-align:center;"><spring:message code="aca.Cerrada"/></th>
		<th style="text-align:center;"><spring:message code="aca.Registrada"/></th>
	</tr>		
	<tr>
		<td style="text-align:center;"><%=Cre %></td>
		<td style="text-align:center;"><%=Or %></td>
		<td style="text-align:center;"><%=Ex %></td>
		<td style="text-align:center;"><%=Ce %></td>
		<td style="text-align:center;"><%=Re %></td>
		
	</tr>
	</table>
	</form>
</div>