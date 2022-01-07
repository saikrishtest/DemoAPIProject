import files.payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SumValidation {

    @Test
    public void sumOfCoursePrice(){
        int sum=0;
        JsonPath js = new JsonPath(payload.CoursePrice());
        int count = js.getInt("courses.size()");

        for(int i=0;i<count;i++){
            int price = js.getInt("courses["+i+"].price");
            int copies = js.getInt("courses["+i+"].copies");
            int courseAmount = price * copies;
            System.out.println(courseAmount);
            sum = sum + courseAmount;
        }
        System.out.println("Sum of All Course Prices is:"+sum);
        int purchaseAmount = js.get("dashboard.purchaseAmount");
        Assert.assertEquals(sum,purchaseAmount);
    }
}
