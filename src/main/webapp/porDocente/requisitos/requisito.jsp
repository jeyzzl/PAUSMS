<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="PorCategoriaU" scope="page" class="aca.por.PorCategoriaUtil"/>
<jsp:useBean id="PorRequisitoU" scope="page" class="aca.por.PorRequisitoUtil"/>
<jsp:useBean id="requisitos" scope="page" class="aca.por.PorRequisito"/>

	<script type="text/javascript">
		function eliminar(requisitoId){
			if(confirm("¿Está seguro que desea borrar la actividad?")){
				document.location = "requisitos?Accion=1&requisitoId="+requisitoId;
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
	
	String descripcion			= request.getParameter("descripcion")==null?"0":request.getParameter("descripcion");
	String valor				= request.getParameter("valor")==null?"0":request.getParameter("valor");
	String seccionId			= request.getParameter("seccion")==null?"0":request.getParameter("seccion");
	String tipo					= request.getParameter("tipo")==null?"0":request.getParameter("tipo");
	String requisitoId			= request.getParameter("requisitoId")==null?"0":request.getParameter("requisitoId");
	
	
	// Si el portafolio como parametro tiene un nuevo valor
	if ( !portafolioId.equals("0") ){
		session.setAttribute("portafolioId",portafolioId);
	}else{
		portafolioId = portafolioSession;
	}
	
%>

<%
	String accion 	= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String strNivel	= "";
	if(accion.equals("1")){
		requisitos.setRequisitoId(requisitoId);
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
	boolean existe = false;
	if(accion.equals("2")){
		requisitos.setRequisitoId(requisitoId);
		if(requisitos.existeReg(conEnoc)){
			requisitos.mapeaRegId(conEnoc, requisitoId);
			existe=true;
		}
	}
	
	
	if(accion.equals("3")){
		requisitos.setRequisitoId(requisitoId);
		if(requisitos.existeReg(conEnoc)){
			requisitos.mapeaRegId(conEnoc);
			requisitos.setDescripcion(descripcion);
			requisitos.setSeccionId(seccionId);
			requisitos.setValor(valor);
			requisitos.setTipo(tipo);
			requisitos.updateReg(conEnoc);
			existe = true;
		}else{
			requisitos.setRequisitoId(requisitos.maximoReg(conEnoc));
			requisitos.setDescripcion(descripcion);
			requisitos.setCategoriaId(categoriaId);
			requisitos.setValor(valor);
			requisitos.setSeccionId(seccionId);
			requisitos.setTipo(tipo);
			if(requisitos.insertReg(conEnoc)){
			}
			
		}
		
		
	}
	
%>
<div class="container-fluid">
	<h2><spring:message code="aca.Requisitos"/></h2>
	<div class="alert alert-info">	
		<a href="requisitos?CategoriaId=<%=categoriaId %>" class="btn btn-primary">Regresar</a>&nbsp;
	</div>		
	<form class="form-horizontal" role="form" method="get">	
		<input type="hidden" id="CategoriaId" name="CategoriaId" value=<%=categoriaId%>>
		<input type="hidden" id="requisitoId" name="requisitoId" value=<%=requisitos.getRequisitoId()==null?"":requisitos.getRequisitoId()%> >
		<input type="hidden" id="Accion" name="Accion" value="3">
		<div class="form-group">
		  <div class="col-sm-2">
		  <label for="requisito">Requisito:</label>
		    <textarea class="form-control" id="descripcion" name="descripcion" required><%if(existe){out.print(requisitos.getDescripcion());} %></textarea>
		
		  </div>
		</div>

		<div class="form-group">
		  <div class="col-sm-2">
	    	<label for="seccion">Seccion:</label>
		  <input type="text" class="form-control" id="seccion" name="seccion" required value=<%if(existe){out.print(requisitos.getSeccionId());} %>>
		  </div>
		</div>
		
		<div class="form-group">
		  <div class="col-sm-2">
		    <label for="valor">Valor:</label>
		  <input type="number" class="form-control" id="valor" name="valor" required step="0.5" min="0" value=<%if(existe){out.print(requisitos.getValor());} %>>
		  </div>
		</div>
		
		<div class="form-group">
		  <div class="col-sm-2">
		    <label for="tipo">Tipo:</label>
		    <select name="tipo" id="tipo" class="form-control" >
		    <option value="N" <%if(existe){if(requisitos.getTipo().equals("N")){out.print("Selected");}}%>>Normal</option>
		    <option value="I" <%if(existe){if(requisitos.getTipo().equals("I")){out.print("Selected");}}%>>Investigacion</option>
		    </select>
		  </div>
		</div>
		
		<button type="submit" class="btn btn-success">Guardar</button>
	</form>

</div>
<%@ include file="../../cierra_enoc.jsp" %>