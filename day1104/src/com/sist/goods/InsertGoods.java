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
	
	JTextField jtf_no; //상품번호를 입력받기 위한 입력상자
	JTextField jtf_item; // 상품명
	JTextField jtf_qty;  //상품 수량
	JTextField jtf_price; // 가격
	
	
	
	public InsertGoods() {
		jtf_no = new JTextField();
		jtf_item = new JTextField();
		jtf_qty = new JTextField();
		jtf_price = new JTextField();
		
		//입력상자들과 무엇을 입력해야하는지 설명하는 라벨들을 담기 위한 패널
		JPanel p =new JPanel();
		p.setLayout(new GridLayout(4,2));
		
		//패널에 라벨과 텍스트 필드를 차례대로 담기
		p.add(new JLabel("상품번호"));
		p.add(jtf_no);
		
		p.add(new JLabel("상품이름"));
		p.add(jtf_item);
				
		p.add(new JLabel("상품수량"));
		p.add(jtf_qty);		
		
		p.add(new JLabel("상품가격"));
		p.add(jtf_price);
		
		JButton button = new JButton("추가");
		
		//프레임의 가운데 패널을 담고 아래쪽에 버튼을 닥미
		add(p,BorderLayout.CENTER);
		add(button,BorderLayout.SOUTH);
		
		setSize(40,300);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//추가버튼을 누르면 사용자가 입력한 상품번호,상품이름,상품수량, 상품가격으로 데이터베이스 ㅌ
		//테이블에 자료 추가
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
					//1.jdbc드라이버를 매모리로 로드
					Class.forName("oracle.jdbc.driver.OracleDriver");
					
					//2. DB서버에 연결
					conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE  "  ,"c##sist" , "sist" );
				
				
					stmt = conn.createStatement();
								 
					int re = stmt.executeUpdate(sql);
					
					if(re==1) {
						System.out.println("상품등록에 성공");
						jtf_no.setText("");
						jtf_item.setText("");
						jtf_qty.setText("");
						jtf_price.setText("");
						
						
					}else if(re==0) {
						System.out.println("상품등록에 실패");
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
			
			
			
			
			}});
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new InsertGoods();

	}

}
