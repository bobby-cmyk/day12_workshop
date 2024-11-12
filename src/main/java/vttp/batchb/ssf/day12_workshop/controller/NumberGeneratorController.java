package vttp.batchb.ssf.day12_workshop.controller;


import java.io.File;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

@RequestMapping(path={"/generate"})
public class NumberGeneratorController {
    
    @GetMapping()
    public String getRandomNumberImages(
        @RequestParam MultiValueMap<String, String> queryParams, 
        Model model
    ) {

        if (!queryParams.containsKey("name") && !queryParams.containsKey("count")) {

            model.addAttribute("numberImages", getSpecificNumbers(queryParams.getFirst("list")));

            return "list_numbers";
        }

        else if (queryParams.containsKey("name") && queryParams.containsKey("count") && queryParams.containsKey("list")) {

            model.addAttribute("numberImages", getSpecificNumbers(queryParams.getFirst("list")));

            model.addAttribute( "name", queryParams.getFirst("name"));
            model.addAttribute("count", queryParams.getFirst("count"));

            return "lucky_numbers";

        }

        else {
            int numberCount = 0;

            try {
                numberCount = Integer.parseInt(queryParams.getFirst("count"));
            }

            catch (Exception e) {
                System.err.print("Error converting count from String to int");
            }
            
            String[] randomNumberImageNames = getRandomNumbers(numberCount);

            model.addAttribute( "name", queryParams.getFirst("name"));
            model.addAttribute("count", queryParams.getFirst("count"));
            model.addAttribute("numberImages", randomNumberImageNames);

            return "lucky_numbers";

            
        }
        
    }

    private String[] getRandomNumbers(int count) {

        // Get all the number images names from the folder
        String[] numberImageNames = getNumberImageNames();
        
        if (count <= 0) {
            count = 1;
        }
        else if (count > numberImageNames.length) {
            count = numberImageNames.length;
        }

        // Initialise the String array with the count provided
        String[] randomNumberImageNames = new String[count];

        // Initialise Random object
        Random rand = new Random();

        for (int i = 0; i < count; i++) {
            // Get a randomIndex
            int randomIndex = rand.nextInt(numberImageNames.length);

            // Add the random image into the new String array
            randomNumberImageNames[i] = numberImageNames[randomIndex];
        }

        return randomNumberImageNames;
    }

    private String[] getSpecificNumbers(String list) {

        // Split the list of numbers
        String[] numbers = list.split(",");
        
        // Lenght of list
        int numbersLength = numbers.length;

        // Instantiate number Image path array
        String[] numberImagePaths = new String[numbersLength];

        // put names into the array
        for (int i = 0; i < numbersLength; i++) {
            numberImagePaths[i] = "number" + numbers[i] + ".jpg";
        }
        
        return numberImagePaths;

    }

    private String[] getNumberImageNames() {
        // Get numbers folder
        File folder = new File("src/main/resources/static/numbers");

        File[] numberImages = folder.listFiles();

        // Get number of images
        int numberOfImages = numberImages.length;
        
        // Initialise String array
        String[] numberImageNames = new String[numberOfImages];

        for (int i = 0; i < numberOfImages; i++) {
            numberImageNames[i] = numberImages[i].getName();
            System.out.printf(">>> Image name: %s\n", numberImages[i].getName());
        }
        return numberImageNames;
    }
}
