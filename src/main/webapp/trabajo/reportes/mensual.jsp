<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.trabajo.spring.TrabDepartamento"%>
<%@ page import="aca.trabajo.spring.TrabCategoria"%>
<%@ page import="aca.trabajo.spring.TrabPeriodo"%>
<%@ page import="aca.trabajo.spring.TrabAlum"%>
<%@ page import="aca.alumno.spring.AlumAcademico"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.alumno.spring.AlumPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script>
    function cambio(){
		var periodoId = document.getElementById("PeriodoId").value;
        var deptId = document.getElementById("DeptId").value;
        var catId = document.getElementById("CatId").value;
        var grado = document.getElementById("Grado").value;
        var mes = document.getElementById("Mes").value;
		location.href = "mensual?PeriodoId="+periodoId+"&DeptId="+deptId+"&CatId="+catId+"&Grado="+grado+"&Mes="+mes;
	}
</script>

<%
    String periodoId                = (String) request.getAttribute("periodoId");
    String deptId                   = (String) request.getAttribute("deptId");
    String catId                    = (String) request.getAttribute("catId");
    String grado                    = (String) request.getAttribute("grado");
    String mes                      = (String) request.getAttribute("mes");
    String periodoYear              = (String) request.getAttribute("periodoYear");

    List<TrabAlum> lisAlumnos                   = (List<TrabAlum>) request.getAttribute("lisAlumnos");
    List<TrabPeriodo> lisPeriodos               = (List<TrabPeriodo>) request.getAttribute("lisPeriodos");
    List<TrabDepartamento> lisDepartamentos     = (List<TrabDepartamento>) request.getAttribute("lisDepartamentos");
    List<TrabCategoria> lisCategorias           = (List<TrabCategoria>) request.getAttribute("lisCategorias");

    HashMap<String,String> mapaHorasPorMes           = (HashMap<String,String>) request.getAttribute("mapaHorasPorMes");
    HashMap<String,String> mapaHorasTotalesPorMes    = (HashMap<String,String>) request.getAttribute("mapaHorasTotalesPorMes");
    HashMap<String,AlumPersonal> mapaAlumnos            = (HashMap<String,AlumPersonal>)request.getAttribute("mapaAlumnos");
    HashMap<String,AlumAcademico> mapaAcademico         = (HashMap<String,AlumAcademico>)request.getAttribute("mapaAcademico");
    HashMap<String,AlumPlan> mapaAlumPlan               = (HashMap<String,AlumPlan>)request.getAttribute("mapaAlumPlan");
    HashMap<String,String> mapCategorias                = (HashMap<String,String>)request.getAttribute("mapCategorias");
    HashMap<String,String> mapaPlanes                   = (HashMap<String,String>)request.getAttribute("mapaPlanes");
%>

<div class="container-fluid">
    <h2>Monthly Hour Report</h2>	
    <div class="alert alert-info d-flex align-items-center">
        Period:
	    <select name="PeriodoId" id="PeriodoId" style="width:15rem;" class="form-select mx-2" onchange="javascript:cambio();">
<%
	for(TrabPeriodo periodo: lisPeriodos){
%>
		    <option value="<%=periodo.getPeriodoId()%>" <%=periodo.getPeriodoId().equals(periodoId)?"selected":""%>><%=periodo.getNombrePeriodo() %></option>
<%  }   %>
	    </select>
        Department:
        <select name="DeptId" id="DeptId" style="width:15rem;" class="form-select mx-2" onchange="javascript:cambio();">
            <option value="0" <%=deptId.equals("0")?"selected":""%>>All</option>    
<%
	for(TrabDepartamento dept: lisDepartamentos){
%>
		    <option value="<%=dept.getDeptId()%>" <%=dept.getDeptId().equals(deptId)?"selected":""%>><%=dept.getNombre() %></option>
<%  }   %>   
        </select>
        Category:
        <select name="CatId" id="CatId" style="width:15rem;" class="form-select mx-2" onchange="javascript:cambio();">
            <option value="0" <%=catId.equals("0")?"selected":""%>>All</option>
<%
	for(TrabCategoria cat: lisCategorias){
%>
		    <option value="<%=cat.getCategoriaId()%>" <%=cat.getCategoriaId().equals(catId)?"selected":""%>><%=cat.getNombreCategoria() %></option>
<%  }   %>   
        </select>
        Year:
		<select name="Grado" id="Grado" style="width:150px;" class="form-select mx-2" onchange="javascript:cambio();">
            <option value="0" <%=grado.equals("0")?"selected":""%>>All</option>
            <option value="1" <%=grado.equals("1")?"selected":""%>>1</option>
            <option value="2" <%=grado.equals("2")?"selected":""%>>2</option>
            <option value="3" <%=grado.equals("3")?"selected":""%>>3</option>
            <option value="4" <%=grado.equals("4")?"selected":""%>>4</option>
		</select>
        Month:
		<select name="Mes" id="Mes" style="width:150px;" class="form-select mx-2" onchange="javascript:cambio();">
            <option value="01" <%=mes.equals("01")?"selected":""%>>JANUARY</option>
            <option value="02" <%=mes.equals("02")?"selected":""%>>FEBRUARY</option>
            <option value="03" <%=mes.equals("03")?"selected":""%>>MARCH</option>
            <option value="04" <%=mes.equals("04")?"selected":""%>>APRIL</option>
            <option value="05" <%=mes.equals("05")?"selected":""%>>MAY</option>
            <option value="06" <%=mes.equals("06")?"selected":""%>>JUNE</option>
            <option value="07" <%=mes.equals("07")?"selected":""%>>JULY</option>
            <option value="08" <%=mes.equals("08")?"selected":""%>>AUGUST</option>
            <option value="09" <%=mes.equals("09")?"selected":""%>>SEPTEMBER</option>
            <option value="10" <%=mes.equals("10")?"selected":""%>>OCTOBER</option>
            <option value="11" <%=mes.equals("11")?"selected":""%>>NOVEMBER</option>
            <option value="12" <%=mes.equals("12")?"selected":""%>>DECEMBER</option>
		</select>
    </div>
    <table class="table table-bordered">
        <thead class="table-info">
            <tr>
                <th>No.</th>
                <th>Student ID</th>
                <th>Name</th>
                <th>Course</th>
                <th>Gender</th>
                <th>Residence</th>
                <th>Category</th>
                <th>Hours Completed</th>
                <th>Monthly Surplus</th>
            </tr>    
        </thead>
        <tbody>
<%      int count = 0;
        for(TrabAlum alum : lisAlumnos){
            String nombreAlum = "";
            String colorOut = "";
            String colorTmi = "";
            if(mapaAlumnos.containsKey(alum.getMatricula())){
                nombreAlum = mapaAlumnos.get(alum.getMatricula()).getNombre()+" "+mapaAlumnos.get(alum.getMatricula()).getApellidoMaterno()+" "+mapaAlumnos.get(alum.getMatricula()).getApellidoPaterno();
            }
            
            AlumPersonal alumPersonal =  new AlumPersonal();
            if(mapaAlumnos.containsKey(alum.getMatricula())){
                alumPersonal = mapaAlumnos.get(alum.getMatricula());
            }

            String gender = alumPersonal.getSexo().equals("M")?"Male":"Female";

            AlumAcademico alumAcademico =  new AlumAcademico();
            if(mapaAcademico.containsKey(alum.getMatricula())){
                alumAcademico = mapaAcademico.get(alum.getMatricula());
            }

            String residence = alumAcademico.getResidenciaId().equals("I")?"Boarding":"Day";

            AlumPlan alumPlan =  new AlumPlan();
            if(mapaAlumPlan.containsKey(alum.getMatricula())){
                alumPlan = mapaAlumPlan.get(alum.getMatricula());
            }

            String nombrePlan = "";
            if(mapaPlanes.containsKey(alumPlan.getPlanId())){
                nombrePlan = mapaPlanes.get(alumPlan.getPlanId());
            }

            if(mapaHorasPorMes.containsKey(alum.getMatricula()+"-"+periodoYear+"-"+mes)){
                count++;

                // Obtiene horas
                Double horas = Double.parseDouble(mapaHorasPorMes.get(alum.getMatricula()+"-"+periodoYear+"-"+mes));
                Double horasTot =  Double.parseDouble(mapaHorasTotalesPorMes.get(alum.getMatricula()+"-"+periodoYear+"-"+mes));

                // Calcula horas sobrantes
                Double tmi = horasTot - horas;
                if(tmi < 0) tmi = 0.0;

                // Color dependiendo de horas faltantes y sobrantes
                if(tmi > 0) colorTmi = "table-success";

                // Formatea datos
                String sTmi = (String) String.format("%.2f", tmi);
%>    
            <tr class="<%=colorOut%>">
                <td><%=count%></td>
                <td><%=alum.getMatricula()%></td>
                <td><%=nombreAlum%></td>
                <td><%=nombrePlan%></td>
                <td><%=gender%></td>
                <td><%=residence%></td>
                <td><%=mapCategorias.get(alum.getCatId())%></td>
                <td><%=horas%></td>
                <td class="<%=colorTmi%>"><%=sTmi%></td>
            </tr>
<%          }else {  
                count++;
%>
            <tr class="<%=colorOut%>">
                <td><%=count%></td>
                <td><%=alum.getMatricula()%></td>
                <td><%=nombreAlum%></td>
                <td><%=nombrePlan%></td>
                <td><%=gender%></td>
                <td><%=residence%></td>
                <td><%=mapCategorias.get(alum.getCatId())%></td>
                <td>0</td>
                <td class="<%=colorTmi%>">0</td>
            </tr>
<%          }
        }
%>
</div>