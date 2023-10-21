package com.in.Attendance;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

//import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
//import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AddAttendance {
	Connection con;
	DefaultTableModel model = new DefaultTableModel();
	

    public void addView(String columnName) throws SQLException {
        connect();
        JFrame frame = new JFrame();
        Font text = new Font("Times New Roman", Font.PLAIN, 18);
        Font btn = new Font("Times New Roman", Font.BOLD, 20);

		
		//------------------------CLOSE---------------------------
		JLabel x = new JLabel("X");
		x.setForeground(Color.decode("#37474F"));
		x.setBounds(965, 10, 100, 20);
		x.setFont(new Font("Times New Roman", Font.BOLD, 20));
		frame.getContentPane().add(x);
		x.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		//----------------------------------------------------------
		
		//-----------------------BACK---------------------------------
		JLabel back = new JLabel("< BACK");
		back.setForeground(Color.decode("#37474F"));
		back.setFont(new Font("Times New Roman", Font.BOLD, 17));
		back.setBounds(18, 10, 100, 20);
		frame.getContentPane().add(back);
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		//--------------------------------------------------------------
		
		//------------------Panel----------------------------------
		JPanel panel = new  JPanel();
		panel.setBounds(0, 0, 1000, 35);
		panel.setBackground(Color.decode("#DEE4E7"));
		frame.getContentPane().add(panel);
		//---------------------------------------------------------
		
		//----------------TABLE---------------------------------
		//@SuppressWarnings("serial")
		JTable table=new JTable(){
			public boolean isCellEditable(int row,int column){
					/*return false;*/
					// Allow editing only for the "STATUS" column (column 3)
					return column == 2;
			}
		};
		model = (DefaultTableModel)table.getModel();
		model.addColumn("SR NO");
		model.addColumn("NAME");
		model.addColumn("STATUS"); // Add the "Username" column
		model.addColumn("USERNAME");
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);

		table.getColumnModel().getColumn(2).setCellRenderer(new CheckBoxRenderer());
        // Set the CellEditor to handle checkbox editing
        table.getColumnModel().getColumn(2).setCellEditor(new CheckBoxEditor());

		JScrollPane scPane=new JScrollPane(table);
		scPane.setBounds(18, 96, 962, 404);
		frame.getContentPane().add(scPane);
		//------------------------------------------------------
		
		//-------------------------DATE-------------------------
		JLabel dt = new JLabel("DATE : ");
		dt.setFont(text);
		dt.setBounds(25, 60, 75, 20);
		dt.setForeground(Color.decode("#DEE4E7"));
		frame.getContentPane().add(dt);
		JTextField dtbox= new JTextField();
		dtbox.setBounds(100, 60, 150, 25);
		dtbox.setBackground(Color.decode("#DEE4E7"));
		dtbox.setFont(text);
		dtbox.setForeground(Color.decode("#37474F"));
		String dateInString =new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		dtbox.setText(dateInString);
		frame.getContentPane().add(dtbox);
		//-------------------------------------------------------
		
		//--------------------CLASS---------------------------------
		JLabel classes = new JLabel("SUBJECT : ");
		classes.setFont(text);
		classes.setBounds(698, 60, 100, 20);
		classes.setForeground(Color.decode("#DEE4E7"));
		frame.getContentPane().add(classes);
		@SuppressWarnings("")
		JComboBox<String> clss = new JComboBox<>(classEt());
		clss.setBounds(801, 61, 179, 25);
		frame.getContentPane().add(clss);
		//------------------------------------------------------------
		
		//---------------------ALREADY ADDED------------------------
		JLabel txt = new JLabel("");
		txt.setFont(text);
		txt.setBounds(373, 570, 350, 20);
		txt.setForeground(Color.red);
		frame.getContentPane().add(txt);
		//-------------------------------------------------------------
		
		//----------------------VIEWBUTTON-----------------------
		JButton view = new JButton("VIEW");
		view.setBounds(658, 510, 150, 50);
		view.setFont(btn);
		view.setBackground(Color.decode("#DEE4E7"));
		view.setForeground(Color.decode("#37474F"));
		frame.getContentPane().add(view);
		view.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(check(String.valueOf(clss.getSelectedItem()), dtbox.getText()))
						txt.setText("Attendance Already marked!!!");
					else
					    // Set the data from the "students" database into the table
						tblupdt(String.valueOf(clss.getSelectedItem()));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		//-------------------------------------------------------
		
		//----------------------ABSENTBUTTON-----------------------
		/*JButton ab = new JButton("ABSENT");
		ab.setBounds(178, 510, 150, 50);
		ab.setFont(btn);
		ab.setBackground(Color.decode("#DEE4E7"));
		ab.setForeground(Color.decode("#37474F"));
		frame.getContentPane().add(ab);
		ab.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				table.setValueAt("Absent", table.getSelectedRow(), 2);
			}
		});*/
		//-------------------------------------------------------
		
		//----------------------PRESENTBUTTON-----------------------
		/*JButton pre = new JButton("PRESENT");
		pre.setBounds(18, 510, 150, 50);
		pre.setFont(btn);
		pre.setBackground(Color.decode("#DEE4E7"));
		pre.setForeground(Color.decode("#37474F"));
		frame.getContentPane().add(pre);
		pre.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.print(table.getSelectedRow());
				table.setValueAt("Present", table.getSelectedRow(), 2);
			}
		});
	//extra
		pre.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            table.setValueAt("Present", selectedRow, 2);
        } else {
            // Handle the case where no row is selected, e.g., show a message or log an error.
            JOptionPane.showMessageDialog(frame, "Please select a row to mark as 'Present'", "No Row Selected", JOptionPane.WARNING_MESSAGE);
        }
    }
});*/
		//-------------------------------------------------------
		
		//----------------------SUBMITBUTTON-----------------------
		JButton sbmt = new JButton("SUBMIT");
		sbmt.setBounds(830, 510, 150, 50);
		sbmt.setFont(btn);
		sbmt.setBackground(Color.decode("#DEE4E7"));
		sbmt.setForeground(Color.decode("#37474F"));
		frame.getContentPane().add(sbmt);

        sbmt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowCount = model.getRowCount();
                for (int i = 0; i < rowCount; i++) {
                    try {
                        Object idObj = table.getValueAt(i, 0);
                        Object statusObj = table.getValueAt(i, 2);

                        if (idObj != null && statusObj != null) {
                            int id = Integer.parseInt(idObj.toString());
                            // Get the status from the checkbox
                            String status = (boolean) statusObj ? "Present" : "Absent";
                            addItem(id, status, dtbox.getText(), String.valueOf(clss.getSelectedItem()));
                        }
                    } catch (NumberFormatException | SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                model.setRowCount(0);
            }
        });

		//-------------------------------------------------------
		
		
		//-------------------------------------------------------
		frame.setSize(1000,600);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);  
		frame.setVisible(true);
		frame.setFocusable(true);
		frame.getContentPane().setBackground(Color.decode("#37474F"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//--------------------------------------------------------------
	}
	
	public void connect() throws SQLException {
		//ENTER PORT, USER, PASSWORD.
		String url = "jdbc:mysql://localhost:3306/attendance";
		String user = "root";
		String pass = "12345678";
		con = DriverManager.getConnection(url, user, pass);
	}
	
	public ResultSet dbSearch(String classes) throws SQLException {
		String str1 = "SELECT * from students where class = '"+classes+"'";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(str1);
		return rst;
	}
	
	public String[] classEt() throws SQLException {
		String str1 = "SELECT name from class";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(str1);
		String[] rt = new String[25];
		int i=0;
		while(rst.next()) {
			rt[i] = rst.getString("name");
			i++;
		}
		return rt;
	}
	
	
	public void tblupdt(String classes) {
		for (int i = 0; i < model.getRowCount(); i++) {
			model.removeRow(i);
			model.setRowCount(0);
		}
		try {
			ResultSet res = dbSearch(classes);
			for (int i = 0; res.next(); i++) {
				model.addRow(new Object[0]);
				model.setValueAt(res.getInt("id"), i, 0);
				model.setValueAt(res.getString("name"), i, 1);
				/*model.setValueAt(false, i, 2); // Set checkboxes as checked (true)
			}*/
			    model.setValueAt(res.getString("username"), i, 3); // Add Username
			    model.setValueAt(false, i, 2); // Set checkboxes as checked (true)
		}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void addItem(int id, String status, String date, String classes) throws SQLException {
		String adding = "INSERT INTO attend values("+id+", '"+date+"', '"+status+"', '"+classes+"')";
		Statement stm = con.createStatement();
        stm.executeUpdate(adding);
	}
	
	
	
	public boolean check(String classes, String dt) throws SQLException {
		String str1 = "select * from attend where class = '"+classes+"' AND dt = '"+dt+"'";
		Statement stm = con.createStatement();
		ResultSet rst = stm.executeQuery(str1);
		if(rst.next())
			return true;
		else 
			return false;

			
	}

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

	public DefaultTableModel getModel() {
		return model;
	}

	public void setModel(DefaultTableModel model) {
		this.model = model;
	}
}