<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.hca.spring.HcaMaestro"%>
<%@page import="aca.vista.spring.Maestros"%>
<%@page import="aca.vista.spring.CargaAcademica"%>
<%@page import="aca.hca.spring.HcaMaestroActividad"%>
<%@page import="aca.hca.spring.HcaActividad"%>

<%	
	// Declaracion de variables
	java.text.DecimalFormat getformato= new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String periodo			= (String) request.getAttribute("periodo");	
	int numPeriodo 			= Integer.parseInt(periodo.substring(0,2))-1;
	String periodoAnt		= String.valueOf(numPeriodo);
	if (periodoAnt.length()==1){
		periodoAnt = "0"+periodoAnt+periodo.substring(0,2);
	}else{
		periodoAnt = periodoAnt+periodo.substring(0,2);
	}	
	
	int matConValor			= 0;
	int matExtras			= 0;
	int alumConValor		= 0;
	int alumExtras			= 0;
	
	String opcion			= request.getParameter("opcion");
		if(opcion == null) 
		opcion = "1";
	String cargaId 			= request.getParameter("alumnos");
	
	int semanas				= 0;
	
	float hrsDocencia		= 0F;
	float hrsActividad		= 0F;
	float hrsPeriodo		= 0F;
	
	int matCarga			= 0;
	int matCargaVale		= 0;
	int alumCargaVale		= 0;
	int alumCargaExtra		= 0;
	float hrsCargaDoc		= 0;
	float hrsCargaAct		= 0;
	float promHoras			= 0;
			

	List<CatFacultad> lisFacultades	= (List<CatFacultad>) request.getAttribute("lisCursos");
	List<Carga>lisCarga 			= (List<Carga>) request.getAttribute("lisCarga");
	List<Carga>lisCargas 			= (List<Carga>) request.getAttribute("lisCargas");
	List<Carga>lisCargaPas 			= (List<Carga>) request.getAttribute("lisCargaPas");

	HashMap<String,List<HcaMaestro>> mapaLisMaestros 		= (HashMap<String,List<HcaMaestro>>) request.getAttribute("mapaLisMaestros");
	HashMap<String, Maestros> mapaMaestros 					= (HashMap<String,Maestros>) request.getAttribute("mapaMaestros");
	HashMap<String,List<CargaAcademica>> mapaLisCursos 		= (HashMap<String,List<CargaAcademica>>) request.getAttribute("mapaLisCursos");
	HashMap<String,List<HcaMaestroActividad>> mapaLisMA 	= (HashMap<String,List<HcaMaestroActividad>>) request.getAttribute("mapaLisMA");
	HashMap<String, Boolean> mapaTieneCarga 				= (HashMap<String,Boolean>) request.getAttribute("mapaTieneCarga");
	HashMap<String, Integer> mapaEstadoCarga 				= (HashMap<String,Integer>) request.getAttribute("mapaEstadoCarga");
	HashMap<String, Float> mapaValor 						= (HashMap<String,Float>) request.getAttribute("mapaValor");
	HashMap<String, Integer> mapaFs 						= (HashMap<String,Integer>) request.getAttribute("mapaFs");
	HashMap<String, String> mapaSemanas 					= (HashMap<String,String>) request.getAttribute("mapaSemanas");
	HashMap<String, HcaActividad> mapaHcaActividad 			= (HashMap<String,HcaActividad>) request.getAttribute("mapaHcaActividad");
	
%>
<head>
	<script type="text/javascript" src="../../js/prototype.js"></script>
	<script type="text/javascript">
	function enviarArchivo(){		
			var alumnos = "";
			var cont = "";
			for(var i = 0; i < <%=lisCarga.size()%>; i++){
				if($("envia-alumno-"+i).checked){
					cont++;
					if(alumnos != "")
						alumnos += ",";
					alumnos += "'" + $("envia-alumno-"+i).value+"'";
				}
			}
			for(var i = 0; i < <%=lisCargaPas.size()%>; i++){
				if($("envia-alumnos-"+i).checked){
					cont++;
					if(alumnos != "")
						alumnos += ",";
					alumnos += "'" + $("envia-alumnos-"+i).value+"'";
				}
			}
			if(alumnos != ""){
				$("alumnos").value = alumnos;
				document.frmCargas.submit()			
			}else
				alert("Seleccione alguna carga a ver");
	}
	
	function seleccionaTodos(cantidadAlumnos){
		for(var i = 0; i < cantidadAlumnos; i++){
			$("envia-alumno-"+i).checked = "checked";
		}
	}
	
	function deseleccionaTodos(cantidadAlumnos){
		for(var i = 0; i < cantidadAlumnos; i++){
			$("envia-alumno-"+i).checked = "";
		}
	}
	</script>
</head>
<body>
<%
	if(opcion == "1"){ // Condicion para elegir cargas primero
%>
<div class="container-fluid">
<h1>Selecciona las cargas</h1>
<div class="alert alert-info"></div>
	<table  width="80%" class="table table-nohover">
		<tr>
			<th align="center">Periodo Actual</th>
			<th align="center">Periodo Pasado</th>
		</tr>
		<tr>
			<td>
			<form action="carga?opcion=<%="2"%>" method="post" name="frmCargas" target="_self">
			<input type="hidden" id="alumnos" name="alumnos" />
				<table  width="100%" class="table table-fullcondensed">
					<tr>
						<td width="30%" align="center">
							<strong>Selecciona</strong>
						</td>
						<td width="70%" align="center"><strong><spring:message code="aca.Carga"/></strong></td>
					</tr>
					
<% //ciclo que acomoda las cargas actuales
	for (int i=0; i< lisCarga.size(); i++){
		Carga cargaAct = lisCarga.get(i);
%> 
					<tr>
						<td width="30%" align="center">
							<input type="checkbox" id="envia-alumno-<%=i %>" name="cargaActId" value="<%=cargaAct.getCargaId()%>">
						</td>
						<td width="70%"><%=cargaAct.getNombreCarga()%></td>
					</tr>
<%} %>
					
				</table>
			  </form>	
			</td>
			<td valign="top">
				<table  width="100%" class="table table-fullcondensed">
					<tr>
						<td width="30%" align="center"><strong>Selecciona</strong></td>
						<td width="70%" align="center"><strong><spring:message code="aca.Carga"/></strong></td>
					</tr>
					
<%	// Ciclo que acomoda las cargas pasadas
	for (int i=0; i< lisCargaPas.size(); i++){
		Carga cargaPas = lisCargaPas.get(i);
%> 
					<tr>
						<td width="30%" align="center">
							<input type="checkbox" id="envia-alumnos-<%=i %>" name="cargaActId" value="<%=cargaPas.getCargaId()%>">
						</td>
						<td width="70%"><%=cargaPas.getNombreCarga()%></td>
					</tr>
<%} %>					
				</table>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2">
				<input type="button" value="Generar Reporte" onclick="enviarArchivo();" class="btn btn-info"/>&nbsp;&nbsp;&nbsp;
				<input type="reset" value="Borrar" class="btn btn-danger">
			</td>
		</tr>		
	</table>	
<%
	}else if(opcion != null || opcion == "2"){		
		if(lisCargas.size() > 0){	
%>
		<!-- Inicio de Estructura # 2 -->
	<br>
	<table style="width:90%" >
		<tr>
			<td align="center">
				<font face="Arial" size="3">
					<strong>Analisis de Carga Docente</strong>
				</font>
			</td>
		</tr>
		<tr>
			<td align="center">
				<a href="carga">Regresar</a>
			</td>
		</tr>
	</table>
	<br>
<%
	for(int i=0; i<lisFacultades.size();i++){ //Ciclo que trae las facultades
		CatFacultad facu = lisFacultades.get(i);
	
		List<HcaMaestro> lisMaestros = new ArrayList<HcaMaestro>();
		if(mapaLisMaestros.containsKey(facu.getFacultadId())){
			lisMaestros = mapaLisMaestros.get(facu.getFacultadId());
		}

%>
	<table style="width:90%" >
		<tr>
			<td align="center">
				<font face="Arial" size="2">
					Facultad: <strong><%=facu.getNombreFacultad()%></strong>
				</font>
			</td>
		</tr>
	</table>
	<br>
<%		
		for(int j=0; j<lisMaestros.size(); j++){ //Ciclo que trae los maestros por facultad
			HcaMaestro master = lisMaestros.get(j);					
			if(lisCargas.size() > 0){
				
				String nombreMaestro = "";
				Maestros maestro = new Maestros();
				if(mapaMaestros.containsKey(master.getCodigoPersonal())){
					maestro = mapaMaestros.get(master.getCodigoPersonal());
					nombreMaestro = maestro.getNombre()+ " "+maestro.getApellidoPaterno()+ " "+maestro.getApellidoMaterno();
				}
%>
	<table style="width:90%" >
		<tr>
			<td><%=master.getCodigoPersonal()%> <strong><%=nombreMaestro%></strong></td>
		</tr>
	</table>
	<table style="width:90%" border="1" class="table table-condensed">
		<tr>
			<th width="27%"><spring:message code="aca.Carga"/></th>
			<th width="5%">Reg.</th>
			<th width="5%"><spring:message code="aca.Estado"/></th>
			<th width="7%">Tot.Mat.</th>
			<th width="7%">Mat.Val.</th>
			<th width="7%">Al.Mat.Val.</th>
			<th width="7%">Al.Comp.</th>
			<th width="7%">Total Alum</th>
			<th width="7%">Hrs Docencia</th>
			<th width="7%">Hrs x Act</th>	
			<th width="7%">Hrs Semestre</th>
			<th width="7%">Prom. Hrs.</th>
		</tr>
<%				
				// Inicializa los valores totales
				matCarga =0; matCargaVale=0; alumCargaVale=0; alumCargaExtra=0; hrsCargaDoc=0; hrsCargaAct=0;
				
				for(int x=0; x<lisCargas.size(); x++){ //Ciclo que trae las cargas x maestro
					Carga car = lisCargas.get(x);
				
					List<CargaAcademica> lisCursos		= new ArrayList<CargaAcademica>();
					if(mapaLisCursos.containsKey(car.getCargaId()+master.getCodigoPersonal())){
						lisCursos = mapaLisCursos.get(car.getCargaId()+master.getCodigoPersonal());
					}

					List<HcaMaestroActividad> lisMA			= new ArrayList<HcaMaestroActividad>();
					if(mapaLisMA.containsKey(car.getCargaId()+master.getCodigoPersonal())){
						lisMA = mapaLisMA.get(car.getCargaId()+master.getCodigoPersonal());
					}
					
					boolean tieneCarga = false;
					if(mapaTieneCarga.containsKey(master.getCodigoPersonal()+car.getCargaId())){
						tieneCarga = mapaTieneCarga.get(master.getCodigoPersonal()+car.getCargaId());
					}
					int estadoCarga = 1;
					if(mapaEstadoCarga.containsKey(master.getCodigoPersonal()+car.getCargaId())){
						estadoCarga = mapaEstadoCarga.get(master.getCodigoPersonal()+car.getCargaId());
					}
					
					// Inicializa el valor de las variables temporales					 
					matConValor		= 0;
					matExtras		= 0;
					alumConValor	= 0;
					alumExtras		= 0;				
					hrsDocencia 	= 0F;
					hrsActividad	= 0F;
				
					// CALCULA HORAS DE DOCENCIA
					for(int y=0; y< lisCursos.size(); y++){					
						CargaAcademica cargaAca = lisCursos.get(y);	
						
						float valor	= 0F;
						if(mapaValor.containsKey(cargaAca.getCarreraId()+cargaAca.getModalidadId()+cargaAca.getNumAlum())){
							valor = mapaValor.get(cargaAca.getCarreraId()+cargaAca.getModalidadId()+cargaAca.getNumAlum());
						}
						
						int fs	= 0;
						if(mapaFs.containsKey(cargaAca.getCursoId())){
							fs = mapaFs.get(cargaAca.getCursoId());
						}
						
						semanas	= Integer.parseInt(cargaAca.getSemanas());
					
						if(cargaAca.getValeucas().equals("S")){
							matConValor++;
							alumConValor += Integer.parseInt(cargaAca.getNumAlum());
							hrsDocencia += (valor*fs*semanas);
						}else{
							matExtras++;
							alumExtras += Integer.parseInt(cargaAca.getNumAlum());
						}					
					}			
								
					// CALCULA HORAS DE ACTIVIDADES				
					for(int z=0;z<lisMA.size();z++){ 
						HcaMaestroActividad hcaMaAct = lisMA.get(z);	
						
						HcaActividad hcaActividad = new HcaActividad();
						if(mapaHcaActividad.containsKey(hcaMaAct.getActividadId())) {
							hcaActividad = mapaHcaActividad.get(hcaMaAct.getActividadId());
						}
						hrsActividad += Float.parseFloat(hcaActividad.getValor())*Float.parseFloat(hcaMaAct.getSemanas())*Float.parseFloat(hcaMaAct.getHoras());										
					}
					
					// Suma Horas totales del periodo
					hrsPeriodo 		= hrsDocencia + hrsActividad;
					
					// Obtener el promedio de horas semanales en el periodo academico. 
					String sem 		= "0";
					if(mapaSemanas.containsKey(car.getCargaId())){
						sem = mapaSemanas.get(car.getCargaId());
					}
					promHoras 		= hrsPeriodo/Float.parseFloat(sem);
						
					matCarga		+= matConValor+matExtras;
					matCargaVale	+= matConValor;
					alumCargaVale	+= alumConValor;
					alumCargaExtra	+= alumExtras;
					hrsCargaDoc		+= hrsDocencia;
					hrsCargaAct		+= hrsActividad;
%>
		<tr>
			<td><%=car.getNombreCarga()%></td>
			<td><% if (tieneCarga) out.println("SI"); else out.println("NO"); %></td>
			<td><% if (estadoCarga==1) out.println("Abierta"); else out.println("Cerrada"); %></td>
			<td align="center"><%=matConValor+matExtras%></td>
			<td align="center"><%=matConValor%></td>
			<td align="right"><%=alumConValor%></td>
			<td align="right"><%=alumExtras%></td>
			<td align="right"><%=alumConValor+alumExtras%></td>
			<td align="right"><%=getformato.format(hrsDocencia)%></td>
			<td align="right"><%=getformato.format(hrsActividad)%></td>
			<td align="right"><%=getformato.format(hrsPeriodo)%></td>
			<td align="right"><%=getformato.format(promHoras)%></td>
		</tr>
<%
				} //Cierre del for de Cargas
%>
		<tr bgcolor="#cccccc">
			<td colspan="3">T o t a l e s . . .</td>
			<td align="center"><%=matCarga%></td>
			<td align="center"><%=matCargaVale%></td>
			<td align="right"><%=alumCargaVale%></td>
			<td align="right"><%=alumCargaExtra%></td>
			<td align="right"><%=alumCargaVale+alumCargaExtra%></td>
			<td align="right"><%=getformato.format(hrsCargaDoc)%></td>
			<td align="right"><%=getformato.format(hrsCargaAct)%></td>
			<td align="right"><%=getformato.format(hrsCargaDoc+hrsCargaAct)%></td>
			<td align="right">&nbsp;</td>
		</tr> 
	</table>
	<br>
<%	
			} //Cierre del If
		} //Cierre del for de Maestros x facultad
	}//Cierre del for de facultades	
		}else{//Cierre de condicion de listor de cargas > 0
%>
		<br>
		<br>
		<br>
		<table style="width:50%"  >
			<tr>
				<td align="center">
					<font size="3" color="red">
						<strong>No hay cargas para ningun maestro... Intente con otra carga...!!</strong>
					</font>
				</td>
			</tr>
			<tr>
			<td align="center">
				<a href="carga.jsp">
					Regresar
				</a>
			</td>
		</tr>
		</table>
		</div>
<%			
		} //Cierre de else If
	} //Cierre de condicion
%>
</body>