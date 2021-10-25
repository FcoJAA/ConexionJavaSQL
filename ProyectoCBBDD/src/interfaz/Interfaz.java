package interfaz;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interfaz extends JFrame implements ActionListener {

	//PANEL INTERFAZ
	private JPanel contentPane;
	JButton btnMainAlta, btnMainModificar, btnMainMostrar, btnMainEliminar, btnAltaAceptar, btnEliminar1, btnModificar1, btnMostrar1;
	
	private JTextField  textFieldMostrar1 , textFieldAlta1, textFieldAlta2, textFieldAlta3, textFieldAlta4, textFieldAlta5 , textFieldEliminar1 ;
	private JTextField textFieldModificar1, textFieldModificar2 , textFieldModificar3 , textFieldModificar4 , textFieldModificar5;
	

	//INICIA LA APLICACION
	public static void main(String[] args) {
		
		Interfaz miVentana = new Interfaz();
		miVentana.setVisible(true);

	}

	// Con el metodo interfaz iniciamos los componentes y damos detalles al panel principal
	public Interfaz() {
		
		iniciarComponentes1();
		
		setResizable(false);
		setTitle("Ventana Principal");
		
	}


	//CON ESTE METODO INICIAMOS AL CONEXION CON EL SERVIDOR ADEMAS CONSEGUIMOS ACTIVAR LOS BOTONES YA QUE REALICEN SU LOGICA
		@Override
		public void actionPerformed(ActionEvent e) {
			
			
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//En el try el orden es primero los botones de la interfazMain y luego los botones de las ventanas interfazAlta, interfazMostrar, interfazEliminar, interfazModificar
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			 try {
					
					//PARAMETROS APRA CONECTAR CON EL SERVIDOR
					//URL
					String url = "jdbc:mysql://localhost/";
					//Nombre de nuestra base de datos
					String bd = "sampledb";
					//creacion del objeto para crear la conexion
					Connection conn = null;
						
					//Aqui introduzca el usuario y contraseña de su base de datos 
					conn = DriverManager.getConnection(url + bd, "root", "***");
					
					 PreparedStatement ps = null;
					 Statement s = conn.createStatement();
					 ResultSet rs = s.executeQuery("Select * from users");
			
			/////BOTONES/////
					 
					 
			//PRIMEROS 4 BOTONES DEL PANEL PRINCIPAL
			if(btnMainAlta == e.getSource()) {	
				interfazAlta();
			}
			if(btnMainMostrar == e.getSource()) {	
				interfazMostrar();
			}
			if(btnMainModificar == e.getSource()) {	
				interfazModificar();
			}
			if(btnMainEliminar == e.getSource()) {	
				interfazEliminar();
			}
			
			//SEGUNDOS BOTONES DE LAS DIFERENTES INTERFACES, ESTA ES LA PARTE QUE SE DEBE IMPLEMENTAR LA LOGIVA PARA MANDAR SECUENCIAS SQL Y MOSTRARLAS
			
			 if(btnMostrar1 == e.getSource()) {		
				 
				 
					 int numInID = Integer.parseInt(textFieldMostrar1.getText());
			
					 while(rs.next()) {	
						 String id = rs.getString("user_id");
						 int comparadorID = Integer.parseInt(id);
						 String username = rs.getString("username");
						 String password = rs.getString("password");
						 String fullname = rs.getString("fullname");
						 String email = rs.getString("email");
						 
						 if(comparadorID == numInID) {
						 		JOptionPane.showInternalMessageDialog(null, "ID: " + id + "\nUsername: "+ username + "\nPassword: " + password + "\nFullname: " + fullname + "\nEmail: " + email );
						 }
					}
		 
				 }
			 
			 
			 //EL APARTADO DE DAR DE ALTA HAY QUE MODIFICARLO PARA QUE ACEPTE QUE NOSOTROS LE DEMOS UN ID
			 if(btnAltaAceptar == e.getSource()) {		
				 
				 String sql = "INSERT INTO Users (username, password, fullname, email) VALUES (?, ?, ?, ?)";
					
				 PreparedStatement statement = conn.prepareStatement(sql);
				 
				 String user= textFieldAlta2.getText();
				 String password= textFieldAlta3.getText();
				 String fullname= textFieldAlta4.getText();
				 String email= textFieldAlta5.getText();
				 
				 statement.setString(1, user);
				 statement.setString(2, password);
				 statement.setString(3, fullname);
				 statement.setString(4, email);
				 
				 int rowsInserted = statement.executeUpdate();
				
				 	if (rowsInserted > 0) {

					    System.out.println("A new user was inserted successfully!");

					}
			}
			 

			 if(btnEliminar1 == e.getSource()) {		
				 
				 String nombre = textFieldEliminar1.getText();
				 
				 String sql = "DELETE FROM Users WHERE username=?";
				 
				 PreparedStatement statement = conn.prepareStatement(sql);
				 
				 statement.setString(1, nombre);
				 
				 int rowsDeleted = statement.executeUpdate();
				 
				 if (rowsDeleted > 0) {

					    System.out.println("A user was deleted successfully!");

					}

				 JOptionPane.showInternalMessageDialog(null, "Hola" );
			 }
			 
			 
			 
			 if(btnModificar1 == e.getSource()) {		

				 String sql = "UPDATE Users SET password=?, fullname=?, email=?, username=? WHERE user_id=?";
				 
				 PreparedStatement statement = conn.prepareStatement(sql);
				 
				 String id= textFieldModificar1.getText();
				 String user= textFieldModificar2.getText();
				 String password= textFieldModificar3.getText();
				 String fullname= textFieldModificar4.getText();
				 String email= textFieldModificar5.getText();
				 
				 statement.setString(1, password);
				 statement.setString(2, fullname);
				 statement.setString(3, email);
				 statement.setString(4, user);
				 statement.setString(5, id);


				 int rowsUpdated = statement.executeUpdate();

				 if (rowsUpdated > 0) {

				     System.out.println("An existing user was updated successfully!");

				 }
				 
				 
				 //ESTO LO USO PARA COMPROBAR QUE LA ESCUCHA Y ENLACE DE BOTONES ES CORRECTO
				 //JOptionPane.showInternalMessageDialog(null, "Hola" );
			 }
			 

			 conn.close();
			 
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
	}
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////BLOQUE DE INTERFACES DE LA APLICACION/////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//INTERFAZ MAIN
		
		private void iniciarComponentes1() {
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 301, 189);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel lblIMain1 = new JLabel("\u00BFQue operacion quiere realizar?");
			lblIMain1.setFont(new Font("Tahoma", Font.BOLD, 17));
			lblIMain1.setBounds(10, 11, 414, 34);
			contentPane.add(lblIMain1);
			
			btnMainAlta = new JButton("Alta");
			btnMainAlta.setForeground(Color.BLUE);
			btnMainAlta.setBounds(20, 56, 89, 23);
			btnMainAlta.addActionListener(this);
			contentPane.add(btnMainAlta);
			
			btnMainMostrar = new JButton("Mostrar");
			btnMainMostrar.setForeground(Color.DARK_GRAY);
			btnMainMostrar.setBounds(169, 56, 89, 23);
			btnMainMostrar.addActionListener(this);
			contentPane.add(btnMainMostrar);
			
			btnMainModificar = new JButton("Modificar");
			btnMainModificar.setBounds(20, 109, 89, 23);
			btnMainModificar.addActionListener(this);
			contentPane.add(btnMainModificar);
			
			btnMainEliminar = new JButton("Eliminar");
			btnMainEliminar.setForeground(Color.RED);
			btnMainEliminar.setBounds(169, 109, 89, 23);
			btnMainEliminar.addActionListener(this);
			contentPane.add(btnMainEliminar);
			
		}
		
		
		
		//INTERFAZ DE MOSTRAR
		public void interfazMostrar() {
			setTitle("Mostrar");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 392, 185);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel lblMostrar1 = new JLabel("Mostrar Usuario");
			lblMostrar1.setForeground(Color.DARK_GRAY);
			lblMostrar1.setFont(new Font("Tahoma", Font.PLAIN, 24));
			lblMostrar1.setBounds(10, 11, 180, 41);
			contentPane.add(lblMostrar1);
			
			JLabel lblMostrar2 = new JLabel("user_id");
			lblMostrar2.setFont(new Font("Tahoma", Font.PLAIN, 17));
			lblMostrar2.setBounds(10, 63, 74, 21);
			contentPane.add(lblMostrar2);
			
			textFieldMostrar1 = new JTextField();
			textFieldMostrar1.setBounds(73, 66, 281, 21);
			contentPane.add(textFieldMostrar1);
			textFieldMostrar1.addActionListener(this);
			textFieldMostrar1.setColumns(10);
			
			btnMostrar1 = new JButton("Mostrar");
			btnMostrar1.setBounds(145, 112, 89, 23);
			btnMostrar1.addActionListener(this);
			contentPane.add(btnMostrar1);
		}
		
		//INTERFAZ DE ALTA
		
		public void interfazAlta() {
			setTitle("Alta");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 422, 380);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel lblAlta1 = new JLabel("Dar de alta:");
			lblAlta1.setFont(new Font("Tahoma", Font.BOLD, 27));
			lblAlta1.setForeground(Color.BLUE);
			lblAlta1.setBounds(10, 11, 158, 46);
			contentPane.add(lblAlta1);
			
			JLabel lblAlta2 = new JLabel("ID");
			lblAlta2.setFont(new Font("Tahoma", Font.BOLD, 17));
			lblAlta2.setBounds(10, 84, 89, 33);
			contentPane.add(lblAlta2);
			
			btnAltaAceptar = new JButton("Aceptar");
			btnAltaAceptar.setBounds(176, 300, 89, 23);
			btnAltaAceptar.addActionListener(this);
			contentPane.add(btnAltaAceptar);
			
			JLabel lblAlta3 = new JLabel("User");
			lblAlta3.setFont(new Font("Tahoma", Font.BOLD, 17));
			lblAlta3.setBounds(10, 128, 89, 33);
			contentPane.add(lblAlta3);
			
			JLabel lblAlta4 = new JLabel("Password");
			lblAlta4.setFont(new Font("Tahoma", Font.BOLD, 17));
			lblAlta4.setBounds(10, 172, 89, 33);
			contentPane.add(lblAlta4);
			
			JLabel lblAlta5 = new JLabel("Fullname");
			lblAlta5.setFont(new Font("Tahoma", Font.BOLD, 17));
			lblAlta5.setBounds(10, 216, 89, 33);
			contentPane.add(lblAlta5);
			
			JLabel lblAlta6 = new JLabel("email");
			lblAlta6.setFont(new Font("Tahoma", Font.BOLD, 17));
			lblAlta6.setBounds(10, 260, 89, 33);
			contentPane.add(lblAlta6);
			
			textFieldAlta1 = new JTextField();
			textFieldAlta1.setBounds(109, 93, 262, 20);
			contentPane.add(textFieldAlta1);
			textFieldAlta1.addActionListener(this);
			textFieldAlta1.setColumns(10);
			
			textFieldAlta2 = new JTextField();
			textFieldAlta2.setColumns(10);
			textFieldAlta2.setBounds(109, 137, 262, 20);
			textFieldAlta2.addActionListener(this);
			contentPane.add(textFieldAlta2);
			
			textFieldAlta3 = new JTextField();
			textFieldAlta3.setColumns(10);
			textFieldAlta3.setBounds(109, 181, 262, 20);
			textFieldAlta3.addActionListener(this);
			contentPane.add(textFieldAlta3);
			
			textFieldAlta4 = new JTextField();
			textFieldAlta4.setColumns(10);
			textFieldAlta4.setBounds(109, 225, 262, 20);
			textFieldAlta4.addActionListener(this);
			contentPane.add(textFieldAlta4);
			
			textFieldAlta5 = new JTextField();
			textFieldAlta5.setColumns(10);
			textFieldAlta5.setBounds(109, 269, 262, 20);
			textFieldAlta5.addActionListener(this);
			contentPane.add(textFieldAlta5);
		}
		
		//INTERFAZ DE ELIMINAR
		
		public void interfazEliminar() {
			setTitle("Eliminar");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 320, 220);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel lblEliminar1 = new JLabel("Eliminar usuario");
			lblEliminar1.setForeground(Color.RED);
			lblEliminar1.setFont(new Font("Tahoma", Font.BOLD, 27));
			lblEliminar1.setBounds(10, 11, 239, 36);
			contentPane.add(lblEliminar1);
			
			JLabel lblEliminar2 = new JLabel("User");
			lblEliminar2.setFont(new Font("Tahoma", Font.BOLD, 17));
			lblEliminar2.setBounds(10, 81, 80, 27);
			contentPane.add(lblEliminar2);
			
			textFieldEliminar1 = new JTextField();
			textFieldEliminar1.setBounds(87, 81, 185, 26);
			textFieldEliminar1.addActionListener(this);
			contentPane.add(textFieldEliminar1);
			textFieldEliminar1.setColumns(10);
			
			btnEliminar1 = new JButton("Eliminar");
			btnEliminar1.setBounds(127, 118, 89, 23);
			btnEliminar1.addActionListener(this);
			contentPane.add(btnEliminar1);
		}

		//INTERFAZ DE MODIFICAR
		
		public void interfazModificar() {
			
			setTitle("Modificar");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 445, 466);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel lblModificar1 = new JLabel("Modificar usuario:");
			lblModificar1.setFont(new Font("Tahoma", Font.BOLD, 27));
			lblModificar1.setBounds(10, 11, 245, 54);
			contentPane.add(lblModificar1);
			
			JLabel lblModificar2 = new JLabel("id: ");
			lblModificar2.setFont(new Font("Tahoma", Font.BOLD, 17));
			lblModificar2.setBounds(20, 76, 38, 34);
			contentPane.add(lblModificar2);
			
			JLabel lblModificar3 = new JLabel("Campos a modificar");
			lblModificar3.setFont(new Font("Tahoma", Font.BOLD, 17));
			lblModificar3.setBounds(20, 121, 177, 34);
			contentPane.add(lblModificar3);
			
			JLabel lblModificar4 = new JLabel("User:");
			lblModificar4.setFont(new Font("Tahoma", Font.BOLD, 17));
			lblModificar4.setBounds(10, 166, 89, 34);
			contentPane.add(lblModificar4);
			
			JLabel lblModificar5 = new JLabel("Password:");
			lblModificar5.setFont(new Font("Tahoma", Font.BOLD, 17));
			lblModificar5.setBounds(10, 213, 89, 34);
			contentPane.add(lblModificar5);
			
			JLabel lblModificar6 = new JLabel("Fullname:");
			lblModificar6.setFont(new Font("Tahoma", Font.BOLD, 17));
			lblModificar6.setBounds(10, 259, 89, 34);
			contentPane.add(lblModificar6);
			
			JLabel lblModificar7 = new JLabel("email:");
			lblModificar7.setFont(new Font("Tahoma", Font.BOLD, 17));
			lblModificar7.setBounds(10, 304, 89, 34);
			contentPane.add(lblModificar7);
			
			btnModificar1 = new JButton("Modificar");
			btnModificar1.setBounds(166, 376, 89, 23);
			btnModificar1.addActionListener(this);
			contentPane.add(btnModificar1);
			
			textFieldModificar1 = new JTextField();
			textFieldModificar1.setBounds(126, 86, 281, 20);
			textFieldModificar1.addActionListener(this);
			contentPane.add(textFieldModificar1);
			textFieldModificar1.setColumns(10);
			
			textFieldModificar2 = new JTextField();
			textFieldModificar2.setColumns(10);
			textFieldModificar2.setBounds(126, 176, 281, 20);
			textFieldModificar2.addActionListener(this);
			contentPane.add(textFieldModificar2);
			
			textFieldModificar3 = new JTextField();
			textFieldModificar3.setColumns(10);
			textFieldModificar3.setBounds(126, 223, 281, 20);
			textFieldModificar3.addActionListener(this);
			contentPane.add(textFieldModificar3);
			
			textFieldModificar4 = new JTextField();
			textFieldModificar4.setColumns(10);
			textFieldModificar4.setBounds(126, 269, 281, 20);
			textFieldModificar4.addActionListener(this);
			contentPane.add(textFieldModificar4);
			
			textFieldModificar5 = new JTextField();
			textFieldModificar5.setColumns(10);
			textFieldModificar5.setBounds(126, 314, 281, 20);
			textFieldModificar5.addActionListener(this);
			contentPane.add(textFieldModificar5);
		}
		
}