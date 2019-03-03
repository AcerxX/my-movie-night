package ro.projects.polls.dto;

import java.util.Date;

public class BaseResponse {
    private Integer status = 200;
    private String message = "Success";
    private Date timestamp = new Date();

    public Integer getStatus() {
        return status;
    }

    public BaseResponse setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public BaseResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public BaseResponse setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
