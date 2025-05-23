<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="aca.util.Fecha"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.vista.spring.Estadistica"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.catalogo.CatModalidad"%>

<%@ include file= "../../headPortal.jsp" %>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="CargaU" scope="page" class="aca.carga.CargaUtil" />
<jsp:useBean id="facultadU" scope="page" class="aca.catalogo.CatFacultadUtil" />
<jsp:useBean id="carreraU" scope="page" class="aca.catalogo.CatCarreraUtil" />
<jsp:useBean id="estadisticaU" scope="page" class="aca.vista.EstadisticaUtil" />
<jsp:useBean id="catModalidad" scope="page" class="aca.catalogo.CatModalidad" />
<jsp:useBean id="catModalidadU" scope="page" class="aca.catalogo.ModalidadUtil" />
<jsp:useBean id="empEstadistica" scope="page" class="aca.emp.EmpEstadistica" />

<script type="text/javascript">	
	function Mostrar(){					
		document.forma.submit();
	}	
</script>
<%
	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String usuario 			= (String) session.getAttribute("codigoPersonal");
	String cargas 			= (String)request.getAttribute("cargas");
	String modalidades		= (String)request.getAttribute("modalidades");
	
	String fechaIni			= (String)request.getAttribute("fechaIni");
	String fechaFin			= (String)request.getAttribute("fechaFin");
	String codigoNacional	= (String)request.getAttribute("codigoNacional");
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	String matricula		= "";	
	double promedio			= 0;	
	int cont				= 0;
	int contRepetidos		= 0;
	
	List<CatFacultad> lisFacultad		= new ArrayList<CatFacultad>();
	List<CatCarrera> lisCarrera			= new ArrayList<CatCarrera>();
	List<Estadistica> lisEstadistica	= new ArrayList<Estadistica>();
	List<Estadistica> lisRepetidos		= new ArrayList<Estadistica>();	
	
	String lisModo[] 		= (String[])request.getAttribute("lisModo");
	String lisCarga[] 		= (String[])request.getAttribute("lisCarga");
	
	HashMap<String, String> mapCreditosCero 	= new HashMap<String, String>();
	HashMap<String,String> mapaModalidad 		= (HashMap<String,String>)request.getAttribute("mapaModalidad");
	HashMap<String, Carga> mapaCargas 			= (HashMap<String, Carga>)request.getAttribute("mapaCargas");
	HashMap<String,String> mapaNombreCarrera 	= (HashMap<String,String>)request.getAttribute("mapaNombreCarrera");
	
	HashMap<String, List<CatFacultad>> mapaListaFacultad 				= (HashMap<String,List<CatFacultad>>)request.getAttribute("mapaListaFacultad");
	HashMap<String, List<CatCarrera>> mapaListaCarrera 					= (HashMap<String,List<CatCarrera>>)request.getAttribute("mapaListaCarrera");
	HashMap<String, HashMap<String, String>> mapaMapaCreditos			= (HashMap<String,HashMap<String, String>>)request.getAttribute("mapaMapaCreditos");
	HashMap<String, List<Estadistica>> mapaLisEstadistica				= (HashMap<String, List<Estadistica>>)request.getAttribute("mapaLisEstadistica");
	HashMap<String, HashMap<String, String>> mapaMapIngresoFacUm		= (HashMap<String,HashMap<String, String>>)request.getAttribute("mapaMapIngresoFacUm");
	HashMap<String, HashMap<String, String>> mapaMapIngresoFacPlan		= (HashMap<String,HashMap<String, String>>)request.getAttribute("mapaMapIngresoFacPlan");
	HashMap<String, HashMap<String, String>> mapaMapIngresoCarreraUm	= (HashMap<String,HashMap<String, String>>)request.getAttribute("mapaMapIngresoCarreraUm");
	HashMap<String, HashMap<String, String>> mapaMapIngresoCarreraPlan	= (HashMap<String,HashMap<String, String>>)request.getAttribute("mapaMapIngresoCarreraPlan");
	HashMap<String, List<Estadistica>> mapaLisRepetidos					= (HashMap<String, List<Estadistica>>)request.getAttribute("mapaLisRepetidos");
	
	Carga carga 			= new Carga();
	CatFacultad facultad 	= new CatFacultad();	
	CatCarrera carrera 		= new CatCarrera();	
	Estadistica estadistica	= new Estadistica();	
	
%>
<head>		
	<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
	<style>
		.titulo td{
			border:1px solid gray;
		}
	</style>
</head>	
<body>
<div class="container-fluid">
	<h2>Enrolled Students Statistics</h2>
	<form id="forma" name="forma" action="estadistica" method="post">
		<div class="alert alert-info">
			<b>Loads: </b> <%= cargas.replace("'", "")%>&nbsp;<a href="cargas" class="btn btn-primary">Select</a>&nbsp;&nbsp;
			<b>Modalities: </b>
<%
		if(!lisModo[0].equals("")) {
			for(String mod : lisModo){
				String nombreModalidad = "";
				if(mapaModalidad.containsKey(mod)){
					nombreModalidad = mapaModalidad.get(mod);
				}
				out.print("["+nombreModalidad+"] ");
			}
		}else{
			String nombreModalidad = "";
			if(mapaModalidad.containsKey(modalidades.replace("'", ""))){
				nombreModalidad = mapaModalidad.get(modalidades.replace("'", ""));
			}
			out.print("["+nombreModalidad+"] ");
		}
%>	
			<a href="modalidades" class="btn btn-primary">Select</a>
		</div>	
		<div class="alert alert-info">		 
			Start Date: <input id="FechaIni" name="FechaIni" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
			End Date: <input id="FechaFin" name="FechaFin" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
			<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>		 
		</div>	
	</form>		
	<table style="width:100%" class="table table-sm table-bordered">
<%
	int tInscritos		= 0;
	int tInternos		= 0;
	int tExternos		= 0;
	int tHombres		= 0;
	int tMujeres		= 0;
	int tMexicanos		= 0;
	int tExtranjeros	= 0;
	int tAcfe			= 0;
	int tNoAcfe			= 0;
	int tPresencial		= 0;
	int tDistancia		= 0;
	int tNocturna		= 0;
	int tCredAlta		= 0;
	int tCredBaja		= 0;
	int totUM			= 0;
	int totPlan			= 0;
	int totCreditoCero 	= 0;
	
	int tPreparatoria	= 0;
	int tLicenciatura	= 0;
	int tMaestria		= 0;
	int tEspecialidad	= 0;
	int tDoctorado		= 0;
	int tNoFormal		= 0;
	
	String lisCargaTmp[] = {cargas};
	if(lisCarga != null){
		lisCargaTmp = lisCarga;
	}
	for(String cargaSesion: lisCargaTmp){
		// Buscar los datos de la carga
		if(mapaCargas.containsKey(cargaSesion)){
			carga = mapaCargas.get(cargaSesion);
		}

		if(mapaListaFacultad.containsKey(cargaSesion)){
			lisFacultad = mapaListaFacultad.get(cargaSesion);
		}

		if(mapaListaCarrera.containsKey(cargaSesion)){
			lisCarrera = mapaListaCarrera.get(cargaSesion);
		}

		if(mapaMapaCreditos.containsKey(cargaSesion)){
			mapCreditosCero = mapaMapaCreditos.get(cargaSesion);
		}
		
		int numUM			= 0;
		int numPlan			= 0;
		int inscritos		= 0;
		int internos		= 0;
		int externos		= 0;
		int hombres			= 0;
		int mujeres			= 0;
		int mexicanos		= 0;
		int extranjeros		= 0;
		int acfe			= 0;
		int noAcfe			= 0;
		int presencial		= 0;
		int distancia		= 0;
		int nocturna		= 0;
		int credAlta		= 0;
		int credBaja		= 0;
		int totUMCarga		= 0;
		int totPlanCarga	= 0;
		int creditoCero 	= 0;
%>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<th><%=carga.getNombreCarga() %> - Period from <%=carga.getFInicio() %> to <%=carga.getFFinal() %></th>
		</tr>
		<tr>
			<td style="border: solid 1px gray;">
				<table class="table table-sm table-bordered" style="width:100%">
					<tr class="table-info">
						<td align="center" width="40%"><b>School/Course</b></td>
						<td align="center" title="Enrolled"><b>Enrolled</b></td>
						<td align="center" title="First Year Students"><b>FY Stud.</b></td>
						<td align="center" title="Firts Year Plan"><b>FY Plan</b></td>
						<td align="center" title="Boarding Students"><b>Boarding.</b></td>
						<td align="center" title="Off-Campus Students"><b>OC.</b></td>
						<td align="center" title="Male"><b>Male</b></td>
						<td align="center" title="Female"><b>Female</b></td>
						<td align="center" title="Nationals"><b>Nat.</b></td>
						<td align="center" title="Foreigners"><b>For.</b></td>
						<td align="center"><b>Acfe</b></td>
						<td align="center"><b>NoAcfe</b></td>
						<td align="center" title="In-Person"><b>In-Person</b></td>
						<td align="center" title="Night Time Students"><b>Nocturn.</b></td>
						<td align="center" title="Distance"><b>Dist.</b></td>
						<td align="center" title="Register Credits"><b>Reg. Crd.</b></td>
						<td align="center" title="Dropped Credits"><b>Drop Crd.</b></td>
						<td align="center" title="Total Credits"><b>Tot. Cr.</b></td>
						<td align="center" title="Total Zero Credits"><b>Zero Credits</b></td>
						<td align="center" title="Average Credits"><b>Avrg. Crd.</b></td>
					</tr>
<%
		if(mapaLisEstadistica.containsKey(carga.getCargaId())){
			lisEstadistica = mapaLisEstadistica.get(carga.getCargaId());			
		}

		/* Cuenta el primer ingreso a al UM por facultad */
		HashMap<String, String> mapIngUMFac = new HashMap<String, String>();
		if(mapaMapIngresoFacUm.containsKey(carga.getCargaId())){
			mapIngUMFac = mapaMapIngresoFacUm.get(carga.getCargaId());
		}
		
		/* Cuenta el primer ingresoa al PLAN por facultad */
		HashMap<String, String> mapIngPlanFac = new HashMap<String, String>();
		if(mapaMapIngresoFacPlan.containsKey(carga.getCargaId())){
			mapIngPlanFac = mapaMapIngresoFacPlan.get(carga.getCargaId());
		}
		
		/* Cuenta el primer ingreso a al UM por carrera */
		HashMap<String, String> mapIngUMCarrera = new HashMap<String, String>();
		if(mapaMapIngresoCarreraUm.containsKey(carga.getCargaId())){
			mapIngUMCarrera = mapaMapIngresoCarreraUm.get(carga.getCargaId());
		}
		
		/* Cuenta el primer ingresoa al PLAN por carrera */
		HashMap<String, String> mapIngPlanCarrera = new HashMap<String, String>();
		if(mapaMapIngresoCarreraPlan.containsKey(carga.getCargaId())){
			mapIngPlanCarrera = mapaMapIngresoCarreraPlan.get(carga.getCargaId());
		}
			
		List<Estadistica> listTemp = new ArrayList<Estadistica>();
		
		for(Estadistica est1 : lisEstadistica){
			boolean encontrado = false;
			
			for(Estadistica est2: listTemp){
				if(est1.getCodigoPersonal().equals(est2.getCodigoPersonal()) && est1.getCargaId().equals(est2.getCargaId())){
					encontrado = true;
					break;
				}
			}				
			if(!encontrado){
				listTemp.add(est1);
			}
		}
		
		lisEstadistica = null;
		lisEstadistica = listTemp;
		
		for(int j = 0; j < lisFacultad.size(); j++){
			facultad =  lisFacultad.get(j);
			inscritos		= 0; 
			numUM			= 0;				numPlan     = 0;								
			internos		= 0;				externos	= 0;				
			hombres			= 0;				mujeres		= 0;				
			mexicanos		= 0;				extranjeros	= 0;				
			acfe			= 0;				noAcfe		= 0;				
			presencial		= 0;				distancia	= 0;				
			nocturna		= 0;				
			credAlta		= 0;				credBaja	= 0;			
			creditoCero		= 0;
			
		
			if (mapIngUMFac.containsKey(facultad.getFacultadId())){
				numUM = Integer.parseInt(mapIngUMFac.get(facultad.getFacultadId()));
			}
			totUM 		+= numUM;
			totUMCarga 	+= numUM; 
			
			if (mapIngPlanFac.containsKey(facultad.getFacultadId())){
				numPlan = Integer.parseInt(mapIngPlanFac.get(facultad.getFacultadId()));
			}
			totPlan 	+= numPlan;
			totPlanCarga+= numPlan;	
			
			for(int l = 0; l < lisEstadistica.size(); l++){
				estadistica = lisEstadistica.get(l);					
				
				if(estadistica.getFacultadId().equals(facultad.getFacultadId())){
					
					boolean permiso = false;
					for(int m=0; m<lisCarrera.size(); m++){
						if(lisCarrera.get(m).getCarreraId().equals(estadistica.getCarreraId())){
							permiso = true;
							break;
						}
					}
					
					if(permiso == false)continue;
					
					inscritos++;						
					
					if(estadistica.getResidenciaId().equals("I"))
						internos++;
					else
						externos++;
					if(estadistica.getSexo().equals("M"))
						hombres++;
					else
						mujeres++;
					if(estadistica.getNacionalidad().equals(codigoNacional)) 
						mexicanos++;
					else
						extranjeros++;
					if(estadistica.getClasFin().equals("1"))
						acfe++;
					else
						noAcfe++;
					if(estadistica.getModalidadId().equals("1"))
						presencial++;
					else if(!estadistica.getModalidadId().equals("4"))
						distancia++;
					else
						nocturna++;
					credAlta += Integer.parseInt(estadistica.getCredAlta());
					credBaja += Integer.parseInt(estadistica.getCredBaja());
					
					// Map creditos 0
					String credCero = "0";
					if(mapCreditosCero.containsKey(estadistica.getCodigoPersonal()+carga.getCargaId()+estadistica.getBloqueId())){
						credCero = mapCreditosCero.get(estadistica.getCodigoPersonal()+estadistica.getCargaId()+estadistica.getBloqueId());
					}
					creditoCero = creditoCero + Integer.parseInt(credCero);
					
				}
				
			}		
			 
			// Promedio cred Facultad
			if ((inscritos>0&&credAlta>0)) promedio = ((double)credAlta/(double)inscritos); else promedio = 0;
%>
					<tr class="table-dark">
						<td><b><%=facultad.getFacultadId()%>: <%=facultad.getTitulo() %><%=facultad.getFacultadId().equals("107")?" ":" of " %><%=facultad.getNombreFacultad() %></b></td>
						<td class="text-end"><b><%=inscritos %></b></font></td>
						<td class="text-end"><b><%=numUM %></b></font></td>
						<td class="text-end"><b><%=numPlan %></b></font></td>
						<td class="text-end"><b><%=internos %></b></font></td>
						<td class="text-end"><b><%=externos %></b></font></td>
						<td class="text-end"><b><%=hombres %></b></font></td>
						<td class="text-end"><b><%=mujeres %></b></font></td>
						<td class="text-end"><b><%=mexicanos %></b></font></td>
						<td class="text-end"><b><%=extranjeros %></b></font></td>
						<td class="text-end"><b><%=acfe %></b></font></td>
						<td class="text-end"><b><%=noAcfe %></b></font></td>
						<td class="text-end"><b><%=presencial %></b></font></td>
						<td class="text-end"><b><%=nocturna %></b></font></td>
						<td class="text-end"><b><%=distancia %></b></font></td>
						<td class="text-end"><b><%=credAlta+credBaja %></b></font></td>
						<td class="text-end"><b><%=credBaja %></b></font></td>
						<td class="text-end"><b><%=credAlta %></b></font></td>
						<td class="text-end"><b><%=creditoCero %></b></font></td>
						<td class="text-end"><b><%=getFormato.format(promedio) %></b></td>
					</tr>
					
<%
			
			for(int k = 0; k < lisCarrera.size(); k++){
				carrera =  lisCarrera.get(k);
				if(carrera.getFacultadId().equals(facultad.getFacultadId())){
					inscritos	= 0;
					numUM		= 0;
					numPlan		= 0;
					internos	= 0;
					externos	= 0;
					hombres		= 0;
					mujeres		= 0;
					mexicanos	= 0;
					extranjeros	= 0;
					acfe		= 0;
					noAcfe		= 0;
					presencial	= 0;
					distancia	= 0;
					nocturna	= 0;
					credAlta	= 0;
					credBaja	= 0;
					creditoCero = 0;
					
					if (mapIngUMCarrera.containsKey(carrera.getCarreraId())){
						numUM = Integer.parseInt(mapIngUMCarrera.get(carrera.getCarreraId()));
					}
					
					if (mapIngPlanCarrera.containsKey(carrera.getCarreraId())){
						numPlan = Integer.parseInt(mapIngPlanCarrera.get(carrera.getCarreraId()));
					}
					
					int conta = 0;
					for(int l = 0; l < lisEstadistica.size(); l++){
						estadistica = (Estadistica) lisEstadistica.get(l);
						if(estadistica.getCarreraId().equals(carrera.getCarreraId())){
							inscritos++;
							
							if(estadistica.getResidenciaId().equals("I"))
								internos++;
							else
								externos++;
							if(estadistica.getSexo().equals("M"))
								hombres++;
							else
								mujeres++;
							if(estadistica.getNacionalidad().equals(codigoNacional))
								mexicanos++;
							else
								extranjeros++;
							if(estadistica.getClasFin().equals("1"))
								acfe++;
							else
								noAcfe++;
							if(estadistica.getModalidadId().equals("1"))
								presencial++;
							else if(!estadistica.getModalidadId().equals("4"))
								distancia++;
							else
								nocturna++;
							credAlta += Integer.parseInt(estadistica.getCredAlta());
							credBaja += Integer.parseInt(estadistica.getCredBaja());
							
							/*  Validar datos de creditos de alta y baja
							if (estadistica.getCarreraId().equals("10808")){
								System.out.println("Datos:"+estadistica.getCodigoPersonal()+":"+estadistica.getCargaId()+":"+estadistica.getCredAlta()+":"+estadistica.getCredBaja());
							}
							*/
							
							if(carrera.getNivelId().equals("1") ){
								tPreparatoria++;
							}else if(carrera.getNivelId().equals("2") ){
								tLicenciatura++;
							}else if(carrera.getNivelId().equals("3") ){
								tMaestria++;
							}else if(carrera.getNivelId().equals("4") ){
								tDoctorado++;
							}else if(carrera.getNivelId().equals("5") ){
								tNoFormal++;
							}else if(carrera.getNivelId().equals("6") ){
								tEspecialidad++;
							}else{
								System.out.println("Student withou level:"+estadistica.getCodigoPersonal());
							}
							
							// Map creditos 0
							String credCero = "0";
							if(mapCreditosCero.containsKey(estadistica.getCodigoPersonal()+carga.getCargaId()+estadistica.getBloqueId())){
								credCero = mapCreditosCero.get(estadistica.getCodigoPersonal()+estadistica.getCargaId()+estadistica.getBloqueId());
							}
							creditoCero = creditoCero + Integer.parseInt(credCero);
							
							conta++;
							
						}						
						
					}				
					
					if ((inscritos>0&&credAlta>0)) promedio = ((double)credAlta/(double)inscritos); else promedio = 0;
%>
					<tr class="tr2">
						<td>&nbsp;&nbsp;&nbsp;&nbsp;<%=carrera.getCarreraId()%>: <%=carrera.getNombreCarrera() %></td>
						<td class="text-end"><%=inscritos %></td>
						<td class="text-end"><%=numUM %></td>
						<td class="text-end"><%=numPlan %></td>
						<td class="text-end"><%=internos %></td>
						<td class="text-end"><%=externos %></td>
						<td class="text-end"><%=hombres %></td>
						<td class="text-end"><%=mujeres %></td>
						<td class="text-end"><%=mexicanos %></td>
						<td class="text-end"><%=extranjeros %></td>
						<td class="text-end"><%=acfe %></td>
						<td class="text-end"><%=noAcfe %></td>
						<td class="text-end"><%=presencial %></td>
						<td class="text-end"><%=nocturna %></td>
						<td class="text-end"><%=distancia %></td>
						<td class="text-end"><%=credAlta+credBaja %></td>
						<td class="text-end"><%=credBaja %></td>
						<td class="text-end"><%=credAlta%></td>
						<td class="text-end"><%=creditoCero%></td>
						<td class="text-end"><%=getFormato.format(promedio)%></td>
					</tr>
<%
				}
			}
		}
		
		inscritos	= 0;
		numUM		= 0;
		numPlan		= 0;
		internos	= 0;
		externos	= 0;
		hombres		= 0;
		mujeres		= 0;
		mexicanos	= 0;
		extranjeros	= 0;
		acfe		= 0;
		noAcfe		= 0;
		presencial	= 0;
		distancia	= 0;
		nocturna	= 0;
		credAlta	= 0;
		credBaja	= 0;
		creditoCero	= 0;
		for(int l = 0; l < lisEstadistica.size(); l++){
			estadistica = (Estadistica) lisEstadistica.get(l);
			
			boolean permiso = false;
			for(int m=0; m<lisCarrera.size(); m++){
				if(lisCarrera.get(m).getCarreraId().equals(estadistica.getCarreraId())){
					permiso = true;
					break;
				}
			}
			
			if(permiso == false)continue;
			
			inscritos++;
			
			if(estadistica.getResidenciaId().equals("I"))
				internos++;
			else
				externos++;
			if(estadistica.getSexo().equals("M"))
				hombres++;
			else
				mujeres++;
			if(estadistica.getNacionalidad().equals(codigoNacional))
				mexicanos++;
			else
				extranjeros++;
			if(estadistica.getClasFin().equals("1"))
				acfe++;
			else
				noAcfe++;
			if(estadistica.getModalidadId().equals("1"))
				presencial++;
			else if(!estadistica.getModalidadId().equals("4"))
				distancia++;
			else
				nocturna++;
			credAlta += Integer.parseInt(estadistica.getCredAlta());
			credBaja += Integer.parseInt(estadistica.getCredBaja());
			
			// Map creditos 0
			String credCero = "0";
			if(mapCreditosCero.containsKey(estadistica.getCodigoPersonal()+carga.getCargaId()+estadistica.getBloqueId())){
				credCero = mapCreditosCero.get(estadistica.getCodigoPersonal()+estadistica.getCargaId()+estadistica.getBloqueId());
			}
			creditoCero = creditoCero + Integer.parseInt(credCero);
			
		}
		// Promedio de creditos por carga
		if ((inscritos>0&&credAlta>0)) promedio = ((double)credAlta/(double)inscritos); else promedio = 0;
		
		tInscritos		+= inscritos;
		tInternos		+= internos;
		tExternos		+= externos;
		tHombres		+= hombres;
		tMujeres		+= mujeres;
		tMexicanos		+= mexicanos;
		tExtranjeros	+= extranjeros;
		tAcfe			+= acfe;
		tNoAcfe			+= noAcfe;
		tPresencial		+= presencial;
		tDistancia		+= distancia;
		tNocturna		+= nocturna;
		tCredAlta		+= credAlta;
		tCredBaja		+= credBaja;
		totCreditoCero  += creditoCero;
%>
					<tr bgcolor="#BDBDBD" class="titulo">
					    <td class="text-end"><b><spring:message code="aca.Total"/></b></td>
						<td class="text-end"><b><%=inscritos %></b></td>
						<td class="text-end"><b><%=totUMCarga %></b></td>
						<td class="text-end"><b><%=totPlanCarga %></b></td>
						<td class="text-end"><b><%=internos %></b></td>
						<td class="text-end"><b><%=externos %></b></td>
						<td class="text-end"><b><%=hombres %></b></td>
						<td class="text-end"><b><%=mujeres %></b></td>
						<td class="text-end"><b><%=mexicanos %></b></td>
						<td class="text-end"><b><%=extranjeros %></b></td>
						<td class="text-end"><b><%=acfe %></b></td>
						<td class="text-end"><b><%=noAcfe %></b></td>
						<td class="text-end"><b><%=presencial %></b></td>
						<td class="text-end"><b><%=nocturna %></b></td>
						<td class="text-end"><b><%=distancia %></b></td>
						<td class="text-end"><b><%=credAlta+credBaja %></b></td>
						<td class="text-end"><b><%=credBaja %></b></td>
						<td class="text-end"><b><%=credAlta %></b></td>
						<td class="text-end"><b><%=creditoCero %></b></td>
						<td class="text-end"><b><%=getFormato.format(promedio) %></b></td>
					</tr>
				</table>
			</td>
		</tr>
<%		
	}
	
	// Promedio general de creditos
	if ((tInscritos>0&&tCredAlta>0)) promedio = ((double)tCredAlta/(double)tInscritos); else promedio = 0;
%>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="center"><b>GRAND&nbsp;&nbsp;&nbsp;&nbsp;TOTAL</b></td>
		</tr>
		<tr>
			<td style="border-top: solid 3px black; border-bottom: solid 3px black;">
				<table style="width:100%">
					<tr bgcolor="#BDBDBD" class="titulo">
						<td align="center" width="40%"><b>School/Course</b></td>
						<td align="center" title="Enrolled"><b>Enrolled</b></td>
						<td align="center" title="First Year Students"><b>FY Stud.</b></td>
						<td align="center" title="Firts Year Plan"><b>FY Plan</b></td>
						<td align="center" title="Boarding Students"><b>Boarding.</b></td>
						<td align="center" title="Off-Campus Students"><b>OC.</b></td>
						<td align="center" title="Male"><b>Male</b></td>
						<td align="center" title="Female"><b>Female</b></td>
						<td align="center" title="Nationals"><b>Nat.</b></td>
						<td align="center" title="Foreigners"><b>For.</b></td>
						<td align="center"><b>Acfe</b></td>
						<td align="center"><b>NoAcfe</b></td>
						<td align="center" title="In-Person"><b>In-Person</b></td>
						<td align="center" title="Night Time Students"><b>Nocturn.</b></td>
						<td align="center" title="Distance"><b>Dist.</b></td>
						<td align="center" title="Register Credits"><b>Reg. Crd.</b></td>
						<td align="center" title="Dropped Credits"><b>Drop Crd.</b></td>
						<td align="center" title="Total Credits"><b>Tot. Cr.</b></td>
						<td align="center" title="Total Zero Credits"><b>Zero Credits</b></td>
						<td align="center" title="Average Credits"><b>Avrg. Crd.</b></td>
					</tr>
					<tr>
						<td class="text-end"><font color="black"><b>Total&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></font></td>
						<td class="text-end"><font color="black"><b><%=tInscritos %></b></font></td>
						<td class="text-end"><font color="black"><b><%=totUM %></b></font></td>
						<td class="text-end"><font color="black"><b><%=totPlan %></b></font></td>
						<td class="text-end"><font color="black"><b><%=tInternos %></b></font></td>
						<td class="text-end"><font color="black"><b><%=tExternos %></b></font></td>
						<td class="text-end"><font color="black"><b><%=tHombres %></b></font></td>
						<td class="text-end"><font color="black"><b><%=tMujeres %></b></font></td>
						<td class="text-end"><font color="black"><b><%=tMexicanos %></b></font></td>
						<td class="text-end"><font color="black"><b><%=tExtranjeros %></b></font></td>
						<td class="text-end"><font color="black"><b><%=tAcfe %></b></font></td>
						<td class="text-end"><font color="black"><b><%=tNoAcfe %></b></font></td>
						<td class="text-end"><font color="black"><b><%=tPresencial %></b></font></td>
						<td class="text-end"><font color="black"><b><%=tNocturna %></b></font></td>
						<td class="text-end"><font color="black"><b><%=tDistancia %></b></font></td>
						<td class="text-end"><font color="black"><b><%=tCredAlta+tCredBaja%></b></font></td>
						<td class="text-end"><font color="black"><b><%=tCredBaja %></b></font></td>
						<td class="text-end"><font color="black"><b><%=tCredAlta%></b></font></td>
						<td class="text-end"><font color="black"><b><%=totCreditoCero%></b></font></td>
						<td class="text-end"><font color="black"><b><%=getFormato.format(promedio)%></b></font></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="center">
				<table style="border: solid 1px gray;">
					<tr>
						<th>Distribution</th>
						<th><spring:message code="aca.Total"/></th>
					</tr>
					<tr>
						<td><b>Diploma</b></td>
						<td align="right"><%=tPreparatoria %></td>
					</tr>
					<tr>
						<td><b>Bachelors Degree</td>
						<td align="right"><%=tLicenciatura %></td>
					</tr>
					<tr>
						<td><b>Masters Degree</td>
						<td align="right"><%=tMaestria %></td>
					</tr>
					<tr>
						<td><b>Specialty</td>
						<td align="right"><%=tEspecialidad %></td>
					</tr>
					<tr>
						<td><b>Doctorate Degree</b></td>
						<td align="right"><%=tDoctorado %></td>
					</tr>
					<tr>
						<td><b>Not Formal Diploma</b></td>
						<td align="right"><%=tNoFormal %></td>
					</tr>
					<tr>
						<td style="border-top: dotted 1px gray;" align="right"><b><spring:message code="aca.Total"/></b></td>
						<td style="border-top: dotted 1px gray;" align="right"><%=tPreparatoria + tLicenciatura + tMaestria + tEspecialidad + tDoctorado + tNoFormal %></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<th>Students that repeat in the selected loads</th>
		</tr>
		<tr>
			<td style="border: solid 1px gray;">
				<table style="width:100%">
					<tr class="th2">
						<td align="center"><spring:message code="aca.Matricula"/></td>
						<td align="center"><spring:message code="aca.Nombre"/></td>
						<td align="center"><spring:message code="aca.Carga"/></td>
						<td align="center"><spring:message code="aca.Carrera"/></td>
					</tr>
<%
	contRepetidos = 0;
	if(mapaLisRepetidos.containsKey(carga.getCargaId())){
		lisRepetidos = mapaLisRepetidos.get(carga.getCargaId());
	}
	
	for(int i = 0; i < lisRepetidos.size(); i++){
		estadistica = (Estadistica) lisRepetidos.get(i);
		if(!matricula.equals(estadistica.getCodigoPersonal())){
			cont++;
			matricula = estadistica.getCodigoPersonal();
		}else{
			contRepetidos++;
		}
		String nombreCarrera = "";
		if(mapaNombreCarrera.containsKey(estadistica.getCarreraId())){
			nombreCarrera = mapaNombreCarrera.get(estadistica.getCarreraId());
		}
%>
					<tr>
						<td><%=estadistica.getCodigoPersonal() %></td>
						<td><%=estadistica.getApellidoPaterno() %> <%=estadistica.getApellidoMaterno() %> <%=estadistica.getNombre() %></td>
						<td align="center"><%=estadistica.getCargaId() %></td>
						<td><%=nombreCarrera%></td>
					</tr>
<%	} %>
				</table>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td align="center">
				<table style="border: solid 1px gray;">
					<tr>
						<td class="th2">
							Repeated Students: <%=cont%>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	</div>	
</body>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();
</script>	