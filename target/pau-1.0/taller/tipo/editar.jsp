<%@ page import="aca.bec.spring.BecTipo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%
	String ejercicioId  	= (String)session.getAttribute("ejercicioId");
	String codigo			= (String)session.getAttribute("codigoEmpleado");
	BecTipo becTipo 		= (BecTipo)request.getAttribute("becTipo");
	String usuarioNombre	= (String)request.getAttribute("usuarioNombre");
	String mensaje			= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
	
	if(mensaje.equals("1")){
		mensaje = "Guardado";
	}else if(mensaje.equals("2")){
		mensaje = "Modificado";
	}
%>

<style>
 	body{
 		background : white;
 	}
 </style>
<div class="container-fluid">
	<h2>
		<a href="becas?ejercicioId=<%=becTipo.getIdEjercicio()%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a> &nbsp; 
		Tipos de beca <small class="text-muted fs-5">( <%=codigo%> - <%=usuarioNombre%> - <%=becTipo.getIdEjercicio()%> )</small>
	</h2>
	<form name="frmBeca" action="grabar" method="post">
	<input name="Tipo" type="hidden" value="<%=becTipo.getTipo()%>"/>
	<input name="Departamentos" type="hidden" value="<%=becTipo.getDepartamentos()%>"/>
	<input name="Meses" type="hidden" value="<%=becTipo.getMeses()%>"/>
	<input name="TipoAlumno" type="hidden" value="<%=becTipo.getTipoAlumno()%>"/>
	<input name=Diezmo type="hidden" value="<%=becTipo.getDiezmo()%>"/>
	<input name="Estado" type="hidden" value="<%=becTipo.getEstado()%>"/>
	<input name="Acumula" type="hidden" value="<%=becTipo.getAcumula()%>"/>
	<input name="Colporta" type="hidden" value="<%=becTipo.getColporta()%>"/>
	<hr>	
<% 	if(!mensaje.equals("0")){%>
	<div class="alert alert-success">
		<%=mensaje%>
	</div>
<% 	}%>
	<div class="row container-fluid">
		<div class="span3 col">			
			<label for="Nombre"><spring:message code="aca.Nombre"/></label>	
			<input type="text" name="Nombre" id="Nombre" class="form-control" maxlength="50" value="<%= becTipo.getNombre()%>"/>
			<input type="hidden" name="EjercicioId" id="EjercicioId" class="form-control" value="<%= becTipo.getIdEjercicio()%>"/>
			<br>
			<label for="Cuenta"><spring:message code="aca.Cuenta"/></label>			
			<input type="text" name="Cuenta" id="Cuenta" class="form-control" value="<%= becTipo.getCuenta()%>"/>
			<br>			
			<label for="Cuenta">Sunplus</label>			
			<input type="text" name="CuentaSunplus" id="CuentaSunplus" class="form-control" value="<%= becTipo.getCuentaSunplus()%>"/>
			<br>
			<label for="Mostrar">Mostrar</label>			
			<select name="Mostrar" class="form-select" style="width:120px">
				<option value="S" <%=becTipo.getMostrar().equals("S")?"selected":""%>>SI</option>
				<option value="N" <%=becTipo.getMostrar().equals("N")?"selected":""%>>NO</option>
			</select>
			<br>
			<label for="HorasPrepa">Descripción</label>
			<textarea id="Descripcion" name="Descripcion" class="form-control"  rows="5" cols="40"><%=becTipo.getDescripcion()%></textarea>
			<br>
		</div>
			
		<div class="span3 col">
			<label for="Flag">Flag</label>			
			<input type="text" name="Flag" id="Flag" class="form-control" value="<%= becTipo.getFlag()%>"/>
			<br>
			<label for="Porcentaje">Porcentaje</label>					
			<input type="text" name="Porcentaje" id="Porcentaje" class="form-control" maxlength="3" value="<%=becTipo.getPorcentaje() %>" />
		    <br>
			<label for="Horas">Horas</label>
			<input type="text" name="Horas" id="Horas" class="form-control" maxlength="4" value="<%=becTipo.getHoras()%>" />
			<br>
			<label for="HorasPrepa">Horas Prepa</label>			
			<input type="text" name="HorasPrepa" id="HorasPrepa"  class="form-control"maxlength="4" value="<%=becTipo.getHorasPrepa()%>" />						
			<br>
		</div>
		<div class="span3 col">
			<label for="Acuerdo">Acuerdo</label>					
			<select  id="Acuerdo" name="Acuerdo" class="form-select">										
				<option value="A" <%if(becTipo.getAcuerdo().equals("A"))out.print("selected"); %>>Adicional</option>
				<option value="B" <%if(becTipo.getAcuerdo().equals("B"))out.print("selected"); %>>Básico</option>
				<option value="I" <%if(becTipo.getAcuerdo().equals("I"))out.print("selected"); %>>Institucional</option>
				<option value="E" <%if(becTipo.getAcuerdo().equals("E"))out.print("selected"); %>>Ind. Adicional</option>
				<option value="P" <%if(becTipo.getAcuerdo().equals("P"))out.print("selected"); %>>Preindustrial</option>
				<option value="M" <%if(becTipo.getAcuerdo().equals("M"))out.print("selected"); %>>Posgrado</option>
				<option value="O" <%if(becTipo.getAcuerdo().equals("O"))out.print("selected"); %>><spring:message code='aca.Otro'/></option>
			</select>
			<br>			 
			<label for="Importe">Importe</label>
			<input type="text" name="Importe" id="Importe" class="form-control" maxlength="11" value="<%=becTipo.getImporte()%>" />
			<br>
			<label for="Maximo">Máximo alumno</label>
			<input type="text" name="Maximo" id="Maximo"   class="form-control" maxlength="8" value="<%=becTipo.getMaximo()%>" />
			<br>
			<label for="Limite">Limite máximo</label>
			<input type="text" name="Limite" id="Limite" class="form-control" maxlength="14" value="<%=becTipo.getLimite()%>" />
		</div>			    
	</div>	
	<br>
	<div class="alert alert-info">
		<input type="button" value="Guardar" class="btn btn-success" onclick="javascript:Guardar();"/>
	</div>
	</form>
</div>
<script type="text/javascript">
	function Guardar() {
		if (document.frmBeca.Tipo.value != ""
				&& document.frmBeca.Nombre.value != ""
				&& document.frmBeca.Cuenta.value != ""
				&& document.frmBeca.Porcentaje.value != ""				
				&& document.frmBeca.Horas.value != ""
				&& document.frmBeca.HorasPrepa.value != ""
				&& document.frmBeca.Acuerdo.value != ""
				&& document.frmBeca.Importe.value != "") {
			document.frmBeca.submit();
		} else {
			alert("¡Completa todos los campos!");
		}
	}
	
</script>