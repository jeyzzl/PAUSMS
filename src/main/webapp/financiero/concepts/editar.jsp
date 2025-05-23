<%@ page import= "java.util.List"%>
<%@ page import= "aca.financiero.spring.FinConcept"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript" >
// Function to check if UnitCost should be disabled
function checkUnitCostDisabled() {
    const typeSelect = document.getElementById('Type');
    const concIdInput = document.getElementById('ConcId');
    const unitCostInput = document.getElementById('UnitCost');
    const nameInput = document.getElementById('Name');
    
    // Check if Type is 'S' and ConcId is '0'
    if ((typeSelect.value === 'S' || typeSelect.value === 'F') && concIdInput.value === '0') {
        unitCostInput.disabled = true;
        unitCostInput.value = '0'; // Optional: set value to 0 when disabled
        nameInput.disabled = true;
        nameInput.value = ''; // Optional: set name to '' when disabled
    } else {
        unitCostInput.disabled = false;
        nameInput.disabled = false;
    }
}

// Function to show/hide CursoClave elements based on Type
function toggleCursoClave() {
    const typeSelect = document.getElementById('Type');
    const cursoClaveLabel = document.getElementById('cursoClaveLabel');
    const cursoClaveSelect = document.getElementById('CursoClave_chzn');
    
    if (typeSelect.value === 'S') {
        cursoClaveLabel.style.display = 'block';
        cursoClaveSelect.style.display = 'block';
    } else {
        cursoClaveLabel.style.display = 'none';
        cursoClaveSelect.style.display = 'none';
    }
}

// Run the functions when the page loads and when Type changes
document.addEventListener('DOMContentLoaded', function() {
    checkUnitCostDisabled();
    toggleCursoClave();
    
    // Add event listener to Type select
    document.getElementById('Type').addEventListener('change', function() {
        checkUnitCostDisabled();
        toggleCursoClave();
    });
});
</script>

<%
    FinConcept concept = (FinConcept)request.getAttribute("concept");

    List<String> lisLlaves = (List<String>)request.getAttribute("lisLlaves");
    List<MapaCurso> lisCursos = (List<MapaCurso>)request.getAttribute("lisCursos");

    String mensaje = request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
%>
<link rel="stylesheet" href="../../js/chosen/chosen.css" />
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
            <option value="A" <%=concept.getStatus().equals("A")?"selected":""%>>Active</option>
            <option value="I" <%=concept.getStatus().equals("I")?"selected":""%>>Inactive</option>
        </select>
        <label for="CursoClave" id="cursoClaveLabel" style="display:<%=concept.getType().equals("S")?"block":"none"%>"><b>Subject Key</b></label>
		<select class="form-select chosen mb-3" name="CursoClave" id="CursoClave" style="width:15rem; display:<%=concept.getType().equals("S")?"block":""%>">
<%      for(MapaCurso curso : lisCursos){%>
            <option value="<%=curso.getCursoClave()%>" <%=concept.getCursoClave() != null && concept.getCursoClave().equals(curso.getCursoClave())?"selected":""%>><%=curso.getCursoClave()%> - <%=curso.getNombreCurso()%></option>
<%      }%>
        </select>
        <label for="Code" class="mt-3"><b>Sync Code</b></label>
		<input class="input form-control mb-2" name="Code" type="text" id="Code" style="width:15rem;" value="<%=concept.getCode()%>">
    </div>
    <div class="alert alert-info">
        <input class="btn btn-primary" type="submit" value="Save">
    </div>
    </form>
</div>
</body>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script>
	jQuery(".chosen").chosen();
</script>