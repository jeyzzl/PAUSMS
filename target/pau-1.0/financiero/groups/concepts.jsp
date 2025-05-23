<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.financiero.spring.FinConcept"%>
<%@ page import="aca.financiero.spring.FinGroup"%>
<%@ page import="aca.financiero.spring.FinGroupConcept"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<%
    FinGroup group                          = (FinGroup) request.getAttribute("group");

    List<FinGroupConcept> lisGroupConcepts  = (List<FinGroupConcept>) request.getAttribute("lisGroupConcepts");
    List<FinConcept> lisConcepts            = (List<FinConcept>) request.getAttribute("lisConcepts");

    HashMap<Integer,FinConcept> mapConcepts = (HashMap<Integer, FinConcept>) request.getAttribute("mapConcepts");

    String mensaje = request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
%>
<body>
<div class="container-fluid">
    <h2>Group Concepts <small class="text-muted fs-5">( <%=group.getGroupId()%> | <%=group.getName()%>)</small></h2>
    <form action="agregar" method="post" name="frmGroupConc">
    <div class="alert alert-info d-flex align-items-center">
        <a class="btn btn-primary me-3" href="groups">Return</a>
        Concept: 
        <select class="form-select mx-2" style="width:20rem;" id="ConcId" name="ConcId">
<%  for(FinConcept concept : lisConcepts){%>
            <option value="<%=concept.getConcId()%>"><%=concept.getConcId()%> - <%=concept.getName()%> - <%=concept.getUnitCost()%></option>
<%  }%>    
        </select>
        No. Units:
        <input class="input form-control ms-1" type="text" name="NoUnits" id="NoUnits" style="width:5rem;" value="0">
        <input type="hidden" name="GroupId" id="GroupId" value="<%=group.getGroupId()%>">

        <input class="btn btn-primary mx-2" type="submit" value="Add">
<%  if(mensaje != null && !mensaje.equals("-")){%>
        <%=mensaje%>
<%  }%>
    </div>
    </form>
    <table class="table table-sm table-bordered">
        <thead class="table-info">
            <th width="5%">Op.</th>
            <th width="5%"><spring:message code="aca.Numero"/></th>
            <th width="8%">Concept ID</th>
            <th>Name</th>
            <th width="10%">No. Units</th>
        </thead>
<%  if(lisGroupConcepts != null){ %>
        <tbody>
<%  
    int row = 0;    
        for(FinGroupConcept groupConcept : lisGroupConcepts){ 
            row++;

            FinConcept conc = new FinConcept();
            
            if(mapConcepts.containsKey(groupConcept.getConcId())){
                conc =  mapConcepts.get(groupConcept.getConcId());
            }
%>
            <tr>
                <td>
                    <a href="remover?GroupId=<%=group.getGroupId()%>&ConcId=<%=conc.getConcId()%>"><i class="fas fa-trash text-danger"></i></a>
                </td>
                <td><%=row%></td>
                <td><%=conc.getConcId()%></td>
                <td><%=conc.getName()%></td>
                <td><%=groupConcept.getNoUnits()%></td>
            </tr>
<%      }%>
        </tbody>
<%  }%>
    </table>
</div>
</body>