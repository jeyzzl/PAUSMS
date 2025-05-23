<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.Mapa"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.exa.spring.ExaEgreso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguroJquery.jsf" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String matricula 		= (String) session.getAttribute("codigoAlumno");	
	String accion 			= request.getParameter("Accion")==null?"":request.getParameter("Accion");
	String facultadId		= request.getParameter("FacultadId")==null?"0":request.getParameter("FacultadId");
	
	String alumnoNombre	 			= (String) request.getAttribute("alumnoNombre");
	boolean existeCorreo 			= (boolean) request.getAttribute("existeCorreo");
	boolean existeDireccion  		= (boolean) request.getAttribute("existeDireccion");
	boolean existeFamilia 			= (boolean) request.getAttribute("existeFamilia");
	boolean existeEgreso 			= (boolean) request.getAttribute("existeEgreso");
	boolean existeEmpleo 			= (boolean) request.getAttribute("existeEmpleo");
	boolean existeTelefono 			= (boolean) request.getAttribute("existeTelefono");
	boolean existeIglesia 			= (boolean) request.getAttribute("existeIglesia");
	boolean existeRed	 			= (boolean) request.getAttribute("existeRed");
	
	List<CatFacultad> lisFacultades			= (List<CatFacultad>)request.getAttribute("lisFacultades");	
	List<aca.Mapa> lisCarreras				= (List<aca.Mapa>)request.getAttribute("lisCarreras");	  
	List<Mapa> lisCarrerasAlumno		= (List<Mapa>)request.getAttribute("lisCarrerasAlumno");	
	List<ExaEgreso> lisEgreso 				= (List<ExaEgreso>)	request.getAttribute("lisEgreso");
	HashMap<String,MapaPlan> mapaPlanes 	= (HashMap<String,MapaPlan>)request.getAttribute("mapaPlanes");
	HashMap<String,CatCarrera> mapaCarreras = (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
%>
	<%@ include file= "menu.jsf" %>
	<script>
		jQuery('ul.nav').children('li.egreso').addClass('active').find('i').eq(1).addClass('icon-white');
	</script>
	<style>
		.agregar{
			margin-top:5px;
			margin-bottom:5px;
		}
		i.icon-remove{
			cursor:pointer;
		}
	</style>
	
	<form name="frmCarrera" action="egreso" method="post">
	<input type="hidden" name="Accion" />	
	<table style="margin: 0 auto;" class="table table-condensed table-nohover" style="margin-top:5px; width:600px">
		<tr><th colspan="2">Add registered Degrees to the Alumni</th></tr>
		<tr>
			<td width="10%">			
				<spring:message code='aca.Carrera'/>:&nbsp;
			</td>
			<td width="90%">	
				<select name="PlanId" style="width:450px">
		<%	for(Mapa carrera : lisCarrerasAlumno) {
					out.print("<option value='"+carrera.getLlave()+"'>"+carrera.getLlave()+"-"+carrera.getValor()+"</option>");
			}
		%>
				</select>
			</td>			
		</tr>
		<tr>
			<td>Year:</td>
			<td>
				<input name="Year" onkeypress="return isNumberKey(event)" id="Year" type="text" maxlength="4" class="input-mini" />
			</td>
		</tr>
		<tr>
			<td colspan="2" style="text-align:left;">
				<input type="button" class="btn btn-primary" onclick="javascript:grabar();" value="Save"/>
			</td>
		</tr>
	</table>
	</form>
	
	<form action="egreso" name="frmOtra">
	<input type="hidden" name="Accion" />	
	<table style="margin: 0 auto;" class="table table-condensed table-nohover" style="margin-top:5px; width:600px">
		<tr><th colspan="2">Add other degrees to the alumni</th></tr>
		<tr>
			<td width="10%">			
				<spring:message code='aca.Facultad'/>:&nbsp;
			</td>
			<td width="90%">	
				<select name="FacultadId" style="width:450px" onchange="document.frmOtra.submit();">
					<option value="0" <%=facultadId.equals("0")?"selected":""%>>Select</option>
		<%	for(CatFacultad facultad : lisFacultades) {
		%>	
					<option value="<%=facultad.getFacultadId()%>" <%=facultadId.equals(facultad.getFacultadId())?"selected":""%>>
					<%=facultad.getFacultadId()%>-<%=facultad.getNombreFacultad()%>
					</option>
		<%			
			}
		%>
				</select>
			</td>
		</tr>
		<tr>				
			<td width="10%">			
				<spring:message code='aca.Carrera'/>:&nbsp;
			</td>			
			<td width="90%">	
				<select name="PlanId" style="width:450px">
		<%	for(Mapa carrera : lisCarreras) {
					out.print("<option value='"+carrera.getLlave()+"'>"+carrera.getLlave()+"-"+carrera.getValor()+"</option>");
			}
		%>
				</select>
			</td>
		</tr>
		<tr>
			<td>Year:</td>
			<td>
				<input name="YearOtra" onkeypress="return isNumberKey(event)" id="YearOtra" type="text" maxlength="4" class="input-mini" />
			</td>
		</tr>
		<tr>
			<td colspan="2" style="text-align:left;">
				<input type="button" class="btn btn-primary" onclick="grabarOtra();" value="Save"/>
			</td>
		</tr>
	</table>
	</form>
	
	<table style="margin: 0 auto;" class="table table-bordered table-fontsmall">
		<tr>
			<th><spring:message code="aca.Eliminar"/>:</th>
			<th><spring:message code="aca.Carrera"/></th>
			<th>Year</th>
			<th>Validated</th>
		</tr>
	<%
		for(ExaEgreso egreso: lisEgreso){
			String nombrePlan = "-";
			if (mapaPlanes.containsKey(egreso.getPlanId())){
				nombrePlan = mapaPlanes.get(egreso.getPlanId()).getCarreraSe();
			}
			String nombreCarrera = "";
			if (mapaCarreras.containsKey(egreso.getCarreraId())){
				nombreCarrera = mapaCarreras.get(egreso.getCarreraId()).getNombreCarrera();	
			}
	%>
		<tr>
			<td style="text-align:center;">
				<a href="javascript:borrar('<%=egreso.getEgresoId()%>')"><i class="fas fa-trash-alt"></i></a>
			</td>
	<%  if(!egreso.getPlanId().equals("0")){ %>
			<td><%=nombrePlan%></td>
	<%  }else{ %>
			<td><%=nombreCarrera%></td>
	<%  } %>
			<td><%=egreso.getYear() %></td>
			<td style="text-align:center;"><%=(lisCarreras.contains(egreso.getCarreraId()))?"Yes":"No" %></td>
		</tr>
	<%
		}
		if(lisEgreso.size()==0){
	%>
		<tr>
			<td colspan="6" style="text-align:center;"><h4>No registers found</h4></td>
		</tr>
	<%  } %>
	</table>

	
<div class="popup carreras"></div>
<script>
	function grabar(){		
		if(document.frmCarrera.PlanId.value!="" && document.frmCarrera.Year.value!=""){
			var year = jQuery("#Year").val();			
			if (year.length === 4 ){
				document.frmCarrera.Accion.value = "1";
				document.frmCarrera.submit();
			}else{
				alert('Capture the year with 4 digits');
			}		
		}else{
			alert('All fields are required');
		}
	}
	
	function grabarOtra(){		
		if(document.frmOtra.CarreraId.value!="" && document.frmOtra.YearOtra.value!=""){
			
			var year = jQuery("#YearOtra").val();
			
			if (year.length === 4 ){
				document.frmOtra.Accion.value = "2";
				document.frmOtra.submit();
			}else{
				alert('Capture the year with 4 digits');
			}		
		}else{
			alert('All fields are required');
		}
	}
	
	function borrar(egresoId){
		if (confirm("¿Are you sure you want to delete this register?") ){
			location.href="egreso?Accion=3&EgresoId="+egresoId;
		}
	}
	
	function isNumberKey(evt){		
	   var charCode = (evt.which) ? evt.which : event.keyCode
	   if ( charCode > 31 && ((charCode < 48) || (charCode > 57)) )
	      return false;
	
	   return true;
	}
</script>