/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aca.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class ModifyColumnas {

	public static ArrayList<String> columnasUM() throws SQLException {
		ArrayList<String> columnas = new ArrayList<String>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String comando = "";

		try {

			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:XE", "enoc",
					"caminacondios");
			stmt = conn.createStatement();
			// 1. Verificar cuales son las columnas que tienen null en el campo
			// DATA_DEFAULT y aplicar el script
			// 2. Verificar cuales son las columnas que tienen NOT NULL en el
			// campo DATA_DEFAULT y hacer las correcciones en el script
			// agregando el valor por DEFAULT
			comando = "SELECT TABLE_NAME, COLUMN_NAME, DATA_TYPE, DATA_LENGTH, COALESCE(DATA_PRECISION,0) AS DATA_PRECISION, NULLABLE, COALESCE(DATA_SCALE,0) AS DATA_SCALE"
					+ " FROM SYS.ALL_TAB_COLUMNS WHERE OWNER = 'NOE' "
					+ " AND TABLE_NAME IN (SELECT DISTINCT(TABLE_NAME) FROM SYS.ALL_ALL_TABLES WHERE OWNER = 'NOE') "
					+ " AND DATA_DEFAULT IS NULL " + " ORDER BY OWNER,1,2";
			rs = stmt.executeQuery(comando);
			while (rs.next()) {

				columnas.add(rs.getString("TABLE_NAME") + ","
						+ rs.getString("COLUMN_NAME") + ","
						+ rs.getString("DATA_TYPE") + ","
						+ rs.getString("DATA_LENGTH") + ","
						+ rs.getString("DATA_PRECISION") + "," + "-" + ","
						+ rs.getString("DATA_SCALE"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}

		return columnas;
	}

	public static HashMap<String, String> columnasUNAV() throws SQLException {
		HashMap<String, String> columnas = new HashMap<String, String>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String comando = "";

		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@192.168.1.8:1521:XE", "enoc",
					"caminacondios");
			stmt = conn.createStatement();

			comando = " SELECT TABLE_NAME, COLUMN_NAME, DATA_TYPE, DATA_LENGTH, COALESCE(DATA_PRECISION,0) AS DATA_PRECISION, NULLABLE, COALESCE(DATA_SCALE,0) AS DATA_SCALE FROM SYS.ALL_TAB_COLUMNS WHERE OWNER = 'NOE'";
			rs = stmt.executeQuery(comando);
			while (rs.next()) {
				columnas.put(
						rs.getString("TABLE_NAME")
								+ rs.getString("COLUMN_NAME"),
						rs.getString("TABLE_NAME") + ","
								+ rs.getString("COLUMN_NAME") + ","
								+ rs.getString("DATA_TYPE") + ","
								+ rs.getString("DATA_LENGTH") + ","
								+ rs.getString("DATA_PRECISION") + ",-,"
								+ rs.getString("DATA_SCALE"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}

		return columnas;
	}

	public static void main(String[] args) throws SQLException {

		ArrayList<String> lisColumnas = columnasUM();
		System.out.println(lisColumnas.size());
		HashMap<String, String> mapColumnas = columnasUNAV();
		System.out.println(mapColumnas.size());
		String modificarColumna = "";

		for (String columna : lisColumnas) {

			String[] arregloCol = columna.split(",");
			if (arregloCol != null)
				// Si no encuentras la columna
				if (mapColumnas.containsKey(arregloCol[0].toString()
						+ arregloCol[1].toString())) {
					String datos = mapColumnas.get(arregloCol[0]
							+ arregloCol[1]);
					if (!datos.equals(arregloCol[0] + "," + arregloCol[1] + ","
							+ arregloCol[2] + "," + arregloCol[3] + ","
							+ arregloCol[4] + ",-," + arregloCol[6])) {

						String[] colUNAV = datos.split(",");

						modificarColumna = "ALTER TABLE NOE." + arregloCol[0]
								+ " MODIFY " + arregloCol[1] + " "
								+ arregloCol[2];
						if (!arregloCol[2].equals("DATE")
								&& !arregloCol[2].equals("BLOB")
								&& !arregloCol[2].equals("NUMBER"))
							modificarColumna += "(" + arregloCol[3] + ")";
						else if (arregloCol[2].equals("NUMBER"))
							modificarColumna += "(" + arregloCol[4] + ","
									+ arregloCol[6] + ")";
						// Solamente si cambió el tipo nullable agrega el dato
						/*
						 * if (!arregloCol[5].equals(colUNAV[5])) if
						 * (arregloCol[5].equals("Y"))
						 * modificarColumna+=" NULL"; else
						 * modificarColumna+=" NOT NULL";
						 */
						modificarColumna += ";";
						System.out.println(modificarColumna);
					} else {
						// System.out.println(modificarColumna);
					}
				}
		}

	}
}