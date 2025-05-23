<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.kardex.spring.KrdxCursoImp"%>
<%@ page import="aca.catalogo.spring.CatTipoCal"%>
<%@ page import="aca.traspaso.spring.Traspaso"%>
<%@ page import="aca.plan.spring.MapaCurso"%>
<%@ page import="aca.plan.spring.MapaPlan"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.alumno.spring.AlumPlan"%>
<%@ page import="aca.vista.spring.AlumnoCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>


<script type="text/javascript">
    function cambioPlan(){
		var planId 		= document.getElementById("PlanId").value;
		location.href = "traspaso?PlanId="+planId;
	}
</script>
<%
    String codigoAlumno	 	= (String)session.getAttribute("codigoAlumno");

    AlumPersonal alumno     = (AlumPersonal)request.getAttribute("alumno");

    String nombreAlumno	 	= (String)request.getAttribute("nombreAlumno");	
	String planId 		 	= (String)request.getAttribute("planId");
	String nombreCarrera 	= (String)request.getAttribute("nombreCarrera");
	String carreraId	 	= (String)request.getAttribute("carreraId");

    List<AlumPlan> lisPlanes				= (List<AlumPlan>)request.getAttribute("lisPlanes");
    List<Traspaso> lisTraspasos				= (List<Traspaso>)request.getAttribute("lisTraspasos");
    List<MapaCurso> lisCursosEnTraspaso		= (List<MapaCurso>)request.getAttribute("lisCursosEnTraspaso");

    HashMap<String,AlumnoCurso> mapaCursosAlumno	        = (HashMap<String,AlumnoCurso>)request.getAttribute("mapaCursosAlumno");
    HashMap<String,String> mapaCicloPorCurso	            = (HashMap<String,String>)request.getAttribute("mapaCicloPorCurso");
    HashMap<String,String> mapaGpaPorCurso	                = (HashMap<String,String>)request.getAttribute("mapaGpaPorCurso");
    HashMap<String,String> mapaGradePorCurso	            = (HashMap<String,String>)request.getAttribute("mapaGradePorCurso");
    HashMap<String,MapaPlan> mapaPlan	                    = (HashMap<String,MapaPlan>)request.getAttribute("mapaPlan");
%>
<div class="container-fluid">
    <h2>Historical Grade Transfer <small class="text-muted fs-5"> (<b><%=codigoAlumno%></b> <%=nombreAlumno%> - <i><%=planId%></i> - <%=nombreCarrera%>)</small></h2>
    <div class="alert alert-info d-flex align-items-center ">
        Plan:&nbsp;&nbsp;<select class="form-select" id="PlanId" name="planId" style="width: 70rem" onchange="javascript:cambioPlan();"> <!-- SELECT PLANES DE ALUMNO -->
<%
        for(AlumPlan plan : lisPlanes){
            String planNombre = "-";
			if (mapaPlan.containsKey(plan.getPlanId())){
				planNombre = mapaPlan.get(plan.getPlanId()).getNombrePlan();
			}
%>
            <option value="<%=plan.getPlanId()%>" <%=planId.equals(plan.getPlanId())?"selected":""%>><%=plan.getPlanId()%>-<%=planNombre%></option>
<%
        }
%>
        </select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="lista" class="btn btn-primary" >Find Students</a>
    </div>
    <div class="row">
        <!-- TABLA DE CURSOS ENCONTRADOS -->
        <div class="col">
        <form name="frmCursos" action="grabar" method="post">
            <input type="hidden" name="PlanId" value="<%=planId%>" >                                    <!-- PLAN ID-->
            <h2>Registered Subjects in Plan</h2>
            <table class="table table-bordered" id="tablaCursos">
            <thead>
                <tr>
                    <th>#</th>
                    <th>SUBJECT NAME</th>
                    <th>SUBJECT KEY</th>
                    <th>SUBJECT STATUS</th>
                    <th>PLAN NAME</th>
                    <th>CYCLE</th>
                    <th>GRADE</th>
                    <th>STATUS</th>
                </tr>
            </thead>
            <tbody>
<%
        int rowCursos = 0;
        boolean mostrarBotones = false;
        // ITERA SOBRE LISTA DE MAPA_CURSO ENCONTRADO EN KRDX_TRASPASO PARA EL ALUMNO
        for(MapaCurso curso: lisCursosEnTraspaso){
            rowCursos++;
        // CHECA SI EXISTE EN CURSO KRDX_CURSO_ACT O KRDX_CURSO_IMP
            boolean tieneCurso = false;
            
            if(mapaCursosAlumno.containsKey(curso.getCursoId())){
                tieneCurso = true;
            }

            if(!tieneCurso){
                mostrarBotones = true;
            }

        
        // OBTIENE EL CICLO DE KRDX_TRASPASO PARA CURSO  
            String ciclo = "0";
            if(mapaCicloPorCurso.containsKey(codigoAlumno+curso.getCursoClave())){
                ciclo = mapaCicloPorCurso.get(codigoAlumno+curso.getCursoClave());
            }

        

        // OBTIENE EL GRADE DE KRDX_TRASPASO PARA CURSO  
            String grade = "0";
            if(mapaGradePorCurso.containsKey(codigoAlumno+curso.getCursoClave())){
                grade = mapaGradePorCurso.get(codigoAlumno+curso.getCursoClave());
            }
            
%>
        <!-- VALORES PARA FORM -->
            <input type="hidden" name="cursoId<%=rowCursos%>" value="<%=curso.getCursoId()%>">          <!-- CURSO_ID -->
            <input type="hidden" name="tipoCalId<%=rowCursos%>" value="<%=curso.getTipoCursoId()%>">    <!-- TIPOCAL_ID -->
            <input type="hidden" name="ciclo<%=rowCursos%>" value="<%=ciclo%>">                         <!-- CICLO -->                          
            <input type="hidden" name="grade<%=rowCursos%>" value="<%=grade%>">                         <!-- GRADE -->
                <tr>
                    <td><%=rowCursos%></td>
                    <td><%=curso.getNombreCurso()%></td>
                    <td><%=curso.getCursoClave()%></td>
                    <td><%=curso.getEstado()%></td>
                    <td><%=curso.getPlanId()%></td>
                    <td><%=ciclo%></td>
                    <td><%=grade.length() > 5 ? grade.substring(0,5) : grade%></td>
                    <td class="text-center">
        <!-- MUESTRA CHECK SI TIENE CURSO O CHECKBOX SI NO TIENE CURSO -->
<%
                        if(tieneCurso){ 
%>
                        <i class="fas fa-check"></i>
<%
                        } else {
%>
                        <i class="fas fa-times"></i>
                        <input type="checkbox" class="form-check-input" name="check<%=rowCursos%>" value="S"> <!-- CHECK -->
<%
                        }
%>
                    </td>
                </tr>
<%
        }
%>
            </tbody>
            </table>
            <div class="alert alert-info d-flex justify-content-between">
                <button class="btn btn-primary" type="submit">Save</button>
                <div>
<%
                    if (mostrarBotones) {
%>
                    <button class="btn btn-success" type="button" id="selectAll">Select All</button>
                    <button class="btn btn-secondary" type="button" id="deselectAll">Deselect All</button>
<%
                    }
%>
                </div>
            </div>
        </form>
        </div>
        <!-- TABLA DE CURSOS EN TRASPASO-->
        <div class="col">
            <h2>Imported Subjects</h2>
            <table class="table table-bordered" id="tablaTraspasos">
            <thead>
                <tr>
                    <th>#</th>
                    <th>SUBJECT NAME</th>
                    <th>SUBJECT KEY</th>
                    <th>SEMESTER NAME</th>
                    <th>DATE</th>
                    <th>GRADE</th>
                </tr>
            </thead>
            <tbody>
<%
        int rowTraspasos = 0;
        for(Traspaso traspaso: lisTraspasos){
            rowTraspasos++;
            
%> 
                <tr>
                    <td><%=rowTraspasos%></td>
                    <td><%=traspaso.getSubjectName()%></td>
                    <td><%=traspaso.getSubjectCode()%></td>
                    <td><%=traspaso.getSemesterName()%></td>
                    <td><%=traspaso.getCourseGradeDate().substring(0,10)%></td>
                    <td><%=traspaso.getCourseGrade().length() > 5 ? traspaso.getCourseGrade().substring(0,5) : traspaso.getCourseGrade()%></td>
                </tr>
<%
        }
%>
            </tbody>
            </table>
        </div>
    </div>
</div>
<script>
    document.getElementById('selectAll').addEventListener('click', function() {
        var checkboxes = document.querySelectorAll('#tablaCursos input[type="checkbox"]');
        checkboxes.forEach(function(checkbox) {
            checkbox.checked = true;
        });
    });

    document.getElementById('deselectAll').addEventListener('click', function() {
        var checkboxes = document.querySelectorAll('#tablaCursos input[type="checkbox"]');
        checkboxes.forEach(function(checkbox) {
            checkbox.checked = false;
        });
    });
</script>