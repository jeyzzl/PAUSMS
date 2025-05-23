<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="PorDocumentoU" scope="page" class="aca.por.PorDocumentoUtil"/>
<jsp:useBean id="PorDocumento" scope="page" class="aca.por.PorDocumento"/>
<jsp:useBean id="PorSeccionU" scope="page" class="aca.por.PorSeccionUtil"/>
<jsp:useBean id="Seccion" scope="page" class="aca.por.PorSeccion"/>

<script>
	function Refrescar(){
 		document.forma.Accion.value = "0";
 		document.forma.submit();
	}
	
	function Guardar(){
 		document.forma.Accion.value = "1";
 		document.forma.submit();
	}
</script>
<%
	String portafolioEnvia		= request.getParameter("PortafolioEnvia")==null?"0":request.getParameter("PortafolioEnvia");
	String portafolioRecibe		= request.getParameter("PortafolioRecibe")==null?"0":request.getParameter("PortafolioRecibe");
	String accion 				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	String mensaje				= "";

	// Lista de portafolios
	ArrayList<aca.por.PorDocumento> lisPortafolio	    =  PorDocumentoU.getListAll(conEnoc, " ORDER BY POR_ID");
	
	// Lista de secciones de ese portafolio
	ArrayList<aca.por.PorSeccion> lisSeccionPortafolio	=  PorSeccionU.listPortafolio(conEnoc, portafolioEnvia, " ORDER BY POR_ID");
	
	if(accion.equals("1")){	
		
		for(aca.por.PorSeccion seccion : lisSeccionPortafolio){
			seccion.setPorId(portafolioRecibe);
			System.out.println("- "+seccion.getPorId()+","+seccion.getSeccionId()+","+seccion.getTitulo()+","+seccion.getSeccionNombre()+","+seccion.getSeccionSuperior()+","+seccion.getTipo()+","+seccion.getAcceso()+","+seccion.getOrden()+","+seccion.getEstado()+","+seccion.getInstrucciones());
			seccion.insertReg(conEnoc);
			mensaje	= "Guardado";
		}
	}
	
	//pageContext.setAttribute("resultado", mensaje);
%>

<div class="container-fluid">
	
		<h2>Traspasar Portafolio</h2>
		
		<% if ( mensaje.equals("Guardado")){%>
	   		<div class='alert alert-success'><%=mensaje%></div>
	  	<% }%>
		<br>
		<form id="forma" name="forma" action="traspasoPortafolio" method="post">
			<div class="alert alert-info d-flex align-items-center">
		 		<a class="btn btn-primary" href="portafolio"><i class="fas fa-arrow-left"></i><spring:message code="aca.Regresar" /></a>
			</div>
			<input type="hidden" id="Accion" name="Accion">
			
			<div class="row">
				<div class="col-5">					
					<h3>Envia</h3>
					<label><spring:message code="aca.Portafolio" />:</label>
					<select id="PortafolioEnvia" name="PortafolioEnvia"  class="form-control" onchange="javascript:Refrescar();" style="width:360px;margin-bottom:0px;">
				<%
				for (aca.por.PorDocumento por : lisPortafolio) {
				%>
						<option value="<%=por.getPorId()  %>" <% if (portafolioEnvia.equals(por.getPorId())) out.print("selected");%> ><%=por.getPorNombre()%></option>
				<%
					}
				%>
					</select>
				</div>
				
				<div class="col-2"><br><br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn btn-success"><i class="fas fa-arrow-right"></a></i></div>
				
				<div class="col-5">					
					<h3>Recibe</h3>
					<label><spring:message code="aca.Portafolio" />:</label>
					<select id="PortafolioRecibe" name="PortafolioRecibe" class="form-control" onchange="javascript:Refrescar();" style="width:360px;margin-bottom:0px;">
				<%
					for (aca.por.PorDocumento por : lisPortafolio) {
				%>
						<option value="<%=por.getPorId()  %>" <% if (portafolioRecibe.equals(por.getPorId())) out.print("selected");%> ><%=por.getPorNombre()%></option>
				<%
					}
				%>
					</select>
				</div>
			</div>	
			<br><br>
			<div class="alert alert-info">
			<%
				if(!portafolioEnvia.equals(portafolioRecibe)){	
					if(PorSeccionU.listPortafolio(conEnoc,portafolioRecibe, "ORDER BY ORDEN, SECCION_ID").size() > 0){
			%>
						<a class="btn btn-warning">
							  El portafolio que va a recibir ya tiene datos
						</a>
			<%
					}else if(PorSeccionU.listPortafolio(conEnoc,portafolioEnvia, "ORDER BY ORDEN, SECCION_ID").size() > 0){
			%>
						<a class="btn btn-primary" href="javascript:Guardar();">
							  <i class="icon-plus icon-white"></i> <spring:message code="aca.Copiar" />
						</a>
			<%
					}else {
			%>
						<a class="btn btn-danger">
							  El portafolio que quieres copiar no tiene datos
						</a>
			<%
					}
				}				 
			%>
			</div>
				
		</form>
	
	</div>

<%@ include file= "../../cierra_enoc.jsp" %>