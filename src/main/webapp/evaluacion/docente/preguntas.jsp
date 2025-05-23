<%@page import="aca.edo.EdoMaestroPreg"%>
<%@page import="aca.edo.EdoArea"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="edo" scope="page" class="aca.edo.Edo"/>
<jsp:useBean id="EdoU" scope="page" class="aca.edo.EdoUtil"/>
<jsp:useBean id="edoMaestroPreg" scope="page" class="aca.edo.EdoMaestroPreg"/>
<jsp:useBean id="edoMaestroPregU" scope="page" class="aca.edo.EdoMaestroPregUtil"/>
<jsp:useBean id="edoArea" scope="page" class="aca.edo.EdoArea"/>
<jsp:useBean id="edoAreaU" scope="page" class="aca.edo.EdoAreaUtil"/>

<%
	String edoId		= request.getParameter("edoId");
	String respuesta	= "";	
	
	int accion			= request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));	
	ArrayList<EdoMaestroPreg> lisPreguntas = null;
	ArrayList<EdoArea> lisAreas			  = null;
	
	switch(accion){
		case 2:{//Grabar y Modificar
			String preguntaId = request.getParameter("preguntaId")==null?"-1":request.getParameter("preguntaId");
			edoMaestroPreg.setEdoId(edoId);
			edoMaestroPreg.setPregunta(request.getParameter("pregunta"));
			edoMaestroPreg.setTipo(request.getParameter("tipo"));
			edoMaestroPreg.setOrden(request.getParameter("orden"));
			
			edoMaestroPreg.setAreaId(request.getParameter("areaId"));
			
			
			if(preguntaId.equals("-1") || preguntaId.trim().equals("")){//Grabar
				edoMaestroPreg.setPreguntaId(edoMaestroPregU.maximoReg(conEnoc, edoId));
				if(edoMaestroPregU.insertReg(conEnoc, edoMaestroPreg)){
					respuesta = "<font size='3' color='blue'><b>Se guard&oacute; correctamente la Pregunta!!!</b></font>";
				}else{
					respuesta = "<font size='3' color='red'><b>Ocurri&oacute; un error al guardar. Int&eacute;ntelo de nuevo!</b></font>";
				}
			}else{//Modificar
				edoMaestroPreg.setPreguntaId(request.getParameter("preguntaId"));
				if(edoMaestroPregU.updateReg(conEnoc,edoMaestroPreg)){ 
					respuesta = "<font size='3' color='blue'><b>Se modific&oacute; correctamente la Pregunta!!!</b></font>";
				}else{
					respuesta = "<font size='3' color='red'><b>Ocurri&oacute; un error al modificar. Int&eacute;ntelo de nuevo!</b></font>";
				}
			}
			edoMaestroPreg = new EdoMaestroPreg();
		}break;
		case 3:{//Borrar
			edoMaestroPreg.setEdoId(edoId);
			edoMaestroPreg.setPreguntaId(request.getParameter("preguntaId"));
			if(edoMaestroPregU.deleteReg(conEnoc,request.getParameter("preguntaId"), edoId)){
				respuesta = "<font size='3' color='blue'><b>Se borr&oacute; correctamente la Pregunta!!!</b></font>";
			}else{
				respuesta = "<font size='3' color='red'><b>Ocurri&oacute; un error al borrar. Int&eacute;ntelo de nuevo!</b></font>";
			}
		}break;
		case 4:{//Consultar
			edoMaestroPreg.mapeaRegId(conEnoc, request.getParameter("preguntaId"), edoId);
		}break;
	}
	
	edo = EdoU.mapeaRegId(conEnoc, edoId);
	lisPreguntas = edoMaestroPregU.getListPreg(conEnoc, edoId, "ORDER BY ORDEN");	
	lisAreas	 = edoAreaU.getListAll(conEnoc, "ORDER BY AREA_ID");
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
<div class="container-fluid">
	<h2>Opinión Estudiantil - <%=edo.getNombre() %><small class="text-muted fs-5">( Periodo: <%=aca.edo.EdoPeriodoUtil.getPeriodoNombre(conEnoc,edo.getPeriodoId())%> )</small></h2>
	<div class="alert alert-info">
		<a href="cuestionarios" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>
	</div>
	<table style="width:100%">		
		<tr><td align="center"><%=respuesta %></td></tr>				
	</table>
	<form id="forma" name="forma" action="preguntas?edoId=<%=edoId %>" method="post">
<%	if(accion == 4){ %>
		<input type="hidden" id="preguntaId" name="preguntaId" value="<%=edoMaestroPreg.getPreguntaId() %>" />
<%	} %>
	<table style="margin: 0 auto;" class="table table-sm table-bordered">
	<tr>
		<td>Pregunta <b><font color="#AE2113">*</font></b></td>
		<td><input id="pregunta" name="pregunta" value="<%=edoMaestroPreg.getPregunta() %>" maxlength="280" size="50" /></td>
	</tr>
	<tr>
		<td>Orden <b><font color="#AE2113">*</font></b></td>
		<td><input id="orden" name="orden" value="<%=edoMaestroPreg.getOrden() %>" maxlength="2" size="2" /></td>
	</tr>
	<tr>
		<td><spring:message code="aca.Tipo"/></td>
		<td>
			<select id="tipo" name="tipo">
				<option value="O"<%=edoMaestroPreg.getTipo().equals("O")?" Selected":"" %>>Opci&oacute;n &uacute;nica</option>
				<option value="D"<%=edoMaestroPreg.getTipo().equals("D")?" Selected":"" %>>Directa</option>
			</select>
		</td>
	</tr>			
	<tr>			
		<td>Área</td>					
		<td>
			<select id="areaId" name="areaId" class="form-select" style="width:300px;">
			
			<%
				for(int i = 0; i < lisAreas.size(); i++){
				edoArea = (EdoArea) lisAreas.get(i);
			%>
				<option value="<%=edoArea.getAreaId() %>"<%=edoArea.getAreaId().equals(edoArea.getAreaId())?" Selected":"" %>><%=edoArea.getAreaNombre() %></option>
			<%
			  }
			%>				
			</select>
		</td>			
	</tr>			
	<tr>
		<td colspan="2" align="center"><input type="submit" value="Guardar" onclick="guardar();" /></td>
	</tr>
	</table>
	</form>	
	<table class="table table-sm table-bordered">
		<tr>
			<td colspan="6">&nbsp;</td>
		</tr>
		<tr>
			<th width="40px"><spring:message code="aca.Operacion"/></th>
			<th><spring:message code="aca.Numero"/></th>
			<th>Pregunta</th>			
			<th>Orden</th>
			<th><spring:message code="aca.Tipo"/></th>
			<th>Área</th>
		</tr>		
<%
	for(int i = 0; i < lisPreguntas.size(); i++){
		edoMaestroPreg = (EdoMaestroPreg) lisPreguntas.get(i);
%>
		<tr>
			<td align="center">
				<img title="Editar" src="../../imagenes/editar.gif" onclick="modificar('<%=edoMaestroPreg.getPreguntaId() %>');" title="Modificar" class="button" />
				<img title="Eliminar" src="../../imagenes/no.png" onclick="borrar('<%=edoMaestroPreg.getPreguntaId() %>');" title="Eliminar" class="button" />
			</td>
			
			<td><%=i+1 %></td>
			<td><%=edoMaestroPreg.getPregunta() %></td>			
			<td align="center"><%=edoMaestroPreg.getOrden() %></td>
			<td align="center"><%=edoMaestroPreg.getTipo().equals("O")?"Opci&oacute;n":"Directa" %></td>
			<td align="center"><%=edoMaestroPreg.getAreaId() %></td>
		</tr>
<%
	}
%>
	</table>
</div>	
</body>
<%@ include file= "../../cierra_enoc.jsp" %>