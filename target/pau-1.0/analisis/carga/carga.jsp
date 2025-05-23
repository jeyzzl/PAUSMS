<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ page import= "aca.alumno.spring.AlumPlan"%>
<%@	page import="aca.catalogo.spring.CatPeriodo"%>
<%@	page import="aca.catalogo.spring.CatFacultad"%>
<%@	page import="aca.catalogo.spring.CatCarrera"%>
<%@	page import="aca.carga.spring.Carga"%>

<script type="text/javascript">	
	function Mostrar(){		
		var x = document.getElementById("loading");
  	  	if (x.style.display === "none") {
 	    	x.style.display = "block";
  	  	} else {
  	    	x.style.display = "none";
  	  	}
		document.forma.submit();
	}
	
	function cambiaPeriodo(periodoId){
		document.location.href="carga?PeriodoId="+periodoId+"&cambioPeriodo=1";
	}
	
	function cambiaCarga(){		
		document.location.href="carga?CargaId="+document.forma.CargaId.value+"&cambioCarga=S";
	}
</script>

<%
	java.text.DecimalFormat getFormato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String codigo			= (String) session.getAttribute("codigoPersonal");
// 	String creditosPlan 	= (String)request.getAttribute("creditosPlan");
	String periodoId 		= (String) request.getAttribute("periodoId");
	String cargaId 			= (String) request.getAttribute("cargaId");
	
	/* Lista de planes de estudio*/
	List<AlumPlan> lisAlumnos 		= (List<AlumPlan>)request.getAttribute("lisAlumnos");
	List<CatPeriodo> listaPeriodos 		= (List<CatPeriodo>) request.getAttribute("listaPeriodos");
	List<Carga> lisCarga 				= (List<Carga>) request.getAttribute("lisCarga");
	
	/* HashMap de los nombres de los alumnos */
	HashMap<String,String> mapAlumnos 	= (HashMap<String,String>)request.getAttribute("mapAlumnos");
	
	/* HashMap de los créditos de los alumnos en el plan */
	HashMap<String,String> mapCreditos 	= (HashMap<String,String>)request.getAttribute("mapCreditos");
	
	/* HashMap de la última fecha de inscripción de los alumnos */
	HashMap<String,String> mapUltimaFecha	= (HashMap<String,String>)request.getAttribute("mapUltimaFecha");
	
	HashMap<String,String> mapCreditosCarga	= (HashMap<String,String>)request.getAttribute("mapCreditosCarga");
	
	HashMap<String, CatFacultad> mapaFacultad = (HashMap<String, CatFacultad>)request.getAttribute("mapaFacultad");
	
	HashMap<String,CatCarrera> mapaCarrera 	= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarrera");

	HashMap<String,String> mapaCarreraCarga	= (HashMap<String,String>)request.getAttribute("mapaCarreraCarga");
%>
<div class="container-fluid">
<h2>Alumnos</h2>
<form id="forma" name="forma" action="carga?Accion=1" method="post">
	<div class="d-flex alert alert-info">
		<a class="btn btn-primary" href="planes"><i class="fas fa-arrow-left"></i></a>
        &nbsp;&nbsp;
		<b>Período:</b>
        <select onchange='javascript:cambiaPeriodo(this.value);' name="PeriodoId" class="input input-medium">
<%		for(int j = 0; j < listaPeriodos.size(); j++){
		CatPeriodo periodo = listaPeriodos.get(j);%>
			<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print("Selected");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
<%		}%>
        </select>
        &nbsp;&nbsp;
		<b>Carga:</b>
	    <select onchange='javascript:cambiaCarga();' name="CargaId" style="width:350px;" class="input input-xlarge">
<%		for(int i = 0; i < lisCarga.size(); i++){
		Carga carga = lisCarga.get(i);%>
			<option <%if(cargaId.equals(carga.getCargaId()))out.print("Selected");%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId() %>] <%=carga.getNombreCarga()%></option>
<%		}%>
			
        </select>
        &nbsp;&nbsp;
        <a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>
        <br>
		<button class="btn btn-success" id="loading" type="button" disabled style="display: none;">
			<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
		  	Buscando ...
		</button>
	</div>
</form>	
<table class="table table-bordered">
	<thead class="table-info">
	<tr>
		<th class='center'>#</th>
		<th class='center'><spring:message code="aca.Matricula"/></th>
		<th class='center'><spring:message code="aca.Alumno"/></th>
		<th class='center'>Plan</th>
		<th class='center'>Fac.</th>
		<th class='center'>Primer Insc.</th>
		<th class='center'>Ultima Insc.</th>
		<th class='center'><spring:message code="aca.Grado"/></th>
		<th class='center'><spring:message code="aca.Ciclo"/></th>
		<th class='center'>Cred.Plan</th>
		<th class='center'>Cred.Alum.</th>
		<th class='center'><spring:message code="aca.Avance"/></th>		
	</tr>
	</thead>
<%		
	int cont = 1;
	for(AlumPlan alumPlan : lisAlumnos){
		
		// Buscar el nombre del alumno
		String nombreAlumno = "";
		if (mapAlumnos.containsKey(alumPlan.getCodigoPersonal())) nombreAlumno = mapAlumnos.get(alumPlan.getCodigoPersonal());
		
		// Buscar los creditos aprobados del alumno en el plan de estudios
		String creditosAlumno = "0";
		if (mapCreditos.containsKey(alumPlan.getCodigoPersonal())) creditosAlumno = mapCreditos.get(alumPlan.getCodigoPersonal());
		
		String creditosPlan = "0";
		if(mapCreditosCarga.containsKey(alumPlan.getPlanId())){
			creditosPlan = mapCreditosCarga.get(alumPlan.getPlanId());
		}
		
		double avance = (Double.valueOf(creditosAlumno)*100)/Double.valueOf(creditosPlan);
		
		// Buscar la ultima fecha de inscripción de los alumnos
		String fecha = "";
		if (mapUltimaFecha.containsKey(alumPlan.getCodigoPersonal())) fecha = mapUltimaFecha.get(alumPlan.getCodigoPersonal());
		
		String carreraId = "";
		CatCarrera carrera = new CatCarrera();
		if(mapaCarreraCarga.containsKey(alumPlan.getCodigoPersonal())){
			carreraId = mapaCarreraCarga.get(alumPlan.getCodigoPersonal());
			if(mapaCarrera.containsKey(carreraId)){
				carrera = mapaCarrera.get(carreraId);
			}
		}

		CatFacultad facultad = new CatFacultad();
		if(mapaFacultad.containsKey(carrera.getFacultadId())){
			facultad = mapaFacultad.get(carrera.getFacultadId());
		}
%>
	<tr>
		<td width="5%" class="center"><%=cont++%></td>
		<td width="10%" class="center"><%= alumPlan.getCodigoPersonal() %></td>
		<td width="30%" class="left"><%= nombreAlumno %></td>
		<td width="30%" class="left"><%= alumPlan.getPlanId() %></td>
		<td width="30%" class="left"><%= facultad.getNombreCorto() %></td>
		<td width="7%" class="center"><%= alumPlan.getPrimerMatricula() %></td>
		<td width="7%" class="center"><%=fecha%></td>
		<td width="5%" class="center"><%= alumPlan.getGrado() %></td>
		<td width="5%" class="center"><%= alumPlan.getCiclo() %></td>
		<td width="5%" class="right"><%=creditosPlan%></td>
		<td width="5%" class="right"><%=creditosAlumno%></td>
		<td width="5%" class="right"><%=getFormato.format(avance)%></td>	
	</tr>	
<%	// Si no esta en la ultima fila
	}
%>
</table>
</div>