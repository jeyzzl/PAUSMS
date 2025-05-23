<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="AfisRegistro" scope="page" class="aca.afis.AfisRegistro"/>
<jsp:useBean id="AfisRegistroUtil" scope="page" class="aca.afis.AfisRegistroUtil"/>
<jsp:useBean id="AfisPeriodo" scope="page" class="aca.afis.AfisPeriodo"/>
<jsp:useBean id="AfisPeriodoUtil" scope="page" class="aca.afis.AfisPeriodoUtil"/>
<jsp:useBean id="Alumno" scope="page" class="aca.alumno.AlumUtil"/>

<script type="text/javascript">
	function Grabar() {
		document.forma.submit();
	}	
</script>

<%	
	String codigoPersonal			= request.getParameter("CodigoPersonal")==null?(String) session.getAttribute("codigoAlumno"):request.getParameter("CodigoPersonal");
	String planId					= aca.alumno.PlanUtil.getPlanActual(conEnoc, codigoPersonal);
	String carreraId 				= aca.alumno.PlanUtil.getCarreraIdPLAN(conEnoc, planId);

	String accion 					= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String periodoId 				= request.getParameter("PeriodoId")==null?"0":request.getParameter("PeriodoId");

	AfisPeriodo = AfisPeriodoUtil.mapeaRegId(conEnoc, periodoId);
	
	String residencia				= aca.alumno.AcademicoUtil.getResidencia(conEnoc, codigoPersonal);

	String mensaje					= "";

	if(accion.equals("1")){ // Grabar
		AfisRegistro.setPeriodoId(periodoId);
		AfisRegistro.setCodigoPersonal(codigoPersonal);
		AfisRegistro.setCarreraId(carreraId);
		AfisRegistro.setPeso(request.getParameter("Peso"));
		AfisRegistro.setTalla(request.getParameter("Talla"));
		AfisRegistro.setCintura(request.getParameter("Cintura"));
		AfisRegistro.setGrasa(request.getParameter("Grasa"));
		AfisRegistro.setMusculo(request.getParameter("Musculo"));
		AfisRegistro.setImc(request.getParameter("Imc"));
		AfisRegistro.setDieta(request.getParameter("Dieta"));
		AfisRegistro.setResidencia(residencia);
		
		if(!AfisRegistroUtil.existeReg(conEnoc, codigoPersonal, periodoId )){
			if (AfisRegistroUtil.insertReg(conEnoc, AfisRegistro)) {
				mensaje = "1";
			} else {
				mensaje = "2";
			}
		} else {
			
			AfisRegistro.setPeriodoId(periodoId);
			AfisRegistro.setCodigoPersonal(codigoPersonal);
			AfisRegistro.setCarreraId(carreraId);
			AfisRegistro.setPeso(request.getParameter("Peso"));
			AfisRegistro.setTalla(request.getParameter("Talla"));
			AfisRegistro.setCintura(request.getParameter("Cintura"));
			AfisRegistro.setGrasa(request.getParameter("Grasa"));
			AfisRegistro.setMusculo(request.getParameter("Musculo"));
			AfisRegistro.setImc(request.getParameter("Imc"));
			AfisRegistro.setDieta(request.getParameter("Dieta"));
			AfisRegistro.setResidencia(residencia);
			
			if (AfisRegistroUtil.updateReg(conEnoc, AfisRegistro)) {
				mensaje = "3";
			} else {
				mensaje = "4";
			}
		}
	}
	
	if(accion.equals("2")){ // Muestra datos
		AfisRegistro = AfisRegistroUtil.mapeaRegId(conEnoc, codigoPersonal, periodoId);
	}
%>

<div class="container-fluid">
	<h1>Registrar alumno <small>(<%= AfisPeriodo.getPeriodoNombre() %>)</small></h1>
	<form action="accionAlumno?PeriodoId=<%=periodoId%>" method="post" name="forma">
		<input type="hidden" name="Accion" value="1">
		<input type="hidden" name="CodigoPersonal" value="<%=codigoPersonal%>">
		<div class="alert alert-info">
			<a class="btn btn-primary" href="listado?PeriodoId=<%=periodoId%>" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;&nbsp;&nbsp;
			( <%=codigoPersonal%> | <%=Alumno.getNombre(conEnoc,codigoPersonal,"")%> )
		</div>
		<div class="row">
			<div class="span2">
				<fieldset >
					<label for="Peso"><strong>Peso(Kg)</strong></label>
				    <input name="Peso" type="text" id="Peso" class="input input-mini" maxlength="5" required value="<%=AfisRegistro.getPeso()%>">
				    <br><br>
				    <label for="Talla"><strong>Talla(m)</strong></label>
					<input name="Talla" type="text" id="Talla" class="input input-mini" maxlength="5" required value="<%=AfisRegistro.getTalla()%>">	    
				    <br><br>
				</fieldset>
			</div>
			<div class="span2">
				<fieldset >
					<label for="Cintura"><strong>Circ. Abd.(cm)</strong></label>
					<input name="Cintura" type="text" id="Cintura" class="input input-mini" maxlength="5" required value="<%=AfisRegistro.getCintura()%>">	    
				    <br><br>
				    <label for="Grasa"><strong>% Grasa</strong></label>
				    <input name="Grasa" type="text" id="Grasa" class="input input-mini" maxlength="5" required value="<%=AfisRegistro.getGrasa()%>">
				    <br><br>
				</fieldset>
			</div>
			<div class="span2">
		        <fieldset>
				    <label for="Musculo"><strong>Masa Muscular</strong></label>
					<input name="Musculo" type="text" id="Musculo" class="input input-mini" maxlength="5" required value="<%=AfisRegistro.getMusculo()%>">	
				 	<br><br>	
					<label for="Imc"><strong>IMC</strong></label>
					<input name="Imc" type="text" id="Imc" class="input input-mini" maxlength="5" required value="<%=AfisRegistro.getImc()%>">
				</fieldset>
			</div>
			<div class="span2">
		        <fieldset>
				    <label for="Dieta"><strong>Dieta</strong></label>
					<select name="Dieta">
						<option value='0' <%=AfisRegistro.getDieta().equals("0")?"selected":""%>>Elegir</option>
						<option value='1' <%=AfisRegistro.getDieta().equals("1")?"selected":""%>>Vegetariano</option>
						<option value='2' <%=AfisRegistro.getDieta().equals("2")?"selected":""%>>No Vegetariano</option>					
					</select>	
				</fieldset>
			</div>
		</div>
<%	
		if(mensaje.equals("1")){
%>
			<div class="alert alert-success">
	  			<spring:message code="aca.Guardado"/>
	  		</div>
<%	
		} else if(mensaje.equals("2")){
%>
	  		<div class="alert alert-danger">
	  			No guardo
	  		</div>
<%	
		} else if(mensaje.equals("3")){
%>
	  		<div class="alert alert-success">
	  			Modificado
	  		</div>
<%	
		} else if(mensaje.equals("4")){
%>
	  		<div class="alert alert-danger">
	  			No modifico
	  		</div>
<%	
		}
%>   
		<div class="alert alert-info">
			<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Guardar"/></a>
		</div>	
	</form>	
</div>

<%@ include file= "../../cierra_enoc.jsp" %>