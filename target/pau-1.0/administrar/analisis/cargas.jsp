<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.carga.spring.Carga"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<script>
	function grabaPeriodo(periodoId){
		document.location.href="cargas?PeriodoId="+periodoId;
	}
</script>

<%	
	String periodoId 			= (String)request.getAttribute("periodoId");
	
	List<CatPeriodo> lisPeriodos				= (List<CatPeriodo>)request.getAttribute("lisPeriodos");
	List<Carga> lisCargas						= (List<Carga>)request.getAttribute("lisCargas");
	HashMap<String,String> mapaMaestros			= (HashMap<String,String>)request.getAttribute("mapaMaestros");	
	HashMap<String,String> mapaMaterias			= (HashMap<String,String>)request.getAttribute("mapaMaterias");	
	HashMap<String,String> mapaEsquemas			= (HashMap<String,String>)request.getAttribute("mapaEsquemas");	
	HashMap<String,String> mapaAgendadas		= (HashMap<String,String>)request.getAttribute("mapaAgendadas");
	HashMap<String,String> mapaMesesPorCargas	= (HashMap<String,String>)request.getAttribute("mapaMesesPorCargas");
%>
<div class="container-fluid">
	<h1>Evaluation Statistics per Load</h1>
	<div class="alert alert-info d-flex align-items-center">			
		<a href="menu" style="text-align:center;" class="btn btn-primary"><i class="far fa-arrow-alt-circle-left fa-lg"></i></a>		
		&nbsp; &nbsp;		
		<strong>Period</strong>
		&nbsp;
		<select name="PeriodoId" class="form-select" onchange="javascript:grabaPeriodo(this.value);" style="width:140px;">
			<%	for(CatPeriodo periodo : lisPeriodos){%>
					<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print(" Selected ");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
			<%	}%>
  	    </select> 	    
	</div>	
	<br>
	<table class="table table-sm table-bordered">
	<thead class="table-info">
	<tr>
		<th>#</th>
		<th><spring:message code="aca.Carga"/></th>
		<th><spring:message code="aca.CargaNombre"/></th>
		<th class="text-right"><spring:message code="aca.Maestros"/></th>
		<th class="text-right"><spring:message code="aca.Materias"/></th>
		<th class="text-right">Schema 100%</th>
		<th class="text-right">%</th>
		<th class="text-right">Missing</th>
		<th class="text-right">Agenda</th>
		<th class="text-right">%</th>
		<th class="text-right">01</th>
		<th class="text-right">02</th>
		<th class="text-right">03</th>
		<th class="text-right">04</th>
		<th class="text-right">05</th>
		<th class="text-right">06</th>
		<th class="text-right">07</th>
		<th class="text-right">08</th>
		<th class="text-right">09</th>
		<th class="text-right">10</th>
		<th class="text-right">11</th>
		<th class="text-right">12</th>				
	</tr>
	</thead>
	<tbody>	
	<%				

	int cont = 1;
	for(Carga cargas : lisCargas){
		
		String materias	= "0";					
		if(mapaMaterias.containsKey(cargas.getCargaId())){
			materias = mapaMaterias.get(cargas.getCargaId());
		}
		
		String maestros = "0";
		if(mapaMaestros.containsKey(cargas.getCargaId())){
			maestros = mapaMaestros.get(cargas.getCargaId());
		}
		
		String esquema = "0";
		if(mapaEsquemas.containsKey(cargas.getCargaId())){
			esquema = mapaEsquemas.get(cargas.getCargaId());
		}					
		int porcentajeEsquema	= 0;
		if (Integer.parseInt(materias)>0){
			porcentajeEsquema	= (Integer.parseInt(esquema)*100) / Integer.parseInt(materias); 
		}				
		
		String agendadas = "0";
		if(mapaAgendadas.containsKey(cargas.getCargaId())){
			agendadas = mapaAgendadas.get(cargas.getCargaId());
		}					
		int porcentajeAgendadas	= 0;
		if (Integer.parseInt(materias)>0){
			porcentajeAgendadas = (Integer.parseInt(agendadas)*100) / Integer.parseInt(materias); 
		}	
		
		String texto		 	= "#000000";
		
		String colorEsquema 	= "";
		
		if(porcentajeEsquema >= 80) {
			colorEsquema = "style='background-color:#9FF781';";
		}else if(porcentajeEsquema <= 50) {
			colorEsquema = "style='background-color:#F78181';";
		}else {
			colorEsquema = "style='background-color:#F4FA58';";			
		}
		
		String colorAgendadas 	= "";

		if(porcentajeAgendadas >= 80) {
			colorAgendadas = "style='background-color:#9FF781';";
		}else if(porcentajeAgendadas <= 50) {
			colorAgendadas = "style='background-color:#F78181';";
		}else {
			colorAgendadas = "style='background-color:#F4FA58';";			
		}
		
		String m01="0",m02="0",m03="0",m04="0",m05="0",m06="0",m07="0",m08="0",m09="0",m10="0",m11="0",m12="0";
		if (mapaMesesPorCargas.containsKey(cargas.getCargaId()+"01")) m01 = mapaMesesPorCargas.get(cargas.getCargaId()+"01");
		if (mapaMesesPorCargas.containsKey(cargas.getCargaId()+"02")) m02 = mapaMesesPorCargas.get(cargas.getCargaId()+"02");
		if (mapaMesesPorCargas.containsKey(cargas.getCargaId()+"03")) m03 = mapaMesesPorCargas.get(cargas.getCargaId()+"03");
		if (mapaMesesPorCargas.containsKey(cargas.getCargaId()+"04")) m04 = mapaMesesPorCargas.get(cargas.getCargaId()+"04");
		if (mapaMesesPorCargas.containsKey(cargas.getCargaId()+"05")) m05 = mapaMesesPorCargas.get(cargas.getCargaId()+"05");
		if (mapaMesesPorCargas.containsKey(cargas.getCargaId()+"06")) m06 = mapaMesesPorCargas.get(cargas.getCargaId()+"06");
		if (mapaMesesPorCargas.containsKey(cargas.getCargaId()+"07")) m07 = mapaMesesPorCargas.get(cargas.getCargaId()+"07");
		if (mapaMesesPorCargas.containsKey(cargas.getCargaId()+"08")) m08 = mapaMesesPorCargas.get(cargas.getCargaId()+"08");
		if (mapaMesesPorCargas.containsKey(cargas.getCargaId()+"09")) m09 = mapaMesesPorCargas.get(cargas.getCargaId()+"09");
		if (mapaMesesPorCargas.containsKey(cargas.getCargaId()+"10")) m10 = mapaMesesPorCargas.get(cargas.getCargaId()+"10");
		if (mapaMesesPorCargas.containsKey(cargas.getCargaId()+"11")) m11 = mapaMesesPorCargas.get(cargas.getCargaId()+"11");
		if (mapaMesesPorCargas.containsKey(cargas.getCargaId()+"12")) m12 = mapaMesesPorCargas.get(cargas.getCargaId()+"12");
		
		String faltan = String.valueOf(Integer.parseInt(materias)-Integer.parseInt(esquema));
	%>
	<tr>
			<td><%=cont %></td>
			<td><a href="carga?CargaId=<%= cargas.getCargaId() %>"><%= cargas.getCargaId()%></a></td>
			<td><a href="carga?CargaId=<%= cargas.getCargaId() %>"><%= cargas.getNombreCarga()%></a></td>
			<td style="text-align:right;"><%= maestros%></td>
			<td style="text-align:right;"><%= materias%></td>
			<td style="text-align:right;"><%= esquema%></td>
			<td style="text-align:right;"><span class="label" <%=colorEsquema%>><font color="<%= texto %>"><%= porcentajeEsquema %></font></span></td>
			<td class="text-right"><a href="falta?CargaId=<%=cargas.getCargaId()%>"><%=faltan %></a></td>
			<td style="text-align:right;"><%= agendadas%></td>
			<td style="text-align:right;"><span class="label" <%=colorAgendadas%>><font color="<%= texto %>"><%= porcentajeAgendadas %></font></span></td>
			<td class="text-right"><%=m01%></td>
			<td class="text-right"><%=m02%></td>
			<td class="text-right"><%=m03%></td>
			<td class="text-right"><%=m04%></td>
			<td class="text-right"><%=m05%></td>
			<td class="text-right"><%=m06%></td>
			<td class="text-right"><%=m07%></td>
			<td class="text-right"><%=m08%></td>
			<td class="text-right"><%=m09%></td>
			<td class="text-right"><%=m10%></td>
			<td class="text-right"><%=m11%></td>
			<td class="text-right"><%=m12%></td>								
	<%
			cont++;
		} 
	%>
	</tr>
	</tbody>
	</table>
</div>