<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>

<%@page import="aca.admision.spring.AdmCartaSubir"%>
<%@page import="aca.parametros.spring.Parametros"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
	
<%	 
	
	AdmCartaSubir carta 	= (AdmCartaSubir)request.getAttribute("carta");
	Parametros parametros 	= (Parametros)request.getAttribute("parametros");

%>
<script type="text/javascript">
	function borrarCarta(folio){
		if(confirm("Do you wish to delete this Acceptace Letter?")){
			document.location.href = "borrarCartaAdmision?Folio="+folio;
		}
	}
</script>
<body>
	<div class="container-fluid">
		<h2>Process Id: <%=carta.getFolio()%></h2>
		<div class="alert alert-info">
			<a class="btn btn-primary" href="mostrarProceso?Folio=<%=carta.getFolio()%>">Return</a>
		</div>
			<div class="col-sm-12">
				<h3>Acceptance Letter</h3>
				<form name="frmArchivo" id="frmArchivo" enctype="multipart/form-data" action="grabarCartaAdmision" method="post">
					<input type="hidden" name="Folio" value="<%=carta.getFolio()%>">
					<div class="fileupload fileupload-new d-flex align-items-center" data-provides="fileupload">	
<% 					if(carta.getCarta() != null){%>
						<h5 class="me-3"><%=carta.getNombre()%></h5>
						<br>
						<a class="btn btn-danger btn-sm" href="javascript:borrarCarta('<%=carta.getFolio()%>')"><i class="fas fa-trash"></i></a>
<% 					} else{%>	
						<span class="btn  btn-default btn-file me-3">
					    	<input type="file" id="archivo" name="archivo"/>
						</span>
						<button class="btn btn-primary btn-large" id="btnGuardar" value="Enviar" type="submit" class="enviar" > 
							Save
						</button>	
<%					}%>
					</div>
					<div class="alert alert-info mt-3">
						<a class="btn btn-success" href="generarCarta?Folio=<%=carta.getFolio()%>">Generate Letter</a>
					</div>
				</form>
			</div>
	</div>	
</body>