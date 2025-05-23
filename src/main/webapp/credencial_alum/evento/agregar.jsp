<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.matricula.spring.MatEvento"%>
<%@page import="aca.carga.spring.Carga"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<html>
<body>
<%  

    List<Carga> lisCargas = (List<Carga>) request.getAttribute("lisCargas");
	String eventoId		= (String) request.getAttribute("eventoId");
    String mensaje      = (String) request.getParameter("Mensaje");

	MatEvento evento    = (MatEvento) request.getAttribute("evento");
%>
<div class="container-fluid">
    <h2>Event</h2>
    <div class="alert alert-info">
        <a href="eventos" class="btn btn-primary">Return</a>

    </div>
    <form name="frmCredencial" action="grabar">
        <label class="form-label" for="EventoId">Event ID</label>
        <input type="text" id="EventoId" name="EventoId" class="form-control" style="width:20rem;" value="<%=evento.getEventoId()%>" readonly>
        <label class="form-label" for="CargaId">Load</label>
        <select id="CargaId" name="CargaId" class="form-select" style="width:20rem;">
<%          for(Carga carga : lisCargas){%>
            <option value="<%=carga.getCargaId()%>" <%=evento.getCargaId().equals(carga.getCargaId())?"selected":""%>><%=carga.getCargaId()%> - <%=carga.getNombreCarga()%></option>
<%          }%>
        </select>
        <label class="form-label" for="Nombre">Event Title</label>
        <input type="text" id="Nombre" name="Nombre" class="form-control" style="width:20rem;" value="<%=evento.getEventoNombre()%>">
        <label for="Estado">Status</label>
        <select id="Estado" name="Estado" class="form-select" style="width:20rem;">
            <option value="A" <%=evento.getEstado().equals("A")?"selected":""%>>Active</option>
            <option value="I" <%=evento.getEstado().equals("I")?"selected":""%>>Inactive</option>
        </select>
        <br>
        <a class="btn btn-primary" onclick="javascript:Guardar();"><i class="icon-ok icon-white"></i> Save</a>
    </form>
</div>
</body>
<script>
	function Guardar() {
			document.frmCredencial.submit();
	}
</script>
</html>