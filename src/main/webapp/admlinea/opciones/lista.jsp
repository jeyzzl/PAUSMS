<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.admision.spring.AdmOpciones"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function Delete(opcionId){
		if(confirm("¿Estás seguro de borrar este item?")){
			document.location.href="borrarOpcion?OpcionId="+opcionId;
		}		
	}
</script>
	
<% 
	String mensaje  			= (String) request.getAttribute("mensaje");
	int planesEnAdmision		= (int) request.getAttribute("planesEnAdmision");
	
	List<AdmOpciones> listaOpciones  				= (List<AdmOpciones>) request.getAttribute("listaOpciones");
	HashMap<String,String> mapaTotales				= (HashMap<String,String>)request.getAttribute("mapaTotales");
	
	int row=1;
%>
<body>
<div class="container-fluid">
	<h2>Admission Option<small class="text-muted fs-5">(<spring:message code='admlinea.opciones.TotalAdmision'/>:<%=planesEnAdmision%>  )</small></h2>
	<div class="alert alert-primary" role="alert">
		<a href="editar" class="btn btn-primary"><spring:message code='aca.Agregar'/></a>
	</div>
<% if(mensaje.equals("1")){%>
	<div class="alert alert-success" role="alert">
		<spring:message code='aca.Grabado'/>
	</div>
<% }else if(mensaje.equals("2")){%>
	<div class="alert alert-danger" role="alert">
		<spring:message code='aca.NoGuardado'/>
	</div>
<% }else if(mensaje.equals("3")){%>
	<div class="alert alert-success" role="alert">
		<spring:message code='aca.Borrado'/>
	</div>
<% }else if(mensaje.equals("4")){%>
	<div class="alert alert-danger" role="alert">
		Did not Delete
	</div>
<% }%>
	<table id="table" class="table table-sm table-bordered"> 
	<thead>
		<tr class ="table-info">
        	<th>#</th>
  			<th><spring:message code='aca.Nombre'/></th>
  			<th><spring:message code='aca.Registrados'/></th>
		</tr>
	</thead>
	<tbody>
<%		
	for(AdmOpciones opcion : listaOpciones){
		String total = "0";
		if (mapaTotales.containsKey(opcion.getOpcionId())){
			total = mapaTotales.get(opcion.getOpcionId());
		}		
%>  
		<tr>
		   	<td>
		   		<%=row++%>&nbsp;&nbsp;
		   		<a href="editar?OpcionId=<%=opcion.getOpcionId()%>" title="Editar"><i class="far fa-edit"></i></a>
		   		<a href="editarPlanes?OpcionId=<%=opcion.getOpcionId()%>" title="Planes"><i class="fas fa-plus-circle"></i></a>
		   		<%if ( total.equals("0") || total.equals(null)){%>
				   <a href="javascript:Delete('<%=opcion.getOpcionId()%>')" title="<spring:message code="aca.Borrar"/>" style="color:red;">
					<i class="fas fa-trash-alt"></i></a>
				</td>
		<%}%>
		   	</td>
		   	<td><%=opcion.getNombre()%></td>
		   	<td><%=total.equals("0")?"<span class='badge bg-warning'>"+total+"</span>":"<span class='badge bg-dark'>"+total+"</span>"%></td>
		</tr>
<%		}%>
	</tbody>
	</table>	
</div>
</body>
<script src="../../js/search.js"></script>
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>