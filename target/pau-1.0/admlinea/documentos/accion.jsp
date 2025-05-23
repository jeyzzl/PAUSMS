<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="Documento" scope="page" class="aca.admision.AdmDocumento"/>
<jsp:useBean id="AdmFormatoUtil" scope="page" class="aca.admision.AdmFormatoUtil"/>

<html>
	<head>
		<script type = "text/javascript">
			
			function Nuevo()	{		
				document.frmDocumento.DocumentoId.value 		= " ";
				document.frmDocumento.DocumentoNombre.value 	= " ";
				document.frmDocumento.Tipo.value 				= " ";
				document.frmDocumento.Comentario.value 			= " ";
				document.frmDocumento.Original.value 			= " ";
				document.frmDocumento.Orden.value 				= " ";
				document.frmDocumento.Accion.value="1";
				document.frmDocumento.submit();		
			}
			
			function Grabar(){
				if(document.frmDocumento.DocumentoId.value!="" && document.frmDocumento.DocumentoNombre!="" 
				    && document.frmDocumento.Tipo!="" && document.frmDocumento.Comentario!="" && document.frmDocumento.Orden!="" ){		
					document.frmDocumento.Accion.value="2";
					document.frmDocumento.submit();			
				}else{
					alert("Fill out the entire form!");
				}
			}
			
			function Borrar( ){
				if(document.frmDocumento.DocumentoId.value!=""){
					if(confirm("Are you sure you want to delete this record?")==true){
			  			document.frmDocumento.Accion.value="4";
						document.frmDocumento.submit();
					}			
				}else{
					alert("Type in the Key");
					document.frmDocumento.DocumentoId.focus(); 
			  	}
			}
			
		</script>
	</head>
<%
	// Declaracion de variables	
	int nAccion			= Integer.parseInt(request.getParameter("Accion"));
	String sResultado	= "";
	
	if ( nAccion == 1 )
		Documento.setDocumentoId(Documento.maximoReg(conEnoc));
	else
		Documento.setDocumentoId(request.getParameter("DocumentoId"));
		
	// Operaciones a realizar en la pantalla	
	switch (nAccion){
		case 2: { // Grabar y Modificar
			Documento.setDocumentoNombre(request.getParameter("DocumentoNombre"));
			Documento.setTipo(request.getParameter("Tipo"));
			Documento.setComentario(request.getParameter("Comentario"));
			Documento.setOriginal(request.getParameter("Original"));
			Documento.setOrden(request.getParameter("Orden"));
			Documento.setFormatoId(request.getParameter("Formato"));
			if (Documento.existeReg(conEnoc) == false){
				if (Documento.insertReg(conEnoc)){
					sResultado = "Saved: "+Documento.getDocumentoId();
				}else{
					sResultado = "Error saving: "+Documento.getDocumentoId();
				}
			}else{
				if (Documento.updateReg(conEnoc)){
					sResultado = "Updated: "+Documento.getDocumentoId();
				}else{
					sResultado = "Error updating: "+Documento.getDocumentoId();
				}				
			}
			
			break;
		}		
		case 4: { // Borrar
			if (Documento.existeReg(conEnoc) == true){
				if (Documento.deleteReg(conEnoc)){
					sResultado = "Deleted: "+Documento.getDocumentoId();
				}else{
					sResultado = "Error deleting: "+Documento.getDocumentoId();
				}	
			}else{
				sResultado = "Not found: "+Documento.getDocumentoId();
			}
			break;
		}
		case 5: { // Consultar			
			if (Documento.existeReg(conEnoc) == true){
				Documento.mapeaRegId(conEnoc, request.getParameter("DocumentoId"));
			}else{
				sResultado = "Not found: "+Documento.getDocumentoId(); 
			}	
			break;			
		}
	}
	
	ArrayList<aca.admision.AdmFormato> listaFormato = AdmFormatoUtil.getAll(conEnoc, "ORDER BY FORMATO_NOMBRE");
%>
	<body>
	<div class="container-fluid">
		<h1><spring:message code='aca.CatalogoDeDocumentos'/></h1>
		<div class="alert alert-info">
			<a class="btn btn-primary" href="documentos.jsp"><spring:message code="aca.Regresar"/></a>
		</div>
		<form action="accion.jsp" method="post" name="frmDocumento" target="_self">
			<input type="hidden" name="Accion">
			<div class="row">
				<div class="span3">
					<label for="DocumentoId"><spring:message code="aca.Clave"/>:</label>			
					<input name="DocumentoId" type="text" class="text" id="DocumentoId" size="3" maxlength="3" value="<%=Documento.getDocumentoId()%>">
					<br><br>
					<label for="DocumentoNombre"><spring:message code="aca.Nombre"/>:</label>					
					<input name="DocumentoNombre" type="text" class="text" id="DocumentoNombre" size="30" maxlength="100" value="<%=Documento.getDocumentoNombre()%>">
					<br><br>
					<label for="Tipo"><spring:message code="aca.Tipo"/>:</label>			
					<input type="radio" name="Tipo" value="1" <% if (Documento.getTipo().equals("1")) out.print("Checked"); %>  />&nbsp;&nbsp;Generales
	           		&nbsp;&nbsp;<input type="radio"  name="Tipo" value="2" <% if (Documento.getTipo().equals("2")) out.print("Checked"); %> />&nbsp;&nbsp;Carrera
					<br><br>
					<label for="Original">Original:</label>					
					<select name="Original" id="Original">
						<%	if(Documento.getOriginal().equals("S")){ %>
				                <option value="S" selected><spring:message code='aca.Si'/></option>
						<%	}
							else{ %>	
								<option value="S"><spring:message code='aca.Si'/></option>
						<%	} %>	
						<%	if(Documento.getOriginal().equals("N")){ %>
				                <option value="N" selected><spring:message code='aca.No'/></option>
						<%	}
							else{ %>	
								<option value="N"><spring:message code='aca.No'/></option>
						<%	} %>	
              		</select>
					<br><br>
					<label for="Comentario"><spring:message code="aca.Comentario"/>:</label>					
					<textarea class="input input-xlarge" name="Comentario" class="text" id="Comentario" rows="5" cols="40"><%=Documento.getComentario()%></textarea>
				</div>
				<div class="span3">
					<label for="Formato"><spring:message code='aca.Formulario'/>:</label>			
					<select name="Formato" id="Formato">
        		  			<option value="0" <%if(Documento.getFormatoId().equals("0")) out.print("Selected"); %>><spring:message code='aca.Ninguno'/></option>
							<%	for(aca.admision.AdmFormato formato : listaFormato){%>
	  			  					<option value="<%=formato.getFormatoId() %>" <%if(Documento.getFormatoId().equals(formato.getFormatoId())) out.print("Selected"); %>><%=formato.getFormatoNombre() %></option>
							<% 	} %>
        			</select>
					<br><br>
					<label for="Orden"><spring:message code='aca.Orden'/>:</label>					
					<input name="Orden" type="text" class="text" id="Orden" size="5" maxlength="2" value="<%= Documento.getOrden() %>">
					<br>
					<%=sResultado%>
				</div>
			</div>
			<br>
			<div class="alert alert-info">
				<a class="btn btn-primary" href="javascript:Nuevo()"><spring:message code='aca.Nuevo'/></a>&nbsp;
				<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a> &nbsp;
				<a class="btn btn-primary" href="javascript:Borrar()"><spring:message code='aca.Borrar'/></a> &nbsp;
			</div>
		</form>
	  </div>
	</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>