package ro.projects.polls.entity;

import org.springframework.format.annotation.DateTimeFormat;
import ro.projects.polls.dto.UserFormattedDto;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    public static final Integer STATUS_ENABLED = 1;
    public static final Integer STATUS_DISABLED = 0;

    @Transient
    private String passwordConfirm;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String observations;
    private Integer status = 1;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id", referencedColumnName = "id")
    private User creator;

    @DateTimeFormat(pattern = "YYYY-MM-dd")
    private LocalDate startDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_permission", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private List<Permission> permissions;

    public UserFormattedDto format() {
        return new UserFormattedDto()
                .setId(getId())
                .setUsername(getUsername())
                .setEmail(getEmail())
                .setFullName(getFullName())
                .setObservations(getObservations())
                .setStartDate(getStartDate())
                .setPermissions(getPermissions())
                .setStatus(getStatus());
    }

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public User setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public User setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getObservations() {
        return observations;
    }

    public User setObservations(String observations) {
        this.observations = observations;
        return this;
    }

    public User getCreator() {
        return creator;
    }

    public User setCreator(User creator) {
        this.creator = creator;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public User setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public User setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public User setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
        return this;
    }
}

