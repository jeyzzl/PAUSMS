<%@ include file= "id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<head>
	<script type="text/javascript">		
		jQuery(document).ready(function() {
			jQuery("input.file").si();
		});
		
		function guardar(){
			if($("archivos").value != ""){
				$("archivos").hidden = true;
				$("btnGuardar").disabled = true;
				$("btnGuardar").value = "Sending...";
				document.formaEnviar.submit();
			}else
				alert("Select at least one file");
		}
	</script>
</head>
<%
	String usuario 			= (String) session.getAttribute("codigoPersonal");
	String totales 			= request.getParameter("Total")==null?"0":request.getParameter("Total");
	String subidas 			= request.getParameter("Subidas")==null?"0":request.getParameter("Subidas");
	String codigoAlumno 	= request.getParameter("CodigoAlumno")==null?"0":request.getParameter("CodigoAlumno");
	
	String colorBien 	= "green";
	String colorMal 	= "#AE2113";
	boolean subioDatos = false;
	if(!totales.equals("0") && !subidas.equals("0")){
		subioDatos = true;
	}
%>
<body>
<div class="container-fluid">
	<h1>Upload documents in batch</h1>
	<div class="alert alert-info d-flex align-items-center">		
	<form name="formaEnviar" id="formaEnviar" enctype="multipart/form-data" action="guardarArchivo" method="post">	
<%	
	if(subioDatos){
		boolean bien = !subidas.equals("0") && totales.equals(subidas);
		
%>
		<font size="4">Images uploaded </font>
		<font color="<%=bien?colorBien:colorMal %>" size="4"><b><%=subidas %></b></font>
		<font size="4"> from 
		<font color="<%=bien?colorBien:colorMal %>" size="4"><b><%=totales %></b></font><br><br></font>
	<%	if(bien){ %>
			<a class="btn btn-info" href="../documentos_alumno/general?Codigo=<%=codigoAlumno%>">Go to unclassified documents</a>
			<script>
				setTimeout(function(){document.location.href="subir";}, 4000);
			</script>
	<%	}else{%>	
			<script>
				alert("Error while uploading images, no change was made.");
				setTimeout(function(){document.location.href="subir";}, 1000);
			</script>
<%		} %>
<%	}else{ %>
		<input style="position:absolute;" type="file" multiple accept="image/jpeg" class="file" id="archivos" name="archivos" />
		<br><br>
		<input class="btn btn-primary" type="button" id="btnGuardar" value="Send documents" onclick="javascript:guardar();"/>
<%	} %>
	</form>
	</div>	
</div>
</body>