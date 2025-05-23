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

<jsp:useBean scope="page" id="BecPuestoAlumnoU" class="aca.bec.BecPuestoAlumnoUtil"/>
<jsp:useBean scope="page" id="ContEjercicioU" class="aca.financiero.ContEjercicioUtil" />
<jsp:useBean scope="page" id="CarreraU" class="aca.catalogo.CatCarreraUtil" />
<jsp:useBean scope="page" id="becTipo" class="aca.bec.BecTipo"/>
<jsp:useBean scope="page" id="becAcuerdoBasica" class="aca.bec.BecAcuerdo"/>
<jsp:useBean scope="page" id="becAcuerdoAdicional" class="aca.bec.BecAcuerdo"/>
<jsp:useBean scope="page" id="AlumnoU" class="aca.alumno.AlumUtil"/>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<%

	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String ejercicioSesion				= (String) session.getAttribute("ejercicioId");
	String ejercicioId					= request.getParameter("ejercicioId")==null?ejercicioSesion:request.getParameter("ejercicioId");
	String fechaHoy 					= aca.util.Fecha.getHoy();
	String fecha 						= request.getParameter("fechaParametro")==null?fechaHoy:request.getParameter("fechaParametro");
	String tipoPlaza					= "";
	String condicion		 			= " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = '"+ejercicioId+"' "+
			  "AND FECHA_INI<= TO_DATE('"+fecha+"', 'DD/MM/YYYY') AND FECHA_FIN>=TO_DATE('"+fecha+"', 'DD/MM/YYYY'))";
	
			
	//Alumnos en los puestos
	ArrayList<aca.bec.BecPuestoAlumno> alumPuesto			= new ArrayList<aca.bec.BecPuestoAlumno>();
	
	//Ejercicios a partir del 2013
	ArrayList<aca.financiero.ContEjercicio> ejercicios	 	= ContEjercicioU.getListProximos(conEnoc, "ORDER BY ID_EJERCICIO DESC");
	// Map de empresa que pertenece a las carreras
	HashMap <String, String> mapEmpresa						= CarreraU.mapEmpresa(conEnoc);
		
	// Map de la carrera del alumno
	HashMap <String, String> mapCarreraAlumno				= BecPuestoAlumnoU.mapAlumCarrera(conEnoc, ejercicioId, fecha);	
	
	// Map de centros de costo
	HashMap <String, ContCcosto> ccostos					= aca.financiero.ContCcostoUtil.getMapCentrosCosto(conEnoc, ejercicioId);
		
	// Map de tipo de alumno
	HashMap <String, String> mapTipoAlum					= aca.alumno.AcademicoUtil.getMapTipoAlumno(conEnoc, condicion);
		
	// Map de catalogo de tipos de alumnos
	HashMap <String, String> mapTipo						= aca.catalogo.TipoAlumnoUtil.getMapNombreTipo(conEnoc);
	
	// Map de catálogo de carreras
	HashMap <String, aca.catalogo.CatCarrera> mapCarrera	= CarreraU.getMapAll(conEnoc, "");
	
	// Map de alumnos
	HashMap <String, aca.alumno.AlumPersonal> mapAlumno		= AlumnoU.mapBecadosFecha(conEnoc, fecha);	
	
	//Total de beca	
	HashMap <String, String> becAlumTipo 		= FesCcAfeAcuerdosUtil.mapBecaAlumPorTipo(conEnoc, ejercicioId, fecha);
	HashMap <String, String> totalbeca 			= FesCcAfeAcuerdosUtil.mapBeca(conEnoc, ejercicioId, fecha);
	HashMap <String, String> becDiezmo 			= FesCcAfeAcuerdosUtil.mapBecaDiezmo(conEnoc, ejercicioId, fecha);	
	
	double uno 			= 0;
	double dos 			= 0;
	double tres 		= 0;
	double cuatro 		= 0;
	double cinco 		= 0;
	double seis 		= 0;			
	double siete 		= 0;
	double ocho 		= 0;
	double nueve 		= 0;
	double diez 		= 0;
	double once 		= 0;
	double doce 		= 0;
	double trece 		= 0;
	double catorce 		= 0;
	double quince 		= 0;
	double dieciseis 	= 0;
	double diescisiete 	= 0;
%>
<div class="container-fluid">
	<h1>Reporte de becas por tipo</h1>
	<form name="frmPuestos" id="frmPuestos" method="post" action="becasTipos">
		<div class="alert alert-info">
			<a class="btn btn-primary" href="menu"><i class="fas fa-arrow-left"></i></a>
			
			<div style="float:right">
				Fecha de revisión <input type="text" data-date-format="dd/mm/yyyy" id="fechaParametro" name="fechaParametro" value="<%=fecha%>"/>
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
			<th style="width:7%;">Nivel</th>
			<th style="width:7%;">Empresa</th>
			<th style="width:5%;"><spring:message code="aca.Carrera"/></th>
			<th style="width:3%;"><spring:message code="aca.Matricula"/></th>
			<th style="width:20%;"><spring:message code="aca.Alumno"/></th>
			<th style="width:5%;">Tipo de Alumno</th>
			<th style="width:5%;">CCosto</th>
			<th style="width:5%;">1</th>
			<th style="width:5%;">2</th>
			<th style="width:5%;">3</th>
			<th style="width:5%;">4</th>
			<th style="width:5%;">5</th>
			<th style="width:5%;">6</th>
			<th style="width:5%;">7</th>
			<th style="width:5%;">8</th>
			<th style="width:5%;">9</th>			
			<th style="width:5%;">10</th>
			<th style="width:5%;">11</th>
			<th style="width:5%;">12</th>
			<th style="width:5%;">13</th>
			<th style="width:5%;">14</th>
			<th style="width:5%;">15</th>
			<th style="width:5%;">16</th>
			<th style="width:5%;">17</th>		
			<th style="width:5%;"><spring:message code="aca.Total"/></th>
			<th style="width:3%;">Diezmo</th>
			<th style="width:3%;">Total Neto</th>
		</tr>	
		</thead>	
<%
		int con = 1;
		alumPuesto = BecPuestoAlumnoU.getListAllEjercicio(conEnoc, ejercicioId, "AND FECHA_INI<= TO_DATE('"+fecha+"', 'DD/MM/YYYY') AND FECHA_FIN>=TO_DATE('"+fecha+"', 'DD/MM/YYYY')");
		if(!alumPuesto.isEmpty()){
			for(aca.bec.BecPuestoAlumno puesto : alumPuesto){
				
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
				
				String empresa 		= "";				
				String carrera 	= aca.alumno.PlanUtil.getCarreraIdPLAN(conEnoc, puesto.getPlanId());
				if (mapEmpresa.containsKey( carrera ) ){
					empresa = mapEmpresa.get( carrera );
					if (empresa.equals("1.01")){
						empresa = "UM";
					}else{
						empresa = "COVOPROM";
					}
				}
				
				String nombreCarrera = "";
				if (mapCarrera.containsKey(carrera)){
					nombreCarrera = mapCarrera.get(carrera).getNombreCarrera();
				}
				
				String nombreAlumno = "";
				if (mapAlumno.containsKey( puesto.getCodigoPersonal() )){
					aca.alumno.AlumPersonal alumno = (aca.alumno.AlumPersonal) mapAlumno.get( puesto.getCodigoPersonal() );
					nombreAlumno = alumno.getNombre()+" "+alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno();	
				}
				
				String tipoAlumno 	= "-";
				if (mapTipoAlum.containsKey(puesto.getCodigoPersonal())){
					if (mapTipo.containsKey( mapTipoAlum.get(puesto.getCodigoPersonal()) ))
						tipoAlumno = mapTipo.get( mapTipoAlum.get(puesto.getCodigoPersonal()) );
				}	
					
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
				}else{
					diezmo = getFormato.format(Double.parseDouble("0"));
				}		
				
				 
%>	
		<tr>
			<td><%=con%></td>			
			<td><%=nivel%></td>
			<td><%=empresa%></td>
			<td><%=nombreCarrera%></td>
			<td><%=puesto.getCodigoPersonal()%></td>
			<td><%=nombreAlumno%></td>
			<td><%=tipoAlumno%></td>
			<td><%=ccostos.get(puesto.getIdCcosto()).getNombre() %></td>
			
			<%
				

				for(int i = 1; i<18; i++){
					if(becAlumTipo.containsKey(puesto.getCodigoPersonal()+puesto.getPuestoId()+i)){
							if(Integer.toString(i).equals("1")){
								uno += Double.parseDouble(becAlumTipo.get(puesto.getCodigoPersonal()+puesto.getPuestoId()+i));
							}else if(Integer.toString(i).equals("2")){
								dos += Double.parseDouble(becAlumTipo.get(puesto.getCodigoPersonal()+puesto.getPuestoId()+i));
							}else if(Integer.toString(i).equals("3")){
								tres += Double.parseDouble(becAlumTipo.get(puesto.getCodigoPersonal()+puesto.getPuestoId()+i));
							}else if(Integer.toString(i).equals("4")){
								cuatro += Double.parseDouble(becAlumTipo.get(puesto.getCodigoPersonal()+puesto.getPuestoId()+i));
							}else if(Integer.toString(i).equals("5")){
								cinco += Double.parseDouble(becAlumTipo.get(puesto.getCodigoPersonal()+puesto.getPuestoId()+i));
							}else if(Integer.toString(i).equals("6")){
								seis += Double.parseDouble(becAlumTipo.get(puesto.getCodigoPersonal()+puesto.getPuestoId()+i));
							}else if(Integer.toString(i).equals("7")){
								siete += Double.parseDouble(becAlumTipo.get(puesto.getCodigoPersonal()+puesto.getPuestoId()+i));
							}else if(Integer.toString(i).equals("8")){
								ocho += Double.parseDouble(becAlumTipo.get(puesto.getCodigoPersonal()+puesto.getPuestoId()+i));
							}else if(Integer.toString(i).equals("9")){
								nueve += Double.parseDouble(becAlumTipo.get(puesto.getCodigoPersonal()+puesto.getPuestoId()+i));
							}else if(Integer.toString(i).equals("10")){
								diez += Double.parseDouble(becAlumTipo.get(puesto.getCodigoPersonal()+puesto.getPuestoId()+i));
							}else if(Integer.toString(i).equals("11")){
								once += Double.parseDouble(becAlumTipo.get(puesto.getCodigoPersonal()+puesto.getPuestoId()+i));
							}else if(Integer.toString(i).equals("12")){
								doce += Double.parseDouble(becAlumTipo.get(puesto.getCodigoPersonal()+puesto.getPuestoId()+i));
							}else if(Integer.toString(i).equals("13")){
								trece += Double.parseDouble(becAlumTipo.get(puesto.getCodigoPersonal()+puesto.getPuestoId()+i));
							}else if(Integer.toString(i).equals("14")){
								catorce += Double.parseDouble(becAlumTipo.get(puesto.getCodigoPersonal()+puesto.getPuestoId()+i));
							}else if(Integer.toString(i).equals("15")){
								quince += Double.parseDouble(becAlumTipo.get(puesto.getCodigoPersonal()+puesto.getPuestoId()+i));
							}else if(Integer.toString(i).equals("16")){
								dieciseis += Double.parseDouble(becAlumTipo.get(puesto.getCodigoPersonal()+puesto.getPuestoId()+i));
							}else if(Integer.toString(i).equals("17")){
								diescisiete += Double.parseDouble(becAlumTipo.get(puesto.getCodigoPersonal()+puesto.getPuestoId()+i));
							}


					%>
						<td style="text-align:right;"><%=getFormato.format(Double.parseDouble(becAlumTipo.get(puesto.getCodigoPersonal()+puesto.getPuestoId()+i))) %></td>
				<%}else{%>
						<td style="text-align:right;"></td>		
				<%}
				
				}
			%>
			<td style="text-align:right;"><%= total %></td>
			<td style="text-align:right;"><%=diezmo %></td>
			<td style="text-align:right;"><%= getFormato.format( nTotal-nDiezmo ) %></td>
		</tr>
<%				con++;
			}%>
			<tr>
				<td colspan="8" ></td>
				<td><%=getFormato.format(uno)%></td>
				<td><%=getFormato.format(dos)%></td>
				<td><%=getFormato.format(tres)%></td>
				<td><%=getFormato.format(cuatro)%></td>
				<td><%=getFormato.format(cinco)%></td>
				<td><%=getFormato.format(seis)%></td>
				<td><%=getFormato.format(siete)%></td>
				<td><%=getFormato.format(ocho)%></td>
				<td><%=getFormato.format(nueve)%></td>
				<td><%=getFormato.format(diez)%></td>
				<td><%=getFormato.format(once)%></td>
				<td><%=getFormato.format(doce)%></td>
				<td><%=getFormato.format(trece)%></td>
				<td><%=getFormato.format(catorce)%></td>
				<td><%=getFormato.format(quince)%></td>
				<td><%=getFormato.format(dieciseis)%></td>
				<td><%=getFormato.format(diescisiete)%></td>
				<td colspan="3" ></td>
				
			</tr>		
<%		
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