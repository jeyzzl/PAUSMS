<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page import= "aca.alumno.spring.AlumPlan"%>
<%@ page import= "aca.plan.spring.MapaPlan"%>
<%@page import="aca.tit.spring.TitAlumno"%>
<%@page import="aca.tit.spring.TitCarrera"%>
<%@page import="aca.tit.spring.TitProfesional"%>
<%@page import="aca.tit.spring.TitExpedicion"%>
<%@page import="aca.tit.spring.TitAntecedente"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%	
	String codigoAlumno 	= (String) session.getAttribute("codigoAlumno");
	
	String folio		 	= request.getParameter("folio")==null?"0":request.getParameter("folio");
	String saccion			= request.getParameter("accion")==null?"0":request.getParameter("accion");
	String nombreAlumno		= (String)request.getAttribute("nombreAlumno");
	String year				= aca.util.Fecha.getHoy().substring(6,10);
	String nombrePlan		= "-";
	boolean errorEnfecha	= false;
	String fechaTerminacion	= "0";
	String fechaExamen		= "0";
	String institucion		= "-";
	
	List<AlumPlan> lisPlanes							= (List<AlumPlan>)request.getAttribute("lisPlanes");
	List<TitAlumno> lisTitulos							= (List<TitAlumno>)request.getAttribute("lisTitulos");
	List<TitAlumno> lisConXml							= (List<TitAlumno>)request.getAttribute("lisConXml");
	HashMap<String,MapaPlan> mapaPlanes					= (HashMap<String, MapaPlan>)request.getAttribute("mapaPlanes");	
	HashMap<String,TitCarrera> mapaTitCarrera			= (HashMap<String, TitCarrera>)request.getAttribute("mapaTitCarrera");
	HashMap<String,TitProfesional> mapaTitProfesional	= (HashMap<String, TitProfesional>)request.getAttribute("mapaTitProfesional");
	HashMap<String,TitExpedicion> mapaTitExpedicion		= (HashMap<String, TitExpedicion>)request.getAttribute("mapaTitExpedicion");
	HashMap<String,TitAntecedente> mapaTitAntecedente	= (HashMap<String, TitAntecedente>)request.getAttribute("mapaTitAntecedente");
	HashMap<String,String> mapaCarreraPlan				= (HashMap<String, String>)request.getAttribute("mapaCarreraPlan");
	HashMap<String,String> mapaTramites					= (HashMap<String, String>)request.getAttribute("mapaTramites");
	
	String carreraId 	= "";	
	String btnGrabar 	= (String)request.getAttribute("btnGrabar");
%>
<div class="container-fluid">
	<h2>Titulación<small class="text-muted fs-4"> ( <%=codigoAlumno%> - <%=nombreAlumno%> )</small></h2>
	<form name="frmTitulo" action="grabar" method="post">
	<div class="alert alert-info d-flex align-items-center">
		Plan:&nbsp;
		<select onchange="Refrescar(this.value,'<%=folio%>')" name="PlanId" id="PlanId" class="form-select" style="width:400px;">
<%		
	for(AlumPlan plan : lisPlanes){
		
		nombrePlan = "-";
		if (mapaPlanes.containsKey(plan.getPlanId())){
			nombrePlan = mapaPlanes.get(plan.getPlanId()).getNombrePlan();
		}
		
		// Carreras de Educación Primaria y Preescolar en COVOPROM
		if(mapaCarreraPlan.get(plan.getPlanId()).equals("10209") || mapaCarreraPlan.get(plan.getPlanId()).equals("10210") || mapaCarreraPlan.get(plan.getPlanId()).equals("10268")){
			institucion = "CO";
		}else{
			institucion = "UM";
		}		
%>
		<option value="<%=plan.getPlanId()%>">[<%=institucion%>-<%=plan.getPlanId()%>] - <%=nombrePlan%></option>
<%	}%>			
		</select>
		<input name="CarreraId" id="CarreraId" type="hidden" value="<%=carreraId%>">	
		&nbsp;&nbsp;Year:&nbsp;
		<input name="Year" id="Year" value="<%=year%>" class="input input-mini form-control" style="width:100px;">
		&nbsp;&nbsp;Estado:&nbsp;
		<select name="Estado" id="Estado" class="input input-small form-select" style="width:100px;">
			<option value="A">Activo</option>
			<option value="I">Inactivo</option>
		</select>
		&nbsp;
		<a href="javascript:Grabar()" class="btn btn-primary <%=btnGrabar%>">Grabar</a>
		&nbsp;&nbsp;
		<a class="btn btn-success" href="tit_requisitos.jsp" target="_blank"><b>Requisitos</b></a>
	</div>	
	</form>
	<table class="table table-sm table-bordered">  
	<thead class="table-info">
		<tr>
			<th style="text-align:center" width="3%">Opción</th>
			<th style="text-align:center" width="2%">#</th>
			<th style="text-align:center" width="3%">Trámite</th>
			<th width="4%">Folio</th>			
			<th width="10%">Folio SEP</th>			
			<th style="text-align:center" width="3%">Fecha</th>
			<th width="3%">Plan</th>
			<th style="text-align:center" width="3%">Inst.</th>
			<th style="text-align:center" width="3%">Estado</th>
			<th style="text-align:center" width="3%">Firmas</th>
			<th style="text-align:center" width="3%">Inst.</th>
			<th style="text-align:center" width="3%">Car.</th>
			<th style="text-align:center" width="3%">Prof.</th>
			<th style="text-align:center" width="3%">Expe.</th>
			<th style="text-align:center" width="3%">Ante.</th>
			<th style="text-align:center" width="3%">XML-UM</th>
			<th style="text-align:center" width="3%">Bajar</th>			
			<th style="text-align:center" width="3%">Enviar</th>
			<th style="text-align:center" width="14%">ZIP-SEP</th>
			<th style="text-align:center" width="5%">Folio físico</th>
		</tr>
	</thead>
	<tbody>
<%
	int row=0;
	for (TitAlumno titulo : lisTitulos){
		row++;

		String folioControl = titulo.getCodigoPersonal()+titulo.getPlanId();
		if (!titulo.getFolioControl().equals("0")) folioControl = titulo.getFolioControl();
		
		boolean borrarTitulo = true;			
		String colorCarrera = "btn-danger";
		if (mapaTitCarrera.containsKey(titulo.getFolio())){
			colorCarrera = "btn-primary";
			borrarTitulo = false;
		}
		
		String colorProfesional = "btn-danger";
		if (mapaTitProfesional.containsKey(titulo.getFolio())){
			colorProfesional = "btn-primary";
			borrarTitulo = false;
		}
		
		String colorExpedicion = "btn-danger";
		if (mapaTitExpedicion.containsKey(titulo.getFolio())){
			colorExpedicion = "btn-primary";
			borrarTitulo = false;
		}
		
		String colorAntecedente = "btn-danger";
		if (mapaTitAntecedente.containsKey(titulo.getFolio())){
			colorAntecedente = "btn-primary";
			borrarTitulo = false;
		}
		String xml = titulo.getXml().equals("XML")?"NO":"YES";	
		
		String estado = "-";
		if (titulo.getEstado().equals("A")) estado = "Activo";
		else if (titulo.getEstado().equals("T")) estado = "Tramite";
		else estado = "Inactivo";
		
		String tramite = "<span class='badge bg-warning'>0</span>";
		if (mapaTramites.containsKey(titulo.getFolio())){
			tramite = "<span class='badge bg-dark'>"+mapaTramites.get(titulo.getFolio())+"</span>";
		}
		
		fechaTerminacion			= "0";
		if (mapaTitCarrera.containsKey(titulo.getFolio())){
			if (mapaTitCarrera.get(titulo.getFolio()).getFechaTerminacion()!=null){
				fechaTerminacion = mapaTitCarrera.get(titulo.getFolio()).getFechaTerminacion();
			}
		}
		
		errorEnfecha = false;
		
		fechaExamen					= "0";
		if (mapaTitExpedicion.containsKey(titulo.getFolio())){
			if (mapaTitExpedicion.get(titulo.getFolio()).getFechaExamen()!=null){
				fechaExamen = mapaTitExpedicion.get(titulo.getFolio()).getFechaExamen();
			}	
		}		
				
		if(!fechaTerminacion.equals("0") && !fechaExamen.equals("0")){
			SimpleDateFormat sdf 	= new SimpleDateFormat("yyyy-MM-dd");
			Date fechaTer 			= sdf.parse(fechaTerminacion);
			Date fechaExa 			= sdf.parse(fechaExamen);
			
			if( fechaTer.getTime() > fechaExa.getTime()){
				errorEnfecha = true;
			}
		}
%>	
		<tr>
			<td style="text-align:center">
		<%	if (borrarTitulo==true){%>		
			<a href="javascript:Borrar('<%=titulo.getFolio()%>')" ><i class="fas fa-trash"></i></a>&nbsp;
		<%	}%>		
		
			</td>
			<td style="text-align:center"><%=row%></td>
			<td class="center"><%=tramite%></td>
			<td><%=titulo.getFolio() %></td>
			<td>
<% 			if(titulo.getXml().equals("XML")){%>
				<form name="frmFolioSep" action="grabarFolioSep" method="post" class="d-flex align-items-center">
					<input name="Folio" type="hidden" value="<%=titulo.getFolio()%>">
					<input class="form-control" name="FolioSep" type="text" placeholder="Folio" value="<%=folioControl%>" style="width:157px;">
					<button class="btn btn-primary rounded-pill" type="submit"><i class="far fa-save"></i></button>
				</form>
<% 			}%>
			</td>
			<td style="text-align:center"><%=titulo.getFecha()%></td>
			<td><%=titulo.getPlanId()%></td>
			<td style="text-align:center"><%=titulo.getInstitucion()%></td>
			<td style="text-align:center"><%=estado%></td>
			<td style="text-align:center">		
				<a href="firma?Institucion=<%=titulo.getInstitucion()%>&Folio=<%=titulo.getFolio()%>" class="btn btn-primary btn-sm"><i class="fas fa-edit"></i></a>				
			</td>
			<td style="text-align:center">
			<% 	if (titulo.getXml().equals("XML")){%>
				<a href="institucion?Folio=<%=titulo.getFolio()%>" class="btn btn-primary btn-sm"><i class="fas fa-edit"></i></a>
			<% 	}else{%>
				<a href="" class="btn btn-primary btn-sm"><i class="fas fa-check"></i></a>
			<% }%>	
			</td>
			<td style="text-align:center">
			<% 	if (titulo.getXml().equals("XML")){%>	
				<a href="carrera?Folio=<%=titulo.getFolio()%>" class="btn btn-sm <%=colorCarrera%>"><i class="fas fa-edit"></i></a>
			<% 	}else{%>
				<a href="carrera?Folio=<%=titulo.getFolio()%>&BtnGrabar=false" class="btn btn-sm <%=colorCarrera%>"><i class="fas fa-check"></i></a>
			<% 	}%>
			</td>
			<td style="text-align:center">
			<% 	if (titulo.getXml().equals("XML")){%>
				<a href="profesional?Folio=<%=titulo.getFolio()%>" class="btn btn-sm <%=colorProfesional%>"><i class="fas fa-edit"></i></a>
			<% 	}else{%>
				<a href="profesional?Folio=<%=titulo.getFolio()%>&BtnGrabar=false" class="btn btn-sm <%=colorProfesional%>"><i class="fas fa-check"></i></a>
			<% 	}%>			
			</td>
			<td style="text-align:center">
			<% 	if (titulo.getXml().equals("XML")){%>
				<a href="expedicion?Folio=<%=titulo.getFolio()%>" class="btn btn-sm <%=colorExpedicion%>"><i class="fas fa-edit"></i></a>
			<% 	}else{%>
				<a href="expedicion?Folio=<%=titulo.getFolio()%>&BtnGrabar=false" class="btn btn-sm <%=colorExpedicion%>"><i class="fas fa-check"></i></a>
			<% 	}%>	
			</td>
			<td style="text-align:center">
			<%	if (titulo.getXml().equals("XML")){%>
				<a href="antecedentes?Folio=<%=titulo.getFolio()%>" class="btn btn-sm <%=colorAntecedente%>"><i class="fas fa-edit"></i></a>
			<%	}else{ %>
				<a href="antecedentes?Folio=<%=titulo.getFolio()%>&BtnGrabar=false" class="btn btn-sm <%=colorAntecedente%>"><i class="fas fa-check"></i></a>
			<% 	}%>
			</td>
			<td style="text-align:center">
		<%
			boolean estaFirmado = false;
			if (!titulo.getXml().contains("SELLOERY") && !titulo.getXml().contains("SELLOJOSE") && !titulo.getXml().contains("SELLORAQUEL")&& !titulo.getXml().contains("SELLOISMAEL")&& !titulo.getXml().contains("SELLOJAIME")&& !titulo.getXml().contains("SELLOANA") && !titulo.getXml().equals("XML")) 
				estaFirmado = true;
			if(mapaTitCarrera.containsKey(titulo.getFolio()) && mapaTitProfesional.containsKey(titulo.getFolio()) && mapaTitExpedicion.containsKey(titulo.getFolio()) && mapaTitAntecedente.containsKey(titulo.getFolio()) && estaFirmado==false && titulo.getXml().equals("XML")){
				if(errorEnfecha==false){%>
				<a class="btn btn-sm btn-info" href="javascript:Xml('<%=titulo.getFolio()%>')"><b>XML</b></a>
		<% 		}else{%>
				<a class="btn btn-sm btn-info" href="javascript:XmlIncorrecto('<%=titulo.getFolio()%>')"><b>XML</b></a>
		<% 		}
			}else if (estaFirmado){			
				out.print("<span class='badge bg-dark'>Firma</span>");
			}else if (titulo.getXml().equals("XML")){
				out.print("<span class='badge bg-warning'>XML</span>");
			}
			out.print("&nbsp;");
			if (!titulo.getXml().equals("XML") && titulo.getEstado().equals("A")){%>
				<a class="btn btn-sm btn-danger" href="javascript:QuitarXml('<%=titulo.getFolio()%>')"><b>X</b></a>
		<%	}			
		%>				
			</td>
			<td style="text-align:center">
<%			if(xml.equals("YES")){ %>	
				<a href="bajarXml?Folio=<%=titulo.getFolio()%>"><i class="fas fa-download"></i></a>
<% 			}%>		
			</td>			
			<td class="center">
<%			if(estaFirmado && titulo.getEstado().equals("T")){ 
				if (institucion.equals("UM")){%>
				<a href="enviarXML?Folio=<%=titulo.getFolio()%>"><i class="fa fa-envelope-open" ></i></a>
<%				}else{%>
				<a href="enviarCovopromXML?Folio=<%=titulo.getFolio()%>"><i class="fa fa-envelope-open" ></i></a>
<%				}	
			}%>	
			</td>			
			<td class="center">
			
		<% 	if(titulo.getRespuesta().equals("XML-SEP")||titulo.getRespuesta().equals("ZIP-SEP")){%>
				<a href="../titulacion/bajarXmlSep?Folio=<%=titulo.getFolio()%>"><i class="fas fa-download"></i></a>
		<% 	}else{
				out.print(titulo.getRespuesta());
			}
		%>	
			</td>
		<%
			String colorFolioFisico = "bg-warning";
			if (!titulo.getFolioFisico().equals("X")){
				colorFolioFisico = "bg-dark";
			}
		%>			
			<td class="center">
				<span class='badge <%=colorFolioFisico%>'>
				<a href="folio?Folio=<%=titulo.getFolio()%>" style="color:white"><%=titulo.getFolioFisico()%></a>
				</span>
			</td>
		</tr>	
<%
	}
%>
		</tbody>
	</table>
	<form name="frmAlumno" action="subirAlumno" method="post">
		<div class="alert alert-info d-flex align-items-center">
			&nbsp;&nbsp;Alumnos con XML:&nbsp;
			<select name="AlumnoXml" onchange="javascript:SubirAlumno()" class="form-select" style="width:200px;">	
				<option value="0">Vacio</option>
<% 	for(TitAlumno alumno : lisConXml){%>
				<option value="<%=alumno.getCodigoPersonal()%>" <%=codigoAlumno.equals(alumno.getCodigoPersonal())?" selected":""%>>
				<%=alumno.getCodigoPersonal()%> - <%=alumno.getPlanId()%>
				</option>
<%	}%>
		 	</select> 		
	 	</div>
 	</form>
</div>
<script type="text/javascript">
	function Grabar() {
		var carrera = document.getElementById("CarreraId").value;
	    if (carrera === "10209" || carrera === "10210" || carrera === "10268"){
	    	document.getElementById("Institucion").value = "COVOPROM";
	   	}
		document.frmTitulo.submit();
	}
	
	function Borrar(folio) {
		if (confirm("¿Estás seguro de borrar este registro?")){
			document.location.href="borrar?Folio="+folio;
		}
	}
	
	function XmlIncorrecto(folio) {
		alert("El folio "+folio+" tiene la fecha de terminacion de la carrera mayor a la fecha del examen");
	}
	
	function Xml(folio) {
		if (confirm("¿Estás seguro de crear XML?")){
			document.location.href="xml?Folio="+folio;
		}
	}

	function QuitarXml(folio) {
		if (confirm("¿Estás seguro de eliminar el XML?")){
			document.location.href="quitarXml?Folio="+folio;
		}
	}

	function SubirAlumno() {		
		document.frmAlumno.submit();
	}
</script>