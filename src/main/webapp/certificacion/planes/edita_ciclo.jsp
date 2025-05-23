<%@ page import= "aca.cert.spring.CertPlan"%>
<%@ page import= "aca.cert.spring.CertCiclo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String planId 		= (String) request.getAttribute("planId"); 
	String facultad		= (String) request.getAttribute("facultad"); 
	String accion 		= (String) request.getAttribute("accion"); 

	String cicloId		= (String) request.getAttribute("cicloId"); 
	String metodo 		= (String) request.getAttribute("metodo"); 
	
	CertPlan certPlan 	= (CertPlan) request.getAttribute("certPlan"); 
	CertCiclo certCiclo = (CertCiclo) request.getAttribute("certCiclo"); 
	boolean inserta		= (boolean) request.getAttribute("inserta"); 
	
	if(accion.equals("1")){	//	Guardar
		if(!inserta){
%>
<table style="margin: 0 auto;">
	<tr><td><font size="3" color="red"><b>Error!!!</b> al guardar el ciclo. Int&eacute;ntelo de nuevo!</font></td></tr>
</table>
<%
		}
	}else if(accion.equals("2")){
		if(!inserta){
%>
<table style="margin: 0 auto;">
	<tr><td><font size="3" color="red"><b>Error!!!</b> al modificar el ciclo. Int&eacute;ntelo de nuevo!</font></td></tr>
</table>
<%
		}
	}
%>
<head>
	<script type="text/javascript">
		function revisa(){
			if(document.forma.titulo.value != ""){
				document.forma.action += "&Accion=<%=metodo.equals("Guardar")?"1":("2&ciclo="+cicloId) %>";
				return true;
			}else
				alert("Llene el Título para poder guardar");
			return false;
		}
	</script>
</head>
<div class="container-fluid">
<h2>Edita Ciclo<small class="text-muted fs-4">(<%=certPlan.getCarrera() %> [<%=certPlan.getPlanId() %>])</small></h2>
<form id="forma" name="forma" action="edita_ciclo?plan=<%=planId %>" method="post">
<div class="alert alert-info">
	<a href="ciclos?plan=<%=planId%>&facultad=<%=facultad%>" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>
</div>	
	<table style="width:80%">
		<tr>
			<td align="center">
				<table class="table">
					<tr><th colspan="2">&nbsp;</th></tr>
					<tr>
						<td>T&iacute;tulo<b><font color="#AE2113"> *</font></b></td>
						<td><input type="text" class="text" id="titulo" name="titulo" value="<%=certCiclo.getTitulo() %>" maxlength="70" size="50" /></td>
					</tr>
					<tr>
						<td><%=certPlan.getTitulo1() %></td>
						<td><input type="text" class="text" id="fst" name="fst" value="<%=certCiclo.getFst() %>" maxlength="40" /></td>
					</tr>
					<tr>
						<td><%=certPlan.getTitulo2() %></td>
						<td><input type="text" class="text" id="fsp" name="fsp" value="<%=certCiclo.getFsp() %>" maxlength="40" /></td>
					</tr>
					<tr>
						<td><%=certPlan.getTitulo3() %></td>
						<td><input type="text" class="text" id="creditos" name="creditos" value="<%=certCiclo.getCreditos() %>" maxlength="40" /></td>
					</tr>
					<tr>
						<th style="text-align:left;" colspan="2"><input type="submit" class="btn btn-primary" value="<%=metodo %>" onclick="return revisa();" /></th>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
</div>