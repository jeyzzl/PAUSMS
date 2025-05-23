<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="InvEvento" scope="page" class="aca.investiga.InvEvento"/>
<jsp:useBean id="InvEventoU" scope="page" class="aca.investiga.InvEventoUtil"/>
<jsp:useBean id="InvProyectoU" scope="page" class="aca.investiga.InvProyectoUtil"/>
<jsp:useBean id="Usuario" scope="page" class="aca.vista.Usuarios"/>

<% 
	String codigo			= (String) session.getAttribute("codigoPersonal");
	String fechaHoy 		= aca.util.Fecha.getHoy();
	
	String accion 			= request.getParameter("Accion")==null?"":request.getParameter("Accion");
	String folio			= request.getParameter("Folio");
		 
	String nombreUsuario	= " ";
	String msj				= " ";
	String maximo			= " ";
	
	Usuario.setCodigoPersonal(codigo);
	if (Usuario.existeReg(conEnoc)){
		Usuario.mapeaRegId(conEnoc, codigo);
		nombreUsuario	= Usuario.getNombre()+" "+Usuario.getApellidoPaterno()+" "+Usuario.getApellidoMaterno();
	}else{
		nombreUsuario = "¡ No existe !";
	}
	
	ArrayList<aca.investiga.InvProyecto> lisProyectosEmp = InvProyectoU.getListProyectosEmpleado(conEnoc, codigo," ORDER BY PROYECTO_ID, PROYECTO_NOMBRE ");
	
	//OPERACIONES A REALIZAR 
	conEnoc.setAutoCommit(false);
	
	if(accion.equals("0")){
		InvEvento.setCodigoPersonal(codigo);
		maximo = InvEventoU.maxReg(conEnoc, codigo);
		InvEvento.setFolio(maximo);
		System.out.println("mqximo" + maximo);
		System.out.println("folio" + InvEvento.getFolio());
	}else if(accion.equals("1")) {
		InvEvento.setFolio(maximo);
		InvEvento.setCodigoPersonal(codigo);
		InvEvento.setProyectoId(request.getParameter("proyectoId"));
		InvEvento.setFechaSolicitud(fechaHoy);
		InvEvento.setFechaInicio(request.getParameter("fechaIni"));
		InvEvento.setDescripcion(request.getParameter("descripcion"));
		InvEvento.setDias(request.getParameter("dias"));
		InvEvento.setViaticos(request.getParameter("viaticos"));
		InvEvento.setGastos(request.getParameter("gastos"));
		InvEvento.setHospedaje(request.getParameter("hospedaje"));
		InvEvento.setLugar(request.getParameter("lugar"));
		InvEvento.setNombreEvento(request.getParameter("nombre"));
		InvEvento.setParticipa(request.getParameter("tipoP"));
		InvEvento.setTipoBeca(request.getParameter("tipoB"));
		InvEvento.setTipoEvento(request.getParameter("tipoE"));
		InvEvento.setTransporte(request.getParameter("transporte"));
		InvEvento.setAlumnos("");
		InvEvento.setEstado("0");
		
		if(InvEventoU.existeReg(conEnoc, codigo, InvEvento.getFolio())==false){
			
			if(InvEventoU.insertReg(conEnoc, InvEvento)){
				msj = "<div class='alert alert-success'><button class='close' data-dismiss='alert'>&times;</button> El Registro se guardó correctamente</div>";
				conEnoc.commit();
			}else{
				msj = "<div class='alert alert-error'><button class='close' data-dismiss='alert'>&times;</button> Ocurrió un error al guardar el registro</div>";
			}
		}else{//Modifica
			
			if(InvEventoU.updateReg(conEnoc, InvEvento)){
				msj = "<div class='alert alert-success'><button class='close' data-dismiss='alert'>&times;</button> El Registro se Editó correctamente</div>";
				conEnoc.commit();
			}else{
				msj = "<div class='alert alert-error'><button class='close' data-dismiss='alert'>&times;</button> Ocurrió un error al editar el registro</div>";
			}
			
		}
	}else if(accion.equals("2")){
		InvEvento.setFolio(folio);
		InvEvento.setCodigoPersonal(codigo);
		if (InvEventoU.existeReg(conEnoc, codigo, InvEvento.getFolio()) == true) {
			if (InvEventoU.deleteReg(conEnoc, codigo, InvEvento.getFolio())) {
				msj = "<div class='alert alert-success'><button class='close' data-dismiss='alert'>&times;</button> El Registro se Eliminó correctamente</div>";
				conEnoc.commit();
			} else {
				msj = "<div class='alert alert-error'><button class='close' data-dismiss='alert'>&times;</button> Ocurrió un error al eliminar el registro</div>";
			}
		} else {
			msj = "<div class='alert alert-error'><button class='close' data-dismiss='alert'>&times;</button> Este Registro no existe</div>";
		}
	}else if((accion.equals("3"))){
		InvEvento.setFolio(folio);
		InvEvento.setCodigoPersonal(codigo);
		InvEvento.mapeaRegId(conEnoc, codigo, folio);
	}
	
	conEnoc.setAutoCommit(true);	
%>
<html>
<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />

<body>
<div class="container-fluid">
	<h1>Solicitud de apoyo para participar en evento académico/científico</h1>
	<br />
	<%=msj %>
	<div class="alert alert-info">
		<a href="evento" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;			 	
	</div>
	<form name="forma" id="forma" action="accion" method="post">
	<input type="hidden" name="Accion" id="Accion" />
	<p>
		<label for="Folio"><spring:message code="aca.Clave"/></label>
		<input name="Folio" type="text" id="Folio"  value="<%= InvEvento.getFolio()%>" readonly>
	</p>
	<p>
		<label for="proyectoId" ><spring:message code="aca.Nombre"/> del proyecto</label>
		<select name="proyectoId" id="proyectoId" class="chosen" style="width:600px;" >
			<option value="1">Selecciona el nombre del proyecto</option>
		<%
			for(aca.investiga.InvProyecto proyectoEmp : lisProyectosEmp){
		%>
				<option value="<%= proyectoEmp.getProyectoId() %>" <%if(proyectoEmp.getProyectoId().equals(InvEvento.getProyectoId()))out.print("selected"); %>>
				  <%= proyectoEmp.getProyectoNombre() %>
				</option>
		<%				
			}
		%>
		</select>
	</p>
	<p>
		<label for="fechaIni">Fecha de inicio</label>
		<input name="fechaIni" type="text" id="fechaIni" size="12" maxlength="10" data-date-format="dd/mm/yyyy" onfocus="focusFecha(this);" value="<%= InvEvento.getFechaInicio()==null?fechaHoy:InvEvento.getFechaInicio()%>">
	</p>
	<p>
		<label for="lugar">Lugar del evento</label>
		<input class="input-xxlarge" name="lugar" type="text" id="lugar"  value="<%= InvEvento.getLugar()==null?"":InvEvento.getLugar()%>">
	</p>
	<p> 
		<label for="tipoE">Tipo de evento</label>
		<select name="tipoE" id="tipoE">
			<option value="1" <%if(InvEvento.getTipoEvento()!=null && InvEvento.getTipoEvento().equals("1"))out.print("selected"); %>>Congreso</option>
			<option value="2" <%if(InvEvento.getTipoEvento()!=null && InvEvento.getTipoEvento().equals("2"))out.print("selected"); %>>Workshop</option>
			<option value="3" <%if(InvEvento.getTipoEvento()!=null && InvEvento.getTipoEvento().equals("3"))out.print("selected"); %>>Jornada</option>
		</select>
	</p>
	<p>
		<label for="dias">Días de duración</label>
		<input class="input-small" name="dias" type="text" id="dias"  value="<%= InvEvento.getDias()==null?"":InvEvento.getDias()%>">
	</p>
	<p>
		<label for="nombre"><spring:message code="aca.Nombre"/> del evento</label>
		<textarea name="nombre" id="nombre" cols="100" rows="3"><%= InvEvento.getNombreEvento()==null?"":InvEvento.getNombreEvento() %></textarea>
	</p>
	<p> 
		<label for="tipoP">Tipo de participación</label>
		<select name="tipoP" id="tipoP">
			<option value="1" <%if(InvEvento.getParticipa()!=null && InvEvento.getParticipa().equals("1"))out.print("selected"); %>>Ponencia</option>
			<option value="2" <%if(InvEvento.getParticipa()!=null && InvEvento.getParticipa().equals("2"))out.print("selected"); %>>Póster</option>
			<option value="3" <%if(InvEvento.getParticipa()!=null && InvEvento.getParticipa().equals("3"))out.print("selected"); %>>Chairman</option>
		</select>
	</p>	
	<p> 
		<label for="tipoB">Tipo de beca</label>
		<select name="tipoB" id="tipoB">
			<option value="1" <%if(InvEvento.getTipoBeca()!=null && InvEvento.getTipoBeca().equals("1"))out.print("selected"); %>>Internacional (Hasta 10,000)</option>
			<option value="2" <%if(InvEvento.getTipoBeca()!=null && InvEvento.getTipoBeca().equals("2"))out.print("selected"); %>>Nacional (Hasta 8,000)</option>
			<option value="3" <%if(InvEvento.getTipoBeca()!=null && InvEvento.getTipoBeca().equals("3"))out.print("selected"); %>>Regional (Hasta 5,000)</option>
		</select>
	</p>
	<p>
		<label for="hospedaje">Hospedaje</label>
		<input class="input-small" name="hospedaje" type="text" id="hospedaje"  value="<%= InvEvento.getHospedaje()==null?"":InvEvento.getHospedaje()%>"> (000.00)
	</p>
	<p>
		<label for="transporte">Transporte</label>
		<input class="input-small" name="transporte" type="text" id="transporte"  value="<%= InvEvento.getTransporte()==null?"":InvEvento.getTransporte()%>"> (000.00)
	</p>
	<p>
		<label for="viaticos">Viáticos</label>
		<input class="input-small" name="viaticos" type="text" id="viaticos"  value="<%= InvEvento.getViaticos()==null?"":InvEvento.getViaticos()%>"> (000.00)
	</p>
	<p>
		<label for="gastos">Total de gastos</label>
		<input class="input-small" name="gastos" type="text" id="gastos"  value="<%= InvEvento.getGastos()==null?"":InvEvento.getGastos()%>"> (000.00)
	</p>
	<p>
		<label for="descripcion"><spring:message code='aca.Descripcion'/></label>
		<textarea name="descripcion" id="descripcion" cols="100" rows="3"><%= InvEvento.getDescripcion()==null?"":InvEvento.getDescripcion() %></textarea>
	</p>
	<div class="alert alert-info">
		<%if(accion.equals("2")){ %>
			<a href="accion?Accion=2&folio=<%= InvEvento.getFolio()%>" class="btn btn-danger btn-large"><i class="icon-trash icon-white"></i> Eliminar</a>
<%
		}else{
%>
			<a class="btn btn-primary" onclick="javascript:Guardar();"><i class="fas fa-check"></i> Guardar</a>
<% 
		} 
%>
		<a href="proyecto" class="btn btn-danger"><i class="fas fa-trash-alt"></i> Cancelar</a>
	</div>
</form>
</div>
</body>

<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script>
	jQuery(".chzn-select").chosen(); 
	jQuery(".chosen").chosen();
	jQuery('#fechaIni').datepicker();
</script>
<script>
	function Guardar() {
		if (document.forma.Folio.value != ""
		 && document.forma.proyectoId.value != ""
		 && document.forma.fechaIni.value != ""
		 && document.forma.lugar.value != ""
		 && document.forma.tipoE.value != ""
		 && document.forma.dias.value != ""
		 && document.forma.nombre.value != ""
		 && document.forma.tipoP.value != ""
		 && document.forma.tipoB.value != ""
		 && document.forma.hospedaje.value != ""
		 && document.forma.transporte.value != ""
		 && document.forma.viaticos.value != ""
		 && document.forma.gastos.value != ""
		 && document.forma.descripcion.value != "") {
			document.forma.Accion.value = "1";
			document.forma.submit();
		} else {
			alert("¡Completa todos los campos!");
		}
	}
</script>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>