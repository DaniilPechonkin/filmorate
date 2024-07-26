package filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of = "id")
public class Film {
    int id;
    String name;
    String description;
    LocalDateTime releaseDate;
    Duration duration;
}
