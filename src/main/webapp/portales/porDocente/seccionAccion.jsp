<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%@ include file= "menuPortal.jsp" %>

<jsp:useBean id="Seccion" scope="page" class="aca.por.PorSeccion"/>
<jsp:useBean id="SeccionEmp" scope="page" class="aca.por.PorSeccionEmp"/>

<script type="text/javascript">

	function guardarImagen(){
		if(document.formaImagen.archivo.value != ""){
			document.formaImagen.btnGuardar.disabled = true;			
			document.formaImagen.btnGuardar.value = "Guardando...";
			document.formaImagen.submit();
		}else
			alert("Selecciona el archivo a subir");
	}
	
	function guardarArchivo(){
		if(document.formaArchivo.archivo.value != ""){
			document.formaArchivo.btnGuardar.disabled = true;			
			document.formaArchivo.btnGuardar.value = "Guardando...";
			document.formaArchivo.submit();
		}else
			alert("Selecciona el archivo a subir");
	}
	
	function GrabarCorto(){
		document.frmCorto.Accion.value="1";
		document.frmCorto.submit();		
	}
	
	function GrabarLargo(){
		document.frmLargo.Accion.value="2";
		document.frmLargo.submit();		
	}
	
	function GrabarLink(){
		document.formaEnlace.Accion.value="3";
		document.formaEnlace.submit();		
	}
	
</script>
<%
	String empleadoId			= session.getAttribute("codigoPersonal").toString();
	String portafolioId			= session.getAttribute("portafolioId").toString();
	String seccionId 			= request.getParameter("SeccionId")==null?"0":request.getParameter("SeccionId");	
	String accion 				= request.getParameter("Accion")==null?"0":request.getParameter("Accion"); 
	int accionFmt				= 0;
	
	if (!seccionId.equals("0")){
		Seccion.setPorId(portafolioId);
		Seccion.setSeccionId(seccionId);
		if (Seccion.existeReg(conEnoc)){
			Seccion.mapeaRegId(conEnoc, portafolioId, seccionId);
		}		
	}
	
	// Grabar un texto corto
	if (accion.equals("1")){
		
		SeccionEmp.setPorId(portafolioId);
		SeccionEmp.setCodigoPersonal(empleadoId);
		SeccionEmp.setSeccionId(seccionId);
		SeccionEmp.setTextoCorto(request.getParameter("Corto"));
		SeccionEmp.setTextoLargo("-");
		SeccionEmp.setComentario("-");
		if (SeccionEmp.existeReg(conEnoc)){
			// Modificar
			if (SeccionEmp.updateReg(conEnoc)){
				accionFmt = 3;
			}
		}else{
			//Grabar
			if (SeccionEmp.insertReg(conEnoc)){
				accionFmt = 1;
			}
		}		
	}
	
	// Grabar un texto largo
	if (accion.equals("2")){
		
		SeccionEmp.setPorId(portafolioId);
		SeccionEmp.setCodigoPersonal(empleadoId);
		SeccionEmp.setSeccionId(seccionId);
		SeccionEmp.setTextoCorto("-");
		SeccionEmp.setTextoLargo(request.getParameter("Largo"));
		SeccionEmp.setComentario("-");
		if (SeccionEmp.existeReg(conEnoc)){
			// Modificar
			if (SeccionEmp.updateReg(conEnoc)){
				accionFmt = 3;
			}
		}else{
			//Grabar
			if (SeccionEmp.insertReg(conEnoc)){
				accionFmt = 1;
			}
		}		
	}
	// Graba link
	if (accion.equals("3")){
		
		SeccionEmp.setPorId(portafolioId);
		SeccionEmp.setCodigoPersonal(empleadoId);
		SeccionEmp.setSeccionId(seccionId);
		SeccionEmp.setTextoCorto(request.getParameter("corto"));
		SeccionEmp.setTextoLargo("-");
		SeccionEmp.setComentario(request.getParameter("Comentario"));
		
		if (SeccionEmp.existeReg(conEnoc)){
			// Modificar
			if (SeccionEmp.updateReg(conEnoc)){
				accionFmt = 3;				
			}else{
				accionFmt = 4;
			}
		}else{
			//Grabar
			if (SeccionEmp.insertReg(conEnoc)){
				accionFmt = 1;				
			}else{
				accionFmt = 2;
			}
		}		
	}
	
	// Borrar la sección
	if (accion.equals("4")){
		SeccionEmp.setPorId(portafolioId);
		SeccionEmp.setCodigoPersonal(empleadoId);
		SeccionEmp.setSeccionId(seccionId);
		if (SeccionEmp.existeReg(conEnoc)){		
			if (SeccionEmp.deleteReg(conEnoc)){
				accionFmt = 5;
			}else{
				accionFmt = 6;
			}
		}
	}
	
	// Traer los datos guardados
	SeccionEmp.setPorId(portafolioId);
	SeccionEmp.setSeccionId(seccionId);
	SeccionEmp.setCodigoPersonal(empleadoId);
	if (SeccionEmp.existeReg(conEnoc)){		
		SeccionEmp.mapeaRegId(conEnoc, portafolioId, seccionId, empleadoId);
	}
%>
<div class="container-fluid">
	<h2><spring:message code="aca.Seccion"/><small class="text-muted fs-4">( <%= Seccion.getTitulo() %> <%= Seccion.getSeccionNombre() %> )</small></h2>
	<div class="alert alert-info"><a href="seccion.jsp" class="btn btn-primary"><i class="fas fa-arrow-left"></i> Regresar</a></div>	
	
<%	if(accionFmt==1){%>
		<div class="alert alert-info"><h3><spring:message code='aca.SeGuardoCorrectamente'/></h3></div>
<%	}else if(accionFmt==2){%>
		<div class="alert alert-danger"><h3><spring:message code='aca.NoSePudoGuardar'/></h3></div>
<%	}else if(accionFmt==3){%>
		<div class="alert alert-info"><h3><spring:message code='aca.SeModifico'/></h3></div>
<%	}else if(accionFmt==4){%>
		<div class="alert alert-danger"><h3><spring:message code='aca.NoSePudoModificar'/></h3></div>
<%	}else if(accionFmt==5){%>
		<div class="alert alert-info"><h3><spring:message code='aca.SeElimino'/></h3></div>
<%	}else if(accionFmt==6){%>
		<div class="alert alert-danger"><h3><spring:message code='aca.NoSePudoEliminar'/></h3></div>
<%	} %>
	<table style="width:50%" align="center" class="table table-condensed">
	<tr>
		<th colspan="2"><h4>Instrucciones: <%= Seccion.getInstrucciones() %> </h4></th>
	</tr>
	</table>
<% 	if (Seccion.getTipo().equals("C")){%>
	<form name="frmCorto" action="seccionAccion.jsp">
	<input name="Accion" type="hidden">
	<input name="SeccionId" type="hidden" value="<%=seccionId%>">
	
	<table style="width:50%" align="center" class="table table-condensed">
	<tr>
		<td colspan="2">
			<strong><label for=""><b><spring:message code='aca.Descripcion'/></b></label></strong>
			<textarea name="Corto" id="Corto" cols="100" rows="3"><%=SeccionEmp.getTextoCorto()%></textarea>
		</td>
	</tr>
	<tr><td colspan="2"><a href="javascript:GrabarCorto()" class="btn btn-primary"><spring:message code="aca.Grabar"/></a></td></tr>
	</table>
	</form>
<%	} %>	
<% 	if (Seccion.getTipo().equals("L")){%>
	<form name="frmLargo" action="seccionAccion.jsp">
	<input name="Accion" type="hidden">
	<input name="SeccionId" type="hidden" value="<%=seccionId%>">
	
	<table style="width:50%" align="center" class="table table-condensed">
	<tr>		
		<td colspan="2">
			<strong><label for=""><b><spring:message code='aca.Descripcion'/></b></label></strong>		
			<textarea name="Largo" id="Largo" cols="100" rows="7"><%=SeccionEmp.getTextoLargo()%></textarea>
		</td>
	</tr>
	<tr><td colspan="2"><a href="javascript:GrabarLargo()" class="btn btn-primary"><spring:message code="aca.Grabar"/></a></td></tr>
	</table>	
	</form>
<%	} %>	
<% 	if (Seccion.getTipo().equals("I")){%>


	<form name="formaImagen" id="formaImagen" enctype="multipart/form-data" action="guardarImagen?EmpleadoId=<%=empleadoId%>&SeccionId=<%=seccionId%>" method="post">
	
	<div class="col-md-6">
	<strong><label for="" class="control-label col-sm-2"><b><spring:message code='aca.Descripcion'/></b></label></strong>
	<br>
	<textarea  class="form-control" name="Comentario" id="Comentario" cols="100" rows="7"><%=SeccionEmp.getComentario()=="-"?"":SeccionEmp.getComentario() %></textarea>
	<br>
	<strong>Elige una imágen:</strong> <input type="file" id="archivo" name="archivo" />
	<br>
	<input type="button" id="btnGuardar" value="Guardar" onclick="guardarImagen();" />
	</div>
	

		
	</form>
	
	
<%	} %>
<% 	if (Seccion.getTipo().equals("A")){%>	
	<form name="formaArchivo" id="formaArchivo" enctype="multipart/form-data" action="guardarArchivo?EmpleadoId=<%=empleadoId%>&SeccionId=<%=seccionId%>" method="post">
	<table style="width:50%" align="center" class="table table-condensed">
	<tr>
		<td colspan="2">
			<label for=""><spring:message code='aca.Descripcion'/></label>		
			<textarea name="Comentario" id="Comentario" cols="100" rows="7">
				<%=SeccionEmp.getComentario()==null?"":SeccionEmp.getComentario() %>
			</textarea>
		</td>
	</tr>	
	<tr>
		<td><strong>Elige un archivo:</strong> <input type="file" id="archivo" name="archivo" /></td>			
	</tr>
	<tr>		
		<td><input type="button" id="btnGuardar" value="Guardar" onclick="guardarArchivo();" /></td>	
	</tr>
	</table>
	</form>		
<%	} %>
<%	if(Seccion.getTipo().equals("E")){ %>	
	<form name="formaEnlace" action="seccionAccion.jsp">
	<input name="Accion" type="hidden">
	<input name="SeccionId" type="hidden" value="<%=seccionId%>">
	<table style="width:50%" align="center" class="table table-condensed">
	<tr>
		<td><strong>Texto:</strong></td>
		<td>
			<textarea name="Comentario" id="Comentario" cols="100" rows="7"><%=SeccionEmp.getComentario()=="-"?"":SeccionEmp.getComentario() %></textarea>
		</td>
	</tr>
	<tr>
		<td><strong>Link del documento:</strong></td>
		<td>
			<textarea name="corto" id="corto" cols="100" rows="3"><%=SeccionEmp.getTextoCorto()==null?"":SeccionEmp.getTextoCorto() %></textarea>
		</td>
	</tr>
	<tr><td colspan="2"><a href="javascript:GrabarLink()" class="btn btn-primary"><spring:message code="aca.Grabar"/></a></td></tr>
	</table>
	</form>
<% } %>   
</div>
<script>
	jQuery('.seccion').addClass('active');
</script>
<%@ include file="../../cierra_enoc.jsp" %>