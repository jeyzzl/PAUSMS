<%@page import="java.util.List"%>
<%@page import="aca.calcula.spring.CalCosto"%>
<%@page import="aca.calcula.spring.CalConcepto"%>

<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">

</script>

<%	
	CalCosto costo		= (CalCosto)request.getAttribute("costo");
	String mensaje		= (String)request.getAttribute("mensaje");
	String periodoId	= (String)request.getAttribute("periodoId");
	String cargaId		= (String)request.getAttribute("cargaId");
	String bloqueId		= (String)request.getAttribute("bloqueId");
	
	List<CalConcepto> listaConceptos 	= (List<CalConcepto>)request.getAttribute("listaConceptos");
%>
<div class="container-fluid">
	<h1>Costs</h1>
	<form name="forma" action="grabar" method="post">
		<input type="hidden" name="CostoId" value="<%=costo.getCostoId()%>">
		<input type="hidden" name="PeriodoId" value="<%=periodoId%>">
		<input type="hidden" name="CargaId" value="<%=cargaId%>">
		<input type="hidden" name="BloqueId" value="<%=bloqueId%>">
		<div class="alert alert-info d-flex align-items-center">
			<a href="lista?PeriodoId=<%=periodoId%>&CargaId=<%=cargaId%>&BloqueId=<%=bloqueId%>" class="btn btn-primary btn-sm"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		</div>
<% 	if(mensaje.equals("1")){%>
		<div class="alert alert-success">
			Saved!
		</div>
<% 	}else if(mensaje.equals("2")){%>
		<div class="alert alert-danger">
			Did not save!
		</div>
<% 	}%>
		<div class="row">
			<div class="col">
				<label class="form-label">Type</label>
				<select class="form-select" name="ConceptoId">
<% 				for(CalConcepto concepto : listaConceptos){%>
					<option <%=costo.getConceptoId().equals(concepto.getConceptoId())?"selected":""%> value="<%=concepto.getConceptoId()%>"><%=concepto.getTipo()%> - <%=concepto.getConceptoNombre()%></option> 
<% 				}%> 
				</select><br>
				
			</div>
			<div class="col">
				<label class="form-label">Amount</label> 
				<input class="form-control" type="text" name="Importe" value="<%=costo.getImporte()%>"/><br>
			</div>
		</div>
		<label class="form-label">Comment</label> 
		<input class="form-control" type="text" name="Comentario" value="<%=costo.getComentario()%>"/><br>
		<button class="btn btn-primary" type="submit">Save</button>
	</form>
</div>
