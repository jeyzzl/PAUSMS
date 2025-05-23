<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="porSeccionU" scope="page" class="aca.por.PorSeccionUtil"/>
<jsp:useBean id="porSeccion" scope="page" class="aca.por.PorSeccion"/>

<script type="text/javascript">
	function Borrar(){
		document.frmPortafolio.Accion.value="2";
		document.frmPortafolio.submit();
	}	
</script>
<%	
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String porId 			= request.getParameter("porId")==null?"0":request.getParameter("porId");
	int accionFmt			= 0;	

	// Lista de portafolios 
	ArrayList<aca.por.PorSeccion> lisPortafolio	=  porSeccionU.listPortafolio(conEnoc, porId, "ORDER BY SECCION_ID");
	if(accion.equals("1")){
		for(aca.por.PorSeccion portafolio : lisPortafolio){
			porSeccion.setPorId(porId);
			porSeccion.setSeccionId(portafolio.getSeccionId());
			String tipo = "-";
			if(request.getParameter(portafolio.getSeccionId())!=null)
				tipo = request.getParameter(portafolio.getSeccionId());
			porSeccion.setTipo(tipo);
			if(porSeccion.updateTipo(conEnoc)){
				accionFmt = 1;
			}else accionFmt = 2;
		}
	}else if(accion.equals("2")){
		porSeccion.setPorId(porId);
		porSeccion.setSeccionId(request.getParameter("seccionId"));
		String name = request.getParameter("seccionId");
		porSeccion.setTipo(request.getParameter(name));
		if(porSeccion.updateTipo(conEnoc)){
			accionFmt = 1;
		}else accionFmt = 2;
	}
	lisPortafolio	=  porSeccionU.listPortafolio(conEnoc, porId, "ORDER BY SECCION_ID");
%>
<div class="container-fluid">
<h1><spring:message code="aca.FormatoPortafolio"/></h1>

<%	if(accionFmt==1){%>
		<div class="alert alert-info"><h3><spring:message code='aca.SeGuardoCorrectamente'/></h3></div>
<%	
	}
	if(accionFmt==2){%>
		<div class="alert alert-danger"><h3>No se pudo guardar</h3></div>
<%	} %>

<form name="frmPortafolio" action="porSeccion" method="post" >
<input type="hidden" name="Accion">
<input type="hidden" name="porId" value="<%=porId%>">
<div class="alert alert-info">
<a href="portafolio" class="btn btn-primary"><i class="fas fa-arrow-left"></i> <spring:message code="aca.Regresar"/></a>
</div>
<div>
<%
	String seccionId = "", seccionIdDos = "", seccionIdTres = "";
	for(aca.por.PorSeccion portafolio : lisPortafolio){
		if(portafolio.getSeccionId().length()==2){
			seccionId = portafolio.getSeccionId();
			out.print("<div class='alert alert-info'><h4><a href='accion?PortafolioId="+portafolio.getPorId()+"&seccionId="+portafolio.getSeccionId()+"'><i class='icon icon-plus'></i></a>&nbsp;&nbsp;&nbsp;"+portafolio.getSeccionNombre()+"</h4></div>");
			if(!porSeccion.tieneHijo(conEnoc, portafolio.getSeccionId())){%>
			<select name="<%=portafolio.getSeccionId()%>">
				<option value="C" <%if(portafolio.getTipo().equals("C")) out.print("selected"); %>>Texto Corto</option>
				<option value="L" <%if(portafolio.getTipo().equals("L")) out.print("selected"); %>>Texto Largo</option>
				<option value="I" <%if(portafolio.getTipo().equals("I")) out.print("selected"); %>>Imagen</option>
				<option value="A" <%if(portafolio.getTipo().equals("A")) out.print("selected"); %>>Archivo</option>
			</select> &nbsp;&nbsp;<a href="porSeccion?Accion=2&seccionId=<%=portafolio.getSeccionId() %>&porId=<%=porId %>" class="btn btn-sm btn-success"><i class="icon-white icon-ok"></i></a><br><br>
<%			}
		}
		if(portafolio.getSeccionId().length()==4){
			seccionIdDos = portafolio.getSeccionId();
			out.print("<div class='alert alert-success' style='width:95%;align:right'><strong><a href='accion?PortafolioId="+portafolio.getPorId()+"&seccionId="+portafolio.getSeccionId()+"'><i class='icon icon-plus'></i></a>&nbsp;&nbsp;&nbsp;"+portafolio.getSeccionNombre()+"</strong> </div>");
			if(!porSeccion.tieneHijo(conEnoc, portafolio.getSeccionId())){%>
			<select name="<%=portafolio.getSeccionId()%>">
				<option value="C" <%if(portafolio.getTipo().equals("C")) out.print("selected"); %>>Texto Corto</option>
				<option value="L" <%if(portafolio.getTipo().equals("L")) out.print("selected"); %>>Texto Largo</option>
				<option value="I" <%if(portafolio.getTipo().equals("I")) out.print("selected"); %>>Imagen</option>
				<option value="A" <%if(portafolio.getTipo().equals("A")) out.print("selected"); %>>Archivo</option>
			</select>&nbsp;&nbsp;<a href="porSeccion?Accion=2&seccionId=<%=portafolio.getSeccionId() %>&porId=<%=porId %>" class="btn btn-sm btn-success"><i class="icon-white icon-ok"></i></a><br><br>
<%			}
		}
		if(portafolio.getSeccionId().length()==6 && portafolio.getSeccionSuperior().equals(seccionIdDos) && !portafolio.getTipo().equals("-")){
			porSeccion.mapeaRegId(conEnoc, porId, portafolio.getSeccionId());
%>
<div class="row">
	<div class="span4">
		<span style="font-size: 16px;"><a href="accion?PortafolioId=<%=portafolio.getPorId()%>&seccionId=<%=portafolio.getSeccionId()%>"><i class='icon icon-plus'></i></a>&nbsp;&nbsp;&nbsp;<%=portafolio.getSeccionNombre() %></span>
	</div>
	<div class="span1">
	
	</div>
	<div class="span4">
		<select name="<%=portafolio.getSeccionId()%>">
			<option value="C" <%if(portafolio.getTipo().equals("C")) out.print("selected"); %>>Texto Corto</option>
			<option value="L" <%if(portafolio.getTipo().equals("L")) out.print("selected"); %>>Texto Largo</option>
			<option value="I" <%if(portafolio.getTipo().equals("I")) out.print("selected"); %>>Imagen</option>
			<option value="A" <%if(portafolio.getTipo().equals("A")) out.print("selected"); %>>Archivo</option>
		</select>&nbsp;&nbsp;<a href="porSeccion?Accion=2&seccionId=<%=portafolio.getSeccionId() %>&porId=<%=porId %>" class="btn btn-sm btn-success"><i class="icon-white icon-ok"></i></a>
	</div>	
</div>
	<br><br>
<%
		}else if(portafolio.getSeccionId().length()==6){
			seccionIdTres = portafolio.getSeccionId();
			out.print("<div class='alert alert-warning' style='width:90%;align:right'><strong><a href='accion?PortafolioId="+portafolio.getPorId()+"&seccionId="+portafolio.getSeccionId()+"'><i class='icon icon-plus'></i></a>&nbsp;&nbsp;&nbsp;"+portafolio.getSeccionNombre()+"</strong></div>");
		
		}else if(portafolio.getSeccionId().length()==8){
%>
		<div class="row">
			<div class="span4">
				<span style="font-size: 16px;"><a href="accion?PortafolioId=<%=portafolio.getPorId()%>&seccionId=<%=portafolio.getSeccionId()%>"><i class='icon icon-plus'></i></a>&nbsp;&nbsp;&nbsp;<%=portafolio.getSeccionNombre() %></span>
			</div>
			<div class="span1">
			
			</div>
			<div class="span4">
				<select name="<%=portafolio.getSeccionId()%>">
					<option value="C" <%if(portafolio.getTipo().equals("C")) out.print("selected"); %>>Texto Corto</option>
					<option value="L" <%if(portafolio.getTipo().equals("L")) out.print("selected"); %>>Texto Largo</option>
					<option value="I" <%if(portafolio.getTipo().equals("I")) out.print("selected"); %>>Imagen</option>
					<option value="A" <%if(portafolio.getTipo().equals("A")) out.print("selected"); %>>Archivo</option>
				</select>
			</div>	
		</div>
			<br><br>	
<%		}		
	}
%>
<div class="alert alert-info">
	<a href="javascript:Guardar()" class="btn btn-primary">Guardar</a>
</div>
</div> 
</form>	
</div>
<script>
	function Guardar(){
		document.frmPortafolio.Accion.value = 1;
		document.frmPortafolio.submit();
	}
</script>
<%@ include file= "../../cierra_enoc.jsp" %>