package business.services;

public class BmiUtil
{
    public static double calcBMI(double weight, double height)
    {
        // convert to meters.
        height = height/100;
        return weight/(height*height);    // formula for BMI
    }
    
    public static String getCategory(double bmi)
    {
        String category;
        if (bmi < 18.5)
        {
            category = "underweight";
        }
        else if (bmi < 25)
        {
            category = "normal";
        }
        else if (bmi < 30)
        {
            category = "overweight";
        }
        else
        {
            category = "obese";
        }
        return category;
    }
}
