# Backend do projeto Renttavel
O Renttavel é um gerenciador de alugueis/hospedagens, nele o anfitrião terá uma visão financeira detalhada dos seus imoveis, gestão de manutenção e limpeza, gestão de reservas, planejamento para expansão de novos imóveis e relatórios.

Na versão atual do sistema (0.0.1), são antendidos os seguintes requisitos e regras de negócio:

- ### Anfitrião
  - CRUD completo
- ### Endereço
  - CRUD completo
  - Busca com filtros
  - Paginação
  - <b>Regras de negócio:</b>
    - Um endereço não pode ser excluído caso exista um imóvel cadastrado com o mesmo
- ### Imóvel
  - CRUD completo
  - Busca com filtros
  - Paginação
  - <b>Regras de negócio:</b>
    - Um imóvel não pode ser excluído caso exista um aluguel cadastrado com o mesmo
    - O campo 'isOcupado' de um imóvel é atualizado automaticamente em consultarPorId(), cosultarTodos() e consultarComSeletor(), conta caso exista um aluguel ativo com esse imovel
- ### Inquilino
  - CRUD completo
  - Busca com filtros
  - Paginação
  - <b>Regras de negócio:</b>
    - Um inquilino não pode ser excluído caso exista um aluguel cadastrado com o mesmo
- ### Aluguel
  - CRUD completo
  - Busca com filtros
  - Paginação
  - <b>Regras de negócio:</b>
    - Validação do campo 'valorTotal', verificando se é o resultado de [(Valor Diária * Quantidade de dias) + Valor Limpeza + Valor Multa], considerando uma margem de erro de 0,10
  

  


