package ro.projects.polls.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "permission")
public class Permission {
    public static final Integer PERMISSION_ID_ADMIN = 13;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "*Please provide a code")
    private String code;

    @NotEmpty(message = "*Please provide a group")
    private String group;

    public int getId() {
        return id;
    }

    public Permission setId(int id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Permission setCode(String code) {
        this.code = code;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public Permission setGroup(String group) {
        this.group = group;
        return this;
    }
}

