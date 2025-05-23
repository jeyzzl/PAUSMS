<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.saii.spring.SaiiPeriodo"%>
<%@ page import= "aca.plan.spring.MapaPlan"%>
<%@page import="aca.acceso.spring.Acceso"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<style>
	.lds-dual-ring {
	  display: inline-block;
	  position: fixed;
	  top: 50%;
	  left: 50%;
	}

	.lds-dual-ring p {
	  transform: translateX(-40%);
	  margin-bottom: 2em;
	}
	
	.lds-dual-ring:after {
	  content: "";
	  display: block;
	  width: 46px;
	  height: 46px;
	  margin: 1px;
	  border-radius: 50%;
	  border: 5px solid #fff;
	  border-color: black transparent black transparent;
	  animation: lds-dual-ring 1.2s linear infinite;
	}

	@keyframes lds-dual-ring {
		0% {
	    	transform: rotate(0deg);
		}
		100% {
	    	transform: rotate(360deg);
	  	}
	}
</style>

<%	
	String periodoId	= (String) request.getAttribute("periodoId");
	String fecha	 	= (String) request.getAttribute("fecha");
	
	Acceso acceso		= (Acceso) request.getAttribute("acceso");
	
	// Lista de periodos
	List<SaiiPeriodo> lisPeriodos 	= (List<SaiiPeriodo>) request.getAttribute("lisPeriodos");
	// Lista de planes
	List<String> lisPlanes 			= (List<String>) request.getAttribute("lisPlanes");	
	
	// Map de los planes
	HashMap<String,MapaPlan> mapaPlanes = (HashMap<String, MapaPlan>) request.getAttribute("mapaPlanes");
	// Map de los alumnos
	HashMap<String,String> mapaAlumnos 	= (HashMap<String, String>) request.getAttribute("mapaAlumnos");	
	HashMap<String,String> mapaGrupos 	= (HashMap<String, String>) request.getAttribute("mapaGrupos");
%>
<body>
<div class="container-fluid">
<!-- <div class="lds-dual-ring hidden"> -->
<!-- 	<p>Por favor espera, las consultas pueden tomar más de un 1 minuto</p> -->
<!-- </div> -->
	<h1>Estadística SAII</h1>
	<form name="forma" action="reporte" method="post">
	<div class="alert alert-info">  	
		<strong>Periodo: </strong>
    	<select id="PeriodoId" name="PeriodoId" onchange="this.form.submit()" class="input input-xlarge">
<%
	for(SaiiPeriodo periodo : lisPeriodos){		
%>	
			<option value="<%=periodo.getPeriodoId()%>" <%if(periodo.getPeriodoId().equals(periodoId)) out.print("selected"); %>>
				<%=periodo.getPeriodoId() %> &nbsp;<%=periodo.getPeriodoNombre()%>
			</option>
<%	} %>
		</select>
		<a href="descargardatosextra/<%=fecha%>" class="mx-5 btn btn-primary">Descargar</a>
<!--     	<a href="avisar" class="mx-5 btn btn-primary">Avisar</a> -->
		<a href="asignar?PeriodoId=<%=periodoId%>" class="mx-5 btn btn-success">Asignar Grupo</a>
	</div>
	</form>	
	<table class="table table-bordered">
	<thead class="table-info">
	<tr>
		<th>#</th>
		<th>Grupo</th>
		<th>Plan</th>
		<th>Nombre del Plan</th>
		<th class="right">Alumnos</th>
	</tr>
	</thead>
<%
	int row = 0;
	int cont = 0;
	boolean puedeVer = false;
	for (String plan : lisPlanes){
		row++;
		
		String nombrePlan = "-";
		if (mapaPlanes.containsKey(plan)){
			nombrePlan = mapaPlanes.get(plan).getNombrePlan();
			
			if(acceso.getAccesos().contains(mapaPlanes.get(plan).getCarreraId()) || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") ){
				puedeVer = true;
			}
			
		}		
		
		String noAlumnos = "-";
		if (mapaAlumnos.containsKey(plan)){
			noAlumnos = mapaAlumnos.get(plan);
			cont+=Integer.parseInt(noAlumnos);
		}
		
		String grupo = "<span class='badge bg-warning rounded-pill'>0</span>";
		String grupoId = "";
		if (mapaGrupos.containsKey(plan)){
			grupo = "<span class='badge bg-dark rounded-pill'>"+mapaGrupos.get(plan)+"</span>";
			grupoId  = mapaGrupos.get(plan);
		}
		
		if(puedeVer){
%>
	<tr>
		<td><%=row%></td>
		<td><a href="editarGrupo?PlanId=<%=plan%>&PeriodoId=<%=periodoId%>" class="loader"><%=grupo%></a></td>
		<td><a href="plan?PlanId=<%=plan%>&Fecha=<%=fecha%>&PeriodoId=<%=periodoId%>&GrupoId=<%=grupoId%>" class="loader"><%=plan%></a></td>
		<td><a href="plan?PlanId=<%=plan%>&Fecha=<%=fecha%>&PeriodoId=<%=periodoId%>&GrupoId=<%=grupoId%>" class="loader"><%=nombrePlan%></a></td>
		<td class="right" >			
			<a href="alumnos?GrupoId=<%=grupoId%>&PeriodoId=<%=periodoId%>&Fecha=<%=fecha%>&PlanId=<%=plan%>" class="loader"><span class="badge bg-dark rounded-pill"><%=noAlumnos%></span></a>
		</td>
	</tr>
<%
			puedeVer = false;
		}
	}
%>	
	<tr>
		<th class="right" colspan="4">TOTAL</th>		
		<th class="right"><%=cont%></th>
	</tr>	
	</table>	
</div>	
</body>

<script>
// window.addEventListener("load", function(){
// 	var load = document.querySelectorAll(".loader")
// 	for (var i = 0; i < load.length; i++) {
// 	    load[i].addEventListener('click', loader, false);
// 	}

// 	function loader (){
// 		document.querySelector(".lds-dual-ring").classList.remove("hidden");
// 	}
// });

</script>