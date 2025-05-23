<%@page import="java.util.List"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.math.RoundingMode"%>
<%@page import="aca.fulton.spring.Student"%>
<%@page import="aca.fulton.spring.StudentTransactions"%> 

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%@ include file="menu.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script  type = "text/javascript">
	function refreshPeriod(){
        document.frmAccion.FInicio.value = null;		
        document.frmAccion.FFin.value = null;
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
    String periodo          = (String)request.getAttribute("periodo");
    String fechaInicio      = (String)request.getAttribute("fechaInicio");
    String fechaFin         = (String)request.getAttribute("fechaFin");
    String balancePrevio    = (String)request.getAttribute("balancePrevio");

    Student student = (Student)request.getAttribute("student");
    List<StudentTransactions> lisTransactions   = (List<StudentTransactions>)request.getAttribute("lisTransactions");
    List<String> lisPeriodosAlumno              = (List<String>)request.getAttribute("lisPeriodosAlumno");

    DecimalFormat df = new DecimalFormat("#.00");
    df.setRoundingMode(RoundingMode.HALF_UP); // Set rounding mode for DecimalFormat

%>
<html>
<body>
<div class="container-fluid">
    <h3 class="mt-1"><spring:message code="portal.alumno.cuenta.EstadoDeCuenta"/>
        <small class="text-muted fs-6" style="font-size:11px">( <%=matricula%> - <%=nombreAlumno%> )
    </h3>
    <form action="financieroFulton" method="post" name="frmAccion">
    <div class="alert alert-info d-flex align-items-center">
        Period:
        <select name="Periodo" id="Periodo" class="form-select me-4" style="width: 14rem;"  onChange="javascritp:refreshPeriod()"> 
            <option value="0">All</option>
<%      for(String period : lisPeriodosAlumno){%>
            <option value="<%=period%>" <%=periodo.equals(period)?"selected":""%>><%=period%></option>
<%      }%>
        </select>
        Date:
        <input name="FInicio" type="text" class="form-control mx-2" style="width: 10rem;" id="FInicio" data-date-format="yyyy/mm/dd" value="<%=fechaInicio.equals("0")?"":fechaInicio%>" onfocus="focusFecha(this);" size="12" maxlength="10" placeholder="YYYY/MM/DD">
        to
        <input name="FFin" type="text" class="form-control ms-2 me-2 ," style="width: 10rem;" id="FFin" data-date-format="yyyy/mm/dd" value="<%=fechaFin.equals("0")?"":fechaFin%>" onfocus="focusFecha(this);" size="12" maxlength="10" placeholder="YYYY/MM/DD">
        <a href="javascript:Grabar()" class="btn btn-success me-3"><i class="fas fa-search"></i></a>
        <a href="referencias" class="btn btn-sm btn-dark disabled"><i class="fas fa-credit-card"></i>&nbsp;Bank References</a>
    </div>
    </form>
    <hr>
<%  // Handles Student with NO Transactions
    if (lisTransactions == null || lisTransactions.isEmpty()) {
%>
    <div class="alert alert-warning">
        <h5>NO TRANSACTIONS FOUND</h5>
    </div>
<%
    } else {
%>
    <table id="table" class="table" data-bs-toggle="table" data-show-columns="true" data-show-header="true" data-show-export="true" data-export-types="['pdf']">
        <thead>
            <tr>
                <th><spring:message code="portal.alumno.cuenta.Periodo"/></th>
                <th><spring:message code="portal.alumno.cuenta.Fecha"/></th> 
                <th><spring:message code="portal.alumno.cuenta.Tipo"/></th> 
                <th><spring:message code="portal.alumno.cuenta.Referencia"/></th>
                <th><spring:message code="portal.alumno.cuenta.Descripcion"/></th> 
                <th><spring:message code="portal.alumno.cuenta.Cargos"/></th> 
                <th><spring:message code="portal.alumno.cuenta.Creditos"/></th> 
                <th class="text-end"><spring:message code="portal.alumno.cuenta.Saldo"/></th> 
                <th></th> 
            </tr> 
        </thead>
        <tbody>
<%      
    // If balancePrevio is not null, display it as the first row
    if (balancePrevio != null && !balancePrevio.isEmpty()) {
        BigDecimal previousBalance = BigDecimal.valueOf(Double.parseDouble(balancePrevio));
%>
        <tr>
            <td></td>
            <td></td> 
            <td></td> 
            <td></td> 
            <td><b>Balance Brought Forward</b></td>
            <td></td> 
            <td></td>
            <td class="text-end text-primary"><b><%=df.format(previousBalance)%></b></td>
            <td></td>
        </tr>
<%      
    }
        // Handles Transactions
        String typeColor = "bg-secondary";
        String balanceTempColor = "text-secondary";
        BigDecimal balanceTot = balancePrevio==null?BigDecimal.ZERO:BigDecimal.valueOf(Double.parseDouble(balancePrevio));
        for(StudentTransactions transaction : lisTransactions){   
            String credito = "";
            String cargo = ""; 
            typeColor = transaction.getDc().equals("CR") ? "bg-success" : "bg-warning";

            BigDecimal amount = transaction.getAmount();

            if(transaction.getDc().equals("CR")){
                credito = df.format(amount);
                balanceTot = balanceTot.subtract(amount);
            } else if(transaction.getDc().equals("DR")){
                cargo = df.format(amount);
                balanceTot = balanceTot.add(amount);
            }

            if(balanceTot.compareTo(BigDecimal.ZERO) < 0) balanceTempColor = "text-danger";
            if(balanceTot.compareTo(BigDecimal.ZERO) > 0) balanceTempColor = "text-primary";
%>
            <tr>
                <td><%=transaction.getPeriod()%></td>
                <td><%=transaction.getDate().substring(0,10)%></td>
                <td><%=transaction.getType()%></td>
                <td><%=transaction.getReference()%></td>
                <td><%=transaction.getDescription()%></td>
                <td><b><%=cargo%></b></td>
                <td><b><%=credito%></b></td>
                <td class="text-end <%=balanceTempColor%>"><b><%=df.format(balanceTot)%></b></td>
                <td></td>
            </tr>
<%      }
        // Checks if calculated balance matches with the stored balance from GL record
        String colorBalance = "text-secondary";

        BigDecimal studentBalance = new BigDecimal(df.format(student.getBalance()));
        studentBalance = studentBalance.setScale(2, RoundingMode.HALF_UP);
        BigDecimal roundedBalanceTot = balanceTot.setScale(2, RoundingMode.HALF_UP);

        if (studentBalance.abs().compareTo(roundedBalanceTot.abs()) == 0) {
            colorBalance = "text-dark";
        }  
%>
        </tbody>
        <tfooter>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td><b>Total:</b> </td>
                <td class="text-end <%=colorBalance%>"><b><%=df.format(balanceTot.abs())%></b></td>
                <td></td>
            </tr>
        </tfooter>
    </table>
    <div class="alert alert-warning d-flex align-items-center">
<%  if(student.getDc()!=null){
        if(student.getDc().equals("DR")){%>
        <h3>Balance to pay:&nbsp;<%=studentBalance%>&nbsp;<%=student.getDc()==null?"":student.getDc()%></h3>
<%      }else if(student.getDc().equals("CR")){%>
        <h3>Balance:&nbsp;<%=studentBalance%>&nbsp;<%=student.getDc()==null?"":student.getDc()%></h3>
<%      }
    }else{
%>
        <h3>Balance:&nbsp;<%=studentBalance%></h3>
<%  }%>
    </div>
<% } %>
</div>
</body>
<script>
    jQuery('#FInicio').datepicker();
	jQuery('#FFin').datepicker();
</script>
</html>
