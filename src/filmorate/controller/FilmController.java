package filmorate.controller;

import filmorate.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import filmorate.model.Film;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import filmorate.validator.FilmValidator;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final List<Film> films = new ArrayList<>();

    @PostMapping
    public ResponseEntity<Film> addFilm(@RequestBody Film film) {
        try {
            FilmValidator.validate(film);
            films.add(film);
            log.info("Фильм добавлен: " + film.getName());
            return new ResponseEntity<>(film, HttpStatus.CREATED);
        } catch (ValidationException e) {
            log.error("Ошибка валидации при добавлении фильма: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{index}")
    public ResponseEntity<Film> updateFilm(@PathVariable int index, @RequestBody Film film) {
        try {
            FilmValidator.validate(film);
            if (index < 0 || index >= films.size()) {
                log.warn("Фильм с индексом " + index + " не найден для обновления.");
                return ResponseEntity.notFound().build();
            }
            films.set(index, film);
            log.info("Фильм обновлён: " + film.getName());
            return ResponseEntity.ok(film);
        } catch (ValidationException e) {
            log.error("Ошибка валидации при обновлении фильма: " + e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }


    @GetMapping
    public ResponseEntity<List<Film>> getAllFilms() {
        return ResponseEntity.ok(films);
    }
}
