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
        var semana = document.getElementById("Semana").value;
		location.href = "semanal?PeriodoId="+periodoId+"&DeptId="+deptId+"&CatId="+catId+"&Grado="+grado+"&Semana="+semana;
	}
</script>

<%
    String periodoId                = (String) request.getAttribute("periodoId");
    String deptId                   = (String) request.getAttribute("deptId");
    String catId                    = (String) request.getAttribute("catId");
    String grado                    = (String) request.getAttribute("grado");
    String semana                   = (String) request.getAttribute("semana");

    List<TrabAlum> lisAlumnos                   = (List<TrabAlum>) request.getAttribute("lisAlumnos");
    List<TrabPeriodo> lisPeriodos               = (List<TrabPeriodo>) request.getAttribute("lisPeriodos");
    List<TrabDepartamento> lisDepartamentos     = (List<TrabDepartamento>) request.getAttribute("lisDepartamentos");
    List<TrabCategoria> lisCategorias           = (List<TrabCategoria>) request.getAttribute("lisCategorias");
    List<String> semanasDisponibles             = (List<String>) request.getAttribute("semanasDisponibles");

    HashMap<String,String> mapaHorasPorSemana           = (HashMap<String,String>) request.getAttribute("mapaHorasPorSemana");
    HashMap<String,String> mapaHorasTotalesPorSemana    = (HashMap<String,String>) request.getAttribute("mapaHorasTotalesPorSemana");
    HashMap<String,AlumPersonal> mapaAlumnos            = (HashMap<String,AlumPersonal>)request.getAttribute("mapaAlumnos");
    HashMap<String,AlumAcademico> mapaAcademico         = (HashMap<String,AlumAcademico>)request.getAttribute("mapaAcademico");
    HashMap<String,AlumPlan> mapaAlumPlan               = (HashMap<String,AlumPlan>)request.getAttribute("mapaAlumPlan");
    HashMap<String,String> mapCategorias                = (HashMap<String,String>)request.getAttribute("mapCategorias");
    HashMap<String,String> mapaPlanes                   = (HashMap<String,String>)request.getAttribute("mapaPlanes");
%>
<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<div class="container-fluid">
    <h2>Weekly Hour Report</h2>	
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
        Week:
        <select name="Semana" id="Semana" style="width:15rem;" class="form-select mx-2" onchange="javascript:cambio();">
            <% for(String week : semanasDisponibles) { %>
                <option value="<%=week.split(" to ")[0]%>" <%=week.split(" to ")[0].equals(semana)?"selected":""%>>
                    <%= week %>
                </option>
            <% } %>
        </select>
    </div>
    <table class="table table-bordered">
        <thead class="table-info">
            <tr>
                <th>No.</th>
                <th>Student ID</th>
                <th >Name</th>
                <th>Course</th>
                <th>Gender</th>
                <th>Residence</th>
                <th>Category</th>
                <th>Hours Completed</th>
                <th>Weekly Deficit</th>
                <th>Weekly Surplus</th>
            </tr>    
        </thead>
        <tbody>
<%      int count = 0;
        int weeklyMin = 8;
        for(TrabAlum alum : lisAlumnos){
            count++;
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

            if(mapaHorasPorSemana.containsKey(alum.getMatricula()+semana)){

                // Obtiene horas
                Double horas = Double.parseDouble(mapaHorasPorSemana.get(alum.getMatricula()+semana));
                Double horasTot =  Double.parseDouble(mapaHorasTotalesPorSemana.get(alum.getMatricula()+semana));

                // Calcula horas faltantes
                Double outstanding = weeklyMin - horas;
                if(outstanding < 0 ) outstanding = 0.0;

                // Calcula horas sobrantes
                Double tmi = horasTot - horas;
                if(tmi < 0) tmi = 0.0;

                // Color dependiendo de horas faltantes y sobrantes
                if(outstanding > 0) colorOut = "table-warning";
                if(tmi > 0) colorTmi = "table-success";

                // Formatea datos
                String sOutstanding = (String) String.format("%.2f", outstanding);
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
            <td><%=sOutstanding%></td>
            <td class="<%=colorTmi%>"><%=sTmi%></td>
        </tr>
<%          }else { %>
        <tr class="table-danger">
            <td><%=count%></td>
            <td><%=alum.getMatricula()%></td>
            <td><%=nombreAlum%></td>
            <td><%=nombrePlan%></td>
            <td><%=gender%></td>
            <td><%=residence%></td>
            <td><%=mapCategorias.get(alum.getCatId())%></td>
            <td>0</td>
            <td>8</td>
            <td>0</td>
        </tr>
<%          }
        }
%>
        </tbody>
    </table>
</div>