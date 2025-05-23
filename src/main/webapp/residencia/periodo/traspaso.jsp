<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.residencia.spring.ResRazon"%>
<%@page import="aca.residencia.spring.ResPeriodo"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%	
	List<ResRazon> lisResRazon 	= (List<ResRazon>)request.getAttribute("lisResRazon");	
	List<ResPeriodo> lisPeriodo = (List<ResPeriodo>)request.getAttribute("lisPeriodo");	
	
 	String mensaje		 = (String)request.getAttribute("mensaje");	
 	String grabados		 = (String)request.getAttribute("grabados");	
 	String noGrabados	 = (String)request.getAttribute("noGrabados");	
%>
<body>

<div class="container-fluid">
	<h1>Cycle</h1>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="listado"><i class="fas fa-arrow-left"></i></a>
	</div>
<% 	if(mensaje.equals("1")){%>
	<div class="alert alert-success" role="alert">
  		 <%=grabados%> saved. <%=noGrabados%> missing.
	</div>
<%  }%>
	<form style="padding-left: 10px;" class="form-group" action="grabarTraspaso" name="form" method="post">
		<div class="row">
			<div class="col-sm-12">
				<h2>From</h2>
			</div>
			<div class="col-sm-6">
				<label>Cycle</label>
				<select class="form-control" name="De">
<% 				for(ResPeriodo periodo : lisPeriodo){%>
					<option value="<%=periodo.getPeriodoId()%>"><%=periodo.getPeriodoNombre()%></option>
<% 				}%>
				</select>
			</div>
			<div class="col-sm-6">
				<label>Reason</label>
				<select class="form-control" name="RazonId">
<% 				for(ResRazon razon : lisResRazon){%>
					<option value="<%=razon.getRazon()%>"><%=razon.getDescripcion()%></option>
<% 				}%>
				</select>
			</div>
		</div><br>
		<div class="row">
			<div class="col-sm-12">
				<h2>For</h2>
			</div>
			<div class="col-sm-4">
				<label>Cycle</label>
				<select class="form-control" name="Para">
<% 				for(ResPeriodo periodo : lisPeriodo){%>
					<option value="<%=periodo.getPeriodoId()%>"><%=periodo.getPeriodoNombre()%></option>
<% 				}%>
				</select>
			</div>
		</div><br>
		<div class="row">
			<div class="col-sm-12">
				<button class="btn btn-primary" type="submit">Save</button>
			</div>
		</div>
	</form>
<%	
	
%>
</div>
</body>
