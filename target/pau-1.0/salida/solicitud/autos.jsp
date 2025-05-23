<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%@page import="aca.salida.spring.SalAuto"%>
<%@page import="java.util.List"%>

<script>
	function Grabar(){
		document.frmAuto.submit();
	}
</script>	
<%
	
	String salidaId  		= request.getParameter("SalidaId")==null?"0":request.getParameter("SalidaId");
 	List<SalAuto> lista		= (List<SalAuto>) request.getAttribute("lista");

	String usuario 			= (String) session.getAttribute("codigoPersonal");	
	String codigoAlumno		= (String) session.getAttribute("codigoAlumno");	
%>
<div class="container-fluid">
	<h2>Listado de autos</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="solicitud">Regresar</a>	
	</div>
	<form id="frmAuto" name="frmAuto" method="post" action="grabarAuto">
		<input type="hidden" name="SalidaId" value="<%=salidaId%>">
		<div class="alert alert-info d-flex align-items-center">
			<b>Tipo:</b>&nbsp;				
			<select style="width:200px;" name="Tipo" id="Tipo" class="form-select">
				<option value="C">Comercial</option>
				<option value="P">Particular</option>
				<option value="U">Universitario</option>
			</select>&nbsp;&nbsp;
			<b>Poliza:</b>&nbsp;&nbsp;		
				<input type="text" name="Poliza" style="width:200px;" class="form-control" id="Poliza" required="required"/>
			<b>Telefono:</b>&nbsp;&nbsp;		
				<input type="text" name="Telefono" style="width:200px;" class="form-control" id="Telefono"/>	
				&nbsp;&nbsp;	
				<a onclick="javascript:Grabar();" class="btn btn-primary"><i class="fas fa-check icon-white"></i> Grabar</a>	
		</div>			
	</form>	
	<table class="table table-sm table-bordered">
	<thead>
		<tr>
			<th>Opción</th>
			<th>#</th>
			<th>Salida Id</th>
			<th>Folio</th>
			<th>Tipo</th>
			<th>Poliza</th>
			<th>Teléfono</th>					
		</tr>
	</thead>
	<tbody>
<%
	int row=0;
	for (SalAuto auto : lista){
		row++;
		String tipo = " ";
		if (auto.getTipo().equals("C")) 
			tipo = "Comercial";
		else if (auto.getTipo().equals("P")){
			tipo = "Particular";
		}else{
			tipo = "Universitario";
		}
%>		
		<tr>
			<td><a href="borrarAuto?SalidaId=<%=salidaId%>&Folio=<%=auto.getFolio()%>"><i class="fas fa-trash" ></i></a></td>
			<td><%=row%></td>
			<td><%=auto.getSalidaId()%></td>
			<td><%=auto.getFolio()%></td>
			<td><%=tipo%></td>
			<td><%=auto.getPoliza()%></td>
			<td><%=auto.getTelefono()%></td>
		</tr>
<%	} %>		
	</tbody>
	</table>			
</div>
</html>