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
    
    List<MapaMayorMenor> lisMayores                 = (List<MapaMayorMenor>) request.getAttribute("lisMayores");

    HashMap<String,String> mapMayoresActivos = (HashMap<String,String>) request.getAttribute("mapMayoresActivos");

    int count = 0;
%>
<div class="container-fluid">
    <h2><%=nombrePlan%> MAJORS</h2>
    <div class="alert alert-info">
        <a class="btn btn-primary" href="listado?Facultad=<%=facultad%>"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
        <a class="btn btn-success" href="accion?Facultad=<%=facultad%>&PlanId=<%=planId%>&Tipo=MA">Add Major</a>
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
<%          for(MapaMayorMenor mayor : lisMayores){
                count++;
                String activos = "0";
                if(mapMayoresActivos.containsKey(mayor.getFolio())){
                    activos = mapMayoresActivos.get(mayor.getFolio());
                }
%>    
            <tr>
                <td>
                    <a href="accion?Folio=<%=mayor.getFolio()%>&PlanId=<%=planId%>&Facultad=<%=facultad%>&Tipo=MA"><i class="fas fa-edit"></i></a>
<% 
                if(activos.equals("0")){
%>
                    <a href="borrarMayor?Facultad=<%=facultad%>&PlanId=<%=mayor.getPlanId()%>&Folio=<%=mayor.getFolio()%>"><i class="fas fa-trash text-danger"></i></a>
<%              }%>
                </td>
                <td><%=count%></td>
                <td><%=mayor.getNombre()%></td>
                <td><%=mayor.getPorDefecto()%></td>
            </tr>
<%          }       
%>
        </tbody>
    </table>
</div>