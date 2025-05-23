<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.carga.spring.CargaGrupo"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>
<%@ page import= "aca.carga.spring.CargaGrupoCurso"%>
<%@ page import= "aca.kardex.spring.KrdxCursoAct"%>
<%@ page import= "aca.kardex.spring.KrdxAlumnoEval"%>
<%@ page import= "aca.kardex.spring.KrdxAlumnoActiv"%>
<%@ page import= "aca.vista.spring.AlumnoEficiencia"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="AlumEvaluacion" scope="page" class="aca.kardex.KrdxAlumnoEval"/>
<jsp:useBean id="AlumActividad" scope="page" class="aca.kardex.KrdxAlumnoActiv"/>

<%	
	java.text.DecimalFormat getFormato= new java.text.DecimalFormat("###,##0.00;(###,##0.00)");

 	String sCodigoPersonal	= (String) session.getAttribute("codigoPersonal");
 	String sCargaId 		= (String) session.getAttribute("cargaId");
 	
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
	String evaluacionId 		= request.getParameter("EvaluacionId")==null?"0":request.getParameter("EvaluacionId");
	
	String sEstado 			= cargaGrupo.getEstado();
	String sNota			= "";
	String sPromedio		= "";
	String promedioOld 		= "0"; 
	int nPromedio 			= 0;
	double sePromedio		= 0;
	double sumaPuntos  		= 0;
	String eficiencia       = "";
	
	String opc1="", opc4="",opc5="",opc6="";
	int j=0, i=0;
	int nContador = 0;
	String sNumExtras = request.getParameter("numExtras");
	if (sNumExtras==null) sNumExtras="0";
	int numExtras = Integer.parseInt(sNumExtras);
	String sContador 		= "";
	String sMatricula		= "";
	
	aca.util.Fecha f 		= new aca.util.Fecha();
	String sFecha 			= f.getFecha("1");	
	
	
	aca.kardex.EvaluacionUtil krdxEvaluacionUtil	= new aca.kardex.EvaluacionUtil(); // Informacion de notas del alumno
	aca.kardex.KrdxCursoAct kardex	= new aca.kardex.KrdxCursoAct(); // Informacion de la materia del alumno
	aca.catalogo.TipoCalUtil TipocalU = new aca.catalogo.TipoCalUtil(); // Catalogo de calificaciones.

	List<aca.carga.spring.CargaGrupoEvaluacion> lisEvaluaciones 	= (List<aca.carga.spring.CargaGrupoEvaluacion>) request.getAttribute("lisEvaluaciones");	
	List<aca.kardex.spring.KrdxCursoAct> lisAlumnos 				= (List<aca.kardex.spring.KrdxCursoAct>) request.getAttribute("lisAlumnos");	
	HashMap<String,String> mapaEstrategias							= (HashMap<String,String>) request.getAttribute("mapaEstrategias");
	
	// Map de alumnos
	HashMap<String,String> mapAlumnos 					= (HashMap<String,String>) request.getAttribute("mapaAlumnos");	
	// Map que obtiene las notas de todos los alumnos en la materia
	HashMap<String,String> mapNotas 					= (HashMap<String,String>) request.getAttribute("mapaNotas");	
	// Map de tipos de calificaci�n
	HashMap<String,String> mapTipoCal 					= (HashMap<String,String>) request.getAttribute("mapaTipoCal");	
	//Map de puntos
	HashMap<String, String> mapAlumnoPuntos 			= (HashMap<String,String>) request.getAttribute("mapaPuntos");	
	//Map de puntos Extras
	HashMap<String, String> mapAlumnoExtras 			= (HashMap<String,String>) request.getAttribute("mapaExtras");	
	//Map de puntos evaluados por alumno
	HashMap<String, String> mapAlumnoEvaluados 			= (HashMap<String,String>) request.getAttribute("mapaEvaluados");
	//Map de limite de extraordinario en el plan del alumno
	HashMap<String, String> mapExtraPlan 				= (HashMap<String,String>) request.getAttribute("mapaLimiteExtras");	
	HashMap<String, MapaCurso> mapaCursoMateria			= (HashMap<String,MapaCurso>) request.getAttribute("mapaCursoMateria");
	HashMap<String, String> mapaActividades				= (HashMap<String,String>) request.getAttribute("mapaActividades");
	HashMap<String, String> mapaPromedios				= (HashMap<String,String>) request.getAttribute("mapaPromedios");
	HashMap<String, String> mapaArchivosEval			= (HashMap<String,String>) request.getAttribute("mapaArchivosEval");
	HashMap<String, String> mapaArchivosAlum			= (HashMap<String,String>) request.getAttribute("mapaArchivosAlum");
	HashMap<String, AlumnoEficiencia> mapaEficiencia	= (HashMap<String,AlumnoEficiencia>) request.getAttribute("mapaEficiencia");
	HashMap<String, String> mapaLetrasDeNotas			= (HashMap<String,String>) request.getAttribute("mapaLetrasDeNotas");
	
	String estado 		= "";	
	String sColor		= "#eeeeee";
%>
<div class="container-fluid">
	<h2>Grading <small class="text-muted fs-6">( <%=sCodigoPersonal%>  - <%=maestroNombre%> - <%=materiaNombre%>)</small> </h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="cursos"><i class="fas fa-arrow-left"></i></a>
<%
		if (cargaGrupo.getEstado().equals("1")) estado = " Empty Subject ";
		else if (cargaGrupo.getEstado().equals("2")) estado = " Ordinary Grade ";
		else if (cargaGrupo.getEstado().equals("3")) estado = " Make-up Grade ";
		else if (cargaGrupo.getEstado().equals("4")) estado = " Delivered Subject ";
		else if (cargaGrupo.getEstado().equals("5")) estado = " Grading Record ";	
%>	
	</div>
	<table style="width:80%" class="table table-sm table-bordered">
		<tr>
	    	<td colspan="9"><h3>Assesment Scheme &nbsp; <small class="text-muted fs-5"> [ <%=cursoCargaId %> ] &nbsp; [ <%=materiaNombre %> ] &nbsp; [<%= estado %>] &nbsp; [ Passing grade: <%=mapaCurso.getNotaAprobatoria()%>]</small></h3></td>
	    </tr>	
		<tr>
	    	<th width="3%" style="text-align:left"><spring:message code="aca.Numero"/></th>
	    	<th width="41%" colspan="2" style="text-align:left">Strategy and Description</th>
	    	<th width="3%" style="text-align:left"><i class="fas file-alt" ></i></th>
			<th width="15%" style="text-align:left">Submit <spring:message code="aca.Fecha"/></th>
			<th width="8%" class="right">Weight</th>
			<th width="10%"><spring:message code="aca.Tipo"/></th>
			<th width="10%" class="right"><spring:message code="aca.Actividades"/></th>
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
	    	<td height="15">&nbsp;<%=row%>.-</td>
	    	<td colspan="2">    		
	    		<span class="badge bg-dark"><%=evaluacion.getEvaluacionId()%></span> <%=evaluacion.getNombreEvaluacion()%>   		    		
	    	</td>
	    	<td align="center">
	    		<span class="badge bg-info"><%=numArchEval%></span>    		
	    	</td>
			<td align="left"><%=evaluacion.getFecha()%></td>
			<td style="text-align:right"><%=getFormato.format(puntos)%></td>
			<td align="center">
<% 
			if (evaluacion.getTipo().equals("%")){
				out.println("Percentage");
			}else if(evaluacion.getTipo().equals("P")){
				out.println("Points");
			}else if(evaluacion.getTipo().equals("E")){
				out.println("Extra point");
			}
%>	
			</td>
			<td style="text-align:right;"><span class="badge bg-info"><%=actividad%></span></td>
		</tr>
<%		}%>
		<tr>
			<th colspan="5" style="text-align:right; font-size:9pt;"><b>Total points:<b></th>
			<th style="text-align:right; font-size:9pt;"><b><%=getFormato.format(sumaPuntos)%></b></th>
			<th colspan="3">&nbsp;</th>
	  	</tr>
	</table>	
    	
	<table style="width:100%"  class="table table-sm table-bordered">
		<tr>
			<td colspan="4"><h4>Student listing</h4></td>
			<td colspan="<%=lisEvaluaciones.size()%>" class="center"><h4>Assesments</h4></td>
			<td colspan="2" class="center"><h4>Progress and Marks</h4></td>
			<td class="center"><h4>Grade</h4></td>
			<td colspan="3" class="center"><h4>Obtained</h4></td>
			<td class="center"><h4>Status</h4></td>
		</tr>
	  	<tr>
	    	<th style="text-align:left"><spring:message code="aca.Numero"/></th>
	    	<th style="text-align:left">Student ID</th>
	    	<th style="text-align:left"><i class="fas file-alt" ></i></th>    	
			<th style="text-align:left"><spring:message code="aca.NombreDelAlumno"/></th>
<%		for (j=0;j<lisEvaluaciones.size();j++){ %>
			<th style="text-align:center" width="25"><%=j+1%></th>
<%		} %>
			<th style="text-align:right" title="Progress">Progress (%)</th>
			<th style="text-align:right" title="Efficiency">Efficiency</th>
			<th style="text-align:right">Current</th>
			<th style="text-align:right" title="Nota m�xima que puede lograr si obtiene 100 en todas la evaluaciones que faltan de registrar">Max.</th>
			<th style="text-align:right" title="Nota actual del alumno(puntos logrados)">GPA</th>
			<th style="text-align:right">Extra.</th>
			<th style="text-align:center">
		  		<input type = 'hidden' name = 'totalAlumnos' value ='<%=lisAlumnos.size()%>'>
			</th>
  		</tr>
<%  	numExtras=0;
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
			
			String textoDeuda = (saldoVencido < -100 && !permiso)?" title='Saldo Vencido: $"+getFormato.format(saldoVencido)+"'":"";
			String saldoColor = (saldoVencido < -100 && !permiso)?" style='color: #AE2113;'":"";
%>
		<tr>
	    	<td <%=saldoColor%> align="center"><%=i+1%></td>
	    	<td <%=saldoColor%> align="center" <%=textoDeuda%> ><%=ac.getCodigoPersonal()%></td>
	    	<td <%=saldoColor%> align="center">
	    		<span class="badge bg-info"><%=numArchAlum%></span>
	    	</td>
    		<td <%=saldoColor%> align="left"><%=nombreAlumno%></td>
<%		
		String estiloNota = "";
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
			<td width="25" class="text-right">
	  			<input type="Hidden" name='CodigoPersonal<%=i%>' value='<%=ac.getCodigoPersonal()%>'>
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
<%					}
%>					
					<input id='ChkNota<%=i%>' name="Nota<%=i%>" id="Nota<%=i%>"  type="text" class="text" tabindex="<%=i+1%>" value="<%=sNota%>" size="3" maxlength="5">
<%				}else{%>
					<input id='ChkNota<%=i%>' type="Hidden" name="Nota<%=i%>" value="<%=sNota%>">
<%				}%>
				<input type="Hidden" name="NotaOld<%=i%>" value="<%=sNota%>">	
<%			}else{ %>
				<span <%=estiloNota%>><%=sNota%></span>
<%			} %>       
			</td>
	
<%		}

		String letraProm = "";

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
			//System.out.println("Puntos:"+ac.getCodigoPersonal()+":"+puntos);
			// Trae los puntos extras del alumno en la materia
			double puntosExtra = 0;
			if( mapAlumnoExtras.containsKey(ac.getCodigoPersonal()) ){
				puntosExtra = Double.parseDouble( mapAlumnoExtras.get(ac.getCodigoPersonal()) );
			}
			//System.out.println("Puntos extras:"+ac.getCodigoPersonal()+":"+puntosExtra);
			// Total de puntos del alumno en la materia
			sPromedio = String.valueOf((int)Math.round(puntos+puntosExtra)).trim();			
		}
		if (sPromedio==null) sPromedio="0";

		if(mapaLetrasDeNotas.containsKey(sPromedio)){
			letraProm = mapaLetrasDeNotas.get(sPromedio);
		}else{
			letraProm = "X";			
		}
		
		// Obtiene el valor en entero del promedio
		nPromedio = Integer.parseInt(sPromedio); //sPromedio = String.valueOf((long)Math.floor(Double.valueOf(sPromedio).doubleValue() + 0.5d));
		double evaluados = 0;
		if (mapAlumnoEvaluados.containsKey(ac.getCodigoPersonal())){
			evaluados = Double.parseDouble( mapAlumnoEvaluados.get(ac.getCodigoPersonal()));
		}
		//if (ac.getCodigoPersonal().equals("0941428")&&evaluados>0) System.out.println("Promedio2 = "+(nPromedio/evaluados*100));
		double rendimiento = 0; 
		if (evaluados>0) rendimiento = Math.round(nPromedio/evaluados*100);
		
		eficiencia = String.valueOf((int)rendimiento);		
		sePromedio= rendimiento/10;
		
		double notaMaxima = 100-evaluados+Double.valueOf(sPromedio);
		
		// Colores de la nota m�xima
		String colorMaximo = "";
		if (notaMaxima<70){			
			colorMaximo = "class='badge bg-danger'";
		}else if (notaMaxima>=70 && notaMaxima<=80){
			colorMaximo = "class='badge bg-warning'";
		}else{
			colorMaximo = "class='badge bg-success'";			
		}		
		
		String notaAprobatoria = "70";
		if (mapaCursoMateria.containsKey(ac.getCursoId())){
			notaAprobatoria = mapaCursoMateria.get(ac.getCursoId()).getNotaAprobatoria();
		}		
%>
			<td class="text-right"><b><%=evaluados%></b></td>
			<td class="text-right" title="Points=<%=sPromedio%> Graded:<%=evaluados%>"><%=eficiencia %></td>
			<%-- <td class="text-right"><b><%=sePromedio%></b>aa</td> --%>
			<td class="text-right"><%=letraProm%></td>
			<td class="text-right" title="Max grade obtainable"><span <%=colorMaximo%>><%=getFormato.format(notaMaxima) %></span></td>
			<td class="text-right"><span class="badge bg-dark"><b><%=sPromedio%></b></span></td>
			<td class="text-center"><b>
<%
		numExtras++;

		// Determina el valor del limite permitido para evalua el extraordinario en un plan de estudios
		int limiteExtra = 60;
		if (mapExtraPlan.containsKey(ac.getCodigoPersonal())){
			limiteExtra = Integer.parseInt( mapExtraPlan.get(ac.getCodigoPersonal()) );		
		}
	  
		if (!ac.getTipoCalId().equals("6") && evaluacionId.equals("E") && 
			((nPromedio >= limiteExtra && nPromedio < Integer.parseInt(notaAprobatoria)) ||
			(ac.getTipoCalId().equals("4") && nPromedio >= limiteExtra))){  
%>	  
		  	<input name="NotaExtra<%=numExtras%>" type="text" class="text" value="<%=ac.getNotaExtra()%>" size="3" maxlength="5">
	 	  	<input type="Hidden" name='CodigoPersonalE<%=numExtras%>' value='<%=ac.getCodigoPersonal()%>'>
<%  	}else{
			if (ac.getNotaExtra()==null) out.print("&nbsp;");
			else if (ac.getNotaExtra().equals("0")) out.print("&nbsp;"); 
			else out.print(ac.getNotaExtra());
 		}
%>
			</b>
		</td>
		<td <%=(saldoVencido < -50 && !permiso) ? "style='color: #AE2113;'":"" %> class="text-center">
<%		if ( evaluacionId.equals("T") && !ac.getTipoCalId().equals("3")){ 
			opc1 = " ";	opc4 = " ";	opc5 = " ";	opc6 = " ";
			if (ac.getTipoCalId().equals("4"))
				opc4 = "selected";
			else if (ac.getTipoCalId().equals("5"))
				opc5 = "selected";
			else if (ac.getTipoCalId().equals("6"))
				opc6 = "selected";
			else
				opc1 = "selected";
%>
			<select name="tipocalid<%=i%>" class="input input-small">
		  		<option value="I" <%=opc1%>>Insc.</option>
		  		<option value="4" <%=opc4%>>RA</option>
		  		<option value="6" <%=opc6%>>CD</option>
			</select>
<%		}else{
		if (mapTipoCal.containsKey(ac.getTipoCalId())) out.print(mapTipoCal.get(ac.getTipoCalId()));
%>
			<input name="tipocalid<%=i%>" type="hidden" value="<%=ac.getTipoCalId()%>">
<%	}%>
			</td>
  		</tr>
<%	} // termina for de lisAlumnos 
%>
	
		<tr>
	    	<td colspan="<%=lisEvaluaciones.size()+11%>" style="text-align:center;"> 
	        	<input type="Hidden" name="Contador" value="<%=lisAlumnos.size()%>">
				<input type="Hidden" name="numExtras" value="<%=numExtras%>">				
			</td>
	  	</tr>  	
	</table>
</div>
<script type="text/javascript">
	if(document.getElementById("<%=enfocar %>"))
		document.getElementById("<%=enfocar %>").focus();
</script>
<%	
	AlumEvaluacion	= null;			
	krdxEvaluacionUtil= null;
	kardex			= null;	
	TipocalU		= null;
%>