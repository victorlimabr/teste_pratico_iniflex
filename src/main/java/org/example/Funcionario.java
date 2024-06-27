package org.example;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

public class Funcionario extends Pessoa {
    private BigDecimal salario;
    private String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getFormattedSalario() {
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.of("pt", "BR"));
        return formatter.format(salario);
    }

    @Override
    public String toString() {
        return super.toString() + ", Salário: " + getFormattedSalario() + ", Função: " + funcao;
    }
}
