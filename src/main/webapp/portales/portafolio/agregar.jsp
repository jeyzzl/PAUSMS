<%@page import="aca.util.Fecha"%>

<%@ include file= "../../con_enoc.jsp" %> 
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="PorEmpdocto" scope="page" class="aca.portafolio.PorEmpDocto"/>
<jsp:useBean id="PorEmpdoctoU" scope="page" class="aca.portafolio.PorEmpDoctoUtil"/>
<jsp:useBean id="PorDocumentosU" scope="page" class="aca.portafolio.PorDocumentoUtil"/>

<%	
	String periodoId			= request.getParameter("porPeriodo")==null?"2014":request.getParameter("porPeriodo");
	String codigoPersonal		= request.getParameter("codigoPersonal")==null?"0000000":request.getParameter("codigoPersonal");
	String documentoId			= request.getParameter("documentoId")==null?"0":request.getParameter("documentoId");
	String accion				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");	
	int accionFmt				= 0;	
	String fechaHoy 			= aca.util.Fecha.getHoy();
	
	// lista de documentos 
	ArrayList<aca.portafolio.PorDocumento> lisDoc = PorDocumentosU.listDisponibles(conEnoc, periodoId, codigoPersonal, "ORDER BY 1");	
	
		
	if (accion.equals("2")){
		PorEmpdocto.setPeriodoId(periodoId);
		PorEmpdocto.setCodigoPersonal(codigoPersonal);
		PorEmpdocto.setDocumentoId(documentoId);
		PorEmpdocto.setHojas("0");		
		PorEmpdocto.setUsuario(codigoPersonal);
		PorEmpdocto.setFecha(fechaHoy);
		if(PorEmpdocto.existeReg(conEnoc)==false){
			if(PorEmpdocto.insertReg(conEnoc)){
				accionFmt = 1;
			}else{
				accionFmt = 2;
			}
		}else{//Modifica
			if(PorEmpdocto.updateReg(conEnoc)){
				accionFmt = 3;
			}else{
				accionFmt = 4;
			}
		}
	}else if (accion.equals("3")){
		PorEmpdocto.setCodigoPersonal(codigoPersonal);
		PorEmpdocto.setDocumentoId(request.getParameter("documentoId"));
		PorEmpdocto.setPeriodoId(periodoId);
		if(PorEmpdocto.existeReg(conEnoc)){
			if(PorEmpdocto.deleteReg(conEnoc)){
				accionFmt = 5;
			}else{
				accionFmt = 6;
			}
		}else{
			accionFmt = 7;
		}
	}	
%>
<div class="container-fluid">
<%		if(accionFmt==1){%>
		<div class="alert alert-info"><h3><spring:message code='aca.SeGuardoCorrectamente'/></h3></div>
<%	
}		if(accionFmt==2){%>
		<div class="alert alert-danger"></div><h3><spring:message code='aca.NoSePudoGuardar'/></h3></div>
<%	
}		if(accionFmt==3){%>
		<div class="alert alert-info"><h3><spring:message code='aca.SeModifico'/></h3></div>
<%	
}		if(accionFmt==4){%>
		<div class="alert alert-danger"><h3><spring:message code='aca.NoSePudoModificar'/></h3></div>
<%	
}		if(accionFmt==5){%>
		<div class="alert alert-info"><h3><spring:message code='aca.SeElimino'/></h3></div>
<%	
}		if(accionFmt==6){%>
		<div class="alert alert-danger"><h3><spring:message code='aca.NoSePudoEliminar'/></h3></div>
<%	
}		if(accionFmt==7){%>
		<div class="alert alert-danger"><h3><spring:message code='aca.NoExiste'/></h3></div>
<%	
}
%>  
	<div class="alert alert-info">
		<a href="documentos" class="btn btn-primary"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
	</div>
	<form name="frmDocumento" action="agregar">
	<input name="Accion" type="hidden" value="<%=accion%>"/>
	<input name="periodoId" type="hidden" value="<%=periodoId%>"/>
	<input name="codigoPersonal" type="hidden" value="<%=codigoPersonal%>"/>
	
	<table class="table table-nohover">
		<tr>
			<td>Tipo de Documento</td>
			<td>
				<select  id="documentoId" name="documentoId">
<%		for (aca.portafolio.PorDocumento documento : lisDoc){ %>														
					<option value="<%=documento.getDocumentoId()%>" <%if(documento.getDocumentoId().equals(documentoId)) out.print("selected");%>>
					<%=documento.getDocumentoNombre()%>
					</option>
<%		} %>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<a class="btn btn-primary" onclick="javascript:Guardar();"><i class="icon-ok icon-white"></i> Guardar</a>				
			</td>
		</tr>
	</table>	
	</form>
</div>
<script>

	function Guardar() {
		if (document.frmDocumento.documentoId.value != "") {
			document.frmDocumento.Accion.value = "2";
			document.frmDocumento.submit();
		} else {
			alert("¡Completa todos los campos!");
		}
	}
	
</script>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>