package com.github.rafaritter44.cloud_native.tomcat.app.operacoes;

import java.util.Optional;

public class Multiplicacao implements Operacao {

    private double primeiroOperando;
    private double segundoOperando;

    public Multiplicacao(double primeiroOperando, double segundoOperando) {
        this.primeiroOperando = primeiroOperando;
        this.segundoOperando = segundoOperando;
    }

    public Optional<Double> calcular() {
        return Optional.ofNullable(primeiroOperando * segundoOperando);
    }

}
