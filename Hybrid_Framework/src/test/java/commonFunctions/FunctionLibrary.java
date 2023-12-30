package commonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.formula.functions.Column;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary {
	public static WebDriver driver;
	public static Properties conpro;
	//method for launching Browser
	public static WebDriver startBrowser() throws Throwable
	{
		conpro=new Properties();
		conpro.load(new FileInputStream("./PropertyFiles/Environment.properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			driver=new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
			driver=new FirefoxDriver();
		}
		else
		{
			Reporter.log("Browser value not Matching",true);
		}
		return driver;
	}
	//method for launching Url
	public static void openUrl(WebDriver driver)
	{
		driver.get(conpro.getProperty("Url"));
	}
	//method for wait for element
	public static void waitForElement(WebDriver driver,String Locator_Type,String Locator_Value,String Test_Data)
	{
		WebDriverWait mywait=new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(Test_Data)));
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locator_Value)));
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locator_Value)));
		}
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator_Value)));
		}
	}
	//method for textboxes
	public static void typeAction(WebDriver driver,String Locator_Type,String Locator_Value,String Test_Data)
	{
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locator_Value)).clear();
			driver.findElement(By.id(Locator_Value)).sendKeys(Test_Data);
			
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locator_Value)).clear();
			driver.findElement(By.name(Locator_Value)).sendKeys(Test_Data);
		}
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locator_Value)).clear();
			driver.findElement(By.xpath(Locator_Value)).sendKeys(Test_Data);
		}
	}
	//method for click action
	public static void clickAction(WebDriver driver,String Locator_Type,String Locator_Value)
	{
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locator_Value)).sendKeys(Keys.ENTER);
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locator_Value)).click();
		}
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locator_Value)).click();
		}
	}
	//method for validate data
	public static void validateTitle(WebDriver driver,String Expected_Title)
	{
		String Actual_Title=driver.getTitle();
		try {
			Assert.assertEquals(Expected_Title, Actual_Title,"Title is not Matching");
		}catch ( Throwable t) {
			System.out.println(t.getMessage());
		}
	}
	//method for closing browser
	public static void closeBrowser(WebDriver driver)
	{
		driver.quit();
	}
	//method for mouse click actions
	public static void mouseClick(WebDriver driver) throws Throwable
	{
		Actions ac=new Actions(driver);
		ac.moveToElement(driver.findElement(By.xpath("//a[.='Stock Items ']"))).perform();
		Thread.sleep(2000);
		ac.moveToElement(driver.findElement(By.xpath("(//a[.='Stock Categories'])[2]")));
		ac.pause(3000).click().perform();
	}
	//method for category table
	public static void categoryTable(WebDriver driver,String Exp_Data) throws Throwable
	{
		if(!driver.findElement(By.xpath(conpro.getProperty("search-box"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-box"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-box"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
		String Act_Data=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[4]/div/span/span")).getText();
		Reporter.log(Exp_Data+"    "+Act_Data,true);
		try {
			Assert.assertEquals(Exp_Data, Act_Data,"Category name is not found in the table");
	}
		catch(Throwable t)
		{
			System.out.println(t.getMessage());
		}
}
	//method for dropdown action
	public static void dropDownAction(WebDriver driver,String Locator_Type,String Locator_Value,String Test_Data)
	{
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
		int value=Integer.parseInt(Test_Data);
		WebElement element=driver.findElement(By.xpath(Locator_Value));
		Select select=new Select(element);
		select.selectByIndex(value);
		}
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			int value=Integer.parseInt(Test_Data);
			WebElement element=driver.findElement(By.id(Locator_Value));
			Select select=new Select(element);
			select.selectByIndex(value);
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			int value=Integer.parseInt(Test_Data);
			WebElement element=driver.findElement(By.name(Locator_Value));
			Select select=new Select(element);
			select.selectByIndex(value);
		}
		
		
	}
	//method for capturing stock number from notepad
	public static void captureStock(WebDriver driver,String Locator_Type,String Locator_Value) throws Throwable
	{
		String StockNumber="";
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			StockNumber=driver.findElement(By.id(Locator_Value)).getAttribute("value");
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			StockNumber=driver.findElement(By.name(Locator_Value)).getAttribute("value");
		}
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			StockNumber=driver.findElement(By.xpath(Locator_Value)).getAttribute("value");	
		}
		//method for writing stock number into notepad
		FileWriter fw=new FileWriter("./CaptureData/stocknumber.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(StockNumber);
		bw.flush();
		bw.close();
		}
	//method for stock table
	public static void stockTable(WebDriver driver) throws Throwable
	{
		FileReader fr=new FileReader("./CaptureData/stocknumber.txt");
		BufferedReader br=new BufferedReader(fr);
		String Exp_Data=br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("search-box"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-box"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-box"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
			String Act_Data=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
		Reporter.log(Exp_Data+"     "+Act_Data,true);
		Assert.assertEquals(Exp_Data, Act_Data,"Stock Number is not found in the table");
		
	}
	//method for capturing supplier number into notepad
	public static void captureSupplierNum(WebDriver driver,String Locator_Type,String Locator_Value) throws Throwable
	{
		String supplierNum="";
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			supplierNum=driver.findElement(By.id(Locator_Value)).getAttribute("value");
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			supplierNum=driver.findElement(By.name(Locator_Value)).getAttribute("value");
		}
		if(Locator_Type.equalsIgnoreCase("xpath"))
		{
			supplierNum=driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		}
		FileWriter fw=new FileWriter("./CaptureData/suppliernum.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(supplierNum);
		bw.flush();
		bw.close();
		}
	
	//method for reading supplierNumber from notepad
	public static void supplierTable(WebDriver driver) throws Throwable
	{
		FileReader fr=new FileReader("./CaptureData/suppliernum.txt");
		BufferedReader br=new BufferedReader(fr);
		String Exp_Data= br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("search-box"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-box"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-box"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
		String Act_Data=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
		Reporter.log(Exp_Data+"     "+Act_Data,true);
		try {
			Assert.assertEquals(Exp_Data, Act_Data, "Supplier Number not found in the table ");
		}catch(Throwable t)
		{
			System.out.println(t.getMessage());
		}
		
	}
	//method for capturing customer number into notepad
	public static void captureCustomerNumber(WebDriver driver,String Locator_Type,String Locator_Value) throws Throwable
	{
		String customerNum="";
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			customerNum=driver.findElement(By.id(Locator_Value)).getAttribute("value");
		}
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			customerNum=driver.findElement(By.name(Locator_Value)).getAttribute("value");
		}
		if(Locator_Type.contentEquals("xpath"))
		{
			customerNum=driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
		}
		FileWriter fw=new FileWriter("./CaptureData/customernumber.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(customerNum);
		bw.flush();
		bw.close();
	}
	//method for reading customer number from notepad
	public static void customerTable(WebDriver driver) throws Throwable
	{
		FileReader fr=new FileReader("./CaptureData/customernumber.txt");
		BufferedReader br=new BufferedReader(fr);
		String Exp_Data=br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("search-box"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-box"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-box"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
		String Act_Data=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
		Reporter.log(Exp_Data+"     "+Act_Data,true);
		try {
			Assert.assertEquals(Exp_Data, Act_Data, "customer Number is not found in Table");
			
		}catch(Throwable t)
		{
			System.out.println(t.getMessage());
		}
		
	}
	
	//method for date generate
	public static String generateDate()
	{
		Date date=new Date(0);
		DateFormat df=new SimpleDateFormat("YYYY_MM_DD");
		return df.format(date);
		

		
		
	}

	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
