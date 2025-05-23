<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.Carga"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<script>
	function Refrescar(cargaId){
		document.location.href="maestros?CargaId="+cargaId;
	}
</script>

<%
	java.text.DecimalFormat formato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	
	String cargaId 										= (String)session.getAttribute("cargaId");
	int totEval = 0, totReg = 0, totAct = 0, totActReg = 0;
	
	
	List<Carga> lisCargas					= (List<Carga>) request.getAttribute("lisCargas");	
	List<aca.Mapa> lisMaestros				= (List<aca.Mapa>) request.getAttribute("lisMaestros");
	
	// Mapa para las materias por profesor
	HashMap<String, String> mapMaterias					= (HashMap<String,String>) request.getAttribute("mapMaterias");
	
	HashMap<String, String> mapEvaluaciones				= (HashMap<String,String>) request.getAttribute("mapEvaluaciones");
	HashMap<String, String> mapEvalRegistradas			= (HashMap<String,String>) request.getAttribute("mapEvalRegistradas");
	HashMap<String, String> mapEvalTotales				= (HashMap<String,String>) request.getAttribute("mapEvalTotales");
	
	HashMap<String, String> mapActividades				= (HashMap<String,String>) request.getAttribute("mapActividades");	
	HashMap<String, String> mapActivRegistradas			= (HashMap<String,String>) request.getAttribute("mapActivRegistradas");
	HashMap<String, String> mapActivTotales				= (HashMap<String,String>) request.getAttribute("mapActivTotales");	
	HashMap<String, String> mapEsquema					= (HashMap<String,String>) request.getAttribute("mapEsquema");
%>
<div class="container-fluid">
	<h2>Registro de evaluaciones por maestro</h2>
	<div class="alert alert-info d-flex align-items-center">
		<a href="menu" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;
		<strong>Carga</strong>&nbsp;
		<select name="CargaId" class="form-select" onchange="javascript:Refrescar(this.value);" style="width:400px;">
		<%	for(Carga carga : lisCargas){%>
			<option value="<%=carga.getCargaId()%>" <%=cargaId.equals(carga.getCargaId())?"Selected":""%>><%=carga.getCargaId()%> - <%=carga.getNombreCarga()%></option>
		<%	}%>
  	    </select>&nbsp; &nbsp;
		<input type="text" class="form-control search-query" placeholder="Buscar" id="buscar" style="width:200px;">
	</div>	
	<table class="table table-sm table-bordered" id="table">
	<thead>
	<tr>		
		<th colspan ="4">&nbsp;</th>		
		<th colspan ="3" class="text-center">Esquema de evaluación</th>
		<th colspan ="6" class="text-center">Registro de notas</th>	
	</tr>
	<tr class="table-info">
		<th >#</th>
		<th><spring:message code="aca.Clave"/></th>
		<th><spring:message code="aca.Maestro"/></th>
		<th style="text-align:right;"><spring:message code="aca.Materias"/></th>
		<th style="text-align:right;">Esq. 100%</th>
		<th style="text-align:right;"><spring:message code="aca.Evaluaciones"/></th>
		<th style="text-align:right;"><spring:message code="aca.Actividades"/></th>
		<th style="text-align:right;">Tot. Eval.</th>		
		<th style="text-align:right;">Eval. Reg.</th>
		<th style="text-align:right;">Avance %</th>
		<th style="text-align:right;">Tot. Activ.</th>		
		<th style="text-align:right;">Activ. Reg.</th>
		<th style="text-align:right;">Avance %</th>		
	</tr>
	</thead>
<%
	// Buscar datos
	int cont = 1;
	
	for(aca.Mapa maestro : lisMaestros){
		
		String materias = "0";
		if(mapMaterias.containsKey(maestro.getLlave())){
			materias = mapMaterias.get(maestro.getLlave());
		}
		
		String evaluaciones = "0"; 
		if(mapEvaluaciones.containsKey(maestro.getLlave())){
			evaluaciones = mapEvaluaciones.get(maestro.getLlave());
		}
		
		String actividades = "0";
		if(mapActividades.containsKey(maestro.getLlave())){
			actividades = mapActividades.get(maestro.getLlave());
		}
		
		String totEvaluaciones = "0";
		if(mapEvalTotales.containsKey(maestro.getLlave())){
			totEvaluaciones = mapEvalTotales.get(maestro.getLlave());
		}	
		totEval = totEval + Integer.parseInt(totEvaluaciones);
		
		String regEvaluaciones = "0";
		if(mapEvalRegistradas.containsKey(maestro.getLlave())){
			regEvaluaciones = mapEvalRegistradas.get(maestro.getLlave());
		}
		totReg = totReg + Integer.parseInt(regEvaluaciones);
		
		String totActividades = "0";
		if(mapActivTotales.containsKey(maestro.getLlave())){
			totActividades = mapActivTotales.get(maestro.getLlave());
		}	
		totAct = totAct + Integer.parseInt(totActividades);
		
		String regActividades = "0";
		if(mapActivRegistradas.containsKey(maestro.getLlave())){
			regActividades = mapActivRegistradas.get(maestro.getLlave());
		}
		totActReg = totActReg + Integer.parseInt(regActividades);
		
		float avanceEval = 0;
		if (Float.parseFloat(totEvaluaciones) > 0 && Float.parseFloat(regEvaluaciones) > 0){
			avanceEval = Float.parseFloat(regEvaluaciones)/Float.parseFloat(totEvaluaciones) *100;
		}
		
		float avanceActiv = 0;
		if (Float.parseFloat(totActividades) > 0 && Float.parseFloat(regActividades) > 0){
			avanceActiv = Float.parseFloat(regActividades)/Float.parseFloat(totActividades) *100;
		}
		
		String esquema = "0";
		if(mapEsquema.containsKey(maestro.getLlave())){
			esquema = mapEsquema.get(maestro.getLlave());
		}
%>
	<tr>
		<td><%=cont %></td>
		<td><%=maestro.getLlave()%></td>
		<td><a href="detalle?CodigoPersonal=<%=maestro.getLlave()%>"><%=maestro.getValor()%></a></td>
		<td style="text-align:right;"><%=materias%></td>
		<td style="text-align:right;"><%=esquema%></td>
		<td style="text-align:right;"><%=evaluaciones%></td>
		<td style="text-align:right;"><%=actividades%></td>
		<td style="text-align:right;"><%=totEvaluaciones%></td>
		<td style="text-align:right;"><%=regEvaluaciones%></td>
		<td style="text-align:right;"><%=formato.format(avanceEval)%></td>
		<td style="text-align:right;"><%=totActividades%></td>
		<td style="text-align:right;"><%=regActividades%></td>
		<td style="text-align:right;"><%=formato.format(avanceActiv)%></td>
	</tr>	
<%
		cont++;
	} 
	float avanceEval 	= 0;
	if (totEval>0) avanceEval = totReg*100/totEval;
	float avanceAct		= 0;  
	if (totAct>0) avanceAct = totActReg*100/totAct;
%>	
	<tr class="alert alert-info">
		<th colspan ="6"></th>
		<th style="text-align:center;"><h4>Total</h4></th>
		<th style="text-align:right;"><strong><%=formato.format(totEval)%></strong></th>
		<th style="text-align:right;"><strong><%=formato.format(totReg)%></strong></th>
		<th style="text-align:right;"><strong><%=formato.format(avanceEval)%>%</strong></th>	
		<th style="text-align:right;"><strong><%=formato.format(totAct)%></strong></th>
		<th style="text-align:right;"><strong><%=formato.format(totActReg)%></strong></th>	
		<th style="text-align:right;"><strong><%=formato.format(avanceAct)%>%</strong></th>	
	</tr>
	</table>
</div>
<script src="../../js/search.js"></script>
<script>
	$('#buscar').search();
</script>