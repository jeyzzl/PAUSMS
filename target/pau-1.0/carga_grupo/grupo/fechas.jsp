<%@ page import= "java.util.List"%>
<%@ page import= "aca.carga.spring.CargaGrupo"%>
<%@ page import= "aca.catalogo.spring.CatModalidad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<head>
<script type="text/javascript">
	
	function Grabar(){
		var fInicio =  document.frmFechas.FInicio.value;
		var fFinal 	= document.frmFechas.FFinal.value;
		if(fInicio!="" && fFinal!=""){
			var arrIni = fInicio.split('/');
			var fIni = new Date(arrIni[1]+"/"+arrIni[0]+"/"+arrIni[2]);
			
			var arrFin = fFinal.split('/');
			var fFin = new Date(arrFin[1]+"/"+arrFin[0]+"/"+arrFin[2]);
			
			if(fIni=='Invalid Date' || fFin=='Invalid Date'){
				alert("<spring:message code="aca.JSVerificarFechas"/>");
			}
			else{
				if(fIni<fFin || fInicio==fFinal){
					document.frmFechas.submit();
				}
				else{
					alert("<spring:message code="cargasGrupo.grupo.JSFecha"/>");
				}				
			}
		}else{
			alert("<spring:message code='aca.JSCompletar'/>");
		}
	}	
	
</script>
</head>
<body>
<%
	String cursoCargaId 	= (String) request.getAttribute("cursoCargaId");
	String planId 			= (String) request.getAttribute("planId");
	String cursoId			= (String) request.getAttribute("cursoId");
	String practica 		= (String) request.getAttribute("practica");
	String materia			= (String) request.getAttribute("materia");
	String nombreCarga		= (String) request.getAttribute("nombreCarga");
	String nombreModalidad	= (String) request.getAttribute("nombreModalidad");
	int duracion 			= (int) request.getAttribute("duracion");
	int numAlumnos 			= (int) request.getAttribute("numAlumnos");
	boolean esAdmin 		= (boolean) request.getAttribute("esAdmin");
	
	String mensaje 			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	int i = 0;
	CargaGrupo cargaGrupo	= (CargaGrupo) request.getAttribute("cargaGrupo");
	
	List<CatModalidad> lisModalidades = (List<CatModalidad>)request.getAttribute("lisModalidades");
%>
<div class="container-fluid">
	<h2><%=materia%>
		<small class="text-muted fs-5">( <%=planId%> - <%=nombreCarga%> - <spring:message code="cargasGrupo.grupo.Acta"/>: <b><%=cargaGrupo.getCursoCargaId()%></b> -&nbsp;
			<spring:message code="aca.Modalidad"/>: <b><%=nombreModalidad%></b>&nbsp;-&nbsp;<spring:message code="aca.Bloque"/>: <b><%=cargaGrupo.getBloqueId()%></b> )
    	</small>
    </h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="grupo?CarreraId=<%=cargaGrupo.getCarreraId()%>&PlanId=<%=planId%>"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;   		
	</div>
<%	if (!mensaje.equals("-")){%>	
	<div class="alert alert-info">
		<%=mensaje%>
	</div>
<%	} %>
	<form name="frmFechas" action="guardarFechas" method="post">
	<input type="hidden" name="CursoCargaId" value="<%=cursoCargaId%>">
	<input type="hidden" name="CarreraId" value="<%=cargaGrupo.getCarreraId()%>">
	<input type="hidden" name="PlanId" value="<%=planId%>">
	<div class="row">
    	<div class="span5">
<%		if(numAlumnos == 0){%>
			<h4>Modality:</h4>
			<select name="ModalidadId">
<% 			for (CatModalidad mod : lisModalidades){%>			
				<option value="<%=mod.getModalidadId()%>" <%=cargaGrupo.getModalidadId().equals(mod.getModalidadId())?"selected":""%>><%=mod.getNombreModalidad()%></option>
<%			} %>				
			</select>
			<br><br>
<%		}else{ %>
			<h4>Students enrolled in the subject: <span class="badge bg-success"><%=numAlumnos%></span></h4>
			<input type="hidden" name="ModalidadId" value="<%=cargaGrupo.getModalidadId()%>">
			<br>
<% 		}%>    	

<%		if(numAlumnos == 0){%>
			<h4>Attendance/Classroom:</h4>
			<select name="ModoId">
		<%	//if (practica.equals("N")){ %>				
<%-- 				<option value="V" <%if(cargaGrupo.getModo().equals("V"))out.print("selected");%>>Virtual</option> --%>
<%-- 				<option value="H" <%if(cargaGrupo.getModo().equals("H"))out.print("selected");%>>Híbrida</option> --%>
		<%	//} %>		
<%-- 				<option value="R" <%if(cargaGrupo.getModo().equals("R"))out.print("selected");%>>Mixta</option> --%>				
				<option value="P" <%if(cargaGrupo.getModo().equals("P"))out.print("selected");%>>In person</option>
				<option value="V" <%if(cargaGrupo.getModo().equals("V"))out.print("selected");%>>Virtual</option>
			</select>
			<br><br>
<%		}else{ %>			
			<input type="hidden" name="ModoId" value="<%=cargaGrupo.getModo()%>">
<% 		}%>    	
       		<h4><spring:message code="cargasGrupo.grupo.Grupo"/>:</h4>
       		<input name="Grupo" type="text" id="Grupo" value="<%=cargaGrupo.getGrupo() == null ? "" : cargaGrupo.getGrupo()%>" size="5" maxlength="2">
       		<br><br>
       		<h4><spring:message code="aca.Inicio"/>:</h4>
       		<input name="FInicio" type="text" id="FInicio" value="<%=cargaGrupo.getfInicio()%>" size="12" maxlength="10"> <spring:message code="aca.DDMMYYYY"/>
       		<br><br>
       		<h4>End:</h4>
       		<input name="FFinal" type="text" id="FFinal" value="<%=cargaGrupo.getfFinal()%>" size="12" maxlength="10"> <spring:message code="aca.DDMMYYYY"/> &nbsp; &nbsp;
       		<spring:message code="cargasGrupo.grupo.Dias"/>: <%=duracion%>
       		<br><br>
       		<h4>Price:</h4>       		
<%		if(!(cargaGrupo.getPrecio().equals("N"))){%>
			<input name="Precio" type="text" id="Precio" value="<%=cargaGrupo.getPrecio()%>" size="8" maxlength="8" placeholder="$" readonly>
<%		}else {%>
			<input name="Precio" type="text" id="Precio" value="<%=cargaGrupo.getPrecio()%>" size="8" maxlength="8" placeholder="$" readonly>
<%		}%>       	
			<br><br>	
			<h4><spring:message code="aca.Comentario"/>:</h4>
			<input name="Comentario" type="text" id="Comentario" value="<%=(cargaGrupo.getComentario() != null && !cargaGrupo.getComentario().equals(" ") && !cargaGrupo.getComentario().equals("")) ? cargaGrupo.getComentario() : ""%>" size="55" maxlength="40">
			<br><br>
<% 	if (esAdmin){%>	
			Validate Schedule: 
			&nbsp;
			<input type="radio" name="Restriccion" id="Restriccion" value="S" <%if(cargaGrupo.getRestriccion().equals("S")){out.print("checked");} %>>YES 
			&nbsp;
			<input type="radio" name="Restriccion" id="Restriccion" value="N" <%if(cargaGrupo.getRestriccion().equals("N")){out.print("checked");} %>>NO		
<%	}else{%>
			<input type="hidden" name="Restriccion" value="<%=cargaGrupo.getRestriccion()%>"/>
<%	}%>			
			<br><br>
		</div>
	</div>		
	<div class="alert alert-info">
		<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Guardar" /></a>
	</div>
	</form>
</div>
</body>
