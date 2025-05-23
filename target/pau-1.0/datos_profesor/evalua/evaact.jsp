<%@ include file= "../../con_enoc.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="AlumEvaluacion" scope="page" class="aca.kardex.KrdxAlumnoEval"/>
<jsp:useBean id="Materia" scope="page" class="aca.carga.CargaGrupo"/>
<jsp:useBean id="mapaCurso" scope="page" class="aca.plan.MapaCurso"/>
<jsp:useBean id="MapaCursoU" scope="page" class="aca.plan.CursoUtil"/>
<jsp:useBean id="cursoU" scope="page" class="aca.vista.CargaAcaUtil"/>
<jsp:useBean id="cEvaU" scope="page" class="aca.carga.CargaGrupoEvaluacionUtil"/>
<jsp:useBean id="log" scope="page" class="aca.log.LogOperacion"/>
<jsp:useBean id="logU" scope="page" class="aca.log.LogOperacionUtil"/>
<jsp:useBean id="acu" scope="page" class="aca.kardex.ActualUtil"/>
<jsp:useBean id="cgau" scope="page" class="aca.carga.CargaGrupoActividadUtil"/>
<jsp:useBean id="AlumActividad" scope="page" class="aca.kardex.KrdxAlumnoActiv"/>
<jsp:useBean id="kaau" scope="page" class="aca.kardex.KrdxAlumnoActivUtil"/>
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
				alert("Hay notas que superan el valor de la evaluacion. No se puede grabar."); 
			}else{
				document.frmevaluacion.Accion.value="2";
				document.frmevaluacion.submit();			
			}			
		}else{
			alert("Complete el formulario ..! ");
		}
	}
		
	function Borrar( ){
		if(document.frmevaluacion.ActividadId.value!=""){
			if(confirm("Estas seguro de eliminar las notas!")==true){
	  			document.frmevaluacion.Accion.value="4";
				document.frmevaluacion.submit();
			}			
		}else{
			alert("Escriba la Clave !");
			document.frmevaluacion.EvaluacionId.focus(); 
	  	}
	}
	
</script>

<%
 	String sCodigoPersonal	= (String) session.getAttribute("codigoPersonal");
	String sCursoCargaId	= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
	String sMaestro 		= request.getParameter("Maestro");
	String sMateria 		= request.getParameter("Materia");	
	String sEvaluacionId 	= request.getParameter("EvaluacionId")== null?"0":request.getParameter("EvaluacionId");
	String sActividadId		= request.getParameter("ActividadId")==null?"0":request.getParameter("ActividadId");
	String sAccion 			= request.getParameter("Accion")==null?"1":request.getParameter("Accion");
	int nAccion				= Integer.parseInt(sAccion);
	String sNumExtras 		= request.getParameter("numExtras")==null?"0":request.getParameter("numExtras");	
	int numExtras 			= Integer.parseInt(sNumExtras);	
		
	String sEstado 			= "";
	
	Materia.mapeaRegId(conEnoc, sCursoCargaId);
	sEstado = Materia.getEstado();
	
	String sNota			= "";
	String sPromedio		= "";
	int nPromedio 			= 0;
		
	String sResultado		= "";
	String opc1="", opc4="",opc5="",opc6="";
	int j=0, i=0;
	int nContador 			= 0;	
	String sContador 		= "";
	String sMatricula		= "";
	
	aca.util.Fecha f 		= new aca.util.Fecha();
	String sFecha 			= f.getFecha("1");
		
	aca.carga.CargaGrupoUtil grupoU 	= new aca.carga.CargaGrupoUtil(); // Información del grupo
	aca.alumno.AlumUtil alumnoU	= new aca.alumno.AlumUtil(); // Informacion del alumno
	aca.kardex.EvaluacionUtil krdxEvaluacionUtil	= new aca.kardex.EvaluacionUtil(); // Informacion de notas del alumno
	aca.kardex.ActividadUtil krdxActividadUtil		= new aca.kardex.ActividadUtil();
	aca.kardex.KrdxCursoAct kardex	= new aca.kardex.KrdxCursoAct(); // Informacion de la materia del alumno
	aca.catalogo.TipoCalUtil TipocalU = new aca.catalogo.TipoCalUtil(); // Catalogo de calificaciones.

	// ArrayList que almacena la metodología de evaluacion de la Materia
	ArrayList lisActividad 	= new ArrayList();
	aca.carga.CargaGrupoActividadUtil actividadU	= new aca.carga.CargaGrupoActividadUtil();
	lisActividad 			= actividadU.getListCargaEvaluacion(conEnoc, sCursoCargaId, sEvaluacionId);
	//lisActividad 			= evaluacionU.getLista(conEnoc, sCursoCargaId, "ORDER BY SUBSTR(FECHA,7,4)||SUBSTR(FECHA,4,5)||SUBSTR(FECHA,1,2), EVALUACION_ID");
	//------------------------------------------------------Necesito llenar el listor de actividades para enlistarlas
	
	// ArrayList que almacena los Alumnos inscritos en la materia	
	ArrayList<aca.kardex.KrdxCursoAct> lisAlumnos 		= acu.getListCurso(conEnoc, sCursoCargaId, "ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
	
	AlumActividad.setCursoCargaId(sCursoCargaId);
	AlumActividad.setActividadId(request.getParameter("ActividadId"));
	// Operaciones a realizar en la pantalla

	switch (nAccion){
	
		case 1: { // Nuevo
			sResultado = "Formulario para ingresar las evaluaciones";
			break;
		}		
		case 2: { // Grabar Ordinario
			
			conEnoc.setAutoCommit(false);
		
			nAccion	=1;
			sContador = request.getParameter("Contador");
			nContador = Integer.parseInt(sContador);
			
			for (i=0; i<nContador; i++){
				AlumActividad.setCodigoPersonal(request.getParameter("CodigoPersonal"+i));
				
				
				String nota = request.getParameter("Nota"+i);
				
				if(nota != null && !nota.equals(""))
					AlumActividad.setNota(nota);
				else
					AlumActividad.setNota("0");
				

/********* LOG ****/
				String datos = "CursoCargaId: "+request.getParameter("CursoCargaId")+", CodigoPersonal: "+request.getParameter("CodigoPersonal"+i) + ", ActividadId: "+request.getParameter("ActividadId")+", Nota: "+request.getParameter("Nota"+i);
				log.setDatos(datos);
				log.setIp(request.getRemoteAddr());
				log.setUsuario((String) session.getAttribute("codigoPersonal"));
				log.setTabla("krdx_alumno_activ");
				
				if (AlumActividad.existeReg(conEnoc) == true){
					
					
					if (AlumActividad.updateReg(conEnoc)){
						sResultado = "Modificado: "+AlumActividad.getCodigoPersonal();
						/******* LOG ******/
						log.setOperacion("update");
						logU.insertReg(conEnoc, log);
						conEnoc.commit();
					}else{
						sResultado = "No Cambió: "+AlumActividad.getCodigoPersonal();
					}
										
				}else{
					if (AlumActividad.insertReg(conEnoc)){
						sResultado = "Grabado: "+AlumActividad.getCodigoPersonal();
						/******* LOG ******/
						log.setOperacion("insert");
						logU.insertReg(conEnoc, log);						
						conEnoc.commit();
					}else{
						sResultado = "No Grabó: "+AlumActividad.getCodigoPersonal();
					}
					
				}							
			}
			sActividadId="0";
			
			conEnoc.commit();
			
			//System.out.println(kaau.setPromEstrID(conEnoc, sCursoCargaId));
			//System.out.println("kaau.setPromEstrID("+conEnoc+", "+sCursoCargaId+")");
			if(!kaau.setPromEstrID(conEnoc, sCursoCargaId, sEvaluacionId))
				if(!kaau.setPromEstrID(conEnoc, sCursoCargaId, sEvaluacionId))
					if(!kaau.setPromEstrID(conEnoc, sCursoCargaId, sEvaluacionId))
						sResultado = "Grabó pero no pudo ACTUALIZAR las Evaluaciones";
			
			conEnoc.setAutoCommit(true);
			break;
		}		
		case 4: { // Borrar
			conEnoc.setAutoCommit(false);
		
			sContador = request.getParameter("Contador");
			nContador = Integer.parseInt(sContador);
			for (i=0; i<nContador; i++){

/********* LOG ****/
				String datos = "CursoCargaId: "+request.getParameter("CursoCargaId")+", CodigoPersonal: "+request.getParameter("CodigoPersonal"+i) + ", ActividadId: "+request.getParameter("EvaluacionId")+", Nota: "+request.getParameter("Nota"+i);
				log.setDatos(datos);
				log.setIp(request.getRemoteAddr());
				log.setUsuario((String) session.getAttribute("codigoPersonal"));
				log.setTabla("krdx_alumno_activ");

				AlumActividad.setCodigoPersonal(request.getParameter("CodigoPersonal"+i));
				AlumActividad.setNota(request.getParameter("Nota"+i));
				if (AlumActividad.existeReg(conEnoc) == true){
					if (AlumActividad.deleteReg(conEnoc)){
						sResultado = "Borrado: "+AlumActividad.getCodigoPersonal();
						/******* LOG ******/
						log.setOperacion("delete");
						logU.insertReg(conEnoc, log);
						conEnoc.commit();
					}else{
						sResultado = "No Borró: "+AlumActividad.getCodigoPersonal();
				}					
				}else{
					sResultado = "No existe: "+AlumActividad.getCodigoPersonal();
				}
			}
			if(!kaau.setPromEstrID(conEnoc, sCursoCargaId, sEvaluacionId))
				if(!kaau.setPromEstrID(conEnoc, sCursoCargaId, sEvaluacionId))
					if(!kaau.setPromEstrID(conEnoc, sCursoCargaId, sEvaluacionId))
						sResultado = "Grabó pero no pudo ACTUALIZAR las Evaluaciones";
			conEnoc.setAutoCommit(true);
			break;
		}		
	}
	
 	mapaCurso = MapaCursoU.mapeaRegId(conEnoc, aca.carga.CargaGrupoCursoUtil.cursoIdOrigen(conEnoc,sCursoCargaId));
 	
%>
<div class="container-fluid">
<h3>Materia: <%=sMateria%> <small class="text-muted fs-5">( <%=sCodigoPersonal%> - <%=sMaestro%> )</h3>
<div class="alert alert-info">
	<a href="evaluar?CursoCargaId=<%=sCursoCargaId%>&Maestro=<%=sMaestro%>&Materia=<%=sMateria%>&EvaluacionId=0" class="btn btn-primary"> Regresar</a>
</div>
<form action="evaact?EvaluacionId=<%=sEvaluacionId%>&NombreEval=<%=request.getParameter("NombreEval")%>" method="post" name="frmevaluacion" target="_self">
<input type="hidden" name="Accion">
<input type="hidden" name="CursoCargaId" value="<%=sCursoCargaId%>">
<input type="hidden" name="Maestro" value="<%=sMaestro%>">
<input type="hidden" name="Materia" value="<%=sMateria%>">
<input type="hidden" name="NoCerrar" value='0'>
<input type="Hidden" name="ActividadId" value="<%=sActividadId%>">

<table style="width:100%"  cellspacing="1" cellpadding="1">
  <tr>
    <td>
    	<strong>Metodologia:</strong> <%=request.getParameter("NombreEval")%></font> - <em>Nota Aprobatoria: <%=mapaCurso.getNotaAprobatoria()%></em>	
    </td>
  </tr>
</table>
<table style="width:100%; margin:0 auto;" class="table table-fontsmall table-condensed table-bordered">
  <tr>
    <th width="7%"><spring:message code="aca.Numero"/></th>
    <th colspan="2">Actividad y Descripción</th>
	<th width="10%"><spring:message code="aca.Fecha"/></th>
	<th width="8%">Valor</th>
	<th width="13%"><spring:message code="aca.Tipo"/></th>
	<th width="10%"><spring:message code="aca.Estado"/></th>
  </tr>
<%	
	sEstado = grupoU.getEstado(conEnoc, sCursoCargaId);
	for (i=0; i< lisActividad.size(); i++){
		aca.carga.CargaGrupoActividad cga = (aca.carga.CargaGrupoActividad) lisActividad.get(i);
%>  
  <tr>
    <td width="7%" height="15">&nbsp;<%=i+1%>.-</td>
    <td colspan="2"><%=cga.getNombre()%></td>
	<td width="10%" align="center"><%=cga.getFecha()%></td>
	<td width="8%" align="center"><%=cga.getValor()%></td>
	<td width="9%" align="center">
	<% 
			out.println("Porcentaje");
	%>	
	</td>
	  <td width="10%" align="center">
<%	  if (sEstado.equals("2")){%>
		  	<a class="btn btn-primary btn-sm" href="evaact?CursoCargaId=<%=sCursoCargaId%>&Maestro=<%=sMaestro%>&Materia=<%=sMateria%>&EvaluacionId=<%=cga.getEvaluacionId()%>&ActividadId=<%=cga.getActividadId()%>&NombreEval=<%=request.getParameter("NombreEval")%>#GoEvaluar">
		  Evaluar</a>
<%	  }else if (sEstado.equals("3")){%>
		  <a class="btn btn-primary btn-sm" href="evaact?CursoCargaId=<%=sCursoCargaId%>&Maestro=<%=sMaestro%>&Materia=<%=sMateria%>&EvaluacionId=E&ActividadId=E&NombreEval=<%=request.getParameter("NombreEval")%>#GoEvaluar">
		  Extra</a>
<%	  }else if (sEstado.equals("4")){%>
		 <b>Cerrada!</b>
<%	  }else if (sEstado.equals("5")){ %>
		 <b>Entregada</b>
<%	  }%>

	  </td>
  </tr>
<%	}%>
</table>
<div class="alert alert-success" align="center"><font size="3"><b>
<%
	if (sEstado.equals("1")){
		out.println("M a t e r i a  &nbsp; C r e a d a");
	}else if(sEstado.equals("2")){
		out.println("E v a l u a c i ó n &nbsp; O r d i n a r i a");
	}else if (sEstado.equals("3")){
		out.println("E v a l u a c i ó n &nbsp; E x t r a o r d i n a r i a");
	}else if (sEstado.equals("4")){
		out.println("M a t e r i a &nbsp; C e r r a d a");
	}else if (sEstado.equals("5")){
		out.println("M a t e r i a &nbsp; C e r r a d a  &nbsp; Y &nbsp; E n t r e g a d a");
	}else{
		out.println("N o &nbsp; e x i s t e !");
	}

	int nE = cEvaU.getNumEstrategias(conEnoc,sCursoCargaId);
	int nEE = cEvaU.getNumEstrategiasEvaluadas(conEnoc,sCursoCargaId);
	int nAB = cEvaU.getNumAlumnosBaja(conEnoc, sCursoCargaId);	
	if (  nEE < nE && nAB < lisAlumnos.size() ){
%>		<script>document.frmevaluacion.NoCerrar.value ='1';</script>
<%	}
%>
	</b></font>
	</div>
<table style="width:100%; margin:0 auto;" class="table table-fontsmall table-condensed table-bordered">
  <tr>
    <th><spring:message code="aca.Numero"/></th>
    <th><spring:message code="aca.Codigo"/></th>
	<th><spring:message code="aca.NombreDelAlumno"/></th>
<%  for (j=0;j<lisActividad.size();j++){ %>
	<th width="25"><%=j+1%></th>
<%	}%>
	<th>Prom.</th>
  </tr>
  <input type = 'hidden' name = 'totalAlumnos' value ='<%=lisAlumnos.size()%>'>
<%  numExtras=0;
	boolean okvalida = false;
	String sBgcolor="";
	for (i=0; i<lisAlumnos.size(); i++){
		aca.kardex.KrdxCursoAct ac = (aca.kardex.KrdxCursoAct) lisAlumnos.get(i);	
		if ((i % 2) == 0 ) sBgcolor = sColor; else sBgcolor = "";
%>
  <tr <%=sBgcolor%>>
    <td align="center" height="15"><%=i+1%></td>
    <td align="center"><%=ac.getCodigoPersonal()%></td>	
    <td align="left">
    	&nbsp;<%=alumnoU.getNombre(conEnoc, ac.getCodigoPersonal(), "APELLIDO")%>
    </td>

<%
		for (j=0;j<lisActividad.size();j++){ 
			aca.carga.CargaGrupoActividad cga = (aca.carga.CargaGrupoActividad) lisActividad.get(j);
			sNota = krdxActividadUtil.getAlumnoEvaluacion(conEnoc,ac.getCodigoPersonal(),cga.getActividadId()).trim();
%>
	<td width="25" align="right">
	  <input type="Hidden" name='CodigoPersonal<%=i%>' value='<%=ac.getCodigoPersonal()%>'>
<%
			if (cga.getActividadId().equals(sActividadId)){
				if (!ac.getTipoCalId().equals("3") && !ac.getTitulo().equals("S")){  
					if (sNota.equals("-")) sNota="";
					if (!okvalida){
						okvalida=true;
						//<input name="evaluacionTipo" type="hidden" value="<%=cga.getTipo()">
%>
						<input name="evaluacionValor" type="hidden" value="<%=cga.getValor()%>">
<%					}
%>					
					<input id='ChkNota<%=i%>' name="Nota<%=i%>"  type="text" class="text" value="<%=sNota%>" size="3" maxlength="5">
<%				}else{%>
					<input id='ChkNota<%=i%>' type="Hidden" name="Nota<%=i%>" value="<%=sNota%>">
<%				}%>
				<input type="Hidden" name="NotaOld<%=i%>" value="<%=sNota%>">								
				
<%			}else{ 
				out.println(sNota);
			}
%>			        
	</td>
	
<%		}
		sPromedio = krdxEvaluacionUtil.getAlumnoEvaluacion(conEnoc, ac.getCursoCargaId(), ac.getCodigoPersonal(), sEvaluacionId);
		if (sPromedio==null||sPromedio.equals("-")) sPromedio="0";
		sPromedio = sPromedio.trim();
		nPromedio = Double.valueOf(sPromedio).intValue();
		sPromedio = String.valueOf(nPromedio);		
		//sPromedio = String.valueOf((long)Math.floor(Double.valueOf(sPromedio).doubleValue() + 0.5d));
		
		kardex.setCodigoPersonal(ac.getCodigoPersonal());
		kardex.setCursoCargaId(ac.getCursoCargaId());
%>
	<td align="center"><b><%=sPromedio%></b></td>
  </tr>
<%	} // termina for de lisAlumnos %>
	
  <tr bgcolor="#CCCCCC">
      <td colspan="24" align="center"> 
<%	if ( (!sActividadId.equals("0")) && (sEstado.equals("1")||sEstado.equals("2")) ){	%>  	    
		<img src='flecha.gif'><a href="javascript:Grabar()"><font size='3' color='#000000'><b>Grabar Notas</b></font></a><img src='flecha2.gif'>&nbsp;&nbsp;&nbsp;
		<a href="javascript:Borrar()"><font size='3'>Borrar Notas</font></a> 
<%	}%>		
        <input type="Hidden" name="Contador" value="<%=lisAlumnos.size()%>">
		<input type="Hidden" name="numExtras" value="<%=numExtras%>">				
	</td>
  </tr> 
  <tr bgcolor="#eeeeee"><td colspan="24" align="center"><%=sResultado%></td></tr>
</table>
</form>
</div>
<%
	lisActividad	= null;
	AlumEvaluacion	= null;
	actividadU	= null;
	//estrategiaU	= null;
	grupoU 		= null;
	lisAlumnos		= null;
	acu 			= null;
	krdxEvaluacionUtil= null;
	kardex			= null;
	alumnoU 		= null;
	TipocalU		= null;
%>
<!-- fin de estructura -->
<%@ include file= "../../cierra_enoc.jsp" %>