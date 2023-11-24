package image.server.payload;

import lombok.Getter;

import java.util.List;

@Getter
public class Response {
    private String message;
    private String imageLocation;
    private List<String> imageLocations;
    private boolean success;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public void setImageLocations(List<String> imageLocations) {
        this.imageLocations = imageLocations;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
