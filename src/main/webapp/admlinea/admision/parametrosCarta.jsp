<%@ page import="java.text.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.admision.spring.AdmCartaFulton"%>
<%@ page import="aca.admision.spring.AdmCartaSonoma"%>
<%@ page import="aca.admision.spring.AdmCartaPau"%>
<%@ page import="aca.parametros.spring.Parametros"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<%  

    Parametros parametros           = (Parametros)request.getAttribute("parametros");
    boolean existeCarta             = (boolean)request.getAttribute("existeCarta");
	AdmCartaFulton admCartaFulton   = (AdmCartaFulton)request.getAttribute("admCartaFulton");
    AdmCartaSonoma admCartaSonoma   = (AdmCartaSonoma)request.getAttribute("admCartaSonoma");
    AdmCartaPau admCartaPau         = (AdmCartaPau)request.getAttribute("admCartaPau");
%>
<div class="container-fluid">
    <h2>Acceptance Letter Parameters</h2>
    <div class="alert alert-info">
    </div>
<!-- FORMULARIO PARA PAU-->
<% if(parametros.getInstitucion().equals("Pacific Adventist University")){%>
    <form name="frmPau" id="frmArchivo" action="grabarPau" method="post">
        <input type="hidden" name="cartaId" id="cartaId" value="<%=admCartaPau.getCartaId()%>">
        <label for="fechaRegistro"><b>Registration Date</b></label>
        <input type="text" name="fechaRegistro" id="fechaRegistro" data-date-format="dd/mm/yyyy" value="<%=admCartaPau.getFechaRegistro()%>" style="width: 14rem;" class="form-control" maxlength="10" placeholder="DD/MM/YYYY"/>
        <br>
        <label for="fechaOrientacion"><b>Orientation Date</b></label>
        <input type="text" name="fechaOrientacion" id="fechaOrientacion" data-date-format="dd/mm/yyyy" value="<%=admCartaPau.getFechaOrientacion()%>" style="width: 14rem;" class="form-control" maxlength="10" placeholder="DD/MM/YYYY"/>
        <br>
        <label for="fechaApertura"><b>Opening & Connvocation Date</b></label>
        <input type="text" name="fechaApertura" id="fechaApertura" data-date-format="dd/mm/yyyy" value="<%=admCartaPau.getFechaApertura()%>" style="width: 14rem;" class="form-control" maxlength="10" placeholder="DD/MM/YYYY"/>
        <br>
        <label for="fechaInicio"><b>Classes Commencement Date</b></label>
        <input type="text" name="fechaInicio" id="fechaInicio" data-date-format="dd/mm/yyyy" value="<%=admCartaPau.getFechaInicio()%>" style="width: 14rem;" class="form-control" maxlength="10" placeholder="DD/MM/YYYY"/>
        <br>
        <label for="fechaCierre"><b>Final Information Date</b></label>
        <input type="text" name="fechaCierre" id="fechaCierre" data-date-format="dd/mm/yyyy" value="<%=admCartaPau.getFechaCierre()%>" style="width: 14rem;" class="form-control" maxlength="10" placeholder="DD/MM/YYYY"/>
        <br>
        <div class="alert alert-info">
            <button type="submit" name="btnGuardar" id="btnGuardar" value="Enviar" class="btn btn-primary">Save</button>
<%      if(existeCarta){%>
            <a href="borrarPau?cartaId=<%=admCartaPau.getCartaId()%>" class="btn btn-danger">Delete</a>
<%      }%>
        </div>
    </form>
<% }%>
<!-- FORMULARIO PARA SONOMA-->
<% if(parametros.getInstitucion().equals("Sonoma")){%>
    <form name="frmSonoma" id="frmArchivo" action="grabarSonoma" method="post">
        <input type="hidden" name="cartaId" id="cartaId" value="<%=admCartaSonoma.getCartaId()%>">
        <label for="fechaFinal"><b>Payment Due Date</b></label>
        <input type="text" name="fechaFinal" id="fechaFinal" data-date-format="dd/mm/yyyy" value="<%=admCartaSonoma.getFechaFinal()%>" style="width: 14rem;" class="form-control" maxlength="10" placeholder="DD/MM/YYYY"/>
        <br>
        <div class="alert alert-info">
            <button type="submit" name="btnGuardar" id="btnGuardar" value="Enviar" class="btn btn-primary">Save</button>
<%      if(existeCarta){%>
            <a href="borrarSonoma?cartaId=<%=admCartaSonoma.getCartaId()%>" class="btn btn-danger">Delete</a>
<%      }%>
        </div>
    </form>
<% }%>
<!-- FORMULARIO PARA FULTON--> 
<% if(parametros.getInstitucion().equals("Fulton")){%>
    <form name="frmFulton" id="frmArchivo" action="grabarFulton" method="post">
        <input type="hidden" name="cartaId" id="cartaId" value="<%=admCartaFulton.getCartaId()%>">
        <label for="fechaRegistroFulton"><b>Orientation Date</b></label>
        <input type="text" name="fechaRegistroFulton" id="fechaRegistroFulton" data-date-format="dd/mm/yyyy" value="<%=admCartaFulton.getFechaRegistro()%>" style="width: 14rem;" class="form-control" maxlength="10" placeholder="DD/MM/YYYY"/>
        <br>
        <label for="fechaOrientacionFulton"><b>Opening & Connvocation Date</b></label>
        <input type="text" name="fechaOrientacionFulton" id="fechaOrientacionFulton" data-date-format="dd/mm/yyyy" value="<%=admCartaFulton.getFechaOrientacion()%>" style="width: 14rem;" class="form-control" maxlength="10" placeholder="DD/MM/YYYY"/>
        <br>
        <label for="fechaInicioFulton"><b>Classes Commencement Date</b></label>
        <input type="text" name="fechaInicioFulton" id="fechaInicioFulton" data-date-format="dd/mm/yyyy" value="<%=admCartaFulton.getFechaInicio()%>" style="width: 14rem;" class="form-control" maxlength="10" placeholder="DD/MM/YYYY"/>
        <br>
        <label for="fechaCierreFulton"><b>Final Information Date</b></label>
        <input type="text" name="fechaCierreFulton" id="fechaCierreFulton" data-date-format="dd/mm/yyyy" value="<%=admCartaFulton.getFechaCierre()%>" style="width: 14rem;" class="form-control" maxlength="10" placeholder="DD/MM/YYYY"/>
        <br>
        <label for="fechaArriboFulton"><b>Arrival Date</b></label>
        <input type="text" name="fechaArriboFulton" id="fechaArriboFulton" data-date-format="dd/mm/yyyy" value="<%=admCartaFulton.getFechaArribo()%>" style="width: 14rem;" class="form-control" maxlength="10" placeholder="DD/MM/YYYY"/>
        <br>
        <div class="alert alert-info container-fluid">
            <button type="submit" name="btnGuardar" id="btnGuardar" data-date-format="dd/mm/yyyy" value="Enviar" class="btn btn-primary me-2">Save</button>
<%      if(existeCarta){%>
            <a href="borrarFulton?cartaId=<%=admCartaFulton.getCartaId()%>" class="btn btn-danger">Delete</a>
<%      }%>
        </div>
    </form>
<% }%>
</div>
<script>
	jQuery('#fechaRegistro').datepicker();
	jQuery('#fechaOrientacion').datepicker();
	jQuery('#fechaApertura').datepicker();
    jQuery('#fechaInicio').datepicker();
    jQuery('#fechaCierre').datepicker();

    jQuery('#fechaFinal').datepicker();

    jQuery('#fechaRegistroFulton').datepicker();
	jQuery('#fechaOrientacionFulton').datepicker();
    jQuery('#fechaInicioFulton').datepicker();
    jQuery('#fechaCierreFulton').datepicker();
    jQuery('#fechaArriboFulton').datepicker();
</script>