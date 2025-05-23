<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.disciplina.spring.CondAlumno"%>
<%@page import="aca.disciplina.spring.CondReporte"%>
<%@page import="aca.disciplina.spring.CondLugar"%>
<%@page import="aca.disciplina.spring.CondJuez"%>
<%@page import="aca.vista.spring.Maestros"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.plan.spring.MapaPlan"%>

<script type="text/javascript">
	
	function Grabar(){
		if(document.unidad.matricula.value!="" && document.unidad.folio.value!=""){			
			document.unidad.Accion.value="2";
			document.unidad.submit();
		}else{
			alert("Fill out the entire form");
		}
	}
	
	function Modificar(){
		document.unidad.Accion.value="3";
		document.unidad.submit();
	}
	
	function setValue(strLink){
		strLink=strLink;
		window.open( strLink, "verplan", "scrollbars=yes, width=550, height=350, top=250, left=300")
	}
</script>

<%	
	String codigoEmpleado	= (String)session.getAttribute("codigoEmpleado");
	String codigoAlumno 	= (String)request.getAttribute("codigo");
	String periodoId 		= (String)request.getAttribute("periodo");
	String fecha			= (String)request.getAttribute("fecha");
	String cantidad			= (String)request.getAttribute("cantidad");		
	String folio			= (String)request.getAttribute("folio");		
	String accion			= (String)request.getAttribute("accion");
	int nAccion				= Integer.parseInt(accion);
	String alumnoNombre		= (String)request.getAttribute("alumnoNombre");
	String resultado		= (String)request.getAttribute("resultado");

	String codigoEmp		= (String)request.getAttribute("codigoEmp");
	String sResultado		= "";
	String reporteId			= "X";
	String lugarId			= "X";
	String juezId			= "X";
	String nombreReporte	= "";	
	String nombreLugar		= "";	
	String nombreJuez		= "";	
	String nombreTipo		= "";	
	
	List<CondReporte> lisReporte	= (List<CondReporte>) request.getAttribute("lisReporte");
	List<CondLugar> lisLugar		= (List<CondLugar>) request.getAttribute("lisLugar");
	List<CondJuez> lisJuez			= (List<CondJuez>) request.getAttribute("lisJuez");
	List<Maestros> lisMaestros		= (List<Maestros>) request.getAttribute("lisMaestros");
	List<AlumPlan> lisPlanes		= (List<AlumPlan>) request.getAttribute("lisPlanes");
	
	HashMap<String, CondReporte> mapaReportes 	= (HashMap<String,CondReporte>) request.getAttribute("mapaReportes");
	HashMap<String,String> mapaJuez 			= (HashMap<String,String>) request.getAttribute("mapaJuez");
	HashMap<String,String> mapaLugar 			= (HashMap<String,String>) request.getAttribute("mapaLugar");
	HashMap<String, MapaPlan> mapaPlanes		= (HashMap<String,MapaPlan>) request.getAttribute("mapaPlanes");
	
	CondAlumno alumno 		= (CondAlumno)request.getAttribute("alumno");
%>
<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<div class="container-fluid">
	<h2>Misconduct Record<small class="text-muted fs-5">( <%=codigoAlumno%> - <%=alumnoNombre%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="unidad?codigoPersonal=<%=codigoAlumno%>">Return</a>&nbsp;
		<%=!resultado.equals("-")?resultado:""%>
	</div>
	<form name="unidad" method="post" action="grabar?Periodo=<%=periodoId%>&codigoPersonal=<%=codigoAlumno%>">
	 	<input name="Accion" type="hidden" />
	 	<input name="matricula" type="hidden" class="form-control" id="matricula" value="<%=codigoAlumno%>" size="8" maxlength="7">
	 	<input name="folio" type="hidden" class="form-control" id="folio" value="<%=alumno.getFolio()%>" size="3" maxlength="3">
		<div class="row">
			<div class="col-3">
				<label for="reporte"><b>Report:</b></label>			
				<select  id="reporte" name="reporte" class="form-select chosen">
<%			
				for (CondReporte reporte : lisReporte){
					if(reporte.getTipo().equals("C")){
						nombreTipo = "Praise";
					}else if(reporte.getTipo().equals("D")){
						nombreTipo = "Misconduct";
					}else {
						nombreTipo = "Neutral";
					}
					
					if(!reporte.getIdReporte().equals(alumno.getIdReporte())){
%>
					<option value="<%=reporte.getIdReporte()%>" selected="selected"><%=nombreTipo+" - "+reporte.getNombre()%></option>
<%			
					}
				}
				if(nAccion != 1 && nAccion != 4){ 
					if(mapaReportes.containsKey(alumno.getIdReporte())){
						if(mapaReportes.get(alumno.getIdReporte()).getTipo().equals("C")){
							nombreTipo = "Praise";
						}else if(mapaReportes.get(alumno.getIdReporte()).getTipo().equals("D")){
							nombreTipo = "Misconduct";
						}else {
							nombreTipo = "Neutral";
						}
						nombreReporte = nombreTipo+" - "+mapaReportes.get(alumno.getIdReporte()).getNombre();
					}
%>
					<option value="<%=alumno.getIdReporte()%>" selected="selected"><%=nombreReporte%></option>
<%			
				}
%>
	    		</select>
				<br><br>
				<label for="lugar"><b>Location:</b></label>			
				<select id="lugar" name="lugar" class="chosen form-select">
<%			
				for(CondLugar lugar : lisLugar){
					if(!lugar.getIdLugar().equals(alumno.getIdLugar())){
						out.print(" <option value='"+lugar.getIdLugar()+"'");			
						out.print("Selected>"+ lugar.getNombre()+"</option>");
					}
				}
				if(nAccion != 1 && nAccion != 4){ 
					if(mapaLugar.containsKey(alumno.getIdLugar())){
						nombreLugar = mapaLugar.get(alumno.getIdLugar());
					}
					out.print(" <option value='"+alumno.getIdLugar()+"' ");			
					out.print("Selected>"+ nombreLugar+"</option>");
				}
%>
		        </select>			
				<br><br>
				<label><b>Employee:</b></label>
				<select id="empleado" name="empleado" class="form-select chosen">
<%				for(Maestros maestro : lisMaestros){ %>
					<option value="<%=maestro.getCodigoPersonal()%>" <%=maestro.getCodigoPersonal().equals(alumno.getEmpleado())?"selected":""%>><%=maestro.getCodigoPersonal()+" - "+maestro.getApellidoPaterno()+" "+maestro.getApellidoMaterno()+" "+maestro.getNombre()%></option>
<% 				}%>					
				</select>	
				<br><br>
				<label><b>Study Plan:</b></label>
				<select id="planId" name="planId" class="form-select chosen">
<%				
				for(AlumPlan alumPlan : lisPlanes){
					String planNombre = "";
					if (mapaPlanes.containsKey(alumPlan.getPlanId())){
						planNombre = mapaPlanes.get(alumPlan.getPlanId()).getCarreraSe();
					}
%>
					<option value="<%=alumPlan.getPlanId()%>" <%=alumPlan.getPlanId().equals(alumno.getPlanId())?"selected":""%>><%=alumPlan.getEstado().equals("1")?"»":""%><%=alumPlan.getPlanId()%>-<%=planNombre%></option>
<% 				}%>					
				</select>
														
				</div>
				<div class="col-3">
					<label for="juez"><b>Judge:</b></label>			
					<select id="juez" name="juez" class="form-select chosen">
<%	    
				for(CondJuez juez : lisJuez){
					if(!juez.getIdJuez().equals(alumno.getIdJuez())){
						out.print(" <option value='"+juez.getIdJuez()+"'");			
						out.print("Selected>"+ juez.getNombre()+"</option>");
					}
				}
				if(nAccion != 1 && nAccion != 4){ 
					if(mapaJuez.containsKey(alumno.getIdJuez())){
						nombreJuez = mapaJuez.get(alumno.getIdJuez()); 	
					}
					out.print(" <option value='"+alumno.getIdJuez()+"' ");	
					out.print("Selected>"+ nombreJuez+"</option>");
				}
%>
	        	</select>
				<br><br>
				<label for="fecha"><b><spring:message code="aca.Fecha"/></b> (DD/MM/YYYY):</label>
				<input name="fecha" type="text" class="form-control" id="fecha" value="<%=alumno.getFecha()%>" size="11" maxlength="15">
				<br>
				<label for="cantidad"><b>Amount:</b></label>			
				<input name="cantidad" type="text" class="form-control" id="cantidad" value="<%=alumno.getCantidad()%>" size="2" maxlength="2">				
				<br>
				<label for="comentario"><b><spring:message code="aca.Comentario"/>:</b></label>			
				<input name="comentario" type="text" class="form-control" id="comentario" value="<%=alumno.getComentario()%>" size="30" maxlength="60" >
				<br>						
			</div>
		</div>
		<br>
		<div class="alert alert-info">
	      	<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a> 
		</div>
	  </form>
</div>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script>
	jQuery(".chosen").chosen();	
</script>