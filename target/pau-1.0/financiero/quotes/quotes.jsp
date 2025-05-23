<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="java.util.Locale"%>
<%@ page import="aca.financiero.spring.FinQuote"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.alumno.spring.AlumAcademico"%>
<%@ page import="aca.alumno.spring.AlumPlan"%>
<%@ page import="aca.alumno.spring.AlumUbicacion"%>
<%@ page import="aca.catalogo.spring.CatPeriodo"%>
<%@ page import="aca.catalogo.spring.CatRecogida"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript" >
	function cambioPeriodo(){
		var periodoId = document.getElementById("PeriodoId").value;
		location.href = "quotes?PeriodoId="+periodoId;
	}
</script>

<%
    String codigoAlumno             = (String)request.getAttribute("codigoAlumno");
    String periodoId                = (String)request.getAttribute("periodoId");

    AlumPersonal alumPersonal       = (AlumPersonal)request.getAttribute("alumPersonal");
    AlumAcademico alumAcademico     = (AlumAcademico)request.getAttribute("alumAcademico");
    AlumPlan alumPlan               = (AlumPlan)request.getAttribute("alumPlan");
    AlumUbicacion alumUbicacion     = (AlumUbicacion)request.getAttribute("alumUbicacion");

    List<CatPeriodo> lisPeriodos    = (List<CatPeriodo>)request.getAttribute("lisPeriodos");
    List<FinQuote> lisQuotes        = (List<FinQuote>)request.getAttribute("lisQuotes");

    HashMap<String,String> mapFeesPerQuote = (HashMap<String,String>)request.getAttribute("mapFeesPerQuote");
    HashMap<String,String> mapDescPerQuote = (HashMap<String,String>)request.getAttribute("mapDescPerQuote");

    HashMap<String,CatRecogida> mapRecogida = (HashMap<String,CatRecogida>)request.getAttribute("mapRecogida");

    String mensaje  = request.getParameter("Mensaje")==null?"-": request.getParameter("Mensaje");

    String nombreAlumno = alumPersonal.getNombre()+" "+(alumPersonal.getApellidoMaterno()==null?"":alumPersonal.getApellidoMaterno())+" "+(alumPersonal.getApellidoPaterno()==null?"":alumPersonal.getApellidoPaterno());
    String residencia = alumAcademico.getResidenciaId().equals("I")?"Boarding":"Day Student";
    String estadoCivil = "";
    if(alumPersonal.getEstadoCivil().equals("C")) estadoCivil = "Married";
    if(alumPersonal.getEstadoCivil().equals("D")) estadoCivil = "Divorced";
    if(alumPersonal.getEstadoCivil().equals("S")) estadoCivil = "Single";
    if(alumPersonal.getEstadoCivil().equals("V")) estadoCivil = "Widowed";

    String recogida = "-";
    if(mapRecogida.containsKey(alumUbicacion.getRecogidaId())) recogida = mapRecogida.get(alumUbicacion.getRecogidaId()).getNombre();
    
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
%>
<body>
<div class="container-fluid">
    <h2>Student Quotes <small class="text-muted fs-5">( <%=codigoAlumno%> | <%=nombreAlumno%> | <%=alumPlan.getPlanId()%> | <%=residencia%> | Y<%=alumAcademico.getGrado()%> | <%=estadoCivil%> | <%=recogida%>)</small></h2>
    <div class="alert alert-info d-flex align-items-center">
        Period:
        <select class="form-select mx-2" style="width:20rem;" id="PeriodoId" name="PeriodoId" onchange="javacsript:cambioPeriodo();">
<%  for(CatPeriodo periodo : lisPeriodos){%>
            <option value="<%=periodo.getPeriodoId()%>" <%=periodoId.equals(periodo.getPeriodoId())?"selected":""%> ><%=periodo.getPeriodoId()%> | <%=periodo.getNombre()%></option>
<%  }%>
        </select>
        <a class="btn btn-primary me-2" href="editar?PeriodoId=<%=periodoId%>"><i class="fas fa-plus"></i> Quote</a>
<%  if(mensaje != null && !mensaje.equals("-")){%>
        <%=mensaje%>
<%  }%>
    </div>
    <table class="table table-sm table-bordered">
        <thead class="table-info">
            <th width="5%">Op.</th>
            <th width="5%"><spring:message code="aca.Numero"/></th>
            <th width="8%">Quote ID</th>
            <th width="20%">Date</th>
            <th width="8%">Status</th>
            <th>Desc.</th>
            <th width="10%">T. Fees</th>
            <th width="10%">T. Disc.</th>
            <th width="10%">T. Net.</th>
        </thead>
<%           
    double gTotal = 0;
    if(lisQuotes != null){ %>
        <tbody>
<%  
        int row = 0;
        for(FinQuote quote : lisQuotes){
            row++;

            double totalFees = 0, totalDisc = 0, total = 0;
            if(mapFeesPerQuote.containsKey(String.valueOf(quote.getQuoteId()))) totalFees = Double.parseDouble(mapFeesPerQuote.get(String.valueOf(quote.getQuoteId())));
            if(mapDescPerQuote.containsKey(String.valueOf(quote.getQuoteId()))) totalDisc = Double.parseDouble(mapDescPerQuote.get(String.valueOf(quote.getQuoteId())));
            total = totalFees - totalDisc;
            gTotal += total;
%>
            <tr>
                <td>
                    <a href="quote?QuoteId=<%=quote.getQuoteId()%>&PeriodoId=<%=periodoId%>"><i class="fas fa-plus-square text-success"></i></a>
                    <a href="editar?QuoteId=<%=quote.getQuoteId()%>&PeriodoId=<%=periodoId%>"><i class="fas fa-edit"></i></a>
                    <a href="eliminar?QuoteId=<%=quote.getQuoteId()%>&PeriodoId=<%=periodoId%>"><i class="fas fa-trash text-danger"></i></a>
                </td>
                <td><%=row%></td>
                <td><%=quote.getQuoteId()%></td>
                <td><%=quote.getFecha()%></td>
                <td><%=quote.getStatus().equals("A")?"Active":"Inactive"%></td>
                <td><%=quote.getDescription()%></td>
                <td><%=currencyFormat.format(totalFees)%></td>
                <td><%=currencyFormat.format(totalDisc)%></td>
                <td><%=currencyFormat.format(total)%></td>
            </tr>
<%      }%>
        </tbody>
<%  } %>
    </table>
    <div class="alert alert-secondary align-items-center d-flex justify-content-end">
        <h3 class="me-3">Net Total:</h3>
        <h4><%=currencyFormat.format(gTotal)%></h4>
    </div>
</div>
</body>