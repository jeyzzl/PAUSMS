
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.vista.spring.Maestros"%>
<%@page import="aca.emp.spring.EmpContrato"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	 
	String codigoPersonal 		= (String)session.getAttribute("codigoEmpleado");
	String yearActual			= aca.util.Fecha.getHoy().substring(6,10);
	String year 				= request.getParameter("Year")==null?yearActual:request.getParameter("Year");
	
	String empleadoNombre	 	= (String)request.getAttribute("empleadoNombre");
	EmpContrato empContrato 	= (EmpContrato)request.getAttribute("empContrato");
	List<Maestros> lisMaestros 	= (List<Maestros>)request.getAttribute("lisMaestros");
%>
<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
	<link rel="stylesheet" href="../../js/chosen/chosen.css" />
</head>
<body>
<div class="container-fluid">
	<h2>Contratos <small class="text-muted fs-4">( <%=codigoPersonal%>-<%=empleadoNombre%>-<%=year%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="contratos?EmpleadoId=<%=codigoPersonal%>&Year=<%=year%>">
			<i class="fas fa-arrow-left"></i> <spring:message code='aca.Regresar'/>
		</a>&nbsp; &nbsp;
	</div>
	<form name="forma" action="grabar" method="post">
		<input name="CodigoPersonal" type="hidden" value="<%=codigoPersonal%>">
		<input name="Year" type="hidden" value="<%=year%>">
	 	<div class="row row-cols-2">
			<div>
				<label class="form-label"><b>Contrato:</b></label>			
				<input class="form-control" type="text" name="ContratoId" value="<%=empContrato.getContratoId()%>" readonly="readonly"/>
				<label class="form-label" for="Fecha"><b>Fecha:</b></label>			
				<input class="form-control"type="text" name="Fecha" id="Fecha" size="12" maxlength="10" data-date-format="dd/mm/yyyy" onfocus="focusFecha(this);" value="<%=empContrato.getFecha()%>" />
				<label class="form-label"for="Costo"><b>Importe<b></label>		
				<input class="form-control"type="text" name="Costo" value="<%=empContrato.getCosto()%>" class="input input-xsm"/>		
				<label class="form-label"for="Institucion"><b>Institución<b></label>	
				<select class="form-select" name="Institucion">
					<option value="UM" <%=empContrato.getInstitucion().equals("UM")?"selected":""%>>UM</option>
					<option value="COVOPROM" <%=empContrato.getInstitucion().equals("COVOPROM")?"selected":""%>>COVOPROM</option>
				</select>	
				<label class="form-label" for="Comentario"><b>Comentario<b>(<font color="#AE2113" id="ComentarioSize">1000</font>)</label>	
				<textarea class="form-control" id="Comentario" name="Comentario" cols="60" rows="4"  onKeyUp="javascript:refrescarComentario();" onkeyDown="return revisarComentario();"><%=empContrato.getComentario()%></textarea> 
			</div>
			<div>
				<label class="form-label" for="Estado"><b>Estado<b></label>	
				<select class="form-select" name="Estado">
					<option value="A" <%=empContrato.getEstado().equals("A")?"selected":""%>>Activo</option>
					<option value="F" <%=empContrato.getEstado().equals("F")?"selected":""%>>Firma</option>
					<option value="P" <%=empContrato.getEstado().equals("P")?"selected":""%>>Pagado</option>
				</select>	
				<label class="form-label" for="FechaIni"><b>Fecha Inicial:<b></label>		
				<input class="form-control" type="text" name="FechaIni" id="FechaIni" size="12" maxlength="10" data-date-format="dd/mm/yyyy" onfocus="focusFechaIni(this);" value="<%=empContrato.getFechaIni()%>" />			
				<label class="form-label" for="FechaFin"><b>Fecha Final:<b></label>		
				<input class="form-control" type="text" name="FechaFin" id="FechaFin" size="12" maxlength="10" data-date-format="dd/mm/yyyy" onfocus="focusFechaFin(this);" value="<%=empContrato.getFechaFin()%>" />		
				<label class="form-label" for="Firma"><b>Firma:<b></label>		
				<select class="form-select" name="Firma">
					<option value="S" <%=empContrato.getFirma().equals("S")?"selected":""%>>SI</option>
					<option value="N" <%=empContrato.getFirma().equals("N")?"selected":""%>>NO</option>				
				</select>	
			</div>
		</div><br>
		<div class="alert alert-info">
			<a class="btn btn-primary" href="javascript:Grabar();"><i class="icon-ok icon-white"></i>Grabar</a>
		</div>
 	</form>	
</div>

<script src="../../js/search.js"></script>
<script type="text/javascript" src="../../js/chosen/chosen.jquery.js"></script>
<script type="text/javascript">
	jQuery(".chosen").chosen();
	jQuery('#Fecha').datepicker();
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();
	
	function Grabar(){
		document.forma.submit();
	}

	function revisarComentario(){
		var com = document.getElementById('Comentario');
		if(event.keyCode==8) return true;
		if(com.value.length==1000) return false;
		else return true;
	}
	
	function refrescarComentario(){
		var com = document.getElementById('Comentario');
		var cant = document.getElementById('ComentarioSize');
		cant.innerHTML = 1000-com.value.length;
		if(com.value.length>1000){
			com.value = com.value.substr(0, 1000);
		}
	}	
</script>
</body>