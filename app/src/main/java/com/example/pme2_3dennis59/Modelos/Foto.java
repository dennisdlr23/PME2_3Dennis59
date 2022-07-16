package com.example.pme2_3dennis59.Modelos;

public class Foto {
    private Integer id;
    private String description;
    private byte[] foto;

    public Foto(){
    }

    public Foto(Integer id, String description, byte[] foto) {
        this.id = id;
        this.description = description;
        this.foto = foto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
