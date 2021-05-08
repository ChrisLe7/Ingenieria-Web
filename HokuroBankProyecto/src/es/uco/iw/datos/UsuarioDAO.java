package es.uco.iw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

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
        UsuarioDTO contact = null;

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Usuario");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, dni);
            ResultSet set = stmt.executeQuery();
            
            if (set.next()) {
                // La direccion no esta en la base de datos
                // Las tarjetas y cuentas bancarias estan por ver como guardarlas todavia
            	contact = new UsuarioDTO(dni, set.getString(2), set.getString(3), set.getString(5), "", set.getInt(6), new ArrayList<String>(), new ArrayList<String>());               
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        return contact;
    }

    /**
     * Busca al usuario de la base de datos cuyo dni coincide con el dado para comprobar su contraseña
     * 
     * @param dni Dni del usuario a buscar
     * @return Usuario cuyo email coincide con el dado
     */
    public UsuarioDTO QueryByPassword(String dni) {
        UsuarioDTO contact = null;

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Usuario_Password");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, dni);
            ResultSet set = stmt.executeQuery();

            if (set.next()) {
            	contact = new UsuarioDTO(set.getString(1));
                contact.setPassword(set.getString(2));
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        return contact;
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
            stmt.setInt(6, usuario.getTelefono());
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
            stmt.setString(5, usuario.getDni());
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellidos());
            stmt.setString(3, usuario.getEmail());
            stmt.setInt(4, usuario.getTelefono());
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

