<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<% 
	List<aca.plan.spring.MapaNuevoUnidad> unidades								= (List<aca.plan.spring.MapaNuevoUnidad>)request.getAttribute("unidades");
	HashMap<String,aca.pron.spring.PronUnidad> mapaUnidad						= (HashMap<String,aca.pron.spring.PronUnidad>)request.getAttribute("mapaUnidad");
	HashMap<String,List<aca.plan.spring.MapaNuevoProducto>> mapaListaProductos 	= (HashMap<String,List<aca.plan.spring.MapaNuevoProducto>>)request.getAttribute("mapaListaProductos");

	String cursoCargaId = request.getParameter("CursoCargaId")==null?"0":request.getParameter("CursoCargaId");
	boolean envio		= (boolean)request.getAttribute("envio");	
	
	String materia 			= (String)request.getAttribute("materia");	
	String maestroNombre	= (String)request.getAttribute("maestroNombre");
	
	String completoMateria 	= (String)request.getAttribute("completoMateria");
	String completoUnidades = (String)request.getAttribute("completoUnidades");
	String completoEjes 	= (String)request.getAttribute("completoEjes");
	String completoValores 	= (String)request.getAttribute("completoValores");
	String completoEsquema 	= (String)request.getAttribute("completoEsquema");
	String completoBiblio 	= (String)request.getAttribute("completoBiblio");	
%>
<body>
	<div class="container-fluid">
		<h3>Course plan<small class="text-muted">( <%=maestroNombre%> - <%=materia%> )</small></h3>
		<div class="alert alert-success">
			<a class="btn btn-primary" href="cursos">Return</a>
<%		if(completoMateria.equals("Si") && completoUnidades.equals("Si") && /*completoEjes.equals("Si") && completoValores.equals("Si") &&*/ completoEsquema.equals("Si") && completoBiblio.equals("Si")){%>
			<a class="btn btn-success" href="enviarCursoCargaId?CursoCargaId=<%=cursoCargaId%>&Origen=pronUnidad">Send</a>
<%			if(envio){%>
				<a class="btn btn-primary" href="pdfPron?CursoCargaId=<%=cursoCargaId%>" target="_blank">Print</a>
<%			}%>
<%      }%>
		</div>		
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
				<div class="navbar-nav">
					<ul class="nav nav-tabs" role="tablist">
					    <li class="nav-item nav-link"><a href="pronMateria?CursoCargaId=<%=cursoCargaId%>">Subject <%if(completoMateria.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li> 
					    <li class="nav-item nav-link active"><a href="#home" aria-controls="home" role="tab" data-bs-toggle="tab">Units <%if(completoUnidades.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li>
					    <li class="nav-item nav-link"><a href="pronEjes?CursoCargaId=<%=cursoCargaId%>">Axes <%if(completoEjes.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li> 
					    <li class="nav-item nav-link"><a href="pronValor?CursoCargaId=<%=cursoCargaId%>">Principles <%if(completoValores.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li>
					    <li class="nav-item nav-link"><a href="pronEsquema?CursoCargaId=<%=cursoCargaId%>">Schema <%if(completoEsquema.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li> 
					    <li class="nav-item nav-link"><a href="pronBiblio?CursoCargaId=<%=cursoCargaId%>">Bibliography <%if(completoBiblio.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li> 
					</ul>
				</div>
			</div>
		</nav>
<%
	for(aca.plan.spring.MapaNuevoUnidad unidad : unidades){
		String ordenUnidad = "0";
		if (mapaUnidad.containsKey(unidad.getUnidadId())){
			ordenUnidad = mapaUnidad.get(unidad.getUnidadId()).getOrden();
		}
%>
		<form action="grabaPronUnidad" method="POST">
			<input type="hidden" name="CursoCargaId" value="<%=cursoCargaId%>">
			<input type="hidden" name="UnidadId" value="<%=unidad.getUnidadId()%>">
			<div class="alert alert-info" role="alert">
				<h4><%=unidad.getNombre()%> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Order: 
					<input size="2px;" type="text" name="Orden" value="<%=ordenUnidad%>">
				</h4>
			</div>
			<div class="alert alert-secondary">
				<div class="row">
					<%							
						String strProductos ="";
						if(mapaListaProductos.containsKey(unidad.getUnidadId())){
							List<aca.plan.spring.MapaNuevoProducto> productos = mapaListaProductos.get(unidad.getUnidadId());
 							for(aca.plan.spring.MapaNuevoProducto producto : productos){
								strProductos += producto.getDescripcion()+"<br>";
							}
 						}						
 					%>
					<div class="col-md-6">
						<strong>Learning products:<br><br></strong>
						<label><%=strProductos%></label>
					</div>
					<div class="col-md-6">
						<strong>Contribution to the integration project:<br><br>
						</strong><textarea rows="5" cols="" class="form-control" name="Aporte"><%=mapaUnidad.containsKey(unidad.getUnidadId())?mapaUnidad.get(unidad.getUnidadId()).getAporte():"-"%></textarea>
					</div>
				</div>
			</div>			
			<input type="submit" class="btn btn-primary" value="Save">
<% 			if(mapaUnidad.containsKey(unidad.getUnidadId())){%>
			<a class="btn btn-primary" href="pronSemana?CursoCargaId=<%=cursoCargaId%>&UnidadId=<%=unidad.getUnidadId()%>">Weeks</a>&nbsp;
<% 			}%>
		</form>
<% 	}%>
	</div>
</body>