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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;


public class GoodsTest extends JFrame {
	
	JTextField jtf_no; //상품번호를 입력받기 위한 입력상자
	JTextField jtf_item; // 상품명
	JTextField jtf_qty;  //상품 수량
	JTextField jtf_price; // 가격
	
	JTable table ; //모든 상품목록을 엑설과 같은 모양으로 보여주기 위한 테이블
	Vector colNames;//데이터 칼럼을 위한 선언
	Vector<Vector> rowData; //진짜 자료들 : lowData 테이블의 데이터를 위한 백터 선언
	
	
	
	
	public GoodsTest() {
		
		colNames = new Vector<String>();
		colNames.add("상품번호");
		colNames.add("상품명");
		colNames.add("수량");
		colNames.add("단가");
		
		rowData = new Vector<Vector>();
		Vector<String> v1= new Vector();
		v1.add("1");
		v1.add("색종이");
		v1.add("10");
		v1.add("1500");
		
		
		rowData = new Vector<Vector>();
		Vector v2= new Vector();
		v2.add("2");
		v2.add("딱풀");
		v2.add("20");
		v2.add("1200");
		
		rowData.add(v1);
		rowData.add(v2);
		
		//컬럼이름이 있는 cloName 백터와 실제 데이터 있는 rowData 백터를 갖고 엑셀과 같은 화면의 테이블 생성
		table = new JTable(rowData,colNames );
		
		//테이블 자료가 넘 많아서 한 화면에 보이지 않을 때 자동으로 스크롤 만들기
		JScrollPane jsp = new JScrollPane(table);
		
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
		JButton button_list = new JButton("목록");
		
		//버튼을 담을 패널
		JPanel p2 = new JPanel();
		p2.add(button);
		p2.add(button_list);
		
		//입력상자 +버튼이 있는 패널 담기 위한 패널
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
		
		//추가버튼을 누르면 사용자가 입력한 상품번호,상품이름,상품수량, 상품가격으로 데이터베이스 
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
		
		new GoodsTest();

	}

}
