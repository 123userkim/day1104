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


public class GoodsTestDB extends JFrame {
	
	JTextField jtf_no; //상품번호를 입력받기 위한 입력상자
	JTextField jtf_item; // 상품명
	JTextField jtf_qty;  //상품 수량
	JTextField jtf_price; // 가격
	
	JTable table ; //모든 상품목록을 엑설과 같은 모양으로 보여주기 위한 테이블
	Vector colNames;//데이터 칼럼을 위한 선언
	Vector<Vector> rowData; //진짜 자료들 : lowData 테이블의 데이터를 위한 백터 선언
	
	
	//모든 데이터베이스에 연결하여 Goods 테이블에 모든 상품을 읽어와서 JTable에 출력하는 메소드 만들기
	public void printGoods(){
		//새로운 상품을 등록하여 계속 호출될 수 있으니  RowData를 먼저 깨끗이 비워줌
		rowData.clear();
		
		String sql = "select * from goods";
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs= null;
		
		try{
			
			//1.드라이버 메모리로 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//2.db서버에 연결
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE  "  ,"c##sist" , "sist" );
			
			//3.statement 객체 연결
			stmt = conn.createStatement();
			
			////데이터베이스 명령을 실행
			rs = stmt.executeQuery(sql);
			
			//검색한 자료가 있는 만큼 반복실행하여 한 행씩 읽어와서 그것을 벡터로 만들어
			//그 백터를 테이블의 자룔를 위한 rowData에 담기
			
			//resultSet의 결과에서 특정 레코드를 가리키는 커서가 있음
			//이 커서를  한 행씩 옮겨주는 메소드는 next()
			//메소드는 다음의 레코드가 있어서 성공적으로 옮겨지면 true를 반환하고
			//다음의 레코드가 더이상 없어서 옮겨지지 않으면 false반환
			//그래서 보통 resultSet의 결과를 모두 읽어서 처리하려면 다음과 같이 while문 이용
			
			while(rs.next()) {
				//현재 커서가 바라보고 있는 레코드의 속성 값을 읽어오기 위해선
				//자료형에 따라 get~~()메소드를 사용
				//컬럼의 값이 문자열이라면 getString()을 이용
				//컬럼의 값이 정수형이라면 getInt()
				//이때 매개변수로 컬럼의 인덱스나 컬럼의 이름을 문자열로 전달함
				//여기서 인덱스는 1부터 시작함
				
				int
				no = rs.getInt(1);
				String item = rs.getString(2);
				int qty = rs.getInt(3);
				int price = rs.getInt(4);
				
				//백터를 하나 만들고 그 벡터에 no, item,qty, price를 담기
				Vector v = new Vector();
				v.add(no);
				v.add(item);
				v.add(qty);
				v.add(price);
				
				//그 벡터를 rowData에 담기
				rowData.add(v);

				//바뀐 내용으로 테이블을 다시 그림
				table.updateUI();
				//처음 테이블을 만들 때에 rowData벡터가 비어있었음
				//나중에 테이블의 데이터인 rowData에 바뀐 자료를 적응하기 위해
				//updateUI를 호출해야함

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
	
	public GoodsTestDB() {
		
		colNames = new Vector<String>();
		colNames.add("상품번호");
		colNames.add("상품명");
		colNames.add("수량");
		colNames.add("단가");
		
		rowData = new Vector<Vector>();
		
		
		
		
		
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
		
		
		
		
		//목록 버튼을 누르면 
		//데이터베이스에 연결하여 모든 상품목록을 읽어와서 테이블에 출력하는 메소드를 호출함
		button_list.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				printGoods();
				
			}
			
			
			
			
			
		});
		
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
						//상품등록을 성공하면 테이블의 내용을 보이도록 메소드 호출
						printGoods();
						
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
		
		new GoodsTestDB();

	}

}
