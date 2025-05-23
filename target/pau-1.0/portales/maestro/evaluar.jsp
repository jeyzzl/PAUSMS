<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.carga.spring.CargaGrupo"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>
<%@ page import= "aca.carga.spring.CargaGrupoCurso"%>
<%@ page import= "aca.kardex.spring.KrdxCursoAct"%>
<%@ page import= "aca.kardex.spring.KrdxAlumnoEval"%>
<%@ page import= "aca.kardex.spring.KrdxAlumnoActiv"%>
<%@ page import= "aca.vista.spring.AlumnoEficiencia"%>
<%@ page import= "aca.parametros.spring.Parametros"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function Grabar(){
		if(document.frmevaluacion.EvaluacionId.value!= ""){
			var total = document.frmevaluacion.totalAlumnos.value;
			var tipo = document.frmevaluacion.evaluacionTipo.value;
			var nota, max, mess;
			
			if (tipo == "P" || tipo == "E") max = document.frmevaluacion.evaluacionValor.value;
			else max = 100;
			
			for (i=0;i<total;i++){				
				nota = document.getElementById("ChkNota"+i).value;
				
				if (nota=='-') nota = "0";						
				if (parseFloat(nota)>parseFloat(max)){
					 mess = 1;
				}else if (isNaN(nota)) {
			     	mess = 2;
			    }/*else if (nota % 1 != 0) {} */
			}
			
			if (mess==1){
				alert("Some grades exceed the value of the assessment. Unable to save.");			
			}else if (mess==2){
				alert("Only numbers between 0 and 100 allowed");
			}else{
				document.frmevaluacion.Accion.value="2";
				document.frmevaluacion.submit();
			}
			
		} else {
			alert("Fill out the entire form!");
		}
	}

	function GrabarExtra(){
		document.frmevaluacion.Accion.value="6";
		document.frmevaluacion.submit();			
	}
	
	function GrabarTipoCal(){		
		document.frmevaluacion.Accion.value="9";
		document.frmevaluacion.submit();		
	}
		
	function Borrar( ){
		if(document.frmevaluacion.EvaluacionId.value!=""){
			if(confirm("Are you sure you want to delete the grades?")==true){
	  			document.frmevaluacion.Accion.value="4";
				document.frmevaluacion.submit();
			}			
		}else{
			alert("Enter the key");
			document.frmevaluacion.EvaluacionId.focus(); 
	  	}
	}
	function SimularCierre( ){
		if (document.frmevaluacion.NoCerrar.value=="0"){
			if(confirm("This operation sets '0' in the assessments that have not been registered, do you want to continue?")==true){
	  			document.frmevaluacion.Accion.value="3";
				document.frmevaluacion.submit();
			}			
		}else alert("You cannot close the subject because you have not evaluated all the assessments.");
	}
	function CerrarMateria( ){
		if (document.frmevaluacion.NoCerrar.value=="0"){
			if(confirm("Are you sure you want to close the ordinary grading? Once closed any grades and evaluations become FINAL and UNCHANGABLE")==true){
	  			document.frmevaluacion.Accion.value="5";
				document.frmevaluacion.submit();
			}			
		}else alert("You cannot close the subject because you have not evaluated all the assessments.");
	}
	function CerrarMateriaExtra( ){
		if(confirm("Are you sure you want to close the extraordinary grading?")==true){
	  			document.frmevaluacion.Accion.value="7";
				document.frmevaluacion.submit();
		}			
	}

	function cambia(){
		document.frmevaluacion.CursoCargaId.value =document.frmevaluacion.cc.value;
		document.frmevaluacion.Accion.value ="1";
		document.frmevaluacion.submit();
	}		
</script>
<%	
	java.text.DecimalFormat getFormato= new java.text.DecimalFormat("###,##0.00;(###,##0.00)");

 	String sCodigoPersonal	= (String) session.getAttribute("codigoPersonal");
 	String sCargaId 		= (String) session.getAttribute("cargaId");
	Parametros parametros 	= (Parametros)request.getAttribute("parametros");
 	
 	CargaGrupo cargaGrupo	= (CargaGrupo) request.getAttribute("cargaGrupo");
 	MapaCurso mapaCurso		= (MapaCurso) request.getAttribute("mapaCurso");
 	String maestroNombre 	= (String) request.getAttribute("maestroNombre");
	String materiaNombre 	= (String) request.getAttribute("materiaNombre");
	String cursoId			= (String) request.getAttribute("cursoId");
	int diferida			= (int) request.getAttribute("diferida");
	int numEstrategias		= (int) request.getAttribute("numEstrategias");
	int numEvaluadas		= (int) request.getAttribute("numEvaluadas");
	int numBajas			= (int) request.getAttribute("numBajas");
	
	String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
	String evaluacionId 	= request.getParameter("EvaluacionId")==null?"0":request.getParameter("EvaluacionId");
	String sAccion 			= request.getParameter("Accion")==null?"1":request.getParameter("Accion");
	int nAccion				= Integer.parseInt(sAccion);
	
	String sEstado 			= cargaGrupo.getEstado();
	String sNota			= "";
	String sPromedio		= "";
	double sPromedioBase4 	= 0;
	String promedioOld 		= "0"; 
	int nPromedio 			= 0;
	double sePromedio		= 0;
	double sumaPuntos  		= 0;
	String eficiencia       = "";
	
	String opc1="", opc2="", opc4="",opc5="",opc6="", opc7="";
	int j=0, i=0;
	int nContador = 0;
	String sNumExtras = request.getParameter("numExtras");
	if (sNumExtras==null) sNumExtras="0";
	int numExtras = Integer.parseInt(sNumExtras);
	String sContador 		= "";
	String sMatricula		= "";
	
	aca.util.Fecha f 		= new aca.util.Fecha();
	String sFecha 			= f.getFecha("1");	
	
	aca.catalogo.TipoCalUtil TipocalU = new aca.catalogo.TipoCalUtil(); // Catalogo de calificaciones.

	List<aca.carga.spring.CargaGrupoEvaluacion> lisEvaluaciones 	= (List<aca.carga.spring.CargaGrupoEvaluacion>) request.getAttribute("lisEvaluaciones");	
	List<aca.kardex.spring.KrdxCursoAct> lisAlumnos 				= (List<aca.kardex.spring.KrdxCursoAct>) request.getAttribute("lisAlumnos");	
	HashMap<String,String> mapaEstrategias							= (HashMap<String,String>) request.getAttribute("mapaEstrategias");
	
	// Map de alumnos
	HashMap<String,String> mapAlumnos 					= (HashMap<String,String>) request.getAttribute("mapaAlumnos");	
	// Map que obtiene las notas de todos los alumnos en la materia
	HashMap<String,String> mapNotas 					= (HashMap<String,String>) request.getAttribute("mapaNotas");	
	// Map de saldos vencidos del alumno
	//java.util.HashMap<String,String> mapDeuda = aca.financiero.FesContratoFinancieroUtil.mapSaldoVencido(conEnoc, cursoCargaId);
	// Map de tipos de calificaci�n
	HashMap<String,String> mapTipoCal 					= (HashMap<String,String>) request.getAttribute("mapaTipoCal");
	// Map de permisos
	//java.util.HashMap<String,String> mapPermiso = aca.financiero.FinPermisoUtil.mapAlumnoPermiso(conEnoc, " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT WHERE CURSO_CARGA_ID = '"+cursoCargaId+"') AND now() BETWEEN F_INICIO AND F_LIMITE");
	//Map de puntos
	HashMap<String, String> mapAlumnoPuntos 			= (HashMap<String,String>) request.getAttribute("mapaPuntos");	
	//Map de puntos Extras
	HashMap<String, String> mapaExtras 			= (HashMap<String,String>) request.getAttribute("mapaExtras");	
	//Map de puntos evaluados por alumno
	HashMap<String, String> mapaEvaluados 			= (HashMap<String,String>) request.getAttribute("mapaEvaluados");
	//Map de limite de extraordinario en el plan del alumno
	HashMap<String, String> mapaLimiteExtras 				= (HashMap<String,String>) request.getAttribute("mapaLimiteExtras");	
	HashMap<String, MapaCurso> mapaCursoMateria			= (HashMap<String,MapaCurso>) request.getAttribute("mapaCursoMateria");
	HashMap<String, String> mapaActividades				= (HashMap<String,String>) request.getAttribute("mapaActividades");
	HashMap<String, String> mapaPromedios				= (HashMap<String,String>) request.getAttribute("mapaPromedios");
	HashMap<String, String> mapaArchivosEval			= (HashMap<String,String>) request.getAttribute("mapaArchivosEval");
	HashMap<String, String> mapaArchivosAlum			= (HashMap<String,String>) request.getAttribute("mapaArchivosAlum");
	HashMap<String, AlumnoEficiencia> mapaEficiencia	= (HashMap<String,AlumnoEficiencia>) request.getAttribute("mapaEficiencia");
	HashMap<String, String> mapaAlumGraduados 			= (HashMap<String,String>) request.getAttribute("mapaAlumGraduados");
	HashMap<String, String> mapaLetrasDeNotas			= (HashMap<String,String>) request.getAttribute("mapaLetrasDeNotas");
	
	String sColor		= "#eeeeee";
	String estado 		= "";
%>
<div class="container-fluid">
	<h2>Grading <small class="text-muted fs-6">( <%=sCodigoPersonal%>  - <%=maestroNombre%> - <%=materiaNombre%>)</small> </h2>
	<form name="frmevaluacion" action="accion" method="post">
	<input type="hidden" name="Accion">
	<input type="hidden" name="CursoCargaId" value="<%=cursoCargaId%>">
	<input type="hidden" name="Maestro" value="<%=maestroNombre%>">
	<input type="hidden" name="Materia" value="<%=materiaNombre%>">
	<input type="hidden" name="CursoId" value="<%=cursoId%>">
	<input type="hidden" name="NoCerrar" value='0'>
	<input type="Hidden" name="EvaluacionId" value="<%=evaluacionId%>">
	<div class="alert alert-info">
		<a class="btn btn-primary btn-sm" href="cursos"><spring:message code="aca.Regresar"/></a>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<a class="btn btn-secondary btn-sm" href="filesMaestro?CursoCargaId=<%=cursoCargaId%>"><b><spring:message code="portal.maestro.evaluar.Documentos"/></b></a>
		&nbsp;		
<%
	if (cargaGrupo.getEstado().equals("1")) estado = "Created";
	else if (cargaGrupo.getEstado().equals("2")) estado = "Ordinary";
	else if (cargaGrupo.getEstado().equals("3")) estado = "Extraordinary";
	else if (cargaGrupo.getEstado().equals("4")) estado = "Delivered";
	else if (cargaGrupo.getEstado().equals("5")) estado = "Registered";	
	
	if (  numEvaluadas < numEstrategias && numBajas < lisAlumnos.size() ){
%>		<script>document.frmevaluacion.NoCerrar.value ='1';</script>
<%	}
	if (sEstado.equals("2") && evaluacionId.equals("0")){
		out.print("<a class='btn btn-dark btn-sm' href=\"javascript:SimularCierre()\"><b>SIMULATE CLOSING</b></a>");	//<spring:message code="portal.maestro.evaluar."/>	 
		out.print("&nbsp; &nbsp;");
		out.println("<a class='btn btn-danger btn-sm' href=\"javascript:CerrarMateria()\"><b>CLOSE ORDINARY</b></a>");		//<spring:message code="portal.maestro.evaluar."/>
	}else if(sEstado.equals("3") && evaluacionId.equals("0")){
		out.println("<a class='btn btn-primary btn-sm' href=\"javascript:CerrarMateriaExtra()\">CLOSE REMEDIAL</a>");//<spring:message code="portal.maestro.evaluar."/>
%>
		&nbsp;&nbsp;<a class="btn btn-info btn-sm" href="diferida?CursoCargaId=<%=cursoCargaId%>&CursoId=<%=cursoId%>&Materia=<%=materiaNombre%>&Maestro=<%=maestroNombre%>&EvaluacionId=<%=evaluacionId%>"><spring:message code="portal.maestro.evaluar.CalificacionesDiferidas"/></a>
<% 
	}else if(sEstado.equals("4")){	
%>		
		<a class="btn btn-info btn-sm" href="cactames?cursoCargaId=<%=cursoCargaId%>&imp=1" target="_blank"><spring:message code="portal.maestro.evaluar.ImprimirActa"/></a>
		&nbsp;&nbsp;
		<a class="btn btn-info btn-sm" href="correccion?CursoCargaId=<%=cursoCargaId%>"><spring:message code="portal.maestro.evaluar.CorreccionNotas"/></a>
		&nbsp;&nbsp;
		<a class="btn btn-info btn-sm" href="diferida?CursoCargaId=<%=cursoCargaId%>&CursoId=<%=cursoId%>&Materia=<%=materiaNombre%>&Maestro=<%=maestroNombre%>&EvaluacionId=<%=evaluacionId%>"><spring:message code="portal.maestro.evaluar.CalificacionesDiferidas"/></a>
<%	} %>
<%	if(lisEvaluaciones.size() == 0){%>
		<a class="btn btn-outline-success" href="evaluar?Crear=1&CursoCargaId=<%=cursoCargaId%>" ><spring:message code="portal.maestro.evaluar.Eval"/> 100%</a>
<%	} %>
	</div>
	<table style="width:100%" class="table table-sm table-bordered">
	<tr class="table-info">
    	<td colspan="9"><h4><spring:message code="portal.maestro.evaluar.EsquemaEvaluacion"/> &nbsp; <small class="text-muted fs-6"> [ <%=cursoCargaId %> ] &nbsp; [ <%=materiaNombre %> ] &nbsp; [<%= estado %>] &nbsp; [ <spring:message code="portal.maestro.evaluar.NotaAprobatoria"/> <%=mapaCurso.getNotaAprobatoria()%>]</small></h4></td>
    </tr>	
	<tr>
    	<th width="3%" style="text-align:left"><spring:message code="aca.Numero"/></th>
    	<th width="41%" colspan="2" style="text-align:left"><spring:message code="portal.maestro.evaluar.MetodologiaDescripcion"/></th>
    	<th width="3%" style="text-align:center"><i class="fas fa-file-alt"></i></th>
		<th width="15%" style="text-align:left">Submit <spring:message code="aca.Fecha"/></th>
		<th width="8%" class="text-end"><spring:message code="portal.maestro.evaluar.Valor"/></th>
		<th width="10%" style="text-align:center">Evaluation <spring:message code="aca.Tipo"/></th>
		<th width="10%" class="text-end"><spring:message code="aca.Actividades"/></th>
		<th width="10%" class="text-center">
		Grade
<%		if (sEstado.equals("3")){%>
  			<a class="btn btn-warning btn-sm" href="evaluar?CursoCargaId=<%=cursoCargaId%>&Maestro=<%=maestroNombre%>&Materia=<%=materiaNombre%>&CursoId=<%=cursoId%>&EvaluacionId=E"><spring:message code="portal.maestro.evaluar.Extra"/></a>
<%  	} %>
		</th>
  	</tr>
<%	
	sEstado = cargaGrupo.getEstado();
	int row = 0;
	for ( aca.carga.spring.CargaGrupoEvaluacion evaluacion : lisEvaluaciones){
		row++;		
		double puntos = Double.parseDouble(evaluacion.getValor());
		sumaPuntos += puntos;
		String estrategiaNombre = "-";
		if (mapaEstrategias.containsKey(evaluacion.getEstrategiaId())){
			estrategiaNombre = mapaEstrategias.get(evaluacion.getEstrategiaId());
		}
		
		String actividad = "0";
		if (mapaActividades.containsKey(evaluacion.getEvaluacionId())){
			actividad = mapaActividades.get(evaluacion.getEvaluacionId());
		}
		String numArchEval = "0";
		if (mapaArchivosEval.containsKey(cursoCargaId+"-"+evaluacion.getEvaluacionId())){
			numArchEval = mapaArchivosEval.get(cursoCargaId+"-"+evaluacion.getEvaluacionId());
		}
%>  
	<tr>
    	<td height="15">&nbsp;<span class="badge bg-secondary rounded-pill"><%=row%></span></td>
    	<td colspan="2">    		
    		<span class="badge bg-dark rounded-pill"><%=evaluacion.getEvaluacionId()%></span> <%=evaluacion.getNombreEvaluacion()%>   		    		
    	</td>
    	<td align="center">
    		<a href="archivoEval?CursoCargaId=<%=cursoCargaId%>&EvaluacionId=<%=evaluacion.getEvaluacionId()%>&Opcion=2">
    		<span class="badge bg-info rounded-pill"><%=numArchEval%></span>    		
    		</a>
    	</td>
		<td align="center"><%=evaluacion.getFecha()%></td>
		<td style="text-align:right"><%=getFormato.format(puntos)%></td>
		<td align="center">
	<% 
		if (evaluacion.getTipo().equals("%")){
			out.println("Percentage");
		}else if(evaluacion.getTipo().equals("P")){
			out.println("Points");
		}else if(evaluacion.getTipo().equals("E")){
			out.println("Extra Points");
		}
	%>	
		</td>
		<td style="text-align:right;"><span class="badge bg-info rounded-pill"><%=actividad%></span></td>
 		<td align="center">
	<%	if (sEstado.equals("2")){
  			if (!actividad.equals("0")){ %>  			
			<a class="btn btn-success btn-sm" href="evaact?CursoCargaId=<%=cursoCargaId%>&Maestro=<%=maestroNombre%>&Materia=<%=materiaNombre%>&EvaluacionId=<%=evaluacion.getEvaluacionId()%>&NombreEval=<%=estrategiaNombre%> - <% if (evaluacion.getNombreEvaluacion()==null){ out.print(""); }else{ out.print(evaluacion.getNombreEvaluacion());} %>">
		<% 	}else{ %>
  			<a class="btn btn-primary btn-sm" href="evaluar?CursoCargaId=<%=cursoCargaId%>&Maestro=<%=maestroNombre%>&Materia=<%=materiaNombre%>&EvaluacionId=<%=evaluacion.getEvaluacionId()%>">
		<%	} %>
  			<i class="far fa-edit"></i></a>
	<%  }else if (sEstado.equals("3")){ %>
 			<font color="#AE2113"><b><spring:message code="portal.maestro.evaluar.Extra"/></b></font>
	<%  }else if (sEstado.equals("4")){ %>
			<font color="#AE2113"><b><spring:message code="aca.Cerrada"/></b></font>
	<%  }else if (sEstado.equals("5")){ %>
 			<font color="green"><b><spring:message code="portal.maestro.evaluar.Entregada"/></b></font>
	<%  } %>
		</td>
	</tr>
<%	}%>
	<tr>
		<th colspan="5" style="text-align:right; font-size:9pt;"><b><spring:message code="portal.maestro.evaluar.TotalPuntos"/>:<b></th>
		<th style="text-align:right; font-size:9pt;"><b><%=getFormato.format(sumaPuntos)%></b></th>
		<th colspan="3">&nbsp;</th>
  	</tr>
	</table>	
    	
	<table style="width:100%"  class="table table-sm table-bordered">
	<tr class="table-info"> 
	<td colspan="4"><h5><spring:message code="portal.maestro.evaluar.ListadoAlumnos"/><small class="text-muted fs-6">( <i class='fas fa-graduation-cap'></i></span> = <spring:message code="portal.maestro.evaluar.AlumnosGraduandos"/> )</small></h5></td>
	<td colspan="<%=lisEvaluaciones.size()%>" class="text-center"><h5><spring:message code="portal.maestro.evaluar.Evaluaciones"/></h5></td>
	<td colspan="2" class="text-center"><h5>Progress and Marks</h5></td>
	<td class="text-center"><h5><!-- <spring:message code="portal.maestro.evaluar.Calculo"/>-->Grade</h5></td>
	<td colspan="3" class="text-center"><h5>Obtained</h5></td>
	<td class="text-center"><h5><spring:message code="portal.maestro.evaluar.Estado"/></h5></td>
	</tr>
  	<tr>
    	<th style="text-align:left"><spring:message code="aca.Numero"/></th>
    	<th style="text-align:left">Student ID</th>
    	<th style="text-align:center"><i class="fas fa-file-alt"></i></th>    	
		<th style="text-align:left"><spring:message code="aca.Nombre"/></th>
<%		
		int evaluaciones = lisEvaluaciones.size();
		if(lisEvaluaciones.size() < 1) evaluaciones = 1;
		for (j=0;j<evaluaciones;j++){ %>
		<th style="text-align:center" width="25"><%=j+1%></th>
<%		} %>
		<th style="text-align:right" title="Total points/percentage evaluated"> Progress (%)</th>
		<th style="text-align:right" title="Efficiency">Raw Mark</th>
		<th style="text-align:right">Current</th>
		<th style="text-align:right" title="Maximum mark achievable if student obtains a full note in all the assessments that have not yet been graded."><spring:message code="portal.maestro.evaluar.Max"/></th>
		<th style="text-align:right" title="Student's current grade">GPA</th>
		<th style="text-align:right"><spring:message code="portal.maestro.evaluar.Extra"/></th>
		<th style="text-align:center">
	<%	if (sEstado.equals("2")){ %>
			<a href="evaluar?CursoCargaId=<%=cursoCargaId%>&Maestro=<%=maestroNombre%>&Materia=<%=materiaNombre%>&EvaluacionId=T&CursoId=<%=cursoId%>" class="btn btn-success btn-sm"><i class="far fa-edit"></i></a>
	<%	}%>
		</th>
  	</tr>
  	<input type = 'hidden' name = 'totalAlumnos' value ='<%=lisAlumnos.size()%>'>
<%  numExtras=0;
	boolean okvalida = false;
	String sBgcolor="";
	String enfocar = "";
	String nombreAlumno = "";
	double saldoVencido = 0;	
	
	for (i=0; i<lisAlumnos.size(); i++){
		aca.kardex.spring.KrdxCursoAct ac = (aca.kardex.spring.KrdxCursoAct) lisAlumnos.get(i);
		
		if(mapAlumnos.containsKey(ac.getCodigoPersonal())){
			nombreAlumno = mapAlumnos.get( ac.getCodigoPersonal() );
		}else{
			nombreAlumno = "-";
		}
		
		String numArchAlum = "0";
		if (mapaArchivosAlum.containsKey( ac.getCodigoPersonal() )){
			numArchAlum = mapaArchivosAlum.get(ac.getCodigoPersonal());
		}
		if ((i % 2) == 0 ) sBgcolor = sColor; else sBgcolor = "";		
		
		// Verifica si el alumno tiene un permiso financiero
		boolean permiso = false;
		//if (mapPermiso.containsKey(ac.getCodigoPersonal())) permiso = true; 
		
		String textoDeuda = (saldoVencido < -100 && !permiso)?" title='Balance Due: $"+getFormato.format(saldoVencido)+"'":"";
		String saldoColor = (saldoVencido < -100 && !permiso)?" style='color: #AE2113;'":"";
		
		String graduado = "";
		if(mapaAlumGraduados.containsKey(ac.getCodigoPersonal())){
			graduado = "<i class='fas fa-graduation-cap'></i></span>";
		}
%>
	<tr>
    	<td <%=saldoColor%> align="center"><%=i+1%></td>
    	<td <%=saldoColor%> align="center" <%=textoDeuda%> ><%=ac.getCodigoPersonal()%></td>
    	<td <%=saldoColor%> align="center">
    		<a href="archivoAlum?CodigoAlumno=<%=ac.getCodigoPersonal()%>&CursoCargaId=<%=cursoCargaId%>">
    		<span class="badge bg-info rounded-pill"><%=numArchAlum%></span>
    		</a>
    	</td>
    	<td <%=saldoColor%> align="left"><%=graduado%> <%=nombreAlumno%></td>
<%		
		// Determina el estilo del texto(rojo:deudores, negro:sin deuda) y la alineaci�n en todo el rengl�n.
		
		//if (saldoVencido<-100 && !permiso) estiloNota = "style='color:#AE2113; text-align:right;'"; else estiloNota = "style='text-align:right;'";		
		String estiloNota = "";
		String letraProm = "";
		
		// SI LA LISTA DE EVALUACIONES NO ESTA VACIA ENTRA AL FOR
		if(lisEvaluaciones.size() > 1){
			for (j=0;j<lisEvaluaciones.size();j++){
				aca.carga.spring.CargaGrupoEvaluacion evaluacion = (aca.carga.spring.CargaGrupoEvaluacion) lisEvaluaciones.get(j);			
				String avance = "0";
				if (mapaEficiencia.containsKey( ac.getCodigoPersonal()+evaluacion.getEvaluacionId())){
					sNota 		= mapaEficiencia.get( ac.getCodigoPersonal()+evaluacion.getEvaluacionId()).getNota();				
					avance 		= mapaEficiencia.get( ac.getCodigoPersonal()+evaluacion.getEvaluacionId()).getEvaluadas();
					if (sNota==null || sNota.equals("null")) sNota = "-";
					if (Double.valueOf(avance) < 100 || sNota.equals("-")) estiloNota = "class='label label-warning'"; else estiloNota = "";				
				}else{
					sNota = "-";
					estiloNota = "class='label label-warning'";
				}						
%>
		<td width="25" class="text-end">
	  	<%-- <input type="Hidden" name='CodigoPersonal<%=i%>' value='<%=ac.getCodigoPersonal()%>'> --%>
<%
				if(enfocar.equals(""))
					enfocar = "ChkNota"+i;

				if (evaluacion.getEvaluacionId().equals(evaluacionId)){
					if (!ac.getTipoCalId().equals("3") && !ac.getTitulo().equals("S")){  
						if (sNota.equals("-")) sNota="";
						if (!okvalida){
							okvalida=true;
%>						<input name="evaluacionTipo" type="hidden" value="<%=evaluacion.getTipo()%>">
						<input name="evaluacionValor" type="hidden" value="<%=evaluacion.getValor()%>">
<%						}
%>					
					<input id='ChkNota<%=i%>' name="Nota<%=i%>" id="Nota<%=i%>"  type="text" class="text" tabindex="<%=i+1%>" value="<%=sNota%>" size="3" maxlength="5">
<%					}else{%>
					<input id='ChkNota<%=i%>' type="Hidden" name="Nota<%=i%>" value="<%=sNota%>">
<%					}%>
				<input type="Hidden" name="NotaOld<%=i%>" value="<%=sNota%>">	
<%				}else{ %>
				<span <%=estiloNota%>><%=sNota%></span>
<%				} %>       
		</td>
<%			} // SI LA LISTA ESTA VACIA SE IMPRIME UN SOLO ESPACIO DE LA TABLA
		} else {  
%>
		<td class="text-center">
		-
		</td>
<%
		}

		if(sEstado.equals("3") || sEstado.equals("4") || sEstado.equals("5")){			
			sPromedio = ac.getNota();
		}else{
			sPromedio = "0";
			if (mapaPromedios.containsKey(ac.getCodigoPersonal())){
				sPromedio = mapaPromedios.get(ac.getCodigoPersonal());
			}	
			
			// Trae los puntos ordinarios del alumno en la materia
			double puntos = 0;
			if( mapAlumnoPuntos.containsKey(ac.getCodigoPersonal()) ){
				puntos = Double.parseDouble( mapAlumnoPuntos.get(ac.getCodigoPersonal()));
			}

			// Trae los puntos extras del alumno en la materia
			double puntosExtra = 0;
			if( mapaExtras.containsKey(ac.getCodigoPersonal()) ){
				puntosExtra = Double.parseDouble( mapaExtras.get(ac.getCodigoPersonal()) );
			}

			// Total de puntos del alumno en la materia
			sPromedio = String.valueOf((int)Math.round(puntos+puntosExtra));
		}
		if (sPromedio==null) sPromedio="0";	

		if(mapaLetrasDeNotas.containsKey(sPromedio)){
			letraProm = mapaLetrasDeNotas.get(sPromedio);
		}else{
			letraProm = "X";			
		}

		// Obtiene el valor en base 4 del promedio de la materia
		sPromedioBase4 = (Double.valueOf(sPromedio) / 100) * 4;
		
		// Obtiene el valor en entero del promedio
		nPromedio = Integer.parseInt(sPromedio); //sPromedio = String.valueOf((long)Math.floor(Double.valueOf(sPromedio).doubleValue() + 0.5d));
		double evaluados = 0;
		if (mapaEvaluados.containsKey(ac.getCodigoPersonal())){
			evaluados = Double.parseDouble( mapaEvaluados.get(ac.getCodigoPersonal()));
		}
		//if (ac.getCodigoPersonal().equals("0941428")&&evaluados>0) System.out.println("Promedio2 = "+(nPromedio/evaluados*100));
		double rendimiento = 0; 
		if (evaluados>0) rendimiento = Math.round(nPromedio/evaluados*100);
		
		eficiencia 			= String.valueOf((int)rendimiento);		
		sePromedio			= rendimiento/10;		
		double notaMaxima 	= 100-evaluados+Double.valueOf(sPromedio);
		
		// Colores de la nota m�xima
		String colorMaximo = "";
		if (notaMaxima<70){			
			colorMaximo = "class='badge bg-danger rounded-pill'";
		}else if (notaMaxima>=70 && notaMaxima<=80){
			colorMaximo = "class='badge bg-warning rounded-pill'";
		}else{
			colorMaximo = "class='badge bg-success rounded-pill'";			
		}		
		String notaAprobatoria = "70";
		if (mapaCursoMateria.containsKey(ac.getCursoId())){
			notaAprobatoria = mapaCursoMateria.get(ac.getCursoId()).getNotaAprobatoria();
		}		
%>
		<td class="text-end"><b><%=evaluados%></b></td>
		<td class="text-end" title="Points=<%=sPromedio%> Evaluated:<%=evaluados%>"><%=eficiencia %></td>
		<td class="text-end"><span class="badge bg-dark rounded-pill"><%=letraProm%></span></td>
		<td class="text-end" title="Maximum grade achievable: "><span <%=colorMaximo%>><%=getFormato.format(notaMaxima)%></span></td>
		<td class="text-end"><span class="badge bg-dark rounded-pill"><b><%=sPromedioBase4%></b></span></td>
		<td class="text-center"><b>
<%
		numExtras++;
	// Determina el valor del limite permitido para evalua el extraordinario en un plan de estudios
	int limiteExtra = 60;
	if ( mapaLimiteExtras.containsKey(ac.getCodigoPersonal()) ){			
		limiteExtra = Integer.parseInt( mapaLimiteExtras.get(ac.getCodigoPersonal()) );		
	}
	if (!ac.getTipoCalId().equals("6") && evaluacionId.equals("E") && 
		((nPromedio >= limiteExtra && nPromedio < Integer.parseInt(notaAprobatoria)) ||
		(ac.getTipoCalId().equals("4") && nPromedio >= limiteExtra))){  
%>	  
	  	<input name="NotaExtra<%=numExtras%>" type="text" class="text" value="<%=ac.getNotaExtra()%>" size="3" maxlength="5">
 	  	<input type="Hidden" name='CodigoPersonalE<%=numExtras%>' value='<%=ac.getCodigoPersonal()%>'>
<%  }else{
		if (ac.getNotaExtra()==null) out.print("&nbsp;");
		else if (ac.getNotaExtra().equals("0")) out.print("&nbsp;"); 
		else out.print(ac.getNotaExtra());
 	}
%>
		</b>
		</td>
		<td <%=(saldoVencido < -50 && !permiso)?" style=\"color: #AE2113;\"":"" %> class="text-center">
<%	if ( evaluacionId.equals("T") && !ac.getTipoCalId().equals("3")){ 
		opc1 = " ";	opc2 = " "; opc4 = " ";	opc5 = " ";	opc6 = " "; opc7 = " ";
		if (ac.getTipoCalId().equals("4"))
			opc4 = "selected";
		else if (ac.getTipoCalId().equals("2"))
			opc2 = "selected";
		else if (ac.getTipoCalId().equals("5"))
			opc5 = "selected";
		else if (ac.getTipoCalId().equals("6"))
			opc6 = "selected";
		else if (ac.getTipoCalId().equals("7"))
			opc7 = "selected";
		else
			opc1 = "selected";
%>
		<select name="tipocalid<%=i%>" class="input input-small">
	  		<option value="I" <%=opc1%>><spring:message code="portal.maestro.evaluar.Insc"/></option> 	<!-- ENROLLED -->
	  		<option value="4" <%=opc4%>><spring:message code="portal.maestro.evaluar.Ra"/></option> 	<!-- INC. GRADE -->
	  		<option value="6" <%=opc6%>><spring:message code="portal.maestro.evaluar.Cd"/></option> 	<!-- WIDTHDRAWAL -->
<%			if(parametros.getInstitucion().equals("Pacific Adventist University")){ %>
			<option value="7" <%=opc7%>>WF</option> 													<!-- WIDTHDRAWAL FAIL -->
			<option value="5" <%=opc5%>>RP</option> 													<!-- RESTRICTED PASS -->
			<option value="2" <%=opc2%>>FA</option> 													<!-- FAILED -->
<% 			} %>
		</select>
<%	}else{
		if (mapTipoCal.containsKey(ac.getTipoCalId())) out.print(mapTipoCal.get(ac.getTipoCalId()));
%>
			<input name="tipocalid<%=i%>" type="hidden" value="<%=ac.getTipoCalId()%>">
<%	}%>
		</td>
  	</tr>
<%	} %> <!-- Termina lista de alumnos -->
	
	<tr>
    	<td colspan="<%=lisEvaluaciones.size()+11%>" style="text-align:center;"> 
		<%	if (evaluacionId.equals("E")){	%>  
	    		<img src='flecha.gif'><a class="btn btn-primary" href="javascript:GrabarExtra()"><spring:message code="portal.maestro.evaluar.GrabarExtraordinarias"/></a><img src='flecha2.gif'>
		<%	}
			else if (evaluacionId.equals("T")){	%>
	    		<a class="btn btn-primary" href="javascript:GrabarTipoCal()"><spring:message code="portal.maestro.evaluar.GrabarEstado"/></a>
		<%	}
			else if ( (!evaluacionId.equals("0")) && (sEstado.equals("1")||sEstado.equals("2")) ){	%>  	    
				<img src='flecha.gif'><a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="portal.maestro.evaluar.GrabarNotas"/></a><img src='flecha2.gif'>&nbsp;&nbsp;&nbsp;
				<a class="btn btn-primary" href="javascript:Borrar()"><spring:message code="portal.maestro.evaluar.BorrarNotas"/></a> 
		<%	} %>
        	<%-- <input type="Hidden" name="Contador" value="<%=lisAlumnos.size()%>"> --%>
			<input type="Hidden" name="numExtras" value="<%=numExtras%>">				
		</td>
  	</tr>  	
	</table>
</form>
</div>
<script type="text/javascript">
	if(document.getElementById("<%=enfocar %>"))
		document.getElementById("<%=enfocar %>").focus();
</script>
<%			
	TipocalU		= null;
%>
<!-- fin de estructura -->