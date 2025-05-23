<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.admision.spring.AdmOpciones"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<% 
	String planes 		= (String) request.getAttribute("planes");
	String mensaje 		= (String) request.getAttribute("mensaje");
	String facultadId	= (String) request.getAttribute("facultadId");
	
	AdmOpciones opcion  = (AdmOpciones) request.getAttribute("opcion");

	List<CatFacultad> lisFacultades  				= (List<CatFacultad>) request.getAttribute("lisFacultades");
	List<MapaPlan> lisPlanes  						= (List<MapaPlan>) request.getAttribute("lisPlanes");
	HashMap<String,CatFacultad> mapaFacultades		= (HashMap<String,CatFacultad>)request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras 		= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");	
%>
<body>
<div class="container-fluid">
	<h2><spring:message code='aca.Planes'/> for <spring:message code='aca.Opcion'/>: <%=opcion.getNombre()%></h2>
	<div class="alert alert-primary d-flex align-items-center flex-row" role="alert">
		<a href="lista" class="btn btn-primary"><i class="fas fa-arrow-left"></i> <spring:message code='aca.Regresar'/></a>&nbsp;&nbsp;
		<spring:message code='aca.Facultad'/>:&nbsp;
		<select id="FacultadId" name="FacultadId" class="form-select" onchange="cambioFacultad();" style="width:200px;">
			   <option value="0" <%=facultadId.equals("0")?"selected":""%>><spring:message code='aca.Todos'/></option>	
		
<% 		for(CatFacultad facultad : lisFacultades){%>
			<option value="<%=facultad.getFacultadId()%>" <%=facultad.getFacultadId().equals(facultadId) ? "selected" : ""%>><%=facultad.getNombreCorto()%></option>
<% 		}%>
		</select>
	</div>
<% 	if(mensaje.equals("1")){%>
	<div class="alert alert-success" role="alert">
		<spring:message code='aca.Grabado'/>
	</div>
<% 	}else if(mensaje.equals("2")){%>
	<div class="alert alert-danger" role="alert">
		<spring:message code='aca.NoGuardado'/>
	</div>
<% 	}%>
	<form action=grabarPlanes method="POST">
		<input type="hidden" id="OpcionId" name="OpcionId" value="<%=opcion.getOpcionId()%>">
		<input type="hidden" id="FacultadId" name="FacultadId" value="<%=facultadId%>">
		<table style="width:70%" class="table table-bordered table-sm">
			<tr class="table-info">
				<th>				
					<a onclick="jQuery('.checkboxCarga').prop('checked',true)" class="badge bg-success"><spring:message code='aca.Todos'/></a>&nbsp;
					<a onclick="jQuery('.checkboxCarga').prop('checked', false)" class="badge bg-warning"><spring:message code='aca.Ninguno'/></a>				
				</th>
				<th class="text-center">#</th>
				<th><spring:message code='aca.Facultad'/></th>
				<th><spring:message code='aca.Plan'/></th>
				<th><spring:message code='aca.Carrera'/></th>
				<th><spring:message code='aca.Estado'/></th>
			</tr>				
<% 		
		String planSelected = "";
		int row=0;
		for(MapaPlan plan : lisPlanes){
			if (planes.contains(plan.getPlanId())){
				planSelected = "checked";
			}else{
				planSelected = "";
			}
			
			String facultadCorto = "-";			
			if (mapaCarreras.containsKey(plan.getCarreraId())){
				facultadCorto = mapaCarreras.get(plan.getCarreraId()).getFacultadId();
				if (mapaFacultades.containsKey(facultadCorto)){
					facultadCorto = mapaFacultades.get(facultadCorto).getNombreCorto();
				}
			}
			
			String colorEstado = "<span class='badge bg-warning'>Inactive</span>";
			if (plan.getEstado().equals("A")) 
				colorEstado = "<span class='badge bg-info'>Admission</span>";
			else if (plan.getEstado().equals("V"))	
				colorEstado = "<span class='badge bg-success'>Current</span>";			
%>
			<tr>
				<td <%=planSelected.length()==0?"style='background-color:orange'":""%>>
					<input class="checkboxCarga" type="checkbox" id="<%=plan.getPlanId()%>" name="<%=plan.getPlanId()%>" value="<%=plan.getPlanId()%>" <%=planSelected%>/>
				</td>
				<td class="text-center"><%=++row%></td>
				<td><%=facultadCorto%></td>
				<td><%=plan.getPlanId()%></td>
				<td>			
					<%=plan.getNombrePlan()%>																					
				</td>
				<td><%=colorEstado%></td>
			</tr>
<% 		}%>
		</table>
		<button type="submit" class="btn btn-primary"><spring:message code='aca.Grabar'/></button>
	</form><br>
</div> 

</body>
<script src="../../js/search.js"></script>
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	jQuery('#buscar').focus().search({table:jQuery("#table")});
	
	function cambioFacultad(){
		var facultadId 	= document.getElementById("FacultadId").value; 
		var opcionId 	= document.getElementById("OpcionId").value;
				
		document.location.href = "editarPlanes?FacultadId="+facultadId+"&OpcionId="+opcionId;
	}
</script>