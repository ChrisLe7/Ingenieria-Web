package es.uco.iw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import  es.uco.iw.negocio.bizum.BizumDTO;
import es.uco.iw.negocio.transaccion.TipoOperacion;

public class BizumDAO extends DAO {

    /**
     * Constructor de DAO que carga los ficheros properties
     * 
     * @param url URL de la base de datos
     * @param user Usuario de la base de datos
     * @param pwd Contraseña de la base de datos
     * @param sqlProp Fichero Properties con las consultas sql
     */
    public BizumDAO(String url, String user, String pwd, Properties sqlProp) {
    	super(url, user, pwd, sqlProp);
    }

    /**
     * Busca el bizum cuyo id coincide con la dado
     * 
     * @param id Id del bizum a buscar
     * @return Bizum cuyo id coincide con la dado
     */
    public BizumDTO QueryByIdTransaccion(String id) {
    	BizumDTO transaccion = null;

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Bizum");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, id);
            ResultSet set = stmt.executeQuery();
            
            if (set.next()) {
                transaccion = new BizumDTO(set.getString(1), set.getFloat(2), TipoOperacion.valueOf(set.getString(3)), new java.util.Date(set.getDate(4).getTime()), set.getString(5), set.getInt(6), set.getInt(7));
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
     * Busca los bizums cuyo telefono de origen coincide con el dado
     * 
     * @param telefonoOrigen Telefono de origen a buscar
     * @return Bizums cuyo telefono de origen coincide con el dado
     */
    public ArrayList<BizumDTO> QueryByTelefonoOrigen(int telefonoOrigen) {
        ArrayList<BizumDTO> transacciones = new ArrayList<BizumDTO>();
        BizumDTO transaccion = null;

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Bizum_Telefono_Origen");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setInt(1, telefonoOrigen);
            ResultSet set = stmt.executeQuery();
            
            while (set.next()) {
                transaccion = new BizumDTO(set.getString(1), set.getFloat(2), TipoOperacion.valueOf(set.getString(3)), new java.util.Date(set.getDate(4).getTime()), set.getString(5), set.getInt(6), set.getInt(7));
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
     * Busca los bizums cuya operacion sea la dada
     * 
     * @param operacion Operacion de los bizums a buscar
     * @return Bizums cuya operacion sea la dada
     */
    public ArrayList<BizumDTO> QueryByOperacion(String operacion) {
        ArrayList<BizumDTO> transacciones = new ArrayList<BizumDTO>();
        BizumDTO transaccion = null;

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Bizum_Operacion");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, operacion);
            ResultSet set = stmt.executeQuery();
            
            while (set.next()) {
                transaccion = new BizumDTO(set.getString(1), set.getFloat(2), TipoOperacion.valueOf(set.getString(3)), new java.util.Date(set.getDate(4).getTime()), set.getString(5), set.getInt(6), set.getInt(7));
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
     * Inserta un bizum en la base de datos
     * 
     * @param bizum Bizum a introducir en la base de datos
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int Insert(BizumDTO bizum) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Insert_Bizum");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, bizum.getIdTransaccion());
            stmt.setFloat(2, bizum.getCantidad());
            stmt.setString(3, bizum.getTipoOperacion().toString());
            stmt.setDate(4, new java.sql.Date(bizum.getFecha().getTime()));
            stmt.setString(5, bizum.getComentario());
            stmt.setInt(6, bizum.getTelefonoOrigen());
            stmt.setInt(7, bizum.getTelefonoDestino());
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
     * Borra un bizum de la base de datos
     * 
     * @param id Id del bizum a borrar
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int Delete(String id) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Delete_Bizum");
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