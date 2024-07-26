import filmorate.controller.FilmController;
import filmorate.model.Film;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class FilmControllerTest {
    private FilmController filmController;

    @BeforeEach
    void setUp() {
        filmController = new FilmController();
    }

    @Test
    void addFilm_ValidFilm_ReturnsCreatedResponse() {
        Film film = new Film();
        film.setName("Valid Film");
        film.setDuration(Duration.ofHours(1));
        film.setDescription("film");
        film.setReleaseDate(LocalDateTime.now());


        ResponseEntity<Film> response = filmController.addFilm(film);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Valid Film", response.getBody().getName());
    }

    @Test
    void addFilm_InvalidFilm_ReturnsBadRequest() {
        Film film = new Film();
        film.setName("");

        ResponseEntity<Film> response = filmController.addFilm(film);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void updateFilm_ValidFilm_ReturnsOkResponse() {
        Film film = new Film();
        film.setName("First Film");
        filmController.addFilm(film);

        Film updatedFilm = new Film();
        updatedFilm.setName("Updated Film");

        ResponseEntity<Film> response = filmController.updateFilm(0, updatedFilm);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Film", response.getBody().getName());
    }

    @Test
    void updateFilm_NonExistentFilm_ReturnsNotFound() {
        Film updatedFilm = new Film();
        updatedFilm.setName("Updated Film");

        ResponseEntity<Film> response = filmController.updateFilm(0, updatedFilm);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getAllFilms_ReturnsListOfFilms() {
        Film film1 = new Film();
        film1.setName("Film 1");
        filmController.addFilm(film1);

        Film film2 = new Film();
        film2.setName("Film 2");
        filmController.addFilm(film2);

        ResponseEntity<List<Film>> response = filmController.getAllFilms();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }
}
