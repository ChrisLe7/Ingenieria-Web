package es.uco.iw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Properties;

import es.uco.iw.negocio.usuario.PropiedadCuenta;
import es.uco.iw.negocio.usuario.RolPropietario;
import es.uco.iw.negocio.usuario.RolUsuario;
import  es.uco.iw.negocio.usuario.UsuarioDTO;
import es.uco.iw.negocio.usuario.UsuarioLoginDTO;

public class UsuarioDAO extends DAO {

    /**
     * Constructor de DAO que carga los ficheros properties
     * 
     * @param url URL de la base de datos
     * @param user Usuario de la base de datos
     * @param pwd ContraseÒa de la base de datos
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
            	ArrayList<PropiedadCuenta> cuentasBancarias = new ArrayList<PropiedadCuenta>();
            	ArrayList<String> tarjetas = new ArrayList<String>();
            	
        		statement = sqlProp.getProperty("Select_Usuario_Cuenta_Bancaria");
        		PreparedStatement stmtCuentaBancaria = con.prepareStatement(statement);
        		stmtCuentaBancaria.setString(1, dni);            		
                ResultSet setCuentabancaria = stmtCuentaBancaria.executeQuery();
                
                while (setCuentabancaria.next()) {
                	cuentasBancarias.add(new PropiedadCuenta(setCuentabancaria.getString(1), RolPropietario.valueOf(setCuentabancaria.getString(2))));
                }
                
                statement = sqlProp.getProperty("Select_Usuario_Tarjeta");
                PreparedStatement stmtTarjeta = con.prepareStatement(statement);
                stmtTarjeta.setString(1, dni);
                ResultSet setTarjeta = stmtTarjeta.executeQuery();
                
                if (setTarjeta.next()) {
                	Collections.addAll(tarjetas, setTarjeta.getString(1).split(","));
                }
            	
            	usuario = new UsuarioDTO(dni, set.getString(1), set.getString(2), set.getString(3), set.getString(4), set.getInt(5), cuentasBancarias, tarjetas);               
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
     * Busca al usuario de la base de datos cuyo dni coincide con el dado para comprobar su contrase√±a y tol
     * 
     * @param dni Dni del usuario a buscar
     * @return Usuario cuyo email coincide con el dado
     */
    public UsuarioLoginDTO QueryByPassword(String dni) {
    	UsuarioLoginDTO usuario = null;

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Password");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, dni);
            ResultSet set = stmt.executeQuery();

            if (set.next()) {
            	usuario = new UsuarioLoginDTO(dni, set.getString(1), set.getString(2), RolUsuario.valueOf(set.getString(3)));
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
     * Devuelve todos los usuarios que tienen menos de dos cuentas bancarias
     * 
     * @return Usuarios con menos de dos cuentas bancarias
     */
    public ArrayList<UsuarioDTO> QueryByCuentasBancarias() {
    	ArrayList<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();
    	
    	try {
    		usuarios = QueryUsuarios();
    		
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Usuario_Cuentas");
            PreparedStatement stmt = con.prepareStatement(statement);
            ResultSet set = stmt.executeQuery();
            
            while (set.next()) {
            	if (set.getString(2).split(",").length >= 2) {
            		for (int i = usuarios.size() - 1; i >= 0; i--) {
            			if (usuarios.get(i).getDni().equals(set.getString(1))) {
            				usuarios.remove(i);
            				break;
            			}
            		}
            	}
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    	
    	return usuarios;
    }
    
    /**
     * Devuelve todos los usuarios que sean clientes
     * 
     * @return Usuarios clientes
     */
    public ArrayList<UsuarioDTO> QueryUsuarios() {
    	ArrayList<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();
    	
    	try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Usuarios");
            PreparedStatement stmt = con.prepareStatement(statement);
            ResultSet set = stmt.executeQuery();

            while (set.next()) {
            	usuarios.add(new UsuarioDTO(set.getString(1), set.getString(2), set.getString(3), set.getString(4), set.getString(5), set.getInt(6)));
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    	
    	return usuarios;
    }

    /**
     * Inserta un usuario en la base de datos
     * 
     * @param usuario Usuario a introducir en la base de datos
     * @param usuarioLogin Datos de login del usuario a introducir en la base de datos
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int Insert(UsuarioDTO usuario, UsuarioLoginDTO usuarioLogin) {
    	ArrayList<Integer> results = new ArrayList<Integer>();
        int status = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date());
        calendar.add(Calendar.MONTH, 6);
        java.util.Date cambioObligatorio = calendar.getTime();

        try {
            String statement = sqlProp.getProperty("Insert_Usuario");
        	Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, usuario.getDni());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getApellidos());
            stmt.setString(4, usuario.getEmail());
            stmt.setString(5, usuario.getDireccion());
            stmt.setInt(6, usuario.getTelefono());
            results.add(stmt.executeUpdate());
            
            statement = sqlProp.getProperty("Insert_Password");
            stmt = con.prepareStatement(statement);
            stmt.setString(1, usuarioLogin.getDni());
            stmt.setString(2, usuarioLogin.getPassword());
            stmt.setString(3, usuarioLogin.getSalt());
            stmt.setString(4, usuarioLogin.getRol().toString());
            stmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));
            stmt.setDate(6, new java.sql.Date(cambioObligatorio.getTime()));
            results.add(stmt.executeUpdate());
            
            status = CheckResults(results);
            
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
     * Actualiza la contrase√±a de un usuario
     * 
     * @param ususario Usuario a actualizar
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int UpdatePassword(UsuarioLoginDTO usuario) {
        int status = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date());
        calendar.add(Calendar.MONTH, 1);
        java.util.Date cambioDisponible = calendar.getTime();
        calendar.add(Calendar.MONTH, 5);
        java.util.Date cambioObligatorio = calendar.getTime();

        try {
            String statement = sqlProp.getProperty("Update_Password");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(4, usuario.getDni());
            stmt.setString(1, usuario.getPassword());
            stmt.setDate(2, new java.sql.Date(cambioDisponible.getTime()));
            stmt.setDate(3, new java.sql.Date(cambioObligatorio.getTime()));
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
     * Resetea la contrase√±a de un usuario
     * 
     * @param ususario Usuario cuya contraseÒa se va a resetear
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int UpdatePasswordPorReseteo(UsuarioLoginDTO usuario) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Update_Password");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(4, usuario.getDni());
            stmt.setString(1, usuario.getPassword());
            stmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            stmt.setDate(3, new java.sql.Date(System.currentTimeMillis()));
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
    	ArrayList<Integer> results = new ArrayList<Integer>();
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Delete_Usuario");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, dni);
            results.add(stmt.executeUpdate());
            
            statement = sqlProp.getProperty("Delete_Password");
            stmt = con.prepareStatement(statement);
            stmt.setString(1, dni);
            results.add(stmt.executeUpdate());
            
            status = CheckResults(results);
            
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

