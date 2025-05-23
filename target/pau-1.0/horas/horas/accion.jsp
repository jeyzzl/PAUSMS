<%@page import="aca.emp.spring.EmpHoras"%>
<%@page import="aca.vista.spring.Maestros"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="aca.emp.spring.EmpTipoPago"%>
<%@page import="java.util.List"%>
 
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>

<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<body>
<%	
	String codigoPersonal 		= (String)session.getAttribute("codigoPersonal");
	String cargaId 				= (String)session.getAttribute("cargaId");
	String carreraId 			= (String)session.getAttribute("carreraId");
	String nombreCarrera		= (String)request.getAttribute("nombreCarrera");
	
	String folio 				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String accion 				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	String maestroNombre		= (String)request.getAttribute("maestroNombre");
	String maestroEstado		= (String)request.getAttribute("maestroEstado");
	EmpHoras empHoras 			= (EmpHoras)request.getAttribute("empHoras");
	
	List<Maestros> lisMaestros 	= (List<Maestros>)request.getAttribute("lisMaestros");
	List<MapaCurso> lisMaterias = (List<MapaCurso>)request.getAttribute("lisMaterias");
	List<EmpTipoPago> lisPagos 	= (List<EmpTipoPago>)request.getAttribute("lisPagos");
	
	String estadoDelPrecio		= "Disabled";
	
	// Habilitar input en caso de que el usuario sea 9800308,9800660,9800146 
	if (codigoPersonal.equals("9800308")||codigoPersonal.equals("9800146")||codigoPersonal.equals("9800660")||codigoPersonal.equals("9800889")){
		estadoDelPrecio = "";
	}
	
%>
<div class="container-fluid">
	<h2>Registro de Horas<small class="text-muted fs-5">( <%=cargaId%> - <%=nombreCarrera%>)</small></h2>
	<div class="alert alert-info">
		<a href="registro" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<form name="frmHoras" action="grabar" method="post">
	<input type="hidden" name="Accion" value="<%=0%>">
	<input type="hidden" name="Folio" value="<%=folio%>">
	<input type="hidden" name="Carrera" value="<%=carreraId%>">
	<input type="hidden" name="CargaId" value="<%=cargaId%>">
	<input type="hidden" name="Estado" value="<%=empHoras.getEstado()%>">
	
	<div class="row">
		<div class="col-5" class="form-control" >
			<label for="Maestro">Maestro:&nbsp;<%=maestroEstado.equals("I")?"<span class='badge bg-warning rounded-pill'>Inactivo</span>":""%></label><br>	
			<select name="CodigoEmpleado" id="CodigoEmpleado"class="chzn-select" style="width:400px;">
			<%	if (maestroEstado.equals("I")){ %>
				<option value="<%=empHoras.getCodigoPersonal()%>" selected>Maestro Inactivo - <%=empHoras.getCodigoPersonal()%> - <%=maestroNombre%></option>
			<%	}else{%>	
				<option value="0" <%=empHoras.getCodigoPersonal().equals("0")?"selected":""%>>Sin Maestro</option>
			<%	} %>	
		 		<%
		 			String estado = "-";
		 			for(aca.vista.spring.Maestros maestro: lisMaestros){		 				
		 				if (maestro.getEstado().equals("A")) estado = "Activo";
		 				if (maestro.getEstado().equals("I")) estado = "Inactivo";
		 				if (maestro.getEstado().equals("J")) estado = "Jubilado";
		 		%>
			 		<option value="<%=maestro.getCodigoPersonal()%>" <%=empHoras.getCodigoPersonal().equals(maestro.getCodigoPersonal())?" selected":""%>>
			 		<%=maestro.getCodigoPersonal()%> - <%= maestro.getNombre()+" "+maestro.getApellidoPaterno()+" "+maestro.getApellidoMaterno() %> (<%=estado%>)
			 		</option>
			 	<%
		 			}
			 	%>
			</select>
			<br><br>
			<label for="Materia">Materia:</label><br>		
			<select name="CursoId" id="CursoId" class="chzn-select"  style="width:400px;">
				<option value="000000000000000">Elige una Materia</option>
		 		<%		 			
		 			for(aca.plan.spring.MapaCurso materia: lisMaterias){	 				
		 				
		 		%>
			 		<option value="<%=materia.getCursoId()%>" <%=materia.getCursoId().equals(empHoras.getCursoId())?" selected":""%>>
			 		[<%=materia.getPlanId()%>] [<%=materia.getCiclo()%>] <%= materia.getNombreCurso() %>
			 		</option>
			 	<%
		 			}
			 	%>
			</select>
			<br><br>
			<label for="Materia">Otra</label>		
			<input type="text" name="Materia" id="Materia" class="form-control" style="width:400px;" value="<%=empHoras.getMateria()%>" class="input input-xlarge"/>			
			<br>						
			<label for="Tipo">Tipo:</label>
			<select name="Tipo" class="form-select" style="width:400px;">
				<option value="V" <%= empHoras.getTipo().equals("V")?"selected":""%>>Verano</option>
				<option value="O" <%= empHoras.getTipo().equals("O")?"selected":""%>>Otoño</option>
				<option value="I" <%= empHoras.getTipo().equals("I")?"selected":""%>>Invierno</option>
				<option value="P" <%= empHoras.getTipo().equals("P")?"selected":""%>>Primavera</option>
			</select>			
			<br>
			<label for="FechaIni">Fecha Inicio:</label>			
			<input type="text" name="FechaIni" id="FechaIni" class="form-control"  style="width:400px;" size="12" maxlength="10" data-date-format="dd/mm/yyyy" onfocus="focusFecha(this);" value="<%=empHoras.getFechaIni()%>" />
			<br>			
			<label for="FechaFin">Fecha Fin:</label>			
			<input type="text" name="FechaFin" id="FechaFin" class="form-control"  style="width:400px;"  size="12" maxlength="10" data-date-format="dd/mm/yyyy" onfocus="focusFecha(this);" value="<%=empHoras.getFechaFin()%>" />			
			<br>
		</div>
		<div class="col-3">								
			<label for="BloqueId">Bloque:</label>
			<select name="BloqueId" class="form-select"  style="width:400px;" >
				<option value="1" <%= empHoras.getBloqueId().equals("1")?"selected":""%>>Bloque 1</option>
				<option value="2" <%= empHoras.getBloqueId().equals("2")?"selected":""%>>Bloque 2</option>
				<option value="3" <%= empHoras.getBloqueId().equals("3")?"selected":""%>>Bloque 3</option>
				<option value="4" <%= empHoras.getBloqueId().equals("4")?"selected":""%>>Bloque 4</option>
				<option value="5" <%= empHoras.getBloqueId().equals("5")?"selected":""%>>Bloque 5</option>
				<option value="6" <%= empHoras.getBloqueId().equals("6")?"selected":""%>>Bloque 6</option>
				<option value="7" <%= empHoras.getBloqueId().equals("7")?"selected":""%>>Bloque 7</option>
			</select>			
			<br>
			<label for="Frecuencia">Frecuencia</label>		
			<input type="text" name="Frecuencia" id="Frecuencia"  class="form-control" style="width:400px"   value="<%=empHoras.getFrecuencia()%>" class="input input-small"/>
			<br>
			<label for="Semanas">Semanas</label>		
			<input type="text" name="Semanas" id="Semanas" class="form-control"  style="width:400px;"  value="<%=empHoras.getSemanas()%>" class="input input-small"/>
			<br>
			<label for="Precio">Precio</label>
			<input type="text" name="Precio" id="Precio" class="form-control"  style="width:400px;"  value="<%=empHoras.getPrecio()%>" class="input input-small" <%=estadoDelPrecio%> />
			<br>
			<label for="TipoPago">Tipo Pago:</label>
			<select name="TipoPago" class="form-select"  style="width:400px;" >
<%
		for (EmpTipoPago pagos : lisPagos){
%>
				<option value="<%=pagos.getTipopagoId()%>" <%= pagos.getTipopagoId().equals(empHoras.getTipoPagoId())?"selected":""%>>
					<%= pagos.getTipopagoNombre()%>
				</option>
<%			
		}
%>								
			</select>
			<br>
			<label for="Contrato">Contrato</label>
			<input type="text" name="ContratoId" id="ContratoId" class="form-control"  style="width:400px;"  value="<%=empHoras.getContratoId()%>"  readonly="readonly" />
			<br>
		</div>
		<br>		
	</div>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="javascript:Guardar();"><i class="icon-ok icon-white"></i> Guardar</a>
	</div>
	</form>
			
</div>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>	
<script>	
	function Guardar() {
		if (document.frmHoras.Materia.value != "" && document.frmHoras.FechaIni.value != "" && document.frmHoras.FechaFin.value != "") {
			document.frmHoras.Accion.value = "1";
			document.frmHoras.submit();
		} else {
			alert("¡Completa todos los campos!");
		}
	}

	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();
	jQuery(".chzn-select").chosen();
</script>  	
</html>