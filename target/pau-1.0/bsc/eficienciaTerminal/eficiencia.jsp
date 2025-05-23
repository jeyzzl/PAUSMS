<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="aca.carga.spring.Carga"%>
<%@ page import="aca.catalogo.spring.CatFacultad"%>
<%@ page import="aca.catalogo.spring.CatCarrera"%>
<%@ page import="aca.plan.spring.MapaPlan"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<head>
<script>
	function refrescar(){
		document.frmEficiencia.submit();
	}
</script>
<%
	DecimalFormat getFormato		= new DecimalFormat("###,##0.00;(###,##0.00)");

	String cargaId 					= (String) request.getAttribute("cargaId");
	List<Carga> lisCargas 			= (List<Carga>) request.getAttribute("lisCargas");
	List<aca.Mapa> lisPlanes 		= (List<aca.Mapa>) request.getAttribute("lisPlanes");
	
	HashMap<String,CatFacultad> mapaFacultades	= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");	
	HashMap<String,MapaPlan> mapaPlanes 		= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
	HashMap<String,Integer> mapaGradPorPlan 	= (HashMap<String,Integer>) request.getAttribute("mapaGradPorPlan");
	HashMap<String,Integer> mapaGradEnTiempo 	= (HashMap<String,Integer>) request.getAttribute("mapaGradEnTiempo");
%>
</head>
<body>
<div class="container-fluid">
	<h2>Reporte Eficiencia Terminal</h2>
	<form name="frmEficiencia" action="eficiencia" method="post">
	<div class="alert alert-info d-flex align-items-center">
		Carga:&nbsp;
		<select id="CargaId" name="CargaId" class="form-select" onchange="javascript:refrescar()" style="width:350px;">
<%	for(Carga carga : lisCargas){%>
			<option value="<%=carga.getCargaId() %>" <%if(carga.getCargaId().equals(cargaId))out.print("selected"); %>><%=carga.getCargaId() %> - <%=carga.getNombreCarga()%></option>					
<%	} %>
		</select>
		&nbsp;&nbsp;
		<a href="detallado?CargaId=<%=cargaId%>">Reporte detallado</a>
	</div>	
	</form>
	<table class="table table-bordered">
	<thead>
	<tr>
		<th>#</th>
		<th>Facultad</th>
		<th>Carrera</th>
		<th>Plan</th>
		<th class="text-end">#Alum.</th>
		<th class="text-end">#Grad.</th>
		<th class="text-end">%Grad.</th>
		<th class="text-end">#En tiempo</th>
		<th class="text-end">%En tiempo</th>
		
	</tr>	
	</thead>
	<tbody>	
<%	
	int row=0;
	for(aca.Mapa map : lisPlanes){
		row++;
		String planId 			= map.getLlave();
		String planNombre		= "-";
		String carrera			= "";
		String facultad			= "";
		String facultadCorto	= "-";
		if (mapaPlanes.containsKey(planId)){
			carrera 			= mapaPlanes.get(planId).getCarreraId();
			planNombre			= mapaPlanes.get(planId).getCarreraSe();
			if (mapaCarreras.containsKey(carrera)){
				facultad 		= mapaCarreras.get(carrera).getFacultadId();
				facultadCorto	= mapaFacultades.get(facultad).getNombreCorto();
			}
		}
		//System.out.println(planId+":"+mapaGradPorPlan.size());
		int numGraduados = 0;
		if (mapaGradPorPlan.containsKey(planId)){
			numGraduados = mapaGradPorPlan.get(planId);
		}
		float PorGrad = numGraduados*100 / Float.parseFloat(map.getValor());
				
		int numEnTiempo = 0;
		if (mapaGradEnTiempo.containsKey(planId)){
			numEnTiempo = mapaGradEnTiempo.get(planId);
		}
		float PorEnTiempo = numEnTiempo*100 / Float.parseFloat(map.getValor());
%>	
	<tr>
		<td><%=row%></td>
		<td><%=facultadCorto%></td>
		<td><%=planNombre%></td>
		<td><%=planId%></td>
		<td class="text-end"><%=map.getValor()%></td>
		<td class="text-end"><%=numGraduados%></td>
		<td class="text-end"><%=getFormato.format(PorGrad)%></td>
		<td class="text-end"><%=numEnTiempo%></td>
		<td class="text-end"><%=getFormato.format(PorEnTiempo)%></td>
	</tr>
<% 	}%>
	</tbody>
	</table>
</div>
</body>