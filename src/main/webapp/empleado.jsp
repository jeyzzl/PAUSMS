<%@page import="aca.emp.spring.EmpContacto"%>
<%@ page import= "aca.menu.spring.*"%>
<%@ page import= "java.util.List"%>
<%@ page import= "java.util.ArrayList"%>

<% String idJsp= "000";%>
<%@include file= "seguro2.jsf" %>
<%@include file= "idioma.jsp" %>
<html>
<%@ include file= "head.jsp"%>
<style>
	.Llama{
		position: fixed; 
		bottom: 0px;
		right: 0px;
   		width: 200px;
   		height: 300px;
		max-width: 35%;
		margin: 0 auto;			
	}
</style>	
<script>	
	function GrabarContacto(){
		document.frmContacto.submit();
	}
	function EditarContacto(){				
		document.location.href="empleado?Editar=S";
	}
</script>
<%-- <script src="<%=request.getContextPath()%>/js/jquery-3.3.1.min.js"></script> --%>
<%-- <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> --%>
<%	
	String codigoLogin		= (String) session.getAttribute("codigoPersonal");			
	String editar			= request.getParameter("Editar")==null?"N":request.getParameter("Editar");
	String estado			= (String) request.getAttribute("estado");
	EmpContacto empContacto	= (EmpContacto)request.getAttribute("empContacto");
	boolean existeCovid		= (boolean) request.getAttribute("existeCovid");
	boolean existeRango		= (boolean) request.getAttribute("existeRango");
	String nombreMaestro	= (String) request.getAttribute("nombreMaestro");
	int salidasPendientes	= (int) request.getAttribute("salidasPendientes");
	int totDocumentos		= (int) request.getAttribute("totDocumentos");	
	String yearActual		= aca.util.Fecha.getHoyReversa().split("/")[0];
	
	String sColor 			= "bgcolor = '#dddddd'";
	String sColorLogo		= "bgcolor = '#FFCC99'";	
	String sesionId			= session.getId();	
	
	if (estado.equals("A")) estado= "Active";
	if (estado.equals("J")) estado= "Retired";
	if (estado.equals("I")) estado= "Inactive";
%>
<body>
	<div class="container-fluid mt-1">
	<br><br>
  	<form name="frmContacto" action="grabarContacto" method="post">
	<div class="row" style="padding: 0% 15% 0% 15%;">
		<div class="col" style="border-radius: 50px; box-shadow: 5px 5px 10px 5px #eeeeee;">
			<div class="row" style="padding: 80px 0px 80px 0px;">
				<div class="col" align="center">
	           		<img src="empFoto?Codigo=<%=codigoLogin%>" width="280px;" style="border-radius: 35px;">
	           	</div>
				<div class="col">
	          		<h3><%=nombreMaestro%></h3>
	          		<h4><%=estado%></h4>
					<h5><i class="fas fa-user"></i>&nbsp;<%=codigoLogin%></i></h5> 
<%					if (editar.equals("N")){%>
	          		&nbsp;<h5 class="card-subtitle mb-2 "><i class="fas fa-phone-alt"></i>&nbsp;<%=empContacto.getTelefono()%></h5>
	          		&nbsp;<h5 class="card-subtitle mb-2 "><i class="fas fa-envelope"></i>&nbsp;<%=empContacto.getCorreo()%></h5>
	          		<br>           
	          		<button type="button" onclick="javascript:EditarContacto();" class="btn btn-primary rounded-pill" style="height: 40px; width:130px;" >Edit</p></button>
<%					}else{ %>
	          		<label for="Telefono">Phone</label>    				
	   				<input type="Text" class="form-control" name="Telefono" aria-describedby="Phone" placeholder="Phone" value="<%=empContacto.getTelefono()%>" maxsize="50">
	   				<label for="Correo">Email</label>
	   				<input type="Correo" class="form-control" name="Correo" aria-describedby="Email" placeholder="Email" value="<%=empContacto.getCorreo()%>" maxsize="100">
	   				<br>           
	          		<button type="button" onclick="javascript:GrabarContacto();" class="btn btn-primary rounded-pill" style="height: 40px; width:130px;" > <spring:message code="aca.Grabar"/> </p></button>		   					
<%					} %>
	           </div>
    		</div>
   		</div>
   	</div>
	</form> 
	<div class="row" style="padding: 0% 20% 0% 20%;">
		<div class="col">
			<div class="row d-flex align-items-center" style="padding-top: 20px;">
				<div class="col-6" align="center">
		  		<!--	<div class="alert alert-secondary" role="alert" style="border-radius: 25px;">
						<div class="d-flex align-items-center">
					  		<div style="height:150px; width:60%;" align="center">
								<a href="validaEmpleadoMovil" title="Usuario movil">
									<img alt="Movil" src="imagenes/icono-movil.jpg" style="height: 150px; width: 150px; border-radius: 15px;"><br>
								</a>
							</div>
					  		 <div style="height:150px; width:60%;" align="left">
								<h5><spring:message code="aca.RegistraMovil"/></h5>
								<p><spring:message code="aca.CuentaMovil"/></p>
								<a href="validaEmpleadoMovil" class="btn btn-primary rounded-pill" ><spring:message code="aca.Entrar"/></a>
							</div> 
			           	</div>
		           	</div> -->
	           	</div>
<%	if (existeRango){ %>
				<div class="col-6">
	  				<div class="alert alert-secondary" role="alert" style="border-radius: 25px;">
						<div class="d-flex align-items-center">
					  		<div align="center" style="height:150px; width:40%;">
	      						<h5>
					  			<span style="font-size:30px;">
	      							<a href="portales/maestro/rangoPdf"><i class="far fa-file-alt fa-5x"></i></a>
	      						</span>
	      						</h5>
			           		</div>
				  			<div style="height:150px; width:60%;">
				    	   		<h5><b>Rango acad�mico <%=yearActual%></b></h5>
					       		<p>Haz clic en este bot�n para descargar tu constancia.</p>
					       		<a href="portales/maestro/rangoPdf" class="btn btn-primary rounded-pill" >Entrar</a>
							</div>
			           </div>
		           </div>
	           </div>
<% 	}%>	           	
<!-- 				<div class="col"> -->
<!-- 	  				<div class="alert alert-secondary" role="alert" style="border-radius: 25px;"> -->
<!-- 						<div class="d-flex align-items-center"> -->
<!-- 					  		<div align="center" style="height:150px; width:40%;"> -->
<!-- 	      						<h5> -->
<!-- 					  			<span style="font-size:30px;"> -->
<!-- 	      							<a href="portales/portafolio/compromiso"><i class="far fa-file-alt fa-5x"></i></a> -->
<!-- 	      						</span> -->
<!-- 	      						</h5> -->
<!-- 			           		</div> -->
<!-- 				  			<div style="height:150px; width:60%;"> -->
<!-- 				    	   		<h5><b>IDP 2021 </b><small class="text-muted fs-6">(Sumar+compromiso)</small></h5> -->
<!-- 					       		<p>Ingresa aqui para llenar tu compromiso.</p> -->
<!-- 					       		<a href="portales/portafolio/compromiso" class="btn btn-primary rounded-pill" >Entrar</a> -->
<!-- 							</div> -->
<!-- 			           </div> -->
<!-- 		           </div> -->
<!-- 	           </div> -->
    		</div>
   		</div>
   	</div>
  	<div class="row row-cols-md-2">	
  		<div class="col mb-1">			
<%	if (salidasPendientes >= 1){%>	
<!-- 			<div class="card card-body border-dark mb-1"> -->
<%-- 				<h5>Tienes <span class='badge badge-warning'><%=salidasPendientes%></span> salida(s) pendientes de revisar (<small class="text-muted">Ingresa a VRE|Salidas|03. Permiso</small>)</h5>    					 --%>
<!-- 			</div> 	 -->
<%	}%>			
<%-- 			<div class="card card-body mb-1 <%=existeCovid?"border-success":"border-danger"%>"> --%>
<!-- 		        <h5>COVID&nbsp;&nbsp;&nbsp;  -->
<!-- 		        	<a href="encuestaCovid" target="_blanck">Formulario <i class="fas fa-file-alt"></i></a>&nbsp;&nbsp;&nbsp; -->
<!-- 					<a href="resEncuesta" target="_blanck">Resultado <i class="fas fa-file-alt"></i></a> -->
<!-- 				</h5>    					 -->
<!-- 			</div>	 -->
<%		if(codigoLogin.equals("9801218") || codigoLogin.equals("9800308")){%>
<!-- 			<div class="card card-body mb-1"> -->
<!-- 		        <h5>Varianza&nbsp;&nbsp;&nbsp;  -->
<!-- 		        	<a href="varianza" class="btn btn-small btn-primary">Link</a> -->
<!-- 				</h5>    					 -->
<!-- 			</div> -->
<% 		}	%> 				
		</div>		
	</div>			
</div>
<div class="contenedor-div">
	<%-- <img src="imagenes/um3.png" class="Llama" width="400px"> --%>
</div>
</body>
<script>
	function Mensaje(){		
		jquery.getJSON("http://172.16.169.146:7778/SendSMS?username=admin&password=Jete17&phone=8261069665&message=Hola mensaje",function(data){
			console.log(data);
	      	$('body').append(data);
	    });
	}		
	</script>