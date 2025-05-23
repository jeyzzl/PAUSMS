<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.catalogo.CatCarrera"%>

<jsp:useBean id="catCarrera" class="aca.catalogo.CatCarrera" scope="page"/>
<jsp:useBean id="catCarreraU" class="aca.catalogo.CatCarreraUtil" scope="page"/>
<jsp:useBean id="mapaNuevoPlan" class="aca.plan.MapaNuevoPlan" scope="page"/>
<jsp:useBean id="mapaNuevoPlanU" class="aca.plan.MapaNuevoPlanUtil" scope="page"/>

<link rel="stylesheet" href="../../js/chosen/chosen.css" />

<%
	ArrayList<CatCarrera> listCarreras	= catCarreraU.getListAll(conEnoc, "ORDER BY ENOC.FACULTAD_NOMBRE(ENOC.FACULTAD(CARRERA_ID)), NOMBRE_CARRERA");
	String facultad			= "";
	String respuesta		= "";
	String planId			= request.getParameter("planId");
	String versionId		= request.getParameter("versionId");
	int	accion				= Integer.parseInt(request.getParameter("Accion")==null?"0":request.getParameter("Accion"));
	
	String year 			= aca.util.Fecha.getHoy();
	String yearYear[]		= year.split("/"); 
	year					= yearYear[2];
	
	switch(accion){
		case 2:{//Guardar
			mapaNuevoPlan.setPlanId(planId);
			mapaNuevoPlan.setVersionId(versionId);
			if(mapaNuevoPlanU.existeReg(conEnoc, planId, versionId)){
				mapaNuevoPlan.mapeaRegId(conEnoc, planId, versionId);
				mapaNuevoPlan.setNombre(request.getParameter("nombre"));
				mapaNuevoPlan.setCarreraId(request.getParameter("carreraId"));
				mapaNuevoPlan.setTipo(request.getParameter("tipo"));
				mapaNuevoPlan.setIdioma(request.getParameter("idioma"));
				mapaNuevoPlan.setHfd(request.getParameter("hfd")==null?"N":"S");
				mapaNuevoPlan.setHps(request.getParameter("hps")==null?"N":"S");
				mapaNuevoPlan.setHts(request.getParameter("hts")==null?"N":"S");
				mapaNuevoPlan.setHei(request.getParameter("hei")==null?"N":"S");
				mapaNuevoPlan.setHss(request.getParameter("hss")==null?"N":"S");
				mapaNuevoPlan.setHas(request.getParameter("has")==null?"N":"S");
				if(mapaNuevoPlanU.updateReg(conEnoc, mapaNuevoPlan)){
					response.sendRedirect("planes?Accion=5");
				}else{
					respuesta = "<font size=2 color=red><b>Ocurri&oacute; un error al modificar el plan. Int&eacute;ntelo de nuevo</b></font>";
				}
			}else{
				mapaNuevoPlan.setVersionId(mapaNuevoPlanU.maximoReg(conEnoc, planId));
				mapaNuevoPlan.setNombre(request.getParameter("nombre"));
				mapaNuevoPlan.setCarreraId(request.getParameter("carreraId"));
				mapaNuevoPlan.setTipo(request.getParameter("tipo"));
				mapaNuevoPlan.setVersionNombre(request.getParameter("versionNombre"));
				mapaNuevoPlan.setIdioma(request.getParameter("idioma"));
				mapaNuevoPlan.setHfd(request.getParameter("hfd")==null?"N":"S");
				mapaNuevoPlan.setHps(request.getParameter("hps")==null?"N":"S");
				mapaNuevoPlan.setHts(request.getParameter("hts")==null?"N":"S");
				mapaNuevoPlan.setHei(request.getParameter("hei")==null?"N":"S");
				mapaNuevoPlan.setHss(request.getParameter("hss")==null?"N":"S");
				mapaNuevoPlan.setHas(request.getParameter("has")==null?"N":"S");
				mapaNuevoPlan.setYear("2017");
				
				mapaNuevoPlan.setEstado("A");
				if(mapaNuevoPlanU.insertReg(conEnoc, mapaNuevoPlan)){
					response.sendRedirect("planes?Accion=6");
				}else{
					respuesta = "<font size=2 color=red><b>Ocurri&oacute; un error al guardar el plan. Int&eacute;ntelo de nuevo</b></font>";
				}
			}
		}break;
	}
	
	if(planId != null){
		mapaNuevoPlan.mapeaRegId(conEnoc, planId, versionId);
	}
%>

<script type="text/javascript">
		function revisaForma(){
			if(document.forma.nombre.value != "" &&
			   document.forma.carreraId.value != "" &&
			   document.forma.versionNombre.value != ""){
				if(document.forma.planId.value.length == 8)
					document.forma.submit();
				else
					alert("El Plan ID debe ser de 8 caracteres");
			}else{
				alert("Llene todos los campos para poder grabar");
			}
		}
</script>


<div class="container-fluid">
	<h1>Editar plan</h1>
	<form id="forma" name="forma" action="editaPlan">
		<div class="alert alert-info">
			<a class="btn btn-primary" href="planes"><spring:message code="aca.Regresar"/></a>
		</div>
		<input type="hidden" id="Accion" name="Accion" value="2" />
		<div class="row">
			<div class="col-3">
				<label>Plan ID</label>
<%
	if(planId != null){
%>
				 <%=planId %>
				 <input type="hidden" id="planId" name="planId" value="<%=planId %>" />
				 <input type="hidden" id="versionId" name="versionId" value="<%=versionId %>" />
<%
	}else{
%>
				<input type="text" class="text" id="planId" name="planId" size="8" maxlength="8" />
<%
	}
%>
				<br><br>
				<label><spring:message code="aca.Nombre"/></label>
				<input type="text" class="form-control" id="nombre" style="width:280px;" name="nombre" value="<%=mapaNuevoPlan.getNombre() %>" size="30" maxlength="100" />
				<br><br>
				<label><spring:message code="aca.Carrera"/></label>
				<select id="carreraId" class="form-select"name="carreraId" class="chosen" style="width:280px;">
					<optgroup label="">
<%
	for(int i = 0; i < listCarreras.size(); i++){
		catCarrera = (CatCarrera) listCarreras.get(i);
		if(!facultad.equals(catCarrera.getFacultadId())){
			facultad = catCarrera.getFacultadId();
%>
					</optgroup>
					<optgroup label="<%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, catCarrera.getFacultadId()) %>">
<%
		}
%>
						<option value="<%=catCarrera.getCarreraId() %>" <%=catCarrera.getCarreraId().equals(mapaNuevoPlan.getCarreraId())?"Selected":"" %>><%=catCarrera.getNombreCarrera() %></option>
<%
	}
%>
					</optgroup>
				</select>
				<br><br>
				<label>Periodos:</label>
				<select id="tipo" class="form-select" style="width:280px;" name="tipo">
				    <option value="S" <%=mapaNuevoPlan.getTipo().equals("S")?"Selected":""%>>Semestres</option>
				    <option value="T" <%=mapaNuevoPlan.getTipo().equals("T")?"Selected":""%>>Tetramestres</option>
				    <option value="A" <%=mapaNuevoPlan.getTipo().equals("A")?"Selected":""%>>Años</option>
			  	</select>
				<br><br>
				<label>Versión</label>
				<input type="text" class="form-control" style="width:280px;" id="versionNombre" name="versionNombre" value="<%=mapaNuevoPlan.getVersionNombre() %>" size="8" maxlength="50" <%=planId != null ? "Disabled" : "" %> />&nbsp;<i>Sugerencia: 0.01</i>
				<br><br>
			</div>
			<div class="col-3">
				<label>Idioma:</label>
				<select id="idioma" class="form-select" style="width:280px;" name="idioma">
					<option value="E" <%=mapaNuevoPlan.getIdioma().equals("E")?"Selected":""%>>Español</option>
				    <option value="I" <%=mapaNuevoPlan.getIdioma().equals("I")?"Selected":""%>>Inglés</option>				   
			  	</select>
				<br><br>
				<label class="alert alert-info" style="width:280px;">Campos de Horas a Mostrar</label>
				<br></br>
				HTS : <input type="checkbox" id="hts" name="hts" value="S" <%=mapaNuevoPlan.getHts().equals("S")?"checked=\"checked\"":"" %>/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				HPS : <input type="checkbox" id="hps" name="hps" value="S" <%=mapaNuevoPlan.getHps().equals("S")?"checked=\"checked\"":"" %>/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				HEI :&nbsp;&nbsp;<input type="checkbox" id="hei" name="hei" value="S" <%=mapaNuevoPlan.getHei().equals("S")?"checked=\"checked\"":"" %>/>
				<br><br>

				HFD : <input type="checkbox" id="hfd" name="hfd" value="S" <%=mapaNuevoPlan.getHfd().equals("S")?"checked=\"checked\"":"" %>/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				HSS : <input type="checkbox" id="hfd" name="hss" value="S" <%=mapaNuevoPlan.getHss().equals("S")?"checked=\"checked\"":"" %>/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				HAS : <input type="checkbox" id="hfd" name="has" value="S" <%=mapaNuevoPlan.getHas().equals("S")?"checked=\"checked\"":"" %>/>
				<br><br>
				Año : <input type="text" class="form-control" name="year" style="width:280px;" value="<%=year%>" >
			</div>
		</div>
		<div class="alert alert-info">
			<input type="button" class="btn btn-primary" style="button" value="Guardar" onclick="revisaForma();" />
		</div>

	</form>
</div>
</body>

<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript"> 
		jQuery(".chosen").chosen(); 
</script>
<%@ include file= "../../cierra_enoc.jsp" %>