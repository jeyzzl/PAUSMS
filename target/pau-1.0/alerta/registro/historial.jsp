<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.text.*" %>
<%@page import="java.util.Arrays"%>
<jsp:useBean id="historial" class="aca.alerta.AlertaHistorial" scope="page" />
<jsp:useBean id="HistorialU" class="aca.alerta.AlertaHistorialUtil" scope="page" />

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
<head>
<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
<link rel="stylesheet" href="../../js/chosen/chosen.css" />
</head>
<body>
<%
	String codigoPersonal = request.getParameter("matricula")==null?(String) session.getAttribute("matricula"):request.getParameter("matricula"); 
	String accion 		  = request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	int accionFmt		  = 0;	  
	String periodoId 	= (String) session.getAttribute("periodoSanitaria");
	if(session.getAttribute("periodoSanitaria") == null){
		periodoId = aca.alerta.AlertaPeriodoUtil.getPeriodoActual(conEnoc);
		session.setAttribute("periodoSanitaria", periodoId);
	}	
	periodoId 	= (String) session.getAttribute("periodoSanitaria");
	
	ArrayList<String> lisResp =  new ArrayList<String>(Arrays.asList( request.getParameterValues("respuesta5")==null?new String[0]:request.getParameterValues("respuesta5") ));
	String respuestaC 	= "";
	if(lisResp.size() != 0){
		String tmp = "";
		for(String str: lisResp){
			tmp+= str+",";	
		}	
		tmp = tmp.substring(0, tmp.length()-1);
		
		respuestaC = tmp;
	}
	
	String msj			= "";
	if(accion.equals("1")){
		for(int i = 1; i<23; i++){
				historial.setPeriodoId(periodoId);
				historial.setCodigoPersonal(codigoPersonal);
				historial.setPregunta(Integer.toString(i));
				if(!(i==5)) historial.setRespuesta(request.getParameter("respuesta"+i));
				else historial.setRespuesta(respuestaC);
				historial.setComentario1(request.getParameter("comentario1"+i));
				historial.setComentario2(request.getParameter("comentario2"+i)); 
					
				if(!HistorialU.existeReg(conEnoc, periodoId, codigoPersonal, Integer.toString(i))){
					if(HistorialU.insertReg(conEnoc, historial)==true){
					
					}else{
						msj = "<div class='alert alert-info'>Hubo un error al guardar los datos</div>";
						break;
					}
				}else{
					if(HistorialU.updateReg(conEnoc, historial)==true){
						msj = "<div class='alert alert-info'>Se guardo Correctamente los cambios</div>";
					}else{
						msj = "<div class='alert alert-danger'>Hubo un error al guardar los datos</div>";
						break;
					}	
				}
				
			}
		msj = "<div class='alert alert-success'>Redireccionando...</div>";
%>
				
			<META HTTP-EQUIV="Refresh" CONTENT="3; URL=antro?matricula=<%=request.getParameter("matricula")%>">;
<%		}
				
		boolean existe = HistorialU.existeRegistro(conEnoc, periodoId, codigoPersonal);
%>
<div class="container-fluid">	
	<h2><spring:message code='aca.Historial'/><small>( <%=aca.alerta.AlertaPeriodoUtil.getPeriodoNombre(conEnoc, periodoId) %> )</small><small> - <%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, codigoPersonal, "")%></small></h2>
	<div class="alert alert-info">
	<%	if(existe){ %>
		<a href="datos" class="btn btn-primary"><spring:message code="aca.Datos"/></a>&nbsp;&nbsp;
		<a href="accion?matricula=<%=codigoPersonal%>" class="btn btn-success"><span class="icon icon-white icon-arrow-left" >&nbsp;</span><spring:message code='aca.Anterior'/></a>&nbsp;&nbsp;
		<a href="antro?matricula=<%=codigoPersonal%>" class="btn btn-success"><spring:message code='aca.Siguiente'/>&nbsp;<span class="icon icon-white icon-arrow-right" ></span></a>&nbsp;&nbsp;
	<%	}else{
		out.print("&nbsp;"); 
	}
	%>
	</div>		
	<%if(!msj.equals("")){%>
			<%=msj %>
	<%} %>
	<form id="datos" style="width: 100%" name="datos" action="historial" method="post">
		<input name="Accion" type="hidden" value="<%=accion%>"/>
		<input type="hidden" required id="matricula" name="matricula" value="<%=codigoPersonal %>" />
		
		<div class="row">
			<div class="span4">
				<div class="control-group">
				<label>Enfermedades cr&oacute;nicas</label>
				<% 
					historial = HistorialU.mapeaRegId(conEnoc, periodoId, codigoPersonal, "5");
				%>
				 <select id="respuesta5" name="respuesta5" class="chosen" multiple="true" data-placeholder="Elija las enfermedades Cr&oacute;nicas">
				 	<option value="a"<%if(historial.getRespuesta().contains("a")) out.print("selected"); %>>Hipertension arterial</option> 
				 	<optgroup label="Diabetes mellitus">
				 		<option value="b"<%if(historial.getRespuesta().contains("b")) out.print("selected"); %>>Tipo 1</option>
				 		<option value="c"<%if(historial.getRespuesta().contains("c")) out.print("selected"); %>>Tipo 2</option>
				 	</optgroup>
				 	<option value="d"<%if(historial.getRespuesta().contains("d")) out.print("selected"); %>>Hipercolesterolemia</option>
				 	<option value="e"<%if(historial.getRespuesta().contains("e")) out.print("selected"); %>>Trastornos sanguineos</option>
				 	<option value="f"<%if(historial.getRespuesta().contains("f")) out.print("selected"); %>>Epilepsia</option>
				 	<optgroup label="Enfermedades cardiacas">
					 	<option value="g"<%if(historial.getRespuesta().contains("g")) out.print("selected"); %>>Defectos congenitos</option>
					 	<option value="h"<%if(historial.getRespuesta().contains("h")) out.print("selected"); %>>Arritmias</option>
					 	<option value="i"<%if(historial.getRespuesta().contains("i")) out.print("selected"); %>>Fiebre reumatica</option>
				 	</optgroup>
				 	<option value="j"<%if(historial.getRespuesta().contains("j")) out.print("selected"); %>>Asma</option>
				 	<optgroup label="Enfermedades autoinmunes">
					 	<option value="k"<%if(historial.getRespuesta().contains("k")) out.print("selected"); %>>Artritis reumatoide</option>
					 	<option value="l"<%if(historial.getRespuesta().contains("l")) out.print("selected"); %>>Psoriasis</option>
					 	<option value="m"<%if(historial.getRespuesta().contains("m")) out.print("selected"); %>>Lupus eritematoso</option>
<%-- 					 	<option value="n"<%if(historial.getRespuesta().contains("n")) out.print("selected"); %>>Otros</option> --%>
				 	</optgroup>
				 		<option value="o"<%if(historial.getRespuesta().contains("o")) out.print("selected"); %>>Depresi&oacute;n</option>
					 	<option value="p"<%if(historial.getRespuesta().contains("p")) out.print("selected"); %>>Hepatitis</option>
					 	<option value="q"<%if(historial.getRespuesta().contains("q")) out.print("selected"); %>>VIH</option>
				</select>
				<br><br>
				<label>Otros:</label>
				<% 
					historial = HistorialU.mapeaRegId(conEnoc, periodoId, codigoPersonal, "15");
				%>
				<input type="text" name="comentario" value="<%=historial.getComentario1()%>">
				<br><br>
				<label>¿Utilizas algun medicamento por prescripci&oacute;n?</label>
				<% 
					historial = HistorialU.mapeaRegId(conEnoc, periodoId, codigoPersonal, "2");
				%>
				<select name="respuesta2">
					<option></option>
					<option value="S" <%if(historial.getRespuesta().equals("S")) out.print("selected"); %>><spring:message code='aca.Si'/></option>
					<option value="N" <%if(historial.getRespuesta().equals("N")) out.print("selected"); %>><spring:message code='aca.No'/></option>
				</select>
				<br>
				<br>
				<label>¿Para que padecimiento?</label>
				<% 
					historial = HistorialU.mapeaRegId(conEnoc, periodoId, codigoPersonal, "2");
				%>
				<input type="text" name="comentario12" value="<%=historial.getComentario1()%>">
				</div>
				<br>
				<div class="control-group">
				<label><spring:message code='aca.TipoSangreRh'/></label>
				<% 
					historial = HistorialU.mapeaRegId(conEnoc, periodoId, codigoPersonal, "3");
				%>
				<select name="respuesta3">
					<option></option>
					<option value="a" <%if(historial.getRespuesta().equals("a")) out.print("selected"); %>>A+</option>
					<option value="b"<%if(historial.getRespuesta().equals("b")) out.print("selected"); %>>A-</option>
					<option value="c"<%if(historial.getRespuesta().equals("c")) out.print("selected"); %>>B+</option>
					<option value="d"<%if(historial.getRespuesta().equals("d")) out.print("selected"); %>>B-</option>
					<option value="e"<%if(historial.getRespuesta().equals("e")) out.print("selected"); %>>AB+</option>
					<option value="f"<%if(historial.getRespuesta().equals("f")) out.print("selected"); %>>AB-</option>
					<option value="g"<%if(historial.getRespuesta().equals("g")) out.print("selected"); %>>O+</option>
					<option value="h"<%if(historial.getRespuesta().equals("h")) out.print("selected"); %>>O-</option>
					<option value="i"<%if(historial.getRespuesta().equals("i")) out.print("selected"); %>>NO SE</option>
				</select>
				</div>
				<br>
				<div class="control-group">
				<label>¿Te gustaria ser donador altruista de sangre y que te contactemos en caso de necesidad?</label>
				<% 
					historial = HistorialU.mapeaRegId(conEnoc, periodoId, codigoPersonal, "4");
				%>
				<select name="respuesta4">
					<option></option>
					<option value="S"<%if(historial.getRespuesta().equals("S")) out.print("selected"); %>><spring:message code='aca.Si'/></option>
					<option value="N"<%if(historial.getRespuesta().equals("N")) out.print("selected"); %>><spring:message code='aca.No'/></option>
				</select>
				<br><br>
				<label><spring:message code='aca.Telefono'/>:</label>
				<% 
					historial = HistorialU.mapeaRegId(conEnoc, periodoId, codigoPersonal, "4");
				%>
				<input type="text" name="comentario14" value="<%=historial.getComentario1()%>">
				<br><br>
				<label>Email "Institucional": <small>Ejem: 1100294@alumno.um.edu.mx</small> </label>
				<% 
					historial = HistorialU.mapeaRegId(conEnoc, periodoId, codigoPersonal, "4");
				%>
				<input type="text" name="comentario24" value="<%=historial.getComentario2()%>">
				</div>
				<br><br>
			</div>
			<div class="span4">
				<label>¿Cuando fue la ultima vez que visitaste al dentista?</label>
				<% 
					historial = HistorialU.mapeaRegId(conEnoc, periodoId, codigoPersonal, "6");
				%>
				<select name="respuesta6">
					<option></option>
					<option value="a"<%if(historial.getRespuesta().equals("a")) out.print("selected"); %>>1 a&ntilde;o</option>
					<option value="b"<%if(historial.getRespuesta().equals("b")) out.print("selected"); %>>M&aacute;s de 3 a&ntilde;o</option>
					<option value="c"<%if(historial.getRespuesta().equals("c")) out.print("selected"); %>>Nunca</option>
				</select>
				<br><br>
				<label>Cuantas veces te cepillas los dientes</label>
				<% 
					historial = HistorialU.mapeaRegId(conEnoc, periodoId, codigoPersonal, "7");
				%>
				<select name="respuesta7">
					<option></option>
					<option value="a"<%if(historial.getRespuesta().equals("a")) out.print("selected"); %>>1 vez</option>
					<option value="b"<%if(historial.getRespuesta().equals("b")) out.print("selected"); %>>2 veces</option>
					<option value="c"<%if(historial.getRespuesta().equals("c")) out.print("selected"); %>>3 o mas veces</option>
				</select>
				<br><br>
				<label>¿Presentas sangrado en las encias al cepillarte los dientes?</label>
				<% 
					historial = HistorialU.mapeaRegId(conEnoc, periodoId, codigoPersonal, "8");
				%>
				<select name="respuesta8">
					<option></option>
					<option value="a"<%if(historial.getRespuesta().equals("a")) out.print("selected"); %>><spring:message code='aca.Si'/></option>
					<option value="b"<%if(historial.getRespuesta().equals("b")) out.print("selected"); %>>Ocasionalmente</option>
					<option value="c"<%if(historial.getRespuesta().equals("c")) out.print("selected"); %>><spring:message code='aca.No'/></option>
				</select>
				<br><br>
				<label>¿Has experimentado alguna perdida de un diente?</label>
				<% 
					historial = HistorialU.mapeaRegId(conEnoc, periodoId, codigoPersonal, "9");
				%>
				<select name="respuesta9">
					<option></option>
					<option value="a"<%if(historial.getRespuesta().equals("a")) out.print("selected"); %>><spring:message code='aca.Si'/></option>
					<option value="b"<%if(historial.getRespuesta().equals("b")) out.print("selected"); %>><spring:message code='aca.No'/></option>
				</select>
				<br><br>
				<label>¿Tienes sensibilidad los dientes ya sea por el frio, lo dulce o al morder?</label>
				<% 
					historial = HistorialU.mapeaRegId(conEnoc, periodoId, codigoPersonal, "10");
				%>
				<select name="respuesta10">
					<option></option>
					<option value="a"<%if(historial.getRespuesta().equals("a")) out.print("selected"); %>><spring:message code='aca.Si'/></option>
					<option value="b"<%if(historial.getRespuesta().equals("b")) out.print("selected"); %>><spring:message code='aca.No'/></option>
				</select>
				<br><br>
				<label>¿Has tenido cirugias bucales mayores?</label>
				<% 
					historial = HistorialU.mapeaRegId(conEnoc, periodoId, codigoPersonal, "11");
				%>
				<select name="respuesta11">
					<option></option>
					<option value="a"<%if(historial.getRespuesta().equals("a")) out.print("selected"); %>><spring:message code='aca.Si'/></option>
					<option value="b"<%if(historial.getRespuesta().equals("b")) out.print("selected"); %>><spring:message code='aca.No'/></option>
				</select>
				<br><br>
				<label><spring:message code='aca.¿Cuales?'/></label>
				<% 
					historial = HistorialU.mapeaRegId(conEnoc, periodoId, codigoPersonal, "11");
				%>
				<input type="text" name="comentario111" size="25" value="<%=historial.getComentario1()%>">
				<br><br>
				<div class="control-group">
				<label>Alergias a algun alimento... especifica</label>
				<% 
					historial = HistorialU.mapeaRegId(conEnoc, periodoId, codigoPersonal, "14");
				%>
				<input type="text" name="comentario114" size="25" value="<%=historial.getComentario1()%>">
				</div>
			</div>
			<div class="span5">
<!-- 				<label>¿Te consideras una persona informada en el tema de la salud sexual?</label> -->
				<% 
// 					historial = HistorialU.mapeaRegId(conEnoc, periodoId, codigoPersonal, "12");
				%>
<!-- 				<select name="respuesta12"> -->
<!-- 					<option></option> -->
<%-- 					<option value="a"<%if(historial.getRespuesta().equals("a")) out.print("selected"); %>><spring:message code='aca.Si'/></option> --%>
<%-- 					<option value="b"<%if(historial.getRespuesta().equals("b")) out.print("selected"); %>><spring:message code='aca.No'/></option> --%>
<!-- 				</select> -->
<!-- 				<br><br> -->
<!-- 				<label>¿Desearias recibir informacion de expertos?</label> -->
				<% 
// 					historial = HistorialU.mapeaRegId(conEnoc, periodoId, codigoPersonal, "13");
				%>
<!-- 				<select name="respuesta13"> -->
<!-- 					<option></option> -->
<%-- 					<option value="a"<%if(historial.getRespuesta().equals("a")) out.print("selected"); %>><spring:message code='aca.Si'/></option> --%>
<%-- 					<option value="b"<%if(historial.getRespuesta().equals("b")) out.print("selected"); %>><spring:message code='aca.No'/></option> --%>
<!-- 				</select> -->
<!-- 				<br><br> -->
				<div class="control-group">
					<label>Alérgico a insectos... especifica:</label>
					<% 
						historial = HistorialU.mapeaRegId(conEnoc, periodoId, codigoPersonal, "16");
					%>
					<input type="text" name="comentario116" size="25" value="<%=historial.getComentario1()%>">
				</div>
				<br>
				<div class="control-group">
					<label><spring:message code='aca.AlergiaAMedicamentos'/>... especifica</label>
					<% 
						historial = HistorialU.mapeaRegId(conEnoc, periodoId, codigoPersonal, "1");
					%>
					<input type="text" name="comentario1" size="25" value="<%=historial.getComentario1()%>">
				</div>
				<br>
				<div class="control-group">
					<label>Alérgico a látex</label>
					<% 
						historial = HistorialU.mapeaRegId(conEnoc, periodoId, codigoPersonal, "18");
					%>
					<select name="respuesta18">
						<option></option>
						<option value="S" <%if(historial.getRespuesta().equals("S")) out.print("selected"); %>><spring:message code='aca.Si'/></option>
						<option value="N" <%if(historial.getRespuesta().equals("N")) out.print("selected"); %>><spring:message code='aca.No'/></option>
					</select>
				</div>
				<div class="control-group">
				<label>¿Alguna vez te ha indicado tu médico, que tienes un problema cardiovascular, y que solamente puedes participar en ejercicios o actividad física bajo su permiso y/o autorización?</label>
				<% 
					historial = HistorialU.mapeaRegId(conEnoc, periodoId, codigoPersonal, "19");
				%>
				<select name="respuesta19">
					<option></option>
					<option value="S" <%if(historial.getRespuesta().equals("S")) out.print("selected"); %>><spring:message code='aca.Si'/></option>
					<option value="N" <%if(historial.getRespuesta().equals("N")) out.print("selected"); %>><spring:message code='aca.No'/></option>
				</select>
				</div>
				<div class="control-group">
				<label>¿Tienes problemas en los huesos o articulaciones (por ejemplo, en la espalda, rodillas o cadera) que pudiera agravarse al aumentar la actividad física?</label>
				<% 
					historial = HistorialU.mapeaRegId(conEnoc, periodoId, codigoPersonal, "20");
				%>
				<select name="respuesta20">
					<option></option>
					<option value="S" <%if(historial.getRespuesta().equals("S")) out.print("selected"); %>><spring:message code='aca.Si'/></option>
					<option value="N" <%if(historial.getRespuesta().equals("N")) out.print("selected"); %>><spring:message code='aca.No'/></option>
				</select>
				</div>
				<div class="control-group">
				<label>¿Existe alguna razón por la cual no deberías participar en un programa de actividad física diario y semanal?</label>
				<% 
					historial = HistorialU.mapeaRegId(conEnoc, periodoId, codigoPersonal, "21");
				%>
				<input type="text" name="comentario121" size="25" value="<%=historial.getComentario1()%>">
				</div>
				<div class="control-group">
				<label> ¿Estas bajo tratamiento (o estuviste en los últimos 6 meses),  de un experto en nutrición(incluye entrenador, coach o bariata) que te receta(ó) suplementos ?</label>
				<% 
					historial = HistorialU.mapeaRegId(conEnoc, periodoId, codigoPersonal, "22");
				%>
				<input type="text" name="comentario122" value="<%=historial.getComentario1()%>">
				</div>
			</div>
		</div>				
		<div class="alert alert-info">
			<a onclick="javascript:probar();" class="btn btn-primary"><spring:message code="aca.Grabar"/></a>
		</div>
	</form>
</div>
</body>
</html>
<script type="text/javascript">
function probar() {
	if (document.datos.respuesta2.value != ""
	&& document.datos.respuesta4.value != ""
	&& document.datos.respuesta6.value != ""
	&& document.datos.respuesta7.value != ""
	&& document.datos.respuesta8.value != ""
	&& document.datos.respuesta9.value != ""
	&& document.datos.respuesta10.value != ""
	&& document.datos.respuesta11.value != ""){
		document.datos.Accion.value ="1";
		document.datos.submit();
	}else{
		alert("Llena los campos");
	}
}

</script>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script>
	jQuery(".chosen").chosen();
	jQuery('.nav-pills').find('.historial').addClass('active');
</script>
<%@ include file= "../../cierra_enoc.jsp" %>