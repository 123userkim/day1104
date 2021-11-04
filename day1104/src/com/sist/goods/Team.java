package com.sist.goods;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import java.awt.event.MouseListener;


	public class Team extends JFrame {
		
		JTextField jtf_no;  
		JTextField jtf_name;  
		
		
		JTextField jtf_addr; 
		JTextField jtf_age;
		JTextField jtf_phone;
		
		
		JTable table ;  
		Vector colNames; 
		Vector<Vector> rowData;  
		
		 
			public void updatePerson(int no,String name, String addr, int age, String phone) {
				String sql = "update person set name='"+name+"', addr="+addr+",age="+age+",phone="+phone+" where no="+no;
				Connection conn = null;
				Statement stmt = null;
				try {
					
					Class.forName("oracle.jdbc.driver.OracleDriver");
					
					String url = "jdbc:oracle:thin:@localhost:1521:XE";
					String user = "c##sist";
					String pwd="sist";
					conn = DriverManager.getConnection(url, user, pwd);
					
					stmt = conn.createStatement();
					
					int re =stmt.executeUpdate(sql);
					
					if(re == 1) {
						System.out.println("ȸ�������� �����Ͽ����ϴ�.");
						printPerson();
					}else {
						System.out.println("ȸ�������� �����Ͽ����ϴ�.");
					}
					
				}catch (Exception e) {
					System.out.println("���ܹ߻�:"+e.getMessage());
				}finally {
					try {
						if(stmt != null) {
							stmt.close();
						}
						
						if(conn != null) {
							stmt.close();
						}
					}catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		
		
		 
		public void printPerson(){
			 
			rowData.clear();
			
			String sql = "select * from person";
			
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs= null;
			
			try{
				
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE  "  ,"c##sist" , "sist" );
				
				stmt = conn.createStatement();
				
				rs = stmt.executeQuery(sql);
				
				
				
				while(rs.next()) {
					
					//String no,String name, String addr, int age, String phone
					int	no = rs.getInt(1);
					String name = rs.getString(2);
					String addr = rs.getString(3);
					
					int age = rs.getInt(4);
					String phone = rs.getString(5);
					
					
					//���͸� �ϳ� ����� �� ���Ϳ� no, item,qty, price�� ���
					Vector v = new Vector();
					v.add(no);
					v.add(name);
					v.add(addr);
					v.add(age);
					v.add(phone);
					
					//�� ���͸� rowData�� ���
					rowData.add(v);

					//�ٲ� �������� ���̺��� �ٽ� �׸�
					//ó�� ���̺��� ���� ���� rowData���Ͱ� ����־���
					//���߿� ���̺��� �������� rowData�� �ٲ� �ڷḦ �����ϱ� ����
					//updateUI�� ȣ���ؾ���
					table.updateUI();		
				}
			}catch(Exception e) {
				System.out.println("���ܹ߻� : "+e.getMessage());
			}finally{
				try {
					//�ڿ� �ݱ�
					if(rs != null) {
						rs.close();
					}if(stmt != null) {
						stmt.close();
					}if(conn != null) {
						conn.close();
					}				
			   }catch(Exception e) {
					System.out.println("���ܹ߻� : "+e.getMessage());
				}
				
			}
		}
		
		public void insertPerson(int no,String name, String addr, int age, String phone) {
			
			String sql= "insert into goods values(" + no + ",'" + name + "'," + addr + "," + age +   ","   + phone + ")";
			 
			Connection conn=null;
			Statement stmt=null;
			try {
				
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE  "  ,"c##sist" , "sist" );
		
				stmt = conn.createStatement();
							 
				int re = stmt.executeUpdate(sql);
				
				if(re==1) {
					System.out.println("ȸ����Ͽ� ����");
					jtf_no.setText("");
					jtf_name.setText("");
					jtf_age.setText("");
					jtf_addr.setText("");
					jtf_phone.setText("");
					//��ǰ����� �����ϸ� ���̺��� ������ ���̵��� �޼ҵ� ȣ��
					printPerson();
					
				}else if(re==0) {
					System.out.println("ȸ����Ͽ� ����");
				}
		}catch(Exception ex) {
			System.out.println("���ܹ߻� : "+ex.getMessage());
		}finally {		
			try {
					if(stmt != null) {
						stmt.close();
					}			
					if(conn != null) {
						conn.close();
					}
			}catch(Exception ex) {
				
			}
		}
		
	}
		 
		public void deletePerson(int no) {
			
			String sql = "delete person where no = "+no ;
			
			Connection conn = null;
			Statement stmt = null;
			
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
				
					conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE  "  ,"c##sist" , "sist" );
					
					stmt = conn.createStatement();			
				
					int re = stmt.executeUpdate(sql);
				
					if(re ==1 ) {
						System.out.println("ȸ�� ���� ����");
						printPerson();
						jtf_addr.setText("");
						jtf_no.setText("");
						jtf_phone.setText("");
						jtf_age.setText("");
						jtf_name.setText("");
						
					}else if(re ==0) {
						System.out.println("ȸ�� ���� ����");
					}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally {
						try {					
						
							if(stmt != null) {
								stmt.close();
							}if(conn != null) {
								conn.close();
							}					
						}catch(Exception ex) {
							System.out.println("���ܹ߻� : "+ex.getMessage());
						}					
				}
			}
			
	 
		public Team() {
		
			//String no,String name, String addr, int age, String phone
			colNames = new Vector<String>();
			colNames.add("ȸ����ȣ");
			colNames.add("����");
			colNames.add("�ּ�");
			colNames.add("����");
			colNames.add("��ȭ��ȣ");
			
			rowData = new Vector<Vector>();
			
			//�÷��̸��� �ִ� cloName ���Ϳ� ���� ������ �ִ� rowData ���͸� ���� ������ ���� ȭ���� ���̺� ����
			table = new JTable(rowData,colNames );
			
			//���̺� �ڷᰡ �� ���Ƽ� �� ȭ�鿡 ������ ���� �� �ڵ����� ��ũ�� �����
			JScrollPane jsp = new JScrollPane(table);
			
			jtf_no = new JTextField();
			jtf_addr = new JTextField();
			jtf_age = new JTextField();
			jtf_name = new JTextField();
			jtf_phone = new JTextField();
			 
			
			//�Է»��ڵ�� ������ �Է��ؾ��ϴ��� �����ϴ� �󺧵��� ��� ���� �г�
			JPanel p =new JPanel();
			p.setLayout(new GridLayout(5,2));
			
			//�гο� �󺧰� �ؽ�Ʈ �ʵ带 ���ʴ�� ���
			p.add(new JLabel("ȸ����ȣ"));
			p.add(jtf_no);
			
			p.add(new JLabel("����"));
			p.add(jtf_name);
					
			p.add(new JLabel("�ּ�"));
			p.add(jtf_addr);		
			
			p.add(new JLabel("����"));
			p.add(jtf_age);
			
			p.add(new JLabel("��ȭ��ȣ"));
			p.add(jtf_phone);
			
			JButton button_add = new JButton("�߰�");
			JButton button_list = new JButton("���");
			JButton button_update = new JButton("����");
			JButton button_delete = new JButton("����");
			
			 
			JPanel p2 = new JPanel();
			p2.add(button_add);
			p2.add(button_list);
			p2.add(button_update);
			p2.add(button_delete);
			
			
			JPanel p_center = new JPanel();
			p_center.setLayout(new BorderLayout());
			p_center.add(p,BorderLayout.CENTER);
			p_center.add(p2,BorderLayout.SOUTH);
			
			//�������� ����� �Է»��ڿ� ��ư�� ��� �ִ� P_center �г��� ���
			add(p_center, BorderLayout.CENTER);
			//���̺��� ����ִ� ��ũ������ �������� �Ʒ��ʿ� ��� 
			add(jsp,BorderLayout.SOUTH);
			
			setSize(800,600);
			setVisible(true);
			
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			
			
			button_update.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					int no=  Integer.parseInt(jtf_no.getText()) ;
					String name = jtf_name.getText();
					String addr = jtf_addr.getText();
					int age = Integer.parseInt(jtf_name.getText());
					String phone = jtf_phone.getText();
					
					
					updatePerson(no, name, addr, age, phone);
					
				}});
			
			
			
			
			button_list.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					printPerson();
					
				}});
			
			
			 
					button_add.addActionListener(new  ActionListener() {

						public void actionPerformed(ActionEvent e) {
							
							//�����ͺ��̽� insert�� �����ϴ� �޼ҵ带 ����� ȣ���ϵ��� ����
							
							int no=Integer.parseInt(jtf_no.getText()) ;
							String name =jtf_name.getText();
							String addr =jtf_addr.getText();
							String phone=jtf_phone.getText();
							 
							int age=Integer.parseInt(jtf_age.getText()  )  ;
							
							insertPerson(no, name, addr, age, phone);		
						
						}});	
				
					
			
			
			table.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				
					int index = table.getSelectedRow(); 

					//rowData�� index��°�� ���͸� �����
					Vector vr	= rowData.get(index);
					 
					//�� ������ ��Ҹ� ���ʴ�� �Է»��ڿ� ���
					jtf_no.setText(vr.get(0)+"");
					jtf_name.setText(vr.get(1)+"");
					jtf_addr.setText(vr.get(2)+"");
					jtf_age.setText(vr.get(3)+"");
					jtf_phone.setText(vr.get(3)+"");
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}});
			
		}

		public static void main(String[] args) {
			// TODO Auto-generated method stub
			
			new Team();

		}

	}



