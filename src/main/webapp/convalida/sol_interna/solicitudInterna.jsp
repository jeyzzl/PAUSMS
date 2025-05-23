<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import= "aca.plan.spring.MapaPlan"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>
<%@ page import= "aca.vista.spring.AlumnoCurso"%>
<%@ page import= "aca.conva.*"%>
<%@ page import= "aca.util.*"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function materiaOrigen(nota){
		document.formConNota.MatOrigen.value=nota;
		document.formConNota.submit();
	}
	
	function materiaDestino(nota){
		document.formSinNota.MatDestino.value=nota;
		document.formSinNota.submit();
	}
</script>

<% 
	String convalidacionId		= request.getParameter("ConvalidacionId")==null?"0":request.getParameter("ConvalidacionId");
	String convalidaNota   		= request.getParameter("MatOrigen")==null?"0":request.getParameter("MatOrigen");
	String convalidaSinNota   	= request.getParameter("MatDestino")==null?"0":request.getParameter("MatDestino");
	
	String codigoPersonal		= (String)session.getAttribute("codigoPersonal");
	String codigoAlumno			= (String)session.getAttribute("codigoAlumno");
	String nombreAlumno  		= (String)request.getAttribute("nombreAlumno");
	String planActual			= (String)request.getAttribute("planActual");	 
	String carreraId			= (String)request.getAttribute("carreraId");	
	String nombreCarrera		= (String)request.getAttribute("nombreCarrera");	
	String planElegido   		= (String)request.getAttribute("planElegido");	
	
	 
	List<String> lisPlanes 				= (List<String>)request.getAttribute("lisPlanes");	 
	List<AlumnoCurso> lisAcreditadas 	= (List<AlumnoCurso>)request.getAttribute("lisAcreditadas");
	List<MapaCurso> lisDisponibles 		= (List<MapaCurso>)request.getAttribute("lisDisponibles");
	
	HashMap<String, ConvMateria> mapPorSolicitud	= (HashMap<String, ConvMateria>)request.getAttribute("mapPorSolicitud");
	HashMap<String, ConvMateria> mapPorOrigen		= (HashMap<String, ConvMateria>)request.getAttribute("mapPorOrigen");	
	HashMap<String, MapaPlan> mapPlanes				= (HashMap<String, MapaPlan>)request.getAttribute("mapPlanes");
	
	AlumnoCurso cursoOrigen 	= (AlumnoCurso)request.getAttribute("cursoOrigen");
	MapaCurso cursoDestino	 	= (MapaCurso)request.getAttribute("cursoDestino");
	
	String carreraTemp = "0";
	if (lisAcreditadas.size() > 0) carreraTemp = lisAcreditadas.get(0).getCarreraId();
	
	String nivelOrigen 			= (String)request.getAttribute("nivelOrigen");
	String nivelDestino 		= (String)request.getAttribute("nivelDestino");
	
	String accion  = request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String mensaje = request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");	
%>
<div class="container-fluid">
	<h2>Solicitud interna<small class="text-muted fs-6">&nbsp;( <%=codigoAlumno%> - <%=nombreAlumno%> )</small></h2>
	<form name="formPlan" action="solicitudInterna"> 
		<input type="hidden" name="ConvalidacionId" value="<%=convalidacionId%>">
		<input type="hidden" name="CambioPlan" value="1">
		<input type="hidden" name="MatOrigen" value="<%=convalidaNota%>">
		<input type="hidden" name="MatDestino" value="<%=convalidaSinNota%>">
		<div class="alert alert-info d-flex align-items-center">
			<a class="btn btn-primary" href="interna"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;&nbsp; 
			Plan 
			<select name="PlanId" style="width:550px" onchange="formPlan.submit()" class="form-select">
<%
			for(String plan : lisPlanes){
				String nombrePlan = "";
				if (mapPlanes.containsKey(plan)){
					nombrePlan = mapPlanes.get(plan).getNombrePlan();
				}
%> 
				<option value="<%=plan%>" <%=plan.equals(planElegido)?"selected":""%>>[<%=plan%>] <%=nombrePlan%></option>
<% 
			}
%> 
			</select>&nbsp;&nbsp;&nbsp; 
		</div>
	 </form>
<% 
	if(!mensaje.equals("0")){
		out.print("<div class='alert alert-success'>"+mensaje+"</div>");
	}	
%> 
	 
	<div class="row">
		<div class="col-4">
			<fieldset >
				<h2>Materias aprobadas <small class="text-muted fs-4">( Plan : <%=planElegido%> )</small></h2>
				<form name="formConNota" action="solicitudInterna"> 
					<input type="hidden" name="ConvalidacionId" value="<%=convalidacionId%>">
					<input type="hidden" name="PlanId" value="<%=planElegido%>">
					<input type="hidden" name="MatOrigen">
					<input type="hidden" name="MatDestino" value="<%=convalidaSinNota%>">
					<table class="table">
						<tr>
							<th>Ciclo</th>
							<th>Nombre</th>							
							<th>Creditos</th>
							<th>Nota</th>
							<th>Fecha</th>
							<th></th>
						</tr>
<%
				for(AlumnoCurso curso : lisAcreditadas){					
					String nota = "";
					String fecha = "";
					if (curso.getTitulo().equals("S")){
						nota = curso.getNota();
						fecha = curso.getFTitulo();
					}else if(!curso.getNotaExtra().equals("0")){					
						nota = curso.getNotaExtra();
						fecha = curso.getFExtra();
					}else {						
						nota = curso.getNota();
						fecha = curso.getFEvaluacion();
					}					
					
					if(!planElegido.equals(planActual)){
						
						if(!mapPorOrigen.containsKey(curso.getCursoId()) && (nivelOrigen.equals(nivelDestino) || Integer.parseInt(nivelOrigen) > 2)){						
%> 
						<tr>
							<td><%=curso.getCiclo()%></td>
							<td><%=curso.getNombreCurso()%></td> 							
							<td><%=curso.getCreditos()%></td>
							<td><%=nota%></td>
							<td><%=fecha%></td>
							<td>							 
								<a class="btn btn-success" title="Elegir" href="javaScript:materiaOrigen('<%=curso.getCursoId()%>')"><span><i class="fas fa-check"></i></span></a>

							</td>
						</tr>
<%						
						}
					}
				}
%> 
					</table>
				</form>
			</fieldset>
		</div>
		<div class="col-4">
			<fieldset >
				<h2>Materias <small class="text-muted fs-4">( Plan actual : <%=planActual%>)</small><br></h2>
				<form name="formSinNota" action="solicitudInterna"> 
					<input type="hidden" name="ConvalidacionId" value="<%=convalidacionId%>">
					<input type="hidden" name="PlanId" value="<%=planElegido%>">
					<input type="hidden" name="MatOrigen" value="<%=convalidaNota%>">
					<input type="hidden" name="MatDestino">
					<table class="table">
						<tr>
							<th>Ciclo</th>
							<th>Nombre</th>							
							<th>Creditos</th>
							<th></th>
						</tr>
<%
					for(MapaCurso curso : lisDisponibles){	
						if(!mapPorSolicitud.containsKey(curso.getCursoId())){
%>
						<tr>
							<td><%=curso.getCiclo()%></td>
							<td><%=curso.getNombreCurso()%></td>							
							<td><%=curso.getCreditos()%></td>
							<td><a class="boton btn btn-success" title="Elegir" href="javaScript:materiaDestino('<%=curso.getCursoId()%>')"><span><i class="fas fa-check"></i></span></a></td>

						</tr>
<%	 
						} 
					}
%> 
					</table>
				</form>
			</fieldset>
		</div>
		<div class="col-4">
	        <fieldset>
	        	<h1>Convalidar</h1>
	        	<form name="formGuardar" action="grabarNota" method="post">
	        		<input type="hidden" name="ConvalidacionId" value="<%=convalidacionId%>">
	        		<input type="hidden" name="PlanId" value="<%=planElegido%>">
	        		<input type="hidden" name="MatOrigen" value="<%=convalidaNota%>">
					<input type="hidden" name="MatDestino" value="<%=convalidaSinNota%>">
	        		<table class="table">
<% 
					if(!convalidaNota.equals("0") || !convalidaSinNota.equals("0")){
%>		
						<tr>
							<th>Nombre</th>
							<th>Ciclo</th>
							<th>Creditos</th>
							<th>Nota</th>
							<th></th>
						</tr>
<% 
					
					String nota = "";
					
					if(!cursoOrigen.getNotaExtra().equals("0")){
						nota = cursoOrigen.getNotaExtra();
					}else {
						nota = cursoOrigen.getNota();
					}		
%>
						<tr>
							<td><%=cursoOrigen.getNombreCurso()%></td>
							<td><%=cursoOrigen.getCiclo()%></td>
							<td><%=cursoOrigen.getCreditos()%></td>
							<td><%=nota%></td>
						</tr>
						
						<tr>
							<td><%=cursoDestino.getNombreCurso()%></td>
							<td><%=cursoDestino.getCiclo()%></td>
							<td><%=cursoDestino.getCreditos()=="0"?"":cursoDestino.getCreditos()%></td>
							<td><%=cursoDestino.getCreditos()=="0"?"":"( - )"%></td>
						</tr>
					</table>
<% 
					}
					if(!convalidaNota.equals("0") && !convalidaSinNota.equals("0")){
%> 
					<input class="btn-primary" type="submit" value="Guardar">
<% 
					}
%> 
				</form>
			</fieldset>
		</div>
	</div>
</div>