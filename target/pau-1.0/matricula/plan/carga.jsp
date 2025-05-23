<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.carga.spring.CargaBloque"%>
<%@page import="aca.carga.spring.CargaAlumno"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.plan.spring.MapaPlan"%>
<%@page import="aca.carga.spring.CargaBloque"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.residencia.spring.ResDatos"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<head>
<script type="text/javascript">
	function grabar(){
		if (document.frmCarga.Grupo.value == ""){
			alert("Group Required!");
		}else{
			document.frmCarga.submit();
		}		
	}
	
	function cambioPeriodo(){
		var periodoId = document.getElementById("PeriodoId").value;
		location.href = "carga?PeriodoId="+periodoId;
	}	
	
	function cambioPlan(){
		var planId 		= document.getElementById("PlanId").value;
		var periodoId 	= document.getElementById("PeriodoId").value;
		var cargaId 	= document.getElementById("CargaId").value;
		var bloqueId 	= document.getElementById("BloqueId").value;
		location.href = "carga?PlanId="+planId+"&PeriodoId="+periodoId+"&CargaId="+cargaId+"&BloqueId="+bloqueId;
	}	
	
	function cambioCarga(){
		var periodoId 	= document.getElementById("PeriodoId").value;
		var cargaId 	= document.getElementById("CargaId").value;
		location.href = "carga?PeriodoId="+periodoId+"&CargaId="+cargaId;
	}
	
	function cambiarModo(cargaId,bloqueId,modo){		
		if (confirm("Do you want to change the student's returning mode?")){
			document.location.href = "cambiarModo?CargaId="+cargaId+"&BloqueId="+bloqueId+"&Modo="+modo;
		} 
	}
	
	function borrar(cargaId,bloqueId,planId,materias){
		var mensaje = "";
		if (!materias) {
			mensaje = "This record has subjects assigned. Do you want to delete it?"
		}else{
			mensaje = "Delete Load"
		} 
		var r = confirm(mensaje);
		if (r == true) {
		location.href = "borrarCarga?CargaId="+cargaId+"&BloqueId="+bloqueId+"&PlanId="+planId;
		} 
	}
</script>
<body>
<%
	String mensaje				= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");

	String periodoId				= (String)request.getAttribute("PeriodoId");
	String cargaId					= (String)request.getAttribute("CargaId");
	String bloqueId					= (String)request.getAttribute("BloqueId");
	String planId					= (String)request.getAttribute("PlanId");	
	boolean esAlumno 				= (boolean)request.getAttribute("esAlumno");
	boolean reingreso 				= (boolean)request.getAttribute("reingreso");
	String codigoAlumno				= (String)session.getAttribute("codigoAlumno");
	String nombreAlumno 			= (String)request.getAttribute("nombreAlumno");
	String nombreInstitucion 		= (String)request.getAttribute("nombreInstitucion");
	Acceso acceso 					= (Acceso)request.getAttribute("acceso");	
	ResDatos resDatos 				= (ResDatos)request.getAttribute("resDatos");	
	AlumAcademico alumAcademico 	= (AlumAcademico)request.getAttribute("alumAcademico");	

	List<CatPeriodo> lisPeriodos 				= (List<CatPeriodo>)request.getAttribute("lisPeriodos");
	List<Carga> lisCargas		 				= (List<Carga>)request.getAttribute("lisCargas");
	List<CargaBloque> lisBloques				= (List<CargaBloque>)request.getAttribute("lisBloques");
	List<AlumPlan> lisPlanes 		 			= (List<AlumPlan>)request.getAttribute("lisPlanes");
	List<CargaAlumno> lisCargasAlumno  			= (List<CargaAlumno>)request.getAttribute("lisCargasAlumno");
	HashMap<String, MapaPlan> mapaPlan  		= (HashMap<String, MapaPlan>)request.getAttribute("mapaPlan");
	HashMap<String, CargaBloque> mapaBloques	= (HashMap<String, CargaBloque>)request.getAttribute("mapaBloques");
	HashMap<String, String> mapaMaterias		= (HashMap<String, String>)request.getAttribute("mapaMaterias");
	HashMap<String, String> mapaInscripciones	= (HashMap<String, String>)request.getAttribute("mapaInscripciones");	
	String datosAlumno							= esAlumno?codigoAlumno+" - "+nombreAlumno:"�Select a Student!";
	String datosResidencia						= alumAcademico.getResidenciaId().equals("I")?" - <b>Residence:</b> Day Student":" - <b>Residence:</b> Boarding Student";
	
	if(datosAlumno.equals("�Select a Student!")){
		datosResidencia = "";
	}
	
	int con = 0;
	
	String color = "alert alert-success";
	if(mensaje.equals("1")) {
		color = "alert alert-warning";
		mensaje = "A similar record (green line) already exists in this cycle.!";
	}else if(mensaje.equals("2")) {
		mensaje = "Saved !";
	}
	
	if (cargaId.equals("0") && lisCargas.size() >= 1 ) cargaId = lisCargas.get(0).getCargaId();
	if (bloqueId.equals("0") && lisBloques.size() >= 1) bloqueId = lisBloques.get(0).getBloqueId();
%>
<div class="container-fluid">
	<h2>Choose Plan<small class="text-muted fs-5"> ( <%=datosAlumno%><%=datosResidencia%> )</small></h2>
	<form name="frmCarga" action="guardar" method="post">
		<div class="alert alert-info d-flex align-items-center">		
			Cycle:&nbsp;
			<select id="PeriodoId" name="PeriodoId" onchange="javascript:cambioPeriodo();" class="form-select" style="width:200px">
			<% for(CatPeriodo periodo : lisPeriodos){%>
				<option value="<%=periodo.getPeriodoId()%>" <%=periodo.getPeriodoId().equals(periodoId)?"selected":""%>><%=periodo.getNombre()%></option>
			<% }%>
			</select>
			&nbsp;
			Load:&nbsp;
			<select id="CargaId" name="CargaId" onchange="javascript:cambioCarga();" class="form-select" style="width:350px">
			<% for(Carga carga : lisCargas){%>
				<option value="<%=carga.getCargaId()%>" <%=carga.getCargaId().equals(cargaId)?"selected":""%>><%=carga.getCargaId()%>-<%=carga.getNombreCarga()%></option>
			<% }%>
			</select>
			&nbsp;
			Block:&nbsp;
			<select id="BloqueId" name="BloqueId" onchange="javascript:recargar()" class="form-select" style="width:330px">
<%
		for (aca.carga.spring.CargaBloque bloque : lisBloques){
%>		
			<option value="<%=bloque.getBloqueId()%>" <%=bloqueId.equals(bloque.getBloqueId())?" selected":""%>><%=bloque.getBloqueId()%>-<%=bloque.getNombreBloque()%></option>
<%	
		} 
%>			
			</select>
		</div>
		<div class="alert alert-info d-flex align-items-center">	
			Plan:&nbsp;
			<select id="PlanId" name="PlanId" class="form-select" style="width:200px"  onchange="javascript:cambioPlan();">
			<%
			for(AlumPlan plan : lisPlanes){
				String planNombre = "-";
				if (mapaPlan.containsKey(plan.getPlanId())){
					planNombre = mapaPlan.get(plan.getPlanId()).getNombrePlan();
				}
			%>
				<option value="<%=plan.getPlanId()%>" <%=planId.equals(plan.getPlanId())?"selected":""%>><%=plan.getPlanId()%>-<%=planNombre%></option>
			<% }%>
			</select>
			&nbsp;
			Location:&nbsp;
			<select id="ModoId" name="ModoId" class="form-select" style="width:165px">
				<option value="P"><%=nombreInstitucion %> Campus</option>
<% 			if(acceso.getEnLinea().equals("S")){%>
				<option value="V">Home/Virtual</option>
<% 			}%>
			</select>
			&nbsp;
			Entry:&nbsp;
			<select id="Ingreso" name="Ingreso" class="form-select" style="width:200px">
				<option value="I">First Year</option>
				<option value="R" <%=reingreso?"selected":""%>>Re-Entry</option>
			</select>
			&nbsp;
			Group:&nbsp;
			<input name="Grupo" size="2" maxlength="2" class="form-control" style="width:50px;"/>
			&nbsp;&nbsp;	
		<% if (lisBloques.size()>=1){ %>	
			<a href="javascript:grabar();" class="btn btn-primary"><i class="fas fa-save"></i></a>
		<% } %>
		
		</div>	
	</form>
<% 	if(!mensaje.equals("0")){%>	
	<div class="<%=color%>">
		<%=mensaje%>
	</div>
<% 	}%>	
	<table class="table table-sm table-bordered">
		<tr>
		    <th>No.</th>
		    <th>Load</th>
		    <th>Block</th>
		    <th>Plan</th>
		    <th>Plan Name</th>
		    <th>Location</th>
		    <th>Group</th>
		    <th>Status</th>	
		    <th class="text-center">Subjects</th>
		    <th>Entry</th>
	  	</tr>
<%	
	for(CargaAlumno carga : lisCargasAlumno){
		con++;
		
		String planNombre	= "-";
		String carreraId 	= "0";
		if (mapaPlan.containsKey(carga.getPlanId())){
			planNombre 		= mapaPlan.get(carga.getPlanId()).getNombrePlan();
			carreraId		= mapaPlan.get(carga.getPlanId()).getCarreraId();
		}
		
		boolean remove 			= false;
		boolean esInscrito 		= false;
		String numMaterias 	= "0";
		if (mapaInscripciones.containsKey(carga.getCodigoPersonal()+carga.getCargaId()+carga.getBloqueId())){
			esInscrito = true;
		}
		
		if (mapaMaterias.containsKey(carga.getCargaId()+carga.getBloqueId()) ){
			numMaterias = mapaMaterias.get(carga.getCargaId()+carga.getBloqueId());
		}
		
		if (numMaterias.equals("0")) remove = true;
		
		String alert = "";
		if (carga.getCargaId().equals(cargaId) && carga.getBloqueId().equals(bloqueId) ){
			alert = "class='alert alert-success'";		
		}
		
		String bloqueNombre = "-";
		if (mapaBloques.containsKey(carga.getCargaId()+carga.getBloqueId())){
			bloqueNombre = mapaBloques.get(carga.getCargaId()+carga.getBloqueId()).getNombreBloque();
		}
		
		String estado = "-";		
		if (carga.getEstado().equals("1")) estado = "Active";
		if (carga.getEstado().equals("2")) estado = "Subject Loading";
		if (carga.getEstado().equals("3")) estado = "Payment Due";
		if (carga.getEstado().equals("4")) estado = "Enrolled";		
%>
		<tr <%=alert%> >
			<td>&nbsp;&nbsp;<b><%=con%></b>&nbsp;&nbsp;
<% 			if(remove || acceso.getAdministrador().equals("S")){%>
				<a class="fas fa-trash-alt" href="javascript:borrar('<%=carga.getCargaId()%>','<%=carga.getBloqueId()%>','<%=carga.getPlanId()%>',<%=remove%>)"></a>
<% 			}%>
			&nbsp;</td>						
			<td><%=carga.getCargaId()%></td>
			<td><%=carga.getBloqueId()%>-<%=bloqueNombre%></td>
			<td><%=carga.getPlanId()%></td>
			<td><%=planNombre%></td>
			<td>
		<%	if (acceso.getAccesos().contains(carreraId)){%>
				<a href="javascript:cambiarModo('<%=carga.getCargaId()%>','<%=carga.getBloqueId()%>','<%=carga.getModo().equals("P")?"V":"P"%>')"><i class="fas fa-exchange-alt"></i></a>
		<%	} %>
				&nbsp;<%=carga.getModo().equals("P")?nombreInstitucion + " Campus":"Home/Virtual"%>
			</td>
			<td><%=carga.getGrupo()%></td>
			<td><%=estado%></td>
			<td class="text-center"><%=numMaterias.equals("0")?"<span class='badge bg-warning'>0</span>":"<span class='badge bg-dark'>"+numMaterias+"</span>"%></td>
			<td><%=carga.getIngreso()%></td>
		</tr>						
		<% }%>
	</table>
</div>
</body>
</html>