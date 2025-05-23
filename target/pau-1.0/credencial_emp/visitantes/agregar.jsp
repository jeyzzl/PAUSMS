<%@page import="aca.cred.spring.CredVisitante"%>
 
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>

<body>
<%
	CredVisitante visitante		= (CredVisitante)request.getAttribute("visitante");

	String mensaje			= request.getAttribute("mensaje")==null?"X":(String)request.getAttribute("mensaje");
	String colorMensaje		= request.getAttribute("colorMensaje")==null?"0":(String)request.getAttribute("colorMensaje");
	String accion 			= visitante.getCodigoPersonal().equals("")?"1":"2";
	String generoCodigo		= (String)request.getAttribute("generoCodigo");
	
	String codigoPersonal	= visitante.getCodigoPersonal()==null?"":visitante.getCodigoPersonal();
	String nombre 			= visitante.getNombre()==null?"":visitante.getNombre();
	String paterno 			= visitante.getPaterno()==null?"":visitante.getPaterno();
	String materno 			= visitante.getMaterno()==null?"":visitante.getMaterno();
	String rfid 			= visitante.getRfid()==null?"":visitante.getRfid();
	String rfidTag 			= visitante.getRfidTag()==null?"":visitante.getRfidTag();
	String estado 			= visitante.getEstado()==null?"":visitante.getEstado();
	String comentario 		= visitante.getComentario()==null?"":visitante.getComentario();
	
	if(codigoPersonal.equals("")){
		codigoPersonal = generoCodigo;
	}
	
	List<String> conFoto = (List<String>)request.getAttribute("conFoto");

%>
	<div class="container-fluid">
		<div class="alert alert-info">
			<a href="visitante" class="btn btn-primary"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
		</div>
<%
	if(!mensaje.equals("X")){
%>
		<div class="alert alert-"<%=colorMensaje%>>
			<%=mensaje%>
		</div>
<%
	}
%>
		<form name="frmVisitante" action="agregar">
			<input type="hidden" name="Accion" value="<%=accion%>">
			<div class="row">
				<div class="col-3">
					<label for="CodigoPersonal">Codigo Personal</label>			
					<input type="text"  name="CodigoPersonal" id="CodigoPersonal" class="form-control" value="<%=codigoPersonal%>" readonly="readonly" />
					<br><br>
					<label for="Nombre">Nombre</label>			
					<input type="text"  name="Nombre" id="Nombre" class="form-control" value="<%=nombre%>" />
					<br><br>
					<label for="Paterno">Paterno</label>			
					<input type="text" name="Paterno" id="Paterno" class="form-control" value="<%=paterno%>" />
					<br><br>
					<label for="Materno">Materno</label>			
					<input type="text" name="Materno" id="Materno" class="form-control" value="<%=materno%>" />
					<br><br>
				</div>
				<div class="col-3">
					<label for="Rfid">RFID</label>			
					<input type="text" class="form-control" name="Rfid" id="Rfid" value="<%=rfid%>" />
					<br><br>
					<label for="RfidTag">RFID_TAG</label>			
					<input type="text" class="form-control" name="RfidTag" id="RfidTag" value="<%=rfidTag%>" />
					<br><br>
					<label for="Estado">Estado</label>			
					<select name="Estado" class="form-select">
						<option value="A" <%=estado=="A"?"Selected":""%>>Activo</option>
						<option value="I" <%=estado=="I"?"Selected":""%>>Inactivo</option>
					</select>
					<br><br>
					<label for="Comentario">Comentario</label>			
					<input type="text" name="Comentario" id="Comentario" class="form-control" value="<%=comentario%>" />
					<br><br>
				</div>
				<div class="col-3">
<% 			if(accion.equals("2")){%>
				<label for="Foto">Foto</label>
<%				if(conFoto.contains(visitante.getCodigoPersonal())){%>
					<div id="sombra"><img src="../../foto?Codigo=<%=visitante.getCodigoPersonal()%>&Tipo=V" width="250" border="1"></div>
					<a href="../../fotoVisitaBajar?Codigo=<%=visitante.getCodigoPersonal()%>&Tipo=V" title="Descargar la Foto"><img src='../../imagenes/descargar.png' width="30" ></a>&nbsp;
                	<a href="javascript:borrarFoto('<%=visitante.getCodigoPersonal()%>')" title="Borrar Foto"><img src='../../imagenes/borrar2.gif' width="25" ></a>
<%				}else{%>
					<a href="subirFoto?CodigoPersonal=<%=visitante.getCodigoPersonal()%>" title="Subir Foto de un archivo"><img src='../../imagenes/upload.png' width="30"  style="position:relative;top:3px;"></a>
	
<%				}
			}	%>
				</div><br>

 		
			</div>
			<br>
			<div class="alert alert-info">
				<a class="btn btn-primary" onclick="javascript:Guardar();"><i class="icon-ok icon-white"></i> Guardar</a>
				<a class="btn btn-success" onclick="javascript:Nuevo();"><i class="icon-user icon-white"></i> Nuevo</a>
			</div>
		</form>
		
	</div>
</body>
<script>
	function Guardar() {
		if (document.frmVisitante.CodigoPersonal.value != "" && document.frmVisitante.Nombre.value != "" && document.frmVisitante.Paterno.value != ""
			&& document.frmVisitante.Materno.value != "" && document.frmVisitante.Rfid.value != "" && document.frmVisitante.RfidTag.value != "") {
				document.frmVisitante.submit();
		} else {
			alert("¡Completa todos los campos!");
		}
	}

	function Nuevo() {
		if(document.frmCredencial.Nombre.value!=""){
			if(confirm("El campo de nombre no está vacío, es posible que la persona no haya sido guardada todavía. ¿Deseas crear un nuevo registro?")==true){
				document.frmCredencial.Accion.value = "1";
				document.frmCredencial.submit();		
			}
		}else{
			document.frmCredencial.submit();
		}
	}
	
	function borrarFoto(codigoAlumno){		
		if (confirm("¿Estas seguro de borrar la fotografia?")){
			document.location.href="borrar?CodigoPersonal="+codigoAlumno+"&Tipo=V"; 
		}	
	}
</script>
</html>