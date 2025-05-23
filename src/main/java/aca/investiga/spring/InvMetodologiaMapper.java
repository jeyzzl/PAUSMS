package aca.investiga.spring;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class InvMetodologiaMapper implements RowMapper<InvMetodologia> {
	
	public InvMetodologia mapRow(ResultSet rs, int rowNum) throws SQLException {
		InvMetodologia obejto = new InvMetodologia();
		
		obejto.setProyectoId(rs.getString("PROYECTO_ID"));
		obejto.setHumanos(rs.getString("HUMANOS"));
		obejto.setDiseno(rs.getString("DISENO"));
		obejto.setMuestra(rs.getString("MUESTRA"));
		obejto.setRecoleccion(rs.getString("RECOLECCION"));
		obejto.setConfidencialidad(rs.getString("CONFIDENCIALIDAD"));
		obejto.setVinculacion(rs.getString("VINCULACION"));
		obejto.setResponsable(rs.getString("RESPONSABLE"));
		obejto.setActividades(rs.getString("ACTIVIDADES"));
		obejto.setEntregable(rs.getString("ENTREGABLE"));
		obejto.setPlan(rs.getString("PLAN"));
		obejto.setOrganizacion(rs.getString("ORGANIZACION"));
		obejto.setProblema(rs.getString("PROBLEMA"));
		obejto.setObjetivo(rs.getString("OBJETIVO"));
		obejto.setHipotesis(rs.getString("HIPOTESIS"));
		obejto.setValidez(rs.getString("VALIDEZ"));
		
		return obejto;
	}

}
