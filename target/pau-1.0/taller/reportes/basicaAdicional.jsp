<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.util.Fecha"%>
<%@page import="aca.afe.FesCcAfeAcuerdosUtil"%>
<%@page import="aca.bec.BecPuestoAlumno"%>
<%@page import="aca.bec.BecCategoriaUtil"%>
<%@page import="aca.financiero.ContCcosto"%>

<%@page import="java.util.*" %>
<%@page import="java.text.*" %>
<jsp:useBean scope="page" id="BecCategoria" class="aca.bec.BecCategoria"/>
<jsp:useBean scope="page" id="BecCategoriaU" class="aca.bec.BecCategoriaUtil"/>
<jsp:useBean scope="page" id="BecPuestoU" class="aca.bec.BecPuestoUtil"/>
<jsp:useBean scope="page" id="BecPuesto" class="aca.bec.BecPuesto"/>
<jsp:useBean scope="page" id="CCostoU" class="aca.financiero.ContCcostoUtil"/>
<jsp:useBean scope="page" id="BecPuestoAlumnoU" class="aca.bec.BecPuestoAlumnoUtil"/>
<jsp:useBean scope="page" id="ContEjercicioU"  class="aca.financiero.ContEjercicioUtil" />

<jsp:useBean id="becTipo"  class="aca.bec.BecTipo" scope="page"/>
<jsp:useBean id="becTipoU"  class="aca.bec.BecTipoUtil" scope="page"/>
<jsp:useBean id="becAcuerdoBasica"  class="aca.bec.BecAcuerdo" scope="page"/>
<jsp:useBean id="becAcuerdoAdicional"  class="aca.bec.BecAcuerdo" scope="page"/>
<jsp:useBean id="becAcuerdoUB"  class="aca.bec.BecAcuerdoUtil" scope="page"/>
<jsp:useBean id="becAcuerdoUA"  class="aca.bec.BecAcuerdoUtil" scope="page"/>
<jsp:useBean scope="page" id="CarreraU" class="aca.catalogo.CatCarreraUtil" />


<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<%
	//Lista de ejercicios
	ArrayList<aca.financiero.ContEjercicio> ejercicios 	= ContEjercicioU.getListAll(conEnoc, "ORDER BY 1 DESC");

	//Variables
	String ejercicioId									= request.getParameter("idEjercicio")==null?ejercicios.get(0).getIdEjercicio():request.getParameter("idEjercicio");
	
	//Nombres de categorías
	HashMap <String, String> categorias					= aca.bec.BecCategoriaUtil.getMapCategorias(conEnoc);
	
	//Nombres de centros de costo
	ArrayList<aca.financiero.ContCcosto> cCostos 		= CCostoU.getListCentrosCostoVacantes(conEnoc, ejercicioId, "'S'", "ORDER BY ID_CCOSTO");
	
	//Lista de puestos
	ArrayList<aca.bec.BecPuesto> lista					= new ArrayList<aca.bec.BecPuesto>();
	
	//Alumnos en los puestos
	ArrayList<aca.bec.BecPuestoAlumno> alumnos			= new ArrayList<aca.bec.BecPuestoAlumno>();
	
	// Map de catálogo de carreras
	HashMap <String, aca.catalogo.CatCarrera> mapCarrera	= CarreraU.getMapAll(conEnoc, "");
	
	
	//Mapa de fechas
	String fechaHoy = aca.util.Fecha.getHoy();
	String fecha = request.getParameter("fechaParametro")==null?fechaHoy:request.getParameter("fechaParametro");	
		
	//Total de beca
	java.text.DecimalFormat getFormato		= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	HashMap <String, String> totalbeca 		= FesCcAfeAcuerdosUtil.mapBeca(conEnoc, ejercicioId, fecha);
	HashMap <String, String> becDiezmo 		= FesCcAfeAcuerdosUtil.mapBecaDiezmo(conEnoc, ejercicioId, fecha);
	HashMap <String, String> basicas   		= FesCcAfeAcuerdosUtil.mapBasicas(conEnoc, ejercicioId, fecha);
	HashMap <String, String> adicionales	= FesCcAfeAcuerdosUtil.mapAdicional(conEnoc, ejercicioId, fecha);	
	
	//TRAER EL TIPO DE LA BECA INDUSTRIAL BASICA
	String tipoBasicaInd 	= becTipoU.getTipo(conEnoc, ejercicioId, "I").equals("none")?"0":becTipoU.getTipo(conEnoc, ejercicioId, "I");

	//TRAER EL TIPO DE LA BECA BASICA
	String tipoBasica 		= becTipoU.getTipo(conEnoc, ejercicioId, "B").equals("none")?"0":becTipoU.getTipo(conEnoc, ejercicioId, "B");

	//TRAER EL TIPO DE LA BECA ADICIONAL
	String tipoAdicional 	= becTipoU.getTipo(conEnoc, ejercicioId, "A").equals("none")?"0":becTipoU.getTipo(conEnoc, ejercicioId, "A");
	
	double totPromesa		= 0;
	double totFija			= 0;
	double totBasica 		= 0;
	double totAdicional 	= 0;
	double totTotal			= 0;
	double totDiezmo		= 0;
	double totNeto			= 0;
%>

 <style>
 	body{
 		background : white;
 	}
 </style>
<div class="container-fluid">
	<h1>Alumnos con beca básica y adicional</h1>
	<form name="frmPuestos" id="frmPuestos" method="post" action="basicaAdicional">
		<div class="alert alert-info">
			<a class="btn btn-primary" href="menu"><i class="fas fa-arrow-left"></i></a>
			
			<div style="float:right">
				Fecha de revisión <input type="text" data-date-format="dd/mm/yyyy" id="fechaParametro" name="fechaParametro" value="<%=fecha%>"/>
				<a class="btn btn-success" onclick="javascript:document.frmPuestos.submit();">Cargar fecha</a>
				<select style="position: right;" id="ejercicioId" name="ejercicioId" onchange="document.frmPuestos.submit()">
								
<%		for(aca.financiero.ContEjercicio ej : ejercicios){%>
					<option value="<%=ej.getIdEjercicio()%>" <%if(ejercicioId.equals(ej.getIdEjercicio())){out.print("selected");}%>><%=ej.getIdEjercicio()%></option>											
<%		}%>
				</select>	
			</div>
		</div>
<%
		for(aca.financiero.ContCcosto  nombre: cCostos){%>
			<div class="alert alert-info" style="margin-bottom:3px;">
				<h3><%=nombre.getIdCcosto() +" | "+nombre.getNombre()%></h3>
			</div> 
<%	
			totPromesa = 0; totFija = 0; totBasica = 0; totAdicional = 0; totTotal = 0; totDiezmo=0; totNeto = 0;
			alumnos = BecPuestoAlumnoU.getListDepartamento(conEnoc, ejercicioId, nombre.getIdCcosto(),
					" AND FECHA_INI<= TO_DATE('"+fecha+"', 'DD/MM/YYYY') AND FECHA_FIN>=TO_DATE('"+fecha+"', 'DD/MM/YYYY')"+
					" AND CODIGO_PERSONAL||PUESTO_ID IN (SELECT CODIGO_PERSONAL||PUESTO_ID FROM ENOC.BEC_ACUERDO WHERE TIPO IN(2,4))");
			if(!alumnos.isEmpty()){
%>				
				<table id="table" class="table table-sm table-bordered">
				<thead class="table-info">
						<tr>
							<th style="width:3%;">#</th>
							<th style="width:7%;">Puesto Id</th>
							<th style="width:15%;">Categoria</th>
							<th style="width:7%;"><spring:message code="aca.Carrera"/></th>
							<th style="width:3%;"><spring:message code="aca.Matricula"/></th>
							<th style="width:28%;"><spring:message code="aca.Alumno"/></th>
							<th style="width:5%;"><spring:message code="aca.FechaInicio"/></th>
							<th style="width:5%;">Fecha Fin</th>
							<th style="width:5%;"><spring:message code="aca.Tipo"/></th>
							<th style="width:5%;"><spring:message code="aca.Estado"/></th>
							<th style="width:5%;">Horas</th>
							<th style="width:5%;">Precio Hora</th>
							<th style="width:10%;">Criterio</th>
							<th style="width:7%;">Promesa</th>
							<th style="width:7%;">Porcentaje Adic.</th>
							<th style="width:7%;">Cantidad Fija Adic.</th>
							<th style="width:2%;">Beca Básica</th>
							<th style="width:2%;">Adicional</th>							
							<th style="width:2%;"><spring:message code="aca.Total"/></th>
							<th style="width:2%;">Diezmo</th>
							<th style="width:2%;">Total Neto</th>
						</tr>
					</thead>
<%				int con = 1;
				for(aca.bec.BecPuestoAlumno obj : alumnos){
					String alum = aca.alumno.AlumUtil.getNombreAlumno(conEnoc, obj.getCodigoPersonal(), "NOMBRE");
					
					String fechaIni = obj.getFechaIni();
					if(fechaIni==null){
						fechaIni = "01/01/1900";
					}
					String fechaFin = obj.getFechaFin();
					if(fechaFin==null){
						fechaFin = "01/01/1901";
					}
					String fechaActual = aca.util.Fecha.getHoy();
					String fechaParam = fecha;									
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
									
					java.util.Date dateIni  = sdf.parse(fechaIni, new ParsePosition(0));
					java.util.Date dateFin  = sdf.parse(fechaFin, new ParsePosition(0));
					java.util.Date dateAct  = sdf.parse(fechaParam, new ParsePosition(0));
					
					//Fecha inicial mayor que fecha final
					if(dateIni.after(dateFin) || dateIni.equals(dateFin)){
										
					}									
								
					//TOTAL DIEZMO ----------->
					String diezmo 	= "";
					double nDiezmo 	= 0;
					if ( becDiezmo.containsKey(obj.getCodigoPersonal()+obj.getPuestoId()) ) {
						String stotal = becDiezmo.get(obj.getCodigoPersonal()+obj.getPuestoId());
						//convierto a double
						nDiezmo = Double.parseDouble(stotal);
						//paso el nuevo formato a un string vacio
						diezmo = getFormato.format(nDiezmo);
					} else {
						diezmo = getFormato.format(Double.parseDouble("0"));
					}  
					
					//TOTAL BASICA ----------->
					String basica 	= "";
					double nBasica 	= 0;
					if ( basicas.containsKey(obj.getCodigoPersonal()+obj.getPuestoId()) ) {
						String stotal = basicas.get(obj.getCodigoPersonal()+obj.getPuestoId());
						//convierto a double
						nBasica = Double.parseDouble(stotal);
						//paso el nuevo formato a un string vacio
						basica = getFormato.format(nBasica);
					} else {
						basica = getFormato.format(Double.parseDouble("0"));
					} 
						
					//TOTAL ADICIONAL (ADICIONA+ACUERDOS) ----------->
					String adicional 	= "";
					double nAdicional 	= 0;
					if ( adicionales.containsKey(obj.getCodigoPersonal()+obj.getPuestoId()) ) {
						String stotal = adicionales.get(obj.getCodigoPersonal()+obj.getPuestoId());
						//convierto a double
						nAdicional = Double.parseDouble(stotal);
						//paso el nuevo formato a un string vacio
						adicional = getFormato.format(nAdicional);
					} else {
						adicional = getFormato.format(Double.parseDouble("0"));
					}					
					
					double nTotal 	= nBasica+nAdicional;
					String total 	= getFormato.format(nTotal);
					
					BecCategoriaU = new aca.bec.BecCategoriaUtil();
					BecCategoria = BecCategoriaU.mapeaRegId(conEnoc, obj.getCategoriaId());
					
			//TRAER BECA BASICA Y ADICIONAL -------------------->
					
					becAcuerdoBasica = new aca.bec.BecAcuerdo();
					becAcuerdoAdicional = new aca.bec.BecAcuerdo();
					
					String tipoB = tipoBasica;
					if(obj.getTipo().equals("I")){
						tipoB = tipoBasicaInd;
					}
					
					String folio = becAcuerdoUB.getFolio(conEnoc, obj.getCodigoPersonal(), ejercicioId, tipoB, nombre.getIdCcosto(), obj.getPuestoId());
					becAcuerdoBasica.setFolio(folio);
					becAcuerdoBasica.setCodigoPersonal(obj.getCodigoPersonal());
					becAcuerdoUB.mapeaRegId(conEnoc, folio, obj.getCodigoPersonal());
					
					String folio2 = becAcuerdoUA.getFolio(conEnoc, obj.getCodigoPersonal(), ejercicioId, tipoAdicional, nombre.getIdCcosto(), obj.getPuestoId());
					becAcuerdoAdicional.setFolio(folio2);
					becAcuerdoAdicional.setCodigoPersonal(obj.getCodigoPersonal());
					becAcuerdoUA.mapeaRegId(conEnoc, folio2, obj.getCodigoPersonal());
					
					
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
					String carrera = aca.alumno.PlanUtil.getCarreraIdPLAN(conEnoc, obj.getPlanId());
					String nombreCarrera = "-";
					if (mapCarrera.containsKey(carrera)){
						nombreCarrera = mapCarrera.get(carrera).getNombreCarrera();
					}
					
			//END TRAER BECA BASICA Y ADICIONAL -------------------->
					 
%>
						<tr>
							<td><%=con%></td>
							<td><%=obj.getPuestoId()%></td>
							<td><%=BecCategoria.getCategoriaNombre()  %></td>
							<td><%=nombreCarrera%></td>
							<td><%=obj.getCodigoPersonal()%></td>
							<td><%=alum%></td>
							<td><%=fechaIni=="01/01/1900"?"-":fechaIni%></td>
							<td><%=fechaFin=="01/01/1901"?"-":fechaFin%></td>
							<td><%if(obj.getTipo().equals("B")){out.print("Básica");}else if(obj.getTipo().equals("A")){out.print("Adicional");}else if (obj.getTipo().equals("I")){out.print("Institucional");}else{out.print("Otro");}%></td>
							<td>
<%
					if(obj.getEstado().equals("P")){
						out.print("Precontratado");
					}else if(obj.getEstado().equals("C")){
						out.print("Contratado");
					} else{
						out.print("Inactivo");
					}

					double nPromesa = 0;
					if(becAcuerdoAdicional.getPromesa() != null && !becAcuerdoAdicional.getPromesa().equals("")){
						nPromesa = Double.parseDouble(becAcuerdoAdicional.getPromesa());
					}
					String promesa 		= getFormato.format(nPromesa);
					
					double becaFija 	= 0;
					if (becAcuerdoAdicional.getTipoadicional().equals("D")){
						becaFija		= Double.parseDouble(becAcuerdoAdicional.getEnsenanza()==null?"0.0":becAcuerdoAdicional.getEnsenanza());
					}
					totPromesa 		+= nPromesa;
					totFija			+= becaFija;
					totBasica		+= nBasica;
					totAdicional	+= nAdicional;
					totTotal		+= nTotal;
					totDiezmo		+= nDiezmo;
					totNeto			+= nTotal-nDiezmo;
					
					//System.out.println(obj.getPuestoId()+":"+obj.getCodigoPersonal()+":"+becaFija);
					
%>
							</td>
							<td><%=becAcuerdoBasica.getHoras() %></td>
							<td><%=becAcuerdoBasica.getEnsenanza().equals("")?"&nbsp;":becAcuerdoBasica.getEnsenanza() %></td>
							<td><%=acuerdo %></td>
							<td style="text-align:right;"><%=promesa %></td>
							<td style="text-align:right;"><%=becAcuerdoAdicional.getValor().equals("P")?becAcuerdoAdicional.getEnsenanza()+"%":"" %></td>
							<td style="text-align:right;"><%=getFormato.format(becaFija)%></td>
							<td style="text-align:right;"><%=basica %></td>
							<td style="text-align:right;"><%=adicional %></td>					
							<td style="text-align:right;"><%=total %></td>
							<td style="text-align:right;"><%=diezmo %></td>
							<td style="text-align:right;"><%=getFormato.format( nTotal-nDiezmo )%></td>
						</tr>
<%						con++;
				}%>
						<tr>
							<td colspan="13"><spring:message code="aca.Total"/></td>
							<td style="text-align:right;"><%=getFormato.format(totPromesa) %></td>
							<td></td>
							<td style="text-align:right;"><%=getFormato.format(totFija) %></td>
							<td style="text-align:right;"><%=getFormato.format(totBasica) %></td>
							<td style="text-align:right;"><%=getFormato.format(totAdicional) %></td>
							<td style="text-align:right;"><%=getFormato.format(totTotal) %></td>
							<td style="text-align:right;"><%=getFormato.format(totDiezmo) %></td>
							<td style="text-align:right;"><%=getFormato.format(totNeto) %></td>
							
						</tr>
				</table>
					
<%			}else{%>
				<table class="table table-condensed table-fontsmall" style="width:150%;">
					<tr>
						<td><strong>No hay plazas con beca adicional registradas en este departamento</strong></td>
					</tr>
				</table>
<%			}%>
					
<%		} %>					
	
	</form>
</div>
<script>
	jQuery('#fechaParametro').datepicker();
</script>
<%@ include file= "../../cierra_enoc.jsp" %>