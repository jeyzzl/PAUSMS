<%@ page import="java.util.List"%>
<%@ page import="aca.plan.spring.MapaPlan"%>
<%@ page import="aca.plan.spring.MapaVersion"%>
<%@ page import="aca.plan.spring.MapaMayorMenor"%>
<%@ page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%
    String facultad     = (String) request.getParameter("Facultad");
    String planId       = (String) request.getParameter("PlanId");
    String tipo         = (String) request.getParameter("Tipo");
    String mensaje      = (String) request.getParameter("Mensaje");

    MapaMayorMenor mm = (MapaMayorMenor) request.getAttribute("mayorMenor");

%>
<div class="container-fluid">
    <h2>Edit</h2>
    <div class="alert alert-info">
        <a class="btn btn-primary" href="<%=tipo.equals("MA")?"mayores":"menores"%>?Facultad=<%=facultad%>&PlanId=<%=planId%>"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;&nbsp;
    </div>
<form action="grabar" method="post" name="frmplan">
    <input name="Tipo" id="Tipo" value="<%=mm.getTipo()%>" type="hidden">
    <input name="Folio" id="Folio" value="<%=mm.getFolio()%>" type="hidden">
    <input name="Facultad" id="Facultad" value="<%=facultad%>" type="hidden">
    <div class="d-flex-inline">
        <label>Plan</label>
        <input name="PlanId" id="PlanId" type="text" class="text form-control" style="width: 15rem;" value="<%=planId%>" readonly>
        <br>
        <label>Name</label>
        <input name="Nombre" id="Nombre" type="text" class="text form-control" style="width: 25rem;" value="<%=mm.getNombre()%>">
        <br>
        <label>Default</label>
        <select name="PorDefault" id="PorDefault" class="form-select" style="width: 15rem;">
            <option value="S" <%=mm.getPorDefecto().equals("S")?"selected":""%>>YES</option>
            <option value="N" <%=mm.getPorDefecto().equals("N")?"selected":""%>>NO</option>
        </select>
        <br>
        <div class="alert alert-info">
            <button class="btn btn-primary" type="submit">Save</button>
        </div>
    </div>
</form>
</div>