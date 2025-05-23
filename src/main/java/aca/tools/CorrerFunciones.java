package aca.tools;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CorrerFunciones {

	public static void main(String[] args) {

		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {
			Connection Conn = null;
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:9000:xe", "enoc",
					"caminacondios");
			PreparedStatement ps = null;
			ResultSet rs = null;

			int contTot = 0;
			int contBien = 0;
			int contMal = 0;
			String linea;
			String comando = "";
			try {
				archivo = new File(
						"C:\\Users\\elier\\Dropbox\\Sistemas UM\\UNAV\\2. Cambios a funciones.sql");
				fr = new FileReader(archivo);
				br = new BufferedReader(fr);

				while ((linea = br.readLine()) != null) {
					if (linea.contains("END;")) {
						contTot++;
						comando += linea + " \n";

						ps = Conn.prepareStatement(comando);
						rs = ps.executeQuery();

						contBien++;
						System.out.println(comando);
						System.out.println(contBien + " de " + contTot + " MAL:" + contMal);

						comando = "";
					} else {
						comando += linea + " \n";
					}
				}
			} catch (Exception e) {
				System.out.println("Errores: " + comando);
				contMal++;
				System.out.println(e);
			} finally {
				try {
					if (null != fr) {
						fr.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

			if (Conn != null)
				Conn.close();

		} catch (Exception e) {
		}
		System.out.println("YA TERMINO ERY!!");
	}

}
