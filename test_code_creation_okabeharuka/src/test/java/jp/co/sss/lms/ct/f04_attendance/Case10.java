package jp.co.sss.lms.ct.f04_attendance;

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
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト 勤怠管理機能
 * ケース10
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース10 受講生 勤怠登録 正常系")
public class Case10 {

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
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {
		
		//「勤怠」リンクをクリック
		webDriver.findElement(By.linkText("勤怠")).click();	
		
		Alert alert = webDriver.switchTo().alert();
		alert.accept();			
		
		//要素の可視性タイムアウト設定
		visibilityTimeout(By.cssSelector("h2"), 10);		
		
		//目的の画面遷移ができているかチェック
		assertEquals("勤怠情報変更｜LMS", webDriver.getTitle());
		
		getEvidence(new Object(){});
		
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「出勤」ボタンを押下し出勤時間を登録")
	void test04() {
		
		//出勤ボタンをクリック
		webDriver.findElement(By.name("punchIn")).click();
		
		//アラートのテキストを取得し「OK」をクリック
		Alert alert = webDriver.switchTo().alert();
		alert.getText();
		alert.accept();		
		
		pageLoadTimeout(10);
		
		//出退勤の一覧の要素をすべて取得
		List<WebElement> punches = webDriver.findElements(By.cssSelector("tr"));
		
		//出退勤のすべての要素のtdだけをすべて取得
		List<WebElement> punchIns = punches.get(1).findElements(By.cssSelector("td"));
		
		//取得したすべてのtdの3つ目＝出勤時間の要素を取得し、空ではないかチェック
		assertTrue(!punchIns.get(2).equals(""));	
		
		getEvidence(new Object(){});
		
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「退勤」ボタンを押下し退勤時間を登録")
	void test05() {
		
		//退勤ボタンをクリック
		webDriver.findElement(By.name("punchOut")).click();
		
		//アラートのテキストを取得し「OK」をクリック
		Alert alert = webDriver.switchTo().alert();
		alert.getText();
		alert.accept();		
		
		pageLoadTimeout(10);
		
		//出退勤の一覧の要素をすべて取得
		List<WebElement> punches = webDriver.findElements(By.cssSelector("tr"));
		
		//出退勤のすべての要素のtdだけをすべて取得
		List<WebElement> punchOut = punches.get(1).findElements(By.cssSelector("td"));
		
		//取得したすべてのtdの4つ目＝出勤時間の要素を取得し、空ではないかチェック
		assertTrue(!punchOut.get(3).equals(""));
		
		getEvidence(new Object(){});		
		
	}

}
