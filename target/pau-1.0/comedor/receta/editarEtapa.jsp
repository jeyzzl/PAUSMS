<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.saum.spring.SaumEtapa"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<html>
<script>
	function Grabar(){
		document.frmEtapa.Accion.value = "1";
		document.frmEtapa.submit();
	}
	function Modificar(){
		document.frmEtapa.Accion.value = "2";
		document.frmEtapa.submit();
	}
</script>	
<% 	
	SaumEtapa etapa 	= (SaumEtapa) request.getAttribute("etapa");
	String recetaId		= request.getParameter("RecetaId")==null?"0":request.getParameter("RecetaId");
	String accion		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
%>
<body>
<div class="container-fluid">
	<h1>Editar Etapa</h1>
	<div class="alert alert-info">
		<a href="etapas?RecetaId=<%=recetaId%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a> &nbsp;		
	</div>
	<form id="frmEtapa" name="frmEtapa" method="post" action="grabarEtapa">
	<input type="hidden" name="Accion" value="<%=accion%>"/>
	<input type="hidden" name="RecetaId" value="<%=recetaId%>"/>
	<div class="row">
		<div class="span3">
			<fieldset>
				<label for="Clave">Clave</label>
				<input type="text" class="input input-mini" name="Id" id="Id" value="<%=etapa.getId()%>" readonly/>
			</fieldset>
			<br>
			<fieldset>
				<label for="Nombre">Nombre</label>
				<input type="text" name="Nombre" id="Nombre" value="<%=etapa.getNombre()%>" required/>
			</fieldset>			
			<br>
			<fieldset>
				<label for="Procedimiento">Procedimiento</label>
				<textarea  name="Procedimiento" id="Procedimiento" cols="70" rows="7"><%=etapa.getProcedimiento()%></textarea>
			</fieldset>						
		</div>
	</div>		
	<br>				
	<div class="alert alert-info">	
<%	if (accion.equals("0")){%>
		<a onclick="javascript:Grabar();" class="btn btn-primary"><i class="fas fa-check"></i> Grabar</a>
<% 	}else{ %>
		<a onclick="javascript:Modificar();" class="btn btn-primary"><i class="fas fa-check"></i> Modificar</a>
<%	}%>
	</div>
	</form>
</div>		
</body>
</html>
