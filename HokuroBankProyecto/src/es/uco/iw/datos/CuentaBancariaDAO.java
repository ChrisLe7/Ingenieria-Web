package es.uco.iw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import  es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO;
import  es.uco.iw.negocio.cuentaBancaria.TipoCuentaBancaria;
import es.uco.iw.negocio.usuario.RolPropietario;
import es.uco.iw.negocio.usuario.UsuarioDTO;

public class CuentaBancariaDAO extends DAO {

    /**
     * Constructor de DAO que carga los ficheros properties
     * 
     * @param url URL de la base de datos
     * @param user Usuario de la base de datos
     * @param pwd Contraseña de la base de datos
     * @param sqlProp Fichero Properties con las consultas sql
     */
    public CuentaBancariaDAO(String url, String user, String pwd, Properties sqlProp) {
    	super(url, user, pwd, sqlProp);
    }

    /**
     * Busca la cuenta bancaria cuya id coincide con la dada
     * 
     * @param idCuentaBancaria Id de la cuenta bancaria a buscar
     * @return Cuenta bancaria cuya id coincide con la dada
     */
    public CuentaBancariaDTO QueryByIdCuentaBancaria(String idCuentaBancaria) {
        CuentaBancariaDTO cuentaBancaria = null;

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Cuenta_Bancaria");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, idCuentaBancaria);
            ResultSet set = stmt.executeQuery();
            
            if (set.next()) {
            	String idTtitular = "";
            	String idCotitular = "";
            	
            	statement = sqlProp.getProperty("Select_Cuenta_Bancaria_Titular");
            	PreparedStatement stmtTtitular = con.prepareStatement(statement);
                stmtTtitular.setString(1, idCuentaBancaria);
                ResultSet setTtitular = stmtTtitular.executeQuery();
                
                if (setTtitular.next()) {
                	idTtitular = setTtitular.getString(1);
                }
                
                statement = sqlProp.getProperty("Select_Cuenta_Bancaria_Cotitular");
            	PreparedStatement stmtCotitular = con.prepareStatement(statement);
                stmtCotitular.setString(1, idCuentaBancaria);
                ResultSet setCotitular = stmtCotitular.executeQuery();
                
                if (setCotitular.next()) {
                	idCotitular = setCotitular.getString(1);
                }
                
                cuentaBancaria = new CuentaBancariaDTO(set.getString(1), set.getFloat(2), TipoCuentaBancaria.valueOf(set.getString(3)), set.getBoolean(4), idTtitular, idCotitular);
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        return cuentaBancaria;
    }

    /**
     * Busca la cuenta bancaria cuya id coincide con la dada y devuelve el estado de su bizum
     * 
     * @param idCuentaBancaria Id de la cuenta bancaria a buscar
     * @return True si tiene el bizum activo y false en caso contrario
     */
    public boolean QueryByBizum(String idCuentaBancaria) {
        boolean res = false;
        
        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Cuenta_Bancaria_Bizum");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, idCuentaBancaria);
            ResultSet set = stmt.executeQuery();
            
            if (set.next()) {
                res = set.getBoolean(1);
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        return res;
    }

    /**
     * Busca los titulares de la cuenta dada
     * 
     * @param idCuentaBancaria Id de la cuenta bancaria cuyos titulares se van a buscar
     * @return Titulares de la cuenta bancaria cuya id de cuenta bancaria coincide con la dada
     */
    public ArrayList<String> QueryByIdCliente(String idCuentaBancaria) {
        ArrayList<String> cuentasBancarias = new ArrayList<String>();

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Cuenta_Bancaria_Usuario");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, idCuentaBancaria);
            ResultSet set = stmt.executeQuery();
            
            while (set.next()) {
                cuentasBancarias.add(set.getString(1));
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        return cuentasBancarias;
    }
    
    /**
     * Busca la cuenta bancaria que este asignada a un telefono para el bizum
     * 
     * @param telefono Telefono para buscar la cuenta
     * @return Cuenta bancaria asignada al telefono
     */
    public CuentaBancariaDTO QueryByTelefono(int telefono) {
    	CuentaBancariaDTO cuentaBancaria = null;
    	
    	try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Cuenta_Bancaria_Telefono");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setInt(1, telefono);
            ResultSet set = stmt.executeQuery();
            
            if (set.next()) {
                cuentaBancaria = QueryByIdCuentaBancaria(set.getString(1));
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    	
    	return cuentaBancaria;
    }
    
    /**
     * Devuelve todas las cuentas bancarias
     * 
     * @return Cuentsa bancarias
     */
    public ArrayList<CuentaBancariaDTO> QueryCuentasBancarias() {
        ArrayList<CuentaBancariaDTO> cuentasBancarias = new ArrayList<CuentaBancariaDTO>();

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Cuentas_Bancarias");
            PreparedStatement stmt = con.prepareStatement(statement);
            ResultSet set = stmt.executeQuery();
            
            while (set.next()) {
            	String idTtitular = "";
            	String idCotitular = "";
            	
            	statement = sqlProp.getProperty("Select_Cuenta_Bancaria_Titular");
            	PreparedStatement stmtTtitular = con.prepareStatement(statement);
                stmtTtitular.setString(1, set.getString(1));
                ResultSet setTtitular = stmtTtitular.executeQuery();
                
                if (setTtitular.next()) {
                	idTtitular = setTtitular.getString(1);
                }
                
                statement = sqlProp.getProperty("Select_Cuenta_Bancaria_Cotitular");
            	PreparedStatement stmtCotitular = con.prepareStatement(statement);
                stmtCotitular.setString(1, set.getString(1));
                ResultSet setCotitular = stmtCotitular.executeQuery();
                
                if (setCotitular.next()) {
                	idCotitular = setCotitular.getString(1);
                }
                
                CuentaBancariaDTO cuentaBancaria = new CuentaBancariaDTO(set.getString(1), set.getFloat(2), TipoCuentaBancaria.valueOf(set.getString(3)), set.getBoolean(4), idTtitular, idCotitular);
                cuentasBancarias.add(cuentaBancaria)
;            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        return cuentasBancarias;
    }

    /**
     * Inserta una cuenta bancaria en la base de datos
     * 
     * @param cuentaBancaria Cuenta bancaria a introducir en la base de datos
     * @param usuario Usuario dueño de la cuenta bancaria
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int Insert(CuentaBancariaDTO cuentaBancaria, UsuarioDTO usuario) {
    	ArrayList<Integer> results = new ArrayList<Integer>();
    	int status = 0;
    	
        try {
            String statement = sqlProp.getProperty("Insert_Cuenta_Bancaria");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, cuentaBancaria.getIdCuentaBancaria());
            stmt.setFloat(2, cuentaBancaria.getSaldo());
            stmt.setString(3, cuentaBancaria.getTipoCuentaBancaria().toString());
            stmt.setBoolean(4, cuentaBancaria.getEstadoBizum());
            results.add(stmt.executeUpdate());
            
            statement = sqlProp.getProperty("Insert_Cuenta_Bancaria_Usuario");
            stmt = con.prepareStatement(statement);
            stmt.setString(1, usuario.getDni());
            stmt.setString(2, cuentaBancaria.getIdCuentaBancaria());
            stmt.setString(3, RolPropietario.Titular.toString());
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
     * Actualiza el saldo de una cuenta bancaria de la base de datos
     * 
     * @param cuentaBancaria Cuenta bancaria a actualizar
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int UpdateSaldo(CuentaBancariaDTO cuentaBancaria) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Update_Cuenta_Bancaria_Saldo");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(2, cuentaBancaria.getIdCuentaBancaria());
            stmt.setFloat(1, cuentaBancaria.getSaldo());
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
     * Actualiza el estado de bizum de una cuenta bancaria de la base de datos
     * 
     * @param cuentaBancaria Cuenta bancaria a actualizar
     * @param usuario Usuario cuyo telefono se va a asignar al bizum
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int UpdateBizum(CuentaBancariaDTO cuentaBancaria, UsuarioDTO usuario) {
    	ArrayList<Integer> results = new ArrayList<Integer>();
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Update_Cuenta_Bancaria_Bizum");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(2, cuentaBancaria.getIdCuentaBancaria());
            stmt.setBoolean(1, cuentaBancaria.getEstadoBizum());
            results.add(stmt.executeUpdate());
            
            if (cuentaBancaria.getEstadoBizum()) {
            	statement = sqlProp.getProperty("Insert_Cuenta_Bancaria_Bizum");
            }
            else {
            	statement = sqlProp.getProperty("Delete_Cuenta_Bancaria_Bizum");
            }
            
            stmt = con.prepareStatement(statement);
            stmt.setString(1, cuentaBancaria.getIdCuentaBancaria());
            
            if (cuentaBancaria.getEstadoBizum()) {
            	stmt.setInt(2, usuario.getTelefono());
            }
            
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
     * Borra una cuenta bancaria de la base de datos
     * 
     * @param idCuentaBancaria Id de la cuenta bancaria a borrar
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int Delete(String idCuentaBancaria) {
    	ArrayList<Integer> results = new ArrayList<Integer>();
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Delete_Cuenta_Bancaria_Usuario");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, idCuentaBancaria);
            results.add(stmt.executeUpdate());
            
            statement = sqlProp.getProperty("Delete_Cuenta_Bancaria_Bizum");
            stmt = con.prepareStatement(statement);
            stmt.setString(1, idCuentaBancaria);
            results.add(stmt.executeUpdate());
            
            statement = sqlProp.getProperty("Delete_Cuenta_Bancaria_Tarjeta");
            stmt = con.prepareStatement(statement);
            stmt.setString(1, idCuentaBancaria);
            results.add(stmt.executeUpdate());
            
            statement = sqlProp.getProperty("Delete_Cuenta_Bancaria");
            stmt = con.prepareStatement(statement);
            stmt.setString(1, idCuentaBancaria);
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