import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
    public static void main(String[] args){

        /* Print the number of courses returned by API */
        JsonPath js=new JsonPath(payload.CoursePrice());
        int countOfCourses = js.getInt("courses.size()");
        System.out.println(countOfCourses);

        /*Print the Purchase Amount*/
        int totalAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println(totalAmount);

        /*Print the title of the First Course*/
        String titleFirstCourse = js.get("courses[0].title");
        System.out.println(titleFirstCourse);

        /*Print All course titles and their respective prices*/
        for(int i=0;i<countOfCourses;i++){
            System.out.println(js.get("courses["+i+"].title").toString());

            System.out.println(js.get("courses["+i+"].price").toString());
        }

        System.out.println("Print no of copies sold by RPA Course");
        for(int i=0;i<countOfCourses;i++){
            String courseTitles = js.get("courses["+i+"].title");
            if(courseTitles.equalsIgnoreCase("RPA"))
            {
                int copies = js.get("courses["+i+"].copies");
                System.out.println(copies);
                break;
            }
        }

    }
}