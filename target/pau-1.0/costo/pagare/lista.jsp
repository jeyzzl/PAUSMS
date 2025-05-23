<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.calcula.spring.CalPagare"%>
<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.carga.spring.CargaBloque"%>

<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function borrar(pagareId){
		if (confirm("Are you sure you want to delete this Installment?")){
			document.location.href = "borrar?PagareId=" + pagareId;
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
	
	List<CalPagare> lista			= (List<CalPagare>)request.getAttribute("lista");
	List<CatPeriodo> lisPeriodos 	= (List<CatPeriodo>)request.getAttribute("lisPeriodos");
	List<Carga> lisCargas		 	= (List<Carga>)request.getAttribute("lisCargas");
	List<CargaBloque> lisBloques	= (List<CargaBloque>)request.getAttribute("lisBloques");

	HashMap<String, CargaBloque> mapaBloques = (HashMap<String, CargaBloque>)request.getAttribute("mapaBloques");
	
	int con = 1;
%>
<div class="container-fluid">
	<h1>List of installments</h1>
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
			for (CargaBloque bloque : lisBloques){
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
				<th>Installment</th>
				<th>Date</th>
				<th>Load</th>
				<th>Block</th>
			</tr>
		</thead>
		<tbody>
<% 		for(CalPagare objeto : lista){
			String nombreBloque = ""; 
			if(mapaBloques.containsKey(objeto.getCargaId()+objeto.getBloqueId())){
				nombreBloque = mapaBloques.get(objeto.getCargaId()+objeto.getBloqueId()).getNombreBloque();
			}
%>
			<tr>
				<td><%=con++%> 
				<a href="editar?PagareId=<%=objeto.getPagareId()%>" title="Edit"><i class="fas fa-edit"></i></a>&nbsp;&nbsp;
				<a onclick="borrar('<%=objeto.getPagareId()%>')" title="Delete"><i class="fas fa-times"></i></a>
				</td>
				<td><%=objeto.getPagareNombre()%></td>
				<td><%=objeto.getFecha()%></td>
				<td><%=objeto.getCargaId()%></td>
				<td><%=objeto.getBloqueId()+" "+nombreBloque%></td>
			</tr>
<% 		}%>
		</tbody>
	</table>
</div>