<%@page import="java.util.*" %>
<%@page import="java.text.*" %>
<%@page import="aca.util.Fecha"%>
<%@page import="aca.afe.FesCcAfeAcuerdosUtil"%>
<%@page import="aca.bec.BecPuestoAlumno"%>
<%@page import="aca.bec.BecCategoriaUtil"%>
<%@page import="aca.financiero.ContCcosto"%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean scope="page" id="BecPuestoU" class="aca.bec.BecPuestoUtil"/>
<jsp:useBean scope="page" id="BecPuestoAlumnoU" class="aca.bec.BecPuestoAlumnoUtil"/>
<jsp:useBean scope="page" id="ContEjercicioU" class="aca.financiero.ContEjercicioUtil" />
<jsp:useBean scope="page" id="CarreraU" class="aca.catalogo.CatCarreraUtil" />
<jsp:useBean scope="page" id="FacultadU" class="aca.catalogo.CatFacultadUtil" />
<jsp:useBean scope="page" id="becTipo" class="aca.bec.BecTipo"/>
<jsp:useBean scope="page" id="becTipoU" class="aca.bec.BecTipoUtil"/>
<jsp:useBean scope="page" id="becAcuerdoBasica" class="aca.bec.BecAcuerdo"/>
<jsp:useBean scope="page" id="becAcuerdoAdicional" class="aca.bec.BecAcuerdo"/>
<jsp:useBean scope="page" id="becAcuerdoUB" class="aca.bec.BecAcuerdoUtil"/>
<jsp:useBean scope="page" id="becAcuerdoUA" class="aca.bec.BecAcuerdoUtil"/>
<jsp:useBean scope="page" id="BecAcuerdoL" class="aca.bec.BecAcuerdoUtil"/>
<jsp:useBean scope="page" id="AlumnoU" class="aca.alumno.AlumUtil"/>
<jsp:useBean scope="page" id="MapaPlanU" class="aca.plan.PlanUtil"/>
<jsp:useBean scope="page" id="SunPlusFuncionesU" class="aca.financiero.SunPlusFunciones"/>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<%
	//Variables
	String ejercicioSesion		= (String) session.getAttribute("ejercicioId");
	String ejercicioId			= request.getParameter("ejercicioId")==null?ejercicioSesion:request.getParameter("ejercicioId");
	String fechaHoy 			= aca.util.Fecha.getHoy();
	String fecha 				= request.getParameter("fechaParametro")==null?fechaHoy:request.getParameter("fechaParametro");
	String tipoPlaza			= "";

	String condicion 		= " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = '"+ejercicioId+"' "+
			  "AND FECHA_INI <= TO_DATE('"+fecha+"', 'DD/MM/YYYY') AND FECHA_FIN >= TO_DATE('"+fecha+"', 'DD/MM/YYYY'))";
	
	//Alumnos en los puestos
	ArrayList<aca.bec.BecPuestoAlumno> alumPuesto		= BecPuestoAlumnoU.getListAllEjercicio(conEnoc, ejercicioId, "AND FECHA_INI<= TO_DATE('"+fecha+"', 'DD/MM/YYYY') AND FECHA_FIN>=TO_DATE('"+fecha+"', 'DD/MM/YYYY')");
	
	//Alumnos en los puestos
	ArrayList<aca.bec.BecAcuerdo> alumAcuerdo			= BecAcuerdoL.getList(conEnoc, ejercicioId, fecha, "'A','P','C','I'"," ORDER BY CODIGO_PERSONAL, PUESTO_ID, TIPO");
	
	//Ejercicios a partir del 2013
	ArrayList<aca.financiero.ContEjercicio> ejercicios 	= ContEjercicioU.getListProximos(conEnoc, "ORDER BY ID_EJERCICIO DESC");
	
	//Nombres de categorías
	HashMap <String, String> categorias					= aca.bec.BecCategoriaUtil.getMapCategorias(conEnoc);
		
	// Map de empresa que pertenece a las carreras
	HashMap <String, String> mapEmpresa					= CarreraU.mapEmpresa(conEnoc);
	
	// Map de los planes de estudio
	HashMap <String, String> mapPlan					= MapaPlanU.mapCarreraPlan(conEnoc);
		
	// Map de la carrera del alumno
	HashMap <String, String> mapCarreraFormal			= BecPuestoAlumnoU.mapAlumCarrera(conEnoc, ejercicioId, fecha,"'1','3','5'");
	
	// Map de la carrera del alumno
	HashMap <String, String> mapCarreraAlumno			= BecPuestoAlumnoU.mapAlumCarrera(conEnoc, ejercicioId, fecha);
	
	// Map de los puestos
	HashMap <String, String> mapPuestos					= aca.bec.BecCategoriaUtil.getMapCategorias(conEnoc);
		
	// Map de catálogo de carreras
	HashMap <String, aca.catalogo.CatCarrera> mapCarrera= CarreraU.getMapAll(conEnoc, "");
	
	// Map de catálogo de facultades
	HashMap <String, aca.catalogo.CatFacultad> mapFac	= FacultadU.getMapFacultad(conEnoc, "");
	
	// Map de alumnos
	HashMap <String, aca.alumno.AlumPersonal> mapAlumno	= AlumnoU.mapBecadosFecha(conEnoc, fecha);
	
	// Map de porcentaje de matricula por puesto del alumno
	HashMap <String, String> mapMatricula	= BecAcuerdoL.mapPorMatricula(conEnoc, ejercicioId,"'A','E','M','O','P'");
	
	// Map de porcentaje de enseñanza por puesto del alumno
	HashMap <String, String> mapEnsenanza	= BecAcuerdoL.mapPorEnsenanza(conEnoc, ejercicioId,"'A','E','M','O','P'");
	
	// Map de porcentaje de internado por puesto del alumno
	HashMap <String, String> mapInternado	= BecAcuerdoL.mapPorInternado(conEnoc, ejercicioId,"'A','E','M','O','P'");
	
	// Map de porcentaje de internado por puesto del alumno
	HashMap <String, String> mapFunciones	= aca.financiero.SunPlusFuncionesUtil.mapaFunciones(conEnoc); 
		
	
	//Total de beca
	java.text.DecimalFormat getFormato			= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	HashMap <String, String> totalbeca 			= FesCcAfeAcuerdosUtil.mapBeca(conEnoc, ejercicioId, fecha);
	HashMap <String, String> becDiezmo 			= FesCcAfeAcuerdosUtil.mapBecaDiezmo(conEnoc, ejercicioId, fecha);
	HashMap <String, String> basicas   			= FesCcAfeAcuerdosUtil.mapBasicas(conEnoc, ejercicioId, fecha);
	HashMap <String, String> adicionales		= FesCcAfeAcuerdosUtil.mapAdicional(conEnoc, ejercicioId, fecha);
	HashMap <String, ContCcosto> ccostos		= aca.financiero.ContCcostoUtil.getMapCentrosCosto(conEnoc, ejercicioId);
	HashMap <String, String> mapTipoAlum		= aca.alumno.AcademicoUtil.getMapTipoAlumno(conEnoc, condicion);
	HashMap <String, String> mapTipo			= aca.catalogo.TipoAlumnoUtil.getMapNombreTipo(conEnoc);
	HashMap <String, String> mapInscrito		= aca.alumno.EstadoUtil.getMapInscritoFecha(conEnoc, fecha);
	HashMap <String, aca.bec.BecTipo> mapBecTipo= aca.bec.BecTipoUtil.mapBecTipo(conEnoc, ejercicioId);
	
	//TRAER EL TIPO DE LA BECA INDUSTRIAL BASICA
	String tipoBasicaInd 	= becTipoU.getTipo(conEnoc, ejercicioId, "I").equals("none")?"0":becTipoU.getTipo(conEnoc, ejercicioId, "I");

	//TRAER EL TIPO DE LA BECA BASICA
	String tipoBasica 		= becTipoU.getTipo(conEnoc, ejercicioId, "B").equals("none")?"0":becTipoU.getTipo(conEnoc, ejercicioId, "B");

	//TRAER EL TIPO DE LA BECA ADICIONAL
	String tipoAdicional 	= becTipoU.getTipo(conEnoc, ejercicioId, "A").equals("none")?"0":becTipoU.getTipo(conEnoc, ejercicioId, "A");
%>
<div class="container-fluid">
	<h1>Listado de puestos</h1>
	<form name="frmPuestos" id="frmPuestos" method="post" action="general">
		<div class="alert alert-info">
			<a class="btn btn-primary" href="menu"><i class="fas fa-arrow-left"></i></a>			
			<div style="float:right">
				Fecha de revisión <input type="text" data-date-format="dd/mm/yyyy" id="fechaParametro" name="fechaParametro" value="<%=fecha%>" style="width:110px;"/>
				<a class="btn btn-success" onclick="javascript:document.frmPuestos.submit();">Cargar fecha</a>
				<select style="position: right;" id="ejercicioId" name="ejercicioId" onchange="document.frmPuestos.submit()">
				
<%				for(aca.financiero.ContEjercicio ej : ejercicios){
						
		%>
					<option value="<%=ej.getIdEjercicio()%>" <%if(ejercicioId.equals(ej.getIdEjercicio())){out.print("selected");}%>><%=ej.getIdEjercicio()%></option>
											
<%				}%>				
				</select>			
			</div>
		</div>
		<table id="table" class="table table-sm table-bordered">
		<thead class="table-info">
		<tr>
			<th style="width:3%;">#</th>
			<th style="width:5%;">Puesto Id</th>
			<th style="width:5%;">Nivel</th>
			<th style="width:5%;">Empresa</th>
			<th style="width:5%;">Comentario</th>
			<th style="width:7%;"><spring:message code="aca.Facultad"/></th>
			<th style="width:7%;"><spring:message code="aca.Carrera"/></th>
			<th style="width:5%;">Función</th>
			<th style="width:2%;"><spring:message code="aca.Matricula"/></th>
			<th style="width:20%;"><spring:message code="aca.Alumno"/></th>
			<th style="width:6%;"><spring:message code="aca.Inscrito"/></th>
			<th style="width:15%;"><spring:message code="aca.Genero"/></th>
			<th style="width:5%;">Tipo de Alumno</th>
			<th>Puesto</th>
			<th style="width:5%;">CCosto</th>
			<th style="width:5%;">Función</th>
			<th style="width:5%;"><spring:message code="aca.FechaInicio"/></th>
			<th style="width:5%;">Fecha Fin</th>
			<th style="width:5%;">Plaza</th>
			<th style="width:5%;">Tipos Acuerdos</th>
			<th style="width:5%;"><spring:message code="aca.Estado"/></th>
			<th style="width:5%;">Horas</th>
			<th style="width:5%;">Precio Hora</th>
			<th style="width:5%;" class="right">Mat.</th>
			<th style="width:5%;" class="right">Ens.</th>			
			<th style="width:5%;" class="right">Int.</th>			
			<th style="width:3%;">Beca Básica</th>
			<th style="width:3%;">Adicional</th>
			<th style="width:3%;">Acuerdo</th>
			<th style="width:3%;"><spring:message code="aca.Total"/></th>
			<th style="width:3%;">Diezmo</th>
			<th style="width:3%;">Total Neto</th>
		</tr>	
		</thead>	
<%
		int con = 1;

		if(!alumPuesto.isEmpty()){
			for(aca.bec.BecPuestoAlumno puesto : alumPuesto){
				
				String nombrePuesto = "-";
				
				if(mapPuestos.containsKey(puesto.getCategoriaId())){
					nombrePuesto = mapPuestos.get(puesto.getCategoriaId());
				}
				
				String categoria = "-";
				if(categorias.containsKey(puesto.getCategoriaId())){		
					categoria = categorias.get(puesto.getCategoriaId());
				}						
				
				String nombreAlumno = "";
				String generoAlumno = "";
				if (mapAlumno.containsKey( puesto.getCodigoPersonal() )){
					aca.alumno.AlumPersonal alumno = (aca.alumno.AlumPersonal) mapAlumno.get( puesto.getCodigoPersonal() );
					nombreAlumno = alumno.getNombre()+" "+alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno();
					generoAlumno = alumno.getSexo();
				}
				
				String fechaIni = puesto.getFechaIni();
				if(fechaIni==null){
					fechaIni = "01/01/1900";
				}
				String fechaFin = puesto.getFechaFin();
				if(fechaFin==null){
					fechaFin = "01/01/1901";
				}
				String fechaActual = aca.util.Fecha.getHoy();
				String fechaParam = fecha;
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				
				java.util.Date dateIni  = sdf.parse(fechaIni, new ParsePosition(0));
				java.util.Date dateFin  = sdf.parse(fechaFin, new ParsePosition(0));
				java.util.Date dateAct  = sdf.parse(fechaParam, new ParsePosition(0));
		
				String total 	= "";
				double nTotal 	= 0;
				if ( totalbeca.containsKey(puesto.getCodigoPersonal()+puesto.getPuestoId()) ) {
					String stotal = totalbeca.get(puesto.getCodigoPersonal()+puesto.getPuestoId());
					//convierto a double
					nTotal 		= Double.parseDouble(stotal);
					//paso el nuevo formato a un string vacio
					total 		= getFormato.format(nTotal);
				} else {
					total = getFormato.format(Double.parseDouble("0"));
				} 
					
				
				String diezmo 	= "";
				double nDiezmo 	= 0;
				if ( becDiezmo.containsKey(puesto.getCodigoPersonal()+puesto.getPuestoId()) ) {
					String stotal = becDiezmo.get(puesto.getCodigoPersonal()+puesto.getPuestoId());
					//convierto a double
					nDiezmo = Double.parseDouble(stotal);
					//paso el nuevo formato a un string vacio
					diezmo = getFormato.format(nDiezmo);
				} else {
					diezmo = getFormato.format(Double.parseDouble("0"));
				}  
				
				String basica 	= "";
				double nBasica 	= 0;
				if ( basicas.containsKey(puesto.getCodigoPersonal()+puesto.getPuestoId()) ) {
					String stotal = basicas.get(puesto.getCodigoPersonal()+puesto.getPuestoId());
					//convierto a double
					nBasica = Double.parseDouble(stotal);
					//paso el nuevo formato a un string vacio
					basica 	= getFormato.format(nBasica);
				} else {
					basica 	= getFormato.format(Double.parseDouble("0"));
				} 
				
				String adicional 	= "";
				double nAdicional 	= 0;
				if ( adicionales.containsKey(puesto.getCodigoPersonal()+puesto.getPuestoId()) ) {
					String stotal = adicionales.get(puesto.getCodigoPersonal()+puesto.getPuestoId());
					//convierto a double
					nAdicional = Double.parseDouble(stotal);
					//paso el nuevo formato a un string vacio
					adicional = getFormato.format(nAdicional);
				} else {
					adicional = getFormato.format(Double.parseDouble("0"));
				}
				
				if (puesto.getTipo().equals("B")) tipoPlaza = "Básica"; 
				if (puesto.getTipo().equals("P")) tipoPlaza = "Preind.";
				if (puesto.getTipo().equals("I")) tipoPlaza = "Institucional";
				if (puesto.getTipo().equals("M")) tipoPlaza = "Posgrado";
				if (puesto.getTipo().equals("T")) tipoPlaza = "Temp.";
				
			//TRAER BECA BASICA Y ADICIONAL -------------------->
				
				becAcuerdoBasica = new aca.bec.BecAcuerdo();
				becAcuerdoAdicional = new aca.bec.BecAcuerdo();
				//if (puesto.getCodigoPersonal().equals("1150326")) System.out.println("Datos:"+tipoBasica+puesto.getTipo());
				String tipoB = tipoBasica;
				if(puesto.getTipo().equals("I")){
					tipoB = tipoBasicaInd;
				}
				//if (puesto.getCodigoPersonal().equals("1150326")) System.out.println("Buscando:"+ejercicioId+":"+tipoB+":"+puesto.getIdCcosto()+":"+puesto.getPuestoId());
				String folio = becAcuerdoUB.getFolio(conEnoc, puesto.getCodigoPersonal(), ejercicioId, tipoB, puesto.getIdCcosto(), puesto.getPuestoId());
				//if (puesto.getCodigoPersonal().equals("1150326")) System.out.println("Folio:"+folio);
				if (!folio.equals("0")){
					becAcuerdoBasica.setFolio(folio);
					becAcuerdoBasica.setCodigoPersonal(puesto.getCodigoPersonal());
					becAcuerdoBasica = becAcuerdoUB.mapeaRegId(conEnoc, folio, puesto.getCodigoPersonal());
				}	
				
				String folio2 = becAcuerdoUA.getFolio(conEnoc, puesto.getCodigoPersonal(), ejercicioId, tipoAdicional, puesto.getIdCcosto(), puesto.getPuestoId());
				if (!folio2.equals("0")){
					becAcuerdoAdicional.setFolio(folio2);
					becAcuerdoAdicional.setCodigoPersonal(puesto.getCodigoPersonal());
					becAcuerdoAdicional = becAcuerdoUA.mapeaRegId(conEnoc, folio2, puesto.getCodigoPersonal());	
				}
								
				
				String inscrito;
				
				if(mapInscrito.containsKey(puesto.getCodigoPersonal()))
					inscrito = "Inscrito";
				else
					inscrito = "No Inscrito";
				
				String acuerdo="";
				if(becAcuerdoAdicional.getTipoadicional().equals("A")){
					acuerdo = "Solo Depósitos de Colportaje";					
				}else if(becAcuerdoAdicional.getTipoadicional().equals("B")){
					acuerdo = "Depósitos de Colportaje y Familia";						
				}else if(becAcuerdoAdicional.getTipoadicional().equals("C")){
					acuerdo = "Solo Depósitos de Familia";
				}else if(becAcuerdoAdicional.getTipoadicional().equals("D")){
					acuerdo = "Cantidad Fija";
				}
				
			//END TRAER BECA BASICA Y ADICIONAL -------------------->
				
				double nPromesa = 0;
				if(becAcuerdoAdicional.getPromesa() != null && !becAcuerdoAdicional.getPromesa().equals("")){
					nPromesa = Double.parseDouble(becAcuerdoAdicional.getPromesa());
				}
				String promesa 		= getFormato.format(nPromesa);
				
				String tipoAlumno 	= "-";
				if (mapTipoAlum.containsKey(puesto.getCodigoPersonal()))
					if (mapTipo.containsKey( mapTipoAlum.get(puesto.getCodigoPersonal()) ))
						tipoAlumno = mapTipo.get( mapTipoAlum.get(puesto.getCodigoPersonal()) );
				
				String nivel = "-";
				if (puesto.getNivelId().equals("1")) 
					nivel = "Bach.";
				else if (puesto.getNivelId().equals("2"))
					nivel = "Lic.";
				else if (puesto.getNivelId().equals("3"))
					nivel = "Mtría.";
				else if (puesto.getNivelId().equals("4"))
					nivel = "Doc.";
				else if (puesto.getNivelId().equals("5"))
					nivel = "NF";
				else if (puesto.getNivelId().equals("6"))
					nivel = "Esp.";
				else if (puesto.getNivelId().equals("7"))
					nivel = "Tec.";
				
				String empresa 		= "";
				String carrera 		= "";
				if (mapPlan.containsKey(puesto.getPlanId())){
					carrera = mapPlan.get(puesto.getPlanId());
				}else if (mapCarreraFormal.containsKey(puesto.getCodigoPersonal())){
					carrera = mapCarreraFormal.get(puesto.getCodigoPersonal());					
				}else if ( mapCarreraAlumno.containsKey(puesto.getCodigoPersonal()) ){
					carrera = mapCarreraAlumno.get(puesto.getCodigoPersonal());					
				}else{					
					carrera = aca.alumno.PlanUtil.getCarreraId(conEnoc, puesto.getCodigoPersonal());
				}							
				
				if (mapEmpresa.containsKey( carrera ) ){
					empresa = mapEmpresa.get( carrera );
					if (empresa.equals("1.01")){
						empresa = "UM";
					}else{
						empresa = "COVOPROM";
					}
				}
				
				String facultadId 		= "0"; 
				String nombreFacultad 	= "";
				String nombreCarrera 	= "";
				String carreraCosto 	= "0";
				if (mapCarrera.containsKey(carrera)){
					nombreCarrera 	= mapCarrera.get(carrera).getNombreCarrera();
					facultadId 		= mapCarrera.get(carrera).getFacultadId();
					nombreFacultad  = mapFac.get(facultadId).getNombreCorto();
					carreraCosto 	= mapCarrera.get(carrera).getCcostoId(); 
				}
				
				String funcionCarrera = "-";
				if (mapFunciones.containsKey(carreraCosto)){
					funcionCarrera = mapFunciones.get(carreraCosto);
				}
				
				String acuerdosAlumno = "";
				for (aca.bec.BecAcuerdo acuerdos: alumAcuerdo){
					if (acuerdos.getCodigoPersonal().equals(puesto.getCodigoPersonal()) && acuerdos.getPuestoId().equals(puesto.getPuestoId()) ){
						if (mapBecTipo.containsKey(acuerdos.getIdEjercicio()+acuerdos.getTipo())){
							acuerdosAlumno += "["+mapBecTipo.get(acuerdos.getIdEjercicio()+acuerdos.getTipo()).getNombre()+"] ";
						}						 
					}
				}
				
				// Porcentaje de Acuerdo (Matricula,enseñanza e internado)
				String porMatricula = "0";
				if (mapMatricula.containsKey(puesto.getCodigoPersonal()+puesto.getPuestoId())){
					porMatricula = mapMatricula.get(puesto.getCodigoPersonal()+puesto.getPuestoId());
				}
				
				String porEnsenanza = "0";
				if (mapEnsenanza.containsKey(puesto.getCodigoPersonal()+puesto.getPuestoId())){
					porEnsenanza = mapEnsenanza.get(puesto.getCodigoPersonal()+puesto.getPuestoId());
				}
				
				String porInternado = "0";
				if (mapInternado.containsKey(puesto.getCodigoPersonal()+puesto.getPuestoId())){
					porInternado = mapInternado.get(puesto.getCodigoPersonal()+puesto.getPuestoId());
				}
				
				String funcionDepto = "-";
				if (mapFunciones.containsKey(puesto.getIdCcosto())){
					funcionDepto = mapFunciones.get(puesto.getIdCcosto());
				}				
%>	
		<tr>
			<td><%=con%></td>
			<td><%=puesto.getPuestoId()%></td>
			<td><%=nivel%></td>
			<td><%=empresa%></td>
			<td><%=puesto.getDescripcion()%></td>
			<td><%=nombreFacultad%></td>
			<td><%=nombreCarrera%></td>
			<td><%=funcionCarrera%></td>
			<td><%=puesto.getCodigoPersonal()%></td>
			<td><%=nombreAlumno%></td>
			<td><%=inscrito%></td>
			<td><%=generoAlumno %></td>
			<td><%=tipoAlumno%></td>
			<td><%=nombrePuesto %></td>
			<td><%=ccostos.get(puesto.getIdCcosto()).getNombre()%></td>
			<td><%=funcionDepto%></td>
			<td><%=fechaIni=="01/01/1900"?"-":fechaIni%></td>
			<td><%=fechaFin=="01/01/1901"?"-":fechaFin%></td>
			<td><%=tipoPlaza%></td>
			<td><%=acuerdosAlumno%></td>
			<td>
				<%if(puesto.getEstado().equals("P")){
					out.print("Precontratado");
				}else if(puesto.getEstado().equals("C")){
					out.print("Contratado");
				} else{
					out.print("Inactivo");
				}%>
			</td>
			<td><%=becAcuerdoBasica.getHoras() %></td>
			<td><%=becAcuerdoBasica.getEnsenanza().equals("")?"&nbsp;":becAcuerdoBasica.getEnsenanza() %></td>
			<td style="text-align:right;"><%=porMatricula %></td>
			<td style="text-align:right;"><%=porEnsenanza%></td>
			<td style="text-align:right;"><%=porInternado%></td>
			<td style="text-align:right;"><%=basica %></td>
			<td style="text-align:right;"><%=adicional %></td>
			<td style="text-align:right;"><%= getFormato.format( nTotal-nBasica-nAdicional ) %></td>
			<td style="text-align:right;"><%= total %></td>
			<td style="text-align:right;"><%=diezmo %></td>
			<td style="text-align:right;"><%= getFormato.format( nTotal-nDiezmo ) %></td>
		</tr>
<%				con++;
				/* if (puesto.getCodigoPersonal().equals("1130491")) System.out.println("Datos:"+nTotal+":"+nBasica+":"+nAdicional); */
			}
		}else{
%>		
		<tr>
			<td></td>
			<td colspan="8" ><strong>No hay plazas registradas en este puesto</strong></td>
		</tr>							
<%		}%>			
		</table>		
	</form>
</div>
<script>
	jQuery('#fechaParametro').datepicker();
</script>
 <style>
 	body{
 		background : white;
 	}
 </style>
<%@ include file= "../../cierra_enoc.jsp" %>