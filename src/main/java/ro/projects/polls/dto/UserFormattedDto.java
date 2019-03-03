package ro.projects.polls.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ro.projects.polls.entity.Permission;

import java.time.LocalDate;
import java.util.List;

public class UserFormattedDto {
    private Integer id;
    private String username;
    private String email;
    private String fullName;
    private String observations;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate startDate;
    private List<Permission> permissions;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public UserFormattedDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserFormattedDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserFormattedDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserFormattedDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getObservations() {
        return observations;
    }

    public UserFormattedDto setObservations(String observations) {
        this.observations = observations;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public UserFormattedDto setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public UserFormattedDto setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public UserFormattedDto setStatus(Integer status) {
        this.status = status;
        return this;
    }
}
