<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.saum.spring.SaumMateria"%>
<%@page import="aca.saum.spring.SaumMateriaDao"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<html>
<script>
	function Grabar(){
		document.frmMateria.submit();
	}
</script>	
<% 	
	SaumMateria materia = (SaumMateria) request.getAttribute("materia");
%>
<body>
<div class="container-fluid">
	<h2>Materia prima<small class="text-muted fs-4"> ( <%=materia.getNombre()%> )</small></h2>
	<div class="alert alert-info">
		<a href="materia" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a> &nbsp;		
	</div>
	<form id="frmMateria" name="frmMateria" method="post" action="grabar">
<%-- 	<input type="hidden" name="Accion" value="<%=0%>"/> --%>
	<div class="row">
		<div class="col">
			<fieldset>
				<label for="Clave">Clave</label>
				<input type="text" class="input input-mini" name="Id" id="Id" value="<%=materia.getId()%>" readonly/>
			</fieldset>
			<br>
			<fieldset>
				<label for="Nombre">Nombre</label>
				<input type="text" class="input input-large" name="Nombre" id="Nombre" value="<%=materia.getNombre()%>" required/>
			</fieldset>
			<br>
			<fieldset>
				<label for="Energia">Energía (kcal)</label>
				<input type="text" class="input input-medium" name="Energia" id="Energia" value="<%=materia.getEnergia()%>" required/>
			</fieldset>
			<br>
			<fieldset>
				<label for="Humedad">Humedad (%)</label>
				<input type="text" class="input input-medium" name="Humedad" id="Humedad" value="<%=materia.getHumedad()%>" required/>
			</fieldset>
			<br>
			<fieldset>
				<label for="Fibra">Fibra dietética (g)</label>
				<input type="text" class="input input-medium" name="Fibra" id="Fibra" value="<%=materia.getFibra()%>" required/>
			</fieldset>
			<br>
			<fieldset>
				<label for="Carbohidrato">Carbohidrato (g)</label>
				<input type="text" class="input input-medium" name="Carbohidrato" id="Carbohidrato" value="<%=materia.getCarbohidrato()%>" required/>
			</fieldset>
		</div>
		<div class="col">
			<fieldset>
				<label for="Proteina">Proteína (g)</label>
				<input type="text" class="input input-medium" name="Proteina" id="Proteina" value="<%=materia.getProteina()%>" required/>
			</fieldset>
			<br>
			<fieldset>
				<label for="Lipido">Lípido (g)</label>
				<input type="text" class="input input-medium" name="Lipido" id="Lipido" value="<%=materia.getLipido()%>" required/>
			</fieldset>
			<br>
			<fieldset>
				<label for="Ceniza">Ceniza</label>
				<input type="text" class="input input-medium" name="Ceniza" id="Ceniza" value="<%=materia.getCeniza()%>" required/>
			</fieldset>
			<br>
			<fieldset>
				<label for="Saturado">Saturado (g)</label>
				<input type="text" class="input input-medium" name="Saturado" id="Saturado" value="<%=materia.getSaturado()%>" required/>
			</fieldset>
			<br>
			<fieldset>
				<label for="Mono">Monoinsaturado (g)</label>
				<input type="text" class="input input-medium" name="Mono" id="Mono" value="<%=materia.getMono()%>" required/>
			</fieldset>
			<br>
			<fieldset>
				<label for="Poli">Poliinsaturado (g)</label>
				<input type="text" class="input input-medium" name="Poli" id="Poli" value="<%=materia.getPoli()%>" required/>
			</fieldset>
		</div>
		<div class="col">
			<fieldset>
				<label for="Colesterol">Colesterol (mg)</label>
				<input type="text" class="input input-medium" name="Colesterol" id="Colesterol" value="<%=materia.getColesterol()%>" required/>
			</fieldset>
			<br>
			<fieldset>
				<label for="Calcio">Calcio (mg)</label>
				<input type="text" class="input input-medium" name="Calcio" id="Calcio" value="<%=materia.getCalcio()%>" required/>
			</fieldset>
			<br>
			<fieldset>
				<label for="Fosforo">Fósforo (mg)</label>
				<input type="text" class="input input-medium" name="Fosforo" id="Fosforo" value="<%=materia.getFosforo()%>" required/>
			</fieldset>
			<br>
			<fieldset>
				<label for="Hierro">Hierro (mg)</label>
				<input type="text" class="input input-medium" name="Hierro" id="Hierro" value="<%=materia.getHierro()%>" required/>
			</fieldset>
			<br>
			<fieldset>
				<label for="Magnesio">Magnesio (mg)</label>
				<input type="text" class="input input-medium" name="Magnesio" id="Magnesio" value="<%=materia.getMagnesio()%>" required/>
			</fieldset>
			<br>
			<fieldset>
				<label for="Selenio">Selenio (mcg)</label>
				<input type="text" class="input input-medium" name="Selenio" id="Selenio" value="<%=materia.getSelenio()%>" required/>
			</fieldset>
		</div>
		<div class="col">
			<fieldset>
				<label for="Sodio">Sodio (mg)</label>
				<input type="text" class="input input-medium" name="Sodio" id="Sodio" value="<%=materia.getSodio()%>" required/>
			</fieldset>
			<br>
			<fieldset>
				<label for="Potasio">Potasio (mg)</label>
				<input type="text" class="input input-medium" name="Potasio" id="Potasio" value="<%=materia.getPotasio()%>" required/>
			</fieldset>
			<br>
			<fieldset>
				<label for="Cinc">Cinc (mg)</label>
				<input type="text" class="input input-medium" name="Cinc" id="Cinc" value="<%=materia.getCinc()%>" required/>
			</fieldset>
			<br>
			<fieldset>
				<label for="Vitamina">Vitamina A (mcg)</label>
				<input type="text" class="input input-medium" name="Vitamina" id="Vitamina" value="<%=materia.getVitaminaa()%>" required/>
			</fieldset>
			<br>
			<fieldset>
				<label for="Ascorbico">Ácido ascórbico (mg)</label>
				<input type="text" class="input input-medium" name="Ascorbico" id="Ascorbico" value="<%=materia.getAscorbico()%>" required/>
			</fieldset>
			<br>
			<fieldset>
				<label for="Tiamina">Tiamina (mg)</label>
				<input type="text" class="input input-medium" name="Tiamina" id="Tiamina" value="<%=materia.getTiamina()%>" required/>
			</fieldset>
		</div>
		<div class="col">
			<fieldset>
				<label for="Ribo">Riboflavina (mg)</label>
				<input type="text" class="input input-medium" name="Ribo" id="Ribo" value="<%=materia.getRibo()%>" required/>
			</fieldset>
			<br>
			<fieldset>
				<label for="Niacina">Niacina (mg)</label>
				<input type="text" class="input input-medium" name="Niacina" id="Niacina" value="<%=materia.getNiacina()%>" required/>
			</fieldset>
			<br>
			<fieldset>
				<label for="Piridoxina">Piridoxina (mg)</label>
				<input type="text" class="input input-medium" name="Piridoxina" id="Piridoxina" value="<%=materia.getPiridoxina()%>" required/>
			</fieldset>
			<br>
			<fieldset>
				<label for="Folico">Ácido fólico (mcg)</label>
				<input type="text" class="input input-medium" name="Folico" id="Folico" value="<%=materia.getFolico()%>" required/>
			</fieldset>
			<br>
			<fieldset>
				<label for="Cobalamina">Cobalamina (mcg)</label>
				<input type="text" class="input input-medium" name="Cobalamina" id="Cobalamina" value="<%=materia.getCobalamina()%>" required/>
			</fieldset>
		</div>
	</div>
	<br>			
	<div class="alert alert-info">	
		<a onclick="javascript:Grabar();" class="btn btn-primary"><i class="fas fa-check"></i> Grabar</a>
	</div>
	</form>
</div>		
</body>
</html>
