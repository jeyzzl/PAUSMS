<%@ page import="java.text.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.financiero.spring.ContEjercicio"%>
<%@ page import="aca.financiero.spring.ContCcosto"%>
<%@ page import="aca.bec.spring.BecInforme"%>
<%@ page import="aca.bec.spring.BecInformeAlumno"%>
<%@ page import="aca.bec.spring.BecPuestoAlumno"%>
<%@ page import="aca.acceso.spring.Acceso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<%
	String idEjercicio 			= (String) session.getAttribute("ejercicioId");
	String codigo				= (String) session.getAttribute("codigoPersonal");	
	String acceso				= (String) request.getAttribute("acceso");
	boolean admin				= (boolean) request.getAttribute("admin");
	String deptoId				= (String) request.getAttribute("deptoId");
	String informeId			= (String) request.getAttribute("informeId");
	BecInforme becInforme 		= (BecInforme) request.getAttribute("becInforme");
	String tipoInforme 			= becInforme.getNivel();
	String year					= aca.util.Fecha.getHoy().substring(6,10);		
	String ejercicioAnt			= "001-"+String.valueOf(Integer.parseInt(year)-1);	
	String mensaje 				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje"); 
	
	
	List<ContEjercicio> lisEjercicios 				= (List<ContEjercicio>)request.getAttribute("lisEjercicios");	
	List<BecInforme> lisInformes 					= (List<BecInforme>)request.getAttribute("lisInformes");	
	List<ContCcosto> lisDeptos 						= (List<ContCcosto>)request.getAttribute("lisDeptos");	
	HashMap<String,String> mapaCategorias 			= (HashMap<String,String>)request.getAttribute("mapaCategorias");
	HashMap<String,String> mapaPrepa 				= (HashMap<String,String>)request.getAttribute("mapaPrepa");
	HashMap<String,String> mapaInscritos			= (HashMap<String,String>)request.getAttribute("mapaInscritos");
	
	List<BecPuestoAlumno> lisPuestos				= (List<BecPuestoAlumno>)request.getAttribute("lisPuestos");		 
	List<BecInforme> lisInfAnt 						= (List<BecInforme>)request.getAttribute("lisInfAnt");
	
	HashMap<String,String> mapaHorasAlumno 			= (HashMap<String,String>) request.getAttribute("mapaHorasAlumno");		
	HashMap<String,String> mapaHorasInforme			= (HashMap<String,String>) request.getAttribute("mapaHorasInforme");
	HashMap<String,String> mapaHorasAco 			= (HashMap<String,String>) request.getAttribute("mapaHorasAco");	
	HashMap<String,String> mapaHorasTotal 			= (HashMap<String,String>) request.getAttribute("mapaHorasTotal");
	HashMap<String,String> mapaAlumnos 				= (HashMap<String,String>) request.getAttribute("mapaAlumnos");
	HashMap<String,BecInformeAlumno> mapaInformes 	= (HashMap<String,BecInformeAlumno>) request.getAttribute("mapaInformes");
%>
<style>
	body{
		background:white;
	}
	.puestosAlum td, .puestosAlum th{
		background: white !important;
	}
	
	.puestosAlum th{
		color: black !important;
		border: 1px solid #DCDEDE !important;
	}
</style>
<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<div class="container-fluid">
	<h2>Informe mensual</h2>	
<%	
	if (!mensaje.equals("-")) out.print(mensaje); 
%>
	<form action="informe" name="forma" method="get">	
	<div class="alert alert-info d-flex align-items-center">
		<select name="EjercicioId" id="EjercicioId" style="width:120px;" onchange="document.forma.submit()" class="form-select">
<%
	for(ContEjercicio ejercicio: lisEjercicios){	
%>
			<option value="<%=ejercicio.getIdEjercicio() %>" <%if(idEjercicio.equals(ejercicio.getIdEjercicio()))out.print("selected"); %>><%=ejercicio.getIdEjercicio() %></option>
<%
	}
%>
		</select> &nbsp; &nbsp;
		<select name="InformeId" id="InformeId" style="width:320px;" onchange="document.forma.submit()" class="form-select">
<%
	for(BecInforme informe: lisInformes){
%>
			<option value="<%=informe.getInformeId()%>" <%if(informeId.equals(informe.getInformeId()))out.print("selected"); %>><%=informe.getInformeNombre() %></option>
<%
	}
%>
		</select> &nbsp; &nbsp;
		<select name="DeptoId" id="DeptoId" class="chosen"  style="width:600px;" onchange="document.forma.submit()" class="form-select">
<%	for(ContCcosto ccosto : lisDeptos){%>
			<option value="<%=ccosto.getIdCcosto() %>" <%if(deptoId.equals(ccosto.getIdCcosto())){out.print("selected");} %>><%=ccosto.getIdCcosto() %> | <%=ccosto.getNombre() %></option>
<%	} %>
		</select>	
	</div>
	</form>
	<table class="table table-condensed no-hover">
	<tr>
		<td style="background:#FBFBFB;"></td>
		<td colspan="9" style="background:white;">
			<table id="table" class="table table-sm table-bordered">
			<thead class="table-info">
				<tr>
					<th>#</th>
					<th>ID</th>
					<th><spring:message code="aca.Alumno"/></th>
					<th>Puesto</th>
					<th><spring:message code="aca.FechaInicio"/></th>
					<th>Fecha Fin</th>
					<th title="Horas del contrato">H.Cont.</th>					
<%				
				String mes = "";
				for (int j=0;j<lisInfAnt.size();j++){
					BecInforme becInf = (BecInforme)lisInfAnt.get(j);
					String yearInforme = becInf.getIdEjercicio().substring(6,8);
					
					if ("123456789101112".contains(becInf.getOrden()))
						mes = aca.util.Fecha.getMesNombreCorto( Integer.parseInt(becInf.getOrden()));					
					else
						mes = "-"; 				
					out.print("<th>"+mes+yearInforme+"</th>");
			
				}
%>										
					<th title="Horas acumuladas"><spring:message code="aca.Total"/></th>					
					<th title="Horas faltantes">H.Fal.</th>
					<th>Nivel</th>
					<th class="text-center"><spring:message code="aca.Estado"/></th>
				</tr>
			</thead>
<%
	int cont = 0;	
	for(BecPuestoAlumno puesto: lisPuestos){
		cont++;
		
		String alumnoNombre = "-";
		if (mapaAlumnos.containsKey(puesto.getCodigoPersonal())){
			alumnoNombre = mapaAlumnos.get(puesto.getCodigoPersonal());
		}
		String alumno = "";			
		if(puesto.getCodigoPersonal() != null){
			alumno = puesto.getCodigoPersonal() + " | " + alumnoNombre;
		}
		
		String edo = "";
		if( puesto.getEstado().equals("I") ){
			edo = "Inactivo";
		}else if( puesto.getEstado().equals("P") ){
			edo = "Precontratado";
		}else if( puesto.getEstado().equals("C") ){
			edo = "Contratado";
		}
		
		String categoria = "";
		if(mapaCategorias.containsKey(puesto.getCategoriaId())){
			categoria = mapaCategorias.get(puesto.getCategoriaId());
		}
		
		
		boolean mostrarPrepa = false, mostrarUniversidad = false;
		if (tipoInforme.equals("P") &&  mapaPrepa.containsKey(puesto.getCodigoPersonal())) mostrarPrepa = true;
		if (tipoInforme.equals("U") && !mapaPrepa.containsKey(puesto.getCodigoPersonal())) mostrarUniversidad = true;
		
		String horasAcumu = "0";
		String horastotales = "0";	
		
		if (mapaHorasAco.containsKey(puesto.getCodigoPersonal()+puesto.getPuestoId())) 
			horasAcumu = mapaHorasAco.get(puesto.getCodigoPersonal()+puesto.getPuestoId());
		
		if (mapaHorasTotal.containsKey(puesto.getCodigoPersonal()+"1"+puesto.getPuestoId())){
			horastotales = mapaHorasTotal.get(puesto.getCodigoPersonal()+"1"+puesto.getPuestoId());	
		}else if(mapaHorasTotal.containsKey(puesto.getCodigoPersonal()+"3"+puesto.getPuestoId())){
			horastotales = mapaHorasTotal.get(puesto.getCodigoPersonal()+"3"+puesto.getPuestoId());
		}else{
			horastotales = "0";
		}
		boolean inscrito = false;
		if (mapaInscritos.containsKey(puesto.getCodigoPersonal())) inscrito = true;
		
		String nivel = "";
		if (inscrito){
			if ( mapaPrepa.containsKey(puesto.getCodigoPersonal()) ) 
				nivel = "Bach.";
			else
				nivel = "Univ.";
		}else{
			nivel = "Inactivo";
		}	
%>
				<tr>
					<td><%=cont%></td>
					<td><%=puesto.getPuestoId() %></td>
					<td><%=alumno %></td>
					<td><%=categoria %></td>
					<td><%=puesto.getFechaIni()==null?"":puesto.getFechaIni() %></td>
					<td><%=puesto.getFechaFin()==null?"":puesto.getFechaFin() %></td>
					<td align="center"><%=horastotales %></td>
<%
				String horas = "";
				for (int j=0;j<lisInfAnt.size();j++){
					BecInforme becInf = (BecInforme)lisInfAnt.get(j);
					
					if (mapaHorasAlumno.containsKey(puesto.getCodigoPersonal()+puesto.getPuestoId()+becInf.getInformeId()))
						horas = mapaHorasAlumno.get( puesto.getCodigoPersonal()+puesto.getPuestoId()+becInf.getInformeId());
					else
						horas = "-";
					out.print("<td>"+horas+"</td>");
			
				}
%>
					<td align="center"><%=horasAcumu%></td>					
					<td align="center"><%=(Integer.parseInt(horastotales) - Integer.parseInt(horasAcumu)) %></td>
					<td align="center"><%=nivel%></td>
					<td class="text-center">				
<%	
			// Si es alumno de prepa o universidad, está inscrito y contratado muestra la opcion de capturar el informe	
			if((mostrarPrepa||mostrarUniversidad) && inscrito && puesto.getEstado().equals("C")){
				
				boolean autorizado = false;
				
				if (mapaInformes.containsKey(informeId+puesto.getCodigoPersonal())){
					BecInformeAlumno becInformeAlumno = mapaInformes.get(informeId+puesto.getCodigoPersonal());
					if(becInformeAlumno.getEstado().equals("2")){
						autorizado = true;
					}
				}				
								
				if(autorizado == false || autorizado == true){
					
					// Solo hay acceso para todos los usuarios cuando el tipoAcceso = "U" en otro caso solo los administradores pueden entrar  
					if (admin || becInforme.getEstado().equals("U")){
						if(!categoria.equals("Zambrano") && !categoria.equals("Regional")){	
%>					
					<a href="editar?informeId=<%=informeId%>&puestoId=<%=puesto.getPuestoId()%>&codigoPersonal=<%=puesto.getCodigoPersonal()%>&horasContrato=<%=horastotales %>&horasAcumuladas=<%=horasAcumu%>&CcostoId=<%=deptoId%>">
<%					  
							if (mapaHorasInforme.containsKey(puesto.getCodigoPersonal()) ){
								out.print("<i class='fas fa-check-square' style='color:black'></i>");
							}else{
								out.print("<i class='fas fa-edit' style='color:orange'></i>");
							}
%>
					</a>
<%
						}
					}else{
						out.print("-");
					}
				}else{
					out.print("Autorizado");
				}			
			}else{
				if ( !puesto.getEstado().equals("C") ){
					out.print("Sin Contrato");
				}else{
					out.print("-");
				}			
			}
%>					  
					</td>
				</tr>
<%	} %>				
			</table>
		</td>	
	</tr>
	</table>
<%
	//if(admin && !informeId.equals("0")){
	if( admin ){
%>	
	<div class="alert alert-info">
		<a href="javascript:autorizar('<%=idEjercicio%>','<%=deptoId%>','<%=informeId%>');" class="btn btn-primary btn-large"><i class="fas fa-check"></i> Autorizar</a>
		<a href="javascript:desautorizar('<%=idEjercicio%>','<%=deptoId%>','<%=informeId%>');" class="btn btn-danger btn-large"><i class="fas fa-check"></i> Desautorizar</a>
	</div>		
	<br/><br/>
<%	} %>
</div>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript"> 
	jQuery(".chosen").chosen();
	
	function autorizar(ejercicioId,deptoId,informeId){
		document.location.href="autorizar?EjercicioId="+ejercicioId+"&DeptoId="+deptoId+"&InformeId="+informeId;
	}
		
	function desautorizar(ejercicioId,deptoId,informeId){
		document.location.href="desautorizar?EjercicioId="+ejercicioId+"&DeptoId="+deptoId+"&InformeId="+informeId;		
	}
</script>