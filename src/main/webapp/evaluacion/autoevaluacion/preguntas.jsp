<%@page import="aca.edo.EdoAutoPreg"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="edo" scope="page" class="aca.edo.Edo"/>
<jsp:useBean id="EdoU" scope="page" class="aca.edo.EdoUtil"/>
<jsp:useBean id="edoAutoPreg" scope="page" class="aca.edo.EdoAutoPreg"/>
<jsp:useBean id="edoAutoPregU" scope="page" class="aca.edo.EdoAutoPregUtil"/>
<%
	String edoId		= request.getParameter("edoId");
	String respuesta	= "";	
	
	int accion			= request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));	
	ArrayList<EdoAutoPreg> lisPreguntas = null;
	
	switch(accion){
		case 2:{//Grabar y Modificar
			String preguntaId = request.getParameter("preguntaId")==null?"-1":request.getParameter("preguntaId");
			edoAutoPreg.setEdoId(edoId);
			edoAutoPreg.setPregunta(request.getParameter("pregunta"));
			edoAutoPreg.setTipo(request.getParameter("tipo"));
			edoAutoPreg.setOrden(request.getParameter("orden"));
			if(preguntaId.equals("-1") || preguntaId.trim().equals("")){//Grabar
				edoAutoPreg.setPreguntaId(edoAutoPregU.maximoReg(conEnoc, edoId));
				if(edoAutoPregU.insertReg(conEnoc, edoAutoPreg)){
					respuesta = "<font size='3' color='blue'><b>Se guard&oacute; correctamente la Pregunta!!!</b></font>";
				}else{
					respuesta = "<font size='3' color='red'><b>Ocurri&oacute; un error al guardar. Int&eacute;ntelo de nuevo!</b></font>";
				}
			}else{//Modificar
				edoAutoPreg.setPreguntaId(request.getParameter("preguntaId"));
				if(edoAutoPregU.updateReg(conEnoc, edoAutoPreg)){ 
					respuesta = "<font size='3' color='blue'><b>Se modific&oacute; correctamente la Pregunta!!!</b></font>";
				}else{
					respuesta = "<font size='3' color='red'><b>Ocurri&oacute; un error al modificar. Int&eacute;ntelo de nuevo!</b></font>";
				}
			}
			edoAutoPreg = new EdoAutoPreg();
		}break;
		case 3:{//Borrar
			edoAutoPreg.setEdoId(edoId);
			edoAutoPreg.setPreguntaId(request.getParameter("preguntaId"));
			if(edoAutoPregU.deleteReg(conEnoc, request.getParameter("preguntaId"), edoId)){
				respuesta = "<font size='3' color='blue'><b>Se borr&oacute; correctamente la Pregunta!!!</b></font>";
			}else{
				respuesta = "<font size='3' color='red'><b>Ocurri&oacute; un error al borrar. Int&eacute;ntelo de nuevo!</b></font>";
			}
		}break;
		case 4:{//Consultar
			edoAutoPreg.mapeaRegId(conEnoc, request.getParameter("preguntaId"), edoId);
		}break;
	}
	
	edo = EdoU.mapeaRegId(conEnoc, edoId);
	lisPreguntas = edoAutoPregU.getListEdo(conEnoc, edoId, "ORDER BY ORDEN");
%>
<head>
	<script type="text/javascript">
		function guardar(){
			if(document.forma.pregunta.value != "" &&
			  document.forma.orden.value != ""){
				document.forma.action += "&Accion=2";
				return true;
			}else{
				alert("Complete los campos requeridos(*) para poder guardar.");
			}
			return false;
		}
		
		function modificar(preguntaId){
			document.forma.action += "&Accion=4&preguntaId="+preguntaId;
			document.forma.submit();
		}
		
		function borrar(preguntaId){
			if(confirm("¿Está seguro que desea borrar la Pregunta?")){
				document.forma.action += "&Accion=3&preguntaId="+preguntaId;
				document.forma.submit();
			}
		}
	</script>
</head>
<body>
	<table style="width:100%">
		<tr>
			<td><a class="btn btn-primary" href="cuestionarios">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a></td>
		</tr>
		<tr><td align="center"><%=respuesta %></td></tr>
		<tr><td align="center" class="titulo">Periodo: <%=aca.edo.EdoPeriodoUtil.getPeriodoNombre(conEnoc,edo.getPeriodoId()) %></td></tr>
		<tr>
			<td class="titulo">Autoevaluaci&oacute;n - <%=edo.getNombre() %></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>
	<form id="forma" name="forma" action="preguntas?edoId=<%=edoId %>" method="post">
<%
	if(accion == 4){
%>
		<input type="hidden" id="preguntaId" name="preguntaId" value="<%=edoAutoPreg.getPreguntaId() %>" />
<%
	}
%>
		<table style="margin: 0 auto;" class="tabla">
			<tr>
				<td>Pregunta<b><font color="#AE2113"> *</font></b></td>
				<td><input id="pregunta" name="pregunta" value="<%=edoAutoPreg.getPregunta() %>" maxlength="100" size="50" /></td>
			</tr>
			<tr>
				<td>Orden<b><font color="#AE2113"> *</font></b></td>
				<td><input id="orden" name="orden" value="<%=edoAutoPreg.getOrden() %>" maxlength="2" size="2" /></td>
			</tr>
			<tr>
				<td><spring:message code="aca.Tipo"/></td>
				<td>
					<select id="tipo" name="tipo">
						<option value="O"<%=edoAutoPreg.getTipo().equals("O")?" Selected":"" %>>Opci&oacute;n &uacute;nica</option>
						<option value="D"<%=edoAutoPreg.getTipo().equals("D")?" Selected":"" %>>Directa</option>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align:center"><input class="btn btn-primary" type="submit" value="Guardar" onclick="guardar();" /></td>
			</tr>
		</table>
	</form>
	<table style="margin: 0 auto;  width:80%" class="tabla">
		<tr>
			<td colspan="5">&nbsp;</td>
		</tr>
		<tr>
			<td width="40px">&nbsp;</td>
			<th><spring:message code="aca.Numero"/></th>
			<th>Pregunta</th>
			<th><spring:message code="aca.Tipo"/></th>
			<th>Orden</th>
		</tr>
<%
	for(int i = 0; i < lisPreguntas.size(); i++){
		edoAutoPreg = (EdoAutoPreg) lisPreguntas.get(i);
%>
		<tr <%=(i%2 != 0)?sColor:"" %>>
			<td>
			 <a class="fas fa-edit" href="javascript:modificar('<%=edoAutoPreg.getPreguntaId() %>');"></a>
			  <a class="fas fa-trash-alt" href="javascript:borrar('<%=edoAutoPreg.getPreguntaId() %>');"></a>
			</td>
			<td><%=i+1 %></td>
			<td><%=edoAutoPreg.getPregunta() %></td>
			<td align="center"><%=edoAutoPreg.getTipo().equals("O")?"Opci&oacute;n":"Directa" %></td>
			<td align="center"><%=edoAutoPreg.getOrden() %></td>
		</tr>
<%
	}
%>
	</table>
</body>

<%@ include file= "../../cierra_enoc.jsp" %>