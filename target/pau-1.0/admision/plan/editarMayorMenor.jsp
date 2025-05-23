<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@page import= "aca.plan.spring.MapaMayorMenor"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Grabar() {
		document.frmMayorMenor.submit();
	}
</script>

<%
	String codigoAlumno 			= (String) session.getAttribute("codigoAlumno");
	String alumnoNombre 			= (String)request.getAttribute("alumnoNombre");
	String planId 					= (String)request.getAttribute("planId");
	String mayor 					= (String)request.getAttribute("mayor");
	String menor 					= (String)request.getAttribute("menor");

    List<MapaMayorMenor> lisMayores = (List<MapaMayorMenor>) request.getAttribute("lisMayores");
    List<MapaMayorMenor> lisMenores = (List<MapaMayorMenor>) request.getAttribute("lisMenores");
	
%>
<div class="container-fluid">
	<h2>Major and Minor - Student: <%=alumnoNombre  %> <small class="text-muted fs-5">(<%=codigoAlumno %>)</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="planes"><spring:message code="aca.Regresar"/></a>
	</div>
	
	<div class="form-group">
		<form name="frmMayorMenor" method="post" action="grabarMayorMenor">
			<div class="container-fluid	">
				<label class="form-label">Student ID</label><br>
				<input class="form-control mb-3" type="text" name="CodigoPersonal" value="<%=codigoAlumno %>" style="width: 15rem" readonly>
				<label class="form-label">Plan ID</label><br>
				<input class="form-control mb-3" type="text" name="PlanId" value="<%=planId %>" style="width: 15rem" readonly>
				<label class="form-label">Major</label><br>
				<select class="form-select mb-3" id="Mayor" name="Mayor" style="width:15rem">
<%                  for(MapaMayorMenor ma : lisMayores){
%>
                    <option value="<%=ma.getFolio()%>" <%=ma.getFolio().equals(mayor)?"selected":""%>><%=ma.getNombre()%></option>
<%                  }
%>
				</select>
				<label class="form-label">Minor</label><br>
				<select class="form-select mb-3" id="Menor" name="Menor" style="width:15rem">
<%                  for(MapaMayorMenor me : lisMenores){
%>
                    <option value="<%=me.getFolio()%>" <%=me.getFolio().equals(menor)?"selected":""%>><%=me.getNombre()%></option>
<%                  }
%>
				</select>
	        </div>
    		<div class="alert alert-info">
				<button type="submit" class="btn btn-primary">Save</button>
	        </div>
		</form>
    </div>
</div>