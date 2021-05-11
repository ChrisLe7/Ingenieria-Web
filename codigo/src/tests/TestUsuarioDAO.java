package tests;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import es.uco.iw.datos.UsuarioDAO;
import es.uco.iw.negocio.usuario.UsuarioDTO;
import es.uco.iw.negocio.usuario.UsuarioLoginDTO;
import es.uco.iw.utilidades.HashPassword;

public class TestUsuarioDAO {

	public static void main(String[] args) throws IOException {
		
		Properties properties = new Properties();
		FileReader fileReader = new FileReader("sql.properties");
		properties.load(fileReader);
			
		System.out.println("Test UsuarioDAO");
		
		UsuarioDAO usuarioDAO = new UsuarioDAO("jdbc:mysql://hokurobank.ddns.net:3306/IW", "HokuroAdmin", "AdL734Mkj692RJd126#", properties);
		
		UsuarioDTO usuarioTest = new UsuarioDTO("UsuarioTest");
		
		// Si hay algun fallo, borra el usuario prueba en caso de haberla
		if (usuarioDAO.QueryByDni(usuarioTest.getDni()) != null ) {
			usuarioDAO.Delete(usuarioTest.getDni());
		}
		
		String salt = HashPassword.createSalt();
					
		String password = "123";
		
		String hash = HashPassword.createHash(password, salt);
				
		UsuarioLoginDTO usuarioLoginTest = new UsuarioLoginDTO(usuarioTest.getDni(), hash, salt);
		
		assert usuarioDAO.Insert(usuarioTest, usuarioLoginTest) > 0 : "No se ha introducido el usuario";
		
		assert usuarioDAO.QueryByDni(usuarioTest.getDni()) != null : "No se ha encontrado al usuario";
				
		UsuarioLoginDTO queryRes = usuarioDAO.QueryByPassword(usuarioTest.getDni());
						
		String queryHash = HashPassword.createHash(password, queryRes.getSalt());

		assert queryHash.equals(queryRes.getPassword()) : "Error contraseña";
		
		assert !usuarioDAO.QueryByDni(usuarioTest.getDni()).getNombre().equals("nombre") : "Error en el nombre";
		
		usuarioTest.setNombre("nombre");
		
		assert usuarioDAO.Update(usuarioTest) > 0 : "Error en la actializacion";
		
		assert usuarioDAO.QueryByDni(usuarioTest.getDni()).getNombre().equals(usuarioTest.getNombre()) : "Error en el nombre actualizado";
		
		password = "1234";
		
		hash = HashPassword.createHash(password, salt);
		
		usuarioLoginTest.setPassword(hash);
				
		assert usuarioDAO.UpdatePassword(usuarioLoginTest) > 0 : "Error en actualizacion de contraseña";
		
		queryRes = usuarioDAO.QueryByPassword(usuarioTest.getDni());
		
		queryHash = HashPassword.createHash(password, queryRes.getSalt());
						
		assert queryHash.equals(queryRes.getPassword()) : "Error contraseña actualizada";
		
		assert usuarioDAO.Delete(usuarioTest.getDni()) > 0 : "Error en el borrado";
		
		assert usuarioDAO.QueryByDni(usuarioTest.getDni()) == null : "Se ha encontrado un usuario borrado";
		
		System.out.println("Exito");
		
	}
	
}
