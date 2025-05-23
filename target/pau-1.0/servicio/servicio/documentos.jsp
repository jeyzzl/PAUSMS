<%@ page import= "java.util.List"%>
<%@ page import= "aca.ssoc.spring.SsocDocumentos"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "aca.ssoc.RequisitoVO"%>
<%@ page import= "aca.ssoc.DocAlumVO"%>

<jsp:useBean id="bDocumentos"  class="aca.ssoc.Documentos" scope="page"/>
<jsp:useBean id="bDocAlumno"  class="aca.ssoc.DocAlumno" scope="page"/>
<%
	String sPlan	= (String)request.getAttribute("sPlan");
	int accion		= (int)request.getAttribute("accion");
	List<SsocDocumentos> listaDocumentos = (List<SsocDocumentos>)request.getAttribute("listaDocumentos");

%>

<script>
	function Guarda(){
		grupoDocs="";
		for(i=0;i<document.forma.docs.length;i++)
			if(document.forma.docs[i].checked)
				grupoDocs+=document.forma.docs[i].value+",";
		document.forma.grupoDocs.value=grupoDocs;
		//alert(document.forma.grupoDocs.value);
		document.forma.submit();
		opener.document.location.href="social?espera=1";
		window.close();
	}
	
	function seleccionaTodos(){
		for(i=0;i<document.forma.docs.length;i++)
			document.forma.docs[i].checked=true;
	}
	
	function deseleccionaTodos(){
		for(i=0;i<document.forma.docs.length;i++)
			document.forma.docs[i].checked=false;
	}
	
</script>
<form name="forma" method='post' action='documentos?accion=1&PlanId=<%=sPlan%>'>
<input type='hidden' name='grupoDocs' value=''/>
<table style='margin:0 auto; width:100%' class="table table-condensed">
	<tr><td colspan='4'><a class="btn btn-primary" href='javascript:seleccionaTodos();'><b>Seleccionar todo</b></a> | <a class="btn btn-primary" href='javascript:deseleccionaTodos();'><b>Deseleccionar todo</b></a></td></tr>
	<tr bgcolor='orange'>
		<td width="5%" align='center'></td>
		<td width="5%" align='center'><spring:message code="aca.Numero"/></td>
		<td width="90%" align='center'><spring:message code="aca.Nombre"/></td>
	</tr>
<%	
	SsocDocumentos documento = null;
	for(int i=0;i<listaDocumentos.size();i++){
	documento = new SsocDocumentos();
	documento=(SsocDocumentos)listaDocumentos.get(i);
%>
	
	<tr valign='top'>
		<td align='center'><input type='checkbox' name='docs' value='<%=documento.getDocumentoId()%>'/></td>
		<td align='center'><%=documento.getDocumentoId() %></td>
		<td><%=documento.getDocumentoNombre()%></td>
	</tr>
<%}%>		
	<tr><td colspan='4' style='text-align:center'><input class="btn btn-primary" onclick='Guarda();' type='button' name='button' value='Aceptar'/>
	</td></tr>
</table>
</form>
