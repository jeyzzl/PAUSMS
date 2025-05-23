<%@page import="aca.catalogo.CatFacultadUtil"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.HashMap"%>

<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
 
<jsp:useBean id="CatFacultadUtil" scope="page" class="aca.catalogo.CatFacultadUtil"/>
<jsp:useBean id="CargaGrupoU" scope="page" class="aca.carga.CargaGrupoUtil"/>
<jsp:useBean id="MateriasU" scope="page" class="aca.vista.CargaAcaUtil"/>
<jsp:useBean id="ModalidadU" scope="page" class="aca.catalogo.ModalidadUtil"/>
<jsp:useBean id="EvaluacionU" scope="page" class="aca.carga.CargaGrupoEvaluacionUtil"/>
<jsp:useBean id="CargaGrupoActividadU" scope="page" class="aca.carga.CargaGrupoActividadUtil"/>
<jsp:useBean id="CargaGrupoEvaluacionU" scope="page" class="aca.carga.CargaGrupoEvaluacionUtil"/>
<jsp:useBean id="KrdxAlumnoActivU" scope="page" class="aca.kardex.KrdxAlumnoActivUtil"/>
<%
	DecimalFormat formateador = new DecimalFormat("###0.##");  

	String cargaId 		= request.getParameter("CargaId")==null?session.getAttribute("cargaId").toString():request.getParameter("CargaId");
	String facultadId 	= request.getParameter("FacultadId")==null?"101":request.getParameter("FacultadId");
	
	// Lista de maestros 
	ArrayList <String> lisMaestros								= aca.carga.CargaGrupoUtil.lisMaestrosCargayFac(conEnoc, cargaId, facultadId, " ORDER BY USUARIO_APELLIDO(CODIGO_PERSONAL)");
	
	// Lista de materias 
	ArrayList <aca.vista.CargaAcademica> lisMaterias			= MateriasU.listaCargaFacultad(conEnoc, cargaId, facultadId, " ORDER BY CODIGO_PERSONAL");
	
	// HashMap de los empleados	
	HashMap<String, String> mapMaestros							= aca.vista.MaestrosUtil.mapMaestroNombre(conEnoc, "APELLIDO");
	
	// Mapa para las evaluaciones por carga
	HashMap<String, String> mapEvaluaciones						= CargaGrupoU.mapEvaluacionesMateria(conEnoc, cargaId);
		
	// Mapa para las actividades por carga
	HashMap<String, String> mapActividades						= CargaGrupoU.mapActividadesMateria(conEnoc, cargaId);	
	
	// Mapa para las modalidades
	HashMap<String, aca.catalogo.CatModalidad> mapModalidades	= aca.catalogo.ModalidadUtil.getMapAll(conEnoc, "");
	
	// Mapa para evalucion de esquema
	HashMap<String, String> mapEsquemaEvaluacion				= aca.carga.CargaGrupoEvaluacionUtil.mapSumaEsquema(conEnoc, cargaId);

	HashMap<String, String> mapaAvanceCargayFacultad			= EvaluacionU.mapaAvanceCargayFacultad(conEnoc, cargaId, facultadId);
	
	// Mapa para actovidad agendada
	HashMap<String, String> mapActividadAgendada				= CargaGrupoActividadU.mapActividadesAgendadas(conEnoc, cargaId, "S");
	
	// Mapa para actovidad agendada
	HashMap<String, String> mapAgendadasPorMeses				= CargaGrupoActividadU.mapAgendadasPorMeses(conEnoc, cargaId, "S");

	// Mapa para actovidades evaluadas
	HashMap<String, String> mapActividadEvaluada				= KrdxAlumnoActivU.mapaNumActividadesEvaluadas(conEnoc, cargaId);	
	
	// Mapa para las modalidades
	HashMap<String, aca.catalogo.CatFacultad> mapFacultad		= CatFacultadUtil.getMapFacultad(conEnoc, "");
	
	HashMap<String,String> mapaEvalPendientes	 				= CargaGrupoEvaluacionU.mapaEvalPendientesPorCarga(conEnoc,cargaId,facultadId);
	HashMap<String,String> mapaActPendientes	 				= CargaGrupoEvaluacionU.mapaActPendientesPorCarga(conEnoc,cargaId,facultadId);
	
	String facultad = "";
	
	if(mapFacultad.containsKey(facultadId)){
		facultad = mapFacultad.get(facultadId).getNombreFacultad();
	}
%>
<div class="container-fluid">
	<h1><spring:message code="aca.EstadisticaDeEvaluaciones"/> <small class="text-muted fs-4">(<%=cargaId%> - <%=facultad%>)</small></h1>
	<div class="alert alert-info">
		<a href="carga?CargaId=<%= cargaId %>" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i> <spring:message code="aca.Menu"/></a>
	</div>
	<br>
	<table class="table table-sm">
	<thead>		
<%
	int cont = 0;
int contM01=0,contM02=0,contM03=0,contM04=0,contM05=0,contM06=0,contM07=0,contM08=0,contM09=0,contM10=0,contM11=0,contM12=0;
	for(String codigoMaestro : lisMaestros){
		
		// Busca el nombre del amestro
		String nombreMaestro = "";
		if ( mapMaestros.containsKey( codigoMaestro ) ){
			nombreMaestro = mapMaestros.get( codigoMaestro );
		}
%>		
		<tr class="alert alert-success">
			<th colspan="2">
				<i class="fas fa-user" title="<%=codigoMaestro%>"></i>&nbsp;<%=nombreMaestro%>
			</th>
			<th>Mod.</th>
			<th>Blo.</th>
			<th>Gpo.</th>
			<th><spring:message code="aca.Plan"/></th>
			<th class="text-right">Cre.</th>			
			<th class="text-right">Evales.</th>
			<th class="text-right">Act.</th>
			<th class="text-right">%Esq.</th>
			<th class="text-right">Age.</th>
			<th class="text-right">1</th>
			<th class="text-right">2</th>
			<th class="text-right">3</th>
			<th class="text-right">4</th>
			<th class="text-right">5</th>
			<th class="text-right">6</th>
			<th class="text-right">7</th>
			<th class="text-right">8</th>
			<th class="text-right">9</th>
			<th class="text-right">10</th>
			<th class="text-right">11</th>
			<th class="text-right">12</th>			
			<th class="text-right">Eval.</th>
			<th class="text-right">Fal.</th>
			<th>Est.</th>
			<th class="text-right">%</th>
			<th class="text-right">Ev.Pen.</th>
			<th class="text-right">Act.Pen.</th>
		</tr>
	</thead>
	<tbody>	
<%					
		double conEsquema = 0;
		double conAgenda  = 0;
		int divide	   = 0;
				
		for( aca.vista.CargaAcademica materia : lisMaterias ){
			
			// Si la materia le pertenece al maestro
			if (materia.getCodigoPersonal().equals(codigoMaestro) && materia.getOrigen().equals("O")){
				cont++;
				
				String evaluaciones = "0"; 
				if(mapEvaluaciones.containsKey(  materia.getCursoCargaId() )){
					evaluaciones = mapEvaluaciones.get( materia.getCursoCargaId() );
				}
				
				String actividades = "0"; 
				if(mapActividades.containsKey( materia.getCursoCargaId() )){
					actividades = mapActividades.get( materia.getCursoCargaId() );
				}			
				
				String estado = "-";
				if (materia.getEstado().equals("1") || materia.getEstado().equals("2")) estado = "Evaluación";
				if (materia.getEstado().equals("3")) estado = "Extra";
				if (materia.getEstado().equals("4")) estado = "Cerrada";
				if (materia.getEstado().equals("5")) estado = "Registrada";
				
				String modalidad = "-";
				if(mapModalidades.containsKey(materia.getModalidadId())){
					modalidad = mapModalidades.get(materia.getModalidadId()).getNombreModalidad();
				}
				
				String porcentaje = "0";
				if(mapEsquemaEvaluacion.containsKey(materia.getCursoCargaId())){
					porcentaje = mapEsquemaEvaluacion.get(materia.getCursoCargaId());				
					if(Integer.parseInt(porcentaje) >= 100){
						conEsquema ++;	
					}
				}
				
				String agendadas = "0";
				if(mapActividadAgendada.containsKey(materia.getCursoCargaId())){
					agendadas = mapActividadAgendada.get(materia.getCursoCargaId());
					if(!porcentaje.equals("0")){
						conAgenda ++;	
					}
				}

				String procentajeaAvance = "0";
				if(mapaAvanceCargayFacultad.containsKey(materia.getCursoCargaId())){
					procentajeaAvance = mapaAvanceCargayFacultad.get(materia.getCursoCargaId());
				}
				
				String m01="0",m02="0",m03="0",m04="0",m05="0",m06="0",m07="0",m08="0",m09="0",m10="0",m11="0",m12="0";
				if (mapAgendadasPorMeses.containsKey(materia.getCursoCargaId()+"01")) {m01 = mapAgendadasPorMeses.get(materia.getCursoCargaId()+"01"); contM01+=Integer.parseInt(m01);}
				if (mapAgendadasPorMeses.containsKey(materia.getCursoCargaId()+"02")) {m02 = mapAgendadasPorMeses.get(materia.getCursoCargaId()+"02"); contM02+=Integer.parseInt(m02);}
				if (mapAgendadasPorMeses.containsKey(materia.getCursoCargaId()+"03")) {m03 = mapAgendadasPorMeses.get(materia.getCursoCargaId()+"03"); contM03+=Integer.parseInt(m03);}
				if (mapAgendadasPorMeses.containsKey(materia.getCursoCargaId()+"04")) {m04 = mapAgendadasPorMeses.get(materia.getCursoCargaId()+"04"); contM04+=Integer.parseInt(m04);}
				if (mapAgendadasPorMeses.containsKey(materia.getCursoCargaId()+"05")) {m05 = mapAgendadasPorMeses.get(materia.getCursoCargaId()+"05"); contM05+=Integer.parseInt(m05);}
				if (mapAgendadasPorMeses.containsKey(materia.getCursoCargaId()+"06")) {m06 = mapAgendadasPorMeses.get(materia.getCursoCargaId()+"06"); contM06+=Integer.parseInt(m06);}
				if (mapAgendadasPorMeses.containsKey(materia.getCursoCargaId()+"07")) {m07 = mapAgendadasPorMeses.get(materia.getCursoCargaId()+"07"); contM07+=Integer.parseInt(m07);}
				if (mapAgendadasPorMeses.containsKey(materia.getCursoCargaId()+"08")) {m08 = mapAgendadasPorMeses.get(materia.getCursoCargaId()+"08"); contM08+=Integer.parseInt(m08);}
				if (mapAgendadasPorMeses.containsKey(materia.getCursoCargaId()+"09")) {m09 = mapAgendadasPorMeses.get(materia.getCursoCargaId()+"09"); contM09+=Integer.parseInt(m09);}
				if (mapAgendadasPorMeses.containsKey(materia.getCursoCargaId()+"10")) {m10 = mapAgendadasPorMeses.get(materia.getCursoCargaId()+"10"); contM10+=Integer.parseInt(m10);}
				if (mapAgendadasPorMeses.containsKey(materia.getCursoCargaId()+"11")) {m11 = mapAgendadasPorMeses.get(materia.getCursoCargaId()+"11"); contM11+=Integer.parseInt(m11);}
				if (mapAgendadasPorMeses.containsKey(materia.getCursoCargaId()+"12")) {m12 = mapAgendadasPorMeses.get(materia.getCursoCargaId()+"12"); contM12+=Integer.parseInt(m12);}				
				
				String evaluadas = "0";
				if(mapActividadEvaluada.containsKey(materia.getCursoCargaId())){
					evaluadas = mapActividadEvaluada.get(materia.getCursoCargaId());
				}
				
				int faltantes = Integer.parseInt(agendadas)-Integer.parseInt(evaluadas);
				if(faltantes < 0)
					faltantes = 0;
				
				
				String colorPorcentaje 	= "";
				
				if(Float.parseFloat(procentajeaAvance) >= 80) {
					colorPorcentaje = "style='background-color:#9FF781';";
				}else if(Float.parseFloat(procentajeaAvance) <= 50) {
					colorPorcentaje = "style='background-color:#F78181';";
				}else {
					colorPorcentaje = "style='background-color:#F4FA58';";			
				}
				
				String evalPendientes 		= "0";
				String colorEvaluaciones	= "";
				if (mapaEvalPendientes.containsKey(materia.getCursoCargaId())){
					evalPendientes 		= mapaEvalPendientes.get(materia.getCursoCargaId());
					if (!evalPendientes.equals("0")) colorEvaluaciones = "bg-warning"; 
				}
				
				String actPendientes 		= "0";
				String colorActividades 	= "";
				if (mapaActPendientes.containsKey(materia.getCursoCargaId())){
					actPendientes 			= mapaActPendientes.get(materia.getCursoCargaId());
					if (!actPendientes.equals("0")) colorActividades = "bg-warning";
				}
%>
		<tr>
			<td><%= cont %></td>
			<td><%= materia.getNombreCurso() %></td>
			<td><%= modalidad %></td>
			<td><%= materia.getBloqueId()%></td>			
			<td><%= materia.getGrupo()%></td>		
			<td><%= materia.getPlanId() %></td>
			<td class="text-right"><%= materia.getCreditos() %></td>
			<td class="text-right"><%= evaluaciones %></td>
			<td class="text-right"><%= actividades %></td>
			<td class="text-right"><a target="_blank" href="avanceEvaluacion?CursoCargaId=<%=materia.getCursoCargaId()%>" class="badge bg-info"><%= porcentaje %></a></td>
			<td class="text-right"><%= agendadas %></td>
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
			<td class="text-right"><%= evaluadas %></td>
			<td class="text-right"><%= faltantes %></td>				
			<td><%=estado%></td>
			<td class="text-right" <%=colorPorcentaje%>"><%= procentajeaAvance %></td>
			<td class="text-right">  			
  				<span class="badge <%=colorEvaluaciones%>"><%=evalPendientes%></span>
	  		</td>
	  		<td class="text-right">  			
	  			<span class="badge <%=colorActividades%>"><%=actPendientes%></span>
	  		</td>  		
		</tr>		
<%
			divide++;
			}
		}
		
		conEsquema = formateador.parse(formateador.format((conEsquema*100)/divide)).doubleValue();
		conAgenda  = formateador.parse(formateador.format((conAgenda*100)/divide)).doubleValue();
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
			<th colspan="9">&nbsp;</th>			
			<th class="text-right"><span class="label" <%=colorEsquema%>><font color="<%= texto %>">% <%= conEsquema %></font></span></th>
			<th class="text-right"><span class="label" <%=colorAgendadas%>><font color="<%= texto %>">% <%= conAgenda %></font></span></th>
			<th class="text-right"><%=contM01%></th>
			<th class="text-right"><%=contM02%></th>				
			<th class="text-right"><%=contM03%></th>
			<th class="text-right"><%=contM04%></th>
			<th class="text-right"><%=contM05%></th>
			<th class="text-right"><%=contM06%></th>
			<th class="text-right"><%=contM07%></th>
			<th class="text-right"><%=contM08%></th>
			<th class="text-right"><%=contM09%></th>
			<th class="text-right"><%=contM10%></th>
			<th class="text-right"><%=contM11%></th>
			<th class="text-right"><%=contM12%></th>
			<th colspan="6">&nbsp;</th>
		</tr>
<%	contM01=0;contM02=0;contM03=0;contM04=0;contM05=0;contM06=0;contM07=0;contM08=0;contM09=0;contM10=0;contM11=0;contM12=0;}  
 %>		
	</tbody>	
	</table>
</div>
<%@ include file="../../cierra_enoc.jsp"%>