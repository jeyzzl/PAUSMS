<%@page import="java.util.List"%>
<%@page import="aca.edo.spring.Edo"%>
<%@page import="aca.edo.spring.EdoAlumnoPreg"%>
<%@page import="aca.carga.spring.CargaGrupo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../body.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	String colorPortal 			= (String)session.getAttribute("colorPortal");

	String edoId 				= request.getParameter("edo")==null?"0":request.getParameter("edo");
	String cursoCargaId 		= request.getParameter("cursoCarga")==null?"0":request.getParameter("cursoCarga");
	
	Edo edo 					= (Edo) request.getAttribute("edo");
	CargaGrupo grupo			= (CargaGrupo) request.getAttribute("grupo");
	String materiaNombre 		= (String) request.getAttribute("materiaNombre");
	String maestroNombre 		= (String) request.getAttribute("maestroNombre");
	
	List<EdoAlumnoPreg> lisPreguntas 	= (List<EdoAlumnoPreg>) request.getAttribute("lisPreguntas");
%>
<head>
	<link href="css/pa<%=colorPortal%>.css" rel="STYLESHEET" type="text/css">
	<script type="text/javascript">
		function guardar(numPreguntas){
			var lleno = true;
			for(var i = 0; i < numPreguntas; i++){
				if(eval("document.forma.pregunta"+i).length){//Si es arreglo de nodos
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
				if(confirm("Si guarda ya no podra modificar lo que contestó.\n¿Está seguro que desea guardar?")){
					document.forma.action += "&Accion=1";
					return true;
				}
			}else
				alert("Llene por lo menos las preguntas de opción única para poder guardar!!!");
			return false;
		}
		
		function checkSize(obj, event){
			if(event.keyCode != 8){//Si no es backspace
				var texto = obj.value;
				if(texto.length < 230){
					return true;
				}
				return false;
			}else{
				return true;
			}
		}
	</script>
</head>
<body>
<div class="container-fluid">
	<h3>
		<a href="materias" style="text-align:center;" class="btn btn-primary btn-sm"><i class="fas fa-arrow-left"></i></a>&nbsp;
		Evaluación de: <%=materiaNombre %><small class="text-muted"> ( <%=maestroNombre%> )<small>
	</h3>
	<div class="alert alert-info">	
		<b>Instrucciones:</b> <%=edo.getEncabezado() %>
	</div>
	<form id="forma" name="forma" action="grabarEvaluacion?edo=<%=edoId %>&cursoCarga=<%=cursoCargaId %>" method="post">
		<table width:80%" class="table table-condensed table-striped">
			
<% 		if(edo.getTipoEncuesta().equals("V")){	%>
			<tr><td colspan="10">SECCIÓN A: EVALUACIÓN DEL DISEÑO DEL CURSO </td></tr>
			<tr><td colspan="10">SECCIÓN B: EVALUACIÓN DEL TUTOR DEL CURSO </td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<th width="7%" Style="background:none; color: black; border:solid 1px black;">(N) Nunca</th>
				<th width="7%" Style="background:none; color: black; border:solid 1px black;">(CN) Casi Nunca</th>
				<th width="7%" Style="background:none; color: black; border:solid 1px black;">(R) Regularmente</th>
				<th width="7%" Style="background:none; color: black; border:solid 1px black;">(CS) Casi Siempre</th>
				<th width="7%" Style="background:none; color: black; border:solid 1px black;">(S) Siempre Bueno</th>
				<th width="7%" Style="background:none; color: black; border:solid 1px black;">(NA) No aplica</th>
			</tr>
			<tr>
				<td colspan="10">&nbsp;</td>
			</tr>
			<tr>
				<td colspan="10"><font size="3" style="margin-left:10%; margin-top:1%;"><b>SECCIÓN A: EVALUACIÓN DEL DISEÑO DEL CURSO</b></font></td>
			</tr>			
			<tr>
				<th width="7%"><spring:message code="aca.Numero"/></th>
				<th width="50%">Indicadores para evaluar el diseño del curso: <br>El Curso... </th>					
				<th width="7%">N</th>
				<th width="7%">CN</th>
				<th width="7%">R</th>
				<th width="7%">CS</th>
				<th width="7%">S</th>
				<th width="7%">NA</th>
			</tr>
<%
		}else{
%>
			<tr>
				<th width="7%"><spring:message code="aca.Numero"/></th>
				<th width="50%">Aspecto a evaluar (docente y materia)</th>
				<th width="7%">Deficiente</th>
				<th width="7%">Regular</th>
				<th width="7%">Bueno</th>
				<th width="7%">Muy bueno(a)</th>
				<th width="7%">Excelente</th>
			</tr>
<%
		}				
	
		int i = -1;
		for(EdoAlumnoPreg edoAlumnoPreg : lisPreguntas ){
			i++;
			if(edoAlumnoPreg.getSeccion().equals("A")){	
%>
			<tr>
				<td align="center" style="border-bottom: dotted 1px gray;"><%=i+1 %></td>
				<td style="border-bottom: dotted 1px gray;"><%=edoAlumnoPreg.getPregunta()%></td>
<%
				if(edoAlumnoPreg.getTipo().equals("O") ){

					if(edo.getTipoEncuesta().equals("P")){
%>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" id="pregunta<%=i %>" name="pregunta<%=i %>" value="1" /></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" id="pregunta<%=i %>" name="pregunta<%=i %>" value="2" /></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" id="pregunta<%=i %>" name="pregunta<%=i %>" value="3" /></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" id="pregunta<%=i %>" name="pregunta<%=i %>" value="4" /></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" id="pregunta<%=i %>" name="pregunta<%=i %>" value="5" /></td>
<%
					}else{
						
%>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" id="pregunta<%=i %>" name="pregunta<%=i %>" value="1" /></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" id="pregunta<%=i %>" name="pregunta<%=i %>" value="2" /></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" id="pregunta<%=i %>" name="pregunta<%=i %>" value="3" /></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" id="pregunta<%=i %>" name="pregunta<%=i %>" value="4" /></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" id="pregunta<%=i %>" name="pregunta<%=i %>" value="5" /></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" id="pregunta<%=i %>" name="pregunta<%=i %>" value="0" /></td>
<%
						
					}
				}else{
%>
				<td style="border-bottom: dotted 1px gray;" colspan="5"><textarea id="pregunta<%=i %>" name="pregunta<%=i %>" cols="50" onkeypress="return checkSize(this, event);"></textarea></td>
<%
				}
%>
			</tr>
<%
			}

		}
		
		if(edo.getTipoEncuesta().equals("V")){
%>
			<tr>
				<td colspan="10"><font size="3" style="margin-left:10%; margin-top:1%;"><b>SECCIÓN B: EVALUACIÓN DEL TUTOR DEL CURSO</b></font></td>
			</tr>
			<tr>			
				<th width="7%"><spring:message code="aca.Numero"/></th>
				<th width="50%">Indicadores para evaluar el diseño del curso: <br>El Tutor... </th>
				<th width="7%">N</th>
				<th width="7%">CN</th>
				<th width="7%">R</th>
				<th width="7%">CS</th>
				<th width="7%">S</th>
				<th width="7%">NA</th>
			</tr>
<%		}		%>			
<%		
		i = -1;
		for(EdoAlumnoPreg edoAlumnoPreg : lisPreguntas){
			i++;
			
			if(edoAlumnoPreg.getSeccion().equals("B")){				
		%>
			<tr>
				<td align="center" style="border-bottom: dotted 1px gray;"><%=i+1 %></td>
				<td style="border-bottom: dotted 1px gray;"><%=edoAlumnoPreg.getPregunta()%></td>
		<%
				if(edoAlumnoPreg.getTipo().equals("O") ){		
		
					if(edo.getTipoEncuesta().equals("P")){
		%>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" id="pregunta<%=i %>" name="pregunta<%=i %>" value="1" /></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" id="pregunta<%=i %>" name="pregunta<%=i %>" value="2" /></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" id="pregunta<%=i %>" name="pregunta<%=i %>" value="3" /></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" id="pregunta<%=i %>" name="pregunta<%=i %>" value="4" /></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" id="pregunta<%=i %>" name="pregunta<%=i %>" value="5" /></td>
		<%
					}else{
						
						%>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" id="pregunta<%=i %>" name="pregunta<%=i %>" value="1" /></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" id="pregunta<%=i %>" name="pregunta<%=i %>" value="2" /></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" id="pregunta<%=i %>" name="pregunta<%=i %>" value="3" /></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" id="pregunta<%=i %>" name="pregunta<%=i %>" value="4" /></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" id="pregunta<%=i %>" name="pregunta<%=i %>" value="5" /></td>
				<td style="border-bottom: dotted 1px gray;" align="center"><input type="radio" id="pregunta<%=i %>" name="pregunta<%=i %>" value="0" /></td>
						
						<%
						
					}
				}else{
		%>
						<td style="border-bottom: dotted 1px gray;" colspan="5"><textarea id="pregunta<%=i %>" name="pregunta<%=i %>" cols="70" onkeypress="return checkSize(this, event);"></textarea></td>
		<%
				}
		%>
			</tr>
		<%
			}
		}
%>			
		</table>
		<div class="alert alert-info">				
			<input class="btn btn-primary" type="submit" value="Guardar" onclick="return guardar(<%=lisPreguntas.size() %>);" />
		</div>			
	</form>
</body>