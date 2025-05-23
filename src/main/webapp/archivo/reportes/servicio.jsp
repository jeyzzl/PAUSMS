<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.plan.spring.MapaPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String planId				= (String)request.getAttribute("planId");
	List<MapaPlan> lisPlanes		    		= (List<MapaPlan>)request.getAttribute("lisPlanes");
	List<aca.Mapa> lisPlanesPorAlumno    		= (List<aca.Mapa>)request.getAttribute("lisPlanesPorAlumno");	
	List<AlumPersonal> lisAlumnos		    	= (List<AlumPersonal>)request.getAttribute("lisAlumnos");
	HashMap<String,String> mapaEnServicio 		= (HashMap<String,String>)request.getAttribute("mapaEnServicio");
	HashMap<String,String> mapaEnLicenciatura 	= (HashMap<String,String>)request.getAttribute("mapaEnLicenciatura");	
%>
<body>
<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<div class="container-fluid">
	<h2>Students in Social Service or Bachelor's Degree</h2>
	<form name="frmServicio" action="servicio" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<a href="menu" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
		&nbsp;&nbsp;
		<select name="PlanId" class="chosen" onchange="javascript:Refrescar();">
<%	for (MapaPlan plan: lisPlanes){%>
			<option value="<%=plan.getPlanId()%>" <%=planId.equals(plan.getPlanId())?"selected":""%>><%=plan.getPlanId()%> - <%=plan.getCarreraSe()%></option>
<%	}%>			
		</select>
	</div>
	</form>
	<table class="table table-sm table-bordered">
	<tr>
		<th><spring:message code="aca.Numero"/></th>
		<th><spring:message code="aca.Matricula"/></th>
		<th><spring:message code="aca.Nombre"/></th>
		<th>Plans</th>
		<th>Social Service</th>
		<th>Bachelor's Degree</th>
	</tr>
<%
	int i = 0;
	for(AlumPersonal alumno : lisAlumnos){
		i++;	
		
		String fechaServicio = "-";
		if(mapaEnServicio.containsKey(alumno.getCodigoPersonal())){
			fechaServicio = mapaEnServicio.get(alumno.getCodigoPersonal());
		}
		
		String fechaLicenciatura = "-";
		if(mapaEnLicenciatura.containsKey(alumno.getCodigoPersonal())){
			fechaLicenciatura = mapaEnLicenciatura.get(alumno.getCodigoPersonal());
		}
		
		String planesAlumno = "-";
		for (aca.Mapa mapa : lisPlanesPorAlumno){
			if (mapa.getLlave().equals(alumno.getCodigoPersonal())){
				if (planesAlumno.length()==1) planesAlumno = mapa.getValor(); else planesAlumno += ","+mapa.getValor();  
			}
		}
%>
	<tr>		
		<td align="right"><%=i%></td>		
		<td><%=alumno.getCodigoPersonal()%></td>
		<td><%=alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno()+" "+alumno.getNombre()%></td>
		<td><%=planesAlumno%></td>
		<td><%=fechaServicio%></td>
		<td><%=fechaLicenciatura%></td>
	</tr>
<%	} %>
	</table>
</div>	
</body>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript">
	jQuery(".chosen").chosen();
	
	function Refrescar(){		
		document.frmServicio.submit();		
	}
</script>	
