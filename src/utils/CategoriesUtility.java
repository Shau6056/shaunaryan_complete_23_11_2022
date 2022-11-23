package utils;
import java.util.ArrayList;
import java.util.List;

public class CategoriesUtility {
    private static ArrayList<String> categoriesUtility = new ArrayList<>(
            List.of("WORK",
                    "HOME",
                    "HOLIDAY",
                    "HOBBY",
                    "COLLEGE"));


    public ArrayList<String> getCategories()
    {
        return categoriesUtility;
    }

    public static boolean isValidCategory(String categoryIn)
    {
        String lowCase = categoryIn.toUpperCase();

        return categoriesUtility.contains(lowCase);
    }


}

