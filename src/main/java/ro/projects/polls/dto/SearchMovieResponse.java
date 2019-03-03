package ro.projects.polls.dto;

public class SearchMovieResponse extends BaseResponse {
    private String html;

    public String getHtml() {
        return html;
    }

    public SearchMovieResponse setHtml(String html) {
        this.html = html;
        return this;
    }
}
