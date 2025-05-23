<%@page import="aca.graduacion.AlumEgresoUtil"%>
<%@ include file= "../../con_enoc.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="AlumEvaluacion" scope="page" class="aca.kardex.KrdxAlumnoEval"/>
<jsp:useBean id="AlumActividad" scope="page" class="aca.kardex.KrdxAlumnoActiv"/>
<jsp:useBean id="Materia" scope="page" class="aca.carga.CargaGrupo"/>
<jsp:useBean id="MateriaU" scope="page" class="aca.carga.CargaGrupoUtil"/>
<jsp:useBean id="mapaCurso" scope="page" class="aca.plan.MapaCurso"/>
<jsp:useBean id="MapaCursoU" scope="page" class="aca.plan.CursoUtil"/>
<jsp:useBean id="cursoU" scope="page" class="aca.vista.CargaAcaUtil"/>
<jsp:useBean id="cEvaU" scope="page" class="aca.carga.CargaGrupoEvaluacionUtil"/>
<jsp:useBean id="log" scope="page" class="aca.log.LogOperacion"/>
<jsp:useBean id="logU" scope="page" class="aca.log.LogOperacionUtil"/>
<jsp:useBean id="cgau" scope="page" class="aca.carga.CargaGrupoActividadUtil"/>
<jsp:useBean id="alumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="evaU" scope="page" class="aca.carga.CargaGrupoEvaluacionUtil"/>
<jsp:useBean id="cge" scope="page" class="aca.carga.CargaGrupoEvaluacion"/>
<jsp:useBean id="cga" scope="page" class="aca.carga.CargaGrupoActividad"/>
<jsp:useBean id="egresoU" scope="page" class="aca.graduacion.AlumEgresoUtil"/>

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
			if(confirm("¿Esta operación coloca ceros en las evaluaciones que no han sido registradas, ¿Deseas realizarlo?")==true){
	  			document.frmevaluacion.Accion.value="3";
				document.frmevaluacion.submit();
			}			
		}else alert("No puede cerrar la materia porque no ha evaluado todas las estrategias.");
	}
	function CerrarMateria( ){
		if (document.frmevaluacion.NoCerrar.value=="0"){
			if(confirm("¿Estas seguro que deseas cerrar la materia ordinaria?")==true){
	  			document.frmevaluacion.Accion.value="5";
				document.frmevaluacion.submit();
			}			
		}else alert("No puede cerrar la materia porque no ha evaluado todas las estrategias.");
	}
	function CerrarMateriaExtra( ){
		if(confirm("¿Estas seguro que deseas cerrar la materia extraordinaria?")==true){
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
	String sCursoCargaId	= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
	String sMaestro 		= request.getParameter("Maestro");
	String sMateria 		= request.getParameter("Materia");
	String cursoId			= aca.carga.CargaGrupoCursoUtil.cursoIdOrigen(conEnoc, sCursoCargaId);
	String sEvaluacion 		= request.getParameter("EvaluacionId")==null?"0":request.getParameter("EvaluacionId");
	
	String sAccion 			= request.getParameter("Accion")==null?"1":request.getParameter("Accion");		
	int nAccion				= Integer.parseInt(sAccion);		
	String sNumExtras 		= request.getParameter("numExtras")==null?"0":request.getParameter("numExtras");	
	int numExtras 			= Integer.parseInt(sNumExtras);
	
	int diferida			= aca.kardex.KrdxCursoAct.numAlumDifMateria(conEnoc,sCursoCargaId, cursoId);	
	String sEstado 			= "";
	
	Materia.mapeaRegId(conEnoc, sCursoCargaId);
	
	sEstado = Materia.getEstado();	
	
	if (sEstado.equals("1")){
		// Si tiene alumnos
		if (aca.carga.CargaGrupoUtil.conAlumnos(conEnoc, sCursoCargaId)){			
			Materia.setEstado("2");
			MateriaU.updateReg(conEnoc, Materia);
		}
	}
	
	sEstado = Materia.getEstado();
	
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
		
	aca.carga.CargaGrupoUtil grupoU 	= new aca.carga.CargaGrupoUtil(); // Información del grupo
	aca.alumno.AlumUtil alumnoU	= new aca.alumno.AlumUtil(); // Informacion del alumno
	aca.kardex.EvaluacionUtil krdxEvaluacionUtil	= new aca.kardex.EvaluacionUtil(); // Informacion de notas del alumno
	aca.kardex.KrdxCursoAct kardex	= new aca.kardex.KrdxCursoAct(); // Informacion de la materia del alumno
	aca.catalogo.TipoCalUtil TipocalU = new aca.catalogo.TipoCalUtil(); // Catalogo de calificaciones.

	// ArrayList que almacena la metodología de evaluacion de la Materia	
	aca.carga.CargaGrupoEvaluacionUtil evaluacionU	= new aca.carga.CargaGrupoEvaluacionUtil();
	ArrayList<aca.carga.CargaGrupoEvaluacion> lisEvaluacion 	= evaluacionU.getLista(conEnoc, sCursoCargaId, " ORDER BY ENOC.CARGA_GRUPO_EVALUACION.FECHA, EVALUACION_ID");
	
	// ArrayList que almacena los Alumnos inscritos en la materia	 
	aca.kardex.ActualUtil acu	= new aca.kardex.ActualUtil();
	ArrayList<aca.kardex.KrdxCursoAct> lisAlumnos 	= acu.getListCurso(conEnoc, sCursoCargaId, " ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
	
	AlumEvaluacion.setCursoCargaId(sCursoCargaId);
	AlumEvaluacion.setEvaluacionId(request.getParameter("EvaluacionId"));
	// Operaciones a realizar en la pantalla

	switch (nAccion){
		case 2: { 
			// Grabar Ordinario
			try{				
				nAccion	=1;
				sContador 	= request.getParameter("Contador");
				nContador 	= Integer.parseInt(sContador);
				conEnoc.setAutoCommit(false);
				for (i=0; i<nContador; i++){
					AlumEvaluacion.setCodigoPersonal(request.getParameter("CodigoPersonal"+i));
					AlumEvaluacion.setNota(request.getParameter("Nota"+i));
	
					/********* LOG ****/
					String datos = "CursoCargaId: "+request.getParameter("CursoCargaId")+", CodigoPersonal: "+request.getParameter("CodigoPersonal"+i) + ", Evaluacion: "+request.getParameter("EvaluacionId")+", Nota: "+request.getParameter("Nota"+i);
					log.setDatos(datos);
					log.setIp(request.getRemoteAddr());
					log.setUsuario((String) session.getAttribute("codigoPersonal"));
					log.setTabla("krdx_alumno_eval");
					
					if (AlumEvaluacion.existeReg(conEnoc) == true){
						if (AlumEvaluacion.updateReg(conEnoc)){
							sResultado = "Modificado: "+AlumEvaluacion.getCodigoPersonal();
							/******* LOG ******/
							log.setOperacion("update");
							logU.insertReg(conEnoc, log);
							conEnoc.commit();
						}else{
							sResultado = "No Cambió: "+AlumEvaluacion.getCodigoPersonal();
						}					
					}else{
						if (AlumEvaluacion.insertReg(conEnoc)){
							sResultado = "Grabado: "+AlumEvaluacion.getCodigoPersonal();
							/******* LOG ******/
							log.setOperacion("insert");
							logU.insertReg(conEnoc, log);						
							conEnoc.commit();
						}else{
							sResultado = "No Grabó: "+AlumEvaluacion.getCodigoPersonal();
						}
					}
				}
			}catch(Exception ex){
				conEnoc.rollback();
				sResultado = "Error en el proceso";
			}finally{
				conEnoc.setAutoCommit(true);
			}	
		
			sEvaluacion="0";
			
			break;
		}
		
		// Simular el Cierre de la Materia (Coloca ceros a las evaluaciones y actividades que no han sido evaluadas)
		case 3:{			
			try{
				conEnoc.setAutoCommit(false);
				// Busca las actividades y coloca un cero en las que no fueron evaluadas en la e42...
				ArrayList lisAct = cgau.getListCurso(conEnoc, sCursoCargaId, "ORDER BY ACTIVIDAD_ID");
				for(i = 0; i < lisAlumnos.size(); i++){
					aca.kardex.KrdxCursoAct ac = (aca.kardex.KrdxCursoAct) lisAlumnos.get(i);
					
					for(j = 0; j < lisAct.size(); j++){
						cga = (aca.carga.CargaGrupoActividad) lisAct.get(j);
						
						AlumActividad.setCodigoPersonal(ac.getCodigoPersonal());						
						AlumActividad.setActividadId(cga.getActividadId());
						AlumActividad.setCursoCargaId(sCursoCargaId);
						if(!AlumActividad.existeReg(conEnoc)){
							AlumActividad.setNota("0");
							AlumActividad.setActividadE42("0");
							if (AlumActividad.insertReg(conEnoc)){
								conEnoc.commit();
								//System.out.println("Grabe:"+i+":"+AlumActividad.getCodigoPersonal()+":"+AlumActividad.getActividadId());
							}else{
								System.out.println("Error al grabar la actividad");
							}
						}else{
							
						}
					}
				}
				
				// Busca las evaluaciones y coloca un cero en las que no fueron evaluadas en la e42...
				ArrayList lisEv = evaU.getLista(conEnoc, sCursoCargaId, "ORDER BY EVALUACION_ID");
				for(i = 0; i < lisAlumnos.size(); i++){
					aca.kardex.KrdxCursoAct ac = (aca.kardex.KrdxCursoAct) lisAlumnos.get(i);
					for(j = 0; j < lisEv.size(); j++){
						cge = (aca.carga.CargaGrupoEvaluacion) lisEv.get(j);
						AlumEvaluacion.setCodigoPersonal(ac.getCodigoPersonal());
						AlumEvaluacion.setCursoCargaId(sCursoCargaId);
						AlumEvaluacion.setEvaluacionId(cge.getEvaluacionId());
						if(!AlumEvaluacion.existeReg(conEnoc)){
							AlumEvaluacion.setNota("0");
							AlumEvaluacion.setEvaluacionE42("0");
							if (AlumEvaluacion.insertReg(conEnoc)){
								conEnoc.commit();
							}else{
								System.out.println("Error al grabar las evaluaciones");
							}
						}else{
							
						}
					}
				}				
			}catch(Exception ex){
				conEnoc.rollback();
				sResultado = "Error en el proceso";
			}finally{
				conEnoc.setAutoCommit(true);
			}			
			break;
		} // fin de opcion
		
		case 4: { // Borrar

			sContador = request.getParameter("Contador");
			nContador = Integer.parseInt(sContador);
			conEnoc.setAutoCommit(false);
			for (i=0; i<nContador; i++){

/********* LOG ****/
				String datos = "CursoCargaId: "+request.getParameter("CursoCargaId")+", CodigoPersonal: "+request.getParameter("CodigoPersonal"+i) + ", Evaluacion: "+request.getParameter("EvaluacionId")+", Nota: "+request.getParameter("Nota"+i);
				log.setDatos(datos);
				log.setIp(request.getRemoteAddr());
				log.setUsuario((String) session.getAttribute("codigoPersonal"));
				log.setTabla("krdx_alumno_eval");

				AlumEvaluacion.setCodigoPersonal(request.getParameter("CodigoPersonal"+i));
				AlumEvaluacion.setNota(request.getParameter("Nota"+i));
				if (AlumEvaluacion.existeReg(conEnoc) == true){
					if (AlumEvaluacion.deleteReg(conEnoc)){
						sResultado = "Borrado: "+AlumEvaluacion.getCodigoPersonal();
						/******* LOG ******/
						log.setOperacion("delete");
						logU.insertReg(conEnoc, log);
						conEnoc.commit();
					}else{
						sResultado = "No Borró: "+AlumEvaluacion.getCodigoPersonal();
				}					
				}else{
					sResultado = "No existe: "+AlumEvaluacion.getCodigoPersonal();
				}
			}
			sEvaluacion="0";
			conEnoc.setAutoCommit(true);
			break;
		}		
		case 5:{ //Cerrar Materia Ordinaria
			int rowValido = 0; int rowUpdate = 0;
			conEnoc.setAutoCommit(false);
			try{				
				// Busca las actividades y coloca un cero en las que no fueron evaluadas en la e42...
				ArrayList lisAct = cgau.getListCurso(conEnoc, sCursoCargaId, "ORDER BY ACTIVIDAD_ID");
				for(i = 0; i < lisAlumnos.size(); i++){
					aca.kardex.KrdxCursoAct ac = (aca.kardex.KrdxCursoAct) lisAlumnos.get(i);
					
					for(j = 0; j < lisAct.size(); j++){
						cga = (aca.carga.CargaGrupoActividad) lisAct.get(j);
						
						AlumActividad.setCodigoPersonal(ac.getCodigoPersonal());						
						AlumActividad.setActividadId(cga.getActividadId());
						AlumActividad.setCursoCargaId(sCursoCargaId);
						if(!AlumActividad.existeReg(conEnoc)){
							AlumActividad.setNota("0");
							AlumActividad.setActividadE42("0");
							if (AlumActividad.insertReg(conEnoc)){
								conEnoc.commit();
								//System.out.println("Grabe:"+i+":"+AlumActividad.getCodigoPersonal()+":"+AlumActividad.getActividadId());
							}else{
								System.out.println("Error al grabar la actividad");
							}
						}else{
							
						}
					}
				}
				
				// Busca las evaluaciones y coloca un cero en las que no fueron evaluadas en la e42...
				ArrayList lisEv = evaU.getLista(conEnoc, sCursoCargaId, "ORDER BY EVALUACION_ID");
				for(i = 0; i < lisAlumnos.size(); i++){
					aca.kardex.KrdxCursoAct ac = (aca.kardex.KrdxCursoAct) lisAlumnos.get(i);
					for(j = 0; j < lisEv.size(); j++){
						cge = (aca.carga.CargaGrupoEvaluacion) lisEv.get(j);
						AlumEvaluacion.setCodigoPersonal(ac.getCodigoPersonal());
						AlumEvaluacion.setCursoCargaId(sCursoCargaId);
						AlumEvaluacion.setEvaluacionId(cge.getEvaluacionId());
						if(!AlumEvaluacion.existeReg(conEnoc)){
							AlumEvaluacion.setNota("0");
							AlumEvaluacion.setEvaluacionE42("0");
							AlumEvaluacion.insertReg(conEnoc);
						}else{
							
						}
					}
				}
				conEnoc.commit();				
				Materia.mapeaRegId(conEnoc, sCursoCargaId);
				Materia.setEstado("3");
				Materia.setFEvaluacion(sFecha);
				
				if (MateriaU.updateReg(conEnoc, Materia)){
					sEstado = "3";
					for (i=0; i<lisAlumnos.size(); i++){
						aca.kardex.KrdxCursoAct ac = (aca.kardex.KrdxCursoAct) lisAlumnos.get(i);
						
						/* ES POSIBLE MEJORAR EL RENDIMIENTO */
						sPromedio = krdxEvaluacionUtil.getAlumnoPromedio(conEnoc, ac.getCursoCargaId(), ac.getCodigoPersonal());
						if (sPromedio==null) sPromedio="0";
						sPromedio = sPromedio.trim();
						nPromedio = Double.valueOf(sPromedio).intValue();
						sPromedio = String.valueOf(nPromedio);
		
						kardex.setCodigoPersonal(ac.getCodigoPersonal());
						kardex.setCursoCargaId(ac.getCursoCargaId());
						
						if (kardex.existeReg(conEnoc)){
							kardex.mapeaRegId(conEnoc, ac.getCodigoPersonal(), ac.getCursoCargaId());
							mapaCurso = MapaCursoU.mapeaRegId(conEnoc,kardex.getCursoId());
							kardex.setNota(sPromedio);
							kardex.setFNota(sFecha);
							kardex.setFExtra(null);
							// Si el tipo de calificación es diferente de RA, CD o CP actualiza el estado como AC o NA.
							if ( !kardex.getTipoCalId().equals("3") && !kardex.getTipoCalId().equals("4") && 
								!kardex.getTipoCalId().equals("5") && !kardex.getTipoCalId().equals("6")){
								if (nPromedio >= Integer.parseInt(mapaCurso.getNotaAprobatoria())){
									kardex.setTipoCalId("1");
								}else{ 
									kardex.setTipoCalId("2"); 
								}
							}	
						}
						if (kardex.getTitulo().equals("N")){
							rowValido++;
							if (kardex.updateReg(conEnoc)){								
								rowUpdate++;
							}
						}
					}
					if (rowUpdate==rowValido){
						conEnoc.commit();
						sResultado = "Materia Ordinaria Cerrada !!!";
					}else{
						conEnoc.rollback();
						sResultado = "No terminó el proceso..!! Intentelo nuevamente.¡¡ "+rowUpdate+" : "+rowValido;
					}					
				}else{
					sResultado = "No se pudo cerrar la materia..!! "+rowUpdate+" : "+rowValido;
				}
			}catch(Exception ex){
				conEnoc.rollback();
				sResultado = "Error en el proceso";
			}
			conEnoc.setAutoCommit(true);
			break;
		} // fin de opcion
		case 6: { //Grabar Extraordinarios
			conEnoc.setAutoCommit(false);
			for (i=1;i<=numExtras;i++){
				kardex.setCodigoPersonal(request.getParameter("CodigoPersonalE"+i));
				kardex.setCursoCargaId(sCursoCargaId);
				if (kardex.existeReg(conEnoc)){
					kardex.mapeaRegId(conEnoc, request.getParameter("CodigoPersonalE"+i), sCursoCargaId);
					kardex.setNotaExtra(request.getParameter("NotaExtra"+i));
					kardex.setFExtra(sFecha);
					mapaCurso = MapaCursoU.mapeaRegId(conEnoc,kardex.getCursoId());
					if (kardex.getTipoCalId()!= "3" &&
					 	!kardex.getTipoCalId().equals("5") && !kardex.getTipoCalId().equals("6")){
						if (Integer.parseInt(kardex.getNotaExtra()) >= Integer.parseInt(mapaCurso.getNotaAprobatoria()))
							kardex.setTipoCalId("1");
						else 
							kardex.setTipoCalId("2");
					}	
					if (kardex.updateReg(conEnoc)){
						sResultado = "OK.. Extraordinarios Guardados";
/********* LOG ****/
						String datos = "CursoCargaId: "+sCursoCargaId+", CodigoPersonal: "+request.getParameter("CodigoPersonalE"+i) + ", Nota Extra: "+request.getParameter("NotaExtra"+i)+", Fecha: "+sFecha;
						log.setDatos(datos);
						log.setIp(request.getRemoteAddr());
						log.setUsuario((String) session.getAttribute("codigoPersonal"));
						log.setTabla("krdx_curso_act");
						log.setOperacion("update");
						logU.insertReg(conEnoc, log);
					}
					else sResultado = "Ocurrio un error al actualizar extraordinarios...";
				}				
			}
			sEvaluacion="0";
			conEnoc.setAutoCommit(true);
			break;
		}		
		case 7: { //Cerrar Materia Extraordinaria
			conEnoc.setAutoCommit(false);
		
			Materia.mapeaRegId(conEnoc, sCursoCargaId);
			Materia.setEstado("4");
			if (MateriaU.updateReg(conEnoc, Materia)){
				sEstado = "4";
				for (i=0; i<lisAlumnos.size(); i++){
					aca.kardex.KrdxCursoAct ac = (aca.kardex.KrdxCursoAct) lisAlumnos.get(i);
					kardex.setCodigoPersonal(ac.getCodigoPersonal());
					kardex.setCursoCargaId(ac.getCursoCargaId());
					if (kardex.existeReg(conEnoc)){
						kardex.mapeaRegId(conEnoc, ac.getCodigoPersonal(), ac.getCursoCargaId());
						mapaCurso = MapaCursoU.mapeaRegId(conEnoc,kardex.getCursoId());
						// Si el tipo de calificación es diferente de RA, CD o CP actualiza el estado como AC o NA.
						//System.out.println(kardex.getCodigoPersonal()+" EX="+kardex.getNotaExtra()+" NA="+mapaCurso.getNotaAprobatoria()+" TC="+kardex.getTipoCalId());
						if ( !kardex.getTipoCalId().equals("3") && 
							!kardex.getTipoCalId().equals("5") && !kardex.getTipoCalId().equals("6")){
							//System.out.println(Integer.parseInt(kardex.getNotaExtra()) >= Integer.parseInt(mapaCurso.getNotaAprobatoria()));
							if (Integer.parseInt(kardex.getNotaExtra()) >= Integer.parseInt(mapaCurso.getNotaAprobatoria()) || Integer.parseInt(kardex.getNota()) >= Integer.parseInt(mapaCurso.getNotaAprobatoria()))
								kardex.setTipoCalId("1");
							else
								kardex.setTipoCalId("2"); 
						}
					}
					kardex.updateReg(conEnoc);
				}
				sResultado = "Cerrado el período Extraordinario..!!";
			}else{
				sResultado = "No se pudo cerrar el período extraordinario..!!";
			}
			conEnoc.setAutoCommit(true);
			
			break;
		}		
		case 8: { 
			conEnoc.setAutoCommit(false);
			String URL = "../../portales/maestro/cactames?cursoCargaId="+sCursoCargaId+"&imp=1";
			Materia.mapeaRegId(conEnoc,sCursoCargaId);
			Materia.setEstado("4");
			MateriaU.updateReg(conEnoc, Materia);
			sResultado = "Entregar el Acta!!!";
			//response.sendRedirect(URL); 
			conEnoc.setAutoCommit(true);
			break;
		}
		case 9: { //Grabar Tipo Calificación 
			conEnoc.setAutoCommit(false);
		
			for (i=0;i<=lisAlumnos.size();i++){
				kardex.setCodigoPersonal(request.getParameter("CodigoPersonal"+i));
				kardex.setCursoCargaId(sCursoCargaId);
				if (kardex.existeReg(conEnoc)){
					kardex.mapeaRegId(conEnoc, request.getParameter("CodigoPersonal"+i), sCursoCargaId);
						kardex.setTipoCalId(request.getParameter("tipocalid"+i));
					if (kardex.updateReg(conEnoc)){
						sResultado = "Actualizo estado ¡¡";
/********* LOG ****/
						String datos = "CursoCargaId: "+sCursoCargaId+", CodigoPersonal: "+request.getParameter("CodigoPersonal"+i) + ", TipoCalId: "+request.getParameter("tipocalid"+i)+", Fecha: "+sFecha;
						log.setDatos(datos);
						log.setIp(request.getRemoteAddr());
						log.setUsuario((String) session.getAttribute("codigoPersonal"));
						log.setTabla("krdx_curso_act");
						log.setOperacion("update");
						logU.insertReg(conEnoc, log);
					}
					else sResultado = "Ocurrio un error al actualizar el estado..¡¡";
				}
			}
			sEvaluacion="0";
			
			conEnoc.setAutoCommit(true);
			break;
		}
	}
	aca.catalogo.EstrategiaUtil estrategiaU = new aca.catalogo.EstrategiaUtil();
	// Actualiza la lista de alumnos
	lisAlumnos 		= acu.getListCurso(conEnoc, sCursoCargaId, "ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
	// Map de saldos vencidos del alumno
	java.util.HashMap<String,String> mapAlumnos = aca.alumno.AlumUtil.mapAlumnosMateria(conEnoc, sCursoCargaId);	
	// Map que obtiene las notas de todos los alumnos en la materia
	java.util.HashMap<String,String> mapNotas = krdxEvaluacionUtil.getMapAlumEval(conEnoc, sCursoCargaId);
	// Map de saldos vencidos del alumno
	java.util.HashMap<String,String> mapDeuda = aca.financiero.FesContratoFinancieroUtil.mapSaldoVencido(conEnoc, sCursoCargaId);
	// Map de tipos de calificación
	java.util.HashMap<String,String> mapTipoCal = TipocalU.mapTipoCal(conEnoc, "1");
	// Map de permisos
	java.util.HashMap<String,String> mapPermiso = aca.financiero.FinPermisoUtil.mapAlumnoPermiso(conEnoc, " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT WHERE CURSO_CARGA_ID = '"+sCursoCargaId+"') AND now() BETWEEN F_INICIO AND F_LIMITE");	
	//Map de puntos
	java.util.HashMap<String, String> mapAlumnoPuntos 		= aca.kardex.ActualUtil.mapAlumnoPuntos(conEnoc, sCursoCargaId, "");	
	//Map de puntos Extras
	java.util.HashMap<String, String> mapAlumnoExtras 		= aca.kardex.ActualUtil.mapAlumnoExtras(conEnoc, sCursoCargaId, "");	
	//Map de puntos evaluados por alumno
	java.util.HashMap<String, String> mapAlumnoEvaluados 	= aca.kardex.ActualUtil.mapAlumnoEvaluados(conEnoc, sCursoCargaId, "");
	//Map de limite de extraordinario en el plan del alumno
	java.util.HashMap<String, String> mapExtraPlan 			= aca.kardex.ActualUtil.mapLimiteExtra(conEnoc, sCursoCargaId);
	//Map de alumnos graduados para identificarlos
	java.util.HashMap<String, String> mapaAlumGraduados 	= egresoU.mapaAlumGraduados(conEnoc);
	
	// Trae los datos de la materia 
	mapaCurso = MapaCursoU.mapeaRegId(conEnoc, aca.carga.CargaGrupoCursoUtil.cursoIdOrigen(conEnoc,sCursoCargaId));
	
	String cursoCargaId		= request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
	
%>
<div class="container-fluid">
	<h2>Evaluar <small>( <%= sCodigoPersonal %> - <%= sMaestro %> - <%=sMateria%>)</small></h2>
	<form action="evaluar" method="post" name="frmevaluacion" target="_self">
	<input type="hidden" name="Accion">
	<input type="hidden" name="CursoCargaId" value="<%=sCursoCargaId%>">
	<input type="hidden" name="Maestro" value="<%=sMaestro%>">
	<input type="hidden" name="Materia" value="<%=sMateria%>">
	<input type="hidden" name="CursoId" value="<%=cursoId%>">
	<input type="hidden" name="NoCerrar" value='0'>
	<input type="Hidden" name="EvaluacionId" value="<%=sEvaluacion%>">
		
	<div class="alert alert-info">
		<a class="btn btn-primary" href="cursos"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
<%		
	int nE = cEvaU.getNumEstrategias(conEnoc,sCursoCargaId);
	int nEE = cEvaU.getNumEstrategiasEvaluadas(conEnoc,sCursoCargaId);
	int nAB = cEvaU.getNumAlumnosBaja(conEnoc, sCursoCargaId);	
	if (  nEE < nE && nAB < lisAlumnos.size() ){
%>		<script>document.frmevaluacion.NoCerrar.value ='1';</script>
<%	}
		
	if (sEstado.equals("2") && sEvaluacion.equals("0")){
		out.print("<a class='btn btn-info' href=\"javascript:SimularCierre()\">Simular cierre</a>");
		out.print("&nbsp; &nbsp;");
		out.println("<a class='btn btn-primary' href=\"javascript:CerrarMateria()\">Cerrar evaluacion ordinaria</a>");
	}else if(sEstado.equals("3") && sEvaluacion.equals("0")){
		out.println("<a class='btn btn-primary' href=\"javascript:CerrarMateriaExtra()\">Cerrar evaluacion extraordinaria</a>");
%>
		<a class="btn btn-info" href="diferida?CursoCargaId=<%=sCursoCargaId%>&CursoId=<%=cursoId%>&Materia=<%=sMateria%>&Maestro=<%=sMaestro%>&EvaluacionId=<%=sEvaluacion%>">Calificaciones diferidas</a>
<% 			
	}else if(sEstado.equals("4")){
%>		
		<a class="btn btn-info" href="../../portales/maestro/cactames?cursoCargaId=<%=sCursoCargaId%>&imp=1" target="_blank">Imprimir Acta</a>
		<a class="btn btn-info" href="correccion?CursoCargaId=<%=sCursoCargaId%>&CursoId=<%=cursoId%>&Materia=<%=sMateria%>&Maestro=<%=sMaestro%>&EvaluacionId=<%=sEvaluacion%>">Corrección de notas</a>
		<a class="btn btn-info" href="diferida?CursoCargaId=<%=sCursoCargaId%>&CursoId=<%=cursoId%>&Materia=<%=sMateria%>&Maestro=<%=sMaestro%>&EvaluacionId=<%=sEvaluacion%>">Calificaciones diferidas</a>
<%	} %>

	</div>
		
	<table style="width:80%" class="table table-condensed table-bordered">
	<tr>
    	<td colspan="9"><h3>Esquema de evaluación &nbsp; <small> [ <%=cursoCargaId %> ] &nbsp; [ Nota aprobatoria <%=mapaCurso.getNotaAprobatoria()%>]</small></h3></td>
    </tr>	
  	<tr>
    	<th width="5%"><spring:message code="aca.Numero"/></th>
    	<th colspan="2" width="50%">Metodología y Descripción</th>
		<th width="5%"><spring:message code="aca.Fecha"/></th>
		<th width="10%" class="right">Valor</th>
		<th width="10%"><spring:message code="aca.Tipo"/></th>
		<th width="10%" class="right"><spring:message code="aca.Actividades"/></th>
		<th width="10%"><spring:message code="aca.Estado"/></th>
  	</tr>
<%	
	sEstado = grupoU.getEstado(conEnoc, sCursoCargaId);
	for (i=0; i< lisEvaluacion.size(); i++){
		cge = (aca.carga.CargaGrupoEvaluacion) lisEvaluacion.get(i);
		double puntos = Double.parseDouble(cge.getValor());
		sumaPuntos += puntos;
%>  
  	<tr>
	    <td height="15">&nbsp;<%=i+1%>.-</td>
	    <td colspan="2"><a href="transferir?ccid=<%=sCursoCargaId %>&evid=<%=cge.getEvaluacionId() %>&vista=xAlumnoEstrategia&id=<%=sCursoCargaId+"-"+cge.getEvaluacionId()%>&nomMateria=<%=sMateria%>&nomProfesor=<%=sMaestro%>&origen=Profesor"><%=estrategiaU.getNombre(conEnoc, cge.getEstrategiaId())%> - <%if (cge.getNombreEvaluacion()==null){ out.print(""); }else{ out.print(cge.getNombreEvaluacion());} %></a></td>
		<td align="center"><%=cge.getFecha()%></td>
		<td style="text-align:right"><%=getFormato.format(puntos)%></td>
		<td align="center">
 
<%				
				if (cge.getTipo().equals("%")){
					out.print("Porcentaje");
				}else if(cge.getTipo().equals("P")){
					out.print("Puntos");
				}else if(cge.getTipo().equals("E")){
					out.print("Puntos Extras");
				}
%>	
		</td>
		
<% 
		if (cgau.evalTieneActiv(conEnoc, cge.getCursoCargaId(), cge.getEvaluacionId())){
			aca.carga.CargaGrupoActividadUtil actividadU	= new aca.carga.CargaGrupoActividadUtil();
			int actividad = actividadU.getListCargaEvaluacion(conEnoc, sCursoCargaId, cge.getEvaluacionId()).size(); 
%>
			 <td style="text-align:right;"><%=actividad%></td>
<% 
		}else{%>
			<td></td>
<% 
		}
%>		
		<td align="center">
<%		if (sEstado.equals("2")){
	  		if (cgau.evalTieneActiv(conEnoc, cge.getCursoCargaId(), cge.getEvaluacionId())){%>
			<a class="btn btn-primary" href="evaact?CursoCargaId=<%=sCursoCargaId%>&Maestro=<%=sMaestro%>&Materia=<%=sMateria%>&EvaluacionId=<%=cge.getEvaluacionId()%>&NombreEval=<%=estrategiaU.getNombre(conEnoc, cge.getEstrategiaId())%> - <%if (cge.getNombreEvaluacion()==null){ out.print(""); }else{ out.print(cge.getNombreEvaluacion());} %>">
			Evaluar</a>
<% 			}else{ %>
  			<a class="btn btn-primary" href="evaluar?CursoCargaId=<%=sCursoCargaId%>&Maestro=<%=sMaestro%>&Materia=<%=sMateria%>&EvaluacionId=<%=cge.getEvaluacionId()%>">Evaluar</a>
<%			} 
		}else if (sEstado.equals("3")){%>
			<a class="btn btn-primary" href="evaluar?CursoCargaId=<%=sCursoCargaId%>&Maestro=<%=sMaestro %>&Materia=<%=sMateria%>&CursoId=<%=cursoId%>&EvaluacionId=E"><spring:message code="aca.Extra"/></a>
<%  	}else if (sEstado.equals("4")){%>
	 		<font color="#AE2113"><b><spring:message code="aca.Cerrada"/></b></font>
<% 		}else if (sEstado.equals("5")){ %>
 		<font color="green"><b>Entregada</b></font>
<%  	} %>
		</td>
  	</tr>
<%	}
	String colorPuntos = "<span class='badge badge-warning'>"+getFormato.format(sumaPuntos)+"</span>";
	if (sumaPuntos==100) colorPuntos = "<span class='badge badge-success'>"+getFormato.format(sumaPuntos)+"</span>";	
%>
	<tr">
		<th colspan="4" style="text-align:right; font-size:9pt;"><b>Total de puntos:<b></th>
		<th style="text-align:right; font-size:9pt;"><b><%=colorPuntos%></b></th>
		<th colspan="3">&nbsp;</th>		
	</tr>    	    
	</table>
	<div align="center"><font size="3"><b>
<%
	if (sEstado.equals("1")){
		out.println("M a t e r i a  &nbsp; c r e a d a");
	}else if(sEstado.equals("2")){
		out.println("E v a l u a c i ó n &nbsp; o r d i n a r i a");
	}else if (sEstado.equals("3")){
		out.println("E v a l u a c i ó n &nbsp; e x t r a o r d i n a r i a");
	}else if (sEstado.equals("4")){
		out.println("M a t e r i a &nbsp; c e r r a d a");
	}else if (sEstado.equals("5")){
		out.println("M a t e r i a &nbsp; c e r r a d a  &nbsp; y &nbsp; e n t r e g a d a");
	}else{
		out.println("N o &nbsp; e x i s t e !");
	}
%>	
	</b></font></div>
	<br>
	<table style="width:100%" class="table table-condensed table-bordered">
	<td colspan="<%=lisEvaluacion.size()+8%>"><h3>Listado de Alumnos</h3><small><span class='badge badge-inverse'>G</span> = Alumno graduado</small></td>
  	<tr>
    	<th ><spring:message code="aca.Numero"/></th>
	    <th >Código</th>
		<th ><spring:message code="aca.NombreDelAlumno"/></th>
		<%	for (j=0;j<lisEvaluacion.size();j++){ %>
				<th width="25" class="right"><%=j+1%></th>
		<%	} %>
		<th class="right" font-size:13px;">&epsilon;</th>
		<th class="right"><b>S.E.</b></th>
		<th class="right"><spring:message code="aca.Total"/></th>
		<th class="right">Extra.</th>
		<th>Estado 
		<%	if (sEstado.equals("2")){ %>
				<a href="evaluar?CursoCargaId=<%=sCursoCargaId%>&Maestro=<%=sMaestro%>&Materia=<%=sMateria%>&EvaluacionId=T&CursoId=<%=cursoId%>"><font size="1">Cambia</font></a>
		<%	}%>
		</th>
  	</tr>
  	<input type='hidden' name='totalAlumnos' value='<%=lisAlumnos.size()%>'>
<%  numExtras=0;
	boolean okvalida = false;
	String enfocar = "";
	String nombreAlumno = "";
	double saldoVencido = 0;
	String graduado = "";
	
	
	for(i=0; i<lisAlumnos.size(); i++){
		aca.kardex.KrdxCursoAct ac = (aca.kardex.KrdxCursoAct) lisAlumnos.get(i);	
		
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
		
		//System.out.println(ac.getCodigoPersonal()+"::"+ac.getCursoId().substring(0, 8));
		if(mapaAlumGraduados.containsKey(ac.getCodigoPersonal()+ac.getCursoId().substring(0, 8))){
			graduado = "<span class='badge badge-inverse'>G</span>";
		}
		  
%>		
  		<tr>
   			<td<%=saldoColor%> align="center" height="15"><%=i+1%></td>
    		<td<%=saldoColor%> align="center" <%=textoDeuda%> ><%=ac.getCodigoPersonal()%></td>	
    		<td<%=saldoColor%> align="left">
      		<%	if(sEstado.equals("2")){%>
    				<a href='../../portales/alumno/transferir?matricula=<%=ac.getCodigoPersonal()%>&id=<%=sCursoCargaId%>&vista=MandarAlumno&origen=Profesor&sCursoCargaId=<%=sCursoCargaId%>&al=<%=ac.getCodigoPersonal()%>&alumno=<%=nombreAlumno%>&nomProfesor=<%=sMaestro%>&nomMateria=<%=sMateria%>'>
   					<img align=absmiddle alt="Enviar Archivo" src="/academico/imagenes/upload3.gif" ></a>
			<%	}%>
    			<a href='../../portales/alumno/transferir?matricula=<%=ac.getCodigoPersonal()%>&id=<%=sCursoCargaId%>&vista=xAlumno&alumno=<%=nombreAlumno%>&nomProfesor=<%=sMaestro%>&origen=Profesor&nomMateria=<%=sMateria%>'>
    			&nbsp;<%=nombreAlumno%></a>
    			<%=graduado%>
    		</td>
    		
		<%	
			// Determina el estilo del texto(rojo:deudores, negro:sin deuda) y la alineación en todo el renglón. 
			String estiloNota = "";
			if (saldoVencido<-100 && !permiso) estiloNota = "style='color:#AE2113; text-align:right;'"; else estiloNota = "style='text-align:right;'";
			
			for (j=0;j<lisEvaluacion.size();j++){
				cge = (aca.carga.CargaGrupoEvaluacion) lisEvaluacion.get(j);
				
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
				//sPromedio = krdxEvaluacionUtil.getAlumnoPromedio(conEnoc, ac.getCursoCargaId(), ac.getCodigoPersonal());
				
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
			//if (ac.getCodigoPersonal().equals("0941428")&&evaluados>0) System.out.println("Promedio2 = "+(nPromedio/evaluados*100));
			double rendimiento = 0; 
			if (evaluados>0) rendimiento = Math.round(nPromedio/evaluados*100);
			
			eficiencia = String.valueOf((int)rendimiento);
			//eficiencia = krdxEvaluacionUtil.getAlumnoEficiencia(conEnoc, ac.getCursoCargaId(), ac.getCodigoPersonal());
			sePromedio= rendimiento/10;
			
			// Traer los datos del curso
			mapaCurso = MapaCursoU.mapeaRegId(conEnoc, ac.getCursoId());
		%>
			<td <%=estiloNota%> title="Puntos=<%=sPromedio%> Evaluados:<%=evaluados%>"><%=eficiencia%></td>
			<td <%=estiloNota%>><b><%=sePromedio%></b></td>
			<td <%=estiloNota%>>
				<span class="badge badge-inverse"><%=sPromedio%></span>
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
						((nPromedio >= limiteExtra && nPromedio < Integer.parseInt(mapaCurso.getNotaAprobatoria())) ||
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
		<%	}
			else if (sEvaluacion.equals("T")){	%>
	    		<a class="btn btn-primary" href="javascript:GrabarTipoCal()">Grabar Estado</a>
		<%	}
			else if ( (!sEvaluacion.equals("0")) && (sEstado.equals("1")||sEstado.equals("2")) ){	%>  	    
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
<%
	lisEvaluacion		= null;
	AlumEvaluacion		= null;
	evaluacionU			= null;
	estrategiaU			= null;
	grupoU 				= null;
	lisAlumnos			= null;
	acu 				= null;
	krdxEvaluacionUtil	= null;
	kardex				= null;
	alumnoU 			= null;
	TipocalU			= null;
%>
<!-- fin de estructura -->
<%@ include file= "../../cierra_enoc.jsp" %>