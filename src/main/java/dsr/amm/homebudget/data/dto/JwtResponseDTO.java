package dsr.amm.homebudget.data.dto;

/**
 * Created by knekrasov on 10/24/2018.
 */
public class JwtResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtResponseDTO() {
    }

    public JwtResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
