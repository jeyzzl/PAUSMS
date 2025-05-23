<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.financiero.spring.FinGroup"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<%
    List<FinGroup> lisGroups = (List<FinGroup>) request.getAttribute("lisGroups");

    HashMap<String,String> mapNumConcepts = (HashMap<String,String>) request.getAttribute("mapNumConcepts");

    String mensaje = request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
%>
<body>
<div class="container-fluid">
    <h2>Concept Groups</h2>
    <div class="alert alert-info d-flex align-items-center">
        <a class="btn btn-primary me-2" href="editar">New Group</a>
<%  if(mensaje != null && !mensaje.equals("-")){%>
        <%=mensaje%>
<%  }%>
    </div>
    <table class="table table-sm table-bordered">
        <thead class="table-info">
            <th width="5%">Op.</th>
            <th width="5%"><spring:message code="aca.Numero"/></th>
            <th width="8%">Group ID</th>
            <th>Name</th>
            <th>Desc.</th>
            <th>Concepts</th>
        </thead>
<%  if(lisGroups != null){ %>
        <tbody>
<%  
        int row = 0;
        for(FinGroup group : lisGroups){
            row++;

            int numConcepts = 0;
            if(mapNumConcepts.containsKey(String.valueOf(group.getGroupId()))){
                numConcepts = Integer.parseInt(mapNumConcepts.get(String.valueOf(group.getGroupId())));
            }
%>
            <tr>
                <td>
                    <a href="concepts?GroupId=<%=group.getGroupId()%>"><i class="fas fa-plus-square text-success"></i></a>
                    <a href="editar?GroupId=<%=group.getGroupId()%>"><i class="fas fa-edit"></i></a>
<%          if(numConcepts == 0){%>
                    <a href="eliminar?GroupId=<%=group.getGroupId()%>"><i class="fas fa-trash text-danger"></i></a>
<%          }%>  
                </td>
                <td><%=row%></td>
                <td><%=group.getGroupId()%></td>
                <td><%=group.getName()%></td>
                <td><%=group.getDescription()%></td>
                <td><%=numConcepts%></td>
            </tr>
<%      }%>
        </tbody>
<%  }%>
    </table>
</div>
</body>