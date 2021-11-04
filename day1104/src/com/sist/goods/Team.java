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
						System.out.println("회원수정에 성공하였습니다.");
						printPerson();
					}else {
						System.out.println("회원수정에 실패하였습니다.");
					}
					
				}catch (Exception e) {
					System.out.println("예외발생:"+e.getMessage());
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
					
					
					//백터를 하나 만들고 그 벡터에 no, item,qty, price를 담기
					Vector v = new Vector();
					v.add(no);
					v.add(name);
					v.add(addr);
					v.add(age);
					v.add(phone);
					
					//그 벡터를 rowData에 담기
					rowData.add(v);

					//바뀐 내용으로 테이블을 다시 그림
					//처음 테이블을 만들 때에 rowData벡터가 비어있었음
					//나중에 테이블의 데이터인 rowData에 바뀐 자료를 적응하기 위해
					//updateUI를 호출해야함
					table.updateUI();		
				}
			}catch(Exception e) {
				System.out.println("예외발생 : "+e.getMessage());
			}finally{
				try {
					//자원 닫기
					if(rs != null) {
						rs.close();
					}if(stmt != null) {
						stmt.close();
					}if(conn != null) {
						conn.close();
					}				
			   }catch(Exception e) {
					System.out.println("예외발생 : "+e.getMessage());
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
					System.out.println("회원등록에 성공");
					jtf_no.setText("");
					jtf_name.setText("");
					jtf_age.setText("");
					jtf_addr.setText("");
					jtf_phone.setText("");
					//상품등록을 성공하면 테이블의 내용을 보이도록 메소드 호출
					printPerson();
					
				}else if(re==0) {
					System.out.println("회원등록에 실패");
				}
		}catch(Exception ex) {
			System.out.println("예외발생 : "+ex.getMessage());
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
						System.out.println("회원 삭제 성공");
						printPerson();
						jtf_addr.setText("");
						jtf_no.setText("");
						jtf_phone.setText("");
						jtf_age.setText("");
						jtf_name.setText("");
						
					}else if(re ==0) {
						System.out.println("회원 삭제 실패");
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
							System.out.println("예외발생 : "+ex.getMessage());
						}					
				}
			}
			
	 
		public Team() {
		
			//String no,String name, String addr, int age, String phone
			colNames = new Vector<String>();
			colNames.add("회원번호");
			colNames.add("성함");
			colNames.add("주소");
			colNames.add("나이");
			colNames.add("전화번호");
			
			rowData = new Vector<Vector>();
			
			//컬럼이름이 있는 cloName 백터와 실제 데이터 있는 rowData 백터를 갖고 엑셀과 같은 화면의 테이블 생성
			table = new JTable(rowData,colNames );
			
			//테이블 자료가 넘 많아서 한 화면에 보이지 않을 때 자동으로 스크롤 만들기
			JScrollPane jsp = new JScrollPane(table);
			
			jtf_no = new JTextField();
			jtf_addr = new JTextField();
			jtf_age = new JTextField();
			jtf_name = new JTextField();
			jtf_phone = new JTextField();
			 
			
			//입력상자들과 무엇을 입력해야하는지 설명하는 라벨들을 담기 위한 패널
			JPanel p =new JPanel();
			p.setLayout(new GridLayout(5,2));
			
			//패널에 라벨과 텍스트 필드를 차례대로 담기
			p.add(new JLabel("회원번호"));
			p.add(jtf_no);
			
			p.add(new JLabel("성함"));
			p.add(jtf_name);
					
			p.add(new JLabel("주소"));
			p.add(jtf_addr);		
			
			p.add(new JLabel("나이"));
			p.add(jtf_age);
			
			p.add(new JLabel("전화번호"));
			p.add(jtf_phone);
			
			JButton button_add = new JButton("추가");
			JButton button_list = new JButton("목록");
			JButton button_update = new JButton("수정");
			JButton button_delete = new JButton("삭제");
			
			 
			JPanel p2 = new JPanel();
			p2.add(button_add);
			p2.add(button_list);
			p2.add(button_update);
			p2.add(button_delete);
			
			
			JPanel p_center = new JPanel();
			p_center.setLayout(new BorderLayout());
			p_center.add(p,BorderLayout.CENTER);
			p_center.add(p2,BorderLayout.SOUTH);
			
			//프레임의 가운데에 입력상자와 버튼을 담고 있는 P_center 패널을 담기
			add(p_center, BorderLayout.CENTER);
			//테이블을 담고있는 스크롤팬을 프레임의 아래쪽에 담기 
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
							
							//데이터베이스 insert를 수행하는 메소드를 만들고 호출하도록 수정
							
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

					//rowData의 index번째의 벡터를 끄집어낸
					Vector vr	= rowData.get(index);
					 
					//그 벡터의 요소를 차례대로 입력상자에 출력
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



