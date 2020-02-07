package ticketrank;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import domain.MovieDTO;
import persistence.MovieDAO;

public class NaverTicket {
	String base = "";
	int page = 1; // ������ ��������(1page = ��� 10��)
	String url = "";
	int count = 0;
	int total = 0;
	String compare = "";
	MovieDAO mDao = new MovieDAO();
	
	// ���̹� ��ȭ ���(����)�� �����ϴ� �޼���
	public TicketDTO naverCrawler(String movie, String code) throws IOException {
		base = "https://movie.naver.com/movie/bi/mi/pointWriteFormList.nhn?code="+code+"&type=after&isActualPointWriteExecute=false&isMileageSubscriptionAlready=false&isMileageSubscriptionReject=false&page=";
		url = base +page;
		
		int score = 0, regdate = 0, count = 0;
		String title = "", writer ="", content = "", basedate="", compare ="", subdate = "";
		
		System.out.println("�ƢƢƢ�NAVER START�ƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢ�");
		
		label:while(true) {
			Document doc = Jsoup.connect(url).get();
			Elements reply = doc.select("div.score_result li");
		
			
			
			if(reply.equals(writer)) {
				break label;
			}
			// for(int i = 0; i<reply.size(); i++
			// content = reply.get(i).select
			for(int i = 0; i< reply.size(); i++) {
				count++;
				// Elements movieName = doc.select("");
				// title = movieName.text();
				writer = reply.select("div.score_reple dl span").text(); // �ۼ���
				score = Integer.parseInt(reply.get(i).select("div.star_score > em").text()); // ����
				content = reply.select("div.score_reple > p").text(); // ����
				basedate =reply.select("div.score_reple dt em:last-child").text();
//				compare = Integer(reply.get(i).select(".score_reple dt em:nth-child()").text());
				
				subdate = basedate.substring(0,10); 
				regdate = Integer.parseInt(subdate.replace(".","")); // ��¥�Է�
				
				if(i == 0) {
					if(compare.equals(writer)){
						break label;
					} else {
						compare=writer;
					}
					
				} 
				total += score;
				
				MovieDTO mDto = new MovieDTO(movie, content, writer, score, "naver", regdate);
				
				mDao.addMovie(mDto);
				System.out.println("�ƢƢƢƢƢƢƢƢƢƢƢƢ�"+count+"�� �ƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢ�");
				System.out.println("��ȭ: " + movie);
				System.out.println("����: " + score);
				System.out.println("�ۼ���: " + writer);
				System.out.println("����: " + content);
				System.out.println("�ۼ�����: " +regdate);
				
			}// for��
			
			page = page + 1;
			url = base + page;
		
		}// while ��
		
		System.out.println("�ƢƢƢ�NAVER END�ƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢ�");
		// TicketMain���� ��ۼ�, ������ ���� return
		TicketDTO tDto = new TicketDTO(count, total);	
		return tDto;
	}



}
