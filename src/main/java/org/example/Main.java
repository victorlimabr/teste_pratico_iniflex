package org.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;

public class Main {
    private static final List<Funcionario> funcionarios = new ArrayList<>();
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");

    public static void main(String[] args) {
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloisa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        // remover funcionario "João"
        removerFuncionarioPorNome("João");

        //Imprimir a lista de funcionários com formatação.
        imprimirFuncionarios(funcionarios);

        //aumentar salario de todos os funcionarios em 10%
        aumentarSalarioDeTodos(10);

        //agrupa os funcionarios por função
        Map<String, List<Funcionario>> funcionariosPorFuncao = agruparFuncionariosPorFuncao();

        //Imprimir os funcionarios por função
        imprimirFuncionariosPorFuncao(funcionariosPorFuncao);

        //imprime os funcionarios que fazem aniversario no mes 10 e 12
        imprimirFuncionariosAniversariantesMes(10);
        imprimirFuncionariosAniversariantesMes(12);

        // imprime o funcionario mais velho
        imprimirFuncionarioMaisVelho();

        //imprime todos os funcionarios em ordem alfabetica
        imprimirFuncionariosEmOrdemAlfabetica();

        // imprime o total do salario dos funcionarios
        imprimirTotalSalarios();

        // imprime quantos salarios mínimos ganha cada funcionario
        imprimirSalariosMinimos();

    }

    private static void imprimirFuncionarios(List<Funcionario> funcionarios) {
        for (Funcionario funcionario : funcionarios) {
            System.out.println(funcionario);
        }
        System.out.println();
    }

    //metodo para remover um funcionario, aceita um nome como parametro.
    public static void removerFuncionarioPorNome(String nome) {
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals(nome));
    }

    // metodo para aumentar o salario de um funcionario especifico dando um nome e a porcentagem desejada como parametro.
    public static void aumentarSalarioDoFuncionario(Funcionario funcionario, double percentual) {
        BigDecimal aumento = funcionario.getSalario().multiply(BigDecimal.valueOf(percentual / 100));
        funcionario.setSalario(funcionario.getSalario().add(aumento));
    }

    // metodo para aumentar o salario de todos os funcionarios dando a porcentagem desejada como parametro.
    public static void aumentarSalarioDeTodos(double percentual) {
        for (Funcionario funcionario : funcionarios) {
            aumentarSalarioDoFuncionario(funcionario, percentual);
        }
    }

    // metodo que agrupa  os funcionarios por função
    public static Map<String, List<Funcionario>> agruparFuncionariosPorFuncao() {
        Map<String, List<Funcionario>> map = new HashMap<>();
        for (Funcionario funcionario : funcionarios) {
            map.computeIfAbsent(funcionario.getFuncao(), k -> new ArrayList<>()).add(funcionario);
        }
        return map;
    }

    // metodo que imprime os funcionarios por função
    public static void imprimirFuncionariosPorFuncao(Map<String, List<Funcionario>> funcionariosPorFuncao) {
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println("Função: " + entry.getKey());
            imprimirFuncionarios(entry.getValue());
        }
    }

    // metodo que imprime os aniversariantes do mes tendo o mes como parametro.
    public static void imprimirFuncionariosAniversariantesMes(int mes) {
        System.out.println("Funcionários que fazem aniversário no mês " + mes + ":");

        List<Funcionario> aniversariantes = funcionarios.stream()
                .filter(funcionario -> funcionario.getDataNascimento().getMonthValue() == mes)
                .toList();

        imprimirFuncionarios(aniversariantes);
    }

    // metodo que imprime o nome e a idade do funcionario mais velho da equipe
    public static void imprimirFuncionarioMaisVelho() {

        Optional<Funcionario> maisVelho = funcionarios.stream()
                .max(Comparator.comparingInt(Pessoa::calcularIdade));

        if (maisVelho.isPresent()) {
            System.out.println("Funcionário mais velho:");
            System.out.println("Nome: " + maisVelho.get().getNome());
            System.out.println("Idade: " + maisVelho.get().calcularIdade());
            System.out.println();
        }
    }

    // metodo que imprime os funcionarios em ordem alfabetica
    public static void imprimirFuncionariosEmOrdemAlfabetica() {
        funcionarios.sort(Comparator.comparing(Funcionario::getNome));

        System.out.println("Lista de funcionários em ordem alfabética:");
        imprimirFuncionarios(funcionarios);
    }

    // metodo que imprime o salario de todos das empresa somado.
    public static void imprimirTotalSalarios() {
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("Total dos salários de todos os funcionários: " + NumberFormat.getCurrencyInstance().format(totalSalarios));
        System.out.println();
    }

    public static void imprimirSalariosMinimos() {
        System.out.println("Quantos salários mínimos cada funcionário ganha:");
        for (Funcionario funcionario : funcionarios) {
            BigDecimal salariosMinimos = funcionario.getSalario().divide(SALARIO_MINIMO, 2, RoundingMode.HALF_UP);
            System.out.println(funcionario.getNome() + ": " + salariosMinimos + " salários mínimos");
        }
        System.out.println();
    }

}
