package com.ftn.dan.airhermes.controller;

import com.ftn.dan.airhermes.model.entity.Airport;
import com.ftn.dan.airhermes.model.entity.Location;
import com.ftn.dan.airhermes.service.AirportService;
import com.ftn.dan.airhermes.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping(value = "/locations")
public class LocationController {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private LocationService locationService;

    @Autowired
    private ServletContext servletContext;
    private String baseURL;

    @PostConstruct
    public void init() {
        baseURL = servletContext.getContextPath() + "/";
    }

    @GetMapping
    public ModelAndView getAll(
            HttpSession httpSession, HttpServletResponse response) throws IOException {

        List<Location> locations = locationService.findAll();

        ModelAndView mov = new ModelAndView("locationsPage");
        mov.addObject("locations", locations);
        return mov;
    }

    @PostMapping
    public void defineLocation(
            @RequestParam(name = "city") String city,
            @RequestParam(name = "state") String state,
            @RequestParam(name = "continent") String continent,
            @RequestParam(required = false, name = "imageFile") MultipartFile imageFile,
            HttpSession httpSession, HttpServletResponse response) throws IOException {

        Path filePath = null;
        if (imageFile != null && !imageFile.isEmpty()) {
//            return "Please select an image to upload.";
            try {
//                Create the upload directory if it doesn't exist
                Path uploadPath = Paths.get(UPLOAD_DIR);
//                System.out.println("uploading to: " + uploadPath.toAbsolutePath());
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

//                Save the uploaded file to the server
                filePath = uploadPath.resolve(imageFile.getOriginalFilename());
                Files.copy(imageFile.getInputStream(), filePath);

//                return "Image uploaded successfully: " + imageFile.getOriginalFilename();
            } catch (IOException e) {
                e.printStackTrace();
//                return "Failed to upload image: " + e.getMessage();
            }
        }

        locationService.save(new Location(null, city, state, continent, filePath!=null ? filePath.toString() : null));
        response.sendRedirect(baseURL + "locations");
    }


}
