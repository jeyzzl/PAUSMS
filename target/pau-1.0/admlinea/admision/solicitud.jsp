<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.admision.spring.AdmSolicitud"%>
<%@page import="aca.admision.spring.AdmUsuario"%>
<%@page import="aca.admision.spring.AdmAcademico"%>
<%@page import="aca.admision.spring.AdmEstudio"%>
<%@page import="aca.admision.spring.AdmSalud"%>
<%@page import="aca.admision.spring.AdmPadres"%>
<%@page import="aca.admision.spring.AdmTutor"%>
<%@page import="aca.admision.spring.AdmRecomienda"%>
<%@page import="aca.admision.spring.AdmAcomodo"%>
<%@page import="aca.admision.spring.AdmBanco"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%
  String folio            = request.getParameter("Folio")==null?"0":request.getParameter("Folio");

  AdmSolicitud solicitud  = (AdmSolicitud)request.getAttribute("solicitud");
  AdmUsuario usuario      = (AdmUsuario)request.getAttribute("usuario");
  AdmAcademico academico  = (AdmAcademico)request.getAttribute("academico");
  AdmEstudio estudio      = (AdmEstudio)request.getAttribute("estudio");
  AdmSalud salud          = (AdmSalud)request.getAttribute("salud");
  AdmPadres padres        = (AdmPadres)request.getAttribute("padres");
  AdmTutor tutor          = (AdmTutor)request.getAttribute("tutor");
  AdmAcomodo acomodo      = (AdmAcomodo)request.getAttribute("acomodo");
  AdmBanco banco          = (AdmBanco)request.getAttribute("banco");

  HashMap<String, AdmRecomienda> recomienda = (HashMap<String,AdmRecomienda>)request.getAttribute("recomienda");

  String nombreAlumno 		= usuario.getNombre()+" "+(usuario.getApellidoMaterno()==null?"":usuario.getApellidoMaterno())+" "+usuario.getApellidoPaterno();
  String nombreCarrera    = (String)request.getAttribute("nombreCarrera");
  String nombrePlan       = (String)request.getAttribute("nombrePlan");
  String tipoSolicitud    = (String)request.getAttribute("tipoSolicitud");
  String edad             = (String)request.getAttribute("edad");

  String nombreNacionalidad = (String)request.getAttribute("nombreNacionalidad");
  String nombrePais = (String)request.getAttribute("nombrePais");
  String nombreEstado = (String)request.getAttribute("nombreEstado");
  String nombreCiudad = (String)request.getAttribute("nombreCiudad");
  String nombreReligion = (String)request.getAttribute("nombreReligion");
  String nombreModalidad = (String)request.getAttribute("nombreModalidad");
  String educacionPais = (String)request.getAttribute("educacionPais");
  String nombrePadreReligion = (String)request.getAttribute("nombrePadreReligion");
  String nombrePadreNacionalidad = (String)request.getAttribute("nombrePadreNacionalidad");
  String nombreMadreReligion = (String)request.getAttribute("nombreMadreReligion");
  String nombreMadreNacionalidad = (String)request.getAttribute("nombreMadreNacionalidad");
  String nombreResPais = (String)request.getAttribute("nombreResPais");
  String nombreResEstado = (String)request.getAttribute("nombreResEstado");
  String nombreResCiudad = (String)request.getAttribute("nombreResCiudad");

%>

<html>
  <head>
  </head>
  <body>
    <div class="container-fluid">
      <h2>Application Form <small class="text-muted fs-5">( <%=nombreAlumno %> - <%=academico.getCarreraId().equals("00000")?"&nbsp;":academico.getCarreraId()%> - <%=academico.getCarreraId().equals("00000")?"&nbsp;":nombreCarrera%> )</small></h2>
      <div class="alert alert-info">
        <a class="btn btn-primary" href="mostrarProceso?Folio=<%=folio %>"><spring:message code="aca.Regresar"/></a>
      </div>
      <div>
        <h4>Personal Details</h4>
        <table class="my-3 table" width="65%">
          <tr>
            <td width="50%"><b>Name</b></td>
            <td width="50%"><%=solicitud.getNombre()==null?"":solicitud.getNombre()%> <%=solicitud.getApellidoPaterno()==null?"":solicitud.getApellidoPaterno()%> <%=solicitud.getApellidoMaterno()==null?"":solicitud.getApellidoMaterno()%></td>
          </tr>
          <tr>
            <td width="50%"><b>Place of birth</b></td>
            <td width="50%"><%=nombrePais+", "+nombreEstado+", "+nombreCiudad%></td>
          </tr>
          <tr>
            <td width="50%"><b>Nationality</b></td>
            <td width="50%"><%=nombreNacionalidad%></td>
          </tr>
          <tr>
            <td width="50%"><b>Date of birth</b></td>
            <td width="50%"><%=solicitud.getFechaNac()%></td>
          </tr>
          <tr>
            <td width="50%"><b>Age</b></td>
            <td width="50%"><%=edad%></td>
          </tr>
          <tr>
            <td width="50%"><b>Marital Status</b></td>
            <td width="50%"><%=solicitud.getEstadoCivil().equals("C")?"Married":"Single"%></td>
          </tr>
          <tr>
            <td width="50%"><b>Gender</b></td>
            <td width="50%"><%=solicitud.getGenero().equals("M")?"Male":"Female"%></td>
          </tr>
          <tr>
            <td width="50%"><b>Denomination</b></td>
            <td width="50%"><%=nombreReligion%></td>
          </tr>
          <tr>
            <td width="50%"><b>Home Residence</b></td>
            <td width="50%"><%=nombreResPais+", "+nombreResEstado+", "+nombreResCiudad%></td>
          </tr>
          <tr>
            <td width="50%"><b>Phone</b></td>
            <td width="50%"><%=solicitud.getTelefono()%></td>
          </tr>
          <tr>
            <td width="50%"><b>Email</b></td>
            <td width="50%"><%=usuario.getEmail()%></td>
          </tr>         
        </table>
        <br>
        <h4>Course Information</h4>
        <table class="my-3 table" width="65%">
          <tr>
            <td width="50%"><b>Selected Modality</b></td>
            <td width="50%"><%=nombreModalidad%></td>
          </tr>
          <tr>
            <td width="50%"><b>Selected Course</b></td>
            <td width="50%"><%=nombrePlan%></td>
          </tr>
          <tr>
            <td width="50%"><b>Year and Cycle</b></td>
            <td width="50%"><%=academico.getFecha()%></td>
          </tr>
          <tr>
            <td width="50%"><b>Application Type</b></td>
            <td width="50%"><%=tipoSolicitud%></td>
          </tr>
        </table>
        <br>
        <h4>Educational Background</h4>
        <table class="my-3 table" width="65%">
          <tr>
            <td width="50%"><b>Achievement</b></td>
            <td width="50%"><%=estudio.getTitulo()%></td>
          </tr>
          <tr>
            <td width="50%"><b>Institution</b></td>
            <td width="50%"><%=estudio.getInstitucion()%></td>
          </tr>
          <tr>
            <td width="50%"><b>Country</b></td>
            <td width="50%"><%=educacionPais%></td>
          </tr>
          <tr>
            <td width="50%"><b>Year</b></td>
            <td width="50%"><%=estudio.getFin()%></td>
          </tr>
        </table>
        <br>
        <h4>Preferred Accommodation</h4>
        <table class="my-3 table" width="65%">
          <tr>
            <td width="50%"><b>Accommodation</b></td>
            <td width="50%"><%=acomodo.getAcomodoNombre()%></td>
          </tr>
        </table>
        <h4>Medical Information</h4>
        <table class="my-3 table" width="65%">
          <tr>
            <td width="50%"><b>Chornic Illness</b></td>
            <td width="50%"><%=salud.getEnfermedad().equals("S")?salud.getEnfermedadDato():"<span class='badge rounded bg-warning'>None</span>"%></td>
          </tr>
          <tr>
            <td width="50%"><b>Physical Impediment</b></td>
            <td width="50%"><%=salud.getImpedimento().equals("S")?salud.getImpedimentoDato():"<span class='badge rounded bg-warning'>None</span>"%></td>
          </tr>
        </table>
        <br>
        <h4>Parental Information</h4>
        <table class="my-3 table" width="65%">
          <tr>
            <td colspan="2" class=""><b><i>Father</i></b></td>
          </tr>
          <tr>
            <td width="50%"><b>Name</b></td>
            <td width="50%"><%=padres.getPadreNombre()+" "+padres.getPadreApellido()%></td>
          </tr>
          <tr>
            <td width="50%"><b>Denomination</b></td>
            <td width="50%"><%=nombrePadreReligion%></td>
          </tr>
          <tr>
            <td width="50%"><b>Nationality</b></td>
            <td width="50%"><%=nombrePadreNacionalidad%></td>
          </tr>
          <tr>
            <td width="50%"><b>Occupation</b></td>
            <td width="50%"><%=padres.getPadreOcupacion()%></td>
          </tr>
          <tr>
            <td colspan="2">
&nbsp;
            </td>
          </tr>
          <tr>
            <td colspan="2"><b><i>Mother</i></b></td>
          </tr>
          <tr>
            <td width="50%"><b>Name</b></td>
            <td width="50%"><%=padres.getMadreNombre()+" "+padres.getMadreApellido()%></td>
          </tr>
          <tr>
            <td width="50%"><b>Denomination</b></td>
            <td width="50%"><%=nombreMadreReligion%></td>
          </tr>
          <tr>
            <td width="50%"><b>Nationality</b></td>
            <td width="50%"><%=nombreMadreNacionalidad%></td>
          </tr>
          <tr>
            <td width="50%"><b>Occupation</b></td>
            <td width="50%"><%=padres.getMadreOcupacion()%></td>
          </tr>
        </table>
        <br>
        <h4>Guardian Information</h4>
        <table class="my-3 table" width="65%">
          <tr>
            <td width="50%"><b>Type</b></td>
            <td width="50%"><span class="badge rounded bg-info"><%=tutor.getTutor().equals("2")?"Other":tutor.getTutor().equals("0")?"Father":"Mother"%></span></td>
          </tr>
<%  if(tutor.getTutor().equals("2")){%>
          <tr>
            <td width="50%"><b>Name</b></td>
            <td width="50%"><%=tutor.getNombre()%></td>
          </tr>
          <tr>
            <td width="50%"><b>Country</b></td>
            <td width="50%"><%=tutor.getPaisId()%></td>
          </tr>
<%  }%>
          <tr>
            <td width="50%"><b>Phone Number</b></td>
            <td width="50%"><%=tutor.getTelefono()%></td>
          </tr>
        </table>
        <br>
        <h4>Referees</h4>
        <table class="my-3 table" width="65%">
<% if(recomienda.containsKey(folio+"1")) { %>
          <tr>
            <td colspan="2" class=""><b><i>Referee 1</i></b></td>
          </tr>
          <tr>
            <td width="50%"><b>Name</b></td>
            <td width="50%"><%=recomienda.get(folio+"1").getNombre()%></td>
          </tr>
          <tr>
            <td width="50%"><b>Email</b></td>
            <td width="50%"><%=recomienda.get(folio+"1").getEmail()%></td>
          </tr>
          <tr>
            <td width="50%"><b>Phone</b></td>
            <td width="50%"><%=recomienda.get(folio+"1").getTelefono()%></td>
          </tr>
          <tr>
            <td width="50%"><b>Address</b></td>
            <td width="50%"><%=recomienda.get(folio+"1").getDireccion()%></td>
          </tr>
          <tr>
            <td colspan="2">
&nbsp;
            </td>
          </tr>
<% }else { %>
          <tr>
            <td colspan="2"><span class="badge bg-warning">MISSING REFEREE!</span></td>
          </tr>
<% }%>
<% if(recomienda.containsKey(folio+"2")) {%>
          <tr>
            <td colspan="2"><b><i>Referee 2</i></b></td>
          </tr>
          <tr>
            <td width="50%"><b>Name</b></td>
            <td width="50%"><%=recomienda.get(folio+"2").getNombre()%></td>
          </tr>
          <tr>
            <td width="50%"><b>Email</b></td>
            <td width="50%"><%=recomienda.get(folio+"2").getEmail()%></td>
          </tr>
          <tr>
            <td width="50%"><b>Phone</b></td>
            <td width="50%"><%=recomienda.get(folio+"2").getTelefono()%></td>
          </tr>
          <tr>
            <td width="50%"><b>Address</b></td>
            <td width="50%"><%=recomienda.get(folio+"2").getDireccion()%></td>
          </tr>
<% } else {%>
          <tr>
            <td colspan="2"><b><i>Referee 2</i></b></td>
          </tr>
          <tr>
            <td colspan="2"><span class="badge bg-danger">MISSING REFEREE!</span></td>
          </tr>
<% }%>
        </table>
        <br>
        <h4>Bank Information</h4>
        <table class="my-3 table" width="65%">
          <tr>
            <td width="50%"><b>Bank Name</b></td>
            <td width="50%"><%=banco.getBanco()%></td>
          </tr>
          <tr>
            <td width="50%"><b>Bank Branch</b></td>
            <td width="50%"><%=banco.getBancoRama()%></td>
          </tr>
          <tr>
            <td width="50%"><b>Account Name</b></td>
            <td width="50%"><%=banco.getCuentaNombre()%></td>
          </tr>
          <tr>
            <td width="50%"><b>Account Number</b></td>
            <td width="50%"><%=banco.getCuentaNumero()%></td>
          </tr>
          <tr>
            <td width="50%"><b>Account Type</b></td>
            <td width="50%"><%=banco.getCuentaTipo()%></td>
          </tr>
        </table>
      </div>
    </div>
  </body>
</html>