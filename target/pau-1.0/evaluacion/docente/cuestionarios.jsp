<%@page import="aca.edo.EdoPeriodo"%>
<%@page import="aca.edo.Edo"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="edoPeriodo" scope="page" class="aca.edo.EdoPeriodo"/>
<jsp:useBean id="edoPeriodoU" scope="page" class="aca.edo.EdoPeriodoUtil"/>
<jsp:useBean id="edo" scope="page" class="aca.edo.Edo"/>
<jsp:useBean id="edoU" scope="page" class="aca.edo.EdoUtil"/>
<%
	String respuesta				= "";

	int accion						= request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));

	ArrayList<EdoPeriodo> lisPeriodo 	= null;
	ArrayList<Edo> lisEdo				= null;	
	
	switch(accion){
		case 2:{//Grabar y Modificar
			String edoId = request.getParameter("edoId")==null?"-1":request.getParameter("edoId");
			edo.setNombre(request.getParameter("nombre"));
			edo.setFInicio(request.getParameter("fInicio"));
			edo.setFFinal(request.getParameter("fFinal"));
			edo.setPeriodoId(request.getParameter("periodoId"));			
			edo.setTipo(request.getParameter("tipo"));
			edo.setModalidad(request.getParameter("modalidad"));
			edo.setEncabezado(request.getParameter("mensaje"));
			
			if(edoId.equals("-1") || edoId.trim().equals("")){//Grabar
				edo.setEdoId(edoU.maximoReg(conEnoc));
				if(edoU.insertReg(conEnoc, edo)){
					respuesta = "<font size='3' color='blue'><b>Se guard&oacute; correctamente la Evaluacion!!!</b></font>";
				}else{
					respuesta = "<font size='3' color='red'><b>Ocurri&oacute; un error al guardar. Int&eacute;ntelo de nuevo!</b></font>";
				}
			}else{//Modificar
				edo.setEdoId(edoId);
				if(edoU.updateReg(conEnoc,edo)){ 
					respuesta = "<font size='3' color='blue'><b>Se modific&oacute; correctamente la Evaluacion!!!</b></font>";
				}else{
					respuesta = "<font size='3' color='red'><b>Ocurri&oacute; un error al modificar. Int&eacute;ntelo de nuevo!</b></font>";
				}
			}
			edo = new Edo();
		}break;
		case 3:{//Borrar			
			if(edoU.deleteReg(conEnoc,request.getParameter("edoId"))){
				respuesta = "<font size='3' color='blue'><b>Se borr&oacute; correctamente la Evaluacion!!!</b></font>";
			}else{
				respuesta = "<font size='3' color='red'><b>Ocurri&oacute; un error al borrar. Int&eacute;ntelo de nuevo!</b></font>";
			}
		}break;
		case 4:{//Consultar
			edo = edoU.mapeaRegId(conEnoc, request.getParameter("edoId"));
		}break;
	}
	
	lisPeriodo						= edoPeriodoU.getListAll(conEnoc, "ORDER BY PERIODO_ID");
	lisEdo							= edoU.getTipo(conEnoc, "");
%>
<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
	<script type="text/javascript">
		function guardar(){
			if(document.forma.nombre.value != "" &&
			  document.forma.fInicio.value != "" &&
			  document.forma.tipo.value != "" &&
			  document.forma.fFinal.value != ""){
				document.forma.action += "?Accion=2";
				return true;
			}else{
				alert("Complete los campos requeridos(*) para poder guardar.");
			}
			return false;
		}
		
		function modificar(edoId){
			document.forma.action += "?Accion=4&edoId="+edoId;
			document.forma.submit();
		}
		
		function borrar(edoId){
			if(confirm("¿Está seguro que desea borrar el Cuestionario?")){
				document.forma.action += "?Accion=3&edoId="+edoId;
				document.forma.submit();
			}
		}
		
		function preguntas(edoId){
			document.forma.action = "preguntas?edoId="+edoId;
			document.forma.submit();
		}
		function checkSize(obj, event){
			if(event.keyCode != 8)
				if(obj.value.length > 1000)
					return false;
			return true;
		}
	</script>
</head>
<body>
<div class="container-fluid">
	<h2>Cuestionarios de Opini&oacute;n Estudiantil</h2>	
	<div class="alert alert-info"><%=respuesta %></div>
	<form id="forma" name="forma" action="cuestionarios" method="post">		
		<div class="row">
			<div class="col-3">
				<label for="periodoId">Periodo</label>			
				<select id="periodoId" name="periodoId">
<%
				for(int i = 0; i < lisPeriodo.size(); i++){
					edoPeriodo = (EdoPeriodo) lisPeriodo.get(i);
%>
						<option value="<%=edoPeriodo.getPeriodoId() %>"<%=edoPeriodo.getPeriodoId().equals(edo.getPeriodoId())?" Selected":"" %>><%=edoPeriodo.getPeriodoNombre() %></option>
<%
				}
%>
				</select>
<%
				if(accion == 4){
%>
					<input type="hidden" id="edoId" name="edoId" value="<%=edo.getEdoId() %>" />
<%
				}
%>
				<br><br>
				<label for="nombre"><spring:message code="aca.Nombre"/> <font color="#AE2113">*</font></label>					
				<input type="text" class="text" id="nombre" name="nombre" value="<%=edo.getNombre() %>" maxlength="100" size="30" />
				<br><br>
				<label for="tipo"><spring:message code="aca.Tipo"/> <font color="#AE2113">*</font></label>
				<select  id="tipo" name="tipo"  style="width: 280px;" >	         		
					<option value="A" <%if(edo.getTipo().equals("A"))out.print("selected"); %>>AutoEvaluación</option>
					<option value="P" <%if(edo.getTipo().equals("B"))out.print("selected"); %>>Pares</option>
					<option value="C" <%if(edo.getTipo().equals("C"))out.print("selected"); %>><spring:message code="aca.Coordinador"/></option>
				</select>
				<br><br>
				<label for="fInicio">Fecha Inicio <font color="#AE2113">*</font></label>					
				<input type="text" class="text" id="fInicio" data-date-format="dd/mm/yyyy" name="fInicio" value="<%=edo.getFInicio() %>" size="12" maxlength="10" />
				(DD/MM/AAAA)
				<br><br>
				<label for="fFinal">Fecha Final <font color="#AE2113">*</font></label>
				<input id="fFinal" name="fFinal" data-date-format="dd/mm/yyyy" value="<%=edo.getFFinal() %>" size="12" maxlength="10" />
				(DD/MM/AAAA)
			</div>
			<div class="span3">
				<label for="modalidad">Modalidades: <b><font color="#AE2113">*</font></label>			
				<input id="modalidad" name="modalidad" value="<%=edo.getModalidad() %>" size="12" maxlength="70" />&nbsp; (Todas: -0-)
				<br><br>
				<label for="CategoriaNombre">Instrucciones: <font color="#AE2113">*</font></label>					
				<textarea id="mensaje" name="mensaje" cols="40" rows="5" onkeypress="return checkSize(this, event);" ><%=edo.getEncabezado() %></textarea>
			</div>
		</div>
		<br>
		<div class="alert alert-info">
			<input class="btn btn-primary" type="submit" value="Guardar" onclick="return guardar();" />
		</div>
	</form>
	<table style="width:80%" class="table table-sm">
		<tr class="table-info">
			<th width="40px"><spring:message code="aca.Operacion"/></th>
			<th><spring:message code="aca.Numero"/></th>
			<th><spring:message code="aca.Nombre"/></th>
			<th><spring:message code="aca.Tipo"/></th>
			<th>F. Inicio</th>
			<th>F. Final</th>
			<th>Periodo</th>			
		</tr>
<%
	for(int i = 0; i < lisEdo.size(); i++){
		edo = (Edo) lisEdo.get(i);
%>
		<tr class="button">
			<td align="center">
			  <a class="fas fa-edit" href="javascript:modificar('<%=edo.getEdoId() %>');"></a>
			  <a class="fas fa-trash-alt" href="javascript:borrar('<%=edo.getEdoId() %>');"></a>
			</td>
			<td onclick="preguntas('<%=edo.getEdoId() %>');" title="Preguntas de este cuestionario"><%=i+1 %></td>
			<td onclick="preguntas('<%=edo.getEdoId() %>');" title="Preguntas de este cuestionario"><%=edo.getNombre() %></td>
			<td onclick="preguntas('<%=edo.getEdoId() %>');" title="Preguntas de este cuestionario"><%=edo.getTipo() %></td>
			<td onclick="preguntas('<%=edo.getEdoId() %>');" title="Preguntas de este cuestionario" align="center"><%=edo.getFInicio() %></td>
			<td onclick="preguntas('<%=edo.getEdoId() %>');" title="Preguntas de este cuestionario" align="center"><%=edo.getFFinal() %></td>
			<td onclick="preguntas('<%=edo.getEdoId() %>');" title="Preguntas de este cuestionario"><%=aca.edo.EdoPeriodoUtil.getPeriodoNombre(conEnoc, edo.getPeriodoId()) %></td>
		</tr>
<%
	}
%>
	</table>
	</div>
</body>
<script>
	jQuery('#fInicio').datepicker();
	jQuery('#fFinal').datepicker();
</script>
<%@ include file= "../../cierra_enoc.jsp" %>