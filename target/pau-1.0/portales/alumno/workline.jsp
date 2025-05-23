<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%> 
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Locale"%>
<%@page import="aca.trabajo.spring.TrabAlum"%>
<%@page import="aca.trabajo.spring.TrabPeriodo"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.trabajo.spring.TrabInformeAlum"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%@ include file= "menu.jsp" %>
<body>
<%
	String matricula 			= (String) session.getAttribute("codigoAlumno");
	String nombreAlumno 		= (String) request.getAttribute("nombreAlumno");

    TrabAlum trabAlum                   = (TrabAlum) request.getAttribute("trabAlum");

// Check if student has CTP assigned
if((trabAlum.getMatricula().equals(matricula))){
    TrabPeriodo trabPeriodo             = (TrabPeriodo) request.getAttribute("trabPeriodo");
    Carga carga                         = (Carga) request.getAttribute("carga");

    List<TrabInformeAlum> lisTrabInformeAlum    = (List<TrabInformeAlum>) request.getAttribute("lisTrabInformeAlum");

    HashMap<String,String> mapaNombreDepartamentos  = (HashMap<String,String>) request.getAttribute("mapaNombreDepartamentos");
    HashMap<String,String> mapaNombreCategorias     = (HashMap<String,String>) request.getAttribute("mapaNombreCategorias");
    HashMap<String,String> mapaNombrePeriodos       = (HashMap<String,String>) request.getAttribute("mapaNombrePeriodos");
    HashMap<String,String> mapaPeriodoHoras         = (HashMap<String,String>) request.getAttribute("mapaPeriodoHoras");
    HashMap<String,String> mapaPeriodoHorasTotales  = (HashMap<String,String>) request.getAttribute("mapaPeriodoHorasTotales");
    HashMap<String,String> mapaHorasPorSemana       = (HashMap<String,String>) request.getAttribute("mapaHorasPorSemana");
    
    // Minimum hours required per week
    int defaultWeekMin = 8;

    // Calculates hours completed per period
    String horasAlcanzadasPeriodo = "0";
    String horasAclanzadasTotPeriodo = "0";
    if(mapaPeriodoHoras.containsKey(trabAlum.getPeriodoId())){
        horasAlcanzadasPeriodo = mapaPeriodoHoras.get(trabAlum.getPeriodoId());
        horasAclanzadasTotPeriodo = mapaPeriodoHorasTotales.get(trabAlum.getPeriodoId());
    }

    // Calculates outstanding & surplus hours per period
    int horasAsignadas = Integer.parseInt(trabAlum.getHoras()==null?"0":trabAlum.getHoras());
    float horasRestantes = horasAsignadas - Float.parseFloat(horasAlcanzadasPeriodo);
    float horasSobrantes = Float.parseFloat(horasAclanzadasTotPeriodo) - Float.parseFloat(horasAlcanzadasPeriodo);

    // Gets start & end date of CTP period
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date fInicoDate = sdf.parse(trabPeriodo.getFechaIni()==null?"01/01/2000":trabPeriodo.getFechaIni()); // Start date of the period
    Date fFinalDate = sdf.parse(trabPeriodo.getFechaFin()==null?"01/01/2000":trabPeriodo.getFechaFin()); // End date of the period

    // Initialize calendar based on period start.
    Calendar startCal = Calendar.getInstance();
    startCal.setTime(fInicoDate);

    Calendar currCal = Calendar.getInstance();
%>
<div class="container-fluid">
	<h3>
		CTP Reports <small class="text-muted fs-6">( <%=matricula%> - <%=nombreAlumno%> )</small>
	</h3>
	<div class="alert alert-info d-flex align-items-center">
        <a href="registrarHoras" class="btn btn-primary mx-3">Create Report</a>	
        <label class="mx-1">Assigned Hours in current period: </label>
        <b class="me-3"><%=horasAsignadas%></b>
        <label class="mx-1">Completed Hours in current period: </label>
        <b class="me-3"><%=horasAlcanzadasPeriodo%></b>
        <label class="mx-1">Outstanding Hours in current period: </label>
        <b class="me-3"><%=horasRestantes%></b>
        <label class="mx-1">Surplus Hours in current period: </label>
        <b ><%=horasSobrantes%></b>
	</div>
    <table class="table table-bordered">
        <thead class="table-info">
            <tr>
                <th width="3%">No.</th>
                <th width="4%"></th>
                <th>Rep. ID</th>
                <th>Period</th>
                <th>Department</th>
                <th>Category</th>
                <th>Date</th>
                <th>Hours</th>
                <th width="30%">Desc.</th>
                <th>Status</th>
            </tr>
        </thead>
        <tbody>
<%      int row = 0;
        // Store current week's data
        int currentWeekNumber = -1;
        String currentWeekStartDate = "";

        for(TrabInformeAlum trabInforme : lisTrabInformeAlum){
            row++;
            String curInformeFecha = trabInforme.getFecha();

            if (curInformeFecha != null && !curInformeFecha.isEmpty()) {
                try {
                    // Parse the date consistently
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date reportDate = inputFormat.parse(curInformeFecha);     

                    // Set calendar to report date
                    currCal.setTime(reportDate);
                    
                    // Calculate week start date (Sunday)
                    String weekStartDate = getWeekStartDate(curInformeFecha);
                    
                    // Calculate week number
                    int weekNumber = calculateWeekNumber(startCal, currCal);
                
                    // Display week header if new week
                    if (currentWeekNumber != weekNumber) {
                        currentWeekNumber = weekNumber;
                        currentWeekStartDate = weekStartDate;

                        // Get completed & outstanding hours for the week
                        String weekHours = mapaHorasPorSemana.containsKey(matricula+weekStartDate) ? mapaHorasPorSemana.get(matricula+weekStartDate) : "0";
                        double weekOustanding = defaultWeekMin - Double.parseDouble(weekHours);
                        if (weekOustanding < 0) weekOustanding = 0;                         
%> 
            <tr class="table-secondary">
                <td colspan="10" class="text-center">
                    <div><strong> Week <%=weekNumber%></strong></div>
                    <div>
                        <small>Completed <span class="badge bg-success"><%=weekHours%></span></small>
                        <small>Outstanding <span class="badge bg-warning"><%=String.format("%.2f", weekOustanding)%></span></small>
                    </div>
                </td>
            </tr>
<%                  
                    }
                    // Format date for display
                    SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MMM-yy", Locale.US);
                    String formattedDate = displayFormat.format(reportDate).toUpperCase();

                    String nombreDept = "";
                    String nombreCat = "";
                    String nombrePeriodo = "";
                    if(mapaNombreDepartamentos.containsKey(trabInforme.getDeptId())){
                        nombreDept = mapaNombreDepartamentos.get(trabInforme.getDeptId());
                    }
                    if(mapaNombreCategorias.containsKey(trabInforme.getCatId())){
                        nombreCat = mapaNombreCategorias.get(trabInforme.getCatId());
                    }
                    if(mapaNombrePeriodos.containsKey(trabInforme.getPeriodoId())){
                        nombrePeriodo = mapaNombrePeriodos.get(trabInforme.getPeriodoId());
                    }
%>
            <tr>
                <td><%=row%></td>
                <td class="d-flex">
<%              if(trabInforme.getStatus().equals("S") || trabInforme.getStatus().equals("A")){ %>
                    <a class="mx-2" href="registrarHoras?InformeId=<%=trabInforme.getInformeId()%>"><i class="fas fa-edit"></i></a>
<%                  if(trabInforme.getStatus().equals("S")){ %>
                    <a href="eliminarHoras?InformeId=<%=trabInforme.getInformeId()%>"><i class="fas fa-trash text-danger"></i></a>
<%                  
                    }
                } 
%>
                </td>
                <td><%=trabInforme.getInformeId()%></td>
                <td><%=nombrePeriodo%></td>
                <td><%=nombreDept%></td>
                <td><%=nombreCat%></td>
                <td><%=formattedDate%></td>
                <td><%=trabInforme.getHoras()%></td>
                <td><%=trabInforme.getDescripcion()==null?"":trabInforme.getDescripcion()%></td>
                <td>
<%                  if(trabInforme.getStatus().equals("S")){ %>
                    Waiting for authorization...
<%                  } 
                    if(trabInforme.getStatus().equals("A")){ 
%>
                    Active
<%                  } 
                    if(trabInforme.getStatus().equals("F")){ 
%>
                    Waiting for approval
<%                  } 
                    if(trabInforme.getStatus().equals("C")){ 
%>
                    Closed
<%                  } 
%>
                </td>
            </tr>
<%      
                } catch (Exception e) {
                    System.err.println("Error processing report: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
%>
        </tbody>
    </table>
</div>
<%!
    // Helper method to calculate the week number relative to the start date
    private int calculateWeekNumber(Calendar startCal, Calendar currCal) {
        // Reset both calendars to Sunday at 00:00:00
        Calendar startSunday = (Calendar) startCal.clone();
        startSunday.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        startSunday.set(Calendar.HOUR_OF_DAY, 0);
        startSunday.set(Calendar.MINUTE, 0);
        startSunday.set(Calendar.SECOND, 0);
        startSunday.set(Calendar.MILLISECOND, 0);
        
        Calendar currentSunday = (Calendar) currCal.clone();
        currentSunday.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        currentSunday.set(Calendar.HOUR_OF_DAY, 0);
        currentSunday.set(Calendar.MINUTE, 0);
        currentSunday.set(Calendar.SECOND, 0);
        currentSunday.set(Calendar.MILLISECOND, 0);
        
        // Calculate full weeks between dates
        long diffMillis = currentSunday.getTimeInMillis() - startSunday.getTimeInMillis();
        int weekNumber = (int) (diffMillis / (7 * 24 * 60 * 60 * 1000)) + 1;
        
        // Ensure week number is at least 1
        return Math.max(1, weekNumber);
    }

    // Parses "dd-MM-yy HH:mm:ss" and returns "dd-MMM-yy" (uppercase month)
    private String getWeekStartDate(String dateTimeStr) throws Exception {
        try {
            // Step 1: Parse the original date string (e.g., "10-03-25 12:00:00")
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = inputFormat.parse(dateTimeStr);

            // Step 2: Adjust to Sunday of the week
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

            // Step 3: Format as "dd-MMM-yy" (uppercase month)
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MMM-yy", Locale.US);
            String formattedDate = outputFormat.format(cal.getTime()).toUpperCase();

            return formattedDate;
        } catch (Exception e) {
            // Log error for debugging
            System.err.println("Error parsing date: " + dateTimeStr);
            e.printStackTrace();
            return "01-JAN-00"; // Return default date on error
        }
    }
%>
<%
}else{
%>
<div class="container-fluid">
	<h3>
		CTP Reports <small class="text-muted fs-6">( <%=matricula%> - <%=nombreAlumno%> )</small>
	</h3>
    <div class="alert alert-warning">
        No CTP Assigned. Contact your CTP Coordinator for assistance. 
    </div>
</div>
<%
}
%>
</body>