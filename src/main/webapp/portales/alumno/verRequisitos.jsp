<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.bitacora.spring.BitRequisito"%>
<%@page import="aca.bitacora.spring.BitTramite"%>
<%@page import="aca.bitacora.spring.BitTramiteRequisito"%>
<%@page import="aca.bitacora.spring.BitRequisitoAlumno"%>
<%@page import="aca.bitacora.spring.BitSolicitud"%>

<%-- <jsp:include page="../menu.jsp" /> --%>
<%@ include file= "menu.jsp" %>
<%
	String codigoAlumno	= (String) request.getAttribute("matricula");
	String nombreAlumno	= (String)request.getAttribute("nombreAlumno");
	String folio		= (String)request.getAttribute("folio");
	String nombreTramite		= (String)request.getAttribute("nombreTramite");

	BitSolicitud bitSolicitud = (BitSolicitud)request.getAttribute("bitSolicitud");

	List<BitTramiteRequisito> lisRequisitos = (List<BitTramiteRequisito>)request.getAttribute("lisRequisitos");

	HashMap<String, BitRequisitoAlumno> mapaBitRequisitoAlumno 	= (HashMap<String, BitRequisitoAlumno>) request.getAttribute("mapaBitRequisitoAlumno");
	HashMap<String, BitRequisito> mapRequisitos 				= (HashMap<String, BitRequisito>) request.getAttribute("mapRequisitos");
%>
<div class="container-fluid">
	<h3>Requisitos de tu solicitud de trámite en línea:<small class="text-muted fs-5"> ( <%=codigoAlumno%> - <%=nombreAlumno%> - <%=nombreTramite%>)</small></h3>
	<div class="alert alert-primary" role="alert">
		<a href="tramites?Folio=<%=folio%>" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;
	</div><br>
	<div>
		<h3>Comentario administrador</h3>
		<%=bitSolicitud.getTextoAdmin()%>
		<br>
	</div>
	<table class="table table-stripped">		
		<tr>
			<th><font size="4">#</font></th>
			<th><font size="4">Cumple</font></th>			
			<th><font size="4">Requisito</font></th>
			<th><font size="4">Comentario</font></th>
		</tr>
<%
	int row = 1;
	for (BitTramiteRequisito requisito : lisRequisitos){
		
		BitRequisito bitRequisito = new BitRequisito();
		if (mapRequisitos.containsKey(requisito.getRequisitoId())){
			bitRequisito = mapRequisitos.get(requisito.getRequisitoId());
		}
		
		boolean ok 	= false;
		if (mapaBitRequisitoAlumno.containsKey(codigoAlumno+requisito.getTramiteId()+requisito.getRequisitoId()+folio)){	
			BitRequisitoAlumno requisitoAlumno = mapaBitRequisitoAlumno.get(codigoAlumno+requisito.getTramiteId()+requisito.getRequisitoId()+folio);
			if(requisitoAlumno.getEstado().equals("A")){
				ok 	= true;
			}
		}
		
%>
		<tr>
			<td><font size="3"><%=row++%></font></td>
			<td>
				<font size="3">
<%				if(ok){%>
					<i class="fas fa-check" style="color: #adff2f;"></i>
<%				}else{%>
					<i class="fas fa-times" style="color: #ff0000;"></i>
<%				}%>
				</font>
			</td>
			<td><font size="3"><%=bitRequisito.getNombre()%></font></td>
			<td><font size="3"><%=bitRequisito.getComentario()%></font></td>
		</tr>
<%		
	}	
%>		
	</table>
</div>
