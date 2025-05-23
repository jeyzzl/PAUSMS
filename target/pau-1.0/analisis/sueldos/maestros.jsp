<%@page import="aca.est.spring.EstCcosto"%>
<%@page import="aca.est.spring.EstMaestro"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<head>	
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
 </head>

<%
	java.text.DecimalFormat getFormato = new java.text.DecimalFormat("##0.0000;-##0.0000");
	java.text.DecimalFormat getFormato2 = new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String facultad			= request.getParameter("Facultad")==null?"1":request.getParameter("Facultad");
	String estado			= request.getParameter("Estado")==null?"1":request.getParameter("Estado");	
		
	List<EstMaestro> lisMaestros 		= (List<EstMaestro>) request.getAttribute("lisMaestros");
	
	HashMap<String,String> mapaMaestros	= (HashMap<String,String>) request.getAttribute("mapaMaestros");
	
%>
<div class="container-fluid">
	<h2>Maestros/recursos</h2>
	<div class="alert alert-info">
<!-- 		<input type="text" id="myInput" onkeyup="myFunction()" placeholder="Buscar..."> -->
		<input id="myInput" type="text" placeholder="Buscar..">
	</div>
	<form name="frmMaestro" action="maestros" method="post">
		<table class="table table-bordered">
			<thead class="table-info">
				<tr class="header">
					<th width="1%" class="center">Op.</th>		
					<th width="1%" class="center">#</th>
					<th width="10%" class="left"><spring:message code="aca.Nomina"/></th>
					<th width="55%" class="left"><spring:message code="aca.Maestro"/></th>
					<th width="15%" class="right">Tipo</th>
					<th width="10%" class="right">No. Horas</th>
					<th width="10%" class="right">$ Importe</th>
					
				</tr>
			</thead>
<%		
		String alert = "class='alert alert-success'";
		int row = 0;
		String codigo = "0";
		double total = 0;
		for(EstMaestro maestro : lisMaestros){
			row++;
			if (!maestro.getCodigoPersonal().equals(codigo)){
				if (alert.equals("class='alert alert-success'")) alert = ""; else alert = "class='alert alert-success'";
				codigo = maestro.getCodigoPersonal();
			}
			
			String nombreMaestro = "-";
			if (mapaMaestros.containsKey(maestro.getCodigoPersonal())){
				nombreMaestro = mapaMaestros.get(maestro.getCodigoPersonal());
			}		
			
			total += Double.parseDouble(maestro.getImporte());
			
			String tipo = "-";
			if(maestro.getTipo().equals("Z")) tipo = "Visitante";
			else if(maestro.getTipo().equals("E")) tipo = "Externo";
			else if(maestro.getTipo().equals("C")) tipo = "HLC";
			else if(maestro.getTipo().equals("V")) tipo = "Virtual";
			else if(maestro.getTipo().equals("H")) tipo = "Horas/Contrato";
			else if(maestro.getTipo().equals("I")) tipo = "Intercambio";
			else if(maestro.getTipo().equals("A")) tipo = "UM Pres.";
			else if(maestro.getTipo().equals("X")) tipo = "No clasificado";
			else if(maestro.getTipo().equals("N")) tipo = "Nomina";
			else if(maestro.getTipo().equals("B")) tipo = "Visitante";
			else if(maestro.getTipo().equals("J")) tipo = "Jubilado";
			else if(maestro.getTipo().equals("G")) tipo = "Gratuito";
			else if(maestro.getTipo().equals("M")) tipo = "Contrato";
			
%>
			<tbody id="myTable">
				<tr <%=alert%> >
					<td  width="1%" class="center">
						<a href="editar?Nomina=<%=maestro.getCodigoPersonal()%>"><i class="fas fa-edit"></i></a>&nbsp;&nbsp;
					</td>		
					<td width="1%" class="center"><%=row%></td>		
					<td width="10%" class="left"><%=maestro.getCodigoPersonal()%></td>
					<td width="55%" class="left"><%=nombreMaestro%></td>
					<td width="15%" class="right"><%=tipo=="-"?maestro.getTipo():tipo%></td>
					<td width="10%" class="right"><%=maestro.getHoras()%></td>
					<td width="10%" class="right"><%=getFormato2.format(Double.parseDouble(maestro.getImporte()))%></td>
				</tr>	
			</tbody>
<%	
		}
%>
			<tr>
				<th class="right" " colspan="6">TOTAL:</th>
				<th class="right"><%=getFormato2.format(total)%></th>
			</tr>	
		</table>
	</form>
</div>
<script type="text/javascript">	

	$(document).ready(function(){
	  $("#myInput").on("keyup", function() {
	    var value = $(this).val().toLowerCase();
	    $("#myTable tr").filter(function() {
	      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	    });
	  });
	});
</script>
<script src="../../js/jquery-1.9.1.min.js"></script>
<script src="../../js/search.js"></script>
