<%@page import="java.text.*" %>
<%@page import="aca.alerta.spring.AlertaDatos"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="AlertaDatosU" class="aca.alerta.AlertaDatosUtil" scope="page" />
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<html>
<style>
	body{
		background: white;
	} 
	input[type=checkbox]{
	  /* Double-sized Checkboxes */
	  -ms-transform: scale(1.5); /* IE */
	  -moz-transform: scale(1.5); /* FF */
	  -webkit-transform: scale(1.5); /* Safari and Chrome */
	  -o-transform: scale(1.5); /* Opera */
	  padding: 10px;
	}
	
	.sintomas label, .sintomas input{
		display: inline-block;
		margin-right: 5px;
	}
</style>
<body>
<%
	String periodoId 			= (String) request.getAttribute("periodoId");
	String readonly				= request.getParameter("readonly")==null?"0":request.getParameter("readonly");
	String sintomas				= request.getParameter("valores");
	String matricula			= request.getParameter("codigoAlumno")==null?"":request.getParameter("codigoAlumno");
	String msj					= (String) request.getAttribute("msj");
	boolean existe 				= (boolean) request.getAttribute("existe");
	String periodoNombre		= (String) request.getAttribute("periodoNombre");
	AlertaDatos alertaDatos 	= (AlertaDatos) request.getAttribute("alertaDatos");		
%>
	<div class="container-fluid">
		<h2><spring:message code='aca.SistemaVigilanciaEpidemiologica'/><small class="text-muted h4">( <%=periodoNombre%> )</small></h2>		
		<div class="alert alert-info">
			<a href="datos" class="btn btn-success"><span class="icon icon-white fas fa-arrow-left" ></span>&nbsp;<spring:message code='aca.Anterior'/></a>&nbsp;&nbsp;
<%-- 			<a href="historial.jsp?matricula=<%=alertaDatos.getCodigoPersonal()%>&oeriodoId=<%=periodoId%>" class="btn btn-success"><spring:message code='aca.Siguiente'/>&nbsp;<span class="icon icon-white icon-arrow-right" ></span></a>&nbsp;&nbsp; --%>
		</div>
		
	<%if(existe){%>
		
	<%} %>
	<%if(msj.equals("1")){%>
		<div class="alert alert-success" role="alert">
			Guardado correctamente !!
		</div>
	<%} %>
		<form id="frmDatos" name="frmDatos" action="grabar" method="post">
			<input name="PeriodoId" type="hidden" value="<%=periodoId%>"/>
			
			<div class="row">
				<div class="span4 col" align="left">
					<div class="control-group">
						<label for="matricula"><spring:message code="aca.Matricula"/><span class="required-indicator"></span></label>
						<input type="text" required id="matricula" class="form-control" style="width:200px;" maxlength="7" size="6" name="matricula" value="<%=alertaDatos.getCodigoPersonal()%>" <%if(readonly.equals("1")){ %> readonly <%} %> />&nbsp;
						<img src="../../imagenes/loading.gif" alt="" style="display:none;" class="loading-bar" />
	            		<span class="result"></span>		            		
					</div>
					<div class="control-group">
						<label for="direccion"><spring:message code='aca.Direccion'/><span class="required-indicator"></span></label>
						<input type="text" size="40" class="form-control" style="width:400px;" required id="direccion" name="direccion" value="<%=alertaDatos.getDireccion()%>" size="" />
					</div><br>
					<div class="control-group">
						<label for="procedencia"><spring:message code='aca.Procedencia'/><span class="required-indicator"></span></label>
						<input type="text"  class="form-control" style="width:400px;" required id="procedencia" name="procedencia" value="<%=alertaDatos.getProcedencia()%>" />
					</div><br>
					<div class="control-group">
						<label for="correo"><spring:message code="aca.Correo"/></label>
						<input type="email"  class="form-control" style="width:400px;" required id="correo" name="correo" value="<%=alertaDatos.getCorreo()%>" />
					</div><br>
					<div class="control-group">
						<label for="celular"><spring:message code="aca.Celular"/></label>
						<input type="text" class="form-control" style="width:400px;" required id="celular" name="celular" value="<%=alertaDatos.getCelular()%>" />
					</div><br>
				</div>
				<div class="span4 col">
					<label><h4><spring:message code='aca.LugaresVisitadosUltimos15Dias'/></h4></label>
					<div class="control-group">
						<label for="lugar1"><spring:message code='aca.Lugar1'/><span class="required-indicator"></span></label>
						<input type="text" class="form-control" style="width:400px;" size="40" required id="lugar1" name="lugar1" value="<%=alertaDatos.getLugar1()%>" />
					</div><br>
					<div class="control-group">
						<label for="lugar2"><spring:message code='aca.Lugar2'/></label>
						<input type="text" class="form-control" style="width:400px;" size="40" id="lugar2" name="lugar2" value="<%=alertaDatos.getLugar2()%>" />
					</div><br>					
					<div class="control-group">
						<label for="estado"><spring:message code="aca.Estado"/><span class="required-indicator"></span></label>
						<select id="estado" name="estado" class="form-select" style="width:200px;">
							<option value="A" <%if(alertaDatos.getEstado().equals("A")){out.print("selected");} %>><spring:message code='aca.Autorizado'/></option> 
							<option value="P" <%if(alertaDatos.getEstado().equals("P")){out.print("selected");} %>><spring:message code='aca.Pendiente'/></option>
						</select>
					</div><br>
					<div class="control-group">
						<label for="referente"><spring:message code='aca.Referente'/><span class="required-indicator"></span></label>
						<select id="referente" name="referente" class="form-select" style="width:200px;">
							<option value="NINGUNO" <%if(alertaDatos.getReferente().equals("")){out.print("selected");} %> >NINGUNO</option>
							<option value="CARLOTA" <%if(alertaDatos.getReferente().equals("CARLOTA")){out.print("selected");} %>>HOSPITAL LA CARLOTA</option> 
							<option value="SSALUD" <%if(alertaDatos.getReferente().equals("SSALUD")){out.print("selected");} %>>SECRETARIA DE SALUD</option>
						</select>
					</div><br>
					<div class="control-group">
						<input type="checkbox" class="form-control" style="width:20px;" class="ningunSintoma" id="sintoma" name="sintoma" value="1" />
						<label><spring:message code='aca.NingunSintoma'/></label>
					</div>
				</div>&nbsp;
				<div class="span4 col sintomas">
				
					<label><h4><spring:message code='aca.SeleccioneSignosSintomas'/></h4></label>
					
					<input type="hidden" name="valores" value="<%=alertaDatos.getSintomas()%>" />

					<p>
						<input type="checkbox" class="sintomas" id="fiebre" name="fiebre" value="fiebre" />
						<label><spring:message code='aca.FiebreEscalofrios'/></label>
					</p>
					<p>
						<input type="checkbox" class="sintomas" id="muscular" name="muscular" value="muscular" />
						<label><spring:message code='aca.MialgiasDolorMuscular'/></label>
					</p>
					<p>
						<input type="checkbox" class="sintomas" id="cutanea" name="cutanea" value="cutanea" />
						<label><spring:message code='aca.ErupcionCutanea'/></label>
					</p>
					<p>
						<input type="checkbox" class="sintomas" id="malestar" name="malestar" value="malestar" />
						<label><spring:message code='aca.MalestarGeneral'/></label>
					</p>
					<p>
						<input type="checkbox" class="sintomas" id="nauseas" name="nauseas" value="nauseas" />
						<label><spring:message code='aca.NauseasVomitos'/></label>
					</p>
					<p>
						<input type="checkbox" class="sintomas" id="fatiga" name="fatiga" value="fatiga" />
						<label><spring:message code='aca.Fatiga'/></label>
					</p>
				

				</div>
				
				<div class="span4 col sintomas">
					<p>
						<input type="checkbox" class="sintomas" id="cabeza" name="cabeza" value="cabeza" />
						<label><spring:message code='aca.Cefalea'/></label>
					</p>
					<p>
						<input type="checkbox" class="sintomas" id="sudoracion" name="sudoracion" value="sudoracion" />
						<label><spring:message code='aca.Diaforesis'/></label>
					</p>
					<p>
						<input type="checkbox" class="sintomas" id="tos" name="tos" value="tos" />
						<label><spring:message code='aca.TosExpectoracion'/></label>
					</p>
					<p>
						<input type="checkbox" class="sintomas" id="articular" name="articular" value="huesos" />
						<label><spring:message code='aca.Artralgias'/></label>
					</p>
					<p>
						<label><spring:message code='aca.Otro'/></label>
						<input type="Text" class="sintomas otro"  id="otro" name="otro" maxlength="50" size="30" value="<%=alertaDatos.getOtro()==null?"":alertaDatos.getOtro()%>" />
					</p>
				</div>
			</div>
			<div class="alert alert-info">
				<a onclick="probar();" class="btn btn-primary"><spring:message code="aca.Grabar"/></a>
			</div>
		</form>
	</div>
</body>
</html>
<script>

function setDefaults(){
	var valores = jQuery('input[name="valores"]').val();
	
	if(valores.length == 11){
		document.frmDatos.fiebre.checked 			= valores.charAt(0)=='1'?true:false;
		document.frmDatos.muscular.checked			= valores.charAt(1)=='1'?true:false;
		document.frmDatos.cutanea.checked		 	= valores.charAt(2)=='1'?true:false;
		document.frmDatos.malestar.checked		 	= valores.charAt(3)=='1'?true:false;
		document.frmDatos.nauseas.checked		 	= valores.charAt(4)=='1'?true:false;
		document.frmDatos.fatiga.checked		 	= valores.charAt(5)=='1'?true:false;
		document.frmDatos.cabeza.checked		 	= valores.charAt(6)=='1'?true:false;
		document.frmDatos.sudoracion.checked		= valores.charAt(7)=='1'?true:false;
		document.frmDatos.tos.checked		 		= valores.charAt(8)=='1'?true:false;
		document.frmDatos.articular.checked		= valores.charAt(9)=='1'?true:false;
		document.frmDatos.sintoma.checked		 	= valores.charAt(10)=='1'?true:false;
	}
	
}

setDefaults();


function probar(){
	if(document.frmDatos.direccion.value != "" 
	&& document.frmDatos.procedencia.value != ""
	&& document.frmDatos.lugar1.value != ""
	&& document.frmDatos.estado.value !=""){
	     var valores = "";
	     
	     valores  += document.frmDatos.fiebre.checked     	==true?"1":"0";
		 valores  += document.frmDatos.muscular.checked 	==true?"1":"0";
		 valores  += document.frmDatos.cutanea.checked 	==true?"1":"0";
		 valores  += document.frmDatos.malestar.checked 	==true?"1":"0";
		 valores  += document.frmDatos.nauseas.checked 	==true?"1":"0";
		 valores  += document.frmDatos.fatiga.checked 		==true?"1":"0";
		 valores  += document.frmDatos.cabeza.checked 		==true?"1":"0";
		 valores  += document.frmDatos.sudoracion.checked 	==true?"1":"0";
		 valores  += document.frmDatos.tos.checked 		==true?"1":"0";
		 valores  += document.frmDatos.articular.checked 	==true?"1":"0";
		 valores  += document.frmDatos.sintoma.checked 	==true?"1":"0";
		 
		 jQuery('input[name="valores"]').val(valores);
		 document.frmDatos.submit();
	 
	}else{
		alert("Llene correctamente el formulario")
	} 
	
}
</script>
<script>
	jQuery(".ningunSintoma").on("click",function(){
		if(jQuery(this).is(":checked")){
			jQuery(".sintomas").prop("checked",false);
			jQuery(".sintomas").val("");
		}
	});
	
	jQuery(".sintomas").on("click",function(){
		if(jQuery(this).is(":checked")){
			jQuery(".ningunSintoma").prop("checked",false);
		}
	});
	
	jQuery("input.otro").on("click",function(){
		jQuery(".ningunSintoma").prop("checked",false);
	});
</script>
<script>
var loadingBar 	= jQuery('.loading-bar');
var result 		= jQuery('.result');
jQuery("#matricula").keyup(function(){
	$this = jQuery(this);
	var value 	= $this.val();
	var length 	= value.length;
	if(length == 7){
		loadingBar.show();
		jQuery.get("alumnoExiste?matricula="+value,function(a){
			if(jQuery.trim(a)==""){		
				jQuery.get("getNombreAlumno?matricula="+value, function(r){					
					loadingBar.hide();
					var nombre = jQuery.trim(r);					
					result.html(nombre);
					if(nombre.indexOf("Numero de Matrícula No Válido") != -1){
						//matricula no valida
					}else{
						jQuery.get("datosAlumno?matricula="+value, function(r){
							jQuery(".datos").html(r);
						})
					}
				});
			}else{
				loadingBar.hide();
				var nombre = jQuery.trim(a);
				
				result.html(nombre);
			}
		});
		
	}else{
		loadingBar.hide();
		result.html("");
	}
});
</script>
<script>
	jQuery('.nav-pills').find('.sintomas').addClass('active');
</script>