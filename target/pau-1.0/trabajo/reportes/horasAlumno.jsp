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
    function cambioPeriodo(){
		var periodoId = document.getElementById("PeriodoId").value;
        var deptId = document.getElementById("DeptId").value;
        var catId = document.getElementById("CatId").value;
        var grado = document.getElementById("Grado").value;

		location.href = "horasAlumno?PeriodoId="+periodoId+"&DeptId="+deptId+"&CatId="+catId+"&Grado="+grado;
	}
</script>
<%
    String periodoId                = (String) request.getAttribute("periodoId");
    String deptId                   = (String) request.getAttribute("deptId");
    String catId                    = (String) request.getAttribute("catId");
    String grado                    = (String) request.getAttribute("grado");

    List<TrabAlum> lisAlumnos                   = (List<TrabAlum>) request.getAttribute("lisAlumnos");
    List<TrabPeriodo> lisPeriodos               = (List<TrabPeriodo>) request.getAttribute("lisPeriodos");
    List<TrabDepartamento> lisDepartamentos     = (List<TrabDepartamento>) request.getAttribute("lisDepartamentos");
    List<TrabCategoria> lisCategorias           = (List<TrabCategoria>) request.getAttribute("lisCategorias");

    HashMap<String,AlumPersonal> mapaAlumnos            = (HashMap<String,AlumPersonal>)request.getAttribute("mapaAlumnos");
    HashMap<String,AlumAcademico> mapaAcademico         = (HashMap<String,AlumAcademico>)request.getAttribute("mapaAcademico");
    HashMap<String,AlumPlan> mapaAlumPlan               = (HashMap<String,AlumPlan>)request.getAttribute("mapaAlumPlan");
    HashMap<String,String> mapHorasRegistradas          = (HashMap<String,String>)request.getAttribute("mapHorasRegistradas");
    HashMap<String,String> mapHorasTotalesRegistradas   = (HashMap<String,String>)request.getAttribute("mapHorasTotalesRegistradas");
    HashMap<String,String> mapCategorias                = (HashMap<String,String>)request.getAttribute("mapCategorias");
    HashMap<String,String> mapDepartamentos             = (HashMap<String,String>)request.getAttribute("mapDepartamentos");
    HashMap<String,String> mapaPlanes                   = (HashMap<String,String>)request.getAttribute("mapaPlanes");
%>
<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<div class="container-fluid">
    <h2>Registered Hours per Student</h2>	
    <div class="alert alert-info d-flex align-items-center">
        Period:&nbsp;
		<select name="PeriodoId" id="PeriodoId" style="width:300px;" class="form-select" onchange="javascript:cambioPeriodo();">
<%
	for(TrabPeriodo periodo: lisPeriodos){
%>
			<option value="<%=periodo.getPeriodoId()%>" <%=periodo.getPeriodoId().equals(periodoId)?"selected":""%>><%=periodo.getNombrePeriodo() %></option>
<%
	}
%>
		</select> &nbsp; &nbsp;
        Department:
		<select name="DeptId" id="DeptId" style="width:300px;" class="form-select" onchange="javascript:cambioPeriodo();">
            <option value="0" <%=deptId.equals("0")?"selected":""%>>All</option>
<%
	for(TrabDepartamento departamento: lisDepartamentos){
%>
			<option value="<%=departamento.getDeptId()%>" <%=departamento.getDeptId().equals(deptId)?"selected":""%>><%=departamento.getNombre() %></option>
<%
	}
%>
		</select> &nbsp; &nbsp;
        Category:
		<select name="CatId" id="CatId" style="width:300px;" class="form-select" onchange="javascript:cambioPeriodo();">
            <option value="0" <%=catId.equals("0")?"selected":""%>>All</option>
<%
	for(TrabCategoria categoria: lisCategorias){
%>
			<option value="<%=categoria.getCategoriaId()%>" <%=categoria.getCategoriaId().equals(catId)?"selected":""%>><%=categoria.getNombreCategoria() %></option>
<%
	}
%>
		</select> &nbsp; &nbsp;
        Year:
		<select name="Grado" id="Grado" style="width:150px;" class="form-select" onchange="javascript:cambioPeriodo();">
            <option value="0" <%=grado.equals("0")?"selected":""%>>All</option>
            <option value="1" <%=grado.equals("1")?"selected":""%>>1</option>
            <option value="2" <%=grado.equals("2")?"selected":""%>>2</option>
            <option value="3" <%=grado.equals("3")?"selected":""%>>3</option>
            <option value="4" <%=grado.equals("4")?"selected":""%>>4</option>
		</select> &nbsp; &nbsp;
    </div>
    <table class="table table-sm table-bordered">
        <thead class="table-info">
            <tr>
                <th>No.</th>
                <th>Student ID</th>
                <th>Name</th>
                <th>Course</th>
                <th>Gender</th>
                <th>Residence</th>
                <th>Department</th>
                <th>Category</th>
                <th>Assigned Hours</th>
                <th>Completed</th>
                <th>Outstanding</th>
                <th>Surplus</th>
            </tr>
        </thead>
        <tbody>
<%      
        int row = 0;
        for(TrabAlum alumno : lisAlumnos){
            row++;

            String horasRegistradas = "0";
            String colorHoras = "bg-secondary";
            String horasTotRegistradas = "0";
            if(mapHorasRegistradas.containsKey(alumno.getMatricula())){
                horasRegistradas = mapHorasRegistradas.get(alumno.getMatricula());
                colorHoras = "bg-primary";
            }
            if(mapHorasTotalesRegistradas.containsKey(alumno.getMatricula())){
                horasTotRegistradas = mapHorasTotalesRegistradas.get(alumno.getMatricula());
                colorHoras = "bg-primary";
            }

            Double horasReg = Double.parseDouble(horasRegistradas);
            Double horasTotReg = Double.parseDouble(horasTotRegistradas);
            Double surplus = horasTotReg - horasReg;

            AlumPersonal alumPersonal =  new AlumPersonal();
            if(mapaAlumnos.containsKey(alumno.getMatricula())){
                alumPersonal = mapaAlumnos.get(alumno.getMatricula());
            }

            String gender = alumPersonal.getSexo().equals("M")?"Male":"Female";

            AlumAcademico alumAcademico =  new AlumAcademico();
            if(mapaAcademico.containsKey(alumno.getMatricula())){
                alumAcademico = mapaAcademico.get(alumno.getMatricula());
            }

            String residence = alumAcademico.getResidenciaId().equals("I")?"Boarding":"Day";

            AlumPlan alumPlan =  new AlumPlan();
            if(mapaAlumPlan.containsKey(alumno.getMatricula())){
                alumPlan = mapaAlumPlan.get(alumno.getMatricula());
            }

            // String nombrePlan = "";
            // if(mapaPlanes.containsKey(alumPlan.getPlanId())) nombrePlan = mapaPlanes.get(alumPlan.getPlanId());

            String departamento = "";
            if(mapDepartamentos.containsKey(alumno.getDeptId())){
                departamento = mapDepartamentos.get(alumno.getDeptId());
            }

            String categoria = "";
            if(mapCategorias.containsKey(alumno.getCatId())){
                categoria = mapCategorias.get(alumno.getCatId());
            }

            Double horasFaltantes = Integer.parseInt(alumno.getHoras()==null?"0":alumno.getHoras()) - horasReg;
            // float horasSobrantes = 0;
            // if(horasFaltantes < 0){
            //     horasSobrantes = Math.abs(horasFaltantes);
            //     horasFaltantes = 0;
            // }
%>
            <tr>
                <td><%=row%></td>
                <td><%=alumno.getMatricula()%></td>
                <td><%=alumPersonal.getNombre()+" "+(alumPersonal.getApellidoMaterno().equals("-")?"":alumPersonal.getApellidoMaterno())+" "+alumPersonal.getApellidoPaterno()%></td>
                <td><%=alumPlan.getPlanId()%></td>
                <td><%=gender%></td>
                <td><%=residence%></td>
                <td><%=departamento%></td>
                <td><%=categoria%></td>
                <td><b><%=alumno.getHoras()==null?"<span class='badge rounded-pill bg-warning'>0</span>":alumno.getHoras()%></b></td>
                <td><span class="badge rounded-pill <%=colorHoras%>"><%=horasRegistradas%></span></td>
                <td><b><%=String.format("%.2f", horasFaltantes)%></b></td>
                <td><b><%=String.format("%.2f", surplus)%></b></td>
            </tr>
<%      }%>
        </tbody>
    </table> 
</div>