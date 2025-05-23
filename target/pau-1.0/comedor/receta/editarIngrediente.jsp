<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.saum.spring.SaumIngrediente"%>
<%@page import="aca.saum.spring.SaumMateria"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<html>
<script>
	function Grabar(){
		document.frmIngrediente.Accion.value = "1";
		document.frmIngrediente.submit();
	}
	
	function Modificar(){
		document.frmIngrediente.Accion.value = "2";
		document.frmIngrediente.submit();
	}
</script>	
<% 	
	String accion			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String recetaId			= request.getParameter("RecetaId")==null?"0":request.getParameter("RecetaId");
	String etapaId			= request.getParameter("EtapaId")==null?"0":request.getParameter("EtapaId");
	String recetaNombre		= (String) request.getAttribute("recetaNombre");
	String etapaNombre		= (String) request.getAttribute("etapaNombre");
	
	ArrayList<SaumMateria> lisMaterias 	= (ArrayList<SaumMateria>) request.getAttribute("lisMaterias");
	
	SaumIngrediente ingrediente 	= (SaumIngrediente) request.getAttribute("ingrediente");	
%>
<body>
<div class="container-fluid">
	<h2>Ingrediente<small class="text-muted fs-4"> ( <%=recetaNombre%> - <%=etapaNombre%> )</small></h2>
	<div class="alert alert-info">
		<a href="ingredientes?RecetaId=<%=recetaId%>&EtapaId=<%=etapaId%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a> &nbsp; &nbsp;		
		<a href="etapas?RecetaId=<%=recetaId%>" class="btn btn-primary">Etapa</a>
	</div>
	<form id="frmIngrediente" name="frmIngrediente" method="post" action="grabarIngrediente">
	<input type="hidden" name="Accion" value="<%=accion%>"/>
	<input type="hidden" name="RecetaId" value="<%=recetaId%>"/>
	<input type="hidden" name="EtapaId" value="<%=etapaId%>"/>
	<div class="row">
		<div class="span3">
			<fieldset>
				<label for="IngredienteId">Clave</label>
				<input type="text" class="input input-mini" name="IngredienteId" id="IngredienteId" value="<%=ingrediente.getId()%>" readonly/>
			</fieldset>
			<br>								
			<fieldset>
				<label for="MateriaId">Materia</label>
				<select name="MateriaId" class="chosen">
<% 			for (SaumMateria materia : lisMaterias){%>
					<option value="<%=materia.getId()%>" <%=materia.getId().equals(ingrediente.getMateriaId())?"selected":""%>><%=materia.getNombre()%></option>
<% 			}%>					
				</select>
			</fieldset>
			<br>
			<fieldset>
				<label for="Presentacion">Presentación</label>
				<input type="text" class="input" name="Presentacion" id="Presentacion" value="<%=ingrediente.getPresentacion()%>"/>
			</fieldset>
			<br>
			<fieldset>
				<label for="Cantidad">Cantidad</label>
				<input type="text" class="input input-mini" name="Cantidad" id="Cantidad" value="<%=ingrediente.getCantidad()%>"/>
			</fieldset>
			<br>
			<fieldset>
				<label for="UnidadMedida">Unidad de medida</label>
				<select name="UnidadMedida">
					<option value="g" <%=ingrediente.getUnidadMedida().equals("g")?"selected":""%>>Gramos</option>
					<option value="kg" <%=ingrediente.getUnidadMedida().equals("kg")?"selected":""%>>Kilos</option>
					<option value="ml" <%=ingrediente.getUnidadMedida().equals("ml")?"selected":""%>>Mililitros</option>
					<option value="l" <%=ingrediente.getUnidadMedida().equals("l")?"selected":""%>>Litros</option>
					<option value="pza" <%=ingrediente.getUnidadMedida().equals("pza")?"selected":""%>>Pieza</option>
				</select>
			</fieldset>
			<br>	
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
<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<script type="text/javascript" src="../../js/chosen/chosen.jquery.js"></script>
<script>
	jQuery(".chosen").chosen();
</script>
</html>
