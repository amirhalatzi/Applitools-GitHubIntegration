package MyPackage;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;

public class NewTest {
	public Eyes eyes = new Eyes();
	public String AppName = "GithubIntegration";
	RemoteWebDriver driver;
	public String TestName = "Test_Github_Integration";

	@BeforeMethod
	public void setUpBeforMethod() throws MalformedURLException {
		System.out.println("--------------test-----------------");
		System.err.println(System.getenv("APPLITOOLS_API_KEY"));
		eyes.setApiKey(System.getenv("APPLITOOLS_API_KEY"));
		final String sauceUser = System.getenv("SAUCE_USER");
		final String sauceKey = System.getenv("SAUCE_KEY");
		String url = "http://" + sauceUser + ":" + sauceKey + "@ondemand.saucelabs.com:80/wd/hub";

		DesiredCapabilities caps = DesiredCapabilities.chrome();
		caps.setCapability("platform", "Windows 10");
		caps.setCapability("version", "65.0");

		driver = new RemoteWebDriver(new URL(url), caps);

		String batchId = System.getenv("APPLITOOLS_BATCH_ID");
		System.out.println(System.getenv("APPLITOOLS_BATCH_ID"));
		String batchName = "TestNG";
		BatchInfo batchInfo = new BatchInfo(batchName);

		batchInfo.setId(batchId);
		eyes.setBatch(batchInfo);
		System.out.println("batch name");
		System.out.println(eyes.getBatch().getName());

		// eyes.setBranchName("master");

	}

	@Test
	public void Test() {
		try {
			System.out.println("in test method");

			eyes.setSaveNewTests(true);

			eyes.open(driver, AppName, TestName, new RectangleSize(800, 600));

			driver.get("https://applitools.com/helloworld/?diff1");

			// eyes.checkWindow();
			eyes.check("test", Target.window());
			System.out.println("in test method 2");
			eyes.close(false);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			driver.quit();
			eyes.abort();
		}
	}
}
