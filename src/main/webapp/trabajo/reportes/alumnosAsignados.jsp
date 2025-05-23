<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.trabajo.spring.TrabDepartamento"%>
<%@ page import="aca.trabajo.spring.TrabCategoria"%>
<%@ page import="aca.trabajo.spring.TrabPeriodo"%>
<%@ page import="aca.trabajo.spring.TrabAlum"%>
<%@ page import="aca.alumno.spring.AlumAcademico"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.alumno.spring.AlumPlan"%>
<%@ page import="aca.residencia.spring.ResComentario"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script>
    function cambioPeriodo(){
		var periodoId = document.getElementById("PeriodoId").value;
        var residencia = document.getElementById("Residencia").value;
        var asignacion = document.getElementById("Asignacion").value;

		location.href = "alumnosAsignados?PeriodoId="+periodoId+"&Residencia="+residencia+"&Asignacion="+asignacion;
	}
</script>
<%
    String periodoId                = (String) request.getAttribute("periodoId");
    String residencia               = (String) request.getAttribute("residencia");
    String asignacion               = (String) request.getAttribute("asignacion");

    List<AlumPersonal> lisAlumnos                   = (List<AlumPersonal>) request.getAttribute("lisAlumnos");
    List<TrabPeriodo> lisPeriodos               = (List<TrabPeriodo>) request.getAttribute("lisPeriodos");
    List<TrabDepartamento> lisDepartamentos     = (List<TrabDepartamento>) request.getAttribute("lisDepartamentos");
    List<TrabCategoria> lisCategorias           = (List<TrabCategoria>) request.getAttribute("lisCategorias");

    HashMap<String,TrabAlum> mapTrabAlum                = (HashMap<String,TrabAlum>)request.getAttribute("mapTrabAlum");
    HashMap<String,AlumPersonal> mapaAlumnos            = (HashMap<String,AlumPersonal>)request.getAttribute("mapaAlumnos");
    HashMap<String,AlumAcademico> mapaAcademico         = (HashMap<String,AlumAcademico>)request.getAttribute("mapaAcademico");
    HashMap<String,AlumPlan> mapaAlumPlan               = (HashMap<String,AlumPlan>)request.getAttribute("mapaAlumPlan");
    HashMap<String,String> mapCategorias                = (HashMap<String,String>)request.getAttribute("mapCategorias");
    HashMap<String,String> mapDepartamentos             = (HashMap<String,String>)request.getAttribute("mapDepartamentos");
    HashMap<String,String> mapaPlanes                   = (HashMap<String,String>)request.getAttribute("mapaPlanes");    
    HashMap<String,ResComentario> mapaMaxiComentario    = (HashMap<String,ResComentario>)request.getAttribute("mapaMaxiComentario");

%>
<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<div class="container-fluid">
    <h2>Students with CTP</h2>	
    <div class="alert alert-info d-flex align-items-center">
        Period:
		<select name="PeriodoId" id="PeriodoId" style="width:300px;" class="form-select mx-2" onchange="javascript:cambioPeriodo();">
<%
	for(TrabPeriodo periodo: lisPeriodos){
%>
			<option value="<%=periodo.getPeriodoId()%>" <%=periodo.getPeriodoId().equals(periodoId)?"selected":""%>><%=periodo.getNombrePeriodo() %></option>
<%
	}
%>
		</select>
        Residence:
        <select name="Residencia" id="Residencia" style="width:150px;" class="form-select mx-2" onchange="javascript:cambioPeriodo();">
            <option value="A" <%=residencia.equals("A")?"selected":""%>>All</option>
            <option value="I" <%=residencia.equals("I")?"selected":""%>>Boarding Student</option>
            <option value="E" <%=residencia.equals("E")?"selected":""%>>Day Student</option>
		</select>
        Assignment:
		<select name="Asignacion" id="Asignacion" style="width:150px;" class="form-select mx-2" onchange="javascript:cambioPeriodo();">
            <option value="0" <%=asignacion.equals("0")?"selected":""%>>All</option>
            <option value="1" <%=asignacion.equals("1")?"selected":""%>>Assigned</option>
            <option value="2" <%=asignacion.equals("2")?"selected":""%>>Not Assigned</option>
		</select>        
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
                <th>Res. Hist.</th>
                <th>Department</th>
                <th>Category</th>
            </tr>
        </thead>
        <tbody>
<%  if(lisAlumnos != null ){%>
<%      
        int row = 0;
        for(AlumPersonal alumno : lisAlumnos){
            row++;

            TrabAlum trabAlum =  new TrabAlum();
            if(mapTrabAlum.containsKey(alumno.getCodigoPersonal())){
                trabAlum = mapTrabAlum.get(alumno.getCodigoPersonal());
            }

            String gender = alumno.getSexo().equals("M")?"Male":"Female";

            AlumAcademico alumAcademico =  new AlumAcademico();
            if(mapaAcademico.containsKey(alumno.getCodigoPersonal())){
                alumAcademico = mapaAcademico.get(alumno.getCodigoPersonal());
            }

            String residence = alumAcademico.getResidenciaId().equals("I")?"Boarding":"Day";

            AlumPlan alumPlan =  new AlumPlan();
            if(mapaAlumPlan.containsKey(alumno.getCodigoPersonal())){
                alumPlan = mapaAlumPlan.get(alumno.getCodigoPersonal());
            }

            String nombrePlan = "";
            if(mapaPlanes.containsKey(alumPlan.getPlanId())) nombrePlan = mapaPlanes.get(alumPlan.getPlanId());

            String departamento = "";
            if(mapDepartamentos.containsKey(trabAlum.getDeptId())){
                departamento = mapDepartamentos.get(trabAlum.getDeptId());
            }

            String categoria = "";
            if(mapCategorias.containsKey(trabAlum.getCatId())){
                categoria = mapCategorias.get(trabAlum.getCatId());
            }

            ResComentario comentario = new ResComentario();
            if(mapaMaxiComentario.containsKey(alumno.getCodigoPersonal())){
                comentario = mapaMaxiComentario.get(alumno.getCodigoPersonal());
            }
%>
            <tr>
                <td><%=row%></td>
                <td><%=alumno.getCodigoPersonal()%></td>
                <td><%=alumno.getNombre()+" "+(alumno.getApellidoMaterno().equals("-")?"":alumno.getApellidoMaterno())+" "+alumno.getApellidoPaterno()%></td>
                <td><%=nombrePlan%></td>
                <td><%=gender%></td>
                <td><%=residence%></td>
                <td><%=comentario.getComentario()%></td>
                <td><%=departamento%></td>
                <td><%=categoria%></td>
            </tr>
<%      }%>
        </tbody>
<%  }%>
    </table> 
</div>