<%@ include file="../../con_enoc.jsp"%>
<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
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
	
	ArrayList<aca.catalogo.CatModalidad> lisModalidad	= ModalidadU.getListAll(conEnoc, "ORDER BY MODALIDAD_ID");
	
	if (accion.equals("1")){
		
		modalidades = "";
		for(int i = 0; i < lisModalidad.size(); i++){
			aca.catalogo.CatModalidad modalidad = (aca.catalogo.CatModalidad) lisModalidad.get(i);
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
	<h1>Select Modalities</h1>
	<form id="forma" name="forma" action="modalidades" method="post">
	<input name="Accion" type="hidden">
	<div class="alert alert-info">
		<a class="btn btn-primary btn-small" href="reporte"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
	</div>	
	<table style="width:50%" class="table table-sm table-bordered">
		<tr>
			<th><spring:message code="aca.Elegir"/></th>
			<th><spring:message code="aca.Clave"/></th>
			<th><spring:message code="aca.Modalidad"/></th>
		</tr>	
		<tr><td colspan="4"><b><spring:message code="aca.Seleccionar"/>:</b></td></tr>		
<%
	String modalidadSelected = "";
	for( aca.catalogo.CatModalidad modalidad : lisModalidad){
		if (modalidades.contains(modalidad.getModalidadId()))
			modalidadSelected = "checked";
		else
			modalidadSelected = "";
%>
		<tr>
			<td>
				<input type="checkbox" id="<%=modalidad.getModalidadId() %>" name="<%=modalidad.getModalidadId() %>" value="S" <%=modalidadSelected%>/>
			</td>
			<td>			
				<%=modalidad.getModalidadId() %>																					
			</td>
			<td>			
				<b><%=modalidad.getNombreModalidad() %></b>																					
			</td>			
		</tr>		
<%	} %>
		<tr><td colspan="4"><input class="btn btn-primary" type="submit" value="Save" onclick="Mostrar();"/></td></tr>
	</table>
	
	</form>
<%@ include file="../../cierra_enoc.jsp"%>