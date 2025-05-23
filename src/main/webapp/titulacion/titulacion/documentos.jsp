<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "aca.tit.RequisitoVO"%>
<%@ page import= "aca.tit.DocAlumVO"%>

<jsp:useBean id="bDocumentos"  class="aca.tit.Documentos" scope="page"/>
<jsp:useBean id="bDocAlumno"  class="aca.tit.DocAlumno" scope="page"/>
<jsp:useBean id="alumnoUtil" scope="page" class="aca.alumno.AlumUtil"/>
<%
	int accion=0;
	int orden=0;
	if(request.getParameter("accion")!=null)
		accion=Integer.parseInt(request.getParameter("accion"));
	String grupoDocs=request.getParameter("grupoDocs");
	if (grupoDocs==null)grupoDocs="";
	String tituloId=request.getParameter("tituloId");
	if (tituloId==null)tituloId="";
	String plan=request.getParameter("plan");
	if (plan==null)plan="";
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
<!----------------------- DOCUMENTOS ------------------------------ -->

<form name="forma" method='post' action='documentos?accion=1&tituloId=<%=tituloId%>'>
<input type='hidden' name='grupoDocs' value=''/>
<table class="table table-sm table-bordered">  
<%
		if(accion==1){
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
			String[] arrGrupoDocs = grupoDocs.split(",");
			for(int i=0;i<arrGrupoDocs.length;i++){
				DocAlumVO documento = new DocAlumVO();
				documento.setTituloId(tituloId);
				documento.setDocumentoId(Integer.parseInt(arrGrupoDocs[i]));
				documento.setFecha(sdf.format(new java.util.Date()));
				documento.setComentario("");
				documento.setEntregado("N");
				bDocAlumno.guardaDocumento(conEnoc,documento);
			}
%>
	<script>
		opener.document.location.href="titulacion";
		window.close();
	</script>
<%
		}
%>
	<tr><td colspan='4'><a class="btn btn-primary" href='javascript:seleccionaTodos();'><b>Seleccionar todo</b></a> | <a class="btn btn-primary" href='javascript:deseleccionaTodos();'><b>Deseleccionar todo</b></a></td></tr>
	<tr bgcolor='orange'>
		<td width="5%" align='center'></td>
		<td width="5%" align='center'><spring:message code="aca.Numero"/></td>
		<td width="70%" align='center'><spring:message code="aca.Nombre"/></td>
		<td width="20%" align='center'>Nivel</td>
	</tr>
<%
	String matricula = (String) session.getAttribute("codigoAlumno");	
	RequisitoVO documento = null;
	ArrayList<aca.tit.RequisitoVO> documentos = bDocumentos.getDocumentos(conEnoc, "ORDER BY TIPO");
	int nivel = bDocAlumno.getNivelPlan(conEnoc,plan);
	for(int i=0;i<documentos.size();i++){
	  documento = new RequisitoVO();
	  documento=(RequisitoVO)documentos.get(i);
	  if(((documento.getTipo().equals("L")&&nivel==2)||documento.getTipo().equals("S")||documento.getTipo().equals("G")||documento.getTipo().equals("O"))
	  	|((documento.getTipo().equals("P")&&nivel==3)||documento.getTipo().equals("S")||documento.getTipo().equals("G")||documento.getTipo().equals("O"))
	  	|((documento.getTipo().equals("P")&&nivel==4)||documento.getTipo().equals("S")||documento.getTipo().equals("G")||documento.getTipo().equals("O"))
	  ){
%>
	
	<tr valign='top'>
		<td align='center'><input type='checkbox' name='docs' value='<%=documento.getId()%>'/></td>
		<td align='center'><%=documento.getId()%></td>
		<td><%=documento.getNombre()%></td>
		<td><%
			if(documento.getTipo().equals("L"))out.print("Licenciatura");
			  if(documento.getTipo().equals("P"))out.print("Posgrado");
			  if(documento.getTipo().equals("S"))out.print("Seguimiento");
			  if(documento.getTipo().equals("G"))out.print("General");
			  if(documento.getTipo().equals("O"))out.print("Servicio");
		%></td>
	</tr>
<%	  }
	}
%>		
	<tr><td colspan='4' align='center'><input onclick='Guarda();' type='button' name='button' value='Aceptar'/>
	</td></tr>
</table>
</form>
<%@ include file= "../../cierra_enoc.jsp" %>