<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.tit.spring.TitAlumno"%>
<%@ page import= "aca.plan.spring.MapaPlan"%>
<%@ page import= "aca.tit.spring.TitTramite"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<head></head>
<%	 
	String tramite 			= request.getParameter("Tramite")==null?"0":request.getParameter("Tramite");
	String institucion 		= (String) session.getAttribute("valInstitucion");
	String estado 			= (String) session.getAttribute("valEstado");
	int totFirmas			= (int)request.getAttribute("totFirmas");

	TitTramite titTramite			= (TitTramite)request.getAttribute("titTramite");
	
	List<TitAlumno> lisAsignados			= (List<TitAlumno>)request.getAttribute("lisAsignados");
	List<TitAlumno> lisDisponibles			= (List<TitAlumno>)request.getAttribute("lisDisponibles");
	HashMap<String,String> mapaAlumnos		= (HashMap<String,String>)request.getAttribute("mapaAlumnos");
	HashMap<String,MapaPlan> mapaPlanes		= (HashMap<String,MapaPlan>)request.getAttribute("mapaPlanes");	
%>
<body>
<div class="container-fluid">
	<h2>Agregar títulos al tramite <small class="text-muted fs-4">(<%=" Tramite: "+titTramite.getTramiteId()+" - "+titTramite.getDescripcion()%>)</small></h2>
	<form name="frmTitulo" action="titulos" method="post">
	<input name="Tramite" type="hidden" value="<%=tramite%>">
	<div class="alert alert-info">
		<a class="btn btn-primary" href="tramite?Institucion=<%=institucion%>&Estado=<%=estado%>"><i class="fas fa-arrow-left"></i></a>&nbsp;
	</div>
	</form>
	<div class="row" style="margin: 20px 20px 0 20px;">
		<div class="col-md-6">			
			<h4>Disponibles <input type="text" class="search-query" placeholder="Buscar" id="buscarNuevos" style="width:200px"></h4>
			<table id="tableNuevos" class="table table-sm table-bordered">   
			<thead class="table-info">
			<tr>
				<th width="5%">Opción</th>
				<th width="3%">#</th>
				<th width="7%">Código</th>
				<th width="30%">Alumno</th>
				<th width="10%">Plan</th>
				<th width="10%" class="text-center">Firmas</th>
				<th width="10%">Fecha</th>
				<th width="5%">Edo.</th>	
				<th width="20%">Resp.</th>
			</tr>
			</thead>
			<tbody>
<%
	int row = 0;
	for (TitAlumno titulo : lisDisponibles){
		row++;
			
		String alumnoNombre = "-";
		if (mapaAlumnos.containsKey(titulo.getCodigoPersonal())){
			alumnoNombre = mapaAlumnos.get(titulo.getCodigoPersonal());			
		}			
		
		String planNombre = "-";
		if (mapaPlanes.containsKey(titulo.getPlanId())){
			planNombre = mapaPlanes.get(titulo.getPlanId()).getCarreraSe();			
		}
		
		String respuesta = titulo.getRespuesta();
		if (titulo.getRespuesta().length()>100){
			respuesta = "XML";
		}
		int numFirmas = totFirmas; 
		if (titulo.getXml().contains("SELLORAQUEL")) numFirmas--;
		if (titulo.getXml().contains("SELLOISMAEL")) numFirmas--;
		if (titulo.getXml().contains("SELLOJOSE")) numFirmas--;
		if (titulo.getXml().contains("SELLOJAIME")) numFirmas--;
		if (titulo.getXml().contains("SELLOANA")) numFirmas--;
		if (titulo.getXml().contains("SELLOERY")) numFirmas--;
%>
			<tr>
				<td class="text-center">
			<%if (numFirmas==0){ %>
				<a class="btn btn-success rounded-pill" href="agregarTitulo?Tramite=<%=tramite%>&Folio=<%=titulo.getFolio()%>"><i class="fas fa-plus" ></i></a>
			<%	}else{ %>
				<i class="fas fa-exclamation-triangle" style="color:orange" title="¡Genera nuevamente el XML!"></i>&nbsp;
				<a href=javascript:CrearXml('<%=titTramite.getTramiteId()%>','<%=titulo.getFolio()%>');><i class="far fa-file"></i></a>
			<%	} %>
				</td>
				<td><%=row%></td>
				<td><%=titulo.getCodigoPersonal()%></td>
				<td><%=alumnoNombre%></td>
				<td><span title="<%=planNombre%>"><%=titulo.getPlanId()%></td>
				<td class="text-center"><%=numFirmas==0?"<span class='badge bg-warning rounded-pill'>"+numFirmas+"</span>":"<span class='badge bg-dark rounded-pill'>"+numFirmas+"</span>"%></td>
				<td><%=titulo.getFecha()%></td>
				<td><%=titulo.getEstado()%></td>
				<td><%=respuesta%></td>
			</tr>
<%	} %>	
			</tbody>
			</table>	
		</div>
		<div class="col-md-6">
			<h4>Asignados <input type="text" class="search-query" placeholder="Buscar" id="buscarAsignados" style="width:200px"></h4>
			<table id="tableAsignados" class="table table-sm table-bordered">  
			<thead class="table-info">
			<tr>
				<th width="5%">Opción</th>
				<th width="3%">#</th>
				<th width="7%">Codigo</th>
				<th width="30%">Alumno</th>
				<th width="10%">Plan</th>
				<th width="10%" class="text-center">Firmas</th>
				<th width="10%">Fecha</th>
				<th width="5%">Edo.</th>	
				<th width="20%">Resp.</th>
			</tr>
			</thead>
			<tbody>
<%
	row = 0;
	for (TitAlumno titulo : lisAsignados){
		row++;
		
		String alumnoNombre = "-";
		if (mapaAlumnos.containsKey(titulo.getCodigoPersonal())){
			alumnoNombre = mapaAlumnos.get(titulo.getCodigoPersonal());	
		}		
		
		String planNombre = "-";
		if (mapaPlanes.containsKey(titulo.getPlanId())){
			planNombre = mapaPlanes.get(titulo.getPlanId()).getCarreraSe();
		}
		
		if (institucion.equals("0")) {
			institucion = titulo.getInstitucion();	
		}
		
		String respuesta = titulo.getRespuesta();
		if (titulo.getRespuesta().length()>100){
			respuesta = "XML";
		}
		
		int numFirmas = totFirmas; 
		if (titulo.getXml().contains("SELLORAQUEL")) numFirmas--;
		if (titulo.getXml().contains("SELLOISMAEL")) numFirmas--;
		if (titulo.getXml().contains("SELLOJAIME")) numFirmas--;
		if (titulo.getXml().contains("SELLOANA")) numFirmas--;
		if (titulo.getXml().contains("SELLOERY")) numFirmas--;
%>
			<tr>
				<td class="text-center">
					<a class="btn btn-danger rounded-pill" href="quitarTitulo?Tramite=<%=tramite%>&Folio=<%=titulo.getFolio()%>"><i class="fas fa-trash" ></i></a>
				</td>
				<td><%=row%></td>
				<td><%=titulo.getCodigoPersonal()%></td>
				<td><%=alumnoNombre%></td>
				<td><span title="<%=planNombre%>"><%=titulo.getPlanId()%></span></td>
				<td class="text-center"><%=numFirmas==0?"<span class='badge bg-warning rounded-pill'>"+numFirmas+"</span>":"<span class='badge bg-dark rounded-pill'>"+numFirmas+"</span>"%></td>
				<td><%=titulo.getFecha()%></td>
				<td><%=titulo.getEstado()%></td>
				<td><%=respuesta%></td>
			</tr>
<%	} %>	
			</tbody>
			</table>
		</div>
	</div>
</div>
	<script type="text/javascript">
		function CrearXml(tramite, folio) {
			if (confirm("Esta operación reinicia el contenido del XML, ¿Deseas continuar?")){
				document.location.href = "crearXml?Tramite="+tramite+"&Folio="+folio+"&Origen=titulos";
			}	
		}
	</script>
	<script src="../../js/jquery-1.9.1.min.js"></script>
	<script src="../../js/search.js"></script>
	<script type="text/javascript">
		$('#buscarNuevos').focus().search({table:$("#tableNuevos")});
		$('#buscarAsignados').focus().search({table:$("#tableAsignados")});
		
		
	</script>
</body>
</html>