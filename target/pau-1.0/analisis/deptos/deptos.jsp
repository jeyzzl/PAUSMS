<%@page import="aca.est.spring.EstCcosto"%>
<%@page import="aca.est.spring.EstMaestro"%>
<%@page import="aca.plan.spring.MapaCurso"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%
	java.text.DecimalFormat getFormato = new java.text.DecimalFormat("##0.0000;-##0.0000");
	java.text.DecimalFormat getFormato2 = new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String facultad			= request.getParameter("Facultad")==null?"1":request.getParameter("Facultad");
	String estado			= request.getParameter("Estado")==null?"1":request.getParameter("Estado");
	
	List<String> listDepartamentos 					= (List<String>) request.getAttribute("listDepartamentos");
	HashMap<String,String> mapaTotalDepartamento 	= (HashMap<String,String>) request.getAttribute("mapaTotalDepartamento");
	HashMap<String,String> mapaDeptos 				= (HashMap<String,String>) request.getAttribute("mapaDeptos");	
%>
<div class="container-fluid">
	<h2>Distribución departamentos</h2> 
	<form name="frmMaestro" action="maestros" method="post">
		<table class="table table-bordered">
		<thead class="table-info">
		<tr>
			<th width="5%" class="center">#</th>
			<th width="15%" class="left">Ccosto</th>
			<th width="75%" class="left">Departamento</th>
			<th width="5%" class="right">Total</th>				
		</tr>
		</thead>
	<%		
		int row = 0;
		String codigo = "0";
		double totalTotal = 0;
		
		for(String departamento : listDepartamentos){
			row++;
			String total = "0";
			if(mapaTotalDepartamento.containsKey(departamento)){
				total = mapaTotalDepartamento.get(departamento);
			}
			
			totalTotal = totalTotal+Double.parseDouble(total);
			
			String nombreDepto = "-";
			if (mapaDeptos.containsKey(departamento)){
				nombreDepto = mapaDeptos.get(departamento);
				
			}		
	%>
		<tr>
			<td class="center"><%=row%></td>		
			<td class="left"><a href="infoDepto?CcostoId=<%=departamento%>"><%=departamento%></a></td>
			<td class="left"><%=nombreDepto%></td>
			<td class="right"><%=getFormato2.format(Double.parseDouble(total))%></td>			
		</tr>	
	<%	
		}
	%>
		<tr>
			<th class="right" colspan="3"><strong>Total</strong></th>		
			<th class="right"><%=getFormato2.format(totalTotal)%></th>
		</tr>	
		</table>
	</form>
</div>