<%@page import="aca.catalogo.spring.CatTipoAlumno"%>
<%@page import="aca.catalogo.CatCarreraUtil"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="aca.vista.spring.Estadistica"%>
<%@page import="java.util.TreeMap"%>
<%@page import="aca.alumno.spring.AlumAsesor"%>
<%@page import="aca.financiero.spring.FinSaldo"%>
<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatTipoAlumno"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>
<%@page import="aca.catalogo.spring.CatPais"%>
<%@page import="aca.catalogo.spring.CatEstado"%>
<%@page import="aca.catalogo.spring.CatCiudad"%>
<%@page import="aca.catalogo.spring.CatReligion"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumUbicacion"%>
<%@page import="aca.financiero.spring.SaldosAlumnos"%>
<%@page import="aca.financiero.spring.FesCcobro"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.carga.spring.CargaAlumno"%>

<%-- <%@ include file= "id.jsp" %> --%>
<%-- <%@ include file= "../../seguro.jsp" %> --%>
<%-- <%@ include file= "../../body.jsp" %> --%>
<%-- <%@ include file= "../../idioma.jsp" %> --%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>


<script type="text/javascript">	
	function Mostrar(){		
		var x = document.getElementById("loading");
  	  	if (x.style.display === "none") {
 	    	x.style.display = "block";
  	  	} else {
  	    	x.style.display = "none";
  	  	}
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
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css"/>
    <link rel = "stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">  
</head>	

<!-- inicio de estructura -->
<%
	java.text.DecimalFormat frmDecimal= new java.text.DecimalFormat("###,##0.00; -###,##0.00");

	String accion 			= (String) request.getAttribute("accion");
	String modalidades		= (String) request.getAttribute("modalidades");
	String residencia		= (String) request.getAttribute("residencia");
	String nacion			= (String) request.getAttribute("nacion");
	String tipoAlumno		= (String) request.getAttribute("tipoAlumno");
	String periodoId 		= (String) request.getAttribute("periodoId");
	String cargaId 			= (String) request.getAttribute("cargaId");
	String codigo			= (String) request.getAttribute("codigo");
	String fechaIni			= (String) request.getAttribute("fechaIni");
	String fechaFin			= (String) request.getAttribute("fechaFin");
	String conSaldo			= (String) request.getAttribute("conSaldo");
	String paisOrganizacion = (String) request.getAttribute("paisOrganizacion");
	
	String lisModo[] 		= modalidades.replace("'", "").split(",");

	Carga cargaElegida 		= (Carga) request.getAttribute("cargaElegida");
	
	List<CatPeriodo> listaPeriodos 		= (List<CatPeriodo>) request.getAttribute("listaPeriodos");
	List<Carga> lisCarga 				= (List<Carga>) request.getAttribute("lisCarga");
	List<CatTipoAlumno> lisTipoAlumno 	= (List<CatTipoAlumno>) request.getAttribute("lisTipoAlumno");
	
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
	
	int añoHoy				= Integer.parseInt(fechaHoy[2]);
	int	mesHoy				= Integer.parseInt(fechaHoy[1]);
	int diaHoy				= Integer.parseInt(fechaHoy[0]);
	
	int añoNac				= 0;
	int	mesNac				= 0;
	int diaNac				= 0;
	
	int edad				= 0;
	
	Acceso acceso 			= (Acceso) request.getAttribute("acceso");	
	String sBgcolor			= "";
	
	int cont 				= 1;
	int nInscritos 			= 0, nCalculos 		= 0;
	int nHombres			= 0, nMujeres		= 0;
	int nInternos			= 0, nExternos 		= 0;
	int nNacional			= 0, nExtranjero	= 0;
	int i 					= 0;
	
	int nMexicanos = 0, nExtranjeros = 0, nAdventistas = 0, nNoAdventistas = 0;
	int inscritosFac 		= 0, internosFac = 0, externosFac = 0, hombresFac = 0,	mujeresFac = 0, mexicanos = 0, extranjeros = 0, adventistas = 0, noAdventistas = 0;
	if (cargaId==null){ cargaId	= (String)session.getAttribute("cargaId");  }
	
	HashMap<String,AlumAsesor> mapaAsesores = (HashMap<String,AlumAsesor>) request.getAttribute("mapaAsesores");
	HashMap<String,String> mapAlumComidas 	= (HashMap<String,String>) request.getAttribute("mapAlumComidas");
	HashMap<String, FinSaldo> mapaSaldos 	= (HashMap<String, FinSaldo>) request.getAttribute("mapaSaldos");
	
	// Map para contar numero total de materias
	HashMap<String, String> mapMaterias 			= (HashMap<String,String>) request.getAttribute("mapMaterias");
	// Map para contar numero de materias acreditadas
	HashMap<String, String> mapAcreditadas			= (HashMap<String,String>) request.getAttribute("mapAcreditadas");
	// Map para contar numero de materias acreditadas
	HashMap<String, String> mapReprobadas 			= (HashMap<String,String>) request.getAttribute("mapReprobadas");
	// Map para contar numero de materias acreditadas
	HashMap<String, String> mapPendientes			= (HashMap<String,String>) request.getAttribute("mapPendientes");
	// Map para contar numero de materias acreditadas
	HashMap<String, String> mapBajas 				= (HashMap<String,String>) request.getAttribute("mapBajas");
	// Map para contar numero de materias acreditadas
	HashMap<String, String> mapCreditos 			= (HashMap<String,String>) request.getAttribute("mapCreditos");
	// Map para contar numero de materias acreditadas
	HashMap<String, String> mapCreditosRep			= (HashMap<String,String>) request.getAttribute("mapCreditosRep");	
	//Map de Carreras
	HashMap <String, CatCarrera> mapaCarrera 		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarrera");
	//Map de Carreras
	HashMap <String, CatTipoAlumno> mapaTipo		= (HashMap<String,CatTipoAlumno>) request.getAttribute("mapaTipo");
	// Map de facultades
	HashMap <String, CatFacultad> mapaFacultad		= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultad");
	// Map de modalidades
	HashMap <String, CatModalidad> mapaModalidad 	= (HashMap<String,CatModalidad>) request.getAttribute("mapaModalidad");
	// Map de países
	HashMap <String, CatPais> mapaPais 				= (HashMap<String,CatPais>) request.getAttribute("mapaPais");
	// Map de estados
	HashMap <String, CatEstado> mapaEstado 			= (HashMap<String,CatEstado>) request.getAttribute("mapaEstado");
	// Map de ciudades
	HashMap <String, CatCiudad> mapaCiudad 			= (HashMap<String,CatCiudad>) request.getAttribute("mapaCiudad");
	// Map de semestres
	HashMap <String, FesCcobro> mapaCobro 			= (HashMap<String,FesCcobro>) request.getAttribute("mapaCobro");
	// Map de religiones
	HashMap<String, CatReligion> mapReligion 		= (HashMap<String,CatReligion>) request.getAttribute("mapReligion");		
	// Map de Datos personales
	HashMap<String, AlumPersonal> mapAlumnos 		= (HashMap<String,AlumPersonal>) request.getAttribute("mapAlumnos");
	// Map de Datos personales
	HashMap<String, AlumUbicacion> mapUbicacion 	= (HashMap<String,AlumUbicacion>) request.getAttribute("mapUbicacion");
	// Lista de alumnos inscritos en una carga
	List<Estadistica> lista 						= (List<Estadistica>) request.getAttribute("lista");
	// Saldos de alumnos en la carga
	HashMap<String, SaldosAlumnos> saldos 			= (HashMap<String,SaldosAlumnos>) request.getAttribute("saldos");

	HashMap<String, String> mapaPrimerMatricula 	= (HashMap<String,String>) request.getAttribute("mapaPrimerMatricula");
	
	HashMap<String, CargaAlumno> mapaCargasAlumno 	= (HashMap<String,CargaAlumno>) request.getAttribute("mapaCargasAlumno");
	
%>
<div class="container-fluid">
	<h1>Enrolled in an Academic Load</h1>
	<form id="forma" name="forma" action="inscritos_per?Accion=1" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<b>Period:&nbsp;</b>
        <select onchange='javascript:cambiaPeriodo(this.value);' name="PeriodoId" class="form form-select" style="width: 12rem">
<%	for(int j=0; j<listaPeriodos.size(); j++){
		CatPeriodo periodo = listaPeriodos.get(j);%>
			<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print("Selected");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
<%	}%>
        </select>
        &nbsp;&nbsp;
		<b>Load:&nbsp;</b>
	    <select onchange='javascript:cambiaCarga();' name="CargaId" style="width:350px;" class="form form-select" style="width: 15rem">
<%	for(i=0; i<lisCarga.size(); i++){
		Carga carga = lisCarga.get(i);%>
			<option <%if(cargaId.equals(carga.getCargaId()))out.print("Selected");%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId() %>] <%=carga.getNombreCarga()%></option>
<%	}%>
			
        </select>
		&nbsp;&nbsp;
        <b>Residence:</b>&nbsp;
        <select name="Residencia" class="form form-select" style="width:11rem ">
			<option value="T" <%=residencia.equals("T")?"selected":""%>>All</option>
			<option value="I" <%=residencia.equals("I")?"selected":""%>>Boarding Students</option>
			<option value="E" <%=residencia.equals("E")?"selected":""%>>Day Students</option>
		</select>
        &nbsp;&nbsp;
        <b>Nationality:</b>&nbsp;
        <select name="Nacionalidad" class="form form-select" style="width:8rem" >
			<option value="T" <%=nacion.equals("T")?"selected":""%>>All</option>
			<option value="M" <%=nacion.equals("M")?"selected":""%>>National</option>
			<option value="E" <%=nacion.equals("E")?"selected":""%>>Foreigner</option>
		</select>
        &nbsp;&nbsp;
        <b>Type:</b>&nbsp;
        <select name="TipoAlumno" class="form form-select" style="width:12rem ">
        <option value="T" <%=tipoAlumno.equals("T")?"selected":""%>>All</option>
<%	for(i=0; i<lisTipoAlumno.size(); i++){
		CatTipoAlumno catTipoAlumno = lisTipoAlumno.get(i);%>
			<option <%if(tipoAlumno.equals(catTipoAlumno.getTipoId()))out.print("Selected");%> value="<%=catTipoAlumno.getTipoId()%>"><%=catTipoAlumno.getNombreTipo()%></option>
<%	}%>
		</select>
        &nbsp;&nbsp;
<%
			for(String mod:lisModo){
				String nombreModalidad = "";
				if(mapaModalidad.containsKey(mod)){
					nombreModalidad = mapaModalidad.get(mod).getNombreModalidad();
				}
				out.print("<b>["+nombreModalidad+"] </b> &nbsp;");	
			}		
%>
		<a href="modalidades" class="btn btn-primary btn-sm"><i class="fas fa-pencil-alt"></i></a>		
	</div>
	<div class="alert alert-info d-flex align-items-center">		
		<b>See balances:</b>&nbsp;	
		<select name="ConSaldo" class="form form-select" style="width:6rem ">
			<option value="S" <%=conSaldo.equals("S")?"selected":""%>>Yes</option>
			<option value="N" <%=conSaldo.equals("N")?"selected":""%>>No</option>
		</select> 
		&nbsp;&nbsp;
		<b>Start date:&nbsp;</b> <input id="FechaIni" name="FechaIni" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10" class="form-control" style="width:8rem"/>&nbsp;&nbsp;
		<b>End date:&nbsp;</b> <input id="FechaFin" name="FechaFin" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10" class="form-control" style="width:8rem"/>&nbsp;&nbsp;		
		<b>Load Dates: <%=cargaElegida.getFInicio()+" - "+cargaElegida.getFFinal()%></b>&nbsp;&nbsp;
		<a href="javascript:Mostrar();" title="Load" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>
		&nbsp;&nbsp;
		<a href="reporte?FechaIni=<%=fechaIni%>&FechaFin=<%=fechaFin%>&CargaId=<%=cargaId%>" title="Report" class="btn btn-primary btn-sm"><i class="fas fa-file-alt"></i></a>
		&nbsp;&nbsp;
		<button class="btn btn-success" id="loading"  type="button" disabled style="display: none;">
			<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
		  	Searching ...
		</button>
	</div>
	</form>	
<%	
	
	// Si la acción es mostrar
	if (accion.equals("1")){
		String idFacultad			= "X";
		String codigoNuevo			= "";
		String codigoViejo			= "X";
	    for(Estadistica obj : lista){		
			sMatricula 			= obj.getCodigoPersonal();		
			sSexo 				= obj.getSexo();
			sResidencia 		= obj.getResidenciaId();		
			sCarreraTemp 		= obj.getCarreraId();			
			sFechaNac 			= obj.getFechaNacimiento();
			idFacultad 			= obj.getFacultadId();
			religionId			= obj.getReligionId();
			
			codigoNuevo = sMatricula;
			if(!codigoNuevo.equals(codigoViejo)){
				
				
				if( acceso.getAccesos().indexOf(sCarreraTemp)!= -1 || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") ){		
					nInscritos++;
					if (sSexo.equals("M")){ nHombres++; }else{ nMujeres++; }	 			
					if (sResidencia.equals("Int.")){ nInternos++; }else{ nExternos++; }					
				
					
					if(!sFacultad.equals(idFacultad)){
						if(mapaFacultad.containsKey(idFacultad)){
							sNombreFacultad = /*mapaFacultad.get(idFacultad).getTitulo()+": "+*/ mapaFacultad.get(idFacultad).getNombreFacultad();
						}else{
							sNombreFacultad = "";
							
						}	
						sFacultad 		= idFacultad;
						sCarrera		= "X";
					
					//sFacultad = rset.getString("facultad");
				 	//sNombreFacultad = aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc,sFacultad);
	%>	
	<table class="table table-sm  table-condensed">	  
	  <tr class="alert alert-success">
	    <td align="center" colspan="32"><h4><%=sNombreFacultad%></h4></td>
	  </tr>
	<%				
		       		}//fin del if de facultades diferentes
		       		if(!sCarrera.equals(sCarreraTemp)){
		       			if(mapaCarrera.containsKey(sCarreraTemp)){
		       				sNombreCarrera 	= mapaCarrera.get(sCarreraTemp).getNombreCarrera();
		       			}else{
		       				sNombreCarrera = "";
		       			}		       			
		       			if(!sCarrera.equals("X")){
		       			cont = 1;
	       			
	%>
	<tr>
		<th colspan="4"><h5>Total by Program: <%=inscritosFac %></h5></th>
		<th colspan="29">
		<font size="1">
			[ Boarding: <%=internosFac %> ]&nbsp;&nbsp;
			[ Off-Campus: <%=externosFac%> ]&nbsp;&nbsp;
			[ Female: <%=mujeresFac%> ]&nbsp;&nbsp;
			[ Male: <%=hombresFac%> ]&nbsp;&nbsp;
			[ Nationals: <%=mexicanos%> ]&nbsp;&nbsp;
			[ Foreigners: <%=extranjeros%> ]&nbsp;&nbsp;
			[ SDA: <%=adventistas%> ]&nbsp;&nbsp;
			[ NON SDA: <%=noAdventistas%> ]&nbsp;&nbsp;
		</font>
		</th>							
	</tr>		
<%						}
		       			sCarrera		= sCarreraTemp;
%>		
	  <tr> 
	    <td colspan="32" class="titulo3"><h5><b>Program:</b> <%=sNombreCarrera%></h5></td>
	  </tr>
	  <tr> 
	    <th width="2%" align="center"><spring:message code="aca.Numero"/></th>
		<th width="2%" align="center"> <spring:message code="aca.Plan"/> </th>
	    <th width="4%" align="center"> <spring:message code="aca.Matricula"/> </th>
	    <th width="10%" align="center"> <spring:message code="aca.Nombre"/> </th>
	    <th width="10%" align="center"> <spring:message code="aca.Correo"/> </th>
	    <th width="7%" align="center"> Stud. Tel. </th>
	    <th width="7%" align="center"> Tutor Tel. </th>
	    <th width="4%" align="center"> Enroll. Date </th>
	    <th width="4%"  align="center"> <spring:message code="aca.Modalidad"/> </th>
	    <th width="2%"  align="center"> Group </th>
	    <th width="4%"  align="center"> <spring:message code="aca.Residencia"/> </th>
	    <th width="2%"  align="center"> <spring:message code="aca.Gen"/> </th>
	    <th width="2%"  align="center"> Sem. </th>
	    <th width="4%"  align="center"> Rel. </th>
		<th width="2%"  align="center"> Age </th>
		<th width="4%"  align="center"> B.D. </th>
		<th width="8%"  align="center"> Country </th>
		<th width="6%"  align="center"> <spring:message code="aca.Estado"/> </th>
		<th width="2%"  align="center"> Block. </th>
<%-- 		<th width="3%"   align="center"><b><spring:message code="aca.Ciudad"/></b></th> --%>
		<th width="4%"   align="center"> Type </th>
		<th width="8%"   align="center"> Nation. </th>
<!-- 		<th width="2%"   align="center"> N° Sub. </th> -->
<!-- 		<th width="2%"   align="center"><b>N° AC</b></th> -->
<!-- 		<th width="2%"   align="center"><b>N° NA</b></th> -->
<!-- 		<th width="2%"   align="center"><b>N° CP</b></th> -->
<!-- 		<th width="2%"   align="center"><b>N° BA</b></th> -->
<!-- 		<th width="2%"   align="center"> N° Crd. </th> -->
<!-- 		<th width="3%"   align="center"><b>N° Crd.NA</b></th>				 -->
<!-- 		<th width="3%"   align="center"><b>N° Meals </th>				 -->
		<th width="2%"   align="center"> First Years </th>				
<!-- 		<th width="3%"   align="center"><b>Tot. Balance</b></th>				 -->
<!-- 		<th width="3%"   align="center"><b>Due Balance</b></th>				 -->
	  </tr>
	  
	  <%
		inscritosFac = 0;
		internosFac = 0;
		externosFac = 0;
		hombresFac = 0;
		mujeresFac = 0;
		mexicanos = 0;
		extranjeros = 0;
		adventistas = 0;
		noAdventistas = 0;     			
					}//fin del if de carreras diferentes
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
					
					String paisNombre = "";
					if(mapaPais.containsKey(obj.getPaisId())){
						paisNombre = mapaPais.get(obj.getPaisId()).getNombrePais();
					}
					
					String nacionNombre = "";
					if(mapaPais.containsKey(obj.getPaisId())){
						nacionNombre = mapaPais.get(obj.getNacionalidad()).getNombrePais();
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
      					CatReligion rel = mapReligion.get(obj.getReligionId());
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
      					CatPais p = mapaPais.get(obj.getPaisId());
      					pais 	= p.getNacionalidad();
      				}
      				
      				if(obj.getPaisId().equals(paisOrganizacion)){
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
      					CatReligion rel = mapReligion.get(religionId);
      					religionNombre = rel.getNombreCorto();
      				}
      				
					if ((cont % 2) == 0 ) sBgcolor = sBgcolor; else sBgcolor = "";
					
					String[] nacimiento = sFechaNac.split("-");
					añoNac 				= Integer.parseInt(nacimiento[2]);
					mesNac 				= Integer.parseInt(nacimiento[1]);
					diaNac 				= Integer.parseInt(nacimiento[0]);
					
					edad = añoHoy - añoNac;
					
					if (mesHoy - mesNac < 0){
						edad = edad - 1;
					}else if (mesHoy - mesNac == 0){
						if(diaHoy - diaNac < 0){
							edad = edad - 1;
						}
					}					
					
					double saldo 	= 0;	
					
					if(saldos.containsKey(obj.getCodigoPersonal())){
						SaldosAlumnos sal = saldos.get(obj.getCodigoPersonal());
						saldo 	= Double.parseDouble(sal.getSaldoGlobal());		
					}
																			
					String color = "#AE2113";
					if(saldo>=0) color = "green";
					
					String tipoAlumnos 	= "-";
					if (mapaTipo.containsKey(obj.getTipoAlumnoId())){
						tipoAlumnos = mapaTipo.get( obj.getTipoAlumnoId()).getNombreTipo();
					}
					
					String correo 	= "-";
					String telefono	= "-";					
					if (mapAlumnos.containsKey(obj.getCodigoPersonal())){						
						correo 		= mapAlumnos.get(obj.getCodigoPersonal()).getEmail();
						telefono 	= mapAlumnos.get(obj.getCodigoPersonal()).getTelefono();
						//System.out.println(obj.getCodigoPersonal()+""+correo);
					}
					
					String telefonoTutor	= "-";
					if (mapUbicacion.containsKey(obj.getCodigoPersonal())){						
						telefonoTutor 		= mapUbicacion.get(obj.getCodigoPersonal()).gettCelular();
					}
					
					String fechaInscripcion = obj.getFechaInscripcion().substring(0, 10);

					String[] arrFecha = fechaInscripcion.split("-");
					
					fechaInscripcion = arrFecha[2]+"/"+arrFecha[1]+"/"+arrFecha[0];
					
					String asesor = "<span class='badge badge-warning'>0</span>";
					if (mapaAsesores.containsKey(obj.getCodigoPersonal()+obj.getPlanId())){
						asesor = mapaAsesores.get(obj.getCodigoPersonal()+obj.getPlanId()).getAsesorId();
					}
					
					String comidas = "-";
					if (mapAlumComidas.containsKey(obj.getCodigoPersonal()) ){
						comidas = mapAlumComidas.get(obj.getCodigoPersonal());				
					}
					
					String primerIngreso = "No";
					if (mapaPrimerMatricula.containsKey(obj.getCodigoPersonal()+obj.getPlanId())){
						String fec = mapaPrimerMatricula.get(obj.getCodigoPersonal()+obj.getPlanId());						
						if(fec.equals(obj.getFechaInscripcion())){
							primerIngreso = "Yes";
						}
					}
					
					String saldoTotal 	= "";
					String saldoVencido =  "";
					FinSaldo finSaldo = new FinSaldo();
					
					if (mapaSaldos.containsKey(obj.getCodigoPersonal())){
						finSaldo = mapaSaldos.get(obj.getCodigoPersonal());
						saldoTotal = frmDecimal.format(Float.parseFloat(finSaldo.getSaldoSP()))+" Db";
						saldoVencido =  frmDecimal.format(Float.parseFloat(finSaldo.getSaldoVencido()))+" Db";
						if(Float.parseFloat(finSaldo.getSaldoSP()) < 0){
							saldoTotal = frmDecimal.format(Float.parseFloat(finSaldo.getSaldoSP().substring(1)))+" Cr";
						}
						if(Float.parseFloat(finSaldo.getSaldoVencido()) < 0){
							saldoVencido =  frmDecimal.format(Float.parseFloat(finSaldo.getSaldoVencido()))+" Cr";
						}
					}
					
					String asistencia 	= "Home/Virtual";
					String grupo		= "XX";
					if (mapaCargasAlumno.containsKey(obj.getCodigoPersonal()+obj.getCargaId()+obj.getBloqueId())){
						if(mapaCargasAlumno.get(obj.getCodigoPersonal()+obj.getCargaId()+obj.getBloqueId()).getModo().equals("P")){
							asistencia 	= "On Campus";							  
						}
						grupo 	= mapaCargasAlumno.get(obj.getCodigoPersonal()+obj.getCargaId()+obj.getBloqueId()).getGrupo();
					}
					
%>
	  <tr class="tr2" <%=sBgcolor%>> 
	    <td> &nbsp;<%=cont%></font></td>
		<td> <%=obj.getPlanId()%></font></td>
	    <td align="center"> <%=sMatricula%></font></td>    
	    <td> <%=obj.getApellidoPaterno()+" "+obj.getApellidoMaterno()+" "+obj.getNombre()%></font></td>
	    <td> <%=correo%></font></td>
	    <td> <%=telefono%></font></td>
	    <td> <%=telefonoTutor%></font></td>
	    <td> <%= fechaInscripcion%></font></td>    
	    <td> <%= modalidad%></font></td>    
	    <td> <%= grupo%></font></td>  
	    <td> <%= sResidencia%></font></td>  
	    <td> <%= sSexo%></font></td>    
	    <td> <%= semestre%></font></td>
	    <td> <%= religionNombre%></font></td>
		<td> <%= edad %></font></td>
		<td> <%=sFechaNac%></font></td>
		<td> <%= paisNombre%></font></td>
		<td> <%= estado %></font></td>
		<td> <%=obj.getBloqueId()%></font></td>
		<td> <%= tipoAlumnos%></font></td>
		<td> <%= nacionNombre%></font></td>
<%-- 		<td class="right"> <%= totMaterias%></font></td> --%>
<%-- 		<td class="right"><font size="1"><%= totAcreditadas%></font></td> --%>
<%-- 		<td class="right"><font size="1"><%= totReprobadas %></font></td> --%>
<%-- 		<td class="right"><font size="1"><%= totPendientes %></font></td> --%>
<%-- 		<td class="right"><font size="1"><%= totBajas %></font></td> --%>
<%-- 		<td class="right"> <%= totCreditos%></font></td> --%>
<%-- 		<td class="right"><font size="1"><%= totCreditosRep%></font></td> --%>
<%-- 		<td class="right"><font size="1"><%= comidas%></font></td> --%>
		<td> <%= primerIngreso%></font></td>
<%-- 		<td class="right"><font size="1"><%= saldoTotal%></font></td> --%>
<%-- 		<td class="right"><font size="1"><%= saldoVencido%></font></td> --%>
	  </tr>
<%
	     			cont++;	  
				}
			}
			codigoViejo = codigoNuevo;
 		} // fin del while 		
%>
		<tr>
			<th colspan="4"><font size="1">Total by Program: <%=inscritosFac %></font></th>
			<th colspan="28">
				<font size="1">
					[ Boarding: <%=internosFac %> ]&nbsp;&nbsp;
					[ Off-campus: <%=externosFac%> ]&nbsp;&nbsp;
					[ Female: <%=mujeresFac%> ]&nbsp;&nbsp;
					[ Male: <%=hombresFac%> ]&nbsp;&nbsp;
					[ Nationals: <%=mexicanos%> ]&nbsp;&nbsp;
					[ Foreigners: <%=extranjeros%> ]&nbsp;&nbsp;
					[ SDA: <%=adventistas%> ]&nbsp;&nbsp;
					[ NON SDA: <%=noAdventistas%> ]&nbsp;&nbsp;
				</font>			
			</th>
		</tr>
	</table> 
	<div class="alert alert-info">
  		Enrolled: <%=nInscritos%>&nbsp;&nbsp;&nbsp;
  		Boarding: <%=nInternos%>&nbsp;&nbsp;&nbsp;
    	Off-campus: <%=nExternos%>  	
	</div>
<%	} %>
</div>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>
