<%@ include file= "../../con_enoc.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="krdxAlumnoEval" scope="page" class="aca.kardex.KrdxAlumnoEval"/><%
	String codigoAlumno = request.getParameter("matricula");
	String cursoCargaId = request.getParameter("cursoCarga");
	String evaluacionId = request.getParameter("evaluacion");
	krdxAlumnoEval.setCodigoPersonal(codigoAlumno);
	krdxAlumnoEval.setCursoCargaId(cursoCargaId);
	krdxAlumnoEval.setEvaluacionId(evaluacionId);
	if(krdxAlumnoEval.existeReg(conEnoc)){
		krdxAlumnoEval.mapeaRegId(conEnoc, codigoAlumno, cursoCargaId, evaluacionId);
		krdxAlumnoEval.setNota(request.getParameter("nota"));
		if(krdxAlumnoEval.updateReg(conEnoc)){
%>
alert("The grade was correctly modified");
<%
		}else{
%>
alert("ERROR while modifying the grade. Try again!");
inputNota.value = notaAnterior;
<%
		}
	}else{
		krdxAlumnoEval.setNota(request.getParameter("nota"));
		krdxAlumnoEval.setEvaluacionE42("0");
		if(krdxAlumnoEval.insertReg(conEnoc)){
%>
alert("Se guardó correctamente la nota");
<%
		}else{
%>
alert("ERROR!!!! al guardar la nota, inténtelo de nuevo");
inputNota.value = notaAnterior;
<%
		}
	}
%>
<%@ include file= "../../cierra_enoc2.jsf" %>