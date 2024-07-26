package filmorate.validator;

import filmorate.exception.ValidationException;
import filmorate.model.Film;

import java.time.LocalDate;

public class FilmValidator {
    private static final LocalDate MIN_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    public static void validate(Film film) {
        if (film.getName().isEmpty() || film.getName() == null) {
            throw new ValidationException("Название фильма не может быть пустым.");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания - 200 символов.");
        }
        if (film.getReleaseDate().isBefore(MIN_RELEASE_DATE.atStartOfDay())) {
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года.");
        }
        if (film.getDuration().isNegative()) {
            throw new ValidationException("Продолжительность фильма должна быть больше 0.");
        }
    }
}
