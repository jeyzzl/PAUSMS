<%@page import="java.util.List"%>
<%@page import= "java.util.HashMap"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.math.RoundingMode"%>
<%@page import="aca.attache.spring.AttacheCustomer"%>
<%@page import="aca.attache.spring.AttacheCustomerInvoiceTransaction"%>
<%@page import="aca.attache.spring.AttacheCustomerPaymentTransaction"%>
<%@page import="aca.attache.spring.AttacheCustomerAdjustmentTransaction"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%@ include file="menu.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script  type = "text/javascript">
	function refreshPeriod(){
		document.frmAccion.submit();
	}
	function Grabar(){
		if(document.frmAccion.Periodo.value=="0"){			
			document.frmAccion.submit();
		}else{
			alert("Select 'All' periods to filter by Date");
		}
	}
</script>
<%
    String matricula        = (String)request.getAttribute("matricula");
    String nombreAlumno     = (String)request.getAttribute("nombreAlumno");
    String invnum           = (String)request.getAttribute("invnum");

    AttacheCustomer student                                         = (AttacheCustomer)request.getAttribute("student");
    AttacheCustomerInvoiceTransaction invoice                       = (AttacheCustomerInvoiceTransaction)request.getAttribute("invoice");
    List<AttacheCustomerInvoiceTransaction> lisInvoices             = (List<AttacheCustomerInvoiceTransaction>)request.getAttribute("lisInvoices");
    List<AttacheCustomerInvoiceTransaction> lisInvoicesAlum         = (List<AttacheCustomerInvoiceTransaction>)request.getAttribute("lisInvoicesAlum");
    List<AttacheCustomerPaymentTransaction> lisPagosPorInvoice      = (List<AttacheCustomerPaymentTransaction>)request.getAttribute("lisPagosPorInvoice");
    List<AttacheCustomerAdjustmentTransaction> lisAjustesPorInvoice = (List<AttacheCustomerAdjustmentTransaction>)request.getAttribute("lisAjustesPorInvoice");

    HashMap<String, String> mapaPagoPorInvoice = (HashMap<String, String>)request.getAttribute("mapaPagoPorInvoice");
%>
<html>
<body>
<div class="container-fluid">
    <h3 class="mt-1"><spring:message code="portal.alumno.cuenta.EstadoDeCuenta"/>
        <small class="text-muted fs-6" style="font-size:11px">( <%=matricula%> - <%=nombreAlumno%> )
    </h3>
    <form action="financieroPau" method="post" name="frmAccion">
        <div class="alert alert-info d-flex align-items-center">
            Invoice: 
            <select name="Invnum" id="Invnum" class="form-select ms-1 me-4" style="width: 14rem;"  onChange="javascritp:refreshPeriod()"> 
                <option value="0">All</option>
<%      for(AttacheCustomerInvoiceTransaction inv : lisInvoices){
            if(mapaPagoPorInvoice.containsKey(inv.getInvnum())){    
%>
                <option value="<%=inv.getInvnum()%>" <%=invnum.equals(inv.getInvnum())?"selected":""%>><%=inv.getInvnum()%> - <%=inv.getRefer()%></option>
<%      
            }
        }
%>
            </select>
            <a href="https://www.pau.ac.pg/payments/pay-tuition-fees/" class="btn btn-sm btn-dark" target="_blank"><i class="fas fa-credit-card"></i>&nbsp;Payment Options</a>
        </div>
    </form>
    <hr>
<%  // Handles Student with NO Transactions
    if (lisPagosPorInvoice == null || lisPagosPorInvoice.isEmpty()) {
%>    <div class="alert alert-warning">
        <h5>NO TRANSACTIONS FOUND</h5>
    </div>
<%
    } else {
%>
    <table id="table" class="table" data-bs-toggle="table" data-show-columns="true" data-show-header="true" data-show-export="true" data-export-types="['pdf']">
        <thead>
            <tr>
                <th width="10%"><spring:message code="portal.alumno.cuenta.Fecha"/></th>
                <th width="10%">Type</th>
                <th width="10%">Number</th>
                <th width="40%">Details</th>
                <th width="10%"><spring:message code="portal.alumno.cuenta.Cargos"/></th> 
                <th width="10%"><spring:message code="portal.alumno.cuenta.Creditos"/></th> 
                <th width="10%"><spring:message code="portal.alumno.cuenta.Saldo"/></th> 
            </tr> 
        </thead>
        <tbody>
<% 
        // Inicia recorrido por invoices
        for(AttacheCustomerInvoiceTransaction inv : lisInvoicesAlum){
            if(mapaPagoPorInvoice.containsKey(inv.getInvnum())){
%>
            <tr class="table-light">
                <td><b><%=inv.getInvdate()%></b></td>
                <td><b>Invoice</b></td>
                <td><b><%=inv.getInvnum()%></b></td>
                <td><b><%=inv.getRefer()%></b></td>
                <td><b><%=inv.getInvamt()%></b></td>
                <td><b></b></td>
                <td><b><%=inv.getInvbal()%></b></td>
            </tr>
<%          
            }
            // Imprime pagos por invoice
            for(AttacheCustomerPaymentTransaction payment : lisPagosPorInvoice){
                if (payment.getInvnum().equals(inv.getInvnum())) {
%>
            <tr>
                <td><%=payment.getInvdate()%></td>
                <td>Payment</td>
                <td><%=payment.getDocnum()%></td>
                <td><%=payment.getComment()%></td>
                <td><%=payment.getInvamt() < 0 ? Math.abs(payment.getInvamt()) : ""%></td>
                <td><%=payment.getInvamt() > 0 ? "-"+payment.getInvamt() : ""%></td>
                <td></td>
            </tr>
<%      
                }
            }
            // Imprime transacciones por invoice
            for(AttacheCustomerAdjustmentTransaction adjustment : lisAjustesPorInvoice){
                if (adjustment.getInvnum().equals(inv.getInvnum())) {
%>
            <tr>
                <td><%=adjustment.getInvdate()%></td>
                <td><%=adjustment.getInvnum()%></td>
                <td><%=adjustment.getRefer()%></td>
                <td><%=adjustment.getComment()%></td>
                <td></td>
                <td><%=adjustment.getInvamt()%></td>
                <td></td>
            </tr>
<%                
                }
            }
        }
%>
        </tbody>
        <tfooter>
            <tr></tr>
        </footer>
    </table>
    <div class="alert alert-warning d-flex align-items-center">
        <h3>Amount Due:&nbsp;<%=student.getOpenbal()%></h3>
    </div>
<% }%>
</div>
</body>
</html>