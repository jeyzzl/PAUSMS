<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.catalogo.spring.CatModalidad"%>

<%@ include file= "../../headPortal.jsp" %>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="Modalidad" scope="page" class="aca.catalogo.CatModalidad" />
<jsp:useBean id="ModalidadU" scope="page" class="aca.catalogo.ModalidadUtil" />

<script type="text/javascript">	
	function Mostrar(){					
		document.forma.Accion.value="1";
		document.forma.submit();
	}
</script>
<%
	String codigoPersonal				= (String) session.getAttribute("codigoPersonal");	
	String modalidades					= (String)session.getAttribute("modalidades") == null?"":session.getAttribute("modalidades").toString();
	String accion 						= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	List<CatModalidad> lisModalidades	= (List<CatModalidad>) request.getAttribute("lisModalidades");
	
	if (accion.equals("1")){
		modalidades = "";
		for(int i = 0; i < lisModalidades.size(); i++){
			CatModalidad modalidad = (CatModalidad) lisModalidades.get(i);
			if(request.getParameter(modalidad.getModalidadId()) != null ){			
				if(modalidades.equals(""))
					modalidades = "'"+modalidad.getModalidadId()+"'";
				else
					modalidades += ",'"+modalidad.getModalidadId()+"'";						
			}
		}
		session.setAttribute("modalidades", modalidades);
	}		
%>
<head>
	
</head>	
<body>
<div class="container-fluid">
	<h2>Elegir Modalidades</h2>
	<form id="forma" name="forma" action="modalidades" method="post">
	<input name="Accion" type="hidden">
	<div class="alert alert-info">
		<a class="btn btn-primary btn-small" href="menu"><spring:message code="aca.Regresar"/></a>&nbsp;
		<input class="btn btn-primary" type="submit" value="Grabar" onclick="Mostrar();"/>
	</div>	
	<table style="width:50%" class="table table-sm table-bordered table-striped">
		<tr>
			<th>
				Elegir
				<a onclick="jQuery('.checkboxModalidades').prop('checked',true)" class="badge bg-success"><spring:message code='aca.Todos'/></a>&nbsp;
				<a onclick="jQuery('.checkboxModalidades').prop('checked', false)" class="badge bg-warning"><spring:message code='aca.Ninguno'/></a>	
			</th>
			<th><spring:message code="aca.Clave"/></th>
			<th><spring:message code="aca.Modalidad"/></th>
		</tr>				
<%
	String modalidadSelected = "";
	for( CatModalidad modalidad : lisModalidades){
		if (modalidades.contains("'" + modalidad.getModalidadId() + "'"))
			modalidadSelected = "checked";
		else
			modalidadSelected = "";
%>
		<tr>
			<td>
				<input class="checkboxModalidades" type="checkbox" id="<%=modalidad.getModalidadId() %>" name="<%=modalidad.getModalidadId() %>" value="S" <%=modalidadSelected%>/>
			</td>
			<td>			
				<%=modalidad.getModalidadId() %>																					
			</td>
			<td>			
				<b><%=modalidad.getNombreModalidad() %></b>																					
			</td>			
		</tr>		
<%	} %>
		<tr><td colspan="4"><input class="btn btn-primary" type="submit" value="Grabar" onclick="Mostrar();"/></td></tr>
	</table>	
	</form>
</div>
</body>