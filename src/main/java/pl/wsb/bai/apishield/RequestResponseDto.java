package pl.wsb.bai.apishield;

public class RequestResponseDto {

    private Long id;
    private String title;
    private String description;
    private String ownerUsername;
    private String role;
    private String status;

    public RequestResponseDto(ServiceRequest entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.ownerUsername = entity.getOwnerUsername();
        this.role = entity.getRole();
        this.status = entity.getStatus();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getOwnerUsername() { return ownerUsername; }
    public String getRole() { return role; }
    public String getStatus() { return status; }
}