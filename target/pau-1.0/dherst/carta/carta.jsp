<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@page import="aca.dherst.spring.DherstArchivo"%>
<%@page import="aca.dherst.spring.DherstAlumno"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<% 
    String folio            = (String) request.getAttribute("folio");
    String codigoPersonal   = (String) request.getAttribute("codigoPersonal");
    String slfNo            = (String) request.getAttribute("slfNo");
    
    DherstAlumno alumno     = (DherstAlumno) request.getAttribute("alumno");
%>
<body>
<div class="container-fluid">
    <h2>Generate Letter <small class="text-muted fs-6">( <%=alumno.getCodigoPersonal()%> - <%=alumno.getNombre()%> <%=alumno.getApellido()%>)</small></h2>
    <div class="alert alert-info">
        <a class="btn btn-primary me-4" href="alumnos?folio=<%=folio%>">Return</a>
    </div>
    Click to generate the Acceptance Letter for the student
    <br>
    <a href="generarCarta?folio=<%=folio%>&codigoPersonal=<%=codigoPersonal%>&slfNo=<%=slfNo%>" class="btn btn-success my-3">Generate Letter</a>

</div>
</body>