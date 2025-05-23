<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.tit.spring.TitAlumno"%>
<%@page import="aca.tit.spring.TitCarrera"%>
<%@page import="aca.tit.spring.TitProfesional"%>
<%@page import="aca.tit.spring.TitExpedicion"%>
<%@page import="aca.tit.spring.TitAntecedente"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
</head>
<%
	String opcion = request.getParameter("Opcion")==null?"0":request.getParameter("Opcion");

	List<TitAlumno> lisTitulos	= (List<TitAlumno>) request.getAttribute("lisTitulos");
	HashMap<String,TitCarrera> mapaTitCarrera			= (HashMap<String, TitCarrera>)request.getAttribute("mapaTitCarrera");
	HashMap<String,TitProfesional> mapaTitProfesional	= (HashMap<String, TitProfesional>)request.getAttribute("mapaTitProfesional");
	HashMap<String,TitExpedicion> mapaTitExpedicion		= (HashMap<String, TitExpedicion>)request.getAttribute("mapaTitExpedicion");
	HashMap<String,TitAntecedente> mapaTitAntecedente	= (HashMap<String, TitAntecedente>)request.getAttribute("mapaTitAntecedente");
	HashMap<String,String> mapaTitulados				= (HashMap<String, String>)request.getAttribute("mapaTitulados");
	HashMap<String,String> mapaTramites					= (HashMap<String, String>)request.getAttribute("mapaTramites");
	HashMap<String,String> mapaTerminados				= (HashMap<String, String>)request.getAttribute("mapaTerminados");
%>
<body>
<div class="container-fluid">
	<h2>Alumnos Registrados</h2>
	<form name="frmAlumnos" action="alumnos" method="post">
	<div class="alert alert-info d-flex align-items-center ">
		<a class="btn btn-primary rounded-pill" href="menu"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;		
		<select name="Opcion" class="form-select" onchange="document.frmAlumnos.submit()"  style="width:140px">		
			<option value="0" <%=opcion.equals("0")?"selected":""%>>Todos</option>
			<option value="1" <%=opcion.equals("1")?"selected":""%>>Registrados</option>
			<option value="2" <%=opcion.equals("2")?"selected":""%>>En trámite</option>
			<option value="3" <%=opcion.equals("3")?"selected":""%>>Terminados</option>
		</select>
		&nbsp;&nbsp;
		<input type="text" class="form-control search-query" placeholder="Buscar..." id="buscar"  style="width:120px">
	</div>
	</form>
	<table class="table table-sm table-bordered" id="table" style="font-size:10pt;">
	<thead class="table-info">
		<tr>
			<th width="3%">#</th>
			<th width="3%">Folio</th>
			<th width="5%">Tramite</th>
			<th width="7%">Matrícula</th>
			<th width="27%">Nombre</th>
			<th width="5%">Fecha</th>
			<th width="5%">Plan</th>
			<th width="10%">Institución</th>
			<th width="5%">Estado</th>
			<th width="5%">Carr.</th>
			<th width="5%">Prof.</th>
			<th width="5%">Exp.</th>
			<th width="5%">Ant.</th>
			<th width="5%">XML</th>
			<th width="10%">XML-SEP</th>
		</tr>
	</thead>
	<tbody>
<%
	int row=0;
	for (TitAlumno titAlumno : lisTitulos){
		row++;
		//if (titAlumno.getCodigoPersonal().equals("1050354")) System.out.println("Datos:"+titAlumno.getXml());
		
		String tieneCarrera = "<span class='badge bg-warning rounded-pill'>NO</span>";
		if (mapaTitCarrera.containsKey(titAlumno.getFolio())){
			tieneCarrera = "<span class='badge bg-dark rounded-pill'>SI</span>";
		}
		
		String tieneProfesional = "<span class='badge bg-warning rounded-pill'>NO</span>";
		if (mapaTitProfesional.containsKey(titAlumno.getFolio())){
			tieneProfesional = "<span class='badge bg-dark rounded-pill'>SI</span>";
		}
		
		String tieneExpedicion = "<span class='badge bg-warning rounded-pill'>NO</span>";
		if (mapaTitExpedicion.containsKey(titAlumno.getFolio())){
			tieneExpedicion = "<span class='badge bg-dark rounded-pill'>SI</span>";
		}
		
		String tieneAntecedente = "<span class='badge bg-warning rounded-pill'>NO</span>";
		if (mapaTitAntecedente.containsKey(titAlumno.getFolio())){
			tieneAntecedente = "<span class='badge bg-dark rounded-pill'>SI</span>";
		}
		
		String nombreAlumno = "-";
		if (mapaTitulados.containsKey(titAlumno.getCodigoPersonal())){
			nombreAlumno = mapaTitulados.get(titAlumno.getCodigoPersonal());
		}
		
		String xml = "XML";
		if (titAlumno.getXml().length()>15){
			xml = "<span class='badge bg-dark rounded-pill'>SI</span>";
		}else{
			xml = "<span class='badge bg-warning rounded-pill'>NO<span>";
		}
		
		String tramite 	= "<span class='badge bg-warning rounded-pill'>0</span>";
		if (mapaTramites.containsKey(titAlumno.getFolio())){
			tramite 	= "<span class='badge bg-dark rounded-pill'>"+mapaTramites.get(titAlumno.getFolio())+"</span>";
		}
	
		String estado = "-";
		if (titAlumno.getEstado().equals("A") ){
			estado = "Registrado";
		}else if (mapaTerminados.containsKey(titAlumno.getFolio())){
			estado = "Terminado";
		}else{
			estado = " En Trámite";
		}			
%>	
		<tr>			
			<td><%=row%></td>
			<td><%=titAlumno.getFolio()%></td>
			<td><%=tramite%></td>
			<td><%=titAlumno.getCodigoPersonal()%></td>
			<td><%=nombreAlumno%></td>
			<td><%=titAlumno.getFecha().substring(0, 10) %></td>
			<td><%=titAlumno.getPlanId()%></td>
			<td><%=titAlumno.getInstitucion()%></td>
			<td><%=estado%></td>
			<td><%=tieneCarrera%></td>
			<td><%=tieneProfesional%></td>
			<td><%=tieneExpedicion%></td>
			<td><%=tieneAntecedente%></td>
			<td class="center"><%=xml%></td>
			<td class="center"><%=titAlumno.getFechaRes() != null ? titAlumno.getFechaRes() : ""%></td>
		</tr>	
<%
	}
%>
	</tbody>
	</table>
</div>
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>
</body>
</html>