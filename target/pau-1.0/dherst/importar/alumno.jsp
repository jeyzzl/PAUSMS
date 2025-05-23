<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@page import="aca.dherst.spring.DherstAlumno"%>
<%@page import="aca.catalogo.spring.CatEstado"%>
<%@page import="aca.catalogo.spring.CatReligion"%>
<%@page import="aca.plan.spring.MapaPlan"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%

    DherstAlumno alumno = (DherstAlumno) request.getAttribute("alumno");
    String folio        = (String) request.getAttribute("folio");

    List<CatEstado> lisEstados      = (List<CatEstado>) request.getAttribute("lisEstados");
    List<CatReligion> lisReligiones = (List<CatReligion>) request.getAttribute("lisReligiones");
    List<MapaPlan> lisPlanes        = (List<MapaPlan>) request.getAttribute("lisPlanes");

    String mensaje = (String) request.getParameter("mensaje");
    String textoMensaje = "";

    if(mensaje != null){
        if(mensaje.equals("1")) textoMensaje = "Updated";
        if(mensaje.equals("2")) textoMensaje = "Error Updating";
    }

%>
<head>
    <script>
        function revisa(){
            if(document.getElementById("nombre").value != "" && document.getElementById("nombre").value != "-"){
                if(document.getElementById("apellido").value != "" && document.getElementById("apellido").value != "-"){
                    if(document.getElementById("direccion").value != "" && document.getElementById("direccion").value != "-"){
                        if(document.getElementById("email").value != "" && document.getElementById("email").value != "-"){
                            if(document.getElementById("telefono").value != "" && document.getElementById("telefono").value != "-"){
                                if(document.getElementById("celular").value != "" && document.getElementById("celular").value != "-"){
                                    if(document.getElementById("gpa").value != "" && document.getElementById("gpa").value != "0"){
                                        if(document.getElementById("sexo").value != "" && document.getElementById("sexo").value != "-"){
                                            if(document.getElementById("residencia").value != "" && document.getElementById("residencia").value != "-"){
                                                if(document.getElementById("resEstadoId").value != "" && document.getElementById("resEstadoId").value != "0"){
                                                    if(document.getElementById("estadoId").value != "" && document.getElementById("estadoId").value != "0"){
                                                        if(document.getElementById("religionId").value != "" && document.getElementById("religionId").value != "0"){
                                                            if(document.getElementById("prefAeropuerto").value != "" && document.getElementById("prefAeropuerto").value != "-"){
                                                                if(document.getElementById("estadoCivil").value != "" && document.getElementById("estadoCivil").value != "-"){
                                                                    if(document.getElementById("residenciaTipo").value != "" && document.getElementById("residenciaTipo").value != "-"){
                                                                        if(document.getElementById("planId").value != "" && document.getElementById("planId").value != "-"){
                                                                            return true;
                                                                        }else{
                                                                            alert("Course required");
                                                                            document.getElementById("planId").focus();
                                                                        }
                                                                    }else{
                                                                        alert("Residential required");
                                                                        document.getElementById("residenciaTipo").focus();
                                                                    }                                                            
                                                                }else{
                                                                    alert("Marital Status required");
                                                                    document.getElementById("estadoCivil").focus();
                                                                }
                                                            }else{
                                                                alert("Preferred Airport required");
                                                                document.getElementById("prefAeropuerto").focus();
                                                            }
                                                        }else{
                                                            alert("Religion required");
                                                            document.getElementById("religionId").focus();
                                                        }
                                                    }else{
                                                        alert("Living Province required");
                                                        document.getElementById("estadoId").focus();
                                                    }
                                                }else{
                                                    alert("Home Province required");
                                                    document.getElementById("resEstadoId").focus();
                                                }
                                            }else{
                                                alert("Residence required");
                                                document.getElementById("residencia").focus();
                                            }
                                        }else{
                                            alert("Gender required");
                                            document.getElementById("sexo").focus();
                                        }
                                    }else{
                                        alert("GPA required");
                                        document.getElementById("gpa").focus();
                                    }
                                }else{
                                    alert("Mobile required");
                                    document.getElementById("celular").focus();
                                }
                            }else{
                                alert("Phone required");
                                document.getElementById("telefono").focus();
                            }
                        }else{
                            alert("Email required");
                            document.getElementById("email").focus();
                        }
                    }else{
                        alert("Address required");
                        document.getElementById("direccion").focus();
                    }
                }else{
				    alert("Surname required");
				    document.getElementById("apellido").focus();
                }
			}else{
				alert("Name required");
				document.getElementById("nombre").focus();
			}

			return false;
        }

        function grabar(){
			if(revisa()){
				document.frmAlumno.submit();
			}
		}
    </script>
</head>
<body>
<div class="container-fluid">
    <h2>Edit student</h2>
    <div class="alert alert-info">
    	<a class="btn btn-primary me-4" href="subirArchivo?folio=<%=folio%>">Return</a>
        <%=textoMensaje%>
	</div>
    <form action="guardarAlumno" method="post" name="frmAlumno">
        <div class="d-flex">
            <div class="me-5">
                <input type="hidden" id="folio" name="folio" value="<%=folio%>">
                <label for="codigoPersonal">Student ID</label>
                <input type="text" id="codigoPersonal" class="form-control mb-3" style="width: 15rem;" name="codigoPersonal" value="<%=alumno.getCodigoPersonal()%>" readonly>
                <label for="slfNo">Slf. No.</label>
                <input type="text" id="slfNo" class="form-control mb-3" style="width: 15rem;" name="slfNo" value="<%=alumno.getSlfNo()%>" readonly>
                <label for="nombre">Name</label>
                <input type="text" id="nombre" class="form-control mb-3" style="width: 30rem;" name="nombre" value="<%=alumno.getNombre()%>">
                <label for="apellido">Surname</label>
                <input type="text" id="apellido" class="form-control mb-3" style="width: 30rem;" name="apellido" value="<%=alumno.getApellido()%>">
                <label for="direccion">Address</label>
                <input type="text" id="direccion" class="form-control mb-3" style="width: 30rem;" name="direccion" value="<%=alumno.getDireccion()%>">
                <label for="email">Email</label>
                <input type="text" id="email" class="form-control mb-3" style="width: 30rem;" name="email" value="<%=alumno.getEmail()%>">
                <label for="telefono">Phone</label>
                <input type="text" id="telefono" class="form-control mb-3" style="width: 15rem;" name="telefono" value="<%=alumno.getTelefono()%>">
                <label for="celular">Mobile</label>
                <input type="text" id="celular" class="form-control mb-3" style="width: 15rem;" name="celular" value="<%=alumno.getCelular()%>">
                <label for="gpa">GPA</label>
                <input type="text" id="gpa" class="form-control mb-3" style="width: 10rem;" name="gpa" value="<%=alumno.getGpa()%>" >
                <label for="sexo">Gender</label>
                <select name="sexo" id="sexo" class="form-select mb-3" style="width: 15rem;">
                    <option value="M" <%=alumno.getSexo().equals("M")?"selected":""%>>Male</option>
                    <option value="F" <%=alumno.getSexo().equals("F")?"selected":""%>>Female</option>
                </select>
            </div>
            <div class="">
                <label for="residencia">Residence</label>
               <select name="residencia" id="residencia" class="form-select mb-3" style="width: 15rem;">
                    <option value="DST" <%=alumno.getResidencia().equals("DST")?"selected":""%>>DST</option>
                    <option value="MD" <%=alumno.getResidencia().equals("MD")?"selected":""%>>MD</option>
                    <option value="LD" <%=alumno.getResidencia().equals("LD")?"selected":""%>>LD</option>
                    <option value="DSTN" <%=alumno.getResidencia().equals("DSTN")?"selected":""%>>DSTN</option>
                </select>
                <label for="resEstadoId">Home Province</label>
                <select name="resEstadoId" id="resEstadoId" class="form-select mb-3" style="width: 15rem;">
                    <option value="0">N/A</option>
<%                  for(CatEstado estado : lisEstados){%>
                    <option value="<%=estado.getEstadoId()%>" <%=alumno.getResEstadoId().equals(estado.getEstadoId())?"selected":""%>><%=estado.getNombreEstado()%></option>
<%                  }%>
                </select>
                <label for="estadoId">Living Province</label>
                <select name="estadoId" id="estadoId" class="form-select mb-3" style="width: 15rem;">
                    <option value="0">N/A</option>
<%                  for(CatEstado estado : lisEstados){%>
                    <option value="<%=estado.getEstadoId()%>" <%=alumno.getEstadoId().equals(estado.getEstadoId())?"selected":""%>><%=estado.getNombreEstado()%></option>
<%                  }%>
                </select>
                <label for="religionId">Religion</label>
                <select name="religionId" id="religionId" class="form-select mb-3" style="width: 15rem;">
                    <option value="0">N/A</option>
<%                  for(CatReligion religion : lisReligiones){%>
                    <option value="<%=religion.getReligionId()%>" <%=alumno.getReligionId().equals(religion.getReligionId())?"selected":""%>><%=religion.getNombreReligion()%></option>
<%                  }%>
                </select>                
                <label for="prefAeropuerto">Preferred Airport</label>
                <input type="text" id="prefAeropuerto" class="form-control mb-3" style="width: 30rem;" name="prefAeropuerto" value="<%=alumno.getPrefAeropuerto()%>">
                <label for="estadoCivil">Marital Status</label>
                <select name="estadoCivil" id="estadoCivil" class="form-select mb-3" style="width: 15rem;">
                    <option value="-">N/A</option>
                    <option value="S" <%=alumno.getEstadoCivil().equals("S")?"selected":""%>>Single / Single Parent</option>
                    <option value="C" <%=alumno.getEstadoCivil().equals("C")?"selected":""%>>Married</option>
                    <option value="D" <%=alumno.getEstadoCivil().equals("D")?"selected":""%>>Divorced</option>
                </select>
                <label for="residenciaTipo">Residential</label>
                <select name="residenciaTipo" id="residenciaTipo" class="form-select mb-3" style="width: 15rem;">
                    <option value="I" <%=alumno.getResidenciaTipo().equals("I")?"selected":""%>>Residential</option>
                    <option value="E" <%=alumno.getResidenciaTipo().equals("E")?"selected":""%>>Non-residential</option>
                </select>
                <label for="planId">Course</label>
                <select name="planId" id="planId" class="form-select mb-3">
                    <option value="-">N/A</option>
<%                  for(MapaPlan plan : lisPlanes){%>
                    <option value="<%=plan.getPlanId()%>" <%=alumno.getPlanId().equals(plan.getPlanId())?"selected":""%>><%=plan.getNombrePlan()%></option>
<%                  }%>
                </select>  
                <label for="status">Status</label>
                <input type="text" id="status" class="form-control mb-3" style="width: 10rem;" name="status" value="<%=alumno.getStatus()%>" readonly>
            </div>
        </div>
        <button type="button" class="btn btn-primary" onClick="grabar();">Update</button>
        
    </form>
</div>
</body>