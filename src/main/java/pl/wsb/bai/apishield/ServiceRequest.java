package pl.wsb.bai.apishield;

import jakarta.persistence.*;

@Entity
@Table(name = "service_requests")
public class ServiceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String ownerUsername;

    private String role = "USER";
    private String status = "PENDING";
    private String internalSecretNote = "INTERNAL_CONFIDENTIAL_TOKEN_XYZ987";

    public ServiceRequest() {}

    public ServiceRequest(String title, String description, String ownerUsername, String role, String status, String internalSecretNote) {
        this.title = title;
        this.description = description;
        this.ownerUsername = ownerUsername;
        this.role = role;
        this.status = status;
        this.internalSecretNote = internalSecretNote;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getOwnerUsername() { return ownerUsername; }
    public void setOwnerUsername(String ownerUsername) { this.ownerUsername = ownerUsername; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getInternalSecretNote() { return internalSecretNote; }
    public void setInternalSecretNote(String internalSecretNote) { this.internalSecretNote = internalSecretNote; }
}