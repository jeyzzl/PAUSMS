<%@ page import="aca.bitacora.spring.BitArea"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%
	String areaId 		= request.getParameter("AreaId")==null?"0":request.getParameter("AreaId");
	BitArea area 		= (BitArea)request.getAttribute("area");
%>

<div class="container-fluid">
	<h2>&Aacute;rea</h2>
	<div class="alert alert-info">
		<a href="area" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;
	</div>
	<form name="formArea" action="grabar" method="post">
		<div class="row container-fluid">
			<div class="span3">
				<input type="hidden" name=AreaId value="<%=areaId%>">
				<label for="nombre"><b>Nombre:</b></label>
				<input type="text" class="text form-control" name="Nombre" style="width:250px" size="40" maxlength="40" value="<%=area.getAreaNombre()%>">
				<br>
				<label for="responsable"><b>Responsable:</b></label>
				<input type="text" class="text form-control" name="Responsable" style="width:250px" size="6" maxlength="7" value="<%=area.getResponsable()%>">  
			</div>
		</div>
		<br>
	<div class="alert alert-info">
		<input class="btn btn-primary" type="submit" value="Grabar">
	</div>
	</form>
</div>