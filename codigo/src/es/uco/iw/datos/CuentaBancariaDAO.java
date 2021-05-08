package es.uco.iw.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import  es.uco.iw.negocio.cuentaBancaria.CuentaBancariaDTO;
import  es.uco.iw.negocio.cuentaBancaria.TipoCuentaBancaria;

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
    public CuentaBancariaDTO QueryById(String idCuentaBancaria) {
        CuentaBancariaDTO cuentaBancaria = null;

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Cuenta_Bancaria");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, idCuentaBancaria);
            ResultSet set = stmt.executeQuery();
            
            if (set.next()) {
                cuentaBancaria = new CuentaBancariaDTO(set.getString(1), set.getFloat(2), TipoCuentaBancaria.valueOf(set.getString(3)), set.getBoolean(4));
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
            String statement = sqlProp.getProperty("Select_Bizum");
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
     * Busca las cuentas bancarias cuya id del cliente coincide con la dada
     * 
     * @param idCliente Id del cliente de las cuentas bancarias a buscar
     * @return Cuentas bancarias cuya id del cliente coincide con la dada
     */
    public ArrayList<String> QueryByIdCliente(String idCliente) {
        ArrayList<String> cuentasBancarias = new ArrayList<String>();

        try {
            Connection con = getConnection();
            String statement = sqlProp.getProperty("Select_Cuenta_Bancaria_Usuario");
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, idCliente);
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
     * Inserta una cuenta bancaria en la base de datos
     * 
     * @param cuentaBancaria Cuenta bancaria a introducir en la base de datos
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int Insert(CuentaBancariaDTO cuentaBancaria) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Insert_Cuenta_Bancaria");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, cuentaBancaria.getIdCuentaBancaria());
            stmt.setFloat(2, cuentaBancaria.getSaldo());
            stmt.setString(3, cuentaBancaria.getTipoCuentaBancaria().toString());
            stmt.setBoolean(4, cuentaBancaria.getEstadoBizum());
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
     * Actualiza el saldo de una cuenta bancaria de la base de datos
     * 
     * @param cuentaBancaria Cuenta bancaria a actualizar
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int UpdateSaldo(CuentaBancariaDTO cuentaBancaria) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Update_Saldo");
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
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int UpdateBizum(CuentaBancariaDTO cuentaBancaria) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Update_Bizum");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(2, cuentaBancaria.getIdCuentaBancaria());
            stmt.setBoolean(1, cuentaBancaria.getEstadoBizum());
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
     * Borra una cuenta bancaria de la base de datos
     * 
     * @param idCuentaBancaria Id de la cuenta bancaria a borrar
     * @return El numero de filas afectadas o 0 en caso de fallo
     */
    public int Delete(String idCuentaBancaria) {
        int status = 0;

        try {
            String statement = sqlProp.getProperty("Delete_Cuenta_Bancaria");
            Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(statement);
            stmt.setString(1, idCuentaBancaria);
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