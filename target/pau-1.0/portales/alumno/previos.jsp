<%@ page import="java.util.HashMap"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.alumno.spring.AlumPlan"%>
<%@page import= "aca.catalogo.spring.CatFacultad"%>
<%@page import= "aca.catalogo.spring.CatCarrera"%>
<%@page import= "aca.nse.spring.NseEncuesta"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%@ include file="../alumno/menu.jsp"%>
<head></head>
<%	
	String codigoPersonal 			= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno 			= (String) session.getAttribute("codigoAlumno");
	AlumPersonal alumPersonal 		= (AlumPersonal) request.getAttribute("alumPersonal");
	
// 	String esEnLinea				= (String) request.getAttribute("esEnLinea");

	String esEnLinea				= "I";
	boolean seguros 				= false;	
	int encuestaNse					= (int)request.getAttribute("encuestaNse");
	boolean tieneNse				= (boolean)request.getAttribute("tieneNse");
	boolean tieneVacuna				= (boolean)request.getAttribute("tieneVacuna");	 
	NseEncuesta encuestaActiva		= (NseEncuesta)request.getAttribute("encuestaActiva");
%>
<body>
<div class="container-fluid">
	<div class="mt-1">
		<div class="alert alert-success">
			<a href="pasos"><i class="far fa-arrow-alt-circle-left fa-lg"></i></a>&nbsp;
			<span class="badge rounded-pill bg-dark">1</span>
			<spring:message code="portal.alumno.previos.AsuntosPrevios"/><small class="text-muted fs-6"> ( <%=alumPersonal.getCodigoPersonal()%> - <%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoMaterno()%> <%=alumPersonal.getApellidoPaterno()%> )</small>
		</div>
		<p class="ms-3">You must review and complete all stations in order to move to the next step. Staff will record and update your details to guide you thorugh each station, please ensure that the details you see displayed are correct. 
		</p>
		<hr>
	</div>
	<div class="container-fluid d-flex justify-content-around mt-1">
		<div class="row row-cols-1 row-cols-md-2 g-0">
			<div class="card bg-light mb-3 me-2" style="max-width: 20rem; min-height: 18rem;">
				<div class="card-header"><span class="badge rounded-pill bg-dark">A</span>&nbsp;Personal <spring:message code="portal.alumno.previos.Datos"/></div>
			  	<div class="card-body ">
			    	<p style="font-size: 14px">
						<em><u>Station 2</u></em>
					</p>
			    	<p class="card-text"><spring:message code="portal.alumno.previos.TarjetaUno"/></p>
			  	</div>
			  	<div class="card-footer">
			    	<a class="btn btn-success" href="validacion"><spring:message code="portal.alumno.previos.Entrar"/></a>
			  	</div>
			</div>
			<div class="card bg-light mb-3 me-2" style="max-width: 20rem; min-height: 18rem;">
				<div class="card-header"><span class="badge badge rounded-pill bg-dark">B</span>&nbsp;Academic Information</div>
			  	<div class="card-body">
			    	<p style="font-size: 14px">
						<em><u>Station 3</u></em>
					</p>
			    	<p class="card-text"><spring:message code="portal.alumno.previos.TarjetaDos"/></p>
			  	</div>
			  	<div class="card-footer">
			    	<a class="btn btn-success" href="planes"><spring:message code="portal.alumno.previos.Entrar"/></a>
			  	</div>
			</div>
			<div class="card bg-light mb-3 me-2" style="max-width: 20rem; min-height: 18rem;">
			  	<div class="card-header"><span class="badge rounded-pill bg-dark">C</span>&nbsp;ID Printing</div>
			  	<div class="card-body ">
			    	<p style="font-size: 14px">
						<em><u>Station 4</u></em>
					</p>
			    	<p class="card-text"><spring:message code="portal.alumno.previos.TarjetaSiete"/></p>
			  	</div>
			  	<div class="card-footer">
			    	<a class="btn btn-success" href="credencial"><spring:message code="portal.alumno.previos.Entrar"/></a>
			  	</div>
			</div>
			<div class="card bg-light mb-3 me-2" style="max-width: 20rem; min-height: 18rem;">
			  	<div class="card-header"><span class="badge rounded-pill bg-dark">D</span>&nbsp;<spring:message code="portal.alumno.previos.Externado"/></div>
			  	<div class="card-body ">
			    	<p style="font-size: 14px">
						<em><u>Station 5</u></em>
					</p>
			    	<p class="card-text"><spring:message code="portal.alumno.previos.TarjetaSeis"/></p>
			  	</div>
			  	<div class="card-footer">
			    	<a class="btn btn-success" href="externado"><spring:message code="portal.alumno.previos.Entrar"/></a>
			  	</div>
			</div>
			<div class="card bg-light mb-3 me-2" style="max-width: 20rem; min-height: 18rem;">
			  	<div class="card-header"><span class="badge rounded-pill bg-dark">E</span>&nbsp;CTP (Capacity Training Program)</div>
			  	<div class="card-body ">
			    	<p style="font-size: 14px">
						<em><u>Station 6</u></em>
					</p>
			    	<p class="card-text"><spring:message code="portal.alumno.previos.TarjetaPatrocinador"/></p>
			  	</div>
			  	<div class="card-footer">
			    	<a class="btn btn-success" href="patrocinadores"><spring:message code="portal.alumno.previos.Entrar"/></a>
			  	</div>
			</div>
			<%-- <div class= "card bg-light mb-3 me-2" style="max-width: 20rem; min-height: 18rem;">
				<div class="card-header"><span class="badge rounded-pill bg-dark">F</span>&nbsp;<spring:message code="portal.alumno.previos.Candados"/></div>
			  	<div class="card-body">
			    	<p style="font-size: 14px">
						<em><u>Station 6</u></em>
					</p>
			    	<p class="card-text"><spring:message code="portal.alumno.previos.TarjetaTres"/></p>
				</div>
				<div class="card-footer">
			    	<a class="btn btn-success" href="candados"><spring:message code="portal.alumno.previos.Entrar"/></a>
			  	</div>
			</div>	 --%>
	<%-- <% if(seguros){%>
			<div class="card bg-light mb-3 me-2" style="max-width: 20rem;">
			  	<div class="card-header"><span class=" badge rounded-pill bg-dark">D</span>&nbsp;<spring:message code="portal.alumno.previos.Seguros"/></div>
			  	<div class="card-body">
			    	<p style="font-size: 14px">
						<em><u>Station </u></em>
					</p>
			    	<p class="card-text"><spring:message code="portal.alumno.previos.TarjetaCuatro"/></p>
			  	</div>
			  	<div class="card-footer">
			    	<a class="btn btn-success" href="seguro"><spring:message code="portal.alumno.previos.Entrar"/></a>
			  	</div>
			</div>
	<% }%> --%>
<!-- 			<div class="card bg-light mb-3" style="max-width: 20rem;"> -->
<%-- 			  	<div class="card-header"><span class="badge rounded-pill bg-dark">E</span>&nbsp;<spring:message code="portal.alumno.previos.Becas"/></div> --%>
<!-- 			  	<div class="card-body "> -->
<%-- 			    	<h5 class="card-title"><spring:message code="portal.alumno.previos.Informacion"/></h5> --%>
<%-- 			    	<p class="card-text"><spring:message code="portal.alumno.previos.TarjetaCinco"/></p> --%>
<!-- 			  	</div> -->
<!-- 			  	<div class="card-footer">			    	 -->
<!-- 			    	<a class="btn btn-outline-dark btn-sm" href="experiencias"><i class="fas fa-info-circle"></i>Scholarships</a> -->
<!-- 			    	<a class="btn btn-outline-dark btn-sm" href="plazas"><i class="fas fa-info-circle"></i>Places</a> -->
<%-- 			        <a class="btn btn-success" href="solicitudBeca"><spring:message code="portal.alumno.previos.Entrar"/></a>			    	 --%>
<!-- 			  	</div> -->
<!-- 			</div> -->
<%-- <%   		if (esEnLinea.equals("N")){%>					 --%>
			<%-- <div class="card bg-light mb-3 me-2" style="max-width: 20rem;">
			  	<div class="card-header"><span class="badge rounded-pill bg-dark">G</span>&nbsp;<spring:message code="portal.alumno.previos.RetornoSeguro"/></div>
			  	<div class="card-body ">
			    	<p style="font-size: 14px">
						<em><u>Station </u></em>
					</p>
			    	<p class="card-text"><spring:message code="portal.alumno.previos.TarjetaSiete"/></p>
			  	</div>
			  	<div class="card-footer"> --%>
<%-- <%					if(codigoPersonal.equals(codigoAlumno)){%>			  	 --%>
			    	<%-- <a class="btn btn-success" href="retorno"><spring:message code="portal.alumno.previos.Entrar"/></a> --%>
<%-- <%					} %>			    	 --%>
<%-- <% 			    	if (!tieneVacuna){%> --%>
						<%-- <div class='badge bg-warning rounded-pill'>Pendiente</div> --%>
<%-- <%					}%> --%>
			  	<%-- </div>
			</div>
			<div class="card bg-light mb-3 me-2" style="max-width: 20rem;">
			  	<div class="card-header"><span class="badge bg-pill badge bg-dark">H</span>&nbsp;<spring:message code="portal.alumno.previos.RetornoSeguro"/></div>
			  	<div class="card-header"><span class="badge rounded-pill bg-dark">H</span>&nbsp;Encuesta NSE</div>
			  	<div class="card-body ">
			    	<p style="font-size: 14px">
						<em><u>Station </u></em>
					</p>
			    	<p class="card-text">Contesta esta peque�a encuesta con veracidad (requisito de agencia acreditadora).</p> --%>
<%-- <%					if (encuestaNse == 0){%> --%>
				    	<%-- <span>�No hay encuestas activas!</span> --%>
<%-- <%					}else if (tieneNse){%> --%>
						<%-- <div class='badge bg-success rounded-pill'>Vigente hasta <%=encuestaActiva.getFechaFin()%></div> --%>
<%-- <%					}%> --%>
			  	<%-- </div>
			  	<div class="card-footer"> --%>
<%-- <%			if (encuestaNse >= 1 && codigoPersonal.equals(codigoAlumno)){%>	  	 --%>
			    	<%-- <a class="btn btn-success" href="nseEncuesta?EncuestaId=<%=encuestaNse%>"><spring:message code="portal.alumno.previos.Entrar"/></a> --%>
<%-- <%			}%>	 --%>
<%-- <% 			if (!tieneNse){%> --%>
				<%-- <div class='badge bg-warning rounded-pill'>Pendiente</div> --%>
<%-- <%			}	 --%>
<%-- %>				 --%>
			  	<%-- </div>
			</div> --%>
		<%-- <% }%> --%>
		</div>
	</div>	
</div>	
</body>