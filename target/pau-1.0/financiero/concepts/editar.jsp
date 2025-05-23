<%@ page import= "java.util.List"%>
<%@ page import= "aca.financiero.spring.FinConcept"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<%
    FinConcept concept = (FinConcept)request.getAttribute("concept");

    List<String> lisLlaves = (List<String>)request.getAttribute("lisLlaves");

    String mensaje = request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
%>
<body>
<div class="container-fluid">
    <h2>Edit Concept <small  class="text-muted fs-5">(ConcId: <%=concept.getConcId()%>)</small></h2>
    <div class="alert alert-info d-flex align-items-center">
        <a class="btn btn-primary me-2" href="concepts"><spring:message code="aca.Regresar"/></a>
<%  if(mensaje != null && !mensaje.equals("-")){%>
        <%=mensaje%>
<%  }%>
    </div>
    <form action="grabar" method="post" name="frmConcept">
    <div class="form-group">
        <label for="ConcId"><b>Concept ID</b></label>
		<input class="input form-control mb-2" name="ConcId" type="text" id="ConcId" maxlength="2" style="width:15rem;" value="<%=concept.getConcId()%>" readonly>
        <label for="Name"><b>Name</b></label>
		<input class="input form-control mb-2" name="Name" type="text" id="Name" maxlength="40" style="width:15rem;" value="<%=concept.getName()%>">
        <label for="UnitCost"><b>Unit Cost</b></label>
		<input class="input form-control mb-2" name="UnitCost" type="text" id="UnitCost" style="width:15rem;" value="<%=concept.getUnitCost()%>">
        <label for="Type"><b>Type</b></label>
		<select class="form-select mb-3" name="Type" id="Type" style="width:15rem;">
            <option value="F" <%=concept.getType().equals("F")?"selected":""%>>Fee</option>
            <option value="D" <%=concept.getType().equals("D")?"selected":""%>>Discount</option>
            <option value="S" <%=concept.getType().equals("S")?"selected":""%>>Subject</option>
        </select>
        <label for="Status"><b>Status</b></label>
		<select class="form-select mb-3" name="Status" id="Status" style="width:15rem;">
            <option value="A" <%=concept.getType().equals("A")?"selected":""%>>Active</option>
            <option value="I" <%=concept.getType().equals("I")?"selected":""%>>Inactive</option>
        </select>
<%  if(concept.getType().equals("S")){%>
        <label for="CursoClave"><b>Subject Key</b></label>
		<select class="form-select mb-3" name="CursoClave" id="CursoClave" style="width:15rem;">
            <option>Select key...</option>
<%      for(String llave : lisLlaves){%>
            <option value="<%=llave%>" <%=concept.getCursoClave().equals(llave)?"selected":""%>><%=llave%></option>
<%      }%>
        </select>
<%  }%>
    </div>
    <div class="alert alert-info">
        <input class="btn btn-primary" type="submit" value="Save">
    </div>
    </form>
</div>
</body>