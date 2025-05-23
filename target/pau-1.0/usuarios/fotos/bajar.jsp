<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<head>
<script type="text/javascript">
	function mostrar(){
		document.forma.submit();
	}	
</script>
</head>
<%	
	String tabla 	= request.getParameter("Tabla")==null?"alum_foto":request.getParameter("Tabla");
	String nombre 	= request.getParameter("Nombre")==null?"nofoto":request.getParameter("Nombre");  	
%>
<div class="container-fluid">
	<h2>Bajar Fotos</h2>
	<form name="forma" action="bajar" method="post">
	<div class="alert alert-info d-flex align-items-center">			
		<select name="Tabla" class="form-select" style="width:140px">	
			<option value="alum_foto" <%=tabla.equals("alum_foto")?"selected":""%>>Alumnos</option>
			<option value="emp_foto" <%=tabla.equals("emp_foto")?"selected":""%>>Empleados</option>
			<option value="foto_chica" <%=tabla.equals("foto_chica")?"selected":""%>>Fotos chicas</option>
		</select>&nbsp;
		<input name="Nombre" class="form-control" value="<%=nombre%>" style="width:240px"/>
		&nbsp;
		<a href="javascript:document.forma.submit();" class="btn btn-primary">Bajar</a>
	</div>
	<div>
<% 	if (tabla.equals("alum_foto")){%>	
		<img class="rounded border border-dark" src="../../fotoCuadrada?Codigo=<%=nombre%>&Tipo=O" width="150">
<% 	}else if (tabla.equals("emp_foto")){%>
		<img class="rounded border border-dark" src="../../empFoto?Codigo=<%=nombre%>&Tipo=O" width="150">
<%	}else{%>
		<img class="rounded border border-dark" src="../../fotochica?Codigo=<%=nombre%>&Tipo=O" width="150">
<%	}%>		
	</div>	
	</form>	
</div>