package model.entity;

import java.time.LocalDateTime;

public class AluguelSeletor extends BaseSeletor{

    private LocalDateTime dataCheckinInicio;
    private LocalDateTime dataCheckinFinal;
    private LocalDateTime dataCheckoutPrevistoInicio;
    private LocalDateTime dataCheckoutPrevistoFinal;
    private LocalDateTime dataCheckoutEfetivoInicio;
    private LocalDateTime dataCheckoutEfetivoFinal;
    private double valorTotalMin;
    private double valorTotalMax;
    private double valorDiariaMin;
    private double valorDiariaMax;
    private double valorLimpezaMin;
    private double valorLimpezaMax;
    private double valorMultaMin;
    private double valorMultaMax;
    private int qtdDiasMin;
    private int qtdDiasMax;
    private int idImovel;
    private int idInquilino;

    public AluguelSeletor() {
    }

    public AluguelSeletor(LocalDateTime dataCheckinInicio, LocalDateTime dataCheckinFinal, LocalDateTime dataCheckoutPrevistoInicio, LocalDateTime dataCheckoutPrevistoFinal, LocalDateTime dataCheckoutEfetivoInicio, LocalDateTime dataCheckoutEfetivoFinal, double valorTotalMin, double valorTotalMax, double valorDiariaMin, double valorDiariaMax, double valorLimpezaMin, double valorLimpezaMax, double valorMultaMin, double valorMultaMax, int qtdDiasMin, int qtdDiasMax, int idImovel, int idInquilino) {
        this.dataCheckinInicio = dataCheckinInicio;
        this.dataCheckinFinal = dataCheckinFinal;
        this.dataCheckoutPrevistoInicio = dataCheckoutPrevistoInicio;
        this.dataCheckoutPrevistoFinal = dataCheckoutPrevistoFinal;
        this.dataCheckoutEfetivoInicio = dataCheckoutEfetivoInicio;
        this.dataCheckoutEfetivoFinal = dataCheckoutEfetivoFinal;
        this.valorTotalMin = valorTotalMin;
        this.valorTotalMax = valorTotalMax;
        this.valorDiariaMin = valorDiariaMin;
        this.valorDiariaMax = valorDiariaMax;
        this.valorLimpezaMin = valorLimpezaMin;
        this.valorLimpezaMax = valorLimpezaMax;
        this.valorMultaMin = valorMultaMin;
        this.valorMultaMax = valorMultaMax;
        this.qtdDiasMin = qtdDiasMin;
        this.qtdDiasMax = qtdDiasMax;
        this.idImovel = idImovel;
        this.idInquilino = idInquilino;
    }

    public LocalDateTime getDataCheckinInicio() {
        return dataCheckinInicio;
    }

    public void setDataCheckinInicio(LocalDateTime dataCheckinInicio) {
        this.dataCheckinInicio = dataCheckinInicio;
    }

    public LocalDateTime getDataCheckinFinal() {
        return dataCheckinFinal;
    }

    public void setDataCheckinFinal(LocalDateTime dataCheckinFinal) {
        this.dataCheckinFinal = dataCheckinFinal;
    }

    public LocalDateTime getDataCheckoutPrevistoInicio() {
        return dataCheckoutPrevistoInicio;
    }

    public void setDataCheckoutPrevistoInicio(LocalDateTime dataCheckoutPrevistoInicio) {
        this.dataCheckoutPrevistoInicio = dataCheckoutPrevistoInicio;
    }

    public LocalDateTime getDataCheckoutPrevistoFinal() {
        return dataCheckoutPrevistoFinal;
    }

    public void setDataCheckoutPrevistoFinal(LocalDateTime dataCheckoutPrevistoFinal) {
        this.dataCheckoutPrevistoFinal = dataCheckoutPrevistoFinal;
    }

    public LocalDateTime getDataCheckoutEfetivoInicio() {
        return dataCheckoutEfetivoInicio;
    }

    public void setDataCheckoutEfetivoInicio(LocalDateTime dataCheckoutEfetivoInicio) {
        this.dataCheckoutEfetivoInicio = dataCheckoutEfetivoInicio;
    }

    public LocalDateTime getDataCheckoutEfetivoFinal() {
        return dataCheckoutEfetivoFinal;
    }

    public void setDataCheckoutEfetivoFinal(LocalDateTime dataCheckoutEfetivoFinal) {
        this.dataCheckoutEfetivoFinal = dataCheckoutEfetivoFinal;
    }

    public double getValorTotalMin() {
        return valorTotalMin;
    }

    public void setValorTotalMin(double valorTotalMin) {
        this.valorTotalMin = valorTotalMin;
    }

    public double getValorTotalMax() {
        return valorTotalMax;
    }

    public void setValorTotalMax(double valorTotalMax) {
        this.valorTotalMax = valorTotalMax;
    }

    public double getValorDiariaMin() {
        return valorDiariaMin;
    }

    public void setValorDiariaMin(double valorDiariaMin) {
        this.valorDiariaMin = valorDiariaMin;
    }

    public double getValorDiariaMax() {
        return valorDiariaMax;
    }

    public void setValorDiariaMax(double valorDiariaMax) {
        this.valorDiariaMax = valorDiariaMax;
    }

    public double getValorLimpezaMin() {
        return valorLimpezaMin;
    }

    public void setValorLimpezaMin(double valorLimpezaMin) {
        this.valorLimpezaMin = valorLimpezaMin;
    }

    public double getValorLimpezaMax() {
        return valorLimpezaMax;
    }

    public void setValorLimpezaMax(double valorLimpezaMax) {
        this.valorLimpezaMax = valorLimpezaMax;
    }

    public double getValorMultaMin() {
        return valorMultaMin;
    }

    public void setValorMultaMin(double valorMultaMin) {
        this.valorMultaMin = valorMultaMin;
    }

    public double getValorMultaMax() {
        return valorMultaMax;
    }

    public void setValorMultaMax(double valorMultaMax) {
        this.valorMultaMax = valorMultaMax;
    }

    public int getQtdDiasMin() {
        return qtdDiasMin;
    }

    public void setQtdDiasMin(int qtdDiasMin) {
        this.qtdDiasMin = qtdDiasMin;
    }

    public int getQtdDiasMax() {
        return qtdDiasMax;
    }

    public void setQtdDiasMax(int qtdDiasMax) {
        this.qtdDiasMax = qtdDiasMax;
    }

    public int getIdImovel() {
        return idImovel;
    }

    public void setIdImovel(int idImovel) {
        this.idImovel = idImovel;
    }

    public int getIdInquilino() {
        return idInquilino;
    }

    public void setIdInquilino(int idInquilino) {
        this.idInquilino = idInquilino;
    }

    public boolean temFiltro(){
        return (this.getDataCheckinInicio() != null)
                || (this.getDataCheckinFinal() != null)
                || (this.getDataCheckoutPrevistoInicio() != null)
                || (this.getDataCheckoutPrevistoFinal() != null)
                || (this.getDataCheckoutEfetivoInicio() != null)
                || (this.getDataCheckoutEfetivoFinal() != null)
                || (this.getValorTotalMin() > 0)
                || (this.getValorTotalMax() > 0)
                || (this.getValorDiariaMin() > 0)
                || (this.getValorDiariaMax() > 0)
                || (this.getValorLimpezaMin() > 0)
                || (this.getValorLimpezaMax() > 0)
                || (this.getValorMultaMin() > 0)
                || (this.getValorMultaMax() > 0)
                || (this.getQtdDiasMin() > 0)
                || (this.getQtdDiasMax() > 0)
                || (this.getIdImovel() > 0)
                || (this.getIdInquilino() > 0);
    }
}
