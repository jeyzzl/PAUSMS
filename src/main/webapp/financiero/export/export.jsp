<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.financiero.spring.FinQuote"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.catalogo.spring.CatPeriodo"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%
    String periodoId = (String)request.getAttribute("periodoId");
    String semester = (String)request.getAttribute("semester");

    List<CatPeriodo> lisPeriodos = (List<CatPeriodo>)request.getAttribute("lisPeriodos");
    List<FinQuote> lisQuotes = (List<FinQuote>)request.getAttribute("lisQuotes");

    HashMap<String,AlumPersonal> mapAlumPersonal    = (HashMap<String,AlumPersonal>)request.getAttribute("mapAlumPersonal");
    HashMap<String,String> mapSubjectsPerQuote      = (HashMap<String,String>)request.getAttribute("mapSubjectsPerQuote");
    HashMap<String,String> mapFeesPerQuote          = (HashMap<String,String>)request.getAttribute("mapFeesPerQuote");
%>
<script type="text/javascript" >
	function cambioPeriodo(){
		var periodoId = document.getElementById("PeriodoId").value;
        var semester = document.getElementById("Semester").value;
		location.href = "export?PeriodoId="+periodoId+"&Semester="+semester;
	}
</script>
<body>
<div class="container-fluid">
    <h2>Export Quotation Orders</h2>    
    <form name="frmOrders" action="generate" method="post">
    <div class="alert alert-info d-flex align-items-center">
        Period: 
        <select class="form-select mx-2" style="width:25rem;" name="PeriodoId" id="PeriodoId" onchange="javacsript:cambioPeriodo();">
<%      for(CatPeriodo periodo : lisPeriodos){%>
            <option value="<%=periodo.getPeriodoId()%>" <%=periodo.getPeriodoId().equals(periodoId)?"selected":""%>><%=periodo.getPeriodoId()%> | <%=periodo.getNombre()%></option>
<%      }%> 
        </select>
        Semester:
        <select class="form-select mx-2" style="width:8rem;" name="Semester" id="Semester" onchange="javacsript:cambioPeriodo();">
            <option value="1" <%=semester.equals("1")?"selected":""%>>1</option>
            <option value="2" <%=semester.equals("2")?"selected":""%>>2</option>
        </select>
        <input class="btn btn-success" type="submit" value="Export">
    </div>
    <table class="table table-sm table-bordered">
        <thead class="table-info">
            <th width="5%"><spring:message code="aca.Numero"/></th>
            <th width="5%">Op.</th>
            <th width="10%">Student ID</th>
            <th width="8%">Quote ID</th>
            <th width="20%">Name</th>
            <th>Description</th>
            <th width="8%">Subjects</th>
            <th width="8%">Fees</th>
        </thead>
<%           
    double gTotal = 0;
    if(lisQuotes != null){ 
%>
        <tbody>
<%      
        int row = 0;
        for(FinQuote quote : lisQuotes){
            row++;
            String nombreAlumno = "";
            if(mapAlumPersonal.containsKey(quote.getCodigoPersonal())){
                nombreAlumno =  (mapAlumPersonal.get(quote.getCodigoPersonal()).getApellidoMaterno()==null?"":mapAlumPersonal.get(quote.getCodigoPersonal()).getApellidoMaterno()) +" "+
                                (mapAlumPersonal.get(quote.getCodigoPersonal()).getApellidoPaterno()==null?"":mapAlumPersonal.get(quote.getCodigoPersonal()).getApellidoPaterno()) +" "+
                                mapAlumPersonal.get(quote.getCodigoPersonal()).getNombre();
            }

            String numSubjects = "0";
            String numFees = "0";

            if(mapSubjectsPerQuote.containsKey(String.valueOf(quote.getQuoteId()))) numSubjects = mapSubjectsPerQuote.get(String.valueOf(quote.getQuoteId()));
            if(mapFeesPerQuote.containsKey(String.valueOf(quote.getQuoteId()))) numFees = mapFeesPerQuote.get(String.valueOf(quote.getQuoteId()));
%>
            <tr>
                <td><%=row%></td>
                <td class="text-center">
                    <input class="form-check-input" type="checkbox" value="<%=quote.getCodigoPersonal()%>" id="<%=quote.getCodigoPersonal()%>" name="<%=quote.getCodigoPersonal()%>">
                </td>
                <td><%=quote.getCodigoPersonal()%></td>
                <td><%=quote.getQuoteId()%></td>
                <td><%=nombreAlumno%></td>
                <td><%=quote.getDescription()%></td>
                <td><%=numSubjects%></td>
                <td><%=numFees%></td>
            </tr>
<%      }%>        
        </tbody>
<%  }%>
        </form>
    </table>
</div>
</body>