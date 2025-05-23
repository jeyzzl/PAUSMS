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

<%
    String codigoAlumno     = (String)request.getAttribute("codigoAlumno")==null?"0":(String)request.getAttribute("codigoAlumno");
    String planId           = (String)request.getAttribute("planId");
    String nombreAlumno     = (String)request.getAttribute("nombreAlumno");
    String nombreCarrera    = (String)request.getAttribute("nombreCarrera");
    String carreraId        = (String)request.getAttribute("carreraId");

    List<AlumPersonal> lisAlumnos = (List<AlumPersonal>)request.getAttribute("lisAlumnos");

    HashMap<String,String> mapaAlumPlan                     = (HashMap<String,String>)request.getAttribute("mapaAlumPlan");
    HashMap<String,String> mapNumMateriasTraspaso          = (HashMap<String,String>)request.getAttribute("mapNumMateriasTraspaso");
    HashMap<String,String> mapNumMateriasFaltantesTraspaso = (HashMap<String,String>)request.getAttribute("mapNumMateriasFaltantesTraspaso");

%>

<div class="container-fluid">
    <h2>Students with subjects for transfer <small class="text-muted fs-5"> (<b><%=codigoAlumno%></b> <%=nombreAlumno%> - <i><%=planId%></i> - <%=nombreCarrera%>)</small></h2>
    <div class="alert alert-info d-flex">
        <a href="traspaso" class="btn btn-primary">Return</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="text" class="form-control search-query" placeholder="<spring:message code="aca.Buscar"/>" id="buscar" style="width:200px;">
    </div>
    <table class="table table-bordered" id="table">
        <thead>
            <tr>
                <th>#</th>
                <th></th>
                <th>Student ID</th>
                <th>Name</th>
                <th>Plan</th>
                <th>Transfered Subjects</th>
                <th>Available Subjects</th>
            </tr>
        </thead>
        <tbody>
<%
    int row = 0;
    for(AlumPersonal alumno : lisAlumnos){

        String numTraspasos = "0";
        String numFaltantes = "0";

        if(mapNumMateriasTraspaso.containsKey(alumno.getCodigoPersonal())){
            numTraspasos = mapNumMateriasTraspaso.get(alumno.getCodigoPersonal());
        }
        
        if(mapNumMateriasFaltantesTraspaso.containsKey(alumno.getCodigoPersonal())){
            numFaltantes = mapNumMateriasFaltantesTraspaso.get(alumno.getCodigoPersonal());
        }

        String plan = mapaAlumPlan.containsKey(alumno.getCodigoPersonal())?mapaAlumPlan.get(alumno.getCodigoPersonal()):"<span class='badge bg-warning rounded-pill'>NONE</span>";
        if(!numFaltantes.equals("0")) {
            row++;
%>
            <tr>
                <td><%=row%></td>
                <td class="text-center"><a href="lista?CodigoAlumno=<%=alumno.getCodigoPersonal()%>"><span class="badge rounded-pill bg-primary">Load</span></a></td>
                <td><%=alumno.getCodigoPersonal()%></td>
                <td> <%=alumno.getApellidoPaterno()%> <%=alumno.getNombre()%></td>
                <td><%=plan%></td>
                <td><%=numTraspasos%></td>
                <td><%=numFaltantes%></td>
            </tr>
<%
        }
    }
%>
        </tbody>
    </table>
</div>
<script src="../../js/jquery-1.9.1.min.js"></script>
<script src="../../js/search.js"></script>	
<script>	
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>