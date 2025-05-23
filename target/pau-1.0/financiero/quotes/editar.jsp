<%@ page import= "java.util.List"%>
<%@ page import="aca.financiero.spring.FinQuote"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<%
    String periodoId    = (String) request.getAttribute("periodoId");

    String mensaje  = request.getParameter("Mensaje")==null?"-": request.getParameter("Mensaje");

    FinQuote quote = (FinQuote)request.getAttribute("quote");
%>
<body>
<div class="container-fluid">
    <h2>Edit Quote</h2>
    <div class="alert alert-info d-flex align-items-center">
        <a class="btn btn-primary" href="quotes?PeriodoId=<%=periodoId%>"><spring:message code="aca.Regresar"/></a>
    </div>
    <form action="grabar" method="post" name="frmQuote">
    <div class="form-group">
        <label for="PeriodoId"><b>Period ID</b></label>
        <input class="input form-control mb-2" type="text" id="PeriodoId" name="PeriodoId" style="width:15rem;" value="<%=periodoId%>" readonly>
        <label for="QuoteId"><b>Quote ID</b></label>
		<input class="input form-control mb-2" name="QuoteId" type="text" id="QuoteId" maxlength="2" style="width:15rem;" value="<%=quote.getQuoteId()%>" readonly>
        <label for="Fecha"><b>Date</b></label>
        <input type="text" name="Fecha" id="Fecha" data-date-format="dd/mm/yyyy" class="form-control" style="width: 15rem;" value="<%=quote.getFecha()%>" <%=quote.getQuoteId()==0?"readonly":""%>>
        <label for="Desc"><b>Description</b></label>
		<textarea class="form-control" name="Desc" id="Desc" rows="3" style="width:15rem;"><%=quote.getDescription()%></textarea>
        <label for="Status"><b>Status</b></label>
        <select name="Status" id="Status" class="form-select" style="width:15rem;" >
            <option value="A" <%=quote.getStatus().equals("A")?"selected":""%>>Active</option>
            <option value="I" <%=quote.getStatus().equals("I")?"selected":""%>>Inactive</option>
        </select>
    </div>
    <div class="alert alert-info mt-4">
        <input class="btn btn-primary" type="submit" value="Save">
    </div>
    </form>
</div>
</body>
<script>
	jQuery('#Fecha').datepicker();
</script>