<%@ page import="java.text.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="aca.nse.spring.NsePregunta" %>
<%@ page import="aca.nse.spring.NseRespuestaPregunta" %>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%@ include file="../alumno/menu.jsp"%>
<%
	String codigoPersonal 		= (String) session.getAttribute("codigoAlumno");
	String encuestaId 			= (String) request.getAttribute("encuestaId");
	String mensaje	  			= (String) request.getAttribute("mensaje");

	List<NsePregunta> lisPreguntas 				= (List<NsePregunta>) request.getAttribute("lisPreguntas");
	List<NseRespuestaPregunta> lisRespuestas 	= (List<NseRespuestaPregunta>) request.getAttribute("lisRespuestas");	
	HashMap<String,String> mapaRespuestas		= (HashMap<String,String>) request.getAttribute("mapaRespuestas");
	
%>
<div class="container-fluid">
	<h2>
		Encuesta NSE		 
<%-- 		<small class="text-muted fs-5">( <%=codigoAlumno%> - <%=alumnoNombre%> - <%=planId%> - <%=nombreMateria%> )</small> --%>
	</h2>
	<div class="alert alert-info">
		<a href="previos" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
<%  if(mensaje.equals("1")){%>
	<div class="alert alert-success">
		Grabado.
	</div>
<%  }%>
	<form action="grabarEncuesta" method="post" name="form" target="_self">
		<input type="hidden" name="EncuestaId" value="<%=encuestaId%>">
<% 		for(NsePregunta pregunta : lisPreguntas){%>
			<h4 class="mt-3"><%=pregunta.getPregunta()%></h4>
			<select class="form-select" name="preguntaId<%=pregunta.getPreguntaId()%>">
<% 			
			for (NseRespuestaPregunta respuesta : lisRespuestas){
				if (respuesta.getPreguntaId().equals(pregunta.getPreguntaId())){				
					String respuestaAlumno = "0";
					if(mapaRespuestas.containsKey(respuesta.getPreguntaId())){
						respuestaAlumno = mapaRespuestas.get(respuesta.getPreguntaId());
					}
%>
				<option value="<%=respuesta.getRespuestaId()+"-"+respuesta.getPuntos()%>" <%=respuestaAlumno.equals(respuesta.getRespuestaId())?"selected":""%>>
					<%=respuesta.getRespuesta()%>
				</option>
<% 				}%>			
<% 			}%>
			</select>
<% 		}%>
		<br>
		<button type="submit" class="btn btn-success">Grabar</button>
	</form>
</div>