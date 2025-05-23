<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>
<%@page import="aca.vista.spring.CargaAcademica"%>
 
<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script>
	function grabaPeriodo(periodoId){
		document.location.href="maestros?cambioPeriodo=1&PeriodoId="+periodoId;
	}
</script>

<%
	DecimalFormat formateador = new DecimalFormat("###0.##"); 
	
	String cargaId 				= request.getParameter("CargaId")==null?session.getAttribute("cargaId").toString():request.getParameter("CargaId");	
	String codigoPersonal		= request.getParameter("CodigoPersonal")==null?(String) session.getAttribute("codigoEmpleado"):request.getParameter("CodigoPersonal");
	
	String maestro 				= (String)request.getAttribute("maestroNombre");
	
	List<CargaAcademica> lisMaterias				= (List<CargaAcademica>)request.getAttribute("lisMaterias");
	
	HashMap<String, String> mapEvaluaciones			= (HashMap<String, String>)	request.getAttribute("mapEvaluaciones");
	HashMap<String, String> mapActividades			= (HashMap<String, String>)	request.getAttribute("mapActividades");
	HashMap<String,CatModalidad> mapModalidades		= (HashMap<String,CatModalidad>) request.getAttribute("mapModalidades");
	HashMap<String,String> mapEsquemaEvaluacion		= (HashMap<String, String>)	request.getAttribute("mapEsquemaEvaluacion");		
	// Mapa para actovidad agendada
	HashMap<String,String> mapActividadAgendada		= (HashMap<String, String>)	request.getAttribute("mapActividadAgendada");
	// Mapa para actovidades evaluadas
	HashMap<String,String> mapActividadEvaluada		= (HashMap<String, String>)	request.getAttribute("mapActividadEvaluada");
	HashMap<String,String> mapaAvanceMaestro		= (HashMap<String, String>)	request.getAttribute("mapaAvanceMaestro");	
	HashMap<String,String> mapaEvalPendientes		= (HashMap<String, String>)	request.getAttribute("mapaEvalPendientes");
	HashMap<String,String> mapaActPendientes		= (HashMap<String, String>)	request.getAttribute("mapaActPendientes");	
	HashMap<String,String> mapaAlumnos				= (HashMap<String, String>)	request.getAttribute("mapaAlumnos");
%>
<div class="container-fluid">
	<h2>Maestro <small class="text-muted fs-4">(<%=codigoPersonal%> | <%= maestro %>)</small></h2>
	<div class="alert alert-info">
		<a href="maestros" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<br>
	<table class="table table-sm table-bordered">
	<thead class="table-info">
	<tr>
		<th width="5%">#</th>
		<th width="5%"><spring:message code="aca.Carrera"/></th>
		<th width="5%"><spring:message code="aca.Acta"/></th>
		<th width="5%"><spring:message code="aca.Plan"/></th>
		<th width="20%"><spring:message code="aca.Materia"/></th>
		<th width="10%">Modalidad</th>
		<th width="5%" class="text-right">#Alum.</th>
		<th width="5%" class="text-right"><spring:message code="aca.Eval"/></th>
		<th width="5%" class="text-right">Tot.Eval.</th>
		<th width="5%" class="text-right"><spring:message code="aca.Activ"/></th>
		<th width="5%" class="text-right">Tot.Act.</th>
		<th width="5%" class="text-right">% Esquema</th>
		<th width="5%" class="text-right">Agendadas</th>
		<th width="5%"class="text-right">Evaluadas</th>
		<th width="5%"class="text-right">Faltantes</th>
		<th width="10%"><spring:message code="aca.Estado"/></th>
		<th width="5%" class="text-right">Avance</th>
		<th width="5%">Ev.Pen.</th>
		<th width="5%">Act.Pen.</th>
	</tr>
	</thead>	
<% 		
	double conEsquema 	= 0;
	double conAgenda  	= 0;
	int row 			= 0;
	int sumaEval		= 0;
	int sumaAct			= 0;

	for(CargaAcademica academica : lisMaterias){		
		row++;
		
		String alumnos = "0"; 
		if(mapaAlumnos.containsKey(  academica.getCursoCargaId() )){
			alumnos = mapaAlumnos.get( academica.getCursoCargaId() );
		}
		
		String evaluaciones = "0"; 
		if(mapEvaluaciones.containsKey(  academica.getCursoCargaId() )){
			evaluaciones = mapEvaluaciones.get( academica.getCursoCargaId() );
		}
		
		String actividades = "0"; 
		if(mapActividades.containsKey( academica.getCursoCargaId() )){
			actividades = mapActividades.get( academica.getCursoCargaId() );
		}
		
		int totEval = Integer.parseInt(alumnos)*Integer.parseInt(evaluaciones);
		sumaEval 	+= totEval;
		
		int totAct 	= Integer.parseInt(alumnos)*Integer.parseInt(actividades);
		sumaAct 	+= totAct;
		
		String estado = "-";
		if (academica.getEstado().equals("1") || academica.getEstado().equals("2")) estado = "Evaluación";
		if (academica.getEstado().equals("3")) estado = "Extra";
		if (academica.getEstado().equals("4")) estado = "Cerrada";
		if (academica.getEstado().equals("5")) estado = "Registrada";
		
		String modalidad = "-";
		if(mapModalidades.containsKey(academica.getModalidadId())){
			modalidad = mapModalidades.get(academica.getModalidadId()).getNombreModalidad();
		}			
		
		String porcentaje = "0";			
		if(mapEsquemaEvaluacion.containsKey(academica.getCursoCargaId())){
			porcentaje = mapEsquemaEvaluacion.get(academica.getCursoCargaId());
			if(Integer.parseInt(porcentaje) >= 100){
				conEsquema ++;	
			}
		}			
		
		String agendadas = "0";
		if(mapActividadAgendada.containsKey(academica.getCursoCargaId())){
			agendadas = mapActividadAgendada.get(academica.getCursoCargaId());
			if(!porcentaje.equals("0")){
				conAgenda ++;	
			}
		}			
		
		String evaluadas = "0";			
		if(mapActividadEvaluada.containsKey(academica.getCursoCargaId())){
			evaluadas = mapActividadEvaluada.get(academica.getCursoCargaId());
		}

		String porcentajeEval = "0";
		if(mapaAvanceMaestro.containsKey(academica.getCursoCargaId())){
			porcentajeEval = mapaAvanceMaestro.get(academica.getCursoCargaId());
		}
		
		int faltantes = Integer.parseInt(agendadas)-Integer.parseInt(evaluadas);
		
		if(faltantes < 0){
			faltantes = 0;
		}
		
		String colorPorcentaje 	= "";
		
		if(Float.parseFloat(porcentajeEval) >= 80) {
			colorPorcentaje = "style='background-color:#9FF781';";
		}else if(Float.parseFloat(porcentajeEval) <= 50) {
			colorPorcentaje = "style='background-color:#F78181';";
		}else {
			colorPorcentaje = "style='background-color:#F4FA58';";			
		}
		
		String evalPendientes 		= "0";
		String colorEvaluaciones	= "";
		if (mapaEvalPendientes.containsKey(academica.getCursoCargaId())){
			evalPendientes 		= mapaEvalPendientes.get(academica.getCursoCargaId());
			if (!evalPendientes.equals("0")) colorEvaluaciones = "bg-warning"; 
		}
		
		String actPendientes 		= "0";
		String colorActividades 	= "";
		if (mapaActPendientes.containsKey(academica.getCursoCargaId())){
			actPendientes 			= mapaActPendientes.get(academica.getCursoCargaId());
			if (!actPendientes.equals("0")) colorActividades = "bg-warning";
		}
		
		
%>
		<tr>
			<td><%=row%></td>
			<td><%=academica.getCarreraId() %></td>
			<td><%=academica.getCursoCargaId() %></td>
			<td><%=academica.getPlanId()%></td>
			<td><%=academica.getNombreCurso() %></td>
			<td><%=modalidad %></td>
			<td class="text-right"><%=alumnos%></td>
			<td class="text-right"><%=evaluaciones%></td>
			<td class="text-right"><%=totEval%></td>
			<td class="text-right"><%=actividades%></td>
			<td class="text-right"><%=totAct%></td>
			<td class="text-right"><a target="_blank" href="avanceEvaluacion?CursoCargaId=<%=academica.getCursoCargaId()%>" class="badge bg-info"><%= porcentaje %></a></td>			
			<td class="text-right"><%= agendadas %></td>
			<td class="text-right"><%= evaluadas %></td>
			<td class="text-right"><%= faltantes %></td>	
			<td width="10%"><%=estado%></td>
			<td width="10%" class="text-right" <%=colorPorcentaje%>><%=porcentajeEval%></td>
			<td class="text-right">  			
  				<span class="badge <%=colorEvaluaciones%>"><%=evalPendientes%></span>
	  		</td>
	  		<td class="text-right">  			
	  			<span class="badge <%=colorActividades%>"><%=actPendientes%></span>
	  		</td> 
		</tr>
<%
	}
		
	conEsquema = formateador.parse(formateador.format((conEsquema*100)/row)).doubleValue();
	conAgenda = formateador.parse(formateador.format((conAgenda*100)/row)).doubleValue();
	String texto		 	= "#000000";
	
	String colorEsquema 	= "";
	
	if(conEsquema >= 80) {
		colorEsquema = "style='background-color:#9FF781';";
	}else if(conEsquema <= 50) {
		colorEsquema = "style='background-color:#F78181';";
	}else {
		colorEsquema = "style='background-color:#F4FA58';";			
	}
	
	String colorAgendadas 	= "";

	if(conAgenda >= 80) {
		colorAgendadas = "style='background-color:#9FF781';";
	}else if(conAgenda <= 50) {
		colorAgendadas = "style='background-color:#F78181';";
	}else {
		colorAgendadas = "style='background-color:#F4FA58';";			
	}		
%>		
		<tr>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>				
			<th>&nbsp;</th>				
			<th>&nbsp;</th>
			<th>&nbsp;</th>
			<th class="text-right"><%=sumaEval%></th>
			<th>&nbsp;</th>
			<th class="text-right"><%=sumaAct%></th>
			<th class="text-right"><span class="label" <%=colorEsquema%>><font color="<%= texto %>">% <%= conEsquema %></font></span></th>				
			<th class="text-right"><span class="label" <%=colorAgendadas%>><font color="<%= texto %>">% <%= conAgenda %></font></span></th>
			<th>&nbsp;</th>				
			<th>&nbsp;</th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
		</tr>
	</table>
</div>