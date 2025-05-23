<%@page import="aca.util.Fecha"%>
<%@page import="aca.afe.FesCcAfeAcuerdosUtil"%>
<%@page import="aca.bec.BecPuestoAlumno"%>
<%@page import="aca.bec.BecCategoriaUtil"%>
<%@page import="aca.financiero.ContCcosto"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@page import="java.util.*" %>
<%@page import="java.text.*" %>
<jsp:useBean scope="page" id="BecPuestoU" class="aca.bec.BecPuestoUtil"/>
<jsp:useBean scope="page" id="BecPuesto" class="aca.bec.BecPuesto"/>
<jsp:useBean scope="page" id="CCostoU" class="aca.financiero.ContCcostoUtil"/>
<jsp:useBean scope="page" id="BecPuestoAlumnoU" class="aca.bec.BecPuestoAlumnoUtil"/>
<jsp:useBean scope="page" id="ContEjercicioU"  class="aca.financiero.ContEjercicioUtil" />
<jsp:useBean id="PuestoAlumno" scope="page" class="aca.bec.BecPuestoAlumno"/>
<jsp:useBean id="BecInformeU"  class="aca.bec.BecInformeUtil" scope="page"/>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<%
	java.text.DecimalFormat getFormato		= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	
	//Lista de ejercicios
	ArrayList<aca.financiero.ContEjercicio> ejercicios 	= ContEjercicioU.getListAll(conEnoc, "ORDER BY 1 DESC");	

	//Variables
	String ejercicioId 		= request.getParameter("ejercicioId")==null?ejercicios.get(0).getIdEjercicio():request.getParameter("ejercicioId");	
	String fechaHoy 		= aca.util.Fecha.getHoy();
	String fecha 			= request.getParameter("fechaParametro")==null?fechaHoy:request.getParameter("fechaParametro");
	String cat				= "";	
	char inicial			='\0';
	String tipoPlaza		= "";	
	String nivelId			= request.getParameter("nivelId")==null?"1":request.getParameter("nivelId");
	
	//Nombres de categorías
	HashMap <String, String> categorias					= aca.bec.BecCategoriaUtil.getMapCategorias(conEnoc);
	
	//Nombres de centros de costo
	ArrayList<aca.financiero.ContCcosto> cCostos 		= CCostoU.getListCentrosCostoVacantes(conEnoc, ejercicioId, "'S'", " ORDER BY ID_CCOSTO");
	
	//Lista de puestos
	ArrayList<aca.bec.BecPuesto> lista					= new ArrayList<aca.bec.BecPuesto>();
	
	//Alumnos en los puestos
	ArrayList<aca.bec.BecPuestoAlumno> alumnos			= new ArrayList<aca.bec.BecPuestoAlumno>();	
	
	// Lista de meses con informes
	ArrayList<String> lisMeses							= BecInformeU.listMesesEnInforme(conEnoc, ejercicioId, fecha, "ORDER BY 1");	
%>

<div class="container-fluid">
	<h1>Listado de puestos</h1>
	<form name="frmPuestos" id="frmPuestos" method="post" action="reporteHoras">
		<div class="alert alert-info">
			<a class="btn btn-primary" href="menu"><i class="fas fa-arrow-left"></i></a>							
			<select style="position: right;" id="nivelId" name="nivelId" onchange="document.frmPuestos.submit()">				
				<option value="1" <%if(nivelId.equals("1")){out.print("selected");}%>>Bachillerato</option>
				<option value="'2','3','4','5'" <%if(!nivelId.equals("1")){out.print("selected");}%>>Universitario</option>										
			</select>	
			<div style="float:right">
				Fecha de revisión <input type="text" data-date-format="dd/mm/yyyy" id="fechaParametro" name="fechaParametro" value="<%=fecha%>"/>
				<a class="btn btn-success" onclick="javascript:document.frmPuestos.submit();">Cargar fecha</a>
				<select name="ejercicioId" id="ejercicioId" style="width:150px;" onchange="document.frmPuestos.submit()">
			<%
				for(aca.financiero.ContEjercicio ejercicio: ejercicios){	
			%>
					<option value="<%=ejercicio.getIdEjercicio() %>" <%if(ejercicioId.equals(ejercicio.getIdEjercicio()))out.print("selected"); %>><%=ejercicio.getIdEjercicio() %></option>
			<%
				}
			%>
			</select>		
			</div>
		</div>
<%
		for(aca.financiero.ContCcosto  nombre: cCostos){%>
			<div class="alert alert-info" style="margin-bottom:3px;">
				<h3><%=nombre.getIdCcosto() +" | "+nombre.getNombre()%></h3>
			</div> 
<%				lista = BecPuestoU.listPuestosDepto(conEnoc, nombre.getIdCcosto(), ejercicioId, fecha, " ORDER BY CATEGORIA_ID ");

				if(!lista.isEmpty()){
					for(aca.bec.BecPuesto bp : lista){										
						String catId = bp.getCategoriaId();
						if(categorias.containsKey(catId)){
							cat = categorias.get(catId);
							inicial = cat.charAt(0);
%>
						</table>
						
						<div class="alert alert-warning" style="margin-bottom:0px;">
							<span style="font-size:15px;"><%=inicial+cat.substring(1).toLowerCase()%></span> 
						</div>
					
						<table class="table table-condensed table table-nohover" style="width:100%;">	
<%						}%>
						<tr>
<%						alumnos = BecPuestoAlumnoU.getListPuestosNivel(conEnoc, ejercicioId, nombre.getIdCcosto(), bp.getCategoriaId(), nivelId, "AND FECHA_INI<= TO_DATE('"+fecha+"', 'DD/MM/YYYY') AND FECHA_FIN>=TO_DATE('"+fecha+"', 'DD/MM/YYYY')");
						if(!alumnos.isEmpty()){%>							
						<tr>
							<th style="width:3%;">#</th>
							<th style="width:7%;">Puesto Id</th>
							<th style="width:3%;"><spring:message code="aca.Matricula"/></th>
							<th style="width:20%;"><spring:message code="aca.Alumno"/></th>
							<th style="width:5%;"><spring:message code="aca.FechaInicio"/></th>
							<th style="width:5%;">Fecha Fin</th>
							<th style="width:3%; text-align:right;">Horas/Convenio</th>
<%
							for (String mes :lisMeses){
								String nombreMes = aca.util.Fecha.getMesNombreCorto( Integer.parseInt(mes));
								out.print("<th style='width:5%; text-align:right;'>"+nombreMes+"</th>");
							}
%>
							<th style="width:3%; text-align:right;">Horas/faltantes</th>
						</tr>
<%							int con = 1;
							for(aca.bec.BecPuestoAlumno obj : alumnos){								
								String alum = aca.alumno.AlumUtil.getNombreAlumno(conEnoc, obj.getCodigoPersonal(), "NOMBRE");
%>
						<tr>
<%									
								String fechaIni = obj.getFechaIni();
								if(fechaIni==null){
									fechaIni = "01/01/1900";
								}
									
								String fechaFin = obj.getFechaFin();
								if(fechaFin==null){
									fechaFin = "01/01/1901";
								}
								String fechaActual	= aca.util.Fecha.getHoy();
								String fechaParam	= fecha;
									
								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
								
								java.util.Date dateIni  = sdf.parse(fechaIni, new ParsePosition(0));
								java.util.Date dateFin  = sdf.parse(fechaFin, new ParsePosition(0));
								java.util.Date dateAct  = sdf.parse(fechaParam, new ParsePosition(0));
									
								//Fecha inicial mayor que fecha final
								if(dateIni.after(dateFin) || dateIni.equals(dateFin)){
									//System.out.println("Conflicto con fechas");	
								}
									
								PuestoAlumno.mapeaRegIdPuesto(conEnoc, obj.getPuestoId());								
								//Llena el map de las horas del alumno
								java.util.HashMap <String, String> mapHorasAlumno 	= aca.bec.BecInformeAlumnoUtil.mapHorasAlumnoPorMes(conEnoc, obj.getCodigoPersonal(), obj.getPuestoId());
								java.util.HashMap <String, String> mapEstadoAlumno	= aca.bec.BecInformeAlumnoUtil.mapEstado(conEnoc, obj.getCodigoPersonal(), obj.getPuestoId());
								//Llena el map de las evaluaciones del  alumnos
								java.util.HashMap <String, String> mapEvalAlumno 	= aca.bec.BecInformeAlumnoUtil.mapEvaluacionAlumno(conEnoc, obj.getCodigoPersonal(), obj.getPuestoId());
								
								String horasConvenio = aca.bec.BecAcuerdoUtil.getHorasConv(conEnoc, obj.getCodigoPersonal(), obj.getPuestoId());
								int horasTotal			= 0;	
								if (obj.getTipo().equals("B")) tipoPlaza = "Básica"; 
								if (obj.getTipo().equals("P")) tipoPlaza = "Preind.";
								if (obj.getTipo().equals("I")) tipoPlaza = "Institucional";
								if (obj.getTipo().equals("M")) tipoPlaza = "Posgrado";
								if (obj.getTipo().equals("T")) tipoPlaza = "Temp.";
%>									
							<td><%=con%></td>
							<td><%=obj.getPuestoId()%></td>
							<td><%=obj.getCodigoPersonal()%></td>
							<td><%=alum%></td>
							<td><%=PuestoAlumno.getFechaIni()%></td>
							<td><%=PuestoAlumno.getFechaFin()%></td>
							<td style='text-align:right;'><%=horasConvenio%></td>
<%
								for (String mes :lisMeses){
									String horasMes = "0";
									if (mapHorasAlumno.containsKey(mes)){
										horasMes = mapHorasAlumno.get(mes);
										out.print("<td style='text-align:right;'>"+horasMes+"</th>");
										horasTotal+= Integer.parseInt(horasMes);
									}else{
										out.print("<td style='text-align:right;'>-</th>");
									}
								}						 
%>						
							<td style='text-align:right;'><%=(Integer.parseInt(horasConvenio)-horasTotal)%></td>
						</tr>
<%						con++;
						 

							}%>
							</table>
							</td>
<%						}else{%>
							<tr>
								<td></td>
								<td colspan="8" ><strong>No hay plazas registradas en este puesto</strong></td>
							</tr>
<%						}%>					
					</tr>
<%					}			
				}else{%>
					<tr>
						<td colspan="8" ><strong>No hay puestos registrados</strong></td>
					</tr>
<%			}
			//  float:right
%>
			</table>
<%	}%> 
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