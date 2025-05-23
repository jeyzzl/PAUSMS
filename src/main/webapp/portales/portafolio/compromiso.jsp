<%@page import="aca.portafolio.spring.PorCompromiso"%>
<%@page import="java.util.ArrayList"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<% 	String porMenu= "2";%>
<%@ include file="menuPortal.jsp"%>

<script type = "text/javascript">
	function Grabar(){
		if(document.forma.PeriodoId.value!=""){			
			document.forma.submit();
		}else{
			alert(" Complete todos los campos");
		}
	}

	function Enviar(codigoPersonal, periodoId){
		if(confirm("Estas seguro de enviar el registro!")==true){
  			document.location.href="enviarEstado?CodigoPersonal="+codigoPersonal+"&PeriodoId="+periodoId+"&Estado=E";
		}			
	}	
</script>
<%
	String codigoPersonal	= (String) request.getAttribute("codigoPersonal");
	String periodoId		= (String) request.getAttribute("periodoId");
	String mensaje			= (String) request.getAttribute("mensaje");
	String nombre			= (String) request.getAttribute("nombre");
    boolean existe 			= (boolean)request.getAttribute("existe");

	PorCompromiso porCompromiso	= (PorCompromiso) request.getAttribute("porCompromiso");
%>

<div class="container-fluid">
	<h3>
		Compromiso del empleado <small class="text-muted fs-6"> (<%=nombre%>)</small>
	</h3>
	<hr/>
<% if(mensaje.equals("1")){%>
	<div class="alert alert-success" role="alert">
	  Grabado
	</div>
<% }else if(mensaje.equals("2")){%>
	<div class="alert alert-danger" role="alert">
	  No se grabo
	</div>
<% }%>	
	<form action="grabarCompromiso" method="post" name="forma">
		<input type="hidden" name="PeriodoId" value="<%=periodoId%>">
		<label for="Yo" class="form-label"><h5>Yo <%=nombre%> que trabajo en:</h5></label>
		<input type="text" class="form-control" name="departamento" value="<%=porCompromiso.getDepartamento()%>" required="required" size="100"></h5>
		<br>
		<div class="col-12">
			<h5><strong>1. Educo integralmente</strong></h5>
			<textarea  name="educar" id="educar" class="form-control" required="required" required="required"  rows="3"><%=porCompromiso.getEducar() %></textarea>
		</div>
		<br>
<!-- 		<div style="Box-sizing: Border-box; width: 100%; float: left; background: white;" class="alert alert-info"> -->
		<div class="col-12">
			<h5>2. con un modelo educativo <strong>sustentable</strong></h5>
			<textarea  name="modelar" id="modelar" class="form-control" required="required" rows="3"><%=porCompromiso.getModelar() %></textarea>
		</div>
		<br>
		<div class="col-12">
			<h5>3. en escenarios de <strong>investigación</strong></h5>
			<textarea   name="investigar" id="investigar" class="form-control" rows="3" required="required" style="width: 100%;"><%=porCompromiso.getInvestigar() %></textarea>
		</div>
		<br>
		<div class="col-12">
			<h5>4. y <strong>servicio abnegado,</strong></h5>
			<textarea  name="servir" id="servir" required="required" class="form-control" rows="3"><%=porCompromiso.getServir() %></textarea>
		</div>
		<br>
		<div class="col-12">
			<h5>5. que se unen a la <strong>proclamación bíblica global</strong></h5>
			<textarea name="proclamar" id="proclamar" class="form-control" required="required" rows="3"><%=porCompromiso.getProclamar() %></textarea>
		</div>
		<br>
		<div class="col-12">
			<h5>6. de la <strong>esperanza adventista</strong> de un mundo nuevo.</h5>
			<textarea name="esperanza" id="esperanza" class="form-control" required="required"  rows="3"><%=porCompromiso.getEsperanza() %></textarea>
		</div>
<!-- 		<div style="clear: both;"></div> -->
		<br>
		<div class="alert alert-info">
			<button class="btn btn-primary" type="submit"><i class="icon-white icon-ok"></i> Guardar</button>
<%		if(porCompromiso.getEstado().equals("A")){%>			
			<a class="btn btn-success" href="javascript:Enviar('<%=porCompromiso.getCodigoPersonal()%>&PeriodoId=<%=porCompromiso.getPeriodoId()%>')">Enviar</a>
<%		}	%>	
<% 		if(porCompromiso.getEstado().equals("E")&& existe){%>
			<a class="btn btn-warning" href="compromisoPdf?CodigoPersonal=<%=porCompromiso.getCodigoPersonal()%>&PeriodoId=<%=porCompromiso.getPeriodoId()%>"> PDF</a>
<%		} %>
		</div>
	</form>
</div>
	<link rel="stylesheet" href="../../js/maxlength/jquery.maxlength.css" />
	<script src="../../js/maxlength/jquery.maxlength.min.js"></script>
	<script>
		jQuery('#educar').maxlength({ 
		    max: 500
		});
		jQuery('#modelar').maxlength({ 
		    max: 500
		});
		jQuery('#investigar').maxlength({ 
		    max: 500
		});
		jQuery('#servir').maxlength({ 
		    max: 500
		});
		jQuery('#proclamar').maxlength({ 
		    max: 500
		});
		jQuery('#esperanza').maxlength({ 
		    max: 500
		});
	
		function cambioPeriodo(){	
			document.formPeriodo.submit();
		}
	</script>

	<script type="text/javascript">
		jQuery('.compromiso').addClass('active');
	</script>
