<%@ page import="java.text.*" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.carga.spring.CargaGrupo"%>
<%@ page import="aca.carga.spring.CargaGrupoActividad"%>
<%@ page import="aca.carga.spring.CargaGrupoEvaluacion"%>
<%@ page import="aca.vista.spring.CargaAcademica"%>
<%@ page import="aca.kardex.spring.KrdxAlumnoEval"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<script type="text/javascript">	
	
	function eliminar(e){
		document.frmevaluacion.EvaluacionId.value = e;		
		Borrar();
	}
	
	function Borrar( ){
		if(document.frmevaluacion.EvaluacionId.value!=""){
			if(confirm("Do you want to delete the assessment?")==true){
	  			document.frmevaluacion.Accion.value = "4";
				document.frmevaluacion.submit();
			}			
		}
	}
	
	function BorrarActividad(cursoCargaId, actividadId){
			if(confirm("Do you want to delete the activity?")==true){
	  			document.location.href="borrarActividad?CursoCargaId="+cursoCargaId+"&ActividadId="+actividadId;
			}			
	}
	
	function Modificar(a,b,c,d,e){
		document.location.href='actividad.jsp?ActividadId='+a+'&accion=1&CursoCargaId='+b+'&idEvaluacion='+c+'&Maestro='+d+'&Materia='+e
	}
	
	function Consultar( Estrategia ){
		if (parseInt(Estrategia) > 0){			
			document.frmevaluacion.EvaluacionId.value=Estrategia;
		}	
		document.frmevaluacion.Accion.value="5";				
		document.frmevaluacion.submit();
	}
	
	function copiarEstrategias(){
		if (document.frmevaluacion.externo.value == ""){
			if (document.frmevaluacion.cc.value!=""){			
				document.frmevaluacion.Accion.value="6";
				document.frmevaluacion.submit();
			}
		}else{
			document.frmevaluacion.Accion.value="7";
			document.frmevaluacion.submit();
		}
	}
</script>

<%
	DecimalFormat getformato 	= new DecimalFormat("##0.00;(##0.00)");

 	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");
 	
	String cursoCargaId			= (String)request.getAttribute("cursoCargaId");
	String cargaId				= (String)request.getAttribute("cargaId");
	String evaluaCarga			= (String)request.getAttribute("evaluaCarga");
	String mensaje				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	String maestroNombre 		= (String)request.getAttribute("maestroNombre");
	String materiaNombre 		= (String)request.getAttribute("materiaNombre");
	String sIdEvaluacion		= request.getParameter("idEvaluacion");
	String evaluacionE42		= request.getParameter("EvaluacionE42")==null?"0":request.getParameter("EvaluacionE42");
	
	HashMap <String, String> mapNombreEstrategia 		= (HashMap <String, String>) request.getAttribute("mapNombreEstrategia");
	HashMap <String, Double> mapPromedioEstrategia 		= (HashMap <String, Double>) request.getAttribute("mapPromedioEstrategia");
	HashMap <String, KrdxAlumnoEval> mapNotasEval 		= (HashMap <String, KrdxAlumnoEval>) request.getAttribute("mapNotasEval");
	HashMap <String, String> mapaActividadesEvaluadas	= (HashMap <String, String>) request.getAttribute("mapaActividadesEvaluadas");
	HashMap <String, String> mapaArchivosActividad		= (HashMap <String, String>) request.getAttribute("mapaArchivosActividad");
	
	CargaGrupo cargaGrupo 		= (CargaGrupo)request.getAttribute("cargaGrupo");
	
	double	   nSumaValor		= 0;
	
	// Variables de estudio
	String promedio 			= "";
	int nEst=0,nAvance=0,nEval	= 0;
	double promGrupo=0, sumaEst	= 0;	
	
	List<CargaGrupoEvaluacion> lisEvaluacion	= (List<CargaGrupoEvaluacion>) request.getAttribute("lisEvaluacion");
	
	int j=0, i=0;	
	
%>
<div class="container-fluid">
	<h3>Assessment Scheme <small class="text-muted fs-6">( <%=codigoPersonal%> -  <%=maestroNombre%> - <%=materiaNombre%> )</small></h3>
	
	<form action="metodo" method="post" name="frmevaluacion" target="_self">	
	<input type="hidden" name="Accion">
	<input type="hidden" name="EvaluacionId">
	<input type="hidden" name="Maestro" value="<%=maestroNombre%>">
	<input type="hidden" name="Materia" value="<%=materiaNombre%>">
	<input type="hidden" name="CursoCargaId" value="<%=cursoCargaId%>">
	<input type="hidden" name="EvaluacionE42" value="<%=evaluacionE42%>">
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="cursos">Return</a>&nbsp; &nbsp;
		
		<a class="btn btn-primary" data-bs-toggle ="collapse" href="#collapseItems" role="button"><i class="icon-ok icon-white"></i>Copy</a>&nbsp;&nbsp;&nbsp; 
<% if (evaluaCarga.equals("S")){%>
		<a class="btn btn-primary" href="evaluacion?accion=1&CursoCargaId=<%=cursoCargaId%>&Maestro=<%=maestroNombre%>&Materia=<%=materiaNombre%>">
			<i class="fas fa-plus"></i> Assessment
		</a>
<%   }%>			
	</div>
		<div class="collapse m-1" id="collapseItems">
		  <div class="alert alert-info d-flex">
		    <b class= "m-1">Copy from:</b>&nbsp;
			<select name = "cc" class="form-select" style="width:450px">
			<option>[ Subjects Currently ]</option>
<%

			List<CargaAcademica> lisCursos 	= (List<CargaAcademica>) request.getAttribute("lisCursos");
			String estado 		= "";
			for (i=0;i<lisCursos.size();i++){
				CargaAcademica curso = lisCursos.get(i);
				if (!curso.getCursoCargaId().equals(cursoCargaId)){
					if (curso.getEstado().equals("1")) estado = "(Created)";
					else if (curso.getEstado().equals("2")) estado = "(Ordinary)";
					else if (curso.getEstado().equals("3"))estado = "(Extra)";
					else if (curso.getEstado().equals("4"))estado = "(Delivered)";
					else if (curso.getEstado().equals("5"))estado = "(Registered)";
%>	
			<option value="<%=curso.getCursoCargaId()%>" <%if (cursoCargaId.equals(curso.getCursoCargaId())) out.print("selected");%>><%=curso.getCursoCargaId()%>-<%=curso.getNombreCurso()%>-<%=estado%></option>		
<%		}
	}
%>
			</select>
			&nbsp; or the subject:
			<input type="text" class="form-control m-1" style="width:150px; height:30px" name="externo"> (Example: <%=cursoCargaId%>)
			&nbsp;&nbsp;
				
			<a class="btn btn-primary m-1" href="javascript:copiarEstrategias()" role="button"><i class="icon-ok icon-white"></i>Save</a>

		  </div>
		</div>	
	</form>
<%	if (mensaje.length() > 1){%>		
	<div class="alert alert-success"><%= mensaje %></div>
<%	} %>	
	<table style="width:97%"  class="table table-fontsmall table-sm table-bordered">
	<thead class="table-dark">  	
  	<tr>
		<th width="3%"><spring:message code="aca.Numero"/></th>
		<th width="10%">Option</th>
    	<th width="45%">Strategy and Description</th>
		<th width="13%">Submission Date</th>
		<th width="7%" class="left">Submit type</th>
		<th width="7%" class="right">List</th>
		<th width="7%">Evaluation Type</th>
		<th width="5%" class="right">Weight</th>		
		<th width="5%" class="right">Graded</th>
		<th width="5%" class="right">Average</th>
  	</tr>
  	</thead>
<%	
	nEst = lisEvaluacion.size();
	String contArchivo = "";
	for (i=0; i< lisEvaluacion.size(); i++){	
		CargaGrupoEvaluacion cge = lisEvaluacion.get(i);
		
		double valor = Double.valueOf(cge.getValor()).doubleValue();
		nSumaValor = nSumaValor + valor;
		
		String nombreEstrategia = mapNombreEstrategia.get(cge.getEstrategiaId());
		
		contArchivo += cge.getEvaluacionId()+","+cge.getEstrategiaId()+","+cge.getFecha()+","+cge.getNombreEvaluacion()+","+cge.getTipo()+","+cge.getValor();
		
		String tipoEval = "";
		if (cge.getTipo().equals("%")) 
			tipoEval = "Percentage";
		else if (cge.getTipo().equals("P")) 
			tipoEval = "Points";
		else if (cge.getTipo().equals("E"))
			tipoEval = "Extra points";
		
		// Verificar si la materia est? evaluada
		String esEvaluada	= "NO"; 
		boolean tieneNotas  = false;		
		if (mapNotasEval.containsKey(cge.getCursoCargaId()+cge.getEvaluacionId())){	
			tieneNotas = true;
			esEvaluada = "YES"; 
			nEval++;
			promedio = getformato.format(mapPromedioEstrategia.get(cge.getCursoCargaId()+cge.getEvaluacionId()));
			if (cge.getTipo().equals("%")){
				promGrupo 	+= ((Double.valueOf(promedio).doubleValue()*Double.valueOf(cge.getValor()).doubleValue())/100);
			}else{
				promGrupo	+= Double.valueOf(promedio).doubleValue();
			}
			sumaEst += Double.valueOf(cge.getValor()).doubleValue();
		}else{
			promedio = "0";			
		}
		if (nEst==nEval && nEst>0) nAvance = 100; else if(nEst>0) nAvance = (nEval*100)/nEst; else nAvance = 0;
		
		String enviar = "<span class='badge bg-secondary rounded-pill'>N</span>";		
		if (cge.getEnviar().equals("A")) 
			enviar = "<span class='badge bg-success rounded-pill'>F</span>"; 
		else if (cge.getEnviar().equals("R"))			 
			enviar = "<span class='badge bg-info rounded-pill'>C</span>";
			
		
%>  
  	<tr class="table-secondary">    
		<td align="center"><strong><%=i+1%></strong></td>
		<td align="center">
<% 		if (!tieneNotas){%>
			<a href="javascript:eliminar('<%=cge.getEvaluacionId()%>')" class="btn btn-danger btn-sm rounded-pill"><i class="fas fa-trash-alt icon-white"></i></a>
<% 		}%>			
			&nbsp;
			<a href="evaluacion?CursoCargaId=<%=cursoCargaId%>&EvaluacionId=<%=cge.getEvaluacionId()%>" class="btn btn-info btn-sm rounded-pill">
				<i class="fas fa-pencil-alt"></i>
			</a>
			&nbsp;			
			<a href="actividad?CursoCargaId=<%=cursoCargaId%>&EvaluacionId=<%=cge.getEvaluacionId()%>" class="btn btn-primary btn-sm rounded-pill">
				<i class="fas fa-plus"></i></i>
			</a>			
		</td>		
    	<td>
        	<h5> <span class="badge bg-dark rounded-pill"><%=cge.getEvaluacionId()%></span> <%=mapNombreEstrategia.get(cge.getEstrategiaId().trim())%> - <%=cge.getNombreEvaluacion()%></h5>	
    	</td>    
		<td><h5><%=cge.getFecha()%></h5></td>	
		<td class="left"><h5><%=enviar%></h5></td>
		<td>
<%	if(cge.getEnviar().equals("A") || cge.getEnviar().equals("R")){%>
			<a href="archivoEval?CursoCargaId=<%=cge.getCursoCargaId()%>&EvaluacionId=<%=cge.getEvaluacionId()%>&Opcion=1" class="btn btn-dark btn-sm rounded-pill"><i class="fas fa-file"></i></a>
<%	}else{%>
		&nbsp;
<% 	} %>
		</td>
		<td><h5><%=tipoEval%></h5></td>
		<td style="text-align:right"><h5><%=getformato.format(valor)%></h5></td>		
		<td style="text-align:right"><h5><%=esEvaluada%></h5></td>
		<td style="text-align:right"><h5><%=promedio%></h5></td>
  	</tr>  
<%
		List<CargaGrupoActividad> lisActividad = (List<CargaGrupoActividad>) request.getAttribute("lisActividad");		
		if(lisActividad.size() != 0){
			CargaGrupoActividad actividad = new CargaGrupoActividad();
			for(int k = 0; k < lisActividad.size(); k++){
				if(lisActividad.get(k).getEvaluacionId().equals(cge.getEvaluacionId())){
					actividad = lisActividad.get(k);
					
					String textoEnviar = "<span class='badge bg-secondary rounded-pill'>N</span>";
					if (actividad.getEnviar().equals("A")) textoEnviar = "<span class='badge bg-success rounded-pill'>F</span>";
					if (actividad.getEnviar().equals("R")) textoEnviar = "<span class='badge bg-info rounded-pill'>C</span>";
 					String numActividades = "0";
					if (mapaActividadesEvaluadas.containsKey(actividad.getActividadId())){
						numActividades = mapaActividadesEvaluadas.get(actividad.getActividadId());
					}	
					String totEnviadas = "0";
					if (mapaArchivosActividad.containsKey(actividad.getActividadId()+"E")){
						totEnviadas = mapaArchivosActividad.get(actividad.getActividadId()+"E");
					}
%>
	<tr>
		<td align='center'>	- </td>
		<td align='center'>			
			<a href="actividad?ActividadId=<%=actividad.getActividadId()%>&CursoCargaId=<%=cursoCargaId%>&EvaluacionId=<%=cge.getEvaluacionId()%>">
				<i class="fas fa-pencil-alt"></i>				
			</a>
<% 			if (numActividades.equals("0")){%>	
			&nbsp;
			<a href="javascript:BorrarActividad('<%=cursoCargaId%>','<%=actividad.getActividadId()%>')">
				<i class="fas fa-trash" ></i>				
			</a>
<% 			}%>
		</td>	
		<td>	
			<em><%=actividad.getNombre()%></em>
		</td>
		<td><em><%=actividad.getFecha()%></em></td>
		<td style="text-align:left"><%=textoEnviar%></td>
		<td>
<%	if(actividad.getEnviar().equals("A") || actividad.getEnviar().equals("R")){%>
			<a title="Delivered List" class="btn btn-sm <%=totEnviadas.equals("0")?"btn-secondary":"btn-info"%> rounded-pill" href="alumnoArch?ActividadId=<%= actividad.getActividadId() %>&CursoCargaId=<%=cursoCargaId%>"><i class="fas fa-file-import"></i></a>
<%	}else{%>
			&nbsp;
<%	} %>			
		</td>
		<td><em><spring:message code="portal.maestro.metodo.Porcentaje"/></em></td>
		<td style="text-align:right"><em><%=actividad.getValor()%></em></td>		
		<td style="text-align:right"><em><%=numActividades%></em></td>
		<td style="text-align:right"><em>&nbsp;</em></td>
	</tr>
<%
				}
			}
		}	
	}
	promGrupo = (promGrupo*100)/sumaEst;
%>
	<tr class="table-dark">
    	<td colspan="6" class="text-left"><b> The subject has a total of <%=nEst%> strategies.</b></td>
		<td style="text-align:right">&nbsp;</td>		
		<td style="text-align:right"><b><%=getformato.format(nSumaValor)%></b></td>
		<td style="text-align:right"><%=nAvance%>%</td>
		<td style="text-align:right"><%=getformato.format(promGrupo)%></td>
  	</tr>
	</table>
</div>
<% 
	lisEvaluacion	= null;	
%>
<!-- fin de estructura -->