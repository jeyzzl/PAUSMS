<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<% 
	aca.pron.spring.PronMateria pronMateria = (aca.pron.spring.PronMateria)request.getAttribute("pronMateria");

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
	
	//System.out.println("Completo:"+completoMateria+":"+completoUnidades+":"+completoEjes+":"+completoValores+":"+completoEsquema+":"+completoBiblio);
%>
<body>
	<div class="container-fluid">
		<h3>Course plan<small class="text-muted">( <%=maestroNombre%> - <%=materia%> )</small></h3>
		<div class="alert alert-success">
			<a class="btn btn-primary" href="cursos"><spring:message code="aca.Regresar"/></a>
<%		if(completoMateria.equals("Si") && completoUnidades.equals("Si") && /*completoEjes.equals("Si") && completoValores.equals("Si") &&*/ completoEsquema.equals("Si") && completoBiblio.equals("Si")){%>
			<a class="btn btn-success" href="enviarCursoCargaId?CursoCargaId=<%=cursoCargaId%>&Origen=pronMateria">Send</a>
<%			if(envio){%>
				<a class="btn btn-primary" href="pdfPron?CursoCargaId=<%=cursoCargaId%>" target="_blank">Print</a>
<%			}%>
<%      }%>
		</div>		
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<div class="collapse navbar-collapse" id="navbarNavAltMarkup">
				<div class="navbar-nav">
					<ul class="nav nav-tabs" role="tablist">
					    <li class="nav-item nav-link"><a href="#home" aria-controls="home" role="tab" data-bs-toggle="tab">Subject <%if(completoMateria.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li>
					    <li class="nav-item nav-link"><a href="pronUnidad?CursoCargaId=<%=cursoCargaId%>">Units <%if(completoUnidades.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li> 
					    <li class="nav-item nav-link"><a href="pronEjes?CursoCargaId=<%=cursoCargaId%>">Axes <%if(completoEjes.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li> 
					    <li class="nav-item nav-link"><a href="pronValor?CursoCargaId=<%=cursoCargaId%>">Principles <%if(completoValores.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li>
					    <li class="nav-item nav-link"><a href="pronEsquema?CursoCargaId=<%=cursoCargaId%>">Schema <%if(completoEsquema.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li> 
					    <li class="nav-item nav-link"><a href="pronBiblio?CursoCargaId=<%=cursoCargaId%>">Bibliography <%if(completoBiblio.equals("Si")){%><i class="fas fa-check"></i><%}%></a></li> 
					</ul>
				</div>
			</div>
		</nav>
		<div class="alert alert-secondary">
			<form action="grabaPronMateria" method="POST">
				<input type="hidden" name="CursoCargaId" value="<%=cursoCargaId%>">
				<div class="row">
					<div class="col-md-3">
						<strong>Class schedule:<br></strong><input type="text" class="form-control" name="HorarioClase" value="<%=pronMateria.getHoraClase()%>"><br>		
					</div>
					<div class="col-md-3">
						<strong>Location:<br></strong><input type="text" class="form-control" name="Lugar" value="<%=pronMateria.getLugar()%>"><br>		
					</div>
				</div>
				<div class="row">	
					<div class="col-md-3">
						<strong>Tutoring hours:<br></strong><input type="text" class="form-control" name="HorarioTutoria" value="<%=pronMateria.getHoraTutoria()%>"><br>		
					</div>
					<div class="col-md-3">
						<strong>E-mail:<br></strong><input type="text" class="form-control" name="Correo" value="<%=pronMateria.getCorreo()%>">
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
					<td colspan="4" style="text-align:center;">
						<strong>Academic background:</strong>(<font color="#AE2113" id="FormacionSize">200</font>)<br>
						<textarea class="input input-xxlarge" id="Formacion" rows="" cols="" class="form-control" name="Formacion" maxlength="200"  onKeyUp="refrescarLongitudFormacion();" onkeyDown="return revisarLongitudFormacion();"><%=pronMateria.getFormacion()%></textarea><br><br>
					</td>
					</div>
					<div class="col-md-12">
						<strong>Course description:<br></strong><textarea rows="" cols="" class="form-control" name="Descripcion"><%=pronMateria.getDescripcion()%></textarea><br>
					</div>
					<div class="col-md-12">
						<strong>Course perspective and approach:<br></strong><textarea rows="" cols="" class="form-control" name="Enfoque"><%=pronMateria.getEnfoque()%></textarea><br>
					</div>
					<div class="col-md-12">
						<strong>Special needs of the course:</strong>(<font color="#AE2113" id="EspecialSize">1990</font>)<br>
						<textarea class="input input-xxlarge" id="Especial" rows="" cols="" class="form-control" name="Especial" maxlength="1990"  onKeyUp="refrescarLongitudNecesidades();" onkeyDown="return revisarLongitudNecesidades();"><%=pronMateria.getEspecial()%></textarea><br><br>
					</div>
					<div class="col-md-12">
						<strong>Academic </strong>(<font color="#AE2113" id="IntegridadSize">1990</font>)<br>
						<textarea class="input input-xxlarge" id="Integridad" rows="" cols="" class="form-control" name="Integridad" maxlength="1990"  onKeyUp="refrescarLongitudIntegridad();" onkeyDown="return revisarLongitudIntegridad();"><%=pronMateria.getIntegridad()%></textarea><br><br>
					</div>
				</div>
				<input type="submit" class="btn btn-primary" value="Save">
			</form>
		</div>
	</div>
</body>
<script>
	function revisarLongitudFormacion(){
		var com = document.getElementById('Formacion');
		if(event.keyCode==8) return true;
		if(com.value.length==200) return false;
		else return true;
	}
	
	function refrescarLongitudFormacion(){
		var com = document.getElementById('Formacion');
		var cant = document.getElementById('FormacionSize');
		cant.innerHTML = 200-com.value.length;
		if(com.value.length>200){
			com.value = com.value.substr(0, 200);
		}
	}
	function revisarLongitudNecesidades(){
		var com = document.getElementById('Especial');
		if(event.keyCode==8) return true;
		if(com.value.length==1990) return false;
		else return true;
	}
	
	function refrescarLongitudNecesidades(){
		var com = document.getElementById('Especial');
		var cant = document.getElementById('EspecialSize');
		cant.innerHTML = 1990-com.value.length;
		if(com.value.length>1990){
			com.value = com.value.substr(0, 1990);
		}
	}
	function revisarLongitudIntegridad(){
		var com = document.getElementById('Integridad');
		if(event.keyCode==8) return true;
		if(com.value.length==1990) return false;
		else return true;
	}
	
	function refrescarLongitudIntegridad(){
		var com = document.getElementById('Integridad');
		var cant = document.getElementById('IntegridadSize');
		cant.innerHTML = 1990-com.value.length;
		if(com.value.length>1990){
			com.value = com.value.substr(0, 1990);
		}
	}
	
</script>
