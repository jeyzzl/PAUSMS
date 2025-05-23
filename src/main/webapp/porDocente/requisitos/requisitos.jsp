<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="PorCategoriaU" scope="page" class="aca.por.PorCategoriaUtil"/>
<jsp:useBean id="PorRequisitoU" scope="page" class="aca.por.PorRequisitoUtil"/>
<jsp:useBean id="requisitos" scope="page" class="aca.por.PorRequisito"/>


<script type="text/javascript">
	function Refrescar(){		
		document.frmRequisitos.submit();
	}	
</script>

	<script type="text/javascript">
		function eliminar(requisitoId, CategoriaId){
			if(confirm("¿Está seguro que desea borrar la actividad?")){
				document.location = "requisitos?Accion=1&requisitoId="+requisitoId+"&CategoriaId="+CategoriaId;
			}
		}
	</script>
<%
	String codigoPersonal 		= session.getAttribute("codigoPersonal").toString();
	// Trae el valor del portafolio en sesion
	String portafolioSession	= session.getAttribute("portafolioId")==null?"0":session.getAttribute("portafolioId").toString();
	
	// Trae el valor del portafolio como parametro
	String portafolioId			= request.getParameter("PortafolioId")==null?"0":request.getParameter("PortafolioId");
	String categoriaId			= request.getParameter("CategoriaId")==null?"0":request.getParameter("CategoriaId");
	
	// Si el portafolio como parametro tiene un nuevo valor
	if ( !portafolioId.equals("0") ){
		session.setAttribute("portafolioId",portafolioId);
	}else{
		portafolioId = portafolioSession;
	}
	
	// Lista de categorias de maestros 
	ArrayList<aca.por.PorCategoria> lisCategoria = PorCategoriaU.getListAll(conEnoc, " ORDER BY CATEGORIA_ID");
	
	
	
%>
<div class="container-fluid">
	<h2><spring:message code="aca.Requisitos"/></h2>	
	<div class="alert alert-info">
	<form class="row row-cols-lg-auto g-3 align-items-center" name="frmRequisitos" action="requisitos" method="post" >
		<select name="CategoriaId" onchange="javascript:Refrescar();" id="CategoriaId" class="form-control" style="width:200px" >
			<option value="0">Seleccionar</option>
<%
		for (aca.por.PorCategoria categoria : lisCategoria){
%>		
			<option value="<%=categoria.getCategoriaId()%>" <%=categoriaId.equals(categoria.getCategoriaId())?"selected":""%> >
				<%=categoria.getCategoriaNombre()%>
			</option>
<% 		}%>			
		</select>
		&nbsp;<a href="requisito?CategoriaId=<%=categoriaId%>" class="btn btn-primary">Nuevo</a>
	</form>			
	</div>
	
<%
	String accion 	= request.getParameter("Accion");
	String strNivel	= "";

	if(accion == null)
		accion = "0";
	
	if(accion.equals("1")){
		requisitos.setRequisitoId(request.getParameter("requisitoId"));
		if(requisitos.deleteReg(conEnoc)){
%>
<table align="left">
	<tr>
		<td><font color="blue">Se elimin&oacute; correctamente la actividad!!!</font></td>
	</tr>
</table>
<%
		}else{
%>
<table align="left">
	<tr>
		<td><font color="red">ERROR!!!</font> No se pudo eliminar la actividad. Int&eacute;ntelo de nuevo!</td>
	</tr>
</table>
<%
		}
	}
	
	// lista de requisitos en una categoria
		ArrayList<aca.por.PorRequisito> lisRequisitos = PorRequisitoU.getListCategoria(conEnoc, categoriaId, " ORDER BY TIPO, DESCRIPCION ");
%>


	<table class="table table-sm table-bordered ">
		<tr class="table-info">
			<th width="5%">N°</th>
			<th>Requisito</th>
			<th>Valor</th>
			<th>Tipo</th>
		</tr>
<%
	int row = 0;
	for (aca.por.PorRequisito requisito : lisRequisitos){
		row++;
%>
		<tr>
			<td> <%=row %> <a href="requisito?Accion=2&requisitoId=<%=requisito.getRequisitoId()%>&CategoriaId=<%=categoriaId %>" class="fas fa-pencil-alt"></a><a href="javascript:eliminar('<%=requisito.getRequisitoId() %>','<%=categoriaId %>');"  class="fas fa-trash-alt"></a></td>
			<td><%=requisito.getDescripcion()%></td>
			<td><%=requisito.getValor()%></td>
			<td>
<%				if (requisito.getTipo().equals("I")){
					out.print("Investigación");                
				} else {
					out.print("Normal");
				}
%>
			</td>
		</tr>
<%	} %>
	</table>

</div>

<%@ include file="../../cierra_enoc.jsp" %>