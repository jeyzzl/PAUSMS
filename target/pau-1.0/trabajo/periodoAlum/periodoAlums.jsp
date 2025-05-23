<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.trabajo.spring.TrabDepartamento"%>
<%@ page import="aca.trabajo.spring.TrabCategoria"%>
<%@ page import="aca.trabajo.spring.TrabPeriodo"%>
<%@ page import="aca.trabajo.spring.TrabAlum"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.alumno.spring.AlumPlan"%>
<%@ page import="aca.alumno.spring.AlumAcademico"%>
<%@ page import="aca.carga.spring.Carga"%>
<%@ page import="aca.internado.spring.IntDormitorio"%>
<%@ page import="aca.internado.spring.IntDormitorio"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.3/css/jquery.dataTables.css">
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.js"></script>
<script type="text/javascript" >
	function grabar(){
		if(document.frmPeriodoAlums.PeriodoId.value=="" || document.frmPeriodoAlums.Horas.value==""){
			alert("You must assign a Period and Hours");
		}else{
			document.frmPeriodoAlums.submit();
		}
	}

    function cambioCarga(){
        var cargaId     = document.getElementById("CargaId").value;
		var periodoId 	= document.getElementById("PeriodoId").value;
		var deptId 		= document.getElementById("DeptId").value;
		var catId 		= document.getElementById("CatId").value;
		var grado 		= document.getElementById("Grado").value;
		var residencia 	= document.getElementById("Residencia").value;
		location.href = "periodoAlums?CargaId="+cargaId+"&PeriodoId="+periodoId+"&DeptId="+deptId+"&CatId="+catId+"&Grado="+grado+"&Residencia="+residencia;
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
    String cargaId                              = (String)request.getAttribute("cargaId");
	String deptId 								= (String)request.getAttribute("deptId");
	String catId 								= (String)request.getAttribute("catId");
	String grado 								= (String)request.getAttribute("grado");
	String periodoId 							= (String)request.getAttribute("periodoId");
	String residencia 							= (String)request.getAttribute("residencia");
    String codigoPersonal                       = (String)session.getAttribute("codigoPersonal");

    List<TrabDepartamento> lisDepartamentos 	= (List<TrabDepartamento>)request.getAttribute("lisDepartamentos");
	List<TrabCategoria> lisCategorias 			= (List<TrabCategoria>)request.getAttribute("lisCategorias");
	List<TrabPeriodo> lisPeriodos 				= (List<TrabPeriodo>)request.getAttribute("lisPeriodos");
    List<Carga> lisCargas                       = (List<Carga>)request.getAttribute("lisCargas");
    List<AlumPersonal> lisAlumnos               = (List<AlumPersonal>)request.getAttribute("lisAlumnos");
    
    HashMap<String, AlumPlan> mapaAlumPlan      		= (HashMap<String, AlumPlan>)request.getAttribute("mapaAlumPlan");
	HashMap<String, String> mapaAlumnosEnPeriodo		= (HashMap<String, String>)request.getAttribute("mapaAlumnosEnPeriodo");
	HashMap<String, AlumAcademico> mapaAlumAcademico 	= (HashMap<String, AlumAcademico>)request.getAttribute("mapaAlumAcademico");
	HashMap<String, IntDormitorio> mapaIntDormitorio    = (HashMap<String, IntDormitorio>)request.getAttribute("mapaIntDormitorio");

    String mensaje								= (String)request.getParameter("Mensaje")==null?"-":(String)request.getParameter("Mensaje");
%>
<div class="container-fluid">
    <h2>Assign CTP</h2>	
	<form action="grabarPeriodoAlums" name="frmPeriodoAlums" method="post">
	<div class="alert alert-info d-flex align-items-center">
		Load:
		<select name="CargaId" id="CargaId" style="width:15rem;" onchange="javascript:cambioCarga();" class="form-select ms-1 me-3">
<%
	for(Carga carga: lisCargas){
%>
			<option value="<%=carga.getCargaId()%>" <%=carga.getCargaId().equals(cargaId)?"selected":""%>><%=carga.getNombreCarga() %></option>
<%
	}
%>
		</select>
		Year:
		<select name="Grado" id="Grado" class="form-select ms-1 me-3" onchange="javascript:cambioCarga();" style="width:15rem;">
			<option value="0" <%=grado.equals("0")?"selected":""%>>All</option>
			<option value="1" <%=grado.equals("1")?"selected":""%>>1</option>
			<option value="2" <%=grado.equals("2")?"selected":""%>>2</option>
			<option value="3" <%=grado.equals("3")?"selected":""%>>3</option>
			<option value="4" <%=grado.equals("4")?"selected":""%>>4</option>
		</select>
		Residence:
		<select name="Residencia" id="Residencia" class="form-select ms-1 me-3" onchange="javascript:cambioCarga();" style="width:15rem;">
			<option value="A" <%=residencia.equals("A")?"selected":""%>>All</option>
			<option value="I" <%=residencia.equals("I")?"selected":""%>>Boarding</option>
			<option value="E" <%=residencia.equals("E")?"selected":""%>>Day Student</option>
		</select>
	</div>
	<div class="alert alert-info d-flex align-items-center">
		Period:
		<select name="PeriodoId" id="PeriodoId" style="width:15rem;"  class="form-select ms-1 me-3" onchange="javascript:cambioCarga();">
<%
	for(TrabPeriodo periodo: lisPeriodos){
%>
			<option value="<%=periodo.getPeriodoId()%>" <%=periodo.getPeriodoId().equals(periodoId)?"selected":""%>><%=periodo.getNombrePeriodo() %></option>
<%
	}
%>
		</select>
		Department:
		<select name="DeptId" id="DeptId" style="width:280px;" onchange="javascript:cambioCarga();" class="form-select ms-1 me-3">
<%
	for(TrabDepartamento departamento: lisDepartamentos){	
%>
			<option value="<%=departamento.getDeptId() %>" <%=departamento.getDeptId().equals(deptId)?"selected":""%>><%=departamento.getNombre() %></option>
<%
	}
%>
		</select>
		Category:
		<select name="CatId" id="CatId" style="width:280px;" class="form-select ms-1 me-3">
<%
	for(TrabCategoria categoria: lisCategorias){
%>
			<option value="<%=categoria.getCategoriaId()%>" <%=categoria.getCategoriaId().equals(catId)?"selected":""%>><%=categoria.getNombreCategoria() %></option>
<%
	}
%>
		</select>
		Hours:
		<input type="text" name="Horas" id="Horas" class="form-control ms-1" style="width:5rem" value>
	</div>
	<button onclick="grabar();" type="button" class="btn btn-primary">Assign</button>
	<button type="button" class="btn-sm btn-secondary mb-2" onclick="selectAllCheckboxes(true);" id="selectAllButton">Select All</button>
    <button type="button" class="btn-sm btn-secondary mb-2" onclick="selectAllCheckboxes(false);" id="deselectAllButton">Deselect All</button>
    <table id="table" class="table table-sm table-bordered table-striped">
        <thead class="table-dark">
            <tr>
                <th>No.</th>
                <th></th>
                <th>Student ID</th>
                <th>Name</th>
                <th>Semester</th>
				<th>Plan ID</th>
				<th>Residence</th>
            </tr>
        </thead>
        <tbody>
<%  int row = 0;
    for(AlumPersonal alumno : lisAlumnos){
        row++;

		boolean estaEnPeriodo = false;
		if(mapaAlumnosEnPeriodo.containsKey(alumno.getCodigoPersonal())){
			estaEnPeriodo = true;
		}

		AlumPlan alumPlan = new AlumPlan();
		if(mapaAlumPlan.containsKey(alumno.getCodigoPersonal())){
			alumPlan = mapaAlumPlan.get(alumno.getCodigoPersonal());
		}

		AlumAcademico alumAcademico = new AlumAcademico();
		if(mapaAlumAcademico.containsKey(alumno.getCodigoPersonal())){
			alumAcademico = mapaAlumAcademico.get(alumno.getCodigoPersonal());
		}

		String generoDormitorio = "";
		if(mapaIntDormitorio.containsKey(alumAcademico.getDormitorio())){
		 	generoDormitorio = mapaIntDormitorio.get(alumAcademico.getDormitorio()).getSexo();
		}

%>
            <tr>
                <td><%=row%></td>
                <td>
<%				if(!estaEnPeriodo){%>
					<input class="form-check-input" type="checkbox" value="<%=alumno.getCodigoPersonal()%>" id="<%=alumno.getCodigoPersonal()%>" name="<%=alumno.getCodigoPersonal()%>">
<%				}%>	
				</td>
                <td><%=alumno.getCodigoPersonal()%></td>
                <td><%=alumno.getNombre()+" "+(alumno.getApellidoMaterno()==null?"":alumno.getApellidoMaterno())+" "+alumno.getApellidoPaterno()%></td>
                <td><%=alumPlan.getCiclo()%></td>
				<td><%=alumPlan.getPlanId()%></td>
				<td><%=alumAcademico.getResidenciaId().equals("E")?"DST":generoDormitorio.equals("M")?"MD":"LD"%></td>
            </tr>
<%  }%>
        </tbody>
    </table>
	</form>
</div>
<script>	
	$('#table').DataTable({
        language: {
            url: 'https://cdn.datatables.net/plug-ins/1.10.21/i18n/English.json',
            search: "Search:"
        },
        paging: false,
        pageLength: 100,
        info: false,
        lengthChange: false,
        layout: {
            topStart: {
                buttons: ['copy', 'csv', 'excel', 'pdf', 'print']
            }
        }
    });
</script>