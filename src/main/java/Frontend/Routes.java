package Frontend;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import Backend.component.Nota;
import Backend.controller.HomeController;
import Backend.controller.NotasCRUDController;
import Backend.repository.NotasRepository;
import Backend.repository.RepositoryAdapter;
import Backend.services.NotaServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import spark.ModelAndView;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;


@SpringBootApplication
public class Routes {

    public static void main(String[] args) {
        SpringApplication.run(Routes.class, args);
}}
