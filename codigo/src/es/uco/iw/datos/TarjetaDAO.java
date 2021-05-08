package es.uco.iw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import  es.uco.iw.negocio.tarjeta.TarjetaDTO;
import es.uco.iw.negocio.tarjeta.TipoTarjeta;

public class TarjetaDAO extends DAO {

    /**
     * Constructor de DAO que carga los ficheros properties
     * 
     * @param url URL de la base de datos
     * @param user Usuario de la base de datos
     * @param pwd Contraseña de la base de datos
     * @param sqlProp Fichero Properties con las consultas sql
     */
    public TarjetaDAO(String url, String user, String pwd, Properties sqlProp) {
    	super(url, user, pwd, sqlProp);
    }

    /**
     * Busca la tarjeta cuya id coincide con la dada
     * 
     * @param numTarjeta Id de la tarjeta a buscar
     * @return Tarjeta cuya id coincide con la dada
     */
    public TarjetaDTO QueryByNumTarjeta(String numTarjeta) {
        TarjetaDTO tarjeta = null;

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Tarjeta");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, numTarjeta);
            ResultSet set = stmt.executeQuery();
            
            if (set.next()) {
                tarjeta = new TarjetaDTO(set.getString(1), set.getInt(2), TipoTarjeta.valueOf(set.getString(3)), set.getString(4), set.getString(5));
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        return tarjeta;
    }

    /**
     * Busca las tarjetas cuya id de cliente coincide con la dada
     * 
     * @param idCliente Id del cliente de las tarjetas a buscar
     * @return Tarjetas cuya id de cliente coincide con la dada
     */
    public ArrayList<TarjetaDTO> QueryByIdCliente(String idCliente) {
        ArrayList<TarjetaDTO> tarjetas = new ArrayList<TarjetaDTO>();
        TarjetaDTO tarjeta = null;

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Tarjeta_Usuario");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, idCliente);
            ResultSet set = stmt.executeQuery();
            
            while (set.next()) {
                tarjeta = new TarjetaDTO(set.getString(1), set.getInt(2), TipoTarjeta.valueOf(set.getString(3)), set.getString(4), set.getString(5));
                tarjetas.add(tarjeta);
            }

            if (stmt != null) {
                stmt.close();
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        return tarjetas;
    }

    /**
     * Inserta una tarjeta en la base de datos
     * 
     * @param tarjeta Tarjeta a introducir en la base de datos
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int Insert(TarjetaDTO tarjeta) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Insert_Tarjeta");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, tarjeta.getNumTarjeta());
            stmt.setInt(2, tarjeta.getPin());
            stmt.setString(3, tarjeta.getTipotarjeta().toString());
            stmt.setString(4, tarjeta.getIdCliente());
            stmt.setString(5, tarjeta.getIdCuenta());
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
     * Actualiza el pin de una tarjeta de la base de datos
     * 
     * @param tarjeta Tarjeta a actualizar
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int UpdatePin(TarjetaDTO tarjeta) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Update_Pin");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(2, tarjeta.getNumTarjeta());
            stmt.setFloat(1, tarjeta.getPin());
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
     * Borra una tarjeta de la base de datos
     * 
     * @param numTarjeta Id de la tarjeta a borrar
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int Delete(String numTarjeta) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Delete_Tarjeta");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, numTarjeta);
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