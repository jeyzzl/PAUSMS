<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.catalogo.spring.CatPatrocinador"%>
<%@ page import="aca.catalogo.spring.CatAcomodo"%>
<%@ page import="aca.catalogo.spring.CatPeriodo"%>
<%@ page import="aca.carga.spring.Carga"%>
<%@ page import="aca.alumno.spring.AlumPatrocinador"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.alumno.spring.AlumAcademico"%>
<%@ page import="aca.alumno.spring.AlumPlan"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript" >
	function grabar(){
        document.frmPatrocinador.submit();
	}

    function cambioCarga(){
        var periodoId   = document.getElementById("PeriodoId").value;
		var grado 		= document.getElementById("Grado").value;
		var acomodo 	= document.getElementById("Acomodo").value;
		location.href = "patrocinadores?PeriodoId="+periodoId+"&Grado="+grado+"&Acomodo="+acomodo;
	}
	
	function Borrar(PeriodoId, DeptId, CatId) {
		if (confirm("<spring:message code="aca.JSEliminar"/>") == true) {
			document.location = "borrarPeriodo?PeriodoId="+PeriodoId+"&DeptId="+DeptId+"&CatId="+CatId;
		}
	}

	function selectAllCheckboxes(checked) {
        const checkboxes = document.querySelectorAll('.form-check-input');
        checkboxes.forEach(checkbox => {
            checkbox.checked = checked;
        });
        toggleButton();
    }
	
</script>
<%
	String periodoId								= (String) request.getAttribute("periodoId");
    String grado									= (String) request.getAttribute("grado");
    String acomodo									= (String) request.getAttribute("acomodo");
	String mensaje									= (String)request.getParameter("Mensaje")==null?"-":(String)request.getParameter("Mensaje");
	
	List<CatPeriodo> lisPeriodos 						= (List<CatPeriodo>)request.getAttribute("lisPeriodos");
    List<AlumPersonal> lisAlumnos 						= (List<AlumPersonal>)request.getAttribute("lisAlumnos");
	List<CatPatrocinador> lisPatrocinadores 			= (List<CatPatrocinador>)request.getAttribute("lisPatrocinadores");
	// List<AlumPatrocinador> lisPatrocinadosPorCarga 		= (List<AlumPatrocinador>)request.getAttribute("lisPatrocinadosPorCarga");
    List<CatAcomodo> lisAcomodos 		                = (List<CatAcomodo>)request.getAttribute("lisAcomodos");
    
    HashMap<String, AlumAcademico> mapAcademico	            = (HashMap<String, AlumAcademico>)request.getAttribute("mapAcademico");
    HashMap<String, AlumPlan> mapAlumPlan	                = (HashMap<String, AlumPlan>)request.getAttribute("mapAlumPlan");
    HashMap<String, AlumPatrocinador> mapAlumPatrocinador	= (HashMap<String, AlumPatrocinador>)request.getAttribute("mapAlumPatrocinador");
	HashMap<String, CatPeriodo> mapPeriodos					= (HashMap<String, CatPeriodo>)request.getAttribute("mapPeriodos");
	HashMap<String, CatPatrocinador> mapPatrocinadores	    = (HashMap<String, CatPatrocinador>)request.getAttribute("mapPatrocinadores");
%>

<div class="container-fluid">
    <h2>Assign Sponsors</h2>
    <div class="alert alert-info d-flex align-items-center">
        Cycle:
        <select name="PeriodoId" id="PeriodoId" class="form-select ms-1 me-2" style="width:20rem;" onchange="javascript:cambioCarga();">
<%  for(CatPeriodo periodo: lisPeriodos){%>
            <option value="<%=periodo.getPeriodoId()%>" <%=periodoId.equals(periodo.getPeriodoId())?"selected":""%>><%=periodo.getPeriodoId()%> - <%=periodo.getNombre()%></option>
<%  }%>
        </select>
        Year:
        <select name="Grado" id="Grado" class="form-select ms-1 me-2" style="width:10rem;" onchange="javascript:cambioCarga();">
            <option value="0" <%=grado.equals("0")?"selected":""%>>All</option>
            <option value="1" <%=grado.equals("1")?"selected":""%>>1</option>
            <option value="2" <%=grado.equals("2")?"selected":""%>>2</option>
            <option value="3" <%=grado.equals("3")?"selected":""%>>3</option>
            <option value="4" <%=grado.equals("4")?"selected":""%>>4</option>
        </select>
        Accommodation:
        <select name="Acomodo" id="Acomodo" class="form-select ms-1 me-2" style="width:15rem;" onchange="javascript:cambioCarga();">
            <option value="0" <%=acomodo.equals("0")?"selected":""%>>All</option>
<%  for(CatAcomodo catAcomodo: lisAcomodos){%>
            <option value="<%=catAcomodo.getAcomodoId()%>" <%=acomodo.equals(catAcomodo.getAcomodoId())?"selected":""%>><%=catAcomodo.getNombreCorto()%> - <%=catAcomodo.getNombre()%></option>
<%  }%>
        </select>
    </div>
    <form action="asignar" name="frmPatrocinador" method="post">
    <input type="hidden" name="PeriodoId" id="PeriodoId" value="<%=periodoId%>">
    <div class="alert alert-info d-flex align-items-center">
        Sponsors:
        <select name="Patrocinador" id="Patrocinador" class="form-select ms-1 me-2" style="width:15rem;">
<%  for(CatPatrocinador patrocinador : lisPatrocinadores){%>
            <option value="<%=patrocinador.getPatrocinadorId()%>"><%=patrocinador.getPatrocinadorId()%> - <%=patrocinador.getNombrePatrocinador()%></option>
<%  }%>
        </select>
        Amount:
        <input type="text" name="Cantidad" id="Cantidad" style="width:7rem;" class="form-control ms-2 me-2">
        <button onclick="grabar();" type="button" class="btn btn-primary">Assign</button>
        <button type="button" class="btn btn-secondary ms-2 me-2" onclick="selectAllCheckboxes(true);" id="selectAllButton">Select All</button>
        <button type="button" class="btn btn-secondary me-2" onclick="selectAllCheckboxes(false);" id="deselectAllButton">Deselect All</button>
<%  if(!mensaje.equals("-")){%>
    <%=mensaje%>
<%  }%>
    </div>
    <table class="table table-bordered">
        <thead class="table-dark">
            <tr>
                <th width="5%">No.</th>
                <th width="5%">Op.</th>
                <th width="7%">Student ID</th>
                <th width="25%">Name</th>
                <th width="5%">Semester</th>
                <th>Plan ID</th>
                <th>Residence</th>
            </tr>
        </thead> 
        <tbody>
<%  int row = 0;
    for(AlumPersonal alumno : lisAlumnos){

        boolean patrocinado = false;
        AlumPatrocinador alumPatrocinador = new AlumPatrocinador();
        if(mapAlumPatrocinador.containsKey(alumno.getCodigoPersonal())){
            alumPatrocinador = mapAlumPatrocinador.get(alumno.getCodigoPersonal());
            patrocinado = true;
        }

        AlumAcademico alumAcademico = new AlumAcademico();
        String semestre = "";
        String residencia = "";
        if(mapAcademico.containsKey(alumno.getCodigoPersonal())){
            alumAcademico = mapAcademico.get(alumno.getCodigoPersonal());
            semestre = alumAcademico.getSemestre();
            residencia = alumAcademico.getResidenciaId().equals("I")?"Boarding":"Day Student";
        }

        AlumPlan alumPlan = new AlumPlan();
        String planId = "";
        if(mapAlumPlan.containsKey(alumno.getCodigoPersonal())){
            alumPlan = mapAlumPlan.get(alumno.getCodigoPersonal());
            planId = alumPlan.getPlanId();
        }

        String nombreAlumno = alumno.getNombre()+" "+alumno.getApellidoMaterno()+" "+alumno.getApellidoPaterno();

        if(!patrocinado){
            row++;
%>
            <tr>
                <td><%=row%></td>
                <td>
                    <input class="form-check-input" type="checkbox" name="<%=alumno.getCodigoPersonal()%>" id="<%=alumno.getCodigoPersonal()%>" value="<%=alumno.getCodigoPersonal()%>">
                </td>
                <td><%=alumno.getCodigoPersonal()%></td>
                <td><%=nombreAlumno%></td>
                <td><%=semestre%></td>
                <td><%=planId%></td>
                <td><%=residencia%></td>
            </tr>
<%      }
    }
%>
        </tbody>           
    </table>
    </form>
</div>