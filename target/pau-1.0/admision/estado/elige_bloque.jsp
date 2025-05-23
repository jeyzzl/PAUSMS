<%@ page import="java.util.List"%>
<%@ page import="aca.alumno.spring.AlumEstado"%>
<%@ page import="aca.catalogo.spring.CatTipoCal"%>
<%@ page import="aca.carga.spring.CargaBloque"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<!-- inicio de estructura -->
<%	
	//declaracion de variables
	String cargaId 					= request.getParameter("CargaId");
	String bloqueId 				= request.getParameter("BloqueId")==null?"0":request.getParameter("BloqueId");
	String mensaje 					= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	AlumEstado alumEstado			= (AlumEstado) request.getAttribute("alumEstado");
	String codigoAlumno 			= (String) request.getAttribute("codigoAlumno");
	String nombreAlumno 			= (String) request.getAttribute("nombreAlumno");
	String cargaNombre	 			= (String) request.getAttribute("cargaNombre");
	List<CatTipoCal> lisStatus	 	= (List<CatTipoCal>) request.getAttribute("lisStatus");			
	List<CargaBloque> lisBloques 	= (List<CargaBloque>) request.getAttribute("lisBloques");
%>
<div class="container-fluid">
	<h2>Student: <small class="text-muted fs-5">( <%=codigoAlumno%> - <%=nombreAlumno%> )</small></h2>
	<div class="alert alert-info">
		<a href="estado" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;<%=mensaje.equals("-")?"":mensaje%>
	</div>
	<form name="documento" method="post" action="grabar">
	<input type="hidden" name="CargaId" value="<%=cargaId%>">
	<input type="hidden" name="CodigoAlumno" value="<%=codigoAlumno%>">
	<table class="table table-sm table-bordered" style="width:50%;">
		<tr>
			<td width="17%"><b><spring:message code="aca.Carga"/></b></td>
			<td width="83%"><%=cargaNombre%></td>
		</tr>
		<tr>
			<td width="17%"><b><spring:message code="aca.Bloque"/></b></td>
			<td width="83%"><b>
			<%	if (!bloqueId.equals("0")){ %>			
				<input type="text" name="BloqueId" id="f_bloque" value="<%=bloqueId%>" readonly="readonly" />
			<%	}else{%>	
				<select name="BloqueId">
			<%		for(CargaBloque bloque : lisBloques){ %>		
					<option value="<%=bloque.getBloqueId()%>"> <%=bloque.getNombreBloque()%></option>
			<% 		} %>		
				</select>
			<% 	} %>	
			</b></td>
		</tr>
		<tr>
			<td width="17%"><b>Enrolled</b></td>
			<td width="83%">
				<b>
				<select name="Estado">
				<%
					for (CatTipoCal tipo : lisStatus){
				%>
					<option value="<%=tipo.getTipoCalId()%>" <%=alumEstado.getEstado().equals(tipo.getTipoCalId())?"selected":""%> ><%=tipo.getNombreTipoCal()%></option>
				<%		
					}						
				%>
				</select>
				</b>
			</td>
		</tr>
		<tr>
			<th colspan="2"><b><input type="submit" name="Submit" value="Save" class="btn btn-primary"></b></th>
		</tr>
	</table>
</form>
<!-- fin de estructura -->
</div>