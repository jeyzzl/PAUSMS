<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="PorSeccionU" scope="page" class="aca.por.PorSeccionUtil"/>
<jsp:useBean id="Seccion" scope="page" class="aca.por.PorSeccion"/>

<script type="text/javascript">
	function Guardar(){
		if(document.frmSeccion.nombre.value != ""){
			document.frmSeccion.Accion.value="1";
			document.frmSeccion.submit();
		}else{
			alert("Agregale un nombre a la nueva seccion");
		}
	}	
</script>
<%
	String portafolioId		= request.getParameter("PortafolioId")==null?"0":request.getParameter("PortafolioId");
	String seccionId 		= request.getParameter("SeccionId")==null?"0":request.getParameter("SeccionId");
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String seccionNew 		= "0";
	String msj 				= "";
	boolean existe 			= false;
	
	if(accion.equals("1")){
		int maximo = Integer.parseInt(Seccion.maximoSeccion(conEnoc, seccionId));
		//System.out.println("Maximo:"+maximo);
		if(maximo < 10){
			if (seccionId.equals("0"))
				seccionNew = "0"+maximo;
			else
				seccionNew = seccionId+"0"+maximo;
		}else{ 
			seccionNew = seccionId+maximo;
		}	
		
		Seccion.setPorId(portafolioId);
		Seccion.setSeccionId(seccionNew);
		Seccion.setSeccionNombre(request.getParameter("nombre"));
		Seccion.setSeccionSuperior(seccionId);
		Seccion.setTipo(request.getParameter("tipo"));
		Seccion.setTitulo(request.getParameter("titulo"));		
		Seccion.setAcceso("T");
		Seccion.setOrden(request.getParameter("Orden"));
		Seccion.setEstado(request.getParameter("Estado"));
		Seccion.setInstrucciones(request.getParameter("instrucciones"));
		if(Seccion.insertReg(conEnoc)){
			msj = "<div class='alert alert-success'>Guardado Correctamente</div>";
			existe = true;
			Seccion.mapeaRegId(conEnoc, portafolioId, seccionNew);
		}else{
			msj = "<div class='alert alert-danger'>Hubo un error al guardar</div>";
		}
	}	
%>
<div class="container-fluid">
	<h1><spring:message code="aca.Secciones"/></h1>
	
	<div class="alert alert-info"><a href="porSeccion?porId=<%=portafolioId %>" class="btn btn-primary"><i class="icon-white icon-chevron-left"></i> Regresar</a></div> 
	 
	<%out.print(msj); %>

	<form name="frmSeccion" action="grabarSeccion" method="post" >
	<input type="hidden" name="Accion">
	<input type="hidden" name="PortafolioId" value="<%=portafolioId%>">
	<input type="hidden" name="SeccionId" value="<%=seccionId%>">
	
	
	
	<div class="row container-fluid">
		<div class="span2 col">
	
	<label>Titulo</label>	
	<input name="titulo" type="text" class="form-control" style="width:200px" value="<%=Seccion.getTitulo()==null?"":Seccion.getTitulo()%>">
	<br><br>
	
	 
	<label>Nombre de la Seccion</label>	
	<input name="nombre" type="text"  class="form-control" style="width:200px" value="<%=Seccion.getSeccionNombre()==null?"":Seccion.getSeccionNombre()%>" class="input input-xlarge"/>
	<br><br>
	<label>Instrucciones</label>	
	<textarea name="instrucciones" class="form-control" style="width:200px"></textarea>
	<br><br> 
	 </div>
		
		
				<div class="span2 col">
		
	<label>Tipo para guardar</label>	
	<select name="tipo" class="form-control"  style="width:200px">
		<option value="-" <%if(Seccion.getTipo().equals("-")) out.print("selected"); %>>Informativo</option>
		<option value="C" <%if(Seccion.getTipo().equals("C")) out.print("selected"); %>>Texto Corto</option>
		<option value="L" <%if(Seccion.getTipo().equals("L")) out.print("selected"); %>>Texto Largo</option>
		<option value="I" <%if(Seccion.getTipo().equals("I")) out.print("selected"); %>>Imagen</option>
		<option value="A" <%if(Seccion.getTipo().equals("A")) out.print("selected"); %>>Archivo</option>
		<option value="E" <%if(Seccion.getTipo().equals("E")) out.print("selected"); %>>Enlace Externo</option>
	</select>	  
	<br><br>
	
	<label>Orden:</label>
	<input name="Orden" class="form-control" style="width:200px" type="text" value="<%=Seccion.getOrden()%>">
	<br><br>
	<label>Estado:</label>
	<select name="Estado" class="form-control" style="width:200px">
		<option value="A" <%if(Seccion.getEstado().equals("A")) out.print("selected"); %>>Activo</option>
		<option value="I" <%if(Seccion.getEstado().equals("I")) out.print("selected"); %>>Inactivo</option>
	</select>
	<br><br>
		 </div>
	
	</div>
	</div>
	<div class="alert alert-info">
<%	if (!existe){%>	
	<a href="javascript: Guardar()" class="btn btn-primary">Guardar</a>
<%	} %>	
	</div>
	</form>	
</div>
<script>
</script>
<%@ include file="../../cierra_enoc.jsp" %>