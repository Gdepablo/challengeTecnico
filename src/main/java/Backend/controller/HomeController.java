package Backend.controller;

import Backend.repository.NotesRepository;
import spark.ModelAndView;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class HomeController {
    NotesRepository repo;

    public ModelAndView getHome() {
        Map<String, Object> modelo = new HashMap<>();
        modelo.put("anio", LocalDate.now().getYear());
        modelo.put("notas",repo);

        return new ModelAndView(modelo, "index.hbs");
    }
}