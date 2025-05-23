<%@page import="aca.catalogo.CatCarreraUtil"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.vista.EstadisticaUtil"%>
<%@page import="aca.carga.spring.CargaAlumno"%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="acceso" scope="page" class="aca.acceso.Acceso"/>
<jsp:useBean id="AccesoU" scope="page" class="aca.acceso.AccesoUtil"/>
<jsp:useBean id="cargaU" scope="page" class="aca.carga.CargaUtil"/>
<jsp:useBean id="kardexU" scope="page" class="aca.kardex.ActualUtil"/>
<jsp:useBean id="EstadisticaU" scope="page" class="aca.vista.EstadisticaUtil"/>
<jsp:useBean id="Estadistica" scope="page" class="aca.vista.Estadistica"/>
<jsp:useBean id="CarreraU" scope="page" class="aca.catalogo.CatCarreraUtil"/>
<jsp:useBean id="FacultadU" scope="page" class="aca.catalogo.FacultadUtil"/>
<jsp:useBean id="CatPeriodoUtil" scope="page" class="aca.catalogo.CatPeriodoUtil"/>
<jsp:useBean id="Carga" scope="page" class="aca.carga.Carga"/>
<jsp:useBean id="ReligionDao" scope="page" class="aca.catalogo.CatReligionDao"/>
<jsp:useBean id="EstadoU" scope="page" class="aca.catalogo.EstadoUtil"/>
<jsp:useBean id="CiudadU" scope="page" class="aca.catalogo.CiudadUtil"/>
<jsp:useBean id="CatTipoU" scope="page" class="aca.catalogo.TipoAlumnoUtil"/>
<jsp:useBean id="AlumPersonalU" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="AlumPlanU" scope="page" class="aca.alumno.PlanUtil"/>


<script type="text/javascript">	
	function Mostrar(){		
		document.forma.submit();
	}
	
	function cambiaPeriodo(periodoId){
		document.location.href="inscritos_per?PeriodoId="+periodoId+"&cambioPeriodo=1";
	}
	
	function cambiaCarga(){		
		document.location.href="inscritos_per?CargaId="+document.forma.CargaId.value+"&cambioCarga=S";
	}
</script>

<head>		
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
</head>	

<!-- inicio de estructura -->
<%
	java.text.DecimalFormat frmDecimal= new java.text.DecimalFormat("###,##0.00; -###,##0.00");
	
	String cargaId 			= (String)session.getAttribute("cargaId");
	String modalidades		= (String)session.getAttribute("modalidades") == null?"'1'":session.getAttribute("modalidades").toString();
	String cargas			= (String)session.getAttribute("cargas") == null?aca.carga.CargaUtil.getCargasActivas(conEnoc, aca.util.Fecha.getHoy()):session.getAttribute("cargas").toString();	
	String fechaIni			= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin			= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");

	String codigo			= (String) session.getAttribute("codigoPersonal");	
	String sMatricula 		= "X";
	String sNombreAlumno 	= "X";
	String sFacultad 		= "X";
	String sCarrera 		= "X";
	String sCarreraTemp		= "X";
	String sNombreFacultad 	= "X";
	String sNombreCarrera	= "X";
	String sSexo 			= "X";
	String sResidencia 		= "X";
	String sModalidad		= "0";	
	String sFechaNac 		= "X";
	String religionId		= "0";
	String religion			= "";
	String pais				= "";	
	String hoy 				= aca.util.Fecha.getHoy();
	String fechaHoy[]		= hoy.split("/");			
	
	int yearHoy				= Integer.parseInt(fechaHoy[2]);
	int	mesHoy				= Integer.parseInt(fechaHoy[1]);
	int diaHoy				= Integer.parseInt(fechaHoy[0]);
	
	int yearNac				= 0;
	int	mesNac				= 0;
	int diaNac				= 0;
	
	int edad				= 0;
	
	acceso = AccesoU.mapeaRegId(conEnoc, codigo);	
	String sBgcolor			= "";
	
	int cont 				= 1;
	int nInscritos 			= 0, nCalculos 		= 0;
	int nHombres			= 0, nMujeres		= 0;
	int nInternos			= 0, nExternos 		= 0;
	int nNacional			= 0, nExtranjero	= 0;
	int i 					= 0;	
	
	int nMexicanos = 0, nExtranjeros = 0, nAdventistas = 0, nNoAdventistas = 0;
	int inscritosFac = 0, internosFac = 0, externosFac = 0, hombresFac = 0,	mujeresFac = 0, mexicanos = 0, extranjeros = 0, adventistas = 0, noAdventistas = 0;
	if (cargaId==null){ cargaId	= (String)session.getAttribute("cargaId");  }
	
	HashMap<String,String> mapAlumComidas 	= (HashMap<String,String>) request.getAttribute("mapAlumComidas");
%>
<div class="container-fluid">	
	<h3>Reporte de Inscritos<small class="text-muted fs-5">( <%=cargaId%> )</small></h3>
	<div class="alert alert-info">
		<a href="inscritos_per" class="btn btn-primary">Regresar</a>
	</div>	
<%	
	// Map para contar numero total de materias
	java.util.HashMap<String, String> mapMaterias 			= kardexU.getNumAlumMatTipoModalidad(conEnoc, cargaId, modalidades, "'I','1','2','3','4','5','6'");
	// Map para contar numero de materias acreditadas
	java.util.HashMap<String, String> mapAcreditadas		= kardexU.getNumAlumMatTipoModalidad(conEnoc, cargaId, modalidades,"'1'");
	// Map para contar numero de materias acreditadas
	java.util.HashMap<String, String> mapReprobadas 		= kardexU.getNumAlumMatTipoModalidad(conEnoc, cargaId,modalidades, "'2','4'");
	// Map para contar numero de materias acreditadas
	java.util.HashMap<String, String> mapPendientes			= kardexU.getNumAlumMatTipoModalidad(conEnoc, cargaId,modalidades, "'I','5','6'");
	// Map para contar numero de materias acreditadas
	java.util.HashMap<String, String> mapBajas 				= kardexU.getNumAlumMatTipoModalidad(conEnoc, cargaId,modalidades, "'3'");
	// Map para contar numero de materias acreditadas
	java.util.HashMap<String, String> mapCreditos 			= kardexU.getNumAlumCreditosModalidad(conEnoc, cargaId, modalidades,"'I','1','2','3','4','5','6'");
	// Map para contar numero de materias acreditadas
	java.util.HashMap<String, String> mapCreditosRep		= kardexU.getNumAlumCreditosModalidad(conEnoc, cargaId, modalidades,"'2','4'");	
	//Map de Carreras
	HashMap <String, aca.catalogo.CatCarrera> mapaCarrera 	= CarreraU.getMapAll(conEnoc, "ORDER BY NOMBRE_CARRERA");
	//Map de Carreras
	HashMap <String, aca.catalogo.CatTipoAlumno> mapaTipo	= CatTipoU.getMapAll(conEnoc, "");
	// Map de facultades
	HashMap <String, aca.catalogo.CatFacultad> mapaFacultad	= FacultadU.getMapAll(conEnoc, "ORDER BY NOMBRE_FACULTAD");
	// Map de modalidades
	HashMap <String, aca.catalogo.CatModalidad> mapaModalidad = aca.catalogo.ModalidadUtil.getMapAll(conEnoc, " ");
	// Map de países
	HashMap <String, aca.catalogo.CatPais> mapaPais 		= aca.catalogo.PaisUtil.getMapAll(conEnoc, "");
	// Map de estados
	HashMap <String, aca.catalogo.CatEstado> mapaEstado 	= EstadoU.getMapPaisEstado(conEnoc, "");
	// Map de ciudades
	HashMap <String, aca.catalogo.CatCiudad> mapaCiudad 	= CiudadU.getMapAll(conEnoc, "");
	// Map de semestres
	HashMap <String, aca.financiero.FesCcobro> mapaCobro 	= aca.financiero.FesCcobroUtil.getMapInscritos(conEnoc, cargaId);		
	// Map de religiones
	HashMap<String, aca.catalogo.CatReligion> mapReligion 	= ReligionDao.getMapAll(conEnoc,"");		
	// Map de religiones
	HashMap<String, aca.alumno.AlumPersonal> mapAlumnos 	= AlumPersonalU.mapAlumnosEnCargas(conEnoc, "'"+cargaId+"'");
	// Lista de alumnos inscritos en una carga
	ArrayList<aca.vista.Estadistica> lista 					= EstadisticaU.getListOrdenModalidad(conEnoc, cargaId, modalidades, "ORDER BY ENOC.FACULTAD(CARRERA_ID), CARRERA_ID, APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE");
	// Saldos de alumnos en la carga
	HashMap<String, aca.financiero.SaldosAlumnos> saldos 	= aca.financiero.SaldosAlumnosUtil.mapInscritosCarga(conEnoc, cargaId);

	HashMap<String, String> mapaPrimerMatricula 			= AlumPlanU.mapaPrimerMatricula(conEnoc, cargaId);
	
	HashMap<String, CargaAlumno> mapaCargasAlumno 	= (HashMap<String,CargaAlumno>) request.getAttribute("mapaCargasAlumno");
%>
	<table class="table table-condensed">
	<tr> 
		<th width="2%" height="22" align="center"><spring:message code="aca.Numero"/></th>
		<th width="4%" height="22" align="center"><spring:message code="aca.Plan"/></th>
	    <th width="4%" height="22" align="center"><b><spring:message code="aca.Matricula"/></b></th>
	    <th width="17%" height="22" align="center"><b><spring:message code="aca.Nombre"/></b></th>
	    <th width="10%" height="22" align="center"><b>Facultad</b></td>
	    <th width="10%" height="22" align="center"><b>Carrera</b></td>
	    <th width="10%" height="22" align="center"><b><spring:message code="aca.Correo"/></b></th>
	    <th width="5%" height="22" align="center"><b>F. Iscri.</b></th>
	    <th width="5%" height="22" align="center"><b><spring:message code="aca.Modalidad"/></b></th>
	    <th width="5%" height="22" align="center"><b>Ubicación</b></th>
	    <th width="2%" height="22" align="center"><b><spring:message code="aca.Res"/></b></th>
	    <th width="2%" height="22" align="center"><b><spring:message code="aca.Gen"/></b></th>
	    <th width="2%" height="22" align="center"><b>Sem.</b></th>
	    <th width="2%" height="22" align="center"><b>Rel.</b></th>
		<th width="4%" height="22" align="center"><b>F.Nac.</b></th>
		<th width="3%" height="22" align="center"><b>Pais</b></th>
		<th width="3%" height="22" align="center"><b><spring:message code="aca.Estado"/></b></th>
		<th width="3%" height="22" align="center"><b><spring:message code="aca.Ciudad"/></b></th>
		<th width="4%" height="22" align="center"><b>Tipo</b></th>
		<th width="3%" height="22" align="center"><b>N° Mat</b></th>
		<th width="2%" height="22" align="center"><b>N° AC</b></th>
		<th width="2%" height="22" align="center"><b>N° NA</b></th>
		<th width="2%" height="22" align="center"><b>N° CP</b></th>
		<th width="2%" height="22" align="center"><b>N° BA</b></th>
		<th width="2%" height="22" align="center"><b>N° Cr</b></th>
		<th width="3%" height="22" align="center"><b>N° Cr.NA</b></th>	
		<th width="3%" height="22" align="center"><b>N° Comidas</b></th>	
		<th width="3%" height="22" align="center"><b>1er. Ingreso</b></th>				
	</tr>
<%	
	String idFacultad			= "X";
	String codigoNuevo			= "";
	String codigoViejo			= "X";
    for(aca.vista.Estadistica obj : lista){		
		sMatricula 			= obj.getCodigoPersonal();		
		sSexo 				= obj.getSexo();
		sResidencia 		= obj.getResidenciaId();		
		sCarreraTemp 		= obj.getCarreraId();			
		sFechaNac 			= obj.getFNacimiento();			
		idFacultad 			= obj.getFacultadId();
		religionId			= obj.getReligionId();
		
		codigoNuevo = sMatricula;
		if(!codigoNuevo.equals(codigoViejo)){
			if( acceso.getAccesos().indexOf(sCarreraTemp)!= -1 || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") ){		
				nInscritos++;
				if (sSexo.equals("M")){ nHombres++; }else{ nMujeres++; }	 			
				if (sResidencia.equals("Int.")){ nInternos++; }else{ nExternos++; }
				
				if(mapaFacultad.containsKey(idFacultad)){
					sNombreFacultad = mapaFacultad.get(idFacultad).getNombreCorto();
				}else{
					sNombreFacultad = "";			
				}
				if(mapaCarrera.containsKey(sCarreraTemp)){
       				sNombreCarrera 	= mapaCarrera.get(sCarreraTemp).getNombreCarrera();
       			}else{
       				sNombreCarrera = "";
       			}		  
				// Busca los valores en los maps
				String totMaterias = "0";
				if (mapMaterias.containsKey(sMatricula)) totMaterias = mapMaterias.get(sMatricula);
				
				String totAcreditadas = "0";
				if (mapAcreditadas.containsKey(sMatricula)) totAcreditadas = mapAcreditadas.get(sMatricula);
				
				String totReprobadas = "0";
				if (mapReprobadas.containsKey(sMatricula)) totReprobadas = mapReprobadas.get(sMatricula);
				
				String totPendientes = "0";
				if (mapPendientes.containsKey(sMatricula)) totPendientes = mapPendientes.get(sMatricula);
				
				String totBajas = "0";
				if (mapBajas.containsKey(sMatricula)) totBajas = mapBajas.get(sMatricula);
				
				String totCreditos = "0";
				if (mapCreditos.containsKey(sMatricula)) totCreditos = mapCreditos.get(sMatricula);
				
				String totCreditosRep = "0";
				if (mapCreditosRep.containsKey(sMatricula)) totCreditosRep = mapCreditosRep.get(sMatricula);
				
				String modalidad = "";
				if (mapaModalidad.containsKey(obj.getModalidadId()) ){
					modalidad = mapaModalidad.get(obj.getModalidadId()).getNombreModalidad();				
				}
				
				String nacionalidad = "";
				if(mapaPais.containsKey(obj.getPaisId())){
					nacionalidad = mapaPais.get(obj.getPaisId()).getNombrePais();
				}
				
				String estado = "";
				if(mapaEstado.containsKey(obj.getPaisId()+obj.getEstadoId())){
					estado = mapaEstado.get(obj.getPaisId()+obj.getEstadoId()).getNombreEstado();
				}
				
				String ciudad = "";
				if(mapaCiudad.containsKey(obj.getPaisId()+obj.getEstadoId()+obj.getCiudadId())){
					ciudad = mapaCiudad.get(obj.getPaisId()+obj.getEstadoId()+obj.getCiudadId()).getNombreCiudad();
				}
				
				String semestre = "";
				if(mapaCobro.containsKey( obj.getCodigoPersonal()+obj.getCargaId()+obj.getBloqueId() )){
					semestre = mapaCobro.get(obj.getCodigoPersonal()+obj.getCargaId()+obj.getBloqueId()).getSemestre();
				}
				inscritosFac++;
				if (sResidencia.equals("Int.")){ internosFac++; }else{ externosFac++; }			
     				if(obj.getSexo().equals("M")){
     					hombresFac++;
     					nHombres++;
     				}else{
     					mujeresFac++;
     					nMujeres++;
     				}         				
     				
     				// Obtiene el nombre de la religion
     				religion = "";
     				if (mapReligion.containsKey(obj.getReligionId())){
     					aca.catalogo.CatReligion rel = mapReligion.get(obj.getReligionId());
     					religion = rel.getNombreCorto();
     				}
     				
     				if(obj.getReligionId().equals("1")){
     					adventistas++;
     					nAdventistas++;
     				}
     				else{
     					noAdventistas++;
     					nNoAdventistas++;
     				}
     			// Obtiene el nombre del pais
     				pais = "";
     				if (mapaPais.containsKey(obj.getPaisId())){
     					aca.catalogo.CatPais p = mapaPais.get(obj.getPaisId());
     					pais 	= p.getNacionalidad();
     				}
     				
     				if(obj.getPaisId().equals("91")){
     					mexicanos++;
     					nMexicanos++;
     				}
     				else{
     					extranjeros++;
     					nExtranjeros++;
     				}
				/*
				double deudaHoy 	= 0;
				double saldo 		= 0;
				if(mapSaldo.containsKey(obj.getCodigoPersonal())){
					aca.financiero.SaldosAlumnos saldoAlumno = mapSaldo.get(obj.getCodigoPersonal());
					deudaHoy	= Double.parseDouble(saldoAlumno.getSVencido());
					saldo		= Double.parseDouble(saldoAlumno.getSaldo());
				}
				*/
				// Obtiene el nombre de la religion
     				String religionNombre = "";
     				if (mapReligion.containsKey(religionId)){
     					aca.catalogo.CatReligion rel = mapReligion.get(religionId);
     					religionNombre = rel.getNombreCorto();
     				}
     				
				if ((cont % 2) == 0 ) sBgcolor = sColor; else sBgcolor = "";
				
				String[] nacimiento = sFechaNac.split("-");
				yearNac 			= Integer.parseInt(nacimiento[2]);
				mesNac 				= Integer.parseInt(nacimiento[1]);
				diaNac 				= Integer.parseInt(nacimiento[0]);
				
				edad = yearHoy - yearNac;
				
				if (mesHoy - mesNac < 0){
					edad = edad - 1;
				}else if (mesHoy - mesNac == 0){
					if(diaHoy - diaNac < 0){
						edad = edad - 1;
					}
				}					
				
				double saldo 	= 0;	
				
				if(saldos.containsKey(obj.getCodigoPersonal())){
					aca.financiero.SaldosAlumnos sal = saldos.get(obj.getCodigoPersonal());
					saldo 	= Double.parseDouble(sal.getSaldoGlobal());		
				}
																		
				String color = "#AE2113";
				if(saldo>=0) color = "green";
				
				String tipoAlumno 	= "-";
				if (mapaTipo.containsKey(obj.getTipoAlumnoId())){
					tipoAlumno = mapaTipo.get( obj.getTipoAlumnoId()).getNombreTipo();
				}
				
				String correo = "-";
				if (mapAlumnos.containsKey(obj.getCodigoPersonal())){
					correo = mapAlumnos.get(obj.getCodigoPersonal()).getEmail();
				}
					
				String fechaInscripcion = obj.getFInscripcion().substring(0, 10);

				String[] arrFecha = fechaInscripcion.split("-");
				
				fechaInscripcion = arrFecha[2]+"/"+arrFecha[1]+"/"+arrFecha[0];
				
				String comidas = "-";
				if (mapAlumComidas.containsKey(obj.getCodigoPersonal()) ){
					comidas = mapAlumComidas.get(obj.getCodigoPersonal());				
				}
				
				String primerIngreso = "No";
				if (mapaPrimerMatricula.containsKey(obj.getCodigoPersonal()+obj.getPlanId())){
					String fec = mapaPrimerMatricula.get(obj.getCodigoPersonal()+obj.getPlanId());						
					if(fec.equals(obj.getFInscripcion())){
						primerIngreso = "Si";
					}
				}
				
				String asistencia = "Hogar/Virtual";
				if (mapaCargasAlumno.containsKey(obj.getCodigoPersonal()+obj.getCargaId()+obj.getBloqueId())){
					if(mapaCargasAlumno.get(obj.getCodigoPersonal()+obj.getCargaId()+obj.getBloqueId()).getModo().equals("P")){
						asistencia = "Campus UM";
					}
				}
	%>
	  <tr class="tr2" <%=sBgcolor%>> 
	    <td><font size="1">&nbsp;<%=cont%></font></td>
		<td><font size="1"><%=obj.getPlanId()%></font></td>
	    <td align="center"><font size="1"><%=sMatricula%></font></td>    
	    <td><font size="1"><%=obj.getApellidoPaterno()+" "+obj.getApellidoMaterno()+" "+obj.getNombre()%></font></td>
	    <td align="center"><font size="1"><%=sNombreFacultad%></font></td>
	    <td align="center"><font size="1"><%=sNombreCarrera%></font></td>
	    <td><font size="1"><%=correo%></font></td>
	    <td align="center"><font size="1"><%= fechaInscripcion%></font></td>    
	    <td align="center"><font size="1"><%= modalidad%></font></td>    
	    <td align="center"><font size="1"><%= asistencia%></font></td>
	    <td align="center"><font size="1"><%= sResidencia%></font></td>  
	    <td align="center"><font size="1"><%= sSexo%></font></td>    
	    <td align="center"><font size="1"><%= semestre%></font></td>
	    <td align="center"><font size="1"><%= religionNombre%></font></td>
		<td align="center"><font size="1"><%= edad %></font></td>
		<td align="center"><font size="1"><%= nacionalidad%></font></td>
		<td align="center"><font size="1"><%= estado %></font></td>
		<td align="center"><font size="1"><%= ciudad %></font></td>
		<td align="center"><font size="1"><%= tipoAlumno%></font></td>
		<td class="right"><font size="1"><%= totMaterias%></font></td>
		<td class="right"><font size="1"><%= totAcreditadas%></font></td>
		<td class="right"><font size="1"><%= totReprobadas %></font></td>
		<td class="right"><font size="1"><%= totPendientes %></font></td>
		<td class="right"><font size="1"><%= totBajas %></font></td>
		<td class="right"><font size="1"><%= totCreditos%></font></td>
		<td class="right"><font size="1"><%= totCreditosRep%></font></td>
		<td class="right"><font size="1"><%= comidas%></font></td>	
		<td class="right"><font size="1"><%= primerIngreso%></font></td>
	  </tr>
	  <%
	     			cont++;
	  
				}
			
	    	}
			codigoViejo = codigoNuevo;
 		} // fin del while	 
 		
%>
		<tr>
			<th colspan="4"><font size="1">Totales</font></th>
			<th colspan="16">
				<font size="1">
					[ Internos: <%=internosFac %> ]&nbsp;&nbsp;
					[ Externos: <%=externosFac%> ]&nbsp;&nbsp;
					[ Mujeres: <%=mujeresFac%> ]&nbsp;&nbsp;
					[ Hombres: <%=hombresFac%> ]&nbsp;&nbsp;
					[ Mexicanos: <%=mexicanos%> ]&nbsp;&nbsp;
					[ Extranjeros: <%=extranjeros%> ]&nbsp;&nbsp;
					[ ASD: <%=adventistas%> ]&nbsp;&nbsp;
					[ NO ASD: <%=noAdventistas%> ]&nbsp;&nbsp;
				</font>			
			</th>
		</tr>
	</table> 
	<div class="alert alert-info">
  		Inscritos: <%=nInscritos%>&nbsp;&nbsp;&nbsp;
  		Internos: <%=nInternos%>&nbsp;&nbsp;&nbsp;
    	Externos: <%=nExternos%>  	
	</div>
</div>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();
</script>
<%@ include file= "../../cierra_enoc.jsp" %>