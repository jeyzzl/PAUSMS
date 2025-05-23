<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.trabajo.spring.TrabDepartamento"%>
<%@ page import="aca.trabajo.spring.TrabAsesor"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Borrar(CodigoPersonal) {
		if(confirm("<spring:message code="aca.JSEliminar"/> " ) == true) {
			document.location.href = "borrar?CodigoPersonal="+CodigoPersonal;
		} 
	}

	function cambioDepartamento(){
		var deptId 		= document.getElementById("DeptId").value;
		location.href = "listado?DeptId="+deptId;
	}
</script>
<%
	List<TrabDepartamento> lisDepartamentos	= (List<TrabDepartamento>) request.getAttribute("lisDepartamentos");
    List<TrabAsesor> lisAsesoresPorDepartamento	= (List<TrabAsesor>) request.getAttribute("lisAsesoresPorDepartamento");

	HashMap<String,String> mapaNombres 	= (HashMap<String,String>) request.getAttribute("mapaNombres");

    String deptId 					    = (String)request.getAttribute("deptId");
%>
<body>
<div class="container-fluid">
    <h2>Department Supervisors</h2>
<% if(lisDepartamentos != null){%>
	<div class="alert alert-info d-flex align-items-center">
		Department:&nbsp;
		<select name="DeptId" id="DeptId" style="width:280px;" onchange="javascript:cambioDepartamento();" class="form-select">
			<option value="0" <%=deptId.equals("0")?"selected":""%>>All</option>
<%
	for(TrabDepartamento departamento: lisDepartamentos){	
%>
			<option value="<%=departamento.getDeptId() %>" <%=departamento.getDeptId().equals(deptId)?"selected":""%>><%=departamento.getNombre() %></option>
<%
	}
%>
		</select> &nbsp; &nbsp;	
<% if(!deptId.equals("0")){%>
		<a class="btn btn-primary me-3" href="editarAsesor?DeptId=<%=deptId%>"><spring:message code="aca.Anadir"/></a>
<% }%>
	</div>

	<table class="table table-sm table-bordered">  
	<thead class="table-info">	
		<th width="5%"><spring:message code="aca.Operacion"/></th>
		<th width="5%"><spring:message code="aca.Numero"/></th>
		<th width="20%">User ID</th>
		<th width="30%">Name</th>
		<th width="10%">Status</th>
	</thead>
    <tbody>
<%  int row = 0;
    for(TrabAsesor asesor : lisAsesoresPorDepartamento){
        row++;

        String nombre = "";
        if(mapaNombres.containsKey(asesor.getCodigoPersonal())){
            nombre = mapaNombres.get(asesor.getCodigoPersonal());
        }
%>
        <tr>
            <td><%=row%></td>
            <td>
                <a href="editarAsesor?CodigoPersonal=<%=asesor.getCodigoPersonal()%>&DeptId=<%=asesor.getDeptId()%>"><i class="fas fa-edit"></i></a>
                <a href="javascript:Borrar('<%=asesor.getCodigoPersonal()%>')"><i class="fas fa-trash-alt"></i></a>
            </td>
            <td><%=asesor.getCodigoPersonal()%></td>
            <td><%=nombre%></td>
            <td><%=asesor.getStatus()%></td>
        </tr>
<%  }%>
    </tbody>
<% } else {%>
	<div class="alert alert-info">
		No departments found.
	</div>
<% }%>
</div>
</body>