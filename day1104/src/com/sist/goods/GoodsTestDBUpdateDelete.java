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

public class GoodsTestDBUpdateDelete extends JFrame {
	
	JTextField jtf_no; //��ǰ��ȣ�� �Է¹ޱ� ���� �Է»���
	JTextField jtf_item; // ��ǰ��
	JTextField jtf_qty;  //��ǰ ����
	JTextField jtf_price; // ����
	
	JTable table ; //��� ��ǰ����� ������ ���� ������� �����ֱ� ���� ���̺�
	Vector colNames;//������ Į���� ���� ����
	Vector<Vector> rowData; //��¥ �ڷ�� : lowData ���̺��� �����͸� ���� ���� ����
	
	//������ ��ǰ��ȣ, ��ǰ�̸�, ����, ������ �Ű������� ���޹޾� �����ͺ��̽��� �����ϴ� �޼ҵ带 ����
		public void updateGoods(int no,String item,int qty, int price) {
			String sql = "update goods set item='"+item+"', qty="+qty+", price="+price+" where no="+no;
			Connection conn = null;
			Statement stmt = null;
			try {
				//1. jdbc����̹��� �޸𸮷� �ε��Ѵ�.
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
				//2. DB ������ �����Ѵ�.
				String url = "jdbc:oracle:thin:@localhost:1521:XE";
				String user = "c##sist";
				String pwd="sist";
				conn = DriverManager.getConnection(url, user, pwd);
				
				//3. Statement ��ü�� �����Ѵ�.
				stmt = conn.createStatement();
				
				//4. �����ͺ��̽� ����� �����Ѵ�
				int re =stmt.executeUpdate(sql);
				
				//������ ��ó���� �Ѵ�.
				if(re == 1) {
					System.out.println("��ǰ������ �����Ͽ����ϴ�.");
					printGoods();
				}else {
					System.out.println("��ǰ������ �����Ͽ����ϴ�.");
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
	
	
	///////////////////// Goods ���̺� ��� ��ǰ�� �о�ͼ� JTable�� ����ϴ� �޼ҵ� �����
	public void printGoods(){
		//���ο� ��ǰ�� ����Ͽ� ��� ȣ��� �� ������  RowData�� ���� ������ �����
		rowData.clear();
		
		String sql = "select * from goods";
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs= null;
		
		try{
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE  "  ,"c##sist" , "sist" );
			
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			//�˻��� �ڷᰡ �ִ� ��ŭ �ݺ������Ͽ� �� �྿ �о�ͼ� �װ��� ���ͷ� �����
			//�� ���͸� ���̺��� �ڷ㸦 ���� rowData�� ���
			
			//resultSet�� ������� Ư�� ���ڵ带 ����Ű�� Ŀ���� ����
			//�� Ŀ����  �� �྿ �Ű��ִ� �޼ҵ�� next()
			//�޼ҵ�� ������ ���ڵ尡 �־ ���������� �Ű����� true�� ��ȯ�ϰ�
			//������ ���ڵ尡 ���̻� ��� �Ű����� ������ false��ȯ
			//�׷��� ���� resultSet�� ����� ��� �о ó���Ϸ��� ������ ���� while�� �̿�
			
			while(rs.next()) {
				//���� Ŀ���� �ٶ󺸰� �ִ� ���ڵ��� �Ӽ� ���� �о���� ���ؼ�
				//�ڷ����� ���� get~~()�޼ҵ带 ���
				//�÷��� ���� ���ڿ��̶�� getString()�� �̿�
				//�÷��� ���� �������̶�� getInt()
				//�̶� �Ű������� �÷��� �ε����� �÷��� �̸��� ���ڿ��� ������
				//���⼭ �ε����� 1���� ������
				
				int
				no = rs.getInt(1);
				String item = rs.getString(2);
				int qty = rs.getInt(3);
				int price = rs.getInt(4);
				
				//���͸� �ϳ� ����� �� ���Ϳ� no, item,qty, price�� ���
				Vector v = new Vector();
				v.add(no);
				v.add(item);
				v.add(qty);
				v.add(price);
				
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
	
	
	
	/////////////////////��ǰ �߰�
	public void insertGoods( int no, String item, int qty, int price   ) {
		
		String sql= "insert into goods values(" + no + ",'" + item + "'," + qty + "," + price + ")";
		 
		Connection conn=null;
		Statement stmt=null;
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE  "  ,"c##sist" , "sist" );
	
			stmt = conn.createStatement();
						 
			int re = stmt.executeUpdate(sql);
			
			if(re==1) {
				System.out.println("��ǰ��Ͽ� ����");
				jtf_no.setText("");
				jtf_item.setText("");
				jtf_qty.setText("");
				jtf_price.setText("");
				//��ǰ����� �����ϸ� ���̺��� ������ ���̵��� �޼ҵ� ȣ��
				printGoods();
				
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
	
}
	/////////////////////��ǰ����	
	public void deleteGoods(int no) {
		
		String sql = "delete goods where no = "+no ;
		
		Connection conn = null;
		Statement stmt = null;
		
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE  "  ,"c##sist" , "sist" );
				
				stmt = conn.createStatement();			
			
				int re = stmt.executeUpdate(sql);
			
				if(re ==1 ) {
					System.out.println("��ǰ ���� ����");
					printGoods();
					jtf_item.setText("");
					jtf_no.setText("");
					jtf_price.setText("");
					jtf_qty.setText("");
					
				}else if(re ==0) {
					System.out.println("��ǰ ���� ����");
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
		
/////////////////////��������
	public GoodsTestDBUpdateDelete() {
		
		colNames = new Vector<String>();
		colNames.add("��ǰ��ȣ");
		colNames.add("��ǰ��");
		colNames.add("����");
		colNames.add("�ܰ�");
		
		rowData = new Vector<Vector>();
		
		//�÷��̸��� �ִ� cloName ���Ϳ� ���� ������ �ִ� rowData ���͸� ���� ������ ���� ȭ���� ���̺� ����
		table = new JTable(rowData,colNames );
		
		//���̺� �ڷᰡ �� ���Ƽ� �� ȭ�鿡 ������ ���� �� �ڵ����� ��ũ�� �����
		JScrollPane jsp = new JScrollPane(table);
		
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
		
		JButton button_add = new JButton("�߰�");
		JButton button_list = new JButton("���");
		JButton button_update = new JButton("����");
		JButton button_delete = new JButton("����");
		
		//��ư�� ���� �г�
		JPanel p2 = new JPanel();
		p2.add(button_add);
		p2.add(button_list);
		p2.add(button_update);
		p2.add(button_delete);
		
		//�Է»��� +��ư�� �ִ� �г� ��� ���� �г�
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
				
				int no= Integer.parseInt(  jtf_no.getText()  );
				String item= jtf_item.getText();
				int qty=  Integer.parseInt(jtf_qty.getText());
				int price=  Integer.parseInt(jtf_price.getText());
				
				
				updateGoods(no, item, qty, price);
				
			}});
		
		
		
		
		
		button_update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int no = Integer.parseInt(jtf_no.getText());
				int qty = Integer.parseInt(jtf_qty.getText());
				int price = Integer.parseInt(jtf_price.getText());
				String item = jtf_item.getText();
				updateGoods(no, item, qty, price);
				
			}});
		
		
		
		//��� ��ư�� ������ 
		//�����ͺ��̽��� �����Ͽ� ��� ��ǰ����� �о�ͼ� ���̺� ����ϴ� �޼ҵ带 ȣ����
		button_list.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				printGoods();
				
			}});
		
		
		
		//�߰���ư�� ������ ����ڰ� �Է��� ��ǰ��ȣ,��ǰ�̸�,��ǰ����, ��ǰ�������� �����ͺ��̽� 
				//���̺� �ڷ� �߰�
				button_add.addActionListener(new  ActionListener() {

					public void actionPerformed(ActionEvent e) {
						
						//�����ͺ��̽� insert�� �����ϴ� �޼ҵ带 ����� ȣ���ϵ��� ����
						
						int no=Integer.parseInt( jtf_no.getText()  )  ;
						String item=jtf_item.getText();
						int qty=Integer.parseInt( jtf_qty.getText()  )  ;
						int price=Integer.parseInt(jtf_price.getText()  )  ;
						
						insertGoods(no, item, qty, price);			
					
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
				jtf_item.setText(vr.get(1)+"");
				jtf_qty.setText(vr.get(2)+"");
				jtf_price.setText(vr.get(3)+"");
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
		
		new GoodsTestDBUpdateDelete();

	}

}
