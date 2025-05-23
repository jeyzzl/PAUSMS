<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.util.*"%>

<jsp:useBean id="CatPeriodoUtil" scope="page" class="aca.catalogo.CatPeriodoUtil"/>
<jsp:useBean id="EstadisticaU" scope="page" class="aca.vista.EstadisticaUtil" />
<jsp:useBean id="CargaU" scope="page" class="aca.carga.CargaUtil" />
<jsp:useBean id="FacultadU" scope="page" class="aca.catalogo.CatFacultadUtil" />
<jsp:useBean id="CarreraU" scope="page" class="aca.catalogo.CatCarreraUtil" />
<jsp:useBean id="CatModalidadU" scope="page" class="aca.catalogo.ModalidadUtil" />
<jsp:useBean id="Acceso" scope="page" class="aca.acceso.Acceso"/>
<jsp:useBean id="AccesoU" scope="page" class="aca.acceso.AccesoUtil" />
<jsp:useBean id="AlumnoU" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="AcademicoU" scope="page" class="aca.alumno.AcademicoUtil"/>
<jsp:useBean id="ReligionDao" scope="page" class="aca.catalogo.CatReligionDao"/>
<jsp:useBean id="ModalidadU" scope="page" class="aca.catalogo.ModalidadUtil"/>
<jsp:useBean id="PaisU" scope="page" class="aca.catalogo.PaisUtil"/>
<jsp:useBean id="EstadoU" scope="page" class="aca.catalogo.EstadoUtil"/>
<jsp:useBean id="CiudadU" scope="page" class="aca.catalogo.CiudadUtil"/>
<jsp:useBean id="TipoU" scope="page" class="aca.catalogo.TipoAlumnoUtil"/>
<jsp:useBean id="BecPuestoAlumno" scope="page" class="aca.bec.BecPuestoAlumno"/>
<jsp:useBean id="BecPuestoAlumnoU" scope="page" class="aca.bec.BecPuestoAlumnoUtil"/>
<jsp:useBean id="KrdxCursoActU" scope="page" class="aca.kardex.ActualUtil"/>
<jsp:useBean id="DivisionU" scope="page" class="aca.catalogo.DivisionUtil" />
<jsp:useBean id="altasUtil" scope="page" class="aca.reportes.AltasBajasUtil"/>
<jsp:useBean id="AlumEstadoU" scope="page" class="aca.alumno.EstadoUtil"/>
<jsp:useBean id="AlumPersonal" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumAcademico" scope="page" class="aca.alumno.AlumAcademico"/>
<jsp:useBean id="ResDatos" scope="page" class="aca.residencia.ResDatos"/>
<jsp:useBean id="MentContactoU" scope="page" class="aca.mentores.MentContactoUtil"/>
<style>	
	.table th{
		font-size: 10px;
	}	
</style>
<script type="text/javascript">	
	function Mostrar(){		
		document.forma.submit();
	}
	
	function cambiaPeriodo(periodoId){
		document.location.href="perfilAlumnos?Accion=0&PeriodoId="+periodoId+"&cambioPeriodo=1";
	}
	
	function cambiaCarga(){		
		document.location.href="perfilAlumnos?Accion=0&CargaId="+document.forma.CargaId.value+"&cambioCarga=S";
	}
</script>
<%	
	DecimalFormat frmDecimal= new DecimalFormat("###,##0.00; -###,##0.00");
	DecimalFormat frmEntero	= new DecimalFormat("###,##0; -###,##0");

	String codigo			= (String) session.getAttribute("codigoPersonal");
	String cambioPeriodo	= request.getParameter("cambioPeriodo")==null?"0":request.getParameter("cambioPeriodo");
	String periodoId 		= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");
	String fechaIni			= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin			= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");	
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	int altas				= 0;
	int bajas				= 0;
	int total 				= 0;
	int origen				= 0;
	
	if (accion.equals("1")){		
		session.setAttribute("fechaIni", fechaIni);
		session.setAttribute("fechaFin", fechaFin);
	}	
	ArrayList<aca.catalogo.CatPeriodo> listaPeriodos = CatPeriodoUtil.getListAll(conEnoc, "ORDER BY PERIODO_ID");
	if(!cambioPeriodo.equals("0")){
		session.setAttribute("periodo", request.getParameter("PeriodoId"));
	}
	periodoId = (String)session.getAttribute("periodo");
	ArrayList<aca.carga.Carga> lisCarga = new aca.carga.CargaUtil().getListAll(conEnoc,"WHERE PERIODO IN ('"+periodoId+"') AND ESTADO = '1' ORDER BY CARGA_ID");
	
	if(!cambioPeriodo.equals("0")&&!lisCarga.isEmpty()){
		session.setAttribute("cargaId", lisCarga.get(0).getCargaId());		
	}else if(request.getParameter("cambioCarga")!=null&&!request.getParameter("cambioCarga").equals("")){
		session.setAttribute("cargaId", request.getParameter("CargaId"));		
	}
	String cargaId = (String)session.getAttribute("cargaId");
	if(lisCarga.isEmpty()){
		session.setAttribute("cargaId", "XXXXXX");
	}
	
	String facultadId		= "";	
	
	int cont 				= 0;	
	int nInscritos 			= 0;	
	String codigoTemp		= "";
	String fechaHoy 		= aca.util.Fecha.getHoy();
	
	ArrayList<aca.catalogo.CatModalidad> lisModalidad 			= CatModalidadU.getListAll(conEnoc, " ORDER BY MODALIDAD_ID");
	ArrayList<aca.alumno.AlumEstado> lisInsc					= null;
	// Saldos de alumnos en la carga
	HashMap<String, aca.financiero.SaldosAlumnos> mapSaldos = aca.financiero.SaldosAlumnosUtil.mapInscritosCarga(conEnoc, cargaId);
	Acceso = AccesoU.mapeaRegId(conEnoc, codigo);	
	if( AccesoU.existeReg(conEnoc, codigo)==true){
		
		String modalidades			= (String)session.getAttribute("modalidades") == null?"'1'":session.getAttribute("modalidades").toString();		
		String lisModo[] 			= modalidades.replace("'", "").split(",");
		
		String tipoCarga 			= cargaId.substring(4, 5);
		ArrayList<String> lisCargas	= CargaU.lisCargas(conEnoc, "'"+tipoCarga+"'", "'A','B','C'", " ORDER BY CARGA_ID DESC");
		
		// Busca las cargas anteriores
		String carga2 = "";
		String carga3 = "";
		int row=0;
		for (String carga:lisCargas){
			row++;
			if (carga.equals(cargaId)){
				carga2 = lisCargas.get(row);
				carga3 = lisCargas.get(row+1);
				break;
			}	        				
		} 
		//System.out.println("Cargas:"+cargaId+":"+carga2+":"+carga3);		
		
		if(modalidades.equals("")) modalidades="' '";
%>
<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
</head>
<div class="container-fluid">
	<h1>Perfil de Alumnos</h1>
	<form id="forma" name="forma" action="perfilAlumnos?Accion=1" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<b>Período:</b>
        <select onchange='javascript:cambiaPeriodo(this.value);' name="PeriodoId" class="input input-medium">
<%		for(int j=0; j<listaPeriodos.size(); j++){
			aca.catalogo.CatPeriodo periodo = listaPeriodos.get(j);%>
			<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print("Selected");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
<%		}%>
        </select>
        &nbsp;
		<b>Carga:</b>
	    <select onchange='javascript:cambiaCarga();' name="CargaId" style="width:350px;" class="input input-xlarge">
<%		for(int i=0; i<lisCarga.size(); i++){
			aca.carga.Carga carga = lisCarga.get(i);%>
			<option <%if(cargaId.equals(carga.getCargaId()))out.print("Selected");%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId() %>] <%=carga.getNombreCarga()%></option>
<%		}%>				
        </select>
        
		&nbsp;&nbsp;
		<b>Modalidades:</b>
	<%
			for(String mod:lisModo){
				String nombreModalidad = aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc, mod);
				out.print("["+nombreModalidad+"] ");	
			}		
	%>					
	</div>
	<div class="alert alert-info">
		<a class="btn btn-primary btn-small" href="menu"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;		 
		Fecha Inicio: <input data-format="hh:mm:ss" id="FechaIni" name="FechaIni" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10"/>&nbsp;&nbsp;
		<span class="add-on">
    	 	<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
   		</span>
		Fecha Final: <input data-format="hh:mm:ss" id="FechaFin" name="FechaFin" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10"/>&nbsp;&nbsp;
		<span class="add-on">
     	 	<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
   		</span>
		<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>		 
	</div>
	</form>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	
		<tr> 
		    <th align="center">Nº</th>
			<th align="center">Fac</th>
			<th align="center">Plan</th>
		    <th align="center">Mat</th>
		    <th align="center">Apellidos</th>
		    <th align="center">Nombres</th>
		    <th align="center">Ins</th>
		    <th align="center">C/Fin</th>
			<th align="center">Rel</th>
			<th align="center">Bau</th>
			<th align="center">T/Al</th>
			<th align="center">Gen</th>
			<th align="center">Ed/Civ</th>			
			<th align="center">Edad</th>			
			<th align="center">Res</th>
			<th align="center">Int/C</th>
			<th align="center">Dorm</th>
			<th align="center">R/Ext</th>
			<th align="center">Vive/Tutor</th>
			<th align="center">Dir/Externado</th>
			<th align="center">Pais</th>
			<th align="center">Estado</th>
			<th align="center">Ciudad</th>			
			<th align="center">División</th>
			<th align="center">Turno</th>
			<th align="center">Departamento</th>
			<th align="center">Taller</th>
			<th align="center"><%=cargaId%></th>
			<th align="center"><%=carga2%></th>
			<th align="center"><%=carga3%></th>
			<th align="center">Mat.Ins.</th>
			<th align="center">Mat.Rep.</th>
			<th align="center">Mat.R2</th>
			<th align="center">Cr.Ins.</th>
			<th align="center">Cr.R2</th>
			<th align="center">Costo.R2</th>
			<th align="center">Mentor</th>			
			<th align="center">Ent.Ment.</th>
			<th align="center">Tot.Ent.</th>
			<th align="center">C.C.</th>
			<th align="center">C.A.</th>
			<th align="center">C.B.</th>
			<th align="center">T.C.</th>
			<th align="center">Saldo</th>
			<th align="center">Vencido</th>
		</tr>	
	</thead>
	<%	
		if (accion.equals("1")){
			HashMap<String, aca.catalogo.CatFacultad> mapFacultad 		= FacultadU.getMapFacultad(conEnoc, "");
			HashMap<String, aca.catalogo.CatCarrera> mapCarrera 		= CarreraU.getMapAll(conEnoc, "");					
			HashMap<String, aca.catalogo.CatReligion> mapReligion 		= ReligionDao.getMapAll(conEnoc,"");
			HashMap<String, aca.catalogo.CatTipoAlumno> mapTipo 		= aca.catalogo.TipoAlumnoUtil.getMapAll(conEnoc,"");
			HashMap<String, aca.catalogo.CatPais> mapPais 				= aca.catalogo.PaisUtil.getMapAll(conEnoc,"");
			HashMap<String, aca.catalogo.CatEstado> mapEstado 			= EstadoU.getMapAll(conEnoc,"");
			HashMap<String, aca.catalogo.CatCiudad> mapCiudad 			= CiudadU.getMapAll(conEnoc,"");
			HashMap<String, String> mapCategoria						= aca.bec.BecCategoriaUtil.getMapCategorias(conEnoc);
			HashMap<String, String> mapEmpleado							= aca.vista.MaestrosUtil.mapMaestroNombre(conEnoc, "APELLIDOS");
			
			// Map de departamentos
			HashMap<String, aca.financiero.ContCcosto> mapDepto			= aca.financiero.ContCcostoUtil.getMapCentrosCosto(conEnoc, "001-"+fechaHoy.substring(6));
			// Map de inscritos
			HashMap<String, aca.alumno.AlumPersonal> mapAlumno			= AlumnoU.getMapInscritos(conEnoc, "'"+cargaId+"'", fechaIni, fechaFin);
			// Map de datos academicos
			HashMap<String, aca.alumno.AlumPersonal> mapPersonal 		= AlumnoU.mapAlumnosEnCargasyModalidades(conEnoc, "'"+cargaId+"'", modalidades);
			// Map de datos academicos
			HashMap<String, aca.alumno.AlumAcademico> mapAcademico 		= AcademicoU.mapAcademicoCargasyModalidades(conEnoc, "'"+cargaId+"'", modalidades);
			// Consulta la cantidad de materias por alumno
			HashMap<String, String> mapMatPorAlumno						= KrdxCursoActU.mapMateriasPorAlumno(conEnoc, cargaId, modalidades, "'I','1','2','4','5','6'");
			// Consulta la cantidad de materias por inscritas que fueron reprobadas anteriormente por un alumno			
			HashMap<String, String> mapMatRepPorAlumno					= KrdxCursoActU.mapMatRepPorAlumno(conEnoc, cargaId, modalidades, "'I','1','2','4','5','6'", "'2','4'");
			// Consulta la cantidad de materias reprobadas en la carga actual 
			HashMap<String, String> mapMatRep							= KrdxCursoActU.mapMateriasPorAlumno(conEnoc, cargaId, modalidades, "'2','4'");
			// Consulta la cantidad de materias por alumno
			HashMap<String, String> mapCredPorAlumno					= KrdxCursoActU.mapCreditosPorAlumno(conEnoc, cargaId, modalidades, "'I','1','2','4','5','6'");
			// Consulta la cantidad de materias por inscritas que fueron reprobadas anteriormente por un alumno
			HashMap<String, String> mapCredRepPorAlumno					= KrdxCursoActU.mapCredRepPorAlumno(conEnoc, cargaId, modalidades, "'I','1','2','4','5','6'", "'2','4'");
			// Consulta la cantidad de materias por inscritas que fueron reprobadas anteriormente por un alumno
			HashMap<String, String> mapCostoRepPorAlumno				= KrdxCursoActU.mapCostoRepPorAlumno(conEnoc, cargaId, modalidades, "'I','1','2','4','5','6'", "'2','4'");
			// Consulta los promedios de los alumnos
			HashMap<String, String> mapPromedios						= aca.alumno.EstadoUtil.mapAlumPromCargasyModalidades(conEnoc, "'"+cargaId+"','"+carga2+"','"+carga3+"'", modalidades, "I");			
			// Consulta los promedios de los alumnos
			HashMap<String, String> mapMentor							= aca.mentores.MentAlumnoUtil.mapMentorAlumno(conEnoc, cargaId.substring(0,4));
			// Map de semestre o tetra del alumno
			HashMap<String, String> mapCiclo							= aca.financiero.FesCcobroUtil.mapAlumCicloEnCarga(conEnoc, cargaId);
			// Map de entrevistas del mentor al alumno
			HashMap<String, String> mapEntAlumnoMentor					= MentContactoU.mapEntDelAlumnoPorMentor(conEnoc, fechaIni, fechaFin);
			// Map de entrevistas del alumno
			HashMap<String, String> mapEntAlumno						= MentContactoU.mapEntrevistasAlumno(conEnoc, fechaIni, fechaFin);
			
			
			lisInsc = AlumEstadoU.getListAll(conEnoc, "WHERE ESTADO IN ('I','3') AND CARGA_ID IN('"+cargaId+"')"+
					" AND MODALIDAD_ID IN ("+modalidades+") "+
					" AND FECHA BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"+
					" ORDER BY ENOC.FACULTAD(CARRERA_ID), CARRERA_ID, MODALIDAD_ID, ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");
			for(int i=0; i<lisInsc.size(); i++){			
				aca.alumno.AlumEstado insc = (aca.alumno.AlumEstado) lisInsc.get(i);
				if (!insc.getCodigoPersonal().equals(codigoTemp)){
					codigoTemp = insc.getCodigoPersonal();
					if( Acceso.getAccesos().indexOf(insc.getCarreraId())!= -1 || Acceso.getAdministrador().equals("S") || Acceso.getSupervisor().equals("S") ){
					    cont++;
						nInscritos++;
						
						String bautizado 	= "NO";
	        			String genero 		= "-";
	        			String edoCivil		= "-";
	        			String fechaNac		= "01/01/1950"; 
	        			if (mapPersonal.containsKey(insc.getCodigoPersonal())){
	        				
	        				AlumPersonal =  mapPersonal.get(insc.getCodigoPersonal());
	        				
	        				bautizado 	= AlumPersonal.getBautizado();
	        				if (bautizado.equals("S")) bautizado = "SI"; 
	        				genero 		= mapPersonal.get(insc.getCodigoPersonal()).getSexo();
	        				if (genero.equals("M")) genero = "H"; else genero = "M";
	        				edoCivil 	= mapPersonal.get(insc.getCodigoPersonal()).getEstadoCivil();
	        				if (edoCivil.equals("S")){
	        					edoCivil = "Soltero";
	        				}else if (edoCivil.equals("C")){
	        					edoCivil = "Casado";
	        				}else if (edoCivil.equals("D")){
	        					edoCivil = "Viudo";
	        				}else if (edoCivil.equals("V")){
	        					edoCivil = "Divorciado";
        					}
	        				fechaNac = mapPersonal.get(insc.getCodigoPersonal()).getFNacimiento();
	        			}
	        			
	        			// Calcula la edad del alumno 
	        			int edad = 0;
	        			if (fechaNac.length()==10 && fechaHoy.length()==10) 
	        				edad = aca.util.Fecha.edad(fechaNac, fechaHoy);
						
						// Busca la facultad y la carrera
						String nombreCarrera 	= "-";
						String nombreFacultad 	= "-";
						if (mapCarrera.containsKey( insc.getCarreraId() )){
							// Nombre de la carrera
							facultadId 		= mapCarrera.get( insc.getCarreraId() ).getFacultadId();						
							nombreCarrera 	= mapCarrera.get( insc.getCarreraId() ).getNombreCarrera();
							// Nombre de la Facultad
							if (mapFacultad.containsKey(facultadId)){
								nombreFacultad	= mapFacultad.get(facultadId).getNombreCorto();
							}
						}
						
						// Verifica si está inscrito
						String esInscrito = "NO";
						if (mapAlumno.containsKey( insc.getCodigoPersonal() )){
							esInscrito = "SI";	
						}		  
	        				
	        			// Obtiene el nombre de la religion
	        			String religion = "";
	        			if (mapReligion.containsKey(AlumPersonal.getReligionId())){
	        				aca.catalogo.CatReligion rel = mapReligion.get(AlumPersonal.getReligionId());
	        				religion = rel.getNombreCorto();
	        			}        			
	        			
	        			// Obtiene el nombre del tipo de alumno 
	        			String tipo 		= "0";
	        			String dormitorio 	= "0"; 
	        			if (mapAcademico.containsKey(insc.getCodigoPersonal())){
	        				AlumAcademico = mapAcademico.get(insc.getCodigoPersonal());
	        				dormitorio 	= AlumAcademico.getDormitorio(); 
	        				if (mapTipo.containsKey(AlumAcademico.getTipoAlumno())){
	            				aca.catalogo.CatTipoAlumno tipoAlumno = mapTipo.get(AlumAcademico.getTipoAlumno());
	            				tipo 		= tipoAlumno.getNombreTipo();            				
	            			}          					
	        			}    
	        			
	        			// Obtiene el nombre del pais
	        			String paisId 	= "0";
	        			String paisNombre 	= "-";	        			
	        			if (mapPais.containsKey(AlumPersonal.getPaisId())){	
	        				paisId 		= mapPais.get(AlumPersonal.getPaisId()).getPaisId();
	        				paisNombre 	= mapPais.get(AlumPersonal.getPaisId()).getNombrePais();
	        			}    
	        			
	        			String nombreDivision = "-";
	        			if (mapPais.containsKey(paisId)) nombreDivision = DivisionU.getNombreCorto(conEnoc, mapPais.get(paisId).getDivisionId());
	        			
	        			// Obtiene el nombre del estado
	        			String estado = "-";
	        			if (mapEstado.containsKey(AlumPersonal.getPaisId()+AlumPersonal.getEstadoId())){        				
	        				estado 	= mapEstado.get(AlumPersonal.getPaisId()+AlumPersonal.getEstadoId()).getNombreEstado(); 
	        			}
	        			
	        			// Obtiene el nombre del estado
	        			String ciudad = "-";
	        			if (mapCiudad.containsKey(AlumPersonal.getPaisId()+AlumPersonal.getEstadoId()+AlumPersonal.getCiudadId())){        				
	        				ciudad 	= mapCiudad.get(AlumPersonal.getPaisId()+AlumPersonal.getEstadoId()+AlumPersonal.getCiudadId()).getNombreCiudad();
	        			}
	        			
	        			String razon 		= "-";
	        			String razonNombre 	= "-";
	        			String direccion 	= "-";
	        			String viveTutor	= "-";
	        			if (insc.getResidenciaId().equals("E")){
	        				ResDatos.setMatricula(insc.getCodigoPersonal());
	        				if (ResDatos.existeReg(conEnoc)){
		        				ResDatos.mapeaRegId(conEnoc, insc.getCodigoPersonal());
		        				razon 		= ResDatos.getRazon();
		        				razonNombre	= aca.residencia.ResRazon.nombreRazon(conEnoc, razon);
		        				direccion 	= "Calle:"+ResDatos.getCalle()+" #"+ResDatos.getNumero()+" Colonia:"+ResDatos.getColonia();
		        				viveTutor	= ResDatos.getNombreTut()+" "+ResDatos.getApellidoTut();
		        				
		        				//direccion 	= aca.residencia.ResDatos.getDireccion(conEnoc, insc.getCodigoPersonal());
		        			}	        				
	        			}else{
	        				direccion = "UM";
	        			}
	        			
	        			if ( (insc.getResidenciaId().equals("I") || insc.getResidenciaId().equals("i")) && razonNombre.equals("-")) razonNombre = "Interno";
	        			if (insc.getResidenciaId().equals("E") && razonNombre.equals("-")) razonNombre = "SIN RAZON";
	        			
	        			String numComidas	= aca.internado.ComAutorizacionUtil.numComidas(conEnoc, insc.getCodigoPersonal(), insc.getCargaId(), insc.getBloqueId());
	        			
	        			String turno = "-";
	        			// Prepa, Ingenieria y Teología, Salud
	        			if (facultadId.equals("107") || facultadId.equals("104") || facultadId.equals("105")|| facultadId.equals("103")){
	        				turno = "V";
	        			}
	        			
	        			// Arte, Administracion, Odonto, Educacion, Psicologia, Música
	        			if (facultadId.equals("108")||facultadId.equals("101")||facultadId.equals("114")||facultadId.equals("102")||facultadId.equals("112")||facultadId.equals("109")){
	        				turno = "M";
	        			}
	        			// Arquitectura
	        			if (insc.getCarreraId().equals("10815")){
	        				turno = "V";
	        			}
	        			// Terapia, Enfermeria
	        			if (insc.getCarreraId().equals("10314")|| insc.getCarreraId().equals("10304")){
	        				turno = "M";
	        			}
	        			
	        			// Medicina de primer año estan en turno matutino
	        			if (insc.getCarreraId().equals("10301")){
	        				String ciclo = "0";
	        				if (mapCiclo.containsKey(insc.getCodigoPersonal()+insc.getCargaId()+insc.getBloqueId())){
	        					ciclo = mapCiclo.get(insc.getCodigoPersonal()+insc.getCargaId()+insc.getBloqueId());
	        					if (ciclo.equals("1")||ciclo.equals("2")){
	        						turno = "M";
	        					}
	        				}
	        			}	        			
	        			
	        			String puestoAlumno 	= aca.bec.BecPuestoAlumnoUtil.getPuestoAlumno(conEnoc, insc.getCodigoPersonal(), insc.getCargaId());
	        			String depto			= "-"; 
	        			String deptoNombre		= "-";
	        			String categoriaId		= "0";
	        			String categoriaNombre 	= "-";        			
	        			if (!puestoAlumno.equals("-")){
	        				BecPuestoAlumno.setPuestoId(puestoAlumno);
	        				if (BecPuestoAlumnoU.existeReg(conEnoc, puestoAlumno)){
	        					BecPuestoAlumno.mapeaRegId(conEnoc);	        					
	            				depto 			= BecPuestoAlumno.getIdCcosto();
	            				if (mapDepto.containsKey(depto)){
	            					deptoNombre = mapDepto.get(depto).getNombre();
	            				}
	            				categoriaId 	= BecPuestoAlumno.getCategoriaId();
	            				if (mapCategoria.containsKey(categoriaId)){
	            					categoriaNombre = mapCategoria.get(categoriaId);
	            				}
	        				}        			
	        			} 
	        			
	        			// Promedio carga seleccionada
	        			String prom1 = "0";	        				        			
	        			if (mapPromedios.containsKey(cargaId+insc.getCodigoPersonal())){
	        				prom1 = mapPromedios.get(cargaId+insc.getCodigoPersonal());	        			
	        			}
	        			
	        			// promedio de la carga anterior
	        			String prom2 = "0";
	        			if (mapPromedios.containsKey(carga2+insc.getCodigoPersonal())){
	        				prom2 = mapPromedios.get(carga2+insc.getCodigoPersonal());
	        			}
	        			
	        			// promedio de dos cargas antes de la seleccionada
	        			String prom3 = "0";
	        			if (mapPromedios.containsKey(carga3+insc.getCodigoPersonal())){
	        				prom3 = mapPromedios.get(carga3+insc.getCodigoPersonal());
	        			}
	        			
	        			// Numero de materias inscritas
	        			String totMat = "0";
	        			if (mapMatPorAlumno.containsKey(insc.getCodigoPersonal())){
	        				totMat = mapMatPorAlumno.get(insc.getCodigoPersonal());
	        			}
	        			
	        			// Numero de materias reprobadas
	        			String totRep = "0";
	        			if (mapMatRep.containsKey(insc.getCodigoPersonal())){
	        				totRep = mapMatRep.get(insc.getCodigoPersonal());
	        			}
	        			
	        			// Numero de materias cursadas por segunda vez
	        			String totMatRep = "0";
	        			if (mapMatRepPorAlumno.containsKey(insc.getCodigoPersonal())){
	        				totMatRep = mapMatRepPorAlumno.get(insc.getCodigoPersonal());
	        			}   			
	        			
	        			// Total de créditos inscritos
	        			String totCred = "0";
	        			if (mapCredPorAlumno.containsKey(insc.getCodigoPersonal())){
	        				totCred = mapCredPorAlumno.get(insc.getCodigoPersonal());
	        			}
	        			
	        			// Total de creditos cursados por segunda vez
	        			String totCredRep = "0";
	        			if (mapCredRepPorAlumno.containsKey(insc.getCodigoPersonal())){
	        				totCredRep = mapCredRepPorAlumno.get(insc.getCodigoPersonal());
	        			}
	        			
	        			// Costo de las materias cursadas por segunda vez
	        			String totCostoRep = "0";
	        			if (mapCostoRepPorAlumno.containsKey(insc.getCodigoPersonal())){
	        				totCostoRep = mapCostoRepPorAlumno.get(insc.getCodigoPersonal());
	        			}
	        			
	        			String mentorId 		= "-";
	        			String mentorNombre 	= "-";
	        			if (mapMentor.containsKey(insc.getCodigoPersonal()) ){
	        				mentorId = mapMentor.get(insc.getCodigoPersonal());
	        				if (mapEmpleado.containsKey(mentorId)){
	        					mentorNombre = mapEmpleado.get(mentorId); 
	        				}
	        			}
	        			
	        			origen 		= altasUtil.getNumCursos(conEnoc, cargaId, insc.getCodigoPersonal(), "AND TIPO IN('O','B') AND TIPOCAL_ID != 'M' ");
	        			altas		= altasUtil.getNumCursos(conEnoc, cargaId, insc.getCodigoPersonal(), "AND TIPO = 'A' AND TIPOCAL_ID != 'M' ");
	        			bajas 		= altasUtil.getNumCursos(conEnoc, cargaId, insc.getCodigoPersonal(), "AND TIPO = 'B' AND TIPOCAL_ID  = '3' ");
	        			total 		= (origen + altas) - bajas;
	        			
	        			double saldo 		= 0;
	        			double saldoVencido	= 0;
						if(mapSaldos.containsKey(insc.getCodigoPersonal())){
							aca.financiero.SaldosAlumnos sal = mapSaldos.get( insc.getCodigoPersonal() );
							saldo 			= Double.parseDouble(sal.getSaldoGlobal());
							saldoVencido	= Double.parseDouble(sal.getSVencido()); 
						}						
						
						String entAlumno = "0";
						if (mapEntAlumno.containsKey( insc.getCodigoPersonal() )){
							entAlumno = mapEntAlumno.get( insc.getCodigoPersonal());
						}
						
						String entMentor = "0";
						if (mapEntAlumnoMentor.containsKey( insc.getCodigoPersonal()+mentorId )){
							entMentor = mapEntAlumnoMentor.get( insc.getCodigoPersonal()+mentorId);
						}
%>
			<tr> 
			    <td><%=cont%></td>
				<td><%=nombreFacultad%></td>
			    <td align="center"><%=insc.getPlanId()%></td>
			    <td align="center"><%=insc.getCodigoPersonal()%></td>			    
			    <td><%=AlumPersonal.getApellidoPaterno()%> <%=AlumPersonal.getApellidoMaterno()%></td>
			    <td align="center"><%=AlumPersonal.getNombre()%></td>
			    <td align="center"><%=esInscrito%></td>
			    <td align="center"><%=AlumAcademico.getClasFin()%></td>
				<td align="center"><%=religion%></td>
				<td align="center"><%=bautizado%></td>
				<td align="center"><%=tipo%></td>
				<td align="center"><%=genero%></td>
				<td align="center"><%=edoCivil%></td>
				<td align="center"><%=edad%></td>
				<td align="center"><%=insc.getResidenciaId()%></td>
				<td align="center"><%=numComidas%></td>
				<td align="center"><%=dormitorio%></td>
				<td align="center"><%=razonNombre%></td>
				<td align="center"><%=viveTutor%></td>
				<td align="center"><%=direccion%></td>
				<td align="center"><%=paisNombre%></td>
				<td align="center"><%=estado%></td>
				<td align="center"><%=ciudad%></td>				
				<td align="center"><%=nombreDivision%></td>
				<td align="center"><%=turno%></td>
				<td align="center"><%=deptoNombre%></td>				
				<td class="left"><%=categoriaNombre%></td>
				<td class="right"><%=frmDecimal.format(Double.valueOf(prom1))%></td>
				<td class="right"><%=frmDecimal.format(Double.valueOf(prom2))%></td>
				<td class="right"><%=frmDecimal.format(Double.valueOf(prom3))%></td>
				<td class="right"><%=totMat%></td>
				<td class="right"><%=totRep%></td>
				<td class="right"><%=totMatRep%></td>
				<td class="right"><%=totCred%></td>
				<td class="right"><%=totCredRep%></td>
				<td class="right"><%=frmDecimal.format(Double.valueOf(totCostoRep))%></td>
				<td class="left"><%=mentorNombre%></td>
				<td class="left"><%=entMentor%></td>
				<td class="left"><%=entAlumno%></td>
				<td class="right"><%=origen%></td>
				<td class="right"><%=altas%></td>
				<td class="right"><%=bajas%></td>
				<td class="left"><%=total%></td>
				<td class="left"><%=frmDecimal.format(saldo)%></td>
				<td class="left"><%=frmDecimal.format(saldoVencido)%></td>
			</tr>
<%						          			
					} //fin del alumno academico
				}	// fin de codigo diferente
	 		} // fin del for
		}
%>				
	</table>
<%	}else{	%>
		<b><font color="#000099">No tiene Acceso..!</font></b>
<%	}//fin del if acceso		
%>
	
</div>

<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();
</script>

<%@ include file= "../../cierra_enoc.jsp" %>