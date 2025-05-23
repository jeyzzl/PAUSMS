<%@page import="java.util.*" %>
<%@page import="java.text.*" %>

<%@page import="aca.bec.spring.BecTipo"%>
<%@page import="aca.alumno.spring.AlumPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<style>
	.circuloAzul{		
		width: 	120px;
		height: 120px;
		border-radius: 100%;		
		/*background:-moz-linear-gradient(top, red, gold);*/
		/*background:radial-gradient(center center, circle, black, #1162ac);*/
		/*background:-moz-radial-gradient(center center, circle, black, #1162ac);*/
		background:radial-gradient(100px top, ellipse, black, #5A944D , black);		
		background:-moz-radial-gradient(100px top, ellipse, black, #5A944D, black);
		background:-webkit-radial-gradient(100px top, ellipse, black, #5A944D, black);		
		background:-o-radial-gradient(100px top, ellipse, black, #5A944D, black);		
		background:-ms-radial-gradient(100px top, ellipse, black, #5A944D, black);									
	}
	.circuloGris{		
		width: 	120px;
		height: 120px;
		border-radius: 100%;		
		background:radial-gradient(100px top, ellipse, black, #CCFFAA, black);		
		background:-moz-radial-gradient(100px top, ellipse, black, #CCFFAA, black);
		background:-webkit-radial-gradient(100px top, ellipse, black, #CCFFAA, black);		
		background:-o-radial-gradient(100px top, ellipse, black, #CCFFAA, black);		
		background:-ms-radial-gradient(100px top, ellipse, black, #CCFFAA, black);									
	}
	
</style>
<%		
	String planId				= (String)request.getAttribute("planId");
	String nombreAlumno 		= (String)request.getAttribute("nombreAlumno");
	String codigoAlumno			= (String)request.getAttribute("codigoAlumno");
	String tipoNombre			= (String)request.getAttribute("tipoNombre");

	List<BecTipo> lisTipos 				= (List<BecTipo>)request.getAttribute("lisTipos");	
	List<AlumPlan> lisPlanes			= (List<AlumPlan>)request.getAttribute("lisPlanes");
	HashMap<String,String> mapaPlanes	= (HashMap<String,String>)request.getAttribute("mapaPlanes");
%>
<%@ include file= "menu.jsp" %>
<div class="container-fluid">	
	<a href="previos"><i class="far fa-arrow-alt-circle-left fa-lg"></i></a>&nbsp; Becas<small class="text-muted fs-6"> ( <%=nombreAlumno%> - <%=codigoAlumno%> - <%=tipoNombre%> )</small>	
	<form name="frmExperiencias" action="experiencias" method="post">
	<div class="alert alert-info d-flex align-items-center">
		Plan de estudio:
		<select name="PlanId" class="form-select" onchange="document.frmExperiencias.submit();" style="width:700px;">
<% 	for (AlumPlan alumPlan : lisPlanes){
		String planNombre = "-";
		if (mapaPlanes.containsKey(alumPlan.getPlanId())){
			planNombre = mapaPlanes.get(alumPlan.getPlanId());
		}
%>
			<option value="<%=alumPlan.getPlanId()%>" <%=alumPlan.getPlanId().equals(planId)?"selected":""%>><%=alumPlan.getPlanId()%>-<%=planNombre%></option>		
<%	} %>			
		</select>	
	</div>
	</form>	
<%	if(lisTipos.size()>0){ %>	
	<h5>Tipos de becas en las que puedes aplicar<small class="text-muted fs-6">( Clic en en el icono <i class="far fa-file-pdf"></i> para ver mas detalles )</small></h5><hr>
<%	}else{ %>
	<h5>No hay becas disponibles para este plan de estudios</h5><hr>
<%	}%>
<%
	int row = 0;
	boolean existe = false;
	for (BecTipo becTipo : lisTipos){		
		if (row%5==0){
			if (existe) out.print("</div>"); else existe = true;
			out.print("<div class='row mt-2'>");
		}
		row++;		
		String circulo = "circuloAzul";		
%>	
   <div class="col-xs-12 col-sm-6 col-md-4 col-lg-2 col-xl-1 justify-content-center mx-auto"  data-bs-toggle="tooltip" data-bs-placement="top" title="<%=becTipo.getDescripcion()%>">
		<div class="<%=circulo%> d-flex align-items-center justify-content-center mx-auto position-relative px-1">
			<span class="text-center" style="color:white; font-size:9pt">
				<b><%=becTipo.getNombre()%></b><div class="position-absolute bottom-0 start-50 translate-middle" style="color:white;"><a href="tipo<%=becTipo.getTipo()%>.pdf" target="_blank" style="color:white"><i class="far fa-file-pdf fa-lg"></i></a></div>
			</span>			
		</div>
	</div>	
<%	}out.print("</div>"); %>	
</div>	
<style>
	body{
 		background : white;
 	}
 </style>
 <script>
	var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
	var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
 	 return new bootstrap.Tooltip(tooltipTriggerEl)
	})
</script>