<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.carga.spring.CargaGrupoActividad"%>
<%@ page import= "aca.kardex.spring.KrdxCursoAct"%>
<%@ page import= "aca.kardex.spring.KrdxAlumnoActiv"%>
<%@ page import= "aca.carga.spring.CargaGrupo"%>
<%@ page import= "aca.alumno.spring.AlumPersonal"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>
<%@ page import= "aca.vista.spring.AlumnoEficiencia"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">	
	function Grabar(){
		if(document.frmevaluacion.ActividadId.value!= ""){		
			var total = document.frmevaluacion.totalAlumnos.value;
			var nota, max, mess;
			max = 100;
			for (i=0;i<total;i++){
				nota = document.getElementById("ChkNota"+i).value;
				if (parseFloat(nota)>parseFloat(max)){
					 mess = 1;
				}
			}
			if (mess==1){
				alert("Some grades exceed the value of the activity. Unable to save."); 
			}else{
				document.frmevaluacion.Accion.value="2";
				document.frmevaluacion.submit();			
			}			
		}else{
			alert("Fill out the entire form");
		}
	}
		
	function Borrar( ){
		if(document.frmevaluacion.ActividadId.value!=""){
			if(confirm("Are you sure you want to delete the grades?")==true){
	  			document.frmevaluacion.Accion.value="4";
				document.frmevaluacion.submit();
			}			
		}else{
			alert("Enter the key");
			document.frmevaluacion.EvaluacionId.focus(); 
	  	}
	}
	
</script>

<%
 	String codigoPersonal	= (String) request.getAttribute("codigoPersonal");
	String cursoCargaId		= (String) request.getAttribute("cursoCargaId");
	String maestro 			= (String) request.getAttribute("maestro");
	String materia 			= (String) request.getAttribute("materia");	
	String evaluacionId 	= (String) request.getAttribute("evaluacionId");	
	String actividadId		= (String) request.getAttribute("actividadId");	
	int numExtras 			= (int) request.getAttribute("numExtras");		
	
	String estado 			= (String) request.getAttribute("estado");		
	String resultado		= (String) request.getAttribute("resultado");	
	int j=0, i=0;
		
	// ArrayList que almacena la metodología de evaluacion de la Materia	 	
	List<CargaGrupoActividad> lisActividad  		= (List<CargaGrupoActividad>) request.getAttribute("lisActividad");
	
	//------------------------------------------------------Necesito llenar el listor de actividades para enlistarlas
	// ArrayList que almacena los Alumnos inscritos en la materia	  
	List<KrdxCursoAct> lisAlumnos = (List<KrdxCursoAct>) request.getAttribute("lisAlumnos");
	
	KrdxAlumnoActiv alumActividad = (KrdxAlumnoActiv) request.getAttribute("alumActividad");
	
	MapaCurso mapaCurso = (MapaCurso) request.getAttribute("mapaCurso");	
	HashMap<String,AlumnoEficiencia> mapaEficiencia 	= (HashMap<String,AlumnoEficiencia>) request.getAttribute("mapaEficiencia");		
	HashMap<String,AlumPersonal> mapAlumnosEnMateria 	= (HashMap<String,AlumPersonal>) request.getAttribute("mapAlumnosEnMateria");
	HashMap<String,KrdxAlumnoActiv> mapaNotas			= (HashMap<String,KrdxAlumnoActiv>)request.getAttribute("mapaNotas");
%>

<div class="container-fluid">
	<h3>Subject: <%=materia%> <small>( <%=codigoPersonal%> - <%=maestro%> )</small></h3>
	<div class="alert alert-info">
		<a href="evaluar?CursoCargaId=<%=cursoCargaId%>&Maestro=<%=maestro%>&Materia=<%=materia%>&EvaluacionId=0" class="btn btn-primary">Return</a>
	</div>
	<form name="frmevaluacion" action="evaact?EvaluacionId=<%=evaluacionId%>&NombreEval=<%=request.getParameter("NombreEval")%>" method="post"  target="_self">
		<input type="hidden" name="Accion">
		<input type="hidden" name="CursoCargaId" value="<%=cursoCargaId%>">
		<input type="hidden" name="Maestro" value="<%=maestro%>">
		<input type="hidden" name="Materia" value="<%=materia%>">
		<input type="hidden" name="NoCerrar" value='0'>
		<input type="hidden" name="ActividadId" value="<%=actividadId%>">
		<input type="hidden" name="totalAlumnos" value ="<%=lisAlumnos.size()%>">

		<table style="width:100%; margin:0 auto;" cellspacing="1" cellpadding="1">	
		  	<tr>
		    	<td>
			  		<strong>Strategy:</strong> ( <%=request.getParameter("NombreEval")%></font> - <em>Passing Grade: <%=mapaCurso.getNotaAprobatoria()%> </em>)	
				</td>
		  	</tr>
		</table>
		<table style="width:100%; margin:0 auto;" class="table table-fontsmall table-condensed table-bordered">
		  	<tr>
			    <th width="7%"><spring:message code="aca.Numero"/></th>
			    <th colspan="2">Activity and Description</th>
				<th width="10%">Submit <spring:message code="aca.Fecha"/></th>
				<th width="8%" class="text-end">Value</th>
				<th width="13%">Evaluation <spring:message code="aca.Tipo"/></th>
				<th width="10%" class="text-center">Option</th>
		  	</tr>
<%
	int count = 1;
	for (CargaGrupoActividad cga : lisActividad){
%>  
		  	<tr>
				<td width="7%" height="15">&nbsp;<span class="badge bg-secondary rounded-pill"><%=count%></span></td>
			    <td colspan="2"><%=cga.getNombre()%></td>
				<td width="10%"><%=cga.getFecha()%></td>
				<td width="8%" class="text-end"><%=cga.getValor()%></td>
				<td width="9%">
	<% 
			out.println("Percentage");
	%>	
				</td>
	  			<td width="10%" align="center">
<%	  if (estado.equals("2")){%>
				  	<a class="btn btn-primary btn-sm" href="evaact?CursoCargaId=<%=cursoCargaId%>&Maestro=<%=maestro%>&Materia=<%=materia%>&EvaluacionId=<%=cga.getEvaluacionId()%>&ActividadId=<%=cga.getActividadId()%>&NombreEval=<%=request.getParameter("NombreEval")%>">
				  		Grade
				  	</a>
<%	  }else if (estado.equals("3")){%>
					<a class="btn btn-success btn-sm" href="evaact?CursoCargaId=<%=cursoCargaId%>&Maestro=<%=maestro%>&Materia=<%=materia%>&EvaluacionId=E&ActividadId=E&NombreEval=<%=request.getParameter("NombreEval")%>">
						Extra
					</a>
<%	  }else if (estado.equals("4")){%>
		 			<b>Closed</b>
<%	  }else if (estado.equals("5")){ %>
			 		<b>Delivered</b>
<%	  }%>
  				</td>
  			</tr>
<%	
count++;
	}
%>
		</table>
		<div class="alert alert-success" align="center"><font size="3"><b>
<%
	if (estado.equals("1")){
		out.println("S u b j e c t &nbsp; C l o s e d");
	}else if(estado.equals("2")){
		out.println("O r d i n a r y &nbsp; G r a d i n g");
	}else if (estado.equals("3")){
		out.println("M a k e - u p &nbsp; G r a d i n g");
	}else if (estado.equals("4")){
		out.println("S u b j e c t &nbsp; C l o s e d");
	}else if (estado.equals("5")){
		out.println("S u b j e c t &nbsp; C l o s e d  &nbsp; AND &nbsp; D e l i v e r e d");
	}else{
		out.println("N o t &nbsp; f o u n d !");
	}

	int nE = (int) request.getAttribute("nE");
	int nEE = (int) request.getAttribute("nEE");
	int nAB = (int) request.getAttribute("nAB");
	if (  nEE < nE && nAB < lisAlumnos.size() ){
%>			<script>document.frmevaluacion.NoCerrar.value ='1';</script>
<%	}%>
			</b></font>
		</div>	
		<table style="width:100%; margin:0 auto;" class="table table-fontsmall table-condensed table-bordered">
		  	<tr>
			    <th><spring:message code="aca.Numero"/></th>
			    <th>Student ID</th>
				<th><spring:message code="aca.Nombre"/></th>
<%  for (j=0;j<lisActividad.size();j++){ %>
				<th width="25" class="text-end"><%=j+1%></th>
<%	}%>
				<th class="text-end">Progress</th>
				<th class="text-end">Mark</th>
  			</tr>
<%  
	numExtras=0;
	boolean okvalida = false;	
	String nombre = "";
	AlumPersonal alumPersonal = new AlumPersonal();
	
	for (KrdxCursoAct ac : lisAlumnos){
		if(mapAlumnosEnMateria.containsKey(ac.getCodigoPersonal())){
			alumPersonal = mapAlumnosEnMateria.get(ac.getCodigoPersonal());
		}
		
		nombre = alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno()+" "+alumPersonal.getNombre();
%>
		  	<tr>
		    	<td align="center" height="15"><span class="badge bg-secondary rounded-pill"><%=i+1%></span></td>
		    	<td align="center"><%=ac.getCodigoPersonal()%></td>	
		    	<td align="left">
		    	&nbsp;<%=nombre%>
		    	</td>

<%
		for (CargaGrupoActividad cga : lisActividad){			
 			String notaActividad 	= "0";
 			String actividadE42 	= "0";
 			if (mapaNotas.containsKey(ac.getCodigoPersonal()+cga.getActividadId())){
 				notaActividad 	= mapaNotas.get(ac.getCodigoPersonal()+cga.getActividadId()).getNota();
 				actividadE42 	= mapaNotas.get(ac.getCodigoPersonal()+cga.getActividadId()).getActividadE42();
 			}
%>
				<td width="25" class="text-end">
			  		<input type="Hidden" name="<%=ac.getCodigoPersonal()%>E42" value="<%=actividadE42%>">
<%
			String estiloPromedio = "";					
			if (cga.getActividadId().equals(actividadId)){
				if (!ac.getTipoCalId().equals("3") && !ac.getTitulo().equals("S")){
					if (notaActividad.equals("-")){
						notaActividad="";						
					}
					if (!okvalida){
						okvalida=true;
						//<input name="evaluacionTipo" type="hidden" value="<%=cga.getTipo()">
%>
						<input name="evaluacionValor" type="hidden" value="<%=cga.getValor()%>">
<%					}
%>					
					<input id='ChkNota<%=i%>' name="<%=ac.getCodigoPersonal()%>Nota"  type="text" class="text" value="<%=notaActividad%>" size="3" maxlength="5">
<%				}else{%>
					<input id='ChkNota<%=i%>' type="Hidden" name="<%=ac.getCodigoPersonal()%>Nota" value="<%=notaActividad%>">
<%				}%>
<%			}else{ 
					if (notaActividad.equals("-")){
						estiloPromedio = "class='label label-warning'";
					}
%>					
	  			<span <%=estiloPromedio%>><%=notaActividad%></span>
<%
			}
%>			        
				</td>
	
<%		}

		String strPromedio  = "0";
		String avance 		= "0";
		String estiloAvance	= "class='badge bg-warning'";
		if (mapaEficiencia.containsKey(ac.getCodigoPersonal()+evaluacionId)){
			strPromedio = mapaEficiencia.get(ac.getCodigoPersonal()+evaluacionId).getNota();
			avance 		= mapaEficiencia.get(ac.getCodigoPersonal()+evaluacionId).getEvaluadas();
			if (Double.valueOf(avance) < 100) estiloAvance = "class='badge bg-warning'"; else estiloAvance = "class='badge bg-success'";
		}
		
		strPromedio = strPromedio.trim();
		strPromedio = String.valueOf(Double.valueOf(strPromedio).intValue());
%>
				<td class="text-end"><span <%=estiloAvance%>><%=avance%></span></td>
				<td class="text-end"><span class="badge bg-dark"><%=strPromedio%></span></td>
			</tr>
	<%	
			i++;
		} // termina for de lisAlumnos %>
			<tr>
		    	<td colspan="24" style="text-align:center"> 
<%	if ( (!actividadId.equals("0")) && (estado.equals("1")||estado.equals("2")) ){	%>  	    
					<a class="btn btn-primary" href="javascript:Grabar()">Save Grades</a>&nbsp;&nbsp;&nbsp;
					<a class="btn btn-primary" href="javascript:Borrar()">Delete Grades</a> 
<%	}%>		
					<input type="Hidden" name="numExtras" value="<%=numExtras%>">				
				</td>
		  	</tr>
		  	<tr><td colspan="24" align="center"><%=resultado%></td></tr>
		</table>
	</form>
</div>	