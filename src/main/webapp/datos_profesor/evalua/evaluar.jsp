<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.CargaGrupoEvaluacion"%>
<%@page import="aca.kardex.spring.KrdxCursoAct"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.catalogo.spring.CatEstrategia"%>

<script type="text/javascript">
			
	function Grabar(){
			var total = document.frmevaluacion.totalAlumnos.value;
			var tipo = document.frmevaluacion.evaluacionTipo.value;
			var nota, max, mess;
			if (tipo == "P" || tipo == "E") max = document.frmevaluacion.evaluacionValor.value;
			else max = 100;
			for (i=0;i<total;i++){
				nota = document.getElementById("ChkNota"+i).value;
				if (parseFloat(nota)>parseFloat(max)){
					 mess = 1;
				}
			}
			if (mess==1){
				alert("Hay notas que superan el valor de la evaluacion. No se puede grabar."); 
			}else{
				document.frmevaluacion.Accion.value="2";
				document.frmevaluacion.submit();
			}			
	}
	function GrabarExtra(){
		document.frmevaluacion.Accion.value="6";
		document.frmevaluacion.submit();			
	}	
	function GrabarTipoCal(){		
		console.log(document.frmevaluacion.tipocalid);
		document.frmevaluacion.Accion.value="9";
		document.frmevaluacion.submit();		
	}		
	function Borrar( ){
		if(document.frmevaluacion.EvaluacionId.value!=""){
			if(confirm("Estas seguro de eliminar las notas!")==true){
	  			document.frmevaluacion.Accion.value="4";
				document.frmevaluacion.submit();
			}			
		}else{
			alert("Escriba la Clave !");
			document.frmevaluacion.EvaluacionId.focus(); 
	  	}
	}
	function SimularCierre( ){
		if (document.frmevaluacion.NoCerrar.value=="0"){
			if(confirm("�Esta operaci�n coloca ceros en las evaluaciones que no han sido registradas, �Deseas realizarlo?")==true){
	  			document.frmevaluacion.Accion.value="3";
				document.frmevaluacion.submit();
			}			
		}else alert("No puede cerrar la materia porque no ha evaluado todas las estrategias.");
	}
	function CerrarMateria( ){
		if (document.frmevaluacion.NoCerrar.value=="0"){
			if(confirm("�Estas seguro que deseas cerrar la materia ordinaria?")==true){
	  			document.frmevaluacion.Accion.value="5";
				document.frmevaluacion.submit();
			}			
		}else alert("No puede cerrar la materia porque no ha evaluado todas las estrategias.");
	}
	function CerrarMateriaExtra( ){
		if(confirm("�Estas seguro que deseas cerrar la materia extraordinaria?")==true){
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

 	String sCodigoPersonal	= (String) session.getAttribute("codigoEmpleado");
	String cursoCargaId		= (String) request.getAttribute("cursoCargaId");
	String sMaestro 		= (String) request.getAttribute("sMaestro")==null?"-":(String) request.getAttribute("sMaestro");
	String sMateria 		= (String) request.getAttribute("sMateria");
	String cursoId			= (String) request.getAttribute("cursoId");
	String sEvaluacion 		= request.getAttribute("sEvaluacion")==null?"-":(String) request.getAttribute("sEvaluacion");
	
	String sNumExtras 		= (String) request.getAttribute("sNumExtras");	
	int numExtras 			= (Integer) request.getAttribute("intExtras");
	
	int diferida			= (Integer) request.getAttribute("diferida");	
	String sEstado 			= (String) request.getAttribute("sEstado")==null?"0":(String)request.getAttribute("sEstado");
	boolean tieneActividad 	= false;
	
	String sNota			= "";
	String sPromedio		= "";
	int nPromedio 			= 0;
	double sePromedio		= 0;	
	double sumaPuntos  		= 0;
	String eficiencia       = "";	
	String sContador 		= "";
	String sMatricula		= "";
	String sResultado		= "";
	
	String opc1="", opc4="",opc5="",opc6="";
	int j=0, i=0;
	int nContador = 0;
	
	aca.util.Fecha f 		= new aca.util.Fecha();
	String sFecha 			= f.getFecha("1");

	// ArrayList que almacena la metodolog�a de evaluacion de la Materia	
	List<CargaGrupoEvaluacion> lisEvaluacion 		= (List<CargaGrupoEvaluacion>) request.getAttribute("lisEvaluacion");
	// ArrayList que almacena los Alumnos inscritos en la materia	 
	List<KrdxCursoAct> lisAlumnos 					= (List<KrdxCursoAct>) request.getAttribute("lisAlumnos");
	
	// Map de saldos vencidos del alumno
	HashMap<String,String> mapAlumnos 				= (HashMap<String,String>) request.getAttribute("mapAlumnos");
	// Map que obtiene las notas de todos los alumnos en la materia
	HashMap<String,String> mapNotas 				= (HashMap<String,String>) request.getAttribute("mapNotas");
	// Map de saldos vencidos del alumno
	HashMap<String,String> mapDeuda 				= (HashMap<String,String>) request.getAttribute("mapDeuda");
	// Map de tipos de calificaci�n
	HashMap<String,String> mapTipoCal 				= (HashMap<String,String>) request.getAttribute("mapTipoCal");
	// Map de permisos
	HashMap<String,String> mapPermiso 				= (HashMap<String,String>) request.getAttribute("mapPermiso");	
	//Map de puntos
	HashMap<String, String> mapAlumnoPuntos 		= (HashMap<String,String>) request.getAttribute("mapAlumnoPuntos");	
	//Map de puntos Extras
	HashMap<String, String> mapAlumnoExtras 		= (HashMap<String,String>) request.getAttribute("mapAlumnoExtras");	
	//Map de puntos evaluados por alumno
	HashMap<String, String> mapAlumnoEvaluados 		= (HashMap<String,String>) request.getAttribute("mapAlumnoEvaluados");
	//Map de limite de extraordinario en el plan del alumno
	HashMap<String, String> mapExtraPlan 			= (HashMap<String,String>) request.getAttribute("mapExtraPlan");
	//Map de alumnos graduados para identificarlos
	HashMap<String, String> mapaAlumGraduados 		= (HashMap<String,String>) request.getAttribute("mapaAlumGraduados");
	HashMap<String, MapaCurso> mapaCursoEnCarga 	= (HashMap<String,MapaCurso>) request.getAttribute("mapaCursoEnCarga");
	HashMap<String, String> mapaActiviPorEval 		= (HashMap<String,String>) request.getAttribute("mapaActiviPorEval");
	MapaCurso mapaCurso 							= (MapaCurso) request.getAttribute("mapaCurso");
	HashMap<String, CatEstrategia> mapaEstrategias 	= (HashMap<String, CatEstrategia>) request.getAttribute("mapaEstrategias");
%>
<div class="container-fluid">
	<h2>Evaluate <small class="text-muted fs-6">( <%= sCodigoPersonal %> - <%= sMaestro %> - <%=sMateria%>)</small></h2>
	<form action="evaluar" method="post" name="frmevaluacion" target="_self">
	<input type="hidden" name="Accion">
	<input type="hidden" name="CursoCargaId" value="<%=cursoCargaId%>">
	<input type="hidden" name="Maestro" value="<%=sMaestro%>">
	<input type="hidden" name="Materia" value="<%=sMateria%>">
	<input type="hidden" name="CursoId" value="<%=cursoId%>">
	<input type="hidden" name="NoCerrar" value='0'>
	<input type="Hidden" name="EvaluacionId" value="<%=sEvaluacion%>">
		
	<div class="alert alert-info">
		<a class="btn btn-primary" href="cursos"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
<%		
	int nE 	= (Integer) request.getAttribute("nE");
	int nEE = (Integer) request.getAttribute("nEE");
	int nAB = (Integer) request.getAttribute("nAB");	
	if (  nEE < nE && nAB < lisAlumnos.size() ){
%>		<script>document.frmevaluacion.NoCerrar.value ='1';</script>
<%	}
		
	if (sEstado.equals("2") && sEvaluacion.equals("0")){
		out.print("<a class='btn btn-info' href=\"javascript:SimularCierre()\">SIMULATE CLOSING</a>");
		out.print("&nbsp; &nbsp;");
		out.println("<a class='btn btn-primary' href=\"javascript:CerrarMateria()\">CLOSE ORDINARY</a>");
	}else if(sEstado.equals("3") && sEvaluacion.equals("0")){
		out.println("<a class='btn btn-primary' href=\"javascript:CerrarMateriaExtra()\">CLOSE EXTRAORDINARY</a>");
%>
		<a class="btn btn-info" href="diferida?CursoCargaId=<%=cursoCargaId%>&CursoId=<%=cursoId%>&Materia=<%=sMateria%>&Maestro=<%=sMaestro%>&EvaluacionId=<%=sEvaluacion%>">Deferred Grades</a>
<% 			
	}else if(sEstado.equals("4")){
%>		
		<a class="btn btn-info" href="../../portales/maestro/cactames?cursoCargaId=<%=cursoCargaId%>&imp=1" target="_blank">Print Record</a>
		<a class="btn btn-info" href="correccion?CursoCargaId=<%=cursoCargaId%>&EvaluacionId=<%=sEvaluacion%>">Correct grades</a>
		<a class="btn btn-info" href="diferida?CursoCargaId=<%=cursoCargaId%>&CursoId=<%=cursoId%>&Materia=<%=sMateria%>&Maestro=<%=sMaestro%>&EvaluacionId=<%=sEvaluacion%>">Deferred Grades</a>
<%	} %>
<%	if(lisEvaluacion.size() == 0){%>
		<a class="btn btn-outline-success" href="evaluar?Crear=1&CursoCargaId=<%=cursoCargaId%>" >Eval. 100%</a>
<%	} %>
	</div>		
	<h3>Assessment scheme&nbsp; <small class="text-muted fs-6"> [ <%=cursoCargaId %> ] &nbsp; [ Passing grade <%=mapaCurso.getNotaAprobatoria()%>]</small></h3>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">   	 
  	<tr>
    	<th width="5%"><spring:message code="aca.Numero"/></th>
    	<th colspan="2" width="50%">Methodology and Description</th>
		<th width="5%"><spring:message code="aca.Fecha"/></th>
		<th width="10%" class="text-end">Weight</th>
		<th width="10%" class="text-center"><spring:message code="aca.Tipo"/></th>
		<th width="10%" class="text-center"><spring:message code="aca.Actividades"/></th>
		<th width="10%" class="text-center"><spring:message code="aca.Status"/></th>
  	</tr>
  	</thead>		 
<%	
	for (i=0; i< lisEvaluacion.size(); i++){
		CargaGrupoEvaluacion cge = lisEvaluacion.get(i);
		double puntos = Double.parseDouble(cge.getValor());
		sumaPuntos += puntos;
		
		String nombreEstrategia = "X";
		if (mapaEstrategias.containsKey(cge.getEstrategiaId())){
			nombreEstrategia = mapaEstrategias.get(cge.getEstrategiaId()).getNombreEstrategia();
		}
%>  
  	<tr>
	    <td height="15">&nbsp;<%=i+1%>.-</td>
	    <td colspan="2">
	    	<a href="transferir?ccid=<%=cursoCargaId %>&evid=<%=cge.getEvaluacionId() %>&vista=xAlumnoEstrategia&id=<%=cursoCargaId+"-"+cge.getEvaluacionId()%>&nomMateria=<%=sMateria%>&nomProfesor=<%=sMaestro%>&origen=Profesor"><%=nombreEstrategia%> - <% if (cge.getNombreEvaluacion()==null){ out.print(""); }else{ out.print(cge.getNombreEvaluacion());} %></a>
	    </td>
		<td align="center"><%=cge.getFecha()%></td>
		<td style="text-align:right"><%=getFormato.format(puntos)%></td>
		<td align="center">
 
<%				
				if (cge.getTipo().equals("%")){
					out.print("Percentage");
				}else if(cge.getTipo().equals("P")){
					out.print("Points");
				}else if(cge.getTipo().equals("E")){
					out.print("Extra Points");
				}
%>	
		</td>
		
<% 	
		if (mapaActiviPorEval.containsKey(cge.getEvaluacionId())){			
			String actividad = mapaActiviPorEval.get(cge.getEvaluacionId());			
			tieneActividad 	= true;
%>
			 <td style="text-align:right;"><%=actividad%></td>
<% 
		}else{%>
			<td>&nbsp;</td>
<% 
		}
%>		
		<td align="center">
<%		if (sEstado.equals("2")){
	  		if (tieneActividad){%>
			<a class="btn btn-primary" href="evaact?CursoCargaId=<%=cursoCargaId%>&Maestro=<%=sMaestro%>&Materia=<%=sMateria%>&EvaluacionId=<%=cge.getEvaluacionId()%>&NombreEval=<%=nombreEstrategia%> - <% if (cge.getNombreEvaluacion()==null){ out.print(""); }else{ out.print(cge.getNombreEvaluacion());} %>">
			Grade</a>
<% 			}else{ %>
  			<a class="btn btn-primary" href="evaluar?CursoCargaId=<%=cursoCargaId%>&Maestro=<%=sMaestro%>&Materia=<%=sMateria%>&EvaluacionId=<%=cge.getEvaluacionId()%>">Grade</a>
<%			} 
		}else if (sEstado.equals("3")){%>
			<a class="btn btn-primary" href="evaluar?CursoCargaId=<%=cursoCargaId%>&Maestro=<%=sMaestro %>&Materia=<%=sMateria%>&CursoId=<%=cursoId%>&EvaluacionId=E"><spring:message code="aca.Extra"/></a>
<%  	}else if (sEstado.equals("4")){%>
	 		<font color="#AE2113"><b><spring:message code="aca.Cerrada"/></b></font>
<% 		}else if (sEstado.equals("5")){ %>
 		<font color="green"><b>Delivered</b></font>
<%  	} %>
		</td>
  	</tr>
<%	}
	String colorPuntos = "<span class='badge bg-warning'>"+getFormato.format(sumaPuntos)+"</span>";
	if (sumaPuntos==100) colorPuntos = "<span class='badge bg-success'>"+getFormato.format(sumaPuntos)+"</span>";	
%>
	<tr">
		<th colspan="4" style="text-align:right; font-size:9pt;"><b>Total Points:<b></th>
		<th style="text-align:right; font-size:9pt;"><b><%=colorPuntos%></b></th>
		<th colspan="3">&nbsp;</th>		
	</tr>    	    
	</table>
	<div align="center"><font size="3"><b>
<%
	if (sEstado.equals("1")){
		out.println("C r e a t e d  &nbsp; S u b j e c t");
	}else if(sEstado.equals("2")){
		out.println("O r d i n a r y &nbsp; G r a d i n g");
	}else if (sEstado.equals("3")){
		out.println("E x t r a o r d i n a r y &nbsp; G r a d i n g");
	}else if (sEstado.equals("4")){
		out.println("C l o s e d &nbsp; S u b j e c t");
	}else if (sEstado.equals("5")){
		out.println("D e l i v e r e d &nbsp; S u b j e c t");
	}else{
		out.println("N o &nbsp; S u b j e c t");
	}
%>	
	</b></font></div>
	<br>
	<h3>Student List<small class="text-muted fs-6"> ( <i class="fas fa-graduation-cap"></i></span> = Graduated Student )</small></h3>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">		
  	<tr>
    	<th ><spring:message code="aca.Numero"/></th>
	    <th class="text-center">Student ID</th>
		<th ><spring:message code="aca.Nombre"/></th>
		<%	for (j=0;j<lisEvaluacion.size();j++){ %>
				<th width="25" class="right"><%=j+1%></th>
		<%	} %>
		<th class="text-end" style="font-size:13px;">&nbsp;</th>
		<th class="text-end"><b>S.E.</b></th>
		<th class="text-end"><spring:message code="aca.Total"/></th>
		<th class="text-end">Extra.</th>
		<th class="text-center">Status 
		<%	if (sEstado.equals("2")){ %>
				<a href="evaluar?CursoCargaId=<%=cursoCargaId%>&Maestro=<%=sMaestro%>&Materia=<%=sMateria%>&EvaluacionId=T&CursoId=<%=cursoId%>"><i class="far fa-edit"></i></a>
		<%	}%>
		</th>
  	</tr>
  	</thead>
  	<input type='hidden' name='totalAlumnos' value='<%=lisAlumnos.size()%>'>
<%  numExtras=0;
	boolean okvalida = false;
	String enfocar = "";
	String nombreAlumno = "";
	double saldoVencido = 0;
	String graduado = "";
	
	
	for(i=0; i<lisAlumnos.size(); i++){
		KrdxCursoAct ac = lisAlumnos.get(i);	
		
		if(mapAlumnos.containsKey(ac.getCodigoPersonal())){
			nombreAlumno = mapAlumnos.get( ac.getCodigoPersonal() );
		}else{
			nombreAlumno = "-";
		}
		
		// Obtiene el saldo vencido del alumno
		if (mapDeuda.containsKey(ac.getCodigoPersonal())){
			saldoVencido = Double.parseDouble(mapDeuda.get( ac.getCodigoPersonal() ) );
		}else{
			saldoVencido = 0;
		}
		
		// Verifica si el alumno tiene un permiso financiero
		boolean permiso = false;
		if (mapPermiso.containsKey(ac.getCodigoPersonal())) permiso = true;
		
		String textoDeuda = (saldoVencido < -100 && !permiso)?" title='Saldo Vencido: $"+getFormato.format(saldoVencido)+"'":"";
		String saldoColor = (saldoVencido < -100 && !permiso)?" style='color: #AE2113;'":"";
		
		if(mapaAlumGraduados.containsKey(ac.getCodigoPersonal())){
			graduado = "<i class='fas fa-graduation-cap'></i></span>";
		}		  
%>		
  		<tr>
   			<td<%=saldoColor%> align="center" height="15"><%=i+1%></td>
    		<td<%=saldoColor%> align="center" <%=textoDeuda%> ><%=ac.getCodigoPersonal()%></td>
    		<td<%=saldoColor%> align="left">
      		<%	if(sEstado.equals("2")){%>
    				<a href='../../portales/alumno/transferir?matricula=<%=ac.getCodigoPersonal()%>&id=<%=cursoCargaId%>&vista=MandarAlumno&origen=Profesor&sCursoCargaId=<%=cursoCargaId%>&al=<%=ac.getCodigoPersonal()%>&alumno=<%=nombreAlumno%>&nomProfesor=<%=sMaestro%>&nomMateria=<%=sMateria%>'>
   					<img align=absmiddle alt="Enviar Archivo" src="/academico/imagenes/upload3.gif" ></a>
			<%	}%>    			
    			<%=graduado%>&nbsp;<%=nombreAlumno%>
    		</td>
    		
		<%	
			// Determina el estilo del texto(rojo:deudores, negro:sin deuda) y la alineaci�n en todo el rengl�n. 
			String estiloNota = "";
			if (saldoVencido<-100 && !permiso) estiloNota = "style='color:#AE2113; text-align:right;'"; else estiloNota = "style='text-align:right;'";
			
			for (j=0;j<lisEvaluacion.size();j++){
				CargaGrupoEvaluacion cge = lisEvaluacion.get(j);
				
				if (mapNotas.containsKey( ac.getCodigoPersonal()+ac.getCursoCargaId()+cge.getEvaluacionId())){
					sNota = mapNotas.get( ac.getCodigoPersonal()+ac.getCursoCargaId()+cge.getEvaluacionId() );
				}else{
					sNota = "-";
				}				
				
		%>
				<td <%=estiloNota%> width="25">
	  				<input type="hidden" name="CodigoPersonal<%=i%>" value="<%=ac.getCodigoPersonal()%>">
				<%	if(enfocar.equals(""))
						enfocar = "ChkNota"+i;
						if (cge.getEvaluacionId().equals(sEvaluacion)){
							if (!ac.getTipoCalId().equals("3") && !ac.getTitulo().equals("S")){
								if (sNota.equals("-")) sNota="";
								if (!okvalida){
									okvalida=true; %>
									<input name="evaluacionTipo" type="hidden" value="<%=cge.getTipo()%>">
									<input name="evaluacionValor" type="hidden" value="<%=cge.getValor()%>">
							<%	} %>
								<input id='ChkNota<%=i%>' name="Nota<%=i%>" id="Nota<%=i%>" tabindex="<%=i+1%>" type="text" class="input input-mini" value="<%=sNota%>" size="3" maxlength="5">
						<%	}
							else{ %>
								<input id='ChkNota<%=i%>' type="Hidden" name="Nota<%=i%>" value="<%=sNota%>">
						<%	} %>
							<input type="Hidden" name="NotaOld<%=i%>" value="<%=sNota%>">						
					<%	}
						else{ 
							out.println(sNota);
						} %>
				</td>
		<%	}
			if(sEstado.equals("3") || sEstado.equals("4") || sEstado.equals("5")){				
				String nota = ac.getNota();
				if(nota==null){
					nota="0";
				}
				sPromedio = nota.trim();
			}
			else{				
				// Trae los puntos ordinarios del alumno en la materia
				double puntos = 0;
				if( mapAlumnoPuntos.containsKey(ac.getCodigoPersonal()+ac.getCursoCargaId()) ){
					puntos = Double.parseDouble( mapAlumnoPuntos.get(ac.getCodigoPersonal()+ac.getCursoCargaId()) );
				}
				// Trae los puntos extras del alumno en la materia
				double puntosExtra = 0;
				if( mapAlumnoExtras.containsKey(ac.getCodigoPersonal()+ac.getCursoCargaId()) ){
					puntosExtra = Double.parseDouble( mapAlumnoExtras.get(ac.getCodigoPersonal()+ac.getCursoCargaId()) );
				}
				// Total de puntos del alumno en la materia
				sPromedio = String.valueOf((int)Math.round(puntos+puntosExtra)).trim();				
			}
			
			// Establece en 0 el total de puntos
			if(sPromedio==null) sPromedio="0";
			
			// Obtiene el valor en entero del promedio
			nPromedio = Integer.parseInt(sPromedio);
			//sPromedio = String.valueOf(nPromedio);
			//sPromedio = String.valueOf((long)Math.floor(Double.valueOf(sPromedio).doubleValue() + 0.5d));			
			
			double evaluados = 0;
			if (mapAlumnoEvaluados.containsKey(ac.getCodigoPersonal())){
				evaluados = Double.parseDouble( mapAlumnoEvaluados.get(ac.getCodigoPersonal()));				
			}			
			
			double rendimiento = 0; 
			if (evaluados>0) rendimiento = Math.round(nPromedio/evaluados*100);
			
			eficiencia = String.valueOf((int)rendimiento);			
			sePromedio= rendimiento/10;
			
			// Traer los datos del curso
			MapaCurso curso = new MapaCurso();
			
			if(mapaCursoEnCarga.containsKey(ac.getCursoId())){
				curso = mapaCursoEnCarga.get(ac.getCursoId());
			}
		%>
			<td <%=estiloNota%> title="Puntos=<%=sPromedio%> Evaluados:<%=evaluados%>"><%=eficiencia%></td>
			<td <%=estiloNota%>><b><%=sePromedio%></b></td>
			<td <%=estiloNota%>>
				<span class="badge bg-dark"><%=sPromedio%></span>
			</td>
			<td <%=estiloNota%>>
				<b>
				<%  numExtras++; 
				
					// Determina el valor del limite permitido para evalua el extraordinario en un plan de estudios
					int limiteExtra = 60;
					if (mapExtraPlan.containsKey(ac.getCodigoPersonal())){
						limiteExtra = Integer.parseInt( mapExtraPlan.get(ac.getCodigoPersonal()) );
						//System.out.println("Limite Extra:"+limiteExtra);
					}
					//System.out.println("Datos:"+sEvaluacion+":"+nPromedio+":"+ac.getTipoCalId().equals("6")+":"+mapaCurso.getNotaAprobatoria()+":"+limiteExtra);	
					if(!ac.getTipoCalId().equals("6") && sEvaluacion.equals("E") && 
						((nPromedio >= limiteExtra && nPromedio < Integer.parseInt(curso.getNotaAprobatoria())) ||
						(ac.getTipoCalId().equals("4") && nPromedio >= limiteExtra)) && !ac.getTipoCalId().equals("6")){
					%>	  
			  			<input name="NotaExtra<%=numExtras%>" type="text" class="text" value="<%=ac.getNotaExtra()%>" size="3" maxlength="5">
		 	  			<input type="Hidden" name='CodigoPersonalE<%=numExtras%>' value='<%=ac.getCodigoPersonal()%>'>
				<%  }
					else{ %>	  
					<%	if (ac.getNotaExtra()==null) out.print("&nbsp;");
						else if (ac.getNotaExtra().equals("0")) out.print("&nbsp;"); 
						else out.print(ac.getNotaExtra());%>
				<% 	} %>
				</b>
			</td>
			<td<%=(saldoVencido < -100 && !permiso)?" style=\"color: #AE2113;\"":"" %> align="center">
			<%	if(sEvaluacion.equals("T") && !ac.getTipoCalId().equals("3")){ 
					opc1 = " ";	opc4 = " ";	opc5 = " ";	opc6 = " ";
					if (ac.getTipoCalId().equals("4"))
						opc4 = "selected";
					else if (ac.getTipoCalId().equals("6"))
						opc6 = "selected";
					else
						opc1 = "selected"; %>
					<select name="tipocalid<%=i%>" class="input input-small">
					  	<option value="I" <%=opc1%>>Insc.</option>
					  	<option value="4" <%=opc4%>>RA</option>
					  	<option value="6" <%=opc6%>>CD</option>
					</select>
					<input type="hidden" name="CodigoPersonal<%=i%>" value="<%=ac.getCodigoPersonal()%>">
			<%	}
				else{
					if (mapTipoCal.containsKey(ac.getTipoCalId())) out.print(mapTipoCal.get(ac.getTipoCalId()));
				%>					
					<input name="tipocalid<%=i%>" type="hidden" value="<%=ac.getTipoCalId()%>">
			<%	} %>
			</td>
  		</tr>
<%	} // termina for de lisAlumnos %>
	
  	<tr>
      	<td colspan="24" style="text-align:center;"> 
		<%	if (sEvaluacion.equals("E")){	%>  
	    		<img src='flecha.gif'><a class="btn btn-primary" href="javascript:GrabarExtra()">Grabar notas extraordinarias</a><img src='flecha2.gif'>
		<%	}else if (sEvaluacion.equals("T")){	%>
	    		<a class="btn btn-primary" href="javascript:GrabarTipoCal()">Grabar Estado</a>
		<%	}else if ( (!sEvaluacion.equals("0")) && (sEstado.equals("1")||sEstado.equals("2")) ){	%>  	    
				<img src='flecha.gif'><a class="btn btn-primary" href="javascript:Grabar()">Grabar notas</a><img src='flecha2.gif'>&nbsp;&nbsp;&nbsp;
				<a class="btn btn-primary" href="javascript:Borrar()">Borrar notas</a> 
		<%	} %>
       		<input type="Hidden" name="Contador" value="<%=lisAlumnos.size()%>">
			<input type="Hidden" name="numExtras" value="<%=numExtras%>">
		</td>
  	</tr> 
  	<tr><td colspan="24" align="center"><%=sResultado%></td></tr>
	</table>
	</form>
	<script type="text/javascript">
		if(document.getElementById("<%=enfocar %>"))
			document.getElementById("<%=enfocar %>").focus();
	</script>
</div>	
<!-- fin de estructura -->