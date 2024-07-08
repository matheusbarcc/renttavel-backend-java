package services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import exception.RenttavelException;
import model.dto.BarraDTO;
import model.dto.DiferencaDTO;
import model.entity.Aluguel;
import model.entity.Imovel;
import model.repository.AluguelRepository;
import model.repository.ImovelRepository;

public class DashboardService {

	private AluguelRepository repo = new AluguelRepository();
	private ImovelRepository imvRepo = new ImovelRepository();
		
	public double calcularRendimentoAnual(int idAnfitriao) throws RenttavelException {
        List<Aluguel> alugueis = repo.consultarPorAnfitriao(idAnfitriao);
        double rendimentoAnual = 0;

        for(Aluguel a : alugueis) {
            if(a.getDataCheckin().getYear() == LocalDateTime.now().getYear()) {
                rendimentoAnual += a.getValorTotal();
            }
        }

        return rendimentoAnual;
    }
	
	public double calcularRendimentoMensal(int idAnfitriao) throws RenttavelException {
        List<Aluguel> alugueis = repo.consultarPorAnfitriao(idAnfitriao);
        double rendimentoMensal = 0;

        for(Aluguel a : alugueis) {
            if(a.getDataCheckin().getYear() == LocalDateTime.now().getYear() 
            		&& a.getDataCheckin().getMonth() == LocalDateTime.now().getMonth()) {
            	rendimentoMensal += a.getValorTotal();
            }
        }

        return rendimentoMensal;
    }
	
	public double calcularOcupacao(int idAnfitriao) throws RenttavelException{
		List<Imovel> imoveis = imvRepo.consultarPorAnfitriao(idAnfitriao);
		double imoveisOcupados = 0;
		
		for(Imovel i : imoveis) {
			if(i.getIsOcupado()) {
				imoveisOcupados++;
			}
		}
		
		double ocupacao = (imoveisOcupados/imoveis.size()) * 100;

		return ocupacao;
	}
	
	public List<Aluguel> getProximosAlugueis(int idAnfitriao) throws RenttavelException{
		List<Aluguel> alugueis = repo.consultarPorAnfitriao(idAnfitriao);
		List<Aluguel> proxAlugueis = new ArrayList<>();
		
		for(Aluguel a : alugueis) {
			if(a.getDataCheckin().isAfter(LocalDateTime.now())) {
				if(proxAlugueis.size() == 4) {
					break;
				}
				proxAlugueis.add(a);
			}
		}
		
		return proxAlugueis;
	}
	
	public List<String> getLabelsGraficoBarra(int idAnfitriao) throws RenttavelException {
	    List<Aluguel> alugueis = repo.consultarPorAnfitriao(idAnfitriao);
	    int mesAtual = LocalDateTime.now().getMonthValue();

	    List<String> labels = new ArrayList<>();
	    String[] mesesNomes = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
	    
	    for (int i = 0; i < mesAtual; i++) {
	        labels.add(mesesNomes[i]);
	    }

	    return labels;
	}
	
	public List<BarraDTO> getDatasetGraficoBarra(int idAnfitriao) throws RenttavelException {
	    List<Aluguel> alugueis = repo.consultarPorAnfitriao(idAnfitriao);
	    Map<String, Double[]> imovelAlugueis = new HashMap<>();

	    for (Aluguel a : alugueis) {
	        if (a.getDataCheckin().getYear() == LocalDateTime.now().getYear()) {
	            String nomeImovel = a.getImovel().getNome();
	            int mes = a.getDataCheckin().getMonthValue() - 1; // Meses vão de 0 a 11 no array
	            
	            // Inicializa o array com 0.0 se ainda não existir
	            imovelAlugueis.putIfAbsent(nomeImovel, new Double[12]);
	            Double[] alugueisPorMes = imovelAlugueis.get(nomeImovel);
	            if (alugueisPorMes[mes] == null) {
	                alugueisPorMes[mes] = 0.0;
	            }
	            
	            // Adiciona o valor do aluguel ao mês correspondente
	            alugueisPorMes[mes] += a.getValorTotal();
	        }
	    }

	    // Ordena os imóveis pelo total arrecadado e seleciona os top 3
	    List<Map.Entry<String, Double[]>> imoveisOrdenados = imovelAlugueis.entrySet().stream()
	        .sorted((e1, e2) -> Double.compare(
	            Arrays.stream(e2.getValue()).mapToDouble(val -> val == null ? 0.0 : val).sum(),
	            Arrays.stream(e1.getValue()).mapToDouble(val -> val == null ? 0.0 : val).sum()))
	        .limit(3)
	        .collect(Collectors.toList());

	    List<BarraDTO> datasets = new ArrayList<>();
	    String[] cores = {"#FF914D", "#011E44", "#D8E1E3"};

	    for (int i = 0; i < imoveisOrdenados.size(); i++) {
	        Map.Entry<String, Double[]> entry = imoveisOrdenados.get(i);
	        BarraDTO barra = new BarraDTO();
	        barra.setLabel(entry.getKey());

	        // Garante que os valores nulos sejam substituídos por 0.0
	        List<Double> data = Arrays.stream(entry.getValue())
	                                  .map(val -> val == null ? 0.0 : val)
	                                  .collect(Collectors.toList());

	        barra.setData(data);
	        barra.setBackgroundColor(Collections.singletonList(cores[i]));
	        datasets.add(barra);
	    }

	    return datasets;
	}
	
	public DiferencaDTO calcularDiferenca(int idAnfitriao) throws RenttavelException {
		List<Aluguel> alugueis = repo.consultarPorAnfitriao(idAnfitriao);
		LocalDate dataAtual = LocalDate.now();
		DiferencaDTO dto = new DiferencaDTO();
		LocalDateTime data1 = dataAtual.minusMonths(1).atStartOfDay();;
		LocalDateTime data2 = dataAtual.minusMonths(2).atStartOfDay();;
		double totalMes1 = 0;
		double totalMes2 = 0;
		
		for(Aluguel a : alugueis) {
			if(a.getDataCheckin().getYear() == dataAtual.getYear()) {
				if(a.getDataCheckin().getMonthValue() == (dataAtual.getMonthValue() - 1)) {
					totalMes1 += a.getValorTotal();
				}
				if(a.getDataCheckin().getMonthValue() == (dataAtual.getMonthValue() - 2)) {
					totalMes2 += a.getValorTotal();
				}	
			}
		}
		
		double rendimentoMesAtual = this.calcularRendimentoMensal(idAnfitriao);
		
		dto.setPorcentagem1(((totalMes1 - rendimentoMesAtual) / rendimentoMesAtual) * 100);
		dto.setPorcentagem2(((totalMes2 - rendimentoMesAtual) / rendimentoMesAtual) * 100);		
		dto.setMes1(this.getStringMes(data1));
		dto.setMes2(this.getStringMes(data2));
		
		
		return dto;
	}
	
	public String getStringMes(LocalDateTime data) {
		
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM", new Locale("pt", "BR"));
		
		String stringMes = data.format(fmt);
		
		return stringMes;
	}
}