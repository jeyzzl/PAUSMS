<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%Boolean inscripcion = request.getParameter("Inscripcion")==null?false:Boolean.parseBoolean(request.getParameter("Inscripcion"));

	String cargaId 		= request.getParameter("CargaId");
%>
<html>
<%
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno		= (String) session.getAttribute("codigoAlumno");
	
	if(!inscripcion){		 
%>
<%-- 		<jsp:include page="../menuPadre.jsp" /> --%>
		<%@ include file= "../menuPadre.jsp" %>
<%	}%>
	<div id="divCargar" style="text-align:center;margin:auto;width:10%;height:16px;"></div>
	<div id="divHorario"></div>	
	<script>		
		function ref(){			
			new Ajax.Request("horario3?CargaId=<%=cargaId%>&BloqueId=<%=request.getParameter("BloqueId")%>&Inscripcion=<%=inscripcion%>", {
				method: "get",
				onSuccess: function(req){
					if(req.responseText.indexOf("Error")==-1){
						jQuery("#divHorario").html(req.responseText);
						ayuda();
						jQuery("#divCargar").html("");	
					}
					else{
						jQuery("#divCargar").html("<font color='#AE2113' class='oculto'>Error de conexi&oacute;n</font>");
					}
				},
				onFailure: function(){
					jQuery("#divCargar").html("<font color='#AE2113' class='oculto'>Error de conexi&oacute;n</font>");
				}
			});
		}
		ref();
		jQuery(document).ready(function(){setInterval("ref()", 40000);});
	</script>
<%	if(!inscripcion){ %>
		<script>$('.nav-tabs').find('.materias').addClass('active');</script>
<%	}%>
</html>