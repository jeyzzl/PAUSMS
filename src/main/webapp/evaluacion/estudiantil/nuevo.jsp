<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.edo.spring.EdoPeriodo"%>
<%@page import="aca.edo.spring.Edo"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	String edoId 		= request.getParameter("EdoId") == null ? "0" : request.getParameter("EdoId");
	Edo edo				= (Edo) request.getAttribute("edo");
	String mensaje 		= (String) request.getAttribute("mensaje");

	List<EdoPeriodo> lisEdoPeriodo	= (List<EdoPeriodo>) request.getAttribute("lisEdoPeriodo");   	
%>
<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
	<script type="text/javascript">		
		function checkSize(obj, event){
			if(event.keyCode != 8)
				if(obj.value.length > 1000)
					return false;
			return true;
		}
	</script>
</head>
<body>
<div class="container-fluid">
	<h2>Evaluación docente</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="cuestionarios">Regresar</a>
	</div>
<%  if(mensaje.equals("1")){%>
		<font size='3' color='blue'><b>Se guard&oacute; correctamente la Evaluaci&oacute;n!!!</b></font>
<%  }else if(mensaje.equals("2")){%>
		<font size='3' color='red'><b>Ocurri&oacute; un error al guardar. Int&eacute;ntelo de nuevo!</b></font>
<%  }else if(mensaje.equals("3")){%>
		<font size='3' color='blue'><b>Se modific&oacute; correctamente la Evaluaci&oacute;n!!!</b></font>
<%  }else if(mensaje.equals("4")){%>
		<font size='3' color='red'><b>Ocurri&oacute; un error al modificar. Int&eacute;ntelo de nuevo!</b></font>
<%  }%>
	<form id="forma" name="forma" action="grabar" method="post" style="margin: 20px 0px 0 15px;">
		<input type="hidden" id="edoId" name="EdoId" value="<%=edoId%>" />
		<div class="row">
			<div class="col-4">
				<label for="periodoId">Periodo</label>			
				<select id="periodoId" name="periodoId" class="form-select" style="width:350px;">
<%				for(EdoPeriodo edoPeriodo : lisEdoPeriodo){%>
					<option value="<%=edoPeriodo.getPeriodoId() %>"<%=edoPeriodo.getPeriodoId().equals(edo.getPeriodoId())?" Selected":"" %>><%=edoPeriodo.getPeriodoNombre() %></option>
<%				}%>
				</select>
				<br>
				<label for="nombre">*<spring:message code="aca.Nombre"/> <font color="#AE2113"></font></label>					
				<input type="text" class="form-control" id="nombre" name="nombre" value="<%=edo.getNombre() %>" maxlength="100" size="30" required="required"/>
				<br>
				<label for="fInicio">*Fecha Inicio (DD/MM/AAAA):<font color="#AE2113"></font></label>
				<input type="text" class="form-control" data-date-format="dd/mm/yyyy" id="fInicio" name="fInicio" value="<%=edo.getFInicio() %>" size="12" maxlength="10" required="required"/>				
				<br>
				<label for="fFinal">*Fecha Final ((DD/MM/AAAA))<font color="#AE2113"></font></label>					
				<input id="fFinal" name="fFinal" class="form-control" data-date-format="dd/mm/yyyy" value="<%=edo.getFFinal() %>" size="12" maxlength="10" required="required"/>				
				<br>
				<label for="modalidad">*Modalidades( Todas: -0- ): <font color="#AE2113"></font></label>
				<input id="modalidad" name="modalidad" class="form-control" value="<%=edo.getModalidad() %>" size="12" maxlength="100" />
			</div>
			<div class="span3">
				<label for="cargas">*Cargas( Todas: -0- ):<font color="#AE2113"></font></label>
				<input id="cargas" name="cargas" class="form-control" value="<%=edo.getCargas() %>" size="12" maxlength="70" />
				<br>
				<label for="tipoEncuesta"><spring:message code="aca.Tipo"/>: <font color="#AE2113">*</font></label>					
				<select name="tipoEncuesta" id="tipoEncuesta" class="form-select">
				  	<option value="P" <%if(edo.getTipoEncuesta().equals("P")){out.print("selected");} %> ><spring:message code="aca.Presencial"/></option>
				  	<option value="V" <%if(edo.getTipoEncuesta().equals("V")){out.print("selected");} %> ><spring:message code="aca.Virtual"/></option>
				</select>	
				<br>
				<label for="mensaje">Instrucciones: <font color="#AE2113">*</font></label>
				<textarea id="mensaje" name="mensaje" class="form-control" cols="40" rows="5" onkeypress="return checkSize(this, event);" ><%=edo.getEncabezado()%></textarea>
			</div>
		</div>
		<br>
		<div class="alert alert-info">
			<button class="btn btn-primary" type="submit">Guardar</button>
		</div>
	</form>
	</div>
</body>
<script>
	jQuery('#fInicio').datepicker();
	jQuery('#fFinal').datepicker();
</script>
