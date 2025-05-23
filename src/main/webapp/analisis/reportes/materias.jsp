<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>

<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.vista.spring.CargaAcademica"%>
<%@ page import= "aca.catalogo.spring.CatModalidad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%
	String cargas				= (String) session.getAttribute("cargas");	
	String cargaId				= (String) request.getAttribute("cargaId");
	String arreglo[]			= cargas.split(",");
	
	List<Carga> lisCargas 					= (List<Carga>) request.getAttribute("lisCargas");
	List<CargaAcademica> lisMaterias		= (List<CargaAcademica>) request.getAttribute("lisMaterias");
	List<aca.Mapa> lisPlanes 				= (List<aca.Mapa>) request.getAttribute("lisPlanes");
	
 	HashMap<String,CatFacultad> mapaFacultades 		= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
 	HashMap<String,CatCarrera> mapaCarreras 		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
 	HashMap<String,CatModalidad> mapaModalidades 	= (HashMap<String,CatModalidad>) request.getAttribute("mapaModalidades");
 	HashMap<String,String> mapaAlumPorMateria 			= (HashMap<String,String>) request.getAttribute("mapaAlumPorMateria");
 	HashMap<String,String> mapaAlumPorPlan 				= (HashMap<String,String>) request.getAttribute("mapaAlumPorPlan");
 	
// 	HashMap<String,String> mapaMaestros 		= (HashMap<String,String>) request.getAttribute("mapaMaestros"); 	
%>	
<head>
	<link rel="stylesheet" href="../../js/chosen/chosen.css"/>
	<script type="text/javascript">
		function AddCarga(){
			document.forma.Accion.value="1";	
			document.forma.submit();
		}
			
		function DelCarga(cargaId){			
			document.location.href="quitar?CargaId="+cargaId;
		}

		function Mostrar(){
			document.forma.Accion.value="2";	
			document.forma.submit();
		}
	</script>
</head>	
<div class="container-fluid">
	<h2>Materias por maestro
	<small class="text-muted fs-5">
	(
<%	for (String carga : arreglo){
		if (!carga.equals("'000000'")){
%>
		&nbsp;<a href="javascript:DelCarga('<%=carga.replace("'","")%>');"><span class="badge bg-dark rounded-pill"><%=carga%></span></a>
<% 		}
	}	
%>
	)
	</small></h2>
	<form name="forma" action="materias" method="post">
	<input type="hidden" name="Accion">
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary btn-sm" href="menu"><i class="fas fa-arrow-left"></i></a>
		&nbsp;&nbsp;
		<b><spring:message code="aca.Carga"/>:&nbsp;</b>
		<select name="CargaId" class="chosen form-select" style="width:350px;">
			<option <%=cargaId.equals("000000")?" Selected":""%> value="000000">[000000] Elige una carga</option>
<%		for(Carga carga : lisCargas){
			if (cargas.contains(carga.getCargaId())==false){
%>
			<option <%=cargaId.equals(carga.getCargaId())?"Selected":""%> value="<%=carga.getCargaId()%>"> <%=carga.getPeriodo()%> - <%=carga.getCargaId()%> - <%=carga.getNombreCarga()%></option>
<%			}
		}
%>
		</select>		
		&nbsp;&nbsp;
		<a class="btn btn-primary btn-sm rounded-pill" href="javascript:AddCarga();"><i class="fas fa-plus"></i></a>
		&nbsp;&nbsp;
		<a class="btn btn-success btn-sm rounded-pill" href="javascript:Mostrar();">Buscar</a>		
	</div>	
	</form>	
	<table class="table table-bordered">
	<thead class="table-info">
	<tr>
		<th>#</th>
		<th>Facultad</th>
		<th>Plan</th>
		<th>Carga</th>
		<th>Maestro</th>		
		<th>Materia</th>	
		<th>Modalidad</th>
		<th class="text-end">#Alum.</th>
		<th class="text-end">Plan Base</th>
		<th class="text-end">Otros</th>
		<th>Alumnos por plan</th>
	</tr>
	</thead>
	<tbody>	
<%
	int row = 0;
	for (CargaAcademica materia : lisMaterias){
		row++;
		
		String facultadId 		= "0";
		String facultadCorto 	= "-";
		if (mapaCarreras.containsKey(materia.getCarreraId())){
			facultadId = mapaCarreras.get(materia.getCarreraId()).getFacultadId();
			if (mapaFacultades.containsKey(facultadId)){
				facultadCorto = mapaFacultades.get(facultadId).getNombreCorto();
			}
		}
		
		String modalidadNombre = "-";
		if (mapaModalidades.containsKey(materia.getModalidadId())){
			modalidadNombre = mapaModalidades.get(materia.getModalidadId()).getNombreModalidad();
		}
		
		String totAlumnos = "0";
		if (mapaAlumPorMateria.containsKey(materia.getCursoCargaId()) ){
			totAlumnos = mapaAlumPorMateria.get(materia.getCursoCargaId());
		}
		
		int planBase 		= 0;
		int otrosPlanes 	= 0;
		String alumnosPorPlan = "";
		
		for (aca.Mapa plan : lisPlanes){
			if (plan.getLlave().equals(materia.getCursoCargaId())){				
				if (plan.getValor().equals(materia.getPlanId())){
					if (mapaAlumPorPlan.containsKey(plan.getLlave()+plan.getValor())){
						planBase += Integer.parseInt(mapaAlumPorPlan.get(plan.getLlave()+plan.getValor()));
						//alumnosPorPlan += " <b>"+plan.getValor()+"</b> <span class='badge bg-dark rounded-pill'>"+mapaAlumPorPlan.get(plan.getLlave()+plan.getValor())+"</span>";
					}					
				}else{
					if (mapaAlumPorPlan.containsKey(plan.getLlave()+plan.getValor())){
						otrosPlanes += Integer.parseInt(mapaAlumPorPlan.get(plan.getLlave()+plan.getValor()));
						if (alumnosPorPlan.length()==0){
							alumnosPorPlan += " "+plan.getValor()+" <span class='badge bg-info rounded-pill'>"+mapaAlumPorPlan.get(plan.getLlave()+plan.getValor())+"</span>";
						}else{
							alumnosPorPlan += ", "+plan.getValor()+" <span class='badge bg-info rounded-pill'>"+mapaAlumPorPlan.get(plan.getLlave()+plan.getValor())+"</span>";
						}	
					}
				}
			}
		}
%>	
	<tr>
		<td><%=row%></tD>
		<td><%=facultadCorto%></td>
		<td><%=materia.getPlanId()%></td>
		<td><%=materia.getCargaId()%></td>		
		<td><%=materia.getNombre()%></td>	
		<td><%=materia.getNombreCurso() %></td>
		<td><%=modalidadNombre%></td>
		<td class="text-end"><%=totAlumnos%></td>	
		<td class="text-end"><%=planBase%></td>
		<td class="text-end"><%=otrosPlanes%></td>
		<td><%=alumnosPorPlan%></td>
	</tr>
<%	}%>
	</tbody>
	</table>
</div>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script>	
	jQuery(".chosen").chosen();	
</script>