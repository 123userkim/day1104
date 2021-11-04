package com.sist.goods;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class InsertGoods extends JFrame {
	
	JTextField jtf_no; //��ǰ��ȣ�� �Է¹ޱ� ���� �Է»���
	JTextField jtf_item; // ��ǰ��
	JTextField jtf_qty;  //��ǰ ����
	JTextField jtf_price; // ����
	
	
	
	public InsertGoods() {
		jtf_no = new JTextField();
		jtf_item = new JTextField();
		jtf_qty = new JTextField();
		jtf_price = new JTextField();
		
		//�Է»��ڵ�� ������ �Է��ؾ��ϴ��� �����ϴ� �󺧵��� ��� ���� �г�
		JPanel p =new JPanel();
		p.setLayout(new GridLayout(4,2));
		
		//�гο� �󺧰� �ؽ�Ʈ �ʵ带 ���ʴ�� ���
		p.add(new JLabel("��ǰ��ȣ"));
		p.add(jtf_no);
		
		p.add(new JLabel("��ǰ�̸�"));
		p.add(jtf_item);
				
		p.add(new JLabel("��ǰ����"));
		p.add(jtf_qty);		
		
		p.add(new JLabel("��ǰ����"));
		p.add(jtf_price);
		
		JButton button = new JButton("�߰�");
		
		//�������� ��� �г��� ��� �Ʒ��ʿ� ��ư�� �ڹ�
		add(p,BorderLayout.CENTER);
		add(button,BorderLayout.SOUTH);
		
		setSize(40,300);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//�߰���ư�� ������ ����ڰ� �Է��� ��ǰ��ȣ,��ǰ�̸�,��ǰ����, ��ǰ�������� �����ͺ��̽� ��
		//���̺� �ڷ� �߰�
		button.addActionListener(new  ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int no=Integer.parseInt( jtf_no.getText()  )  ;
				String item=jtf_item.getText();
				int qty=Integer.parseInt( jtf_qty.getText()  )  ;
				int price=Integer.parseInt(jtf_price.getText()  )  ;
				
				
				String sql= "insert into goods values(" + no + ",'" + item + "'," + qty + "," + price + ")";
	 
				Connection conn=null;
				Statement stmt=null;
				try {
					//1.jdbc����̹��� �Ÿ𸮷� �ε�
					Class.forName("oracle.jdbc.driver.OracleDriver");
					
					//2. DB������ ����
					conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE  "  ,"c##sist" , "sist" );
				
				
					stmt = conn.createStatement();
								 
					int re = stmt.executeUpdate(sql);
					
					if(re==1) {
						System.out.println("��ǰ��Ͽ� ����");
						jtf_no.setText("");
						jtf_item.setText("");
						jtf_qty.setText("");
						jtf_price.setText("");
						
						
					}else if(re==0) {
						System.out.println("��ǰ��Ͽ� ����");
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
			
			
			
			
			}});
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new InsertGoods();

	}

}
