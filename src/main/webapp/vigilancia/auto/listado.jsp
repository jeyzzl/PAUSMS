<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.vigilancia.spring.VigAuto"%>
<%@page import="aca.vigilancia.spring.VigDoc"%>

<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%	
	String mensaje 								= (String) request.getAttribute("mensaje");
	List<VigAuto> lisAutos 						= (List<VigAuto>) request.getAttribute("lisAutos");
	List<VigDoc> lisDocumentos 					= (List<VigDoc>) request.getAttribute("lisDocumentos");
	HashMap<String,String> mapaImagenes 		= (HashMap<String,String>) request.getAttribute("mapaImagenes");
	HashMap<String,String> mapaUsuarios 		= (HashMap<String,String>) request.getAttribute("mapaUsuarios");
	HashMap<String,String> mapaElementos 		= (HashMap<String,String>) request.getAttribute("mapaElementos");
	HashMap<String,String> mapaDocAutos			= (HashMap<String,String>) request.getAttribute("mapaDocAutos");
%>
<div class="container-fluid">
	<h2>Autos</h2>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="editar">Agregar</a>
<%	if (!mensaje.equals("-")) out.print("&nbsp; &nbsp;<span>"+mensaje+"</span>"); %>
	</div>
	<table class="table table-bordered table-sm table-striped">
	<thead class="table-info">	
	<tr>
		<th>Op</th>
		<th>#</th>	
		<th>Propietario</th>
		<th>Placas</th>
		<th>Engomado</th>		
		<th>Color</th>
		<th>Modelo</th>
		<th>Marca</th>
		<th>Póliza</th>
		<th>Fecha</th>
		<th>Comentario</th>
<%	for (VigDoc doc : lisDocumentos){%>
		<th><%=doc.getCorto()%></th>
<%	} %>		
		<th>Estado</th>
	</tr>
	</thead>		
	<tbody>
<%	
	int cont = 1;
	for(VigAuto auto : lisAutos){	
		
		String usuarioNombre = "-";
		if (mapaUsuarios.containsKey(auto.getUsuario())){
			usuarioNombre = mapaUsuarios.get(auto.getUsuario());
		}
		
		String total = "0";
		if (mapaElementos.containsKey(auto.getAutoId())){
			total = mapaElementos.get(auto.getAutoId());
		}
%>
	<tr>
		<td>				
			<a href="editar?AutoId=<%=auto.getAutoId()%>" title="Editar"><i class="fas fa-edit"></i></a>
<% 		if (total.equals("0")){%>
			<a href="javascript:BorrarAuto('<%=auto.getAutoId()%>');" title="Editar"><i class="fas fa-trash-alt" style="color:red"></i></a>
<%		} %>			
		</td>
		<td>
			<%=cont++%>				
		</td>		
		<td><a href="usuario?AutoId=<%=auto.getAutoId()%>"><i class="fas fa-user"></i></a>&nbsp;<%=auto.getUsuario()%> - <%=usuarioNombre%></td>
		<td><%=auto.getPlacas()%></td>
		<td><%=auto.getEngomado()%></td>		
		<td><%=auto.getColor()%></td>
		<td><%=auto.getModelo()%></td>
		<td><%=auto.getMarca()%></td>
		<td><%=auto.getPoliza()%></td>
		<td><%=auto.getFecha()%></td>
		<td><%=auto.getComentario()%></td>
<%		for (VigDoc doc :lisDocumentos){
	
			String vigencia = "01/01/2020";
			boolean existe 	= false;
			if (mapaDocAutos.containsKey(auto.getAutoId()+"-"+doc.getDocumentoId())){
				vigencia = mapaDocAutos.get(auto.getAutoId()+"-"+doc.getDocumentoId());
				existe = true;
			}
			int dias = aca.util.Fecha.diasEntreFechas(aca.util.Fecha.getHoy(), vigencia);
			
			String color = "-";
			if (existe){
				if(dias>30){
				   color = "color:green";
				}else if(dias<0){
					color = "color:red";
				}else if(dias>=0 && dias<=30){
					color = "color:orange";
				}
			}else{
				color = "color:#CCCCCC";
			}
%>		
		<td>
			<i class="fas fa-calendar-day" style="<%=color%>" data-bs-toggle="tooltip" title="<%=vigencia%>"></i>
<% 			if(mapaImagenes.containsKey(auto.getAutoId()+"-"+doc.getDocumentoId()+"-1")){%>
			<a href="editarDocumento?AutoId=<%=auto.getAutoId()%>&DocId=<%=doc.getDocumentoId()%>&Hoja=1"><span class="badge rounded-pill bg-dark">1</span></a>
<% 			}else{%>
			<a href="editarDocumento?AutoId=<%=auto.getAutoId()%>&DocId=<%=doc.getDocumentoId()%>&Hoja=1"><span class="badge rounded-pill bg-secondary">1</span></a>
<% 			}%>
<% 			if(mapaImagenes.containsKey(auto.getAutoId()+"-"+doc.getDocumentoId()+"-2")){%>
			<a href="editarDocumento?AutoId=<%=auto.getAutoId()%>&DocId=<%=doc.getDocumentoId()%>&Hoja=2"><span class="badge rounded-pill bg-dark">2</span></a>
<% 			}else{%>
			<a href="editarDocumento?AutoId=<%=auto.getAutoId()%>&DocId=<%=doc.getDocumentoId()%>&Hoja=2"><span class="badge rounded-pill bg-secondary">2</span></a>
<% 			}%>
		</td>		
<%		} %>
		<td><%=auto.getEstado().equals("A")?"Activo":"Inactivo"%></td>
	</tr>	
<% 	}%>
	</tbody>
	</table>
</div>
<script>
	function BorrarAuto(autoId){
		if (confirm("¿Estás seguro de borrar el registro del auto?")){
			document.location.href= "borrar?AutoId="+autoId;
		}
	}
	
	var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
	var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
	  return new bootstrap.Tooltip(tooltipTriggerEl)
	})
</script>