<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.carga.spring.CargaFinanciero"%>
<%@page import="aca.financiero.spring.FinCalculo"%>
<%@ page import="aca.alumno.spring.AlumAcademico"%>
<%@ page import="aca.alumno.spring.AlumAcademico"%>
<%@ page import="aca.attache.spring.AttacheCustomer"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">	
	
	function Grabar() {
		if (document.frmFinanciero.CargaId.value != "" && document.frmFinanciero.BloqueId.value != "" && document.frmFinanciero.Status.value != "") {
			document.frmFinanciero.submit();
		} else{
			alert("Fill out the entire form");
		}
		}
	
	function borrarImagen(codigoAlumno, cargaId, bloqueId){
		if(confirm("<spring:message code="aca.JSEliminar"/> " ) == true) {
			location.href = "borrarImg?CodigoAlumno="+codigoAlumno+"&CargaId="+cargaId+"&BloqueId="+bloqueId;
		}
	}
</script>

<%

	CargaFinanciero carga;
		
	String codigoAlumno			= (String)request.getAttribute("codigoAlumno")==null?"0":(String)request.getAttribute("codigoAlumno");
	String alumnoNombre 		= (String)request.getAttribute("alumnoNombre");
	String nombrePlan			= (String)request.getAttribute("nombrePlan");
	String cargaId					= (String)request.getAttribute("CargaId");
	String bloqueId					= (String)request.getAttribute("BloqueId");
	String planId 					= (String)request.getAttribute("planId");
	String mensaje					= (String)request.getAttribute("Mensaje");
	boolean esAlumno 							= (boolean)request.getAttribute("esAlumno");
	
	AlumAcademico alumAcademico 				= (AlumAcademico) request.getAttribute("alumAcademico");
	
	FinCalculo finCalc 			=(FinCalculo)request.getAttribute("finCalc");
	
	HashMap<String,CargaFinanciero> mapaCargaFinanciero						= (HashMap<String,CargaFinanciero>)request.getAttribute("mapCargaFinanciero");

	AttacheCustomer customer 	= (AttacheCustomer)request.getAttribute("customer");

	NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
	String formattedOpenBal = "0";
	String formattedUnallocBal = "0";
	String colorOpen = "";
	String colorUnalloc = "";
	if(customer.getOpenbal() != null || customer.getUnallocbal() != null){
		formattedOpenBal = currencyFormatter.format(customer.getOpenbal());
		if(customer.getOpenbal() > 0) colorOpen = "border-warning";
		formattedUnallocBal = currencyFormatter.format(customer.getUnallocbal());
		if(customer.getUnallocbal() < 0) colorUnalloc = "border-success";
	}
	
	if (mapaCargaFinanciero.containsKey(codigoAlumno)){
		carga = mapaCargaFinanciero.get(codigoAlumno);
	}else{
		carga = new CargaFinanciero();
	}
	
	String datosAlumno							= esAlumno?codigoAlumno+" - "+alumnoNombre:"Select a Student!";
	String datosPlan							= esAlumno?planId: "";
	String datosResidencia						= alumAcademico.getResidenciaId().equals("I")?" - <b>Residence:</b> Day Student":" - <b>Residence:</b> Boarding Student";
	
	String institution = (String)session.getAttribute("institucion");
%>

<div class="container-fluid">
	<h2>Student: <%=alumnoNombre  %> <small class="text-muted fs-5">( <%=datosAlumno %> - <%=datosPlan %> <%=datosResidencia%> )</small></h2>
	<div class="alert alert-info">
		<a href="listado" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>
	</div>
	<% if(mensaje.equals("1")){ %>
		<div class="alert alert-success">
			Img deleted!
		</div>
		<% }else if(mensaje.equals("2")){ %>
		<div class="alert alert-danger">
			Error Deleting Image
		</div>
		<% } %>
	<div class="form-group">
		<form name="frmFinanciero" method="post" action="grabar" enctype="multipart/form-data">
			<div class="container-fluid	">
				<label class="form-label">Student ID</label><br>
				<input class="form-control mb-3" type="text" name="CodigoAlumno" value="<%=codigoAlumno %>" style="width: 15rem" readonly>
				<label class="form-label">Load</label><br>
				<input class="form-control mb-3" type="text" name="CargaId" id ="CargaId" value="<%=cargaId %>" style="width: 15rem" readonly>
				<label class="form-label">Block</label><br>
				<input class="form-control mb-3" type="text" name="BloqueId" id="BloqueId" value="<%=bloqueId %>" style="width: 15rem" readonly>
<% 	if(institution.equals("Pacific Adventist University")){%>
				<label class="form-label">Open Balance</label><br>
				<input class="form-control mb-3 <%=colorOpen%>" type="text" name="CurrentBalance" id="CurrentBalance" value="<%=formattedOpenBal%>" style="width: 15rem" readonly>
				<label class="form-label">Unallocated Balance</label><br>
				<input class="form-control mb-3 <%=colorUnalloc%>" type="text" name="CurrentBalance" id="CurrentBalance" value="<%=formattedUnallocBal%>" style="width: 15rem" readonly>
<%	}%>	
				<label class="form-label">Status</label><br>
				<select class="form-select mb-3" name="Status" id="Status" style="width:15rem">
					<option value="A" <%= carga.getStatus().equals("A")?"selected":"" %>>Cleared</option>
					<option value="I" <%= carga.getStatus().equals("I")?"selected":"" %>>Not Cleared</option>
				</select>
				<label class="form-label">Comment</label><br>
				<textarea class="form-control mb-3" type="text" name="Comentario" id="Comentario" value="<%=carga.getComentario()%>" style="width: 20rem"><%=carga.getComentario() %></textarea>  
<%
				if(finCalc.getNombre().equals("") || finCalc.getNombre().isEmpty()){ 				
%>    
        		<input type="file" class="text mb-3" name="imagen" id="imagen">
        		<input type="hidden" name="hasImage" id="hasImage" value="0">
<%
				}else{ 
%>
				<input type="file" class="text" name="imagen" id="imagen" style="display:none">
        		<input type="hidden" name="hasImage" id="hasImage" value="1">
				<div class="mb-3">
					<b>File:</b> 
        			<%=finCalc.getNombre() %>
        			<br><br>
        			<a href="javascript:borrarImagen('<%=codigoAlumno%>','<%=cargaId%>','<%=bloqueId%>')" class="btn btn-danger"> Delete</a><br>
				</div>
<%				
				}
%>
			</div>			
			<div class="alert alert-info">
				<button type="submit" class="btn btn-primary" href="javascript:Grabar()">Save</button>
			</div>
		</form>
	</div>
</div>