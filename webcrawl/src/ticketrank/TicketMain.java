/*
 * Program: Movie Crawler Ver1.2
 * Developer: miiiiigy
 * Date: 2020.02.07
 * Summary : ��ȭ �ǽð� ���� 1~10�� (���̹�, ����)���� ������� ���̹��� �������� ���� ���(����)�� �����ϰ�
 * 			��ۼ��� ������ ��跮�� ����ϴ� ���α׷�
 * Tools : Java, Jsoup, Jdbc, Mybaits, Oracle, SqlDeveloper, SQL
 */
package ticketrank;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TicketMain {
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		int selCode = 0; // ����ڰ� ������ ��ȭ(����)
		
		// 1~10������ ��ȭ����� �����ڵ带 �����ϴ� �迭
		// [i] = ��ȭ 1�� ~ 10��
		// �迭�ε��� : 0~9, �����ε��� : 1~10, �����: 1~10
		// [i][0] = ��ȭ����, [i][1] = Naver�ڵ�, [i][2] = Daum�ڵ�
		String[][] movieArr = new String[10][3];
		
		// ����ð� ���
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:MM:ss");
		Date date = new Date(); // ����ð� ���ϱ�
		String time = dateFormat.format(date);
		// System.out.println(date);
		
		
		// ���α׷� ���ۺ� ���
		System.out.println("�����������������������������������������������������������������������");
		System.out.println("���� MovieCrawler Ver1.2");
		System.out.println("���� Developer : miiiiigy");
		System.out.println("�����������������������������������������������������������������������");
		System.out.println("���� Movie Ticketing Ranking(CurrentTime:" +time +")");
		
		// ���̹�, ���� ���� 1~10�� ��ȭ�����ڵ带 �����ϴ� URL
		String naverRank = "https://movie.naver.com/movie/running/current.nhn"; // ���̹� ���� 1~10��
		String daumRank = "http://ticket2.movie.daum.net/Movie/MovieRankList.aspx"; // ���� ���� 1~10��
		
		// ���̹� ��ȭ �ǽð� ���� ���� 1�� ~ 99������ ����
		Document naverDoc = Jsoup.connect(naverRank).get();
		Elements naverList = naverDoc.select("dt.tit > a");
		
		// ���̹� ��ȭ ���� ���� 1~10������ ����
		for(int i = 0; i < 10; i++) {
			movieArr[i][0] = naverList.get(i).text(); // ��ȭ����
			// href = /movie/bi/mi/basic.nhn?code=181925
			String href = naverList.get(i).attr("href");
			// System.out.println(naverList.get(i).attr("href"));
			
			// substring(start, end); substring(0, 10) 0~9�����̽�
			// substring(start); substring(5) 5~������ �����̽�
			// indexOf() ���� ���ϴ� �ؽ�Ʈ�� �ε��� ��ȣ�� ����
			//  => ó������ ã��, �ؽ�Ʈ�� = �� 3�� ���ԵǾ�������
			//  => ù��° =�� �ε�����ȣ�� ������
			// String str = "���=����=����";
			// str.indexOf("="); >>>2
			// lastIndexOf() == ���������� �˻�
			// str.lastIndexOf("=") >>>5
			movieArr[i][1] = href.substring(href.lastIndexOf("=")+1);// ���̹���ȭ �ڵ�
			// System.out.println(href.substring(href.lastIndexOf("=")+1));
		}
		
		// ���̹� ��ȭ �ǽð� ���� ���� 1�� ~ ������ ����
		Document daumDoc = Jsoup.connect(daumRank).get();
		Elements daumList = daumDoc.select("Strong.tit_join > a");
		
		// ���� ��ȭ ���� ���� 1��~10������ ����
		for(int j =0; j < 10; j++) {
			String url = daumList.get(j).attr("href");
			// ������ ��ȭ����
			Document detailMovie = Jsoup.connect(url).get();
			String href =detailMovie.select("div.wrap_pbtn > a").attr("href");
			movieArr[j][2] = href.substring(href.lastIndexOf("=")+1); // �����ڵ�
			// System.out.println(href);
			// System.out.println(daumList.get(j).attr("href"));
		}
		//	int count = 0;
		//	for (Element element : daumList) {
		//		count++;
		//		System.out.println(element);
		//	}
		//	System.out.println(count);
		
		// ���α׷� ��ȭ��ŷ(1~10��) ���
		for(int i = 0; i< movieArr.length; i++) {
			System.out.println("���� " + (i+1) + "�� \t"+movieArr[i][0]);
		}
		System.out.println("�������������������������������������������������");
		System.out.println("���� Please enter a movie rank");
		
		// ����� �Է°� ��ȿ�� üũ
		while(true) {
			System.out.print("��� >>");
			selCode = sc.nextInt();
			
			if(selCode >= 1 && selCode <= 10) {
				break;
			} else {
				System.out.println("");
				continue;
			}
		
		}
		
		// ���� ����ڰ� �Է��� ��ȭ ���� ����!!	!(���̹�, ����)
		// ���̹� ����
		
		// ����ڰ� 1 >> 1�� Ŭ����
		NaverTicket nTicket = new NaverTicket();
		TicketDTO nDto = nTicket.naverCrawler(movieArr[selCode-1][0], movieArr[selCode-1][1]);
		// ���� ����
		DaumTicket dTicket = new DaumTicket();
		TicketDTO dDto = dTicket.daumCrawler(movieArr[selCode-1][0], movieArr[selCode-1][2]);
 
		
		// nDto => ���̹� ��ۼ�, ������ ��
		// dDto => ���� ��ۼ�, ������ ��
		
		// ���α׷� ������� �����跮
		int nCnt = nDto.getCount(); // ���̹� ��� ��
		int dCnt = dDto.getCount(); // ���� ��� ��
		int totalCnt = nCnt + dCnt; // ���̹� + ���� ��� ��
		
		int nSum = nDto.getTotal(); // ���̹� ������
		int dSum = dDto.getTotal(); // ���� ������
		int totalSum = nSum + dSum; // ���̹� + ���� ������
		
		DecimalFormat df = new DecimalFormat("0.0");
		double nAvg = (double)nSum/nCnt; // ���̹� �������
		double dAvg = (double)dSum/dCnt; // ���̹� �������
		double totalAvg = (double)totalSum/totalCnt; // ���̹� �������
		
		// ���α׷� ������� �м�
		String report = ""; // �м���� ����
		if(totalAvg >= 8 && totalAvg <= 10){
			report = "�� �����ϴ� ";
		} else if (totalAvg >= 6 && totalAvg < 8) {
			report = "����ִ�";
		}else if (totalAvg >= 4 && totalAvg < 6) {
			report = "Ÿ��ų����!";
		}else if (totalAvg >= 2 && totalAvg < 4) {
			report = "�ð� �Ʊ��";
		} else {
			report = "�ʹ� ��̾��� ������ �ñ�������";
		}
		
		// ���α׷� ������� ���
		System.out.println("�ƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢ�");
		System.out.println("�ƢƢ� Collection completed :");
		System.out.println("�ƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢƢ�");
		System.out.println("�ƢƢ� 1. Summary");
		System.out.println("�ƢƢ� NAVER Conut :" +nCnt);
		System.out.println("�ƢƢ� NAVER Sum :" +nSum);
		System.out.println("�ƢƢ� NAVER Avg :" +df.format(nAvg)); // type >> String
		System.out.println("�ƢƢ� ==========================================================");
		System.out.println("�ƢƢ� DAUM Conut :" +dCnt);
		System.out.println("�ƢƢ� DAUM Sum :" +dSum);
		System.out.println("�ƢƢ� DAUM Avg :" + df.format(dAvg));
		System.out.println("�ƢƢ� ==========================================================");
		System.out.println("�ƢƢ� Total Reply Conut :" +totalCnt);
		System.out.println("�ƢƢ� Total Sum :" +totalCnt);
		System.out.println("�ƢƢ� Total Reply Avg :" +df.format(totalAvg));
		System.out.println("�ƢƢ� ==========================================================");
		System.out.println("�ƢƢ� 2. Report");
		System.out.println("�ƢƢ� " + movieArr[selCode-1][0] + "���� �� �м����");
		System.out.println("�ƢƢ� ��ü ��� ��" + totalCnt +", ������� " + df.format(totalAvg) +"��");
		System.out.println("�ƢƢ� " + report +"��ȭ�Դϴ�.");
		

//		// ���� : �迭(��ȭ����, naver�ڵ�, daum�ڵ�)
//		for (int i = 0; i < movieArr.length; i++) {
//			for(int j = 0; j < 3; j++) {
//				System.out.println(movieArr[i][j] + "\t");
//			}
//			System.out.println();
//		}
		// int count = 0;
		// for (Element element : naverList) {
		//		 count++;
		//	System.out.println(element);
		//	}
		//		System.out.println(count);
		
	}
	
	

}
