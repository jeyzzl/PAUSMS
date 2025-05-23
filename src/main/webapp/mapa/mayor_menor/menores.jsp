<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.plan.spring.MapaMayorMenor"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%
    String planId       = (String)request.getParameter("PlanId");
    String nombrePlan   = (String) request.getAttribute("nombrePlan");
    String facultad     = (String) request.getAttribute("facultad");
    
    List<MapaMayorMenor> lisMenores                 = (List<MapaMayorMenor>) request.getAttribute("lisMenores");

    HashMap<String,String> mapMenoresActivos = (HashMap<String,String>) request.getAttribute("mapMenoresActivos");

    int count = 0;
%>
<div class="container-fluid">
    <h2><%=nombrePlan%> MINORS</h2>
    <div class="alert alert-info">
        <a class="btn btn-primary" href="listado?Facultad=<%=facultad%>"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
        <a class="btn btn-success" href="accion?Facultad=<%=facultad%>&PlanId=<%=planId%>&Tipo=ME">Add Minor</a>
    </div>
    <table class="table table-sm table-bordered">
        <thead class="table-dark"> 
            <tr>
                <td width="5%">&nbsp;</td>
                <td width="10%"><b>No.</b></td>
                <td width="70%">Name</td>
                <td width="15%">Default</td>
            </tr>
        </thead>
        <tbody>
<%          for(MapaMayorMenor menor : lisMenores){
                count++;
                String activos = "0";
                if(mapMenoresActivos.containsKey(menor.getFolio())){
                    activos = mapMenoresActivos.get(menor.getFolio());
                }
%>    
            <tr>
                <td>
                    <a href="accion?Folio=<%=menor.getFolio()%>&PlanId=<%=planId%>&Facultad=<%=facultad%>&Tipo=ME"><i class="fas fa-edit"></i></a>
<% 
                if(activos.equals("0")){
%>
                    <a href="borrarMenor?Facultad=<%=facultad%>&PlanId=<%=menor.getPlanId()%>&Folio=<%=menor.getFolio()%>"><i class="fas fa-trash text-danger"></i></a>
<%              } %>
                </td>
                <td><%=count%></td>
                <td><%=menor.getNombre()%></td>
                <td><%=menor.getPorDefecto()%></td>
            </tr>
<%          }       
%>
        </tbody>
    </table>
</div>