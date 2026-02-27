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
 * ケース11
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース11 受講生 勤怠直接編集 正常系")
public class Case11 {

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
	@DisplayName("テスト04 「勤怠情報を直接編集する」リンクから勤怠情報直接変更画面に遷移")
	void test04() {
		
		//「勤怠情報を直接編集する」リンクをクリック
		webDriver.findElement(By.linkText("勤怠情報を直接編集する")).click();	
		
		//要素の可視性タイムアウト設定
		visibilityTimeout(By.className("btn-success"), 10);	
		
		WebElement attendance = webDriver.findElement(By.className("btn-success"));
		
		//目的の画面遷移ができているかチェック
		assertEquals("定時", attendance.getText());
		
		getEvidence(new Object(){});
		
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 すべての研修日程の勤怠情報を正しく更新し勤怠管理画面に遷移")
	void test05() {
		
		//定時ボタンをクリック
		List<WebElement> attendances = webDriver.findElements(By.className("btn-success"));
		attendances.get(0).click();
		
		scrollTo("500");
		
		//更新ボタンをクリック
		webDriver.findElement(By.className("update-button")).click();
		
		//アラートのテキストを取得し「OK」をクリック
		Alert alert = webDriver.switchTo().alert();
		alert.getText();
		alert.accept();	
		
		//要素の可視性タイムアウト設定
		visibilityTimeout(By.cssSelector("h2"), 10);			
		
		//出退勤の一覧の要素をすべて取得し、そこから2025年2月25日のカリキュラム名を取得
		List<WebElement> punches = webDriver.findElements(By.cssSelector("tr"));	
		List<WebElement> curriculums = punches.get(1).findElements(By.cssSelector("td"));
		WebElement curriculum = curriculums.get(1);
		
		//目的の画面遷移ができているかチェック
		assertEquals("ハードウェア、ソフトウェア、WWW", curriculum.getText());
			
		//出退勤のすべての要素のtdだけから2025年2月25日の出勤が押せているかを確認
		List<WebElement> punchIns = punches.get(1).findElements(By.cssSelector("td"));		
		assertEquals("09:00", punchIns.get(2).getText());	
				
		//出退勤のすべての要素のtdだけから2025年2月25日の退勤が押せているかを確認
		List<WebElement> punchOut = punches.get(1).findElements(By.cssSelector("td"));
		assertEquals("18:00", punchOut.get(3).getText());	
		
		getEvidence(new Object(){});		
		
	}

}
