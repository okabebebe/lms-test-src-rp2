package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		
		goTo("http://localhost:8080/lms");		
		
		assertEquals("ログイン | LMS", webDriver.getTitle());
		
		getEvidence(new Object(){});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		
		//初回ログイン済みの受講生ユーザーを入力してテスト
		webDriver.findElement(By.name("loginId")).sendKeys("StudentAA01");
		webDriver.findElement(By.name("password")).sendKeys("StudentAA001");		
		webDriver.findElement(By.className("btn-primary")).click();		
		
		//要素の可視性タイムアウト設定
		visibilityTimeout(By.cssSelector("h2"), 10);
		
		//タイトルタグの記述が一致しているか確認
		assertEquals("コース詳細 | LMS", webDriver.getTitle());	
		
		getEvidence(new Object(){});	
		
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		
		//詳細ボタン要素すべてを取得し、該当するボタンをクリック
		List<WebElement> courseDetails = webDriver.findElements(By.className("btn-default"));	
		courseDetails.get(4).click();
		
		//目的の画面遷移ができているかチェック
		WebElement courseName = webDriver.findElement(By.cssSelector("h2"));		
		assertEquals("関係演算子、条件分岐、繰り返し 2022年10月6日", courseName.getText());
		
		getEvidence(new Object(){});	
		
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		
		//要素の可視性タイムアウト設定
		visibilityTimeout(By.cssSelector("h2"), 10);
		
		//「日報【デモ】を提出する」ボタンをクリック
		webDriver.findElement(By.cssSelector("input[value='日報【デモ】を提出する']")).click();

		//目的の画面遷移ができているかチェック
		WebElement uploadReport = webDriver.findElement(By.cssSelector("h2"));		
		assertEquals("日報【デモ】 2022年10月6日", uploadReport.getText());
		
		getEvidence(new Object(){});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		
		//値を渡したい要素を取得
		WebElement report = webDriver.findElement(By.cssSelector("textarea"));
		
		//念のため要素の値を空にして渡したい値を渡す
		report.clear();
		report.sendKeys("test");
		
		getEvidence(new Object(){}, "beforeSubmission");
		
		//「提出する」ボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();				
		
		//要素の可視性タイムアウト設定
		visibilityTimeout(By.cssSelector("h2"), 10);
		
		//ボタン表示が「提出済み日報【デモ】を確認する」になったかチェック
		WebElement submissionReport = webDriver.findElement(By.cssSelector("input[value='提出済み日報【デモ】を確認する']"));		
		assertEquals("提出済み日報【デモ】を確認する", submissionReport.getAttribute("value"));
		
		getEvidence(new Object(){}, "afterSubmission");
		
	}

}
