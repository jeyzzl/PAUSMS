<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../body.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%@ page import= "aca.tit.DocAlumVO"%>
<%@ page import= "aca.tit.TituloVO"%>

<jsp:useBean id="bDocumentos"  class="aca.tit.Documentos" scope="page"/>
<jsp:useBean id="bDocAlumno"  class="aca.tit.DocAlumno" scope="page"/>
<jsp:useBean id="bPagoAlumno"  class="aca.tit.PagoAlumno" scope="page"/>
<jsp:useBean id="bTitulo"  class="aca.tit.Titulo" scope="page"/>
<jsp:useBean id="bPagos"  class="aca.tit.Pagos" scope="page"/>
<jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="tipoCal" scope="page" class="aca.catalogo.TipoCalUtil"/>
<%
	String matricula = (String) session.getAttribute("codigoAlumno");
	alumno = AlumUtil.mapeaRegId(conEnoc,matricula);
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
	String colorPortal = (String)session.getAttribute("colorPortal");
	if(colorPortal==null)colorPortal="";
%>

<%-- <jsp:include page="../menu.jsp" /> --%>
<%@ include file= "menu.jsp" %>

<html>
<head><link href="css/portalAlumno.css" rel="STYLESHEET" type="text/css"></head>
<body onload='muestraPagina();'>
	<script>
		parent.tabs.document.body.style.backgroundColor=parent.contenidoP.document.bgColor;
	</script>

<script>
	function abrirVentana(strName,iW,iH,TOP,LEFT,R,S,SC,T,TB,URL,modal){
		var sF="";
		if (navigator.appName=="Microsoft Internet Explorer" && modal){
			sF+=T?'unadorned:'+T+';':'';
			sF+=TB?'help:'+TB+';':'';
			sF+=S?'status:'+S+';':'';
			sF+=SC?'scroll:'+SC+';':'';
			sF+=R?'resizable:'+R+';':'';
			sF+=iW?'dialogWidth:'+iW+'px;':'';
			sF+=iH?'dialogHeight:'+(parseInt(iH)+(S?42:0))+'px;':'';
			sF+=TOP?'dialogTop:'+TOP+'px;':'';
			sF+=LEFT?'dialogLeft:'+LEFT+'px;':'';	
			return window.showModalDialog(URL,"",sF);
		}else{
			sF+=iW?'width='+iW+',':'';
			sF+=iH?'height='+iH+',':'';
			sF+=R?'resizable='+R+',':'';
			sF+=S?'status='+S+',':'';
			sF+=SC?'scrollbars='+SC+',':'';
			sF+=T?'titlebar='+T+',':'';
			sF+=TB?'toolbar='+TB+',':'';
			sF+=TB?'menubar='+TB+',':'';
			sF+=TOP?'top='+TOP+',':'';
			sF+=LEFT?'left='+LEFT+',':'';
			return window.open(URL,strName?strName:'',sF).focus()
		}
	}			
	function abrir(pag){
		abrirVentana("titulacion",800,600,10,10,"no","yes","yes","no","no",pag,false);
	}
</script>
<div class="container-fluid">
<h2>
	<spring:message code="datosAlumno.portal.TitEstadoAvanceTitulacion"/>
	<small class="text-muted fs-4">(<%=matricula%> - <%=alumno.getNombre()%> <%=alumno.getApellidoPaterno()%> <%=alumno.getApellidoMaterno()%>)</small>
</h2>
<div class= well>
	<a href="tit_requisitos" target="_blank"><b><spring:message code="datosAlumno.portal.TitRequisitos"/></b></a>
</div>	
	<table style="width:90%"  cellpadding="10">
	<tr valign="top">
	<td>
		<table class="table table-condensed" style="width:100%"  >
		<tr><td>
<%
	ArrayList planes=bTitulo.getTitulos(conEnoc,matricula);
	for(int j=0;j<planes.size();j++){
		TituloVO titulo = (TituloVO)planes.get(j);
%>
		<table  width='90%'>
			<tr>
				<td colspan='6'>
				<%=titulo.getPlanId()%> - <%=AlumUtil.getCarrera(conEnoc,matricula,titulo.getPlanId())%> <%if(titulo.getDescripcion()!=null)out.print(" - "+titulo.getDescripcion());%>
				</td>
			</tr>
			<tr>
				<th width="2%" align='center'><spring:message code="aca.Numero"/></th>
				<th width="28%" align='center'><spring:message code="datosAlumno.portal.TitEntregaDocumento"/></th>
				<th width="6%" align='center'><spring:message code="aca.Fecha"/></th>
				<th width="19%" align='center'><spring:message code="aca.Comentario"/></th>
			</tr>
<%	ArrayList docs = new ArrayList();
	boolean entro = false, entro2 = false;
	DocAlumVO doc = null;
	docs = bDocAlumno.getEntregaDocumentos(conEnoc,titulo.getTituloId());
	int horas=0;
	for(int i=0;i<docs.size();i++){
		doc = new DocAlumVO();
		doc =(DocAlumVO)docs.get(i);	
%>
			<tr valign='top'>
				<td align='center'><%=i+1%></td>
				<td><%if(doc.getEntregado().equals("N"))out.print("<font color=red>");%><%=bDocAlumno.getNombreDocumento(conEnoc,doc.getDocumentoId())%></td>
				<td align='center'><%if(doc.getEntregado().equals("S")) out.print(doc.getFecha()); %></td>
				<td align='center'><%if(doc.getComentario()==null)out.print("");else out.print(doc.getComentario());%></td>
			</tr>
<%	}
	if(docs.size()==0){
%>
			<tr>
				<td colspan='5'><spring:message code="datosAlumno.portal.TitSinDocumentos"/>...</td>
			</tr>	
<%	}%>	

			<tr><td>&nbsp;</td></tr>
			<tr>
				<th width="2%" align='center'><spring:message code="aca.Numero"/></th>
				<th width="28%" align='center'><spring:message code="datosAlumno.portal.TitServicioSolicitado"/></th>
				<th width="6%" align='center'><spring:message code="aca.Fecha"/></th>
				<th width="19%" align='center'><spring:message code="aca.Comentario"/></th>
			</tr>
<%	docs = bDocAlumno.getDocumentos(conEnoc,titulo.getTituloId(),"O");
	for(int i=0;i<docs.size();i++){
		doc = new DocAlumVO();
		doc =(DocAlumVO)docs.get(i);	
%>
			<tr valign='top'>
				<td align='center'><%=i+1%></td>
				<td><%if(doc.getEntregado().equals("N"))out.print("<font color=red>");%><%=bDocAlumno.getNombreDocumento(conEnoc,doc.getDocumentoId())%></td>
				<td align='center'><%if(doc.getEntregado().equals("S")) out.print(doc.getFecha()); %></td>
				<td align='center'><%if(doc.getComentario()==null)out.print("");else out.print(doc.getComentario());%></td>
			</tr>
<%	}
	if(docs.size()==0){
%>
			<tr>
				<td colspan='5'><spring:message code="datosAlumno.portal.TitSinDocumentos"/>...</td>
			</tr>	
<%	}%>	

			<tr><td>&nbsp;</td></tr>
			<tr>
				<th width="2%" align='center'><spring:message code="aca.Numero"/></th>
				<th width="28%" align='center'><spring:message code="datosAlumno.portal.TitAvanceDocumento"/></th>
				<th width="6%" align='center'><spring:message code="aca.Fecha"/></th>
				<th width="19%" align='center'><spring:message code="aca.Comentario"/></th>
			</tr>
<%	docs = bDocAlumno.getDocumentos(conEnoc,titulo.getTituloId(),"S");
	for(int i=0;i<docs.size();i++){
		doc = new DocAlumVO();
		doc =(DocAlumVO)docs.get(i);	
%>
			<tr valign='top'>
				<td align='center'><%=i+1%></td>
				<td><%if(doc.getEntregado().equals("N"))out.print("<font color=red>");%><%=bDocAlumno.getNombreDocumento(conEnoc,doc.getDocumentoId())%></td>
				<td align='center'><%if(doc.getEntregado().equals("S")) out.print(doc.getFecha()); %></td>
				<td align='center'><%if(doc.getComentario()==null)out.print("");else out.print(doc.getComentario());%></td>
			</tr>
<%	}
	if(docs.size()==0){
%>
			<tr>
				<td colspan='5'><spring:message code="datosAlumno.portal.TitSinDocumentos"/>...</td>
			</tr>	
<%	}%>	
		</table>
		<table width='90%'>
		<tr>
			<td><table><tr><td>
			<b><spring:message code="aca.FormaPago"/>:</b></td><td>
<%		DocAlumVO pago = bPagoAlumno.getPago(conEnoc,titulo.getTituloId());
		if(pago!=null){
			out.print(bPagoAlumno.getNombrePago(conEnoc,pago.getDocumentoId())+", por la cantidad de $" + pago.getCantidad() + ", el día "+pago.getFecha());
			if(pago.getComentario()!=null){
				out.print(", "+ pago.getComentario());
			}
		}
		else{%>
			<spring:message code="datosAlumno.portal.TitFaltaPago"/>
	<%	}
%>
			</td></tr></table>
			</td>
		</tr>
		</table>
<%	}
	if(planes.size()==0){%>
		<table>
			<tr>
				<td colspan='5'><spring:message code="datosAlumno.portal.TitMensaje"/></td>
			</tr>
		</table>
<%	}else{%>
		<table>
			<tr>
				<td>
					<b><spring:message code="aca.Nota"/>:</b> <spring:message code="datosAlumno.portal.TitMensaje2"/>
				</td>
			</tr>
		</table>
<%	}%>
	</td>
	</tr>
	</table>
	</td></tr></table>
</div>
<script>
	$('.nav-tabs').find('.titulacion').addClass('active');
</script>
<%@ include file= "../../cierra_enoc.jsp"%>