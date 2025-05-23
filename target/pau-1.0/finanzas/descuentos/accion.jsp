<%@page import="java.util.List" %>
<%@page import="java.text.*" %>
<%@page import="aca.alumno.spring.AlumDescuento" %>
<%@page import="aca.catalogo.spring.CatDescuento" %>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<html>
<body>

<%
	String codigoPersonal 	= (String)session.getAttribute("codigoPersonal");
	String descuentoId		= request.getParameter("DescuentoId")==null?"1":request.getParameter("DescuentoId");
	String accion 		  	= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String cargaId 		  	= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");	
	boolean existe 			= (boolean)request.getAttribute("existe");	
	String alumnoNombre 	= (String)request.getAttribute("alumnoNombre");
	
	AlumDescuento alumDescuento 	= (AlumDescuento)request.getAttribute("alumDescuento");		
	List<CatDescuento> lisDescuentos = (List<CatDescuento>)request.getAttribute("lisDescuentos");
	//System.out.println("Prueba: "+alumDescuento.getCodigoPersonal());
%>
	<div class="container-fluid">
		<h2>Agregar Alumnos con Descuento</h2>
		
		<div class="respuesta"></div>
		
		<div class="alert alert-info">
			<a href="alumnos" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
		</div>
		
		<form id="datos" name="datos" action="accion" method="post">
			<input name="Accion" type="hidden" value="<%=accion%>"/>
			<input name="codigoUsuario" type="hidden" value="<%=codigoPersonal%>"/>
			<input name="cargaId" type="hidden" value="<%=cargaId%>"/>
			
			<div class="row">
				<div class="col">				
					<label for="descuentoId">Descuento<span class="required-indicator">*</span></label>
					<select name="descuentoId" id="descuentoId" class="form-select" style="width:75%;">
						<%for(CatDescuento descuento : lisDescuentos){ %>
							<option value="<%=descuento.getDescuentoId()%>" <%if(descuentoId.equals(descuento.getDescuentoId()))out.print("selected"); %>>
								<%=descuento.getDescuentoNombre() %>
							</option>
						<%} %>
					</select>
					<br>
					<label for="matricula"><spring:message code="aca.Matricula"/><span class="required-indicator">*</span></label>
<%			if (existe){%>
					<input type="hidden" id="matricula"  name="matricula" value="<%=alumDescuento.getCodigoPersonal() %>"/>	
					<br><h5><%=alumDescuento.getCodigoPersonal()%>- <%=alumnoNombre%></h5>															
<%			}else{ %>
					<input type="text" required id="matricula"  name="matricula" class="form-control" value="<%=alumDescuento.getCodigoPersonal() %>" style="width:75%;"/>
					<img src="../../imagenes/loading.gif" alt="" style="display:none;" class="loading-bar" />
	            	<span class="result"></span>	            	
<%			} %>
					<br>					
					<label for="dMatricula">Descuento Matrícula<span class="required-indicator">*</span></label>
					<input type="text"  id="dMatricula"  required maxlength="8" name="dMatricula" class="form-control"  value="<%=alumDescuento.getMatricula()%>" style="width:75%;"/>
					<br>					
					<label for="tipoMatricula">Tipo de Descuento Mat.<span class="required-indicator">*</span></label>
					<select id="tipoMatricula" name="tipoMatricula" class="form-select" style="width:75%;">
						<option value="C" <%if(alumDescuento.getTipoMatricula().equals("C")){out.print("selected");} %>><spring:message code='aca.Cantidad'/></option> 
						<option value="P" <%if(alumDescuento.getTipoMatricula().equals("P")){out.print("selected");} %>>Porcentaje</option>
					</select>
					<br>
					<label for="dEnsenanza">Descuento Enseñanza<span class="required-indicator">*</span></label>
					<input type="text" required id="dEnsenanza" maxlength="8" name="dEnsenanza" class="form-control"  style="width:75%;"  value="<%=alumDescuento.getEnsenanza()%>" size="" />
					<br>
					<label for="tipoEnsenanza">Tipo de Descuento Ens.<span class="required-indicator">*</span></label>
					<select id="tipoEnsenanza" name="tipoEnsenanza" class="form-select" style="width:75%;">
						<option value="C" <%if(alumDescuento.getTipoEnsenanza().equals("C")){out.print("selected");} %>><spring:message code='aca.Cantidad'/></option> 
						<option value="P" <%if(alumDescuento.getTipoEnsenanza().equals("P")){out.print("selected");} %>>Porcentaje</option>
					</select>										
				</div>
				<div class="col">
					<div class="control-group">
						<label for="dInternado">Descuento Internado<span class="required-indicator">*</span></label>
						<input type="text" id="dInternado" required maxlength="8"  name="dInternado" class="form-control" style="width:75%;" value="<%=alumDescuento.getInternado()%>" size="" />
					</div><br>
					<div class="control-group">
						<label for="tipoInternado">Tipo de Descuento Int.<span class="required-indicator">*</span></label>
						<select id="tipoInternado" name="tipoInternado" class="form-select"  style="width:75%;" >
							<option value="C" <%if(alumDescuento.getTipoInternado().equals("C")){out.print("selected");} %>><spring:message code='aca.Cantidad'/></option> 
							<option value="P" <%if(alumDescuento.getTipoInternado().equals("P")){out.print("selected");} %>>Porcentaje</option>
						</select>
					</div><br>
					<div class="control-group">
						<label for="total"><spring:message code="aca.Total"/><span class="required-indicator">*</span></label>
						<input type="text" id="total" required  name="total" class="form-control"  style="width:75%;" maxlength="8" value="<%=alumDescuento.getTotal()%>" />
					</div><br>
					<div class="control-group">
						<label for="fecha"><spring:message code="aca.Fecha"/></label>
						<input type="text" data-date-format="dd/mm/yyyy" id="fecha" name="fecha" class="form-control"  style="width:75%;" value="<%=alumDescuento.getFecha()%>" />
					</div><br>
					<div class="control-group">
						<label for="observaciones">Observaciones<span class="required-indicator">*</span></label>
						<input type="text" id="observaciones" name="observaciones" class="form-control"  style="width:75%;" value="<%=alumDescuento.getObservaciones()%>" />
					</div><br>
					<div class="control-group">
						<label for="aplicado">Descto. Aplicado<span class="required-indicator">*</span></label>
						<select id="aplicado" name="aplicado" class="form-select"  style="width:75%;" >
							<option value="N" <%if(alumDescuento.getAplicado().equals("N")){out.print("selected");} %>><spring:message code='aca.No'/></option>
							<option value="S" <%if(alumDescuento.getAplicado().equals("S")){out.print("selected");} %>><spring:message code='aca.Si'/></option>			
						</select>
					</div>				
				</div>
			</div>
			<div class="alert alert-info">
				<a class="btn btn-primary enviar"><spring:message code="aca.Grabar"/></a>
			</div>
		</form>
	</div>
</body>
</html>
<script>
	jQuery('#fecha').datepicker();
</script>
<script>
	function validador(select,input){
		var selMatricula = select.val();
		if(selMatricula=="P"){
			if(parseInt(input.val())>100){
				input.val("100");
				alert("El valor del porcentaje no puede ser mayor a 100");
			}
		}	
	}

	jQuery('#tipoMatricula').change(function(){
		validador(jQuery(this),jQuery('#dMatricula'));
	});
	
	jQuery('#dMatricula').keyup(function(){
		validador(jQuery('#tipoMatricula'), jQuery(this));
	});

	jQuery('#dEnsenanza').keyup(function(){
		validador(jQuery('#tipoEnsenanza'),jQuery(this));
	});
	
	jQuery('#tipoEnsenanza').change(function(){
		validador(jQuery(this), jQuery('#dEnsenanza'));
	});
	
	jQuery('#dInternado').keyup(function(){
		validador(jQuery('#tipoInternado'),jQuery(this));
	});
	
	jQuery('#tipoInternado').change(function(){
		validador(jQuery(this), jQuery('#dInternado'));
	});
</script>
<script>
	jQuery('.enviar').on("click", function(){
		var variables = jQuery('form').serialize();
		if(document.datos.matricula.value != ""
		&& document.datos.tipoMatricula.value !=""
		&& document.datos.dMatricula.value !=""
		&& document.datos.tipoEnsenanza.value !=""
		&& document.datos.dEnsenanza.value !=""
		&& document.datos.tipoInternado.value !=""
		&& document.datos.dInternado.value !=""){
			jQuery.post("guardar", variables, function(r){
				var resultado = jQuery('.respuesta');
				resultado.html(r);
			});
		}else{
			alert("Llene todos los campos indicados");
		}
	});
</script>
<script>
var loadingBar 	= jQuery('.loading-bar');
var result 		= jQuery('.result');
jQuery("#matricula").keyup(function(){
	$this = jQuery(this);
	var value 	= $this.val();
	var length 	= value.length;
	if(length == 7){
		loadingBar.show();
		jQuery.get("getNombreAlumno?matricula="+value, function(r){
			loadingBar.hide();
			var nombre = jQuery.trim(r);
			result.html(nombre);
		});
	}else{
		loadingBar.hide();
		result.html("");
	}
});
</script>