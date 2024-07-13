# Backend do projeto Renttavel
O Renttavel é um gerenciador de alugueis/hospedagens, nele o anfitrião terá uma visão financeira detalhada dos seus imoveis, gestão de manutenção e limpeza, gestão de reservas, planejamento para expansão de novos imóveis e relatórios.

Na versão atual do sistema (0.0.1), são antendidos os seguintes requisitos e regras de negócio:

- ### Anfitrião (Usuário)
  - CRUD completo
  - Senhas criptografadas
  - <b>Regras de negócio:</b>
    - Não é permitido cadastrar/alterar dois ou mais anfitriões com o mesmo nome
    - Validação no preenchimento dos campos em salvar() e alterar()
- ### Endereço
  - CRUD completo
  - Busca com filtros
  - Paginação
  - <b>Regras de negócio:</b>
    - Um endereço não pode ser excluído caso exista um imóvel cadastrado com o mesmo
    - Não é permitido cadastrar dois ou mais endereços com o mesmo CEP e número
    - Validação no preenchimento dos campos em salvar() e alterar()
- ### Imóvel
  - CRUD completo
  - Busca com filtros
  - Paginação
  - <b>Regras de negócio:</b>
    - Um imóvel não pode ser excluído caso exista um aluguel cadastrado com o mesmo
    - Não é possível cadastrar dois ou mais imóveis com o mesmo nome
    - O campo 'isOcupado' de um imóvel é atualizado automaticamente em consultarPorId(), cosultarTodos() e consultarComSeletor(), conta caso exista um aluguel ativo com esse imovel
    - Validação no preenchimento dos campos em salvar() e alterar()
- ### Inquilino
  - CRUD completo
  - Busca com filtros
  - Paginação
  - <b>Regras de negócio:</b>
    - Um inquilino não pode ser excluído caso exista um aluguel cadastrado com o mesmo
    - Não é possível cadastrar dois inquilinos com o mesmo e-mail
- ### Aluguel
  - CRUD completo
  - Busca com filtros
  - Paginação
  - <b>Regras de negócio:</b>
    - Validação do campo 'valorTotal', verificando se é o resultado de [(Valor Diária * Quantidade de dias) + Valor Limpeza + Valor Multa], considerando uma margem de erro de 0,10
    - Não é possível cadastrar aluguéis no mesmo Imóvel em datas conflitantes
    - Não é possível cadastrar aluguéis com datas de checkout previsto e chekckout efetivo anteriores a data de checkin
- ### Autenticação
  - Autenticação de usuário completa
  - Id de sessão utilizando UUID
    
  

  


