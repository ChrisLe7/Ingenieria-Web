package es.uco.iw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import  es.uco.iw.negocio.transaccion.TransaccionDTO;
import es.uco.iw.negocio.transaccion.TipoOperacion;

public class TransaccionDAO extends DAO {

    /**
     * Constructor de DAO que carga los ficheros properties
     * 
     * @param url URL de la base de datos
     * @param user Usuario de la base de datos
     * @param pwd Contraseña de la base de datos
     * @param sqlProp Fichero Properties con las consultas sql
     */
    public TransaccionDAO(String url, String user, String pwd, Properties sqlProp) {
    	super(url, user, pwd, sqlProp);
    }

    /**
     * Busca la transaccion cuyo id coincide con la dada
     * 
     * @param id Id de la transaccion a buscar
     * @return Transaccion cuyo id coincide con la dada
     */
    public TransaccionDTO QueryByIdTransaccion(String id) {
        TransaccionDTO transaccion = null;

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Transaccion");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, id);
            ResultSet set = stmt.executeQuery();
            
            if (set.next()) {
                transaccion = new TransaccionDTO(set.getString(1), set.getFloat(2), TipoOperacion.valueOf(set.getString(3)), new java.util.Date(set.getDate(4).getTime()), set.getString(5), set.getString(6), set.getString(7));
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        return transaccion;
    }

    /**
     * Busca las transacciones de la cuenta bancaria cuya id coincide con la dada
     * 
     * @param idCuentaBancaria Id de la cuenta bancaria cuyas transacciones se van a buscar
     * @return Transacciones de la cuenta de origen cuya id coincide con la dada
     */
    public ArrayList<TransaccionDTO> QueryByIdCuenta(String idCuentaBancaria) {
        ArrayList<TransaccionDTO> transacciones = new ArrayList<TransaccionDTO>();
        TransaccionDTO transaccion = null;

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Transaccion_Cuenta");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, idCuentaBancaria);
            stmt.setString(2, idCuentaBancaria);
            ResultSet set = stmt.executeQuery();
            
            while (set.next()) {
                transaccion = new TransaccionDTO(set.getString(1), set.getFloat(2), TipoOperacion.valueOf(set.getString(3)), new java.util.Date(set.getDate(4).getTime()), set.getString(5), set.getString(6), set.getString(7));
                transacciones.add(transaccion);
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        return transacciones;
    }
    
    /**
     * Busca las transacciones cuya operacion sea la dada
     * 
     * @param operacion Operacion de las transacciones a buscar
     * @return Transacciones cuya operacion sea la dada
     */
    public ArrayList<TransaccionDTO> QueryByOperacion(String operacion) {
        ArrayList<TransaccionDTO> transacciones = new ArrayList<TransaccionDTO>();
        TransaccionDTO transaccion = null;

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Transaccion_Operacion");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, operacion);
            ResultSet set = stmt.executeQuery();
            
            while (set.next()) {
                transaccion = new TransaccionDTO(set.getString(1), set.getFloat(2), TipoOperacion.valueOf(set.getString(3)), new java.util.Date(set.getDate(4).getTime()), set.getString(5), set.getString(6), set.getString(7));
                transacciones.add(transaccion);
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        return transacciones;
    }

    /**
     * Inserta una transaccion en la base de datos
     * 
     * @param transaccion Transaccion a introducir en la base de datos
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int Insert(TransaccionDTO transaccion) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Insert_Transaccion");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, transaccion.getIdTransaccion());
            stmt.setFloat(2, transaccion.getCantidad());
            stmt.setString(3, transaccion.getTipoOperacion().toString());
            stmt.setDate(4, new java.sql.Date(transaccion.getFecha().getTime()));
            stmt.setString(5, transaccion.getComentario());
            stmt.setString(6, transaccion.getIdCuentaOrigen());
            stmt.setString(7, transaccion.getIdCuentaDestino());
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
     * Borra una transaccion de la base de datos
     * 
     * @param id Id de la transaccion a borrar
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int Delete(String id) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Delete_Transaccion");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, id);
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