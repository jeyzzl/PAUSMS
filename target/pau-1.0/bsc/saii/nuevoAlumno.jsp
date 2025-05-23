<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.saii.spring.SaiiAlumno"%>
<%@page import="aca.catalogo.spring.CatPais"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<%
	SaiiAlumno alumno			= (SaiiAlumno) request.getAttribute("alumno");
    String periodoId 			= (String) request.getAttribute("periodoId");
    String grupoId 				= (String) request.getAttribute("grupoId");
    String planUm 				= (String) request.getAttribute("planUm");
    String mensaje 				= (String) request.getAttribute("mensaje");
    String estado 				= (String) request.getAttribute("estado");
    String fecha 				= (String) request.getAttribute("fecha");
    AlumPersonal alumPersonal	= (AlumPersonal) request.getAttribute("alumPersonal");
   
    boolean tienePlan			= (boolean) request.getAttribute("tienePlan");
    
    HashMap<String,CatPais> mapaCatPais = (HashMap<String,CatPais>) request.getAttribute("mapaCatPais");
    
    String pais = "";
    
    if(mapaCatPais.containsKey(alumno.getPaisId())){
    	pais = mapaCatPais.get(alumno.getPaisId()).getNombrePais();
    }
    
    if(alumno.getGenero().equals("H")){
    	alumno.setGenero("M");
    }
%>
<head>
</head>
<body>
<div class="container-fluid">
	<h2>Nuevo<small class="text-muted fs-4"> ( <%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoPaterno()%> <%=alumPersonal.getApellidoMaterno()%> - <%=alumPersonal.getCodigoPersonal()%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="alumnos?PeriodoId=<%=periodoId%>&GrupoId=<%=grupoId%>&Fecha=<%=fecha%>&PlanId=<%=planUm%>"><i class="fas fa-arrow-left"></i></a>
	</div>
<%  if(mensaje.equals("1")){%>
	<div class="alert alert-success">
		Guardado
	</div>
<%  }else if(mensaje.equals("2")){%>
	<div class="alert alert-danger">
		No se puedo guardar
	</div>
<%  }%>
<%  if(tienePlan){%>
	<form style="padding-left: 20px;" id="forma" name="forma" action="grabarAlumno" method="post">
		<input type="hidden" name="GrupoId" value="<%=grupoId%>"/>
		<input type="hidden" name="PeriodoId" value="<%=periodoId%>"/>
		<input type="hidden" name="Folio" value="<%=alumno.getFolio()%>"/>
		<input type="hidden" name="PlanUm" value="<%=alumno.getPlanUm()%>"/>
		<input type="hidden" name="PlanSep" value="<%=alumno.getPlanSep()%>"/>
		<input type="hidden" name="Fecha" value="<%=fecha%>"/>
		<div class="form-group row">
			<div class="col-sm-3">
				<h3>Nombre: <small class="text-muted fs-5"><%=alumno.getNombre()%> <%=alumno.getPaterno()%> <%=alumno.getMaterno()%></small></h3>
			</div>
			<div class="col-sm-3">
				<h3>Curp: <small class="text-muted fs-5"><%=alumno.getCurp()%></small></h3>
			</div>
			<div class="col-sm-2">
				<h3>Genero: <small class="text-muted fs-5"><%=alumno.getGenero().equals("M") ? "Hombre" : "Mujer"%></small></h3>
			</div>
			<div class="col-sm-3">
				<h3>PlanUm: <small class="text-muted fs-5"><%=alumno.getPlanUm()%></small></h3>
			</div>
		</div>
		<div class="form-group row">
			<div class="col-sm-3">
				<h3>Pais: <small class="text-muted fs-5"><%=pais%></small></h3>
			</div>
			<div class="col-sm-2">
				<h3>Estado: <small class="text-muted fs-5"><%=estado%></small></h3>
			</div>
		</div>
		<div class="form-group row">
			<div class="col-sm-1">
				<label>Ciclo</label>			
				<input class="form-control" type="text" name="Ciclo" value="<%=alumno.getCiclo()%>" required="required"/>
			</div>
			<div class="col-sm-1">
				<label>Grado</label>			
				<input class="form-control" type="text" name="Grado" value="<%=alumno.getGrado()%>" required="required"/>
			</div>
		</div>
		<br>
		<div class="alert alert-info">
			<button class="btn btn-primary" type="submit">Guardar</button>
		</div>
	</form>
<%  }else{%>
	<div class="alert alert-danger">
		Este alumno no tiene este plan
	</div>
<%  }%>
</div>
</body>
