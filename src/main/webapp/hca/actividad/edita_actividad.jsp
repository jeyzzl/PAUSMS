<%@page import="java.util.List"%>
<%@page import="aca.catalogo.spring.CatNivel"%>
<%@page import="aca.hca.spring.HcaTipo"%>
<%@page import="aca.hca.spring.HcaActividad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	
	HcaActividad hcaActividad 	= (HcaActividad)request.getAttribute("hcaActividad");
	List<CatNivel> lisNiveles 	= (List<CatNivel>)request.getAttribute("lisNiveles");
	List<HcaTipo> lisTipos 		= (List<HcaTipo>)request.getAttribute("lisTipos");	
%>	
<head>
<script type="text/javascript">
	function revisa(){
		if(document.forma.Nombre.value != "" && document.forma.Valor.value != ""){			
			return true;
		}else{
			alert("Llene los valores requeridos(*) para poder guardar");
		}
		return false;
	}
</script>
</head>
<div class="container-fluid">
	<h1>Edita Actividad</h1>
	<div class="alert alert-info">
		<a href="listado" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>
	</div>
	<form id="forma" name="forma" action="grabar" method="post">
		<input type="hidden" name="ActividadId" value="<%=hcaActividad.getActividadId()%>">
		<div class="row">
			<div class="col-3">
				<label for="nivel">Nivel<font color="#AE2113"> *</font></label>			
				<select id="Nivel"  name="Nivel" class="form-select">
				  	<option value="0">T o d o s </option>
<%					for(CatNivel catNivel :lisNiveles){ %>
				  	<option value="<%=catNivel.getNivelId() %>"<%=hcaActividad.getNivelId().equals(catNivel.getNivelId())?" Selected":""%>><%=catNivel.getNombreNivel()%></option>
<%					} %>
				</select>
				<br><br>
				<label for="tipo"><spring:message code="aca.Tipo"/><font color="#AE2113"> *</font></label>					
				<select id="Tipo" name="Tipo" class="form-select">
<%				for(HcaTipo hcaTipo : lisTipos){%>
				  	<option value="<%=hcaTipo.getTipoId() %>"<%=hcaActividad.getTipoId().equals(hcaTipo.getTipoId())?" Selected":"" %>><%=hcaTipo.getTipoNombre() %></option>
<%				} %>
				</select>
				<br><br>
				<label for="nombre"><spring:message code="aca.Nombre"/><b><font color="#AE2113"> *</font></label>
				<input type="text"  class="form-control" id="Nombre" name="Nombre" value="<%=hcaActividad.getActividadNombre() %>" maxlength="70" size="40" />
				<br><br>
				<label for="valor">Valor<font color="#AE2113"> *</font></label>
				<input type="text" class="form-control" id="Valor" name="Valor" value="<%=hcaActividad.getValor() %>" maxlength="6" size="4" />
				<br><br>
				<label for="Modificable">Modificable</label>
				<select name="Modificable" class="form-select">
				  	<option <%=hcaActividad.getModificable().equals("S")?" Selected":"" %> value="S"><spring:message code='aca.Si'/></option>
				  	<option <%=hcaActividad.getModificable().equals("N")?" Selected":"" %> value="N"><spring:message code='aca.No'/></option>
				</select>
			</div>
		</div>
		<br>
		<div class="alert alert-info">
			<input type="submit" class="btn btn-primary" value="Grabar" onclick="return revisa();"/>
		</div>
	</form>
</div>