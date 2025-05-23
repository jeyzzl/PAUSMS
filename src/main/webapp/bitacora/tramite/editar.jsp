<%@ page import="aca.bitacora.spring.BitTramite"%>
<%@ page import="aca.bitacora.spring.BitArea"%>
<%@ page import="java.util.List"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%
	String tramiteId 		= request.getParameter("TramiteId")==null?"0":request.getParameter("TramiteId");
	BitTramite tramite		= (BitTramite) request.getAttribute("tramite");
	String mensaje			= (String) request.getAttribute("mensaje");
	List<BitArea> lisArea 	= (List<BitArea>) request.getAttribute("lisArea");
%>

<div class="container-fluid">
	<h2>Tramite</h2>
	<div class="alert alert-info">
		<a href="tramite" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;
	</div>
<% if(mensaje.equals("1")){%>
	<div class="alert alert-success" role="alert">
	 	Grabado
	</div>
<% }else if(mensaje.equals("2")){%>
	<div class="alert alert-danger" role="alert">
  		No pudo grabar
	</div>
<% }%>
	<form name="formTramite" action="grabar" method="post">
		<div class="row container-fluid">
			<div class="span5">
				<input type="hidden" name="TramiteId" value="<%=tramiteId%>">
				<label for="nombre">Area responsable</label>
				<select name="AreaId"  class="form-select" style="width:350px">
<%
				for(BitArea area : lisArea){
%>
					<option <%=tramite.getAreaId().equals(area.getAreaId())?"Selected":""%> value="<%=area.getAreaId()%>"><%=area.getAreaNombre()%></option>	
<%				}%>
				</select><br>
				<input type="hidden" name=TramiteId value="<%=tramiteId%>">
				<label for="nombre">Nombre</label>
				<input type="text" class="text form-control" style="width:500px" name="Nombre" size="50" maxlength="50" value="<%=tramite.getNombre()%>">
				<br>
	            <label for="minimo">Tiempo mínimo</label>
	            <input type="text" class="text form-control" style="width:150px" name="Minimo" size="7" maxlength="6" value="<%=tramite.getMinimo()%>">
	           	<br>
	           	<label for="maximo">Tiempo máximo</label>
				<input type="text" class="text form-control" style="width:150px" name="Maximo" size="7" maxlength="6" value="<%=tramite.getMaximo()%>">
	            <br>
	            <label for="promedio">Tiempo promedio</label>
	            <input type="text" class="text form-control" style="width:150px" name="Promedio" size="7" maxlength="6" value="<%=tramite.getPromedio()%>">	   
	                     
			</div>&nbsp;&nbsp;&nbsp;&nbsp;
			<div class="span5">
				<label for="Tipo">Tipo</label>
	            <select name="Tipo" class="text form-select" style="width:350px">
	            	<option value="G" <%=tramite.getTipo().equals("G")?"selected":""%>>General</option>
	            	<option value="E" <%=tramite.getTipo().equals("E")?"selected":""%>>Externo</option>
	            	<option value="I" <%=tramite.getTipo().equals("I")?"selected":""%>>Interno</option>
	            </select>
	            <br>
				<label for="requisitos">Descripción del trámite </label>
				<textarea class="text form-control" name="Requisitos" rows="7" cols="100"><%=tramite.getRequisitos()%></textarea>
				<br>
			   <label for="costo">Costo</label>
				<input type="text" class="text form-control" style="width:350px" name="Costo" size="7" maxlength="30" value="<%=tramite.getImporte()%>">
				<br>
				<label for="SolAlumno">Trámite en linea</label>
	            <select name="SolAlumno" class="text form-select" style="width:350px">
	            	<option value="S" <%=tramite.getSolAlumno().equals("S")?"selected":""%>>Si</option>
	            	<option value="N" <%=tramite.getSolAlumno().equals("N")?"selected":""%>>No</option>
	            </select>
			</div>
		</div>
		<br>
		<div class="alert alert-info">
		<input class="btn btn-primary" type="submit" value="Grabar">
		</div>
	</form>
</div>