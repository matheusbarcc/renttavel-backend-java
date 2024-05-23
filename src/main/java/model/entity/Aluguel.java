package model.entity;

import java.time.LocalDateTime;

public class Aluguel {
    private int id;
    private LocalDateTime dataCheckin;
    private LocalDateTime dataCheckoutPrevista;
    private LocalDateTime dataCheckoutEfetiva;
    private double valorTotal;
    private double valorDiaria;
    private double valorLimpeza;
    private double valorMulta;
    private int qtdDias;
    private Imovel imovel;
    private Inquilino inquilino;

    public Aluguel() {
    }

    public Aluguel(int id, LocalDateTime dataCheckin, LocalDateTime dataCheckoutPrevista, LocalDateTime dataCheckoutEfetiva, double valorTotal, double valorDiaria, double valorLimpeza, double valorMulta, int qtdDias, Imovel imovel, Inquilino inquilino) {
        this.id = id;
        this.dataCheckin = dataCheckin;
        this.dataCheckoutPrevista = dataCheckoutPrevista;
        this.dataCheckoutEfetiva = dataCheckoutEfetiva;
        this.valorTotal = valorTotal;
        this.valorDiaria = valorDiaria;
        this.valorLimpeza = valorLimpeza;
        this.valorMulta = valorMulta;
        this.qtdDias = qtdDias;
        this.imovel = imovel;
        this.inquilino = inquilino;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDataCheckin() {
        return dataCheckin;
    }

    public void setDataCheckin(LocalDateTime dataCheckin) {
        this.dataCheckin = dataCheckin;
    }

    public LocalDateTime getDataCheckoutPrevista() {
        return dataCheckoutPrevista;
    }

    public void setDataCheckoutPrevista(LocalDateTime dataCheckoutPrevista) {
        this.dataCheckoutPrevista = dataCheckoutPrevista;
    }

    public LocalDateTime getDataCheckoutEfetiva() {
        return dataCheckoutEfetiva;
    }

    public void setDataCheckoutEfetiva(LocalDateTime dataCheckoutEfetiva) {
        this.dataCheckoutEfetiva = dataCheckoutEfetiva;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public double getValorLimpeza() {
        return valorLimpeza;
    }

    public void setValorLimpeza(double valorLimpeza) {
        this.valorLimpeza = valorLimpeza;
    }

    public double getValorMulta() {
        return valorMulta;
    }

    public void setValorMulta(double valorMulta) {
        this.valorMulta = valorMulta;
    }

    public int getQtdDias() {
        return qtdDias;
    }

    public void setQtdDias(int qtdDias) {
        this.qtdDias = qtdDias;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }
}
