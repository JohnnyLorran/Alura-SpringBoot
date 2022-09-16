package br.com.johnny.forum.controller.dto;

public class TokenDTO {

    private String token;
    private String tipo;

    public TokenDTO(String token, String bearer) {
        this.token = token;
        this.tipo = bearer;
    }

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }
}
