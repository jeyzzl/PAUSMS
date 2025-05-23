<%@ page import="java.text.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.trabajo.spring.TrabInforme"%>
<%@ page import="aca.trabajo.spring.TrabInformeAlum"%>
<%@ page import="aca.trabajo.spring.TrabDepartamento"%>	
<%@ page import="aca.trabajo.spring.TrabCategoria"%>
<%@ page import="aca.trabajo.spring.TrabPeriodo"%>
<%@ page import="aca.trabajo.spring.TrabAlum"%>
<%@ page import="aca.acceso.spring.Acceso"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script type="text/javascript">
function Grabar(){		
	document.frmhoras.Accion.value="2";
	document.frmhoras.submit();					

}

</script>
<%
    boolean esAsesor    = (boolean)request.getAttribute("esAsesor");
    boolean esDirector    = (boolean)request.getAttribute("esDirector");
    boolean esAdmin    = (boolean)request.getAttribute("esAdmin");
    String deptId       = (String)request.getAttribute("deptId");
    String catId        = (String)request.getAttribute("catId");
    String periodoId    = (String)request.getAttribute("periodoId");
    String fecha        = (String)request.getAttribute("fecha");
    String mensaje      = request.getParameter("Mensaje");

    List<TrabPeriodo> lisPeriodos           = (List<TrabPeriodo>) request.getAttribute("lisPeriodos");
    List<TrabDepartamento> lisDepartamentos = (List<TrabDepartamento>) request.getAttribute("lisDepartamentos");
    List<TrabCategoria> lisCategorias       = (List<TrabCategoria>) request.getAttribute("lisCategorias");
    List<TrabInformeAlum> lisInformes       = (List<TrabInformeAlum>) request.getAttribute("lisInformes");
    
    HashMap<String, AlumPersonal> mapaAlumPersonal  = (HashMap<String, AlumPersonal>) request.getAttribute("mapaAlumPersonal");
    HashMap<String, String> mapaNombreCategorias    = (HashMap<String, String>) request.getAttribute("mapaNombreCategorias");
%>
<%-- <link rel="stylesheet" href="../../js/chosen/chosen.css" /> --%>
<body>
<div class="container-fluid">
    <h2>CTP Hours Reports</h2>
    <form action="informeHoras" name="frmparametros" method="get">
    <div class="alert alert-info d-flex align-items-center">
        <label for="PeriodoId">Period:</label>
 		<select name="PeriodoId" id="PeriodoId" style="width:200px;" onchange="document.frmparametros.submit()" class="form-select mx-2">
<%
	for(TrabPeriodo periodo: lisPeriodos){	
%>
			<option value="<%=periodo.getPeriodoId()%>" <% if(periodoId.equals(periodo.getPeriodoId()))out.print("selected"); %>><%=periodo.getNombrePeriodo()%></option>
<%
	}
%>	
		</select>
        <label for="DeptId">Department:</label>
		<select name="DeptId" id="DeptId" style="width:300px;" onchange="document.frmparametros.submit()" class="form-select mx-2">
<%	for(TrabDepartamento depto: lisDepartamentos){%>
			<option value="<%=depto.getDeptId() %>" <% if(deptId.equals(depto.getDeptId()))out.print("selected"); %>><%=depto.getDeptId() %> | <%=depto.getNombre() %></option>
<%	} %>
		</select>
        <label for="CatId">Category:</label>
		<select name="CatId" id="CatId" style="width:300px;" onchange="document.frmparametros.submit()" class="form-select mx-2">
<%	for(TrabCategoria cat: lisCategorias){%>
			<option value="<%=cat.getCategoriaId()%>" <% if(catId.equals(cat.getCategoriaId()))out.print("selected"); %>><%=cat.getCategoriaId() %> | <%=cat.getNombreCategoria() %></option>
<%	} %>
		</select>
        <label for="Fecha">Date:</label>
        <input name="Fecha" type="text" class="form-control ms-2" id="Fecha" data-date-format="dd/mm/yyyy" value="<%=fecha%>" size="12" maxlength="10" style="width: 12rem;"> 
        <button name="search" id ="search" type="submit" class="btn btn-primary mx-3"><i class="fas fa-search"></i></button>
<%  if(mensaje!=null){%>
        <%=mensaje%>
<%  }%>
    </div>
    </form>
    <table class="table table-bordered">
        <thead class="table-info">
            <tr>
                <th width="2%">No.</th>
                <th>Rep. ID</th>
                <th width="9%">Student ID</th>
                <th>Name</th>
                <th>Category</th>
                <th width="8%">Date</th>
                <th>Start T.</th>
                <th>End T.</th>
                <th class="text-center">Hours</th>
                <th width="20%">Desc.</th>
                <th>User</th>
                <th class="text-center" width="10%">Authorize</th> 
            </tr>
        </thead>
        <tbody>
<%      int row = 0;
        for(TrabInformeAlum informe : lisInformes){
            row++;

            String nombre = "";
            AlumPersonal alumno = new AlumPersonal();
            if(mapaAlumPersonal.containsKey(informe.getMatricula())){
                alumno = mapaAlumPersonal.get(informe.getMatricula());
                nombre = alumno.getNombre()+" "+(alumno.getApellidoMaterno()==null?"":alumno.getApellidoMaterno())+" "+alumno.getApellidoPaterno();
            }

            String categoria = "";
            if(mapaNombreCategorias.containsKey(informe.getCatId())){
                categoria = mapaNombreCategorias.get(informe.getCatId());
            }
%>
            <tr>
                <td><%=row%></td>
                <td><%=informe.getInformeId()%></td>
                <td><%=informe.getMatricula()%></td>
                <td><%=nombre%></td>
                <td><%=categoria%></td>
                <td><%=informe.getFecha().substring(0, 10)%></td>
                <td><%=informe.getHoraInicio()==null?"":informe.getHoraInicio().substring(10, 19)%></td>
                <td><%=informe.getHoraFin()==null?"":informe.getHoraFin().substring(10, 19)%></td>
                <td class="text-center"><%=informe.getHoras()%></td>
                <td><%=informe.getDescripcion()==null?"":informe.getDescripcion()%></td>
                <td><%=informe.getUsuario()%></td>
                <td class="text-center">
<%          if(informe.getStatus().equals("S") && (esAsesor || esDirector || esAdmin)){%>
                    <a href="autorizar?InformeId=<%=informe.getInformeId()%>&DeptId=<%=informe.getDeptId()%>&CatId=<%=informe.getCatId()%>&PeriodoId=<%=informe.getPeriodoId()%>&Fecha=<%=fecha%>&Matricula=<%=informe.getMatricula()%>" class="btn btn-primary"><i class="fas fa-check"></i></a>
<%          }else if(informe.getStatus().equals("F") && (esAsesor || esDirector || esAdmin)){%>
                    <a href="aprovar?InformeId=<%=informe.getInformeId()%>&DeptId=<%=informe.getDeptId()%>&CatId=<%=informe.getCatId()%>&PeriodoId=<%=informe.getPeriodoId()%>&Fecha=<%=fecha%>&Matricula=<%=informe.getMatricula()%>" class="btn btn-success"><i class="fas fa-check"></i></a>
<%          } %>
<%-- <%          if((Float.parseFloat(informe.getHoras()) == 0 && (esAsesor || esDirector || esAdmin)) || (esDirector || esAdmin)){%> --%>
<%          if(esAsesor || esDirector || esAdmin){%>
                    <a href="eliminar?InformeId=<%=informe.getInformeId()%>&DeptId=<%=informe.getDeptId()%>&CatId=<%=informe.getCatId()%>&PeriodoId=<%=informe.getPeriodoId()%>&Fecha=<%=fecha%>&Matricula=<%=informe.getMatricula()%>" class="btn btn-danger"><i class="fas fa-trash"></i></a>
<%          }%>
<%          if(informe.getStatus().equals("X")){%>
                    auth. by <%=informe.getUsuario()%>
<%          }%>
<%          if(informe.getStatus().equals("C") && informe.getHoraInicio()!=null &&  informe.getHoraFin()!=null && informe.getHoras().equals("0") && (esAsesor || esDirector || esAdmin)){%>
                    <a href="sync?InformeId=<%=informe.getInformeId()%>&DeptId=<%=informe.getDeptId()%>&CatId=<%=informe.getCatId()%>&PeriodoId=<%=informe.getPeriodoId()%>&Fecha=<%=fecha%>&Matricula=<%=informe.getMatricula()%>" class="btn btn-warning"><i class="fas fa-redo"></i></a>
<%          }%>
                </td>
            </tr>
<%      }%>
        </tbody>
    </table>
</div>
</body>
<script>
	jQuery('#Fecha').datepicker();
</script>