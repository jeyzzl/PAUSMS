<%@ include file="../../con_enoc.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ page import="aca.util.Fecha"%>
<%@ page import="java.util.*"%>

<jsp:useBean id="EstadisticaU" scope="page" class="aca.vista.EstadisticaUtil"/>
<jsp:useBean id="EstadoU" scope="page" class="aca.catalogo.EstadoUtil"/>

<script type="text/javascript">
	function Mostrar(){	
		document.forma.submit();
	}
</script>
<%
	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	java.text.DecimalFormat getFormato2	= new java.text.DecimalFormat("Cr ###,##0.00; Db ###,##0.00");

	String cargas				= (String)session.getAttribute("cargas") == null?aca.carga.CargaUtil.getCargasActivas(conEnoc, aca.util.Fecha.getHoy()):session.getAttribute("cargas").toString();
	String modalidades			= (String)session.getAttribute("modalidades") == null?"'1'":session.getAttribute("modalidades").toString();
	String fechaIni				= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin				= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");
	String lisModo[] 			= modalidades.replace("'", "").split(",");	session.setAttribute("fechaFin", fechaFin);
	String muestraModalidades 	= "";
	
	// Lista de alumnos inscritos en el rango de fechas
	ArrayList<aca.vista.Estadistica> lisInscritos	= EstadisticaU.listInscritosCargasModalidadesFechas(conEnoc, cargas, modalidades, fechaIni, fechaFin, " ORDER BY CODIGO_PERSONAL, F_INSCRIPCION");
	
	int con = 1;
	for(String mod:lisModo){
		String nombreModalidad = aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc, mod);
		muestraModalidades += nombreModalidad;	
		if(con < lisModo.length){
			muestraModalidades += ", ";
		}
		con++;
	}
	
	// Map de países
	HashMap <String, aca.catalogo.CatPais> mapaPais 	= aca.catalogo.PaisUtil.getMapAll(conEnoc, "");
	
	// Map de países
	HashMap <String, aca.catalogo.CatEstado> mapaEstado = EstadoU.getMapAll(conEnoc, "");
	
%>
<head>
	<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
	
	<style>
		.titulo td{
			border:1px solid gray;
		}
		
		.table td.alert-info {
			background-color: #d9edf7 !important;
			border-color: #bce8f1;
			color: #3a87ad;
		}
	</style>
</head>
<body>
	<div class="container-fluid">
		<h3>Validar datos personales con CURP<small class="text-muted fs-5">( Cargas : <%=cargas.replace("'","") %> | Modalidades : <%=muestraModalidades %> )</small></h3>
		<form id="forma" name="forma" action="curpIncorrecta?Accion=1" method="post">
			<div class="alert alert-info">
				<a href="menu" class="btn btn-primary">Menú</a>
				Fecha Inicio: <input id="FechaIni" name="FechaIni" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
				Fecha Final: <input id="FechaFin" name="FechaFin" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
				<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>
			</div>	
		</form>		
		<table class="table table-sm table-bordered">
			<tr class="table-info">
				<th>#</th>
				<th>Matricula</th>
				<th>Nombre</th>
				<th>Paterno</th>
				<th>Materno</th>
				<th>Fecha</th>
				<th>Genero</th>
				<th>Pais</th>
				<th>Estado</th>
				<th>Carrera</th>
				<th>Curp</th>
				<th>Patrón?</th>
				<th>Nombre?</th>
				<th>Fecha?</th>
				<th>Genero?</th>
				<th>Estado?</th>
				<th>Con.Int.?</th>
			</tr>
<%
		boolean validaCurp = false;

		int cont = 0;
		for (aca.vista.Estadistica inscrito : lisInscritos){
			
			String genero = "-";
			if (inscrito.getSexo().equals("M")) genero = "H"; else genero = "M";			
			
			// Valida el curp del alumno
			validaCurp = aca.alumno.AlumUtil.validarCurp(inscrito.getCurp()==null?"-":inscrito.getCurp());
			
			String errorCurp = aca.alumno.AlumUtil.validaDatosDeCurp(inscrito.getCurp()==null?"-":inscrito.getCurp(), inscrito.getNombre(), inscrito.getApellidoPaterno(), inscrito.getApellidoMaterno(), inscrito.getFNacimiento(), genero, inscrito.getPaisId(), inscrito.getEstadoId());
			
			if(!validaCurp || !errorCurp.equals("11111")){
				cont++;
				String nomeModalidad 	= aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc, inscrito.getModalidadId());
				String nomCarrera		= aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, inscrito.getCarreraId());
				
				String nomPais			= "-";
				if (mapaPais.containsKey(inscrito.getPaisId())){
					nomPais = mapaPais.get(inscrito.getPaisId()).getNombrePais();
				}
				
				String nomEstado			= "-";
				if (mapaEstado.containsKey(inscrito.getPaisId()+inscrito.getEstadoId())){
					aca.catalogo.CatEstado catEstado = mapaEstado.get(inscrito.getPaisId()+inscrito.getEstadoId());
					nomEstado = catEstado.getNombreEstado()+"("+catEstado.getCorto()+")";
				}
%>		
				<tr>
					<td><%=cont%></td>
					<td><%=inscrito.getCodigoPersonal()%>-<%=errorCurp%></td>
					<td><%=inscrito.getNombre()%></td>
					<td><%=inscrito.getApellidoPaterno()%></td>					
					<td><%=inscrito.getApellidoMaterno()%></td>
					<td><%=inscrito.getFNacimiento()%></td>
					<td><%=genero%></td>
					<td><%=nomPais%></td>
					<td><%=nomEstado%></td>
					<td><%=nomCarrera%></td>
					<td><%=inscrito.getCurp()%></td>					
					<td <%=validaCurp==false?"style='color:red;font-weight: bold;'":""%>><%=validaCurp?"CORRECTO":"ERROR"%></td>
					<td <%=errorCurp.charAt(0)=='0'?"style='color:red;font-weight: bold;'":""%>><%=errorCurp.charAt(0)=='1'?"CORRECTO":"ERROR"%></td>
					<td <%=errorCurp.charAt(1)=='0'?"style='color:red;font-weight: bold;'":""%>><%=errorCurp.charAt(1)=='1'?"CORRECTO":"ERROR"%></td>
					<td <%=errorCurp.charAt(2)=='0'?"style='color:red;font-weight: bold;'":""%>><%=errorCurp.charAt(2)=='1'?"CORRECTO":"ERROR"%></td>
					<td <%=errorCurp.charAt(3)=='0'?"style='color:red;font-weight: bold;'":""%>><%=errorCurp.charAt(3)=='1'?"CORRECTO":"ERROR"%></td>
					<td <%=errorCurp.charAt(4)=='0'?"style='color:red;font-weight: bold;'":""%>><%=errorCurp.charAt(4)=='1'?"CORRECTO":"ERROR"%></td>
				</tr>
<%
			}
		}
%>
		</table>
	</div>		
</body>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>
<%@ include file="../../cierra_enoc.jsp"%>