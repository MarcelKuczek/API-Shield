package pl.wsb.bai.apishield;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RequestCreateDto {

    @NotBlank(message = "Tytuł wniosku nie może być pusty")
    @Size(min = 3, max = 100, message = "Tytuł musi mieć od 3 do 100 znaków")
    private String title;

    @NotBlank(message = "Opis wniosku nie może być pusty")
    @Size(min = 3, max = 500, message = "Opis musi mieć od 3 do 500 znaków")
    private String description;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}