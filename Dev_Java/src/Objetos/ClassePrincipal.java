package Objetos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassePrincipal extends Pessoa {
    private List<Funcionario> funcionarios = new ArrayList<>();
    private Map<String, List<Funcionario>> listaFuncionarios = new HashMap<>();

    public void adicionar(List<Funcionario> variosFuncionarios) {
        funcionarios.addAll(variosFuncionarios);
    }

    public void remover(Funcionario funcionario) {
        if (funcionario.getNome().equals("João")) {
            funcionarios.remove(funcionario);
        }
    }

    public void exibirFuncionarios() {
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Funcionario funcionario : funcionarios) {
            System.out.println(funcionario.getNome());
            System.out.println(funcionario.getDataNascimento().format(formatoData));
            System.out.println(funcionario.getSalario().toString().replace('.', ','));
            System.out.println(funcionario.getFuncao());
        }
    }

    public void atualizacaoSalario() {
        for (Funcionario funcionario : funcionarios) {
            BigDecimal novoSalario = funcionario.getSalario().multiply(new BigDecimal("1.10"));
            funcionario.setSalario(novoSalario);
        }
    }

    public void agruparFuncionariosPorFuncao() {
        for (Funcionario funcionario : funcionarios) {
            listaFuncionarios.computeIfAbsent(funcionario.getFuncao(), k -> new ArrayList<>()).add(funcionario);
        }
    }

    public void exibirFuncionariosPorFuncao(String funcao) {
        if (listaFuncionarios.containsKey(funcao)) {
            for (Funcionario funcionario : listaFuncionarios.get(funcao)) {
                System.out.println(funcionario.getNome());
            }
        }
    }

    public void exibirFuncionariosAniversario() {
        for (Funcionario funcionario : funcionarios) {
            int mes = funcionario.getDataNascimento().getMonthValue();
            if (mes == 10 || mes == 12) {
                System.out.println(funcionario.getNome());
            }
        }
    }

    public void exibirFuncionarioMaiorIdade() {
        LocalDate hoje = LocalDate.now();
        Funcionario funcionarioMaisVelho = funcionarios.stream()
            .max(Comparator.comparingInt(f -> hoje.getYear() - f.getDataNascimento().getYear()))
            .orElse(null);
        if (funcionarioMaisVelho != null) {
            System.out.println(funcionarioMaisVelho.getNome());
            System.out.println(hoje.getYear() - funcionarioMaisVelho.getDataNascimento().getYear());
        }
    }

    public void exibirFuncionariosOrdemAlfabetica() {
        funcionarios.stream()
            .sorted(Comparator.comparing(Funcionario::getNome))
            .forEach(f -> System.out.println(f.getNome()));
    }

    public void exibirTotalSalario() {
        BigDecimal totalSalario = funcionarios.stream()
            .map(Funcionario::getSalario)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(totalSalario);
    }

    public void exibirSalariosMinimos() {
        int salariosMinimos = 0;
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        for (Funcionario funcionario : funcionarios) {
            BigDecimal salarioTotal = funcionario.getSalario();
            while (salarioTotal.compareTo(salarioMinimo) > 0) {
                salarioTotal = salarioTotal.subtract(salarioMinimo);
                salariosMinimos++;
            }
            System.out.println(funcionario.getNome() + " ganha " + salariosMinimos + " salários mínimos.");
        }
    }
}