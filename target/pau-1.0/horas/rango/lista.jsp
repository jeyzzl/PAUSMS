<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.emp.spring.EmpRango"%>
<%@page import="aca.Mapa"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%
	String cargaId					= (String) request.getAttribute("cargaId");
	List<Carga> lisCargas 			= (List<Carga>) request.getAttribute("lisCargas");
	List<aca.Mapa> lisMaestros 		= (List<aca.Mapa>) request.getAttribute("lisMaestros");
	
	HashMap<String, EmpRango> mapaRangos 		= (HashMap<String, EmpRango>) request.getAttribute("mapaRangos");
	HashMap<String, String> mapaRangosEmpCargas = (HashMap<String, String>) request.getAttribute("mapaRangosEmpCargas");
%>
<div class="container-fluid">
	<h1>Maestros en carga</h1>
	<form name="forma" action="lista" method="get">
		<div class="alert alert-info d-flex align-items-center">
			<select name="CargaId" style="width:360px;" class="form-select" onchange="javascript:document.forma.submit();">
<%			for(Carga carga : lisCargas){%>
				<option <%if(cargaId.equals(carga.getCargaId()))out.print(" Selected ");%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId() %>] <%=carga.getNombreCarga()%></option>
<%			}%>
			</select>
			&nbsp;&nbsp;
			<input type="text" class="form-control" style="width:160px;" placeholder="Buscar..." id="buscar">
		</div>
	</form>
	<table class="table table-sm table-bordered" id="table ">
	<thead>
		<tr class="table-info"> 
			<th>Nomina</th>
			<th>Nomina</th>
			<th>Nombre</th>
			<th>Rango</th>
		</tr>
	</thead>
	<tbody>
<%	int row=0;
	for(aca.Mapa maestro : lisMaestros){
		row++;
		
		String rango = "";
		if(mapaRangosEmpCargas.containsKey(maestro.getLlave()+cargaId)){
			rango = mapaRangosEmpCargas.get(maestro.getLlave()+cargaId);
		}
		EmpRango empRango = new EmpRango();
		if(mapaRangos.containsKey(rango)){
			empRango = mapaRangos.get(rango);
		}
%>
		<tr onclick="document.location ='rango?CodigoEmpleado=<%=maestro.getLlave()%>&CargaId=<%=cargaId%>';">
			<td><%=row%></td>
			<td><%=maestro.getLlave()%></td>
			<td><%=maestro.getValor()%></td>
			<td><%=empRango.getRangoNombre()%></td>
		</tr>
<%	}%>
	</tbody>
	</table>
</div>
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>