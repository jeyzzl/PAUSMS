<%@page import="aca.alumno.AlumPersonal"%>
<%@page import="aca.edo.EdoAutoPreg"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="edo" scope="page" class="aca.edo.Edo"/>
<jsp:useBean id="EdoU" scope="page" class="aca.edo.EdoUtil"/>
<jsp:useBean id="edoAutoPreg" scope="page" class="aca.edo.EdoAutoPreg"/>
<jsp:useBean id="edoAutoPregU" scope="page" class="aca.edo.EdoAutoPregUtil"/>
<jsp:useBean id="cargaGrupo" scope="page" class="aca.carga.CargaGrupo"/>
<jsp:useBean id="grupoUtil" scope="page" class="aca.carga.CargaGrupoUtil"/>
<jsp:useBean id="edoAutoResp" scope="page" class="aca.edo.EdoAutoResp"/>
<%
	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");
	String edoId				= request.getParameter("edo");
	String nombreMaestro		= "";
	
	int accion				= request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));

	ArrayList<EdoAutoPreg> lisPreguntas = edoAutoPregU.getListEdo(conEnoc, edoId, "ORDER BY ORDEN");

	edo = EdoU.mapeaRegId(conEnoc, edoId);
	nombreMaestro = aca.vista.MaestrosUtil.getNombreMaestro(conEnoc, codigoPersonal, "NOMBRE");
	
	switch(accion){
		case 1:{
			boolean guardo = true;
			for(int i = 0; i < lisPreguntas.size(); i++){
				edoAutoPreg = (EdoAutoPreg) lisPreguntas.get(i);
				
				edoAutoResp.setEdoId(edoId);
				edoAutoResp.setPreguntaId(edoAutoPreg.getPreguntaId());
				edoAutoResp.setCodigoPersonal(codigoPersonal);
				edoAutoResp.setRespuesta(request.getParameter("pregunta"+i));
				if(!edoAutoResp.insertReg(conEnoc)){
					guardo = false;
				}
			}
			if(guardo){
				response.sendRedirect("cursos");
			}else{
%>
<table style="margin: 0 auto;">
	<tr>
		<td><font size="3" color="red"><b>Error while saving. Try Again!</b></font></td>
	</tr>
</table>
<%
			}
		}break;
	}
%>
<head>	
	<script type="text/javascript">
		function guardar(numPreguntas){
			var lleno = true;
			for(var i = 0; i < numPreguntas; i++){
				if(eval("document.forma.pregunta"+i).tagName != "TEXTAREA"){
					var j;
					var filaContestada = false;
					for(j = 0; j < eval("document.forma.pregunta"+i).length; j++){
						if(eval("document.forma.pregunta"+i)[j].checked)
							filaContestada = true;
					}
					if(!filaContestada)
						lleno = false;
				}
			}
			if(lleno){
				if(confirm("If you save, you will no longer be able to modify what you answered.\n Are you sure you want to save?")){
					document.forma.action += "&Accion=1";
					return true;
				}
			}else
				alert("Fill out at least the single choice questions to save!");
			return false;
		}
	</script>
</head>
<body>
	<form id="forma" name="forma" action="autoevaluacion?edo=<%=edoId %>" method="post">
		<table style="margin: 0 auto;  width:80%">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td align="center"><font size="3"><b><%=nombreMaestro %> Self-evaluation </b></font></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>
					<b>Instructions:</b> Read each statement carefully and choose the
					option that best expresses your opinion about your performance as 
					a professor. This instrument should be submitted to the academic 
					department to which you belong with a portfolio that includes 
					evidence of each self-evaluated aspect.
				</td>
			</tr>
		</table>
		<table style="margin: 0 auto;  width:90%">
			<tr>
				<th width="5%"><spring:message code="aca.Numero"/></th>
				<th width="50%">Aspect to be evaluated (professor and subject)</th>
				<th width="9%">Deficient</th>
				<th width="9%">Regular</th>
				<th width="9%">Good</th>
				<th width="9%">Very Good</th>
				<th width="9%">Excellent</th>
			</tr>
<%
	for(int i = 0; i < lisPreguntas.size(); i++){
		edoAutoPreg = (EdoAutoPreg) lisPreguntas.get(i);
%>
			<tr>
				<td style="border-bottom: dotted 1px gray;"><%=i+1 %></td>
				<td style="border-bottom: dotted 1px gray;"><%=edoAutoPreg.getPregunta() %></td>
<%
		if(edoAutoPreg.getTipo().equals("O")){
%>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" id="pregunta<%=i %>" name="pregunta<%=i %>" value="1" /></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" id="pregunta<%=i %>" name="pregunta<%=i %>" value="2" /></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" id="pregunta<%=i %>" name="pregunta<%=i %>" value="3" /></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" id="pregunta<%=i %>" name="pregunta<%=i %>" value="4" /></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" id="pregunta<%=i %>" name="pregunta<%=i %>" value="5" /></td>
<%
		}else{
%>
				<td style="border-bottom: dotted 1px gray;" colspan="5"><textarea id="pregunta<%=i %>" name="pregunta<%=i %>" cols="50"></textarea></td>
<%
		}
%>
			</tr>
<%
	}
%>
		</table>
		<table style="margin: 0 auto;">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>
					<input type="submit" value="Guardar" onclick="return guardar(<%=lisPreguntas.size() %>);" />
				</td>
			</tr>
		</table>
	</form>
</body>

<%@ include file= "../../cierra_enoc.jsp" %>