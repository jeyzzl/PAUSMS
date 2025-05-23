<%@ page import= "java.util.List"%>
<%@ page import="aca.financiero.spring.FinGroup"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<%
    FinGroup group  = (FinGroup)request.getAttribute("group");

    String mensaje  = request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
%>
<body>
<div class="container-fluid">
    <h2>Edit Group</h2>
    <div class="alert alert-info d-flex align-items-center">
        <a class="btn btn-primary me-2" href="groups"><spring:message code="aca.Regresar"/></a>
<%  if(mensaje != null && !mensaje.equals("-")){%>
        <%=mensaje%>
<%  }%>
    </div>
    <form action="grabar" method="post" name="frmGroup">
    <div class="form-group">
        <label for="GroupId"><b>Group ID</b></label>
		<input class="input form-control mb-2" name="GroupId" type="text" id="GroupId" maxlength="2" style="width:15rem;" value="<%=group.getGroupId()%>" readonly>
        <label for="Name"><b>Name</b></label>
		<input class="input form-control mb-2" name="Name" type="text" id="Name" maxlength="40" style="width:15rem;" value="<%=group.getName()%>">
        <label for="Desc"><b>Description</b> <span id="charCount">0</span>/90</label>
		<textarea class="form-control" name="Desc" id="Desc" rows="3" style="width:15rem;"><%=group.getDescription()%></textarea>
    </div>
    <div class="alert alert-info mt-4">
        <input class="btn btn-primary" type="submit" value="Save">
    </div>
    </form>
</div>
</body>
<script>

    // Character counter for description textarea
    $(document).ready(function() {
        const textarea = $('#Desc');
        const charCount = $('#charCount');
        const maxLength = 90;
        
        // Update counter on page load (in case there's existing text)
        charCount.text(textarea.val().length);
        
        // Update counter as user types
        textarea.on('input', function() {
            const currentLength = $(this).val().length;
            charCount.text(currentLength);
            
            // Optional: change color when approaching limit
            if (currentLength > maxLength * 0.9) {
                charCount.css('color', 'red');
            } else {
                charCount.css('color', '');
            }
        });
    });
</script>