<%@ page import="aca.catalogo.spring.CatNivel"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<html>
	<head>
		<script type="text/javascript">			
			function Grabar() {
				if (document.frmnivel.NivelId.value != ""
						&& document.frmnivel.NombreNivel != "") {					
					document.frmnivel.submit();
				} else {
					alert("<spring:message code='aca.JSCompletar'/>");
				}
			}			
		</script>
	</head>
<%
	// Declaracion de variables	
	CatNivel nivel 		= (CatNivel) request.getAttribute("nivel"); 
	String mensaje 		= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");	   
%>
<body>
<div class="container-fluid">
	<h1><spring:message code="catalogos.niveles.Titulo2"/></h1>
	<form action="grabarNivel" method="post" name="frmnivel" target="_self">	
	<div class="alert alert-info">
		<a class="btn btn-primary" href="nivel"><i class="fas fa-arrow-left"></i></a>
	</div>
	<div class="form-group">
		<label><strong><spring:message code="aca.Clave"/></strong></label>
		<input class="input input-mini form-control" name="NivelId" type="text" id="NivelId" size="3" maxlength="3" value="<%=nivel.getNivelId()%>" readonly>
		<br>
		<label><strong><spring:message code="catalogos.niveles.Niveles"/></strong></label>
		<input class="input input-large form-control" name="NombreNivel" type="text" id="NombreNivel" size="30" maxlength="30" value="<%=nivel.getNombreNivel()%>">
		<br>
	</div>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Guardar"/></a> &nbsp;
<% if (!mensaje.equals("-")){ out.print(mensaje);}%>		
	</div>	
    </form>
</div>
</body>
</html>