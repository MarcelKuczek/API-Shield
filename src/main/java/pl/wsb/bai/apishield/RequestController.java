package pl.wsb.bai.apishield;

import jakarta.validation.Valid;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/requests")
public class RequestController {

    private final ServiceRequestRepository repository;

    public RequestController(ServiceRequestRepository repository) {
        this.repository = repository;
    }

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            repository.save(new ServiceRequest(
                    "Tajny wniosek zarządu",
                    "Klucze dostępu do produkcyjnej bazy danych: prod-db-key-999",
                    "admin",
                    "ADMIN",
                    "APPROVED",
                    "SECRET_ADMIN_HASH_777888999"
            ));
            repository.save(new ServiceRequest(
                    "Prośba o urlop",
                    "Wniosek o 2 dni urlopu",
                    "user",
                    "USER",
                    "PENDING",
                    "INTERNAL_NOTE_USER_1"
            ));
        };
    }

    private boolean isAdmin(Authentication auth) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    private ServiceRequest getAuthorizedEntity(Long id, Authentication auth) {
        ServiceRequest entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Nie znaleziono wniosku o podanym ID"));

        if (!entity.getOwnerUsername().equals(auth.getName()) && !isAdmin(auth)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Brak uprawnień do cudzego zasobu (Ochrona BOLA)");
        }
        return entity;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestResponseDto createRequest(@Valid @RequestBody RequestCreateDto dto, Authentication auth) {
        ServiceRequest entity = new ServiceRequest(
                dto.getTitle(),
                dto.getDescription(),
                auth.getName(),
                "USER",
                "PENDING",
                "INTERNAL_CONFIDENTIAL_TOKEN"
        );
        return new RequestResponseDto(repository.save(entity));
    }

    @GetMapping("/{id}")
    public RequestResponseDto getRequestById(@PathVariable Long id, Authentication auth) {
        ServiceRequest entity = getAuthorizedEntity(id, auth);
        return new RequestResponseDto(entity);
    }

    @GetMapping
    public List<RequestResponseDto> getAllRequests(Authentication auth) {
        return repository.findAll().stream()
                .filter(req -> isAdmin(auth) || req.getOwnerUsername().equals(auth.getName()))
                .map(RequestResponseDto::new)
                .toList();
    }

    @PutMapping("/{id}")
    public RequestResponseDto updateRequest(@PathVariable Long id,
                                            @Valid @RequestBody RequestCreateDto dto,
                                            Authentication auth) {
        ServiceRequest entity = getAuthorizedEntity(id, auth);
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        return new RequestResponseDto(repository.save(entity));
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteRequest(@PathVariable Long id, Authentication auth) {
        ServiceRequest entity = getAuthorizedEntity(id, auth);
        repository.delete(entity);
        return java.util.Map.of("message", "Pomyślnie usunięto wniosek o ID: " + id);
    }
}