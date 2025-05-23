<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="java.util.Locale"%>
<%@ page import="aca.financiero.spring.FinConcept"%>
<%@ page import="aca.financiero.spring.FinQuote"%>
<%@ page import="aca.financiero.spring.FinGroup"%>
<%@ page import="aca.financiero.spring.FinQuoteConcept"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.alumno.spring.AlumAcademico"%>
<%@ page import="aca.alumno.spring.AlumPlan"%>
<%@ page import="aca.alumno.spring.AlumUbicacion"%>
<%@ page import="aca.catalogo.spring.CatRecogida"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<%
    String periodoId                = (String)request.getAttribute("periodoId");
    String quoteId                  = (String)request.getAttribute("quoteId");
    String codigoAlumno             = (String)session.getAttribute("codigoAlumno");

    AlumPersonal alumPersonal       = (AlumPersonal)request.getAttribute("alumPersonal");
    AlumAcademico alumAcademico     = (AlumAcademico)request.getAttribute("alumAcademico");
    AlumPlan alumPlan               = (AlumPlan)request.getAttribute("alumPlan");
    AlumUbicacion alumUbicacion     = (AlumUbicacion)request.getAttribute("alumUbicacion");

    List<FinConcept> lisConcepts    = (List<FinConcept>)request.getAttribute("lisConcepts");
    List<FinGroup> lisGroups        = (List<FinGroup>)request.getAttribute("lisGroups");
    List<FinQuoteConcept> lisQuoteConcepts        = (List<FinQuoteConcept>)request.getAttribute("lisQuoteConcepts");

    HashMap<Integer, FinConcept> mapConcepts = (HashMap<Integer, FinConcept>)request.getAttribute("mapConcepts");
    HashMap<String,CatRecogida> mapRecogida = (HashMap<String,CatRecogida>)request.getAttribute("mapRecogida");

    String descuento = (String)request.getAttribute("descuento");

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
    <h2>Quote Concepts <small class="text-muted fs-5">( <%=codigoAlumno%> | <%=nombreAlumno%> | <%=alumPlan.getPlanId()%> | <%=residencia%> | Y<%=alumAcademico.getGrado()%> | <%=estadoCivil%> | <%=recogida%>)</small></h2>
    <div class="alert alert-info d-flex align-items-center">
        <a class="btn btn-primary me-3" href="quotes?PeriodoId=<%=periodoId%>">Return</a>
    <form action="agregar" method="post" name="frmQuoteConc" class="d-flex align-items-center">
        <input type="hidden" value="<%=periodoId%>" id="PeriodoId" name="PeriodoId">
        <input type="hidden" value="<%=quoteId%>" id="QuoteId" name="QuoteId">
        Concepts: 
        <select class="form-select mx-2" style="width:25rem;" name="ConcId" id="ConcId">
<%  for(FinConcept concept : lisConcepts){%>
            <option value="<%=concept.getConcId()%>"><%=concept.getConcId()%> | <%=concept.getName()%> | <%=currencyFormat.format(concept.getUnitCost())%></option>
<%  }%>   
        </select>
        No. Units:
        <input class="input form-control ms-1 me-2" name="NoUnits" type="text" id="NoUnits" style="width:5rem;" value="0">
        <input class="btn btn-success me-4" type="submit" value="Add">
    </form>
    <form action="agregarG" method="post" name="frmQuoteGroup" class="d-flex align-items-center">
        <input type="hidden" value="<%=periodoId%>" id="PeriodoId" name="PeriodoId">
        <input type="hidden" value="<%=quoteId%>" id="QuoteId" name="QuoteId">
        Groups: 
        <select class="form-select mx-2" style="width:25rem;" name="GroupId" id="GroupId">
<%  for(FinGroup group : lisGroups){%>
            <option value="<%=group.getGroupId()%>"><%=group.getGroupId()%> | <%=group.getName()%></option>
<%  }%>
        </select>
        <input class="btn btn-success me-4" type="submit" value="Add">
    </form>
<%  if(mensaje != null && !mensaje.equals("-")){%>
        <%=mensaje%>
<%  }%>
    </div>
    <table class="table table-sm table-bordered">
        <thead class="table-info">
            <th width="5%">Op.</th>
            <th width="5%"><spring:message code="aca.Numero"/></th>
            <th width="8%">Concept ID</th>
            <th>Name</th>
            <th width="10%">Unit Cost</th>
            <th width="10%">No. Units</th>
            <th width="10%">Disc. Amount</th>
            <th width="10%">Amount</th>
        </thead>
<%  if(lisQuoteConcepts != null){ %>
        <tbody>
<%  
        int row = 0;
        double totalFees = 0, totalSub = 0, totalSubDisc = 0, total = 0;
        for(FinQuoteConcept quoteConcept : lisQuoteConcepts){
            row++;

            FinConcept concept = new FinConcept();
            if(mapConcepts.containsKey(quoteConcept.getConcId())) concept = mapConcepts.get(quoteConcept.getConcId());

            if(concept.getType().equals("F")) totalFees += quoteConcept.getAmount();
            if(concept.getType().equals("S")) {
                totalSub += quoteConcept.getAmount();
                totalSubDisc += quoteConcept.getDiscountAmt();
            }
            
%>
            <tr>
                <td>
                    <a href="remover?PeriodoId=<%=periodoId%>&QuoteId=<%=quoteId%>&ConcId=<%=quoteConcept.getConcId()%>"><i class="fas fa-trash text-danger"></i></a>
                </td>
                <td><%=row%></td>
                <td><%=quoteConcept.getConcId()%></td>
                <td><%=concept.getName()%></td>
                <td><%=currencyFormat.format(concept.getUnitCost()).replace('$','K')%></td>
                <td><%=quoteConcept.getNoUnits()%></td>
                <td><%=currencyFormat.format(quoteConcept.getDiscountAmt()).replace('$','K')%></td>
                <td><%=currencyFormat.format(quoteConcept.getAmount() - quoteConcept.getDiscountAmt()).replace('$','K')%></td>
            </tr>
<%      
        }
        // Calculate total
        total = totalFees + (totalSub - totalSubDisc);     
%>
        </tbody>
    </table>
    <div class="alert alert-warning align-items-center d-flex justify-content-end py-2">
        <h4 class="me-2">Fees:</h4>
        <h5><%=currencyFormat.format(totalFees).replace('$','K')%></h5>
    </div>
    <div class="alert alert-warning align-items-center d-flex justify-content-end py-2">
        <h4 class="me-2">Tuition:</h4>
        <h5><%=currencyFormat.format(totalSub - totalSubDisc).replace('$','K')%></h5>
    </div>
    <div class="alert alert-success align-items-center d-flex justify-content-end py-1">
        <h4 class="me-2">Tuition Discount:</h4>
        <h5>
            <%-- <%=currencyFormat.format(totalDisc)%>  --%>
            <%=currencyFormat.format(totalSubDisc).replace('$','K')%> (<%=descuento%>%)
        </h5>
    </div>
    <div class="alert alert-secondary align-items-center d-flex justify-content-end">
        <h4 class="me-2">Net Total:</h4>
        <h5><%=currencyFormat.format(total).replace('$','K')%></h5>
    </div>
<%  } %>
</div>
</body>