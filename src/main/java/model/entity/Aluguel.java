package model.entity;

import java.time.LocalDateTime;

public class Aluguel {
    private int id;
    private LocalDateTime dataCheckin;
    private LocalDateTime dataCheckoutPrevisto;
    private LocalDateTime dataCheckoutEfetivo;
    private double valorTotal;
    private double valorDiaria;
    private double valorLimpeza;
    private double valorMulta;
    private int qtdDias;
    private boolean isOcupado;
    private Imovel imovel;
    private Inquilino inquilino;

    public Aluguel() {
    }

    public Aluguel(int id, LocalDateTime dataCheckin, LocalDateTime dataCheckoutPrevisto, LocalDateTime dataCheckoutEfetivo, double valorTotal, double valorDiaria, double valorLimpeza, double valorMulta, int qtdDias, boolean isOcupado, Imovel imovel, Inquilino inquilino) {
        this.id = id;
        this.dataCheckin = dataCheckin;
        this.dataCheckoutPrevisto = dataCheckoutPrevisto;
        this.dataCheckoutEfetivo = dataCheckoutEfetivo;
        this.valorTotal = valorTotal;
        this.valorDiaria = valorDiaria;
        this.valorLimpeza = valorLimpeza;
        this.valorMulta = valorMulta;
        this.qtdDias = qtdDias;
        this.isOcupado = isOcupado;
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

    public LocalDateTime getDataCheckoutPrevisto() {
        return dataCheckoutPrevisto;
    }

    public void setDataCheckoutPrevisto(LocalDateTime dataCheckoutPrevisto) {
        this.dataCheckoutPrevisto = dataCheckoutPrevisto;
    }

    public LocalDateTime getDataCheckoutEfetivo() {
        return dataCheckoutEfetivo;
    }

    public void setDataCheckoutEfetivo(LocalDateTime dataCheckoutEfetivo) {
        this.dataCheckoutEfetivo = dataCheckoutEfetivo;
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

    public boolean isOcupado() {
        return isOcupado;
    }

    public void setOcupado(boolean ocupado) {
        isOcupado = ocupado;
    }
}
