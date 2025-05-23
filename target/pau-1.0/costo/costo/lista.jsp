<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.calcula.spring.CalCosto"%>
<%@page import="aca.calcula.spring.CalConcepto"%>
<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.carga.spring.CargaBloque"%>

<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function borrar(costoId,conceptoId){
		var periodoId 	= document.getElementById("PeriodoId").value;
		var cargaId 	= document.getElementById("CargaId").value;
		var bloqueId 	= document.getElementById("BloqueId").value;
		if (confirm("Are you sure youo want to erase this Amount?")){
			document.location.href = "borrar?CostoId="+costoId+"&ConceptoId="+conceptoId+"&PeriodoId="+periodoId+"&CargaId="+cargaId+"&BloqueId="+bloqueId;
		}
	}	
	
	function cambioPeriodo(){
		var periodoId = document.getElementById("PeriodoId").value;
		document.location.href = "lista?PeriodoId="+periodoId;
	}	
	
	function cambioCarga(){
		var periodoId 	= document.getElementById("PeriodoId").value;
		var cargaId 	= document.getElementById("CargaId").value;
		document.location.href = "lista?PeriodoId="+periodoId+"&CargaId="+cargaId;
	}
	
	function cambioBloque(){
		var periodoId 	= document.getElementById("PeriodoId").value;
		var cargaId 	= document.getElementById("CargaId").value;
		var bloqueId 	= document.getElementById("BloqueId").value;
		document.location.href = "lista?PeriodoId="+periodoId+"&CargaId="+cargaId+"&BloqueId="+bloqueId;
	}
</script>
<%	
	String periodoId	= (String)request.getAttribute("periodoId");
	String cargaId		= (String)request.getAttribute("cargaId");
	String bloqueId		= (String)request.getAttribute("bloqueId");
	
	List<CatPeriodo> lisPeriodos 	 	= (List<CatPeriodo>)request.getAttribute("lisPeriodos");
	List<Carga> lisCargas		 		= (List<Carga>)request.getAttribute("lisCargas");
	List<CargaBloque> lisBloques		= (List<CargaBloque>)request.getAttribute("lisBloques");
	List<CalCosto> lista				= (List<CalCosto>)request.getAttribute("lista");
	List<CalConcepto> listaConceptos	= (List<CalConcepto>)request.getAttribute("listaConceptos");

	HashMap<String,CalConcepto> mapaConceptos	= (HashMap<String,CalConcepto>)request.getAttribute("mapaConceptos");
%>
<div class="container-fluid">
	<h1>List of Costs</h1>
	<form name="forma" action="lista" method="post">
		<div class="alert alert-info d-flex align-items-center">
			<a class="btn btn-primary btn-sm" href="editar?PeriodoId=<%=periodoId%>&CargaId=<%=cargaId%>&BloqueId=<%=bloqueId%>">Add</a>&nbsp;&nbsp;
				Period:
				<select id="PeriodoId" name="PeriodoId" onchange="javascript:cambioPeriodo();" class="form-select" style="width:200px">
				<%for(CatPeriodo periodo : lisPeriodos){%>
					<option value="<%=periodo.getPeriodoId()%>" <%=periodo.getPeriodoId().equals(periodoId)?"selected":""%>><%=periodo.getNombre()%></option>
				<%}%>
				</select>
				&nbsp;
				Load:
				<select id="CargaId" name="CargaId" onchange="javascript:cambioCarga();" class="form-select" style="width:350px">
				<%for(Carga carga : lisCargas){%>
					<option value="<%=carga.getCargaId()%>" <%=carga.getCargaId().equals(cargaId)?"selected":""%>><%=carga.getCargaId()%>-<%=carga.getNombreCarga()%></option>
				<%}%>
				</select>
				&nbsp;
				Block:
				<select id="BloqueId" name="BloqueId" class="form-select" onchange="javascript:cambioBloque();" style="width:330px">
<%
			for (aca.carga.spring.CargaBloque bloque : lisBloques){
				if (bloque.getCargaId().equals(cargaId)){
%>		
				<option value="<%=bloque.getBloqueId()%>" <%=bloqueId.equals(bloque.getBloqueId())?" Selected":""%>><%=bloque.getBloqueId()%>-<%=bloque.getNombreBloque()%></option>
<%	
				}
			} 
%>			
				</select>
		</div>
	</form>
	<table class="table">
		<thead class="">
			<tr>
				<th>#</th>
				<th>Costs</th>
				<th>Type Label</th>
				<th>Load</th>
				<th>Block</th>
				<th>Type</th>
				<th>Amount</th>
				<th>Comment</th>
			</tr>
		</thead>
		<tbody>
<% 		for(CalCosto objeto : lista){
			String concepto = "";
			if(mapaConceptos.containsKey(objeto.getConceptoId())){
				concepto = mapaConceptos.get(objeto.getConceptoId()).getConceptoNombre();
			}
%>
			<tr>
				<td><%=objeto.getCostoId()%> 
					<a href="editar?CostoId=<%=objeto.getCostoId()%>&ConceptoId=<%=objeto.getConceptoId()%>" title="Edit"><i class="fas fa-edit"></i></a>&nbsp;&nbsp;
					<a onclick="borrar('<%=objeto.getCostoId()%>','<%=objeto.getConceptoId()%>')" title="Delete"><i class="fas fa-times"></i></a>
				</td>
				<td><%=objeto.getCostoId()%></td>
				<td><%=concepto%></td>
				<td><%=objeto.getCargaId()%></td>
				<td><%=objeto.getBloqueId()%></td>
				<td><%=objeto.getTipo()%></td>
				<td><%=objeto.getImporte()%></td>
				<td><%=objeto.getComentario()%></td>
			</tr>
<% 		}%>
		</tbody>
	</table>
</div>