<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.util.Fecha"%>
<jsp:useBean id="mapaNuevoCurso" class="aca.plan.MapaNuevoCurso" scope="page"/>
<jsp:useBean id="mapaNuevoCursoU" class="aca.plan.MapaNuevoCursoUtil" scope="page"/>
<jsp:useBean id="mapaNuevoPlan" class="aca.plan.MapaNuevoPlan" scope="page"/>
<%
	String planId			= request.getParameter("planId");
	String versionId		= request.getParameter("versionId");
	String cursoId			= request.getParameter("cursoId");
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	String respuesta		= "";
	int accion				= Integer.parseInt(request.getParameter("Accion")==null?"0":request.getParameter("Accion"));
	
	boolean esAdmin			= Boolean.parseBoolean(session.getAttribute("admin")+"");
	
	mapaNuevoPlan.mapeaRegId(conEnoc, planId, versionId);
	
	switch(accion){
		case 2:{// Guardar
			mapaNuevoCurso.setPlanId(planId);
			mapaNuevoCurso.setCursoId(cursoId);
			mapaNuevoCurso.setVersionId(versionId);
			if(mapaNuevoCursoU.existeReg(conEnoc, cursoId, planId, versionId)){
				mapaNuevoCurso.mapeaRegId(conEnoc, cursoId, planId, versionId);
				String claveTmp = mapaNuevoCurso.getClave();
				mapaNuevoCurso.setClave(request.getParameter("clave"));
				mapaNuevoCurso.setNombre(request.getParameter("nombre"));
				mapaNuevoCurso.setCiclo(request.getParameter("ciclo"));
				mapaNuevoCurso.setUbicacion(request.getParameter("ubicacion"));
				mapaNuevoCurso.setSeriacion(request.getParameter("seriacion"));
				mapaNuevoCurso.setHst(request.getParameter("hst"));
				mapaNuevoCurso.setHsp(request.getParameter("hsp"));
				mapaNuevoCurso.setThs(request.getParameter("ths"));
				mapaNuevoCurso.setHt(request.getParameter("ht"));
				mapaNuevoCurso.setCreditos(request.getParameter("creditos"));
				mapaNuevoCurso.setDescripcion(request.getParameter("descripcion"));
				mapaNuevoCurso.setCompetencia(request.getParameter("competencia"));
				mapaNuevoCurso.setHei(request.getParameter("hei"));
				mapaNuevoCurso.setHfd(request.getParameter("hfd"));
				mapaNuevoCurso.setHss(request.getParameter("hss"));
				mapaNuevoCurso.setHas(request.getParameter("has"));
				if(mapaNuevoCursoU.existeClave(conEnoc, mapaNuevoCurso.getClave(), planId, versionId, cursoId) && esAdmin){
					respuesta = "<font size=2 color=red><b>La clave ["+mapaNuevoCurso.getClave()+"] ya est&aacute; asignada en otra materia<br>La materia no se modific&oacute;. Intentelo de nuevo con otra clave</b></font>";
					mapaNuevoCurso.setClave(claveTmp);
				}else{
					if(mapaNuevoCursoU.updateReg(conEnoc, mapaNuevoCurso)){
						response.sendRedirect("materias?Accion=5&planId="+planId+"&versionId="+versionId);
					}else{
						respuesta = "<font size=2 color=red><b>Ocurri&oacute; un error al modificar la materia. Int&eacute;ntelo de nuevo</b></font>";
					}
				}
			}else{
				mapaNuevoCurso.setCursoId(mapaNuevoCursoU.maximoReg(conEnoc));
				mapaNuevoCurso.setClave(request.getParameter("clave"));
				mapaNuevoCurso.setNombre(request.getParameter("nombre"));
				mapaNuevoCurso.setCiclo(request.getParameter("ciclo"));
				mapaNuevoCurso.setFCreada(Fecha.getHoy());
				mapaNuevoCurso.setCodigoMaestro("-");
				mapaNuevoCurso.setUbicacion(request.getParameter("ubicacion"));
				mapaNuevoCurso.setSeriacion(request.getParameter("seriacion"));
				mapaNuevoCurso.setHst(request.getParameter("hst"));
				mapaNuevoCurso.setHsp(request.getParameter("hsp"));
				mapaNuevoCurso.setThs(request.getParameter("ths"));
				mapaNuevoCurso.setHt(request.getParameter("ht"));
				mapaNuevoCurso.setCreditos(request.getParameter("creditos"));
				mapaNuevoCurso.setDescripcion(request.getParameter("descripcion"));
				mapaNuevoCurso.setCompetencia(request.getParameter("competencia"));
				mapaNuevoCurso.setEstado("1");
				mapaNuevoCurso.setHei(request.getParameter("hei"));
				mapaNuevoCurso.setHfd(request.getParameter("hfd"));
				mapaNuevoCurso.setHss(request.getParameter("hss"));
				mapaNuevoCurso.setHas(request.getParameter("has"));
				if(mapaNuevoCursoU.existeClave(conEnoc, mapaNuevoCurso.getClave(), planId, versionId, cursoId)){
					respuesta = "<font size=2 color=red><b>La clave ["+mapaNuevoCurso.getClave()+"] ya est&aacute; asignada en otra materia<br>La materia no se grab&oacute;. Intentelo de nuevo con otra clave</b></font>";
					mapaNuevoCurso.setClave("");
				}else{
					if(mapaNuevoCursoU.insertReg(conEnoc, mapaNuevoCurso)){
						response.sendRedirect("materias?Accion=6&planId="+planId+"&versionId="+versionId);
					}else{
						respuesta = "<font size=2 color=red><b>Ocurri&oacute; un error al guardar la materia. Int&eacute;ntelo de nuevo</b></font>";
					}
				}
			}
		}break;
		case 3:{// Borrar maestro
			mapaNuevoCurso.mapeaRegId(conEnoc, cursoId, planId, versionId);
			mapaNuevoCurso.setCodigoMaestro(mapaNuevoCurso.getCodigoMaestro().replaceFirst(request.getParameter("codigoMaestro")+"-", ""));
			if(mapaNuevoCursoU.updateReg(conEnoc, mapaNuevoCurso)){
				respuesta = "<font size=2 color=blue><b>El maestro fu&eacute; borrado</b></font>";
			}else{
				respuesta = "<font size=2 color=red><b>Ocurri&oacute; un error al borrar al maestro. Int&eacute;ntelo de nuevo</b></font>";
			}
		}break;
		case 5:{// Agregar maestro
			mapaNuevoCurso.mapeaRegId(conEnoc, cursoId, planId, versionId);
			if(mapaNuevoCurso.getCodigoMaestro().indexOf(request.getParameter("codigoMaestro")) == -1){//Si el maestro no esta ya dado de alta
				mapaNuevoCurso.setCodigoMaestro(mapaNuevoCurso.getCodigoMaestro()+request.getParameter("codigoMaestro")+"-");
				if(mapaNuevoCursoU.updateReg(conEnoc, mapaNuevoCurso)){
					respuesta = "<font size=2 color=blue><b>El maestro fu&eacute; agregado correctamente</b></font>";
				}else{
					respuesta = "<font size=2 color=red><b>Ocurri&oacute; un error al agregar al maestro. Int&eacute;ntelo de nuevo</b></font>";
				}
			}else{
				respuesta = "<font size=2 color=blue><b>El maestro ya esta dado de alta en esta materia</b></font>";
			}
		}break;
	}
	
	if(cursoId != null){
		mapaNuevoCurso.mapeaRegId(conEnoc, cursoId, planId, versionId);
	}
%>
<head>
	<script type="text/javascript">
		function revisaForma(){
			if(document.forma.nombre.value != ""){
				document.forma.submit();
			}else{
				alert("Llene todos los campos requeridos(*) para poder guardar");
			}
		}

		function borrarMaestro(codigoMaestro, nombre){
			if(confirm("Esta seguro que desea borrar al maestro ("+codigoMaestro+") "+nombre)){
				document.location = "editaMateria?Accion=3&codigoMaestro="+codigoMaestro+"&planId=<%=planId %>&versionId=<%=versionId %>&cursoId=<%=cursoId %>";
			}
		}

		var btnAgregarMaestro;
		function buscarMaestro(obj){
			btnAgregarMaestro = $(obj);
			$(document.body).insert({
				bottom: '<div id="buscar" style="background-color: white; border: solid 1px blue; position: absolute; z-index: 10;"></div><div id="resultado" align="center"></div>'
			});
			var div = $("buscar");
			div.innerHTML = '<input type="text" class="text" id="frase" onkeypress="if(window.event.keyCode==13)realizarBusqueda();" />'+
							'<input type="button" class="btn btn-primary" value="Buscar" onclick="realizarBusqueda();" />'+
							'<input type="button" class="btn btn-primary" value="Cancelar" onclick="cancelarBusqueda();" />';
			div.clonePosition(btnAgregarMaestro, {setWidth: false, setHeight: false});
			btnAgregarMaestro.disabled = true;
			$("frase").focus();
			
		}

		function cancelarBusqueda(){
			if(btnAgregarMaestro){
				btnAgregarMaestro.disabled = false;
				$("buscar").remove();
				$("resultado").remove();
				btnAgregarMaestro = null;
			}
		}

		function realizarBusqueda(){
			var url = 'editaMateriaAccion'+
					  '?Accion=5'+
					  '&frase='+$("frase").value;
			var div = $("resultado");
			div.setStyle({
				position: 'absolute',
				zIndex: '11',
				backgroundColor: 'white',
				border: 'solid 1px blue',
				width: '350px',
				height: '300px',
				overflow: 'auto'
			});
			div.clonePosition($("buscar"),{setWidth: false, setHeight: false});
			div.style.top = (div.offsetTop+$("buscar").offsetHeight)+"px";
			new Ajax.Request(url, {
				method: 'get',
				onSuccess: function (req){
					$("resultado").innerHTML = req.responseText;
				},
				onFailure: function (req){
					alert("No se pudo conectar a la pagina para buscar. Reportelo a sistemas");
				},
				onCreate: function(req){
					$("resultado").innerHTML = '<img src="../../imagenes/cargando.gif" />';
				}
			});
		}

		function agregaMaestro(codigoMaestro){
			document.location = "editaMateria?Accion=5&codigoMaestro="+codigoMaestro+"&planId=<%=planId %>&versionId=<%=versionId %>&cursoId=<%=cursoId %>";
		}
	</script>
</head>
<body onload="if($('clave')) $('clave').focus();">
<div class="container-fluid">
	<h2>Editar Materia</h2>
	<div class="alert alert-info">
		<a href="materias?planId=<%=planId %>&versionId=<%=versionId %>" class="btn btn-primary">&lsaquo;&lsaquo; <spring:message code="aca.Regresar"/></a>		
	</div>	
<%
	if(!respuesta.equals("")){
%>
	<table style="margin: 0 auto;">
		<tr>
			<td><%=respuesta %></td>
		</tr>
	</table>
<%
	}
%>	
	<br>
	<form id="forma" name="forma" action="editaMateria?Accion=2&planId=<%=planId %>&versionId=<%=versionId %>" method="post">
	<table style="margin: 0 auto;" style="border: dotted 1px gray;">
		<tr>
			<td><b><spring:message code="aca.Clave"/></b></td>
			<td>
<%
	if(cursoId != null){
		if(!esAdmin){
%>
				<input type="hidden" id="clave" name="clave" value="<%=mapaNuevoCurso.getClave() %>" />
<%
		}
%>
				<input type="hidden" id="cursoId" name="cursoId" value="<%=cursoId %>" />
				<input type="text" class="text" id="clave" name="clave" value="<%=mapaNuevoCurso.getClave().trim() %>" size="15" maxlength="15" <%=!esAdmin?"Disabled ":"" %>/>
<%
	}else{
%>
				<input type="text" class="text" id="clave" name="clave" size="15" maxlength="15" />
<%
	}
%>
			</td>
		</tr>
		<tr>
			<td><b>Asignatura<font color="#AE2113"> *</font></b></td>
			<td>
<%
	if(esAdmin){
%>
				<input type="text" class="text" id="nombre" name="nombre" value="<%=mapaNuevoCurso.getNombre() %>" size="30" maxlength="180" />
<%
	}else{
%>
				<input type="text" value="<%=mapaNuevoCurso.getNombre() %>" disabled />
				<input type="hidden" id="nombre" name="nombre" value="<%=mapaNuevoCurso.getNombre() %>"/>
<%
	}
%>
			</td>
		</tr>
		<tr>
			<td><b>Semestre</b></td>
			<td>
<%
	if(esAdmin){
%>
				<input type="text" class="text" id="ciclo" name="ciclo" value="<%=mapaNuevoCurso.getCiclo() %>" size="2" maxlength="2" /> [99]
<%
	}else{
%>
				<input type="text" value="<%=mapaNuevoCurso.getCiclo() %>" disabled />
				<input type="hidden" id="ciclo" name="ciclo" value="<%=mapaNuevoCurso.getCiclo() %>" />
<%
	}
%>
			</td>
		</tr>
<%
	if(cursoId != null){
%>
		<tr>
			<td><b><spring:message code="aca.Maestro"/></b></td>
			<td>
				<table style="width:100%">
<%
		String[] maestros = mapaNuevoCurso.getCodigoMaestro().split("-");
		for(int i = 1; i < maestros.length; i++){
			String nombre = aca.vista.MaestrosUtil.getNombreMaestro(conEnoc, maestros[i], "NOMBRE");
%>
					<tr class="ayuda alumno <%=maestros[i] %>">
						<td width="20px">
							<img class="button" title="Eliminar" src="../../imagenes/no.png" title="Eliminar" onclick="borrarMaestro('<%=maestros[i] %>', '<%=nombre %>');" />
						</td>
						<td width="80px"><%=maestros[i] %></td>
						<td><%=nombre %></td>
					</tr>
<%
		}
%>
					<tr>
						<td colspan="3"><input type="button" class="btn btn-primary" value="Agregar Maestro" onclick="buscarMaestro(this);" /></td>
					</tr>

				</table>
			</td>
		</tr>
<%
	}
%>
		<tr>
			<td><b>Seriacion</b></td>
			<td>
<%
	if(esAdmin){
%>
				<input type="text" class="text" id="seriacion" name="seriacion" value="<%=mapaNuevoCurso.getSeriacion() %>" size="40" maxlength="200" />
<%
	}else{
%>
				<input type="text" value="<%=mapaNuevoCurso.getSeriacion() %>" disabled />
				<input type="hidden" id="seriacion" name="seriacion" value="<%=mapaNuevoCurso.getSeriacion() %>" />
<%
	}
%>
			</td>
		</tr>
		<tr>
			<td><b>Objetivo de la Asignatura</b></td>
			<td>
				<textarea id="descripcion" name="descripcion" cols="40" rows="6"><%=mapaNuevoCurso.getDescripcion() %></textarea>
			</td>
		</tr>
		<tr>
			<td><b>L&iacute;nea Curricular</b></td>
			<td>
				<input type="text" class="text" id="ubicacion" name="ubicacion" value="<%=mapaNuevoCurso.getUbicacion() %>" size="40" maxlength="95" />
			</td>
		</tr>
		<tr>
			<td><b>HTS(Horas Te&oacute;ricas Semanales)</b></td>
			<td>
<%
	if(esAdmin && mapaNuevoPlan.getHts().equals("S")){
%>
				<input type="text" class="text" id="hst" name="hst" value="<%=mapaNuevoCurso.getHst() %>" size="2" maxlength="2" /> [99]
<%
	}else{
%>
				<input type="text" value="<%=mapaNuevoCurso.getHst() %>" size="2" maxlength="2" disabled />
				<input type="hidden" id="hst" name="hst" value="<%=mapaNuevoCurso.getHst() %>" />
<%
	}
%>
			</td>
		</tr>
		<tr>
			<td><b>HPS(Horas Pr&aacute;cticas Semanales)</b></td>
			<td>
<%
	if(esAdmin && mapaNuevoPlan.getHps().equals("S")){
%>
				<input type="text" class="text" id="hsp" name="hsp" value="<%=mapaNuevoCurso.getHsp() %>" size="2" maxlength="2" /> [99]
<%
	}else{
%>
				<input type="text" value="<%=mapaNuevoCurso.getHsp() %>" size="2" maxlength="2" disabled />
				<input type="hidden" id="hsp" name="hsp" value="<%=mapaNuevoCurso.getHsp() %>" />
<%
	}
%>
			</td>
		</tr>
		<tr class="ayuda mensaje Este campo ser&aacute; desplegado &uacute;nicamente si contiene alg&uacute;n dato">
			<td><b>HEI</b></td>
			<td>
<%
	if(esAdmin && mapaNuevoPlan.getHei().equals("S")){
%>
				<input type="text" class="text" id="hei" name="hei" value="<%=mapaNuevoCurso.getHei() %>" size="2" maxlength="2" /> [99]
<%
	}else{
%>
				<input type="text" value="<%=mapaNuevoCurso.getHei() %>" size="2" maxlength="2" disabled />
				<input type="hidden" id="hei" name="hei" value="<%=mapaNuevoCurso.getHei() %>" />
<%
	}
%>
			</td>
		</tr>
		<tr class="ayuda mensaje Este campo ser&aacute; desplegado &uacute;nicamente si contiene alg&uacute;n dato">
			<td><b>HFD</b></td>
			<td>
<%
	if(esAdmin && mapaNuevoPlan.getHfd().equals("S")){
%>
				<input type="text" class="text" id="hfd" name="hfd" value="<%=mapaNuevoCurso.getHfd() %>" size="2" maxlength="2" /> [99]
<%
	}else{
%>
				<input type="text" value="<%=mapaNuevoCurso.getHfd() %>" size="2" maxlength="2" disabled />
				<input type="hidden" id="hfd" name="hfd" value="<%=mapaNuevoCurso.getHfd() %>" />
<%
	}
%>
			</td>
		</tr>		
		<tr class="ayuda mensaje Este campo ser&aacute; desplegado &uacute;nicamente si contiene alg&uacute;n dato">
			<td><b>HSS</b></td>
			<td>
<%
	if(esAdmin && mapaNuevoPlan.getHss().equals("S")){
%>
				<input type="text" class="text" id="hss" name="hss" value="<%=mapaNuevoCurso.getHss()%>" size="2" maxlength="2" /> [99]
<%
	}else{
%>
				<input type="text" value="<%=mapaNuevoCurso.getHss() %>" size="2" maxlength="2" disabled />
				<input type="hidden" id="hss" name="hss" value="<%=mapaNuevoCurso.getHss() %>" />
<%
	}
%>
			</td>
		</tr>
		<tr class="ayuda mensaje Este campo ser&aacute; desplegado &uacute;nicamente si contiene alg&uacute;n dato">
			<td><b>HAS</b></td>
			<td>
<%
	if(esAdmin && mapaNuevoPlan.getHas().equals("S")){
%>
				<input type="text" class="text" id="has" name="has" value="<%=mapaNuevoCurso.getHas() %>" size="2" maxlength="2" /> [99]
<%
	}else{
%>
				<input type="text" value="<%=mapaNuevoCurso.getHas() %>" size="2" maxlength="2" disabled />
				<input type="hidden" id="has" name="has" value="<%=mapaNuevoCurso.getHas() %>" />
<%
	}
%>
			</td>
		</tr>	
		<tr>
			<td><b>THS(Total de Horas Semanales)</b></td>
			<td>
<%
	if(esAdmin){
%>
				<input type="text" class="text" id="ths" name="ths" value="<%=mapaNuevoCurso.getThs() %>" size="2" maxlength="2" /> [99]
<%
	}else{
%>
				<input type="text" value="<%=mapaNuevoCurso.getThs() %>" disabled />
				<input type="hidden" id="ths" name="ths" value="<%=mapaNuevoCurso.getThs() %>" />
<%
	}
%>
			</td>
		</tr>
		<tr>
			<td><b>HT(Horas Totales)</b></td>
			<td>
<%
	if(esAdmin){
%>
				<input type="text" class="text" id="ht" name="ht" value="<%=mapaNuevoCurso.getHt() %>" size="4" maxlength="4" /> [9999]
<%
	}else{
%>
				<input type="text" value="<%=mapaNuevoCurso.getHt() %>" disabled />
				<input type="hidden" id="ht" name="ht" value="<%=mapaNuevoCurso.getHt() %>" />
<%
	}
%>
			</td>
		</tr>
		<tr>
			<td><b><spring:message code='aca.Creditos'/></b></td>
			<td>
<%
	if(esAdmin){
%>
				<input type="text" class="text" id="creditos" name="creditos" value="<%=mapaNuevoCurso.getCreditos() %>" size="5" maxlength="6" /> [99.9999]
<%
	}else{
%>
				<input type="text" value="<%=mapaNuevoCurso.getCreditos() %>" disabled />
				<input type="hidden" id="creditos" name="creditos" value="<%=mapaNuevoCurso.getCreditos() %>" />
<%
	}
%>
			</td>
		</tr>
		<tr>
			<td><b>Competencia del perfil<br> que atiende la asignatura</b></td>
			<td>
				<textarea id="competencia" name="competencia" cols="40" rows="6"><%=mapaNuevoCurso.getCompetencia() %></textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center"><input type="button" class="btn btn-primary" value="Guardar" onclick="revisaForma();" /></td>
		</tr>
	</table>
	</form>
</div>
</body>

<%@ include file= "../../cierra_enoc.jsp" %>