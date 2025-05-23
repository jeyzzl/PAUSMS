<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ include file="menuPortal.jsp"%>

<jsp:useBean id="PorEstudio" scope="page"
	class="aca.portafolio.PorEstudio" />
<jsp:useBean id="PorNivelU" scope="page"
	class="aca.portafolio.PorNivelUtil" />

<script>
	function guardar(){
		if( document.forma.fecha.value != "" && document.forma.titulo.value != ""){
			document.forma.Accion.value = "1";
			document.forma.submit();
		}else{
			alert("Todos los campos son requeridos");
		}
	}
</script>

<%
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	String periodoId		= (String) session.getAttribute("porPeriodo");
	
	String folio 			= request.getParameter("folio");
	if(folio==null){
		folio = PorEstudio.maximoReg(conEnoc, codigoPersonal);
	}else{
		PorEstudio.mapeaRegId(conEnoc, codigoPersonal, folio);
	}
	
	String accion 			= request.getParameter("Accion")==null?"":request.getParameter("Accion");
	String msj 				= "";
	
	if(accion.equals("1")){
		PorEstudio.setCodigoPersonal(codigoPersonal);
		PorEstudio.setFolio( request.getParameter("Folio") );
		PorEstudio.setFecha(request.getParameter("fecha"));
		PorEstudio.setNivelId(request.getParameter("nivelId"));
		PorEstudio.setTitulo(request.getParameter("titulo"));
		PorEstudio.setHojas("");
		
		if(PorEstudio.existeReg(conEnoc)){
			if(PorEstudio.updateReg(conEnoc)){
				msj = "<div class='alert alert-success'>Se actualizó la información</div>";
			}else{
				msj = "<div class='alert alert-danger'>Ocurrió un error al actualizar la información</div>";
			}
		}else{
			if(PorEstudio.insertReg(conEnoc)){
				msj = "<div class='alert alert-success'>Se guardó la información</div>";
			}else{
				msj = "<div class='alert alert-danger'>Ocurrió un error al guardar la información</div>";
			}
		}
		
	}
	
	
	ArrayList<aca.portafolio.PorNivel> niveles = PorNivelU.getListAll(conEnoc, "");

%>

<div class="container-fluid">
	<h2>
		Agregar grado <small> (<%=periodoId%>)
		</small>
	</h2>

	<%=msj %>

	<div class="alert alert-info">
		<a href="datos" class="btn btn-primary"><i
			class="icon-arrow-left icon-white"></i>
		<spring:message code='aca.Regresar' /></a>
	</div>

	<form action="" name="forma" method="post">
		<input type="hidden" name="Accion" /> <input type="hidden"
			name="Folio" value="<%=folio%>" />

		<p>
			<label for="fecha"><spring:message code="aca.Fecha" /></label> <input
				type="text" name="fecha" id="fecha" class="input-medium"
				data-date-format="dd/mm/yyyy" value="<%=PorEstudio.getFecha() %>" />
		</p>

		<p>
			<label for="nivelId">Nivel</label> <select name="nivelId"
				id="nivelId">
				<%for(aca.portafolio.PorNivel nivel : niveles){ %>
				<option value="<%=nivel.getNivelId() %>"
					<%if(PorEstudio.getNivelId().equals(nivel.getNivelId())){out.print("selected");} %>><%=nivel.getNivelNombre() %></option>
				<%} %>
			</select>
		</p>

		<p>
			<label for="titulo">Titulo</label> <input type="text" name="titulo"
				id="titulo" class="input-xxlarge"
				value="<%=PorEstudio.getTitulo() %>" />
		</p>

	</form>

	<div class="alert alert-info">
		<a class="btn btn-primary btn-large" href="javascript:guardar()"><i
			class="icon-ok icon-white"></i> Guardar</a>
	</div>

</div>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script src="../../bootstrap/datepicker/datepicker.js"
	type="text/javascript"></script>


<script>		
		jQuery('#fecha').datepicker();
</script>

<script>
	jQuery('.datos').addClass('active');
</script>
<%@ include file="../../cierra_enoc.jsp"%>