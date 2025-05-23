<%@ page import= "aca.cert.spring.CertPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
</head>
<%
	String facultad		= (String) request.getAttribute("facultad");
	String planId		= (String) request.getAttribute("planId");
	String metodo		= (String) request.getAttribute("metodo");	
	String mensaje		= (String) request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	CertPlan certPlan	= (CertPlan) request.getAttribute("certPlan");
	if(mensaje.equals("1")){
%>
		<table style="margin: 0 auto;">
			<tr><td><font size="3" color="red"><b>Error!!!</b> al guardar el plan. Inténtelo de nuevo!</font></td></tr>
		</table>
<%
	}
	if(mensaje.equals("2")){
%>
		<table style="margin: 0 auto;">
			<tr><td><font size="3" color="red"><b>Error!!!</b> al modificar el plan. Inténtelo de nuevo!</font></td></tr>
		</table>
<%
	}
%>
<head>
	<script type="text/javascript">
		function revisa(){
			var f = document.forma;
			if(f.plan.value.length==8){
				if(f.numCursos.value != "" && f.semanas.value != "" && f.componente.value != ""){				  
					return true;
				}else{
					alert("Los campos con * deben ser llenados para poder guardar");
				}
			}else{
				alert("El plan debe ser de 8 caracteres");
			}
			return false;
		}
	</script>
</head>
<!-- style="border-color:black;  border-width: 1px;  border-style: solid;" -->
	<div class="container-fluid">
	<h2>Edita Plan<small class="text-muted fs-4">(<%=facultad%>)</small></h2>
	<div class="alert alert-info d-flex align-items-center">
		<a href="carrera_plan?facultad=<%=facultad%>" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>
	</div>
<%	if(!mensaje.equals("-")){ %>
	<div class="alert alert-info"><%=mensaje%></div>
<%	}%>	
	<form id="forma" name="forma" action="grabar_plan?facultadId=<%=facultad%>" method="post">	
	
	<div class="row container-fluid"> 
		<div class="span4 col">
			<label for="plan"><spring:message code="aca.Plan"/>:</label>
<%				if(metodo.equals("Guardar")){%>
			<input type="text" class="text" id="plan" name="plan" value="" maxlength="8" size="8" />
<%				}else{%>
			<%=certPlan.getPlanId() %>
			<input type="hidden" id="plan" name="plan" value="<%=certPlan.getPlanId() %>" />
<%				}%>
			<br><br>
			<label for="rvoe"><spring:message code="aca.RVOE"/>:</label>	
			<input type="text" class="form-control" id="rvoe" name="rvoe" value="<%=certPlan.getRvoe() %>" maxlength="30" size="30" />
			<br><br>
			<label for="catalogos.extension.Referente">F. Retro:</label>			
			<input type="text" class="form-control" name="fechaRetro" data-date-format="dd/mm/yyyy" id="fechaRetro" value="<%=certPlan.getFechaRetro() %>" size="12"/>
			DD/MM/YYYY
			<br><br>
			<label for="facultad"><spring:message code="aca.Facultad"/></label>					
			<input type="text" class="form-control" id="facultad" name="facultad" value="<%=certPlan.getFacultad() %>" maxlength="100" size="30" />
			<br><br>
			<label for="facultad"><spring:message code="aca.Carrera"/></label>
			<input type="text" class="form-control" id="carrera" name="carrera" value="<%=certPlan.getCarrera() %>" maxlength="100" size="30" />
			<br><br>
			<label for="numCursos">Número de Cursos<b><font color="#AE2113"> *</font></label>
			<input type="text" class="form-control" id="numCursos" name="numCursos" value="<%=certPlan.getNumCursos() %>" maxlength="3" size="3" />
			<br><br>
		</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<div class="span4 col">
			<label for="semanas">Número de Semanas<b><font color="#AE2113"> *</font></label>	
			<input type="text" class="form-control" id="semanas" style="width:120px" name="semanas" value="<%=certPlan.getSemanas() %>" maxlength="2" size="2" />
			<br><br>
			<label for="tInicial">Parrafo Inicial</label>			
			<textarea id="tInicial" name="tInicial" class="form-control" style="width:120px"  rows="4" cols="30" style="color:black;"><%=certPlan.gettInicial().trim() %></textarea>

			<label for="tFinal">Parrafo Final</label>					
			<textarea id="tFinal" name="tFinal" class="form-control"  rows="4" cols="30" tabindex="0"><%=certPlan.gettFinal().trim() %></textarea>
			<br>
			<label for="nota"><spring:message code="aca.Nota"/></label>
			<input type="text" class="form-control" id="nota" name="nota" value="<%=certPlan.getNota() %>" maxlength="300" size="30" />
			<br><br>
		</div>
		<div class="span4 col">
			<label for="pie">Pie de Página</label>
			<input type="text" class="form-control" id="pie" name="pie" value="<%=certPlan.getPie() %>" maxlength="200" size="30" />
			<br><br>
			<label for="curso">¿Imprime Curso Escolar?</label>	
			<input type="checkbox" id="curso" name="curso" value="S" <%=certPlan.getCurso().equals("S")?"Checked ":"" %>/>
			<br><br>
			<label for="clave">¿Imprime Clave de la Materia?</label>			
			<input type="checkbox" id="clave" name="clave"  value="S" <%=certPlan.getClave().equals("S")?"Checked ":"" %>/>
			<br><br>
			<label for="fst">¿Imprime valor titulo1?</label>					
			<input type="checkbox" id="fst" name="fst"  value="S" <%=certPlan.getFst().equals("S")?"Checked ":"" %>/>
			<br><br>
			<label for="fsp">¿Imprime valor titulo2?</label>
			<input type="checkbox" id="fsp" name="fsp"  value="S" <%=certPlan.getFsp().equals("S")?"Checked ":"" %>/>
			<br><br>
			<label for="fsp">Número de ciclos<br />en los que se lleva componente<b><font color="#AE2113"> *</font></label>
			<input type="text" class="form-control" id="componente" name="componente" value="<%=certPlan.getComponente() %>" maxlength="2" size="2" />
						"0" significa que no lleva componente
		</div>
		
		 
		<div class="span4 col">
			<label for="titulo1">Titulo 1</label>
			<select name="titulo1"  class="form-select"id="titulo1">
				<option value="HTS">HTS</option>
				<option value="FST" <%if(certPlan.getTitulo1().equals("FST"))out.print("selected");%>>FST</option>
				<option value="HSS" <%if(certPlan.getTitulo1().equals("HSS"))out.print("selected");%>>HSS</option>
				<option value="HFD" <%if(certPlan.getTitulo1().equals("HFD"))out.print("selected");%>>HFD</option>
			</select>
			<br><br>
			<label for="titulo2">Titulo 2</label>	
			<select name="titulo2" class="form-select"id="titulo1"> id="titulo2">
				<option value="HPS">HPS</option>
				<option value="HAS" <%if(certPlan.getTitulo2().equals("HAS"))out.print("selected");%>>HAS</option>
				<option value="HEI" <%if(certPlan.getTitulo2().equals("HEI"))out.print("selected");%>>HEI</option>
			</select>
			<br><br>
			<label for="titulo3">Titulo 3</label>			
			<select name="titulo3" id="titulo3" class="form-select"id="titulo1">>
				<option value="CRS">CRS</option>
				<option value="THS" <%if(certPlan.getTitulo3().equals("THS"))out.print("selected");%>>THS</option>
				<option value="HORAS" <%if(certPlan.getTitulo3().equals("HORAS"))out.print("selected");%>>HORAS</option>
			</select>
			<br><br>
		</div>
		
	<div class="alert alert-info">
		<input class="btn btn-primary"type="submit" value="<%=metodo%>" onclick="return revisa();" />
	</div>
	</div>
	</div>
	</form>
<script>
	jQuery('#fechaRetro').datepicker();
</script>
