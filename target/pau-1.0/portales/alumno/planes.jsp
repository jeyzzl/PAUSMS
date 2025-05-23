<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.Date,java.text.SimpleDateFormat"%>
<%@ page import="aca.carga.spring.CargaAlumno"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.alumno.spring.AlumAcademico"%>
<%@ page import="aca.alumno.spring.AlumPlan"%>
<%@ page import="aca.candado.spring.CandTipo"%>
<%@ page import="aca.candado.spring.CandAlumno"%>
<%@ page import="aca.candado.spring.Candado"%>
<%@ page import="aca.financiero.spring.AyudaEstudios"%>
<%@ page import="aca.carga.spring.CargaBloque"%>
<%@ page import="aca.plan.spring.MapaPlan"%>
<%@page import= "aca.catalogo.spring.CatCarrera"%>
<%@page import= "aca.catalogo.spring.CatNivel"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ include file="../alumno/menu.jsp"%>

<head></head>
<%
	DecimalFormat frmDecimal 	= new DecimalFormat("###,##0.00;(###,##0.00)");

	String codigoPersonal 		= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno 		= (String) session.getAttribute("codigoAlumno");

	AlumPersonal alumPersonal 	= (AlumPersonal) request.getAttribute("alumPersonal");
	
	List<AlumPlan> lisPlanes 				= (List<AlumPlan>)request.getAttribute("lisPlanes");
	
	HashMap<String, MapaPlan> mapaPlanes 			= (HashMap<String, MapaPlan>) request.getAttribute("mapaPlanes");
	HashMap<String, String> mapaCreditosPlanes 		= (HashMap<String, String>) request.getAttribute("mapaCreditosPlanes");
	HashMap<String, String> mapaCreditosAlumno 		= (HashMap<String, String>) request.getAttribute("mapaCreditosAlumno");
%>
<body>
<div class="container-fluid mt-1">	
	<div class="alert alert-success">
		<a href="previos"><i class="far fa-arrow-alt-circle-left fa-lg"></i></a>&nbsp;
		<span class="badge rounded-pill bg-dark">1B</span> <spring:message code="portal.alumno.planes.Planes"/>	
		<small class="text-muted fs-6"> ( <%=codigoAlumno%> - <%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoMaterno()%> <%=alumPersonal.getApellidoPaterno()%>	)</small>
	</div>	
<%	if(!lisPlanes.isEmpty() && lisPlanes != null){%>
	<table class="table table-sm table-bordered">
	<thead class="table-dark">
		<tr> 
	    	<th width="3%">#</th>
	        <th width="7%"><spring:message code="aca.Clave"/></th>
	        <th width="32%"><spring:message code="aca.Plan"/> <spring:message code="aca.Nombre"/></th>
	        <th width="8%"><spring:message code="aca.FechaInicio"/></th>
	        <th width="8%"><spring:message code="aca.Oficial"/></th>       
	        <th width="8%" class="center"><spring:message code="portal.alumno.planes.Tipo"/></th>
	        <th width="8%" class="text-end">Plan Credits</th>
	        <th width="8%" class="text-end">Student Credits</th>
	        <th width="8%" class="text-end">% <spring:message code="portal.alumno.planes.Avance"/></th>
	        <th width="10%" class="text-end"><spring:message code="portal.alumno.planes.ProximasMaterias"/></th>
		</tr>
	</thead>
	<tbody>
<%
	int row=1;
	for (AlumPlan plan : lisPlanes){			
		double porcentaje = 0;
		
		String oficial 		= "N";
		String nombrePlan 	= "-"; 
		if (mapaPlanes.containsKey(plan.getPlanId())){
			nombrePlan 	= mapaPlanes.get(plan.getPlanId()).getNombrePlan();
			oficial		= mapaPlanes.get(plan.getPlanId()).getOficial();		
		}
		if (oficial.equals("S")){
			oficial = "<span class='badge bg-success' style='font-size:10px;'>YES</span>";
		}else if (oficial.equals("N")){
			oficial = "<span class='badge bg-warning' style='font-size:10px;'>NO</span>";
		}else if (oficial.equals("I")){
			oficial = "<span class='badge bg-info' style='font-size:10px;'>EN</span>";
		}
		
		int creditosPlan 	= 0; 
		if (mapaCreditosPlanes.containsKey(plan.getPlanId())){
			creditosPlan 	= Integer.parseInt(mapaCreditosPlanes.get(plan.getPlanId()));
		}

		int creditosAlumno 	= 0; 
		if (mapaCreditosAlumno.containsKey(plan.getPlanId())){
			creditosAlumno 	= Integer.parseInt(mapaCreditosAlumno.get(plan.getPlanId()));
		}
		
		if(creditosPlan > 0){
			porcentaje = ((double)creditosAlumno * 100) / (double)creditosPlan;
		}
%>
        <tr> 
           <td><%=row++%></td>
           <td title="<%=nombrePlan%>"><%=plan.getPlanId()%></td>
           <td><%=nombrePlan%></td>           
           <td><%=plan.getFInicio()%></td>
           <td><%=oficial%></td>          
           <td>
               <%=plan.getEstado().equals("1")?"<span class='badge bg-success' style='font-size:10px;'>MAIN</span>":"<span class='badge bg-info' style='font-size:10px;'>ALT</span>"%>
           </td>  
<!-- 	           ALINERA A LA DERECHA           -->
           <td align="right" style="text-align:right;">
           		<%=creditosPlan%>
           </td>          
           <td align="right" style="text-align:right;">
           		<%=creditosAlumno%>
           </td>          
           <td style="text-align:right;">
			<%=frmDecimal.format(porcentaje)%>
		   </td>
		   <td style="text-align:center;"><a href="oferta?PlanId=<%=plan.getPlanId()%>&Origen=Planes"><i class="fas fa-search"></i></a></td>
        </tr>
<%	}%>
	</tbody>		      
	</table>
<% }else{%>
    <div class="alert alert-warning">
        There are no Academic Plan details registered. Please wait until their assigned or contact your advisor.
    </div>
<% }%>
</div>
</body>