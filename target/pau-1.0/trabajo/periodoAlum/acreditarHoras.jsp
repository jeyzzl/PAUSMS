<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.trabajo.spring.TrabDepartamento"%>
<%@ page import="aca.trabajo.spring.TrabCategoria"%>
<%@ page import="aca.trabajo.spring.TrabPeriodo"%>
<%@ page import="aca.trabajo.spring.TrabAlum"%>
<%@ page import="aca.trabajo.spring.TrabInformeAlum"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<%
	String codigoAlumno 						= (String)session.getAttribute("codigoAlumno");
    String deptId 								= (String)request.getAttribute("deptId");
	String catId 								= (String)request.getAttribute("catId");
    String fecha 								= (String)request.getAttribute("fecha");
	String periodoId 							= (String)request.getAttribute("periodoId");
    String mensaje								= (String)request.getParameter("Mensaje")==null?"-":(String)request.getParameter("Mensaje");

    AlumPersonal alumPersonal 		= (AlumPersonal) request.getAttribute("alumPersonal");
    TrabAlum trabAlum               = (TrabAlum) request.getAttribute("trabAlum");

    List<TrabInformeAlum> lisInformesAcreditados = (List<TrabInformeAlum>) request.getAttribute("lisInformesAcreditados");
%>
<div class="container-fluid">
    <h2>Register CTP Hours</h2>
    <div class="alert alert-info">
        <a href="periodoAlum" class="btn btn-primary">Return</a>
<%  if(mensaje != null && !mensaje.equals("-")){%>
    <%=mensaje%>
<%  }%> 
    </div>
    <form action="grabarHoras" name="frmHorasAlum" method="post">
        <input type="hidden" name="PeriodoId" id="PeriodoId" value="<%=periodoId%>">
        <input type="hidden" name="DeptId" id="DeptId" value="<%=deptId%>">
        <input type="hidden" name="CatId" id="CatId" value="<%=catId%>">
        <label for="Fecha" clas="form-label">Date:</label>
        <input type="text" name="Fecha" id="Fecha" value="<%=fecha%>" data-date-format="dd/mm/yyyy" class="form-control" style="width: 10rem;">
        <label for="Horas" clas="form-label">Hours:</label>
        <input type="text" name="Horas" id="Horas" class="form-control" style="width: 10rem;">
        <br>
<div class="alert alert-info">
        <button type="submit" class="btn btn-primary">Save</button>
</div>
    </form> 
    <table class="table table-bordered">
        <thead class="table-info">
            <tr>
               <th>No.</th>
               <th>Op.</th>
               <th>Report ID</th>
               <th>Date</th>
               <th>Hours</th>
               <th>User</th> 
            </tr>
        </thead>
        <tbody>
<%  int row = 0;
    for(TrabInformeAlum informe : lisInformesAcreditados){
        row++;
%>
            <tr>
                <td><%=row%></td>
                <td>
                    <a href="eliminarHoras?InformeId=<%=informe.getInformeId()%>&PeriodoId=<%=periodoId%>&DeptId=<%=deptId%>&CatId=<%=catId%>"><i class="fas fa-trash text-danger"></i></a>
                </td> 
                <td><%=informe.getInformeId()%></td>
                <td><%=informe.getFecha()%></td>
                <td><%=informe.getHoras()%></td>
                <td><%=informe.getUsuario()%></td>               
            </tr>
<%  }%>
        </tbody>
    </table>
</div>
<script>
	jQuery('#Fecha').datepicker();
</script>
