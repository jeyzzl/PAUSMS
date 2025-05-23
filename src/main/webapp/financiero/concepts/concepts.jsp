<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="java.util.Locale"%>
<%@ page import="aca.financiero.spring.FinConcept"%>
<%@ page import="aca.plan.spring.MapaPlan"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%
    String tipo = (String)request.getAttribute("tipo");
    String status = (String)request.getAttribute("status");
    String planId = (String)request.getAttribute("planId");

    List<FinConcept> lisConcepts            = (List<FinConcept>)request.getAttribute("lisConcepts");
    List<MapaPlan> lisPlanes                = (List<MapaPlan>)request.getAttribute("lisPlanes");

    HashMap<String,String> mapConceptsUsed  = (HashMap<String,String>)request.getAttribute("mapConceptsUsed");  

    String mensaje = request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");

    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
%>

<script type="text/javascript">
	function cambioPeriodo(){
		document.frmConcepts.submit();	
	}		
</script>

<body>
<div class="container-fluid">
    <h2>Quotation Concepts</h2>
    <form id="frmConcepts" name="frmConcepts" action="concepts" method="post">
    <div class="alert alert-info d-flex align-items-center">
        <a class="btn btn-primary me-4" href="editar"><spring:message code="aca.Anadir"/></a>
        Type:
        <select class="form-select chosen me-2" name="Tipo" id="Tipo" style="width:15rem;" onchange="javacsript:cambioPeriodo();">
            <option value="A" <%=tipo.equals("A")?"selected":""%>>All</option>
            <option value="F" <%=tipo.equals("F")?"selected":""%>>Fee</option>
            <option value="S" <%=tipo.equals("S")?"selected":""%>>Subject</option>
            <option value="D" <%=tipo.equals("D")?"selected":""%>>Discount</option>
        </select>
        Status:
        <select class="form-select chosen me-2" name="Status" id="Status" style="width:15rem;" onchange="javacsript:cambioPeriodo();">
            <option value="B" <%=status.equals("B")?"selected":""%>>All</option>
            <option value="A" <%=status.equals("A")?"selected":""%>>Active</option>
            <option value="I" <%=status.equals("I")?"selected":""%>>Inactive</option>
        </select>
<%  if(tipo.equals("S")){%>
        Plan:
        <select class="form-select chosen me-2" name="PlanId" id="PlanId" style="width:15rem;" onchange="javacsript:cambioPeriodo();">
            <option value="-" <%=planId.equals("-")?"selected":""%>>All</option>
<%      for(MapaPlan plan : lisPlanes){%>
            <option value="<%=plan.getPlanId()%>" <%=planId.equals(plan.getPlanId())?"selected":""%>><%=plan.getPlanId()%>-<%=plan.getNombrePlan()%></option>
<%      }%>
        </select>
<%  }%>
        <a class="btn btn-success me-2" href="sincronizarMaterias">Sync Subject Prices</a>
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
<%  
            String used = "0";
            if(mapConceptsUsed.containsKey(String.valueOf(concept.getConcId()))) used = mapConceptsUsed.get(String.valueOf(concept.getConcId()));
            if(used.equals("0")){%>
                    <a href="eliminar?ConcId=<%=concept.getConcId()%>"><i class="fas fa-trash text-danger"></i></a>
<%          }%> 
                </td>
                <td><%=row%></td>
                <td><%=concept.getConcId()%></td>
                <td><%=concept.getName()%></td>
                <td><%=currencyFormat.format(concept.getUnitCost()).replace('$','K')%></td>
                <td><%=concept.getType().equals("F")?"Fee":concept.getType().equals("D")?"Discount":"Subject"%></td>
                <td><%=concept.getStatus()%></td>
            </tr>
<%      }%>
        </tbody>
    </table>
<%  }%>
</div>
</body>