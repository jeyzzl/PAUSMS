<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.catalogo.spring.CatPatrocinador"%>
<%@ page import="aca.catalogo.spring.CatAcomodo"%>
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
    function cambioCarga(){
        var cargaId         = document.getElementById("CargaId").value;
		var grado 		    = document.getElementById("Grado").value;
		var acomodo 	    = document.getElementById("Acomodo").value;
        var patrocinadorId  = document.getElementById("PatrocinadorId").value;

		location.href = "patrocinador?CargaId="+cargaId+"&PatrocinadorId="+patrocinadorId+"&Grado="+grado+"&Acomodo="+acomodo;
	}
</script>
<%
    String cargaId          = (String)request.getAttribute("cargaId");
    String patrocinadorId   = (String)request.getAttribute("patrocinadorId");
    String grado            = (String)request.getAttribute("grado");
    String acomodo          = (String)request.getAttribute("acomodo");
    
    List<Carga> lisCargas                           = (List<Carga>)request.getAttribute("lisCargas");
    List<CatPatrocinador> lisPatrocinadores         = (List<CatPatrocinador>)request.getAttribute("lisPatrocinadores");
    List<AlumPatrocinador> lisAlumPatrocinadores    = (List<AlumPatrocinador>)request.getAttribute("lisAlumPatrocinadores");
    List<CatAcomodo> lisAcomodos                    = (List<CatAcomodo>)request.getAttribute("lisAcomodos");

    HashMap<String,CatPatrocinador> mapPatrocinadores   = (HashMap<String,CatPatrocinador>)request.getAttribute("mapPatrocinadores");
    HashMap<String,AlumPersonal> mapaAlumPersonal       = (HashMap<String,AlumPersonal>)request.getAttribute("mapaAlumPersonal");
    HashMap<String,AlumPlan> mapaAlumPlan               = (HashMap<String,AlumPlan>)request.getAttribute("mapaAlumPlan");
    HashMap<String,AlumAcademico> mapaAlumAcademico     = (HashMap<String,AlumAcademico>)request.getAttribute("mapaAlumAcademico");
    HashMap<String,String> mapaPlan                     = (HashMap<String,String>)request.getAttribute("mapaPlan");
%>

<div class="container-fluid">
    <h2>Assigned Sponsorships</h2>
    <div class="alert alert-info d-flex align-items-center">
        Load:
        <select name="CargaId" id="CargaId" class="form-select ms-1 me-2" style="width:20rem;" onchange="javascript:cambioCarga();">
<%  for(Carga carga: lisCargas){%>
            <option value="<%=carga.getCargaId()%>" <%=cargaId.equals(carga.getCargaId())?"selected":""%>><%=carga.getCargaId()%> - <%=carga.getNombreCarga()%></option>
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
        Residence:
        <select name="Acomodo" id="Acomodo" class="form-select ms-1 me-2" style="width:15rem;" onchange="javascript:cambioCarga();">
            <option value="0" <%=acomodo.equals("0")?"selected":""%>>All</option>
<%  for(CatAcomodo catAcomodo: lisAcomodos){%>
            <option value="<%=catAcomodo.getAcomodoId()%>" <%=acomodo.equals(catAcomodo.getAcomodoId())?"selected":""%>><%=catAcomodo.getNombreCorto()%> - <%=catAcomodo.getNombre()%></option>
<%  }%>
        </select>
        Sponsors:
        <select name="PatrocinadorId" id="PatrocinadorId" class="form-select ms-1 me-2" style="width:15rem;" onchange="javascript:cambioCarga();">
            <option value="0" <%=patrocinadorId.equals("0")?"selected":""%>>All</option>
<%  for(CatPatrocinador patrocinador : lisPatrocinadores){%>
            <option value="<%=patrocinador.getPatrocinadorId()%>" <%=patrocinadorId.equals(patrocinador.getPatrocinadorId())?"selected":""%>><%=patrocinador.getNombrePatrocinador()%></option>
<%  }%>
        </select>
    </div>
    <table class="table table-bordered">
        <thead class="table-info">
            <tr>
                <th>No.</th>
                <th>Student ID</th>
                <th>Name</th>
                <th>Plan ID</th>
                <th>Course</th>
                <th>Year</th>
                <th>Residence</th>
                <th>Sponsor</th>
            </tr>
        </thead>
        <tbody>
<%  int row = 0;
    for(AlumPatrocinador alumPatrocinador : lisAlumPatrocinadores){
        row++;

        CatPatrocinador patrocinador = new CatPatrocinador();
        String nombrePatrocinador = "";
        if(mapPatrocinadores.containsKey(alumPatrocinador.getPatrocinadorId())){
            patrocinador = mapPatrocinadores.get(alumPatrocinador.getPatrocinadorId());
            nombrePatrocinador = patrocinador.getNombrePatrocinador();
        }

        AlumPersonal alumPersonal = new AlumPersonal();
        String nombreAlumno = "";
        if(mapaAlumPersonal.containsKey(alumPatrocinador.getCodigoPersonal())){
            alumPersonal = mapaAlumPersonal.get(alumPatrocinador.getCodigoPersonal());
            nombreAlumno = alumPersonal.getNombre()+" "+alumPersonal.getApellidoMaterno()+" "+alumPersonal.getApellidoPaterno();
        }

        AlumPlan alumPlan = new AlumPlan();
        String nombrePlan = "";
        if(mapaAlumPlan.containsKey(alumPatrocinador.getCodigoPersonal())){
            alumPlan = mapaAlumPlan.get(alumPatrocinador.getCodigoPersonal());
            nombrePlan = mapaPlan.get(alumPlan.getPlanId());
        }

        AlumAcademico alumAcademico =  new AlumAcademico();
        String residencia = "";
        String gradoAlum = "";
        if(mapaAlumAcademico.containsKey(alumPatrocinador.getCodigoPersonal())){
            alumAcademico = mapaAlumAcademico.get(alumPatrocinador.getCodigoPersonal());
            residencia = alumAcademico.getResidenciaId().equals("I")?"Boarding":"Day Student";
            gradoAlum = alumAcademico.getGrado();
        }

        String patrocinadorNombre = mapPatrocinadores.get(alumPatrocinador.getPatrocinadorId())==null?"-":mapPatrocinadores.get(alumPatrocinador.getPatrocinadorId()).getNombrePatrocinador();

%>
            <tr>
                <td><%=row%></td>
                <td><%=alumPatrocinador.getCodigoPersonal()%></td>
                <td><%=nombreAlumno%></td>
                <td><%=alumPlan.getPlanId()%></td>
                <td><%=nombrePlan%></td>
                <td><%=gradoAlum%></td>
                <td><%=residencia%></td>
                <td><%=patrocinadorNombre%></td>
            </tr>
<%  }%>
        </tbody>  
    </table>
</div>