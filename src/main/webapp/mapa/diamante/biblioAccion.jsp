<%@page import="aca.plan.MapaNuevoBibliografia"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="mapaNuevoBibliografia" class="aca.plan.MapaNuevoBibliografia" scope="page"/>
<jsp:useBean id="mapaNuevoBibliografiaU" class="aca.plan.MapaNuevoBibliografiaUtil" scope="page"/>

<script type="text/javascript">
	function revisaForma(){
		if( document.forma.bibliografia.value != "" &&  document.forma.referencia.value != ""){
			document.forma.Accion.value = "1";
			document.forma.submit();				
		}else{
			alert("Llene todos los campos para poder grabar");
		}
	}
</script>

<%
	//ArrayList<CatCarrera> listCarreras	= catCarreraU.getListAll(conEnoc, "ORDER BY ENOC.FACULTAD_NOMBRE(ENOC.FACULTAD(CARRERA_ID)), NOMBRE_CARRERA");
	String planId			= request.getParameter("planId");
	String cursoId			= request.getParameter("cursoId");
	String unidadId			= request.getParameter("unidadId");
	String versionId		= request.getParameter("versionId");
	String bibliografia 	= request.getParameter("bibliografia")==null?"-":request.getParameter("bibliografia");
	String referencia 		= request.getParameter("referencia")==null?"-":request.getParameter("referencia");
	String tipo				= request.getParameter("tipoBibliografia")==null?"0":request.getParameter("tipoBibliografia");
	String bibliografiaId	= request.getParameter("bibliografiaId")==null?"0":request.getParameter("bibliografiaId");
	
	int	accion				= Integer.parseInt(request.getParameter("Accion")==null?"0":request.getParameter("Accion"));
	String respuesta 		= "-";
	String alert			= "-";
	
	switch(accion){
		case 0:{
			//Nuevo
			mapaNuevoBibliografia.setCursoId(cursoId);			
			mapaNuevoBibliografia.setBibliografia("-");
			mapaNuevoBibliografia.setReferencia("-");
			mapaNuevoBibliografia.setTipo("1");	
			bibliografiaId = mapaNuevoBibliografiaU.maximoReg(conEnoc, cursoId);
		}break;
		case 1:{ //Guardar
			mapaNuevoBibliografia.setCursoId(cursoId);
			mapaNuevoBibliografia.setBibliografiaId(bibliografiaId);
			mapaNuevoBibliografia.setBibliografia(bibliografia);
			mapaNuevoBibliografia.setReferencia(referencia);
			mapaNuevoBibliografia.setTipo(tipo);
			if (mapaNuevoBibliografiaU.existeReg(conEnoc, cursoId, bibliografiaId) ){			
				if(mapaNuevoBibliografiaU.updateReg(conEnoc, mapaNuevoBibliografia)){
					respuesta = "¡ Grabada !";
					alert = "success";
				}else{
					respuesta = "No guardo la bibliografia";
					alert = "danger";
				}
			}else{
				if(mapaNuevoBibliografiaU.insertReg(conEnoc, mapaNuevoBibliografia)){
					respuesta = "¡ Grabada !";
					alert = "success";
				}else{
					respuesta = "No guardo la bibliografia";
					alert = "danger";
				}				
			}
			
		}break;
		case 2:{ //Editar		
			mapaNuevoBibliografia.setCursoId(cursoId);
			mapaNuevoBibliografia.setBibliografiaId(bibliografiaId);
			if(mapaNuevoBibliografiaU.existeReg(conEnoc, cursoId, bibliografiaId)){
				mapaNuevoBibliografia.mapeaRegId(conEnoc, cursoId, bibliografiaId);
			}
		}break;
	}	
%>
<body>
<div class="container-fluid">
	<h2> <%=planId%> - <%=aca.plan.MapaNuevoCursoUtil.getNombre(conEnoc, cursoId)%><small class="text-muted fs-5">( <%=aca.plan.MapaNuevoUnidadUtil.getNombre(conEnoc, cursoId, unidadId)%> )</small></h2>
	<form id="frmBiblio" name="forma" action="biblioAccion">
		<input type="hidden" id="planId" name="planId" value="<%=planId%>" />
		<input type="hidden" id="cursoId" name="cursoId" value="<%=cursoId%>" />
		<input type="hidden" id="versionId" name="versionId" value="<%=versionId%>" />
		<input type="hidden" id="unidadId" name="unidadId" value="<%=unidadId%>" />
		<input type="hidden" id="Accion" name="Accion" value="<%=accion%>" />
		<input type="hidden" id="bibliografiaId" name="bibliografiaId" value=<%=bibliografiaId%> />
		<div class="alert alert-info">
			<a class="btn btn-primary" href="editaUnidad?planId=<%=planId%>&versionId=<%=versionId%>&cursoId=<%=cursoId%>"><spring:message code="aca.Regresar"/></a>
		</div>
<% if(!respuesta.equals("-")){%>
	<div class="alert alert-<%=alert%>" role="alert"><%=respuesta%></div>
<% }%>
		<div class="row">
			<div class="span3">
				<label><b><spring:message code="aca.Bibliografia"/></b></label>
				<textarea title="Bibliografia completa" name="bibliografia" id="bibliografia" onkeyup="checkSize(this, event, 500);" class="500" cols="80" ><%=mapaNuevoBibliografia.getBibliografia()%></textarea>		
				<br><br>
				<label><b><spring:message code="aca.mapa.diamante.biblioAccion.Frase1"/></b></label>				
				<input title="Abreviacion con la que se citar&aacute; la bibliografia (Ej. [perez, 2007])" type="text" class="text" name="referencia" id="referencia" size="40" maxlength="190" value="<%=mapaNuevoBibliografia.getReferencia()%>" />	
				<br><br>
				<label><b><spring:message code="aca.Tipo"/></b></label>
				<select name="tipoBibliografia">
					<option value="1" <%=mapaNuevoBibliografia.getTipo().equals("1")?"selected":""%>><spring:message code="aca.mapa.diamante.biblioAccion.Frase2"/></option>
					<option value="2" <%=mapaNuevoBibliografia.getTipo().equals("2")?"selected":""%>><spring:message code="aca.mapa.diamante.biblioAccion.Frase3"/></option>
				</select>		
				<br><br>
			</div>
		</div>
		<div class="alert alert-info">
			<input type="button" class="btn btn-primary" style="button" value=<spring:message code="aca.Guardar"/> onclick="javascript:revisaForma();" />
		</div>
	</form>
</div>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>