<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.Date,java.text.SimpleDateFormat"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.alumno.spring.AlumPlan"%>
<%@ page import="aca.plan.spring.MapaPlan"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.vista.spring.Maestros"%>
<%@ page import= "aca.acceso.spring.Acceso"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<head></head>
<%
	DecimalFormat frmDecimal 	= new DecimalFormat("###,##0.00;(###,##0.00)");

	String codigoPersonal 		= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno 		= (String) session.getAttribute("codigoAlumno");
	
	Acceso acceso		 		= (Acceso) request.getAttribute("acceso");
	AlumPersonal alumPersonal 	= (AlumPersonal) request.getAttribute("alumPersonal");
	
	List<AlumPlan> lisPlanes 					= (List<AlumPlan>)request.getAttribute("lisPlanes");
	
	HashMap<String, MapaPlan> mapaPlanes 		= (HashMap<String, MapaPlan>) request.getAttribute("mapaPlanes");
	HashMap<String, String> mapaAsesores 		= (HashMap<String, String>) request.getAttribute("mapaAsesores");
	HashMap<String, Maestros> mapaMaestros 		= (HashMap<String, Maestros>) request.getAttribute("mapaMaestros");
%>
<body>
<div class="container-fluid mt-1">
	<h2>Student Advisers		
		<small class="text-muted fs-4"> ( <%=codigoAlumno%> - <%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoPaterno()%> <%=alumPersonal.getApellidoMaterno()%> )</small>
	</h2>
	<hr>
	<table class="table table-sm table-bordered">
	<thead class="table-info">			
    <tr> 
    	<th width="3%">#</th>    	
        <th width="7%"><spring:message code="aca.Clave"/></th>
        <th width="40%"><spring:message code="aca.Nombre"/> <spring:message code="aca.Plan"/></th>
        <th width="10%"><spring:message code="aca.FechaInicio"/></th>
        <th width="10%"><spring:message code="aca.Oficial"/></th>
        <th width="3%">Edit</th>
        <th width="27%">Adviser</th>
	</tr>
	</thead>
	<tbody>
<%	
	int row=0;
	for (AlumPlan plan : lisPlanes){			
		
		String oficial 		= "N";
		String nombrePlan 	= "-";
		String carreraPlan 	= "0";		 
		if (mapaPlanes.containsKey(plan.getPlanId())){
			nombrePlan 		= mapaPlanes.get(plan.getPlanId()).getNombrePlan();
			oficial			= mapaPlanes.get(plan.getPlanId()).getOficial();
			carreraPlan 	= mapaPlanes.get(plan.getPlanId()).getCarreraId();
		}
		
		boolean autorizado 	= false;
		if (acceso.getAccesos().indexOf(carreraPlan) >= 0) autorizado = true; 
		if (oficial.equals("S")) oficial = "<span class='badge bg-dark'>Yes</span>";
		else if (oficial.equals("N")) oficial = "<span class='badge bg-danger'>NO</span>";
		else if (oficial.equals("I")) oficial = "<span class='badge bg-warning'>IN</span>";
		
		String asesorId		= "0";
		String asesorNombre = "-";
		if (mapaAsesores.containsKey(plan.getCodigoPersonal()+plan.getPlanId())){
			asesorId 		= mapaAsesores.get(plan.getCodigoPersonal()+plan.getPlanId());
			if (mapaMaestros.containsKey(asesorId)){
				asesorNombre 	= mapaMaestros.get(asesorId).getNombre()+" "+mapaMaestros.get(asesorId).getApellidoPaterno()+" "+mapaMaestros.get(asesorId).getApellidoMaterno();
			}		
		} 		
%>						
        <tr> 
           <td><%=++row%></td>
           <td title="<%=nombrePlan%>"><%=plan.getPlanId()%></td>
           <td><%=nombrePlan%></td>           
           <td><%=plan.getFInicio()%></td>
           <td><%=oficial%></td>
           <td>
<%		if (autorizado == true){%>
           	<a href="editar?PlanId=<%=plan.getPlanId()%>"><i class="fas fa-pencil-alt"></i></a>
<%		}else{
			out.print("");
		}
%>           	
           </td>
           <td>
           		<%if(asesorNombre.equals("-")){%>
           			<i class="fas fa-pencil-alt  fa-2x"  href="editar?PlanId=<%=plan.getPlanId()%>"></i>
           		<%}else { 
           			out.println(asesorNombre);
           		}
           		%>
           </td>            
           
           
        </tr>
<%	}%>
	</tbody>		      
	</table>
</div>
</body>