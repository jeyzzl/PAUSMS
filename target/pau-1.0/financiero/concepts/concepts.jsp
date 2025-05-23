<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.financiero.spring.FinConcept"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%
    List<FinConcept> lisConcepts = (List<FinConcept>)request.getAttribute("lisConcepts");

    String mensaje = request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
%>
<body>
<div class="container-fluid">
    <h2>Quotation Concepts</h2>
    <div class="alert alert-info d-flex align-items-center">
        <a class="btn btn-primary me-2" href="editar"><spring:message code="aca.Anadir"/></a>
<%  if(mensaje != null && !mensaje.equals("-")){%>
        <%=mensaje%>
<%  }%>
    </div>
    <table class="table table-sm table-bordered">
        <thead class="table-info">
            <th width="5%">Op.</th>
            <th width="5%"><spring:message code="aca.Numero"/></th>
            <th width="8%">Concept ID</th>
            <th>Name</th>
            <th width="10%">Unit Cost</th>
            <th width="10%">Type</th>
            <th width="10%">Status</th>
        </thead>
<%  if(lisConcepts != null){ %>
        <tbody>
<%  
    int row = 0;    
        for(FinConcept concept : lisConcepts){ 
            row++;
%>
            <tr>
                <td>
                    <a href="editar?ConcId=<%=concept.getConcId()%>"><i class="fas fa-edit"></i></a>
                    <a href="eliminar?ConcId=<%=concept.getConcId()%>"><i class="fas fa-trash text-danger"></i></a>
                </td>
                <td><%=row%></td>
                <td><%=concept.getConcId()%></td>
                <td><%=concept.getName()%></td>
                <td>$<%=concept.getUnitCost()%></td>
                <td><%=concept.getType().equals("F")?"Fee":concept.getType().equals("D")?"Discount":"Subject"%></td>
                <td><%=concept.getStatus()%></td>
            </tr>
<%      }%>
        </tbody>
    </table>
<%  }%>
</div>
</body>