<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.covid.spring.Covid"%>
<%@page import="aca.emp.spring.Empleado"%>
<%@page import="aca.vista.spring.Alumno"%>

<% String idJsp= "000";%>
<%@ include file="../body.jsp"%>
<%@ include file="../idioma.jsp"%>
<html>
<head>&nbsp;</head>	
<% 	
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	Covid encuesta 			= (Covid)request.getAttribute("encuesta");
	Alumno alumno			= (Alumno)request.getAttribute("alumno");
	Empleado empleado 		= (Empleado)request.getAttribute("empleado");
	boolean esEmpleado 		= (boolean)request.getAttribute("esEmpleado");
	String mensaje 			= (String)request.getAttribute("mensaje");
	String nombre			= esEmpleado?empleado.getNombre()+" "+empleado.getAppaterno()+" "+empleado.getApmaterno():alumno.getNombre()+" "+alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno();	
%>
<body>
<div class="container-fluid">
	<h2>Alerta sanitaria <small>( <%=codigoPersonal%> - <%=nombre%>)</small></h2>
	<hr>	
<%	if (!mensaje.equals("-")){%>
	<div class="alert alert-info"><%=mensaje%></div>
<% 	}%>	
	<form name="frmAlerta" action="grabarEncuesta" method="post">
	<input name="CodigoPersonal" type="hidden" value="<%=encuesta.getCodigoPersonal()%>">
	<div class="alert alert-info"><h4>Si tienes alguna de las siguientes enfermedades, selecci&oacute;nala haciendo clic en el casillero que corresponde.</h4></div>
	<div class="row" style="margin: 10px 10px 0 10px;">
		<div class="span6">			
			<fieldset>
				<input type="checkbox" name="Hipertension" value="S" <%=encuesta.getHipertension().equals("S")?"checked":""%>/>
				<label><b>Hipertensi&oacute;n arterial</b></label>											
			</fieldset>
			<fieldset>
				<input type="checkbox" name="Diabetes" value="S" <%=encuesta.getDiabetes().equals("S")?"checked":""%>/>
				<label ><b>Diabetes Mellitus</b></label>				
			</fieldset>	
			<fieldset>
				<input type="checkbox" name="Corazon" value="S" <%=encuesta.getCorazon().equals("S")?"checked":""%>/>
				<label ><b>Enfermedades del coraz&oacute;n</b></label>				
			</fieldset>
			<fieldset>
				<input type="checkbox" name="Pulmon" value="S" <%=encuesta.getPulmon().equals("S")?"checked":""%>/>
				<label ><b>Enfermedades del pulm&oacute;n<small>( asma, bronquitis cr&oacute;nica, otra )</small></b></label>				
			</fieldset>
			<fieldset>
				<input type="checkbox" name="Cancer" value="S" <%=encuesta.getCancer().equals("S")?"checked":""%>/>
				<label ><b>C&aacute;ncer</b></label>				
			</fieldset>
			<fieldset>
				<input type="checkbox" name="Obesidad" value="S" <%=encuesta.getObesidad().equals("S")?"checked":""%>/>
				<label ><b>Obesidad</b></label>				
			</fieldset>
			<fieldset>
				<input type="checkbox" name="Estres" value="S" <%=encuesta.getEstres().equals("S")?"checked":""%>/>
				<label ><b>Estr&eacute;s</b></label>				
			</fieldset>
			<fieldset>
				<input type="checkbox" name="Depresion" value="S" <%=encuesta.getDepresion().equals("S")?"checked":""%>/>
				<label ><b>Depresi&oacute;n</b></label>				
			</fieldset>
		</div>			
	</div>
	<br>
	<div class="alert alert-info"><h4>Seguridad social con que cuentas</h4></div>	
	<div class="row" style="margin: 10px 10px 0 10px;">
		<div class="span6">			
			<fieldset>
				<input type="checkbox" name="Hlc" value="S" <%=encuesta.getHlc().equals("S")?"checked":""%>/>
				<label ><b>Hospital La Carlota</b></label>				
			</fieldset>
			<fieldset>
				<input type="checkbox" name="Pase" value="S" <%=encuesta.getPase().equals("S")?"checked":""%>/>
				<label ><b>Seguro estudiante</b></label>				
			</fieldset>	
			<fieldset>
				<input type="checkbox" name="Imss" value="S" <%=encuesta.getImss().equals("S")?"checked":""%>/>
				<label><b>IMSS</b></label>											
			</fieldset>
			<fieldset>
				<input type="checkbox" name="Isste" value="S" <%=encuesta.getIsste().equals("S")?"checked":""%>/>
				<label ><b>ISSSTE</b></label>				
			</fieldset>
			<fieldset>
				<input type="checkbox" name="Privado" value="S" <%=encuesta.getPrivado().equals("S")?"checked":""%>/>
				<label ><b>Privado</b></label>				
			</fieldset>
			<fieldset>
				<input type="checkbox" name="Otro" value="S" <%=encuesta.getOtro().equals("S")?"checked":""%>/>
				<label ><b>Otro</b></label>				
			</fieldset>
			<fieldset>
				<input type="checkbox" name="Ninguno" value="S" <%=encuesta.getNinguno().equals("S")?"checked":""%>/>
				<label ><b>Ninguno</b></label>				
			</fieldset>			
		</div>
	</div>		
	<div class="alert alert-info">
		<a href="javascript:Grabar()" class="btn btn-primary">Grabar</a>
	</div>
	</form>
</div>		
</body>
<script>
	function Grabar(){
		document.frmAlerta.submit();
	}
</script>
</html>