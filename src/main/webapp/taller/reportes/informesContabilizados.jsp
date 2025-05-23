<%@page import="java.util.HashMap"%>
<%@page import="aca.afe.FesCcAfeAcuerdosUtil"%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean scope="page" id="BecInformeAlumnoU"  class="aca.bec.BecInformeAlumnoUtil" />
<jsp:useBean scope="page" id="FesCcAfeAcuerdosU"  class="aca.afe.FesCcAfeAcuerdosUtil" />
<jsp:useBean scope="page" id="AlumPersonalU"  class="aca.alumno.AlumUtil" />
<jsp:useBean scope="page" id="BecPuestoAlumnoU"  class="aca.bec.BecPuestoAlumnoUtil" />
<jsp:useBean scope="page" id="MapaPlanU"  class="aca.plan.PlanUtil" />
<jsp:useBean scope="page" id="CatCarreraU"  class="aca.catalogo.CatCarreraUtil" />
<%	
	String ejercicioId 		= (String) session.getAttribute("ejercicioId");
	String informes 		= request.getParameter("informes");
	String arrInformes []   = informes.split(",");
	String nombre			= "";
	String estadoInforme	= ejercicioId.equals(aca.util.Fecha.getEjercicioHoy())?"2":"3";
	
	java.text.DecimalFormat getFormato						= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	java.text.DecimalFormat getFormato2						= new java.text.DecimalFormat("###,##0.0;-###,##0.0");
	
	ArrayList<String> alumnos 								= BecInformeAlumnoU.alumnosInformes(conEnoc, ejercicioId, informes, estadoInforme,"ORDER BY 1");			 	
	ArrayList<aca.bec.BecAcuerdo> lisAcuerdo 				= aca.bec.BecAcuerdoUtil.acuerdosInformes(conEnoc, ejercicioId, informes, estadoInforme, "ORDER BY MATRICULA, TIPO");
	
	HashMap<String, String> mapPuesto 						= aca.bec.BecInformeAlumnoUtil.mapPuesto(conEnoc, informes);
	HashMap<String, String> mapHoras 						= aca.bec.BecInformeAlumnoUtil.mapHorasAlumno(conEnoc, ejercicioId);
	HashMap<String, aca.bec.BecTipo> mapBecTipo				= aca.bec.BecTipoUtil.mapBecTipo(conEnoc, ejercicioId);
	HashMap<String, aca.alumno.AlumPersonal> mapNombres		= aca.alumno.AlumUtil.mapAlumnosInformes(conEnoc, ejercicioId);
	HashMap<String, aca.bec.BecPuestoAlumno> puestos 		= BecPuestoAlumnoU.getMapPuestos(conEnoc, ejercicioId);
	java.util.HashMap <String, aca.financiero.ContCcosto> ccostos = aca.financiero.ContCcostoUtil.getMapCentrosCosto(conEnoc, ejercicioId);
	
	HashMap <String, String> mapBeca 						= FesCcAfeAcuerdosUtil.mapBecaSinFecha(conEnoc, ejercicioId);
	HashMap <String, String> mapDiezmo 						= FesCcAfeAcuerdosUtil.mapBecaDiezmoSinFecha(conEnoc, ejercicioId);
	HashMap <String, String> mapBasica 						= FesCcAfeAcuerdosUtil.mapBecaBasicaEnPuesto(conEnoc, ejercicioId);
	HashMap <String, String> mapAdicional					= FesCcAfeAcuerdosUtil.mapBecaAdicionalEnPuesto(conEnoc, ejercicioId);
	HashMap <String, String> mapPlan						= MapaPlanU.mapCarreraPlan(conEnoc);
	HashMap <String, aca.catalogo.CatCarrera> mapCarrera	= CatCarreraU.getMapAll(conEnoc,"");
	// Mapa de funciones en SunPlus
	HashMap <String, String> mapaFuncion					= aca.financiero.SunPlusFuncionesUtil.mapaFunciones(conEnoc);
%>

<div class="container-fluid">
	<h2>Informe para contabilidad</h2>	
	<div class="alert alert-info">
		<a href="informe" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>	
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">
		<tr>
			<th rowspan="2">#</th>
			<th rowspan="2"><spring:message code="aca.Codigo"/></th>
			<th rowspan="2"><spring:message code="aca.Alumno"/></th>
<%
		for(String inf : arrInformes){
			inf = inf.replaceAll("'", "");
%>
			<th rowspan="2">Carrera</th>
			<th rowspan="2">Empresa</th>
			<th colspan="4"><%=aca.bec.BecInformeUtil.getNombreInforme(conEnoc, inf) %></th>
<%		} %>
			<th colspan="3">Tipos de <spring:message code="aca.Beca"/></th>
			<th colspan="11">Datos Convenio</th>
		</tr>
		<tr>					
<%
		for(String inf : arrInformes){
			inf = inf.replaceAll("'", "");
	%>
			<td><strong>Horas</strong></td>
			<td><strong>Básica</strong></td>
			<td><strong>Diezmo</strong></td>
			<td><strong>Adicional</strong></td>
			<td><strong>Diezmo</strong></td>
	<%	}	%>
		   	<td><strong>Beca # 1</strong></td>
		   	<td><strong>Beca # 2</strong></td>
		   	<td><strong>Beca # 3</strong></td>
		   	<td><strong>Depto.</strong></td>
		   	<td><strong>Nombre Depto.</strong></td>
		   	<td><strong>SunPlus</strong></td>		   	
			<td><strong>Horas</strong></td>
			<td><strong>Basica</strong></td>
			<td><strong>Adicional</strong></td>			
			<td><strong>Beca Total</strong></td>
			<td><strong>Diezmo</strong></td>
			<td><strong>Beca Neta</strong></td>
		</tr>
		</thead>
		
<%
		int cont = 0;
		for(String alumno : alumnos){
			nombre = "";
			if(mapNombres.containsKey(alumno)){
				nombre = mapNombres.get(alumno).getNombreLegal();
			}
			cont++;
%>
		<tr>
			<td><%=cont %></td>
			<td><%=alumno %></td>
			<td><%=nombre%></td>
<%
				
				String puesto = "0";
				
				for(String inf : arrInformes){
					inf = inf.replaceAll("'", "");					
						
					if(mapPuesto.containsKey(alumno+inf)){
						puesto = mapPuesto.get(alumno+inf);
					}
					
					String horas 	= "0";
					if(mapHoras.containsKey(alumno+puesto+inf)){
						horas = mapHoras.get(alumno+puesto+inf);
					}					
						
					//Consultar el folio del acuerdo basico
					String folio = aca.bec.BecAcuerdoUtil.getFolioAcuerdoBasico(conEnoc, ejercicioId, alumno, puesto);	
					
					double credito		= Double.parseDouble(aca.bec.BecInformeAlumnoUtil.saldoAlumnoInforme(conEnoc, puesto, alumno, ","+inf+",", "C"));					
					
					
					double becaBasica		= 0;
					double becaAdicional	= 0;
					String horasConvenio	= "0";
					
					for(aca.bec.BecAcuerdo acuerdo : lisAcuerdo){
						if(!acuerdo.getCodigoPersonal().equals(alumno) || !acuerdo.getPuestoId().equals(puesto)){
							continue;
						}
						
						if (mapBecTipo.containsKey(ejercicioId+acuerdo.getTipo())){
							aca.bec.BecTipo becTipo = (aca.bec.BecTipo) mapBecTipo.get( ejercicioId+acuerdo.getTipo() );
							if (becTipo.getAcuerdo().equals("B")||becTipo.getAcuerdo().equals("I")){
								horasConvenio = acuerdo.getHoras();
							}
						}
					}
					
					if ( mapBasica.containsKey(alumno+puesto) ) {
						becaBasica = Double.parseDouble(mapBasica.get(alumno+puesto));
					}
					
					if ( mapAdicional.containsKey(alumno+puesto) ) {
						becaAdicional = Double.parseDouble(mapAdicional.get(alumno+puesto));
					}
					
					becaBasica 		= (becaBasica/Double.parseDouble(horasConvenio)*Double.parseDouble(horas));
					becaAdicional 	= (becaAdicional/Double.parseDouble(horasConvenio)*Double.parseDouble(horas));
					
					double diezmoBasica			= 0;
					double diezmoAdicional	= 0;
					
					diezmoBasica = (becaBasica * 10) / 100;
					diezmoAdicional = (becaAdicional * 10) / 100;
					
					
					// Si no encuentras el importe de la beca básica(Se presenta cuando se cancelan plazas de trabajo) 
					if (becaBasica < 1 && getFormato.format(diezmoBasica).equals(getFormato.format(credito*0.10)) ){
						becaBasica = credito;
					}
					
					//if (alumno.equals("1120289")) System.out.println("Datos:"+puesto+":"+horas+":"+inf+":"+folio+":"+becaBasica+":"+credito+":"+diezmo+":"+credito*0.10);
					String muestaCarrera = mapPlan.containsKey(puestos.containsKey(puesto)?puestos.get(puesto).getPlanId():"-")?mapPlan.get(puestos.get(puesto).getPlanId()):"-";
					
					String carrera = "-";
					
					if(mapCarrera.containsKey(muestaCarrera)){
						carrera = mapCarrera.get(muestaCarrera).getNombreCarrera();
					}
					
					String empresa = "UM";
					
					if(muestaCarrera.equals("10209") || muestaCarrera.equals("102010")){
						empresa = "COVOPROM";
					}
					
					
				%>
					<td><%=carrera%></td>
					<td><%=empresa%></td>
					<td style="text-align:right;"><%= horas %></td>
					<td style="text-align:right;"><%= getFormato.format(becaBasica)%></td>
					<td style="text-align:right;"><%= getFormato.format(diezmoBasica)%></td>
					<td style="text-align:right;"><%= getFormato.format(becaAdicional)%></td>
					<td style="text-align:right;"><%= getFormato.format(diezmoAdicional)%></td>
				<%
				} 
				%>
				
				<%		
				double promesa 			= 0;
				double porcentaje 		= 0;
				int tamanoLista			= 0;
				String horasConvenio	= "0";
				
				for(aca.bec.BecAcuerdo acuerdo : lisAcuerdo){
					if(!acuerdo.getCodigoPersonal().equals(alumno) || !acuerdo.getPuestoId().equals(puesto)){
						continue;
					}
					
					tamanoLista++;					
				/*	
					if( acuerdo.getValor().equals("P") && Double.parseDouble(acuerdo.getPromesa()) > 0 ){
						promesa 		+= Double.parseDouble(acuerdo.getPromesa());
						porcentaje 		+= Double.parseDouble(acuerdo.getMatricula()) + Double.parseDouble(acuerdo.getEnsenanza()) + Double.parseDouble(acuerdo.getInternado());
					}
				*/	
					if (mapBecTipo.containsKey(ejercicioId+acuerdo.getTipo())){
						aca.bec.BecTipo becTipo = (aca.bec.BecTipo) mapBecTipo.get( ejercicioId+acuerdo.getTipo() );
						if (becTipo.getAcuerdo().equals("B")||becTipo.getAcuerdo().equals("I")){
							horasConvenio = acuerdo.getHoras();
						}
					}
				%>
					
					<td><%=aca.bec.BecTipoUtil.getTipoNombre(conEnoc, acuerdo.getTipo(), ejercicioId) %></td>
				<%
					
				} 
				if(tamanoLista==0){
					out.print("<td>&nbsp</td>"); 
					out.print("<td>&nbsp</td>");
					out.print("<td>&nbsp</td>");
				 }else if(tamanoLista==1){
					out.print("<td>&nbsp</td>"); 
					out.print("<td>&nbsp</td>"); 
				 }else if(tamanoLista==2){
					 out.print("<td>&nbsp</td>");
				 }
				
				/* TRAER CENTRO DE COSTO */
				String ccosto = "";
				String nCosto = "";
				
				if(puestos.containsKey(puesto)){
					ccosto = puestos.get(puesto).getIdCcosto();
					nCosto = puestos.get(puesto).getIdCcosto();
					if(ccostos.containsKey(ccosto)){
						ccosto = ccostos.get(ccosto).getNombre();
						
					}
				}
				
				/* BECA BASICA */
				double basica 	= 0;
				if ( mapBasica.containsKey(alumno+puesto) ) {
					basica = Double.parseDouble(mapBasica.get(alumno+puesto));
				}
				
				/* BECA ADICIONAL */
				double adicional 	= 0;
				if ( mapAdicional.containsKey(alumno+puesto) ) {
					adicional = Double.parseDouble(mapAdicional.get(alumno+puesto));
				}
				
				/* BECA TOTAL EN EL PUESTO */
				double becaTotal 	= 0;
				if ( mapBeca.containsKey(alumno+puesto) ) {
					becaTotal = Double.parseDouble(mapBeca.get(alumno+puesto));
				}
				
				// Diezmo 	
				double diezmo 	= 0;
				if ( mapDiezmo.containsKey(alumno+puesto) ) {
					diezmo = Double.parseDouble(mapDiezmo.get(alumno+puesto));
				}	
				
				String funcion = "-";
				if (mapaFuncion.containsKey(nCosto)){
					funcion = mapaFuncion.get(nCosto);
				}
				
				%>
					<td><%=nCosto %></td>
					<td><%=ccosto %></td>
					<td><%=funcion%></td>
					<td style="text-align:right;"><%=horasConvenio%></td>
					<td style="text-align:right;"><%=getFormato.format(basica)%></td>
					<td style="text-align:right;"><%=getFormato.format(adicional)%></td>										
					<td style="text-align:right;"><%= getFormato.format( becaTotal ) %></td>
					<td style="text-align:right;"><%=getFormato.format(diezmo)%></td>
					<td style="text-align:right;"><%= getFormato.format( becaTotal-diezmo ) %></td>
				</tr>
		<%
			} 
		%>
	</table>	
	
</div>

 <style>
 	body{
 		background : white;
 	}
 </style>
<%@ include file= "../../cierra_enoc.jsp" %>