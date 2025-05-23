<%@ include file= "../../con_enoc.jsp" %>
<%@ include file="../../idioma.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<%
	String matricula 	= request.getParameter("matricula");
	
	String nombre 		= aca.alumno.AlumUtil.getNombreAlumno(conEnoc, matricula, "NOMBRE");
	
	ArrayList<String> planes = aca.alumno.PlanUtil.getPlanesAlumnoStatic(conEnoc, matricula);
	String select = "";
	String nombrePlan = "";
	
	for(String plan : planes){
		nombrePlan = aca.plan.PlanUtil.getNombrePlan(conEnoc, plan);
		select += "<option value="+plan+">"+plan+"-"+nombrePlan+"</option>";
	}
	
	if(nombre.equals("0000000"))nombre = "Numero de Matrícula No Válido";
	
	out.print(nombre+"&"+select);
%>

<%@ include file= "../../cierra_enoc2.jsf" %>