<%@ page import="java.text.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ page import= "aca.catalogo.spring.CatEstrategia"%>
<%@ page import= "aca.carga.spring.CargaGrupoEvaluacion"%>
<%@ page import= "aca.carga.spring.CargaGrupoActividad"%>

<jsp:useBean id="Evaluacion" scope="page" class="aca.carga.CargaGrupoEvaluacion"/>
<jsp:useBean id="EvaluacionU" scope="page" class="aca.carga.CargaGrupoEvaluacionUtil"/>
<jsp:useBean id="Materia" scope="page" class="aca.carga.CargaGrupo"/>
<jsp:useBean id="MateriaU" scope="page" class="aca.carga.CargaGrupoUtil"/>
<jsp:useBean id="log" scope="page" class="aca.log.LogOperacion"/>
<jsp:useBean id="logU" scope="page" class="aca.log.LogOperacionUtil"/>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script type="text/javascript">
	function Nuevo()	{		
		document.frmevaluacion.EvaluacionId.value 		= "";
		document.frmevaluacion.NombreEvaluacion.value 	= "";
		document.frmevaluacion.Valor.value				= "";		
		document.frmevaluacion.EvaluacionE42.value		= "0";
		document.frmevaluacion.Accion.value				= "1";
		document.frmevaluacion.submit();
	}
		
	function Grabar(){
		if(document.frmevaluacion.EvaluacionId.value!= "" && document.frmevaluacion.Fecha.value!= "" 
		&& document.frmevaluacion.Tipo.value!="" && document.frmevaluacion.Valor.value!=""){
			document.frmevaluacion.Accion.value="2";
			document.frmevaluacion.submit();			
		}else{
			alert("Fill out the entire form ..! ");
		}
	}
	
	function BorrarEvaluacion(cursoCargaId,evaluacionId){		
		if(confirm("Are you sure to delete the evaluation?")==true){	  		
			document.location.href="borrarMetodo?CursoCargaId="+cursoCargaId+"&EvaluacionId="+evaluacionId;
		}			
		
	}
	
	function BorrarActividad(actividadId, cursoCargaId){
		Swal.fire({
			  title: 'Are you sure to delete the activity?',
			  text: "You cannot reverse this",
			  icon: 'warning',
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Yes',
			  cancelButtonText: 'Cancel'
			}).then((result) => {
			  if (result.isConfirmed) {
			    Swal.fire(
			      'Deleted!',
			      'The activity has been deleted',
			      'success'
			    );
		  		setTimeout(() => {
		  			document.location.href="borrarActividad?ActividadId="+actividadId+"&CursoCargaId="+cursoCargaId;
		  		}, 700);
			  }
			});
	}
	
	function Borrar2(){
			if(confirm("Are you sure to delete the activity?")==true){
	  			document.frmevaluacion.Accion.value="6";
				document.frmevaluacion.submit();
			}			
	}	
</script>

<%
	DecimalFormat getformato= new DecimalFormat("##0.00;(##0.00)");

 	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
 	String codigoEmpleado	= (String) session.getAttribute("codigoEmpleado");
 	
	String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
	String maestroNombre 	= (String)request.getAttribute("maestroNombre");
	String cursoNombre 		= (String)request.getAttribute("cursoNombre");	
	String estadoGrupo 		= (String)request.getAttribute("estadoGrupo");
	String estadoNombre 	= "-";
	
	if (estadoGrupo.equals("1")) estadoNombre = "<span style='color:gray; font-weight: bold;'>Subject created</span>"; 
	if (estadoGrupo.equals("2")) estadoNombre = "<span style='color:blue; font-weight: bold;'>Ordinary Subject</span>";
	if (estadoGrupo.equals("3")) estadoNombre = "<span style='color:green; font-weight: bold;'>Extraordinary Subject</span>";
	if (estadoGrupo.equals("4")) estadoNombre = "<span style='color:black; font-weight: bold;'>Closed Subject</span>";
	if (estadoGrupo.equals("5")) estadoNombre = "<span style='color:cherry; font-weight: bold;'>Registered Subject</span>";
	
	double	   nSumaValor	= 0;	
	// Variables de estudio	
	String avance			= "";
	int nEst=0,nAvance=0,nEval=0;
	double promGrupo=0, sumaEst=0;
	
	String sAccion 		= request.getParameter("Accion")==null?"1":request.getParameter("Accion");
	int nAccion			= Integer.parseInt(sAccion);
	String sResultado	= "";	
	
	List<CatEstrategia> lisEstrategias				= (List<CatEstrategia>)request.getAttribute("lisEstrategias");
	List<CargaGrupoEvaluacion> lisEvaluaciones 		= (List<CargaGrupoEvaluacion>)request.getAttribute("lisEvaluaciones");
	List<CargaGrupoActividad> lisActividades 		= (List<CargaGrupoActividad>)request.getAttribute("lisActividades");
	
	HashMap<String,CatEstrategia> mapaEstrategias 	= (HashMap<String,CatEstrategia>) request.getAttribute("mapaEstrategias");
	HashMap<String,String> mapaActividades 			= (HashMap<String,String>)request.getAttribute("mapaActividades");			
	HashMap<String,String> mapaPromedios 			= (HashMap<String,String>)request.getAttribute("mapaPromedios");
	int j=0, i=0;	
%>
<div class="container-fluid">
	<h2>Methodology <small class="text-muted fs-6">(  <%=codigoEmpleado%> - <%=maestroNombre%> - <%=cursoNombre%> - <%=estadoNombre%> )</small></h2>
	<div class="alert alert-info">
		<a href="cursos" class="btn btn-primary">Return</a>&nbsp;
		<a href="editarMetodo?CursoCargaId=<%=cursoCargaId%>" class="btn btn-primary">New Method</a>
	</div>
	<table style="width:100%" align="center" class="table table-sm table-bordered">
	<thead class="table-info">  
	<tr>
		<th width="5%" class="text-center"><spring:message code="aca.Numero"/></th>
		<th class="text-center">Op.</th>
    	<th>Methodology and Description</th>
		<th width="12%">Date</th>
		<th width="10%">Type</th>
		<th width="8%" class="text-end">Weight</th>	
		<th width="9%" class="text-center">Evaluated</th>
		<th width="8%" class="text-end">Avg.</th>
  	</tr>
  	</thead>
<%	
	nEst = lisEvaluaciones.size();
	int rowEst = 0;
	for (CargaGrupoEvaluacion cge : lisEvaluaciones){
		nSumaValor = nSumaValor + Double.valueOf(cge.getValor()).doubleValue();
		rowEst++;
		
		String estrategiaNombre = "-";
		if (mapaEstrategias.containsKey(cge.getEstrategiaId())){
			estrategiaNombre 	= mapaEstrategias.get(cge.getEstrategiaId()).getNombreEstrategia();
		}
		
		String promEval = "0";
		String evaluado = "N";
		if (mapaPromedios.containsKey(cge.getEvaluacionId())){
			evaluado = "S";
			promEval 	= mapaPromedios.get(cge.getEvaluacionId());	 
			nEval++;			
			if (cge.getTipo().equals("%")){
				promGrupo 	+= ((Double.valueOf(promEval).doubleValue()*Double.valueOf(cge.getValor()).doubleValue())/100);				
			}else{
				promGrupo	+= Double.valueOf(promEval).doubleValue();
			}
			sumaEst += Double.valueOf(cge.getValor()).doubleValue();			
			if (nEst==nEval && nEst>0) nAvance = 100; else if(nEst>0) nAvance = (nEval*100)/nEst; else nAvance = 0;	
		}
%>  
 	<tr>    
		<td align="center"><strong><%=rowEst%></strong></td>
    	<td class="text-center">
<%		if (evaluado.equals("N")){%>    	
    		<a href="javascript:BorrarEvaluacion('<%=cursoCargaId%>','<%=cge.getEvaluacionId()%>');"><i class="fas fa-trash"></i></a>
<%		}%>
			<a href="editarMetodo?CursoCargaId=<%=cursoCargaId%>&EvaluacionId=<%=cge.getEvaluacionId()%>"><i class="fas fa-edit"></i></a>
    	</td>      		
       	<td><strong><%=estrategiaNombre%> - <%=cge.getNombreEvaluacion()%> [<%=cge.getEvaluacionId()%>]</strong></td>
		<td><strong><%=cge.getFecha()%></strong></td>	
		<td><strong>
	<% 
		if (cge.getTipo().equals("%")){
			out.println("Percentage");
		}else if(cge.getTipo().equals("P")){
			out.println("Points");
		}else if(cge.getTipo().equals("E")){
			out.println("Ext. Points");
		}
	%>		</strong>
		</td>
		<td align="right"><strong><%=cge.getValor()%></strong></td>
		<td align="center"><strong><%=evaluado.equals("N")?"NO":"YES"%></strong></td>
		<td align="right"><strong><%=promEval%></strong></td>
  	</tr>
  
<%	
		int rowAct = 0;
		for(CargaGrupoActividad cga : lisActividades){
			if (cge.getEvaluacionId().equals(cga.getEvaluacionId()) ){
				rowAct++;
				String numNotas = "0";
				if (mapaActividades.containsKey(cga.getActividadId())){
					numNotas = mapaActividades.get(cga.getActividadId());
				}
%>
		<tr>
			<td align='center'>
				-
			</td>
			<td class="text-center">
<% 			if (numNotas.equals("0")){%>
				<a href="javascript:BorrarActividad('<%=cga.getActividadId()%>','<%=cursoCargaId%>');"><i class="fas fa-trash" style="color:red"></i></a>
<%			} %>				
			  	<a href="actividad?&CursoCargaId=<%=cursoCargaId%>&EvaluacionId=<%=cge.getEvaluacionId()%>&ActividadId=<%=cga.getActividadId()%>"><i class="fas fa-edit" style="color:green"></i></a>			
			</td>	
			<td><%=cga.getNombre()%></td>			
			<td><%=cga.getFecha()%></td>
			<td>Percentage</td>
			<td align="right"><%=cga.getValor()%></td>
		</tr>
<%
			}
		}	
%>
	<tr>
		<td>&nbsp;</td>		
		<td class="text-end"><a class="btn btn-success" title="New Activity" href="actividad?CursoCargaId=<%=cursoCargaId%>&EvaluacionId=<%=cge.getEvaluacionId()%>">
			<i class="fas fa-plus-circle" style="color:white"></i></a>
		</td>
	</tr>  
<%	}
	promGrupo = (promGrupo*100)/sumaEst;
%>
	  <tr bgcolor="#CCCCCC">
	    <td colspan="5" align="center"><b>The subject has a total of <%=nEst%> strategies.</b></td>
		<td width="8%" align="right"><b><%=getformato.format(nSumaValor)%></b></td>
		<td width="9%" align="right"><%=nAvance%>%</td>
		<td width="8%" align="right"><%=getformato.format(promGrupo)%></td>
	  </tr>
	</table>
</div>
<%	
	Evaluacion		= null;	
%>
<script>
	jQuery('#Fecha').datepicker();
</script>
<!-- fin de estructura -->