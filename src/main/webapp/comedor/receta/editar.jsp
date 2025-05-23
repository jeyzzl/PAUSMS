<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.saum.spring.SaumReceta"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<html>
<script>
	
	function Grabar(){
		document.frmReceta.Accion.value = "1";
		document.frmReceta.submit();
	}
	
	function Modificar(){
		document.frmReceta.Accion.value = "2";
		document.frmReceta.submit();
	}	
</script>	
<% 	
	SaumReceta receta 	= (SaumReceta) request.getAttribute("receta");
	String accion		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
%>
<body>
<div class="container-fluid">
	<h1>Receta</h1>
	<div class="alert alert-info">
		<a href="lista" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a> &nbsp;		
	</div>
	<form id="frmReceta" name="frmReceta" method="post" action="grabar">
	<input type="hidden" name="Accion" value="<%=accion%>"/>
	<div class="row">
		<div class="col">
			<fieldset>
				<label for="Clave">Clave</label>
				<input type="text" class="input input-mini" name="Id" id="Id" value="<%=receta.getId()%>" readonly/>
			</fieldset>
			<br>
			<fieldset>
				<label for="Nombre">Nombre</label>
				<input type="text" name="Nombre" id="Nombre" value="<%=receta.getNombre()%>" required/>
			</fieldset>			
			<br>
			<fieldset>
				<label for="Rendimiento">Rendimiento</label>
				<input type="text" name="Rendimiento" id="Rendimiento" value="<%=receta.getRendimiento()%>" required/>
			</fieldset>			
			<br>
			<fieldset>
				<label for="Calorias">Calorías</label>
				<input type="text" name="Calorias" id="Calorias" value="<%=receta.getCalorias()%>" />
			</fieldset>
			<br>
			<fieldset>
				<label for="Carbohidratos">Carbohidratos</label>
				<input type="text" name="Carbohidratos" id="Carbohidratos" value="<%=receta.getCarbohidratos()%>" />
			</fieldset>
			<br>			
		</div>
		<div class="col">
			<fieldset>
				<label for="Colesterol">Colesterol</label>
				<input type="text" name="Colesterol" id="Colesterol" value="<%=receta.getColesterol()%>" />
			</fieldset>
			<br>
			<fieldset>
				<label for="Color">Color</label>
				<input type="text" name="Color" id="Color" value="<%=receta.getColor()%>" />
			</fieldset>
			<br>
			<fieldset>
				<label for="Fibra">Fibra</label>
				<input type="text" name="Fibra" id="Fibra" value="<%=receta.getFibra()%>" />
			</fieldset>			
			<br>
			<fieldset>
				<label for="Forma">Forma</label>
				<input type="text" name="Forma" id="Forma" value="<%=receta.getForma()%>" />
			</fieldset>
			<br>
			<fieldset>
				<label for="Grasa">Grasa</label>
				<input type="text" name="Grasa" id="Grasa" value="<%=receta.getGrasa()%>" />
			</fieldset>			
		</div>
		<div class="col">
			<fieldset>
				<label for="Porcion">Porción</label>
				<input type="text" name="Porcion" id="Porcion" value="<%=receta.getPorcion()%>" />
			</fieldset>
			<br>
			<fieldset>
				<label for="Proteinas">Proteínas</label>
				<input type="text" name="Proteinas" id="Proteinas" value="<%=receta.getProteinas()%>" />
			</fieldset>
			<br>			
			<fieldset>
				<label for="Sabor">Sabor</label>
				<input type="text" name="Sabor" id="Sabor" value="<%=receta.getSabor()%>" />
			</fieldset>
			<br>
			<fieldset>
				<label for="Sodio">Sodio</label>
				<input type="text" name="Sodio" id="Sodio" value="<%=receta.getSodio()%>" />
			</fieldset>
			<br>
			<fieldset>
				<label for="Temperatura">Temperatura</label>
				<input type="text" name="Temperatura" id="Temperatura" value="<%=receta.getTemperatura()%>" />
			</fieldset>
		</div>
		<div class="col">
			<fieldset>
				<label for="Textura">Textura</label>
				<input type="text" name="Textura" id="Textura" value="<%=receta.getTextura()%>" />
			</fieldset>
			<br>
			<fieldset>
				<label for="Tiempo">Tiempo</label>
				<input type="text" name="Tiempo" id="Tiempo" value="<%=receta.getTiempo()%>" />
			</fieldset>			
			<br>
			<fieldset>
				<label for="TipoPlato">Tipo de Plato</label>
				<input type="text" name="TipoPlato" id="TipoPlato" value="<%=receta.getTipoPlato()%>" />
			</fieldset>
		</div>
	</div>				
	<br>			
	<div class="alert alert-info">
<%	if (accion.equals("0")){%>
		<a onclick="javascript:Grabar();" class="btn btn-primary"><i class="fas fa-check"></i> Grabar</a>
<% 	}else{ %>
		<a onclick="javascript:Modificar();" class="btn btn-primary"><i class="fas fa-check"></i> Modificar</a>
		<a href="subirImagen?Id=<%=receta.getId()%>" class="btn btn-primary"><i class="fas fa-camera"></i> Subir Imagen</a>
<%	}%>		
	</div>
	</form>
</div>		
</body>
</html>
