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
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		
		//詳細ボタン要素すべてを取得し、該当するボタンをクリック
		List<WebElement> courseDetails = webDriver.findElements(By.className("btn-default"));	
		courseDetails.get(2).click();
		
		//目的の画面遷移ができているかチェック
		WebElement courseName = webDriver.findElement(By.cssSelector("h2"));		
		assertEquals("アルゴリズム、フローチャート 2022年10月2日", courseName.getText());
		
		scrollTo("50");
		
		getEvidence(new Object(){});	
	
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		
		//要素の可視性タイムアウト設定
		visibilityTimeout(By.cssSelector("h2"), 10);
		
		//「提出済み週報【デモ】を確認する」ボタンをクリック
		webDriver.findElement(By.cssSelector("input[value='提出済み週報【デモ】を確認する']")).click();

		//目的の画面遷移ができているかチェック
		WebElement updateReport = webDriver.findElement(By.cssSelector("h2"));		
		assertEquals("週報【デモ】 2022年10月2日", updateReport.getText());
		
		getEvidence(new Object(){});
		
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		
		//値を渡したい要素を取得
		WebElement weekReport = webDriver.findElement(By.name("contentArray[1]"));
		
		//念のため要素の値を空にして渡したい値を渡す
		weekReport.clear();
		weekReport.sendKeys("週報のテストサンプルです。");
		
		getEvidence(new Object(){}, "beforeSubmission");
		
		scrollTo("500");
		
		//「提出する」ボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();				
		
		//要素の可視性タイムアウト設定
		visibilityTimeout(By.cssSelector("h2"), 10);
		
		//目的の画面遷移ができているかチェック
		WebElement courseName = webDriver.findElement(By.cssSelector("h2"));		
		assertEquals("アルゴリズム、フローチャート 2022年10月2日", courseName.getText());
		
		getEvidence(new Object(){}, "afterSubmission");
		
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		
		//「ようこそ受講生ＡＡ１さん」リンクをクリック
		webDriver.findElement(By.linkText("ようこそ受講生ＡＡ１さん")).click();	
		
		//要素の可視性タイムアウト設定
		visibilityTimeout(By.cssSelector("h2"), 10);
		
		//目的の画面遷移ができているかチェック
		assertEquals("ユーザー詳細", webDriver.getTitle());
		
		getEvidence(new Object(){});
		
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		
		//指定位置までスクロール
		scrollTo("500");
		
		//すべての.btn-defaultのボタンを取得し、9番目のボタン＝週報レポートの詳細ボタンをクリック
		List<WebElement> reportDetails = webDriver.findElements(By.className("btn-default"));	
		reportDetails.get(9).click();
		
		//要素の可視性タイムアウト設定
		visibilityTimeout(By.cssSelector("h2"), 10);
		
		//すべてのテーブルタグを取得
		List<WebElement> reportTables = webDriver.findElements(By.tagName("table"));
		
		//すべてのテーブルタグの中からすべてのtdタグを取得
		List<WebElement> reportTds = reportTables.get(2).findElements(By.tagName("td"));
		
		//tdタグの1つ目(2つ目)＝所感欄を取得
		WebElement weeklyReport = reportTds.get(1); 
		
		//修正が反映されているかチェック
		assertEquals("週報のテストサンプルです。", weeklyReport.getText()); 

		getEvidence(new Object(){});
				
	}

}
