package es.uco.iw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

import es.uco.iw.negocio.usuario.Rol;
import  es.uco.iw.negocio.usuario.UsuarioDTO;

public class UsuarioDAO extends DAO {

    /**
     * Constructor de DAO que carga los ficheros properties
     * 
     * @param url URL de la base de datos
     * @param user Usuario de la base de datos
     * @param pwd Contrase�a de la base de datos
     * @param sqlProp Fichero Properties con las consultas sql
     */
    public UsuarioDAO(String url, String user, String pwd, Properties sqlProp) {
    	super(url, user, pwd, sqlProp);
    }

    /**
     * Busca al usuario de la base de datos cuyo dni coincide con el dado
     * 
     * @param dni Dni del contacto a buscar
     * @return Usuario cuyo email coincide con el dado
     */
    public UsuarioDTO QueryByDni(String dni) {
        UsuarioDTO usuario = null;

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Usuario");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, dni);
            ResultSet set = stmt.executeQuery();
            
            if (set.next()) {
            	Rol rol = Rol.valueOf(set.getString(6));
            	ArrayList<String> cuentasBancarias = new ArrayList<String>();
            	ArrayList<String> tarjetas = new ArrayList<String>();
            	
            	if (rol.equals(Rol.Cliente)) {
            		statement = sqlProp.getProperty("Select_Usuario_Cuenta_Bancaria");
            		PreparedStatement stmtCuentaBancaria = con.prepareStatement(statement);
            		stmtCuentaBancaria.setString(1, dni);            		
                    ResultSet setCuentabancaria = stmtCuentaBancaria.executeQuery();
                    
                    if (setCuentabancaria.next()) {
                    	Collections.addAll(cuentasBancarias, setCuentabancaria.getString(1).split(","));
                    }
                    
                    statement = sqlProp.getProperty("Select_Usuario_Tarjeta");
                    PreparedStatement stmtTarjeta = con.prepareStatement(statement);
                    stmtTarjeta.setString(1, dni);
                    ResultSet setTarjeta = stmtTarjeta.executeQuery();
                    
                    if (setTarjeta.next()) {
                    	Collections.addAll(tarjetas, setTarjeta.getString(1).split(","));
                    }
            	}
            	
            	usuario = new UsuarioDTO(dni, set.getString(1), set.getString(2), set.getString(3), set.getString(4), set.getInt(5), rol, cuentasBancarias, tarjetas);               
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        return usuario;
    }

    /**
     * Busca al usuario de la base de datos cuyo dni coincide con el dado para comprobar su contraseña
     * 
     * @param dni Dni del usuario a buscar
     * @return Usuario cuyo email coincide con el dado
     */
    public UsuarioDTO QueryByPassword(String dni) {
        UsuarioDTO usuario = null;

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Usuario_Password");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, dni);
            ResultSet set = stmt.executeQuery();

            if (set.next()) {
            	usuario = new UsuarioDTO(set.getString(1), set.getString(2));
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        return usuario;
    }

    /**
     * Inserta un usuario en la base de datos
     * 
     * @param usuario Usuario a introducir en la base de datos
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int Insert(UsuarioDTO usuario) {
        int status = 0;

        try {
            // Falta la direccion, tarjetas y cuentas
            String statement = sqlProp.getProperty("Insert_Usuario");
        	Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, usuario.getDni());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getApellidos());
            stmt.setString(4, usuario.getPassword());
            stmt.setString(5, usuario.getEmail());
            stmt.setString(6, usuario.getDireccion());
            stmt.setInt(7, usuario.getTelefono());
            stmt.setString(8, usuario.getRol().toString());
            status = stmt.executeUpdate();
            
            if (stmt != null) {
            	stmt.close();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return status;
    }

    /**
     * Actualiza un usuario de la base de datos
     * 
     * @param usuario Usuario a actualizar
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int Update(UsuarioDTO usuario) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Update_Usuario");
        	Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(6, usuario.getDni());
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellidos());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getDireccion());
            stmt.setInt(5, usuario.getTelefono());
            status = stmt.executeUpdate();
            
            if (stmt != null) {
            	stmt.close();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return status;
    }

    /**
     * Actualiza la contraseña de un usuario
     * 
     * @param ususario Usuario a actualizar
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int UpdatePassword(UsuarioDTO usuario) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Update_Password");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(2, usuario.getDni());
            stmt.setString(1, usuario.getPassword());
            status = stmt.executeUpdate();
            
            if (stmt != null) {
            	stmt.close();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return status;
    }

    /**
     * Borra un usuario de la base de datos
     * 
     * @param dni Dni del usuario a borrar
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int Delete(String dni) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Delete_Usuario");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, dni);
            status = stmt.executeUpdate();
            
            if (stmt != null) {
            	stmt.close();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return status;
    }

}

