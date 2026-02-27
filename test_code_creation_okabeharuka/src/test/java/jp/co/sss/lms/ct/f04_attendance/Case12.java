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
import org.openqa.selenium.support.ui.Select;

/**
 * 結合テスト 勤怠管理機能
 * ケース12
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース12 受講生 勤怠直接編集 入力チェック")
public class Case12 {

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
		
		//目的の画面遷移ができているかチェック
		WebElement attendance = webDriver.findElement(By.className("btn-success"));		
		assertEquals("定時", attendance.getText());
		
		getEvidence(new Object(){});
		
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 不適切な内容で修正してエラー表示：出退勤の（時）と（分）のいずれかが空白")
	void test05() {
		
		//出勤の時間を空にする
		Select emptyStartHour = new Select(webDriver.findElement(By.name("attendanceList[0].trainingStartTimeHour")));
		emptyStartHour.selectByIndex(0);		
		
		scrollTo("500");
		
		//更新ボタンをクリック
		webDriver.findElement(By.className("update-button")).click();
		
		//アラートのテキストを取得し「OK」をクリック
		Alert alert = webDriver.switchTo().alert();
		alert.getText();
		alert.accept();	
		
		//要素の可視性タイムアウト設定
		visibilityTimeout(By.className("btn-success"), 10);	
		
		//エラー文表示チェック
		assertEquals("* 出勤時間が正しく入力されていません。", webDriver.findElement(By.className("error")).getText());
		
		//エラー時のクラス名が一致しているか確認
		WebElement errEmptyStartHour = webDriver.findElement(By.name("attendanceList[0].trainingStartTimeHour"));		
		assertEquals("form-control errorInput", errEmptyStartHour.getAttribute("class"));
		
		getEvidence(new Object(){}, "EmptyStartHour");
		
		//出勤の時間を9時に設定する
		Select startHour = new Select(webDriver.findElement(By.name("attendanceList[0].trainingStartTimeHour")));
		startHour.selectByValue("9");
		
		//出勤の分数を空にする
		Select emptyStartMinute = new Select(webDriver.findElement(By.name("attendanceList[0].trainingStartTimeMinute")));
		emptyStartMinute.selectByIndex(0);
		
		scrollTo("500");
		
		//更新ボタンをクリック
		webDriver.findElement(By.className("update-button")).click();
		
		//アラートのテキストを取得し「OK」をクリック
		alert.getText();
		alert.accept();	
		
		//要素の可視性タイムアウト設定
		visibilityTimeout(By.className("btn-success"), 10);	
		
		//エラー文表示チェック
		assertEquals("* 出勤時間が正しく入力されていません。", webDriver.findElement(By.className("error")).getText());
		
		//エラー時のクラス名が一致しているか確認
		WebElement errEmptyStartMinute = webDriver.findElement(By.name("attendanceList[0].trainingStartTimeMinute"));
		assertEquals("form-control errorInput", errEmptyStartMinute.getAttribute("class"));
		
		getEvidence(new Object(){}, "EmptyStartMinute");
		
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正してエラー表示：出勤が空白で退勤に入力あり")
	void test06() {
		
		//定時ボタンをクリック
		List<WebElement> attendances = webDriver.findElements(By.className("btn-success"));
		attendances.get(0).click();
		
		//出勤の時間、分数ともに空にする
		Select startHour = new Select(webDriver.findElement(By.name("attendanceList[0].trainingStartTimeHour")));
		startHour.selectByIndex(0);			
		Select startMinute = new Select(webDriver.findElement(By.name("attendanceList[0].trainingStartTimeMinute")));
		startMinute.selectByIndex(0);		
		
		scrollTo("500");
		
		//更新ボタンをクリック
		webDriver.findElement(By.className("update-button")).click();
		
		//アラートのテキストを取得し「OK」をクリック
		Alert alert = webDriver.switchTo().alert();
		alert.getText();
		alert.accept();			
		
		//要素の可視性タイムアウト設定
		visibilityTimeout(By.className("btn-success"), 10);	
		
		//エラー文表示チェック
		assertEquals("* 出勤情報がないため退勤情報を入力出来ません。", webDriver.findElement(By.className("error")).getText());
		
		//エラー時のクラス名が一致しているか確認
		WebElement errEmptyStartHour = webDriver.findElement(By.name("attendanceList[0].trainingStartTimeHour"));				
		assertEquals("form-control errorInput", errEmptyStartHour.getAttribute("class"));
		
		//エラー時のクラス名が一致しているか確認
		WebElement errEmptyStartMinute = webDriver.findElement(By.name("attendanceList[0].trainingStartTimeMinute"));		
		assertEquals("form-control errorInput", errEmptyStartMinute.getAttribute("class"));
		
		getEvidence(new Object(){});			
		
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正してエラー表示：出勤が退勤よりも遅い時間")
	void test07() {
		
		//定時ボタンをクリック
		List<WebElement> attendances = webDriver.findElements(By.className("btn-success"));
		attendances.get(0).click();
		
		//出勤の時間を9時、分数を00分に設定する
		Select startHour = new Select(webDriver.findElement(By.name("attendanceList[0].trainingStartTimeHour")));
		startHour.selectByValue("19");			
		Select startMinute = new Select(webDriver.findElement(By.name("attendanceList[0].trainingStartTimeMinute")));
		startMinute.selectByValue("0");		
		
		scrollTo("500");
		
		//更新ボタンをクリック
		webDriver.findElement(By.className("update-button")).click();
		
		//アラートのテキストを取得し「OK」をクリック
		Alert alert = webDriver.switchTo().alert();
		alert.getText();
		alert.accept();			
		
		//要素の可視性タイムアウト設定
		visibilityTimeout(By.className("btn-success"), 10);	
		
		//エラー文表示チェック
		assertEquals("* 退勤時刻[0]は出勤時刻[0]より後でなければいけません。", webDriver.findElement(By.className("error")).getText());
		
		//エラー時のクラス名が一致しているか確認
		WebElement errEndHour = webDriver.findElement(By.name("attendanceList[0].trainingEndTimeHour"));				
		assertEquals("form-control errorInput", errEndHour.getAttribute("class"));
		
		//エラー時のクラス名が一致しているか確認
		WebElement errEndMinute = webDriver.findElement(By.name("attendanceList[0].trainingEndTimeMinute"));		
		assertEquals("form-control errorInput", errEndMinute.getAttribute("class"));
		
		getEvidence(new Object(){}, "StartHour");		
		
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正してエラー表示：出退勤時間を超える中抜け時間")
	void test08() {
		
		//出勤の時間を9時、分数を00分に設定する
		Select startHour = new Select(webDriver.findElement(By.name("attendanceList[0].trainingStartTimeHour")));
		startHour.selectByValue("9");					
		Select startMinute = new Select(webDriver.findElement(By.name("attendanceList[0].trainingStartTimeMinute")));
		startMinute.selectByValue("0");		
		
		//退勤の時間を10時、分数を00分に設定する
		Select endHour = new Select(webDriver.findElement(By.name("attendanceList[0].trainingEndTimeHour")));
		endHour.selectByValue("10");				
		Select endMinute = new Select(webDriver.findElement(By.name("attendanceList[0].trainingEndTimeMinute")));
		endMinute.selectByValue("0");		
		
		//中抜け時間を5時間に設定する
		Select blankTime = new Select(webDriver.findElement(By.name("attendanceList[0].blankTime")));
		blankTime.selectByValue("315");		
		
		scrollTo("500");
		
		//更新ボタンをクリック
		webDriver.findElement(By.className("update-button")).click();
		
		//アラートのテキストを取得し「OK」をクリック
		Alert alert = webDriver.switchTo().alert();
		alert.getText();
		alert.accept();			
		
		//要素の可視性タイムアウト設定
		visibilityTimeout(By.className("btn-success"), 10);	
		
		//エラー文表示チェック
		assertEquals("* 中抜け時間が勤務時間を超えています。", webDriver.findElement(By.className("error")).getText());
		
		//エラー時のクラス名が一致しているか確認
		WebElement errBlankTime = webDriver.findElement(By.name("attendanceList[0].blankTime"));		
		assertEquals("form-control errorInput", errBlankTime.getAttribute("class"));
		
		getEvidence(new Object(){}, "StartHour");	
		
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正してエラー表示：備考が100文字超")
	void test09() {
		
		//中抜け時間を空にする
		Select blankTime = new Select(webDriver.findElement(By.name("attendanceList[0].blankTime")));
		blankTime.selectByIndex(0);		
		
		//備考欄の要素を取得、空にする
		WebElement note = webDriver.findElement(By.name("attendanceList[0].note"));
		note.clear();
		
		//備考欄にaを101文字渡す
		String text = "a".repeat(101);
		note.sendKeys(text);
		
		scrollTo("500");
		
		//更新ボタンをクリック
		webDriver.findElement(By.className("update-button")).click();
		
		//アラートのテキストを取得し「OK」をクリック
		Alert alert = webDriver.switchTo().alert();
		alert.getText();
		alert.accept();			
		
		//要素の可視性タイムアウト設定
		visibilityTimeout(By.className("btn-success"), 10);	
		
		//エラー文表示チェック
		assertEquals("* 備考の長さが最大値(100)を超えています。", webDriver.findElement(By.className("error")).getText());
		
		//エラー時のクラス名が一致しているか確認
		WebElement errNote = webDriver.findElement(By.name("attendanceList[0].note"));		
		assertEquals("form-control errorInput", errNote.getAttribute("class"));
		
		getEvidence(new Object(){});	
	}

}
