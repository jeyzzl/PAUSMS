<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.edo.spring.EdoAlumnoPreg"%>
<%@page import="aca.edo.spring.EdoArea"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%    
    String mensaje 					= (String) request.getAttribute("mensaje");    
    EdoAlumnoPreg edoAlumnoPreg		= (EdoAlumnoPreg) request.getAttribute("edoAlumnoPreg");    
    List<EdoArea> lisAreas 			= (List<EdoArea>) request.getAttribute("lisAreas");	
%>
<head>
</head>
<body>
<div class="container-fluid">
	<h2>Editar Pregunta</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="preguntas?EdoId=<%=edoAlumnoPreg.getEdoId()%>">Regresar</a>
	</div>
<%  if(mensaje.equals("1")){%>
		<font size='3' color='blue'><b>Se guard&oacute; correctamente la Pregunta!!!</b></font>
<%  }else if(mensaje.equals("2")){%>
		<font size='3' color='red'><b>Ocurri&oacute; un error al guardar. Int&eacute;ntelo de nuevo!</b></font>
<%  }else if(mensaje.equals("3")){%>
		<font size='3' color='blue'><b>Se modific&oacute; correctamente la Pregunta!!!</b></font>
<%  }else if(mensaje.equals("4")){%>
		<font size='3' color='red'><b>Ocurri&oacute; un error al modificar. Int&eacute;ntelo de nuevo!</b></font>
<%  }%>
	<form id="forma" name="forma" action="grabaPregunta" method="post">
		<input type="hidden" name="EdoId" value="<%=edoAlumnoPreg.getEdoId()%>"/>
		<input type="hidden" name="PreguntaId" value="<%=edoAlumnoPreg.getPreguntaId()%>"/>
		<div class="form-group row" style="margin: 10px 0px 0 10px;">
			<div class="col-sm-12">
				Pregunta<input class="form-control" id="pregunta" name="pregunta" value="<%=edoAlumnoPreg.getPregunta() %>" required="required"/>
			</div>
		</div>
		<div class="form-group row" style="margin: 10px 0px 0 10px;">
			<div class="col-sm-3">
				Orden <input class="form-control" id="orden" name="orden" value="<%=edoAlumnoPreg.getOrden() %>" maxlength="2" size="2" required="required"/>
			</div>
			<div class="col-sm-3">
				Tipo
				<select class="form-control" id="tipo" name="tipo">
					<option value="O" <%=edoAlumnoPreg.getTipo().equals("O") ? "selected" : ""%>>Opci&oacute;n &uacute;nica</option>
					<option value="D" <%=edoAlumnoPreg.getTipo().equals("D") ? "selected" : ""%>>Directa</option>
				</select>
			</div>
			<div class="col-sm-4">
				Área
				<select class="form-control" id="areaId" name="areaId">
<%				for(EdoArea edoArea : lisAreas){%>
					<option value="<%=edoArea.getAreaId() %>"<%=edoArea.getAreaId().equals(edoAlumnoPreg.getAreaId()) ? " selected" : ""%>><%=edoArea.getAreaNombre() %></option>
<%			 	}%>					
				</select>
			</div>
			<div class="col-sm-2">
				Seccion
				<select class="form-control" id="seccion" name="seccion">
					<option value="A" <%=edoAlumnoPreg.getSeccion().equals("A") ? "selected" : "" %>>A</option>
					<option value="B" <%=edoAlumnoPreg.getSeccion().equals("B") ? "selected" : "" %>>B</option>
				</select>
			</div>
		</div>
		<br>
		<div class="alert alert-info">		
				<button class="btn btn-primary" type="submit">Guardar</button>			
		</div>
	</form>
</div>
</body>
